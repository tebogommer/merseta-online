using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using ClosedXML.Excel;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Models;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;

namespace Nsdms.Infrastructure.Services;

public class OrganisationEmployeeService : IOrganisationEmployeeService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly ILogger<OrganisationEmployeeService>? _logger;

    private static readonly (string Code, string Name, string OfoPrefix)[] StandardCategories = new[]
    {
        ("MANAGERS", "Managers", "1"),
        ("PROFESSIONALS", "Professionals", "2"),
        ("TECHNICIANS", "Technicians and Associate Professionals", "3"),
        ("CLERICAL", "Clerical Support Workers", "4"),
        ("SERVICE_SALES", "Service and Sales Workers", "5"),
        ("SKILLED_CRAFT", "Skilled Agricultural, Forestry, Fishery, Craft and Related Trades Workers", "6"),
        ("PLANT_OPERATORS", "Plant and Machine Operators and Assemblers", "7"),
        ("ELEMENTARY", "Elementary Occupations", "8")
    };

    public OrganisationEmployeeService(
        INsdmsDbContextFactory contextFactory,
        IAuditService audit,
        ILogger<OrganisationEmployeeService>? logger = null)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _logger = logger;
    }

    public async Task<PagedResult<OrganisationEmployeeDto>> GetPagedEmployeesAsync(
        int organisationId,
        EmployeeFilterQuery query,
        CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var q = db.OrganisationEmployees
            .AsNoTracking()
            .Include(e => e.Person)
            .Include(e => e.OrganisationSite)
            .Include(e => e.OfoCode)
            .Where(e => e.OrganisationId == organisationId);

        if (query.IsActiveOnly == true)
        {
            q = q.Where(e => e.IsActive && (e.EndDate == null || e.EndDate > DateTime.UtcNow));
        }

        if (!string.IsNullOrWhiteSpace(query.OccupationalCategory))
        {
            var cat = query.OccupationalCategory.Trim().ToUpperInvariant();
            q = q.Where(e => e.OccupationalCategoryCode == cat);
        }

        if (!string.IsNullOrWhiteSpace(query.EmploymentStatus))
        {
            var st = query.EmploymentStatus.Trim().ToUpperInvariant();
            q = q.Where(e => e.EmploymentStatusCode == st);
        }

        if (query.OrganisationSiteId.HasValue && query.OrganisationSiteId.Value > 0)
        {
            q = q.Where(e => e.OrganisationSiteId == query.OrganisationSiteId.Value);
        }

        if (!string.IsNullOrWhiteSpace(query.SearchTerm))
        {
            var term = query.SearchTerm.Trim().ToLower();
            q = q.Where(e =>
                (e.EmployeeNumber != null && e.EmployeeNumber.ToLower().Contains(term)) ||
                (e.JobTitle != null && e.JobTitle.ToLower().Contains(term)) ||
                (e.Person != null && (
                    (e.Person.FirstName != null && e.Person.FirstName.ToLower().Contains(term)) ||
                    (e.Person.LastName != null && e.Person.LastName.ToLower().Contains(term)) ||
                    (e.Person.RsaIdNumber != null && e.Person.RsaIdNumber.Contains(term))
                )));
        }

        var totalCount = await q.CountAsync(ct);

        var items = await q
            .OrderBy(e => e.Person != null ? e.Person.LastName : "")
            .ThenBy(e => e.Person != null ? e.Person.FirstName : "")
            .Skip(query.PageIndex * query.PageSize)
            .Take(query.PageSize)
            .Select(e => new OrganisationEmployeeDto
            {
                Id = e.Id,
                OrganisationId = e.OrganisationId,
                PersonId = e.PersonId,
                FullName = e.Person != null ? $"{e.Person.FirstName} {e.Person.LastName}".Trim() : "N/A",
                FirstName = e.Person != null ? (e.Person.FirstName ?? "") : "",
                LastName = e.Person != null ? (e.Person.LastName ?? "") : "",
                RsaIdMasked = MaskPopia(e.Person != null ? e.Person.RsaIdNumber : null),
                PassportNumberMasked = MaskPopia(e.Person != null ? e.Person.PassportNumber : null),
                EmployeeNumber = e.EmployeeNumber,
                JobTitle = e.JobTitle,
                OfoCodeId = e.OfoCodeId,
                OfoDescription = e.OfoCode != null ? e.OfoCode.Description : null,
                EmploymentTypeCode = e.EmploymentTypeCode,
                EmploymentStatusCode = e.EmploymentStatusCode,
                OccupationalCategoryCode = e.OccupationalCategoryCode,
                SiteName = e.OrganisationSite != null ? e.OrganisationSite.SiteName : null,
                OrganisationSiteId = e.OrganisationSiteId,
                GenderCode = e.Person != null ? e.Person.GenderCode : null,
                EquityCode = e.Person != null ? e.Person.EquityCode : null,
                HasDisability = e.Person != null && !string.IsNullOrWhiteSpace(e.Person.DisabilityCode) && e.Person.DisabilityCode != "NONE",
                DateOfBirth = e.Person != null ? e.Person.DateOfBirth : null,
                StartDate = e.StartDate,
                EndDate = e.EndDate,
                IsActive = e.IsActive
            })
            .ToListAsync(ct);

        return new PagedResult<OrganisationEmployeeDto>
        {
            Items = items,
            TotalCount = totalCount,
            PageIndex = query.PageIndex,
            PageSize = query.PageSize
        };
    }

    public async Task<OrganisationEmployeeDto?> GetEmployeeByIdAsync(int id, CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var e = await db.OrganisationEmployees
            .AsNoTracking()
            .Include(x => x.Person)
            .Include(x => x.OrganisationSite)
            .Include(x => x.OfoCode)
            .FirstOrDefaultAsync(x => x.Id == id, ct);

        if (e == null) return null;

        return new OrganisationEmployeeDto
        {
            Id = e.Id,
            OrganisationId = e.OrganisationId,
            PersonId = e.PersonId,
            FullName = e.Person != null ? $"{e.Person.FirstName} {e.Person.LastName}".Trim() : "N/A",
            FirstName = e.Person != null ? (e.Person.FirstName ?? "") : "",
            LastName = e.Person != null ? (e.Person.LastName ?? "") : "",
            RsaIdMasked = MaskPopia(e.Person != null ? e.Person.RsaIdNumber : null),
            PassportNumberMasked = MaskPopia(e.Person != null ? e.Person.PassportNumber : null),
            EmployeeNumber = e.EmployeeNumber,
            JobTitle = e.JobTitle,
            OfoCodeId = e.OfoCodeId,
            OfoDescription = e.OfoCode != null ? e.OfoCode.Description : null,
            EmploymentTypeCode = e.EmploymentTypeCode,
            EmploymentStatusCode = e.EmploymentStatusCode,
            OccupationalCategoryCode = e.OccupationalCategoryCode,
            SiteName = e.OrganisationSite != null ? e.OrganisationSite.SiteName : null,
            OrganisationSiteId = e.OrganisationSiteId,
            GenderCode = e.Person != null ? e.Person.GenderCode : null,
            EquityCode = e.Person != null ? e.Person.EquityCode : null,
            HasDisability = e.Person != null && !string.IsNullOrWhiteSpace(e.Person.DisabilityCode) && e.Person.DisabilityCode != "NONE",
            DateOfBirth = e.Person != null ? e.Person.DateOfBirth : null,
            StartDate = e.StartDate,
            EndDate = e.EndDate,
            IsActive = e.IsActive
        };
    }

    public async Task<OrganisationEmployeeDto> CreateEmployeeAsync(
        CreateOrganisationEmployeeCommand cmd,
        string actor = "Admin",
        CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        int personId;
        Person person;

        if (cmd.ExistingPersonId.HasValue && cmd.ExistingPersonId.Value > 0)
        {
            person = await db.People.FindAsync(new object[] { cmd.ExistingPersonId.Value }, ct)
                ?? throw new KeyNotFoundException($"Person with ID {cmd.ExistingPersonId.Value} not found.");
            personId = person.Id;
        }
        else
        {
            var dob = cmd.DateOfBirth;
            if (!dob.HasValue && !string.IsNullOrWhiteSpace(cmd.RsaIdNumber) && cmd.RsaIdNumber.Length >= 6)
            {
                dob = DeriveDobFromRsaId(cmd.RsaIdNumber);
            }

            person = new Person
            {
                FirstName = cmd.FirstName?.Trim() ?? "",
                LastName = cmd.LastName?.Trim() ?? "",
                RsaIdNumber = cmd.RsaIdNumber?.Trim(),
                PassportNumber = cmd.PassportNumber?.Trim(),
                GenderCode = cmd.GenderCode?.Trim().ToUpperInvariant() ?? "M",
                Gender = cmd.GenderCode == "F" ? "Female" : "Male",
                EquityCode = cmd.EquityCode?.Trim().ToUpperInvariant() ?? "BA",
                DisabilityCode = cmd.DisabilityCode?.Trim(),
                DateOfBirth = dob,
                CreatedBy = actor,
                CreatedAt = DateTime.UtcNow
            };

            db.People.Add(person);
            await db.SaveChangesAsync(ct);
            personId = person.Id;
        }

        var employee = new OrganisationEmployee
        {
            OrganisationId = cmd.OrganisationId,
            PersonId = personId,
            OrganisationSiteId = cmd.OrganisationSiteId,
            EmployeeNumber = cmd.EmployeeNumber?.Trim(),
            JobTitle = cmd.JobTitle?.Trim(),
            OfoCodeId = cmd.OfoCodeId?.Trim(),
            EmploymentTypeCode = cmd.EmploymentTypeCode?.Trim().ToUpperInvariant() ?? "PERMANENT",
            EmploymentStatusCode = cmd.EmploymentStatusCode?.Trim().ToUpperInvariant() ?? "ACTIVE",
            OccupationalCategoryCode = cmd.OccupationalCategoryCode?.Trim().ToUpperInvariant() ?? "MANAGERS",
            StartDate = cmd.StartDate ?? DateTime.UtcNow.Date,
            EndDate = cmd.EndDate,
            IsActive = true,
            CreatedBy = actor,
            CreatedAt = DateTime.UtcNow
        };

        db.OrganisationEmployees.Add(employee);
        await db.SaveChangesAsync(ct);

        await _audit.LogActionAsync(
            "OrganisationEmployee",
            employee.Id,
            "CREATE_EMPLOYEE",
            actor,
            null,
            new
            {
                employee.Id,
                employee.OrganisationId,
                employee.PersonId,
                employee.EmployeeNumber,
                employee.JobTitle,
                employee.OccupationalCategoryCode,
                employee.IsActive
            });

        return new OrganisationEmployeeDto
        {
            Id = employee.Id,
            OrganisationId = employee.OrganisationId,
            PersonId = employee.PersonId,
            FullName = $"{person.FirstName} {person.LastName}".Trim(),
            FirstName = person.FirstName ?? "",
            LastName = person.LastName ?? "",
            RsaIdMasked = MaskPopia(person.RsaIdNumber),
            PassportNumberMasked = MaskPopia(person.PassportNumber),
            EmployeeNumber = employee.EmployeeNumber,
            JobTitle = employee.JobTitle,
            OfoCodeId = employee.OfoCodeId,
            EmploymentTypeCode = employee.EmploymentTypeCode,
            EmploymentStatusCode = employee.EmploymentStatusCode,
            OccupationalCategoryCode = employee.OccupationalCategoryCode,
            OrganisationSiteId = employee.OrganisationSiteId,
            GenderCode = person.GenderCode,
            EquityCode = person.EquityCode,
            HasDisability = !string.IsNullOrWhiteSpace(person.DisabilityCode) && person.DisabilityCode != "NONE",
            DateOfBirth = person.DateOfBirth,
            StartDate = employee.StartDate,
            EndDate = employee.EndDate,
            IsActive = employee.IsActive
        };
    }

    public async Task<OrganisationEmployeeDto> UpdateEmployeeAsync(
        UpdateOrganisationEmployeeCommand cmd,
        string actor = "Admin",
        CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var employee = await db.OrganisationEmployees
            .Include(e => e.Person)
            .Include(e => e.OrganisationSite)
            .Include(e => e.OfoCode)
            .FirstOrDefaultAsync(e => e.Id == cmd.Id, ct)
            ?? throw new KeyNotFoundException($"OrganisationEmployee with ID {cmd.Id} not found.");

        var beforeState = new
        {
            employee.JobTitle,
            employee.OfoCodeId,
            employee.OrganisationSiteId,
            employee.EmploymentTypeCode,
            employee.EmploymentStatusCode,
            employee.OccupationalCategoryCode,
            employee.StartDate,
            employee.EndDate,
            employee.IsActive
        };

        employee.JobTitle = cmd.JobTitle?.Trim();
        employee.OfoCodeId = cmd.OfoCodeId?.Trim();
        employee.OrganisationSiteId = cmd.OrganisationSiteId;
        if (!string.IsNullOrWhiteSpace(cmd.EmploymentTypeCode))
            employee.EmploymentTypeCode = cmd.EmploymentTypeCode.Trim().ToUpperInvariant();
        if (!string.IsNullOrWhiteSpace(cmd.EmploymentStatusCode))
            employee.EmploymentStatusCode = cmd.EmploymentStatusCode.Trim().ToUpperInvariant();
        if (!string.IsNullOrWhiteSpace(cmd.OccupationalCategoryCode))
            employee.OccupationalCategoryCode = cmd.OccupationalCategoryCode.Trim().ToUpperInvariant();
        employee.StartDate = cmd.StartDate;
        employee.EndDate = cmd.EndDate;
        employee.IsActive = cmd.IsActive;
        employee.ModifiedBy = actor;
        employee.ModifiedAt = DateTime.UtcNow;

        await db.SaveChangesAsync(ct);

        await _audit.LogActionAsync(
            "OrganisationEmployee",
            employee.Id,
            "UPDATE_EMPLOYEE",
            actor,
            beforeState,
            new
            {
                employee.JobTitle,
                employee.OfoCodeId,
                employee.OrganisationSiteId,
                employee.EmploymentTypeCode,
                employee.EmploymentStatusCode,
                employee.OccupationalCategoryCode,
                employee.StartDate,
                employee.EndDate,
                employee.IsActive
            });

        return new OrganisationEmployeeDto
        {
            Id = employee.Id,
            OrganisationId = employee.OrganisationId,
            PersonId = employee.PersonId,
            FullName = employee.Person != null ? $"{employee.Person.FirstName} {employee.Person.LastName}".Trim() : "N/A",
            FirstName = employee.Person != null ? (employee.Person.FirstName ?? "") : "",
            LastName = employee.Person != null ? (employee.Person.LastName ?? "") : "",
            RsaIdMasked = MaskPopia(employee.Person != null ? employee.Person.RsaIdNumber : null),
            PassportNumberMasked = MaskPopia(employee.Person != null ? employee.Person.PassportNumber : null),
            EmployeeNumber = employee.EmployeeNumber,
            JobTitle = employee.JobTitle,
            OfoCodeId = employee.OfoCodeId,
            OfoDescription = employee.OfoCode != null ? employee.OfoCode.Description : null,
            EmploymentTypeCode = employee.EmploymentTypeCode,
            EmploymentStatusCode = employee.EmploymentStatusCode,
            OccupationalCategoryCode = employee.OccupationalCategoryCode,
            SiteName = employee.OrganisationSite != null ? employee.OrganisationSite.SiteName : null,
            OrganisationSiteId = employee.OrganisationSiteId,
            GenderCode = employee.Person != null ? employee.Person.GenderCode : null,
            EquityCode = employee.Person != null ? employee.Person.EquityCode : null,
            HasDisability = employee.Person != null && !string.IsNullOrWhiteSpace(employee.Person.DisabilityCode) && employee.Person.DisabilityCode != "NONE",
            DateOfBirth = employee.Person != null ? employee.Person.DateOfBirth : null,
            StartDate = employee.StartDate,
            EndDate = employee.EndDate,
            IsActive = employee.IsActive
        };
    }

    public async Task<bool> TerminateEmployeeAsync(
        int id,
        DateTime terminationDate,
        string? reason = null,
        string actor = "Admin",
        CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var employee = await db.OrganisationEmployees.FindAsync(new object[] { id }, ct);
        if (employee == null) return false;

        var beforeState = new { employee.IsActive, employee.EndDate, employee.EmploymentStatusCode };

        employee.IsActive = false;
        employee.EndDate = terminationDate;
        employee.EmploymentStatusCode = "TERMINATED";
        employee.ModifiedBy = actor;
        employee.ModifiedAt = DateTime.UtcNow;

        await db.SaveChangesAsync(ct);

        await _audit.LogActionAsync(
            "OrganisationEmployee",
            employee.Id,
            "TERMINATE_EMPLOYEE",
            actor,
            beforeState,
            new { employee.IsActive, employee.EndDate, employee.EmploymentStatusCode, Reason = reason });

        return true;
    }

    public async Task<BulkEmployeeImportResultDto> ImportEmployeesAsync(
        int organisationId,
        Stream fileStream,
        string fileName,
        string actor = "Admin",
        CancellationToken ct = default)
    {
        var result = new BulkEmployeeImportResultDto();

        try
        {
            using var ms = new MemoryStream();
            await fileStream.CopyToAsync(ms, ct);
            ms.Position = 0;

            using var workbook = new XLWorkbook(ms);
            var ws = workbook.Worksheets.FirstOrDefault() ?? throw new InvalidOperationException("Excel file contains no worksheets.");

            var rows = ws.RowsUsed().Skip(1);
            using var db = await _contextFactory.CreateDbContextAsync();

            var existingPersonsByRsaId = await db.People
                .Where(p => !string.IsNullOrEmpty(p.RsaIdNumber))
                .ToDictionaryAsync(p => p.RsaIdNumber!.Trim(), ct);

            var existingEmployees = await db.OrganisationEmployees
                .Where(e => e.OrganisationId == organisationId)
                .ToListAsync(ct);

            var sites = await db.OrganisationSites
                .Where(s => s.OrganisationId == organisationId)
                .ToDictionaryAsync(s => s.SiteName.Trim().ToLower(), s => s.Id, ct);

            int rowIndex = 1;
            foreach (var r in rows)
            {
                rowIndex++;
                result.TotalRows++;

                var empNumber = r.Cell(1).GetString()?.Trim();
                var firstName = r.Cell(2).GetString()?.Trim();
                var lastName = r.Cell(3).GetString()?.Trim();
                var rsaId = r.Cell(4).GetString()?.Trim();
                var gender = r.Cell(5).GetString()?.Trim()?.ToUpperInvariant() ?? "M";
                var equity = r.Cell(6).GetString()?.Trim()?.ToUpperInvariant() ?? "BA";
                var disability = r.Cell(7).GetString()?.Trim();
                var jobTitle = r.Cell(8).GetString()?.Trim();
                var ofoCode = r.Cell(9).GetString()?.Trim();
                var category = r.Cell(10).GetString()?.Trim()?.ToUpperInvariant() ?? "MANAGERS";
                var siteName = r.Cell(11).GetString()?.Trim();

                if (string.IsNullOrWhiteSpace(firstName) || string.IsNullOrWhiteSpace(lastName))
                {
                    result.Errors.Add($"Row {rowIndex}: First name and last name are required.");
                    result.SkippedCount++;
                    continue;
                }

                Person person;
                if (!string.IsNullOrWhiteSpace(rsaId) && existingPersonsByRsaId.TryGetValue(rsaId, out var p))
                {
                    person = p;
                }
                else
                {
                    var dob = DeriveDobFromRsaId(rsaId);
                    person = new Person
                    {
                        FirstName = firstName,
                        LastName = lastName,
                        RsaIdNumber = rsaId,
                        GenderCode = gender.StartsWith("F") ? "F" : "M",
                        Gender = gender.StartsWith("F") ? "Female" : "Male",
                        EquityCode = NormalizeEquityCode(equity),
                        DisabilityCode = string.IsNullOrWhiteSpace(disability) || disability.Equals("NONE", StringComparison.OrdinalIgnoreCase) ? null : disability,
                        DateOfBirth = dob,
                        CreatedBy = actor,
                        CreatedAt = DateTime.UtcNow
                    };
                    db.People.Add(person);
                    await db.SaveChangesAsync(ct);

                    if (!string.IsNullOrWhiteSpace(rsaId))
                    {
                        existingPersonsByRsaId[rsaId] = person;
                    }
                }

                int? siteId = null;
                if (!string.IsNullOrWhiteSpace(siteName) && sites.TryGetValue(siteName.ToLower(), out var sId))
                {
                    siteId = sId;
                }

                var existingEmp = existingEmployees.FirstOrDefault(e =>
                    e.PersonId == person.Id ||
                    (!string.IsNullOrWhiteSpace(empNumber) && string.Equals(e.EmployeeNumber, empNumber, StringComparison.OrdinalIgnoreCase)));

                if (existingEmp != null)
                {
                    existingEmp.JobTitle = jobTitle ?? existingEmp.JobTitle;
                    existingEmp.OfoCodeId = ofoCode ?? existingEmp.OfoCodeId;
                    existingEmp.OccupationalCategoryCode = NormalizeOccupationalCategory(category, ofoCode);
                    existingEmp.OrganisationSiteId = siteId ?? existingEmp.OrganisationSiteId;
                    existingEmp.IsActive = true;
                    existingEmp.ModifiedBy = actor;
                    existingEmp.ModifiedAt = DateTime.UtcNow;
                    result.UpdatedCount++;
                }
                else
                {
                    var newEmp = new OrganisationEmployee
                    {
                        OrganisationId = organisationId,
                        PersonId = person.Id,
                        OrganisationSiteId = siteId,
                        EmployeeNumber = empNumber,
                        JobTitle = jobTitle ?? "Staff Member",
                        OfoCodeId = ofoCode,
                        EmploymentTypeCode = "PERMANENT",
                        EmploymentStatusCode = "ACTIVE",
                        OccupationalCategoryCode = NormalizeOccupationalCategory(category, ofoCode),
                        StartDate = DateTime.UtcNow.Date,
                        IsActive = true,
                        CreatedBy = actor,
                        CreatedAt = DateTime.UtcNow
                    };
                    db.OrganisationEmployees.Add(newEmp);
                    result.InsertedCount++;
                }
            }

            await db.SaveChangesAsync(ct);
            result.Success = true;
            result.Message = $"Successfully processed {result.TotalRows} rows ({result.InsertedCount} new, {result.UpdatedCount} updated, {result.SkippedCount} skipped).";

            await _audit.LogActionAsync(
                "OrganisationEmployee",
                organisationId,
                "BULK_IMPORT_ROSTER",
                actor,
                null,
                new { FileName = fileName, result.TotalRows, result.InsertedCount, result.UpdatedCount });
        }
        catch (Exception ex)
        {
            _logger?.LogError(ex, "Failed to import employee roster from {FileName}", fileName);
            result.Success = false;
            result.Message = $"Import failed: {ex.Message}";
            result.Errors.Add(ex.Message);
        }

        return result;
    }

    public async Task<byte[]> ExportEmployeesTemplateAsync(CancellationToken ct = default)
    {
        using var wb = new XLWorkbook();
        var ws = wb.Worksheets.Add("Employee Roster Template");

        var headers = new[]
        {
            "Employee Number",
            "First Name",
            "Last Name",
            "RSA ID Number",
            "Gender (M/F)",
            "Equity (African/Coloured/Indian/White)",
            "Disability (Yes/No/Code)",
            "Job Title",
            "OFO Code",
            "Occupational Category (1-8 or Managers/Professionals...)",
            "Site Name"
        };

        for (int i = 0; i < headers.Length; i++)
        {
            var cell = ws.Cell(1, i + 1);
            cell.Value = headers[i];
            cell.Style.Font.Bold = true;
            cell.Style.Fill.BackgroundColor = XLColor.FromHtml("#0A2540");
            cell.Style.Font.FontColor = XLColor.White;
        }

        ws.Cell(2, 1).Value = "EMP-001";
        ws.Cell(2, 2).Value = "Sipho";
        ws.Cell(2, 3).Value = "Dlamini";
        ws.Cell(2, 4).Value = "8501015009087";
        ws.Cell(2, 5).Value = "M";
        ws.Cell(2, 6).Value = "African";
        ws.Cell(2, 7).Value = "No";
        ws.Cell(2, 8).Value = "Production Operations Manager";
        ws.Cell(2, 9).Value = "132101";
        ws.Cell(2, 10).Value = "MANAGERS";
        ws.Cell(2, 11).Value = "Prospecton Assembly Plant";

        ws.Cell(3, 1).Value = "EMP-002";
        ws.Cell(3, 2).Value = "Nadine";
        ws.Cell(3, 3).Value = "Naidoo";
        ws.Cell(3, 4).Value = "9203150123089";
        ws.Cell(3, 5).Value = "F";
        ws.Cell(3, 6).Value = "Indian";
        ws.Cell(3, 7).Value = "No";
        ws.Cell(3, 8).Value = "Senior Automation Mechanical Engineer";
        ws.Cell(3, 9).Value = "214401";
        ws.Cell(3, 10).Value = "PROFESSIONALS";
        ws.Cell(3, 11).Value = "Prospecton Assembly Plant";

        ws.Columns().AdjustToContents();

        using var ms = new MemoryStream();
        wb.SaveAs(ms);
        return ms.ToArray();
    }

    public async Task<byte[]> ExportEmployeesAsync(int organisationId, CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var employees = await db.OrganisationEmployees
            .AsNoTracking()
            .Include(e => e.Person)
            .Include(e => e.OrganisationSite)
            .Include(e => e.OfoCode)
            .Where(e => e.OrganisationId == organisationId)
            .OrderBy(e => e.Person != null ? e.Person.LastName : "")
            .ToListAsync(ct);

        using var wb = new XLWorkbook();
        var ws = wb.Worksheets.Add("Active Employees");

        var headers = new[]
        {
            "Employee Number",
            "First Name",
            "Last Name",
            "Masked RSA ID",
            "Gender",
            "Equity",
            "Disability",
            "Job Title",
            "OFO Code",
            "OFO Description",
            "Occupational Category",
            "Employment Type",
            "Status",
            "Site Name",
            "Start Date"
        };

        for (int i = 0; i < headers.Length; i++)
        {
            var cell = ws.Cell(1, i + 1);
            cell.Value = headers[i];
            cell.Style.Font.Bold = true;
            cell.Style.Fill.BackgroundColor = XLColor.FromHtml("#0A2540");
            cell.Style.Font.FontColor = XLColor.White;
        }

        int row = 2;
        foreach (var e in employees)
        {
            ws.Cell(row, 1).Value = e.EmployeeNumber ?? "";
            ws.Cell(row, 2).Value = e.Person?.FirstName ?? "";
            ws.Cell(row, 3).Value = e.Person?.LastName ?? "";
            ws.Cell(row, 4).Value = MaskPopia(e.Person?.RsaIdNumber);
            ws.Cell(row, 5).Value = e.Person?.GenderCode ?? "";
            ws.Cell(row, 6).Value = e.Person?.EquityCode ?? "";
            ws.Cell(row, 7).Value = (!string.IsNullOrWhiteSpace(e.Person?.DisabilityCode) && e.Person?.DisabilityCode != "NONE") ? "Yes" : "No";
            ws.Cell(row, 8).Value = e.JobTitle ?? "";
            ws.Cell(row, 9).Value = e.OfoCodeId ?? "";
            ws.Cell(row, 10).Value = e.OfoCode?.Description ?? "";
            ws.Cell(row, 11).Value = e.OccupationalCategoryCode ?? "";
            ws.Cell(row, 12).Value = e.EmploymentTypeCode ?? "";
            ws.Cell(row, 13).Value = e.IsActive ? "Active" : "Inactive";
            ws.Cell(row, 14).Value = e.OrganisationSite?.SiteName ?? "";
            ws.Cell(row, 15).Value = e.StartDate?.ToString("yyyy-MM-dd") ?? "";
            row++;
        }

        ws.Columns().AdjustToContents();

        using var ms = new MemoryStream();
        wb.SaveAs(ms);
        return ms.ToArray();
    }

    public async Task<WspHarvestResultDto> HarvestWspTablesAsync(
        int organisationId,
        int wspSubmissionId,
        string actor = "Admin",
        CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var wsp = await db.WspSubmissions.FindAsync(new object[] { wspSubmissionId }, ct)
            ?? throw new KeyNotFoundException($"WspSubmission with ID {wspSubmissionId} not found.");

        var employees = await db.OrganisationEmployees
            .AsNoTracking()
            .Include(e => e.Person)
            .Include(e => e.OfoCode)
            .Where(e => e.OrganisationId == organisationId && e.IsActive && (e.EndDate == null || e.EndDate > DateTime.UtcNow))
            .ToListAsync(ct);

        var result = new WspHarvestResultDto
        {
            WspSubmissionId = wspSubmissionId,
            TotalEmployeesHarvested = employees.Count
        };

        var existingSummaries = await db.WspEmploymentSummaries
            .Where(s => s.WspSubmissionId == wspSubmissionId)
            .ToListAsync(ct);

        int updatedCount = 0;

        foreach (var (code, name, prefix) in StandardCategories)
        {
            var catEmployees = employees.Where(e =>
            {
                var occCode = e.OccupationalCategoryCode?.Trim().ToUpperInvariant();
                if (occCode == code) return true;

                if (!string.IsNullOrEmpty(e.OfoCodeId) && e.OfoCodeId.StartsWith(prefix)) return true;

                if (occCode != null && occCode.Contains(code)) return true;

                return false;
            }).ToList();

            int maleAfrican = 0;
            int femaleAfrican = 0;
            int maleColoured = 0;
            int femaleColoured = 0;
            int maleIndian = 0;
            int femaleIndian = 0;
            int maleWhite = 0;
            int femaleWhite = 0;
            int disabledCount = 0;

            foreach (var emp in catEmployees)
            {
                var person = emp.Person;
                if (person == null) continue;

                bool isFemale = string.Equals(person.GenderCode, "F", StringComparison.OrdinalIgnoreCase) ||
                                string.Equals(person.Gender, "Female", StringComparison.OrdinalIgnoreCase);

                var eq = person.EquityCode?.Trim().ToUpperInvariant() ?? "BA";
                bool isAfrican = eq == "BA" || eq == "AFRICAN";
                bool isColoured = eq == "BC" || eq == "COLOURED";
                bool isIndian = eq == "BI" || eq == "INDIAN" || eq == "ASIAN";
                bool isWhite = eq == "WH" || eq == "WHITE";

                if (isFemale)
                {
                    if (isAfrican) femaleAfrican++;
                    else if (isColoured) femaleColoured++;
                    else if (isIndian) femaleIndian++;
                    else if (isWhite) femaleWhite++;
                    else femaleAfrican++;
                }
                else
                {
                    if (isAfrican) maleAfrican++;
                    else if (isColoured) maleColoured++;
                    else if (isIndian) maleIndian++;
                    else if (isWhite) maleWhite++;
                    else maleAfrican++;
                }

                if (!string.IsNullOrWhiteSpace(person.DisabilityCode) && person.DisabilityCode != "NONE")
                {
                    disabledCount++;
                }
            }

            int catTotal = catEmployees.Count;

            var existing = existingSummaries.FirstOrDefault(s =>
                string.Equals(s.OccupationalCategory, name, StringComparison.OrdinalIgnoreCase) ||
                string.Equals(s.OccupationalCategory, code, StringComparison.OrdinalIgnoreCase) ||
                (s.OfoCode != null && s.OfoCode.StartsWith(prefix)));

            if (existing != null)
            {
                existing.OccupationalCategory = name;
                existing.OfoCode = prefix + "00000";
                existing.MaleAfrican = maleAfrican;
                existing.FemaleAfrican = femaleAfrican;
                existing.MaleColoured = maleColoured;
                existing.FemaleColoured = femaleColoured;
                existing.MaleIndian = maleIndian;
                existing.FemaleIndian = femaleIndian;
                existing.MaleWhite = maleWhite;
                existing.FemaleWhite = femaleWhite;
                existing.DisabledCount = disabledCount;
                existing.TotalEmployees = catTotal;
                existing.ModifiedBy = actor;
                existing.ModifiedAt = DateTime.UtcNow;
            }
            else
            {
                var newSummary = new WspEmploymentSummary
                {
                    WspSubmissionId = wspSubmissionId,
                    OccupationalCategory = name,
                    OfoCode = prefix + "00000",
                    MaleAfrican = maleAfrican,
                    FemaleAfrican = femaleAfrican,
                    MaleColoured = maleColoured,
                    FemaleColoured = femaleColoured,
                    MaleIndian = maleIndian,
                    FemaleIndian = femaleIndian,
                    MaleWhite = maleWhite,
                    FemaleWhite = femaleWhite,
                    DisabledCount = disabledCount,
                    TotalEmployees = catTotal,
                    CreatedBy = actor,
                    CreatedAt = DateTime.UtcNow
                };
                db.WspEmploymentSummaries.Add(newSummary);
            }

            updatedCount++;

            result.CategoryBreakdown.Add(new WspCategoryBreakdownDto
            {
                CategoryCode = code,
                CategoryName = name,
                MaleAfrican = maleAfrican,
                FemaleAfrican = femaleAfrican,
                MaleColoured = maleColoured,
                FemaleColoured = femaleColoured,
                MaleIndian = maleIndian,
                FemaleIndian = femaleIndian,
                MaleWhite = maleWhite,
                FemaleWhite = femaleWhite,
                DisabledCount = disabledCount,
                Total = catTotal
            });
        }

        wsp.EmployeeCount = employees.Count;
        wsp.ModifiedBy = actor;
        wsp.ModifiedAt = DateTime.UtcNow;

        await db.SaveChangesAsync(ct);

        result.Success = true;
        result.CategoriesUpdatedCount = updatedCount;
        result.Message = $"1-Click WSP Auto-Harvest completed successfully. Synchronized {employees.Count} active employees across {updatedCount} OFO major categories into WSP #{wspSubmissionId}.";

        await _audit.LogActionAsync(
            "WspSubmission",
            wspSubmissionId,
            "WSP_AUTO_HARVEST_EMPLOYEE_ROSTER",
            actor,
            null,
            new
            {
                organisationId,
                wspSubmissionId,
                result.TotalEmployeesHarvested,
                result.CategoriesUpdatedCount,
                HarvestedAt = DateTime.UtcNow
            });

        return result;
    }

    #region Helper Methods

    private static string MaskPopia(string? id)
    {
        if (string.IsNullOrWhiteSpace(id)) return "N/A";
        var clean = id.Trim();
        if (clean.Length <= 6) return "******";
        return string.Concat(clean.AsSpan(0, 4), "******", clean.AsSpan(clean.Length - 3));
    }

    private static DateTime? DeriveDobFromRsaId(string? rsaId)
    {
        if (string.IsNullOrWhiteSpace(rsaId) || rsaId.Length < 6) return null;
        try
        {
            var yy = int.Parse(rsaId.Substring(0, 2));
            var mm = int.Parse(rsaId.Substring(2, 2));
            var dd = int.Parse(rsaId.Substring(4, 2));

            int currentYear = DateTime.UtcNow.Year % 100;
            int fullYear = yy > currentYear ? 1900 + yy : 2000 + yy;

            if (mm >= 1 && mm <= 12 && dd >= 1 && dd <= 31)
            {
                return new DateTime(fullYear, mm, dd);
            }
        }
        catch
        {
        }
        return null;
    }

    private static string NormalizeEquityCode(string? equity)
    {
        if (string.IsNullOrWhiteSpace(equity)) return "BA";
        var eq = equity.Trim().ToUpperInvariant();
        if (eq.StartsWith("AFR") || eq == "BA") return "BA";
        if (eq.StartsWith("COL") || eq == "BC") return "BC";
        if (eq.StartsWith("IND") || eq.StartsWith("ASI") || eq == "BI") return "BI";
        if (eq.StartsWith("WHI") || eq == "WH") return "WH";
        return "BA";
    }

    private static string NormalizeOccupationalCategory(string? category, string? ofoCode)
    {
        if (!string.IsNullOrWhiteSpace(category))
        {
            var cat = category.Trim().ToUpperInvariant();
            if (cat.Contains("MAN")) return "MANAGERS";
            if (cat.Contains("PROF")) return "PROFESSIONALS";
            if (cat.Contains("TECH")) return "TECHNICIANS";
            if (cat.Contains("CLER")) return "CLERICAL";
            if (cat.Contains("SERV") || cat.Contains("SALE")) return "SERVICE_SALES";
            if (cat.Contains("CRAFT") || cat.Contains("ART") || cat.Contains("SKILL")) return "SKILLED_CRAFT";
            if (cat.Contains("PLANT") || cat.Contains("OPER")) return "PLANT_OPERATORS";
            if (cat.Contains("ELEM")) return "ELEMENTARY";
        }

        if (!string.IsNullOrWhiteSpace(ofoCode))
        {
            if (ofoCode.StartsWith("1")) return "MANAGERS";
            if (ofoCode.StartsWith("2")) return "PROFESSIONALS";
            if (ofoCode.StartsWith("3")) return "TECHNICIANS";
            if (ofoCode.StartsWith("4")) return "CLERICAL";
            if (ofoCode.StartsWith("5")) return "SERVICE_SALES";
            if (ofoCode.StartsWith("6")) return "SKILLED_CRAFT";
            if (ofoCode.StartsWith("7")) return "PLANT_OPERATORS";
            if (ofoCode.StartsWith("8")) return "ELEMENTARY";
        }

        return "MANAGERS";
    }

    #endregion
}
