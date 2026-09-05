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
        else
        {
            ValidateNameHygiene(errors, fileId, person.Id, desc, nameof(person.FirstName), person.FirstName, "SETMIS_400_03_FIRST_NAME_PLACEHOLDER");
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
        else
        {
            ValidateNameHygiene(errors, fileId, person.Id, desc, nameof(person.LastName), person.LastName, "SETMIS_400_02_LAST_NAME_PLACEHOLDER");
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

        // 7. Address Anti-Placeholder & Hygiene (Table 18 Attributes 16, 17, 23, 24, 25)
        ValidateAddressHygiene(errors, fileId, person.Id, desc, nameof(person.PhysicalAddress), person.PhysicalAddress);
        ValidateAddressHygiene(errors, fileId, person.Id, desc, nameof(person.PostalAddress), person.PostalAddress);

        // 8. Contact Numbers Validation (Table 18 Attributes 27, 28, 30)
        var contact = person.Contact;
        var cellNumber = person.CellNumber ?? contact?.CellNumber;
        if (!string.IsNullOrWhiteSpace(cellNumber))
        {
            var cellClean = cellNumber.Trim().Replace(" ", "").Replace("-", "");
            if (!System.Text.RegularExpressions.Regex.IsMatch(cellClean, @"^0\d{9}$"))
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = person.Id,
                    EntityName = nameof(Person),
                    RecordDescriptor = desc,
                    FieldName = nameof(person.CellNumber),
                    FieldValue = cellNumber,
                    RuleCode = "SETMIS_400_28_CELL_PHONE_INVALID",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Cell phone number must be 10 digits starting with 0 (e.g. 0821234567).",
                    Remediation = "Enter a valid 10-digit South African mobile phone number."
                });
            }
        }

        var nokContact = contact?.NextOfKinContactNumber ?? person.NextOfKinContactNumber;
        if (!string.IsNullOrWhiteSpace(nokContact))
        {
            var nokClean = nokContact.Trim().Replace(" ", "").Replace("-", "");
            if (!System.Text.RegularExpressions.Regex.IsMatch(nokClean, @"^0\d{9}$"))
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = person.Id,
                    EntityName = nameof(Person),
                    RecordDescriptor = desc,
                    FieldName = "NextOfKinContactNumber",
                    FieldValue = nokContact,
                    RuleCode = "SETMIS_400_30_NOK_PHONE_INVALID",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Next of kin phone number must be 10 digits starting with 0.",
                    Remediation = "Enter a valid 10-digit phone number for the next of kin contact."
                });
            }
        }

        // 9. Email Address Validation (Table 18 Attribute 31)
        if (!string.IsNullOrWhiteSpace(person.Email))
        {
            if (person.Email.Length > 50 || !person.Email.Contains('@') || !person.Email.Contains('.'))
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = person.Id,
                    EntityName = nameof(Person),
                    RecordDescriptor = desc,
                    FieldName = nameof(person.Email),
                    FieldValue = person.Email,
                    RuleCode = "SETMIS_400_31_EMAIL_INVALID",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Primary email address must be a valid email format and cannot exceed 50 characters.",
                    Remediation = "Provide a valid email address (e.g. learner@domain.co.za)."
                });
            }
        }

        return errors;
    }

    private static void ValidateNameHygiene(List<StatutoryValidationError> errors, string fileId, int recordId, string desc, string fieldName, string? value, string ruleCode)
    {
        if (string.IsNullOrWhiteSpace(value)) return;
        var upper = value.Trim().ToUpperInvariant();
        string[] prohibited = { "%UNKNOWN%", "UNKNOWN", "%AS ABOVE%", "AS ABOVE", "%SOOS BO%", "SOOS BO", "%DELETE%", "DELETE", "%TEST%", "TEST", "N/A", "NONE", "GEEN" };
        if (prohibited.Any(p => upper == p || upper.Contains(p)))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = recordId,
                EntityName = nameof(Person),
                RecordDescriptor = desc,
                FieldName = fieldName,
                FieldValue = value,
                RuleCode = ruleCode,
                Severity = StatutoryValidationSeverity.Fatal,
                Message = $"Name field '{fieldName}' contains prohibited placeholder text ('{value}').",
                Remediation = "Capture applicant's real statutory name per National ID or Passport."
            });
        }
    }

    private static void ValidateAddressHygiene(List<StatutoryValidationError> errors, string fileId, int recordId, string desc, string fieldName, string? value)
    {
        if (string.IsNullOrWhiteSpace(value)) return;
        var upper = value.Trim().ToUpperInvariant();

        string[] prohibited = { "%UNKNOWN%", "UNKNOWN", "%AS ABOVE%", "AS ABOVE", "%SOOS BO%", "SOOS BO", "%DELETE%", "N/A", "NA", "NONE", "GEEN", "TEST", "%ONTBREEK%", "NIL" };
        if (prohibited.Any(p => upper == p || upper.Contains(p)))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = recordId,
                EntityName = nameof(Person),
                RecordDescriptor = desc,
                FieldName = fieldName,
                FieldValue = value,
                RuleCode = "SETMIS_400_16_ADDRESS_PLACEHOLDER_FORBIDDEN",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = $"Address field '{fieldName}' contains prohibited placeholder text ('{value}').",
                Remediation = "Provide a genuine physical residential or postal address."
            });
        }

        if (System.Text.RegularExpressions.Regex.IsMatch(upper, @"^\d+$"))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = recordId,
                EntityName = nameof(Person),
                RecordDescriptor = desc,
                FieldName = fieldName,
                FieldValue = value,
                RuleCode = "SETMIS_400_16_ADDRESS_NUMBERS_ONLY",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = $"Address field '{fieldName}' cannot consist of only numeric digits.",
                Remediation = "Include street name and suburb."
            });
        }

        if (System.Text.RegularExpressions.Regex.IsMatch(upper, @"\b\d{4,}\b") && !System.Text.RegularExpressions.Regex.IsMatch(upper, @"\b\d{4}\b\s*$"))
        {
            // Embedded 4+ consecutive digits in street name
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = recordId,
                EntityName = nameof(Person),
                RecordDescriptor = desc,
                FieldName = fieldName,
                FieldValue = value,
                RuleCode = "SETMIS_400_16_ADDRESS_CONSECUTIVE_DIGITS",
                Severity = StatutoryValidationSeverity.Warning,
                Message = $"Address field '{fieldName}' contains 4 or more consecutive embedded digits.",
                Remediation = "Verify that house numbers and postal codes are properly formatted."
            });
        }
    }

    private static bool IsWgDifficulty(string? ratingCode)
    {
        if (string.IsNullOrWhiteSpace(ratingCode)) return false;
        // Rating "02" (Some difficulty), "03" (A lot of difficulty), "04" (Cannot do at all)
        return ratingCode is "02" or "03" or "04" or "2" or "3" or "4";
    }
}
