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

        // 3. Statutory Stream Specific Validations
        if (provider.AccreditationStream == AccreditationStreamType.QctoSkillsDevelopmentProvider)
        {
            if (string.IsNullOrWhiteSpace(provider.QctoAccreditationNumber))
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = provider.Id,
                    EntityName = nameof(TrainingProvider),
                    RecordDescriptor = desc,
                    FieldName = nameof(provider.QctoAccreditationNumber),
                    FieldValue = null,
                    RuleCode = "QCTO_SDP_ACCREDITATION_NUMBER_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "QCTO Accreditation Number is mandatory for QCTO Skills Development Providers.",
                    Remediation = "Capture the official QCTO accreditation letter/certificate number (e.g. QCTOSDP0120230501)."
                });
            }

            if (provider.QctoAccreditationStartDate.HasValue && provider.QctoAccreditationEndDate.HasValue &&
                provider.QctoAccreditationStartDate.Value > provider.QctoAccreditationEndDate.Value)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = provider.Id,
                    EntityName = nameof(TrainingProvider),
                    RecordDescriptor = desc,
                    FieldName = nameof(provider.QctoAccreditationEndDate),
                    FieldValue = provider.QctoAccreditationEndDate.Value.ToString("yyyy-MM-dd"),
                    RuleCode = "QCTO_SDP_ACCREDITATION_END_BEFORE_START",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "QCTO Accreditation End Date cannot precede Start Date.",
                    Remediation = "Correct the QCTO accreditation expiration date."
                });
            }
        }
        else if (provider.AccreditationStream == AccreditationStreamType.QctoTradeTestCentre)
        {
            if (string.IsNullOrWhiteSpace(provider.NambRegistrationNumber))
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = provider.Id,
                    EntityName = nameof(TrainingProvider),
                    RecordDescriptor = desc,
                    FieldName = nameof(provider.NambRegistrationNumber),
                    FieldValue = null,
                    RuleCode = "QCTO_TTC_NAMB_REGISTRATION_NUMBER_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "NAMB Trade Test Centre Registration Number is mandatory for QCTO Trade Test Centres.",
                    Remediation = "Capture the official NAMB TTC registration credential (e.g. AC-2023-014)."
                });
            }

            if (provider.NambRegistrationStartDate.HasValue && provider.NambRegistrationEndDate.HasValue &&
                provider.NambRegistrationStartDate.Value > provider.NambRegistrationEndDate.Value)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = provider.Id,
                    EntityName = nameof(TrainingProvider),
                    RecordDescriptor = desc,
                    FieldName = nameof(provider.NambRegistrationEndDate),
                    FieldValue = provider.NambRegistrationEndDate.Value.ToString("yyyy-MM-dd"),
                    RuleCode = "QCTO_TTC_REGISTRATION_END_BEFORE_START",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "NAMB TTC Registration End Date cannot precede Start Date.",
                    Remediation = "Correct the NAMB trade test centre registration expiration date."
                });
            }
        }
        else if (provider.AccreditationStream == AccreditationStreamType.ProgrammeApproval)
        {
            if (string.IsNullOrWhiteSpace(provider.PrimaryEtqaName))
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = provider.Id,
                    EntityName = nameof(TrainingProvider),
                    RecordDescriptor = desc,
                    FieldName = nameof(provider.PrimaryEtqaName),
                    FieldValue = null,
                    RuleCode = "PROGRAMME_APPROVAL_PRIMARY_ETQA_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Primary ETQA / SETA Name is mandatory for Programme Approval applications.",
                    Remediation = "Specify the primary accrediting SETA or ETQA authority (e.g. CHIETA, Services SETA)."
                });
            }

            if (string.IsNullOrWhiteSpace(provider.PrimaryAccreditationNumber))
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = provider.Id,
                    EntityName = nameof(TrainingProvider),
                    RecordDescriptor = desc,
                    FieldName = nameof(provider.PrimaryAccreditationNumber),
                    FieldValue = null,
                    RuleCode = "PROGRAMME_APPROVAL_PRIMARY_NUMBER_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Primary SETA Accreditation Number is mandatory for Programme Approval applications.",
                    Remediation = "Capture the accreditation certificate number issued by the primary SETA."
                });
            }
        }

        return errors;
    }
}
