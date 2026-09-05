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

    // Partitioned Satellite Operations
    Task<PersonContact?> GetContactAsync(int personId);
    Task<PersonContact> UpdateContactAsync(int personId, PersonContact contact, string currentUsername = "SYSTEM");
    Task<PersonDemographics?> GetDemographicsAsync(int personId);
    Task<PersonDemographics> UpdateDemographicsAsync(int personId, PersonDemographics demographics, string currentUsername = "SYSTEM");
    Task<PersonDisabilityRating?> GetDisabilityRatingAsync(int personId);
    Task<PersonDisabilityRating> UpdateDisabilityRatingAsync(int personId, PersonDisabilityRating rating, string currentUsername = "SYSTEM");

    // 360-Degree Relational Queries
    Task<List<Nsdms.Application.Common.Models.PersonLearnerDto>> GetPersonLearnersAsync(int personId);
    Task<List<Nsdms.Application.Common.Models.PersonEmployerLinkDto>> GetPersonEmployersAsync(int personId);
    Task<List<Nsdms.Application.Common.Models.PersonEtqaDto>> GetPersonEtqaPractitionersAsync(int personId);
    Task<List<Nsdms.Application.Common.Models.PersonMentorDto>> GetPersonMentorshipsAsync(int personId);
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
        return await db.People
            .Include(p => p.Contact)
            .Include(p => p.Demographics)
            .Include(p => p.DisabilityRating)
            .FirstOrDefaultAsync(p => p.Id == id);
    }

    public async Task<Person?> GetByRsaIdAsync(string rsaId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.People
            .Include(p => p.Contact)
            .Include(p => p.Demographics)
            .Include(p => p.DisabilityRating)
            .FirstOrDefaultAsync(p => p.RsaIdNumber == rsaId);
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

        // Synchronize initial satellite records
        var contact = new PersonContact
        {
            PersonId = person.Id,
            Email = person.Email,
            PhoneNumber = person.PhoneNumber,
            CellNumber = person.CellNumber,
            FaxNumber = person.FaxNumber,
            PhysicalAddress = person.PhysicalAddress,
            PhysicalAddressPostalCode = person.PhysicalAddressPostalCode,
            PostalAddress = person.PostalAddress,
            PostalAddressPostalCode = person.PostalAddressPostalCode,
            ProvinceCode = person.ProvinceCode,
            StatssaAreaCode = person.StatssaAreaCode,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        var demographics = new PersonDemographics
        {
            PersonId = person.Id,
            EquityCode = person.EquityCode,
            DisabilityCode = person.DisabilityCode,
            NationalityCode = person.NationalityCode,
            HomeLanguageCode = person.HomeLanguageCode,
            CitizenStatusCode = person.CitizenStatusCode,
            PopiActStatusId = person.PopiActStatusId ?? "01",
            PopiActConsentDate = person.PopiActConsentDate ?? DateTime.UtcNow,
            LastSchoolEmisNumber = person.LastSchoolEmisNumber,
            LastSchoolYear = person.LastSchoolYear,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        var disabilityRating = new PersonDisabilityRating
        {
            PersonId = person.Id,
            DisabilityCode = person.DisabilityCode ?? "00",
            SeeingRatingId = person.SeeingRatingId ?? "01",
            HearingRatingId = person.HearingRatingId ?? "01",
            WalkingRatingId = person.WalkingRatingId ?? "01",
            RememberingRatingId = person.RememberingRatingId ?? "01",
            CommunicatingRatingId = person.CommunicatingRatingId ?? "01",
            SelfCareRatingId = person.SelfCareRatingId ?? "01",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.PersonContacts.Add(contact);
        db.PersonDemographics.Add(demographics);
        db.PersonDisabilityRatings.Add(disabilityRating);
        await db.SaveChangesAsync();

        person.Contact = contact;
        person.Demographics = demographics;
        person.DisabilityRating = disabilityRating;

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

        // Synchronize satellite records
        var contact = await db.PersonContacts.FirstOrDefaultAsync(c => c.PersonId == existing.Id);
        if (contact == null)
        {
            contact = new PersonContact { PersonId = existing.Id, CreatedAt = DateTime.UtcNow, CreatedBy = currentUsername };
            db.PersonContacts.Add(contact);
        }
        contact.Email = existing.Email;
        contact.PhoneNumber = existing.PhoneNumber;
        contact.CellNumber = existing.CellNumber;
        contact.FaxNumber = existing.FaxNumber;
        contact.PhysicalAddress = existing.PhysicalAddress;
        contact.PhysicalAddressPostalCode = existing.PhysicalAddressPostalCode;
        contact.PostalAddress = existing.PostalAddress;
        contact.PostalAddressPostalCode = existing.PostalAddressPostalCode;
        contact.ProvinceCode = existing.ProvinceCode;
        contact.StatssaAreaCode = existing.StatssaAreaCode;
        contact.ModifiedAt = DateTime.UtcNow;
        contact.ModifiedBy = currentUsername;

        var demographics = await db.PersonDemographics.FirstOrDefaultAsync(d => d.PersonId == existing.Id);
        if (demographics == null)
        {
            demographics = new PersonDemographics { PersonId = existing.Id, CreatedAt = DateTime.UtcNow, CreatedBy = currentUsername };
            db.PersonDemographics.Add(demographics);
        }
        demographics.EquityCode = existing.EquityCode;
        demographics.DisabilityCode = existing.DisabilityCode;
        demographics.NationalityCode = existing.NationalityCode;
        demographics.HomeLanguageCode = existing.HomeLanguageCode;
        demographics.CitizenStatusCode = existing.CitizenStatusCode;
        demographics.PopiActStatusId = existing.PopiActStatusId ?? "01";
        demographics.PopiActConsentDate = existing.PopiActConsentDate;
        demographics.LastSchoolEmisNumber = existing.LastSchoolEmisNumber;
        demographics.LastSchoolYear = existing.LastSchoolYear;
        demographics.ModifiedAt = DateTime.UtcNow;
        demographics.ModifiedBy = currentUsername;

        var disabilityRating = await db.PersonDisabilityRatings.FirstOrDefaultAsync(r => r.PersonId == existing.Id);
        if (disabilityRating == null)
        {
            disabilityRating = new PersonDisabilityRating { PersonId = existing.Id, CreatedAt = DateTime.UtcNow, CreatedBy = currentUsername };
            db.PersonDisabilityRatings.Add(disabilityRating);
        }
        disabilityRating.DisabilityCode = existing.DisabilityCode ?? "00";
        disabilityRating.SeeingRatingId = existing.SeeingRatingId ?? "01";
        disabilityRating.HearingRatingId = existing.HearingRatingId ?? "01";
        disabilityRating.WalkingRatingId = existing.WalkingRatingId ?? "01";
        disabilityRating.RememberingRatingId = existing.RememberingRatingId ?? "01";
        disabilityRating.CommunicatingRatingId = existing.CommunicatingRatingId ?? "01";
        disabilityRating.SelfCareRatingId = existing.SelfCareRatingId ?? "01";
        disabilityRating.ModifiedAt = DateTime.UtcNow;
        disabilityRating.ModifiedBy = currentUsername;

        _audit.LogAction(db, "Person", existing.Id, "Update", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();

        existing.Contact = contact;
        existing.Demographics = demographics;
        existing.DisabilityRating = disabilityRating;

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

    #region Partitioned Satellite Operations

    public async Task<PersonContact?> GetContactAsync(int personId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.PersonContacts.FirstOrDefaultAsync(c => c.PersonId == personId);
    }

    public async Task<PersonContact> UpdateContactAsync(int personId, PersonContact contact, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.PersonContacts.FirstOrDefaultAsync(c => c.PersonId == personId);
        var parentPerson = await db.People.FirstOrDefaultAsync(p => p.Id == personId);
        if (parentPerson == null)
        {
            throw new KeyNotFoundException($"Parent Person with ID {personId} was not found.");
        }

        var isNew = existing == null;
        if (isNew)
        {
            existing = new PersonContact
            {
                PersonId = personId,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            };
            db.PersonContacts.Add(existing);
        }

        var beforeState = isNew ? null : (object)new
        {
            existing!.Email,
            existing.PhoneNumber,
            existing.CellNumber,
            existing.FaxNumber,
            existing.PhysicalAddress,
            existing.PhysicalAddressPostalCode,
            existing.PostalAddress,
            existing.PostalAddressPostalCode,
            existing.ProvinceCode,
            existing.StatssaAreaCode
        };

        existing!.Email = contact.Email;
        existing.PhoneNumber = contact.PhoneNumber;
        existing.CellNumber = contact.CellNumber;
        existing.FaxNumber = contact.FaxNumber;
        existing.PhysicalAddress = contact.PhysicalAddress;
        existing.PhysicalAddressPostalCode = contact.PhysicalAddressPostalCode;
        existing.PostalAddress = contact.PostalAddress;
        existing.PostalAddressPostalCode = contact.PostalAddressPostalCode;
        existing.ProvinceCode = contact.ProvinceCode;
        existing.StatssaAreaCode = contact.StatssaAreaCode;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        // Keep parent Person in sync for zero-breaking backward compatibility
        parentPerson.Email = contact.Email;
        parentPerson.PhoneNumber = contact.PhoneNumber;
        parentPerson.CellNumber = contact.CellNumber;
        parentPerson.FaxNumber = contact.FaxNumber;
        parentPerson.PhysicalAddress = contact.PhysicalAddress;
        parentPerson.PhysicalAddressPostalCode = contact.PhysicalAddressPostalCode;
        parentPerson.PostalAddress = contact.PostalAddress;
        parentPerson.PostalAddressPostalCode = contact.PostalAddressPostalCode;
        parentPerson.ProvinceCode = contact.ProvinceCode;
        parentPerson.StatssaAreaCode = contact.StatssaAreaCode;
        parentPerson.ModifiedAt = DateTime.UtcNow;
        parentPerson.ModifiedBy = currentUsername;

        _audit.LogAction(db, "PersonContact", existing.Id, isNew ? "Create" : "Update", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();

        return existing;
    }

    public async Task<PersonDemographics?> GetDemographicsAsync(int personId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.PersonDemographics.FirstOrDefaultAsync(d => d.PersonId == personId);
    }

    public async Task<PersonDemographics> UpdateDemographicsAsync(int personId, PersonDemographics demographics, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.PersonDemographics.FirstOrDefaultAsync(d => d.PersonId == personId);
        var parentPerson = await db.People.FirstOrDefaultAsync(p => p.Id == personId);
        if (parentPerson == null)
        {
            throw new KeyNotFoundException($"Parent Person with ID {personId} was not found.");
        }

        var isNew = existing == null;
        if (isNew)
        {
            existing = new PersonDemographics
            {
                PersonId = personId,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            };
            db.PersonDemographics.Add(existing);
        }

        var beforeState = isNew ? null : (object)new
        {
            existing!.EquityCode,
            existing.DisabilityCode,
            existing.NationalityCode,
            existing.HomeLanguageCode,
            existing.CitizenStatusCode,
            existing.PopiActStatusId,
            existing.PopiActConsentDate,
            existing.LastSchoolEmisNumber,
            existing.LastSchoolYear
        };

        existing!.EquityCode = demographics.EquityCode;
        existing.DisabilityCode = demographics.DisabilityCode;
        existing.NationalityCode = demographics.NationalityCode;
        existing.HomeLanguageCode = demographics.HomeLanguageCode;
        existing.CitizenStatusCode = demographics.CitizenStatusCode;
        existing.PopiActStatusId = demographics.PopiActStatusId ?? "01";
        existing.PopiActConsentDate = demographics.PopiActConsentDate;
        existing.LastSchoolEmisNumber = demographics.LastSchoolEmisNumber;
        existing.LastSchoolYear = demographics.LastSchoolYear;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        // Keep parent Person in sync for zero-breaking backward compatibility
        parentPerson.EquityCode = demographics.EquityCode;
        parentPerson.DisabilityCode = demographics.DisabilityCode;
        parentPerson.NationalityCode = demographics.NationalityCode;
        parentPerson.HomeLanguageCode = demographics.HomeLanguageCode;
        parentPerson.CitizenStatusCode = demographics.CitizenStatusCode;
        parentPerson.PopiActStatusId = demographics.PopiActStatusId ?? "01";
        parentPerson.PopiActConsentDate = demographics.PopiActConsentDate;
        parentPerson.LastSchoolEmisNumber = demographics.LastSchoolEmisNumber;
        parentPerson.LastSchoolYear = demographics.LastSchoolYear;
        parentPerson.ModifiedAt = DateTime.UtcNow;
        parentPerson.ModifiedBy = currentUsername;

        _audit.LogAction(db, "PersonDemographics", existing.Id, isNew ? "Create" : "Update", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();

        return existing;
    }

    public async Task<PersonDisabilityRating?> GetDisabilityRatingAsync(int personId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.PersonDisabilityRatings.FirstOrDefaultAsync(r => r.PersonId == personId);
    }

    public async Task<PersonDisabilityRating> UpdateDisabilityRatingAsync(int personId, PersonDisabilityRating rating, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.PersonDisabilityRatings.FirstOrDefaultAsync(r => r.PersonId == personId);
        var parentPerson = await db.People.FirstOrDefaultAsync(p => p.Id == personId);
        if (parentPerson == null)
        {
            throw new KeyNotFoundException($"Parent Person with ID {personId} was not found.");
        }

        var isNew = existing == null;
        if (isNew)
        {
            existing = new PersonDisabilityRating
            {
                PersonId = personId,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            };
            db.PersonDisabilityRatings.Add(existing);
        }

        var beforeState = isNew ? null : (object)new
        {
            existing!.DisabilityCode,
            existing.SeeingRatingId,
            existing.HearingRatingId,
            existing.WalkingRatingId,
            existing.RememberingRatingId,
            existing.CommunicatingRatingId,
            existing.SelfCareRatingId,
            existing.DisabilitySupportNotes,
            existing.IsDisabilityAssessed,
            existing.AssessedDate,
            existing.AssessedBy
        };

        existing!.DisabilityCode = rating.DisabilityCode ?? "00";
        existing.SeeingRatingId = rating.SeeingRatingId ?? "01";
        existing.HearingRatingId = rating.HearingRatingId ?? "01";
        existing.WalkingRatingId = rating.WalkingRatingId ?? "01";
        existing.RememberingRatingId = rating.RememberingRatingId ?? "01";
        existing.CommunicatingRatingId = rating.CommunicatingRatingId ?? "01";
        existing.SelfCareRatingId = rating.SelfCareRatingId ?? "01";
        existing.DisabilitySupportNotes = rating.DisabilitySupportNotes;
        existing.IsDisabilityAssessed = rating.IsDisabilityAssessed;
        existing.AssessedDate = rating.AssessedDate;
        existing.AssessedBy = rating.AssessedBy;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        // Keep parent Person in sync for zero-breaking backward compatibility
        parentPerson.DisabilityCode = rating.DisabilityCode ?? "00";
        parentPerson.SeeingRatingId = rating.SeeingRatingId ?? "01";
        parentPerson.HearingRatingId = rating.HearingRatingId ?? "01";
        parentPerson.WalkingRatingId = rating.WalkingRatingId ?? "01";
        parentPerson.RememberingRatingId = rating.RememberingRatingId ?? "01";
        parentPerson.CommunicatingRatingId = rating.CommunicatingRatingId ?? "01";
        parentPerson.SelfCareRatingId = rating.SelfCareRatingId ?? "01";
        parentPerson.ModifiedAt = DateTime.UtcNow;
        parentPerson.ModifiedBy = currentUsername;

        _audit.LogAction(db, "PersonDisabilityRating", existing.Id, isNew ? "Create" : "Update", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();

        return existing;
    }

    #endregion

    #region 360-Degree Relational Queries

    public async Task<List<PersonLearnerDto>> GetPersonLearnersAsync(int personId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var learners = await db.CompanyLearners
            .Include(l => l.Organisation)
            .Where(l => l.PersonId == personId)
            .OrderByDescending(l => l.RegistrationDate)
            .ToListAsync();

        return learners.Select(l => new PersonLearnerDto(
            l.Id,
            l.LearnerContractNumber,
            l.Organisation?.CompanyName ?? "Unknown Employer",
            l.OrganisationId,
            l.QualificationTitle ?? "Skills Programme",
            GetProgrammeTypeName(l.LearningProgrammeTypeCode),
            l.EnrolmentStatusCode ?? "Registered",
            l.RegistrationDate
        )).ToList();
    }

    public async Task<List<PersonEmployerLinkDto>> GetPersonEmployersAsync(int personId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var list = new List<PersonEmployerLinkDto>();

        // 1. Primary contact person link
        var primaryOrgs = await db.Organisations
            .Where(o => o.PrimaryContactPersonId == personId)
            .ToListAsync();

        foreach (var org in primaryOrgs)
        {
            list.Add(new PersonEmployerLinkDto(
                org.Id,
                org.CompanyName,
                org.SdlNumber,
                "Primary Contact Person / Executive",
                null,
                null
            ));
        }

        // 2. Training committee membership
        var committeeMembers = await db.TrainingCommitteeMembers
            .Include(m => m.TrainingCommittee)
                .ThenInclude(tc => tc!.Organisation)
            .Where(m => m.PersonId == personId && m.TrainingCommittee != null && m.TrainingCommittee.Organisation != null)
            .ToListAsync();

        foreach (var m in committeeMembers)
        {
            var org = m.TrainingCommittee!.Organisation!;
            if (!list.Any(x => x.OrganisationId == org.Id))
            {
                list.Add(new PersonEmployerLinkDto(
                    org.Id,
                    org.CompanyName,
                    org.SdlNumber,
                    $"Training Committee Member ({m.Constituency} - {m.MemberRoleCode})",
                    null,
                    null
                ));
            }
        }

        return list;
    }

    public async Task<List<PersonEtqaDto>> GetPersonEtqaPractitionersAsync(int personId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var assessors = await db.EtqaAssessors
            .Include(a => a.Scopes)
            .Where(a => a.PersonId == personId)
            .OrderByDescending(a => a.StartDate)
            .ToListAsync();

        return assessors.Select(a => new PersonEtqaDto(
            a.Id,
            a.RegistrationNumber,
            a.EtqaRole ?? "Assessor",
            a.RegistrationStatusCode ?? "Active",
            a.StartDate,
            a.EndDate,
            a.Scopes.Count
        )).ToList();
    }

    public async Task<List<PersonMentorDto>> GetPersonMentorshipsAsync(int personId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var mentors = await db.WorkplaceApprovalMentors
            .Include(m => m.WorkplaceApproval)
                .ThenInclude(w => w!.Organisation)
            .Where(m => m.PersonId == personId && m.WorkplaceApproval != null)
            .ToListAsync();

        return mentors.Select(m => new PersonMentorDto(
            m.Id,
            m.WorkplaceApprovalId,
            m.WorkplaceApproval?.Organisation?.CompanyName ?? "Approved Workshop",
            m.WorkplaceApproval?.QualificationTitle ?? "Trade",
            m.Designation ?? "Artisan Mentor",
            m.ArtisanTradeNumber,
            m.YearsExperience,
            m.IsCertifiedArtisan
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

    #endregion
}
