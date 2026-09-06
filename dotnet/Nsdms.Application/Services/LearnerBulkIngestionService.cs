using System.Globalization;
using System.Security.Cryptography;
using System.Text;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Utilities;
using Nsdms.Application.Validation;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class LearnerBulkIngestionService : ILearnerBulkIngestionService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly ILearnerService _learnerService;
    private readonly ILearnerStpRiskEngine _stpEngine;

    public LearnerBulkIngestionService(
        INsdmsDbContextFactory contextFactory,
        IAuditService audit,
        ILearnerService learnerService,
        ILearnerStpRiskEngine stpEngine)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _learnerService = learnerService;
        _stpEngine = stpEngine;
    }

    public byte[] GenerateBulkTemplateCsv()
    {
        var sb = new StringBuilder();
        sb.AppendLine("FirstName,MiddleName,LastName,RsaIdNumber,PassportNumber,GenderCode,EquityCode,EmailAddress,PhoneNumber,LearningProgrammeTypeCode,SaqaQualificationId,QualificationTitle,TradeCode,LearnerSignatureDate,CommencementDate");
        sb.AppendLine("Kagiso,,Mokwena,0201015800085,,M,BA,kagiso.m@example.co.za,0821234567,01,65789,National Certificate: Autotronics,AUTO-01,2026-08-15,2026-09-01");
        sb.AppendLine("Sipho,John,Dlamini,9905045800081,,M,BA,sipho.d@example.co.za,0837654321,02,59201,FETC: Mechanical Engineering,MECH-02,2026-08-20,2026-09-01");
        return Encoding.UTF8.GetBytes(sb.ToString());
    }

    public async Task<LearnerBulkBatch> StageBatchAsync(
        int organisationId,
        string fileName,
        Stream fileStream,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var org = await db.Organisations.FindAsync(organisationId);
        if (org == null) throw new KeyNotFoundException($"Organisation with ID {organisationId} not found.");

        using var reader = new StreamReader(fileStream, Encoding.UTF8);
        var lines = new List<string>();
        string? currentLine;
        while ((currentLine = await reader.ReadLineAsync()) != null)
        {
            if (!string.IsNullOrWhiteSpace(currentLine)) lines.Add(currentLine);
        }

        if (lines.Count <= 1)
        {
            throw new InvalidOperationException("The uploaded file does not contain any candidate data rows.");
        }

        // Calculate SHA-256 Digital Security Seal
        var contentString = string.Join("\n", lines);
        var hashBytes = SHA256.HashData(Encoding.UTF8.GetBytes(contentString));
        var seal = Convert.ToHexString(hashBytes).ToLowerInvariant();

        var batchRef = $"BATCH-{DateTime.UtcNow.Year}-{DateTime.UtcNow.Ticks % 1000000:D6}";
        var batch = new LearnerBulkBatch
        {
            BatchReference = batchRef,
            OrganisationId = organisationId,
            OriginalFileName = fileName,
            TotalRows = lines.Count - 1,
            Status = "Staged",
            DigitalSecuritySeal = seal,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        // Skip CSV header line
        for (int i = 1; i < lines.Count; i++)
        {
            var cols = ParseCsvLine(lines[i]);
            if (cols.Length < 4) continue;

            var rsaId = cols.Length > 3 ? cols[3].Trim() : string.Empty;
            DateTime dob = DateTime.MinValue;
            string gender = cols.Length > 5 ? cols[5].Trim() : "M";

            if (RsaIdValidator.Validate(rsaId))
            {
                var parsed = RsaIdValidator.Parse(rsaId);
                if (parsed.DateOfBirth.HasValue) dob = parsed.DateOfBirth.Value;
                if (!string.IsNullOrEmpty(parsed.Gender)) gender = parsed.Gender == "Male" ? "M" : "F";
            }

            DateTime sigDate = DateTime.UtcNow;
            if (cols.Length > 13 && DateTime.TryParse(cols[13].Trim(), CultureInfo.InvariantCulture, DateTimeStyles.None, out var parsedSig))
            {
                sigDate = parsedSig;
            }

            DateTime? commDate = null;
            if (cols.Length > 14 && DateTime.TryParse(cols[14].Trim(), CultureInfo.InvariantCulture, DateTimeStyles.None, out var parsedComm))
            {
                commDate = parsedComm;
            }

            var row = new LearnerBulkBatchRow
            {
                RowIndex = i,
                FirstName = cols.Length > 0 ? cols[0].Trim() : string.Empty,
                MiddleName = cols.Length > 1 && !string.IsNullOrWhiteSpace(cols[1]) ? cols[1].Trim() : null,
                LastName = cols.Length > 2 ? cols[2].Trim() : string.Empty,
                RsaIdNumber = rsaId,
                PassportNumber = cols.Length > 4 && !string.IsNullOrWhiteSpace(cols[4]) ? cols[4].Trim() : null,
                DateOfBirth = dob,
                GenderCode = gender,
                EquityCode = cols.Length > 6 ? cols[6].Trim() : "BA",
                EmailAddress = cols.Length > 7 && !string.IsNullOrWhiteSpace(cols[7]) ? cols[7].Trim() : null,
                PhoneNumber = cols.Length > 8 && !string.IsNullOrWhiteSpace(cols[8]) ? cols[8].Trim() : null,
                LearningProgrammeTypeCode = cols.Length > 9 && !string.IsNullOrWhiteSpace(cols[9]) ? cols[9].Trim() : "01",
                SaqaQualificationId = cols.Length > 10 && !string.IsNullOrWhiteSpace(cols[10]) ? cols[10].Trim() : null,
                QualificationTitle = cols.Length > 11 && !string.IsNullOrWhiteSpace(cols[11]) ? cols[11].Trim() : null,
                TradeCode = cols.Length > 12 && !string.IsNullOrWhiteSpace(cols[12]) ? cols[12].Trim() : null,
                LearnerSignatureDate = sigDate,
                CommencementDate = commDate,
                Status = "Pending",
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            };

            // Pre-flight linting
            ValidateRow(row);
            batch.Rows.Add(row);
        }

        db.LearnerBulkBatches.Add(batch);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "LearnerBulkBatch", batch.Id, "StageBatch", currentUsername, null, batch);
        await db.SaveChangesAsync();

        return batch;
    }

    public async Task<BulkBatchImportResult> ProcessBatchAsync(int batchId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var batch = await db.LearnerBulkBatches
            .Include(b => b.Rows)
            .Include(b => b.Organisation)
            .FirstOrDefaultAsync(b => b.Id == batchId);

        if (batch == null) throw new KeyNotFoundException($"Batch with ID {batchId} not found.");

        int processed = 0, succeeded = 0, failed = 0, stpApproved = 0;

        foreach (var row in batch.Rows.Where(r => r.Status == "Pending"))
        {
            processed++;
            ValidateRow(row);

            if (!row.IsValid)
            {
                row.Status = "Failed";
                failed++;
                continue;
            }

            try
            {
                // Find or create Person
                var person = await db.People.FirstOrDefaultAsync(p => p.RsaIdNumber == row.RsaIdNumber);
                if (person == null)
                {
                    person = new Person
                    {
                        FirstName = row.FirstName,
                        MiddleName = row.MiddleName,
                        LastName = row.LastName,
                        RsaIdNumber = row.RsaIdNumber,
                        PassportNumber = row.PassportNumber,
                        DateOfBirth = row.DateOfBirth,
                        Gender = row.GenderCode,
                        EquityCode = row.EquityCode,
                        Email = row.EmailAddress,
                        CellNumber = row.PhoneNumber,
                        CreatedAt = DateTime.UtcNow,
                        CreatedBy = currentUsername
                    };
                    db.People.Add(person);
                    await db.SaveChangesAsync();
                }

                int? parsedSaqaId = null;
                if (!string.IsNullOrWhiteSpace(row.SaqaQualificationId) && int.TryParse(row.SaqaQualificationId, out var sId))
                {
                    parsedSaqaId = sId;
                }

                var companyLearner = new CompanyLearner
                {
                    PersonId = person.Id,
                    OrganisationId = batch.OrganisationId,
                    LearningProgrammeTypeCode = row.LearningProgrammeTypeCode,
                    SaqaQualificationId = parsedSaqaId,
                    QualificationTitle = row.QualificationTitle,
                    LearnerSignatureDate = row.LearnerSignatureDate,
                    CommencementDate = row.CommencementDate,
                    SubmissionDate = DateTime.UtcNow,
                    RegistrationChannel = "AutomatedBulk",
                    IngestionBatchId = batch.Id,
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = currentUsername
                };

                // Run STP Risk Engine
                var stpResult = await _stpEngine.EvaluateRegistrationRiskAsync(companyLearner);
                row.IsStpEligible = stpResult.IsStpEligible;
                row.StpDecisionNotes = stpResult.DecisionReason;
                companyLearner.StpApproved = stpResult.IsStpEligible;
                companyLearner.StpDecisionReason = stpResult.DecisionReason;

                if (stpResult.IsStpEligible)
                {
                    // Automatic registration
                    companyLearner.EnrolmentStatusCode = "Registered";
                    companyLearner.InstateStatusCode = "Active";
                    companyLearner.RegistrationDate = DateTime.UtcNow;
                    stpApproved++;
                    row.Status = "Registered";
                }
                else
                {
                    // Routes to human officer review queue
                    companyLearner.EnrolmentStatusCode = "Submitted";
                    companyLearner.InstateStatusCode = "PendingVerification";
                    row.Status = "QueuedForOfficer";
                }

                var registered = await _learnerService.RegisterLearnerAsync(
                    companyLearner,
                    stpResult.IsStpEligible ? "SYSTEM_STP_GATEKEEPER" : currentUsername
                );

                row.CompanyLearnerId = registered.Id;
                succeeded++;
            }
            catch (Exception ex)
            {
                row.Status = "Failed";
                row.ValidationErrors = ex.Message;
                failed++;
            }
        }

        batch.SuccessCount += succeeded;
        batch.ErrorCount += failed;
        batch.StpCount += stpApproved;
        batch.Status = failed > 0 ? (succeeded > 0 ? "PartiallyProcessed" : "Failed") : "Processed";
        batch.ModifiedAt = DateTime.UtcNow;
        batch.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        _audit.LogAction(db, "LearnerBulkBatch", batch.Id, "ProcessBatch", currentUsername, null, batch);
        await db.SaveChangesAsync();

        return new BulkBatchImportResult(batch, processed, succeeded, failed, stpApproved);
    }

    public async Task<List<LearnerBulkBatch>> GetBatchesAsync(int? organisationId = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var q = db.LearnerBulkBatches
            .Include(b => b.Organisation)
            .AsNoTracking();

        if (organisationId.HasValue)
        {
            q = q.Where(b => b.OrganisationId == organisationId.Value);
        }

        return await q.OrderByDescending(b => b.CreatedAt).ToListAsync();
    }

    public async Task<LearnerBulkBatch?> GetBatchByIdAsync(int batchId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.LearnerBulkBatches
            .Include(b => b.Organisation)
            .Include(b => b.Rows)
                .ThenInclude(r => r.CompanyLearner)
            .AsNoTracking()
            .FirstOrDefaultAsync(b => b.Id == batchId);
    }

    public async Task<LearnerBulkBatchRow> UpdateBatchRowAsync(
        int rowId,
        LearnerBulkBatchRow updatedRow,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.LearnerBulkBatchRows.FindAsync(rowId);
        if (existing == null) throw new KeyNotFoundException($"Batch row with ID {rowId} not found.");

        existing.FirstName = updatedRow.FirstName;
        existing.MiddleName = updatedRow.MiddleName;
        existing.LastName = updatedRow.LastName;
        existing.RsaIdNumber = updatedRow.RsaIdNumber;
        existing.PassportNumber = updatedRow.PassportNumber;
        existing.LearningProgrammeTypeCode = updatedRow.LearningProgrammeTypeCode;
        existing.SaqaQualificationId = updatedRow.SaqaQualificationId;
        existing.QualificationTitle = updatedRow.QualificationTitle;
        existing.TradeCode = updatedRow.TradeCode;
        existing.LearnerSignatureDate = updatedRow.LearnerSignatureDate;
        existing.CommencementDate = updatedRow.CommencementDate;
        existing.EmailAddress = updatedRow.EmailAddress;
        existing.PhoneNumber = updatedRow.PhoneNumber;

        if (RsaIdValidator.Validate(existing.RsaIdNumber))
        {
            var parsed = RsaIdValidator.Parse(existing.RsaIdNumber);
            if (parsed.DateOfBirth.HasValue) existing.DateOfBirth = parsed.DateOfBirth.Value;
            if (!string.IsNullOrEmpty(parsed.Gender)) existing.GenderCode = parsed.Gender == "Male" ? "M" : "F";
        }

        ValidateRow(existing);
        if (existing.IsValid)
        {
            existing.Status = "Pending";
        }

        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();
        return existing;
    }

    private static void ValidateRow(LearnerBulkBatchRow row)
    {
        var errors = new List<string>();

        if (string.IsNullOrWhiteSpace(row.FirstName)) errors.Add("First Name is required.");
        if (string.IsNullOrWhiteSpace(row.LastName)) errors.Add("Last Name is required.");

        if (string.IsNullOrWhiteSpace(row.RsaIdNumber))
        {
            if (string.IsNullOrWhiteSpace(row.PassportNumber)) errors.Add("RSA ID or Passport Number is required.");
        }
        else if (!RsaIdValidator.Validate(row.RsaIdNumber))
        {
            errors.Add("Invalid South African National ID number (Luhn check failed).");
        }

        if (row.LearnerSignatureDate == DateTime.MinValue)
        {
            errors.Add("Learner Signature Date is required.");
        }
        else
        {
            var elapsed = CompanyLearnerDomainValidator.CalculateWorkingDays(row.LearnerSignatureDate, DateTime.UtcNow);
            if (elapsed > 30)
            {
                errors.Add($"Signature date is {elapsed} working days old (> 30-day limit).");
            }
        }

        row.IsValid = errors.Count == 0;
        row.ValidationErrors = errors.Count > 0 ? string.Join("; ", errors) : null;
    }

    private static string[] ParseCsvLine(string line)
    {
        var result = new List<string>();
        bool inQuotes = false;
        var sb = new StringBuilder();

        foreach (char c in line)
        {
            if (c == '\"')
            {
                inQuotes = !inQuotes;
            }
            else if (c == ',' && !inQuotes)
            {
                result.Add(sb.ToString());
                sb.Clear();
            }
            else
            {
                sb.Append(c);
            }
        }
        result.Add(sb.ToString());
        return result.ToArray();
    }
}
