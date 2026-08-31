using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class MentorRatioPolicyEngineTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, SystemConfigurationService sysConfig, MentorRatioPolicyEngine engine) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var config = new ConfigurationBuilder().AddInMemoryCollection(new Dictionary<string, string?>()).Build();
        var sysConfig = new SystemConfigurationService(factory, config, audit);
        var engine = new MentorRatioPolicyEngine(factory, audit, sysConfig);

        // Seed basic trade policies
        db.TradeMentorRatioPolicies.AddRange(
            new TradeMentorRatioPolicy
            {
                TradeCode = "ELEC",
                TradeTitle = "Electrician",
                StandardRatio = 2,
                MaxAllowedRatio = 4,
                MinExperienceYearsRequired = 3,
                EnforceStrictly = true,
                IsActive = true
            },
            new TradeMentorRatioPolicy
            {
                TradeCode = "WELD",
                TradeTitle = "Welder",
                StandardRatio = 3,
                MaxAllowedRatio = 5,
                MinExperienceYearsRequired = 2,
                EnforceStrictly = true,
                IsActive = true
            },
            new TradeMentorRatioPolicy
            {
                TradeCode = "GENERIC",
                TradeTitle = "Generic Artisan Trade",
                StandardRatio = 4,
                MaxAllowedRatio = 6,
                MinExperienceYearsRequired = 1,
                EnforceStrictly = false,
                IsActive = true
            }
        );
        db.SaveChanges();

        return (factory, db, audit, sysConfig, engine);
    }

    [Fact]
    public async Task EvaluateCapacity_StandardTrade_CalculatesCorrectCapacity()
    {
        var (factory, db, audit, sysConfig, engine) = CreateTestContext();

        var org = new Organisation { CompanyName = "Bell Equipment", SdlNumber = "L100200300" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var person1 = new Person { FirstName = "John", LastName = "Smith", RsaIdNumber = "8001015009087" };
        var person2 = new Person { FirstName = "David", LastName = "Ndlovu", RsaIdNumber = "8502025009088" };
        db.People.AddRange(person1, person2);
        await db.SaveChangesAsync();

        var approval = new WorkplaceApproval
        {
            OrganisationId = org.Id,
            TradeCode = "WELD", // Welder Standard Ratio = 3
            QualificationTitle = "Occupational Certificate: Welder",
            ApprovalStatusCode = "APPROVED",
            IsActive = true,
            Mentors = new List<WorkplaceApprovalMentor>
            {
                new() { PersonId = person1.Id, Designation = "Lead Welder", YearsExperience = 5, IsCertifiedArtisan = true, IsActive = true },
                new() { PersonId = person2.Id, Designation = "Master Artisan", YearsExperience = 8, IsCertifiedArtisan = true, IsActive = true }
            }
        };
        db.WorkplaceApprovals.Add(approval);
        await db.SaveChangesAsync();

        var result = await engine.EvaluateWorkplaceApprovalCapacityAsync(approval.Id);

        Assert.True(result.IsEnforcementActive);
        Assert.Equal("WELD", result.TradeCode);
        Assert.Equal(3, result.StandardTradeRatio);
        Assert.Equal(2, result.TotalActiveMentors);
        Assert.Equal(6, result.EffectiveTotalCapacity); // 2 mentors * 3 learners = 6 capacity
        Assert.Equal(MentorRatioComplianceStatus.Compliant, result.Status);
    }

    [Fact]
    public async Task EvaluateCapacity_GlobalDisabled_ReturnsExemptState()
    {
        var (factory, db, audit, sysConfig, engine) = CreateTestContext();

        // Turn off global enforcement
        await engine.SetGlobalRatioEnforcementAsync(false, "TESTADMIN");

        var org = new Organisation { CompanyName = "Denel Dynamics", SdlNumber = "L200300400" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var approval = new WorkplaceApproval
        {
            OrganisationId = org.Id,
            TradeCode = "ELEC",
            QualificationTitle = "Electrician",
            ApprovalStatusCode = "APPROVED",
            IsActive = true
        };
        db.WorkplaceApprovals.Add(approval);
        await db.SaveChangesAsync();

        var result = await engine.EvaluateWorkplaceApprovalCapacityAsync(approval.Id);

        Assert.False(result.IsEnforcementActive);
        Assert.Equal(MentorRatioEnforcementState.GlobalDisabled, result.EnforcementState);
        Assert.Equal(MentorRatioComplianceStatus.Exempt, result.Status);
    }

    [Fact]
    public async Task EvaluateCapacity_OrganisationExemption_OverridesTradePolicy()
    {
        var (factory, db, audit, sysConfig, engine) = CreateTestContext();

        var org = new Organisation
        {
            CompanyName = "Transnet Engineering",
            SdlNumber = "L300400500",
            IsMentorRatioEnforced = false,
            MentorRatioExemptionReason = "Section 28 National Rail Apprenticeship Dispensation"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var approval = new WorkplaceApproval
        {
            OrganisationId = org.Id,
            TradeCode = "ELEC", // 1:2
            QualificationTitle = "Electrician",
            ApprovalStatusCode = "APPROVED",
            IsActive = true
        };
        db.WorkplaceApprovals.Add(approval);
        await db.SaveChangesAsync();

        var result = await engine.EvaluateWorkplaceApprovalCapacityAsync(approval.Id);

        Assert.False(result.IsEnforcementActive);
        Assert.Equal(MentorRatioEnforcementState.OrgExempt, result.EnforcementState);
        Assert.Equal(MentorRatioComplianceStatus.Exempt, result.Status);
        Assert.Contains("Section 28", result.ExemptionReason);
    }

    [Fact]
    public async Task EvaluateCapacity_WorkplaceCustomRatio_OverridesStandardRatio()
    {
        var (factory, db, audit, sysConfig, engine) = CreateTestContext();

        var org = new Organisation { CompanyName = "BMW South Africa", SdlNumber = "L400500600" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var person1 = new Person { FirstName = "Peter", LastName = "Botha", RsaIdNumber = "7803035009089" };
        db.People.Add(person1);
        await db.SaveChangesAsync();

        var approval = new WorkplaceApproval
        {
            OrganisationId = org.Id,
            TradeCode = "ELEC", // Standard is 1:2
            CustomTradeRatio = 5, // Site override is 1:5
            QualificationTitle = "Electrician",
            ApprovalStatusCode = "APPROVED",
            IsActive = true,
            Mentors = new List<WorkplaceApprovalMentor>
            {
                new() { PersonId = person1.Id, Designation = "Lead Electrician", YearsExperience = 10, IsCertifiedArtisan = true, IsActive = true }
            }
        };
        db.WorkplaceApprovals.Add(approval);
        await db.SaveChangesAsync();

        var result = await engine.EvaluateWorkplaceApprovalCapacityAsync(approval.Id);

        Assert.True(result.IsEnforcementActive);
        Assert.Equal(2, result.StandardTradeRatio); // Standard ELEC trade policy is 1:2
        Assert.Equal(5, result.EffectiveTotalCapacity); // 1 mentor * custom ratio 5 = 5
    }

    [Fact]
    public async Task EvaluateCapacity_MentorSpecificOverride_CalculatesCustomQuota()
    {
        var (factory, db, audit, sysConfig, engine) = CreateTestContext();

        var org = new Organisation { CompanyName = "Ford Motor Company", SdlNumber = "L500600700" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var p1 = new Person { FirstName = "Mentor1", LastName = "Custom", RsaIdNumber = "7501015009081" };
        var p2 = new Person { FirstName = "Mentor2", LastName = "Default", RsaIdNumber = "7602025009082" };
        db.People.AddRange(p1, p2);
        await db.SaveChangesAsync();

        var approval = new WorkplaceApproval
        {
            OrganisationId = org.Id,
            TradeCode = "WELD", // Standard = 3
            QualificationTitle = "Welder",
            ApprovalStatusCode = "APPROVED",
            IsActive = true,
            Mentors = new List<WorkplaceApprovalMentor>
            {
                new() { PersonId = p1.Id, Designation = "Senior Mentor", YearsExperience = 15, IsCertifiedArtisan = true, MaxLearnerCapacity = 7, IsActive = true }, // Custom = 7
                new() { PersonId = p2.Id, Designation = "Junior Mentor", YearsExperience = 4, IsCertifiedArtisan = true, MaxLearnerCapacity = null, IsActive = true }  // Default = 3
            }
        };
        db.WorkplaceApprovals.Add(approval);
        await db.SaveChangesAsync();

        var result = await engine.EvaluateWorkplaceApprovalCapacityAsync(approval.Id);

        Assert.Equal(2, result.TotalActiveMentors);
        Assert.Equal(10, result.EffectiveTotalCapacity); // 7 + 3 = 10
    }

    [Fact]
    public async Task EvaluatePlacementFeasibility_DetectsOverCapacity()
    {
        var (factory, db, audit, sysConfig, engine) = CreateTestContext();

        var org = new Organisation { CompanyName = "ArcelorMittal", SdlNumber = "L600700800" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var p1 = new Person { FirstName = "Artisan1", LastName = "Elec", RsaIdNumber = "7701015009083" };
        db.People.Add(p1);
        await db.SaveChangesAsync();

        var approval = new WorkplaceApproval
        {
            OrganisationId = org.Id,
            TradeCode = "ELEC", // Standard 2
            QualificationTitle = "Electrician",
            ApprovalStatusCode = "APPROVED",
            IsActive = true,
            Mentors = new List<WorkplaceApprovalMentor>
            {
                new() { PersonId = p1.Id, Designation = "Electrician", YearsExperience = 6, IsCertifiedArtisan = true, IsActive = true } // Capacity = 2
            }
        };
        db.WorkplaceApprovals.Add(approval);
        await db.SaveChangesAsync();

        // Placing 3 additional learners when capacity is only 2
        var feasibility = await engine.EvaluatePlacementFeasibilityAsync(approval.Id, additionalLearners: 3);

        Assert.Equal(MentorRatioComplianceStatus.OverCapacity, feasibility.Status);
        Assert.Equal(2, feasibility.EffectiveTotalCapacity);
        Assert.Equal(3, feasibility.TotalPlacedLearners);
        Assert.True(feasibility.Warnings.Any());
    }
}
