using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;

namespace Nsdms.Infrastructure.Data;

public class NsdmsDbContextFactory : INsdmsDbContextFactory
{
    private readonly DbContextOptions<NsdmsDbContext> _options;
    private readonly ITenantProvider _tenantProvider;

    public NsdmsDbContextFactory(DbContextOptions<NsdmsDbContext> options, ITenantProvider tenantProvider)
    {
        _options = options;
        _tenantProvider = tenantProvider;
    }

    public INsdmsDbContext CreateDbContext()
    {
        return new NsdmsDbContext(_options, _tenantProvider);
    }

    public Task<INsdmsDbContext> CreateDbContextAsync(CancellationToken cancellationToken = default)
    {
        return Task.FromResult<INsdmsDbContext>(new NsdmsDbContext(_options, _tenantProvider));
    }
}
