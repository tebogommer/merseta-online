using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class SdfAppointmentService : ISdfAppointmentService
{
    private readonly INsdmsDbContextFactory _factory;
    private readonly AuditService _audit;

    public SdfAppointmentService(INsdmsDbContextFactory factory, AuditService audit)
    {
        _factory = factory;
        _audit = audit;
    }

    public async Task<List<SdfCompany>> GetSdfAppointmentsAsync()
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.SdfCompanies
            .Include(s => s.Organisation)
            .Include(s => s.Person)
            .OrderByDescending(s => s.CreatedAt)
            .ToListAsync();
    }

    public async Task<SdfCompany?> GetSdfAppointmentByIdAsync(int id)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.SdfCompanies
            .Include(s => s.Organisation)
            .Include(s => s.Person)
            .FirstOrDefaultAsync(s => s.Id == id);
    }

    public async Task<SdfCompany> SubmitSdfAppointmentAsync(int organisationId, int personId, string sdfTypeCode, DateTime startDate, string? docPath, bool allowWsp, bool allowDg, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var entity = new SdfCompany
        {
            OrganisationId = organisationId,
            PersonId = personId,
            SdfTypeCode = sdfTypeCode,
            AppointmentStartDate = startDate,
            AppointmentLetterDocumentPath = docPath,
            SignedAppointmentLetterReceived = !string.IsNullOrEmpty(docPath),
            SignedAcceptanceDeclarationReceived = true,
            AllowWspSubmission = allowWsp,
            AllowDgApplication = allowDg,
            SdfStatusCode = "PendingApproval",
            CreatedBy = currentUsername,
            CreatedAt = DateTime.UtcNow
        };

        db.SdfCompanies.Add(entity);
        await db.SaveChangesAsync();

        var history = new SdfAppointmentHistory
        {
            SdfCompanyId = entity.Id,
            PreviousStatusCode = "None",
            NewStatusCode = "PendingApproval",
            ChangeReason = "Initial SDF appointment submission.",
            ChangedByUserId = currentUsername,
            ChangedAt = DateTime.UtcNow
        };
        db.SdfAppointmentHistories.Add(history);
        await db.SaveChangesAsync();

        await _audit.LogAsync("SdfCompany", entity.Id, "SubmitSdfAppointment", currentUsername, new { organisationId, personId, sdfTypeCode });
        return entity;
    }

    public async Task<SdfCompany> ApproveSdfAppointmentAsync(int id, string comments, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var entity = await db.SdfCompanies.FindAsync(id) ?? throw new InvalidOperationException($"SDF appointment #{id} not found.");

        entity.SdfStatusCode = "Approved";
        entity.ApprovedByUserId = currentUsername;
        entity.ApprovalDate = DateTime.UtcNow;
        entity.ApprovalComments = comments;
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = currentUsername;

        // If primary SDF, supersede any prior active primary appointments for this organisation
        if (string.Equals(entity.SdfTypeCode, "PRIMARY", StringComparison.OrdinalIgnoreCase) || 
            string.Equals(entity.SdfTypeCode, "Primary", StringComparison.OrdinalIgnoreCase))
        {
            var priorPrimaries = await db.SdfCompanies
                .Where(s => s.OrganisationId == entity.OrganisationId && s.Id != id && s.SdfStatusCode == "Approved" && (s.SdfTypeCode == "PRIMARY" || s.SdfTypeCode == "Primary"))
                .ToListAsync();

            foreach (var prior in priorPrimaries)
            {
                prior.SdfStatusCode = "Superseded";
                prior.AppointmentEndDate = DateTime.UtcNow;
                prior.ModifiedAt = DateTime.UtcNow;
                prior.ModifiedBy = currentUsername;

                db.SdfAppointmentHistories.Add(new SdfAppointmentHistory
                {
                    SdfCompanyId = prior.Id,
                    PreviousStatusCode = "Approved",
                    NewStatusCode = "Superseded",
                    ChangeReason = $"Superseded by newly approved Primary SDF appointment #{id}.",
                    ChangedByUserId = currentUsername,
                    ChangedAt = DateTime.UtcNow
                });
            }
        }

        var history = new SdfAppointmentHistory
        {
            SdfCompanyId = entity.Id,
            PreviousStatusCode = "PendingApproval",
            NewStatusCode = "Approved",
            ChangeReason = comments,
            ChangedByUserId = currentUsername,
            ChangedAt = DateTime.UtcNow
        };
        db.SdfAppointmentHistories.Add(history);
        await db.SaveChangesAsync();

        await _audit.LogAsync("SdfCompany", entity.Id, "ApproveSdfAppointment", currentUsername, new { entity.SdfStatusCode, comments });
        return entity;
    }

    public async Task<SdfCompany> TerminateSdfAppointmentAsync(int id, string reason, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var entity = await db.SdfCompanies.FindAsync(id) ?? throw new InvalidOperationException($"SDF appointment #{id} not found.");

        var prev = entity.SdfStatusCode;
        entity.SdfStatusCode = "Terminated";
        entity.AppointmentEndDate = DateTime.UtcNow;
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = currentUsername;

        var history = new SdfAppointmentHistory
        {
            SdfCompanyId = entity.Id,
            PreviousStatusCode = prev,
            NewStatusCode = "Terminated",
            ChangeReason = reason,
            ChangedByUserId = currentUsername,
            ChangedAt = DateTime.UtcNow
        };
        db.SdfAppointmentHistories.Add(history);
        await db.SaveChangesAsync();

        await _audit.LogAsync("SdfCompany", entity.Id, "TerminateSdfAppointment", currentUsername, new { entity.SdfStatusCode, reason });
        return entity;
    }

    public async Task<SdfCompany> UpdateSdfAppointmentAsync(int id, string sdfTypeCode, DateTime startDate, DateTime? endDate, bool allowWsp, bool allowDg, string? docPath, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var entity = await db.SdfCompanies.FindAsync(id) ?? throw new InvalidOperationException($"SDF appointment #{id} not found.");

        entity.SdfTypeCode = sdfTypeCode;
        entity.AppointmentStartDate = startDate;
        entity.AppointmentEndDate = endDate;
        entity.AllowWspSubmission = allowWsp;
        entity.AllowDgApplication = allowDg;
        if (!string.IsNullOrEmpty(docPath))
        {
            entity.AppointmentLetterDocumentPath = docPath;
            entity.SignedAppointmentLetterReceived = true;
        }
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();
        await _audit.LogAsync("SdfCompany", entity.Id, "UpdateSdfAppointment", currentUsername, new { sdfTypeCode, startDate, endDate, allowWsp, allowDg });
        return entity;
    }

    public async Task<bool> DeleteSdfAppointmentAsync(int id, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var entity = await db.SdfCompanies.FindAsync(id);
        if (entity == null) return false;

        entity.SdfStatusCode = "Deactivated";
        entity.AppointmentEndDate = DateTime.UtcNow;
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();
        await _audit.LogAsync("SdfCompany", entity.Id, "DeleteSdfAppointment", currentUsername, new { entity.Id, StatusCode = "Deactivated" });
        return true;
    }
}
