using System.Security.Cryptography;
using System.Text;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class SummativeAssessmentAndModerationService : ISummativeAssessmentAndModerationService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly AuditService _audit;

    public SummativeAssessmentAndModerationService(INsdmsDbContextFactory contextFactory, AuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<SummativeAssessmentReport> CreateSummativeAssessmentReportAsync(
        int companyLearnerId,
        string qualificationTitle,
        string? saqaQualId,
        int nqfLevel,
        string interventionTypeCode = "Learnership",
        int totalCreditsRequired = 120,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var learner = await db.CompanyLearners
            .Include(l => l.Person)
            .Include(l => l.Organisation)
            .FirstOrDefaultAsync(l => l.Id == companyLearnerId);

        if (learner == null)
        {
            throw new KeyNotFoundException($"CompanyLearner with ID {companyLearnerId} not found.");
        }

        var report = new SummativeAssessmentReport
        {
            CompanyLearnerId = companyLearnerId,
            PersonId = learner.PersonId,
            OrganisationId = learner.OrganisationId,
            ReportNumber = $"SOR-REP-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}",
            QualificationTitle = qualificationTitle,
            SaqaQualificationId = saqaQualId,
            NqfLevel = nqfLevel,
            InterventionTypeCode = interventionTypeCode,
            TotalCreditsRequired = totalCreditsRequired,
            AssessmentDate = DateTime.UtcNow,
            StatusCode = "Draft",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.SummativeAssessmentReports.Add(report);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "SummativeAssessmentReport", report.Id, "CreateSummativeAssessmentReport", currentUsername, null, report);
        await db.SaveChangesAsync();

        return report;
    }

    public async Task<SummativeAssessmentReport> RecordUnitStandardCreditsAsync(
        int reportId,
        int assessorPersonId,
        string assessorRegNumber,
        List<SummativeAssessmentUnitStandard> unitStandards,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var report = await db.SummativeAssessmentReports
            .Include(r => r.UnitStandardAssessments)
            .FirstOrDefaultAsync(r => r.Id == reportId);

        if (report == null)
        {
            throw new KeyNotFoundException($"SummativeAssessmentReport with ID {reportId} not found.");
        }

        if (report.UnitStandardAssessments.Any())
        {
            db.SummativeAssessmentUnitStandards.RemoveRange(report.UnitStandardAssessments);
        }

        int totalEarned = 0;
        foreach (var us in unitStandards)
        {
            us.SummativeAssessmentReportId = reportId;
            us.CreatedAt = DateTime.UtcNow;
            us.CreatedBy = currentUsername;
            if (us.CompetencyStatusCode == "Competent")
            {
                totalEarned += us.Credits;
            }
            db.SummativeAssessmentUnitStandards.Add(us);
        }

        var before = new { report.AssessorPersonId, report.TotalCreditsEarned, report.StatusCode };
        report.AssessorPersonId = assessorPersonId;
        report.AssessorRegistrationNumber = assessorRegNumber;
        report.TotalCreditsEarned = totalEarned;
        report.StatusCode = "Assessed";
        report.ModifiedAt = DateTime.UtcNow;
        report.ModifiedBy = currentUsername;

        _audit.LogAction(db, "SummativeAssessmentReport", report.Id, "RecordAssessorCredits", currentUsername, before, report);
        await db.SaveChangesAsync();

        return report;
    }

    public async Task<SummativeAssessmentReport> PerformInternalModerationAsync(
        int reportId,
        int moderatorPersonId,
        string moderatorRegNumber,
        Dictionary<int, (string Outcome, string? Comments)> moderationDecisions,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var report = await db.SummativeAssessmentReports
            .Include(r => r.UnitStandardAssessments)
            .FirstOrDefaultAsync(r => r.Id == reportId);

        if (report == null)
        {
            throw new KeyNotFoundException($"SummativeAssessmentReport with ID {reportId} not found.");
        }

        int newEarned = 0;
        foreach (var us in report.UnitStandardAssessments)
        {
            if (moderationDecisions.TryGetValue(us.Id, out var dec))
            {
                us.IsModerated = true;
                us.ModerationOutcome = dec.Outcome;
                us.ModeratorComments = dec.Comments;

                if (dec.Outcome == "Overturned")
                {
                    us.CompetencyStatusCode = us.CompetencyStatusCode == "Competent" ? "NotYetCompetent" : "Competent";
                }
            }

            if (us.CompetencyStatusCode == "Competent")
            {
                newEarned += us.Credits;
            }
            us.ModifiedAt = DateTime.UtcNow;
            us.ModifiedBy = currentUsername;
        }

        var before = new { report.InternalModeratorPersonId, report.TotalCreditsEarned, report.StatusCode };
        report.InternalModeratorPersonId = moderatorPersonId;
        report.InternalModeratorRegistrationNumber = moderatorRegNumber;
        report.ModerationDate = DateTime.UtcNow;
        report.TotalCreditsEarned = newEarned;
        report.StatusCode = "InternalModerated";
        report.ModifiedAt = DateTime.UtcNow;
        report.ModifiedBy = currentUsername;

        _audit.LogAction(db, "SummativeAssessmentReport", report.Id, "PerformInternalModeration", currentUsername, before, report);
        await db.SaveChangesAsync();

        return report;
    }

    public async Task<SummativeAssessmentReport> PerformEtqaExternalModerationAsync(
        int reportId,
        bool isApproved,
        string? externalModeratorComments,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var report = await db.SummativeAssessmentReports.FirstOrDefaultAsync(r => r.Id == reportId);
        if (report == null)
        {
            throw new KeyNotFoundException($"SummativeAssessmentReport with ID {reportId} not found.");
        }

        var before = new { report.StatusCode, report.ExternalModeratorApprovalDate };
        report.ExternalModeratorUserId = currentUsername;
        report.ExternalModeratorApprovalDate = DateTime.UtcNow;
        report.ExternalModeratorComments = externalModeratorComments;
        report.StatusCode = isApproved ? "CreditsApproved" : "Rejected";
        report.ModifiedAt = DateTime.UtcNow;
        report.ModifiedBy = currentUsername;

        _audit.LogAction(db, "SummativeAssessmentReport", report.Id, "PerformEtqaExternalModeration", currentUsername, before, report);
        await db.SaveChangesAsync();

        return report;
    }

    public async Task<EisaAssessmentEntry> RecordEisaExamEntryAsync(
        int reportId,
        DateTime examDate,
        string centerName,
        string paperCode,
        decimal scoreAchieved,
        decimal totalScorePossible,
        string? qctoRefNumber,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var report = await db.SummativeAssessmentReports.FirstOrDefaultAsync(r => r.Id == reportId);
        if (report == null)
        {
            throw new KeyNotFoundException($"SummativeAssessmentReport with ID {reportId} not found.");
        }

        decimal pct = totalScorePossible > 0 ? Math.Round((scoreAchieved / totalScorePossible) * 100m, 2) : 0m;
        string outcome = pct >= 60m ? "Competent" : "NotYetCompetent";

        var eisa = new EisaAssessmentEntry
        {
            SummativeAssessmentReportId = reportId,
            EisaAssessmentDate = examDate,
            EisaCenterName = centerName,
            AssessmentPaperCode = paperCode,
            ScoreAchieved = scoreAchieved,
            TotalScorePossible = totalScorePossible,
            PercentageScore = pct,
            CompetencyStatusCode = outcome,
            QctoModerationReferenceNumber = qctoRefNumber ?? $"QCTO-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}",
            QctoSignOffDate = DateTime.UtcNow,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.EisaAssessmentEntries.Add(eisa);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "SummativeAssessmentReport", report.Id, "RecordEisaExamEntry", currentUsername, null, eisa);
        await db.SaveChangesAsync();

        return eisa;
    }

    public async Task<StatementOfResults> IssueStatementOfResultsAsync(
        int reportId,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var report = await db.SummativeAssessmentReports
            .Include(r => r.CompanyLearner)
            .Include(r => r.Person)
            .FirstOrDefaultAsync(r => r.Id == reportId);

        if (report == null)
        {
            throw new KeyNotFoundException($"SummativeAssessmentReport with ID {reportId} not found.");
        }

        if (report.StatusCode != "CreditsApproved")
        {
            throw new InvalidOperationException("Cannot issue Statement of Results before ETQA external moderation approval.");
        }

        var serialNumber = $"SOR-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..8].ToUpper()}";
        
        // Generate cryptographic SHA-256 verification hash
        var rawPayload = $"{serialNumber}:{report.CompanyLearnerId}:{report.PersonId}:{report.TotalCreditsEarned}:{DateTime.UtcNow:O}";
        var hashBytes = SHA256.HashData(Encoding.UTF8.GetBytes(rawPayload));
        var tamperHash = Convert.ToHexString(hashBytes).ToLowerInvariant();

        var sor = new StatementOfResults
        {
            SummativeAssessmentReportId = reportId,
            CompanyLearnerId = report.CompanyLearnerId,
            PersonId = report.PersonId,
            SorSerialNumber = serialNumber,
            DateIssued = DateTime.UtcNow,
            TotalCreditsCertified = report.TotalCreditsEarned,
            TamperProofHashSha256 = tamperHash,
            QrVerificationUrl = $"https://verify.merseta.org.za/sor/{tamperHash}",
            IssuedByUserId = currentUsername,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.StatementOfResults.Add(sor);

        var before = new { report.StatusCode };
        report.StatusCode = "SorIssued";
        report.ModifiedAt = DateTime.UtcNow;
        report.ModifiedBy = currentUsername;

        _audit.LogAction(db, "SummativeAssessmentReport", report.Id, "IssueStatementOfResults", currentUsername, before, sor);
        await db.SaveChangesAsync();

        return sor;
    }

    public async Task<SummativeAssessmentReport?> GetReportByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.SummativeAssessmentReports
            .Include(r => r.Person)
            .Include(r => r.Organisation)
            .Include(r => r.TrainingProvider)
            .Include(r => r.CompanyLearner)
            .Include(r => r.AssessorPerson)
            .Include(r => r.InternalModeratorPerson)
            .Include(r => r.UnitStandardAssessments)
            .Include(r => r.EisaEntries)
            .Include(r => r.StatementOfResultsList)
            .FirstOrDefaultAsync(r => r.Id == id);
    }

    public async Task<List<SummativeAssessmentReport>> GetReportsAsync(string? statusCode = null, string? qualificationTitle = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.SummativeAssessmentReports
            .Include(r => r.Person)
            .Include(r => r.Organisation)
            .Include(r => r.TrainingProvider)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(statusCode))
        {
            query = query.Where(r => r.StatusCode == statusCode);
        }

        if (!string.IsNullOrWhiteSpace(qualificationTitle))
        {
            query = query.Where(r => r.QualificationTitle.Contains(qualificationTitle));
        }

        return await query.OrderByDescending(r => r.CreatedAt).ToListAsync();
    }

    public async Task<StatementOfResults?> VerifyStatementOfResultsAsync(string serialNumberOrHash)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.StatementOfResults
            .Include(s => s.Person)
            .Include(s => s.CompanyLearner)
            .Include(s => s.SummativeAssessmentReport)
            .FirstOrDefaultAsync(s => s.SorSerialNumber == serialNumberOrHash || s.TamperProofHashSha256 == serialNumberOrHash);
    }
}
