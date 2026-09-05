using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Application.Validation;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class LearnerApplicationAlignmentTests
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
    public void WorkingDays_Calculation_ExcludesWeekendsCorrectly()
    {
        var monday = new DateTime(2026, 6, 1);
        var friday = new DateTime(2026, 6, 5);
        var workingDays5 = CompanyLearnerDomainValidator.CalculateWorkingDays(monday, friday);
        Assert.Equal(4, workingDays5);

        var nextMonday = new DateTime(2026, 6, 8);
        var workingDaysWeek = CompanyLearnerDomainValidator.CalculateWorkingDays(monday, nextMonday);
        Assert.Equal(5, workingDaysWeek);

        var sixWeeksLater = monday.AddDays(42);
        var workingDays6Weeks = CompanyLearnerDomainValidator.CalculateWorkingDays(monday, sixWeeksLater);
        Assert.True(workingDays6Weeks >= 30);
    }

    [Fact]
    public void StatutorySubmission_Within30WorkingDays_PassesValidation()
    {
        var signatureDate = new DateTime(2026, 5, 1);
        var submissionDate = new DateTime(2026, 5, 15);

        var learner = new CompanyLearner
        {
            LearnerContractNumber = "APP-2026-001",
            QualificationTitle = "Automotive Electrician",
            LearningProgrammeTypeCode = "Apprenticeship",
            LearnerSignatureDate = signatureDate,
            SubmissionDate = submissionDate
        };

        var errors = CompanyLearnerDomainValidator.Validate(learner);
        Assert.DoesNotContain(errors, e => e.RuleCode == "SETMIS_500_30DAY_DEADLINE_EXCEEDED");
    }

    [Fact]
    public void StatutorySubmission_Exceeding30WorkingDays_FailsValidation()
    {
        var signatureDate = new DateTime(2026, 1, 5);
        var submissionDate = new DateTime(2026, 3, 20);

        var learner = new CompanyLearner
        {
            LearnerContractNumber = "APP-2026-002",
            QualificationTitle = "Automotive Electrician",
            LearningProgrammeTypeCode = "Apprenticeship",
            LearnerSignatureDate = signatureDate,
            SubmissionDate = submissionDate
        };

        var errors = CompanyLearnerDomainValidator.Validate(learner);
        var deadlineError = Assert.Single(errors, e => e.RuleCode == "SETMIS_500_30DAY_DEADLINE_EXCEEDED");
        Assert.Contains("Maximum statutory submission window is 30 working days", deadlineError.Message);
    }

    [Fact]
    public void MinorApplicant_Under18_WithoutGuardian_FailsValidation()
    {
        var person = new Person
        {
            FirstName = "Kagiso",
            LastName = "Modise",
            RsaIdNumber = "0905150123088",
            DateOfBirth = DateTime.Today.AddYears(-17),
            Guardians = new List<PersonGuardian>()
        };

        var learner = new CompanyLearner
        {
            LearnerContractNumber = "APP-2026-003",
            QualificationTitle = "Welder",
            LearningProgrammeTypeCode = "Apprenticeship",
            CommencementDate = DateTime.Today,
            Person = person
        };

        var errors = CompanyLearnerDomainValidator.Validate(learner);
        var guardianError = Assert.Single(errors, e => e.RuleCode == "SETMIS_500_GUARDIAN_REQUIRED_FOR_MINOR");
        Assert.Contains("unmarried minor under 18 years of age", guardianError.Message);
    }

    [Fact]
    public void MinorApplicant_Under18_WithActiveGuardian_PassesValidation()
    {
        var guardian = new PersonGuardian
        {
            GuardianFullName = "Martha Modise",
            GuardianIdNumber = "7501010123089",
            ContactNumber = "0821112233",
            RelationshipTypeId = "01",
            IsActive = true
        };

        var person = new Person
        {
            FirstName = "Kagiso",
            LastName = "Modise",
            RsaIdNumber = "0905150123088",
            DateOfBirth = DateTime.Today.AddYears(-17),
            Guardians = new List<PersonGuardian> { guardian }
        };

        var learner = new CompanyLearner
        {
            LearnerContractNumber = "APP-2026-004",
            QualificationTitle = "Welder",
            LearningProgrammeTypeCode = "Apprenticeship",
            CommencementDate = DateTime.Today,
            Person = person
        };

        var errors = CompanyLearnerDomainValidator.Validate(learner);
        Assert.DoesNotContain(errors, e => e.RuleCode == "SETMIS_500_GUARDIAN_REQUIRED_FOR_MINOR");
    }

    [Fact]
    public void AdultApplicant_Over18_WithoutGuardian_PassesValidation()
    {
        var person = new Person
        {
            FirstName = "Lebo",
            LastName = "Khumalo",
            RsaIdNumber = "9801010123089",
            DateOfBirth = DateTime.Today.AddYears(-26),
            Guardians = new List<PersonGuardian>()
        };

        var learner = new CompanyLearner
        {
            LearnerContractNumber = "APP-2026-005",
            QualificationTitle = "Boilermaker",
            LearningProgrammeTypeCode = "Apprenticeship",
            CommencementDate = DateTime.Today,
            Person = person
        };

        var errors = CompanyLearnerDomainValidator.Validate(learner);
        Assert.DoesNotContain(errors, e => e.RuleCode == "SETMIS_500_GUARDIAN_REQUIRED_FOR_MINOR");
    }

    [Fact]
    public void CandidacyProgramme_WithoutProfessionalCouncilNumber_FailsValidation()
    {
        var learner = new CompanyLearner
        {
            LearnerContractNumber = "CND-2026-001",
            LearningProgrammeTypeCode = "06",
            ProfessionalRegistrationNumber = null
        };

        var errors = CompanyLearnerDomainValidator.Validate(learner);
        var candError = Assert.Single(errors, e => e.RuleCode == "SETMIS_500_CANDIDACY_REG_NUMBER_REQUIRED");
        Assert.Contains("Professional Council candidate registration reference", candError.Message);
    }

    [Fact]
    public void CandidacyProgramme_WithProfessionalCouncilNumber_PassesValidationWithoutQualification()
    {
        var learner = new CompanyLearner
        {
            LearnerContractNumber = "CND-2026-002",
            LearningProgrammeTypeCode = "06",
            ProfessionalRegistrationNumber = "ECSA-PR-ENG-2026",
            QualificationTitle = null
        };

        var errors = CompanyLearnerDomainValidator.Validate(learner);
        Assert.DoesNotContain(errors, e => e.RuleCode == "SETMIS_500_CANDIDACY_REG_NUMBER_REQUIRED");
        Assert.DoesNotContain(errors, e => e.RuleCode == "SETMIS_500_01_QUAL_TITLE_REQUIRED");
    }

    [Fact]
    public void Person_AntiPlaceholder_ValidatesNamesAndAddresses()
    {
        var placeholderPerson = new Person
        {
            FirstName = "UNKNOWN",
            LastName = "AS ABOVE",
            PhysicalAddress = "N/A",
            PostalAddress = "UNKNOWN",
            CellNumber = "0821234567"
        };

        var errors = PersonDomainValidator.Validate(placeholderPerson);
        Assert.Contains(errors, e => e.RuleCode == "SETMIS_400_03_FIRST_NAME_PLACEHOLDER");
        Assert.Contains(errors, e => e.RuleCode == "SETMIS_400_02_LAST_NAME_PLACEHOLDER");
        Assert.Contains(errors, e => e.RuleCode == "SETMIS_400_16_ADDRESS_PLACEHOLDER_FORBIDDEN");
    }

    [Fact]
    public void Person_Telephone_Enforces10DigitsRegex()
    {
        var invalidPhonePerson = new Person
        {
            FirstName = "Thabo",
            LastName = "Mokoena",
            CellNumber = "12345"
        };

        var errors = PersonDomainValidator.Validate(invalidPhonePerson);
        Assert.Contains(errors, e => e.RuleCode == "SETMIS_400_28_CELL_PHONE_INVALID");

        var validPhonePerson = new Person
        {
            FirstName = "Thabo",
            LastName = "Mokoena",
            CellNumber = "0821234567"
        };

        var validErrors = PersonDomainValidator.Validate(validPhonePerson);
        Assert.DoesNotContain(validErrors, e => e.RuleCode == "SETMIS_400_28_CELL_PHONE_INVALID");
    }

    [Fact]
    public async Task LearnerService_WithdrawLearnerApplication_SetsStatusAndAudits()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Sipho", LastName = "Nkosi", RsaIdNumber = "9901010123089" };
        var org = new Organisation { CompanyName = "Bell Equipment", SdlNumber = "L100200300" };
        db.People.Add(person);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var learner = await service.RegisterLearnerAsync(new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            QualificationTitle = "Fitter and Turner",
            LearningProgrammeTypeCode = "Apprenticeship"
        });

        var withdrawn = await service.WithdrawLearnerApplicationAsync(learner.Id, "ApplicantRequest", "Withdrawn due to relocation", "SdfOfficer");

        Assert.Equal("Withdrawn", withdrawn.EnrolmentStatusCode);
        Assert.Equal("ApplicantRequest", withdrawn.WithdrawalReasonCode);
        Assert.Contains("Withdrawn due to relocation", withdrawn.WithdrawalComments);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "CompanyLearner" && a.RecordId == learner.Id && a.ActionName == "WithdrawLearnerApplication");
        Assert.NotNull(auditLog);
    }

    [Fact]
    public async Task LearnerService_ResubmitLearnerApplication_SetsStatusAndAudits()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Zola", LastName = "Mabaso", RsaIdNumber = "0102030123084" };
        var org = new Organisation { CompanyName = "BMW SA", SdlNumber = "L200300400" };
        db.People.Add(person);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var learner = await service.RegisterLearnerAsync(new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            QualificationTitle = "Mechatronics Technician",
            LearningProgrammeTypeCode = "Learnership"
        });

        learner.EnrolmentStatusCode = "RejectedForResubmit";
        await db.SaveChangesAsync();

        learner.QualificationTitle = "Mechatronics Technician (Updated)";
        var resubmitted = await service.ResubmitLearnerApplicationAsync(learner.Id, learner, "SdfUser");

        Assert.Equal("Resubmitted", resubmitted.EnrolmentStatusCode);
        Assert.Equal("Mechatronics Technician (Updated)", resubmitted.QualificationTitle);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "CompanyLearner" && a.RecordId == learner.Id && a.ActionName == "ResubmitLearnerApplication");
        Assert.NotNull(auditLog);
    }

    [Fact]
    public async Task QualificationEnrolmentGatekeeperService_BlocksExpiredQualifications()
    {
        var gatekeeper = new QualificationEnrolmentGatekeeperService();

        var expiredResult = await gatekeeper.ValidateQualificationAsync(111999, "Legacy Fitter Qualification");
        Assert.False(expiredResult.IsEnrolmentAllowed);
        Assert.True(expiredResult.IsExpired);
        Assert.Contains("expired", expiredResult.Message, System.StringComparison.OrdinalIgnoreCase);

        var activeResult = await gatekeeper.ValidateQualificationAsync(65432, "Occupational Certificate: Motor Mechanic");
        Assert.True(activeResult.IsEnrolmentAllowed);
        Assert.False(activeResult.IsExpired);
    }
}
