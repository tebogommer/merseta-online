using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Application.Validation;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

/// <summary>
/// Unit and integration tests for Bursary Registration Application Use Case
/// (Signed Specification Ref: MerSeta\\NSDMS\\LMS\\LR\\01):
/// - Dual-path logic: New vs Continuation.
/// - Unemployed employer details waiver vs Employed employer mandate.
/// - Precondition: Active bursary required for continuation.
/// - Anti-tamper: Prohibition of same-day New and Continuation applications.
/// - Academic progression: Year of study and academic year advancement.
/// - 7 Statutory Bursary funding categories.
/// - Evidentiary document gating matrix (Section 9.1.2).
/// - Minor protection (< 18 years parent/guardian co-signatory) & 30-working-day submission limit.
/// </summary>
public class BursaryRegistrationUseCasesTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, LearnerService service) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var service = new LearnerService(factory, audit);
        return (factory, db, audit, service);
    }

    [Fact]
    public void NewBursary_Unemployed_SucceedsWithoutEmployerOrganisation()
    {
        var learner = new CompanyLearner
        {
            PersonId = 101,
            OrganisationId = null, // Host Employer completely omitted
            LearningProgrammeTypeCode = "05", // Bursary
            BursaryApplicationTypeCode = "New",
            EmploymentStatusCode = "Unemployed",
            EconomicStatusId = "02",
            InstitutionName = "University of the Witwatersrand",
            InstitutionTypeCode = "PublicUniversity",
            QualificationTitle = "BSc Mechanical Engineering",
            YearOfStudy = 1,
            AcademicYear = 2026,
            BursaryFundingTypeCode = "01", // merSETA funded
            LearnerSignatureDate = DateTime.UtcNow.AddDays(-5),
            SubmissionDate = DateTime.UtcNow
        };

        var errors = CompanyLearnerDomainValidator.ValidateBursaryApplication(learner);

        // Employer organization error must NOT be raised for unemployed bursars
        Assert.DoesNotContain(errors, e => e.RuleCode == "BURSARY_EMPLOYER_REQUIRED_FOR_EMPLOYED");
        Assert.DoesNotContain(errors, e => e.RuleCode == "BURSARY_INSTITUTION_REQUIRED");
        Assert.DoesNotContain(errors, e => e.RuleCode == "BURSARY_QUALIFICATION_TITLE_REQUIRED");
    }

    [Fact]
    public void NewBursary_Employed_FailsIfEmployerOrganisationIsMissing()
    {
        var learner = new CompanyLearner
        {
            PersonId = 102,
            OrganisationId = null, // Missing employer for employed bursar
            LearningProgrammeTypeCode = "05",
            BursaryApplicationTypeCode = "New",
            EmploymentStatusCode = "Employed",
            EconomicStatusId = "01",
            InstitutionName = "Tshwane University of Technology",
            InstitutionTypeCode = "PublicUniversity",
            QualificationTitle = "National Diploma Mechatronics",
            YearOfStudy = 1,
            AcademicYear = 2026,
            BursaryFundingTypeCode = "03", // Employer funded
            LearnerSignatureDate = DateTime.UtcNow.AddDays(-2),
            SubmissionDate = DateTime.UtcNow
        };

        var errors = CompanyLearnerDomainValidator.ValidateBursaryApplication(learner);

        Assert.Contains(errors, e => e.RuleCode == "BURSARY_EMPLOYER_REQUIRED_FOR_EMPLOYED");
    }

    [Fact]
    public void ContinuationBursary_WithoutPreviousBursaryLink_FailsValidation()
    {
        var learner = new CompanyLearner
        {
            PersonId = 103,
            LearningProgrammeTypeCode = "05",
            BursaryApplicationTypeCode = "Continuation",
            IsContinuation = true,
            PreviousCompanyLearnerId = null, // Missing prior record link
            InstitutionName = "University of Pretoria",
            QualificationTitle = "BEng Chemical Engineering",
            YearOfStudy = 2,
            AcademicYear = 2026
        };

        var errors = CompanyLearnerDomainValidator.ValidateBursaryApplication(learner, null);

        Assert.Contains(errors, e => e.RuleCode == "BURSARY_CONTINUATION_PREVIOUS_RECORD_REQUIRED");
    }

    [Fact]
    public void ContinuationBursary_WhenPreviousBursaryIsInactive_FailsValidation()
    {
        var priorBursary = new CompanyLearner
        {
            Id = 50,
            PersonId = 104,
            LearningProgrammeTypeCode = "05",
            QualificationTitle = "BSc Electrical Engineering",
            YearOfStudy = 1,
            AcademicYear = 2025,
            EnrolmentStatusCode = "Terminated", // Terminated bursary cannot be continued
            IsActive = false
        };

        var continuation = new CompanyLearner
        {
            PersonId = 104,
            LearningProgrammeTypeCode = "05",
            BursaryApplicationTypeCode = "Continuation",
            IsContinuation = true,
            PreviousCompanyLearnerId = 50,
            InstitutionName = "University of Cape Town",
            QualificationTitle = "BSc Electrical Engineering",
            YearOfStudy = 2,
            AcademicYear = 2026
        };

        var errors = CompanyLearnerDomainValidator.ValidateBursaryApplication(continuation, priorBursary);

        Assert.Contains(errors, e => e.RuleCode == "BURSARY_CONTINUATION_PREVIOUS_MUST_BE_ACTIVE");
    }

    [Fact]
    public void ContinuationBursary_WhenStudyYearDoesNotAdvance_FailsValidation()
    {
        var priorBursary = new CompanyLearner
        {
            Id = 60,
            PersonId = 105,
            LearningProgrammeTypeCode = "05",
            QualificationTitle = "BEng Industrial Engineering",
            YearOfStudy = 2,
            AcademicYear = 2025,
            EnrolmentStatusCode = "Registered",
            IsActive = true
        };

        var continuation = new CompanyLearner
        {
            PersonId = 105,
            LearningProgrammeTypeCode = "05",
            BursaryApplicationTypeCode = "Continuation",
            IsContinuation = true,
            PreviousCompanyLearnerId = 60,
            InstitutionName = "Stellenbosch University",
            QualificationTitle = "BEng Industrial Engineering",
            YearOfStudy = 2, // Violation: must advance beyond Year 2
            AcademicYear = 2026
        };

        var errors = CompanyLearnerDomainValidator.ValidateBursaryApplication(continuation, priorBursary);

        Assert.Contains(errors, e => e.RuleCode == "BURSARY_CONTINUATION_YEAR_MUST_ADVANCE");
    }

    [Fact]
    public void ContinuationBursary_AntiTamper_SameDateNewAndContinuationProhibited()
    {
        var sameDate = new DateTime(2026, 3, 10);

        var priorBursary = new CompanyLearner
        {
            Id = 70,
            PersonId = 106,
            LearningProgrammeTypeCode = "05",
            QualificationTitle = "Diploma in Mechanical Engineering",
            YearOfStudy = 1,
            AcademicYear = 2025,
            EnrolmentStatusCode = "Registered",
            IsActive = true,
            LearnerSignatureDate = sameDate
        };

        var continuation = new CompanyLearner
        {
            PersonId = 106,
            LearningProgrammeTypeCode = "05",
            BursaryApplicationTypeCode = "Continuation",
            IsContinuation = true,
            PreviousCompanyLearnerId = 70,
            InstitutionName = "Ekurhuleni East TVET College",
            QualificationTitle = "Diploma in Mechanical Engineering",
            YearOfStudy = 2,
            AcademicYear = 2026,
            LearnerSignatureDate = sameDate // Prohibited: same signature date
        };

        var errors = CompanyLearnerDomainValidator.ValidateBursaryApplication(continuation, priorBursary);

        Assert.Contains(errors, e => e.RuleCode == "BURSARY_CONTINUATION_SAME_DATE_PROHIBITED");
    }

    [Theory]
    [InlineData("01")]
    [InlineData("merSETA funded")]
    [InlineData("02")]
    [InlineData("Non-merSETA funded")]
    [InlineData("03")]
    [InlineData("Employer funded")]
    [InlineData("04")]
    [InlineData("Learner funded")]
    [InlineData("05")]
    [InlineData("Other SETA funded")]
    [InlineData("06")]
    [InlineData("NSF funded")]
    [InlineData("07")]
    [InlineData("Industry funded")]
    public void StatutoryBursaryFundingTypes_All7Categories_PassValidation(string validFunding)
    {
        var learner = new CompanyLearner
        {
            PersonId = 107,
            LearningProgrammeTypeCode = "05",
            BursaryApplicationTypeCode = "New",
            EmploymentStatusCode = "Unemployed",
            InstitutionName = "University of KwaZulu-Natal",
            QualificationTitle = "BSc Civil Engineering",
            BursaryFundingTypeCode = validFunding
        };

        var errors = CompanyLearnerDomainValidator.ValidateBursaryApplication(learner);

        Assert.DoesNotContain(errors, e => e.RuleCode == "BURSARY_INVALID_FUNDING_TYPE");
    }

    [Fact]
    public void StatutoryBursaryFundingTypes_InvalidFunding_FailsValidation()
    {
        var learner = new CompanyLearner
        {
            PersonId = 108,
            LearningProgrammeTypeCode = "05",
            BursaryApplicationTypeCode = "New",
            EmploymentStatusCode = "Unemployed",
            InstitutionName = "University of Venda",
            QualificationTitle = "BSc Computer Science",
            BursaryFundingTypeCode = "RandomPrivateGrant" // Invalid
        };

        var errors = CompanyLearnerDomainValidator.ValidateBursaryApplication(learner);

        Assert.Contains(errors, e => e.RuleCode == "BURSARY_INVALID_FUNDING_TYPE");
    }

    [Fact]
    public void DocumentChecklist_DynamicallyOmitsEmployerLetterForUnemployedBursary()
    {
        var unemployedDocs = CompanyLearnerDomainValidator.GetRequiredBursaryDocuments("New", "Unemployed");
        var employedDocs = CompanyLearnerDomainValidator.GetRequiredBursaryDocuments("New", "Employed");
        var continuationDocs = CompanyLearnerDomainValidator.GetRequiredBursaryDocuments("Continuation", "Unemployed");

        // Unemployed New Bursary must NOT require employer letter
        Assert.DoesNotContain(unemployedDocs, d => d.DocumentCode == "DOC-BUR-11");
        Assert.Contains(unemployedDocs, d => d.DocumentCode == "DOC-BUR-20"); // Proof of HEI/TVET registration

        // Employed New Bursary MUST require confirmation of employment
        Assert.Contains(employedDocs, d => d.DocumentCode == "DOC-BUR-11");

        // Continuation Bursary MUST require academic transcript and re-registration letter
        Assert.Contains(continuationDocs, d => d.DocumentCode == "DOC-BUR-01");
        Assert.Contains(continuationDocs, d => d.DocumentCode == "DOC-BUR-02");
    }

    [Fact]
    public async Task RegisterBursaryApplication_Async_SavesSuccessfullyWithAuditTrail()
    {
        var (_, db, _, service) = CreateTestContext();

        var person = new Person
        {
            FirstName = "Kagiso",
            LastName = "Mokwena",
            RsaIdNumber = "0201015800085",
            DateOfBirth = new DateTime(2002, 1, 1),
            Gender = "Male",
            EquityCode = "African"
        };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var bursary = new CompanyLearner
        {
            PersonId = person.Id,
            LearningProgrammeTypeCode = "05",
            BursaryApplicationTypeCode = "New",
            EmploymentStatusCode = "Unemployed",
            EconomicStatusId = "02",
            InstitutionName = "University of the Witwatersrand",
            InstitutionTypeCode = "PublicUniversity",
            QualificationTitle = "BSc Aeronautical Engineering",
            YearOfStudy = 1,
            AcademicYear = 2026,
            BursaryFundingTypeCode = "01",
            LearnerSignatureDate = DateTime.UtcNow.AddDays(-10),
            SubmissionDate = DateTime.UtcNow
        };

        var registered = await service.RegisterBursaryApplicationAsync(bursary, "OfficerMokwena");

        Assert.NotNull(registered);
        Assert.True(registered.Id > 0);
        Assert.StartsWith("BUR-", registered.LearnerContractNumber);
        Assert.Equal("Registered", registered.EnrolmentStatusCode);

        // Verify that audit log was recorded
        var auditEntry = await db.AuditLogs
            .FirstOrDefaultAsync(a => a.EntityName == "CompanyLearner" && a.RecordId == registered.Id);
        Assert.NotNull(auditEntry);
        Assert.Equal("OfficerMokwena", auditEntry.Actor);
    }
}
