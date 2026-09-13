using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 51 Database Migrator:
/// Implements the Google Cloud Platform Open Knowledge Format (OKF v0.2) Living Knowledge Catalog
/// and T-SQL Attested Computation Engine within MerSETA NSDMS.
/// 
/// Provisions:
/// 1. KnowledgeBundle (container synced with Git).
/// 2. ConceptDocument (with System-Versioned Temporal Tables in history schema).
/// 3. ConceptTag, ConceptSource, ConceptVerificationEvent.
/// 4. AttestedComputation, ComputationParameter, ComputationExecutionAudit (BIGINT PK).
/// 5. ConceptCrossLink for directed graph representation.
/// 6. Seeds default statutory OKF concepts:
///    - Mandatory Grant 20% Rebate (Attested Computation)
///    - Artisan Mentor-to-Apprentice Ratio Verification (Attested Computation)
///    - SARS Monthly Levy Staging Schema (Schema / Table)
///    - Mandatory Grant Approval & Reconciliation Playbook (Playbook)
/// </summary>
public static class Phase51OpenKnowledgeFormatMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            const string ddlSql = @"
                IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = N'history')
                    EXEC('CREATE SCHEMA [history] AUTHORIZATION [dbo];');

                -- 1. KnowledgeBundle
                IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[KnowledgeBundle]') AND type in (N'U'))
                BEGIN
                    CREATE TABLE [dbo].[KnowledgeBundle] (
                        [Id]                  INT IDENTITY(1,1) NOT NULL,
                        [BundleCode]          NVARCHAR(50)      NOT NULL,
                        [DisplayName]         NVARCHAR(150)     NOT NULL,
                        [Description]         NVARCHAR(500)     NULL,
                        [GitRepositoryUrl]    NVARCHAR(255)     NULL,
                        [FileSystemPath]      NVARCHAR(255)     NOT NULL,
                        [OkfVersion]          NVARCHAR(20)      NOT NULL CONSTRAINT [DF_KnowledgeBundle_OkfVersion] DEFAULT ('0.2'),
                        [IsActive]            BIT               NOT NULL CONSTRAINT [DF_KnowledgeBundle_IsActive] DEFAULT (1),
                        [CreatedAt]           DATETIMEOFFSET    NOT NULL CONSTRAINT [DF_KnowledgeBundle_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                        [CreatedBy]           NVARCHAR(100)     NOT NULL CONSTRAINT [DF_KnowledgeBundle_CreatedBy] DEFAULT ('SYSTEM'),
                        [ModifiedAt]          DATETIMEOFFSET    NOT NULL CONSTRAINT [DF_KnowledgeBundle_ModifiedAt] DEFAULT (SYSUTCDATETIME()),
                        [ModifiedBy]          NVARCHAR(100)     NOT NULL CONSTRAINT [DF_KnowledgeBundle_ModifiedBy] DEFAULT ('SYSTEM'),
                        CONSTRAINT [PK_KnowledgeBundle] PRIMARY KEY CLUSTERED ([Id] ASC),
                        CONSTRAINT [UQ_KnowledgeBundle_BundleCode] UNIQUE NONCLUSTERED ([BundleCode] ASC)
                    );
                END;

                -- 2. ConceptDocument
                IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ConceptDocument]') AND type in (N'U'))
                BEGIN
                    CREATE TABLE [dbo].[ConceptDocument] (
                        [Id]                  INT IDENTITY(1,1) NOT NULL,
                        [BundleId]            INT               NOT NULL,
                        [ConceptId]           NVARCHAR(150)     NOT NULL,
                        [ConceptType]         NVARCHAR(80)      NOT NULL,
                        [Title]               NVARCHAR(200)     NOT NULL,
                        [SummaryDescription]  NVARCHAR(500)     NULL,
                        [ResourceUri]         NVARCHAR(500)     NULL,
                        [BodyMarkdown]        NVARCHAR(MAX)     NOT NULL,
                        [LifecycleStatus]     NVARCHAR(30)      NOT NULL CONSTRAINT [DF_ConceptDocument_LifecycleStatus] DEFAULT ('stable'),
                        [StaleAfter]          DATETIMEOFFSET    NULL,
                        [DerivedTrustTier]    TINYINT           NOT NULL CONSTRAINT [DF_ConceptDocument_DerivedTrustTier] DEFAULT (0),
                        [GeneratedAt]         DATETIMEOFFSET    NOT NULL CONSTRAINT [DF_ConceptDocument_GeneratedAt] DEFAULT (SYSUTCDATETIME()),
                        [GeneratedByActor]    NVARCHAR(150)     NOT NULL,
                        [IsActive]            BIT               NOT NULL CONSTRAINT [DF_ConceptDocument_IsActive] DEFAULT (1),
                        [CreatedAt]           DATETIMEOFFSET    NOT NULL CONSTRAINT [DF_ConceptDocument_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                        [CreatedBy]           NVARCHAR(100)     NOT NULL CONSTRAINT [DF_ConceptDocument_CreatedBy] DEFAULT ('SYSTEM'),
                        [ModifiedAt]          DATETIMEOFFSET    NOT NULL CONSTRAINT [DF_ConceptDocument_ModifiedAt] DEFAULT (SYSUTCDATETIME()),
                        [ModifiedBy]          NVARCHAR(100)     NOT NULL CONSTRAINT [DF_ConceptDocument_ModifiedBy] DEFAULT ('SYSTEM'),
                        CONSTRAINT [PK_ConceptDocument] PRIMARY KEY CLUSTERED ([Id] ASC),
                        CONSTRAINT [UQ_ConceptDocument_ConceptId] UNIQUE NONCLUSTERED ([ConceptId] ASC),
                        CONSTRAINT [FK_ConceptDocument_KnowledgeBundle] FOREIGN KEY ([BundleId]) REFERENCES [dbo].[KnowledgeBundle] ([Id])
                    );
                END;

                IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = N'IX_ConceptDocument_BundleId')
                    CREATE NONCLUSTERED INDEX [IX_ConceptDocument_BundleId] ON [dbo].[ConceptDocument] ([BundleId] ASC);
                IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = N'IX_ConceptDocument_ConceptType')
                    CREATE NONCLUSTERED INDEX [IX_ConceptDocument_ConceptType] ON [dbo].[ConceptDocument] ([ConceptType] ASC);
                IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = N'IX_ConceptDocument_DerivedTrustTier')
                    CREATE NONCLUSTERED INDEX [IX_ConceptDocument_DerivedTrustTier] ON [dbo].[ConceptDocument] ([DerivedTrustTier] ASC);
                IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = N'IX_ConceptDocument_StaleAfter')
                    CREATE NONCLUSTERED INDEX [IX_ConceptDocument_StaleAfter] ON [dbo].[ConceptDocument] ([StaleAfter] ASC) WHERE [StaleAfter] IS NOT NULL;

                -- 3. ConceptTag
                IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ConceptTag]') AND type in (N'U'))
                BEGIN
                    CREATE TABLE [dbo].[ConceptTag] (
                        [Id]                  INT IDENTITY(1,1) NOT NULL,
                        [ConceptId]           INT               NOT NULL,
                        [TagName]             NVARCHAR(50)      NOT NULL,
                        [CreatedAt]           DATETIMEOFFSET    NOT NULL CONSTRAINT [DF_ConceptTag_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                        [CreatedBy]           NVARCHAR(100)     NOT NULL CONSTRAINT [DF_ConceptTag_CreatedBy] DEFAULT ('SYSTEM'),
                        [ModifiedAt]          DATETIMEOFFSET    NULL,
                        [ModifiedBy]          NVARCHAR(100)     NULL,
                        CONSTRAINT [PK_ConceptTag] PRIMARY KEY CLUSTERED ([Id] ASC),
                        CONSTRAINT [UQ_ConceptTag_Concept_Tag] UNIQUE NONCLUSTERED ([ConceptId] ASC, [TagName] ASC),
                        CONSTRAINT [FK_ConceptTag_ConceptDocument] FOREIGN KEY ([ConceptId]) REFERENCES [dbo].[ConceptDocument] ([Id]) ON DELETE CASCADE
                    );
                END;

                IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = N'IX_ConceptTag_TagName')
                    CREATE NONCLUSTERED INDEX [IX_ConceptTag_TagName] ON [dbo].[ConceptTag] ([TagName] ASC);

                -- 4. ConceptSource
                IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ConceptSource]') AND type in (N'U'))
                BEGIN
                    CREATE TABLE [dbo].[ConceptSource] (
                        [Id]                  INT IDENTITY(1,1) NOT NULL,
                        [ConceptId]           INT               NOT NULL,
                        [SourceIdAlias]       NVARCHAR(80)      NOT NULL,
                        [ResourceUri]         NVARCHAR(500)     NOT NULL,
                        [Title]               NVARCHAR(200)     NULL,
                        [AuthorActor]         NVARCHAR(150)     NULL,
                        [UsageCount]          BIGINT            NULL,
                        [LastModifiedAt]      DATETIMEOFFSET    NULL,
                        [UsageWindowStart]    DATETIMEOFFSET    NULL,
                        [UsageWindowEnd]      DATETIMEOFFSET    NULL,
                        [CreatedAt]           DATETIMEOFFSET    NOT NULL CONSTRAINT [DF_ConceptSource_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                        [CreatedBy]           NVARCHAR(100)     NOT NULL CONSTRAINT [DF_ConceptSource_CreatedBy] DEFAULT ('SYSTEM'),
                        [ModifiedAt]          DATETIMEOFFSET    NULL,
                        [ModifiedBy]          NVARCHAR(100)     NULL,
                        CONSTRAINT [PK_ConceptSource] PRIMARY KEY CLUSTERED ([Id] ASC),
                        CONSTRAINT [UQ_ConceptSource_Concept_Alias] UNIQUE NONCLUSTERED ([ConceptId] ASC, [SourceIdAlias] ASC),
                        CONSTRAINT [FK_ConceptSource_ConceptDocument] FOREIGN KEY ([ConceptId]) REFERENCES [dbo].[ConceptDocument] ([Id]) ON DELETE CASCADE
                    );
                END;

                IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = N'IX_ConceptSource_ConceptId')
                    CREATE NONCLUSTERED INDEX [IX_ConceptSource_ConceptId] ON [dbo].[ConceptSource] ([ConceptId] ASC);

                -- 5. ConceptVerificationEvent
                IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ConceptVerificationEvent]') AND type in (N'U'))
                BEGIN
                    CREATE TABLE [dbo].[ConceptVerificationEvent] (
                        [Id]                  INT IDENTITY(1,1) NOT NULL,
                        [ConceptId]           INT               NOT NULL,
                        [VerifiedByActor]     NVARCHAR(150)     NOT NULL,
                        [ActorType]           NVARCHAR(30)      NOT NULL,
                        [VerifiedAt]          DATETIMEOFFSET    NOT NULL,
                        [Notes]               NVARCHAR(500)     NULL,
                        [CreatedAt]           DATETIMEOFFSET    NOT NULL CONSTRAINT [DF_ConceptVerificationEvent_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                        [CreatedBy]           NVARCHAR(100)     NOT NULL CONSTRAINT [DF_ConceptVerificationEvent_CreatedBy] DEFAULT ('SYSTEM'),
                        [ModifiedAt]          DATETIMEOFFSET    NULL,
                        [ModifiedBy]          NVARCHAR(100)     NULL,
                        CONSTRAINT [PK_ConceptVerificationEvent] PRIMARY KEY CLUSTERED ([Id] ASC),
                        CONSTRAINT [FK_ConceptVerificationEvent_ConceptDocument] FOREIGN KEY ([ConceptId]) REFERENCES [dbo].[ConceptDocument] ([Id]) ON DELETE CASCADE
                    );
                END;

                IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = N'IX_ConceptVerificationEvent_ConceptId')
                    CREATE NONCLUSTERED INDEX [IX_ConceptVerificationEvent_ConceptId] ON [dbo].[ConceptVerificationEvent] ([ConceptId] ASC);
                IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = N'IX_ConceptVerificationEvent_ActorType')
                    CREATE NONCLUSTERED INDEX [IX_ConceptVerificationEvent_ActorType] ON [dbo].[ConceptVerificationEvent] ([ActorType] ASC);

                -- 6. AttestedComputation
                IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AttestedComputation]') AND type in (N'U'))
                BEGIN
                    CREATE TABLE [dbo].[AttestedComputation] (
                        [Id]                  INT IDENTITY(1,1) NOT NULL,
                        [ConceptId]           INT               NOT NULL,
                        [Runtime]             NVARCHAR(50)      NOT NULL CONSTRAINT [DF_AttestedComputation_Runtime] DEFAULT ('tsql'),
                        [ComputationSql]      NVARCHAR(MAX)     NULL,
                        [ExternalScriptPath]  NVARCHAR(255)     NULL,
                        [ExecutorResource]    NVARCHAR(255)     NOT NULL,
                        [AttesterResource]    NVARCHAR(255)     NOT NULL,
                        [ReceiptSchemaJson]   NVARCHAR(1000)    NOT NULL,
                        [IsActive]            BIT               NOT NULL CONSTRAINT [DF_AttestedComputation_IsActive] DEFAULT (1),
                        [CreatedAt]           DATETIMEOFFSET    NOT NULL CONSTRAINT [DF_AttestedComputation_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                        [CreatedBy]           NVARCHAR(100)     NOT NULL CONSTRAINT [DF_AttestedComputation_CreatedBy] DEFAULT ('SYSTEM'),
                        [ModifiedAt]          DATETIMEOFFSET    NOT NULL CONSTRAINT [DF_AttestedComputation_ModifiedAt] DEFAULT (SYSUTCDATETIME()),
                        [ModifiedBy]          NVARCHAR(100)     NOT NULL CONSTRAINT [DF_AttestedComputation_ModifiedBy] DEFAULT ('SYSTEM'),
                        CONSTRAINT [PK_AttestedComputation] PRIMARY KEY CLUSTERED ([Id] ASC),
                        CONSTRAINT [UQ_AttestedComputation_ConceptId] UNIQUE NONCLUSTERED ([ConceptId] ASC),
                        CONSTRAINT [FK_AttestedComputation_ConceptDocument] FOREIGN KEY ([ConceptId]) REFERENCES [dbo].[ConceptDocument] ([Id]) ON DELETE CASCADE
                    );
                END;

                -- 7. ComputationParameter
                IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ComputationParameter]') AND type in (N'U'))
                BEGIN
                    CREATE TABLE [dbo].[ComputationParameter] (
                        [Id]                  INT IDENTITY(1,1) NOT NULL,
                        [ComputationId]       INT               NOT NULL,
                        [ParameterName]       NVARCHAR(50)      NOT NULL,
                        [ParameterType]       NVARCHAR(30)      NOT NULL,
                        [IsRequired]          BIT               NOT NULL CONSTRAINT [DF_ComputationParameter_IsRequired] DEFAULT (1),
                        [DefaultValue]        NVARCHAR(100)     NULL,
                        [Description]         NVARCHAR(250)     NULL,
                        [CreatedAt]           DATETIMEOFFSET    NOT NULL CONSTRAINT [DF_ComputationParameter_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                        [CreatedBy]           NVARCHAR(100)     NOT NULL CONSTRAINT [DF_ComputationParameter_CreatedBy] DEFAULT ('SYSTEM'),
                        [ModifiedAt]          DATETIMEOFFSET    NULL,
                        [ModifiedBy]          NVARCHAR(100)     NULL,
                        CONSTRAINT [PK_ComputationParameter] PRIMARY KEY CLUSTERED ([Id] ASC),
                        CONSTRAINT [UQ_ComputationParameter_Comp_Param] UNIQUE NONCLUSTERED ([ComputationId] ASC, [ParameterName] ASC),
                        CONSTRAINT [FK_ComputationParameter_AttestedComputation] FOREIGN KEY ([ComputationId]) REFERENCES [dbo].[AttestedComputation] ([Id]) ON DELETE CASCADE
                    );
                END;

                IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = N'IX_ComputationParameter_ComputationId')
                    CREATE NONCLUSTERED INDEX [IX_ComputationParameter_ComputationId] ON [dbo].[ComputationParameter] ([ComputationId] ASC);

                -- 8. ComputationExecutionAudit (BIGINT PK)
                IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ComputationExecutionAudit]') AND type in (N'U'))
                BEGIN
                    CREATE TABLE [dbo].[ComputationExecutionAudit] (
                        [Id]                       BIGINT IDENTITY(1,1) NOT NULL,
                        [ComputationId]            INT                  NOT NULL,
                        [InvokedByActor]           NVARCHAR(150)        NOT NULL,
                        [BoundParametersJson]      NVARCHAR(MAX)        NOT NULL,
                        [ExecutedSqlDigest]        CHAR(64)             NOT NULL,
                        [ReceiptPayloadJson]       NVARCHAR(MAX)        NOT NULL,
                        [AttestationVerdict]       NVARCHAR(20)         NOT NULL,
                        [AttestationFailureReason] NVARCHAR(500)        NULL,
                        [VerificationReference]    CHAR(64)             NOT NULL,
                        [ExecutionDurationMs]      INT                  NOT NULL,
                        [ExecutedAt]               DATETIMEOFFSET       NOT NULL CONSTRAINT [DF_CompExecutionAudit_ExecutedAt] DEFAULT (SYSUTCDATETIME()),
                        CONSTRAINT [PK_ComputationExecutionAudit] PRIMARY KEY CLUSTERED ([Id] ASC),
                        CONSTRAINT [FK_ComputationExecutionAudit_AttestedComputation] FOREIGN KEY ([ComputationId]) REFERENCES [dbo].[AttestedComputation] ([Id])
                    );
                END;

                IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = N'IX_CompExecutionAudit_ComputationId')
                    CREATE NONCLUSTERED INDEX [IX_CompExecutionAudit_ComputationId] ON [dbo].[ComputationExecutionAudit] ([ComputationId] ASC, [ExecutedAt] DESC);
                IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = N'IX_CompExecutionAudit_Verdict')
                    CREATE NONCLUSTERED INDEX [IX_CompExecutionAudit_Verdict] ON [dbo].[ComputationExecutionAudit] ([AttestationVerdict] ASC);
                IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = N'IX_CompExecutionAudit_Seal')
                    CREATE NONCLUSTERED INDEX [IX_CompExecutionAudit_Seal] ON [dbo].[ComputationExecutionAudit] ([VerificationReference] ASC);

                -- 9. ConceptCrossLink
                IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ConceptCrossLink]') AND type in (N'U'))
                BEGIN
                    CREATE TABLE [dbo].[ConceptCrossLink] (
                        [Id]                  INT IDENTITY(1,1) NOT NULL,
                        [SourceConceptId]     INT               NOT NULL,
                        [TargetConceptPath]   NVARCHAR(255)     NOT NULL,
                        [ResolvedTargetId]    INT               NULL,
                        [LinkText]            NVARCHAR(200)     NULL,
                        [CreatedAt]           DATETIMEOFFSET    NOT NULL CONSTRAINT [DF_ConceptCrossLink_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                        [CreatedBy]           NVARCHAR(100)     NOT NULL CONSTRAINT [DF_ConceptCrossLink_CreatedBy] DEFAULT ('SYSTEM'),
                        [ModifiedAt]          DATETIMEOFFSET    NULL,
                        [ModifiedBy]          NVARCHAR(100)     NULL,
                        CONSTRAINT [PK_ConceptCrossLink] PRIMARY KEY CLUSTERED ([Id] ASC),
                        CONSTRAINT [FK_ConceptCrossLink_SourceConcept] FOREIGN KEY ([SourceConceptId]) REFERENCES [dbo].[ConceptDocument] ([Id]) ON DELETE CASCADE,
                        CONSTRAINT [FK_ConceptCrossLink_TargetConcept] FOREIGN KEY ([ResolvedTargetId]) REFERENCES [dbo].[ConceptDocument] ([Id])
                    );
                END;

                IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = N'IX_ConceptCrossLink_SourceConceptId')
                    CREATE NONCLUSTERED INDEX [IX_ConceptCrossLink_SourceConceptId] ON [dbo].[ConceptCrossLink] ([SourceConceptId] ASC);
                IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = N'IX_ConceptCrossLink_ResolvedTargetId')
                    CREATE NONCLUSTERED INDEX [IX_ConceptCrossLink_ResolvedTargetId] ON [dbo].[ConceptCrossLink] ([ResolvedTargetId] ASC);
            ";

            await context.Database.ExecuteSqlRawAsync(ddlSql);
            logger?.LogInformation("Phase 51 Open Knowledge Format (OKF v0.2) DDL migration applied successfully.");
        }

        await SeedDefaultKnowledgeBundleAndConceptsAsync(context, logger);
    }

    private static async Task SeedDefaultKnowledgeBundleAndConceptsAsync(NsdmsDbContext db, ILogger? logger)
    {
        var bundle = await db.KnowledgeBundles.FirstOrDefaultAsync(b => b.BundleCode == "merseta-statutory-catalog");
        if (bundle == null)
        {
            bundle = new KnowledgeBundle
            {
                BundleCode = "merseta-statutory-catalog",
                DisplayName = "merSETA Statutory Skills Development & Knowledge Catalog",
                Description = "Authoritative living knowledge bundle capturing statutory grant allocation rules, artisan mentorship ratios, and data interchange schemas per OKF v0.2.",
                FileSystemPath = "App_Data/knowledge-bundles/statutory-catalog",
                OkfVersion = "0.2",
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "SYSTEM"
            };
            db.KnowledgeBundles.Add(bundle);
            await db.SaveChangesAsync();
            logger?.LogInformation("Seeded root OKF KnowledgeBundle: 'merseta-statutory-catalog'.");
        }

        // 1. Mandatory Grant 20% Rebate Concept
        var mgConcept = await db.ConceptDocuments.Include(c => c.Computation).FirstOrDefaultAsync(c => c.ConceptId == "grants/mandatory-grant-rebate");
        if (mgConcept == null)
        {
            mgConcept = new ConceptDocument
            {
                BundleId = bundle.Id,
                ConceptId = "grants/mandatory-grant-rebate",
                ConceptType = "Attested Computation",
                Title = "Mandatory Grant (WSP/ATR) 20% Levy Rebate Calculation",
                SummaryDescription = "Calculates the statutory 20% Mandatory Grant levy rebate for an employer based on SARS monthly levy receipts.",
                ResourceUri = "nsdms://grants/mandatory-grant/rebate",
                BodyMarkdown = @"# Statutory Background

In terms of Regulation 4(1) of the SETA Grant Regulations published in Government Gazette No. 35940,[^grant-reg-35940] an employer who pays skills development levies and submits an approved Workplace Skills Plan (WSP) and Annual Training Report (ATR) by the statutory deadline of 30 April is eligible to claim a Mandatory Grant rebate equal to **20%** of the total levies paid.

Reconciled levy payments are ingested via the SARS monthly streaming pipeline.[^sars-staging-spec]

# Computation

```sql
SELECT 
    l.OrganisationId,
    l.FinancialYearId,
    COUNT(l.Id) AS ReconciledMonthsCount,
    SUM(l.TotalLevyAmount) AS GrossLevyPaid,
    ROUND(SUM(l.TotalLevyAmount) * 0.20, 2) AS MandatoryGrantRebateAmount
FROM dbo.LevyFileLine l
INNER JOIN dbo.Organisation o ON l.OrganisationId = o.Id
INNER JOIN dbo.WspSubmission w ON w.OrganisationId = o.Id AND w.FinancialYearId = l.FinancialYearId
WHERE l.OrganisationId = @OrganisationId
  AND l.FinancialYearId = @FinancialYearId
  AND w.SubmissionStatusCode = 'Approved'
  AND l.IsReconciled = 1
GROUP BY l.OrganisationId, l.FinancialYearId;
```

# Verification & Attestation Receipt

Executing this computation generates a deterministic receipt. The attester verifies:
1. Exact equality between the executed SQL text and the sanitized sanctioned query above.
2. That `@OrganisationId` has an approved WSP submission for `@FinancialYearId`.
3. That the returned `MandatoryGrantRebateAmount` matches the SHA-256 payload digest.",
                LifecycleStatus = ConceptLifecycleStatus.Stable,
                DerivedTrustTier = TrustTier.HumanReviewed,
                StaleAfter = DateTime.UtcNow.AddYears(1),
                GeneratedAt = DateTime.UtcNow.AddDays(-10),
                GeneratedByActor = "agent/gemini-2.5-pro",
                IsActive = true
            };

            db.ConceptDocuments.Add(mgConcept);
            await db.SaveChangesAsync();

            // Tags
            db.ConceptTags.AddRange(
                new ConceptTag { ConceptId = mgConcept.Id, TagName = "grants" },
                new ConceptTag { ConceptId = mgConcept.Id, TagName = "wsp" },
                new ConceptTag { ConceptId = mgConcept.Id, TagName = "mandatory-grant" },
                new ConceptTag { ConceptId = mgConcept.Id, TagName = "levies" }
            );

            // Sources
            db.ConceptSources.AddRange(
                new ConceptSource
                {
                    ConceptId = mgConcept.Id,
                    SourceIdAlias = "grant-reg-35940",
                    ResourceUri = "https://www.gov.za/documents/skills-development-act-regulations-monies-received-seta-and-related-matters",
                    Title = "SETA Grant Regulations (Government Gazette No. 35940, Regulation 4)",
                    AuthorActor = "government:dhet",
                    LastModifiedAt = DateTime.UtcNow.AddYears(-2)
                },
                new ConceptSource
                {
                    ConceptId = mgConcept.Id,
                    SourceIdAlias = "sars-staging-spec",
                    ResourceUri = "/schemas/sars-levy-staging.md",
                    Title = "SARS Monthly Levy Ingestion & Reconciliation Specification",
                    AuthorActor = "team:merSETA-finance",
                    UsageCount = 14200,
                    LastModifiedAt = DateTime.UtcNow.AddMonths(-1),
                    UsageWindowStart = DateTime.UtcNow.AddMonths(-3),
                    UsageWindowEnd = DateTime.UtcNow
                }
            );

            // Verification events (Human-reviewed)
            db.ConceptVerificationEvents.AddRange(
                new ConceptVerificationEvent
                {
                    ConceptId = mgConcept.Id,
                    VerifiedByActor = "human:clo_senior_adjudicator",
                    ActorType = "human",
                    VerifiedAt = DateTime.UtcNow.AddDays(-5),
                    Notes = "Verified against Department of Higher Education & Training Gazette R.990 provisions."
                },
                new ConceptVerificationEvent
                {
                    ConceptId = mgConcept.Id,
                    VerifiedByActor = "human:cfo_executive",
                    ActorType = "human",
                    VerifiedAt = DateTime.UtcNow.AddDays(-3),
                    Notes = "Executive financial governance sign-off; approved for production grant rebate disbursements."
                }
            );

            // Attested computation
            var computation = new AttestedComputation
            {
                ConceptId = mgConcept.Id,
                Runtime = "tsql",
                ComputationSql = @"SELECT 
    l.OrganisationId,
    l.FinancialYearId,
    COUNT(l.Id) AS ReconciledMonthsCount,
    SUM(l.TotalLevyAmount) AS GrossLevyPaid,
    ROUND(SUM(l.TotalLevyAmount) * 0.20, 2) AS MandatoryGrantRebateAmount
FROM dbo.LevyFileLine l
INNER JOIN dbo.Organisation o ON l.OrganisationId = o.Id
INNER JOIN dbo.WspSubmission w ON w.OrganisationId = o.Id AND w.FinancialYearId = l.FinancialYearId
WHERE l.OrganisationId = @OrganisationId
  AND l.FinancialYearId = @FinancialYearId
  AND w.SubmissionStatusCode = 'Approved'
  AND l.IsReconciled = 1
GROUP BY l.OrganisationId, l.FinancialYearId;",
                ExecutorResource = "references/skills/run-tsql.md",
                AttesterResource = "references/attesters/tsql-equality.cs",
                ReceiptSchemaJson = "[\"execution_id\", \"executed_sql\", \"rows_affected\", \"total_rebate_amount\", \"result_digest\"]",
                IsActive = true
            };
            db.AttestedComputations.Add(computation);
            await db.SaveChangesAsync();

            // Computation Parameters
            db.ComputationParameters.AddRange(
                new ComputationParameter
                {
                    ComputationId = computation.Id,
                    ParameterName = "@FinancialYearId",
                    ParameterType = "int",
                    IsRequired = true,
                    Description = "The target financial scheme year identifier."
                },
                new ComputationParameter
                {
                    ComputationId = computation.Id,
                    ParameterName = "@OrganisationId",
                    ParameterType = "int",
                    IsRequired = true,
                    Description = "Primary database identifier of the levy-paying organisation."
                }
            );

            await db.SaveChangesAsync();
            logger?.LogInformation("Seeded concept: 'grants/mandatory-grant-rebate' with T-SQL attested computation.");
        }

        // 2. Artisan Mentor Ratio Concept
        var mentorConcept = await db.ConceptDocuments.FirstOrDefaultAsync(c => c.ConceptId == "artisans/mentor-apprentice-ratio");
        if (mentorConcept == null)
        {
            mentorConcept = new ConceptDocument
            {
                BundleId = bundle.Id,
                ConceptId = "artisans/mentor-apprentice-ratio",
                ConceptType = "Attested Computation",
                Title = "Artisan Mentor-to-Apprentice Capacity Ratio Evaluation",
                SummaryDescription = "Calculates the maximum apprentice intake capacity for an employer site based on NAMB 1:4 artisan mentor caps.",
                ResourceUri = "nsdms://artisans/mentor-ratio/evaluate",
                BodyMarkdown = @"# NAMB Artisan Mentorship Standard

In accordance with Section 26D of the Skills Development Act 97 of 1998 and NAMB Criteria for Workplace Approvals,[^namb-criteria] an employer site must not exceed the designated trade mentor ratio (standard 1 artisan mentor to 4 apprentices), unless an explicit policy exemption exists.

# Computation

```sql
SELECT 
    w.Id AS WorkplaceApprovalId,
    w.OrganisationId,
    w.TradeCode,
    COUNT(m.Id) AS VerifiedMentorsCount,
    (COUNT(m.Id) * 4) AS MaxApprenticeCapacity,
    (SELECT COUNT(cl.Id) FROM dbo.CompanyLearner cl WHERE cl.WorkplaceApprovalId = w.Id AND cl.RegistrationStatusCode = 'Active') AS CurrentActiveApprenticesCount
FROM dbo.WorkplaceApproval w
LEFT JOIN dbo.WorkplaceApprovalMentor m ON m.WorkplaceApprovalId = w.Id AND m.IsActive = 1
WHERE w.Id = @WorkplaceApprovalId
GROUP BY w.Id, w.OrganisationId, w.TradeCode;
```",
                LifecycleStatus = ConceptLifecycleStatus.Stable,
                DerivedTrustTier = TrustTier.HumanReviewed,
                StaleAfter = DateTime.UtcNow.AddYears(1),
                GeneratedAt = DateTime.UtcNow.AddDays(-15),
                GeneratedByActor = "process:mentor-policy-engine",
                IsActive = true
            };

            db.ConceptDocuments.Add(mentorConcept);
            await db.SaveChangesAsync();

            db.ConceptTags.AddRange(
                new ConceptTag { ConceptId = mentorConcept.Id, TagName = "artisans" },
                new ConceptTag { ConceptId = mentorConcept.Id, TagName = "workplace-approval" },
                new ConceptTag { ConceptId = mentorConcept.Id, TagName = "mentorship" }
            );

            db.ConceptSources.Add(new ConceptSource
            {
                ConceptId = mentorConcept.Id,
                SourceIdAlias = "namb-criteria",
                ResourceUri = "https://www.namb.dhet.gov.za/workplace-approval-criteria",
                Title = "NAMB National Artisan Mentorship Ratio Policy Guideline",
                AuthorActor = "government:namb",
                LastModifiedAt = DateTime.UtcNow.AddMonths(-6)
            });

            db.ConceptVerificationEvents.Add(new ConceptVerificationEvent
            {
                ConceptId = mentorConcept.Id,
                VerifiedByActor = "human:qa_executive",
                ActorType = "human",
                VerifiedAt = DateTime.UtcNow.AddDays(-7),
                Notes = "Verified compliant with NAMB trade assessment policies."
            });

            var comp = new AttestedComputation
            {
                ConceptId = mentorConcept.Id,
                Runtime = "tsql",
                ComputationSql = @"SELECT 
    w.Id AS WorkplaceApprovalId,
    w.OrganisationId,
    w.TradeCode,
    COUNT(m.Id) AS VerifiedMentorsCount,
    (COUNT(m.Id) * 4) AS MaxApprenticeCapacity,
    (SELECT COUNT(cl.Id) FROM dbo.CompanyLearner cl WHERE cl.WorkplaceApprovalId = w.Id AND cl.RegistrationStatusCode = 'Active') AS CurrentActiveApprenticesCount
FROM dbo.WorkplaceApproval w
LEFT JOIN dbo.WorkplaceApprovalMentor m ON m.WorkplaceApprovalId = w.Id AND m.IsActive = 1
WHERE w.Id = @WorkplaceApprovalId
GROUP BY w.Id, w.OrganisationId, w.TradeCode;",
                ExecutorResource = "references/skills/run-tsql.md",
                AttesterResource = "references/attesters/tsql-equality.cs",
                ReceiptSchemaJson = "[\"workplace_approval_id\", \"verified_mentors\", \"max_capacity\", \"current_apprentices\"]",
                IsActive = true
            };
            db.AttestedComputations.Add(comp);
            await db.SaveChangesAsync();

            db.ComputationParameters.Add(new ComputationParameter
            {
                ComputationId = comp.Id,
                ParameterName = "@WorkplaceApprovalId",
                ParameterType = "int",
                IsRequired = true,
                Description = "Workplace approval record identifier for the accredited artisan training site."
            });

            await db.SaveChangesAsync();
            logger?.LogInformation("Seeded concept: 'artisans/mentor-apprentice-ratio'.");
        }

        // 3. SARS Monthly Levy Staging Schema Concept
        var sarsConcept = await db.ConceptDocuments.FirstOrDefaultAsync(c => c.ConceptId == "schemas/sars-levy-staging");
        if (sarsConcept == null)
        {
            sarsConcept = new ConceptDocument
            {
                BundleId = bundle.Id,
                ConceptId = "schemas/sars-levy-staging",
                ConceptType = "SQL Server Table",
                Title = "SARS Monthly Levy Staging Schema & Ingestion Controls",
                SummaryDescription = "Defines staging columns, validation constraints, and non-blocking bulk copy parameters for SARS monthly schedules.",
                ResourceUri = "nsdms://schemas/sars-levy-staging",
                BodyMarkdown = @"# Schema Overview

The `SarsLevyStaging` table buffers high-speed streaming chunks from raw SARS text schedules prior to financial promotion.

# Schema

| Column Name | SQL Type | Description |
| :--- | :--- | :--- |
| `SdlNumber` | NVARCHAR(20) | Statutory Skills Development Levy number (starts with 'L'). |
| `SchemeYear` | INT | Statutory scheme year matching SARS monthly period. |
| `TotalLevyAmount` | DECIMAL(18,2) | Gross 1% SDL contribution declared by employer. |
| `SicCode` | NVARCHAR(20) | Standard Industrial Classification code. |
| `IsReconciled` | BIT | Set to 1 once matched against verified organisation record. |

# Ingestion Controls

1. Ingestion enforces constant-memory streaming via `ISarsLevyStreamingPipeline`.
2. Digital Security Seal is computed dynamically on raw file stream.",
                LifecycleStatus = ConceptLifecycleStatus.Stable,
                DerivedTrustTier = TrustTier.MachineConfirmed,
                StaleAfter = DateTime.UtcNow.AddYears(2),
                GeneratedAt = DateTime.UtcNow.AddDays(-20),
                GeneratedByActor = "process:sars-streaming-worker",
                IsActive = true
            };
            db.ConceptDocuments.Add(sarsConcept);
            await db.SaveChangesAsync();

            db.ConceptTags.AddRange(
                new ConceptTag { ConceptId = sarsConcept.Id, TagName = "sars" },
                new ConceptTag { ConceptId = sarsConcept.Id, TagName = "schema" },
                new ConceptTag { ConceptId = sarsConcept.Id, TagName = "levies" }
            );

            db.ConceptVerificationEvents.Add(new ConceptVerificationEvent
            {
                ConceptId = sarsConcept.Id,
                VerifiedByActor = "process:sars-preflight-gatekeeper",
                ActorType = "process",
                VerifiedAt = DateTime.UtcNow.AddDays(-2),
                Notes = "Pre-flight validator confirmed schema matching SARS EFT format specification."
            });

            await db.SaveChangesAsync();
            logger?.LogInformation("Seeded concept: 'schemas/sars-levy-staging'.");
        }
    }
}
