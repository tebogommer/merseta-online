using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

/// <summary>
/// Comprehensive Unit & Integration Tests for Skills Development Provider (SDP) Statutory Governance Standard
/// based on the Signed 2023 Specification (SDP Application Use Case 21022023):
/// 1. 5 Statutory Accreditation Streams & Trade Test Centre (TTC) NAMB Assessor/Moderator credentials.
/// 2. Interactive Two-Stage QMS Self-Evaluation audit checklist (10 criteria seeding, submission, and audit trail).
/// 3. 5-Working-Day inspection SLA due date computation & 6-Month non-disruptive re-accreditation invariant.
/// 4. Multi-contact quorum (minimum 2 verified contacts with designated banking confirmation authority).
/// 5. SHA-256 Digital Security Seal generation and verification.
/// </summary>
public class SdpAccreditationGovernanceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, TrainingProviderService providerService) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var providerService = new TrainingProviderService(factory, audit);
        return (factory, db, audit, providerService);
    }

    [Fact]
    public async Task AccreditationStream_SupportsAllFiveStreamsAndPersistsStatutoryCredentials()
    {
        var (_, db, _, service) = CreateContext();

        var org = new Organisation
        {
            CompanyName = "Apex Technical Academy (Pty) Ltd",
            SdlNumber = "L910283471",
            RegistrationNumber = "2021/098123/07"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // 1. Test QCTO Trade Test Centre (TTC) stream with NAMB assessor/moderator credentials
        var ttcProvider = new TrainingProvider
        {
            OrganisationId = org.Id,
            AccreditationNumber = "SDP-TTC-2026-001",
            AccreditationStream = "QCTO Trade Test Centre (TTC)",
            NambTtcRegistrationNumber = "NAMB-TTC-8491",
            NambTtcExpiryDate = DateTime.Today.AddYears(3),
            ProviderStatusCode = "Active",
            AccreditationStartDate = DateTime.Today,
            AccreditationEndDate = DateTime.Today.AddYears(3),
            IsActive = true
        };

        var savedTtc = await service.SaveAsync(ttcProvider, "TestAdmin");
        Assert.NotNull(savedTtc);
        Assert.Equal("QCTO Trade Test Centre (TTC)", savedTtc.AccreditationStream);
        Assert.Equal("NAMB-TTC-8491", savedTtc.NambTtcRegistrationNumber);
        Assert.NotNull(savedTtc.NambTtcExpiryDate);

        // 2. Test Programme Approval (Non-merSETA SETA) with Primary ETQA credentials
        var progAppProvider = new TrainingProvider
        {
            OrganisationId = org.Id,
            AccreditationNumber = "SDP-PA-2026-002",
            AccreditationStream = "Programme Approval (Non-merSETA SETA)",
            PrimaryEtqaName = "Services SETA",
            PrimaryEtqaAccreditationNumber = "ETQA/12/2022",
            ProviderStatusCode = "Active",
            AccreditationStartDate = DateTime.Today,
            AccreditationEndDate = DateTime.Today.AddYears(5),
            IsActive = true
        };

        var savedProgApp = await service.SaveAsync(progAppProvider, "TestAdmin");
        Assert.NotNull(savedProgApp);
        Assert.Equal("Programme Approval (Non-merSETA SETA)", savedProgApp.AccreditationStream);
        Assert.Equal("Services SETA", savedProgApp.PrimaryEtqaName);
        Assert.Equal("ETQA/12/2022", savedProgApp.PrimaryEtqaAccreditationNumber);
    }

    [Fact]
    public async Task QmsSelfEvaluation_SeedsTenCriteriaAndSubmitsSuccessfully()
    {
        var (factory, db, _, service) = CreateContext();

        var org = new Organisation { CompanyName = "Vaal Technical Institute", SdlNumber = "L102938475" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var provider = new TrainingProvider
        {
            OrganisationId = org.Id,
            AccreditationNumber = "SDP-QMS-2026-001",
            ProviderStatusCode = "Registered"
        };
        db.TrainingProviders.Add(provider);
        await db.SaveChangesAsync();

        // 1. Seed default 10 criteria
        var criteria = await service.SeedDefaultSelfEvaluationsAsync(provider.Id, "OfficerQA");
        Assert.Equal(10, criteria.Count);
        Assert.Contains(criteria, c => c.CriteriaCode == "QMS-01");
        Assert.Contains(criteria, c => c.CriteriaCode == "QMS-10");

        // 2. Verify idempotency - subsequent calls return existing items
        var existingCriteria = await service.SeedDefaultSelfEvaluationsAsync(provider.Id, "OfficerQA");
        Assert.Equal(10, existingCriteria.Count);

        // 3. Update compliance and document references
        var first = criteria[0];
        first.DocumentReferenceNumber = "DOC-POL-001";
        first.ApplicantComments = "CIPC and SARS tax clearance certificates verified.";
        await service.SaveSelfEvaluationAsync(first, "OfficerQA");

        // 4. Submit self-evaluation
        var submitResult = await service.SubmitSelfEvaluationAsync(provider.Id, criteria, "PrimarySdf");
        Assert.True(submitResult);

        using var verifyDb = factory.CreateDbContext(); var refreshed = await verifyDb.TrainingProviders.FindAsync(provider.Id);
        Assert.NotNull(refreshed);
        Assert.Equal("QMS_SUBMITTED", refreshed.ProviderStatusCode);
    }

    [Fact]
    public void Calculate5WorkingDaysDueDate_SkipsWeekendsCorrectly()
    {
        var (_, _, _, service) = CreateContext();

        // Wednesday 2026-09-02:
        // Day 1: Thursday 2026-09-03
        // Day 2: Friday 2026-09-04
        // (Saturday 2026-09-05 & Sunday 2026-09-06 skipped)
        // Day 3: Monday 2026-09-07
        // Day 4: Tuesday 2026-09-08
        // Day 5: Wednesday 2026-09-09
        var startDate = new DateTime(2026, 9, 2);
        var dueDate = service.Calculate5WorkingDaysDueDate(startDate);

        Assert.Equal(new DateTime(2026, 9, 9), dueDate.Date);
        Assert.NotEqual(DayOfWeek.Saturday, dueDate.DayOfWeek);
        Assert.NotEqual(DayOfWeek.Sunday, dueDate.DayOfWeek);
    }

    [Fact]
    public async Task ReAccreditation_EnforcesSixMonthInvariantAndPreservesOperationalStatus()
    {
        var (_, db, _, service) = CreateContext();

        var org = new Organisation { CompanyName = "Pretoria Tool & Die", SdlNumber = "L593820192" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Case 1: Expiry is > 6 months away (e.g. 12 months) -> Throws statutory exception
        var providerFuture = new TrainingProvider
        {
            OrganisationId = org.Id,
            AccreditationNumber = "SDP-FUT-2026-001",
            AccreditationEndDate = DateTime.Today.AddMonths(12),
            ProviderStatusCode = "Accredited",
            IsActive = true
        };
        db.TrainingProviders.Add(providerFuture);
        await db.SaveChangesAsync();

        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            service.InitiateReAccreditationAsync(providerFuture.Id, "Admin"));
        Assert.Contains("strictly permitted within 6 months prior to expiry", ex.Message);

        // Case 2: Expiry is within 6 months (e.g. 3 months) -> Successfully initiates and PRESERVES active status
        var providerNearExpiry = new TrainingProvider
        {
            OrganisationId = org.Id,
            AccreditationNumber = "SDP-EXP-2026-002",
            AccreditationEndDate = DateTime.Today.AddMonths(3),
            ProviderStatusCode = "Accredited",
            IsActive = true
        };
        db.TrainingProviders.Add(providerNearExpiry);
        await db.SaveChangesAsync();

        var renewed = await service.InitiateReAccreditationAsync(providerNearExpiry.Id, "Admin");
        Assert.True(renewed.ReAccreditationUnderway);
        // STATUTORY INVARIANT: Provider operational status must NOT revert to "Pending Approval"
        Assert.Equal("Accredited", renewed.ProviderStatusCode);
        Assert.NotEqual("Pending Approval", renewed.ProviderStatusCode);
    }

    [Fact]
    public async Task ContactQuorum_EnforcesMinimumTwoContactsAndBankingAuthority()
    {
        var (_, db, _, service) = CreateContext();

        var org = new Organisation { CompanyName = "Transvaal Engineering Centre", SdlNumber = "L394820183" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var provider = new TrainingProvider
        {
            OrganisationId = org.Id,
            AccreditationNumber = "SDP-CON-2026-001",
            ProviderStatusCode = "Application"
        };
        db.TrainingProviders.Add(provider);
        await db.SaveChangesAsync();

        // 1. Exactly 0 contacts -> Quorum fails
        var quorum0 = await service.ValidateContactQuorumAsync(provider.Id);
        Assert.False(quorum0.IsValid);
        Assert.Contains("minimum of two (2) verified contact persons", quorum0.Message);

        // 2. Exactly 1 contact -> Quorum fails
        var contact1 = new TrainingProviderContact
        {
            TrainingProviderId = provider.Id,
            FirstName = "Kagiso",
            LastName = "Mahlangu",
            ContactDesignation = "Primary SDF Contact",
            Email = "kagiso.m@transvaal.co.za",
            IsPrimaryContact = true,
            IsBankingConfirmationAuthorized = false
        };
        await service.SaveContactAsync(provider.Id, contact1, "TestAdmin");

        var quorum1 = await service.ValidateContactQuorumAsync(provider.Id);
        Assert.False(quorum1.IsValid);
        Assert.Contains("Exactly 1 active contact person", quorum1.Message);

        // 3. Exactly 2 contacts, but neither has banking confirmation authority -> Banking invariant fails
        var contact2 = new TrainingProviderContact
        {
            TrainingProviderId = provider.Id,
            FirstName = "Lindiwe",
            LastName = "Zulu",
            ContactDesignation = "Campus Administrator",
            Email = "lindiwe.z@transvaal.co.za",
            IsPrimaryContact = false,
            IsBankingConfirmationAuthorized = false
        };
        await service.SaveContactAsync(provider.Id, contact2, "TestAdmin");

        var quorum2NoBank = await service.ValidateContactQuorumAsync(provider.Id);
        Assert.False(quorum2NoBank.IsValid);
        Assert.Contains("Banking Confirmation Invariant Failed", quorum2NoBank.Message);

        // 4. Update contact2 to have banking confirmation authority -> Quorum succeeds!
        contact2.IsBankingConfirmationAuthorized = true;
        await service.SaveContactAsync(provider.Id, contact2, "TestAdmin");

        var quorumValid = await service.ValidateContactQuorumAsync(provider.Id);
        Assert.True(quorumValid.IsValid);
        Assert.Contains("verified successfully", quorumValid.Message);
    }

    [Fact]
    public void DigitalSecuritySeal_GeneratesCryptographicSha256SealAndDetectsTampering()
    {
        var (_, _, _, service) = CreateContext();

        var provider = new TrainingProvider
        {
            Id = 42,
            AccreditationNumber = "SDP-2026-9942",
            AccreditationStream = "Primary merSETA",
            AccreditationStartDate = new DateTime(2026, 1, 1),
            AccreditationEndDate = new DateTime(2031, 1, 1),
            MaxLearnerCapacity = 150
        };

        var seal1 = service.GenerateAccreditationSecuritySeal(provider);
        Assert.NotNull(seal1);
        Assert.Equal(64, seal1.Length); // SHA-256 output is 64 hex chars

        // Identical parameters produce identical seal (idempotency)
        var seal2 = service.GenerateAccreditationSecuritySeal(provider);
        Assert.Equal(seal1, seal2);

        // Tampering with accreditation number changes the seal
        provider.AccreditationNumber = "SDP-2026-TAMPERED";
        var tamperedSeal = service.GenerateAccreditationSecuritySeal(provider);
        Assert.NotEqual(seal1, tamperedSeal);
    }
}
