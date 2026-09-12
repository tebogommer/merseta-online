using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator enforcing:
/// 1. Stakeholder eligibility lookup table (lookup.StakeholderEligibilityType).
/// 2. Expanded InterventionType catalog with PIVOTAL/Non-PIVOTAL distinction and non-pivotal seeds.
/// 3. GrantFundingWindow configuration columns (IsPivotal, WindowClassification, RequireWspCompliance, TemplateId).
/// 4. Normalized junction tables GrantWindowEligibility and GrantWindowIntervention.
/// 5. Template Blueprint Engine tables GrantWindowTemplate, GrantWindowTemplateEligibility, GrantWindowTemplateIntervention and seeded blueprints.
/// </summary>
public static class Phase47DgWindowConfigurationAndBlueprintMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            const string ddlSql = @"
                -- 1. Create lookup.StakeholderEligibilityType table
                IF NOT EXISTS (SELECT * FROM sys.tables t JOIN sys.schemas s ON t.schema_id = s.schema_id WHERE s.name = 'lookup' AND t.name = 'StakeholderEligibilityType')
                BEGIN
                    CREATE TABLE [lookup].[StakeholderEligibilityType] (
                        [Code] NVARCHAR(50) NOT NULL PRIMARY KEY,
                        [Name] NVARCHAR(250) NOT NULL,
                        [Description] NVARCHAR(500) NULL,
                        [Active] BIT NOT NULL CONSTRAINT DF_StakeholderEligibilityType_Active DEFAULT 1,
                        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_StakeholderEligibilityType_CreatedAt DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_StakeholderEligibilityType_CreatedBy DEFAULT 'SYSTEM',
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL
                    );
                    CREATE INDEX [IX_StakeholderEligibilityType_Name] ON [lookup].[StakeholderEligibilityType] ([Name]);
                    CREATE INDEX [IX_StakeholderEligibilityType_Active] ON [lookup].[StakeholderEligibilityType] ([Active]);
                END;

                -- Seed StakeholderEligibilityType lookups
                MERGE INTO [lookup].[StakeholderEligibilityType] AS target
                USING (VALUES
                    (N'LEVY_PAYING', N'Levy-paying Employer', N'Registered South African employer contributing the 1% statutory skills development levy to merSETA.'),
                    (N'NON_LEVY_EXEMPT', N'Non-Levy Paying / Exempt Employer', N'Exempt small enterprise or non-levy paying employer within the metals, engineering, auto, and plastics sectors.'),
                    (N'PUBLIC_TVET', N'Public TVET College', N'Public Technical and Vocational Education and Training College registered with DHET.'),
                    (N'PRIVATE_SDP', N'Private TVET / Skills Development Provider (SDP)', N'QCTO/merSETA accredited private skills development provider.'),
                    (N'PUBLIC_HEI', N'Public Higher Education Institution / University', N'Public university or university of technology registered in terms of the Higher Education Act.'),
                    (N'PRIVATE_HEI', N'Private Higher Education Institution', N'Accredited private higher education provider.'),
                    (N'CET_COLLEGE', N'Community Education & Training (CET) College', N'Public community education and training college delivering adult learning and vocational skills.'),
                    (N'NGO_CBO', N'Non-Governmental / Community-Based Organisation (NGO/CBO)', N'Registered non-profit or community-based entity delivering developmental and vocational interventions.'),
                    (N'TRADE_UNION', N'Organised Labour / Trade Union', N'Recognised trade union representing workers within the merSETA industrial scope.'),
                    (N'EMPLOYER_ASSOC', N'Organised Employer Association / Chamber', N'Registered employer body, chamber, or manufacturing industry federation.')
                ) AS source ([Code], [Name], [Description])
                ON target.[Code] = source.[Code]
                WHEN NOT MATCHED THEN
                    INSERT ([Code], [Name], [Description], [Active], [CreatedAt], [CreatedBy])
                    VALUES (source.[Code], source.[Name], source.[Description], 1, SYSUTCDATETIME(), 'SYSTEM');

                -- 2. Expand lookup.InterventionType
                IF EXISTS (SELECT * FROM sys.tables t JOIN sys.schemas s ON t.schema_id = s.schema_id WHERE s.name = 'lookup' AND t.name = 'InterventionType')
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('lookup.InterventionType') AND name = 'IsPivotal')
                        ALTER TABLE [lookup].[InterventionType] ADD [IsPivotal] BIT NOT NULL CONSTRAINT DF_InterventionType_IsPivotal DEFAULT 1;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('lookup.InterventionType') AND name = 'Category')
                        ALTER TABLE [lookup].[InterventionType] ADD [Category] NVARCHAR(50) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('lookup.InterventionType') AND name = 'DefaultUnitCost')
                        ALTER TABLE [lookup].[InterventionType] ADD [DefaultUnitCost] DECIMAL(18,2) NOT NULL CONSTRAINT DF_InterventionType_Cost DEFAULT 0.00;
                END;

                -- Seed PIVOTAL and Non-PIVOTAL interventions
                MERGE INTO [lookup].[InterventionType] AS target
                USING (VALUES
                    -- PIVOTAL
                    (N'LEARNERSHIP_18_1', N'Learnership (18.1 Employed)', N'Structured qualification programme for employed workers.', 1, N'PIVOTAL', 45000.00),
                    (N'LEARNERSHIP_18_2', N'Learnership (18.2 Unemployed)', N'Structured qualification programme for unemployed youth.', 1, N'PIVOTAL', 60000.00),
                    (N'APPRENTICESHIP', N'Artisan Apprenticeship', N'CBMT or legacy dual-system artisan trade qualification.', 1, N'PIVOTAL', 185000.00),
                    (N'SKILLS_PROGRAMME', N'Skills Programme (Accredited)', N'Credit-bearing unit standard skills programme.', 1, N'PIVOTAL', 15000.00),
                    (N'BURSARY_PIVOTAL', N'Undergraduate / Postgraduate Bursary (PIVOTAL)', N'Higher education university or TVET bursary for scarce and critical skills.', 1, N'PIVOTAL', 80000.00),
                    (N'INTERNSHIP_WIL', N'Work Integrated Learning (WIL) / Internship', N'Workplace experiential learning for graduates and diploma candidates.', 1, N'PIVOTAL', 55000.00),
                    (N'CANDIDACY', N'Candidacy Programme (Professional Registration)', N'Mentored practical training toward ECSA or SACPCMP professional registration.', 1, N'PIVOTAL', 90000.00),
                    (N'AET', N'Adult Education and Training (AET)', N'Foundational literacy and numeracy learning for workers.', 1, N'PIVOTAL', 12000.00),
                    -- Non-PIVOTAL Catalog
                    (N'BURSARY_NON_CREDIT', N'Bursary Non-Credit', N'Discretionary bursary funding for non-credit developmental and leadership short courses.', 0, N'NON_PIVOTAL', 25000.00),
                    (N'CAREER_GUIDANCE', N'Career Guidance & Exhibition', N'Socio-economic awareness campaigns, learner career exhibitions, and rural school guidance.', 0, N'NON_PIVOTAL', 100000.00),
                    (N'WORKSHOP_EQUIPMENT', N'Workshop Equipment & Tooling', N'Infrastructure modernization grant for TVET college and accredited training center workshops.', 0, N'NON_PIVOTAL', 500000.00),
                    (N'TRADE_UNION_CAPACITY', N'Trade Union Training & Capacity Building', N'Capacity building programmes for shop stewards and union leadership in advanced manufacturing.', 0, N'NON_PIVOTAL', 75000.00),
                    (N'RESEARCH_CHAIR', N'Research Chair & Innovation Partnership', N'Higher education research chair supporting automotive, renewable energy, and green economy development.', 0, N'NON_PIVOTAL', 1200000.00),
                    (N'RPL_NON_PIVOTAL', N'Recognition of Prior Learning Advisory (Non-Credit)', N'Pre-assessment portfolio coaching and advisory support for informal sector artisans.', 0, N'NON_PIVOTAL', 18000.00),
                    (N'SMME_INCUBATION', N'SMME Incubation & Mentorship Support', N'Small enterprise technical incubation and mentor placement support.', 0, N'NON_PIVOTAL', 150000.00)
                ) AS source ([Code], [Name], [Description], [IsPivotal], [Category], [DefaultUnitCost])
                ON target.[Code] = source.[Code]
                WHEN MATCHED THEN
                    UPDATE SET target.[IsPivotal] = source.[IsPivotal],
                               target.[Category] = source.[Category],
                               target.[DefaultUnitCost] = source.[DefaultUnitCost]
                WHEN NOT MATCHED THEN
                    INSERT ([Code], [Name], [Description], [Active], [IsPivotal], [Category], [DefaultUnitCost], [CreatedAt], [CreatedBy])
                    VALUES (source.[Code], source.[Name], source.[Description], 1, source.[IsPivotal], source.[Category], source.[DefaultUnitCost], SYSUTCDATETIME(), 'SYSTEM');

                -- 3. Update dbo.GrantFundingWindow
                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantFundingWindow')
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'IsPivotal')
                        ALTER TABLE [dbo].[GrantFundingWindow] ADD [IsPivotal] BIT NOT NULL CONSTRAINT DF_GrantFundingWindow_IsPivotal DEFAULT 1;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'WindowClassification')
                        ALTER TABLE [dbo].[GrantFundingWindow] ADD [WindowClassification] NVARCHAR(50) NOT NULL CONSTRAINT DF_GrantFundingWindow_Classification DEFAULT 'Pivotal';

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'RequireWspCompliance')
                        ALTER TABLE [dbo].[GrantFundingWindow] ADD [RequireWspCompliance] BIT NOT NULL CONSTRAINT DF_GrantFundingWindow_RequireWsp DEFAULT 0;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'TemplateId')
                        ALTER TABLE [dbo].[GrantFundingWindow] ADD [TemplateId] INT NULL;

                    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_GrantFundingWindow_IsPivotal' AND object_id = OBJECT_ID('GrantFundingWindow'))
                        CREATE INDEX [IX_GrantFundingWindow_IsPivotal] ON [dbo].[GrantFundingWindow] ([IsPivotal]);

                    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_GrantFundingWindow_TemplateId' AND object_id = OBJECT_ID('GrantFundingWindow'))
                        CREATE INDEX [IX_GrantFundingWindow_TemplateId] ON [dbo].[GrantFundingWindow] ([TemplateId]);
                END;

                -- 4. Create dbo.GrantWindowEligibility
                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantWindowEligibility')
                BEGIN
                    CREATE TABLE [dbo].[GrantWindowEligibility] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [FundingWindowId] INT NOT NULL CONSTRAINT FK_GrantWindowEligibility_Window FOREIGN KEY REFERENCES [dbo].[GrantFundingWindow]([Id]) ON DELETE CASCADE,
                        [StakeholderEligibilityTypeCode] NVARCHAR(50) NOT NULL CONSTRAINT FK_GrantWindowEligibility_Type FOREIGN KEY REFERENCES [lookup].[StakeholderEligibilityType]([Code]),
                        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_GrantWindowEligibility_CreatedAt DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_GrantWindowEligibility_CreatedBy DEFAULT 'SYSTEM',
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL,
                        CONSTRAINT UQ_GrantWindowEligibility UNIQUE ([FundingWindowId], [StakeholderEligibilityTypeCode])
                    );
                    CREATE INDEX [IX_GrantWindowEligibility_WindowId] ON [dbo].[GrantWindowEligibility] ([FundingWindowId]);
                    CREATE INDEX [IX_GrantWindowEligibility_TypeCode] ON [dbo].[GrantWindowEligibility] ([StakeholderEligibilityTypeCode]);
                END;

                -- 5. Create dbo.GrantWindowIntervention
                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantWindowIntervention')
                BEGIN
                    CREATE TABLE [dbo].[GrantWindowIntervention] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [FundingWindowId] INT NOT NULL CONSTRAINT FK_GrantWindowIntervention_Window FOREIGN KEY REFERENCES [dbo].[GrantFundingWindow]([Id]) ON DELETE CASCADE,
                        [InterventionTypeCode] NVARCHAR(50) NOT NULL CONSTRAINT FK_GrantWindowIntervention_Type FOREIGN KEY REFERENCES [lookup].[InterventionType]([Code]),
                        [MaxBudgetCap] DECIMAL(18,2) NULL,
                        [MaxLearnerCap] INT NULL,
                        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_GrantWindowIntervention_CreatedAt DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_GrantWindowIntervention_CreatedBy DEFAULT 'SYSTEM',
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL,
                        CONSTRAINT UQ_GrantWindowIntervention UNIQUE ([FundingWindowId], [InterventionTypeCode])
                    );
                    CREATE INDEX [IX_GrantWindowIntervention_WindowId] ON [dbo].[GrantWindowIntervention] ([FundingWindowId]);
                    CREATE INDEX [IX_GrantWindowIntervention_TypeCode] ON [dbo].[GrantWindowIntervention] ([InterventionTypeCode]);
                END;

                -- 6. Create dbo.GrantWindowTemplate
                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantWindowTemplate')
                BEGIN
                    CREATE TABLE [dbo].[GrantWindowTemplate] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [TemplateCode] NVARCHAR(50) NOT NULL CONSTRAINT UQ_GrantWindowTemplate_Code UNIQUE,
                        [Name] NVARCHAR(200) NOT NULL,
                        [Description] NVARCHAR(1000) NULL,
                        [IsPivotal] BIT NOT NULL CONSTRAINT DF_GrantWindowTemplate_IsPivotal DEFAULT 1,
                        [WindowClassification] NVARCHAR(50) NOT NULL CONSTRAINT DF_GrantWindowTemplate_Classification DEFAULT 'Pivotal',
                        [RequireWspComplianceDefault] BIT NOT NULL CONSTRAINT DF_GrantWindowTemplate_RequireWsp DEFAULT 0,
                        [EstimatedDurationDays] INT NOT NULL CONSTRAINT DF_GrantWindowTemplate_Duration DEFAULT 45,
                        [IsActive] BIT NOT NULL CONSTRAINT DF_GrantWindowTemplate_Active DEFAULT 1,
                        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_GrantWindowTemplate_CreatedAt DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_GrantWindowTemplate_CreatedBy DEFAULT 'SYSTEM',
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL
                    );
                    CREATE INDEX [IX_GrantWindowTemplate_Active] ON [dbo].[GrantWindowTemplate] ([IsActive]);
                END;

                -- 7. Create dbo.GrantWindowTemplateEligibility
                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantWindowTemplateEligibility')
                BEGIN
                    CREATE TABLE [dbo].[GrantWindowTemplateEligibility] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [TemplateId] INT NOT NULL CONSTRAINT FK_GrantWindowTemplateEligibility_Template FOREIGN KEY REFERENCES [dbo].[GrantWindowTemplate]([Id]) ON DELETE CASCADE,
                        [StakeholderEligibilityTypeCode] NVARCHAR(50) NOT NULL CONSTRAINT FK_GrantWindowTemplateEligibility_Type FOREIGN KEY REFERENCES [lookup].[StakeholderEligibilityType]([Code]),
                        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_GrantWindowTemplateEligibility_CreatedAt DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_GrantWindowTemplateEligibility_CreatedBy DEFAULT 'SYSTEM',
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL,
                        CONSTRAINT UQ_GrantWindowTemplateEligibility UNIQUE ([TemplateId], [StakeholderEligibilityTypeCode])
                    );
                    CREATE INDEX [IX_GrantWindowTemplateEligibility_TemplateId] ON [dbo].[GrantWindowTemplateEligibility] ([TemplateId]);
                END;

                -- 8. Create dbo.GrantWindowTemplateIntervention
                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantWindowTemplateIntervention')
                BEGIN
                    CREATE TABLE [dbo].[GrantWindowTemplateIntervention] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [TemplateId] INT NOT NULL CONSTRAINT FK_GrantWindowTemplateIntervention_Template FOREIGN KEY REFERENCES [dbo].[GrantWindowTemplate]([Id]) ON DELETE CASCADE,
                        [InterventionTypeCode] NVARCHAR(50) NOT NULL CONSTRAINT FK_GrantWindowTemplateIntervention_Type FOREIGN KEY REFERENCES [lookup].[InterventionType]([Code]),
                        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_GrantWindowTemplateIntervention_CreatedAt DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_GrantWindowTemplateIntervention_CreatedBy DEFAULT 'SYSTEM',
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL,
                        CONSTRAINT UQ_GrantWindowTemplateIntervention UNIQUE ([TemplateId], [InterventionTypeCode])
                    );
                    CREATE INDEX [IX_GrantWindowTemplateIntervention_TemplateId] ON [dbo].[GrantWindowTemplateIntervention] ([TemplateId]);
                END;

                -- 9. Seed Default Templates (Option B Blueprints)
                IF NOT EXISTS (SELECT 1 FROM [dbo].[GrantWindowTemplate] WHERE [TemplateCode] = 'TPL_PIVOTAL_ANNUAL')
                BEGIN
                    INSERT INTO [dbo].[GrantWindowTemplate] ([TemplateCode], [Name], [Description], [IsPivotal], [WindowClassification], [RequireWspComplianceDefault], [EstimatedDurationDays], [IsActive], [CreatedAt], [CreatedBy])
                    VALUES ('TPL_PIVOTAL_ANNUAL', 'Standard Annual Gazetted PIVOTAL Window', 'Gazetted annual window for occupation-directed qualifications, apprenticeships, learnerships, and bursaries.', 1, 'Pivotal', 0, 60, 1, SYSUTCDATETIME(), 'SYSTEM');
                    
                    DECLARE @pivotalTplId INT = SCOPE_IDENTITY();
                    
                    -- Default Eligibilities
                    INSERT INTO [dbo].[GrantWindowTemplateEligibility] ([TemplateId], [StakeholderEligibilityTypeCode], [CreatedAt], [CreatedBy])
                    VALUES 
                        (@pivotalTplId, 'LEVY_PAYING', SYSUTCDATETIME(), 'SYSTEM'),
                        (@pivotalTplId, 'PUBLIC_TVET', SYSUTCDATETIME(), 'SYSTEM'),
                        (@pivotalTplId, 'PUBLIC_HEI', SYSUTCDATETIME(), 'SYSTEM'),
                        (@pivotalTplId, 'PRIVATE_SDP', SYSUTCDATETIME(), 'SYSTEM');

                    -- Default Interventions
                    INSERT INTO [dbo].[GrantWindowTemplateIntervention] ([TemplateId], [InterventionTypeCode], [CreatedAt], [CreatedBy])
                    VALUES 
                        (@pivotalTplId, 'LEARNERSHIP_18_1', SYSUTCDATETIME(), 'SYSTEM'),
                        (@pivotalTplId, 'LEARNERSHIP_18_2', SYSUTCDATETIME(), 'SYSTEM'),
                        (@pivotalTplId, 'APPRENTICESHIP', SYSUTCDATETIME(), 'SYSTEM'),
                        (@pivotalTplId, 'SKILLS_PROGRAMME', SYSUTCDATETIME(), 'SYSTEM'),
                        (@pivotalTplId, 'BURSARY_PIVOTAL', SYSUTCDATETIME(), 'SYSTEM'),
                        (@pivotalTplId, 'INTERNSHIP_WIL', SYSUTCDATETIME(), 'SYSTEM'),
                        (@pivotalTplId, 'CANDIDACY', SYSUTCDATETIME(), 'SYSTEM');
                END;

                IF NOT EXISTS (SELECT 1 FROM [dbo].[GrantWindowTemplate] WHERE [TemplateCode] = 'TPL_STRATEGIC_NON_PIVOTAL')
                BEGIN
                    INSERT INTO [dbo].[GrantWindowTemplate] ([TemplateCode], [Name], [Description], [IsPivotal], [WindowClassification], [RequireWspComplianceDefault], [EstimatedDurationDays], [IsActive], [CreatedAt], [CreatedBy])
                    VALUES ('TPL_STRATEGIC_NON_PIVOTAL', 'Strategic Non-PIVOTAL Special Projects Window', 'Special window for TVET equipment modernization, research chairs, trade union capacity building, and career guidance.', 0, 'NonPivotal', 0, 45, 1, SYSUTCDATETIME(), 'SYSTEM');
                    
                    DECLARE @nonPivotalTplId INT = SCOPE_IDENTITY();

                    -- Default Eligibilities
                    INSERT INTO [dbo].[GrantWindowTemplateEligibility] ([TemplateId], [StakeholderEligibilityTypeCode], [CreatedAt], [CreatedBy])
                    VALUES 
                        (@nonPivotalTplId, 'PUBLIC_TVET', SYSUTCDATETIME(), 'SYSTEM'),
                        (@nonPivotalTplId, 'PUBLIC_HEI', SYSUTCDATETIME(), 'SYSTEM'),
                        (@nonPivotalTplId, 'TRADE_UNION', SYSUTCDATETIME(), 'SYSTEM'),
                        (@nonPivotalTplId, 'NGO_CBO', SYSUTCDATETIME(), 'SYSTEM'),
                        (@nonPivotalTplId, 'EMPLOYER_ASSOC', SYSUTCDATETIME(), 'SYSTEM');

                    -- Default Interventions
                    INSERT INTO [dbo].[GrantWindowTemplateIntervention] ([TemplateId], [InterventionTypeCode], [CreatedAt], [CreatedBy])
                    VALUES 
                        (@nonPivotalTplId, 'WORKSHOP_EQUIPMENT', SYSUTCDATETIME(), 'SYSTEM'),
                        (@nonPivotalTplId, 'CAREER_GUIDANCE', SYSUTCDATETIME(), 'SYSTEM'),
                        (@nonPivotalTplId, 'TRADE_UNION_CAPACITY', SYSUTCDATETIME(), 'SYSTEM'),
                        (@nonPivotalTplId, 'RESEARCH_CHAIR', SYSUTCDATETIME(), 'SYSTEM'),
                        (@nonPivotalTplId, 'BURSARY_NON_CREDIT', SYSUTCDATETIME(), 'SYSTEM'),
                        (@nonPivotalTplId, 'SMME_INCUBATION', SYSUTCDATETIME(), 'SYSTEM');
                END;

                IF NOT EXISTS (SELECT 1 FROM [dbo].[GrantWindowTemplate] WHERE [TemplateCode] = 'TPL_SMME_ACCELERATOR')
                BEGIN
                    INSERT INTO [dbo].[GrantWindowTemplate] ([TemplateCode], [Name], [Description], [IsPivotal], [WindowClassification], [RequireWspComplianceDefault], [EstimatedDurationDays], [IsActive], [CreatedAt], [CreatedBy])
                    VALUES ('TPL_SMME_ACCELERATOR', 'SMME Accelerated Skills Development Window', 'Targeted window for small and micro businesses and non-levy paying enterprises.', 1, 'Pivotal', 0, 30, 1, SYSUTCDATETIME(), 'SYSTEM');
                    
                    DECLARE @smmeTplId INT = SCOPE_IDENTITY();

                    INSERT INTO [dbo].[GrantWindowTemplateEligibility] ([TemplateId], [StakeholderEligibilityTypeCode], [CreatedAt], [CreatedBy])
                    VALUES 
                        (@smmeTplId, 'LEVY_PAYING', SYSUTCDATETIME(), 'SYSTEM'),
                        (@smmeTplId, 'NON_LEVY_EXEMPT', SYSUTCDATETIME(), 'SYSTEM');

                    INSERT INTO [dbo].[GrantWindowTemplateIntervention] ([TemplateId], [InterventionTypeCode], [CreatedAt], [CreatedBy])
                    VALUES 
                        (@smmeTplId, 'SKILLS_PROGRAMME', SYSUTCDATETIME(), 'SYSTEM'),
                        (@smmeTplId, 'LEARNERSHIP_18_1', SYSUTCDATETIME(), 'SYSTEM'),
                        (@smmeTplId, 'APPRENTICESHIP', SYSUTCDATETIME(), 'SYSTEM');
                END;
            ";

            await context.Database.ExecuteSqlRawAsync(ddlSql);
            logger?.LogInformation("Phase 47 Schema Migration: StakeholderEligibilityType, InterventionType catalog, GrantWindowEligibility, GrantWindowIntervention, and GrantWindowTemplate blueprints verified.");
        }
    }
}
