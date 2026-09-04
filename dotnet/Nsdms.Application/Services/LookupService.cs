using System.Collections.Concurrent;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Common;
using Nsdms.Domain.Lookups;

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
    Task<List<LookupItemDto>> GetLookupItemsAsync(string tableName, string? search = null, int skip = 0, int take = 100);
    Task<int> GetLookupItemsCountAsync(string tableName, string? search = null);
    Task<bool> SaveLookupItemAsync(string tableName, LookupItemDto item, string currentUser = "Admin");

    // Specialized high-frequency queries
    Task<List<LookupItemDto>> GetOfoCodesAsync(string? search = null, int limit = 50);
    Task<List<LookupItemDto>> GetSicCodesAsync(string? search = null, int limit = 50);
    Task<List<LookupItemDto>> GetStatssaAreaCodesAsync(string? search = null, int limit = 50);
    Task<List<LookupItemDto>> GetSetasAsync();
    Task<List<LookupItemDto>> GetAlternateIdTypesAsync();
    Task<List<LookupItemDto>> GetCountriesAsync(string? search = null, int limit = 100);
}

public class LookupService : ILookupService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    // OPT-005 In-memory cache with declared 1-hour expiry
    private static readonly ConcurrentDictionary<string, (DateTime ExpiryUtc, List<LookupItemDto> Items)> _lookupCache = new();
    private static readonly TimeSpan CacheTtl = TimeSpan.FromHours(1);

    public LookupService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    private static readonly List<LookupCategoryMetadata> _lookupRegistry = new()
    {
        // 1. Core & Demographics
        new("GenderType", "Gender Classifications", "Core & Demographics", "Statutory gender identity reference codes (Female, Male, Unknown)", 2),
        new("EquityType", "Employment Equity Classifications", "Core & Demographics", "Statutory Employment Equity racial demographic classifications (African, Coloured, Indian, White)", 5),
        new("CitizenStatusType", "Citizenship & Residency Status", "Core & Demographics", "SETMIS citizenship & permanent residency classifications (SA, PR, Dual, Other)", 5),
        new("AlternateIdType", "Alternate Identification Types", "Core & Demographics", "SETMIS alternate identification document types (Passport, Birth Certificate, Work Permit)", 13),
        new("NationalityType", "Regional Nationalities", "Core & Demographics", "SETMIS regional nationality classification codes (23 Southern African & International regions)", 23),
        new("CountryType", "Global Countries (ISO-3166)", "Core & Demographics", "ISO-3166 2-Letter Alpha codes covering 249 sovereign countries and territories", 249),
        new("HomeLanguageType", "Home Languages", "Core & Demographics", "11 Official SA languages plus South African Sign Language (SASL) and dialects", 14),
        new("ProvinceType", "Provinces & Jurisdictions", "Core & Demographics", "9 South African provincial regions plus National and Outside SA classifications", 11),
        new("DisabilityType", "Disability Impairment Categories", "Core & Demographics", "Statutory Employment Equity disability categories and physical/sensory impairment types", 6),
        new("EconomicStatusType", "Economic Employment Statuses", "Core & Demographics", "Learner and employee economic activity standing (Employed, Unemployed, Student)", 4),
        new("PopiActStatusType", "POPIA Consent Statuses", "Core & Demographics", "Protection of Personal Information Act statutory data processing consent status", 3),

        // 2. Washington Group Functioning Disability Ratings
        new("CommunicatingRatingType", "WG: Communication Functioning", "Washington Group Functioning", "Washington Group Communication difficulty rating (1: None to 6: Cannot determine)", 6),
        new("HearingRatingType", "WG: Hearing Functioning", "Washington Group Functioning", "Washington Group Hearing difficulty rating (1: None to 6: Cannot determine)", 6),
        new("RememberingRatingType", "WG: Memory & Cognitive Functioning", "Washington Group Functioning", "Washington Group Remembering and Concentrating difficulty rating (1 to 6)", 6),
        new("SeeingRatingType", "WG: Visual Functioning", "Washington Group Functioning", "Washington Group Seeing functional difficulty rating (1 to 6)", 6),
        new("SelfCareRatingType", "WG: Self-Care Functioning", "Washington Group Functioning", "Washington Group Self-care and Hygiene functional difficulty rating (1 to 6)", 6),
        new("WalkingRatingType", "WG: Mobility & Walking Functioning", "Washington Group Functioning", "Washington Group Mobility and Walking functional difficulty rating (1 to 6)", 6),

        // 3. Learning & ETQA Lookups
        new("LearningProgrammeType", "Learning Programme Types", "Learning & ETQA", "Intervention modalities (Learnership, Apprenticeship, Skills Programme, Internship, Bursary)", 11),
        new("EnrolmentType", "Learner Enrolment Delivery Modalities", "Learning & ETQA", "Learning delivery modes (Contact, Distance, Mixed Mode, Workplace)", 8),
        new("EnrolmentStatusType", "Learner Agreement Enrolment Statuses", "Learning & ETQA", "SETMIS learner lifecycle milestones (Enrolled, Achieved, Certificated, Discontinued)", 8),
        new("EnrolmentStatusReasonType", "Enrolment Transition Reasons", "Learning & ETQA", "Statutory reasons for learner agreement terminations or status changes", 13),
        new("InternshipStatusType", "Internship / WIL Statuses", "Learning & ETQA", "Work Integrated Learning and graduate internship progress statuses", 3),
        new("NonNqfInterventionStatusType", "Non-NQF Intervention Statuses", "Learning & ETQA", "Non-NQF accredited skills programme registration and approval statuses", 3),
        new("PartOfType", "Programme Hierarchy & Articulation", "Learning & ETQA", "Programme qualification hierarchy (Stand-alone, Part of Learnership, Part of Qualification)", 5),
        new("ProviderClassType", "Provider Institutional Classifications", "Learning & ETQA", "Skills Development Provider legal class (Public, Private, NGO/CBO, Foreign)", 7),
        new("ProviderType", "Provider Functional Entity Types", "Learning & ETQA", "Provider functional operational type (Education, Training, Employer, NGO)", 5),
        new("ProviderStatusType", "Provider ETQA Accreditation Standing", "Learning & ETQA", "ETQA accreditation standing (Accredited, Provisional, De-accredited, Closed)", 11),
        new("DesignationType", "Assessor & Moderator Designations", "Learning & ETQA", "Statutory designations for ETQA practitioners (Assessor, Moderator)", 2),
        new("DesignationStructureStatusType", "Practitioner Registration Standing", "Learning & ETQA", "Assessor and moderator registration standing codes (Registered, Deregistered, etc.)", 6),
        new("SubfieldType", "NQF Subfields", "Learning & ETQA", "SAQA National Qualifications Framework subfields for curriculum development", 68),
        new("TradeTestResultType", "Trade Test Competency Outcomes", "Learning & ETQA", "Artisan practical trade test assessment outcomes (Competent, Not yet competent)", 2),
        new("TradeTestResultReasonType", "Trade Test Outcome Reasons", "Learning & ETQA", "Artisan practical trade test assessment reason codes", 1),
        new("LearnerEvidenceType", "Learner Portfolio Evidence Types", "Learning & ETQA", "Portfolio of Evidence, Assessment Sheet, Logbook, Trade Test Certificate", 4),

        // 4. Occupations & Industries
        new("OfoCodeType", "Organising Framework for Occupations (OFO)", "Occupations & Industries", "DHET Organising Framework for Occupations statutory occupation codes", 1454),
        new("SicCodeType", "Standard Industrial Classification (SIC)", "Occupations & Industries", "Stats SA Standard Industrial Classification economic activity codes", 815),

        // 5. Stats SA & Geolocation
        new("StatssaAreaCodeType", "Stats SA Spatial Sub-Place Areas", "Stats SA & Geolocation", "Official Statistics South Africa spatial sub-place and municipal area codes", 22108),
        new("UrbanRuralType", "Urban vs Rural Classifications", "Stats SA & Geolocation", "Geographic intervention area classification (Urban, Rural, Unknown)", 3),

        // 6. Organisations & Sector
        new("CategoryType", "Organisation Levy Categories", "Organisations & Sector", "Employer, Skills Development Provider (SDP), Assessment Centre, Trade Test Centre", 4),
        new("OrganisationType", "Organisation Legal Types", "Organisations & Sector", "Enterprise legal constitution (Pty Ltd, Close Corporation, Public Entity, NGO)", 4),
        new("CompanySizeType", "Company Size Bands", "Organisations & Sector", "Headcount bands (Micro: 0-9, Small: 10-49, Medium: 50-149, Large: 150+)", 4),
        new("SectorType", "SETA Industrial Sectors", "Organisations & Sector", "MerSETA economic chambers (Automotive, Metal & Engineering, Motor, Plastics, Tyre)", 5),
        new("ChamberType", "MerSETA Chambers", "Organisations & Sector", "MerSETA statutory chamber sub-committees", 4),
        new("SetaType", "Sector Education & Training Authorities", "Organisations & Sector", "21 South African statutory Sector Education & Training Authorities (SETAs)", 21),
        new("StatusType", "Universal Record Statuses", "Organisations & Sector", "Universal operational statuses (Active, Inactive, Pending, Approved, Rejected, Suspended)", 6),

        // 7. Grants & Finance
        new("FundingType", "Intervention Funding Sources", "Grants & Finance", "Learning funding origin (SETA funded, Employer funded, Learner funded)", 5),
        new("GrantTypeType", "Grant Allocation Types", "Grants & Finance", "Discretionary PIVOTAL, Non-PIVOTAL, Mandatory Grant (MG), Special Projects", 4),
        new("InterventionType", "Grant Intervention Categories", "Grants & Finance", "Apprenticeship, Learnership, Skills Programme, Internship, Bursary", 5),

        // 8. Visits & Governance
        new("VisitTypeType", "Site & Monitoring Visit Types", "Visits & Governance", "Routine Monitoring, Workplace Approval, QA Audit, Trade Assessment", 4),
        new("SiteVisitApprovalStatusType", "Site Visit Approval Statuses", "Visits & Governance", "Recommended, Deferred, Not Recommended, Re-inspection Required", 4),
        new("EmployerApprovalStatusType", "Employer Workplace Approvals", "Visits & Governance", "Full workplace approval, conditional, unapproved, legacy", 3),

        // 9. NLRD & Qualifications
        new("AbetBandType", "ABET Band Classifications", "NLRD & Qualifications", "Adult Basic Education & Training Levels (Undefined, Level 1-4 / GETC)", 5),
        new("QualificationTypeType", "Qualification Formal Types", "NLRD & Qualifications", "SAQA NQF qualification types (National Certificate, National Diploma, Occupational Certificate, etc.)", 27),
        new("HonoursClassType", "Academic Distinction Classes", "NLRD & Qualifications", "Higher Education and Umalusi graduation distinctions (Cum Laude, Honours, etc.)", 27)
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

    public async Task<List<LookupItemDto>> GetLookupItemsAsync(string tableName, string? search = null, int skip = 0, int take = 100)
    {
        string cacheKey = $"{tableName}:{skip}:{take}";
        if (string.IsNullOrWhiteSpace(search) && _lookupCache.TryGetValue(cacheKey, out var cached) && cached.ExpiryUtc > DateTime.UtcNow)
        {
            return cached.Items;
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        var query = GetQueryableForTable(db, tableName);

        if (!string.IsNullOrWhiteSpace(search))
        {
            query = query.Where(x => x.Code.Contains(search) || x.Name.Contains(search) || (x.Description != null && x.Description.Contains(search)));
        }

        var result = await query
            .OrderBy(x => x.Name)
            .Skip(skip)
            .Take(take)
            .Select(x => new LookupItemDto
            {
                Code = x.Code,
                Name = x.Name,
                Description = x.Description,
                Active = x.Active
            })
            .ToListAsync();

        if (string.IsNullOrWhiteSpace(search))
        {
            _lookupCache[cacheKey] = (DateTime.UtcNow.Add(CacheTtl), result);
        }

        return result;
    }

    public async Task<int> GetLookupItemsCountAsync(string tableName, string? search = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = GetQueryableForTable(db, tableName);

        if (!string.IsNullOrWhiteSpace(search))
        {
            query = query.Where(x => x.Code.Contains(search) || x.Name.Contains(search) || (x.Description != null && x.Description.Contains(search)));
        }

        return await query.CountAsync();
    }

    public async Task<bool> SaveLookupItemAsync(string tableName, LookupItemDto item, string currentUser = "Admin")
    {
        if (string.IsNullOrWhiteSpace(item.Code) || string.IsNullOrWhiteSpace(item.Name))
        {
            throw new ArgumentException("Code and Name are mandatory for lookup items.");
        }

        item.Code = item.Code.Trim();

        using var db = await _contextFactory.CreateDbContextAsync();
        var entityType = GetEntityTypeForTable(tableName);

        if (entityType == null)
        {
            throw new InvalidOperationException($"Lookup table {tableName} is not recognized.");
        }

        var entry = await db.FindAsync(entityType, item.Code);
        if (entry == null)
        {
            var newObj = Activator.CreateInstance(entityType) as BaseLookupType;
            if (newObj != null)
            {
                newObj.Code = item.Code;
                newObj.Name = item.Name;
                newObj.Description = item.Description;
                newObj.Active = item.Active;
                newObj.CreatedAt = DateTime.UtcNow;
                newObj.CreatedBy = currentUser;
                db.Add(newObj);
                _audit.LogAction(db, $"lookup.{tableName}", 0, "Create", currentUser, null, item);
            }
        }
        else if (entry is BaseLookupType existing)
        {
            existing.Name = item.Name;
            existing.Description = item.Description;
            existing.Active = item.Active;
            existing.ModifiedAt = DateTime.UtcNow;
            existing.ModifiedBy = currentUser;
            _audit.LogAction(db, $"lookup.{tableName}", 0, "Update", currentUser, null, item);
        }

        await db.SaveChangesAsync();

        // Invalidate cached lookups for this table
        foreach (var key in _lookupCache.Keys)
        {
            if (key.StartsWith($"{tableName}:", StringComparison.OrdinalIgnoreCase))
            {
                _lookupCache.TryRemove(key, out _);
            }
        }

        return true;
    }

    public async Task<List<LookupItemDto>> GetOfoCodesAsync(string? search = null, int limit = 50) =>
        await GetLookupItemsAsync("OfoCodeType", search, 0, limit);

    public async Task<List<LookupItemDto>> GetSicCodesAsync(string? search = null, int limit = 50) =>
        await GetLookupItemsAsync("SicCodeType", search, 0, limit);

    public async Task<List<LookupItemDto>> GetStatssaAreaCodesAsync(string? search = null, int limit = 50) =>
        await GetLookupItemsAsync("StatssaAreaCodeType", search, 0, limit);

    public async Task<List<LookupItemDto>> GetSetasAsync() =>
        await GetLookupItemsAsync("SetaType", null, 0, 100);

    public async Task<List<LookupItemDto>> GetAlternateIdTypesAsync() =>
        await GetLookupItemsAsync("AlternateIdType", null, 0, 100);

    public async Task<List<LookupItemDto>> GetCountriesAsync(string? search = null, int limit = 100) =>
        await GetLookupItemsAsync("CountryType", search, 0, limit);

    private static IQueryable<BaseLookupType> GetQueryableForTable(INsdmsDbContext db, string tableName)
    {
        return tableName switch
        {
            "AlternateIdType" => db.AlternateIdTypes.AsNoTracking().Cast<BaseLookupType>(),
            "CitizenStatusType" => db.CitizenStatusTypes.AsNoTracking().Cast<BaseLookupType>(),
            "CountryType" => db.CountryTypes.AsNoTracking().Cast<BaseLookupType>(),
            "NationalityType" => db.NationalityTypes.AsNoTracking().Cast<BaseLookupType>(),
            "HomeLanguageType" => db.HomeLanguageTypes.AsNoTracking().Cast<BaseLookupType>(),
            "ProvinceType" => db.ProvinceTypes.AsNoTracking().Cast<BaseLookupType>(),
            "EquityType" => db.EquityTypes.AsNoTracking().Cast<BaseLookupType>(),
            "GenderType" => db.GenderTypes.AsNoTracking().Cast<BaseLookupType>(),
            "DisabilityType" => db.DisabilityTypes.AsNoTracking().Cast<BaseLookupType>(),
            "EconomicStatusType" => db.EconomicStatusTypes.AsNoTracking().Cast<BaseLookupType>(),
            "PopiActStatusType" => db.PopiActStatusTypes.AsNoTracking().Cast<BaseLookupType>(),
            "CommunicatingRatingType" => db.CommunicatingRatingTypes.AsNoTracking().Cast<BaseLookupType>(),
            "HearingRatingType" => db.HearingRatingTypes.AsNoTracking().Cast<BaseLookupType>(),
            "RememberingRatingType" => db.RememberingRatingTypes.AsNoTracking().Cast<BaseLookupType>(),
            "SeeingRatingType" => db.SeeingRatingTypes.AsNoTracking().Cast<BaseLookupType>(),
            "SelfCareRatingType" => db.SelfCareRatingTypes.AsNoTracking().Cast<BaseLookupType>(),
            "WalkingRatingType" => db.WalkingRatingTypes.AsNoTracking().Cast<BaseLookupType>(),
            "DesignationType" => db.DesignationTypes.AsNoTracking().Cast<BaseLookupType>(),
            "DesignationStructureStatusType" => db.DesignationStructureStatusTypes.AsNoTracking().Cast<BaseLookupType>(),
            "LearningProgrammeType" => db.LearningProgrammeTypes.AsNoTracking().Cast<BaseLookupType>(),
            "EnrolmentType" => db.EnrolmentTypes.AsNoTracking().Cast<BaseLookupType>(),
            "EnrolmentStatusType" => db.EnrolmentStatusTypes.AsNoTracking().Cast<BaseLookupType>(),
            "EnrolmentStatusReasonType" => db.EnrolmentStatusReasonTypes.AsNoTracking().Cast<BaseLookupType>(),
            "InternshipStatusType" => db.InternshipStatusTypes.AsNoTracking().Cast<BaseLookupType>(),
            "NonNqfInterventionStatusType" => db.NonNqfInterventionStatusTypes.AsNoTracking().Cast<BaseLookupType>(),
            "PartOfType" => db.PartOfTypes.AsNoTracking().Cast<BaseLookupType>(),
            "ProviderClassType" => db.ProviderClassTypes.AsNoTracking().Cast<BaseLookupType>(),
            "ProviderType" => db.ProviderTypes.AsNoTracking().Cast<BaseLookupType>(),
            "ProviderStatusType" => db.ProviderStatusTypes.AsNoTracking().Cast<BaseLookupType>(),
            "SubfieldType" => db.SubfieldTypes.AsNoTracking().Cast<BaseLookupType>(),
            "TradeTestResultType" => db.TradeTestResultTypes.AsNoTracking().Cast<BaseLookupType>(),
            "TradeTestResultReasonType" => db.TradeTestResultReasonTypes.AsNoTracking().Cast<BaseLookupType>(),
            "LearnerEvidenceType" => db.LearnerEvidenceTypes.AsNoTracking().Cast<BaseLookupType>(),
            "OfoCodeType" => db.OfoCodeTypes.AsNoTracking().Cast<BaseLookupType>(),
            "SicCodeType" => db.SicCodeTypes.AsNoTracking().Cast<BaseLookupType>(),
            "StatssaAreaCodeType" => db.StatssaAreaCodeTypes.AsNoTracking().Cast<BaseLookupType>(),
            "UrbanRuralType" => db.UrbanRuralTypes.AsNoTracking().Cast<BaseLookupType>(),
            "CategoryType" => db.CategoryTypes.AsNoTracking().Cast<BaseLookupType>(),
            "OrganisationType" => db.OrganisationTypes.AsNoTracking().Cast<BaseLookupType>(),
            "CompanySizeType" => db.CompanySizeTypes.AsNoTracking().Cast<BaseLookupType>(),
            "SectorType" => db.SectorTypes.AsNoTracking().Cast<BaseLookupType>(),
            "ChamberType" => db.ChamberTypes.AsNoTracking().Cast<BaseLookupType>(),
            "SetaType" => db.SetaTypes.AsNoTracking().Cast<BaseLookupType>(),
            "StatusType" => db.StatusTypes.AsNoTracking().Cast<BaseLookupType>(),
            "FundingType" => db.FundingTypes.AsNoTracking().Cast<BaseLookupType>(),
            "GrantTypeType" => db.GrantTypeTypes.AsNoTracking().Cast<BaseLookupType>(),
            "InterventionType" => db.InterventionTypes.AsNoTracking().Cast<BaseLookupType>(),
            "VisitTypeType" => db.VisitTypeTypes.AsNoTracking().Cast<BaseLookupType>(),
            "SiteVisitApprovalStatusType" => db.SiteVisitApprovalStatusTypes.AsNoTracking().Cast<BaseLookupType>(),
            "EmployerApprovalStatusType" => db.EmployerApprovalStatusTypes.AsNoTracking().Cast<BaseLookupType>(),
            "AbetBandType" => db.AbetBandTypes.AsNoTracking().Cast<BaseLookupType>(),
            "QualificationTypeType" => db.QualificationTypeTypes.AsNoTracking().Cast<BaseLookupType>(),
            "HonoursClassType" => db.HonoursClassTypes.AsNoTracking().Cast<BaseLookupType>(),
            _ => db.StatusTypes.AsNoTracking().Cast<BaseLookupType>()
        };
    }

    private static Type? GetEntityTypeForTable(string tableName)
    {
        return tableName switch
        {
            "AlternateIdType" => typeof(AlternateIdType),
            "CitizenStatusType" => typeof(CitizenStatusType),
            "CountryType" => typeof(CountryType),
            "NationalityType" => typeof(NationalityType),
            "HomeLanguageType" => typeof(HomeLanguageType),
            "ProvinceType" => typeof(ProvinceType),
            "EquityType" => typeof(EquityType),
            "GenderType" => typeof(GenderType),
            "DisabilityType" => typeof(DisabilityType),
            "EconomicStatusType" => typeof(EconomicStatusType),
            "PopiActStatusType" => typeof(PopiActStatusType),
            "CommunicatingRatingType" => typeof(CommunicatingRatingType),
            "HearingRatingType" => typeof(HearingRatingType),
            "RememberingRatingType" => typeof(RememberingRatingType),
            "SeeingRatingType" => typeof(SeeingRatingType),
            "SelfCareRatingType" => typeof(SelfCareRatingType),
            "WalkingRatingType" => typeof(WalkingRatingType),
            "DesignationType" => typeof(DesignationType),
            "DesignationStructureStatusType" => typeof(DesignationStructureStatusType),
            "LearningProgrammeType" => typeof(LearningProgrammeType),
            "EnrolmentType" => typeof(EnrolmentType),
            "EnrolmentStatusType" => typeof(EnrolmentStatusType),
            "EnrolmentStatusReasonType" => typeof(EnrolmentStatusReasonType),
            "InternshipStatusType" => typeof(InternshipStatusType),
            "NonNqfInterventionStatusType" => typeof(NonNqfInterventionStatusType),
            "PartOfType" => typeof(PartOfType),
            "ProviderClassType" => typeof(ProviderClassType),
            "ProviderType" => typeof(ProviderType),
            "ProviderStatusType" => typeof(ProviderStatusType),
            "SubfieldType" => typeof(SubfieldType),
            "TradeTestResultType" => typeof(TradeTestResultType),
            "TradeTestResultReasonType" => typeof(TradeTestResultReasonType),
            "LearnerEvidenceType" => typeof(LearnerEvidenceType),
            "OfoCodeType" => typeof(OfoCodeType),
            "SicCodeType" => typeof(SicCodeType),
            "StatssaAreaCodeType" => typeof(StatssaAreaCodeType),
            "UrbanRuralType" => typeof(UrbanRuralType),
            "CategoryType" => typeof(CategoryType),
            "OrganisationType" => typeof(OrganisationType),
            "CompanySizeType" => typeof(CompanySizeType),
            "SectorType" => typeof(SectorType),
            "ChamberType" => typeof(ChamberType),
            "SetaType" => typeof(SetaType),
            "StatusType" => typeof(StatusType),
            "FundingType" => typeof(FundingType),
            "GrantTypeType" => typeof(GrantTypeType),
            "InterventionType" => typeof(InterventionType),
            "VisitTypeType" => typeof(VisitTypeType),
            "SiteVisitApprovalStatusType" => typeof(SiteVisitApprovalStatusType),
            "EmployerApprovalStatusType" => typeof(EmployerApprovalStatusType),
            "AbetBandType" => typeof(AbetBandType),
            "QualificationTypeType" => typeof(QualificationTypeType),
            "HonoursClassType" => typeof(HonoursClassType),
            _ => null
        };
    }
}
