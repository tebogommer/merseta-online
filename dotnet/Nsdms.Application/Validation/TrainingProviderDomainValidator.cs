using Nsdms.Domain.Entities;

namespace Nsdms.Application.Validation;

public static class TrainingProviderDomainValidator
{
    public static List<StatutoryValidationError> Validate(TrainingProvider provider, string fileId = "SETMIS_100")
    {
        var errors = new List<StatutoryValidationError>();
        string desc = $"Provider: {provider.ProviderCode} (ID: {provider.Id})";

        // 1. Mandatory Identifier
        if (string.IsNullOrWhiteSpace(provider.ProviderCode))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = provider.Id,
                EntityName = nameof(TrainingProvider),
                RecordDescriptor = desc,
                FieldName = nameof(provider.ProviderCode),
                FieldValue = provider.ProviderCode,
                RuleCode = "SETMIS_100_01_PROVIDER_CODE_REQUIRED",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = "Provider Code cannot be empty.",
                Remediation = "Assign a unique SDP Provider Accreditation/Registration Code."
            });
        }
        else if (provider.ProviderCode.StartsWith(" "))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = provider.Id,
                EntityName = nameof(TrainingProvider),
                RecordDescriptor = desc,
                FieldName = nameof(provider.ProviderCode),
                FieldValue = provider.ProviderCode,
                RuleCode = "SETMIS_100_01_PROVIDER_CODE_LEADING_SPACE",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = "Provider Code may not contain leading whitespace.",
                Remediation = "Trim leading whitespace from Provider Code."
            });
        }

        // 2. Accreditation Dates vs Status Invariant (NLRD 'structure s' & SETMIS Rules)
        bool isAccredited = provider.ProviderStatusId == "01" || provider.ProviderStatusId == "1" ||
                            provider.AccreditationNumber != null;

        if (isAccredited)
        {
            if (!provider.AccreditationStartDate.HasValue || !provider.AccreditationEndDate.HasValue)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = provider.Id,
                    EntityName = nameof(TrainingProvider),
                    RecordDescriptor = desc,
                    FieldName = nameof(provider.AccreditationStartDate),
                    FieldValue = null,
                    RuleCode = "SETMIS_100_18_ACCREDITATION_DATES_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Accredited Skills Development Providers must have both Accreditation Start Date and End Date defined.",
                    Remediation = "Capture the ETQA committee accreditation validity period."
                });
            }
            else if (provider.AccreditationStartDate.Value > provider.AccreditationEndDate.Value)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = provider.Id,
                    EntityName = nameof(TrainingProvider),
                    RecordDescriptor = desc,
                    FieldName = nameof(provider.AccreditationEndDate),
                    FieldValue = provider.AccreditationEndDate.Value.ToString("yyyy-MM-dd"),
                    RuleCode = "SETMIS_100_19_ACCREDITATION_END_BEFORE_START",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Accreditation End Date cannot precede Accreditation Start Date.",
                    Remediation = "Correct the accreditation validity expiration date."
                });
            }
        }

        return errors;
    }
}
