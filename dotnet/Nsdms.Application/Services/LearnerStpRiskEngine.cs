using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Validation;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Implementation of the Straight-Through Processing (STP) Risk Engine.
/// Evaluates applications submitted via automated bulk or self-service channels.
/// </summary>
public class LearnerStpRiskEngine : ILearnerStpRiskEngine
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IQualificationEnrolmentGatekeeperService _gatekeeperService;

    public LearnerStpRiskEngine(
        INsdmsDbContextFactory contextFactory,
        IQualificationEnrolmentGatekeeperService gatekeeperService)
    {
        _contextFactory = contextFactory;
        _gatekeeperService = gatekeeperService;
    }

    public async Task<StpEvaluationResult> EvaluateRegistrationRiskAsync(CompanyLearner learner)
    {
        var riskFactors = new List<string>();
        var compliantFactors = new List<string>();

        using var db = await _contextFactory.CreateDbContextAsync();

        // Rule 1: Employer Standing & Accreditation
        if (learner.OrganisationId.HasValue)
        {
            var org = await db.Organisations.FindAsync(learner.OrganisationId.Value);
            if (org == null)
            {
                riskFactors.Add("Employer organisation record does not exist.");
            }
            else if (org.LevyCategoryCode == "NON_LEVY_PAYING" && !org.IsActive)
            {
                riskFactors.Add("Employer is not active or compliant in the levy registry.");
            }
            else
            {
                compliantFactors.Add($"Verified Employer in good standing: {org.LegalName} (SDL: {org.SdlNumber})");
            }
        }
        else if (learner.LearningProgrammeTypeCode != "05" || learner.EmploymentStatusCode != "Unemployed")
        {
            riskFactors.Add("Employer is mandatory for this learning programme type.");
        }
        else
        {
            compliantFactors.Add("Statutorily exempt unemployed bursary applicant (no employer required).");
        }

        // Rule 2: Qualification Validity & Last Date for Enrolment
        if (learner.SaqaQualificationId.HasValue && learner.SaqaQualificationId.Value > 0)
        {
            var saqaId = learner.SaqaQualificationId.Value;
            var gatekeeperResult = await _gatekeeperService.ValidateQualificationAsync(saqaId, learner.QualificationTitle);
            if (!gatekeeperResult.IsEnrolmentAllowed)
            {
                riskFactors.Add($"Qualification enrolment blocked: {gatekeeperResult.Message}");
            }
            else
            {
                compliantFactors.Add($"Active SAQA qualification #{saqaId} confirmed.");
            }
        }

        // Rule 3: 30-Working-Day Submission Window SLA
        if (learner.LearnerSignatureDate.HasValue && learner.LearnerSignatureDate.Value > DateTime.MinValue)
        {
            var submissionDate = learner.SubmissionDate ?? DateTime.UtcNow;
            var workingDays = CompanyLearnerDomainValidator.CalculateWorkingDays(learner.LearnerSignatureDate.Value, submissionDate);
            if (workingDays > 30)
            {
                riskFactors.Add($"Submission overdue ({workingDays} working days elapsed vs 30-day limit). Requires human officer condonation.");
            }
            else
            {
                compliantFactors.Add($"Submission timing compliant ({workingDays} working days elapsed).");
            }
        }
        else
        {
            riskFactors.Add("Agreement execution signature date is missing.");
        }

        // Rule 4: Minor Protection Check (< 18)
        var person = learner.Person ?? (learner.PersonId > 0 ? await db.People.FindAsync(learner.PersonId) : null);
        if (person?.DateOfBirth.HasValue == true)
        {
            var age = (DateTime.UtcNow - person.DateOfBirth.Value).TotalDays / 365.25;
            if (age < 18)
            {
                var guardian = await db.PersonGuardians.FirstOrDefaultAsync(g => g.PersonId == person.Id && g.IsActive);
                if (guardian == null)
                {
                    riskFactors.Add("Minor applicant (< 18) without an active verified co-signatory guardian.");
                }
                else
                {
                    compliantFactors.Add($"Minor co-signatory guardian verified: {guardian.GuardianFullName}");
                }
            }
            else
            {
                compliantFactors.Add("Adult applicant (>= 18).");
            }
        }

        // Rule 5: Bursary Continuation Integrity
        if (learner.LearningProgrammeTypeCode == "05" && learner.BursaryApplicationTypeCode == "Continuation")
        {
            if (!learner.PreviousCompanyLearnerId.HasValue)
            {
                riskFactors.Add("Continuation bursary missing link to predecessor active bursary.");
            }
            else if (learner.ContinuationAcademicResultsPassed != true)
            {
                riskFactors.Add("Continuation bursary missing verified passing academic transcripts.");
            }
            else
            {
                compliantFactors.Add("Bursary continuation academic progression confirmed.");
            }
        }

        bool isEligible = riskFactors.Count == 0;
        string decisionReason = isEligible
            ? "100% compliant with statutory SDA, SETMIS, and PFMA rules. Approved via Automated Straight-Through Processing."
            : $"Automated approval declined due to {riskFactors.Count} exceptions. Routed to Verification Officer review.";

        return new StpEvaluationResult(
            isEligible,
            decisionReason,
            riskFactors,
            compliantFactors
        );
    }
}
