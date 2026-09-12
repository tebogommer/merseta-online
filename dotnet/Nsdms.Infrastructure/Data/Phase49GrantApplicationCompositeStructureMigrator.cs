using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator enforcing Option A (Dynamic Multi-Section Composite Structure):
/// 1. Adds legacy Strategic Project narrative motivation fields and metadata to dbo.GrantApplication:
///    - ProjectDescription, Purpose, Outcomes, Benefits, PotentialRisks
///    - EstimatedOverallProjectCost, NumberOfBeneficiaries, RequireProjectAdministrationCosts, TargetProvinces
///    - HasPivotalInterventions, HasNonPivotalInterventions
/// 2. Creates dbo.GrantApplicationIntervention table supporting both PIVOTAL structured training plans
///    and Non-PIVOTAL project deliverables/milestones with foreign keys and covering indexes.
/// </summary>
public static class Phase49GrantApplicationCompositeStructureMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            const string ddlSql = @"
                -- 1. Extend dbo.GrantApplication with Strategic Project Motivation and Composite Fields
                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantApplication')
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'ProjectDescription')
                        ALTER TABLE [dbo].[GrantApplication] ADD [ProjectDescription] NVARCHAR(MAX) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'Purpose')
                        ALTER TABLE [dbo].[GrantApplication] ADD [Purpose] NVARCHAR(MAX) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'Outcomes')
                        ALTER TABLE [dbo].[GrantApplication] ADD [Outcomes] NVARCHAR(MAX) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'Benefits')
                        ALTER TABLE [dbo].[GrantApplication] ADD [Benefits] NVARCHAR(MAX) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'PotentialRisks')
                        ALTER TABLE [dbo].[GrantApplication] ADD [PotentialRisks] NVARCHAR(MAX) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'EstimatedOverallProjectCost')
                        ALTER TABLE [dbo].[GrantApplication] ADD [EstimatedOverallProjectCost] DECIMAL(18,2) NOT NULL CONSTRAINT DF_GrantApplication_EstimatedCost DEFAULT 0.00;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'NumberOfBeneficiaries')
                        ALTER TABLE [dbo].[GrantApplication] ADD [NumberOfBeneficiaries] INT NOT NULL CONSTRAINT DF_GrantApplication_Beneficiaries DEFAULT 0;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'RequireProjectAdministrationCosts')
                        ALTER TABLE [dbo].[GrantApplication] ADD [RequireProjectAdministrationCosts] BIT NOT NULL CONSTRAINT DF_GrantApplication_AdminCosts DEFAULT 0;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'TargetProvinces')
                        ALTER TABLE [dbo].[GrantApplication] ADD [TargetProvinces] NVARCHAR(500) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'HasPivotalInterventions')
                        ALTER TABLE [dbo].[GrantApplication] ADD [HasPivotalInterventions] BIT NOT NULL CONSTRAINT DF_GrantApplication_HasPivotal DEFAULT 1;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'HasNonPivotalInterventions')
                        ALTER TABLE [dbo].[GrantApplication] ADD [HasNonPivotalInterventions] BIT NOT NULL CONSTRAINT DF_GrantApplication_HasNonPivotal DEFAULT 0;

                    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_GrantApplication_HasPivotal' AND object_id = OBJECT_ID('GrantApplication'))
                        CREATE INDEX [IX_GrantApplication_HasPivotal] ON [dbo].[GrantApplication] ([HasPivotalInterventions]);

                    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_GrantApplication_HasNonPivotal' AND object_id = OBJECT_ID('GrantApplication'))
                        CREATE INDEX [IX_GrantApplication_HasNonPivotal] ON [dbo].[GrantApplication] ([HasNonPivotalInterventions]);
                END;

                -- 2. Create dbo.GrantApplicationIntervention table
                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantApplicationIntervention')
                BEGIN
                    CREATE TABLE [dbo].[GrantApplicationIntervention] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [GrantApplicationId] INT NOT NULL CONSTRAINT FK_GrantApplicationIntervention_App FOREIGN KEY REFERENCES [dbo].[GrantApplication]([Id]) ON DELETE CASCADE,
                        [InterventionTypeCode] NVARCHAR(50) NOT NULL CONSTRAINT FK_GrantApplicationIntervention_Type FOREIGN KEY REFERENCES [lookup].[InterventionType]([Code]),
                        [IsPivotal] BIT NOT NULL CONSTRAINT DF_GrantApplicationIntervention_IsPivotal DEFAULT 1,
                        
                        -- PIVOTAL Training Plan Fields
                        [SaqaId] NVARCHAR(50) NULL,
                        [QualificationTitle] NVARCHAR(300) NULL,
                        [NqfLevel] NVARCHAR(50) NULL,
                        [OfoCode] NVARCHAR(100) NULL,
                        [LearnerCountEmployed] INT NOT NULL CONSTRAINT DF_GrantAppIntervention_18_1 DEFAULT 0,
                        [LearnerCountUnemployed] INT NOT NULL CONSTRAINT DF_GrantAppIntervention_18_2 DEFAULT 0,
                        [UnitCost] DECIMAL(18,2) NOT NULL CONSTRAINT DF_GrantAppIntervention_UnitCost DEFAULT 0.00,
                        
                        -- Non-PIVOTAL Project Deliverable Fields
                        [DeliverableName] NVARCHAR(300) NULL,
                        [TargetQuantity] INT NULL,
                        [EstimatedCost] DECIMAL(18,2) NOT NULL CONSTRAINT DF_GrantAppIntervention_EstCost DEFAULT 0.00,
                        [ProjectedStartDate] DATETIME2 NULL,
                        [ProjectedEndDate] DATETIME2 NULL,
                        [ActualEndDate] DATETIME2 NULL,
                        [MilestoneNumber] INT NULL,
                        
                        -- Rollup & Auditing
                        [TotalAmount] DECIMAL(18,2) NOT NULL CONSTRAINT DF_GrantAppIntervention_Total DEFAULT 0.00,
                        [Comments] NVARCHAR(MAX) NULL,
                        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_GrantAppIntervention_CreatedAt DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_GrantAppIntervention_CreatedBy DEFAULT 'SYSTEM',
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL
                    );

                    CREATE INDEX [IX_GrantApplicationIntervention_AppId] ON [dbo].[GrantApplicationIntervention] ([GrantApplicationId]);
                    CREATE INDEX [IX_GrantApplicationIntervention_TypeCode] ON [dbo].[GrantApplicationIntervention] ([InterventionTypeCode]);
                    CREATE INDEX [IX_GrantApplicationIntervention_IsPivotal] ON [dbo].[GrantApplicationIntervention] ([IsPivotal]);
                END;
            ";

            await context.Database.ExecuteSqlRawAsync(ddlSql);
            logger?.LogInformation("Phase49: Successfully executed GrantApplication composite structure and intervention line item migrations.");
        }
    }
}
