using Nsdms.Domain.Entities;

namespace Nsdms.Application.Validation;

public static class CompanyLearnerDomainValidator
{
    public static List<StatutoryValidationError> Validate(CompanyLearner learner, string fileId = "SETMIS_500", int maxWorkingDays = 30)
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

        // 6. Max Working Days Statutory Submission Rule (Default 30 Working Days, Table 17 Item 2 & 4)
        if (learner.LearnerSignatureDate.HasValue && learner.SubmissionDate.HasValue)
        {
            var workingDays = CalculateWorkingDays(learner.LearnerSignatureDate.Value, learner.SubmissionDate.Value);
            if (workingDays > maxWorkingDays)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.SubmissionDate),
                    FieldValue = $"{workingDays} working days",
                    RuleCode = "SETMIS_500_30DAY_DEADLINE_EXCEEDED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = $"Learner agreement was submitted {workingDays} working days after learner signature date. Maximum statutory submission window is {maxWorkingDays} working days.",
                    Remediation = "The agreement has lapsed; request a fresh bilateral agreement execution or upload condonation approval."
                });
            }
        }

        // 7. Minor Learner (< 18 Years) Guardian Requirement (Table 17 Item 2)
        if (learner.Person?.DateOfBirth.HasValue == true)
        {
            var agreementDate = learner.LearnerSignatureDate ?? learner.CommencementDate ?? DateTime.UtcNow;
            var ageYears = (agreementDate - learner.Person.DateOfBirth.Value).TotalDays / 365.25;

            if (ageYears < 18.0)
            {
                var hasActiveGuardian = learner.Person.Guardians != null && learner.Person.Guardians.Any(g => g.IsActive && !string.IsNullOrWhiteSpace(g.GuardianFullName));
                if (!hasActiveGuardian)
                {
                    errors.Add(new StatutoryValidationError
                    {
                        FileIdentifier = fileId,
                        RecordId = learner.Id,
                        EntityName = nameof(CompanyLearner),
                        RecordDescriptor = desc,
                        FieldName = "PersonGuardians",
                        FieldValue = null,
                        RuleCode = "SETMIS_500_GUARDIAN_REQUIRED_FOR_MINOR",
                        Severity = StatutoryValidationSeverity.Fatal,
                        Message = $"Learner is an unmarried minor under 18 years of age ({ageYears:F1} years old). A legal parent or guardian must be a co-signatory to this agreement.",
                        Remediation = "Capture parent or guardian identification, contact details, and signature."
                    });
                }
            }
        }

        // 8. Programme-Specific Rules (Candidacy & AET)
        bool isCandidacy = learner.LearningProgrammeTypeCode == "06" || 
                           (learner.LearningProgrammeTypeCode != null && learner.LearningProgrammeTypeCode.Equals("Candidacy", StringComparison.OrdinalIgnoreCase));
        if (isCandidacy)
        {
            if (string.IsNullOrWhiteSpace(learner.ProfessionalRegistrationNumber))
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.ProfessionalRegistrationNumber),
                    FieldValue = null,
                    RuleCode = "SETMIS_500_CANDIDACY_REG_NUMBER_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Candidacy Programme requires a valid Professional Council candidate registration reference (e.g. ECSA Candidate Registration Number).",
                    Remediation = "Capture the professional council candidate registration number."
                });
            }
        }
        else
        {
            // For non-candidacy programmes, QualificationTitle is mandatory
            if (string.IsNullOrWhiteSpace(learner.QualificationTitle))
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.QualificationTitle),
                    FieldValue = null,
                    RuleCode = "SETMIS_500_01_QUAL_TITLE_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Qualification Title is required for learner registration agreements.",
                    Remediation = "Select or enter the qualification title."
                });
            }
        }

        return errors;
    }

    /// <summary>
    /// Calculates business working days between two dates, excluding weekends (Saturday/Sunday).
    /// </summary>
    public static int CalculateWorkingDays(DateTime startDate, DateTime endDate)
    {
        if (endDate < startDate) return 0;
        int businessDays = 0;
        var current = startDate.Date.AddDays(1); // Days elapsed after signature

        while (current <= endDate.Date)
        {
            if (current.DayOfWeek != DayOfWeek.Saturday && current.DayOfWeek != DayOfWeek.Sunday)
            {
                businessDays++;
            }
            current = current.AddDays(1);
        }

        return businessDays;
    }

    /// <summary>
    /// Validates Bursary Registration Application per Signed Use Case (MerSeta\NSDMS\LMS\LR\01):
    /// - Employed vs Unemployed conditional employer gating.
    /// - New vs Continuation dual-path logic & anti-tamper preconditions.
    /// - 7 Statutory Bursary funding types.
    /// - Educational Institution linkage (HEI / TVET / Private HEI).
    /// - Academic year & study year progression.
    /// </summary>
    public static List<StatutoryValidationError> ValidateBursaryApplication(
        CompanyLearner learner, 
        CompanyLearner? previousBursary = null, 
        string fileId = "BURSARY_APP",
        int maxWorkingDays = 30)
    {
        var errors = new List<StatutoryValidationError>();
        string desc = $"Bursary: {learner.LearnerContractNumber ?? "Draft"} (Learner ID: {learner.PersonId})";

        bool isEmployed = string.Equals(learner.EmploymentStatusCode, "Employed", StringComparison.OrdinalIgnoreCase) ||
                          learner.EconomicStatusId == "01";
        bool isUnemployed = string.Equals(learner.EmploymentStatusCode, "Unemployed", StringComparison.OrdinalIgnoreCase) ||
                            learner.EconomicStatusId == "02";

        // 1. Employment-Status Conditional Employer Gating (Section 5 Item 13)
        if (isEmployed)
        {
            if (!learner.OrganisationId.HasValue || learner.OrganisationId.Value <= 0)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.OrganisationId),
                    FieldValue = null,
                    RuleCode = "BURSARY_EMPLOYER_REQUIRED_FOR_EMPLOYED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "For employed bursary applicants, host employer identification (Levy number / Organisation) is mandatory.",
                    Remediation = "Select the registered employer from the organisation directory."
                });
            }
        }
        // For unemployed applicants, employer details are NOT required (bypassed per statutory rule).

        // 2. Educational Institution Validation (Section 4.2.3 Items 1-2, 15-16)
        bool hasInstitution = !string.IsNullOrWhiteSpace(learner.InstitutionName) ||
                              (learner.TrainingProviderId.HasValue && learner.TrainingProviderId.Value > 0) ||
                              (learner.OrganisationId.HasValue && learner.IsNonEmployerEntity);

        if (!hasInstitution)
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = learner.Id,
                EntityName = nameof(CompanyLearner),
                RecordDescriptor = desc,
                FieldName = nameof(learner.InstitutionName),
                FieldValue = null,
                RuleCode = "BURSARY_INSTITUTION_REQUIRED",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = "Registered Higher Education Institution (HEI) or TVET College is mandatory for bursary applications.",
                Remediation = "Select or capture the tertiary educational institution."
            });
        }

        // 3. Qualification Title
        if (string.IsNullOrWhiteSpace(learner.QualificationTitle))
        {
            errors.Add(new StatutoryValidationError
            {
                FileIdentifier = fileId,
                RecordId = learner.Id,
                EntityName = nameof(CompanyLearner),
                RecordDescriptor = desc,
                FieldName = nameof(learner.QualificationTitle),
                FieldValue = null,
                RuleCode = "BURSARY_QUALIFICATION_TITLE_REQUIRED",
                Severity = StatutoryValidationSeverity.Fatal,
                Message = "Tertiary qualification title is mandatory for bursary registration.",
                Remediation = "Enter the degree, diploma, or national certificate title."
            });
        }

        // 4. Dual-Path: Continuation Application Preconditions (Section 5 Business Rules)
        bool isContinuation = learner.IsContinuation ||
                              string.Equals(learner.BursaryApplicationTypeCode, "Continuation", StringComparison.OrdinalIgnoreCase);

        if (isContinuation)
        {
            if (!learner.PreviousCompanyLearnerId.HasValue || learner.PreviousCompanyLearnerId.Value <= 0)
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.PreviousCompanyLearnerId),
                    FieldValue = null,
                    RuleCode = "BURSARY_CONTINUATION_PREVIOUS_RECORD_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "A continuation bursary application requires linkage to a prior active bursary record on the learner profile.",
                    Remediation = "Select the active prior-year bursary agreement to continue."
                });
            }

            if (previousBursary != null)
            {
                // Must be an active bursary
                bool isPreviousActive = previousBursary.IsActive && 
                    (previousBursary.EnrolmentStatusCode == "Registered" || 
                     previousBursary.EnrolmentStatusCode == "InProgress" || 
                     previousBursary.EnrolmentStatusCode == "Completed");

                if (!isPreviousActive)
                {
                    errors.Add(new StatutoryValidationError
                    {
                        FileIdentifier = fileId,
                        RecordId = learner.Id,
                        EntityName = nameof(CompanyLearner),
                        RecordDescriptor = desc,
                        FieldName = nameof(learner.PreviousCompanyLearnerId),
                        FieldValue = previousBursary.Id.ToString(),
                        RuleCode = "BURSARY_CONTINUATION_PREVIOUS_MUST_BE_ACTIVE",
                        Severity = StatutoryValidationSeverity.Fatal,
                        Message = $"Prior bursary #{previousBursary.Id} is not in an active or eligible state ({previousBursary.EnrolmentStatusCode}).",
                        Remediation = "Only active, registered bursaries can be submitted for continuation."
                    });
                }

                // Anti-tamper: Cannot apply for New and Continuation on the same date
                if (learner.LearnerSignatureDate.HasValue && previousBursary.LearnerSignatureDate.HasValue &&
                    learner.LearnerSignatureDate.Value.Date == previousBursary.LearnerSignatureDate.Value.Date)
                {
                    errors.Add(new StatutoryValidationError
                    {
                        FileIdentifier = fileId,
                        RecordId = learner.Id,
                        EntityName = nameof(CompanyLearner),
                        RecordDescriptor = desc,
                        FieldName = nameof(learner.LearnerSignatureDate),
                        FieldValue = learner.LearnerSignatureDate.Value.ToString("yyyy-MM-dd"),
                        RuleCode = "BURSARY_CONTINUATION_SAME_DATE_PROHIBITED",
                        Severity = StatutoryValidationSeverity.Fatal,
                        Message = "Anti-tamper violation: New Bursary and Continuation cannot be applied for on the same execution date.",
                        Remediation = "Continuation applications must occur in a subsequent academic period."
                    });
                }

                // Academic progression: Year of Study must advance
                if (learner.YearOfStudy.HasValue && previousBursary.YearOfStudy.HasValue &&
                    learner.YearOfStudy.Value <= previousBursary.YearOfStudy.Value)
                {
                    errors.Add(new StatutoryValidationError
                    {
                        FileIdentifier = fileId,
                        RecordId = learner.Id,
                        EntityName = nameof(CompanyLearner),
                        RecordDescriptor = desc,
                        FieldName = nameof(learner.YearOfStudy),
                        FieldValue = learner.YearOfStudy.Value.ToString(),
                        RuleCode = "BURSARY_CONTINUATION_YEAR_MUST_ADVANCE",
                        Severity = StatutoryValidationSeverity.Fatal,
                        Message = $"Academic progression violation: Year of Study ({learner.YearOfStudy.Value}) must advance beyond prior year ({previousBursary.YearOfStudy.Value}).",
                        Remediation = "Set the study year to the progressive level (e.g. Year 2, Year 3)."
                    });
                }
            }
        }

        // 5. Statutory Bursary Funding Types (Section 4.2.3 Item 20 & 9.1.1 Item 19)
        if (!string.IsNullOrWhiteSpace(learner.BursaryFundingTypeCode))
        {
            var validFundingCodes = new HashSet<string>(StringComparer.OrdinalIgnoreCase)
            {
                "01", "merSETA funded", "merSETA",
                "02", "Non-merSETA funded", "Non-merSETA",
                "03", "Employer funded", "Employer",
                "04", "Learner funded", "Learner",
                "05", "Other SETA funded", "Other SETA",
                "06", "NSF funded", "NSF",
                "07", "Industry funded", "Industry"
            };

            if (!validFundingCodes.Contains(learner.BursaryFundingTypeCode))
            {
                errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = fileId,
                    RecordId = learner.Id,
                    EntityName = nameof(CompanyLearner),
                    RecordDescriptor = desc,
                    FieldName = nameof(learner.BursaryFundingTypeCode),
                    FieldValue = learner.BursaryFundingTypeCode,
                    RuleCode = "BURSARY_INVALID_FUNDING_TYPE",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = $"Invalid bursary funding type '{learner.BursaryFundingTypeCode}'. Must be one of the 7 statutory categories.",
                    Remediation = "Select a valid funding category from the 7 statutory options."
                });
            }
        }

        // 6. Inherit common statutory rules: Minor Guardian and max working days submission
        var commonErrors = Validate(learner, fileId, maxWorkingDays);
        foreach (var ce in commonErrors)
        {
            if (isUnemployed && ce.RuleCode.Contains("EMPLOYER", StringComparison.OrdinalIgnoreCase))
            {
                continue;
            }
            if (!errors.Any(e => e.RuleCode == ce.RuleCode))
            {
                errors.Add(ce);
            }
        }

        return errors;
    }

    /// <summary>
    /// Returns the statutory evidentiary document checklist based on Application Type and Employment Status (Section 9.1.2).
    /// </summary>
    public static List<BursaryDocumentRequirement> GetRequiredBursaryDocuments(string applicationType, string employmentStatus)
    {
        bool isContinuation = string.Equals(applicationType, "Continuation", StringComparison.OrdinalIgnoreCase);
        bool isEmployed = string.Equals(employmentStatus, "Employed", StringComparison.OrdinalIgnoreCase);

        if (isContinuation)
        {
            return new List<BursaryDocumentRequirement>
            {
                new("DOC-BUR-01", "Proof of re-registration / continuation letter from HEI/TVET", true),
                new("DOC-BUR-02", "Prior year Statement of Results / Academic transcript", true),
                new("DOC-BUR-03", "Certified ID / Passport copy of the learner", true)
            };
        }

        if (isEmployed)
        {
            return new List<BursaryDocumentRequirement>
            {
                new("DOC-BUR-10", "Proof of registration at HEI or TVET College", true),
                new("DOC-BUR-11", "Confirmation of employment status (Employer Letter)", true),
                new("DOC-BUR-12", "Duly completed Learner Registration Form", true),
                new("DOC-BUR-13", "Certified ID / Passport copy of the learner", true),
                new("DOC-BUR-14", "Copy of highest qualification / matric certificate", true)
            };
        }

        // Unemployed New Bursary: Employer Letter is NOT required
        return new List<BursaryDocumentRequirement>
        {
            new("DOC-BUR-20", "Proof of registration at HEI or TVET College", true),
            new("DOC-BUR-21", "Duly completed Learner Registration Form", true),
            new("DOC-BUR-22", "Certified ID / Passport copy of the learner", true),
            new("DOC-BUR-23", "Copy of highest qualification / matric certificate", true)
        };
    }
}

/// <summary>
/// Evidentiary document requirement descriptor for bursaries.
/// </summary>
public record BursaryDocumentRequirement(string DocumentCode, string DocumentName, bool IsMandatory);

/// <summary>
/// Canonical statutory alias for CompanyLearnerDomainValidator adhering to Clean Architecture terminology.
/// </summary>
public static class LearnerEnrolmentDomainValidator
{
    public static List<StatutoryValidationError> Validate(CompanyLearner enrolment, string fileId = "SETMIS_500") =>
        CompanyLearnerDomainValidator.Validate(enrolment, fileId);

    public static int CalculateWorkingDays(DateTime startDate, DateTime endDate) =>
        CompanyLearnerDomainValidator.CalculateWorkingDays(startDate, endDate);

    public static List<BursaryDocumentRequirement> GetRequiredBursaryDocuments(string applicationType, string employmentStatus) =>
        CompanyLearnerDomainValidator.GetRequiredBursaryDocuments(applicationType, employmentStatus);
}

