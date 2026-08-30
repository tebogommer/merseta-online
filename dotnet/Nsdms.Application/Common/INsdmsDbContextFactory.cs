namespace Nsdms.Application.Common;

public interface INsdmsDbContextFactory
{
    INsdmsDbContext CreateDbContext();
    Task<INsdmsDbContext> CreateDbContextAsync(CancellationToken cancellationToken = default);
}
