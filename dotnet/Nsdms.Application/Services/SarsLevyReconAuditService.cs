using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class SarsLevyReconAuditService : ISarsLevyReconAuditService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly AuditService _audit;

    public SarsLevyReconAuditService(INsdmsDbContextFactory contextFactory, AuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<SarsLevyReconAudit> PerformSarsLevyAuditAsync(
        string financialYear,
        string sdlNumber,
        decimal totalSarsLeviesReceived,
        decimal totalCalculatedLeviesExpected,
        int? organisationId = null,
        string? auditNotes = null,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        decimal variance = totalSarsLeviesReceived - totalCalculatedLeviesExpected;
        string reasonCode;
        string statusCode;
        bool clawbackReq = false;
        decimal clawbackAmt = 0m;

        if (Math.Abs(variance) < 0.01m)
        {
            reasonCode = "ExactMatch";
            statusCode = "Reconciled";
        }
        else if (variance < 0)
        {
            reasonCode = "Underpayment";
            statusCode = "DiscrepancyFlagged";
        }
        else
        {
            reasonCode = "Overpayment";
            statusCode = "DiscrepancyFlagged";
            clawbackReq = true;
            clawbackAmt = variance;
        }

        var audit = new SarsLevyReconAudit
        {
            FinancialYear = financialYear,
            SchemeYear = financialYear,
            SdlNumber = sdlNumber,
            OrganisationId = organisationId,
            TotalSarsLeviesReceived = totalSarsLeviesReceived,
            TotalCalculatedLeviesExpected = totalCalculatedLeviesExpected,
            VarianceAmount = variance,
            DiscrepancyReasonCode = reasonCode,
            ClawbackActionRequired = clawbackReq,
            ClawbackAmount = clawbackAmt,
            AuditStatusCode = statusCode,
            AuditNotes = auditNotes,
            AuditorUserId = currentUsername,
            ReconciliationDate = DateTime.UtcNow,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.SarsLevyReconAudits.Add(audit);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "SarsLevyReconAudit", audit.Id, "PerformSarsLevyAudit", currentUsername, null, audit);
        await db.SaveChangesAsync();

        return audit;
    }

    public async Task<SarsLevyReconAudit> IssueClawbackNoticeAsync(
        int auditId,
        decimal clawbackAmount,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var audit = await db.SarsLevyReconAudits.FirstOrDefaultAsync(a => a.Id == auditId);
        if (audit == null)
        {
            throw new KeyNotFoundException($"SarsLevyReconAudit with ID {auditId} not found.");
        }

        var before = new { audit.AuditStatusCode, audit.ClawbackAmount, audit.ClawbackIssuedDate };
        audit.AuditStatusCode = "ClawbackIssued";
        audit.ClawbackAmount = clawbackAmount;
        audit.ClawbackActionRequired = true;
        audit.ClawbackIssuedDate = DateTime.UtcNow;
        audit.ModifiedAt = DateTime.UtcNow;
        audit.ModifiedBy = currentUsername;

        _audit.LogAction(db, "SarsLevyReconAudit", audit.Id, "IssueClawbackNotice", currentUsername, before, audit);
        await db.SaveChangesAsync();

        return audit;
    }

    public async Task<SarsLevyReconAudit> SettleClawbackAsync(
        int auditId,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var audit = await db.SarsLevyReconAudits.FirstOrDefaultAsync(a => a.Id == auditId);
        if (audit == null)
        {
            throw new KeyNotFoundException($"SarsLevyReconAudit with ID {auditId} not found.");
        }

        var before = new { audit.AuditStatusCode, audit.ClawbackSettledDate };
        audit.AuditStatusCode = "Resolved";
        audit.ClawbackSettledDate = DateTime.UtcNow;
        audit.ModifiedAt = DateTime.UtcNow;
        audit.ModifiedBy = currentUsername;

        _audit.LogAction(db, "SarsLevyReconAudit", audit.Id, "SettleClawback", currentUsername, before, audit);
        await db.SaveChangesAsync();

        return audit;
    }

    public async Task<List<SarsLevyReconAudit>> GetReconAuditsAsync(string? financialYear = null, string? statusCode = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.SarsLevyReconAudits
            .Include(a => a.Organisation)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(financialYear))
        {
            query = query.Where(a => a.FinancialYear == financialYear);
        }

        if (!string.IsNullOrWhiteSpace(statusCode))
        {
            query = query.Where(a => a.AuditStatusCode == statusCode);
        }

        return await query.OrderByDescending(a => a.CreatedAt).ToListAsync();
    }

    public async Task<SarsLevyReconAudit?> GetAuditByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.SarsLevyReconAudits
            .Include(a => a.Organisation)
            .FirstOrDefaultAsync(a => a.Id == id);
    }
}
