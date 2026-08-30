using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;

namespace Nsdms.Infrastructure.Data;

public class NsdmsDbContextFactory : INsdmsDbContextFactory
{
    private readonly IDbContextFactory<NsdmsDbContext> _underlyingFactory;

    public NsdmsDbContextFactory(IDbContextFactory<NsdmsDbContext> underlyingFactory)
    {
        _underlyingFactory = underlyingFactory;
    }

    public INsdmsDbContext CreateDbContext()
    {
        return _underlyingFactory.CreateDbContext();
    }

    public async Task<INsdmsDbContext> CreateDbContextAsync(CancellationToken cancellationToken = default)
    {
        return await _underlyingFactory.CreateDbContextAsync(cancellationToken);
    }
}
