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
}
