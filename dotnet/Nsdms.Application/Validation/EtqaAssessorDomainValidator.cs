using Nsdms.Domain.Entities;

namespace Nsdms.Application.Validation;

public static class EtqaAssessorDomainValidator
{
    public static List<StatutoryValidationError> Validate(EtqaAssessor assessor, string fileId = "SETMIS_401")
    {
        var errors = new List<StatutoryValidationError>();
        string desc = $"Assessor Reg: {assessor.RegistrationNumber} (Person ID: {assessor.PersonId})";

        // 1. Registration Number
        if (string.IsNullOrWhiteSpace(assessor.RegistrationNumber))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = assessor.Id,
                EntityName = nameof(EtqaAssessor),
                RecordDescriptor = desc,
                FieldName = nameof(assessor.RegistrationNumber),
                FieldValue = assessor.RegistrationNumber,
                RuleCode = "SETMIS_401_03_REG_NUMBER_REQUIRED",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = "Assessor/Moderator ETQA Registration Number cannot be empty.",
                Remediation = "Capture the statutory registration number issued by merSETA ETQA."
            });
        }

        // 2. Person Linkage
        if (assessor.PersonId <= 0)
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = assessor.Id,
                EntityName = nameof(EtqaAssessor),
                RecordDescriptor = desc,
                FieldName = nameof(assessor.PersonId),
                FieldValue = assessor.PersonId.ToString(),
                RuleCode = "SETMIS_401_01_PERSON_LINKAGE_REQUIRED",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = "Assessor record must link to a valid demographic Person record.",
                Remediation = "Associate this assessor registration with a Person."
            });
        }

        // 3. Designation Type Check
        if (string.IsNullOrWhiteSpace(assessor.DesignationTypeId))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = assessor.Id,
                EntityName = nameof(EtqaAssessor),
                RecordDescriptor = desc,
                FieldName = nameof(assessor.DesignationTypeId),
                FieldValue = assessor.DesignationTypeId,
                RuleCode = "SETMIS_401_02_DESIGNATION_TYPE_REQUIRED",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = "Designation Type is mandatory (01 Assessor, 02 Moderator).",
                Remediation = "Select the practitioner designation classification."
            });
        }

        return errors;
    }
}
