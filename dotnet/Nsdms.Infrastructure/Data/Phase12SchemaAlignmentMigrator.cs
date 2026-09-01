using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator aligning TrainingCommitteeMember and BankingDetails anti-fraud security schema.
/// </summary>
public static class Phase12SchemaAlignmentMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            const string ddlSql = @"
                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'TrainingCommittee')
                BEGIN
                    CREATE TABLE [dbo].[TrainingCommittee] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [OrganisationId] INT NOT NULL,
                        [FinancialYear] INT NOT NULL DEFAULT 2026,
                        [CommitteeStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Active',
                        [ConstitutionalQuorumMet] BIT NOT NULL DEFAULT 1,
                        [LastMeetingDate] DATETIME2 NULL,
                        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL
                    );
                    CREATE INDEX [IX_TrainingCommittee_Org] ON [dbo].[TrainingCommittee] ([OrganisationId]);
                END;

                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'TrainingCommitteeMember')
                BEGIN
                    CREATE TABLE [dbo].[TrainingCommitteeMember] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [TrainingCommitteeId] INT NOT NULL,
                        [PersonId] INT NOT NULL,
                        [MemberRoleCode] NVARCHAR(50) NOT NULL DEFAULT 'UnionRepresentative',
                        [Constituency] NVARCHAR(50) NOT NULL DEFAULT 'NUMSA',
                        [IsActive] BIT NOT NULL DEFAULT 1,
                        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL
                    );
                    CREATE INDEX [IX_TrainingCommitteeMember_Committee] ON [dbo].[TrainingCommitteeMember] ([TrainingCommitteeId]);
                    CREATE INDEX [IX_TrainingCommitteeMember_Person] ON [dbo].[TrainingCommitteeMember] ([PersonId]);
                END;

                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WspDispute')
                BEGIN
                    CREATE TABLE [dbo].[WspDispute] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [OrganisationId] INT NOT NULL,
                        [WspSubmissionId] INT NULL,
                        [DisputeReferenceNumber] NVARCHAR(50) NOT NULL,
                        [DisputeReasonCode] NVARCHAR(50) NOT NULL DEFAULT 'UnionRefusalToSign',
                        [Description] NVARCHAR(2000) NOT NULL DEFAULT '',
                        [DisputeStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Logged',
                        [ResolutionDate] DATETIME2 NULL,
                        [ResolutionNotes] NVARCHAR(2000) NULL,
                        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL
                    );
                    CREATE INDEX [IX_WspDispute_Org] ON [dbo].[WspDispute] ([OrganisationId]);
                    CREATE INDEX [IX_WspDispute_Ref] ON [dbo].[WspDispute] ([DisputeReferenceNumber]);
                    CREATE INDEX [IX_WspDispute_Status] ON [dbo].[WspDispute] ([DisputeStatusCode]);
                    CREATE INDEX [IX_WspDispute_WspSubmission] ON [dbo].[WspDispute] ([WspSubmissionId]);
                END;

                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WspSkillsGap')
                BEGIN
                    CREATE TABLE [dbo].[WspSkillsGap] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [OrganisationId] INT NOT NULL,
                        [FinancialYear] INT NOT NULL DEFAULT 2026,
                        [OfoCode] NVARCHAR(50) NOT NULL DEFAULT '651202',
                        [OccupationTitle] NVARCHAR(200) NOT NULL DEFAULT 'Welder',
                        [HardToFillVacanciesCount] INT NOT NULL DEFAULT 0,
                        [SkillsGapReason] NVARCHAR(1000) NOT NULL DEFAULT '',
                        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL
                    );
                    CREATE INDEX [IX_WspSkillsGap_Org] ON [dbo].[WspSkillsGap] ([OrganisationId]);
                    CREATE INDEX [IX_WspSkillsGap_Year] ON [dbo].[WspSkillsGap] ([FinancialYear]);
                END;

                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'BankingDetails')
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'RequiresForensicApproval')
                        ALTER TABLE [dbo].[BankingDetails] ADD [RequiresForensicApproval] BIT NOT NULL DEFAULT 0;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'FraudRiskFlags')
                        ALTER TABLE [dbo].[BankingDetails] ADD [FraudRiskFlags] NVARCHAR(500) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'IsCoolingOffActive')
                        ALTER TABLE [dbo].[BankingDetails] ADD [IsCoolingOffActive] BIT NOT NULL DEFAULT 0;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'CoolingOffExpiresAt')
                        ALTER TABLE [dbo].[BankingDetails] ADD [CoolingOffExpiresAt] DATETIME2 NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'AvsVerificationReference')
                        ALTER TABLE [dbo].[BankingDetails] ADD [AvsVerificationReference] NVARCHAR(100) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'AvsVerifiedAt')
                        ALTER TABLE [dbo].[BankingDetails] ADD [AvsVerifiedAt] DATETIME2 NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'AvsStatusResponse')
                        ALTER TABLE [dbo].[BankingDetails] ADD [AvsStatusResponse] NVARCHAR(100) NULL;
                END;
            ";

            await context.Database.ExecuteSqlRawAsync(ddlSql);
            logger?.LogInformation("Phase 12 Schema Alignment: TrainingCommitteeMember and BankingDetails anti-fraud columns synchronized.");
        }
    }
}
