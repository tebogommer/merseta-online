using System.Globalization;
using System.Security.Cryptography;
using System.Text;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class TradeTestTaskScoreDto
{
    public int TaskNumber { get; set; }
    public string TaskTitle { get; set; } = string.Empty;
    public decimal TotalMarksAvailable { get; set; } = 100m;
    public decimal MarksObtained { get; set; }
    public decimal PassPercentage { get; set; } = 70m;
    public string? AssessorComments { get; set; }
}

public class NambBatchCandidateDto
{
    public int TradeTestApplicationId { get; set; }
    public string ApplicationNumber { get; set; } = string.Empty;
    public string CandidateName { get; set; } = string.Empty;
    public string NationalIdNumber { get; set; } = string.Empty;
    public string TradeTitle { get; set; } = string.Empty;
    public string TradeOfoCode { get; set; } = string.Empty;
    public string AssessmentCenterName { get; set; } = string.Empty;
    public DateTime? AssessmentDate { get; set; }
    public string CompetencyOutcome { get; set; } = "Competent";
    public string? AllocatedNambSerial { get; set; }
}

public interface INambBatchService
{
    Task<List<NambBatchCandidateDto>> GetPendingCandidatesForBatchAsync();
    Task<NambSubmissionBatch> CreateNambBatchAsync(List<int> applicationIds, string description, string currentUsername = "SYSTEM");
    Task<string> GenerateNambExportTsvAsync(int batchId);
    Task<NambSubmissionBatch> ProcessNambDecisionAsync(int batchId, string decisionCode, string moderatorNotes, Dictionary<int, string> allocatedSerials, string currentUsername = "SYSTEM");
    Task<LearnerTradeTestApplication> RecordPracticalTaskMarksAsync(int tradeTestId, List<TradeTestTaskScoreDto> taskScores, string assessorName, string currentUsername = "SYSTEM");
    Task<List<NambSubmissionBatch>> GetAllBatchesAsync();
    Task<NambSubmissionBatch?> GetBatchByIdAsync(int batchId);
}

public class NambBatchService : INambBatchService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public NambBatchService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<List<NambBatchCandidateDto>> GetPendingCandidatesForBatchAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.LearnerTradeTestApplications
            .Include(a => a.Person)
            .Where(a => a.CompetencyStatusCode == "Competent" && a.NambSubmissionBatchId == null)
            .OrderBy(a => a.AssessmentDate)
            .Select(a => new NambBatchCandidateDto
            {
                TradeTestApplicationId = a.Id,
                ApplicationNumber = a.ApplicationNumber,
                CandidateName = a.Person != null ? a.Person.FullName : "Artisan Candidate",
                NationalIdNumber = a.Person != null ? (a.Person.RsaIdNumber ?? a.Person.PassportNumber ?? "N/A") : "N/A",
                TradeTitle = a.TradeTitle,
                TradeOfoCode = a.TradeOfoCode ?? "N/A",
                AssessmentCenterName = a.AssessmentCenterName ?? "Accredited Test Centre",
                AssessmentDate = a.AssessmentDate,
                CompetencyOutcome = a.CompetencyStatusCode
            })
            .ToListAsync();
    }

    public async Task<NambSubmissionBatch> CreateNambBatchAsync(List<int> applicationIds, string description, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var applications = await db.LearnerTradeTestApplications
            .Where(a => applicationIds.Contains(a.Id) && a.NambSubmissionBatchId == null)
            .ToListAsync();

        if (applications.Count == 0)
            throw new InvalidOperationException("No eligible competent candidates selected for NAMB batching.");

        var existingCount = await db.NambSubmissionBatches.CountAsync();
        string batchRef = $"NAMB-{DateTime.UtcNow.Year}-B{(existingCount + 1):D4}";

        // Compute SHA-256 seal
        string sealRaw = $"NAMB-BATCH:{batchRef}:{applications.Count}:{string.Join(",", applications.Select(a => a.Id))}";
        using var sha = SHA256.Create();
        string seal = Convert.ToHexString(sha.ComputeHash(Encoding.UTF8.GetBytes(sealRaw))).ToLowerInvariant();

        var batch = new NambSubmissionBatch
        {
            BatchReferenceNumber = batchRef,
            BatchDescription = description,
            SubmissionDate = DateTime.UtcNow,
            Status = "SubmittedToNamb",
            TotalCandidates = applications.Count,
            ApprovedCandidates = 0,
            RejectedCandidates = 0,
            DigitalSecuritySeal = seal,
            CreatedBy = currentUsername
        };

        db.NambSubmissionBatches.Add(batch);
        await db.SaveChangesAsync();

        foreach (var app in applications)
        {
            app.NambSubmissionBatchId = batch.Id;
            app.NambSubmissionDate = DateTime.UtcNow;
            app.StatusCode = "AwaitingNambApproval";
            app.NambDecisionStatusCode = "Pending";
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("NambSubmissionBatch", batch.Id, "CreateBatch", currentUsername, new
        {
            batch.BatchReferenceNumber,
            batch.TotalCandidates,
            CandidateIds = applicationIds
        });

        return batch;
    }

    public async Task<string> GenerateNambExportTsvAsync(int batchId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var batch = await db.NambSubmissionBatches
            .Include(b => b.Applications)
                .ThenInclude(a => a.Person)
            .FirstOrDefaultAsync(b => b.Id == batchId);

        if (batch == null)
            throw new KeyNotFoundException($"NambSubmissionBatch with ID {batchId} not found.");

        var sb = new StringBuilder();
        // TSV Header
        sb.AppendLine("BATCH_REF\tAPP_REF\tRSA_ID\tCANDIDATE_NAME\tTRADE_TITLE\tOFO_CODE\tTEST_CENTRE\tASSESSMENT_DATE\tOUTCOME\tSTATUS");

        foreach (var app in batch.Applications)
        {
            var nationalId = app.Person?.RsaIdNumber ?? app.Person?.PassportNumber ?? "N/A";
            var name = app.Person?.FullName ?? "Artisan Candidate";
            var testDate = app.AssessmentDate?.ToString("yyyy-MM-dd", CultureInfo.InvariantCulture) ?? "";

            sb.AppendLine($"{batch.BatchReferenceNumber}\t{app.ApplicationNumber}\t{nationalId}\t{name}\t{app.TradeTitle}\t{app.TradeOfoCode}\t{app.AssessmentCenterName}\t{testDate}\t{app.CompetencyStatusCode}\t{app.StatusCode}");
        }

        return sb.ToString();
    }

    public async Task<NambSubmissionBatch> ProcessNambDecisionAsync(int batchId, string decisionCode, string moderatorNotes, Dictionary<int, string> allocatedSerials, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var batch = await db.NambSubmissionBatches
            .Include(b => b.Applications)
            .FirstOrDefaultAsync(b => b.Id == batchId);

        if (batch == null)
            throw new KeyNotFoundException($"NambSubmissionBatch with ID {batchId} not found.");

        batch.AdjudicationDate = DateTime.UtcNow;
        batch.NambModeratorNotes = moderatorNotes;
        batch.Status = decisionCode; // Approved, PartiallyApproved, Rejected

        int approvedCount = 0;
        int rejectedCount = 0;

        foreach (var app in batch.Applications)
        {
            if (allocatedSerials.TryGetValue(app.Id, out var serial) && !string.IsNullOrWhiteSpace(serial))
            {
                app.NambSerialNumber = serial;
                app.NambApprovalDate = DateTime.UtcNow;
                app.NambDecisionStatusCode = "Approved";
                app.SerialCertificateNumber = $"CERT-NAMB-{DateTime.UtcNow.Year}-{app.Id:D5}";
                app.CertificateIssueDate = DateTime.UtcNow;
                app.StatusCode = "Certified";
                approvedCount++;
            }
            else
            {
                app.NambDecisionStatusCode = "Rejected";
                app.StatusCode = "NambQueried";
                rejectedCount++;
            }
        }

        batch.ApprovedCandidates = approvedCount;
        batch.RejectedCandidates = rejectedCount;

        await db.SaveChangesAsync();

        await _audit.LogAsync("NambSubmissionBatch", batch.Id, "ProcessNambDecision", currentUsername, new
        {
            batch.Status,
            batch.ApprovedCandidates,
            batch.RejectedCandidates,
            ModeratorNotes = moderatorNotes
        });

        return batch;
    }

    public async Task<LearnerTradeTestApplication> RecordPracticalTaskMarksAsync(int tradeTestId, List<TradeTestTaskScoreDto> taskScores, string assessorName, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.LearnerTradeTestApplications
            .Include(a => a.Tasks)
            .FirstOrDefaultAsync(a => a.Id == tradeTestId);

        if (app == null)
            throw new KeyNotFoundException($"LearnerTradeTestApplication with ID {tradeTestId} not found.");

        app.AssessorName = assessorName;

        // Clear existing or update
        db.TradeTestTasks.RemoveRange(app.Tasks);

        decimal totalScore = 0m;
        decimal totalPossible = 0m;
        bool allTasksPassed = true;

        foreach (var score in taskScores)
        {
            decimal pct = score.TotalMarksAvailable > 0 ? (score.MarksObtained / score.TotalMarksAvailable) * 100m : 0m;
            bool isComp = pct >= score.PassPercentage;
            if (!isComp) allTasksPassed = false;

            totalScore += score.MarksObtained;
            totalPossible += score.TotalMarksAvailable;

            var task = new TradeTestTask
            {
                LearnerTradeTestApplicationId = app.Id,
                TaskNumber = score.TaskNumber,
                TaskTitle = score.TaskTitle,
                TotalMarksAvailable = score.TotalMarksAvailable,
                MarksObtained = score.MarksObtained,
                PassPercentage = score.PassPercentage,
                PercentageAchieved = pct,
                IsCompetent = isComp,
                AssessorComments = score.AssessorComments,
                CreatedBy = currentUsername
            };
            db.TradeTestTasks.Add(task);
        }

        decimal overallPct = totalPossible > 0 ? (totalScore / totalPossible) * 100m : 0m;

        // QCTO standard: minimum 70% threshold + all compulsory tasks passed
        if (allTasksPassed && overallPct >= 70m)
        {
            app.CompetencyStatusCode = "Competent";
            app.StatusCode = "Competent";
        }
        else
        {
            app.CompetencyStatusCode = "NotYetCompetent";
            app.StatusCode = "NotYetCompetent";
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("LearnerTradeTestApplication", app.Id, "RecordPracticalTasks", currentUsername, new
        {
            app.CompetencyStatusCode,
            OverallPercentage = overallPct.ToString("F2", CultureInfo.InvariantCulture),
            TasksCount = taskScores.Count
        });

        return app;
    }

    public async Task<List<NambSubmissionBatch>> GetAllBatchesAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.NambSubmissionBatches
            .Include(b => b.Applications)
            .OrderByDescending(b => b.CreatedAt)
            .ToListAsync();
    }

    public async Task<NambSubmissionBatch?> GetBatchByIdAsync(int batchId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.NambSubmissionBatches
            .Include(b => b.Applications)
                .ThenInclude(a => a.Person)
            .Include(b => b.Applications)
                .ThenInclude(a => a.Organisation)
            .FirstOrDefaultAsync(b => b.Id == batchId);
    }
}
