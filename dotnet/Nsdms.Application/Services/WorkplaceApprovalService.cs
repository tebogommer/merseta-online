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
    private readonly INsdmsDbContext _db;
    private readonly IAuditService _audit;

    public WorkplaceApprovalService(INsdmsDbContext db, IAuditService audit)
    {
        _db = db;
        _audit = audit;
    }

    public async Task<List<WorkplaceApproval>> GetAllAsync(string? search = null, string? status = null, int? organisationId = null)
    {
        var query = _db.WorkplaceApprovals
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
        return await _db.WorkplaceApprovals
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

        approval.CreatedAt = DateTime.UtcNow;
        approval.CreatedBy = currentUsername;

        _db.WorkplaceApprovals.Add(approval);
        await _db.SaveChangesAsync();

        await _audit.LogActionAsync("WorkplaceApproval", approval.Id, "Create", currentUsername, null, approval);
        return approval;
    }

    public async Task<WorkplaceApproval> UpdateAsync(WorkplaceApproval approval, string currentUsername = "SYSTEM")
    {
        var existing = await _db.WorkplaceApprovals.FindAsync(approval.Id);
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

        await _db.SaveChangesAsync();
        await _audit.LogActionAsync("WorkplaceApproval", existing.Id, "Update", currentUsername, beforeState, existing);
        return existing;
    }

    public async Task<WorkplaceApproval> SaveAsync(WorkplaceApproval approval, string currentUsername = "SYSTEM")
    {
        if (approval.Id == 0) return await CreateAsync(approval, currentUsername);
        return await UpdateAsync(approval, currentUsername);
    }

    public async Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM")
    {
        var item = await _db.WorkplaceApprovals.Include(w => w.Mentors).Include(w => w.ToolItems).FirstOrDefaultAsync(w => w.Id == id);
        if (item == null) return false;

        var beforeState = new { item.Id, item.ApprovalNumber, item.QualificationTitle, item.OrganisationId };
        _db.WorkplaceApprovals.Remove(item);
        await _db.SaveChangesAsync();

        await _audit.LogActionAsync("WorkplaceApproval", id, "Delete", currentUsername, beforeState, null);
        return true;
    }

    public async Task<WorkplaceApprovalMentor> AddMentorAsync(WorkplaceApprovalMentor mentor, string currentUsername = "SYSTEM")
    {
        mentor.CreatedAt = DateTime.UtcNow;
        mentor.CreatedBy = currentUsername;

        _db.WorkplaceApprovalMentors.Add(mentor);
        await _db.SaveChangesAsync();

        await _audit.LogActionAsync("WorkplaceApprovalMentor", mentor.Id, "AddMentor", currentUsername, null, mentor);
        return mentor;
    }

    public async Task<bool> RemoveMentorAsync(int mentorId, string currentUsername = "SYSTEM")
    {
        var mentor = await _db.WorkplaceApprovalMentors.FindAsync(mentorId);
        if (mentor == null) return false;

        _db.WorkplaceApprovalMentors.Remove(mentor);
        await _db.SaveChangesAsync();

        await _audit.LogActionAsync("WorkplaceApprovalMentor", mentorId, "RemoveMentor", currentUsername, null, null);
        return true;
    }

    public async Task<WorkplaceApprovalToolList> AddToolItemAsync(WorkplaceApprovalToolList toolItem, string currentUsername = "SYSTEM")
    {
        toolItem.CreatedAt = DateTime.UtcNow;
        toolItem.CreatedBy = currentUsername;

        _db.WorkplaceApprovalToolLists.Add(toolItem);
        await _db.SaveChangesAsync();

        await _audit.LogActionAsync("WorkplaceApprovalToolList", toolItem.Id, "AddToolItem", currentUsername, null, toolItem);
        return toolItem;
    }

    public async Task<bool> RemoveToolItemAsync(int toolItemId, string currentUsername = "SYSTEM")
    {
        var tool = await _db.WorkplaceApprovalToolLists.FindAsync(toolItemId);
        if (tool == null) return false;

        _db.WorkplaceApprovalToolLists.Remove(tool);
        await _db.SaveChangesAsync();

        await _audit.LogActionAsync("WorkplaceApprovalToolList", toolItemId, "RemoveToolItem", currentUsername, null, null);
        return true;
    }

    public async Task<WorkplaceApproval> ApproveWorkplaceAsync(int id, string recommendations, string currentUsername = "SYSTEM")
    {
        var existing = await _db.WorkplaceApprovals.FindAsync(id);
        if (existing == null) throw new KeyNotFoundException($"WorkplaceApproval with ID {id} not found.");

        var beforeState = new { existing.ApprovalStatusCode, existing.ApprovalDate };
        existing.ApprovalStatusCode = "Approved";
        existing.ApprovalDate = DateTime.UtcNow;
        existing.ExpiryDate = DateTime.UtcNow.AddYears(3);
        existing.Recommendations = recommendations;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        await _db.SaveChangesAsync();
        await _audit.LogActionAsync("WorkplaceApproval", existing.Id, "ApproveWorkplace", currentUsername, beforeState, existing);
        return existing;
    }

    public async Task<WorkplaceApproval> RejectWorkplaceAsync(int id, string reason, string currentUsername = "SYSTEM")
    {
        var existing = await _db.WorkplaceApprovals.FindAsync(id);
        if (existing == null) throw new KeyNotFoundException($"WorkplaceApproval with ID {id} not found.");

        var beforeState = new { existing.ApprovalStatusCode };
        existing.ApprovalStatusCode = "Rejected";
        existing.Recommendations = reason;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        await _db.SaveChangesAsync();
        await _audit.LogActionAsync("WorkplaceApproval", existing.Id, "RejectWorkplace", currentUsername, beforeState, existing);
        return existing;
    }
}
