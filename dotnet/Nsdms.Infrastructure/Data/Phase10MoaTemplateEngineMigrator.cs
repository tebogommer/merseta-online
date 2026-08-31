using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator and seeder for the MoA Template & Reusable Clause Engine (Option A).
/// </summary>
public static class Phase10MoaTemplateEngineMigrator
{
    public static async Task MigrateMoaTemplateSchemaAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        var ddl = @"
-- 1. Create MoaTemplate Table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'MoaTemplate')
BEGIN
    CREATE TABLE [dbo].[MoaTemplate] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_MoaTemplate] PRIMARY KEY CLUSTERED,
        [TemplateCode] NVARCHAR(100) NOT NULL,
        [TemplateTitle] NVARCHAR(200) NOT NULL,
        [FinancialYear] INT NOT NULL,
        [GrantTypeCode] NVARCHAR(50) NOT NULL CONSTRAINT [DF_MoaTemplate_GrantType] DEFAULT 'DiscretionaryGrant',
        [LegalEntityType] NVARCHAR(50) NOT NULL CONSTRAINT [DF_MoaTemplate_LegalEntityType] DEFAULT 'All',
        [VersionNumber] NVARCHAR(20) NOT NULL CONSTRAINT [DF_MoaTemplate_Version] DEFAULT '1.0.0',
        [ApprovalStatus] NVARCHAR(50) NOT NULL CONSTRAINT [DF_MoaTemplate_ApprovalStatus] DEFAULT 'Draft',
        [EffectiveFrom] DATETIME2 NOT NULL CONSTRAINT [DF_MoaTemplate_EffectiveFrom] DEFAULT SYSUTCDATETIME(),
        [EffectiveTo] DATETIME2 NULL,
        [IsActive] BIT NOT NULL CONSTRAINT [DF_MoaTemplate_IsActive] DEFAULT 1,
        [ApprovedBy] NVARCHAR(100) NULL,
        [ApprovedAt] DATETIME2 NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_MoaTemplate_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE UNIQUE INDEX [IX_MoaTemplate_TemplateCode] ON [dbo].[MoaTemplate]([TemplateCode]);
    CREATE INDEX [IX_MoaTemplate_FinYear_GrantType] ON [dbo].[MoaTemplate]([FinancialYear], [GrantTypeCode], [IsActive]);
    CREATE INDEX [IX_MoaTemplate_ApprovalStatus] ON [dbo].[MoaTemplate]([ApprovalStatus]);
END;

-- 2. Create MoaClause Table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'MoaClause')
BEGIN
    CREATE TABLE [dbo].[MoaClause] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_MoaClause] PRIMARY KEY CLUSTERED,
        [ClauseCode] NVARCHAR(100) NOT NULL,
        [ClauseTitle] NVARCHAR(200) NOT NULL,
        [Category] NVARCHAR(50) NOT NULL CONSTRAINT [DF_MoaClause_Category] DEFAULT 'General',
        [ClauseContent] NVARCHAR(MAX) NOT NULL,
        [IsMandatory] BIT NOT NULL CONSTRAINT [DF_MoaClause_IsMandatory] DEFAULT 1,
        [IsActive] BIT NOT NULL CONSTRAINT [DF_MoaClause_IsActive] DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_MoaClause_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE UNIQUE INDEX [IX_MoaClause_ClauseCode] ON [dbo].[MoaClause]([ClauseCode]);
    CREATE INDEX [IX_MoaClause_Category] ON [dbo].[MoaClause]([Category]);
    CREATE INDEX [IX_MoaClause_IsActive] ON [dbo].[MoaClause]([IsActive]);
END;

-- 3. Create MoaTemplateSection Table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'MoaTemplateSection')
BEGIN
    CREATE TABLE [dbo].[MoaTemplateSection] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_MoaTemplateSection] PRIMARY KEY CLUSTERED,
        [MoaTemplateId] INT NOT NULL,
        [MoaClauseId] INT NOT NULL,
        [SectionNumber] NVARCHAR(50) NOT NULL,
        [SectionTitle] NVARCHAR(200) NOT NULL,
        [SequenceOrder] INT NOT NULL,
        [IsMandatory] BIT NOT NULL CONSTRAINT [DF_MoaTemplateSection_IsMandatory] DEFAULT 1,
        [ConditionRuleJson] NVARCHAR(1000) NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_MoaTemplateSection_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [FK_MoaTemplateSection_MoaTemplate] FOREIGN KEY ([MoaTemplateId]) REFERENCES [dbo].[MoaTemplate]([Id]) ON DELETE CASCADE,
        CONSTRAINT [FK_MoaTemplateSection_MoaClause] FOREIGN KEY ([MoaClauseId]) REFERENCES [dbo].[MoaClause]([Id])
    );

    CREATE INDEX [IX_MoaTemplateSection_MoaTemplateId] ON [dbo].[MoaTemplateSection]([MoaTemplateId]);
    CREATE INDEX [IX_MoaTemplateSection_MoaClauseId] ON [dbo].[MoaTemplateSection]([MoaClauseId]);
    CREATE INDEX [IX_MoaTemplateSection_Sequence] ON [dbo].[MoaTemplateSection]([MoaTemplateId], [SequenceOrder]);
END;

-- 4. Create MoaExecutionSnapshot Table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'MoaExecutionSnapshot')
BEGIN
    CREATE TABLE [dbo].[MoaExecutionSnapshot] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_MoaExecutionSnapshot] PRIMARY KEY CLUSTERED,
        [GrantMoaId] INT NOT NULL,
        [MoaTemplateId] INT NOT NULL,
        [TemplateVersionNumber] NVARCHAR(20) NOT NULL,
        [RenderedContentHash] NVARCHAR(100) NOT NULL,
        [RenderedContent] NVARCHAR(MAX) NOT NULL,
        [PdfStorageUri] NVARCHAR(500) NULL,
        [FrozenAt] DATETIME2 NOT NULL CONSTRAINT [DF_MoaExecutionSnapshot_FrozenAt] DEFAULT SYSUTCDATETIME(),
        [SignatoryEmployer] NVARCHAR(150) NULL,
        [SignatorySeta] NVARCHAR(150) NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_MoaExecutionSnapshot_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [FK_MoaExecutionSnapshot_GrantMoa] FOREIGN KEY ([GrantMoaId]) REFERENCES [dbo].[GrantMoa]([Id]) ON DELETE CASCADE,
        CONSTRAINT [FK_MoaExecutionSnapshot_MoaTemplate] FOREIGN KEY ([MoaTemplateId]) REFERENCES [dbo].[MoaTemplate]([Id])
    );

    CREATE INDEX [IX_MoaExecutionSnapshot_GrantMoaId] ON [dbo].[MoaExecutionSnapshot]([GrantMoaId]);
    CREATE INDEX [IX_MoaExecutionSnapshot_MoaTemplateId] ON [dbo].[MoaExecutionSnapshot]([MoaTemplateId]);
    CREATE INDEX [IX_MoaExecutionSnapshot_Hash] ON [dbo].[MoaExecutionSnapshot]([RenderedContentHash]);
    CREATE INDEX [IX_MoaExecutionSnapshot_FrozenAt] ON [dbo].[MoaExecutionSnapshot]([FrozenAt]);
END;

-- 5. Add MoaTemplateId to GrantMoa table if missing
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantMoa') AND name = 'MoaTemplateId')
BEGIN
    ALTER TABLE [dbo].[GrantMoa] ADD [MoaTemplateId] INT NULL;
    ALTER TABLE [dbo].[GrantMoa] ADD CONSTRAINT [FK_GrantMoa_MoaTemplate] FOREIGN KEY ([MoaTemplateId]) REFERENCES [dbo].[MoaTemplate]([Id]);
    CREATE INDEX [IX_GrantMoa_MoaTemplateId] ON [dbo].[GrantMoa]([MoaTemplateId]);
END;
";

        try
        {
            await context.Database.ExecuteSqlRawAsync(ddl);
            logger?.LogInformation("[SCHEMA MIGRATOR] Phase 10 MoA Template Engine schema applied successfully.");
        }
        catch (Exception ex)
        {
            logger?.LogWarning(ex, "[SCHEMA MIGRATOR] Phase 10 migration DDL notice: {Message}", ex.Message);
        }

        // Seed initial Clause Library and Master Templates
        try
        {
            await SeedClausesAndTemplatesAsync(context, logger);
        }
        catch (Exception ex)
        {
            logger?.LogWarning(ex, "[SCHEMA MIGRATOR] Phase 10 seeding notice: {Message}", ex.Message);
        }
    }

    private static async Task SeedClausesAndTemplatesAsync(NsdmsDbContext context, ILogger? logger)
    {
        // 1. Seed Reusable Clauses
        var standardClauses = new List<MoaClause>
        {
            new MoaClause
            {
                ClauseCode = "CLAUSE-PREAMBLE",
                ClauseTitle = "1. Parties & Statutory Authority",
                Category = "Statutory",
                IsMandatory = true,
                IsActive = true,
                ClauseContent = @"This Memorandum of Agreement (MoA) is entered into by and between the **Manufacturing, Engineering and Related Services SETA (merSETA)**, established in terms of Section 9(1) of the Skills Development Act 97 of 1998, herein represented by its Chief Executive Officer, and **{{OrganisationName}}** (SDL Number: `{{LevyNumber}}`), herein represented by its duly authorised signatory.

**Project Title:** {{ProjectTitle}}  
**Agreement Reference:** `{{MoaNumber}}`  
**Financial Grant Year:** {{GrantYear}}  
**Total Committed Grant Value:** R {{TotalContractValue}}  
**Commencement Date:** {{ContractStartDate}} | **Termination Date:** {{ContractEndDate}}"
            },
            new MoaClause
            {
                ClauseCode = "CLAUSE-DEFINITIONS",
                ClauseTitle = "2. Definitions & Interpretation",
                Category = "Statutory",
                IsMandatory = true,
                IsActive = true,
                ClauseContent = @"In this Agreement, unless the context clearly indicates a contrary intention:
- **""Act""** means the Skills Development Act, 1998 (Act No. 97 of 1998) as amended;
- **""Grantee / Employer""** means **{{OrganisationName}}**;
- **""Grant Regulations""** means the Sector Education and Training Authorities (SETA) Grant Regulations published under GN R35923;
- **""Tranche""** means an approved monetary disbursement payable upon verified delivery of contracted milestone deliverables;
- **""PIP""** means the Project Implementation Plan (Annexure D) mutually agreed between merSETA and the Grantee."
            },
            new MoaClause
            {
                ClauseCode = "CLAUSE-TRANCHES",
                ClauseTitle = "3. Tranche Disbursement Milestones & Payment Schedule",
                Category = "Financial",
                IsMandatory = true,
                IsActive = true,
                ClauseContent = @"The merSETA shall disburse the allocated grant funding of **R {{TotalContractValue}}** in accordance with the verified delivery milestones set out below. Disbursements are subject to the submission of compliant tax invoices and supporting evidence:

{{TrancheScheduleTable}}

All payments shall be made directly into the verified banking account of **{{OrganisationName}}** as recorded in Annexure C."
            },
            new MoaClause
            {
                ClauseCode = "CLAUSE-CLAWBACK",
                ClauseTitle = "4. Unspent Funds, Breach & Financial Clawback",
                Category = "Financial",
                IsMandatory = true,
                IsActive = true,
                ClauseContent = @"In the event that the Grantee fails to register contracted learners, misses statutory implementation deadlines, or fails to submit required learner evidence within the agreed timeframes:
1. The merSETA reserves the right to sweep, recover, or clawback any uncommitted or unspent funds.
2. The Grantee shall be liable to refund any disbursed tranches where deliverables remain unfulfilled after 30 business days written notice.
3. No grant funds may be redirected to other interventions without prior formal written approval and an executed Contract Addendum."
            },
            new MoaClause
            {
                ClauseCode = "CLAUSE-POPIA",
                ClauseTitle = "5. POPIA Compliance & Personal Data Privacy",
                Category = "Compliance",
                IsMandatory = true,
                IsActive = true,
                ClauseContent = @"Both Parties undertake to strictly comply with the provisions of the **Protection of Personal Information Act 4 of 2013 (POPIA)** in respect of all learner and demographic records. The Grantee warrants that explicit data processing consent has been obtained from all registered learners prior to submitting records to the National Skills Development Management System (NSDMS)."
            },
            new MoaClause
            {
                ClauseCode = "CLAUSE-BBBEE",
                ClauseTitle = "6. Broad-Based Black Economic Empowerment (B-BBEE) Covenants",
                Category = "Compliance",
                IsMandatory = false,
                IsActive = true,
                ClauseContent = @"The Grantee warrants that it maintains a valid B-BBEE contributor certificate or sworn affidavit throughout the duration of this Agreement. A verified copy of the valid B-BBEE verification certificate shall be submitted as a condition precedent to the disbursement of Tranche 1."
            },
            new MoaClause
            {
                ClauseCode = "CLAUSE-DISPUTE",
                ClauseTitle = "7. Dispute Resolution & Arbitration",
                Category = "General",
                IsMandatory = true,
                IsActive = true,
                ClauseContent = @"Any dispute arising out of or in connection with this Agreement shall first be referred to executive negotiation between the merSETA Chief Operations Officer and the Grantee’s Chief Executive. Should negotiation fail within 14 business days, the dispute shall be referred to arbitration in Johannesburg in accordance with the rules of the Arbitration Foundation of Southern Africa (AFSA)."
            },
            new MoaClause
            {
                ClauseCode = "CLAUSE-ANNEXURE-A",
                ClauseTitle = "ANNEXURE A: Grant Allocation & Intervention Schedule",
                Category = "Annexure",
                IsMandatory = true,
                IsActive = true,
                ClauseContent = @"**Approved Intervention Quotas & Allocations:**
- Contract Number: `{{MoaNumber}}`
- Targeted Beneficiary Numbers: In accordance with merSETA Regional Committee approvals.
- Funding Window: Discretionary Grants {{GrantYear}}.
- All training must be provided by merSETA or QCTO accredited Skills Development Providers (SDPs)."
            },
            new MoaClause
            {
                ClauseCode = "CLAUSE-ANNEXURE-C",
                ClauseTitle = "ANNEXURE C: Verified Banking Details & Fraud Prevention",
                Category = "Annexure",
                IsMandatory = true,
                IsActive = true,
                ClauseContent = @"All electronic disbursements shall be routed exclusively to the verified corporate bank account on file. Any bank account alteration requires a formal bank-stamped confirmation letter, director resolution, and manual verification by merSETA Finance."
            },
            new MoaClause
            {
                ClauseCode = "CLAUSE-SIGNATURES",
                ClauseTitle = "8. Bilateral Execution & Signatures",
                Category = "Statutory",
                IsMandatory = true,
                IsActive = true,
                ClauseContent = @"THUS DONE AND SIGNED at ____________________ on this _____ day of _______________ 20___.

**For the Grantee / Employer ({{OrganisationName}}):**  
Name: `{{SignatoryEmployer}}`  
Designation: Authorised Legal Representative  
Signature: _____________________________________

**For the merSETA:**  
Name: `{{SignatorySeta}}`  
Designation: Chief Executive Officer / Delegated Authority  
Signature: _____________________________________"
            }
        };

        foreach (var clause in standardClauses)
        {
            var existing = await context.MoaClauses.FirstOrDefaultAsync(c => c.ClauseCode == clause.ClauseCode);
            if (existing == null)
            {
                context.MoaClauses.Add(clause);
            }
            else
            {
                existing.ClauseTitle = clause.ClauseTitle;
                existing.ClauseContent = clause.ClauseContent;
                existing.Category = clause.Category;
                existing.IsMandatory = clause.IsMandatory;
            }
        }
        await context.SaveChangesAsync();

        // 2. Seed Master Templates
        var clausesMap = await context.MoaClauses.ToDictionaryAsync(c => c.ClauseCode);

        // Template 1: 2026/27 Standard Discretionary Grant
        var template2026 = await context.MoaTemplates
            .Include(t => t.Sections)
            .FirstOrDefaultAsync(t => t.TemplateCode == "DG-STD-2026");

        if (template2026 == null)
        {
            template2026 = new MoaTemplate
            {
                TemplateCode = "DG-STD-2026",
                TemplateTitle = "merSETA Discretionary Grant Standard Agreement (2026/27)",
                FinancialYear = 2026,
                GrantTypeCode = "DiscretionaryGrant",
                LegalEntityType = "All",
                VersionNumber = "1.0.0",
                ApprovalStatus = "Approved",
                EffectiveFrom = new DateTime(2026, 4, 1),
                EffectiveTo = new DateTime(2027, 3, 31),
                IsActive = true,
                ApprovedBy = "Legal & Governance Executive",
                ApprovedAt = DateTime.UtcNow
            };
            context.MoaTemplates.Add(template2026);
            await context.SaveChangesAsync();

            // Link Sections in order
            var sectionConfigs = new List<(string clauseCode, string secNum, string secTitle, int order)>
            {
                ("CLAUSE-PREAMBLE", "1.0", "Parties & Statutory Authority", 10),
                ("CLAUSE-DEFINITIONS", "2.0", "Definitions & Interpretation", 20),
                ("CLAUSE-TRANCHES", "3.0", "Tranche Disbursement Schedule", 30),
                ("CLAUSE-CLAWBACK", "4.0", "Unspent Funds & Clawback", 40),
                ("CLAUSE-POPIA", "5.0", "POPIA Data Privacy", 50),
                ("CLAUSE-BBBEE", "6.0", "B-BBEE Compliance Covenants", 60),
                ("CLAUSE-DISPUTE", "7.0", "Dispute Resolution & Arbitration", 70),
                ("CLAUSE-ANNEXURE-A", "Sched 1", "Annexure A: Intervention Allocation", 80),
                ("CLAUSE-ANNEXURE-C", "Sched 2", "Annexure C: Banking Details", 90),
                ("CLAUSE-SIGNATURES", "8.0", "Bilateral Signatures", 100)
            };

            foreach (var (code, secNum, secTitle, order) in sectionConfigs)
            {
                if (clausesMap.TryGetValue(code, out var cl))
                {
                    template2026.Sections.Add(new MoaTemplateSection
                    {
                        MoaTemplateId = template2026.Id,
                        MoaClauseId = cl.Id,
                        SectionNumber = secNum,
                        SectionTitle = secTitle,
                        SequenceOrder = order,
                        IsMandatory = true
                    });
                }
            }
            await context.SaveChangesAsync();
        }

        // Template 2: 2025/26 Standard Discretionary Grant (Historical policy version)
        var template2025 = await context.MoaTemplates
            .Include(t => t.Sections)
            .FirstOrDefaultAsync(t => t.TemplateCode == "DG-STD-2025");

        if (template2025 == null)
        {
            template2025 = new MoaTemplate
            {
                TemplateCode = "DG-STD-2025",
                TemplateTitle = "merSETA Discretionary Grant Standard Agreement (2025/26)",
                FinancialYear = 2025,
                GrantTypeCode = "DiscretionaryGrant",
                LegalEntityType = "All",
                VersionNumber = "1.2.0",
                ApprovalStatus = "Approved",
                EffectiveFrom = new DateTime(2025, 4, 1),
                EffectiveTo = new DateTime(2026, 3, 31),
                IsActive = false,
                ApprovedBy = "Legal & Governance Executive",
                ApprovedAt = new DateTime(2025, 4, 1)
            };
            context.MoaTemplates.Add(template2025);
            await context.SaveChangesAsync();

            var secConfigs2025 = new List<(string clauseCode, string secNum, string secTitle, int order)>
            {
                ("CLAUSE-PREAMBLE", "1.0", "Parties & Statutory Authority", 10),
                ("CLAUSE-DEFINITIONS", "2.0", "Definitions & Interpretation", 20),
                ("CLAUSE-TRANCHES", "3.0", "Tranche Disbursement Schedule", 30),
                ("CLAUSE-CLAWBACK", "4.0", "Unspent Funds & Clawback", 40),
                ("CLAUSE-POPIA", "5.0", "POPIA Data Privacy", 50),
                ("CLAUSE-DISPUTE", "6.0", "Dispute Resolution & Arbitration", 60),
                ("CLAUSE-SIGNATURES", "7.0", "Bilateral Signatures", 70)
            };

            foreach (var (code, secNum, secTitle, order) in secConfigs2025)
            {
                if (clausesMap.TryGetValue(code, out var cl))
                {
                    template2025.Sections.Add(new MoaTemplateSection
                    {
                        MoaTemplateId = template2025.Id,
                        MoaClauseId = cl.Id,
                        SectionNumber = secNum,
                        SectionTitle = secTitle,
                        SequenceOrder = order,
                        IsMandatory = true
                    });
                }
            }
            await context.SaveChangesAsync();
        }

        // Link existing GrantMoa entities to the 2026 template if unlinked
        var unlinkedMoas = await context.GrantMoas.Where(m => m.MoaTemplateId == null).ToListAsync();
        if (unlinkedMoas.Count > 0 && template2026 != null)
        {
            foreach (var moa in unlinkedMoas)
            {
                moa.MoaTemplateId = template2026.Id;
            }
            await context.SaveChangesAsync();
        }

        logger?.LogInformation("[SCHEMA MIGRATOR] Seeded {ClauseCount} MoA clauses and {TemplateCount} MoA master templates.",
            standardClauses.Count, 2);
    }
}
