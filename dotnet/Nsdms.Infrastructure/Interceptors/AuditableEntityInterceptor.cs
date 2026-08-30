using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Diagnostics;
using Nsdms.Domain.Common;

namespace Nsdms.Infrastructure.Interceptors;

public class AuditableEntityInterceptor : SaveChangesInterceptor
{
    public override InterceptionResult<int> SavingChanges(DbContextEventData eventData, InterceptionResult<int> result)
    {
        UpdateEntities(eventData.Context);
        return base.SavingChanges(eventData, result);
    }

    public override ValueTask<InterceptionResult<int>> SavingChangesAsync(DbContextEventData eventData, InterceptionResult<int> result, CancellationToken cancellationToken = default)
    {
        UpdateEntities(eventData.Context);
        return base.SavingChangesAsync(eventData, result, cancellationToken);
    }

    private static void UpdateEntities(DbContext? context)
    {
        if (context == null) return;

        var entries = context.ChangeTracker.Entries<IAuditableEntity>();
        var now = DateTime.UtcNow;

        foreach (var entry in entries)
        {
            if (entry.State == EntityState.Added)
            {
                entry.Entity.CreatedAt = now;
                if (string.IsNullOrEmpty(entry.Entity.CreatedBy))
                {
                    entry.Entity.CreatedBy = "SYSTEM";
                }
            }
            else if (entry.State == EntityState.Modified)
            {
                entry.Entity.ModifiedAt = now;
                if (string.IsNullOrEmpty(entry.Entity.ModifiedBy))
                {
                    entry.Entity.ModifiedBy = "SYSTEM";
                }
            }
        }
    }
}
