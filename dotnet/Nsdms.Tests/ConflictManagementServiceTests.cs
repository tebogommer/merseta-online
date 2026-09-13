using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class ConflictManagementServiceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, ISystemConfigurationService config, ConflictManagementService service) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var conf = new ConfigurationBuilder().Build();
        ISystemConfigurationService config = new SystemConfigurationService(factory, conf, audit);
        var service = new ConflictManagementService(factory, config, audit);

        return (factory, db, audit, config, service);
    }

    [Fact]
    public async Task ValidateGovernance_NoDirectors_FailsValidation()
    {
        var (_, db, _, _, service) = CreateTestContext();
        var org = new Organisation
        {
            CompanyName = "Apex Fabrication (Pty) Ltd",
            TradingName = "Apex Fab",
            RegistrationNumber = "2020/123456/07",
            SdlNumber = "L100200300",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var result = await service.ValidateOrganisationGovernanceComplianceAsync(org.Id, isAccreditationContext: false);

        Assert.False(result.IsCompliant);
        Assert.Contains(result.Violations, e => e.Contains("Director", StringComparison.OrdinalIgnoreCase));
    }

    [Fact]
    public async Task ValidateGovernance_AccreditationContext_RequiresIdVerifiedAndCipcRegistered()
    {
        var (_, db, _, _, service) = CreateTestContext();
        var org = new Organisation
        {
            CompanyName = "Delta Tech Training Institute",
            RegistrationNumber = "2019/888999/07",
            SdlNumber = "L999888777",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.Organisations.Add(org);

        var person = new Person
        {
            FirstName = "Sipho",
            LastName = "Khumalo",
            RsaIdNumber = "8501015009087",
            EmailAddress = "sipho.khumalo@deltatech.co.za",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var member = new OrganisationGovernanceMember
        {
            OrganisationId = org.Id,
            PersonId = person.Id,
            GovernanceRoleCode = "DIRECTOR",
            ShareholdingPercentage = 100.00m,
            HasVotingRights = true,
            CipcRegistered = false,
            IdVerified = false,
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.OrganisationGovernanceMembers.Add(member);
        await db.SaveChangesAsync();

        var generalResult = await service.ValidateOrganisationGovernanceComplianceAsync(org.Id, isAccreditationContext: false);
        var accreditResult = await service.ValidateOrganisationGovernanceComplianceAsync(org.Id, isAccreditationContext: true);

        Assert.True(generalResult.IsCompliant);
        Assert.False(accreditResult.IsCompliant);
        Assert.Contains(accreditResult.Violations, e => e.Contains("Accreditation requirement", StringComparison.OrdinalIgnoreCase));
    }

    [Fact]
    public async Task ValidateGovernance_ConfigurableShareholdingThreshold_EnforcesDisclosure()
    {
        var (_, db, _, config, service) = CreateTestContext();
        await config.SetValueAsync("Governance:MinimumShareholdingDisclosureThresholdPercent", "10.00", "System");

        var org = new Organisation
        {
            CompanyName = "Precision Dynamics (Pty) Ltd",
            RegistrationNumber = "2021/444555/07",
            SdlNumber = "L444555666",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.Organisations.Add(org);

        var director = new Person
        {
            FirstName = "Johan",
            LastName = "Botha",
            RsaIdNumber = "7803155008081",
            EmailAddress = "johan@precision.co.za",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.People.Add(director);
        await db.SaveChangesAsync();

        db.OrganisationGovernanceMembers.Add(new OrganisationGovernanceMember
        {
            OrganisationId = org.Id,
            PersonId = director.Id,
            GovernanceRoleCode = "DIRECTOR",
            ShareholdingPercentage = 100.00m,
            HasVotingRights = true,
            CipcRegistered = true,
            IdVerified = true,
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        });
        await db.SaveChangesAsync();

        var result = await service.ValidateOrganisationGovernanceComplianceAsync(org.Id, isAccreditationContext: false);

        Assert.True(result.IsCompliant);
        Assert.Equal(10.00m, result.MinimumThresholdPercentEnforced);
    }

    [Fact]
    public async Task EvaluateOrganisationConflicts_InsiderHoldingSharesInApplicant_GeneratesCriticalFlag()
    {
        var (_, db, _, _, service) = CreateTestContext();

        var person = new Person
        {
            FirstName = "Nombuso",
            LastName = "Dlamini",
            RsaIdNumber = "8805120123084",
            EmailAddress = "nombusod@merseta.org.za",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.People.Add(person);

        var org = new Organisation
        {
            CompanyName = "Starlight Engineering Services",
            RegistrationNumber = "2018/777888/07",
            SdlNumber = "L777888999",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        db.InstitutionalAffiliations.Add(new InstitutionalAffiliation
        {
            PersonId = person.Id,
            AffiliationTypeCode = "EMPLOYEE",
            DepartmentOrCommittee = "Grants & Levies Division",
            Designation = "Senior Grant Evaluation Specialist",
            EmployeeNumber = "MS-EMP-2041",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        });

        db.OrganisationGovernanceMembers.Add(new OrganisationGovernanceMember
        {
            OrganisationId = org.Id,
            PersonId = person.Id,
            GovernanceRoleCode = "SHAREHOLDER",
            ShareholdingPercentage = 35.00m,
            HasVotingRights = true,
            CipcRegistered = true,
            IdVerified = true,
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        });

        var grantApp = new GrantApplication
        {
            OrganisationId = org.Id,
            ApplicationNumber = $"DG-{DateTime.UtcNow.Year}-STARLIGHT-01",
            RequestedAmount = 750000.00m,
            ApplicationStatusCode = "Submitted",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "Applicant"
        };
        db.GrantApplications.Add(grantApp);
        await db.SaveChangesAsync();

        var flags = await service.EvaluateOrganisationConflictsAsync(org.Id);

        Assert.NotEmpty(flags);
        var criticalFlag = flags.FirstOrDefault(f => f.SeverityCode == "RED_CRITICAL");
        Assert.NotNull(criticalFlag);
        Assert.Equal("INSIDER_AFFILIATION", criticalFlag.ConflictCategoryCode);
        Assert.Equal("OPEN", criticalFlag.ResolutionStatusCode);
        Assert.Contains("PFMA Section 50/51", criticalFlag.Description);
        Assert.Equal(org.Id, criticalFlag.TargetOrganisationId);
    }

    [Fact]
    public async Task EvaluateGrantApplicationConflicts_CommonDirectorInMultipleApplicants_GeneratesElevatedFlag()
    {
        var (_, db, _, _, service) = CreateTestContext();

        var person = new Person
        {
            FirstName = "Kagiso",
            LastName = "Mokoena",
            RsaIdNumber = "7911035002081",
            EmailAddress = "kmokoena@holdings.co.za",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.People.Add(person);

        var org1 = new Organisation
        {
            CompanyName = "AutoTech Assemblies (Pty) Ltd",
            RegistrationNumber = "2015/111222/07",
            SdlNumber = "L111222333",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        var org2 = new Organisation
        {
            CompanyName = "Precision Tooling Solutions (Pty) Ltd",
            RegistrationNumber = "2016/333444/07",
            SdlNumber = "L333444555",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.Organisations.AddRange(org1, org2);
        await db.SaveChangesAsync();

        db.OrganisationGovernanceMembers.Add(new OrganisationGovernanceMember
        {
            OrganisationId = org1.Id,
            PersonId = person.Id,
            GovernanceRoleCode = "DIRECTOR",
            ShareholdingPercentage = 50.00m,
            HasVotingRights = true,
            CipcRegistered = true,
            IdVerified = true,
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        });
        db.OrganisationGovernanceMembers.Add(new OrganisationGovernanceMember
        {
            OrganisationId = org2.Id,
            PersonId = person.Id,
            GovernanceRoleCode = "DIRECTOR",
            ShareholdingPercentage = 50.00m,
            HasVotingRights = true,
            CipcRegistered = true,
            IdVerified = true,
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        });

        var app1 = new GrantApplication
        {
            OrganisationId = org1.Id,
            ApplicationNumber = $"DG-{DateTime.UtcNow.Year}-AUTOTECH-01",
            RequestedAmount = 1200000.00m,
            ApplicationStatusCode = "Submitted",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "Applicant"
        };
        var app2 = new GrantApplication
        {
            OrganisationId = org2.Id,
            ApplicationNumber = $"DG-{DateTime.UtcNow.Year}-PRECTOOL-01",
            RequestedAmount = 980000.00m,
            ApplicationStatusCode = "Submitted",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "Applicant"
        };
        db.GrantApplications.AddRange(app1, app2);
        await db.SaveChangesAsync();

        var flags = await service.EvaluateGrantApplicationConflictsAsync(app1.Id);

        Assert.NotEmpty(flags);
        var syndicateFlag = flags.FirstOrDefault(f => f.ConflictCategoryCode == "MULTI_ORGANISATION_GRANT_SYNDICATE");
        Assert.NotNull(syndicateFlag);
        Assert.Equal("AMBER_ELEVATED", syndicateFlag.SeverityCode);
    }

    [Fact]
    public async Task ResolveConflictFlag_ConfigurableClearanceAuthority_EnforcesAuthorization()
    {
        var (_, db, _, config, service) = CreateTestContext();

        await config.SetValueAsync("Governance:ConflictClearanceAuthority", "Internal Audit", "System");

        var flag = new ConflictFlag
        {
            SeverityCode = "AMBER_ELEVATED",
            ConflictCategoryCode = "MULTI_ORGANISATION_GRANT_SYNDICATE",
            Title = "Cross Directorship Disclosure",
            Description = "Director holds position on advisory council.",
            ResolutionStatusCode = "OPEN",
            DetectedAt = DateTime.UtcNow,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.ConflictFlags.Add(flag);
        await db.SaveChangesAsync();

        var ex = await Assert.ThrowsAsync<UnauthorizedAccessException>(() =>
            service.ResolveConflictFlagAsync(
                flag.Id,
                "CLEARED_WITH_JUSTIFICATION",
                "Self-clearing is not allowed",
                "Grant Liaison Officer",
                "officer1"));

        Assert.Contains("not configured as an authorized statutory clearance authority", ex.Message);

        var resolved = await service.ResolveConflictFlagAsync(
            flag.Id,
            "CLEARED_WITH_JUSTIFICATION",
            "Recusal protocol verified and registered in audit records.",
            "Internal Audit",
            "auditor1");

        Assert.Equal("CLEARED_WITH_JUSTIFICATION", resolved.ResolutionStatusCode);
        Assert.Equal("auditor1", resolved.ClearedByUserId);
        Assert.Equal("Internal Audit", resolved.ClearanceAuthorityRole);
        Assert.NotNull(resolved.ClearedAt);
    }

    [Fact]
    public async Task InterestDeclaration_SubmitAndCertify_GeneratesDigitalSecuritySeal()
    {
        var (_, db, _, _, service) = CreateTestContext();

        var person = new Person
        {
            FirstName = "Lindiwe",
            LastName = "Zulu",
            RsaIdNumber = "8204040123089",
            EmailAddress = "lzulu@merseta.org.za",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var declaration = new InterestDeclaration
        {
            PersonId = person.Id,
            DeclarationPeriodYear = DateTime.UtcNow.Year.ToString(),
            DeclarationTypeCode = "ANNUAL_COMPLIANCE",
            HasConflictsToDeclare = true,
            StatusCode = "DRAFT",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "lzulu"
        };
        var items = new List<InterestDeclarationItem>
        {
            new InterestDeclarationItem
            {
                OrganisationName = "Vaal Logistics CC",
                RegistrationOrSdlNumber = "2010/012345/23",
                NatureOfRelationship = "Spouse is Managing Member",
                InterestPercentage = 50.00m,
                AnnualRemunerationOrBenefit = 0.00m,
                IsApprovedExternalWork = true,
                ApprovalReference = "RWOPS-2026-042",
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "lzulu"
            }
        };

        var created = await service.SubmitDeclarationAsync(declaration, items, "lzulu");
        Assert.Equal("FLAGGED_CONFLICT", created.StatusCode);

        var certified = await service.CertifyDeclarationAsync(created.Id, "lzulu");

        Assert.Equal("CERTIFIED", certified.StatusCode);
        Assert.NotNull(certified.CertifiedAt);
        Assert.Equal("lzulu", certified.CertifiedByUserId);
        Assert.False(string.IsNullOrWhiteSpace(certified.DigitalSignatureSeal));
        Assert.Equal(64, certified.DigitalSignatureSeal.Length);
    }

    [Fact]
    public async Task ConflictDashboardAndReporting_ReturnsAccurateMetrics()
    {
        var (_, db, _, _, service) = CreateTestContext();

        var person = new Person
        {
            FirstName = "Governance",
            LastName = "TestSubject",
            RsaIdNumber = "7501015009087",
            EmailAddress = "gov@merseta.org.za",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.People.Add(person);
        await db.SaveChangesAsync();

        db.ConflictFlags.AddRange(
            new ConflictFlag
            {
                PersonId = person.Id,
                SeverityCode = "RED_CRITICAL",
                ConflictCategoryCode = "INSIDER_AFFILIATION",
                Title = "Insider Directorship",
                Description = "PFMA Section 50 violation",
                ResolutionStatusCode = "OPEN",
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "System"
            },
            new ConflictFlag
            {
                PersonId = person.Id,
                SeverityCode = "AMBER_ELEVATED",
                ConflictCategoryCode = "MULTI_ORGANISATION_GRANT_SYNDICATE",
                Title = "Syndicate 1",
                Description = "Common director",
                ResolutionStatusCode = "OPEN",
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "System"
            },
            new ConflictFlag
            {
                PersonId = person.Id,
                SeverityCode = "YELLOW_ADVISORY",
                ConflictCategoryCode = "COMMITTEE_RECUSAL_REQUIRED",
                Title = "Advisory",
                Description = "Minor disclosure",
                ResolutionStatusCode = "CLEARED_WITH_JUSTIFICATION",
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "System"
            }
        );
        await db.SaveChangesAsync();

        var metrics = await service.GetDashboardMetricsAsync();
        var report = await service.GenerateAgsConflictReportAsync(DateTime.UtcNow.Year.ToString());

        Assert.Equal(2, metrics.TotalActiveFlags);
        Assert.Equal(1, metrics.RedCriticalCount);
        Assert.Equal(1, metrics.AmberElevatedCount);
        Assert.Equal(1, metrics.ClearedFlagsCount);
        Assert.Equal(3, report.Count);
    }
}


