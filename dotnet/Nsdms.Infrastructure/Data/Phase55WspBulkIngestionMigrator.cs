using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 55: Universal Bulk Ingestion Engine for WSP / ATR submissions (Option C: Modern Hybrid).
/// Provisions WspBulkImportBatch and WspBulkImportStaging tables and sets up validation and commitment procedures.
/// </summary>
public static class Phase55WspBulkIngestionMigrator
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

                -- 1. Table: [dbo].[WspBulkImportBatch]
                IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'WspBulkImportBatch' AND schema_id = SCHEMA_ID('dbo'))
                BEGIN
                    CREATE TABLE [dbo].[WspBulkImportBatch] (
                        [Id] INT IDENTITY(1,1) NOT NULL,
                        [BatchGuid] UNIQUEIDENTIFIER NOT NULL CONSTRAINT [DF_WspBulkImportBatch_Guid] DEFAULT (NEWID()),
                        [BatchReference] NVARCHAR(50) NOT NULL,
                        [WspSubmissionId] INT NOT NULL,
                        [OrganisationId] INT NOT NULL,
                        [SchemeYear] INT NOT NULL,
                        [ReportType] NVARCHAR(10) NOT NULL CONSTRAINT [DF_WspBulkImportBatch_ReportType] DEFAULT ('WSP'),
                        [OriginalFileName] NVARCHAR(250) NOT NULL,
                        [FileSizeBytes] BIGINT NOT NULL CONSTRAINT [DF_WspBulkImportBatch_FileSize] DEFAULT (0),
                        [ContentHashSha256] NVARCHAR(64) NOT NULL,
                        [BatchStatus] NVARCHAR(50) NOT NULL CONSTRAINT [DF_WspBulkImportBatch_Status] DEFAULT ('Queued'),
                        
                        [TotalRowCount] INT NOT NULL CONSTRAINT [DF_WspBulkImportBatch_TotalRows] DEFAULT (0),
                        [ValidRowCount] INT NOT NULL CONSTRAINT [DF_WspBulkImportBatch_ValidRows] DEFAULT (0),
                        [ErrorRowCount] INT NOT NULL CONSTRAINT [DF_WspBulkImportBatch_ErrorRows] DEFAULT (0),
                        [CommittedRowCount] INT NOT NULL CONSTRAINT [DF_WspBulkImportBatch_CommittedRows] DEFAULT (0),
                        [TotalEstimatedCostRollup] DECIMAL(18,2) NOT NULL CONSTRAINT [DF_WspBulkImportBatch_TotalCost] DEFAULT (0),
                        [TotalBeneficiariesRollup] INT NOT NULL CONSTRAINT [DF_WspBulkImportBatch_TotalBeneficiaries] DEFAULT (0),
                        
                        [AllowPartialCommit] BIT NOT NULL CONSTRAINT [DF_WspBulkImportBatch_Partial] DEFAULT (0),
                        [ErrorSummaryJson] NVARCHAR(MAX) NULL,
                        [ProcessingDurationMs] INT NULL,
                        
                        [CreatedAt] DATETIME2(3) NOT NULL CONSTRAINT [DF_WspBulkImportBatch_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                        [CreatedBy] NVARCHAR(100) NULL CONSTRAINT [DF_WspBulkImportBatch_CreatedBy] DEFAULT ('SYSTEM'),
                        [ModifiedAt] DATETIME2(3) NULL,
                        [ModifiedBy] NVARCHAR(100) NULL,
                        
                        CONSTRAINT [PK_WspBulkImportBatch] PRIMARY KEY CLUSTERED ([Id] ASC),
                        CONSTRAINT [FK_WspBulkImportBatch_WspSubmission] FOREIGN KEY ([WspSubmissionId]) 
                            REFERENCES [dbo].[WspSubmission] ([Id]) ON DELETE CASCADE,
                        CONSTRAINT [FK_WspBulkImportBatch_Organisation] FOREIGN KEY ([OrganisationId]) 
                            REFERENCES [dbo].[Organisation] ([Id])
                    );

                    CREATE UNIQUE NONCLUSTERED INDEX [IX_WspBulkImportBatch_BatchGuid] ON [dbo].[WspBulkImportBatch] ([BatchGuid]);
                    CREATE NONCLUSTERED INDEX [IX_WspBulkImportBatch_WspSubmissionId] ON [dbo].[WspBulkImportBatch] ([WspSubmissionId]);
                    CREATE NONCLUSTERED INDEX [IX_WspBulkImportBatch_OrganisationId] ON [dbo].[WspBulkImportBatch] ([OrganisationId]);
                    CREATE NONCLUSTERED INDEX [IX_WspBulkImportBatch_Status] ON [dbo].[WspBulkImportBatch] ([BatchStatus]);
                END;

                -- 2. Table: [dbo].[WspBulkImportStaging]
                IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'WspBulkImportStaging' AND schema_id = SCHEMA_ID('dbo'))
                BEGIN
                    CREATE TABLE [dbo].[WspBulkImportStaging] (
                        [Id] BIGINT IDENTITY(1,1) NOT NULL,
                        [BatchId] INT NOT NULL,
                        [RowIndex] INT NOT NULL,
                        
                        [RawIdType] NVARCHAR(50) NULL,
                        [RawIdNumber] NVARCHAR(50) NULL,
                        [RawFirstName] NVARCHAR(100) NULL,
                        [RawLastName] NVARCHAR(100) NULL,
                        [RawGenderCode] NVARCHAR(50) NULL,
                        [RawEquityCode] NVARCHAR(50) NULL,
                        [RawNationalityCode] NVARCHAR(50) NULL,
                        [RawOfoCode] NVARCHAR(50) NULL,
                        [RawSpecialisationCode] NVARCHAR(50) NULL,
                        [RawInterventionTypeCode] NVARCHAR(50) NULL,
                        [RawQualificationCode] NVARCHAR(50) NULL,
                        [RawSkillsProgramCode] NVARCHAR(50) NULL,
                        [RawSkillsSetCode] NVARCHAR(50) NULL,
                        [RawEmploymentTypeCode] NVARCHAR(50) NULL,
                        [RawProviderTypeCode] NVARCHAR(50) NULL,
                        [RawTrainingDeliveryMethodCode] NVARCHAR(50) NULL,
                        [RawMunicipalityCode] NVARCHAR(50) NULL,
                        [RawStartDate] NVARCHAR(50) NULL,
                        [RawEndDate] NVARCHAR(50) NULL,
                        [RawEstimatedCost] NVARCHAR(50) NULL,
                        [RawBeneficiaryCount] NVARCHAR(50) NULL,
                        
                        [ResolvedOfoCodeId] INT NULL,
                        [ResolvedQualificationId] INT NULL,
                        [ResolvedInterventionTypeId] INT NULL,
                        [ResolvedNqfLevel] INT NULL,
                        [ParsedEstimatedCost] DECIMAL(18,2) NULL,
                        [ParsedBeneficiaryCount] INT NULL,
                        [ParsedStartDate] DATETIME2(3) NULL,
                        [ParsedEndDate] DATETIME2(3) NULL,
                        
                        [IsValid] BIT NOT NULL CONSTRAINT [DF_WspBulkImportStaging_IsValid] DEFAULT (1),
                        [IsCommitted] BIT NOT NULL CONSTRAINT [DF_WspBulkImportStaging_IsCommitted] DEFAULT (0),
                        [ValidationErrorCode] NVARCHAR(100) NULL,
                        [ValidationErrorDetails] NVARCHAR(MAX) NULL,
                        
                        [CreatedAt] DATETIME2(3) NOT NULL CONSTRAINT [DF_WspBulkImportStaging_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                        [CreatedBy] NVARCHAR(100) NULL CONSTRAINT [DF_WspBulkImportStaging_CreatedBy] DEFAULT ('SYSTEM'),
                        [ModifiedAt] DATETIME2(3) NULL,
                        [ModifiedBy] NVARCHAR(100) NULL,
                        
                        CONSTRAINT [PK_WspBulkImportStaging] PRIMARY KEY CLUSTERED ([Id] ASC),
                        CONSTRAINT [FK_WspBulkImportStaging_Batch] FOREIGN KEY ([BatchId]) 
                            REFERENCES [dbo].[WspBulkImportBatch] ([Id]) ON DELETE CASCADE
                    );

                    CREATE NONCLUSTERED INDEX [IX_WspBulkImportStaging_BatchId_IsValid] 
                        ON [dbo].[WspBulkImportStaging] ([BatchId], [IsValid]) INCLUDE ([RowIndex], [IsCommitted]);
                    CREATE NONCLUSTERED INDEX [IX_WspBulkImportStaging_BatchId_Committed] 
                        ON [dbo].[WspBulkImportStaging] ([BatchId], [IsCommitted]);
                END;
            ";

            await context.Database.ExecuteSqlRawAsync(ddlSql);
            logger?.LogInformation("[SCHEMA MIGRATOR] Phase 55 WSP Bulk Ingestion tables verified successfully.");
        }
    }
}
