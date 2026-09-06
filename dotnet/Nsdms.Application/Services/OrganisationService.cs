using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Models;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IOrganisationService
{
    // Organisation CRUD
    Task<List<Organisation>> GetAllAsync(string? search = null);
    Task<PagedResult<OrganisationListDto>> GetPagedAsync(PaginationQuery query, CancellationToken cancellationToken = default);
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

    // Relational Child Queries (360-Degree Views)
    Task<List<OrganisationLearnerDto>> GetLinkedLearnersAsync(int organisationId);
    Task<List<OrganisationGrantSummaryDto>> GetGrantMoasAndApplicationsAsync(int organisationId);
    Task<List<OrganisationWpaDto>> GetWorkplaceApprovalsAsync(int organisationId);
    Task<List<OrganisationCommitteeMemberDto>> GetTrainingCommitteeMembersAsync(int organisationId);
    Task<OrganisationCommitteeMemberDto> AddTrainingCommitteeMemberAsync(int organisationId, int personId, string roleCode, string constituency, string currentUsername = "Admin");
    Task<bool> RemoveTrainingCommitteeMemberAsync(int memberId, string currentUsername = "Admin");
    Task<List<OrganisationLevyReconDto>> GetLevyReconHistoryAsync(int organisationId);
    Task<string?> ResolveChamberFromSicCodeAsync(string sicCode);
    Task<Organisation> SetManualChamberOverrideAsync(int organisationId, string newChamberCode, string overrideReason, string approvedBy);
    Task<string> GenerateNonLevyNumberAsync(CancellationToken cancellationToken = default);
    Task<ChamberDerivationResult> DeriveChamberAndVendorClassAsync(string? sicCode, string? organisationTypeCode = null, string? manualChamber = null, bool isManual = false);
    Task<ChamberValidationResult> ValidateChamberGovernanceAsync(int organisationId);
}

public class OrganisationService : IOrganisationService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly ITenantProvider? _tenantProvider;
    private readonly INonLevyNumberGeneratorService _nonLevyGenerator;
    private readonly IChamberDerivationService _chamberService;

    public OrganisationService(
        INsdmsDbContextFactory contextFactory, 
        IAuditService audit,
        ITenantProvider? tenantProvider = null,
        INonLevyNumberGeneratorService? nonLevyGenerator = null,
        IChamberDerivationService? chamberService = null)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _tenantProvider = tenantProvider;
        _nonLevyGenerator = nonLevyGenerator!;
        _chamberService = chamberService!;
    }

    public OrganisationService(INsdmsDbContextFactory contextFactory, IAuditService audit)
        : this(contextFactory, audit, null, null, null)
    {
    }

    public OrganisationService(
        INsdmsDbContextFactory contextFactory, 
        IAuditService audit,
        INonLevyNumberGeneratorService? nonLevyGenerator,
        IChamberDerivationService? chamberService)
        : this(contextFactory, audit, null, nonLevyGenerator, chamberService)
    {
    }

    public async Task<PagedResult<OrganisationListDto>> GetPagedAsync(PaginationQuery query, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var baseQuery = db.Organisations
            .Include(o => o.PrimaryContactPerson)
            .AsNoTracking();

        if (_tenantProvider != null && !_tenantProvider.IsAdmin)
        {
            if (_tenantProvider.CurrentOrganisationId != null)
            {
                baseQuery = baseQuery.Where(o => o.Id == _tenantProvider.CurrentOrganisationId.Value);
            }
            else
            {
                baseQuery = baseQuery.Where(o => false);
            }
        }

        if (query.FilterParams.TryGetValue("status", out var statusVal) && !string.IsNullOrWhiteSpace(statusVal) && statusVal != "All")
        {
            if (statusVal.Equals("Active", StringComparison.OrdinalIgnoreCase))
                baseQuery = baseQuery.Where(o => o.IsActive);
            else if (statusVal.Equals("Inactive", StringComparison.OrdinalIgnoreCase))
                baseQuery = baseQuery.Where(o => !o.IsActive);
        }

        if (!string.IsNullOrWhiteSpace(query.SearchText))
        {
            var s = query.SearchText.Trim();
            baseQuery = baseQuery.Where(o =>
                o.CompanyName.Contains(s) ||
                (o.TradingName != null && o.TradingName.Contains(s)) ||
                o.SdlNumber.Contains(s) ||
                (o.MainSdlNumber != null && o.MainSdlNumber.Contains(s)) ||
                (o.RegistrationNumber != null && o.RegistrationNumber.Contains(s)) ||
                (o.TaxNumber != null && o.TaxNumber.Contains(s)));
        }

        var totalCount = await baseQuery.CountAsync(cancellationToken);

        var rawItems = await baseQuery
            .OrderByDescending(o => o.Id)
            .Skip((query.PageIndex - 1) * query.PageSize)
            .Take(query.PageSize)
            .ToListAsync(cancellationToken);

        var items = rawItems.Select(o => new OrganisationListDto(
            o.Id,
            o.SdlNumber,
            o.CompanyName,
            o.TradingName,
            o.RegistrationNumber,
            o.TaxNumber,
            o.ChamberCode,
            o.SectorCode,
            o.IsActive,
            o.PrimaryContactPerson != null ? $"{o.PrimaryContactPerson.FirstName} {o.PrimaryContactPerson.LastName}".Trim() : null,
            o.PrimaryContactPerson?.EmailAddress
        )).ToList();

        return new PagedResult<OrganisationListDto>(items, totalCount, query.PageIndex, query.PageSize);
    }

    public async Task<List<Organisation>> GetAllAsync(string? search = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.Organisations
            .AsNoTracking()
            .Include(o => o.PrimaryContactPerson)
            .AsQueryable();

        if (_tenantProvider != null && !_tenantProvider.IsAdmin)
        {
            if (_tenantProvider.CurrentOrganisationId != null)
            {
                query = query.Where(o => o.Id == _tenantProvider.CurrentOrganisationId.Value);
            }
            else
            {
                query = query.Where(o => false);
            }
        }

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim();
            query = query.Where(o =>
                o.CompanyName.Contains(s) ||
                (o.TradingName != null && o.TradingName.Contains(s)) ||
                o.SdlNumber.Contains(s) ||
                (o.MainSdlNumber != null && o.MainSdlNumber.Contains(s)) ||
                (o.RegistrationNumber != null && o.RegistrationNumber.Contains(s)) ||
                (o.TaxNumber != null && o.TaxNumber.Contains(s)));
        }

        return await query
            .OrderByDescending(o => o.Id)
            .ToListAsync();
    }

    public async Task<Organisation?> GetByIdAsync(int id)
    {
        if (_tenantProvider != null && !_tenantProvider.IsAdmin)
        {
            if (_tenantProvider.CurrentOrganisationId == null || id != _tenantProvider.CurrentOrganisationId.Value)
            {
                return null;
            }
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.Organisations
            .AsNoTracking()
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

        // 1. Auto-assign statutory N-Number for non-levy organisations, TVETs, NGOs, and exempt SMEs
        bool isNonLevy = string.IsNullOrWhiteSpace(org.SdlNumber) || 
                         org.LevyCategoryCode == "NON_LEVY_PAYING" ||
                         org.OrganisationTypeCode?.Contains("TVET", StringComparison.OrdinalIgnoreCase) == true ||
                         org.OrganisationTypeCode?.Contains("NGO", StringComparison.OrdinalIgnoreCase) == true ||
                         org.OrganisationTypeCode?.Contains("NPO", StringComparison.OrdinalIgnoreCase) == true ||
                         org.OrganisationTypeCode?.Contains("UNIVERSITY", StringComparison.OrdinalIgnoreCase) == true;

        if (isNonLevy && (string.IsNullOrWhiteSpace(org.SdlNumber) || org.SdlNumber == "N/A" || org.SdlNumber == "PENDING"))
        {
            org.SdlNumber = _nonLevyGenerator != null
                ? await _nonLevyGenerator.GenerateNextNonLevyNumberAsync()
                : $"N{DateTime.UtcNow.Ticks % 1000000000:D9}";
            org.LevyCategoryCode = "NON_LEVY_PAYING";
        }

        // 2. Chamber & Dynamics GP Vendor Class Derivation & Governance
        if (_chamberService != null)
        {
            var derivation = await _chamberService.DeriveChamberAndVendorClassAsync(
                org.SicCode,
                org.OrganisationTypeCode,
                org.ChamberCode,
                org.IsManualChamberOverride
            );

            if (derivation.IsSuccess)
            {
                org.ChamberCode = derivation.ChamberCode;
                org.GpVendorClass = derivation.GpVendorClass;
                org.HasMissingChamberMapping = false;
            }
            else
            {
                org.ChamberCode = null;
                org.GpVendorClass = null;
                org.HasMissingChamberMapping = true;
            }
        }
        else if (!org.IsManualChamberOverride && !string.IsNullOrWhiteSpace(org.SicCode))
        {
            var matchedSic = await db.SicCodeTypes.AsNoTracking().FirstOrDefaultAsync(s => s.Code == org.SicCode.Trim());
            if (matchedSic != null && !string.IsNullOrWhiteSpace(matchedSic.ChamberCode))
            {
                org.ChamberCode = matchedSic.ChamberCode;
                org.HasMissingChamberMapping = false;
                org.GpVendorClass = matchedSic.ChamberCode switch
                {
                    "AUTO" => "AUTO",
                    "METAL" => "METAL",
                    "MOTOR" => "MOTOR",
                    "NEW_TYRE" or "NEW TYRE" => "NEW TYRE",
                    "PLASTICS" => "PLASTICS",
                    _ => "SETA"
                };
            }
            else
            {
                org.HasMissingChamberMapping = true;
            }
        }

        if (org.IsManualChamberOverride)
        {
            org.ChamberOverrideDate ??= DateTime.UtcNow;
            org.ChamberOverrideApprovedBy ??= currentUsername;
        }

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
            existing.MainSdlNumber,
            existing.SetaId,
            existing.RegistrationNumber,
            existing.TaxNumber,
            existing.LevyCategoryCode,
            existing.OrganisationStatusCode,
            existing.ProvinceCode,
            existing.CountryCode,
            existing.SectorCode,
            existing.ChamberCode,
            existing.SicCode,
            existing.IsManualChamberOverride,
            existing.ChamberOverrideReason,
            existing.ChamberOverrideDate,
            existing.ChamberOverrideApprovedBy,
            existing.CompanySizeCode,
            existing.OrganisationTypeCode,
            existing.PhoneNumber,
            existing.FaxNumber,
            existing.WebsiteUrl,
            existing.PhysicalAddress,
            existing.PhysicalAddressPostalCode,
            existing.PostalAddress,
            existing.PostalAddressPostalCode,
            existing.BankName,
            existing.BankBranchCode,
            existing.BankAccountNumber,
            existing.BankAccountType,
            existing.BankingDetailsVerified,
            existing.PrimaryContactPersonId,
            existing.IsActive
        };

        // 1. Auto-assign statutory N-Number if missing on non-levy organisations
        bool isNonLevy = string.IsNullOrWhiteSpace(org.SdlNumber) || 
                         org.LevyCategoryCode == "NON_LEVY_PAYING" ||
                         org.OrganisationTypeCode?.Contains("TVET", StringComparison.OrdinalIgnoreCase) == true ||
                         org.OrganisationTypeCode?.Contains("NGO", StringComparison.OrdinalIgnoreCase) == true ||
                         org.OrganisationTypeCode?.Contains("NPO", StringComparison.OrdinalIgnoreCase) == true ||
                         org.OrganisationTypeCode?.Contains("UNIVERSITY", StringComparison.OrdinalIgnoreCase) == true;

        if (isNonLevy && (string.IsNullOrWhiteSpace(org.SdlNumber) || org.SdlNumber == "N/A" || org.SdlNumber == "PENDING"))
        {
            org.SdlNumber = _nonLevyGenerator != null
                ? await _nonLevyGenerator.GenerateNextNonLevyNumberAsync()
                : $"N{DateTime.UtcNow.Ticks % 1000000000:D9}";
            org.LevyCategoryCode = "NON_LEVY_PAYING";
        }

        // 2. Chamber & Dynamics GP Vendor Class Derivation & Governance
        if (_chamberService != null)
        {
            var derivation = await _chamberService.DeriveChamberAndVendorClassAsync(
                org.SicCode,
                org.OrganisationTypeCode,
                org.ChamberCode,
                org.IsManualChamberOverride
            );

            if (derivation.IsSuccess)
            {
                org.ChamberCode = derivation.ChamberCode;
                org.GpVendorClass = derivation.GpVendorClass;
                org.HasMissingChamberMapping = false;
            }
            else
            {
                org.ChamberCode = null;
                org.GpVendorClass = null;
                org.HasMissingChamberMapping = true;
            }
        }
        else if (!org.IsManualChamberOverride && !string.IsNullOrWhiteSpace(org.SicCode))
        {
            var matchedSic = await db.SicCodeTypes.AsNoTracking().FirstOrDefaultAsync(s => s.Code == org.SicCode.Trim());
            if (matchedSic != null && !string.IsNullOrWhiteSpace(matchedSic.ChamberCode))
            {
                org.ChamberCode = matchedSic.ChamberCode;
                org.HasMissingChamberMapping = false;
                org.GpVendorClass = matchedSic.ChamberCode switch
                {
                    "AUTO" => "AUTO",
                    "METAL" => "METAL",
                    "MOTOR" => "MOTOR",
                    "NEW_TYRE" or "NEW TYRE" => "NEW TYRE",
                    "PLASTICS" => "PLASTICS",
                    _ => "SETA"
                };
            }
            else
            {
                org.HasMissingChamberMapping = true;
            }
        }

        existing.CompanyName = org.CompanyName;
        existing.TradingName = org.TradingName;
        existing.SdlNumber = org.SdlNumber;
        existing.MainSdlNumber = org.MainSdlNumber;
        existing.SetaId = org.SetaId;
        existing.RegistrationNumber = org.RegistrationNumber;
        existing.TaxNumber = org.TaxNumber;
        existing.LevyCategoryCode = org.LevyCategoryCode;
        existing.OrganisationStatusCode = org.OrganisationStatusCode;
        existing.ProvinceCode = org.ProvinceCode;
        existing.CountryCode = org.CountryCode;
        existing.SectorCode = org.SectorCode;
        existing.ChamberCode = org.ChamberCode;
        existing.GpVendorClass = org.GpVendorClass;
        existing.HasMissingChamberMapping = org.HasMissingChamberMapping;
        existing.SicCode = org.SicCode;
        existing.IsManualChamberOverride = org.IsManualChamberOverride;
        existing.ChamberOverrideReason = org.ChamberOverrideReason;
        existing.ChamberOverrideDate = org.IsManualChamberOverride ? (org.ChamberOverrideDate ?? DateTime.UtcNow) : null;
        existing.ChamberOverrideApprovedBy = org.IsManualChamberOverride ? (org.ChamberOverrideApprovedBy ?? currentUsername) : null;
        existing.CompanySizeCode = org.CompanySizeCode;
        existing.OrganisationTypeCode = org.OrganisationTypeCode;
        existing.PhoneNumber = org.PhoneNumber;
        existing.FaxNumber = org.FaxNumber;
        existing.WebsiteUrl = org.WebsiteUrl;
        existing.PhysicalAddress = org.PhysicalAddress;
        existing.PhysicalAddressPostalCode = org.PhysicalAddressPostalCode;
        existing.PostalAddress = org.PostalAddress;
        existing.PostalAddressPostalCode = org.PostalAddressPostalCode;
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

    public async Task<string> GenerateNonLevyNumberAsync(CancellationToken cancellationToken = default)
    {
        return _nonLevyGenerator != null
            ? await _nonLevyGenerator.GenerateNextNonLevyNumberAsync(cancellationToken)
            : $"N{DateTime.UtcNow.Ticks % 1000000000:D9}";
    }

    public async Task<ChamberDerivationResult> DeriveChamberAndVendorClassAsync(
        string? sicCode, string? organisationTypeCode = null, string? manualChamber = null, bool isManual = false)
    {
        if (_chamberService != null)
        {
            return await _chamberService.DeriveChamberAndVendorClassAsync(sicCode, organisationTypeCode, manualChamber, isManual);
        }
        return new ChamberDerivationResult(false, null, null, null, "None", "Chamber derivation service not available");
    }

    public async Task<ChamberValidationResult> ValidateChamberGovernanceAsync(int organisationId)
    {
        if (_chamberService != null)
        {
            return await _chamberService.ValidateChamberGovernanceAsync(organisationId);
        }
        return new ChamberValidationResult(true, null, null, true, true, true, true, null);
    }

    public async Task<string?> ResolveChamberFromSicCodeAsync(string sicCode)
    {
        if (string.IsNullOrWhiteSpace(sicCode)) return null;
        if (_chamberService != null)
        {
            var derivation = await _chamberService.DeriveChamberAndVendorClassAsync(sicCode);
            return derivation.IsSuccess ? derivation.ChamberCode : null;
        }
        using var db = await _contextFactory.CreateDbContextAsync();
        var matchedSic = await db.SicCodeTypes.AsNoTracking().FirstOrDefaultAsync(s => s.Code == sicCode.Trim() && s.Active);
        return matchedSic?.ChamberCode;
    }

    public async Task<Organisation> SetManualChamberOverrideAsync(int organisationId, string newChamberCode, string overrideReason, string approvedBy)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var org = await db.Organisations.FindAsync(organisationId);
        if (org == null)
        {
            throw new KeyNotFoundException($"Organisation with ID {organisationId} was not found.");
        }

        var before = new
        {
            org.ChamberCode,
            org.GpVendorClass,
            org.HasMissingChamberMapping,
            org.IsManualChamberOverride,
            org.ChamberOverrideReason,
            org.ChamberOverrideDate,
            org.ChamberOverrideApprovedBy
        };

        org.ChamberCode = newChamberCode;
        org.GpVendorClass = _chamberService != null ? _chamberService.MapChamberToGpVendorClass(newChamberCode) : "SETA";
        org.HasMissingChamberMapping = false;
        org.IsManualChamberOverride = true;
        org.ChamberOverrideReason = overrideReason;
        org.ChamberOverrideDate = DateTime.UtcNow;
        org.ChamberOverrideApprovedBy = approvedBy;
        org.ModifiedAt = DateTime.UtcNow;
        org.ModifiedBy = approvedBy;

        _audit.LogAction(db, "Organisation", org.Id, "ManualChamberOverride", approvedBy, before, org);
        await db.SaveChangesAsync();
        return org;
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
            existing.SiteNumber,
            existing.PhysicalAddress,
            existing.PostalAddress,
            existing.ProvinceCode,
            existing.CountryCode,
            existing.City,
            existing.PostalCode,
            existing.Latitude,
            existing.Longitude,
            existing.StatssaAreaCode,
            existing.PhoneNumber,
            existing.FaxNumber,
            existing.Email,
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
        existing.SiteNumber = site.SiteNumber;
        existing.PhysicalAddress = site.PhysicalAddress;
        existing.PostalAddress = site.PostalAddress;
        existing.ProvinceCode = site.ProvinceCode;
        existing.CountryCode = site.CountryCode;
        existing.City = site.City;
        existing.PostalCode = site.PostalCode;
        existing.Latitude = site.Latitude;
        existing.Longitude = site.Longitude;
        existing.StatssaAreaCode = site.StatssaAreaCode;
        existing.PhoneNumber = site.PhoneNumber;
        existing.FaxNumber = site.FaxNumber;
        existing.Email = site.Email;
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
            site.SiteNumber,
            site.IsHeadOffice,
            site.IsActive
        };

        db.OrganisationSites.Remove(site);
        _audit.LogAction(db, "OrganisationSite", siteId, "RemoveSite", currentUsername, before, null);
        await db.SaveChangesAsync();
        return true;
    }

    // 360-Degree Relational Queries Implementation
    public async Task<List<OrganisationLearnerDto>> GetLinkedLearnersAsync(int organisationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var learners = await db.CompanyLearners
            .Include(cl => cl.Person)
            .Include(cl => cl.TrainingProvider)
            .Include(cl => cl.OrganisationSite)
            .Where(cl => cl.OrganisationId == organisationId)
            .OrderByDescending(cl => cl.Id)
            .ToListAsync();

        return learners.Select(l => new OrganisationLearnerDto(
            l.Id,
            l.PersonId,
            l.Person != null ? $"{l.Person.FirstName} {l.Person.LastName}".Trim() : "Unknown Learner",
            l.Person?.RsaIdNumber,
            l.Person?.PassportNumber,
            l.LearnerContractNumber,
            l.LearningProgrammeTypeCode,
            GetProgrammeTypeName(l.LearningProgrammeTypeCode),
            l.QualificationTitle,
            l.NqfLevel,
            l.EnrolmentStatusId,
            GetEnrolmentStatusName(l.EnrolmentStatusId),
            l.RegistrationDate,
            l.CommencementDate,
            l.CompletionDate,
            l.TrainingProvider?.ProviderName,
            l.OrganisationSite?.SiteName
        )).ToList();
    }

    private static string GetProgrammeTypeName(string? code) => code switch
    {
        "01" => "Apprenticeship",
        "02" => "Learnership",
        "03" => "Skills Programme",
        "04" => "Internship",
        "05" => "Bursary",
        "06" => "Candidacy",
        "07" => "ARPL",
        _ => code ?? "Learnership"
    };

    private static string GetEnrolmentStatusName(string? code) => code switch
    {
        "01" => "Enrolled / Active",
        "02" => "Achieved",
        "03" => "Certificated",
        "04" => "Terminated",
        "05" => "Transferred",
        _ => code ?? "Active"
    };

    public async Task<List<OrganisationGrantSummaryDto>> GetGrantMoasAndApplicationsAsync(int organisationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var apps = await db.GrantApplications
            .Include(g => g.FundingWindow)
            .Where(g => g.OrganisationId == organisationId)
            .OrderByDescending(g => g.Id)
            .ToListAsync();

        var appIds = apps.Select(a => a.Id).ToList();
        var moas = await db.GrantMoas
            .Include(m => m.Milestones)
            .Where(m => appIds.Contains(m.GrantApplicationId))
            .ToListAsync();

        var results = new List<OrganisationGrantSummaryDto>();

        foreach (var app in apps)
        {
            var linkedMoa = moas.FirstOrDefault(m => m.GrantApplicationId == app.Id);
            var totalDisbursed = await db.GrantTranchePayments
                .Where(t => t.GrantApplicationId == app.Id && t.PaymentStatusCode == "Paid")
                .SumAsync(t => (decimal?)t.ApprovedPaymentAmount) ?? 0m;

            results.Add(new OrganisationGrantSummaryDto(
                app.Id,
                linkedMoa?.Id,
                linkedMoa != null ? linkedMoa.MoaNumber : app.ApplicationNumber,
                app.ProjectTitle,
                app.FundingWindow?.WindowName,
                app.GrantTypeCode,
                app.RequestedAmount,
                linkedMoa != null ? linkedMoa.TotalContractValue : (app.ApprovedAmount ?? 0m),
                totalDisbursed,
                linkedMoa != null ? linkedMoa.MoaStatusCode : (app.ApplicationStatusCode ?? "Pending"),
                linkedMoa != null ? linkedMoa.ContractStartDate : app.CreatedAt,
                linkedMoa?.ContractEndDate,
                linkedMoa?.Milestones.Count ?? 0
            ));
        }

        return results;
    }

    public async Task<List<OrganisationWpaDto>> GetWorkplaceApprovalsAsync(int organisationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var wpas = await db.WorkplaceApprovals
            .Include(w => w.OrganisationSite)
            .Include(w => w.Mentors)
            .Include(w => w.ToolItems)
            .Where(w => w.OrganisationId == organisationId)
            .OrderByDescending(w => w.Id)
            .ToListAsync();

        return wpas.Select(w => new OrganisationWpaDto(
            w.Id,
            w.ApprovalNumber,
            w.OrganisationSite?.SiteName ?? "Main Facility",
            w.QualificationTitle,
            w.SaqaQualificationId,
            w.ApprovalStatusCode,
            w.InspectionDate,
            w.ApprovalDate,
            w.ExpiryDate,
            w.Mentors.Count,
            w.ToolItems.Count
        )).ToList();
    }

    public async Task<List<OrganisationCommitteeMemberDto>> GetTrainingCommitteeMembersAsync(int organisationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var members = await db.TrainingCommitteeMembers
            .Include(m => m.Person)
            .Include(m => m.TrainingCommittee)
            .Where(m => m.TrainingCommittee != null && m.TrainingCommittee.OrganisationId == organisationId)
            .OrderByDescending(m => m.Id)
            .ToListAsync();

        return members.Select(m => new OrganisationCommitteeMemberDto(
            m.Id,
            m.TrainingCommitteeId,
            m.PersonId,
            m.Person != null ? $"{m.Person.FirstName} {m.Person.LastName}".Trim() : "Unknown Person",
            m.Person?.RsaIdNumber,
            m.Person?.EmailAddress,
            m.Person?.CellPhoneNumber,
            m.MemberRoleCode,
            m.Constituency,
            m.IsActive,
            m.CreatedAt
        )).ToList();
    }

    public async Task<OrganisationCommitteeMemberDto> AddTrainingCommitteeMemberAsync(int organisationId, int personId, string roleCode, string constituency, string currentUsername = "Admin")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var committee = await db.TrainingCommittees.FirstOrDefaultAsync(tc => tc.OrganisationId == organisationId && tc.CommitteeStatusCode == "Active");
        if (committee == null)
        {
            committee = new TrainingCommittee
            {
                OrganisationId = organisationId,
                FinancialYear = DateTime.UtcNow.Year,
                CommitteeStatusCode = "Active",
                ConstitutionalQuorumMet = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            };
            db.TrainingCommittees.Add(committee);
            await db.SaveChangesAsync();
        }

        var member = new TrainingCommitteeMember
        {
            TrainingCommitteeId = committee.Id,
            PersonId = personId,
            MemberRoleCode = roleCode,
            Constituency = constituency,
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.TrainingCommitteeMembers.Add(member);
        await db.SaveChangesAsync();

        var person = await db.People.FindAsync(personId);
        _audit.LogAction(db, "TrainingCommitteeMember", member.Id, "AddMember", currentUsername, null, member);
        await db.SaveChangesAsync();

        return new OrganisationCommitteeMemberDto(
            member.Id,
            committee.Id,
            personId,
            person != null ? $"{person.FirstName} {person.LastName}".Trim() : "Unknown Person",
            person?.RsaIdNumber,
            person?.EmailAddress,
            person?.CellPhoneNumber,
            member.MemberRoleCode,
            member.Constituency,
            member.IsActive,
            member.CreatedAt
        );
    }

    public async Task<bool> RemoveTrainingCommitteeMemberAsync(int memberId, string currentUsername = "Admin")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var member = await db.TrainingCommitteeMembers.FindAsync(memberId);
        if (member == null) return false;

        db.TrainingCommitteeMembers.Remove(member);
        _audit.LogAction(db, "TrainingCommitteeMember", memberId, "RemoveMember", currentUsername, member, null);
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<List<OrganisationLevyReconDto>> GetLevyReconHistoryAsync(int organisationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var org = await db.Organisations.FindAsync(organisationId);
        if (org == null) return new List<OrganisationLevyReconDto>();

        var recons = await db.SarsLevyReconAudits
            .Where(r => r.OrganisationId == organisationId || r.SdlNumber == org.SdlNumber)
            .OrderByDescending(r => r.FinancialYear)
            .ToListAsync();

        return recons.Select(r => new OrganisationLevyReconDto(
            r.Id,
            r.FinancialYear,
            r.SdlNumber,
            r.TotalSarsLeviesReceived,
            r.TotalSarsLeviesReceived * 0.20m,
            r.TotalSarsLeviesReceived * 0.495m,
            r.TotalSarsLeviesReceived * 0.105m,
            r.AuditStatusCode,
            r.ReconciliationDate
        )).ToList();
    }
}
