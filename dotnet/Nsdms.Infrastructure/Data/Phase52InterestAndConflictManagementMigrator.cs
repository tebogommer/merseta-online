using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator for Module 18: Interest and Conflict of Interest (COI) Management.
/// Provisions tables for OrganisationGovernanceMember, InstitutionalAffiliation, InterestDeclaration,
/// InterestDeclarationItem, and ConflictFlag.
/// Seeds default governance configurations and baseline demonstration data.
/// </summary>
public static class Phase52InterestAndConflictManagementMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            var sqlFile = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_15_Interest_And_Conflict_Management.sql");
            if (!File.Exists(sqlFile))
            {
                // Fallback for test runner execution
                sqlFile = Path.Combine(AppContext.BaseDirectory, "..", "..", "..", "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_15_Interest_And_Conflict_Management.sql");
            }

            if (File.Exists(sqlFile))
            {
                var sql = await File.ReadAllTextAsync(sqlFile);
                await context.Database.ExecuteSqlRawAsync(sql);
                logger?.LogInformation("Phase 52 Interest and Conflict Management DDL executed successfully from {Path}.", sqlFile);
            }
            else
            {
                // In-line execution fallback
                const string inlineDdl = @"
                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'OrganisationGovernanceMember')
                BEGIN
                    CREATE TABLE [dbo].[OrganisationGovernanceMember] (
                        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_OrganisationGovernanceMember] PRIMARY KEY CLUSTERED,
                        [OrganisationId] INT NOT NULL,
                        [PersonId] INT NULL,
                        [ShareholderOrganisationId] INT NULL,
                        [MemberType] NVARCHAR(30) NOT NULL CONSTRAINT [DF_OrganisationGovernanceMember_MemberType] DEFAULT ('NATURAL_PERSON'),
                        [CorporateEntityName] NVARCHAR(200) NULL,
                        [CorporateRegistrationNumber] NVARCHAR(50) NULL,
                        [DirectorCategory] NVARCHAR(30) NULL,
                        [GovernanceRoleCode] NVARCHAR(50) NOT NULL,
                        [ShareholdingPercentage] DECIMAL(5,2) NOT NULL CONSTRAINT [DF_OrganisationGovernanceMember_ShareholdingPercentage] DEFAULT (0.00),
                        [HasVotingRights] BIT NOT NULL CONSTRAINT [DF_OrganisationGovernanceMember_HasVotingRights] DEFAULT (1),
                        [AppointmentDate] DATETIME2 NULL,
                        [ResignationDate] DATETIME2 NULL,
                        [CipcRegistered] BIT NOT NULL CONSTRAINT [DF_OrganisationGovernanceMember_CipcRegistered] DEFAULT (1),
                        [IdVerified] BIT NOT NULL CONSTRAINT [DF_OrganisationGovernanceMember_IdVerified] DEFAULT (1),
                        [IsActive] BIT NOT NULL CONSTRAINT [DF_OrganisationGovernanceMember_IsActive] DEFAULT (1),
                        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_OrganisationGovernanceMember_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                        [CreatedBy] NVARCHAR(150) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(150) NULL,
                        CONSTRAINT [FK_OrganisationGovernanceMember_Organisation] FOREIGN KEY ([OrganisationId]) REFERENCES [dbo].[Organisation] ([Id]) ON DELETE CASCADE,
                        CONSTRAINT [FK_OrganisationGovernanceMember_Person] FOREIGN KEY ([PersonId]) REFERENCES [dbo].[Person] ([Id]),
                        CONSTRAINT [FK_OrganisationGovernanceMember_ShareholderOrg] FOREIGN KEY ([ShareholderOrganisationId]) REFERENCES [dbo].[Organisation] ([Id])
                    );
                    CREATE NONCLUSTERED INDEX [IX_OrganisationGovernanceMember_OrganisationId] ON [dbo].[OrganisationGovernanceMember] ([OrganisationId]);
                    CREATE NONCLUSTERED INDEX [IX_OrganisationGovernanceMember_PersonId] ON [dbo].[OrganisationGovernanceMember] ([PersonId]);
                    CREATE NONCLUSTERED INDEX [IX_OrganisationGovernanceMember_ShareholderOrgId] ON [dbo].[OrganisationGovernanceMember] ([ShareholderOrganisationId]) WHERE [ShareholderOrganisationId] IS NOT NULL;
                    CREATE NONCLUSTERED INDEX [IX_OrganisationGovernanceMember_MemberType] ON [dbo].[OrganisationGovernanceMember] ([MemberType]);
                    CREATE NONCLUSTERED INDEX [IX_OrganisationGovernanceMember_DirectorCategory] ON [dbo].[OrganisationGovernanceMember] ([DirectorCategory]);
                    CREATE NONCLUSTERED INDEX [IX_OrganisationGovernanceMember_GovernanceRoleCode] ON [dbo].[OrganisationGovernanceMember] ([GovernanceRoleCode]);
                    CREATE NONCLUSTERED INDEX [IX_OrganisationGovernanceMember_Org_Person_Role] ON [dbo].[OrganisationGovernanceMember] ([OrganisationId], [PersonId], [GovernanceRoleCode]);
                END;

                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'InstitutionalAffiliation')
                BEGIN
                    CREATE TABLE [dbo].[InstitutionalAffiliation] (
                        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_InstitutionalAffiliation] PRIMARY KEY CLUSTERED,
                        [PersonId] INT NOT NULL,
                        [AffiliationTypeCode] NVARCHAR(50) NOT NULL,
                        [DepartmentOrCommittee] NVARCHAR(150) NOT NULL,
                        [Designation] NVARCHAR(150) NOT NULL,
                        [EmployeeNumber] NVARCHAR(50) NULL,
                        [IsIndependentMember] BIT NOT NULL CONSTRAINT [DF_InstitutionalAffiliation_IsIndependentMember] DEFAULT (0),
                        [TermStartDate] DATETIME2 NULL,
                        [TermEndDate] DATETIME2 NULL,
                        [IsActive] BIT NOT NULL CONSTRAINT [DF_InstitutionalAffiliation_IsActive] DEFAULT (1),
                        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_InstitutionalAffiliation_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                        [CreatedBy] NVARCHAR(150) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(150) NULL,
                        CONSTRAINT [FK_InstitutionalAffiliation_Person] FOREIGN KEY ([PersonId]) REFERENCES [dbo].[Person] ([Id]) ON DELETE CASCADE
                    );
                    CREATE NONCLUSTERED INDEX [IX_InstitutionalAffiliation_PersonId] ON [dbo].[InstitutionalAffiliation] ([PersonId]);
                    CREATE NONCLUSTERED INDEX [IX_InstitutionalAffiliation_AffiliationTypeCode] ON [dbo].[InstitutionalAffiliation] ([AffiliationTypeCode]);
                    CREATE NONCLUSTERED INDEX [IX_InstitutionalAffiliation_IsActive] ON [dbo].[InstitutionalAffiliation] ([IsActive]);
                END;

                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'InterestDeclaration')
                BEGIN
                    CREATE TABLE [dbo].[InterestDeclaration] (
                        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_InterestDeclaration] PRIMARY KEY CLUSTERED,
                        [PersonId] INT NOT NULL,
                        [FinancialYearId] INT NULL,
                        [DeclarationPeriodYear] NVARCHAR(10) NOT NULL,
                        [DeclarationTypeCode] NVARCHAR(50) NOT NULL,
                        [MeetingOrProjectRef] NVARCHAR(150) NULL,
                        [StatusCode] NVARCHAR(50) NOT NULL CONSTRAINT [DF_InterestDeclaration_StatusCode] DEFAULT ('SUBMITTED'),
                        [HasConflictsToDeclare] BIT NOT NULL CONSTRAINT [DF_InterestDeclaration_HasConflictsToDeclare] DEFAULT (0),
                        [GeneralDeclarationNotes] NVARCHAR(2000) NULL,
                        [DigitalSignatureSeal] NVARCHAR(100) NULL,
                        [CertifiedAt] DATETIME2 NULL,
                        [CertifiedByUserId] NVARCHAR(150) NULL,
                        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_InterestDeclaration_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                        [CreatedBy] NVARCHAR(150) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(150) NULL,
                        CONSTRAINT [FK_InterestDeclaration_Person] FOREIGN KEY ([PersonId]) REFERENCES [dbo].[Person] ([Id]) ON DELETE CASCADE,
                        CONSTRAINT [FK_InterestDeclaration_FinancialYear] FOREIGN KEY ([FinancialYearId]) REFERENCES [dbo].[FinancialYear] ([Id]) ON DELETE SET NULL
                    );
                    CREATE NONCLUSTERED INDEX [IX_InterestDeclaration_PersonId] ON [dbo].[InterestDeclaration] ([PersonId]);
                    CREATE NONCLUSTERED INDEX [IX_InterestDeclaration_FinancialYearId] ON [dbo].[InterestDeclaration] ([FinancialYearId]);
                    CREATE NONCLUSTERED INDEX [IX_InterestDeclaration_Period_Status] ON [dbo].[InterestDeclaration] ([DeclarationPeriodYear], [StatusCode]);
                    CREATE NONCLUSTERED INDEX [IX_InterestDeclaration_DeclarationTypeCode] ON [dbo].[InterestDeclaration] ([DeclarationTypeCode]);
                END;

                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'InterestDeclarationItem')
                BEGIN
                    CREATE TABLE [dbo].[InterestDeclarationItem] (
                        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_InterestDeclarationItem] PRIMARY KEY CLUSTERED,
                        [InterestDeclarationId] INT NOT NULL,
                        [OrganisationName] NVARCHAR(250) NOT NULL,
                        [RegistrationOrSdlNumber] NVARCHAR(50) NULL,
                        [NatureOfRelationship] NVARCHAR(50) NOT NULL,
                        [InterestPercentage] DECIMAL(5,2) NULL,
                        [AnnualRemunerationOrBenefit] DECIMAL(18,2) NULL,
                        [IsApprovedExternalWork] BIT NOT NULL CONSTRAINT [DF_InterestDeclarationItem_IsApprovedExternalWork] DEFAULT (0),
                        [ApprovalReference] NVARCHAR(100) NULL,
                        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_InterestDeclarationItem_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                        [CreatedBy] NVARCHAR(150) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(150) NULL,
                        CONSTRAINT [FK_InterestDeclarationItem_InterestDeclaration] FOREIGN KEY ([InterestDeclarationId]) REFERENCES [dbo].[InterestDeclaration] ([Id]) ON DELETE CASCADE
                    );
                    CREATE NONCLUSTERED INDEX [IX_InterestDeclarationItem_DeclarationId] ON [dbo].[InterestDeclarationItem] ([InterestDeclarationId]);
                END;

                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'ConflictFlag')
                BEGIN
                    CREATE TABLE [dbo].[ConflictFlag] (
                        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_ConflictFlag] PRIMARY KEY CLUSTERED,
                        [TargetOrganisationId] INT NULL,
                        [TargetGrantApplicationId] INT NULL,
                        [TargetTrainingProviderId] INT NULL,
                        [PersonId] INT NOT NULL,
                        [SeverityCode] NVARCHAR(50) NOT NULL,
                        [ConflictCategoryCode] NVARCHAR(50) NOT NULL,
                        [Title] NVARCHAR(250) NOT NULL,
                        [Description] NVARCHAR(2000) NOT NULL,
                        [DetectedAt] DATETIME2 NOT NULL CONSTRAINT [DF_ConflictFlag_DetectedAt] DEFAULT (SYSUTCDATETIME()),
                        [ResolutionStatusCode] NVARCHAR(50) NOT NULL CONSTRAINT [DF_ConflictFlag_ResolutionStatusCode] DEFAULT ('OPEN'),
                        [ResolutionNotes] NVARCHAR(2000) NULL,
                        [ClearedByUserId] NVARCHAR(150) NULL,
                        [ClearedAt] DATETIME2 NULL,
                        [ClearanceAuthorityRole] NVARCHAR(100) NULL,
                        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_ConflictFlag_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                        [CreatedBy] NVARCHAR(150) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(150) NULL,
                        CONSTRAINT [FK_ConflictFlag_Organisation] FOREIGN KEY ([TargetOrganisationId]) REFERENCES [dbo].[Organisation] ([Id]) ON DELETE SET NULL,
                        CONSTRAINT [FK_ConflictFlag_GrantApplication] FOREIGN KEY ([TargetGrantApplicationId]) REFERENCES [dbo].[GrantApplication] ([Id]) ON DELETE SET NULL,
                        CONSTRAINT [FK_ConflictFlag_TrainingProvider] FOREIGN KEY ([TargetTrainingProviderId]) REFERENCES [dbo].[TrainingProvider] ([Id]) ON DELETE SET NULL,
                        CONSTRAINT [FK_ConflictFlag_Person] FOREIGN KEY ([PersonId]) REFERENCES [dbo].[Person] ([Id])
                    );
                    CREATE NONCLUSTERED INDEX [IX_ConflictFlag_TargetOrganisationId] ON [dbo].[ConflictFlag] ([TargetOrganisationId]);
                    CREATE NONCLUSTERED INDEX [IX_ConflictFlag_TargetGrantApplicationId] ON [dbo].[ConflictFlag] ([TargetGrantApplicationId]);
                    CREATE NONCLUSTERED INDEX [IX_ConflictFlag_TargetTrainingProviderId] ON [dbo].[ConflictFlag] ([TargetTrainingProviderId]);
                    CREATE NONCLUSTERED INDEX [IX_ConflictFlag_PersonId] ON [dbo].[ConflictFlag] ([PersonId]);
                    CREATE NONCLUSTERED INDEX [IX_ConflictFlag_SeverityCode] ON [dbo].[ConflictFlag] ([SeverityCode]);
                    CREATE NONCLUSTERED INDEX [IX_ConflictFlag_Category_Status] ON [dbo].[ConflictFlag] ([ConflictCategoryCode], [ResolutionStatusCode]);
                    CREATE NONCLUSTERED INDEX [IX_ConflictFlag_DetectedAt] ON [dbo].[ConflictFlag] ([DetectedAt]);
                END;";

                await context.Database.ExecuteSqlRawAsync(inlineDdl);
                logger?.LogInformation("Phase 52 Interest and Conflict Management inline DDL executed successfully.");
            }
        }

        // Seed default governance configurations if missing
        await SeedDefaultGovernanceConfigurationsAsync(context);

        // Seed sample demonstration data
        await SeedSampleConflictDataAsync(context);
    }

    private static async Task SeedDefaultGovernanceConfigurationsAsync(NsdmsDbContext db)
    {
        var defaultConfigs = new List<(string Key, string Value, string Category, string Description)>
        {
            ("Governance:MinimumShareholdingDisclosureThresholdPercent", "5.0", "Governance", "Minimum beneficial shareholding percentage requiring mandatory identity disclosure and conflict vetting (enforced for grants and accreditation)."),
            ("Governance:EnforceShareholdingDisclosure", "true", "Governance", "Indicates whether grant and accreditation submissions are strictly blocked or flagged if shareholding disclosure is incomplete."),
            ("Governance:EnforceAccreditationGovernanceCheck", "true", "Governance", "Indicates whether Skills Development Provider accreditation applications enforce full director and shareholder capture."),
            ("Governance:ConflictClearanceAuthority", "RiskAndComplianceManager,InternalAudit,Ceo,AccountingAuthority", "Governance", "Comma-separated list of authorized roles permitted to investigate and clear flagged conflicts of interest."),
            ("Governance:RequireDeclarationBeforeAdjudication", "true", "Governance", "Indicates whether committee members must submit a meeting recusal attestation prior to grant adjudication voting.")
        };

        foreach (var (key, value, category, desc) in defaultConfigs)
        {
            var existing = await db.SystemConfigs.FirstOrDefaultAsync(c => c.ConfigKey == key);
            if (existing == null)
            {
                db.SystemConfigs.Add(new SystemConfig
                {
                    ConfigKey = key,
                    ConfigValue = value,
                    Category = category,
                    Description = desc,
                    DataType = "String",
                    IsActive = true,
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "SYSTEM"
                });
            }
        }

        await db.SaveChangesAsync();
    }

    private static async Task SeedSampleConflictDataAsync(NsdmsDbContext db)
    {
        // Check if sample governance members or insiders already exist
        if (await db.OrganisationGovernanceMembers.AnyAsync() || await db.InstitutionalAffiliations.AnyAsync())
        {
            return;
        }

        // Ensure key sample people exist
        var sipho = await db.People.FirstOrDefaultAsync(p => p.RsaIdNumber == "7503155123089");
        if (sipho == null)
        {
            sipho = new Person
            {
                FirstName = "Sipho",
                LastName = "Nkosi",
                RsaIdNumber = "7503155123089",
                Email = "sipho.nkosi@merseta.org.za",
                PhoneNumber = "011-555-0199",
                IsSouthAfricanCitizen = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "SYSTEM"
            };
            db.People.Add(sipho);
        }

        var thandi = await db.People.FirstOrDefaultAsync(p => p.RsaIdNumber == "8206120199081");
        if (thandi == null)
        {
            thandi = new Person
            {
                FirstName = "Thandi",
                LastName = "Mthembu",
                RsaIdNumber = "8206120199081",
                Email = "thandi.mthembu@auditboard.co.za",
                PhoneNumber = "012-444-9871",
                IsSouthAfricanCitizen = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "SYSTEM"
            };
            db.People.Add(thandi);
        }

        var willem = await db.People.FirstOrDefaultAsync(p => p.RsaIdNumber == "6811205098083");
        if (willem == null)
        {
            willem = new Person
            {
                FirstName = "Willem",
                LastName = "Botha",
                RsaIdNumber = "6811205098083",
                Email = "willem.botha@precisionauto.co.za",
                PhoneNumber = "031-700-1122",
                IsSouthAfricanCitizen = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "SYSTEM"
            };
            db.People.Add(willem);
        }

        var nomvula = await db.People.FirstOrDefaultAsync(p => p.RsaIdNumber == "8904050187084");
        if (nomvula == null)
        {
            nomvula = new Person
            {
                FirstName = "Nomvula",
                LastName = "Dlamini",
                RsaIdNumber = "8904050187084",
                Email = "nomvula.dlamini@merseta.org.za",
                PhoneNumber = "011-555-0144",
                IsSouthAfricanCitizen = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "SYSTEM"
            };
            db.People.Add(nomvula);
        }

        await db.SaveChangesAsync();

        // 1. Seed Institutional Affiliations
        // Sipho Nkosi is an Accounting Authority (Board) Member
        db.InstitutionalAffiliations.Add(new InstitutionalAffiliation
        {
            PersonId = sipho.Id,
            AffiliationTypeCode = "ACCOUNTING_AUTHORITY_MEMBER",
            DepartmentOrCommittee = "Accounting Authority Board",
            Designation = "Non-Executive Board Member",
            IsIndependentMember = false,
            TermStartDate = new DateTime(2025, 4, 1),
            TermEndDate = new DateTime(2030, 3, 31),
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "SYSTEM"
        });

        // Thandi Mthembu is an Independent Audit & Risk Committee Member
        db.InstitutionalAffiliations.Add(new InstitutionalAffiliation
        {
            PersonId = thandi.Id,
            AffiliationTypeCode = "INDEPENDENT_COMMITTEE_MEMBER",
            DepartmentOrCommittee = "Audit & Risk Committee",
            Designation = "Independent Audit Specialist",
            IsIndependentMember = true,
            TermStartDate = new DateTime(2024, 1, 1),
            TermEndDate = new DateTime(2027, 12, 31),
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "SYSTEM"
        });

        // Nomvula Dlamini is a permanent merSETA employee (Senior Grant Evaluator)
        db.InstitutionalAffiliations.Add(new InstitutionalAffiliation
        {
            PersonId = nomvula.Id,
            AffiliationTypeCode = "PERMANENT_EMPLOYEE",
            DepartmentOrCommittee = "Grants & Operations",
            Designation = "Senior Grant Evaluator",
            EmployeeNumber = "EMP-0042",
            IsIndependentMember = false,
            TermStartDate = new DateTime(2022, 6, 1),
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "SYSTEM"
        });

        // 2. Fetch organisations for governance linkage
        var orgs = await db.Organisations.Take(3).ToListAsync();
        if (orgs.Count > 0)
        {
            var org1 = orgs[0];
            
            // Willem Botha is Director & 60% Shareholder in Org 1
            db.OrganisationGovernanceMembers.Add(new OrganisationGovernanceMember
            {
                OrganisationId = org1.Id,
                PersonId = willem.Id,
                GovernanceRoleCode = "MANAGING_DIRECTOR",
                ShareholdingPercentage = 60.00m,
                HasVotingRights = true,
                AppointmentDate = new DateTime(2018, 5, 12),
                CipcRegistered = true,
                IdVerified = true,
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "SYSTEM"
            });

            // Sipho Nkosi (Board Member!) is also a 25% Shareholder in Org 1 -> Creates a RED CRITICAL conflict!
            db.OrganisationGovernanceMembers.Add(new OrganisationGovernanceMember
            {
                OrganisationId = org1.Id,
                PersonId = sipho.Id,
                GovernanceRoleCode = "SHAREHOLDER",
                ShareholdingPercentage = 25.00m,
                HasVotingRights = true,
                AppointmentDate = new DateTime(2020, 2, 1),
                CipcRegistered = true,
                IdVerified = true,
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "SYSTEM"
            });

            if (orgs.Count > 1)
            {
                var org2 = orgs[1];

                // Willem Botha is ALSO a Director & 40% Shareholder in Org 2! -> Creates an AMBER MULTI-ORGANISATION SYNDICATE conflict!
                db.OrganisationGovernanceMembers.Add(new OrganisationGovernanceMember
                {
                    OrganisationId = org2.Id,
                    PersonId = willem.Id,
                    GovernanceRoleCode = "DIRECTOR",
                    ShareholdingPercentage = 40.00m,
                    HasVotingRights = true,
                    AppointmentDate = new DateTime(2021, 8, 15),
                    CipcRegistered = true,
                    IdVerified = true,
                    IsActive = true,
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "SYSTEM"
                });
            }

            await db.SaveChangesAsync();

            // 3. Seed Demonstrative Conflict Flags
            // Red Critical Flag for Sipho Nkosi (Board Member holding shares in applicant Org 1)
            db.ConflictFlags.Add(new ConflictFlag
            {
                TargetOrganisationId = org1.Id,
                PersonId = sipho.Id,
                SeverityCode = "RED_CRITICAL",
                ConflictCategoryCode = "INSIDER_AFFILIATION",
                Title = "Accounting Authority Member Beneficial Interest in Grant Applicant",
                Description = $"Mr Sipho Nkosi serves as an active Accounting Authority Board Member while holding a 25.00% equity shareholding in {org1.CompanyName}. Prohibited under PFMA Section 50(1) without formal recusal and Ministerial disclosure.",
                DetectedAt = DateTime.UtcNow.AddDays(-5),
                ResolutionStatusCode = "UNDER_INVESTIGATION",
                CreatedAt = DateTime.UtcNow.AddDays(-5),
                CreatedBy = "SYSTEM"
            });

            // Amber Elevated Flag for Willem Botha (Multi-organisation syndicate)
            if (orgs.Count > 1)
            {
                var org2 = orgs[1];
                db.ConflictFlags.Add(new ConflictFlag
                {
                    TargetOrganisationId = org2.Id,
                    PersonId = willem.Id,
                    SeverityCode = "AMBER_ELEVATED",
                    ConflictCategoryCode = "MULTI_ORGANISATION_GRANT_SYNDICATE",
                    Title = "Multi-Organisation Dual Directorship & Grant Beneficiary Syndicate",
                    Description = $"Mr Willem Botha holds controlling directorships and significant shareholding across multiple participating organisations ({org1.CompanyName} and {org2.CompanyName}) concurrently applying for merSETA Discretionary Grants.",
                    DetectedAt = DateTime.UtcNow.AddDays(-3),
                    ResolutionStatusCode = "OPEN",
                    CreatedAt = DateTime.UtcNow.AddDays(-3),
                    CreatedBy = "SYSTEM"
                });
            }

            // Yellow Advisory Flag for Overdue Annual Declaration
            db.ConflictFlags.Add(new ConflictFlag
            {
                PersonId = thandi.Id,
                SeverityCode = "YELLOW_ADVISORY",
                ConflictCategoryCode = "OVERDUE_DECLARATION",
                Title = "Annual Declaration of Interest Filing Overdue",
                Description = "Independent Audit & Risk Committee member Ms Thandi Mthembu has not submitted the mandatory annual King IV Declaration of Interest for the active financial year.",
                DetectedAt = DateTime.UtcNow.AddDays(-14),
                ResolutionStatusCode = "OPEN",
                CreatedAt = DateTime.UtcNow.AddDays(-14),
                CreatedBy = "SYSTEM"
            });

            // 4. Seed an Example Certified Declaration of Interest
            var sampleDec = new InterestDeclaration
            {
                PersonId = sipho.Id,
                DeclarationPeriodYear = "2026",
                DeclarationTypeCode = "ANNUAL_COMPLIANCE",
                StatusCode = "FLAGGED_CONFLICT",
                HasConflictsToDeclare = true,
                GeneralDeclarationNotes = "Disclosing commercial equity interest in engineering manufacturing entities.",
                DigitalSignatureSeal = "SEAL-SHA256-78A9B2C4",
                CertifiedAt = DateTime.UtcNow.AddDays(-30),
                CertifiedByUserId = "sipho.nkosi",
                CreatedAt = DateTime.UtcNow.AddDays(-30),
                CreatedBy = "sipho.nkosi"
            };
            db.InterestDeclarations.Add(sampleDec);
            await db.SaveChangesAsync();

            db.InterestDeclarationItems.Add(new InterestDeclarationItem
            {
                InterestDeclarationId = sampleDec.Id,
                OrganisationName = org1.CompanyName,
                RegistrationOrSdlNumber = org1.SdlNumber,
                NatureOfRelationship = "SHAREHOLDER",
                InterestPercentage = 25.00m,
                AnnualRemunerationOrBenefit = 150000.00m,
                IsApprovedExternalWork = true,
                ApprovalReference = "RWOPS-2025-014",
                CreatedAt = DateTime.UtcNow.AddDays(-30),
                CreatedBy = "sipho.nkosi"
            });

            await db.SaveChangesAsync();
        }
    }
}
