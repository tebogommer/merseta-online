using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class TrainingCommitteeAndDisputeService : ITrainingCommitteeAndDisputeService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly AuditService _audit;

    public TrainingCommitteeAndDisputeService(INsdmsDbContextFactory contextFactory, AuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<TrainingCommittee> RegisterCommitteeAsync(
        TrainingCommittee committee,
        List<TrainingCommitteeMember> members,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        // Statutory validation: training committee must have both employer and labour representatives
        bool hasLabour = members.Any(m => m.MemberRoleCode == "UnionRepresentative" || m.Constituency != "Management");
        bool hasEmployer = members.Any(m => m.MemberRoleCode == "EmployerRepresentative" || m.Constituency == "Management");

        committee.ConstitutionalQuorumMet = hasLabour && hasEmployer;
        committee.CommitteeStatusCode = committee.ConstitutionalQuorumMet ? "Active" : "NonCompliant";
        committee.CreatedAt = DateTime.UtcNow;
        committee.CreatedBy = currentUsername;

        foreach (var member in members)
        {
            member.CreatedAt = DateTime.UtcNow;
            member.CreatedBy = currentUsername;
        }

        committee.Members = members;

        db.TrainingCommittees.Add(committee);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "TrainingCommittee", committee.Id, "RegisterCommittee", currentUsername, null, committee);
        await db.SaveChangesAsync();

        return committee;
    }

    public async Task<TrainingCommittee?> GetCommitteeByOrgAsync(int organisationId, int? financialYear = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var year = financialYear ?? DateTime.UtcNow.Year;
        return await db.TrainingCommittees
            .Include(t => t.Organisation)
            .Include(t => t.Members)
                .ThenInclude(m => m.Person)
            .FirstOrDefaultAsync(t => t.OrganisationId == organisationId && t.FinancialYear == year);
    }

    public async Task<List<TrainingCommittee>> GetAllCommitteesAsync(int? financialYear = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.TrainingCommittees
            .Include(t => t.Organisation)
            .Include(t => t.Members)
            .AsQueryable();

        if (financialYear.HasValue && financialYear.Value > 0)
        {
            query = query.Where(t => t.FinancialYear == financialYear.Value);
        }

        return await query.OrderByDescending(t => t.FinancialYear).ToListAsync();
    }

    public async Task<WspDispute> LogDisputeAsync(WspDispute dispute, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        dispute.DisputeReferenceNumber = $"DSP-{DateTime.UtcNow:yyyyMM}-{Guid.NewGuid().ToString()[..4].ToUpper()}";
        dispute.DisputeStatusCode = "Logged";
        dispute.CreatedAt = DateTime.UtcNow;
        dispute.CreatedBy = currentUsername;

        db.WspDisputes.Add(dispute);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "WspDispute", dispute.Id, "LogDispute", currentUsername, null, dispute);
        await db.SaveChangesAsync();

        return dispute;
    }

    public async Task<WspDispute> ResolveDisputeAsync(int disputeId, string resolutionNotes, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var dispute = await db.WspDisputes.FindAsync(disputeId);
        if (dispute == null) throw new KeyNotFoundException($"WspDispute with ID {disputeId} not found.");

        var before = new { dispute.DisputeStatusCode, dispute.ResolutionDate };
        dispute.DisputeStatusCode = "Resolved";
        dispute.ResolutionDate = DateTime.UtcNow;
        dispute.ResolutionNotes = resolutionNotes;
        dispute.ModifiedAt = DateTime.UtcNow;
        dispute.ModifiedBy = currentUsername;

        if (dispute.WspSubmissionId.HasValue)
        {
            var wsp = await db.WspSubmissions.FindAsync(dispute.WspSubmissionId.Value);
            if (wsp != null)
            {
                var otherActiveDisputes = await db.WspDisputes.AnyAsync(d =>
                    d.WspSubmissionId == wsp.Id &&
                    d.Id != dispute.Id &&
                    d.DisputeStatusCode == "Logged");

                if (!otherActiveDisputes)
                {
                    wsp.DisputeLogged = false;
                    wsp.WspApprovalStatusCode = "PendingSignoff";
                    wsp.ModifiedAt = DateTime.UtcNow;
                    wsp.ModifiedBy = currentUsername;
                }
            }
        }

        _audit.LogAction(db, "WspDispute", dispute.Id, "ResolveDispute", currentUsername, before, dispute);
        await db.SaveChangesAsync();

        return dispute;
    }

    public async Task<List<WspDispute>> GetAllDisputesAsync(int? organisationId = null, string? statusCode = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.WspDisputes
            .Include(d => d.Organisation)
            .Include(d => d.WspSubmission)
            .AsQueryable();

        if (organisationId.HasValue && organisationId.Value > 0)
        {
            query = query.Where(d => d.OrganisationId == organisationId.Value);
        }

        if (!string.IsNullOrWhiteSpace(statusCode))
        {
            query = query.Where(d => d.DisputeStatusCode == statusCode);
        }

        return await query.OrderByDescending(d => d.CreatedAt).ToListAsync();
    }

    public async Task<WspSkillsGap> RecordSkillsGapAsync(WspSkillsGap skillsGap, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        skillsGap.CreatedAt = DateTime.UtcNow;
        skillsGap.CreatedBy = currentUsername;

        db.WspSkillsGaps.Add(skillsGap);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "WspSkillsGap", skillsGap.Id, "RecordSkillsGap", currentUsername, null, skillsGap);
        await db.SaveChangesAsync();

        return skillsGap;
    }

    public async Task<List<WspSkillsGap>> GetSkillsGapsByOrgAsync(int organisationId, int? financialYear = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var year = financialYear ?? DateTime.UtcNow.Year;
        return await db.WspSkillsGaps
            .Where(s => s.OrganisationId == organisationId && s.FinancialYear == year)
            .OrderBy(s => s.OfoCode)
            .ToListAsync();
    }
}
