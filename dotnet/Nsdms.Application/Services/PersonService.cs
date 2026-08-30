using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Models;
using Nsdms.Application.Common.Utilities;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IPersonService
{
    Task<List<Person>> GetAllAsync(string? search = null);
    Task<PagedResult<PersonListDto>> GetPagedAsync(PaginationQuery query, CancellationToken cancellationToken = default);
    Task<Person?> GetByIdAsync(int id);
    Task<Person?> GetByRsaIdAsync(string rsaId);
    Task<Person> CreateAsync(Person person, string currentUsername = "SYSTEM");
    Task<Person> UpdateAsync(Person person, string currentUsername = "SYSTEM");
    Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM");
}

public class PersonService : IPersonService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public PersonService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<PagedResult<PersonListDto>> GetPagedAsync(PaginationQuery query, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var baseQuery = db.People.AsNoTracking();

        if (query.FilterParams.TryGetValue("status", out var statusVal) && !string.IsNullOrWhiteSpace(statusVal) && statusVal != "All")
        {
            if (statusVal.Equals("Active", StringComparison.OrdinalIgnoreCase))
                baseQuery = baseQuery.Where(p => p.IsActive);
            else if (statusVal.Equals("Inactive", StringComparison.OrdinalIgnoreCase))
                baseQuery = baseQuery.Where(p => !p.IsActive);
        }

        if (!string.IsNullOrWhiteSpace(query.SearchText))
        {
            var s = query.SearchText.Trim();
            baseQuery = baseQuery.Where(p =>
                p.FirstName.Contains(s) ||
                p.LastName.Contains(s) ||
                p.RsaIdNumber.Contains(s) ||
                (p.PassportNumber != null && p.PassportNumber.Contains(s)) ||
                (p.Email != null && p.Email.Contains(s)) ||
                (p.PhoneNumber != null && p.PhoneNumber.Contains(s)));
        }

        var totalCount = await baseQuery.CountAsync(cancellationToken);

        var pagedEntities = await baseQuery
            .OrderBy(p => p.LastName)
            .ThenBy(p => p.FirstName)
            .Skip(query.PageIndex * query.PageSize)
            .Take(query.PageSize)
            .ToListAsync(cancellationToken);

        var items = pagedEntities.Select(p => new PersonListDto(
            p.Id,
            p.Title,
            p.FirstName ?? string.Empty,
            p.LastName ?? string.Empty,
            p.MiddleName,
            p.RsaIdNumber,
            p.PassportNumber,
            p.Gender,
            p.DateOfBirth,
            p.Email,
            p.PhoneNumber,
            p.ProvinceCode,
            p.IsActive
        )).ToList();

        return new PagedResult<PersonListDto>(items, totalCount, query.PageIndex, query.PageSize);
    }

    public async Task<List<Person>> GetAllAsync(string? search = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.People.AsQueryable();

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim();
            query = query.Where(p =>
                p.FirstName.Contains(s) ||
                p.LastName.Contains(s) ||
                p.RsaIdNumber.Contains(s) ||
                (p.PassportNumber != null && p.PassportNumber.Contains(s)) ||
                (p.Email != null && p.Email.Contains(s)) ||
                (p.PhoneNumber != null && p.PhoneNumber.Contains(s)));
        }

        return await query
            .OrderBy(p => p.LastName)
            .ThenBy(p => p.FirstName)
            .ToListAsync();
    }

    public async Task<Person?> GetByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.People.FirstOrDefaultAsync(p => p.Id == id);
    }

    public async Task<Person?> GetByRsaIdAsync(string rsaId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.People.FirstOrDefaultAsync(p => p.RsaIdNumber == rsaId);
    }

    public async Task<Person> CreateAsync(Person person, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        if (!string.IsNullOrWhiteSpace(person.RsaIdNumber))
        {
            var parse = RsaIdValidator.Parse(person.RsaIdNumber);
            if (!parse.IsValid)
            {
                throw new ArgumentException($"Invalid RSA ID number: {parse.ErrorMessage}");
            }

            var duplicate = await db.People.AnyAsync(p => p.RsaIdNumber == person.RsaIdNumber);
            if (duplicate)
            {
                throw new InvalidOperationException($"A person with RSA ID {person.RsaIdNumber} already exists.");
            }
        }

        // Auto-calculate demographic data from RSA ID if provided
        AutoPopulateFromRsaId(person);

        person.CreatedAt = DateTime.UtcNow;
        person.CreatedBy = currentUsername;

        db.People.Add(person);
        await db.SaveChangesAsync();

        // Double-write audit log
        _audit.LogAction(db, "Person", person.Id, "Create", currentUsername, null, person);
        await db.SaveChangesAsync();

        return person;
    }

    public async Task<Person> UpdateAsync(Person person, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.People.FindAsync(person.Id);
        if (existing == null)
        {
            throw new KeyNotFoundException($"Person with ID {person.Id} was not found.");
        }

        // Auto-calculate demographic data from RSA ID if provided
        AutoPopulateFromRsaId(person);

        var beforeState = new
        {
            existing.Title,
            existing.FirstName,
            existing.MiddleName,
            existing.LastName,
            existing.RsaIdNumber,
            existing.PassportNumber,
            existing.AlternateIdTypeId,
            existing.DateOfBirth,
            existing.Gender,
            existing.GenderCode,
            existing.IsSouthAfricanCitizen,
            existing.CitizenStatusCode,
            existing.EquityCode,
            existing.DisabilityCode,
            existing.NationalityCode,
            existing.HomeLanguageCode,
            existing.ProvinceCode,
            existing.SeeingRatingId,
            existing.HearingRatingId,
            existing.WalkingRatingId,
            existing.RememberingRatingId,
            existing.CommunicatingRatingId,
            existing.SelfCareRatingId,
            existing.LastSchoolEmisNumber,
            existing.LastSchoolYear,
            existing.StatssaAreaCode,
            existing.PopiActStatusId,
            existing.PopiActConsentDate,
            existing.PreviousLastName,
            existing.PreviousAlternateId,
            existing.PreviousAlternateIdTypeId,
            existing.PreviousProviderCode,
            existing.PreviousProviderEtqaId,
            existing.Email,
            existing.PhoneNumber,
            existing.CellNumber,
            existing.FaxNumber,
            existing.PhysicalAddress,
            existing.PhysicalAddressPostalCode,
            existing.PostalAddress,
            existing.PostalAddressPostalCode,
            existing.IsActive
        };

        existing.Title = person.Title;
        existing.FirstName = person.FirstName;
        existing.MiddleName = person.MiddleName;
        existing.LastName = person.LastName;
        existing.RsaIdNumber = person.RsaIdNumber;
        existing.PassportNumber = person.PassportNumber;
        existing.AlternateIdTypeId = person.AlternateIdTypeId;
        existing.DateOfBirth = person.DateOfBirth;
        existing.Gender = person.Gender;
        existing.GenderCode = person.GenderCode;
        existing.IsSouthAfricanCitizen = person.IsSouthAfricanCitizen;
        existing.CitizenStatusCode = person.CitizenStatusCode;
        existing.EquityCode = person.EquityCode;
        existing.DisabilityCode = person.DisabilityCode;
        existing.NationalityCode = person.NationalityCode;
        existing.HomeLanguageCode = person.HomeLanguageCode;
        existing.ProvinceCode = person.ProvinceCode;
        existing.SeeingRatingId = person.SeeingRatingId;
        existing.HearingRatingId = person.HearingRatingId;
        existing.WalkingRatingId = person.WalkingRatingId;
        existing.RememberingRatingId = person.RememberingRatingId;
        existing.CommunicatingRatingId = person.CommunicatingRatingId;
        existing.SelfCareRatingId = person.SelfCareRatingId;
        existing.LastSchoolEmisNumber = person.LastSchoolEmisNumber;
        existing.LastSchoolYear = person.LastSchoolYear;
        existing.StatssaAreaCode = person.StatssaAreaCode;
        existing.PopiActStatusId = person.PopiActStatusId;
        existing.PopiActConsentDate = person.PopiActConsentDate;
        existing.PreviousLastName = person.PreviousLastName;
        existing.PreviousAlternateId = person.PreviousAlternateId;
        existing.PreviousAlternateIdTypeId = person.PreviousAlternateIdTypeId;
        existing.PreviousProviderCode = person.PreviousProviderCode;
        existing.PreviousProviderEtqaId = person.PreviousProviderEtqaId;
        existing.Email = person.Email;
        existing.PhoneNumber = person.PhoneNumber;
        existing.CellNumber = person.CellNumber;
        existing.FaxNumber = person.FaxNumber;
        existing.PhysicalAddress = person.PhysicalAddress;
        existing.PhysicalAddressPostalCode = person.PhysicalAddressPostalCode;
        existing.PostalAddress = person.PostalAddress;
        existing.PostalAddressPostalCode = person.PostalAddressPostalCode;
        existing.IsActive = person.IsActive;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "Person", existing.Id, "Update", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();

        return existing;
    }

    public async Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var person = await db.People.FindAsync(id);
        if (person == null)
        {
            return false;
        }

        var beforeState = new
        {
            person.Id,
            person.FirstName,
            person.LastName,
            person.RsaIdNumber,
            person.Email,
            person.IsActive
        };

        person.IsActive = false;
        person.ModifiedAt = DateTime.UtcNow;
        person.ModifiedBy = currentUsername;

        _audit.LogAction(db, "Person", id, "Delete", currentUsername, beforeState, person);
        await db.SaveChangesAsync();

        return true;
    }

    private static void AutoPopulateFromRsaId(Person person)
    {
        if (string.IsNullOrWhiteSpace(person.RsaIdNumber))
        {
            return;
        }

        var parseResult = RsaIdValidator.Parse(person.RsaIdNumber);
        if (parseResult.IsValid)
        {
            if (parseResult.DateOfBirth.HasValue)
            {
                person.DateOfBirth = parseResult.DateOfBirth.Value;
            }

            if (!string.IsNullOrWhiteSpace(parseResult.Gender))
            {
                person.Gender = parseResult.Gender;
                person.GenderCode = parseResult.Gender.StartsWith("M", StringComparison.OrdinalIgnoreCase) ? "M" : "F";
            }

            if (parseResult.IsSouthAfricanCitizen.HasValue)
            {
                person.IsSouthAfricanCitizen = parseResult.IsSouthAfricanCitizen.Value;
                person.CitizenStatusCode = parseResult.IsSouthAfricanCitizen.Value ? "SA_CIT" : "PERM_RES";
            }
        }
    }
}
