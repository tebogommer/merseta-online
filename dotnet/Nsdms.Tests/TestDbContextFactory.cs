using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Infrastructure.Data;

namespace Nsdms.Tests;

public class TestDbContextFactory : INsdmsDbContextFactory
{
    private readonly DbContextOptions<NsdmsDbContext> _options;

    public TestDbContextFactory(string dbName)
    {
        _options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: dbName)
            .Options;
    }

    public INsdmsDbContext CreateDbContext()
    {
        var ctx = new NsdmsDbContext(_options);
        ctx.Database.EnsureCreated();
        return ctx;
    }

    public Task<INsdmsDbContext> CreateDbContextAsync(CancellationToken cancellationToken = default)
    {
        return Task.FromResult(CreateDbContext());
    }
}
