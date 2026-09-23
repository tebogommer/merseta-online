using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class IsoDpsaAuditComplianceAndZeroPartialCommitTests
{
    [Fact]
    public async Task ZeroPartialCommits_WhenOperationThrows_RollsBackEverything_NoOrphanRecordsPersisted()
    {
        // Arrange
        var dbName = $"AtomicRollbackDb_{Guid.NewGuid()}";
        var factory = new TestDbContextFactory(dbName);
        var manager = new AtomicAuditTransactionManager(factory);

        // Act & Assert: An exception thrown during domain logic must roll back the transaction completely
        await Assert.ThrowsAsync<ApplicationException>(() =>
            manager.ExecuteAtomicAsync<Organisation>(async db =>
            {
                var org = new Organisation
                {
                    CompanyName = "Rollback Aerospace Ltd",
                    SdlNumber = "L999888777",
                    IsActive = true
                };
                db.Organisations.Add(org);

                // Simulate unexpected failure before commit
                throw new ApplicationException("Simulated catastrophic crash prior to audit completion.");
            }));

        // Assert: Database state must be pristine - zero partial commits
        using var ctx = factory.CreateDbContext();
        var persistedOrg = await ctx.Organisations.FirstOrDefaultAsync(o => o.CompanyName == "Rollback Aerospace Ltd");
        Assert.Null(persistedOrg);

        var persistedAudit = await ctx.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "Organisation");
        Assert.Null(persistedAudit);
    }

    [Fact]
    public async Task ZeroPartialCommits_WhenDpsaAnonymousActorDetected_FailsFastAndRollsBackEntity()
    {
        // Arrange
        var dbName = $"DpsaAnonymousRollbackDb_{Guid.NewGuid()}";
        var factory = new TestDbContextFactory(dbName);
        var manager = new AtomicAuditTransactionManager(factory);

        // Act & Assert: DPSA Information Security Directive forbids anonymous mutations
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            manager.ExecuteAtomicAsync<Organisation>(async db =>
            {
                var org = new Organisation
                {
                    CompanyName = "Anonymous Entity Breach",
                    SdlNumber = "L111222333",
                    IsActive = true
                };
                db.Organisations.Add(org);

                return (org, new AuditEntrySpec
                {
                    EntityName = "Organisation",
                    ActionName = "Create",
                    Actor = "ANONYMOUS", // Forbidden by DPSA Directive
                    BeforeState = null,
                    AfterState = org
                });
            }));

        Assert.Contains("DPSA Information Security Directive breach", ex.Message);

        // Assert: Entity must NOT exist in the database
        using var ctx = factory.CreateDbContext();
        var exists = await ctx.Organisations.AnyAsync(o => o.CompanyName == "Anonymous Entity Breach");
        Assert.False(exists, "Entity must not be partially committed when DPSA actor check fails.");
    }

    [Fact]
    public async Task AtomicAudit_PersistsBothEntityAndAuditLog_WithNonZeroEntityIdAndSeal()
    {
        // Arrange
        var dbName = $"AtomicSuccessDb_{Guid.NewGuid()}";
        var factory = new TestDbContextFactory(dbName);
        var manager = new AtomicAuditTransactionManager(factory);

        // Act
        var createdOrg = await manager.ExecuteAtomicAsync<Organisation>(async db =>
        {
            var org = new Organisation
            {
                CompanyName = "Verified Transmissions Pty Ltd",
                SdlNumber = "L555666777",
                IsActive = true
            };
            db.Organisations.Add(org);

            return (org, new AuditEntrySpec
            {
                EntityName = "Organisation",
                ActionName = "Create",
                Actor = "compliance.officer@merseta.org.za",
                BeforeState = null,
                AfterState = org
            });
        });

        // Assert
        Assert.NotNull(createdOrg);
        Assert.True(createdOrg.Id > 0, "Persisted entity must receive a positive auto-generated ID.");

        using var ctx = factory.CreateDbContext();
        var audit = await ctx.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "Organisation" && a.RecordId == createdOrg.Id);
        Assert.NotNull(audit);
        Assert.Equal(createdOrg.Id, audit.RecordId);
        Assert.Equal("compliance.officer@merseta.org.za", audit.Actor);
        Assert.Equal("Create", audit.ActionName);
        Assert.NotNull(audit.MetadataJson);
        Assert.Contains("digitalSecuritySeal", audit.MetadataJson);
        Assert.Contains("iso9001Compliance", audit.MetadataJson);
        Assert.Contains("dpsaDirectiveCompliance", audit.MetadataJson);
    }

    [Fact]
    public async Task IsoDpsaComplianceService_VerifyZeroPartialCommits_DetectsZeroRecordIdViolation()
    {
        // Arrange
        var dbName = $"ZeroIdViolationDb_{Guid.NewGuid()}";
        var factory = new TestDbContextFactory(dbName);
        var auditService = new AuditService(factory);
        var complianceService = new IsoDpsaAuditComplianceService(factory, auditService);

        using (var ctx = factory.CreateDbContext())
        {
            // Seed a corrupt audit log with RecordId = 0
            ctx.AuditLogs.Add(new AuditLog
            {
                EntityName = "CorruptRecord",
                RecordId = 0, // Corrupt ID violating ISO 9001
                ActionName = "BadInsert",
                Actor = "bad.actor@merseta.org.za",
                MetadataJson = "{}",
                Timestamp = DateTime.UtcNow
            });
            await ctx.SaveChangesAsync();
        }

        // Act
        var findings = await complianceService.VerifyZeroPartialCommitsAsync();

        // Assert
        var zeroIdFinding = findings.FirstOrDefault(f => f.RuleId == "ZERO-PARTIAL-01");
        Assert.NotNull(zeroIdFinding);
        Assert.False(zeroIdFinding.IsPassed, "ZERO-PARTIAL-01 must fail when an audit log has RecordId <= 0.");
    }

    [Fact]
    public async Task IsoDpsaComplianceService_Iso9001Clause75_VerifiesDocumentedInformationIntegrity()
    {
        // Arrange
        var dbName = $"Iso9001TestDb_{Guid.NewGuid()}";
        var factory = new TestDbContextFactory(dbName);
        var auditService = new AuditService(factory);
        var complianceService = new IsoDpsaAuditComplianceService(factory, auditService);

        using (var ctx = factory.CreateDbContext())
        {
            // Seed valid ISO 9001 compliant audit log
            var now = DateTime.UtcNow;
            var seal = AtomicAuditTransactionManager.ComputeDigitalSecuritySeal("GrantMoa", 101, "ApproveMoa", "ceo@merseta.org.za", now);
            ctx.AuditLogs.Add(new AuditLog
            {
                EntityName = "GrantMoa",
                RecordId = 101,
                ActionName = "ApproveMoa",
                Actor = "ceo@merseta.org.za",
                MetadataJson = $"{{\"before\":null,\"after\":{{\"Status\":\"Approved\"}},\"digitalSecuritySeal\":\"{seal}\"}}",
                Timestamp = now
            });
            await ctx.SaveChangesAsync();
        }

        // Act
        var findings = await complianceService.VerifyIso9001ComplianceAsync();

        // Assert
        Assert.All(findings, f => Assert.True(f.IsPassed, $"Finding {f.RuleId} should pass for compliant log."));
    }

    [Fact]
    public async Task IsoDpsaComplianceService_DpsaDirective_FlagsAnonymousActors()
    {
        // Arrange
        var dbName = $"DpsaAnonFlagDb_{Guid.NewGuid()}";
        var factory = new TestDbContextFactory(dbName);
        var auditService = new AuditService(factory);
        var complianceService = new IsoDpsaAuditComplianceService(factory, auditService);

        using (var ctx = factory.CreateDbContext())
        {
            ctx.AuditLogs.Add(new AuditLog
            {
                EntityName = "WspSubmission",
                RecordId = 42,
                ActionName = "Submit",
                Actor = "ANONYMOUS", // Breach
                MetadataJson = "{\"test\":true}",
                Timestamp = DateTime.UtcNow
            });
            await ctx.SaveChangesAsync();
        }

        // Act
        var findings = await complianceService.VerifyDpsaDirectiveComplianceAsync();

        // Assert
        var actorFinding = findings.FirstOrDefault(f => f.RuleId == "DPSA-DIR-01");
        Assert.NotNull(actorFinding);
        Assert.False(actorFinding.IsPassed, "DPSA-DIR-01 must flag failure when anonymous actor is present.");
    }

    [Fact]
    public async Task IsoDpsaComplianceService_DpsaDirective_FlagsUnmaskedRsaIdInMetadata()
    {
        // Arrange
        var dbName = $"DpsaPopiaFlagDb_{Guid.NewGuid()}";
        var factory = new TestDbContextFactory(dbName);
        var auditService = new AuditService(factory);
        var complianceService = new IsoDpsaAuditComplianceService(factory, auditService);

        using (var ctx = factory.CreateDbContext())
        {
            // Raw unmasked 13-digit RSA National ID exposed in JSON
            ctx.AuditLogs.Add(new AuditLog
            {
                EntityName = "Person",
                RecordId = 88,
                ActionName = "Update",
                Actor = "hr@merseta.org.za",
                MetadataJson = "{\"RsaIdNumber\":\"9201015009087\",\"Name\":\"John\"}", // Unmasked PII breach
                Timestamp = DateTime.UtcNow
            });
            await ctx.SaveChangesAsync();
        }

        // Act
        var findings = await complianceService.VerifyDpsaDirectiveComplianceAsync();

        // Assert
        var popiaFinding = findings.FirstOrDefault(f => f.RuleId == "DPSA-POPIA-02");
        Assert.NotNull(popiaFinding);
        Assert.False(popiaFinding.IsPassed, "DPSA-POPIA-02 must flag failure when unmasked 13-digit RSA ID is detected.");
    }

    [Fact]
    public async Task IsoDpsaComplianceService_DpsaDirective_FlagsMakerCheckerSelfApproval()
    {
        // Arrange
        var dbName = $"DpsaSodFlagDb_{Guid.NewGuid()}";
        var factory = new TestDbContextFactory(dbName);
        var auditService = new AuditService(factory);
        var complianceService = new IsoDpsaAuditComplianceService(factory, auditService);

        using (var ctx = factory.CreateDbContext())
        {
            ctx.BankingDetails.Add(new BankingDetails
            {
                BankName = "Standard Bank",
                BranchCode = "051001",
                AccountNumber = "******1234",
                AccountHolderName = "Self Approver Corp",
                AccountTypeCode = "Current",
                ApprovalStatusCode = "FullyApproved",
                CreatedBy = "officer.smith@merseta.org.za",
                SecondSignoffUserId = "officer.smith@merseta.org.za" // Self-approval breach!
            });
            await ctx.SaveChangesAsync();
        }

        // Act
        var findings = await complianceService.VerifyDpsaDirectiveComplianceAsync();

        // Assert
        var sodFinding = findings.FirstOrDefault(f => f.RuleId == "DPSA-SOD-03");
        Assert.NotNull(sodFinding);
        Assert.False(sodFinding.IsPassed, "DPSA-SOD-03 must flag failure when self-approval violation exists.");
    }

    [Fact]
    public async Task IsoDpsaComplianceService_RunFullComplianceAudit_GeneratesSignedReport()
    {
        // Arrange
        var dbName = $"FullReportDb_{Guid.NewGuid()}";
        var factory = new TestDbContextFactory(dbName);
        var auditService = new AuditService(factory);
        var complianceService = new IsoDpsaAuditComplianceService(factory, auditService);

        // Act
        var report = await complianceService.RunFullComplianceAuditAsync();

        // Assert
        Assert.NotNull(report);
        Assert.NotEmpty(report.AssessmentStatus);
        Assert.True(report.ComplianceScorePercentage >= 0.0);
        Assert.NotEmpty(report.DigitalSecuritySeal);
        Assert.NotEmpty(report.Findings);
    }

    [Fact]
    public async Task OrganisationService_CreateAsync_ExecutesAtomicallyWithAuditLog()
    {
        // Arrange
        var dbName = $"OrgAtomicDb_{Guid.NewGuid()}";
        var factory = new TestDbContextFactory(dbName);
        var auditService = new AuditService(factory);
        var orgService = new OrganisationService(factory, auditService);

        var org = new Organisation
        {
            CompanyName = "Precision Dynamics Engineering",
            SdlNumber = "L123987456",
            IsActive = true
        };

        // Act
        var created = await orgService.CreateAsync(org, "admin@merseta.org.za");

        // Assert
        Assert.NotNull(created);
        Assert.True(created.Id > 0);

        using var ctx = factory.CreateDbContext();
        var audit = await ctx.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "Organisation" && a.RecordId == created.Id);
        Assert.NotNull(audit);
        Assert.Equal(created.Id, audit.RecordId);
        Assert.Equal("admin@merseta.org.za", audit.Actor);
        Assert.Equal("Create", audit.ActionName);
    }

    [Fact]
    public async Task BankingDetailsService_SubmitAndSignoffs_ExecuteAtomicallyWithAuditLog()
    {
        // Arrange
        var dbName = $"BankAtomicDb_{Guid.NewGuid()}";
        var factory = new TestDbContextFactory(dbName);
        var auditService = new AuditService(factory);
        var bankService = new BankingDetailsService(factory, auditService);

        // Act 1: Submit Banking Details
        var submitted = await bankService.SubmitBankingDetailsAsync(
            organisationId: 10,
            trainingProviderId: null,
            bankName: "First National Bank",
            branchCode: "250655",
            branchName: "Sandton",
            accountNumber: "62899911122",
            accountHolderName: "Apex Motors Pty Ltd",
            accountTypeCode: "Cheque",
            docPath: "/docs/bank_conf.pdf",
            docDate: DateTime.UtcNow,
            currentUsername: "submitting.clerk@employer.co.za");

        Assert.NotNull(submitted);
        Assert.True(submitted.Id > 0);

        // Act 2: First Signoff
        var firstApproved = await bankService.FirstSignoffAsync(
            submitted.Id,
            approved: true,
            notes: "Bank letter verified against CIPC certificate.",
            currentUsername: "clo.reviewer@merseta.org.za");

        Assert.Equal("FirstSignoffApproved", firstApproved.ApprovalStatusCode);

        // Act 3: Second Signoff (Segregation of Duties: must use different officer)
        var secondApproved = await bankService.SecondSignoffAndActivateErpAsync(
            submitted.Id,
            approved: true,
            notes: "AVS verified and forensic clearance confirmed.",
            currentUsername: "finance.manager@merseta.org.za");

        Assert.Equal("FullyApproved", secondApproved.ApprovalStatusCode);
        Assert.True(secondApproved.IsErpActive);

        // Assert: Verify all 3 audit records exist atomically
        using var ctx = factory.CreateDbContext();
        var audits = await ctx.AuditLogs
            .Where(a => a.EntityName == "BankingDetails" && a.RecordId == submitted.Id)
            .OrderBy(a => a.Id)
            .ToListAsync();

        Assert.True(audits.Count >= 3, "All three banking lifecycle stages must have atomic audit logs.");
        Assert.Contains(audits, a => a.ActionName == "SubmitBankingDetails");
        Assert.Contains(audits, a => a.ActionName == "FirstSignoff");
        Assert.Contains(audits, a => a.ActionName == "SecondSignoffAndActivateErp");
    }
}
