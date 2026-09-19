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

        TouchParentAggregates(context, now);
    }

    private static void TouchParentAggregates(DbContext context, DateTime now)
    {
        var childEntries = context.ChangeTracker.Entries()
            .Where(e => e.State == EntityState.Added || e.State == EntityState.Modified || e.State == EntityState.Deleted)
            .ToList();

        foreach (var child in childEntries)
        {
            if (child.Entity is Nsdms.Domain.Entities.WspTrainingPlan plan && plan.WspSubmissionId > 0)
            {
                var parent = context.ChangeTracker.Entries<Nsdms.Domain.Entities.WspSubmission>()
                    .FirstOrDefault(p => p.Entity.Id == plan.WspSubmissionId);
                if (parent != null && parent.State == EntityState.Unchanged)
                {
                    parent.Entity.ModifiedAt = now;
                    parent.State = EntityState.Modified;
                }
            }
            else if (child.Entity is Nsdms.Domain.Entities.WspEmploymentSummary summary && summary.WspSubmissionId > 0)
            {
                var parent = context.ChangeTracker.Entries<Nsdms.Domain.Entities.WspSubmission>()
                    .FirstOrDefault(p => p.Entity.Id == summary.WspSubmissionId);
                if (parent != null && parent.State == EntityState.Unchanged)
                {
                    parent.Entity.ModifiedAt = now;
                    parent.State = EntityState.Modified;
                }
            }
            else if (child.Entity is Nsdms.Domain.Entities.GrantApplicationIntervention interv && interv.GrantApplicationId > 0)
            {
                var parent = context.ChangeTracker.Entries<Nsdms.Domain.Entities.GrantApplication>()
                    .FirstOrDefault(p => p.Entity.Id == interv.GrantApplicationId);
                if (parent != null && parent.State == EntityState.Unchanged)
                {
                    parent.Entity.ModifiedAt = now;
                    parent.State = EntityState.Modified;
                }
            }
        }
    }
}
