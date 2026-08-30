using Nsdms.Application.Validation;
using Nsdms.Domain.Entities;
using Xunit;

namespace Nsdms.Tests.ValidationTests;

public class CrossEntityDateValidationTests
{
    [Fact]
    public void LearnerChronology_DetectsCommencementBeforeBirth()
    {
        var person = new Person
        {
            Id = 1,
            FirstName = "Sipho",
            LastName = "Khumalo",
            DateOfBirth = new DateTime(2005, 5, 10)
        };

        var learner = new CompanyLearner
        {
            Id = 101,
            PersonId = 1,
            Person = person,
            CommencementDate = new DateTime(2004, 1, 1), // Before birth!
            CompletionDate = new DateTime(2006, 1, 1)
        };

        var errors = CrossEntityDateValidator.ValidateLearnerChronology(learner, person);

        Assert.Contains(errors, e => e.RuleCode == "DATE_X01_COMMENCEMENT_BEFORE_BIRTH");
    }

    [Fact]
    public void LearnerChronology_DetectsAgeUnder15AtCommencement()
    {
        var person = new Person
        {
            Id = 2,
            FirstName = "Lerato",
            LastName = "Molefe",
            DateOfBirth = new DateTime(2012, 1, 1) // 13 years old in 2025
        };

        var learner = new CompanyLearner
        {
            Id = 102,
            PersonId = 2,
            Person = person,
            CommencementDate = new DateTime(2025, 6, 1),
            CompletionDate = new DateTime(2026, 6, 1)
        };

        var errors = CrossEntityDateValidator.ValidateLearnerChronology(learner, person);

        Assert.Contains(errors, e => e.RuleCode == "DATE_X02_MINIMUM_AGE_15_VIOLATION");
    }

    [Fact]
    public void LearnerChronology_PassesForValidAge15AndAbove()
    {
        var person = new Person
        {
            Id = 3,
            FirstName = "Kagiso",
            LastName = "Dlamini",
            DateOfBirth = new DateTime(2000, 1, 1)
        };

        var learner = new CompanyLearner
        {
            Id = 103,
            PersonId = 3,
            Person = person,
            CommencementDate = new DateTime(2020, 1, 1),
            CompletionDate = new DateTime(2021, 1, 1)
        };

        var errors = CrossEntityDateValidator.ValidateLearnerChronology(learner, person);

        Assert.DoesNotContain(errors, e => e.RuleCode.StartsWith("DATE_X0"));
    }

    [Fact]
    public void LearnerChronology_DetectsCompletionBeforeCommencement()
    {
        var learner = new CompanyLearner
        {
            Id = 104,
            PersonId = 4,
            CommencementDate = new DateTime(2026, 6, 1),
            CompletionDate = new DateTime(2025, 1, 1) // Before commencement!
        };

        var errors = CrossEntityDateValidator.ValidateLearnerChronology(learner);

        Assert.Contains(errors, e => e.RuleCode == "DATE_001_COMPLETION_BEFORE_COMMENCEMENT");
    }

    [Fact]
    public void LearnerChronology_DetectsCommencementOutsideProviderAccreditation()
    {
        var provider = new TrainingProvider
        {
            Id = 1,
            ProviderCode = "PRV-MER-001",
            AccreditationStartDate = new DateTime(2024, 1, 1),
            AccreditationEndDate = new DateTime(2026, 12, 31)
        };

        var learner = new CompanyLearner
        {
            Id = 105,
            PersonId = 5,
            CommencementDate = new DateTime(2023, 6, 1) // Before accreditation!
        };

        var errors = CrossEntityDateValidator.ValidateLearnerChronology(learner, provider: provider);

        Assert.Contains(errors, e => e.RuleCode == "DATE_X03_COMMENCEMENT_BEFORE_PROVIDER_ACCREDITATION");
    }

    [Fact]
    public void LearnerChronology_DetectsCommencementAfterProviderAccreditationExpired()
    {
        var provider = new TrainingProvider
        {
            Id = 2,
            ProviderCode = "PRV-MER-002",
            AccreditationStartDate = new DateTime(2020, 1, 1),
            AccreditationEndDate = new DateTime(2023, 12, 31) // Expired
        };

        var learner = new CompanyLearner
        {
            Id = 106,
            PersonId = 6,
            CommencementDate = new DateTime(2024, 6, 1) // After expiry!
        };

        var errors = CrossEntityDateValidator.ValidateLearnerChronology(learner, provider: provider);

        Assert.Contains(errors, e => e.RuleCode == "DATE_X04_COMMENCEMENT_AFTER_PROVIDER_ACCREDITATION_EXPIRY");
    }

    [Fact]
    public void LearnerChronology_DetectsCommencementOutsideGrantMoaWindow()
    {
        var grantMoa = new GrantMoa
        {
            Id = 1,
            MoaNumber = "MOA-2025-001",
            ContractStartDate = new DateTime(2025, 4, 1),
            ContractEndDate = new DateTime(2026, 3, 31)
        };

        var learner = new CompanyLearner
        {
            Id = 107,
            PersonId = 7,
            CommencementDate = new DateTime(2026, 6, 1) // After MOA project end!
        };

        var errors = CrossEntityDateValidator.ValidateLearnerChronology(learner, grantMoa: grantMoa);

        Assert.Contains(errors, e => e.RuleCode == "DATE_X05_COMMENCEMENT_OUTSIDE_GRANT_PROJECT_WINDOW");
    }

    [Fact]
    public void AssessmentChronology_DetectsModerationBeforeAssessment()
    {
        var assessment = new LearnerAssessment
        {
            Id = 1,
            UnitStandardId = 119472,
            AssessmentDate = new DateTime(2026, 5, 20),
            ModerationDate = new DateTime(2026, 5, 10) // Before assessment!
        };

        var errors = CrossEntityDateValidator.ValidateAssessmentChronology(assessment);

        Assert.Contains(errors, e => e.RuleCode == "DATE_010_MODERATION_BEFORE_ASSESSMENT");
    }

    [Fact]
    public void AssessmentChronology_DetectsAssessmentBeforeLearnerCommencement()
    {
        var learner = new CompanyLearner
        {
            Id = 108,
            CommencementDate = new DateTime(2026, 4, 1)
        };

        var assessment = new LearnerAssessment
        {
            Id = 2,
            UnitStandardId = 119472,
            AssessmentDate = new DateTime(2026, 2, 1) // Before commencement!
        };

        var errors = CrossEntityDateValidator.ValidateAssessmentChronology(assessment, learner: learner);

        Assert.Contains(errors, e => e.RuleCode == "DATE_X10_ASSESSMENT_BEFORE_LEARNER_COMMENCEMENT");
    }

    [Fact]
    public void TradeTestChronology_DetectsTestDateBeforeApplication()
    {
        var tradeTest = new LearnerTradeTest
        {
            Id = 1,
            TradeTestDate = new DateTime(2026, 5, 1) // Before application!
        };

        var applicationDate = new DateTime(2026, 6, 1);

        var errors = CrossEntityDateValidator.ValidateTradeTestChronology(tradeTest, applicationDate);

        Assert.Contains(errors, e => e.RuleCode == "DATE_020_TRADE_TEST_BEFORE_APPLICATION");
    }

    [Fact]
    public void TradeTestChronology_DetectsCandidateUnderAge16()
    {
        var person = new Person
        {
            Id = 9,
            DateOfBirth = new DateTime(2012, 1, 1) // 14 years old in 2026
        };

        var tradeTest = new LearnerTradeTest
        {
            Id = 2,
            TradeTestDate = new DateTime(2026, 2, 1)
        };

        var applicationDate = new DateTime(2026, 1, 1);

        var errors = CrossEntityDateValidator.ValidateTradeTestChronology(tradeTest, applicationDate, person: person);

        Assert.Contains(errors, e => e.RuleCode == "DATE_X21_TRADE_TEST_UNDER_AGE_16");
    }

    [Fact]
    public void GrantFundingWindow_DetectsSubmissionOutsideWindow()
    {
        var window = new GrantFundingWindow
        {
            Id = 1,
            OpeningDate = new DateTime(2026, 1, 15),
            ClosingDate = new DateTime(2026, 2, 28)
        };

        var errors = CrossEntityDateValidator.ValidateGrantFundingWindow(window, new DateTime(2026, 3, 5));

        Assert.Contains(errors, e => e.RuleCode == "DATE_X30_SUBMISSION_OUTSIDE_FUNDING_WINDOW");
    }

    [Fact]
    public void BankingDetails_WarnsWhenConfirmationLetterOlderThan90Days()
    {
        var oldDate = DateTime.UtcNow.AddDays(-120); // 120 days old

        var errors = CrossEntityDateValidator.ValidateBankingDetails(oldDate);

        Assert.Contains(errors, e => e.RuleCode == "DATE_041_BANK_CONFIRMATION_STALE_OVER_90_DAYS");
    }
}
