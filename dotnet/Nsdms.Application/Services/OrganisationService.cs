using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IOrganisationService
{
    // Organisation CRUD
    Task<List<Organisation>> GetAllAsync(string? search = null);
    Task<Organisation?> GetByIdAsync(int id);
    Task<Organisation> CreateAsync(Organisation org, string currentUsername = "Admin");
    Task<Organisation> UpdateAsync(Organisation org, string currentUsername = "Admin");
    Task<Organisation> SaveAsync(Organisation org, string currentUser = "Admin");
    Task<bool> DeleteAsync(int id, string currentUser = "Admin");

    // Contacts Management
    Task<List<OrganisationContact>> GetContactsAsync(int organisationId);
    Task<OrganisationContact> AddContactAsync(OrganisationContact contact, string currentUsername = "Admin");
    Task<OrganisationContact> AddContactAsync(int organisationId, int personId, string contactType = "General", bool isPrimary = false, string currentUsername = "Admin", string? designation = null);
    Task<bool> RemoveContactAsync(int organisationId, int contactId, string currentUsername = "Admin");
    Task<bool> RemoveContactAsync(int contactId, string currentUsername = "Admin");

    // Sites Management
    Task<List<OrganisationSite>> GetSitesAsync(int organisationId);
    Task<OrganisationSite> AddSiteAsync(OrganisationSite site, string currentUsername = "Admin");
    Task<OrganisationSite> UpdateSiteAsync(OrganisationSite site, string currentUsername = "Admin");
    Task<bool> RemoveSiteAsync(int siteId, string currentUsername = "Admin");
}

public class OrganisationService : IOrganisationService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public OrganisationService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<List<Organisation>> GetAllAsync(string? search = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.Organisations
            .Include(o => o.PrimaryContactPerson)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim();
            query = query.Where(o =>
                o.CompanyName.Contains(s) ||
                (o.TradingName != null && o.TradingName.Contains(s)) ||
                o.SdlNumber.Contains(s) ||
                (o.RegistrationNumber != null && o.RegistrationNumber.Contains(s)) ||
                (o.TaxNumber != null && o.TaxNumber.Contains(s)));
        }

        return await query
            .OrderByDescending(o => o.Id)
            .ToListAsync();
    }

    public async Task<Organisation?> GetByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.Organisations
            .Include(o => o.PrimaryContactPerson)
            .Include(o => o.Contacts)
                .ThenInclude(c => c.Person)
            .Include(o => o.Sites)
                .ThenInclude(s => s.PrimaryContactPerson)
            .Include(o => o.Visits)
                .ThenInclude(v => v.ContactPerson)
            .Include(o => o.WspSubmissions)
            .Include(o => o.GrantApplications)
            .FirstOrDefaultAsync(o => o.Id == id);
    }

    public async Task<Organisation> CreateAsync(Organisation org, string currentUsername = "Admin")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        org.CreatedAt = DateTime.UtcNow;
        org.CreatedBy = currentUsername;

        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "Organisation", org.Id, "Create", currentUsername, null, org);
        await db.SaveChangesAsync();
        return org;
    }

    public async Task<Organisation> UpdateAsync(Organisation org, string currentUsername = "Admin")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.Organisations.FindAsync(org.Id);
        if (existing == null)
        {
            throw new KeyNotFoundException($"Organisation with ID {org.Id} was not found.");
        }

        var before = new
        {
            existing.CompanyName,
            existing.TradingName,
            existing.SdlNumber,
            existing.RegistrationNumber,
            existing.TaxNumber,
            existing.CategoryCode,
            existing.StatusCode,
            existing.ProvinceCode,
            existing.SectorCode,
            existing.ChamberCode,
            existing.SicCode,
            existing.CompanySizeCode,
            existing.OrganisationTypeCode,
            existing.BankName,
            existing.BankBranchCode,
            existing.BankAccountNumber,
            existing.BankAccountType,
            existing.BankingDetailsVerified,
            existing.PrimaryContactPersonId,
            existing.IsActive
        };

        existing.CompanyName = org.CompanyName;
        existing.TradingName = org.TradingName;
        existing.SdlNumber = org.SdlNumber;
        existing.RegistrationNumber = org.RegistrationNumber;
        existing.TaxNumber = org.TaxNumber;
        existing.CategoryCode = org.CategoryCode;
        existing.StatusCode = org.StatusCode;
        existing.ProvinceCode = org.ProvinceCode;
        existing.SectorCode = org.SectorCode;
        existing.ChamberCode = org.ChamberCode;
        existing.SicCode = org.SicCode;
        existing.CompanySizeCode = org.CompanySizeCode;
        existing.OrganisationTypeCode = org.OrganisationTypeCode;
        existing.BankName = org.BankName;
        existing.BankBranchCode = org.BankBranchCode;
        existing.BankAccountNumber = org.BankAccountNumber;
        existing.BankAccountType = org.BankAccountType;
        existing.BankingDetailsVerified = org.BankingDetailsVerified;
        existing.PrimaryContactPersonId = org.PrimaryContactPersonId;
        existing.IsActive = org.IsActive;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "Organisation", org.Id, "Update", currentUsername, before, existing);
        await db.SaveChangesAsync();

        return existing;
    }

    public async Task<Organisation> SaveAsync(Organisation org, string currentUser = "Admin")
    {
        if (org.Id == 0)
        {
            return await CreateAsync(org, currentUser);
        }
        else
        {
            return await UpdateAsync(org, currentUser);
        }
    }

    public async Task<bool> DeleteAsync(int id, string currentUser = "Admin")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var org = await db.Organisations.FindAsync(id);
        if (org == null) return false;

        var before = new
        {
            org.Id,
            org.CompanyName,
            org.SdlNumber,
            org.IsActive
        };

        db.Organisations.Remove(org);
        _audit.LogAction(db, "Organisation", id, "Delete", currentUser, before, null);
        await db.SaveChangesAsync();
        return true;
    }

    // Contacts Management
    public async Task<List<OrganisationContact>> GetContactsAsync(int organisationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.OrganisationContacts
            .Include(c => c.Person)
            .Where(c => c.OrganisationId == organisationId)
            .OrderByDescending(c => c.IsPrimary)
            .ThenBy(c => c.Id)
            .ToListAsync();
    }

    public async Task<OrganisationContact> AddContactAsync(OrganisationContact contact, string currentUsername = "Admin")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        contact.CreatedAt = DateTime.UtcNow;
        contact.CreatedBy = currentUsername;

        if (contact.IsPrimary)
        {
            // Unset previous primary contacts for this organisation
            var existingPrimaries = await db.OrganisationContacts
                .Where(c => c.OrganisationId == contact.OrganisationId && c.IsPrimary)
                .ToListAsync();

            foreach (var existingPrimary in existingPrimaries)
            {
                existingPrimary.IsPrimary = false;
            }
        }

        db.OrganisationContacts.Add(contact);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "OrganisationContact", contact.Id, "AddContact", currentUsername, null, contact);
        await db.SaveChangesAsync();
        return contact;
    }

    public async Task<OrganisationContact> AddContactAsync(int organisationId, int personId, string contactType = "General", bool isPrimary = false, string currentUsername = "Admin", string? designation = null)
    {
        var contact = new OrganisationContact
        {
            OrganisationId = organisationId,
            PersonId = personId,
            ContactTypeCode = contactType,
            Designation = designation,
            IsPrimary = isPrimary,
            IsActive = true
        };

        return await AddContactAsync(contact, currentUsername);
    }

    public async Task<bool> RemoveContactAsync(int organisationId, int contactId, string currentUsername = "Admin")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var contact = await db.OrganisationContacts
            .FirstOrDefaultAsync(c => c.Id == contactId && c.OrganisationId == organisationId);

        if (contact == null) return false;

        var before = new
        {
            contact.Id,
            contact.OrganisationId,
            contact.PersonId,
            contact.ContactTypeCode,
            contact.IsPrimary
        };

        db.OrganisationContacts.Remove(contact);
        _audit.LogAction(db, "OrganisationContact", contactId, "RemoveContact", currentUsername, before, null);
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<bool> RemoveContactAsync(int contactId, string currentUsername = "Admin")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var contact = await db.OrganisationContacts.FindAsync(contactId);
        if (contact == null) return false;

        return await RemoveContactAsync(contact.OrganisationId, contactId, currentUsername);
    }

    // Sites Management
    public async Task<List<OrganisationSite>> GetSitesAsync(int organisationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.OrganisationSites
            .Include(s => s.PrimaryContactPerson)
            .Where(s => s.OrganisationId == organisationId)
            .OrderByDescending(s => s.IsHeadOffice)
            .ThenBy(s => s.SiteName)
            .ToListAsync();
    }

    public async Task<OrganisationSite> AddSiteAsync(OrganisationSite site, string currentUsername = "Admin")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        site.CreatedAt = DateTime.UtcNow;
        site.CreatedBy = currentUsername;

        if (site.IsHeadOffice)
        {
            var existingHeadOffices = await db.OrganisationSites
                .Where(s => s.OrganisationId == site.OrganisationId && s.IsHeadOffice)
                .ToListAsync();

            foreach (var ho in existingHeadOffices)
            {
                ho.IsHeadOffice = false;
            }
        }

        db.OrganisationSites.Add(site);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "OrganisationSite", site.Id, "AddSite", currentUsername, null, site);
        await db.SaveChangesAsync();
        return site;
    }

    public async Task<OrganisationSite> UpdateSiteAsync(OrganisationSite site, string currentUsername = "Admin")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.OrganisationSites.FindAsync(site.Id);
        if (existing == null)
        {
            throw new KeyNotFoundException($"OrganisationSite with ID {site.Id} was not found.");
        }

        var before = new
        {
            existing.SiteName,
            existing.SiteCode,
            existing.PhysicalAddress,
            existing.PostalAddress,
            existing.ProvinceCode,
            existing.City,
            existing.PostalCode,
            existing.PrimaryContactPersonId,
            existing.IsHeadOffice,
            existing.IsActive
        };

        if (site.IsHeadOffice && !existing.IsHeadOffice)
        {
            var otherHeadOffices = await db.OrganisationSites
                .Where(s => s.OrganisationId == existing.OrganisationId && s.Id != existing.Id && s.IsHeadOffice)
                .ToListAsync();

            foreach (var ho in otherHeadOffices)
            {
                ho.IsHeadOffice = false;
            }
        }

        existing.SiteName = site.SiteName;
        existing.SiteCode = site.SiteCode;
        existing.PhysicalAddress = site.PhysicalAddress;
        existing.PostalAddress = site.PostalAddress;
        existing.ProvinceCode = site.ProvinceCode;
        existing.City = site.City;
        existing.PostalCode = site.PostalCode;
        existing.PrimaryContactPersonId = site.PrimaryContactPersonId;
        existing.IsHeadOffice = site.IsHeadOffice;
        existing.IsActive = site.IsActive;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "OrganisationSite", existing.Id, "UpdateSite", currentUsername, before, existing);
        await db.SaveChangesAsync();

        return existing;
    }

    public async Task<bool> RemoveSiteAsync(int siteId, string currentUsername = "Admin")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var site = await db.OrganisationSites.FindAsync(siteId);
        if (site == null) return false;

        var before = new
        {
            site.Id,
            site.OrganisationId,
            site.SiteName,
            site.SiteCode,
            site.IsHeadOffice,
            site.IsActive
        };

        db.OrganisationSites.Remove(site);
        _audit.LogAction(db, "OrganisationSite", siteId, "RemoveSite", currentUsername, before, null);
        await db.SaveChangesAsync();
        return true;
    }
}
