using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Utilities;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IPersonService
{
    Task<List<Person>> GetAllAsync(string? search = null);
    Task<Person?> GetByIdAsync(int id);
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

    public async Task<Person> CreateAsync(Person person, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
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
            existing.DateOfBirth,
            existing.Gender,
            existing.GenderCode,
            existing.IsSouthAfricanCitizen,
            existing.EquityCode,
            existing.DisabilityCode,
            existing.NationalityCode,
            existing.HomeLanguageCode,
            existing.ProvinceCode,
            existing.Email,
            existing.PhoneNumber,
            existing.CellNumber,
            existing.PhysicalAddress,
            existing.PostalAddress,
            existing.IsActive
        };

        existing.Title = person.Title;
        existing.FirstName = person.FirstName;
        existing.MiddleName = person.MiddleName;
        existing.LastName = person.LastName;
        existing.RsaIdNumber = person.RsaIdNumber;
        existing.PassportNumber = person.PassportNumber;
        existing.DateOfBirth = person.DateOfBirth;
        existing.Gender = person.Gender;
        existing.GenderCode = person.GenderCode;
        existing.IsSouthAfricanCitizen = person.IsSouthAfricanCitizen;
        existing.EquityCode = person.EquityCode;
        existing.DisabilityCode = person.DisabilityCode;
        existing.NationalityCode = person.NationalityCode;
        existing.HomeLanguageCode = person.HomeLanguageCode;
        existing.ProvinceCode = person.ProvinceCode;
        existing.Email = person.Email;
        existing.PhoneNumber = person.PhoneNumber;
        existing.CellNumber = person.CellNumber;
        existing.PhysicalAddress = person.PhysicalAddress;
        existing.PostalAddress = person.PostalAddress;
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

        db.People.Remove(person);
        _audit.LogAction(db, "Person", id, "Delete", currentUsername, beforeState, null);
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
                person.GenderCode ??= parseResult.Gender.StartsWith("M", StringComparison.OrdinalIgnoreCase) ? "M" : "F";
            }

            if (parseResult.IsSouthAfricanCitizen.HasValue)
            {
                person.IsSouthAfricanCitizen = parseResult.IsSouthAfricanCitizen.Value;
            }
        }
    }
}
