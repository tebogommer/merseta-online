using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class LearnerLifecycleService : ILearnerLifecycleService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly AuditService _audit;

    public LearnerLifecycleService(INsdmsDbContextFactory contextFactory, AuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<CompanyLearnerTransfer> RequestTransferAsync(CompanyLearnerTransfer transfer, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var learner = await db.CompanyLearners.FindAsync(transfer.CompanyLearnerId);
        if (learner == null) throw new KeyNotFoundException($"CompanyLearner with ID {transfer.CompanyLearnerId} not found.");

        transfer.FromOrganisationId = learner.OrganisationId;
        transfer.TransferStatusCode = "Pending";
        transfer.CreatedAt = DateTime.UtcNow;
        transfer.CreatedBy = currentUsername;

        db.CompanyLearnerTransfers.Add(transfer);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "CompanyLearnerTransfer", transfer.Id, "RequestTransfer", currentUsername, null, transfer);
        await db.SaveChangesAsync();

        return transfer;
    }

    public async Task<CompanyLearnerTransfer> ApproveTransferAsync(int transferId, string comments, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var transfer = await db.CompanyLearnerTransfers
            .Include(t => t.CompanyLearner)
            .FirstOrDefaultAsync(t => t.Id == transferId);

        if (transfer == null) throw new KeyNotFoundException($"CompanyLearnerTransfer with ID {transferId} not found.");

        var beforeTransfer = new { transfer.TransferStatusCode, transfer.CompanyLearner?.OrganisationId };

        transfer.TransferStatusCode = "Approved";
        transfer.ApprovalComments = comments;
        transfer.ApprovedByUserId = currentUsername;
        transfer.ApprovalDate = DateTime.UtcNow;
        transfer.ModifiedAt = DateTime.UtcNow;
        transfer.ModifiedBy = currentUsername;

        var learner = transfer.CompanyLearner ?? await db.CompanyLearners.FindAsync(transfer.CompanyLearnerId);
        if (learner != null)
        {
            learner.OrganisationId = transfer.ToOrganisationId;
            learner.ModifiedAt = DateTime.UtcNow;
            learner.ModifiedBy = currentUsername;
        }

        _audit.LogAction(db, "CompanyLearnerTransfer", transfer.Id, "ApproveTransfer", currentUsername, beforeTransfer, transfer);
        await db.SaveChangesAsync();

        return transfer;
    }

    public async Task<CompanyLearnerTransfer> RejectTransferAsync(int transferId, string reason, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var transfer = await db.CompanyLearnerTransfers.FindAsync(transferId);
        if (transfer == null) throw new KeyNotFoundException($"CompanyLearnerTransfer with ID {transferId} not found.");

        var before = new { transfer.TransferStatusCode };
        transfer.TransferStatusCode = "Rejected";
        transfer.ApprovalComments = reason;
        transfer.ApprovedByUserId = currentUsername;
        transfer.ApprovalDate = DateTime.UtcNow;
        transfer.ModifiedAt = DateTime.UtcNow;
        transfer.ModifiedBy = currentUsername;

        _audit.LogAction(db, "CompanyLearnerTransfer", transfer.Id, "RejectTransfer", currentUsername, before, transfer);
        await db.SaveChangesAsync();

        return transfer;
    }

    public async Task<List<CompanyLearnerTransfer>> GetTransfersByLearnerAsync(int companyLearnerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerTransfers
            .Include(t => t.FromOrganisation)
            .Include(t => t.ToOrganisation)
            .Where(t => t.CompanyLearnerId == companyLearnerId)
            .OrderByDescending(t => t.TransferDate)
            .ToListAsync();
    }

    public async Task<CompanyLearnerLostTime> RecordLostTimeAsync(CompanyLearnerLostTime lostTime, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var learner = await db.CompanyLearners.FindAsync(lostTime.CompanyLearnerId);
        if (learner == null) throw new KeyNotFoundException($"CompanyLearner with ID {lostTime.CompanyLearnerId} not found.");

        int days = Math.Max(1, (int)(lostTime.EndDate.Date - lostTime.StartDate.Date).TotalDays);
        lostTime.DaysLost = days;
        lostTime.OriginalContractEndDate = learner.RegistrationDate.AddYears(3); // Default apprenticeship length
        lostTime.RevisedContractEndDate = lostTime.OriginalContractEndDate.AddDays(days);
        lostTime.LostTimeStatusCode = "Pending";
        lostTime.CreatedAt = DateTime.UtcNow;
        lostTime.CreatedBy = currentUsername;

        db.CompanyLearnerLostTimes.Add(lostTime);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "CompanyLearnerLostTime", lostTime.Id, "RecordLostTime", currentUsername, null, lostTime);
        await db.SaveChangesAsync();

        return lostTime;
    }

    public async Task<CompanyLearnerLostTime> ApproveLostTimeAsync(int lostTimeId, string comments, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var lostTime = await db.CompanyLearnerLostTimes.FindAsync(lostTimeId);
        if (lostTime == null) throw new KeyNotFoundException($"CompanyLearnerLostTime with ID {lostTimeId} not found.");

        var before = new { lostTime.LostTimeStatusCode };
        lostTime.LostTimeStatusCode = "Approved";
        lostTime.ApprovalComments = comments;
        lostTime.ApprovedByUserId = currentUsername;
        lostTime.ApprovalDate = DateTime.UtcNow;
        lostTime.ModifiedAt = DateTime.UtcNow;
        lostTime.ModifiedBy = currentUsername;

        _audit.LogAction(db, "CompanyLearnerLostTime", lostTime.Id, "ApproveLostTime", currentUsername, before, lostTime);
        await db.SaveChangesAsync();

        return lostTime;
    }

    public async Task<List<CompanyLearnerLostTime>> GetLostTimeByLearnerAsync(int companyLearnerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerLostTimes
            .Where(l => l.CompanyLearnerId == companyLearnerId)
            .OrderByDescending(l => l.StartDate)
            .ToListAsync();
    }

    public async Task<CompanyLearnerTermination> RequestTerminationAsync(CompanyLearnerTermination termination, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        termination.TerminationStatusCode = "Pending";
        termination.CreatedAt = DateTime.UtcNow;
        termination.CreatedBy = currentUsername;

        db.CompanyLearnerTerminations.Add(termination);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "CompanyLearnerTermination", termination.Id, "RequestTermination", currentUsername, null, termination);
        await db.SaveChangesAsync();

        return termination;
    }

    public async Task<CompanyLearnerTermination> ApproveTerminationAsync(int terminationId, string comments, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var termination = await db.CompanyLearnerTerminations
            .Include(t => t.CompanyLearner)
            .FirstOrDefaultAsync(t => t.Id == terminationId);

        if (termination == null) throw new KeyNotFoundException($"CompanyLearnerTermination with ID {terminationId} not found.");

        var before = new { termination.TerminationStatusCode, LearnerStatus = termination.CompanyLearner?.EnrolmentStatusCode };

        termination.TerminationStatusCode = "Approved";
        termination.ApprovalComments = comments;
        termination.ApprovedByUserId = currentUsername;
        termination.ApprovalDate = DateTime.UtcNow;
        termination.ModifiedAt = DateTime.UtcNow;
        termination.ModifiedBy = currentUsername;

        var learner = termination.CompanyLearner ?? await db.CompanyLearners.FindAsync(termination.CompanyLearnerId);
        if (learner != null)
        {
            learner.EnrolmentStatusCode = "Terminated";
            learner.ModifiedAt = DateTime.UtcNow;
            learner.ModifiedBy = currentUsername;
        }

        _audit.LogAction(db, "CompanyLearnerTermination", termination.Id, "ApproveTermination", currentUsername, before, termination);
        await db.SaveChangesAsync();

        return termination;
    }

    public async Task<List<CompanyLearnerTermination>> GetTerminationsByLearnerAsync(int companyLearnerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerTerminations
            .Where(t => t.CompanyLearnerId == companyLearnerId)
            .OrderByDescending(t => t.EffectiveDate)
            .ToListAsync();
    }
}
