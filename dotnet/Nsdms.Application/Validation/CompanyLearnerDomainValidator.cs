using Nsdms.Domain.Entities;

namespace Nsdms.Application.Validation;

public static class CompanyLearnerDomainValidator
{
    public static List<StatutoryValidationError> Validate(CompanyLearner learner, string fileId = "SETMIS_500")
    {
        var errors = new List<StatutoryValidationError>();
        string desc = $"Contract: {learner.LearnerContractNumber ?? "Draft"} (Learner ID: {learner.PersonId})";

        // 1. Part_Of Articulation & Hierarchy Dependencies
        if (learner.PartOfId == "02" || learner.PartOfId == "2")
        {
            if (!learner.SaqaQualificationId.HasValue || learner.SaqaQualificationId.Value <= 0)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.SaqaQualificationId),
                    FieldValue = learner.SaqaQualificationId?.ToString(),
                    RuleCode = "SETMIS_500_03_PARTOF_QUAL_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "When Part_Of is set to 'Part of Qualification' (02), a valid SAQA Qualification ID is mandatory.",
                    Remediation = "Link a registered SAQA Qualification to this learner agreement."
                });
            }
        }
        else if (learner.PartOfId == "03" || learner.PartOfId == "3")
        {
            if (string.IsNullOrWhiteSpace(learner.LearnershipId))
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.LearnershipId),
                    FieldValue = learner.LearnershipId,
                    RuleCode = "SETMIS_500_02_PARTOF_LEARNERSHIP_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "When Part_Of is set to 'Part of Learnership' (03), a registered SAQA Learnership ID is mandatory.",
                    Remediation = "Capture the SAQA Learnership Registration Code (e.g. 18Q180026241203)."
                });
            }
        }

        // 2. Date Chronology & Lifecycle Milestones
        if (learner.CommencementDate.HasValue && learner.CommencementDate.Value > DateTime.UtcNow.AddMonths(3))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = learner.Id,
                EntityName = nameof(CompanyLearner),
                RecordDescriptor = desc,
                FieldName = nameof(learner.CommencementDate),
                FieldValue = learner.CommencementDate.Value.ToString("yyyy-MM-dd"),
                RuleCode = "SETMIS_500_08_COMMENCEMENT_DATE_FUTURE",
                Severity = StatutoryValidationSeverity.Warning,
                Message = "Learner Agreement commencement date is set more than 3 months in the future.",
                Remediation = "Verify that the enrolment start date matches the signed tri-partite agreement."
            });
        }

        if (learner.CommencementDate.HasValue && learner.CompletionDate.HasValue && learner.CommencementDate.Value > learner.CompletionDate.Value)
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = learner.Id,
                EntityName = nameof(CompanyLearner),
                RecordDescriptor = desc,
                FieldName = nameof(learner.CompletionDate),
                FieldValue = learner.CompletionDate.Value.ToString("yyyy-MM-dd"),
                RuleCode = "SETMIS_500_09_COMPLETION_BEFORE_COMMENCEMENT",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = $"Contract Completion Date ({learner.CompletionDate.Value:yyyy-MM-dd}) cannot precede Commencement Date ({learner.CommencementDate.Value:yyyy-MM-dd}).",
                Remediation = "Correct the expected/actual completion date."
            });
        }

        // 3. Minimum Age at Enrolment Invariant (Age >= 15 years per Skills Development Act)
        if (learner.CommencementDate.HasValue && learner.Person?.DateOfBirth != null)
        {
            var minEnrolmentDate = learner.Person.DateOfBirth.Value.AddYears(15);
            if (learner.CommencementDate.Value < minEnrolmentDate)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.CommencementDate),
                    FieldValue = learner.CommencementDate.Value.ToString("yyyy-MM-dd"),
                    RuleCode = "SETMIS_500_08_MINIMUM_AGE_VIOLATION",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = $"Learner was under 15 years of age on Commencement Date ({learner.CommencementDate.Value:yyyy-MM-dd}). Minimum legal working/training age in SA is 15 years.",
                    Remediation = "Verify learner birth date and agreement commencement date."
                });
            }
        }

        // 4. Financial & Cumulative Spend Rules
        if (learner.CumulativeSpend < 0)
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = learner.Id,
                EntityName = nameof(CompanyLearner),
                RecordDescriptor = desc,
                FieldName = nameof(learner.CumulativeSpend),
                FieldValue = learner.CumulativeSpend.ToString("F2"),
                RuleCode = "SETMIS_500_17_CUMULATIVE_SPEND_NEGATIVE",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = "Cumulative spend on learning intervention cannot be negative.",
                Remediation = "Correct the cumulative expenditure figure."
            });
        }

        // 5. Enrolment Status Consistency
        if (learner.EnrolmentStatusId == "02" || learner.EnrolmentStatusId == "03" || learner.EnrolmentStatusId == "2" || learner.EnrolmentStatusId == "3")
        {
            // Achieved / Certificated
            if (!learner.CompletionDate.HasValue)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.CompletionDate),
                    FieldValue = null,
                    RuleCode = "SETMIS_500_09_ACHIEVED_WITHOUT_COMPLETION_DATE",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Learner status is set to Achieved/Certificated but Completion Date is empty.",
                    Remediation = "Capture the date of achievement/completion."
                });
            }
        }

        return errors;
    }
}
