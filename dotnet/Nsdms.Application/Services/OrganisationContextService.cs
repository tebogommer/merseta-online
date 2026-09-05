using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class OrganisationContextService : IOrganisationContextService
{
    private readonly INsdmsDbContextFactory _factory;

    public OrganisationContextService(INsdmsDbContextFactory factory)
    {
        _factory = factory;
    }

    public async Task<List<AffiliatedOrganisationDto>> GetAffiliatedOrganisationsAsync(string? userEmail, string? personIdNumber = null, bool isAdmin = false)
    {
        using var db = await _factory.CreateDbContextAsync();

        if (isAdmin)
        {
            var adminOrgs = await db.Organisations
                .IgnoreQueryFilters()
                .AsNoTracking()
                .Where(o => o.IsActive)
                .OrderBy(o => o.CompanyName)
                .Take(25)
                .ToListAsync();

            return adminOrgs.Select(o => new AffiliatedOrganisationDto
            {
                OrganisationId = o.Id,
                OrganisationName = o.CompanyName,
                SdlNumber = o.SdlNumber,
                TradingName = o.TradingName,
                ChamberCode = o.ChamberCode,
                ChamberName = FormatChamber(o.ChamberCode),
                LegalStatus = o.StatusCode ?? "ACTIVE",
                Roles = new() { "Global merSETA Access" },
                IsPrimary = false
            }).ToList();
        }

        Person? person = null;
        if (!string.IsNullOrWhiteSpace(userEmail))
        {
            person = await db.People.AsNoTracking().FirstOrDefaultAsync(p => p.Email == userEmail);
            if (person == null && userEmail.Contains('@'))
            {
                var username = userEmail.Split('@')[0].Replace(".", "");
                person = await db.People.AsNoTracking().FirstOrDefaultAsync(p => 
                    (p.FirstName + p.LastName).ToLower() == username.ToLower());
            }
        }

        if (person == null && !string.IsNullOrWhiteSpace(personIdNumber))
        {
            person = await db.People.AsNoTracking().FirstOrDefaultAsync(p => p.RsaIdNumber == personIdNumber);
        }

        var resultDict = new Dictionary<int, AffiliatedOrganisationDto>();

        if (person != null)
        {
            // 1. Check OrganisationContact links (SDF, HR, Director, Owner, Training Manager)
            var contacts = await db.OrganisationContacts
                .IgnoreQueryFilters()
                .AsNoTracking()
                .Include(c => c.Organisation)
                .Where(c => c.PersonId == person.Id && c.IsActive && c.Organisation != null && c.Organisation.IsActive)
                .ToListAsync();

            foreach (var c in contacts)
            {
                var org = c.Organisation!;
                if (!resultDict.TryGetValue(org.Id, out var dto))
                {
                    dto = MapBaseDto(org);
                    resultDict[org.Id] = dto;
                }

                var roleLabel = !string.IsNullOrWhiteSpace(c.Designation) 
                    ? c.Designation 
                    : (!string.IsNullOrWhiteSpace(c.ContactType) ? c.ContactType : "Organisation Contact");

                if (!dto.Roles.Contains(roleLabel))
                {
                    dto.Roles.Add(roleLabel);
                }

                if (c.IsPrimary)
                {
                    dto.IsPrimary = true;
                }
            }

            // 2. Check SdfCompany appointment links (Primary SDF, Secondary SDF, Labour SDF)
            var sdfAppointments = await db.SdfCompanies
                .IgnoreQueryFilters()
                .AsNoTracking()
                .Include(s => s.Organisation)
                .Where(s => s.PersonId == person.Id && s.IsActive && s.Organisation != null && s.Organisation.IsActive)
                .ToListAsync();

            foreach (var s in sdfAppointments)
            {
                var org = s.Organisation!;
                if (!resultDict.TryGetValue(org.Id, out var dto))
                {
                    dto = MapBaseDto(org);
                    resultDict[org.Id] = dto;
                }

                var sdfRole = $"SDF ({s.SdfTypeCode ?? "Primary"})";
                if (!dto.Roles.Contains(sdfRole))
                {
                    dto.Roles.Insert(0, sdfRole); // Prioritize SDF appointment at front
                }

                if (s.SdfTypeCode == "Primary")
                {
                    dto.IsPrimary = true;
                }

                if (s.AllowWspSubmission && string.IsNullOrEmpty(dto.WspStatus))
                {
                    dto.WspStatus = "Eligible for WSP";
                }
            }
        }

        // Fallback for demo / persona switching if no specific Person link exists in DB
        if (resultDict.Count == 0 && !string.IsNullOrWhiteSpace(userEmail))
        {
            if (userEmail.Contains("toyota", StringComparison.OrdinalIgnoreCase))
            {
                var toyota = await db.Organisations.IgnoreQueryFilters().AsNoTracking().FirstOrDefaultAsync(o => o.SdlNumber.Contains("700100200") || o.CompanyName.Contains("Toyota"));
                if (toyota != null)
                {
                    var dto = MapBaseDto(toyota);
                    dto.Roles.Add("Designated SDF & Employer Lead");
                    dto.IsPrimary = true;
                    resultDict[toyota.Id] = dto;
                }
            }
        }

        // Enrich with recent WSP / Grant status indicators
        foreach (var entry in resultDict.Values)
        {
            var latestWsp = await db.WspSubmissions
                .AsNoTracking()
                .Where(w => w.OrganisationId == entry.OrganisationId)
                .OrderByDescending(w => w.Id)
                .Select(w => w.WspApprovalStatusCode)
                .FirstOrDefaultAsync();

            if (!string.IsNullOrEmpty(latestWsp))
            {
                entry.WspStatus = latestWsp;
            }

            var latestDg = await db.GrantApplications
                .AsNoTracking()
                .Where(g => g.OrganisationId == entry.OrganisationId)
                .OrderByDescending(g => g.Id)
                .Select(g => g.StatusCode)
                .FirstOrDefaultAsync();

            if (!string.IsNullOrEmpty(latestDg))
            {
                entry.DgStatus = latestDg;
            }
        }

        return resultDict.Values
            .OrderByDescending(d => d.IsPrimary)
            .ThenBy(d => d.OrganisationName)
            .ToList();
    }

    public async Task<AffiliatedOrganisationDto?> GetOrganisationSummaryAsync(int organisationId)
    {
        using var db = await _factory.CreateDbContextAsync();
        var org = await db.Organisations.IgnoreQueryFilters().AsNoTracking().FirstOrDefaultAsync(o => o.Id == organisationId);
        if (org == null) return null;
        return MapBaseDto(org);
    }

    public async Task<List<AffiliatedOrganisationDto>> SearchAllOrganisationsAsync(string? searchTerm, int maxResults = 25)
    {
        using var db = await _factory.CreateDbContextAsync();
        var query = db.Organisations.IgnoreQueryFilters().AsNoTracking().Where(o => o.IsActive);

        if (!string.IsNullOrWhiteSpace(searchTerm))
        {
            var term = searchTerm.Trim().ToLower();
            query = query.Where(o => 
                o.CompanyName.ToLower().Contains(term) ||
                (o.TradingName != null && o.TradingName.ToLower().Contains(term)) ||
                o.SdlNumber.ToLower().Contains(term));
        }

        var orgs = await query.OrderBy(o => o.CompanyName).Take(maxResults).ToListAsync();
        return orgs.Select(MapBaseDto).ToList();
    }

    private static AffiliatedOrganisationDto MapBaseDto(Organisation org)
    {
        return new AffiliatedOrganisationDto
        {
            OrganisationId = org.Id,
            OrganisationName = org.CompanyName,
            SdlNumber = org.SdlNumber,
            TradingName = org.TradingName,
            ChamberCode = org.ChamberCode,
            ChamberName = FormatChamber(org.ChamberCode),
            LegalStatus = org.StatusCode ?? "ACTIVE",
            IsPrimary = false
        };
    }

    private static string FormatChamber(string? chamberCode)
    {
        if (string.IsNullOrEmpty(chamberCode)) return "Chamber Unassigned";
        return chamberCode switch
        {
            "CHAMBER_1" or "AUTO" => "Automotive Chamber",
            "CHAMBER_2" or "PLAST" => "Plastics Chamber",
            "CHAMBER_3" or "METAL" => "Metal & Engineering Chamber",
            "CHAMBER_4" or "MOTOR" => "Motor Chamber",
            "CHAMBER_5" or "NEW_TYRE" => "New Tyre Chamber",
            _ => chamberCode
        };
    }
}
