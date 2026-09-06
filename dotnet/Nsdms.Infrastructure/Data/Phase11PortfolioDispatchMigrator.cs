using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator and seeder for Option B: Unified Dynamic Portfolio & Capability Dispatch Engine.
/// </summary>
public static class Phase11PortfolioDispatchMigrator
{
    public static async Task MigratePortfolioDispatchSchemaAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        var ddl = @"
-- 1. Create TerritoryDemarcation Table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'TerritoryDemarcation')
BEGIN
    CREATE TABLE [dbo].[TerritoryDemarcation] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_TerritoryDemarcation] PRIMARY KEY CLUSTERED,
        [TownName] NVARCHAR(150) NOT NULL,
        [RegionCode] NVARCHAR(50) NOT NULL,
        [RegionName] NVARCHAR(150) NOT NULL,
        [ProvinceCode] NVARCHAR(10) NOT NULL,
        [StatssaAreaCode] NVARCHAR(50) NULL,
        [EffectiveFrom] DATETIME2 NOT NULL CONSTRAINT [DF_TerritoryDemarcation_EffectiveFrom] DEFAULT '2020-01-01T00:00:00',
        [EffectiveTo] DATETIME2 NULL,
        [BoundaryGazetteReference] NVARCHAR(250) NULL,
        [IsActive] BIT NOT NULL CONSTRAINT [DF_TerritoryDemarcation_IsActive] DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_TerritoryDemarcation_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE INDEX [IX_TerritoryDemarcation_TownName] ON [dbo].[TerritoryDemarcation]([TownName]);
    CREATE INDEX [IX_TerritoryDemarcation_RegionCode] ON [dbo].[TerritoryDemarcation]([RegionCode]);
    CREATE INDEX [IX_TerritoryDemarcation_ProvinceCode] ON [dbo].[TerritoryDemarcation]([ProvinceCode]);
    CREATE INDEX [IX_TerritoryDemarcation_TownName_IsActive] ON [dbo].[TerritoryDemarcation]([TownName], [IsActive]);
END;

-- 2. Create StaffCapability Table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'StaffCapability')
BEGIN
    CREATE TABLE [dbo].[StaffCapability] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_StaffCapability] PRIMARY KEY CLUSTERED,
        [UserId] NVARCHAR(100) NOT NULL,
        [StaffName] NVARCHAR(150) NOT NULL,
        [Email] NVARCHAR(150) NOT NULL,
        [CapabilityCode] NVARCHAR(50) NOT NULL,
        [CapabilityName] NVARCHAR(150) NOT NULL,
        [StationedRegionCode] NVARCHAR(50) NOT NULL,
        [EmploymentRole] NVARCHAR(100) NOT NULL CONSTRAINT [DF_StaffCapability_EmploymentRole] DEFAULT 'Officer',
        [CertifiedDate] DATETIME2 NOT NULL CONSTRAINT [DF_StaffCapability_CertifiedDate] DEFAULT SYSUTCDATETIME(),
        [ExpiryDate] DATETIME2 NULL,
        [IsActive] BIT NOT NULL CONSTRAINT [DF_StaffCapability_IsActive] DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_StaffCapability_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE INDEX [IX_StaffCapability_UserId] ON [dbo].[StaffCapability]([UserId]);
    CREATE INDEX [IX_StaffCapability_CapabilityCode] ON [dbo].[StaffCapability]([CapabilityCode]);
    CREATE INDEX [IX_StaffCapability_StationedRegionCode] ON [dbo].[StaffCapability]([StationedRegionCode]);
    CREATE INDEX [IX_StaffCapability_User_Cap_Active] ON [dbo].[StaffCapability]([UserId], [CapabilityCode], [IsActive]);
END;

-- 3. Create OrganisationPortfolio Table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'OrganisationPortfolio')
BEGIN
    CREATE TABLE [dbo].[OrganisationPortfolio] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_OrganisationPortfolio] PRIMARY KEY CLUSTERED,
        [OrganisationId] INT NOT NULL CONSTRAINT [FK_OrganisationPortfolio_Organisation] FOREIGN KEY REFERENCES [dbo].[Organisation]([Id]) ON DELETE CASCADE,
        [RelationshipOfficerUserId] NVARCHAR(100) NOT NULL,
        [RelationshipOfficerName] NVARCHAR(150) NOT NULL,
        [RelationshipOfficerEmail] NVARCHAR(150) NOT NULL,
        [PortfolioRoleCode] NVARCHAR(50) NOT NULL CONSTRAINT [DF_OrganisationPortfolio_Role] DEFAULT 'PRIMARY_CLO',
        [ManagingRegionCode] NVARCHAR(50) NOT NULL,
        [IsCrossRegionalAssignment] BIT NOT NULL CONSTRAINT [DF_OrganisationPortfolio_CrossRegional] DEFAULT 0,
        [AssignmentReason] NVARCHAR(250) NOT NULL CONSTRAINT [DF_OrganisationPortfolio_Reason] DEFAULT 'Territory Default',
        [EffectiveFrom] DATETIME2 NOT NULL CONSTRAINT [DF_OrganisationPortfolio_EffectiveFrom] DEFAULT SYSUTCDATETIME(),
        [EffectiveTo] DATETIME2 NULL,
        [AssignedByUserId] NVARCHAR(100) NOT NULL CONSTRAINT [DF_OrganisationPortfolio_AssignedBy] DEFAULT 'SYSTEM',
        [IsActive] BIT NOT NULL CONSTRAINT [DF_OrganisationPortfolio_IsActive] DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_OrganisationPortfolio_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE INDEX [IX_OrganisationPortfolio_OrganisationId] ON [dbo].[OrganisationPortfolio]([OrganisationId]);
    CREATE INDEX [IX_OrganisationPortfolio_OfficerUserId] ON [dbo].[OrganisationPortfolio]([RelationshipOfficerUserId]);
    CREATE INDEX [IX_OrganisationPortfolio_ManagingRegionCode] ON [dbo].[OrganisationPortfolio]([ManagingRegionCode]);
    CREATE INDEX [IX_OrganisationPortfolio_Org_Active] ON [dbo].[OrganisationPortfolio]([OrganisationId], [IsActive]);
END;

-- 4. Create FieldDispatchAssignment Table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'FieldDispatchAssignment')
BEGIN
    CREATE TABLE [dbo].[FieldDispatchAssignment] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_FieldDispatchAssignment] PRIMARY KEY CLUSTERED,
        [OrganisationId] INT NOT NULL CONSTRAINT [FK_FieldDispatchAssignment_Organisation] FOREIGN KEY REFERENCES [dbo].[Organisation]([Id]) ON DELETE CASCADE,
        [VisitId] INT NULL CONSTRAINT [FK_FieldDispatchAssignment_Visit] FOREIGN KEY REFERENCES [dbo].[Visit]([Id]) ON DELETE SET NULL,
        [ContactPersonId] INT NOT NULL CONSTRAINT [FK_FieldDispatchAssignment_ContactPerson] FOREIGN KEY REFERENCES [dbo].[Person]([Id]),
        [ScheduledDate] DATETIME2 NOT NULL,
        [ActivityTypeCode] NVARCHAR(50) NOT NULL CONSTRAINT [DF_FieldDispatch_ActivityType] DEFAULT 'WORKPLACE_APPROVAL',
        [RequiredCapabilityCode] NVARCHAR(50) NOT NULL CONSTRAINT [DF_FieldDispatch_ReqCap] DEFAULT 'CAP_WORKPLACE_AUDIT',
        [DispatchedOfficerUserId] NVARCHAR(100) NOT NULL,
        [DispatchedOfficerName] NVARCHAR(150) NOT NULL,
        [ScheduledByCoordinatorUserId] NVARCHAR(100) NOT NULL,
        [ScheduledByCoordinatorName] NVARCHAR(150) NOT NULL,
        [DispatchStatus] NVARCHAR(50) NOT NULL CONSTRAINT [DF_FieldDispatch_Status] DEFAULT 'Dispatched',
        [Priority] NVARCHAR(30) NOT NULL CONSTRAINT [DF_FieldDispatch_Priority] DEFAULT 'Normal',
        [CoordinatorNotes] NVARCHAR(1000) NULL,
        [OfficerAcceptanceNotes] NVARCHAR(1000) NULL,
        [CompletedDate] DATETIME2 NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_FieldDispatch_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE INDEX [IX_FieldDispatchAssignment_OrganisationId] ON [dbo].[FieldDispatchAssignment]([OrganisationId]);
    CREATE INDEX [IX_FieldDispatchAssignment_VisitId] ON [dbo].[FieldDispatchAssignment]([VisitId]);
    CREATE INDEX [IX_FieldDispatchAssignment_ContactPersonId] ON [dbo].[FieldDispatchAssignment]([ContactPersonId]);
    CREATE INDEX [IX_FieldDispatchAssignment_OfficerUserId] ON [dbo].[FieldDispatchAssignment]([DispatchedOfficerUserId]);
    CREATE INDEX [IX_FieldDispatchAssignment_Status] ON [dbo].[FieldDispatchAssignment]([DispatchStatus]);
    CREATE INDEX [IX_FieldDispatchAssignment_ScheduledDate] ON [dbo].[FieldDispatchAssignment]([ScheduledDate]);
END;

-- 5. Create PortfolioHandoffLog Table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'PortfolioHandoffLog')
BEGIN
    CREATE TABLE [dbo].[PortfolioHandoffLog] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_PortfolioHandoffLog] PRIMARY KEY CLUSTERED,
        [FromOfficerUserId] NVARCHAR(100) NOT NULL,
        [FromOfficerName] NVARCHAR(150) NOT NULL,
        [ToOfficerUserId] NVARCHAR(100) NOT NULL,
        [ToOfficerName] NVARCHAR(150) NOT NULL,
        [OrganisationId] INT NOT NULL CONSTRAINT [FK_PortfolioHandoffLog_Organisation] FOREIGN KEY REFERENCES [dbo].[Organisation]([Id]) ON DELETE CASCADE,
        [AuthorizedByUserId] NVARCHAR(100) NOT NULL,
        [AuthorizedByName] NVARCHAR(150) NOT NULL,
        [HandoffReason] NVARCHAR(500) NOT NULL,
        [ReassignedTasksCount] INT NOT NULL CONSTRAINT [DF_PortfolioHandoffLog_TasksCount] DEFAULT 0,
        [ReassignedTaskIdsJson] NVARCHAR(MAX) NOT NULL CONSTRAINT [DF_PortfolioHandoffLog_TaskIds] DEFAULT '[]',
        [SecuritySealHash] NVARCHAR(128) NOT NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_PortfolioHandoffLog_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE INDEX [IX_PortfolioHandoffLog_OrganisationId] ON [dbo].[PortfolioHandoffLog]([OrganisationId]);
    CREATE INDEX [IX_PortfolioHandoffLog_FromOfficer] ON [dbo].[PortfolioHandoffLog]([FromOfficerUserId]);
    CREATE INDEX [IX_PortfolioHandoffLog_ToOfficer] ON [dbo].[PortfolioHandoffLog]([ToOfficerUserId]);
    CREATE INDEX [IX_PortfolioHandoffLog_CreatedAt] ON [dbo].[PortfolioHandoffLog]([CreatedAt]);
END;
";

        try
        {
            await context.Database.ExecuteSqlRawAsync(ddl);
            logger?.LogInformation("Phase 11: Dynamic Portfolio & Capability Dispatch Engine DDL migration executed successfully.");
        }
        catch (Exception ex)
        {
            logger?.LogWarning(ex, "Phase 11 DDL execution encountered non-fatal notification (tables may already exist).");
        }

        // Seed initial Territory Demarcations if empty
        if (!await context.TerritoryDemarcations.AnyAsync())
        {
            var territories = new List<TerritoryDemarcation>
            {
                // Gauteng South
                new() { TownName = "Johannesburg", RegionCode = "GAUTENG_SOUTH", RegionName = "Gauteng South Regional Office", ProvinceCode = "GP", StatssaAreaCode = "798000" },
                new() { TownName = "Germiston", RegionCode = "GAUTENG_SOUTH", RegionName = "Gauteng South Regional Office", ProvinceCode = "GP", StatssaAreaCode = "797000" },
                new() { TownName = "Alberton", RegionCode = "GAUTENG_SOUTH", RegionName = "Gauteng South Regional Office", ProvinceCode = "GP", StatssaAreaCode = "797005" },
                new() { TownName = "Soweto", RegionCode = "GAUTENG_SOUTH", RegionName = "Gauteng South Regional Office", ProvinceCode = "GP", StatssaAreaCode = "798015" },
                new() { TownName = "Vanderbijlpark", RegionCode = "GAUTENG_SOUTH", RegionName = "Gauteng South Regional Office", ProvinceCode = "GP", StatssaAreaCode = "799001" },

                // Gauteng North
                new() { TownName = "Pretoria", RegionCode = "GAUTENG_NORTH", RegionName = "Gauteng North Regional Office", ProvinceCode = "GP", StatssaAreaCode = "799000" },
                new() { TownName = "Centurion", RegionCode = "GAUTENG_NORTH", RegionName = "Gauteng North Regional Office", ProvinceCode = "GP", StatssaAreaCode = "799002" },
                new() { TownName = "Midrand", RegionCode = "GAUTENG_NORTH", RegionName = "Gauteng North Regional Office", ProvinceCode = "GP", StatssaAreaCode = "798003" },

                // Western Cape
                new() { TownName = "Cape Town", RegionCode = "WESTERN_CAPE", RegionName = "Western Cape Regional Office", ProvinceCode = "WC", StatssaAreaCode = "199000" },
                new() { TownName = "Bellville", RegionCode = "WESTERN_CAPE", RegionName = "Western Cape Regional Office", ProvinceCode = "WC", StatssaAreaCode = "199001" },
                new() { TownName = "Paarl", RegionCode = "WESTERN_CAPE", RegionName = "Western Cape Regional Office", ProvinceCode = "WC", StatssaAreaCode = "102000" },
                new() { TownName = "Stellenbosch", RegionCode = "WESTERN_CAPE", RegionName = "Western Cape Regional Office", ProvinceCode = "WC", StatssaAreaCode = "102005" },
                new() { TownName = "George", RegionCode = "WESTERN_CAPE", RegionName = "Western Cape Regional Office", ProvinceCode = "WC", StatssaAreaCode = "104000" },

                // KwaZulu-Natal
                new() { TownName = "Durban", RegionCode = "KZN", RegionName = "KwaZulu-Natal Regional Office", ProvinceCode = "KZN", StatssaAreaCode = "599000" },
                new() { TownName = "Pinetown", RegionCode = "KZN", RegionName = "KwaZulu-Natal Regional Office", ProvinceCode = "KZN", StatssaAreaCode = "599004" },
                new() { TownName = "Pietermaritzburg", RegionCode = "KZN", RegionName = "KwaZulu-Natal Regional Office", ProvinceCode = "KZN", StatssaAreaCode = "522000" },
                new() { TownName = "Richards Bay", RegionCode = "KZN", RegionName = "KwaZulu-Natal Regional Office", ProvinceCode = "KZN", StatssaAreaCode = "528000" },

                // Eastern Cape
                new() { TownName = "Gqeberha", RegionCode = "EASTERN_CAPE", RegionName = "Eastern Cape Regional Office", ProvinceCode = "EC", StatssaAreaCode = "299000" },
                new() { TownName = "East London", RegionCode = "EASTERN_CAPE", RegionName = "Eastern Cape Regional Office", ProvinceCode = "EC", StatssaAreaCode = "260000" },
                new() { TownName = "Uitenhage", RegionCode = "EASTERN_CAPE", RegionName = "Eastern Cape Regional Office", ProvinceCode = "EC", StatssaAreaCode = "299002" },

                // Free State & Northern Cape
                new() { TownName = "Bloemfontein", RegionCode = "FREE_STATE_NC", RegionName = "Free State & Northern Cape Office", ProvinceCode = "FS", StatssaAreaCode = "499000" },
                new() { TownName = "Kimberley", RegionCode = "FREE_STATE_NC", RegionName = "Free State & Northern Cape Office", ProvinceCode = "NC", StatssaAreaCode = "399000" },

                // Mpumalanga & Limpopo
                new() { TownName = "Mbombela", RegionCode = "MPUMALANGA_LIMPOPO", RegionName = "Mpumalanga & Limpopo Office", ProvinceCode = "MP", StatssaAreaCode = "899000" },
                new() { TownName = "Polokwane", RegionCode = "MPUMALANGA_LIMPOPO", RegionName = "Mpumalanga & Limpopo Office", ProvinceCode = "LP", StatssaAreaCode = "999000" },
                new() { TownName = "Witbank", RegionCode = "MPUMALANGA_LIMPOPO", RegionName = "Mpumalanga & Limpopo Office", ProvinceCode = "MP", StatssaAreaCode = "899002" }
            };

            await context.TerritoryDemarcations.AddRangeAsync(territories);
            await context.SaveChangesAsync();
            logger?.LogInformation("Phase 11: Seeded 21 baseline territory demarcations.");
        }

        // Seed Staff Capabilities if empty
        if (!await context.StaffCapabilities.AnyAsync())
        {
            var capabilities = new List<StaffCapability>
            {
                // Field Officers (CLOs)
                new() { UserId = "OFFICER_GP_01", StaffName = "Thabo Mokoena", Email = "tmokoena@merseta.org.za", CapabilityCode = "CAP_WORKPLACE_AUDIT", CapabilityName = "Workplace Site Approval Audit", StationedRegionCode = "GAUTENG_SOUTH", EmploymentRole = "Client Liaison Officer" },
                new() { UserId = "OFFICER_GP_01", StaffName = "Thabo Mokoena", Email = "tmokoena@merseta.org.za", CapabilityCode = "CAP_PORTFOLIO_MANAGEMENT", CapabilityName = "Dedicated Account Management", StationedRegionCode = "GAUTENG_SOUTH", EmploymentRole = "Client Liaison Officer" },
                new() { UserId = "OFFICER_KZN_01", StaffName = "Nomusa Zungu", Email = "nzungu@merseta.org.za", CapabilityCode = "CAP_WORKPLACE_AUDIT", CapabilityName = "Workplace Site Approval Audit", StationedRegionCode = "KZN", EmploymentRole = "Client Liaison Officer" },
                new() { UserId = "OFFICER_KZN_01", StaffName = "Nomusa Zungu", Email = "nzungu@merseta.org.za", CapabilityCode = "CAP_PORTFOLIO_MANAGEMENT", CapabilityName = "Dedicated Account Management", StationedRegionCode = "KZN", EmploymentRole = "Client Liaison Officer" },
                new() { UserId = "OFFICER_WC_01", StaffName = "Pieter Van Zyl", Email = "pvanzyl@merseta.org.za", CapabilityCode = "CAP_WORKPLACE_AUDIT", CapabilityName = "Workplace Site Approval Audit", StationedRegionCode = "WESTERN_CAPE", EmploymentRole = "Client Liaison Officer" },

                // Quality Assurors (Non-CLO Verifiers)
                new() { UserId = "QA_SPEC_01", StaffName = "Fatima Patel", Email = "fpatel@merseta.org.za", CapabilityCode = "CAP_WORKPLACE_AUDIT", CapabilityName = "Workplace Site Approval Audit", StationedRegionCode = "GAUTENG_SOUTH", EmploymentRole = "Quality Assuror" },
                new() { UserId = "QA_SPEC_01", StaffName = "Fatima Patel", Email = "fpatel@merseta.org.za", CapabilityCode = "CAP_QUALITY_ASSURANCE", CapabilityName = "ETQA Curriculum & Tool Evaluation", StationedRegionCode = "GAUTENG_SOUTH", EmploymentRole = "Quality Assuror" },

                // External Technical Evaluator (Non-CLO Specialist)
                new() { UserId = "EXT_AUDITOR_01", StaffName = "Johan Botha", Email = "jbotha.consult@merseta-audit.co.za", CapabilityCode = "CAP_WORKPLACE_AUDIT", CapabilityName = "Workplace Site Approval Audit", StationedRegionCode = "NATIONAL", EmploymentRole = "Contract Evaluator" },
                new() { UserId = "EXT_AUDITOR_01", StaffName = "Johan Botha", Email = "jbotha.consult@merseta-audit.co.za", CapabilityCode = "CAP_ARPL_ASSESSMENT", CapabilityName = "Artisan Practical Trade Assessment", StationedRegionCode = "NATIONAL", EmploymentRole = "Contract Evaluator" },

                // Client Liaison Coordinator (CLC - Scheduler & Dispatcher)
                new() { UserId = "CLC_COORD_01", StaffName = "Lerato Khumalo", Email = "lkhumalo@merseta.org.za", CapabilityCode = "CAP_DISPATCH_COORDINATION", CapabilityName = "Field Scheduling & Visit Dispatch", StationedRegionCode = "GAUTENG_SOUTH", EmploymentRole = "Client Liaison Coordinator" },
                new() { UserId = "CLC_COORD_KZN", StaffName = "Sipho Dlamini", Email = "sdlamini@merseta.org.za", CapabilityCode = "CAP_DISPATCH_COORDINATION", CapabilityName = "Field Scheduling & Visit Dispatch", StationedRegionCode = "KZN", EmploymentRole = "Client Liaison Coordinator" }
            };

            await context.StaffCapabilities.AddRangeAsync(capabilities);
            await context.SaveChangesAsync();
            logger?.LogInformation("Phase 11: Seeded 11 staff capability credentials across CLOs, QAs, and Coordinators.");
        }

        // Seed Organisation Portfolios if empty and Organisations exist
        if (!await context.OrganisationPortfolios.AnyAsync())
        {
            var orgs = await context.Organisations.Take(5).ToListAsync();
            if (orgs.Count > 0)
            {
                var portfolios = new List<OrganisationPortfolio>();

                // 1. Standard Territory Allocation
                portfolios.Add(new OrganisationPortfolio
                {
                    OrganisationId = orgs[0].Id,
                    RelationshipOfficerUserId = "OFFICER_GP_01",
                    RelationshipOfficerName = "Thabo Mokoena",
                    RelationshipOfficerEmail = "tmokoena@merseta.org.za",
                    PortfolioRoleCode = "PRIMARY_CLO",
                    ManagingRegionCode = "GAUTENG_SOUTH",
                    IsCrossRegionalAssignment = false,
                    AssignmentReason = "Territory Default Allocation",
                    EffectiveFrom = DateTime.UtcNow.AddMonths(-12),
                    IsActive = true
                });

                if (orgs.Count > 1)
                {
                    // 2. Cross-Regional National Key Account Allocation
                    portfolios.Add(new OrganisationPortfolio
                    {
                        OrganisationId = orgs[1].Id,
                        RelationshipOfficerUserId = "OFFICER_GP_01", // Gauteng-based officer
                        RelationshipOfficerName = "Thabo Mokoena",
                        RelationshipOfficerEmail = "tmokoena@merseta.org.za",
                        PortfolioRoleCode = "KEY_ACCOUNT_MANAGER",
                        ManagingRegionCode = "GAUTENG_SOUTH",
                        IsCrossRegionalAssignment = true, // Cross-regional override!
                        AssignmentReason = "National Key Account - Automotive Multi-Plant Group",
                        EffectiveFrom = DateTime.UtcNow.AddMonths(-6),
                        IsActive = true
                    });
                }

                if (orgs.Count > 2)
                {
                    // 3. Specialized Sector Portfolio
                    portfolios.Add(new OrganisationPortfolio
                    {
                        OrganisationId = orgs[2].Id,
                        RelationshipOfficerUserId = "OFFICER_KZN_01",
                        RelationshipOfficerName = "Nomusa Zungu",
                        RelationshipOfficerEmail = "nzungu@merseta.org.za",
                        PortfolioRoleCode = "SECTOR_SPECIALIST",
                        ManagingRegionCode = "KZN",
                        IsCrossRegionalAssignment = false,
                        AssignmentReason = "Marine & Plastics Sector Specialist Allocation",
                        EffectiveFrom = DateTime.UtcNow.AddMonths(-8),
                        IsActive = true
                    });
                }

                await context.OrganisationPortfolios.AddRangeAsync(portfolios);
                await context.SaveChangesAsync();
                logger?.LogInformation("Phase 11: Seeded baseline organisation portfolios with cross-regional accounts.");
            }
        }
    }
}
