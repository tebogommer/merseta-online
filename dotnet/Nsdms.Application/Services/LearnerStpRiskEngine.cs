using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Models;
using Nsdms.Application.Validation;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Implementation of the Straight-Through Processing (STP) Risk Engine.
/// Evaluates applications submitted via automated bulk or self-service channels.
/// Powered by dynamic business rule evaluation with built-in statutory fallback.
/// </summary>
public class LearnerStpRiskEngine : ILearnerStpRiskEngine
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IQualificationEnrolmentGatekeeperService _gatekeeperService;
    private readonly IBusinessRuleEngineService? _ruleEngine;
    private readonly ISystemConfigurationService? _configService;

    public LearnerStpRiskEngine(
        INsdmsDbContextFactory contextFactory,
        IQualificationEnrolmentGatekeeperService gatekeeperService,
        IBusinessRuleEngineService? ruleEngine = null,
        ISystemConfigurationService? configService = null)
    {
        _contextFactory = contextFactory;
        _gatekeeperService = gatekeeperService;
        _ruleEngine = ruleEngine;
        _configService = configService;
    }

    public async Task<StpEvaluationResult> EvaluateRegistrationRiskAsync(CompanyLearner learner)
    {
        var riskFactors = new List<string>();
        var compliantFactors = new List<string>();

        using var db = await _contextFactory.CreateDbContextAsync();

        // 1. Gather Fact Metrics for Rule Evaluation
        bool hasValidEmployer = false;
        bool isEmployerActive = false;
        string? employerName = null;

        if (learner.OrganisationId.HasValue)
        {
            var org = await db.Organisations.FindAsync(learner.OrganisationId.Value);
            if (org != null)
            {
                hasValidEmployer = true;
                isEmployerActive = org.LevyCategoryCode != "NON_LEVY_PAYING" || org.IsActive;
                employerName = $"{org.LegalName} (SDL: {org.SdlNumber})";
            }
        }

        bool isUnemployedBursary = learner.LearningProgrammeTypeCode == "05" && learner.EmploymentStatusCode == "Unemployed";

        bool hasSaqa = learner.SaqaQualificationId.HasValue && learner.SaqaQualificationId.Value > 0;
        bool isQualActive = true;
        string? qualMessage = null;

        if (hasSaqa)
        {
            var saqaId = learner.SaqaQualificationId!.Value;
            var gatekeeperResult = await _gatekeeperService.ValidateQualificationAsync(saqaId, learner.QualificationTitle);
            isQualActive = gatekeeperResult.IsEnrolmentAllowed;
            qualMessage = gatekeeperResult.Message;
        }

        int workingDays = 0;
        bool hasExecutionDate = learner.LearnerSignatureDate.HasValue && learner.LearnerSignatureDate.Value > DateTime.MinValue;
        if (hasExecutionDate)
        {
            var submissionDate = learner.SubmissionDate ?? DateTime.UtcNow;
            workingDays = CompanyLearnerDomainValidator.CalculateWorkingDays(learner.LearnerSignatureDate!.Value, submissionDate);
        }

        double age = 18.0;
        bool hasMinorGuardian = false;
        var person = learner.Person ?? (learner.PersonId > 0 ? await db.People.FindAsync(learner.PersonId) : null);
        if (person?.DateOfBirth.HasValue == true)
        {
            age = (DateTime.UtcNow - person.DateOfBirth.Value).TotalDays / 365.25;
            if (age < 18)
            {
                var guardian = await db.PersonGuardians.FirstOrDefaultAsync(g => g.PersonId == person.Id && g.IsActive);
                hasMinorGuardian = guardian != null;
            }
            else
            {
                hasMinorGuardian = true;
            }
        }

        bool isContinuation = learner.LearningProgrammeTypeCode == "05" && learner.BursaryApplicationTypeCode == "Continuation";
        bool hasPredecessor = learner.PreviousCompanyLearnerId.HasValue;
        bool hasPassedTranscripts = learner.ContinuationAcademicResultsPassed == true;

        // 2. Evaluate via Dynamic Rules Engine if available
        if (_ruleEngine != null)
        {
            var payload = new LearnerStpPayload
            {
                PersonId = learner.PersonId,
                Age = age,
                HasMinorGuardian = hasMinorGuardian,
                HasValidEmployer = hasValidEmployer && isEmployerActive,
                IsEmployerActive = isEmployerActive,
                IsUnemployedBursary = isUnemployedBursary,
                HasSaqaQualification = hasSaqa,
                IsQualificationActive = isQualActive,
                WorkingDaysElapsed = hasExecutionDate ? workingDays : 999,
                IsContinuationBursary = isContinuation,
                HasPredecessorBursary = hasPredecessor,
                HasPassedTranscripts = hasPassedTranscripts
            };

            var ruleOutcome = await _ruleEngine.EvaluateWorkflowAsync("LearnerStpEvaluation", payload);

            if (!ruleOutcome.IsSuccess)
            {
                foreach (var failure in ruleOutcome.Failures)
                {
                    riskFactors.Add(failure.ErrorMessage);
                }
            }

            foreach (var passed in ruleOutcome.PassedRuleNames)
            {
                compliantFactors.Add($"Verified compliant rule gate: {passed}");
            }
        }
        else
        {
            // Programmatic Fallback Rules
            if (learner.OrganisationId.HasValue)
            {
                if (!hasValidEmployer)
                    riskFactors.Add("Employer organisation record does not exist.");
                else if (!isEmployerActive)
                    riskFactors.Add("Employer is not active or compliant in the levy registry.");
                else
                    compliantFactors.Add($"Verified Employer in good standing: {employerName}");
            }
            else if (!isUnemployedBursary)
            {
                riskFactors.Add("Employer is mandatory for this learning programme type.");
            }
            else
            {
                compliantFactors.Add("Statutorily exempt unemployed bursary applicant (no employer required).");
            }

            if (hasSaqa && !isQualActive)
                riskFactors.Add($"Qualification enrolment blocked: {qualMessage}");
            else if (hasSaqa)
                compliantFactors.Add($"Active SAQA qualification confirmed.");

            var maxSignatureDays = _configService != null 
                ? await _configService.GetValueAsync<int>("LearnerRegistration:MaxSignatureElapsedBusinessDays", 30) 
                : 30;

            if (!hasExecutionDate)
                riskFactors.Add("Agreement execution signature date is missing.");
            else if (workingDays > maxSignatureDays)
                riskFactors.Add($"Submission overdue ({workingDays} working days elapsed vs {maxSignatureDays}-day limit). Requires human officer condonation.");
            else
                compliantFactors.Add($"Submission timing compliant ({workingDays} working days elapsed).");

            if (age < 18 && !hasMinorGuardian)
                riskFactors.Add("Minor applicant (< 18) without an active verified co-signatory guardian.");
            else if (age < 18)
                compliantFactors.Add("Minor co-signatory guardian verified.");
            else
                compliantFactors.Add("Adult applicant (>= 18).");

            if (isContinuation)
            {
                if (!hasPredecessor)
                    riskFactors.Add("Continuation bursary missing link to predecessor active bursary.");
                else if (!hasPassedTranscripts)
                    riskFactors.Add("Continuation bursary missing verified passing academic transcripts.");
                else
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
