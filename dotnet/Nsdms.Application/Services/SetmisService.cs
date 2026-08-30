using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;
using System.Text;

namespace Nsdms.Application.Services;

public class SetmisService : ISetmisService
{
    private readonly INsdmsDbContextFactory _contextFactory;

    public SetmisService(INsdmsDbContextFactory contextFactory)
    {
        _contextFactory = contextFactory;
    }

    public async Task<string> GenerateSetmisFileContentAsync(string fileCode)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var sb = new StringBuilder();

        switch (fileCode.ToUpperInvariant())
        {
            case "500":
                // File 500: Training Providers & Accreditation
                var providers = await context.TrainingProviders
                    .Include(p => p.Organisation)
                    .Include(p => p.PrimaryContactPerson)
                    .OrderBy(p => p.Id)
                    .ToListAsync();

                sb.AppendLine("PROVIDER_ID|LEGAL_NAME|TRADING_NAME|ACCREDITATION_NO|PROVIDER_TYPE|PROVINCE|CONTACT_EMAIL|ACCREDITATION_START|ACCREDITATION_END|STATUS");
                foreach (var p in providers)
                {
                    sb.AppendLine($"PRV-{p.Id:D4}|{p.Organisation?.CompanyName ?? "N/A"}|{p.Organisation?.TradingName ?? ""}|{p.AccreditationNumber}|{p.ProviderTypeCode}|{p.Organisation?.ProvinceCode ?? "GP"}|{p.PrimaryContactPerson?.Email ?? ""}|{p.AccreditationStartDate:yyyy-MM-dd}|{p.AccreditationEndDate:yyyy-MM-dd}|{p.ProviderStatusCode}");
                }
                break;

            case "501":
                // File 501: ETQA Assessors & Moderators
                var assessors = await context.EtqaAssessors
                    .Include(a => a.Person)
                    .Include(a => a.Scopes)
                    .OrderBy(a => a.Id)
                    .ToListAsync();

                sb.AppendLine("REGISTRATION_NO|ID_NUMBER|FIRST_NAME|LAST_NAME|PRACTITIONER_ROLE|START_DATE|END_DATE|SCOPE_COUNT|STATUS");
                foreach (var a in assessors)
                {
                    sb.AppendLine($"{a.RegistrationNumber}|{a.Person?.RsaIdNumber ?? "N/A"}|{a.Person?.FirstName ?? ""}|{a.Person?.LastName ?? ""}|{a.EtqaRole}|{a.StartDate:yyyy-MM-dd}|{a.EndDate:yyyy-MM-dd}|{a.Scopes.Count}|{a.StatusCode ?? "Active"}");
                }
                break;

            case "502":
                // File 502: Workplace Approvals
                var approvals = await context.WorkplaceApprovals
                    .Include(w => w.Organisation)
                    .Include(w => w.Mentors)
                    .OrderBy(w => w.Id)
                    .ToListAsync();

                sb.AppendLine("APPROVAL_NUMBER|EMPLOYER_SDL|EMPLOYER_NAME|QUALIFICATION_TITLE|SAQA_ID|INSPECTION_DATE|EXPIRY_DATE|MENTOR_COUNT|STATUS");
                foreach (var w in approvals)
                {
                    sb.AppendLine($"{w.ApprovalNumber}|{w.Organisation?.SdlNumber ?? "N/A"}|{w.Organisation?.CompanyName ?? ""}|{w.QualificationTitle}|{w.SaqaQualificationId?.ToString() ?? "N/A"}|{w.InspectionDate:yyyy-MM-dd}|{w.ExpiryDate:yyyy-MM-dd}|{w.Mentors.Count}|{w.ApprovalStatusCode}");
                }
                break;

            case "503":
                // File 503: Non-NQF & Skills Programmes Lookups
                var programmes = await context.LearningProgrammeTypes
                    .Where(l => l.Active)
                    .OrderBy(l => l.Code)
                    .ToListAsync();

                sb.AppendLine("PROGRAMME_CODE|PROGRAMME_NAME|DESCRIPTION|ACTIVE");
                foreach (var prg in programmes)
                {
                    sb.AppendLine($"{prg.Code}|{prg.Name}|{prg.Description}|{prg.Active}");
                }
                break;

            case "504":
                // File 504: Learner Enrolments
                var learners = await context.CompanyLearners
                    .Include(l => l.Person)
                    .Include(l => l.Organisation)
                    .Include(l => l.TrainingProvider)
                    .OrderBy(l => l.Id)
                    .ToListAsync();

                sb.AppendLine("LEARNER_ID|ID_NUMBER|FIRST_NAME|LAST_NAME|DOB|GENDER|EQUITY|EMPLOYER_SDL|PROVIDER_ID|QUALIFICATION|ENROLMENT_DATE|EXPECTED_END|STATUS");
                foreach (var l in learners)
                {
                    sb.AppendLine($"{l.Id:D6}|{l.Person?.RsaIdNumber ?? "N/A"}|{l.Person?.FirstName ?? ""}|{l.Person?.LastName ?? ""}|{l.Person?.DateOfBirth:yyyy-MM-dd}|{l.Person?.GenderCode ?? "U"}|{l.Person?.EquityCode ?? "1"}|{l.Organisation?.SdlNumber ?? "N/A"}|PRV-{(l.TrainingProviderId?.ToString("D4") ?? "0000")}|{l.QualificationTitle}|{l.RegistrationDate:yyyy-MM-dd}|{l.ExpectedCompletionDate:yyyy-MM-dd}|{l.StatusCode}");
                }
                break;

            case "505":
                // File 505: Learner Assessments & Trade Tests
                var tradeTests = await context.LearnerTradeTests
                    .Include(t => t.CompanyLearner)
                        .ThenInclude(cl => cl!.Person)
                    .OrderBy(t => t.Id)
                    .ToListAsync();

                sb.AppendLine("TRADE_TEST_ID|LEARNER_ID|ID_NUMBER|LEARNER_NAME|TRADE_TEST_DATE|SERIAL_CERTIFICATE_NO|RESULT_STATUS|ISSUED_DATE");
                foreach (var t in tradeTests)
                {
                    sb.AppendLine($"{t.Id:D6}|{t.CompanyLearnerId:D6}|{t.CompanyLearner?.Person?.RsaIdNumber ?? "N/A"}|{t.CompanyLearner?.Person?.FirstName} {t.CompanyLearner?.Person?.LastName}|{t.TradeTestDate:yyyy-MM-dd}|{t.SerialCertificateNumber ?? "PENDING"}|{t.ResultStatusCode}|{t.CertificateIssueDate?.ToString("yyyy-MM-dd") ?? "N/A"}");
                }
                break;

            default:
                sb.AppendLine($"# MERSETA SETMIS CONSOLIDATED BUNDLE - {DateTime.UtcNow:yyyy-MM-dd HH:mm:ss}");
                sb.AppendLine($"# FILE_CODE: {fileCode} - STANDARD DHET PIPE DELIMITED EXTRACT");
                break;
        }

        return sb.ToString();
    }

    public async Task<List<SetmisValidationMessage>> ValidateSetmisDataAsync()
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var messages = new List<SetmisValidationMessage>();

        // 1. Validate Learners (File 504)
        var learners = await context.CompanyLearners
            .Include(l => l.Person)
            .Include(l => l.Organisation)
            .ToListAsync();

        foreach (var l in learners)
        {
            if (l.Person == null)
            {
                messages.Add(new SetmisValidationMessage
                {
                    FileCode = "504",
                    RecordId = l.Id,
                    EntityIdentifier = $"Learner #{l.Id}",
                    Severity = "Error",
                    FieldName = "PersonId",
                    RuleDescription = "Learner record is missing demographic Person linkage.",
                    RemediationGuidance = "Link learner to an active record in Person registry."
                });
            }
            else if (string.IsNullOrWhiteSpace(l.Person.RsaIdNumber) || l.Person.RsaIdNumber.Length != 13)
            {
                messages.Add(new SetmisValidationMessage
                {
                    FileCode = "504",
                    RecordId = l.Id,
                    EntityIdentifier = $"{l.Person.FirstName} {l.Person.LastName}",
                    Severity = "Error",
                    FieldName = "RsaIdNumber",
                    RuleDescription = "RSA ID Number must be exactly 13 digits for DHET compliance.",
                    RemediationGuidance = "Update Person record with valid 13-digit RSA ID."
                });
            }

            if (l.ExpectedCompletionDate < l.RegistrationDate)
            {
                messages.Add(new SetmisValidationMessage
                {
                    FileCode = "504",
                    RecordId = l.Id,
                    EntityIdentifier = $"Learner #{l.Id}",
                    Severity = "Error",
                    FieldName = "ExpectedCompletionDate",
                    RuleDescription = "Expected Completion Date cannot precede Registration Date.",
                    RemediationGuidance = "Adjust programme expected completion date."
                });
            }

            if (l.Organisation == null)
            {
                messages.Add(new SetmisValidationMessage
                {
                    FileCode = "504",
                    RecordId = l.Id,
                    EntityIdentifier = $"Learner #{l.Id}",
                    Severity = "Warning",
                    FieldName = "OrganisationId",
                    RuleDescription = "Learner has no host employer linked.",
                    RemediationGuidance = "Assign host employer SDL number."
                });
            }
        }

        // 2. Validate Providers (File 500)
        var providers = await context.TrainingProviders
            .Include(p => p.Organisation)
            .ToListAsync();

        foreach (var p in providers)
        {
            if (string.IsNullOrWhiteSpace(p.AccreditationNumber))
            {
                messages.Add(new SetmisValidationMessage
                {
                    FileCode = "500",
                    RecordId = p.Id,
                    EntityIdentifier = $"Provider #{p.Id}",
                    Severity = "Error",
                    FieldName = "AccreditationNumber",
                    RuleDescription = "Accreditation number is mandatory for SETMIS File 500.",
                    RemediationGuidance = "Assign official SAQA / QCTO accreditation reference."
                });
            }

            if (p.AccreditationEndDate.HasValue && p.AccreditationStartDate.HasValue && p.AccreditationEndDate < p.AccreditationStartDate)
            {
                messages.Add(new SetmisValidationMessage
                {
                    FileCode = "500",
                    RecordId = p.Id,
                    EntityIdentifier = $"Provider #{p.Id}",
                    Severity = "Error",
                    FieldName = "AccreditationEndDate",
                    RuleDescription = "Accreditation End Date cannot precede Start Date.",
                    RemediationGuidance = "Correct accreditation validity date range."
                });
            }
        }

        // 3. Validate Assessors (File 501)
        var assessors = await context.EtqaAssessors
            .Include(a => a.Person)
            .ToListAsync();

        foreach (var a in assessors)
        {
            if (a.EndDate < a.StartDate)
            {
                messages.Add(new SetmisValidationMessage
                {
                    FileCode = "501",
                    RecordId = a.Id,
                    EntityIdentifier = a.RegistrationNumber,
                    Severity = "Error",
                    FieldName = "EndDate",
                    RuleDescription = "Assessor registration expiry date cannot precede start date.",
                    RemediationGuidance = "Update ETQA registration validity dates."
                });
            }
        }

        return messages;
    }

    public async Task<List<SetmisSubmissionBatch>> GetSubmissionBatchesAsync()
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        return await context.SetmisSubmissionBatches
            .OrderByDescending(b => b.GeneratedDate)
            .ToListAsync();
    }

    public async Task<SetmisSubmissionBatch> CreateSubmissionBatchAsync(string period, string fileCode, string userId)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var validationMessages = await ValidateSetmisDataAsync();
        var errorCount = validationMessages.Count(m => m.Severity == "Error");

        int totalCount = await context.CompanyLearners.CountAsync() +
                         await context.TrainingProviders.CountAsync() +
                         await context.EtqaAssessors.CountAsync() +
                         await context.WorkplaceApprovals.CountAsync();

        var batch = new SetmisSubmissionBatch
        {
            BatchNumber = $"SETMIS-{period.Replace(" ", "-").ToUpper()}-{DateTime.UtcNow:yyyyMMddHHmm}",
            FileCode = fileCode,
            SubmissionPeriod = period,
            TotalRecords = totalCount,
            ValidRecords = Math.Max(0, totalCount - errorCount),
            ErrorRecords = errorCount,
            Status = errorCount == 0 ? "Validated" : "Generated",
            GeneratedFileUri = $"vault://setmis/{period.ToLower()}/SETMIS_{period.ToUpper()}_EXTRACT.zip",
            GeneratedByUserId = userId,
            GeneratedDate = DateTime.UtcNow,
            ValidationSummaryJson = $"{{\"total\":{totalCount},\"errors\":{errorCount},\"warnings\":{validationMessages.Count(m => m.Severity == "Warning")}}}",
            CreatedBy = userId
        };

        context.SetmisSubmissionBatches.Add(batch);

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "SetmisSubmissionBatch",
            RecordId = batch.Id,
            ActionName = "GENERATE_SETMIS_BATCH",
            Actor = userId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"batch\":\"{batch.BatchNumber}\",\"period\":\"{period}\",\"errors\":{errorCount}}}"
        });

        await context.SaveChangesAsync();
        return batch;
    }

    public async Task<bool> UpdateBatchStatusAsync(int batchId, string status, string? ackRef, string userId)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var batch = await context.SetmisSubmissionBatches.FirstOrDefaultAsync(b => b.Id == batchId);
        if (batch == null) return false;

        batch.Status = status;
        batch.DhetAcknowledgmentRef = ackRef ?? batch.DhetAcknowledgmentRef;
        batch.ModifiedAt = DateTime.UtcNow;
        batch.ModifiedBy = userId;

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "SetmisSubmissionBatch",
            RecordId = batchId,
            ActionName = "UPDATE_SETMIS_BATCH_STATUS",
            Actor = userId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"status\":\"{status}\",\"ackRef\":\"{ackRef}\"}}"
        });

        await context.SaveChangesAsync();
        return true;
    }
}
