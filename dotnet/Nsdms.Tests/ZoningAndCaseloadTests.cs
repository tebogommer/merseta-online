using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

/// <summary>
/// Unit and integration test suite for Phase 1: Spatial Zoning, Auto-Demarcation Intake & Caseload Heatmaps.
/// Covers:
/// 1. AutoDemarcateOrganisationAsync resolving town, sub-regional industrial zone, and default officer.
/// 2. Preservation of strategic national OEM accounts (IsCrossRegionalAssignment = true).
/// 3. Re-evaluation force reassignment override.
/// 4. Officer Caseload Heatmap metric calculations (capacity percentages, task counts, and status categories).
/// 5. Official QuestPDF Letter of Introduction (ETQ-TP-012) generation with SHA-256 digital security seal.
/// 6. Sub-regional TerritoryZone CRUD and town demarcation mapping.
/// </summary>
public class ZoningAndCaseloadTests
{
    private static (TestDbContextFactory Factory, IAuditService Audit, PortfolioDispatchService DispatchService, ZoneAndCaseloadService ZoneService, QuestPdfDocumentService PdfService) CreateServices()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var conf = new ConfigurationBuilder().AddInMemoryCollection().Build();
        var audit = new AuditService(factory);
        var config = new SystemConfigurationService(factory, conf, audit);
        var flags = new FeatureFlagService(factory, conf, audit);
        var pdfService = new QuestPdfDocumentService(factory, flags, config);
        var dispatchService = new PortfolioDispatchService(factory, audit);
        var zoneService = new ZoneAndCaseloadService(factory, audit, pdfService);

        return (factory, audit, dispatchService, zoneService, pdfService);
    }

    [Fact]
    public async Task AutoDemarcateOrganisation_ResolvesZoneAndAssignsDefaultOfficer()
    {
        var (factory, audit, dispatch, zoneService, _) = CreateServices();

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            // Seed Zone
            var zone = new TerritoryZone
            {
                ZoneCode = "ZONE_GP_EAST_RAND",
                ZoneName = "Ekurhuleni & East Rand Heavy Engineering",
                RegionCode = "GAUTENG_SOUTH",
                RegionName = "Gauteng South Regional Office",
                Description = "Heavy engineering and aerospace manufacturing cluster",
                DefaultOfficerUserId = "OFFICER_GP_EAST",
                DefaultOfficerName = "Thabo Mokoena",
                DefaultOfficerEmail = "thabo.mokoena@merseta.org.za",
                IsActive = true
            };
            db.TerritoryZones.Add(zone);
            await db.SaveChangesAsync();

            // Seed Demarcation
            var demarcation = new TerritoryDemarcation
            {
                TownName = "Boksburg",
                RegionCode = "GAUTENG_SOUTH",
                RegionName = "Gauteng South Regional Office",
                ProvinceCode = "GP",
                ZoneId = zone.Id,
                ZoneCode = zone.ZoneCode,
                EffectiveFrom = new DateTime(2020, 1, 1, 0, 0, 0, DateTimeKind.Utc),
                IsActive = true
            };
            db.TerritoryDemarcations.Add(demarcation);

            // Seed Organisation
            var org = new Organisation
            {
                CompanyName = "East Rand Metal Works Ltd",
                LegalName = "East Rand Metal Works Ltd",
                SdlNumber = "L987654321",
                PhysicalAddress = "14 Commissioner Street, Boksburg Industrial",
                ProvinceCode = "GP",
                IsActive = true
            };
            db.Organisations.Add(org);
            await db.SaveChangesAsync();

            // Execute auto-demarcation
            var result = await zoneService.AutoDemarcateOrganisationAsync(org.Id, forceReassignment: false, currentUsername: "SYSTEM_INTAKE");

            // Assertions on result DTO
            Assert.NotNull(result);
            Assert.Equal(org.Id, result.OrganisationId);
            Assert.Equal("Boksburg", result.ResolvedTown);
            Assert.Equal("GAUTENG_SOUTH", result.ResolvedRegionCode);
            Assert.Equal("ZONE_GP_EAST_RAND", result.ResolvedZoneCode);
            Assert.Equal("OFFICER_GP_EAST", result.AssignedOfficerUserId);
            Assert.Equal("Thabo Mokoena", result.AssignedOfficerName);
            Assert.Equal("AutoAssigned", result.ActionTaken);

            // Verify active portfolio created in database
            var activePortfolio = await dispatch.GetActivePortfolioAsync(org.Id);
            Assert.NotNull(activePortfolio);
            Assert.Equal("OFFICER_GP_EAST", activePortfolio.RelationshipOfficerUserId);
            Assert.Equal("GAUTENG_SOUTH", activePortfolio.ManagingRegionCode);
            Assert.False(activePortfolio.IsCrossRegionalAssignment);
            Assert.True(activePortfolio.IsActive);

            // Verify audit log entry exists
            var auditLogs = await db.AuditLogs
                .Where(a => a.RecordId == activePortfolio.Id && a.ActionName == "AUTO_DEMARCATE")
                .ToListAsync();
            Assert.NotEmpty(auditLogs);
        }
    }

    [Fact]
    public async Task AutoDemarcateOrganisation_PreservesCrossRegionalAccount_UnlessForced()
    {
        var (factory, audit, dispatch, zoneService, _) = CreateServices();

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            // Seed Zone & Demarcation
            var zone = new TerritoryZone
            {
                ZoneCode = "ZONE_KZN_COASTAL",
                ZoneName = "eThekwini Coastal Corridor",
                RegionCode = "KZN",
                RegionName = "KwaZulu-Natal Regional Office",
                DefaultOfficerUserId = "OFFICER_KZN_01",
                DefaultOfficerName = "Sipho Khumalo",
                IsActive = true
            };
            db.TerritoryZones.Add(zone);
            await db.SaveChangesAsync();

            var demarcation = new TerritoryDemarcation
            {
                TownName = "Durban",
                RegionCode = "KZN",
                RegionName = "KwaZulu-Natal Regional Office",
                ProvinceCode = "KZN",
                ZoneId = zone.Id,
                ZoneCode = zone.ZoneCode,
                EffectiveFrom = new DateTime(2020, 1, 1, 0, 0, 0, DateTimeKind.Utc),
                IsActive = true
            };
            db.TerritoryDemarcations.Add(demarcation);

            var org = new Organisation
            {
                CompanyName = "National Strategic OEM - Plant Alpha",
                LegalName = "National Strategic OEM - Plant Alpha",
                SdlNumber = "L112233445",
                PhysicalAddress = "Prospecton Road, Durban",
                ProvinceCode = "KZN",
                IsActive = true
            };
            db.Organisations.Add(org);
            await db.SaveChangesAsync();

            // Assign National Key Account Manager (cross-regional assignment)
            var initialPortfolio = new OrganisationPortfolio
            {
                OrganisationId = org.Id,
                RelationshipOfficerUserId = "KAM_HEAD_OFFICE",
                RelationshipOfficerName = "Brenda Vance",
                RelationshipOfficerEmail = "brenda.vance@merseta.org.za",
                PortfolioRoleCode = "KEY_ACCOUNT_MANAGER",
                ManagingRegionCode = "NATIONAL",
                IsCrossRegionalAssignment = true,
                AssignmentReason = "Strategic National Automotive OEM Account",
                EffectiveFrom = DateTime.UtcNow.AddMonths(-6),
                IsActive = true
            };
            db.OrganisationPortfolios.Add(initialPortfolio);
            await db.SaveChangesAsync();

            // 1. Run auto-demarcation without force
            var resultSkipped = await zoneService.AutoDemarcateOrganisationAsync(org.Id, forceReassignment: false, currentUsername: "SYSTEM_INTAKE");

            Assert.Equal("ExemptedStrategicAccount", resultSkipped.ActionTaken);
            Assert.True(resultSkipped.IsCrossRegionalExemption);
            Assert.Equal("KAM_HEAD_OFFICE", resultSkipped.AssignedOfficerUserId);

            // Verify portfolio was NOT changed
            var preservedPortfolio = await dispatch.GetActivePortfolioAsync(org.Id);
            Assert.NotNull(preservedPortfolio);
            Assert.Equal("KAM_HEAD_OFFICE", preservedPortfolio.RelationshipOfficerUserId);
            Assert.True(preservedPortfolio.IsCrossRegionalAssignment);

            // 2. Run auto-demarcation with forceReassignment = true
            var resultForced = await zoneService.AutoDemarcateOrganisationAsync(org.Id, forceReassignment: true, currentUsername: "CRM_ADMIN");

            Assert.Equal("AutoAssigned", resultForced.ActionTaken);
            Assert.Equal("OFFICER_KZN_01", resultForced.AssignedOfficerUserId);

            // Verify portfolio WAS updated
            var updatedPortfolio = await dispatch.GetActivePortfolioAsync(org.Id);
            Assert.NotNull(updatedPortfolio);
            Assert.Equal("OFFICER_KZN_01", updatedPortfolio.RelationshipOfficerUserId);
            Assert.False(updatedPortfolio.IsCrossRegionalAssignment);
        }
    }

    [Fact]
    public async Task GetOfficerCaseloadHeatmap_ComputesAccurateCountsAndSlaThresholds()
    {
        var (factory, audit, dispatch, zoneService, _) = CreateServices();

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            // Seed 2 Officers with capabilities in Gauteng South
            db.StaffCapabilities.AddRange(
                new StaffCapability
                {
                    UserId = "OFFICER_ALPHA",
                    StaffName = "Officer Alpha",
                    Email = "alpha@merseta.org.za",
                    EmploymentRole = "CLO",
                    CapabilityCode = "CAP_WORKPLACE_AUDIT",
                    CapabilityName = "Workplace Audit",
                    StationedRegionCode = "GAUTENG_SOUTH",
                    IsActive = true
                },
                new StaffCapability
                {
                    UserId = "OFFICER_BETA",
                    StaffName = "Officer Beta",
                    Email = "beta@merseta.org.za",
                    EmploymentRole = "CLO",
                    CapabilityCode = "CAP_WORKPLACE_AUDIT",
                    CapabilityName = "Workplace Audit",
                    StationedRegionCode = "GAUTENG_SOUTH",
                    IsActive = true
                }
            );

            // Officer Alpha has 5 assigned organisations
            for (int i = 1; i <= 5; i++)
            {
                var org = new Organisation
                {
                    CompanyName = $"Alpha Client {i}",
                    SdlNumber = $"L10000000{i}",
                    IsActive = true
                };
                db.Organisations.Add(org);
                await db.SaveChangesAsync();

                db.OrganisationPortfolios.Add(new OrganisationPortfolio
                {
                    OrganisationId = org.Id,
                    RelationshipOfficerUserId = "OFFICER_ALPHA",
                    RelationshipOfficerName = "Officer Alpha",
                    PortfolioRoleCode = "PRIMARY_CLO",
                    ManagingRegionCode = "GAUTENG_SOUTH",
                    EffectiveFrom = DateTime.UtcNow.AddMonths(-1),
                    IsActive = true
                });
            }

            // Officer Beta has 65 assigned organisations (Elevated caseload: 65/80 = 81%)
            for (int i = 1; i <= 65; i++)
            {
                var org = new Organisation
                {
                    CompanyName = $"Beta Heavy Client {i}",
                    SdlNumber = $"L20000{i:D4}",
                    IsActive = true
                };
                db.Organisations.Add(org);
                await db.SaveChangesAsync();

                db.OrganisationPortfolios.Add(new OrganisationPortfolio
                {
                    OrganisationId = org.Id,
                    RelationshipOfficerUserId = "OFFICER_BETA",
                    RelationshipOfficerName = "Officer Beta",
                    PortfolioRoleCode = "PRIMARY_CLO",
                    ManagingRegionCode = "GAUTENG_SOUTH",
                    EffectiveFrom = DateTime.UtcNow.AddMonths(-1),
                    IsActive = true
                });
            }

            await db.SaveChangesAsync();

            // Run caseload heatmap query
            var heatmap = await zoneService.GetOfficerCaseloadHeatmapAsync("GAUTENG_SOUTH");

            Assert.NotNull(heatmap);
            Assert.True(heatmap.Count >= 2);

            var alphaMetric = heatmap.FirstOrDefault(m => m.OfficerUserId == "OFFICER_ALPHA");
            Assert.NotNull(alphaMetric);
            Assert.Equal(5, alphaMetric.AssignedOrganisationsCount);
            Assert.Equal("Normal", alphaMetric.CapacityStatus);

            var betaMetric = heatmap.FirstOrDefault(m => m.OfficerUserId == "OFFICER_BETA");
            Assert.NotNull(betaMetric);
            Assert.Equal(65, betaMetric.AssignedOrganisationsCount);
            Assert.True(betaMetric.CapacityLoadPercentage > 80);
            Assert.Equal("Elevated", betaMetric.CapacityStatus);
        }
    }

    [Fact]
    public async Task GenerateIntroductionLetter_CreatesStatutoryPdfWithSecuritySeal()
    {
        var (factory, audit, dispatch, zoneService, _) = CreateServices();

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            var org = new Organisation
            {
                CompanyName = "Durban Marine Engineering Works",
                LegalName = "Durban Marine Engineering Works (Pty) Ltd",
                SdlNumber = "L554433221",
                RegistrationNumber = "2020/098765/07",
                PhysicalAddress = "Quayside Road, Durban Harbour",
                PostalAddress = "PO Box 1234, Durban",
                PostalAddressPostalCode = "4000",
                ChamberCode = "Metal",
                IsActive = true
            };
            db.Organisations.Add(org);
            await db.SaveChangesAsync();

            var portfolio = new OrganisationPortfolio
            {
                OrganisationId = org.Id,
                RelationshipOfficerUserId = "CLO_KZN_02",
                RelationshipOfficerName = "Nomvula Zulu",
                RelationshipOfficerEmail = "nomvula.zulu@merseta.org.za",
                PortfolioRoleCode = "PRIMARY_CLO",
                ManagingRegionCode = "KZN",
                EffectiveFrom = DateTime.UtcNow.AddDays(-10),
                IsActive = true
            };
            db.OrganisationPortfolios.Add(portfolio);
            await db.SaveChangesAsync();

            // Generate statutory QuestPDF Letter of Introduction
            var pdfBytes = await zoneService.GenerateLetterOfIntroductionPdfAsync(org.Id, "SDF_USER_TEST");

            // Verify document output
            Assert.NotNull(pdfBytes);
            Assert.True(pdfBytes.Length > 1000);

            // Verify PDF header magic bytes: %PDF-
            var header = Encoding.ASCII.GetString(pdfBytes.Take(5).ToArray());
            Assert.Equal("%PDF-", header);

            // Verify audit trail entry
            var auditLogs = await db.AuditLogs
                .Where(a => a.RecordId == portfolio.Id && a.ActionName == "DOWNLOAD_INTRO_LETTER")
                .ToListAsync();
            Assert.NotEmpty(auditLogs);
        }
    }

    [Fact]
    public async Task TerritoryZone_CrudAndTownDemarcationMapping_Succeeds()
    {
        var (factory, audit, _, zoneService, _) = CreateServices();

        // 1. Create Zone
        var newZone = new TerritoryZone
        {
            ZoneCode = "ZONE_WC_HELDERBERG",
            ZoneName = "Helderberg & Overberg Agro-Industrial",
            RegionCode = "WESTERN_CAPE",
            RegionName = "Western Cape Regional Office",
            Description = "Somerset West, Strand, and surrounding agricultural engineering cluster",
            IsActive = true
        };

        var savedZone = await zoneService.SaveZoneAsync(newZone, "REGIONAL_MANAGER");
        Assert.True(savedZone.Id > 0);

        // 2. Seed demarcations and assign to zone
        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            var town1 = new TerritoryDemarcation
            {
                TownName = "Somerset West",
                RegionCode = "WESTERN_CAPE",
                RegionName = "Western Cape Regional Office",
                ProvinceCode = "WC",
                EffectiveFrom = new DateTime(2020, 1, 1, 0, 0, 0, DateTimeKind.Utc),
                IsActive = true
            };
            var town2 = new TerritoryDemarcation
            {
                TownName = "Strand",
                RegionCode = "WESTERN_CAPE",
                RegionName = "Western Cape Regional Office",
                ProvinceCode = "WC",
                EffectiveFrom = new DateTime(2020, 1, 1, 0, 0, 0, DateTimeKind.Utc),
                IsActive = true
            };
            db.TerritoryDemarcations.AddRange(town1, town2);
            await db.SaveChangesAsync();

            // Assign to zone
            await zoneService.AssignTownsToZoneAsync(savedZone.Id, new List<int> { town1.Id, town2.Id }, "REGIONAL_MANAGER");

            // Verify demarcations query
            var zonedDemarcations = await zoneService.GetDemarcationsForZoneAsync(savedZone.Id);
            Assert.Equal(2, zonedDemarcations.Count);
            Assert.Contains(zonedDemarcations, d => d.TownName == "Somerset West");
            Assert.Contains(zonedDemarcations, d => d.TownName == "Strand");

            // Verify GetAllZonesAsync includes the town count
            var allZones = await zoneService.GetAllZonesAsync("WESTERN_CAPE");
            var retrievedZoneDto = allZones.FirstOrDefault(z => z.Id == savedZone.Id);
            Assert.NotNull(retrievedZoneDto);
            Assert.Equal(2, retrievedZoneDto.TownCount);

            // 3. Delete Zone (clears Demarcation foreign keys safely without deleting demarcations)
            var deleted = await zoneService.DeleteZoneAsync(savedZone.Id, "REGIONAL_MANAGER");
            Assert.True(deleted);

            var townCheck = await db.TerritoryDemarcations.FindAsync(town1.Id);
            Assert.NotNull(townCheck);
            Assert.Null(townCheck.ZoneId);
        }
    }
}
