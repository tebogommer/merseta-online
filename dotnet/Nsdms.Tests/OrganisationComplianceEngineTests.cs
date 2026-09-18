using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class OrganisationComplianceEngineTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, OrganisationComplianceEngine engine) CreateTestEngine()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var engine = new OrganisationComplianceEngine(factory, audit);
        return (factory, db, audit, engine);
    }

    [Fact]
    public async Task EvaluateOrganisationComplianceAsync_FullCompliance_ReturnsGoldStandard()
    {
        var (factory, db, audit, engine) = CreateTestEngine();

        var org = new Organisation
        {
            CompanyName = "Toyota Manufacturing SA",
            SdlNumber = "L123456789",
            CompanySizeCode = "LARGE",
            LevyCategoryCode = "LEVY_PAYING",
            BankingDetailsVerified = true,
            BankAccountNumber = "1234567890",
            BankName = "Standard Bank"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        db.WspSubmissions.Add(new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = DateTime.UtcNow.Year,
            StatusCode = "Approved",
            ReferenceNumber = "WSP-2026-TOYOTA"
        });

        db.LevyFileLines.Add(new LevyFileLine
        {
            SdlNumber = org.SdlNumber,
            MandatoryLevyAmount = 10000m,
            DiscretionaryLevyAmount = 25000m
        });

        var committee = new TrainingCommittee
        {
            OrganisationId = org.Id,
            CommitteeStatusCode = "Active",
            ConstitutionalQuorumMet = true
        };
        db.TrainingCommittees.Add(committee);
        await db.SaveChangesAsync();

        db.TrainingCommitteeMembers.AddRange(
            new TrainingCommitteeMember { TrainingCommitteeId = committee.Id, MemberRoleCode = "EmployerRepresentative", Constituency = "Management", IsActive = true },
            new TrainingCommitteeMember { TrainingCommitteeId = committee.Id, MemberRoleCode = "EmployerRepresentative", Constituency = "HR", IsActive = true },
            new TrainingCommitteeMember { TrainingCommitteeId = committee.Id, MemberRoleCode = "UnionRepresentative", Constituency = "NUMSA", IsActive = true },
            new TrainingCommitteeMember { TrainingCommitteeId = committee.Id, MemberRoleCode = "UnionRepresentative", Constituency = "Solidarity", IsActive = true }
        );

        db.OrganisationGovernanceMembers.AddRange(
            new OrganisationGovernanceMember { OrganisationId = org.Id, DirectorCategory = "EXECUTIVE", GovernanceRoleCode = "DIRECTOR", IsActive = true },
            new OrganisationGovernanceMember { OrganisationId = org.Id, MemberType = "CORPORATE_ENTITY", CorporateEntityName = "Toyota Motor Corp", ShareholdingPercentage = 100m, IsActive = true }
        );

        db.WorkplaceApprovals.Add(new WorkplaceApproval
        {
            OrganisationId = org.Id,
            ApprovalStatusCode = "Approved",
            ApprovalNumber = "WPA-2026-001",
            QualificationTitle = "Automotive Motor Mechanic NQF 4"
        });

        await db.SaveChangesAsync();

        var report = await engine.EvaluateOrganisationComplianceAsync(org.Id);

        Assert.Equal(org.Id, report.OrganisationId);
        Assert.Equal(100m, report.OverallScore);
        Assert.Equal("Success", report.OverallBadgeColor);
        Assert.Contains("Gold Standard", report.OverallGrade);
        Assert.True(report.IsEligibleForDgGrants);
        Assert.True(report.IsEligibleForLearnerRegistrations);
        Assert.Equal(7, report.CompliantPillarsCount);
        Assert.Empty(report.CriticalActionItems);
    }

    [Fact]
    public async Task EvaluateOrganisationComplianceAsync_NonCompliant_FlagsCriticalActionsAndRestrictsGrants()
    {
        var (factory, db, audit, engine) = CreateTestEngine();

        var org = new Organisation
        {
            CompanyName = "NonCompliant Steelworks",
            SdlNumber = "L999888777",
            CompanySizeCode = "LARGE",
            LevyCategoryCode = "LEVY_PAYING",
            BankingDetailsVerified = false,
            BankAccountNumber = null
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        db.ConflictFlags.Add(new ConflictFlag
        {
            TargetOrganisationId = org.Id,
            SeverityCode = "RED_CRITICAL",
            Title = "Insider Shareholder detected",
            Description = "Executive board member holds undeclared beneficial interest",
            ResolutionStatusCode = "OPEN"
        });
        await db.SaveChangesAsync();

        var report = await engine.EvaluateOrganisationComplianceAsync(org.Id);

        Assert.True(report.OverallScore < 50m);
        Assert.Equal("Error", report.OverallBadgeColor);
        Assert.Contains("Non-Compliant", report.OverallGrade);
        Assert.False(report.IsEligibleForDgGrants);
        Assert.False(report.IsEligibleForLearnerRegistrations);
        Assert.NotEmpty(report.CriticalActionItems);

        var coiPillar = report.Pillars.First(p => p.PillarType == StatutoryPillarType.ConflictOfInterestStanding);
        Assert.Equal(CompliancePillarStatus.NonCompliant, coiPillar.Status);
        Assert.Equal(0m, coiPillar.Score);

        var bankPillar = report.Pillars.First(p => p.PillarType == StatutoryPillarType.BankingDetailsSecurity);
        Assert.Equal(CompliancePillarStatus.NonCompliant, bankPillar.Status);
        Assert.Equal(0m, bankPillar.Score);
    }

    [Fact]
    public async Task EvaluateTrainingCommitteeParityAsync_SmmeEmployer_ExemptFromMandatoryCommittee()
    {
        var (factory, db, audit, engine) = CreateTestEngine();

        var smmeOrg = new Organisation
        {
            CompanyName = "Precision Machine Shop CC",
            SdlNumber = "L555666777",
            CompanySizeCode = "SMALL"
        };
        db.Organisations.Add(smmeOrg);
        await db.SaveChangesAsync();

        var report = await engine.EvaluateTrainingCommitteeParityAsync(smmeOrg.Id);

        Assert.False(report.IsMandatory);
        Assert.True(report.IsCompliant);
        Assert.Equal("Info", report.StatusSeverity);
        Assert.Contains("Exempt", report.StatusText);
    }

    [Fact]
    public async Task EvaluateTrainingCommitteeParityAsync_LargeEmployer_ImbalancedRepresentation_Flagged()
    {
        var (factory, db, audit, engine) = CreateTestEngine();

        var largeOrg = new Organisation
        {
            CompanyName = "Heavy Industries Group Ltd",
            SdlNumber = "L444333222",
            CompanySizeCode = "LARGE"
        };
        db.Organisations.Add(largeOrg);
        await db.SaveChangesAsync();

        var committee = new TrainingCommittee
        {
            OrganisationId = largeOrg.Id,
            CommitteeStatusCode = "Active",
            ConstitutionalQuorumMet = true
        };
        db.TrainingCommittees.Add(committee);
        await db.SaveChangesAsync();

        db.TrainingCommitteeMembers.AddRange(
            new TrainingCommitteeMember { TrainingCommitteeId = committee.Id, MemberRoleCode = "EmployerRepresentative", Constituency = "Management", IsActive = true },
            new TrainingCommitteeMember { TrainingCommitteeId = committee.Id, MemberRoleCode = "EmployerRepresentative", Constituency = "Finance", IsActive = true },
            new TrainingCommitteeMember { TrainingCommitteeId = committee.Id, MemberRoleCode = "EmployerRepresentative", Constituency = "Operations", IsActive = true },
            new TrainingCommitteeMember { TrainingCommitteeId = committee.Id, MemberRoleCode = "EmployerRepresentative", Constituency = "HR", IsActive = true },
            new TrainingCommitteeMember { TrainingCommitteeId = committee.Id, MemberRoleCode = "UnionRepresentative", Constituency = "NUMSA", IsActive = true }
        );
        await db.SaveChangesAsync();

        var report = await engine.EvaluateTrainingCommitteeParityAsync(largeOrg.Id);

        Assert.True(report.IsMandatory);
        Assert.False(report.IsCompliant);
        Assert.Equal(5, report.TotalMembers);
        Assert.Equal(4, report.ManagementMembersCount);
        Assert.Equal(1, report.LabourMembersCount);
        Assert.Equal("Warning", report.StatusSeverity);
        Assert.Contains("Imbalanced", report.StatusText);
        Assert.Contains("Labour/Union", report.Recommendation);
    }
}