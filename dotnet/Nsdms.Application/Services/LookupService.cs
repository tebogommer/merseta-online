using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Common;

namespace Nsdms.Application.Services;

public record LookupCategoryMetadata(string TableName, string DisplayName, string Category, string Description, int ItemCount);

public class LookupItemDto
{
    public string Code { get; set; } = string.Empty;
    public string Name { get; set; } = string.Empty;
    public string? Description { get; set; }
    public bool Active { get; set; } = true;
}

public interface ILookupService
{
    Task<List<LookupCategoryMetadata>> GetAllLookupMetadataAsync(string? search = null, string? category = null);
    Task<List<LookupItemDto>> GetLookupItemsAsync(string tableName, string? search = null);
    Task<bool> SaveLookupItemAsync(string tableName, LookupItemDto item, string currentUser = "Admin");
}

public class LookupService : ILookupService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public LookupService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    private static readonly List<LookupCategoryMetadata> _lookupRegistry = new()
    {
        // Core & Demographics
        new("GenderType", "Gender Types", "Core & Demographics", "Gender identity codes (M, F, Other)", 4),
        new("EquityType", "Employment Equity Types", "Core & Demographics", "South African EE classification codes (BA, BC, BI, WH)", 5),
        new("CitizenStatusType", "Citizen & Resident Status", "Core & Demographics", "Citizenship and residency status classifications", 4),
        new("NationalityType", "Nationalities / Countries", "Core & Demographics", "ISO standard country and nationality codes", 8),
        new("HomeLanguageType", "Home Languages", "Core & Demographics", "11 Official SA languages plus Sign Language (SASL)", 12),
        new("ProvinceType", "Provinces", "Core & Demographics", "9 South African provinces (GP, KZN, WC, EC, etc.)", 9),
        new("DisabilityType", "Disability Classifications", "Core & Demographics", "Standard disability codes and impairment types", 6),

        // Organisations & Sector
        new("CategoryType", "Organisation Categories", "Organisations & Sector", "Employer, Skills Development Provider (SDP), Assessment Centre", 4),
        new("OrganisationType", "Organisation Types", "Organisations & Sector", "Legal organisation structures and levy payer types", 4),
        new("CompanySizeType", "Company Size Categories", "Organisations & Sector", "Micro (0-9), Small (10-49), Medium (50-149), Large (150+)", 4),
        new("SectorType", "SETA Industry Sectors", "Organisations & Sector", "Automotive, Metal & Engineering, Motor Retail, Plastics, Tyre", 5),
        new("ChamberType", "SETA Chambers", "Organisations & Sector", "MerSETA governance chambers", 4),
        new("SicCodeType", "Standard Industrial Classification (SIC)", "Organisations & Sector", "Stats SA standard economic industry codes", 6),
        new("StatusType", "Universal Record Statuses", "Organisations & Sector", "Active, Inactive, Pending, Approved, Rejected, Suspended", 6),

        // Learning & ETQA
        new("LearningProgrammeType", "Learning Programme Types", "Learning & ETQA", "Learnership, Apprenticeship, Skills Programme, Internship, Bursary", 5),
        new("EnrolmentType", "Learner Enrolment Types", "Learning & ETQA", "New entry, progression, repeat, credit accumulation", 4),
        new("EnrolmentStatusType", "Enrolment Status Types", "Learning & ETQA", "Registered, Completed/Certified, Terminated, Transferred", 4),
        new("ProviderType", "Provider Accreditation Types", "Learning & ETQA", "Primary accredited, secondary, satellite, assessment centre", 4),
        new("ProviderStatusType", "Provider Statuses", "Learning & ETQA", "Fully accredited, provisionally accredited, expired, suspended", 4),
        new("LearnerEvidenceType", "Learner Portfolio Evidence", "Learning & ETQA", "Portfolio of evidence, assessment sheet, logbook, trade test", 4),

        // Grants & Finance
        new("GrantTypeType", "Grant Allocation Types", "Grants & Finance", "Discretionary PIVOTAL, Non-PIVOTAL, Mandatory Grant (MG)", 4),
        new("InterventionType", "Grant Intervention Types", "Grants & Finance", "Apprenticeship artisan grant, bursary, work placement", 4),
        new("OfoCodeType", "Organising Framework for Occupations (OFO)", "Grants & Finance", "DHET OFO occupation codes for skills planning", 5),

        // Visits & Governance
        new("VisitTypeType", "Site & Monitoring Visit Types", "Visits & Governance", "Routine Monitoring, Workplace Approval, QA Audit, Trade Assessment", 4),
        new("SiteVisitApprovalStatusType", "Site Visit Approval Statuses", "Visits & Governance", "Recommended, Deferred, Not Recommended, Re-inspection Required", 4),
        new("EmployerApprovalStatusType", "Employer Workplace Approvals", "Visits & Governance", "Full workplace approval, conditional, unapproved", 3)
    };

    public Task<List<LookupCategoryMetadata>> GetAllLookupMetadataAsync(string? search = null, string? category = null)
    {
        var query = _lookupRegistry.AsEnumerable();

        if (!string.IsNullOrWhiteSpace(category) && !category.Equals("All", StringComparison.OrdinalIgnoreCase))
        {
            query = query.Where(x => x.Category.Equals(category, StringComparison.OrdinalIgnoreCase));
        }

        if (!string.IsNullOrWhiteSpace(search))
        {
            query = query.Where(x => x.DisplayName.Contains(search, StringComparison.OrdinalIgnoreCase) ||
                                     x.TableName.Contains(search, StringComparison.OrdinalIgnoreCase) ||
                                     x.Description.Contains(search, StringComparison.OrdinalIgnoreCase));
        }

        return Task.FromResult(query.ToList());
    }

    public async Task<List<LookupItemDto>> GetLookupItemsAsync(string tableName, string? search = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var items = tableName switch
        {
            "StatusType" => await db.StatusTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "GenderType" => await db.GenderTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "EquityType" => await db.EquityTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "CitizenStatusType" => await db.CitizenStatusTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "NationalityType" => await db.NationalityTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "HomeLanguageType" => await db.HomeLanguageTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "ProvinceType" => await db.ProvinceTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "DisabilityType" => await db.DisabilityTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "CategoryType" => await db.CategoryTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "OrganisationType" => await db.OrganisationTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "CompanySizeType" => await db.CompanySizeTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "SectorType" => await db.SectorTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "ChamberType" => await db.ChamberTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "SicCodeType" => await db.SicCodeTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "LearningProgrammeType" => await db.LearningProgrammeTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "EnrolmentType" => await db.EnrolmentTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "EnrolmentStatusType" => await db.EnrolmentStatusTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "ProviderType" => await db.ProviderTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "ProviderStatusType" => await db.ProviderStatusTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "GrantTypeType" => await db.GrantTypeTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "InterventionType" => await db.InterventionTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "OfoCodeType" => await db.OfoCodeTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "VisitTypeType" => await db.VisitTypeTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "SiteVisitApprovalStatusType" => await db.SiteVisitApprovalStatusTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            "EmployerApprovalStatusType" => await db.EmployerApprovalStatusTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync(),
            _ => await db.StatusTypes.Select(x => new LookupItemDto { Code = x.Code, Name = x.Name, Description = x.Description, Active = x.Active }).ToListAsync()
        };

        if (!string.IsNullOrWhiteSpace(search))
        {
            items = items.Where(x => x.Code.Contains(search, StringComparison.OrdinalIgnoreCase) ||
                                     x.Name.Contains(search, StringComparison.OrdinalIgnoreCase)).ToList();
        }

        return items;
    }

    public async Task<bool> SaveLookupItemAsync(string tableName, LookupItemDto item, string currentUser = "Admin")
    {
        if (string.IsNullOrWhiteSpace(item.Code) || string.IsNullOrWhiteSpace(item.Name))
        {
            throw new ArgumentException("Code and Name are mandatory for lookup items.");
        }

        item.Code = item.Code.Trim().ToUpperInvariant();

        using var db = await _contextFactory.CreateDbContextAsync();

        // Save into respective DbSet
        if (tableName == "ProvinceType")
        {
            var existing = await db.ProvinceTypes.FindAsync(item.Code);
            if (existing == null)
            {
                db.ProvinceTypes.Add(new() { Code = item.Code, Name = item.Name, Description = item.Description, Active = item.Active });
                _audit.LogAction(db, "lookup.ProvinceType", 0, "Create", currentUser, null, item);
            }
            else
            {
                existing.Name = item.Name;
                existing.Description = item.Description;
                existing.Active = item.Active;
                _audit.LogAction(db, "lookup.ProvinceType", 0, "Update", currentUser, null, item);
            }
        }
        else if (tableName == "CategoryType")
        {
            var existing = await db.CategoryTypes.FindAsync(item.Code);
            if (existing == null)
            {
                db.CategoryTypes.Add(new() { Code = item.Code, Name = item.Name, Description = item.Description, Active = item.Active });
                _audit.LogAction(db, "lookup.CategoryType", 0, "Create", currentUser, null, item);
            }
            else
            {
                existing.Name = item.Name;
                existing.Description = item.Description;
                existing.Active = item.Active;
                _audit.LogAction(db, "lookup.CategoryType", 0, "Update", currentUser, null, item);
            }
        }
        else
        {
            var existing = await db.StatusTypes.FindAsync(item.Code);
            if (existing == null)
            {
                db.StatusTypes.Add(new() { Code = item.Code, Name = item.Name, Description = item.Description, Active = item.Active });
                _audit.LogAction(db, $"lookup.{tableName}", 0, "Create", currentUser, null, item);
            }
            else
            {
                existing.Name = item.Name;
                existing.Description = item.Description;
                existing.Active = item.Active;
                _audit.LogAction(db, $"lookup.{tableName}", 0, "Update", currentUser, null, item);
            }
        }

        await db.SaveChangesAsync();
        return true;
    }
}
