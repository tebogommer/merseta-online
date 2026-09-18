using System;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using ClosedXML.Excel;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Common.Models;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class OrganisationEmployeeServiceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, OrganisationEmployeeService service) CreateTestService()
    {
        var dbName = Guid.NewGuid().ToString();
        var factory = new TestDbContextFactory(dbName);
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var service = new OrganisationEmployeeService(factory, audit);
        return (factory, db, service);
    }

    [Fact]
    public async Task CreateEmployeeAsync_WithRsaId_DerivesDobAndGenderAndMasksPopia()
    {
        var (factory, db, service) = CreateTestService();

        var org = new Organisation
        {
            CompanyName = "Toyota South Africa Motors",
            SdlNumber = "L700100200",
            IsActive = true
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var cmd = new CreateOrganisationEmployeeCommand
        {
            OrganisationId = org.Id,
            FirstName = "Sipho",
            LastName = "Khumalo",
            RsaIdNumber = "8501015009087", // 1985-01-01, Male (digit 7 is 5 >= 5)
            JobTitle = "Assembly Production Specialist",
            OfoCodeId = "671201",
            OccupationalCategoryCode = "SKILLED_CRAFT",
            EmploymentTypeCode = "PERMANENT"
        };

        var result = await service.CreateEmployeeAsync(cmd, "TestOfficer");

        Assert.NotNull(result);
        Assert.True(result.Id > 0);
        Assert.Equal("Sipho Khumalo", result.FullName);
        Assert.Equal("8501******087", result.RsaIdMasked);
        Assert.Equal("M", result.GenderCode);
        Assert.Equal(new DateTime(1985, 1, 1), result.DateOfBirth);
        Assert.True(result.IsActive);

        // Verify in DB
        var saved = await db.OrganisationEmployees.Include(e => e.Person).FirstOrDefaultAsync(e => e.Id == result.Id);
        Assert.NotNull(saved);
        Assert.NotNull(saved.Person);
        Assert.Equal("8501015009087", saved.Person.RsaIdNumber);
    }

    [Fact]
    public async Task GetPagedEmployeesAsync_FiltersByOccupationalCategory()
    {
        var (factory, db, service) = CreateTestService();

        var org = new Organisation { CompanyName = "BMW Rosslyn", SdlNumber = "L700100300", IsActive = true };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // 1. Manager
        await service.CreateEmployeeAsync(new CreateOrganisationEmployeeCommand
        {
            OrganisationId = org.Id,
            FirstName = "Alice",
            LastName = "Manager",
            OccupationalCategoryCode = "MANAGERS",
            JobTitle = "Plant Manager"
        });

        // 2. Professional
        await service.CreateEmployeeAsync(new CreateOrganisationEmployeeCommand
        {
            OrganisationId = org.Id,
            FirstName = "Bob",
            LastName = "Engineer",
            OccupationalCategoryCode = "PROFESSIONALS",
            JobTitle = "Industrial Systems Engineer"
        });

        // 3. Elementary
        await service.CreateEmployeeAsync(new CreateOrganisationEmployeeCommand
        {
            OrganisationId = org.Id,
            FirstName = "Charlie",
            LastName = "Worker",
            OccupationalCategoryCode = "ELEMENTARY",
            JobTitle = "Assembly General Worker"
        });

        var query = new EmployeeFilterQuery
        {
            PageIndex = 0,
            PageSize = 10,
            OccupationalCategory = "MANAGERS"
        };

        var paged = await service.GetPagedEmployeesAsync(org.Id, query);

        Assert.Single(paged.Items);
        Assert.Equal("MANAGERS", paged.Items[0].OccupationalCategoryCode);
        Assert.Equal("Alice Manager", paged.Items[0].FullName);
    }

    [Fact]
    public async Task HarvestWspTablesAsync_AggregatesEightOfoCategories_AndPopulatesDemographics()
    {
        var (factory, db, service) = CreateTestService();

        var org = new Organisation { CompanyName = "Mercedes-Benz SA", SdlNumber = "L700100400", IsActive = true };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wsp = new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = 2026,
            ReferenceNumber = "WSP-2026-MBSA-001",
            WspApprovalStatusCode = "Draft"
        };
        db.WspSubmissions.Add(wsp);
        await db.SaveChangesAsync();

        // Add employees across categories with diverse demographics:
        // Employee 1: Manager, African Male
        await service.CreateEmployeeAsync(new CreateOrganisationEmployeeCommand
        {
            OrganisationId = org.Id,
            FirstName = "Kagiso",
            LastName = "Molefe",
            GenderCode = "M",
            EquityCode = "BA",
            OccupationalCategoryCode = "MANAGERS"
        });

        // Employee 2: Manager, African Female
        await service.CreateEmployeeAsync(new CreateOrganisationEmployeeCommand
        {
            OrganisationId = org.Id,
            FirstName = "Zanele",
            LastName = "Zulu",
            GenderCode = "F",
            EquityCode = "BA",
            OccupationalCategoryCode = "MANAGERS"
        });

        // Employee 3: Professional, Indian Female, with Disability
        await service.CreateEmployeeAsync(new CreateOrganisationEmployeeCommand
        {
            OrganisationId = org.Id,
            FirstName = "Priya",
            LastName = "Govender",
            GenderCode = "F",
            EquityCode = "BI",
            DisabilityCode = "01",
            OccupationalCategoryCode = "PROFESSIONALS"
        });

        // Employee 4: Skilled Craft, Coloured Male
        await service.CreateEmployeeAsync(new CreateOrganisationEmployeeCommand
        {
            OrganisationId = org.Id,
            FirstName = "Trevor",
            LastName = "Adams",
            GenderCode = "M",
            EquityCode = "BC",
            OccupationalCategoryCode = "SKILLED_CRAFT"
        });

        // Employee 5: Skilled Craft, White Female
        await service.CreateEmployeeAsync(new CreateOrganisationEmployeeCommand
        {
            OrganisationId = org.Id,
            FirstName = "Susan",
            LastName = "Van Der Merwe",
            GenderCode = "F",
            EquityCode = "WH",
            OccupationalCategoryCode = "SKILLED_CRAFT"
        });

        // Execute 1-Click WSP Auto-Harvest
        var result = await service.HarvestWspTablesAsync(org.Id, wsp.Id, "OfficerSipho");

        Assert.True(result.Success);
        Assert.Equal(5, result.TotalEmployeesHarvested);
        Assert.Equal(8, result.CategoriesUpdatedCount);

        // Verify database persistence in WspEmploymentSummary
        var summaries = await db.WspEmploymentSummaries
            .Where(s => s.WspSubmissionId == wsp.Id)
            .ToListAsync();

        Assert.Equal(8, summaries.Count);

        // Verify Managers
        var managers = summaries.First(s => s.OccupationalCategory == "Managers");
        Assert.Equal(1, managers.MaleAfrican);
        Assert.Equal(1, managers.FemaleAfrican);
        Assert.Equal(2, managers.TotalEmployees);

        // Verify Professionals
        var professionals = summaries.First(s => s.OccupationalCategory == "Professionals");
        Assert.Equal(1, professionals.FemaleIndian);
        Assert.Equal(1, professionals.DisabledCount);
        Assert.Equal(1, professionals.TotalEmployees);

        // Verify Skilled Craft
        var skilled = summaries.First(s => s.OccupationalCategory == "Skilled Agricultural, Forestry, Fishery, Craft and Related Trades Workers");
        Assert.Equal(1, skilled.MaleColoured);
        Assert.Equal(1, skilled.FemaleWhite);
        Assert.Equal(2, skilled.TotalEmployees);

        // Verify WSP EmployeeCount updated
        var updatedWsp = await db.WspSubmissions.AsNoTracking().FirstOrDefaultAsync(w => w.Id == wsp.Id);
        Assert.NotNull(updatedWsp);
        Assert.Equal(5, updatedWsp.EmployeeCount);
    }

    [Fact]
    public async Task TerminateEmployeeAsync_MarksInactiveAndSetsEndDate()
    {
        var (factory, db, service) = CreateTestService();

        var org = new Organisation { CompanyName = "Ford SA", SdlNumber = "L700100500", IsActive = true };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var emp = await service.CreateEmployeeAsync(new CreateOrganisationEmployeeCommand
        {
            OrganisationId = org.Id,
            FirstName = "David",
            LastName = "Miller",
            JobTitle = "Assembly Technician",
            OccupationalCategoryCode = "TECHNICIANS"
        });

        var terminationDate = new DateTime(2026, 6, 30);
        var success = await service.TerminateEmployeeAsync(emp.Id, terminationDate, "Resigned", "Admin");

        Assert.True(success);

        var terminated = await db.OrganisationEmployees.FindAsync(emp.Id);
        Assert.NotNull(terminated);
        Assert.False(terminated.IsActive);
        Assert.Equal("TERMINATED", terminated.EmploymentStatusCode);
        Assert.Equal(terminationDate, terminated.EndDate);
    }

    [Fact]
    public async Task BulkImportEmployeesAsync_ValidExcelStream_ParsesAndUpserts()
    {
        var (factory, db, service) = CreateTestService();

        var org = new Organisation { CompanyName = "Isuzu Motors SA", SdlNumber = "L700100600", IsActive = true };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Create in-memory workbook
        using var wb = new XLWorkbook();
        var ws = wb.Worksheets.Add("Employees");

        // Headers
        ws.Cell(1, 1).Value = "Employee Number";
        ws.Cell(1, 2).Value = "First Name";
        ws.Cell(1, 3).Value = "Last Name";
        ws.Cell(1, 4).Value = "RSA ID Number";
        ws.Cell(1, 5).Value = "Gender";
        ws.Cell(1, 6).Value = "Equity";
        ws.Cell(1, 7).Value = "Disability";
        ws.Cell(1, 8).Value = "Job Title";
        ws.Cell(1, 9).Value = "OFO Code";
        ws.Cell(1, 10).Value = "Category";
        ws.Cell(1, 11).Value = "Site";

        // Row 1
        ws.Cell(2, 1).Value = "ISU-101";
        ws.Cell(2, 2).Value = "Thabo";
        ws.Cell(2, 3).Value = "Mokoena";
        ws.Cell(2, 4).Value = "9005125800084";
        ws.Cell(2, 5).Value = "M";
        ws.Cell(2, 6).Value = "African";
        ws.Cell(2, 7).Value = "No";
        ws.Cell(2, 8).Value = "Paint Shop Supervisor";
        ws.Cell(2, 9).Value = "312201";
        ws.Cell(2, 10).Value = "TECHNICIANS";
        ws.Cell(2, 11).Value = "Struandale Plant";

        // Row 2
        ws.Cell(3, 1).Value = "ISU-102";
        ws.Cell(3, 2).Value = "Fatima";
        ws.Cell(3, 3).Value = "Patel";
        ws.Cell(3, 4).Value = "9511200189081";
        ws.Cell(3, 5).Value = "F";
        ws.Cell(3, 6).Value = "Indian";
        ws.Cell(3, 7).Value = "No";
        ws.Cell(3, 8).Value = "Financial Accountant";
        ws.Cell(3, 9).Value = "241101";
        ws.Cell(3, 10).Value = "PROFESSIONALS";
        ws.Cell(3, 11).Value = "Struandale Plant";

        using var ms = new MemoryStream();
        wb.SaveAs(ms);
        ms.Position = 0;

        var result = await service.ImportEmployeesAsync(org.Id, ms, "isuzu_workforce.xlsx", "ImportOfficer");

        Assert.True(result.Success);
        Assert.Equal(2, result.TotalRows);
        Assert.Equal(2, result.InsertedCount);
        Assert.Empty(result.Errors);

        // Verify in DB
        var employees = await db.OrganisationEmployees.Where(e => e.OrganisationId == org.Id).ToListAsync();
        Assert.Equal(2, employees.Count);
    }
}
