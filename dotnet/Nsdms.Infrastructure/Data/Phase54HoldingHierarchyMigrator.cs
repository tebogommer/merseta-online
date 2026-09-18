using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

public static class Phase54HoldingHierarchyMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            const string ddlSql = @"
                SET QUOTED_IDENTIFIER ON;
                SET ANSI_NULLS ON;

                IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.Organisation') AND name = 'ParentOrganisationId')
                BEGIN
                    ALTER TABLE [dbo].[Organisation] ADD [ParentOrganisationId] INT NULL;
                END;

                IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.Organisation') AND name = 'HoldingRelationshipType')
                BEGIN
                    ALTER TABLE [dbo].[Organisation] ADD [HoldingRelationshipType] NVARCHAR(50) NULL;
                END;

                IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.Organisation') AND name = 'OwnershipPercentage')
                BEGIN
                    ALTER TABLE [dbo].[Organisation] ADD [OwnershipPercentage] DECIMAL(5, 2) NULL;
                END;

                IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_Organisation_ParentOrganisation' AND parent_object_id = OBJECT_ID('dbo.Organisation'))
                BEGIN
                    ALTER TABLE [dbo].[Organisation] WITH CHECK 
                    ADD CONSTRAINT [FK_Organisation_ParentOrganisation] FOREIGN KEY ([ParentOrganisationId])
                    REFERENCES [dbo].[Organisation] ([id]);
                END;

                IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_Organisation_ParentOrganisationId' AND object_id = OBJECT_ID('dbo.Organisation'))
                BEGIN
                    CREATE NONCLUSTERED INDEX [IX_Organisation_ParentOrganisationId]
                    ON [dbo].[Organisation] ([ParentOrganisationId]);
                END;
            ";

            await context.Database.ExecuteSqlRawAsync(ddlSql);
            logger?.LogInformation("[SCHEMA MIGRATOR] Phase 54 Corporate Holding Hierarchy schema verified successfully.");
        }
    }
}
