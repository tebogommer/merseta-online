using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Data;

public static class Phase5SetmisSchemaMigrator
{
    public static async Task MigrateSetmisSchemaAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        var ddl = @"
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'SetmisSubmissionBatch')
BEGIN
    CREATE TABLE [dbo].[SetmisSubmissionBatch] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [BatchNumber] NVARCHAR(100) NOT NULL,
        [FileCode] NVARCHAR(50) NOT NULL DEFAULT 'ALL',
        [SubmissionPeriod] NVARCHAR(50) NOT NULL,
        [TotalRecords] INT NOT NULL DEFAULT 0,
        [ValidRecords] INT NOT NULL DEFAULT 0,
        [ErrorRecords] INT NOT NULL DEFAULT 0,
        [Status] NVARCHAR(50) NOT NULL DEFAULT 'Generated',
        [GeneratedFileUri] NVARCHAR(500) NULL,
        [GeneratedByUserId] NVARCHAR(100) NULL,
        [GeneratedDate] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [DhetAcknowledgmentRef] NVARCHAR(100) NULL,
        [ValidationSummaryJson] NVARCHAR(MAX) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE UNIQUE INDEX [IX_SetmisSubmissionBatch_BatchNumber] ON [dbo].[SetmisSubmissionBatch]([BatchNumber]);
    CREATE INDEX [IX_SetmisSubmissionBatch_SubmissionPeriod] ON [dbo].[SetmisSubmissionBatch]([SubmissionPeriod]);
    CREATE INDEX [IX_SetmisSubmissionBatch_Status] ON [dbo].[SetmisSubmissionBatch]([Status]);
END
";

        await context.Database.ExecuteSqlRawAsync(ddl);
        logger?.LogInformation("Phase 5 SETMIS & DHET Compliance DDL verified and executed successfully.");

        // Seed Sample SETMIS Batches if empty
        if (!await context.SetmisSubmissionBatches.AnyAsync())
        {
            var batch1 = new SetmisSubmissionBatch
            {
                BatchNumber = "SETMIS-2026-Q1-FINAL",
                FileCode = "ALL",
                SubmissionPeriod = "2026-Q1",
                TotalRecords = 45,
                ValidRecords = 45,
                ErrorRecords = 0,
                Status = "Accepted by DHET",
                GeneratedFileUri = "vault://setmis/2026-q1/SETMIS_2026_Q1_BUNDLE.zip",
                GeneratedByUserId = "compliance@merseta.org.za",
                GeneratedDate = new DateTime(2026, 4, 15),
                DhetAcknowledgmentRef = "DHET-SETMIS-ACK-20260415-0082",
                ValidationSummaryJson = "{\"passedRules\":18,\"totalChecks\":18,\"exceptions\":0}",
                CreatedBy = "Compliance Officer"
            };

            var batch2 = new SetmisSubmissionBatch
            {
                BatchNumber = "SETMIS-2026-Q2-VALIDATED",
                FileCode = "ALL",
                SubmissionPeriod = "2026-Q2",
                TotalRecords = 62,
                ValidRecords = 60,
                ErrorRecords = 2,
                Status = "Validated",
                GeneratedFileUri = "vault://setmis/2026-q2/SETMIS_2026_Q2_BUNDLE.zip",
                GeneratedByUserId = "compliance@merseta.org.za",
                GeneratedDate = new DateTime(2026, 7, 20),
                ValidationSummaryJson = "{\"passedRules\":16,\"totalChecks\":18,\"exceptions\":2}",
                CreatedBy = "Compliance Officer"
            };

            context.SetmisSubmissionBatches.AddRange(batch1, batch2);
            await context.SaveChangesAsync();
        }
    }
}
