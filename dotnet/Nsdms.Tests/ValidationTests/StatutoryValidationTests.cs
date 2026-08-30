using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Utilities;
using Nsdms.Application.Services;
using Nsdms.Application.Validation;
using Nsdms.Domain.Entities;
using Xunit;

namespace Nsdms.Tests.ValidationTests;

public class StatutoryValidationTests
{
    [Theory]
    [InlineData("L123456789", true)]
    [InlineData("N987654321", true)]
    [InlineData("W111222333", true)]
    [InlineData("L12345", false)] // Length too short
    [InlineData("X123456789", false)] // Invalid prefix
    [InlineData("", false)]
    public void SdlNumberValidator_ValidatesFormatCorrectly(string sdl, bool expectedValid)
    {
        var result = SdlNumberValidator.Validate(sdl);
        if (expectedValid)
        {
            Assert.True(result);
        }
        else
        {
            Assert.False(result);
        }
    }

    [Fact]
    public void PersonDomainValidator_DetectsMissingRsaIdForSaCitizen()
    {
        var person = new Person
        {
            Id = 1,
            FirstName = "John",
            LastName = "Doe",
            CitizenStatusCode = "SA",
            IsSouthAfricanCitizen = true,
            RsaIdNumber = "",
            EquityCode = "BA",
            GenderCode = "M"
        };

        var errors = PersonDomainValidator.Validate(person);

        Assert.Contains(errors, e => e.RuleCode == "SETMIS_400_01_RSA_ID_REQUIRED");
    }

    [Fact]
    public void PersonDomainValidator_DetectsDobMismatchWithRsaId()
    {
        // 8001015009087 -> Born 1980-01-01
        var person = new Person
        {
            Id = 2,
            FirstName = "Jane",
            LastName = "Smith",
            CitizenStatusCode = "SA",
            IsSouthAfricanCitizen = true,
            RsaIdNumber = "8001015009087",
            DateOfBirth = new DateTime(1995, 5, 20), // Mismatch!
            EquityCode = "BA",
            GenderCode = "F"
        };

        var errors = PersonDomainValidator.Validate(person);

        Assert.Contains(errors, e => e.RuleCode == "SETMIS_400_15_DOB_MISMATCH_WITH_ID");
    }

    [Fact]
    public void PersonDomainValidator_WarnsWhenWashingtonGroupIndicatesDifficultyButDisabilityIsNone()
    {
        var person = new Person
        {
            Id = 3,
            FirstName = "Thabo",
            LastName = "Mokoena",
            CitizenStatusCode = "SA",
            IsSouthAfricanCitizen = true,
            RsaIdNumber = "8001015009087",
            DateOfBirth = new DateTime(1980, 1, 1),
            EquityCode = "BA",
            GenderCode = "M",
            DisabilityCode = "00", // None
            SeeingRatingId = "03" // A lot of difficulty!
        };

        var errors = PersonDomainValidator.Validate(person);

        Assert.Contains(errors, e => e.RuleCode == "SETMIS_400_10_DISABILITY_WG_MISMATCH");
    }

    [Fact]
    public void CompanyLearnerDomainValidator_EnforcesPartOfQualificationDependency()
    {
        var learner = new CompanyLearner
        {
            Id = 10,
            PersonId = 1,
            PartOfId = "02", // Part of Qualification
            SaqaQualificationId = null, // Missing!
            CommencementDate = DateTime.UtcNow.AddMonths(-6)
        };

        var errors = CompanyLearnerDomainValidator.Validate(learner);

        Assert.Contains(errors, e => e.RuleCode == "SETMIS_500_03_PARTOF_QUAL_REQUIRED");
    }

    [Fact]
    public void CompanyLearnerDomainValidator_EnforcesPartOfLearnershipDependency()
    {
        var learner = new CompanyLearner
        {
            Id = 11,
            PersonId = 1,
            PartOfId = "03", // Part of Learnership
            LearnershipId = null, // Missing!
            CommencementDate = DateTime.UtcNow.AddMonths(-6)
        };

        var errors = CompanyLearnerDomainValidator.Validate(learner);

        Assert.Contains(errors, e => e.RuleCode == "SETMIS_500_02_PARTOF_LEARNERSHIP_REQUIRED");
    }

    [Fact]
    public void CompanyLearnerDomainValidator_DetectsCompletionBeforeCommencement()
    {
        var learner = new CompanyLearner
        {
            Id = 12,
            PersonId = 1,
            CommencementDate = new DateTime(2026, 6, 1),
            CompletionDate = new DateTime(2026, 1, 1) // Before commencement!
        };

        var errors = CompanyLearnerDomainValidator.Validate(learner);

        Assert.Contains(errors, e => e.RuleCode == "SETMIS_500_09_COMPLETION_BEFORE_COMMENCEMENT");
    }

    [Fact]
    public void CompanyLearnerDomainValidator_DetectsUnderAgeLearner()
    {
        var learner = new CompanyLearner
        {
            Id = 13,
            PersonId = 1,
            Person = new Person
            {
                DateOfBirth = new DateTime(2015, 1, 1) // 11 years old in 2026!
            },
            CommencementDate = new DateTime(2026, 1, 1)
        };

        var errors = CompanyLearnerDomainValidator.Validate(learner);

        Assert.Contains(errors, e => e.RuleCode == "SETMIS_500_08_MINIMUM_AGE_VIOLATION");
    }

    [Fact]
    public void TrainingProviderDomainValidator_RequiresAccreditationDatesWhenAccredited()
    {
        var provider = new TrainingProvider
        {
            Id = 1,
            ProviderCode = "PRV-001",
            ProviderStatusId = "01",
            AccreditationNumber = "ACC-001",
            AccreditationStartDate = null, // Missing
            AccreditationEndDate = null // Missing
        };

        var errors = TrainingProviderDomainValidator.Validate(provider);

        Assert.Contains(errors, e => e.RuleCode == "SETMIS_100_18_ACCREDITATION_DATES_REQUIRED");
    }

    [Fact]
    public void OrganisationDomainValidator_DetectsMissingSdlAndLegalName()
    {
        var org = new Organisation
        {
            Id = 1,
            SdlNumber = "",
            LegalName = ""
        };

        var errors = OrganisationDomainValidator.Validate(org);

        Assert.Contains(errors, e => e.RuleCode == "SETMIS_200_01_SDL_REQUIRED");
        Assert.Contains(errors, e => e.RuleCode == "SETMIS_200_03_LEGAL_NAME_REQUIRED");
    }

    [Fact]
    public void EtqaAssessorDomainValidator_RequiresRegistrationNumber()
    {
        var assessor = new EtqaAssessor
        {
            Id = 1,
            PersonId = 1,
            RegistrationNumber = ""
        };

        var errors = EtqaAssessorDomainValidator.Validate(assessor);

        Assert.Contains(errors, e => e.RuleCode == "SETMIS_401_03_REG_NUMBER_REQUIRED");
    }
}
