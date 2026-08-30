using Nsdms.Application.Common.Utilities;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Validation;

public static class OrganisationDomainValidator
{
    public static List<StatutoryValidationError> Validate(Organisation org, string fileId = "SETMIS_200")
    {
        var errors = new List<StatutoryValidationError>();
        string desc = $"{org.LegalName ?? "Unnamed Organisation"} (SDL: {org.SdlNumber})";

        // 1. Mandatory SDL Number Check
        if (string.IsNullOrWhiteSpace(org.SdlNumber))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = org.Id,
                EntityName = nameof(Organisation),
                RecordDescriptor = desc,
                FieldName = nameof(org.SdlNumber),
                FieldValue = org.SdlNumber,
                RuleCode = "SETMIS_200_01_SDL_REQUIRED",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = "Employer Skills Development Levy (SDL) Number cannot be empty.",
                Remediation = "Capture a valid 10-character SARS SDL Number (e.g. L123456789)."
            });
        }
        else
        {
            var sdlResult = SdlNumberValidator.Parse(org.SdlNumber);
            if (!sdlResult.IsValid)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = org.Id,
                    EntityName = nameof(Organisation),
                    RecordDescriptor = desc,
                    FieldName = nameof(org.SdlNumber),
                    FieldValue = org.SdlNumber,
                    RuleCode = "SETMIS_200_01_SDL_CHECKSUM_INVALID",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = $"SDL Number failed validation: {sdlResult.ErrorMessage}",
                    Remediation = "Correct the SDL number prefix and check digits."
                });
            }
        }

        // 2. Legal Name
        if (string.IsNullOrWhiteSpace(org.LegalName))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = org.Id,
                EntityName = nameof(Organisation),
                RecordDescriptor = desc,
                FieldName = nameof(org.LegalName),
                FieldValue = org.LegalName,
                RuleCode = "SETMIS_200_03_LEGAL_NAME_REQUIRED",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = "Organisation registered legal name is mandatory.",
                Remediation = "Capture the CIPC registered legal entity name."
            });
        }

        // 3. SIC Code
        if (string.IsNullOrWhiteSpace(org.SicCode))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = org.Id,
                EntityName = nameof(Organisation),
                RecordDescriptor = desc,
                FieldName = nameof(org.SicCode),
                FieldValue = org.SicCode,
                RuleCode = "SETMIS_200_02_SIC_CODE_REQUIRED",
                Severity = StatutoryValidationSeverity.Warning,
                Message = "Standard Industrial Classification (SIC) code is recommended for statutory reporting.",
                Remediation = "Select the appropriate 5-digit SIC code."
            });
        }

        return errors;
    }
}
