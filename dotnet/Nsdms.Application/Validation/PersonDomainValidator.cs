using Nsdms.Application.Common.Utilities;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Validation;

public static class PersonDomainValidator
{
    public static List<StatutoryValidationError> Validate(Person person, string fileId = "SETMIS_400")
    {
        var errors = new List<StatutoryValidationError>();
        string desc = $"{person.FirstName} {person.LastName} (ID: {person.Id})";

        // 1. Mandatory Identity Check
        bool isSa = person.CitizenStatusCode == "SA" || person.IsSouthAfricanCitizen == true;
        if (isSa)
        {
            if (string.IsNullOrWhiteSpace(person.RsaIdNumber))
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = person.Id,
                    EntityName = nameof(Person),
                    RecordDescriptor = desc,
                    FieldName = nameof(person.RsaIdNumber),
                    FieldValue = person.RsaIdNumber,
                    RuleCode = "SETMIS_400_01_RSA_ID_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "South African citizen records must provide a valid 13-digit RSA National Identity Number.",
                    Remediation = "Capture the 13-digit RSA ID Number."
                });
            }
            else
            {
                var idResult = RsaIdValidator.Parse(person.RsaIdNumber);
                if (!idResult.IsValid)
                {
                    errors.Add(new StatutoryValidationError
                    {
                        FileIdentifier = fileId,
                        RecordId = person.Id,
                        EntityName = nameof(Person),
                        RecordDescriptor = desc,
                        FieldName = nameof(person.RsaIdNumber),
                        FieldValue = person.RsaIdNumber,
                        RuleCode = "SETMIS_400_01_RSA_ID_CHECKSUM_INVALID",
                        Severity = StatutoryValidationSeverity.Fatal,
                        Message = $"RSA ID Number failed validation: {idResult.ErrorMessage}",
                        Remediation = "Correct the ID number to ensure valid Luhn checksum and date format."
                    });
                }
                else
                {
                    // Check DOB match
                    if (person.DateOfBirth.HasValue && idResult.DateOfBirth.HasValue)
                    {
                        if (person.DateOfBirth.Value.Date != idResult.DateOfBirth.Value.Date)
                        {
                            errors.Add(new StatutoryValidationError
                            {
                                FileIdentifier = fileId,
                                RecordId = person.Id,
                                EntityName = nameof(Person),
                                RecordDescriptor = desc,
                                FieldName = nameof(person.DateOfBirth),
                                FieldValue = person.DateOfBirth.Value.ToString("yyyy-MM-dd"),
                                RuleCode = "SETMIS_400_15_DOB_MISMATCH_WITH_ID",
                                Severity = StatutoryValidationSeverity.Fatal,
                                Message = $"Date of Birth ({person.DateOfBirth.Value:yyyy-MM-dd}) does not match the birth date encoded in the RSA ID ({idResult.DateOfBirth.Value:yyyy-MM-dd}).",
                                Remediation = "Synchronize Date of Birth with the RSA ID Number."
                            });
                        }
                    }
                }
            }
        }
        else
        {
            // Non-SA citizen must have passport / alternate ID
            if (string.IsNullOrWhiteSpace(person.PassportNumber) && string.IsNullOrWhiteSpace(person.RsaIdNumber))
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = person.Id,
                    EntityName = nameof(Person),
                    RecordDescriptor = desc,
                    FieldName = nameof(person.PassportNumber),
                    FieldValue = null,
                    RuleCode = "SETMIS_400_02_PASSPORT_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Foreign nationals must provide a Passport or Alternative Identification Number.",
                    Remediation = "Capture the foreign passport number and select the Alternate ID Type."
                });
            }
        }

        // 2. Names validation
        if (string.IsNullOrWhiteSpace(person.FirstName))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = person.Id,
                EntityName = nameof(Person),
                RecordDescriptor = desc,
                FieldName = nameof(person.FirstName),
                FieldValue = person.FirstName,
                RuleCode = "SETMIS_400_12_FIRST_NAME_REQUIRED",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = "Person first name cannot be empty or start with whitespace.",
                Remediation = "Capture legal given first name."
            });
        }

        if (string.IsNullOrWhiteSpace(person.LastName))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = person.Id,
                EntityName = nameof(Person),
                RecordDescriptor = desc,
                FieldName = nameof(person.LastName),
                FieldValue = person.LastName,
                RuleCode = "SETMIS_400_11_LAST_NAME_REQUIRED",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = "Person last name/surname cannot be empty.",
                Remediation = "Capture legal family surname."
            });
        }

        // 3. Demographic Lookups
        if (string.IsNullOrWhiteSpace(person.EquityCode))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = person.Id,
                EntityName = nameof(Person),
                RecordDescriptor = desc,
                FieldName = nameof(person.EquityCode),
                FieldValue = person.EquityCode,
                RuleCode = "SETMIS_400_04_EQUITY_REQUIRED",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = "Employment Equity demographic classification is mandatory for statutory reporting.",
                Remediation = "Select a statutory equity code (e.g. BA, BC, BI, WH)."
            });
        }

        if (string.IsNullOrWhiteSpace(person.GenderCode) && string.IsNullOrWhiteSpace(person.Gender))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = person.Id,
                EntityName = nameof(Person),
                RecordDescriptor = desc,
                FieldName = nameof(person.GenderCode),
                FieldValue = person.GenderCode,
                RuleCode = "SETMIS_400_07_GENDER_REQUIRED",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = "Gender classification is mandatory for statutory reporting.",
                Remediation = "Select statutory gender code (F or M)."
            });
        }

        // 4. Washington Group vs Disability Consistency
        bool anyWgDifficulty = IsWgDifficulty(person.SeeingRatingId) ||
                               IsWgDifficulty(person.HearingRatingId) ||
                               IsWgDifficulty(person.WalkingRatingId) ||
                               IsWgDifficulty(person.RememberingRatingId) ||
                               IsWgDifficulty(person.CommunicatingRatingId) ||
                               IsWgDifficulty(person.SelfCareRatingId);

        if (anyWgDifficulty && (person.DisabilityCode == "00" || string.IsNullOrWhiteSpace(person.DisabilityCode)))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = person.Id,
                EntityName = nameof(Person),
                RecordDescriptor = desc,
                FieldName = nameof(person.DisabilityCode),
                FieldValue = person.DisabilityCode,
                RuleCode = "SETMIS_400_10_DISABILITY_WG_MISMATCH",
                Severity = StatutoryValidationSeverity.Warning,
                Message = "Washington Group difficulty ratings indicate a functional impairment, but Disability Code is set to 'None' (00).",
                Remediation = "Update Disability Code to reflect the specific impairment type."
            });
        }

        // 5. POPIA Consent Validation
        if (person.PopiActConsentDate.HasValue && person.PopiActConsentDate.Value > DateTime.UtcNow)
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = person.Id,
                EntityName = nameof(Person),
                RecordDescriptor = desc,
                FieldName = nameof(person.PopiActConsentDate),
                FieldValue = person.PopiActConsentDate.Value.ToString("yyyy-MM-dd"),
                RuleCode = "SETMIS_400_27_POPI_DATE_IN_FUTURE",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = "POPIA Consent Date cannot be in the future.",
                Remediation = "Set the consent recording date to the actual agreement date."
            });
        }

        // 6. Address & Postal Code
        if (!string.IsNullOrWhiteSpace(person.PostalAddressPostalCode) && person.PostalAddressPostalCode.Length != 4)
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = person.Id,
                EntityName = nameof(Person),
                RecordDescriptor = desc,
                FieldName = nameof(person.PostalAddressPostalCode),
                FieldValue = person.PostalAddressPostalCode,
                RuleCode = "SETMIS_400_23_POSTAL_CODE_INVALID",
                Severity = StatutoryValidationSeverity.Warning,
                Message = "Postal address postal code should be exactly 4 digits for South African addresses.",
                Remediation = "Correct postal code to 4-digit format."
            });
        }

        return errors;
    }

    private static bool IsWgDifficulty(string? ratingCode)
    {
        if (string.IsNullOrWhiteSpace(ratingCode)) return false;
        // Rating "02" (Some difficulty), "03" (A lot of difficulty), "04" (Cannot do at all)
        return ratingCode is "02" or "03" or "04" or "2" or "3" or "4";
    }
}
