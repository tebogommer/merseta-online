using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator and seeder for Phase 1: Spatial Zoning, Auto-Intake & Foundational Caseload Heatmap (The Smart Backbone).
/// </summary>
public static class Phase12ZoningAndCaseloadMigrator
{
    public static async Task MigrateZoningAndCaseloadSchemaAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        var ddl = @"
-- 1. Create TerritoryZone Table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'TerritoryZone')
BEGIN
    CREATE TABLE [dbo].[TerritoryZone] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_TerritoryZone] PRIMARY KEY CLUSTERED,
        [ZoneCode] NVARCHAR(50) NOT NULL,
        [ZoneName] NVARCHAR(150) NOT NULL,
        [RegionCode] NVARCHAR(50) NOT NULL,
        [RegionName] NVARCHAR(150) NOT NULL,
        [ProvinceCode] NVARCHAR(10) NOT NULL,
        [DefaultOfficerUserId] NVARCHAR(100) NULL,
        [DefaultOfficerName] NVARCHAR(150) NULL,
        [DefaultOfficerEmail] NVARCHAR(150) NULL,
        [Description] NVARCHAR(500) NULL,
        [IsActive] BIT NOT NULL CONSTRAINT [DF_TerritoryZone_IsActive] DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_TerritoryZone_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE UNIQUE NONCLUSTERED INDEX [IX_TerritoryZone_ZoneCode] ON [dbo].[TerritoryZone]([ZoneCode]);
    CREATE NONCLUSTERED INDEX [IX_TerritoryZone_RegionCode] ON [dbo].[TerritoryZone]([RegionCode]);
    CREATE NONCLUSTERED INDEX [IX_TerritoryZone_DefaultOfficerUserId] ON [dbo].[TerritoryZone]([DefaultOfficerUserId]);
    CREATE NONCLUSTERED INDEX [IX_TerritoryZone_IsActive] ON [dbo].[TerritoryZone]([IsActive]);
    PRINT 'Created table [dbo].[TerritoryZone].';
END;

-- 2. Add Zone columns to TerritoryDemarcation if missing
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'TerritoryDemarcation')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('TerritoryDemarcation') AND name = 'ZoneId')
    BEGIN
        ALTER TABLE [dbo].[TerritoryDemarcation] ADD [ZoneId] INT NULL;
        ALTER TABLE [dbo].[TerritoryDemarcation] ADD CONSTRAINT [FK_TerritoryDemarcation_Zone] FOREIGN KEY ([ZoneId]) REFERENCES [dbo].[TerritoryZone]([Id]) ON DELETE SET NULL;
        CREATE NONCLUSTERED INDEX [IX_TerritoryDemarcation_ZoneId] ON [dbo].[TerritoryDemarcation]([ZoneId]);
        PRINT 'Added column [ZoneId] to [dbo].[TerritoryDemarcation].';
    END;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('TerritoryDemarcation') AND name = 'ZoneCode')
    BEGIN
        ALTER TABLE [dbo].[TerritoryDemarcation] ADD [ZoneCode] NVARCHAR(50) NULL;
        CREATE NONCLUSTERED INDEX [IX_TerritoryDemarcation_ZoneCode] ON [dbo].[TerritoryDemarcation]([ZoneCode]);
        PRINT 'Added column [ZoneCode] to [dbo].[TerritoryDemarcation].';
    END;
END;
";

        try
        {
            await context.Database.ExecuteSqlRawAsync(ddl);
            logger?.LogInformation("Phase 12: DDL execution for TerritoryZone and Demarcation Zone links succeeded.");
        }
        catch (Exception ex)
        {
            logger?.LogWarning(ex, "Phase 12: Schema migration raw SQL warning (may be using in-memory provider).");
        }

        // Seed Territory Zones if empty
        if (!await context.TerritoryZones.AnyAsync())
        {
            var zones = new List<TerritoryZone>
            {
                // Gauteng South
                new TerritoryZone
                {
                    ZoneCode = "ZONE_GP_EAST_RAND",
                    ZoneName = "East Rand Heavy Engineering Zone",
                    RegionCode = "GAUTENG_SOUTH",
                    RegionName = "Gauteng South Regional Office",
                    ProvinceCode = "GP",
                    DefaultOfficerUserId = "OFFICER_GP_01",
                    DefaultOfficerName = "Thabo Mokoena",
                    DefaultOfficerEmail = "tmokoena@merseta.org.za",
                    Description = "Alrode, Germiston, Benoni, Boksburg heavy engineering and foundry hub.",
                    IsActive = true
                },
                new TerritoryZone
                {
                    ZoneCode = "ZONE_GP_VAAL",
                    ZoneName = "Vaal Triangle Industrial Zone",
                    RegionCode = "GAUTENG_SOUTH",
                    RegionName = "Gauteng South Regional Office",
                    ProvinceCode = "GP",
                    DefaultOfficerUserId = "OFFICER_GP_01",
                    DefaultOfficerName = "Thabo Mokoena",
                    DefaultOfficerEmail = "tmokoena@merseta.org.za",
                    Description = "Vereeniging and Vanderbijlpark steel fabrication, chemical and metal corridor.",
                    IsActive = true
                },
                new TerritoryZone
                {
                    ZoneCode = "ZONE_GP_CENTRAL_JHB",
                    ZoneName = "Central Johannesburg & West Rand",
                    RegionCode = "GAUTENG_SOUTH",
                    RegionName = "Gauteng South Regional Office",
                    ProvinceCode = "GP",
                    DefaultOfficerUserId = "OFFICER_GP_01",
                    DefaultOfficerName = "Thabo Mokoena",
                    DefaultOfficerEmail = "tmokoena@merseta.org.za",
                    Description = "Johannesburg central, Industria, Roodepoort and Krugersdorp manufacturing.",
                    IsActive = true
                },

                // Gauteng North
                new TerritoryZone
                {
                    ZoneCode = "ZONE_GP_TSHWANE_AUTO",
                    ZoneName = "Tshwane Automotive & Rosslyn Hub",
                    RegionCode = "GAUTENG_NORTH",
                    RegionName = "Gauteng North Regional Office",
                    ProvinceCode = "GP",
                    DefaultOfficerUserId = "OFFICER_GP_02",
                    DefaultOfficerName = "Pieter Van Der Merwe",
                    DefaultOfficerEmail = "pvandermerwe@merseta.org.za",
                    Description = "Rosslyn, Silverton, Pretoria West automotive assembly and OEM suppliers.",
                    IsActive = true
                },
                new TerritoryZone
                {
                    ZoneCode = "ZONE_GP_MIDRAND_CORRIDOR",
                    ZoneName = "Midrand & Centurion Commercial Corridor",
                    RegionCode = "GAUTENG_NORTH",
                    RegionName = "Gauteng North Regional Office",
                    ProvinceCode = "GP",
                    DefaultOfficerUserId = "OFFICER_GP_02",
                    DefaultOfficerName = "Pieter Van Der Merwe",
                    DefaultOfficerEmail = "pvandermerwe@merseta.org.za",
                    Description = "High-tech manufacturing, electronics, and technical logistics corridor.",
                    IsActive = true
                },

                // KwaZulu-Natal
                new TerritoryZone
                {
                    ZoneCode = "ZONE_KZN_ETHEKWINI_SOUTH",
                    ZoneName = "eThekwini South & Prospecton Automotive",
                    RegionCode = "KZN",
                    RegionName = "KwaZulu-Natal Regional Office",
                    ProvinceCode = "KZN",
                    DefaultOfficerUserId = "OFFICER_KZN_01",
                    DefaultOfficerName = "Nomusa Zungu",
                    DefaultOfficerEmail = "nzungu@merseta.org.za",
                    Description = "Prospecton, Jacobs, Mobeni automotive and component manufacturing cluster.",
                    IsActive = true
                },
                new TerritoryZone
                {
                    ZoneCode = "ZONE_KZN_NORTH_COAST",
                    ZoneName = "North Coast & Richards Bay Heavy Metals",
                    RegionCode = "KZN",
                    RegionName = "KwaZulu-Natal Regional Office",
                    ProvinceCode = "KZN",
                    DefaultOfficerUserId = "OFFICER_KZN_01",
                    DefaultOfficerName = "Nomusa Zungu",
                    DefaultOfficerEmail = "nzungu@merseta.org.za",
                    Description = "Richards Bay aluminium smelters, titanium mining, and deep-sea port logistics.",
                    IsActive = true
                },
                new TerritoryZone
                {
                    ZoneCode = "ZONE_KZN_MIDLANDS",
                    ZoneName = "Pietermaritzburg & KZN Midlands",
                    RegionCode = "KZN",
                    RegionName = "KwaZulu-Natal Regional Office",
                    ProvinceCode = "KZN",
                    DefaultOfficerUserId = "OFFICER_KZN_01",
                    DefaultOfficerName = "Nomusa Zungu",
                    DefaultOfficerEmail = "nzungu@merseta.org.za",
                    Description = "Pietermaritzburg, Ladysmith, Newcastle general engineering and manufacturing.",
                    IsActive = true
                },

                // Western Cape
                new TerritoryZone
                {
                    ZoneCode = "ZONE_WC_METRO_IND",
                    ZoneName = "Cape Town Metro & Marine Engineering",
                    RegionCode = "WESTERN_CAPE",
                    RegionName = "Western Cape Regional Office",
                    ProvinceCode = "WC",
                    DefaultOfficerUserId = "OFFICER_WC_01",
                    DefaultOfficerName = "David Abrahams",
                    DefaultOfficerEmail = "dabrahams@merseta.org.za",
                    Description = "Paarden Eiland, Epping, Montague Gardens, marine repair and boatbuilding.",
                    IsActive = true
                },
                new TerritoryZone
                {
                    ZoneCode = "ZONE_WC_WINELANDS",
                    ZoneName = "Winelands, Paarl & West Coast Corridor",
                    RegionCode = "WESTERN_CAPE",
                    RegionName = "Western Cape Regional Office",
                    ProvinceCode = "WC",
                    DefaultOfficerUserId = "OFFICER_WC_01",
                    DefaultOfficerName = "David Abrahams",
                    DefaultOfficerEmail = "dabrahams@merseta.org.za",
                    Description = "Agricultural equipment manufacturing, agro-processing engineering, Saldanha steel.",
                    IsActive = true
                },

                // Eastern Cape
                new TerritoryZone
                {
                    ZoneCode = "ZONE_EC_GQEBERHA_AUTO",
                    ZoneName = "Gqeberha & Kariega Automotive Hub",
                    RegionCode = "EASTERN_CAPE",
                    RegionName = "Eastern Cape Regional Office",
                    ProvinceCode = "EC",
                    DefaultOfficerUserId = "OFFICER_EC_01",
                    DefaultOfficerName = "Lindiwe Ndlovu",
                    DefaultOfficerEmail = "lndlovu@merseta.org.za",
                    Description = "Struandale, Markman, Kariega OEM automotive assembly and tyre manufacturers.",
                    IsActive = true
                },

                // Mpumalanga / Limpopo
                new TerritoryZone
                {
                    ZoneCode = "ZONE_MP_MINING_STEEL",
                    ZoneName = "Highveld Steel & Coal Corridor",
                    RegionCode = "MPUMALANGA_LIMPOPO",
                    RegionName = "Mpumalanga & Limpopo Regional Office",
                    ProvinceCode = "MP",
                    DefaultOfficerUserId = "OFFICER_MP_01",
                    DefaultOfficerName = "Sipho Mahlangu",
                    DefaultOfficerEmail = "smahlangu@merseta.org.za",
                    Description = "eMalahleni, Middelburg, Polokwane heavy mining machinery and stainless steel.",
                    IsActive = true
                }
            };

            await context.TerritoryZones.AddRangeAsync(zones);
            await context.SaveChangesAsync();
            logger?.LogInformation("Phase 12: Seeded 12 statutory industrial territory zones across 7 regions.");

            // Map existing demarcations to newly created zones
            var allDemarcations = await context.TerritoryDemarcations.ToListAsync();
            var zonesByCode = await context.TerritoryZones.ToDictionaryAsync(z => z.ZoneCode);

            foreach (var d in allDemarcations)
            {
                var townLower = d.TownName.ToLower();
                TerritoryZone? targetZone = null;

                if (townLower.Contains("germiston") || townLower.Contains("boksburg") || townLower.Contains("benoni") || townLower.Contains("alberton"))
                {
                    zonesByCode.TryGetValue("ZONE_GP_EAST_RAND", out targetZone);
                }
                else if (townLower.Contains("vereeniging") || townLower.Contains("vanderbijlpark"))
                {
                    zonesByCode.TryGetValue("ZONE_GP_VAAL", out targetZone);
                }
                else if (townLower.Contains("johannesburg") || townLower.Contains("roodepoort"))
                {
                    zonesByCode.TryGetValue("ZONE_GP_CENTRAL_JHB", out targetZone);
                }
                else if (townLower.Contains("pretoria") || townLower.Contains("rosslyn"))
                {
                    zonesByCode.TryGetValue("ZONE_GP_TSHWANE_AUTO", out targetZone);
                }
                else if (townLower.Contains("midrand") || townLower.Contains("centurion"))
                {
                    zonesByCode.TryGetValue("ZONE_GP_MIDRAND_CORRIDOR", out targetZone);
                }
                else if (townLower.Contains("durban") || townLower.Contains("pinetown"))
                {
                    zonesByCode.TryGetValue("ZONE_KZN_ETHEKWINI_SOUTH", out targetZone);
                }
                else if (townLower.Contains("richards bay") || townLower.Contains("empangeni"))
                {
                    zonesByCode.TryGetValue("ZONE_KZN_NORTH_COAST", out targetZone);
                }
                else if (townLower.Contains("pietermaritzburg") || townLower.Contains("ladysmith") || townLower.Contains("newcastle"))
                {
                    zonesByCode.TryGetValue("ZONE_KZN_MIDLANDS", out targetZone);
                }
                else if (townLower.Contains("cape town") || townLower.Contains("bellville"))
                {
                    zonesByCode.TryGetValue("ZONE_WC_METRO_IND", out targetZone);
                }
                else if (townLower.Contains("paarl") || townLower.Contains("saldanha") || townLower.Contains("stellenbosch"))
                {
                    zonesByCode.TryGetValue("ZONE_WC_WINELANDS", out targetZone);
                }
                else if (townLower.Contains("gqeberha") || townLower.Contains("kariega") || townLower.Contains("port elizabeth"))
                {
                    zonesByCode.TryGetValue("ZONE_EC_GQEBERHA_AUTO", out targetZone);
                }
                else if (townLower.Contains("emalahleni") || townLower.Contains("witbank") || townLower.Contains("middelburg") || townLower.Contains("polokwane"))
                {
                    zonesByCode.TryGetValue("ZONE_MP_MINING_STEEL", out targetZone);
                }

                if (targetZone != null)
                {
                    d.ZoneId = targetZone.Id;
                    d.ZoneCode = targetZone.ZoneCode;
                }
            }

            await context.SaveChangesAsync();
            logger?.LogInformation("Phase 12: Linked existing territory demarcations to parent territory zones.");
        }
    }
}
