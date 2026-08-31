using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator and seeder for Universal Enterprise Document Templates & Cryptographic Verification Snapshots (Strategic Action Items).
/// </summary>
public static class Phase11UniversalDocumentVerificationMigrator
{
    public static async Task MigrateDocumentVerificationSchemaAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            const string ddlSql = @"
                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'DocumentTemplate')
                BEGIN
                    CREATE TABLE [dbo].[DocumentTemplate] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [TemplateCode] NVARCHAR(50) NOT NULL,
                        [TemplateTitle] NVARCHAR(200) NOT NULL,
                        [DocumentCategory] NVARCHAR(50) NOT NULL,
                        [DocumentTypeCode] NVARCHAR(50) NOT NULL,
                        [FinancialYear] INT NOT NULL,
                        [TargetEntityType] NVARCHAR(50) NOT NULL DEFAULT 'All',
                        [VersionNumber] NVARCHAR(20) NOT NULL DEFAULT '1.0.0',
                        [ApprovalStatus] NVARCHAR(30) NOT NULL DEFAULT 'Draft',
                        [EffectiveFrom] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [EffectiveTo] DATETIME2 NULL,
                        [IsActive] BIT NOT NULL DEFAULT 1,
                        [HeaderBannerUrl] NVARCHAR(500) NULL,
                        [FooterDisclaimerText] NVARCHAR(500) NULL,
                        [ApprovedBy] NVARCHAR(100) NULL,
                        [ApprovedAt] DATETIME2 NULL,
                        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL
                    );
                    CREATE UNIQUE INDEX [IX_DocumentTemplate_TemplateCode] ON [dbo].[DocumentTemplate] ([TemplateCode]);
                    CREATE INDEX [IX_DocumentTemplate_Category_Type_Year] ON [dbo].[DocumentTemplate] ([DocumentCategory], [DocumentTypeCode], [FinancialYear], [IsActive]);
                END;

                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'DocumentClause')
                BEGIN
                    CREATE TABLE [dbo].[DocumentClause] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [ClauseCode] NVARCHAR(50) NOT NULL,
                        [ClauseTitle] NVARCHAR(200) NOT NULL,
                        [Category] NVARCHAR(50) NOT NULL DEFAULT 'General',
                        [ClauseContent] NVARCHAR(MAX) NOT NULL,
                        [IsMandatory] BIT NOT NULL DEFAULT 1,
                        [IsActive] BIT NOT NULL DEFAULT 1,
                        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL
                    );
                    CREATE UNIQUE INDEX [IX_DocumentClause_ClauseCode] ON [dbo].[DocumentClause] ([ClauseCode]);
                    CREATE INDEX [IX_DocumentClause_Category] ON [dbo].[DocumentClause] ([Category]);
                END;

                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'DocumentTemplateSection')
                BEGIN
                    CREATE TABLE [dbo].[DocumentTemplateSection] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [DocumentTemplateId] INT NOT NULL,
                        [DocumentClauseId] INT NOT NULL,
                        [SectionNumber] NVARCHAR(30) NOT NULL,
                        [SectionTitle] NVARCHAR(200) NOT NULL,
                        [SequenceOrder] INT NOT NULL,
                        [IsMandatory] BIT NOT NULL DEFAULT 1,
                        [ConditionRuleJson] NVARCHAR(MAX) NULL,
                        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL,
                        CONSTRAINT [FK_DocumentTemplateSection_DocumentTemplate] FOREIGN KEY ([DocumentTemplateId]) REFERENCES [dbo].[DocumentTemplate] ([Id]) ON DELETE CASCADE,
                        CONSTRAINT [FK_DocumentTemplateSection_DocumentClause] FOREIGN KEY ([DocumentClauseId]) REFERENCES [dbo].[DocumentClause] ([Id])
                    );
                    CREATE INDEX [IX_DocumentTemplateSection_Template] ON [dbo].[DocumentTemplateSection] ([DocumentTemplateId]);
                    CREATE INDEX [IX_DocumentTemplateSection_Clause] ON [dbo].[DocumentTemplateSection] ([DocumentClauseId]);
                    CREATE INDEX [IX_DocumentTemplateSection_Sequence] ON [dbo].[DocumentTemplateSection] ([DocumentTemplateId], [SequenceOrder]);
                END;

                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'DocumentSnapshot')
                BEGIN
                    CREATE TABLE [dbo].[DocumentSnapshot] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [DocumentSnapshotNumber] NVARCHAR(50) NOT NULL,
                        [DocumentTypeCode] NVARCHAR(50) NOT NULL,
                        [DocumentTemplateId] INT NULL,
                        [TemplateVersionNumber] NVARCHAR(20) NOT NULL DEFAULT '1.0.0',
                        [RelatedEntityId] INT NOT NULL,
                        [RelatedEntityType] NVARCHAR(50) NOT NULL,
                        [RecipientName] NVARCHAR(200) NOT NULL,
                        [RecipientIdentifier] NVARCHAR(50) NOT NULL,
                        [RenderedContentHash] NVARCHAR(100) NOT NULL,
                        [RenderedContent] NVARCHAR(MAX) NOT NULL,
                        [VerificationQrBase64] NVARCHAR(MAX) NULL,
                        [VerificationUri] NVARCHAR(500) NOT NULL,
                        [PdfStorageUri] NVARCHAR(500) NULL,
                        [IssuedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [IssuedBy] NVARCHAR(100) NOT NULL DEFAULT 'System',
                        [SignatoryName] NVARCHAR(150) NULL,
                        [SignatoryTitle] NVARCHAR(150) NULL,
                        [SignatorySignedAt] DATETIME2 NULL,
                        [VerificationScanCount] INT NOT NULL DEFAULT 0,
                        [LastVerifiedAt] DATETIME2 NULL,
                        [IsRevoked] BIT NOT NULL DEFAULT 0,
                        [RevocationReason] NVARCHAR(500) NULL,
                        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL,
                        CONSTRAINT [FK_DocumentSnapshot_DocumentTemplate] FOREIGN KEY ([DocumentTemplateId]) REFERENCES [dbo].[DocumentTemplate] ([Id])
                    );
                    CREATE UNIQUE INDEX [IX_DocumentSnapshot_SnapshotNumber] ON [dbo].[DocumentSnapshot] ([DocumentSnapshotNumber]);
                    CREATE INDEX [IX_DocumentSnapshot_ContentHash] ON [dbo].[DocumentSnapshot] ([RenderedContentHash]);
                    CREATE INDEX [IX_DocumentSnapshot_RelatedEntity] ON [dbo].[DocumentSnapshot] ([RelatedEntityType], [RelatedEntityId]);
                    CREATE INDEX [IX_DocumentSnapshot_TypeCode] ON [dbo].[DocumentSnapshot] ([DocumentTypeCode]);
                    CREATE INDEX [IX_DocumentSnapshot_IssuedAt] ON [dbo].[DocumentSnapshot] ([IssuedAt]);
                    CREATE INDEX [IX_DocumentSnapshot_RecipientId] ON [dbo].[DocumentSnapshot] ([RecipientIdentifier]);
                END;
            ";

            await context.Database.ExecuteSqlRawAsync(ddlSql);
        }

        await SeedDefaultDocumentTemplatesAndClausesAsync(context, logger);
    }

    private static async Task SeedDefaultDocumentTemplatesAndClausesAsync(NsdmsDbContext db, ILogger? logger)
    {
        // 1. Seed Reusable Document Clauses
        var defaultClauses = new List<DocumentClause>
        {
            new()
            {
                ClauseCode = "CL-WSP-APPROVE-PREAMBLE",
                ClauseTitle = "WSP Approval Preamble",
                Category = "MandatoryGrant",
                ClauseContent = "The Manufacturing, Engineering and Related Services SETA (merSETA) hereby confirms that the Workplace Skills Plan (WSP) and Annual Training Report (ATR) for **{{RecipientName}}** (SDL Number: `{{RecipientIdentifier}}`) for the **{{FinancialYear}}/{{NextFinancialYear}}** mandatory grant scheme has been officially reviewed and **APPROVED**.",
                IsMandatory = true,
                IsActive = true
            },
            new()
            {
                ClauseCode = "CL-WSP-APPROVE-DISBURSEMENT",
                ClauseTitle = "Mandatory Grant Rebate Conditions",
                Category = "MandatoryGrant",
                ClauseContent = "In accordance with Sector Education and Training Authorities (SETA) Grant Regulations (Government Gazette No. 35940), your organisation qualifies for the statutory 20% mandatory grant rebate disbursement subject to up-to-date SARS levy payments and valid verified banking details.",
                IsMandatory = true,
                IsActive = true
            },
            new()
            {
                ClauseCode = "CL-WSP-REJECT-BODY",
                ClauseTitle = "WSP Non-Approval Findings",
                Category = "MandatoryGrant",
                ClauseContent = "Please be advised that following verification of the statutory submission for **{{RecipientName}}** (`{{RecipientIdentifier}}`), the WSP/ATR application for scheme year **{{FinancialYear}}** has **NOT BEEN APPROVED** due to non-compliance with the criteria set forth in the Skills Development Act.",
                IsMandatory = true,
                IsActive = true
            },
            new()
            {
                ClauseCode = "CL-WSP-REJECT-APPEAL",
                ClauseTitle = "Mandatory Grant Dispute & Appeal Mechanism",
                Category = "MandatoryGrant",
                ClauseContent = "An applicant aggrieved by this non-approval decision may lodge a formal written appeal with supporting evidence to the merSETA Chief Executive Officer within 14 (fourteen) calendar days from receipt of this notice.",
                IsMandatory = true,
                IsActive = true
            },
            new()
            {
                ClauseCode = "CL-TRADE-CERT-BODY",
                ClauseTitle = "National Artisan Trade Competency Declaration",
                Category = "TradeTest",
                ClauseContent = "This is to certify that **{{LearnerFullName}}** (National ID: `{{LearnerIdNumber}}`) has successfully undergone assessment and trade testing in accordance with Section 26D of the Skills Development Act and is hereby declared **COMPETENT AS A QUALIFIED ARTISAN** in the designated trade of **{{TradeTitle}}**.",
                IsMandatory = true,
                IsActive = true
            },
            new()
            {
                ClauseCode = "CL-ETQA-ACCRED-BODY",
                ClauseTitle = "SDP Accreditation Scope & Authority",
                Category = "EtqaAccreditation",
                ClauseContent = "This certificate confirms that **{{RecipientName}}** (Accreditation No: `{{AccreditationNumber}}`) is fully accredited as a Skills Development Provider (SDP) under the Education and Training Quality Assurance (ETQA) regulations of the South African Qualifications Authority (SAQA) and Quality Council for Trades and Occupations (QCTO).",
                IsMandatory = true,
                IsActive = true
            },
            new()
            {
                ClauseCode = "CL-REMITTANCE-BODY",
                ClauseTitle = "Rebate Payout Notification & Banking Remittance",
                Category = "FinanceAudit",
                ClauseContent = "merSETA has processed a Mandatory Grant rebate payment of **{{RebateAmount}}** to the nominated bank account of **{{RecipientName}}** (`{{RecipientIdentifier}}`) for the **{{FinancialYear}}** cycle. Please verify against your bank statement using the remittance reference `{{DocumentNumber}}`.",
                IsMandatory = true,
                IsActive = true
            },
            new()
            {
                ClauseCode = "CL-SARS-CLAWBACK-DEMAND",
                ClauseTitle = "SARS Levy Adjustment & Statutory Recovery Notice",
                Category = "FinanceAudit",
                ClauseContent = "Following the monthly reconciliation of SARS Scheme-12 and Scheme-19 levy files for scheme year **{{FinancialYear}}**, a negative variance has been identified resulting in a clawback liability of **{{ClawbackAmount}}**. This amount will be offset against future grant disbursements or must be refunded within 30 days.",
                IsMandatory = true,
                IsActive = true
            },
            new()
            {
                ClauseCode = "CL-POPIA-VERIFICATION",
                ClauseTitle = "POPIA Compliance & Public Verification Warranty",
                Category = "Compliance",
                ClauseContent = "This document is an authentic electronic record issued by the merSETA National Skills Development Management System. Point-in-time cryptographic authenticity can be verified anytime by scanning the embedded QR code or visiting `{{VerificationUri}}`.",
                IsMandatory = true,
                IsActive = true
            },
            new()
            {
                ClauseCode = "CL-SIGNATURES-SETA",
                ClauseTitle = "Authorised SETA Digital Signatory Block",
                Category = "Signatures",
                ClauseContent = "**Authorised Signatory:** {{SignatoryName}}\n**Designation:** {{SignatoryTitle}}\n**Date Issued:** {{IssuedDate}}\n\n*Signed under the delegated authority of the merSETA Accounting Authority.*",
                IsMandatory = true,
                IsActive = true
            }
        };

        foreach (var clause in defaultClauses)
        {
            if (!await db.DocumentClauses.AnyAsync(c => c.ClauseCode == clause.ClauseCode))
            {
                clause.CreatedAt = DateTime.UtcNow;
                clause.CreatedBy = "SystemSeed";
                db.DocumentClauses.Add(clause);
            }
        }
        await db.SaveChangesAsync();

        // 2. Seed Standard Templates
        var templates = new List<(DocumentTemplate Template, string[] ClauseCodes)>
        {
            (
                new DocumentTemplate
                {
                    TemplateCode = "WSP-APPROVAL-STD",
                    TemplateTitle = "Mandatory Grant (WSP/ATR) Annual Approval Letter",
                    DocumentCategory = "MandatoryGrant",
                    DocumentTypeCode = "WspApprovalLetter",
                    FinancialYear = 2026,
                    TargetEntityType = "Employer",
                    VersionNumber = "1.0.0",
                    ApprovalStatus = "Approved",
                    IsActive = true,
                    FooterDisclaimerText = "merSETA is a statutory body established in terms of the Skills Development Act No. 97 of 1998."
                },
                new[] { "CL-WSP-APPROVE-PREAMBLE", "CL-WSP-APPROVE-DISBURSEMENT", "CL-POPIA-VERIFICATION", "CL-SIGNATURES-SETA" }
            ),
            (
                new DocumentTemplate
                {
                    TemplateCode = "WSP-REJECT-STD",
                    TemplateTitle = "Mandatory Grant (WSP/ATR) Non-Approval Notice",
                    DocumentCategory = "MandatoryGrant",
                    DocumentTypeCode = "WspRejectionLetter",
                    FinancialYear = 2026,
                    TargetEntityType = "Employer",
                    VersionNumber = "1.0.0",
                    ApprovalStatus = "Approved",
                    IsActive = true,
                    FooterDisclaimerText = "merSETA is a statutory body established in terms of the Skills Development Act No. 97 of 1998."
                },
                new[] { "CL-WSP-REJECT-BODY", "CL-WSP-REJECT-APPEAL", "CL-POPIA-VERIFICATION", "CL-SIGNATURES-SETA" }
            ),
            (
                new DocumentTemplate
                {
                    TemplateCode = "TRADE-CERT-STD",
                    TemplateTitle = "National Artisan Trade Competency Certificate",
                    DocumentCategory = "TradeTest",
                    DocumentTypeCode = "TradeTestCertificate",
                    FinancialYear = 2026,
                    TargetEntityType = "Learner",
                    VersionNumber = "1.0.0",
                    ApprovalStatus = "Approved",
                    IsActive = true,
                    FooterDisclaimerText = "National Artisan Moderation Body (NAMB) and merSETA Accredited Trade Test Assessment Certificate."
                },
                new[] { "CL-TRADE-CERT-BODY", "CL-POPIA-VERIFICATION", "CL-SIGNATURES-SETA" }
            ),
            (
                new DocumentTemplate
                {
                    TemplateCode = "ETQA-ACCRED-STD",
                    TemplateTitle = "Skills Development Provider Accreditation Certificate",
                    DocumentCategory = "EtqaAccreditation",
                    DocumentTypeCode = "AccreditationCertificate",
                    FinancialYear = 2026,
                    TargetEntityType = "Provider",
                    VersionNumber = "1.0.0",
                    ApprovalStatus = "Approved",
                    IsActive = true,
                    FooterDisclaimerText = "Quality Council for Trades and Occupations (QCTO) delegated ETQA body certification."
                },
                new[] { "CL-ETQA-ACCRED-BODY", "CL-POPIA-VERIFICATION", "CL-SIGNATURES-SETA" }
            ),
            (
                new DocumentTemplate
                {
                    TemplateCode = "REMITTANCE-STD",
                    TemplateTitle = "Mandatory Grant Rebate Remittance Advice",
                    DocumentCategory = "FinanceAudit",
                    DocumentTypeCode = "RemittanceAdvice",
                    FinancialYear = 2026,
                    TargetEntityType = "Employer",
                    VersionNumber = "1.0.0",
                    ApprovalStatus = "Approved",
                    IsActive = true,
                    FooterDisclaimerText = "Official merSETA Finance Division Electronic Funds Transfer (EFT) Disbursement Advice."
                },
                new[] { "CL-REMITTANCE-BODY", "CL-POPIA-VERIFICATION", "CL-SIGNATURES-SETA" }
            ),
            (
                new DocumentTemplate
                {
                    TemplateCode = "SARS-CLAWBACK-STD",
                    TemplateTitle = "SARS Statutory Levy Reconciliation Demand Notice",
                    DocumentCategory = "FinanceAudit",
                    DocumentTypeCode = "SarsClawbackNotice",
                    FinancialYear = 2026,
                    TargetEntityType = "Employer",
                    VersionNumber = "1.0.0",
                    ApprovalStatus = "Approved",
                    IsActive = true,
                    FooterDisclaimerText = "Issued pursuant to the Skills Development Levies Act No. 9 of 1999."
                },
                new[] { "CL-SARS-CLAWBACK-DEMAND", "CL-POPIA-VERIFICATION", "CL-SIGNATURES-SETA" }
            )
        };

        foreach (var (template, clauseCodes) in templates)
        {
            if (!await db.DocumentTemplates.AnyAsync(t => t.TemplateCode == template.TemplateCode))
            {
                template.CreatedAt = DateTime.UtcNow;
                template.CreatedBy = "SystemSeed";
                template.ApprovedBy = "GovernanceExecutive";
                template.ApprovedAt = DateTime.UtcNow;
                db.DocumentTemplates.Add(template);
                await db.SaveChangesAsync();

                int seq = 10;
                foreach (var code in clauseCodes)
                {
                    var c = await db.DocumentClauses.FirstOrDefaultAsync(x => x.ClauseCode == code);
                    if (c != null)
                    {
                        db.DocumentTemplateSections.Add(new DocumentTemplateSection
                        {
                            DocumentTemplateId = template.Id,
                            DocumentClauseId = c.Id,
                            SectionNumber = $"{seq / 10}.0",
                            SectionTitle = c.ClauseTitle,
                            SequenceOrder = seq,
                            IsMandatory = c.IsMandatory,
                            CreatedAt = DateTime.UtcNow,
                            CreatedBy = "SystemSeed"
                        });
                        seq += 10;
                    }
                }
                await db.SaveChangesAsync();
            }
        }
    }
}
