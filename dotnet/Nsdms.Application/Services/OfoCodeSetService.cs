using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Common.Models;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Service implementation managing DHET Gazetted Organising Framework for Occupations (OFO) releases.
/// </summary>
public class OfoCodeSetService : IOfoCodeSetService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public OfoCodeSetService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<List<OfoCodeSetDto>> GetActiveSetsAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var sets = await db.OfoCodeSets.AsNoTracking()
            .Where(s => s.IsActive)
            .OrderByDescending(s => s.SetYear)
            .Select(s => new OfoCodeSetDto
            {
                Id = s.Id,
                SetYear = s.SetYear,
                Name = s.Name,
                Description = s.Description,
                GazettedDate = s.GazettedDate,
                GazetteNumber = s.GazetteNumber,
                IsActive = s.IsActive,
                TotalOccupationsCount = db.OfoCodeSetItems.Count(i => i.OfoCodeSetId == s.Id && i.IsActiveInSet),
                TradeCount = db.OfoCodeSetItems.Count(i => i.OfoCodeSetId == s.Id && i.IsActiveInSet && i.Trade)
            })
            .ToListAsync();

        return sets;
    }

    public async Task<OfoCodeSetDto?> GetSetByIdAsync(int setId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var set = await db.OfoCodeSets.AsNoTracking()
            .Where(s => s.Id == setId)
            .Select(s => new OfoCodeSetDto
            {
                Id = s.Id,
                SetYear = s.SetYear,
                Name = s.Name,
                Description = s.Description,
                GazettedDate = s.GazettedDate,
                GazetteNumber = s.GazetteNumber,
                IsActive = s.IsActive,
                TotalOccupationsCount = db.OfoCodeSetItems.Count(i => i.OfoCodeSetId == s.Id && i.IsActiveInSet),
                TradeCount = db.OfoCodeSetItems.Count(i => i.OfoCodeSetId == s.Id && i.IsActiveInSet && i.Trade)
            })
            .FirstOrDefaultAsync();

        return set;
    }

    public async Task<List<OfoCodeLookupDto>> SearchActiveCodesInSetAsync(int setId, string query, int maxResults = 50)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var q = query?.Trim() ?? string.Empty;

        // INVARIANT 1 & 2: Strictly query items that belong to the set AND whose lookup.OfoCodeType is Active
        var itemsQuery = from item in db.OfoCodeSetItems.AsNoTracking()
                         join ofo in db.OfoCodeTypes.AsNoTracking() on item.OfoCodeId equals ofo.Code
                         where item.OfoCodeSetId == setId && item.IsActiveInSet && ofo.Active
                         select new { item, ofo };

        if (!string.IsNullOrWhiteSpace(q))
        {
            itemsQuery = itemsQuery.Where(x =>
                x.ofo.Code.Contains(q) ||
                x.ofo.Name.Contains(q) ||
                (x.ofo.Description != null && x.ofo.Description.Contains(q)));
        }

        var results = await itemsQuery
            .OrderBy(x => x.ofo.Code)
            .Take(maxResults)
            .Select(x => new OfoCodeLookupDto
            {
                Code = x.ofo.Code,
                Name = x.ofo.Name,
                MajorGroup = x.item.MajorGroup,
                Trade = x.item.Trade,
                Active = x.ofo.Active
            })
            .ToListAsync();

        return results;
    }

    public async Task<OfoCodeSet> CreateSetAsync(CreateOfoCodeSetDto dto, string userId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var existing = await db.OfoCodeSets.FirstOrDefaultAsync(s => s.SetYear == dto.SetYear);
        if (existing != null)
        {
            throw new InvalidOperationException($"A statutory OFO code set already exists for Year {dto.SetYear} (Set ID #{existing.Id}).");
        }

        var set = new OfoCodeSet
        {
            SetYear = dto.SetYear,
            Name = dto.Name.Trim(),
            Description = dto.Description?.Trim(),
            GazettedDate = dto.GazettedDate,
            GazetteNumber = dto.GazetteNumber?.Trim(),
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = userId
        };

        db.OfoCodeSets.Add(set);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "OfoCodeSet", set.Id, "CreateSet", userId, null, set);
        await db.SaveChangesAsync();

        return set;
    }
}
