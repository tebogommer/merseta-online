using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IWorkplaceApprovalService
{
    Task<List<WorkplaceApproval>> GetAllAsync(string? search = null, string? status = null, int? organisationId = null);
    Task<WorkplaceApproval?> GetByIdAsync(int id);
    Task<List<WorkplaceApproval>> GetByOrganisationIdAsync(int organisationId);
    Task<WorkplaceApproval> CreateAsync(WorkplaceApproval approval, string currentUsername = "SYSTEM");
    Task<WorkplaceApproval> UpdateAsync(WorkplaceApproval approval, string currentUsername = "SYSTEM");
    Task<WorkplaceApproval> SaveAsync(WorkplaceApproval approval, string currentUsername = "SYSTEM");
    Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM");

    Task<WorkplaceApprovalMentor> AddMentorAsync(WorkplaceApprovalMentor mentor, string currentUsername = "SYSTEM");
    Task<bool> RemoveMentorAsync(int mentorId, string currentUsername = "SYSTEM");

    Task<WorkplaceApprovalToolList> AddToolItemAsync(WorkplaceApprovalToolList toolItem, string currentUsername = "SYSTEM");
    Task<bool> RemoveToolItemAsync(int toolItemId, string currentUsername = "SYSTEM");

    Task<WorkplaceApproval> ApproveWorkplaceAsync(int id, string recommendations, string currentUsername = "SYSTEM");
    Task<WorkplaceApproval> RejectWorkplaceAsync(int id, string reason, string currentUsername = "SYSTEM");
}

public class WorkplaceApprovalService : IWorkplaceApprovalService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public WorkplaceApprovalService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<List<WorkplaceApproval>> GetAllAsync(string? search = null, string? status = null, int? organisationId = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.WorkplaceApprovals
            .Include(w => w.Organisation)
            .Include(w => w.OrganisationSite)
            .Include(w => w.AssessorPerson)
            .Include(w => w.Mentors).ThenInclude(m => m.Person)
            .Include(w => w.ToolItems)
            .AsQueryable();

        if (organisationId.HasValue && organisationId.Value > 0)
        {
            query = query.Where(w => w.OrganisationId == organisationId.Value);
        }

        if (!string.IsNullOrWhiteSpace(status))
        {
            query = query.Where(w => w.ApprovalStatusCode == status);
        }

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim();
            query = query.Where(w =>
                w.ApprovalNumber.Contains(s) ||
                w.QualificationTitle.Contains(s) ||
                (w.Organisation != null && (w.Organisation.CompanyName.Contains(s) || w.Organisation.SdlNumber.Contains(s))));
        }

        return await query
            .OrderByDescending(w => w.Id)
            .ToListAsync();
    }

    public async Task<WorkplaceApproval?> GetByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.WorkplaceApprovals
            .Include(w => w.Organisation)
            .Include(w => w.OrganisationSite)
            .Include(w => w.AssessorPerson)
            .Include(w => w.Mentors).ThenInclude(m => m.Person)
            .Include(w => w.ToolItems)
            .FirstOrDefaultAsync(w => w.Id == id);
    }

    public async Task<List<WorkplaceApproval>> GetByOrganisationIdAsync(int organisationId)
    {
        return await GetAllAsync(organisationId: organisationId);
    }

    public async Task<WorkplaceApproval> CreateAsync(WorkplaceApproval approval, string currentUsername = "SYSTEM")
    {
        if (approval.OrganisationId <= 0)
        {
            throw new ArgumentException("A valid OrganisationId is required.");
        }

        if (string.IsNullOrWhiteSpace(approval.QualificationTitle))
        {
            throw new ArgumentException("Qualification title is required for workplace approval.");
        }

        if (string.IsNullOrWhiteSpace(approval.ApprovalNumber))
        {
            approval.ApprovalNumber = $"WPA-{DateTime.UtcNow.Year}-{approval.OrganisationId}-{Guid.NewGuid().ToString("N")[..4].ToUpper()}";
        }

        if (string.IsNullOrWhiteSpace(approval.ApprovalStatusCode))
        {
            approval.ApprovalStatusCode = "Pending";
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        approval.CreatedAt = DateTime.UtcNow;
        approval.CreatedBy = currentUsername;

        db.WorkplaceApprovals.Add(approval);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "WorkplaceApproval", approval.Id, "Create", currentUsername, null, approval);
        await db.SaveChangesAsync();
        return approval;
    }

    public async Task<WorkplaceApproval> UpdateAsync(WorkplaceApproval approval, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.WorkplaceApprovals.FindAsync(approval.Id);
        if (existing == null) throw new KeyNotFoundException($"WorkplaceApproval with ID {approval.Id} not found.");

        var beforeState = new { existing.ApprovalNumber, existing.ApprovalStatusCode, existing.QualificationTitle, existing.InspectionDate };

        existing.QualificationTitle = approval.QualificationTitle;
        existing.SaqaQualificationId = approval.SaqaQualificationId;
        existing.OrganisationSiteId = approval.OrganisationSiteId;
        existing.AssessorPersonId = approval.AssessorPersonId;
        existing.InspectionDate = approval.InspectionDate;
        existing.ApprovalDate = approval.ApprovalDate;
        existing.ExpiryDate = approval.ExpiryDate;
        existing.ApprovalStatusCode = approval.ApprovalStatusCode;
        existing.Recommendations = approval.Recommendations;
        existing.IsActive = approval.IsActive;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceApproval", existing.Id, "Update", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();
        return existing;
    }

    public async Task<WorkplaceApproval> SaveAsync(WorkplaceApproval approval, string currentUsername = "SYSTEM")
    {
        if (approval.Id == 0) return await CreateAsync(approval, currentUsername);
        return await UpdateAsync(approval, currentUsername);
    }

    public async Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var item = await db.WorkplaceApprovals.Include(w => w.Mentors).Include(w => w.ToolItems).FirstOrDefaultAsync(w => w.Id == id);
        if (item == null) return false;

        var beforeState = new { item.Id, item.ApprovalNumber, item.QualificationTitle, item.OrganisationId };
        db.WorkplaceApprovals.Remove(item);
        _audit.LogAction(db, "WorkplaceApproval", id, "Delete", currentUsername, beforeState, null);
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<WorkplaceApprovalMentor> AddMentorAsync(WorkplaceApprovalMentor mentor, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        mentor.CreatedAt = DateTime.UtcNow;
        mentor.CreatedBy = currentUsername;

        db.WorkplaceApprovalMentors.Add(mentor);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "WorkplaceApprovalMentor", mentor.Id, "AddMentor", currentUsername, null, mentor);
        await db.SaveChangesAsync();
        return mentor;
    }

    public async Task<bool> RemoveMentorAsync(int mentorId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var mentor = await db.WorkplaceApprovalMentors.FindAsync(mentorId);
        if (mentor == null) return false;

        db.WorkplaceApprovalMentors.Remove(mentor);
        _audit.LogAction(db, "WorkplaceApprovalMentor", mentorId, "RemoveMentor", currentUsername, null, null);
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<WorkplaceApprovalToolList> AddToolItemAsync(WorkplaceApprovalToolList toolItem, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        toolItem.CreatedAt = DateTime.UtcNow;
        toolItem.CreatedBy = currentUsername;

        db.WorkplaceApprovalToolLists.Add(toolItem);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "WorkplaceApprovalToolList", toolItem.Id, "AddToolItem", currentUsername, null, toolItem);
        await db.SaveChangesAsync();
        return toolItem;
    }

    public async Task<bool> RemoveToolItemAsync(int toolItemId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var tool = await db.WorkplaceApprovalToolLists.FindAsync(toolItemId);
        if (tool == null) return false;

        db.WorkplaceApprovalToolLists.Remove(tool);
        _audit.LogAction(db, "WorkplaceApprovalToolList", toolItemId, "RemoveToolItem", currentUsername, null, null);
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<WorkplaceApproval> ApproveWorkplaceAsync(int id, string recommendations, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.WorkplaceApprovals.FindAsync(id);
        if (existing == null) throw new KeyNotFoundException($"WorkplaceApproval with ID {id} not found.");

        var beforeState = new { existing.ApprovalStatusCode, existing.ApprovalDate };
        existing.ApprovalStatusCode = "Approved";
        existing.ApprovalDate = DateTime.UtcNow;
        existing.ExpiryDate = DateTime.UtcNow.AddYears(3);
        existing.Recommendations = recommendations;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceApproval", existing.Id, "ApproveWorkplace", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();
        return existing;
    }

    public async Task<WorkplaceApproval> RejectWorkplaceAsync(int id, string reason, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.WorkplaceApprovals.FindAsync(id);
        if (existing == null) throw new KeyNotFoundException($"WorkplaceApproval with ID {id} not found.");

        var beforeState = new { existing.ApprovalStatusCode };
        existing.ApprovalStatusCode = "Rejected";
        existing.Recommendations = reason;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceApproval", existing.Id, "RejectWorkplace", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();
        return existing;
    }
}
