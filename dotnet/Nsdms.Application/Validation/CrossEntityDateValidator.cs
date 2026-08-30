using Nsdms.Domain.Entities;

namespace Nsdms.Application.Validation;

/// <summary>
/// Domain-centric cross-entity chronological and statutory date validator.
/// Enforces business invariants across relational boundaries (Defense-in-Depth Tier 2).
/// </summary>
public static class CrossEntityDateValidator
{
    public static List<StatutoryValidationError> ValidateLearnerChronology(
        CompanyLearner learner,
        Person? person = null,
        TrainingProvider? provider = null,
        GrantMoa? grantMoa = null,
        WorkplaceApproval? workplaceApproval = null,
        string fileId = "SETMIS_500")
    {
        var errors = new List<StatutoryValidationError>();
        string desc = $"Learner Contract: {learner.LearnerContractNumber ?? "Draft"} (Person ID: {learner.PersonId})";
        var effectivePerson = person ?? learner.Person;

        // 1. Single-entity chronological bounds
        if (learner.CommencementDate.HasValue && learner.CompletionDate.HasValue)
        {
            if (learner.CompletionDate.Value < learner.CommencementDate.Value)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.CompletionDate),
                    FieldValue = learner.CompletionDate.Value.ToString("yyyy-MM-dd"),
                    RuleCode = "DATE_001_COMPLETION_BEFORE_COMMENCEMENT",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = $"Contract Completion Date ({learner.CompletionDate.Value:yyyy-MM-dd}) cannot precede Commencement Date ({learner.CommencementDate.Value:yyyy-MM-dd}).",
                    Remediation = "Correct the learner completion date to be on or after the commencement date."
                });
            }
        }

        // 2. Cross-Entity: Person DateOfBirth vs Learner CommencementDate
        if (learner.CommencementDate.HasValue && effectivePerson?.DateOfBirth.HasValue == true)
        {
            var dob = effectivePerson.DateOfBirth.Value.Date;
            var commencement = learner.CommencementDate.Value.Date;

            if (commencement < dob)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.CommencementDate),
                    FieldValue = commencement.ToString("yyyy-MM-dd"),
                    RuleCode = "DATE_X01_COMMENCEMENT_BEFORE_BIRTH",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = $"Learner cannot commence training on {commencement:yyyy-MM-dd} prior to their birth date ({dob:yyyy-MM-dd}).",
                    Remediation = "Verify learner identity and agreement commencement date."
                });
            }
            else
            {
                var minAgeDate = dob.AddYears(15);
                if (commencement < minAgeDate)
                {
                    errors.Add(new StatutoryValidationError
                    {
                        FileIdentifier = fileId,
                        RecordId = learner.Id,
                        EntityName = nameof(CompanyLearner),
                        RecordDescriptor = desc,
                        FieldName = nameof(learner.CommencementDate),
                        FieldValue = commencement.ToString("yyyy-MM-dd"),
                        RuleCode = "DATE_X02_MINIMUM_AGE_15_VIOLATION",
                        Severity = StatutoryValidationSeverity.Fatal,
                        Message = $"Learner must be at least 15 years old on Commencement Date ({commencement:yyyy-MM-dd}). Minimum legal age threshold is {minAgeDate:yyyy-MM-dd}.",
                        Remediation = "Ensure the learner is of statutory working age (15+) at time of contract start."
                    });
                }
            }
        }

        // 3. Cross-Entity: Training Provider Accreditation Lifespan
        if (learner.CommencementDate.HasValue && provider != null)
        {
            var commencement = learner.CommencementDate.Value.Date;
            if (provider.AccreditationStartDate.HasValue && commencement < provider.AccreditationStartDate.Value.Date)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.CommencementDate),
                    FieldValue = commencement.ToString("yyyy-MM-dd"),
                    RuleCode = "DATE_X03_COMMENCEMENT_BEFORE_PROVIDER_ACCREDITATION",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = $"Training Provider '{provider.ProviderCode}' was not accredited on commencement date {commencement:yyyy-MM-dd} (Accreditation started {provider.AccreditationStartDate.Value:yyyy-MM-dd}).",
                    Remediation = "Align learner commencement date with provider's active accreditation window."
                });
            }

            if (provider.AccreditationEndDate.HasValue && commencement > provider.AccreditationEndDate.Value.Date)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.CommencementDate),
                    FieldValue = commencement.ToString("yyyy-MM-dd"),
                    RuleCode = "DATE_X04_COMMENCEMENT_AFTER_PROVIDER_ACCREDITATION_EXPIRY",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = $"Training Provider '{provider.ProviderCode}' accreditation expired on {provider.AccreditationEndDate.Value:yyyy-MM-dd} prior to learner commencement ({commencement:yyyy-MM-dd}).",
                    Remediation = "Renew provider accreditation or transfer learner to an active provider."
                });
            }
        }

        // 4. Cross-Entity: Discretionary Grant Project Window
        if (learner.CommencementDate.HasValue && grantMoa != null)
        {
            var commencement = learner.CommencementDate.Value.Date;
            if (commencement < grantMoa.ContractStartDate.Date || commencement > grantMoa.ContractEndDate.Date)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.CommencementDate),
                    FieldValue = commencement.ToString("yyyy-MM-dd"),
                    RuleCode = "DATE_X05_COMMENCEMENT_OUTSIDE_GRANT_PROJECT_WINDOW",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = $"Learner commencement ({commencement:yyyy-MM-dd}) falls outside the approved DG MOA contract lifespan ({grantMoa.ContractStartDate:yyyy-MM-dd} to {grantMoa.ContractEndDate:yyyy-MM-dd}).",
                    Remediation = "Verify that learner is allocated within the active grant contract window."
                });
            }
        }

        // 5. Cross-Entity: Workplace Approval Expiry
        if (learner.CommencementDate.HasValue && workplaceApproval != null && workplaceApproval.ExpiryDate.HasValue)
        {
            var commencement = learner.CommencementDate.Value.Date;
            if (commencement > workplaceApproval.ExpiryDate.Value.Date)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.CommencementDate),
                    FieldValue = commencement.ToString("yyyy-MM-dd"),
                    RuleCode = "DATE_X06_COMMENCEMENT_AFTER_WORKPLACE_APPROVAL_EXPIRY",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = $"Workplace Approval for employer expired on {workplaceApproval.ExpiryDate.Value:yyyy-MM-dd} prior to learner commencement ({commencement:yyyy-MM-dd}).",
                    Remediation = "Conduct workplace re-approval visit before registering new learners."
                });
            }
        }

        // 6. Prior Qualification Date Bounds
        if (learner.PriorQualificationAchievementDate.HasValue)
        {
            var priorDate = learner.PriorQualificationAchievementDate.Value.Date;
            if (priorDate > DateTime.UtcNow.Date)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.PriorQualificationAchievementDate),
                    FieldValue = priorDate.ToString("yyyy-MM-dd"),
                    RuleCode = "DATE_003_PRIOR_QUAL_ACHIEVED_IN_FUTURE",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Prior qualification achievement date cannot be in the future.",
                    Remediation = "Capture actual historical qualification award date."
                });
            }

            if (effectivePerson?.DateOfBirth.HasValue == true)
            {
                var minPriorQualDate = effectivePerson.DateOfBirth.Value.Date.AddYears(14);
                if (priorDate < minPriorQualDate)
                {
                    errors.Add(new StatutoryValidationError
                    {
                        FileIdentifier = fileId,
                        RecordId = learner.Id,
                        EntityName = nameof(CompanyLearner),
                        RecordDescriptor = desc,
                        FieldName = nameof(learner.PriorQualificationAchievementDate),
                        FieldValue = priorDate.ToString("yyyy-MM-dd"),
                        RuleCode = "DATE_X07_PRIOR_QUAL_BEFORE_REASONABLE_AGE",
                        Severity = StatutoryValidationSeverity.Fatal,
                        Message = $"Prior qualification achievement date ({priorDate:yyyy-MM-dd}) indicates age under 14 at qualification award.",
                        Remediation = "Verify prior qualification certificate date."
                    });
                }
            }
        }

        return errors;
    }

    public static List<StatutoryValidationError> ValidateAssessmentChronology(
        LearnerAssessment assessment,
        CompanyLearner? learner = null,
        AssessorModeratorScope? assessorScope = null,
        string fileId = "SETMIS_503")
    {
        var errors = new List<StatutoryValidationError>();
        string desc = $"Assessment #{assessment.Id} (Unit Standard ID: {assessment.UnitStandardId})";

        // 1. Single Entity: Assessment vs Moderation
        if (assessment.ModerationDate.HasValue)
        {
            if (assessment.ModerationDate.Value.Date < assessment.AssessmentDate.Date)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = assessment.Id,
                    EntityName = nameof(LearnerAssessment),
                    RecordDescriptor = desc,
                    FieldName = nameof(assessment.ModerationDate),
                    FieldValue = assessment.ModerationDate.Value.ToString("yyyy-MM-dd"),
                    RuleCode = "DATE_010_MODERATION_BEFORE_ASSESSMENT",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = $"Moderation Date ({assessment.ModerationDate.Value:yyyy-MM-dd}) cannot precede Assessment Date ({assessment.AssessmentDate:yyyy-MM-dd}).",
                    Remediation = "Correct moderation date to be on or after the assessment date."
                });
            }
        }

        if (assessment.AssessmentDate.Date > DateTime.UtcNow.Date)
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = assessment.Id,
                EntityName = nameof(LearnerAssessment),
                RecordDescriptor = desc,
                FieldName = nameof(assessment.AssessmentDate),
                FieldValue = assessment.AssessmentDate.ToString("yyyy-MM-dd"),
                RuleCode = "DATE_011_ASSESSMENT_IN_FUTURE",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = "Assessment Date cannot be in the future.",
                Remediation = "Record the actual date the assessment took place."
            });
        }

        // 2. Cross-Entity: Assessment Date vs Learner Commencement
        if (learner?.CommencementDate.HasValue == true)
        {
            if (assessment.AssessmentDate.Date < learner.CommencementDate.Value.Date)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = assessment.Id,
                    EntityName = nameof(LearnerAssessment),
                    RecordDescriptor = desc,
                    FieldName = nameof(assessment.AssessmentDate),
                    FieldValue = assessment.AssessmentDate.ToString("yyyy-MM-dd"),
                    RuleCode = "DATE_X10_ASSESSMENT_BEFORE_LEARNER_COMMENCEMENT",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = $"Assessment Date ({assessment.AssessmentDate:yyyy-MM-dd}) cannot precede Learner Commencement Date ({learner.CommencementDate.Value:yyyy-MM-dd}).",
                    Remediation = "Ensure all assessments occur during or after the active learning agreement commencement."
                });
            }
        }

        // 3. Cross-Entity: Assessor Active Scope Period
        if (assessorScope != null)
        {
            var assessmentDate = assessment.AssessmentDate.Date;
            if (assessorScope.ExpiryDate.HasValue && assessmentDate > assessorScope.ExpiryDate.Value.Date)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = assessment.Id,
                    EntityName = nameof(LearnerAssessment),
                    RecordDescriptor = desc,
                    FieldName = nameof(assessment.AssessmentDate),
                    FieldValue = assessmentDate.ToString("yyyy-MM-dd"),
                    RuleCode = "DATE_X12_ASSESSMENT_AFTER_ASSESSOR_EXPIRY",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = $"Assessor registration expired on {assessorScope.ExpiryDate.Value:yyyy-MM-dd} prior to assessment on {assessmentDate:yyyy-MM-dd}.",
                    Remediation = "Renew assessor scope registration before conducting summative assessments."
                });
            }
        }

        return errors;
    }

    public static List<StatutoryValidationError> ValidateTradeTestChronology(
        LearnerTradeTest tradeTest,
        DateTime applicationDate,
        CompanyLearner? learner = null,
        Person? person = null,
        string fileId = "SETMIS_504")
    {
        var errors = new List<StatutoryValidationError>();
        string desc = $"Trade Test #{tradeTest.Id} (Learner ID: {tradeTest.CompanyLearnerId})";

        if (tradeTest.TradeTestDate.Date < applicationDate.Date)
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = tradeTest.Id,
                EntityName = nameof(LearnerTradeTest),
                RecordDescriptor = desc,
                FieldName = nameof(tradeTest.TradeTestDate),
                FieldValue = tradeTest.TradeTestDate.ToString("yyyy-MM-dd"),
                RuleCode = "DATE_020_TRADE_TEST_BEFORE_APPLICATION",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = $"Trade Test Date ({tradeTest.TradeTestDate:yyyy-MM-dd}) cannot precede Application Date ({applicationDate:yyyy-MM-dd}).",
                Remediation = "Ensure the trade test is scheduled on or after application submission."
            });
        }

        if (learner?.CommencementDate.HasValue == true)
        {
            if (tradeTest.TradeTestDate.Date < learner.CommencementDate.Value.Date)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = tradeTest.Id,
                    EntityName = nameof(LearnerTradeTest),
                    RecordDescriptor = desc,
                    FieldName = nameof(tradeTest.TradeTestDate),
                    FieldValue = tradeTest.TradeTestDate.ToString("yyyy-MM-dd"),
                    RuleCode = "DATE_X20_TRADE_TEST_BEFORE_COMMENCEMENT",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = $"Trade Test cannot take place ({tradeTest.TradeTestDate:yyyy-MM-dd}) prior to apprenticeship/learnership commencement ({learner.CommencementDate.Value:yyyy-MM-dd}).",
                    Remediation = "Verify learner contract start date and trade test date."
                });
            }
        }

        if (person?.DateOfBirth.HasValue == true)
        {
            var minTradeTestAgeDate = person.DateOfBirth.Value.Date.AddYears(16);
            if (tradeTest.TradeTestDate.Date < minTradeTestAgeDate)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = tradeTest.Id,
                    EntityName = nameof(LearnerTradeTest),
                    RecordDescriptor = desc,
                    FieldName = nameof(tradeTest.TradeTestDate),
                    FieldValue = tradeTest.TradeTestDate.ToString("yyyy-MM-dd"),
                    RuleCode = "DATE_X21_TRADE_TEST_UNDER_AGE_16",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = $"Candidate must be at least 16 years of age to attempt an Artisan Trade Test (Reached on {minTradeTestAgeDate:yyyy-MM-dd}).",
                    Remediation = "Verify candidate birth date."
                });
            }
        }

        return errors;
    }

    public static List<StatutoryValidationError> ValidateGrantFundingWindow(
        GrantFundingWindow window,
        DateTime? applicationSubmissionDate = null)
    {
        var errors = new List<StatutoryValidationError>();
        string desc = $"Grant Window #{window.Id} ({window.FinYear})";

        if (window.ClosingDate < window.OpeningDate)
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = "GRANT_CONFIG",
                RecordId = window.Id,
                EntityName = nameof(GrantFundingWindow),
                RecordDescriptor = desc,
                FieldName = nameof(window.ClosingDate),
                FieldValue = window.ClosingDate.ToString("yyyy-MM-dd"),
                RuleCode = "DATE_030_GRANT_WINDOW_CLOSING_BEFORE_OPENING",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = $"Funding Window Closing Date ({window.ClosingDate:yyyy-MM-dd}) cannot precede Opening Date ({window.OpeningDate:yyyy-MM-dd}).",
                Remediation = "Adjust the funding window dates."
            });
        }

        if (applicationSubmissionDate.HasValue)
        {
            var subDate = applicationSubmissionDate.Value.Date;
            if (subDate < window.OpeningDate.Date || subDate > window.ClosingDate.Date)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = "GRANT_APP",
                    RecordId = window.Id,
                    EntityName = nameof(GrantApplication),
                    RecordDescriptor = "Grant Application Submission",
                    FieldName = "SubmissionDate",
                    FieldValue = subDate.ToString("yyyy-MM-dd"),
                    RuleCode = "DATE_X30_SUBMISSION_OUTSIDE_FUNDING_WINDOW",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = $"Grant application was submitted on {subDate:yyyy-MM-dd}, which is outside the active funding window ({window.OpeningDate:yyyy-MM-dd} to {window.ClosingDate:yyyy-MM-dd}).",
                    Remediation = "Applications can only be submitted while the window is open."
                });
            }
        }

        return errors;
    }

    public static List<StatutoryValidationError> ValidateBankingDetails(
        DateTime? bankConfirmationDate,
        DateTime? verificationDate = null)
    {
        var errors = new List<StatutoryValidationError>();

        if (bankConfirmationDate.HasValue)
        {
            var confDate = bankConfirmationDate.Value.Date;
            if (confDate > DateTime.UtcNow.Date)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = "FIN_BANKING",
                    RecordId = 0,
                    EntityName = "BankingDetails",
                    RecordDescriptor = "Banking Details Update",
                    FieldName = nameof(bankConfirmationDate),
                    FieldValue = confDate.ToString("yyyy-MM-dd"),
                    RuleCode = "DATE_040_BANK_CONFIRMATION_IN_FUTURE",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Bank confirmation letter date cannot be in the future.",
                    Remediation = "Capture the actual date stamped on the bank confirmation letter."
                });
            }

            // 90-day SARS/FICA freshness invariant
            var maxAgeDate = DateTime.UtcNow.Date.AddDays(-90);
            if (confDate < maxAgeDate)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = "FIN_BANKING",
                    RecordId = 0,
                    EntityName = "BankingDetails",
                    RecordDescriptor = "Banking Details Update",
                    FieldName = nameof(bankConfirmationDate),
                    FieldValue = confDate.ToString("yyyy-MM-dd"),
                    RuleCode = "DATE_041_BANK_CONFIRMATION_STALE_OVER_90_DAYS",
                    Severity = StatutoryValidationSeverity.Warning,
                    Message = $"Bank confirmation letter dated {confDate:yyyy-MM-dd} is older than 90 days. Statutory governance requires a recent confirmation letter.",
                    Remediation = "Request an updated bank confirmation letter from the employer/provider."
                });
            }
        }

        return errors;
    }
}
