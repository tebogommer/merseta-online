using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Models;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class PerformancePagingTests
{
    [Fact]
    public void PagedResult_Calculates_TotalPages_And_Navigation_Correctly()
    {
        // 55 items with PageSize 20 -> 3 pages (20, 20, 15)
        var result = new PagedResult<string>(new List<string> { "a", "b" }, totalCount: 55, pageIndex: 0, pageSize: 20);

        Assert.Equal(3, result.TotalPages);
        Assert.False(result.HasPreviousPage);
        Assert.True(result.HasNextPage);

        var middlePage = new PagedResult<string>(new List<string> { "c" }, totalCount: 55, pageIndex: 1, pageSize: 20);
        Assert.True(middlePage.HasPreviousPage);
        Assert.True(middlePage.HasNextPage);

        var lastPage = new PagedResult<string>(new List<string> { "d" }, totalCount: 55, pageIndex: 2, pageSize: 20);
        Assert.True(lastPage.HasPreviousPage);
        Assert.False(lastPage.HasNextPage);
    }

    [Fact]
    public async Task OrganisationService_GetPagedAsync_Returns_Projected_Page_Data()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var audit = new AuditService(factory);
        var orgService = new OrganisationService(factory, audit);

        // Seed test organisations
        for (int i = 1; i <= 25; i++)
        {
            await orgService.CreateAsync(new Organisation
            {
                CompanyName = $"Engineering Corp {i:D2}",
                SdlNumber = $"L{100000000 + i}",
                IsActive = i % 2 == 0,
                ChamberCode = "AUTO"
            });
        }

        // Query page 0 with page size 10
        var query = new PaginationQuery
        {
            PageIndex = 0,
            PageSize = 10
        };

        var page1 = await orgService.GetPagedAsync(query);

        Assert.Equal(25, page1.TotalCount);
        Assert.Equal(10, page1.Items.Count);
        Assert.Equal(3, page1.TotalPages);
        Assert.IsType<OrganisationListDto>(page1.Items[0]);

        // Query with filter active only
        var activeQuery = new PaginationQuery
        {
            PageIndex = 0,
            PageSize = 10,
            FilterParams = new Dictionary<string, string> { { "status", "Active" } }
        };

        var activePage = await orgService.GetPagedAsync(activeQuery);
        Assert.Equal(12, activePage.TotalCount); // 12 active out of 25
        Assert.All(activePage.Items, item => Assert.True(item.IsActive));
    }

    [Fact]
    public async Task PersonService_GetPagedAsync_Filters_And_Projects_Demographics()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var audit = new AuditService(factory);
        var personService = new PersonService(factory, audit);

        for (int i = 1; i <= 15; i++)
        {
            await personService.CreateAsync(new Person
            {
                FirstName = $"Candidate{i}",
                LastName = $"Zuma{i}",
                PassportNumber = $"A{1000000 + i}",
                AlternateIdTypeId = "527",
                IsActive = true
            });
        }

        var query = new PaginationQuery
        {
            SearchText = "Candidate1",
            PageIndex = 0,
            PageSize = 5
        };

        var paged = await personService.GetPagedAsync(query);

        Assert.True(paged.TotalCount >= 1);
        Assert.All(paged.Items, p => Assert.Contains("Candidate1", p.FirstName));
    }

    [Fact]
    public async Task LearnerService_GetPagedAsync_Supports_Status_And_Programme_Filter()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var audit = new AuditService(factory);
        var learnerService = new LearnerService(factory, audit);

        using (var db = await factory.CreateDbContextAsync())
        {
            var person = new Person { FirstName = "Thabo", LastName = "Mokoena", RsaIdNumber = "9901015009081" };
            var org = new Organisation { CompanyName = "Sasol Synfuels", SdlNumber = "L999888777" };
            db.People.Add(person);
            db.Organisations.Add(org);
            await db.SaveChangesAsync();

            db.CompanyLearners.Add(new CompanyLearner
            {
                PersonId = person.Id,
                OrganisationId = org.Id,
                LearnerContractNumber = "CTR-2026-001",
                QualificationTitle = "Chemical Plant Operator",
                LearningProgrammeTypeCode = "Apprenticeship",
                EnrolmentStatusCode = "InProgress",
                RegistrationDate = DateTime.UtcNow
            });
            await db.SaveChangesAsync();
        }

        var query = new PaginationQuery
        {
            PageIndex = 0,
            PageSize = 10,
            FilterParams = new Dictionary<string, string>
            {
                { "status", "InProgress" },
                { "programmeType", "Apprenticeship" }
            }
        };

        var paged = await learnerService.GetPagedAsync(query);

        Assert.Equal(1, paged.TotalCount);
        Assert.Single(paged.Items);
        Assert.Equal("CTR-2026-001", paged.Items[0].LearnerContractNumber);
        Assert.Equal("Thabo Mokoena", paged.Items[0].LearnerFullName);
    }
}
