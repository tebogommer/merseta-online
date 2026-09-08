using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IVisitService
{
    Task<Visit> ScheduleVisitAsync(Visit v, string currentUser = "Admin");
    Task<List<Visit>> GetVisitsForOrganisationAsync(int organisationId);
}

public class VisitService : IVisitService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public VisitService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<Visit> ScheduleVisitAsync(Visit v, string currentUser = "Admin")
    {
        // Explicit Rule Validation: Contact Person is mandatory
        if (v.ContactPersonId <= 0)
        {
            throw new ArgumentException("A valid Contact Person (ContactPersonId) must be selected for every Employer visit activity.");
        }

        using var db = await _contextFactory.CreateDbContextAsync();

        var contactExists = await db.People.AnyAsync(p => p.Id == v.ContactPersonId);
        if (!contactExists)
        {
            throw new ArgumentException($"The selected Contact Person (ID: {v.ContactPersonId}) does not exist in the Person registry. A verified contact person must be selected.");
        }

        if (v.Id == 0)
        {
            db.Visits.Add(v);
            await db.SaveChangesAsync();
            _audit.LogAction(db, "Visit", v.Id, "ScheduleVisit", currentUser, null, v);
            await db.SaveChangesAsync();
        }
        else
        {
            var existing = await db.Visits.FindAsync(v.Id);
            if (existing != null)
            {
                var before = new { existing.Title, existing.VisitDate, existing.ContactPersonId };
                existing.Title = v.Title;
                existing.VisitDate = v.VisitDate;
                existing.ContactPersonId = v.ContactPersonId;
                existing.Location = v.Location;
                existing.OutcomeNotes = v.OutcomeNotes;

                _audit.LogAction(db, "Visit", v.Id, "UpdateVisit", currentUser, before, v);
                await db.SaveChangesAsync();
            }
        }

        return v;
    }

    public async Task<List<Visit>> GetVisitsForOrganisationAsync(int organisationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.Visits
            .Include(v => v.ContactPerson)
            .Where(v => v.OrganisationId == organisationId)
            .OrderByDescending(v => v.VisitDate)
            .ToListAsync();
    }
}
