using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Services;
using Nsdms.Application.Validation;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

/// <summary>
/// Comprehensive Unit & Integration Tests for QCTO Accreditation Statutory Governance:
/// 1. Section 26I Statutory Intake Gate: QCTO SDP requires QctoAccreditationNumber.
/// 2. QCTO Trade Test Centre (TTC) requires NAMB Registration Number.
/// 3. Programme Approval requires Primary ETQA credentials.
/// 4. Service-level Validation: Throws InvalidOperationException on missing credentials.
/// 5. Persistence of dedicated QCTO model columns & Digital Security Seal calculation.
/// 6. QuestPDF ETQA Certificate (ETQ-TP-002) blocked for QCTO providers.
/// 7. QuestPDF QCTO Letter of Endorsement (ETQ-QCTO-001) successfully generated.
/// </summary>
public class QctoAccreditationGovernanceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, TrainingProviderService providerService, QuestPdfDocumentService pdfService) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var providerService = new TrainingProviderService(factory, audit);

        var inMemory = new Dictionary<string, string?>
        {
            { "System.BaseUrl", "https://nsdms.merseta.org.za" },
            { "General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)" }
        };
        var conf = new ConfigurationBuilder().AddInMemoryCollection(inMemory).Build();
        var configService = new SystemConfigurationService(factory, conf, audit);
        var featureFlags = new FeatureFlagService(factory, conf, audit);
        var pdfService = new QuestPdfDocumentService(factory, featureFlags, configService, null);

        return (factory, db, audit, providerService, pdfService);
    }

    [Fact]
    public void DomainValidator_QctoSdp_MissingQctoAccreditationNumber_ReturnsFatalError()
    {
        var provider = new TrainingProvider
        {
            ProviderCode = "SDP-QCTO-001",
            AccreditationNumber = "SDP-QCTO-001",
            AccreditationStream = AccreditationStreamType.QctoSkillsDevelopmentProvider,
            QctoAccreditationNumber = null // Missing required credential
        };

        var errors = TrainingProviderDomainValidator.Validate(provider);

        Assert.Contains(errors, e => e.RuleCode == "QCTO_SDP_ACCREDITATION_NUMBER_REQUIRED");
    }

    [Fact]
    public void DomainValidator_QctoTtc_MissingNambRegistrationNumber_ReturnsFatalError()
    {
        var provider = new TrainingProvider
        {
            ProviderCode = "SDP-TTC-001",
            AccreditationNumber = "SDP-TTC-001",
            AccreditationStream = AccreditationStreamType.QctoTradeTestCentre,
            NambRegistrationNumber = null // Missing required NAMB credential
        };

        var errors = TrainingProviderDomainValidator.Validate(provider);

        Assert.Contains(errors, e => e.RuleCode == "QCTO_TTC_NAMB_REGISTRATION_NUMBER_REQUIRED");
    }

    [Fact]
    public void DomainValidator_ProgrammeApproval_MissingPrimaryEtqa_ReturnsFatalError()
    {
        var provider = new TrainingProvider
        {
            ProviderCode = "SDP-PA-001",
            AccreditationNumber = "SDP-PA-001",
            AccreditationStream = AccreditationStreamType.ProgrammeApproval,
            PrimaryEtqaName = null
        };

        var errors = TrainingProviderDomainValidator.Validate(provider);

        Assert.Contains(errors, e => e.RuleCode == "PROGRAMME_APPROVAL_PRIMARY_ETQA_REQUIRED");
    }

    [Fact]
    public async Task Service_CreateAsync_QctoSdpMissingNumber_ThrowsInvalidOperationException()
    {
        var (_, _, _, service, _) = CreateContext();

        var provider = new TrainingProvider
        {
            AccreditationNumber = "SDP-FAIL-01",
            AccreditationStream = AccreditationStreamType.QctoSkillsDevelopmentProvider,
            QctoAccreditationNumber = null
        };

        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => service.CreateAsync(provider));
        Assert.Contains("QCTO Accreditation Number is mandatory", ex.Message);
    }

    [Fact]
    public async Task Service_SavesAndPersistsQctoCredentialsWithSecuritySeal()
    {
        var (_, db, _, service, _) = CreateContext();

        var org = new Organisation
        {
            CompanyName = "Precision Engineering Academy (Pty) Ltd",
            SdlNumber = "L481920394",
            RegistrationNumber = "2022/102938/07"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var qctoProvider = new TrainingProvider
        {
            OrganisationId = org.Id,
            AccreditationNumber = "SDP-QCTO-2026-0042",
            AccreditationStream = AccreditationStreamType.QctoSkillsDevelopmentProvider,
            QctoAccreditationNumber = "QCTOSDP0120230915-88",
            QctoCentreCode = "CC-QCTO-0812",
            QctoAccreditationStartDate = DateTime.UtcNow.Date,
            QctoAccreditationEndDate = DateTime.UtcNow.Date.AddYears(5),
            QctoLetterAttachmentRef = "DOC-QCTO-2026-END-01.pdf",
            ProviderStatusCode = "Active",
            IsActive = true
        };

        var saved = await service.SaveAsync(qctoProvider, "TestOfficer");

        Assert.NotNull(saved);
        Assert.Equal(AccreditationStreamType.QctoSkillsDevelopmentProvider, saved.AccreditationStream);
        Assert.Equal("QCTOSDP0120230915-88", saved.QctoAccreditationNumber);
        Assert.Equal("CC-QCTO-0812", saved.QctoCentreCode);
        Assert.Equal("DOC-QCTO-2026-END-01.pdf", saved.QctoLetterAttachmentRef);
        Assert.False(string.IsNullOrEmpty(saved.DigitalSecuritySeal));
        Assert.Equal(64, saved.DigitalSecuritySeal.Length); // SHA-256 hex string length
    }

    [Fact]
    public async Task QuestPdf_GenerateSdpAccreditationCertificatePdfAsync_ThrowsForQctoProvider()
    {
        var (_, db, _, _, pdfService) = CreateContext();

        var org = new Organisation
        {
            CompanyName = "Vortex Technical Institute",
            SdlNumber = "L102938475"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var qctoProvider = new TrainingProvider
        {
            OrganisationId = org.Id,
            AccreditationNumber = "SDP-QCTO-CERT-01",
            AccreditationStream = AccreditationStreamType.QctoSkillsDevelopmentProvider,
            QctoAccreditationNumber = "QCTOSDP-TEST-001",
            ProviderStatusCode = "Accredited",
            IsActive = true
        };
        db.TrainingProviders.Add(qctoProvider);
        await db.SaveChangesAsync();

        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            pdfService.GenerateSdpAccreditationCertificatePdfAsync(qctoProvider.Id));

        Assert.Contains("merSETA cannot issue a Primary Certificate of Accreditation (Form ETQ-TP-002)", ex.Message);
        Assert.Contains("SDA §26I", ex.Message);
    }

    [Fact]
    public async Task QuestPdf_GenerateQctoEndorsementLetterPdfAsync_ProducesValidPdf()
    {
        var (_, db, _, _, pdfService) = CreateContext();

        var org = new Organisation
        {
            CompanyName = "Highland Trade & Occupational College",
            SdlNumber = "L892019283",
            PhysicalAddress = "45 Industry Road, Germiston"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var contact = new Person
        {
            FirstName = "Sipho",
            LastName = "Dlamini",
            Email = "s.dlamini@highland.edu.za",
            PhoneNumber = "0118273645",
            RsaIdNumber = "8001015009087"
        };
        db.People.Add(contact);
        await db.SaveChangesAsync();

        var qctoProvider = new TrainingProvider
        {
            OrganisationId = org.Id,
            PrimaryContactPersonId = contact.Id,
            AccreditationNumber = "SDP-QCTO-ENDORSE-01",
            AccreditationStream = AccreditationStreamType.QctoSkillsDevelopmentProvider,
            QctoAccreditationNumber = "QCTOSDP0120230501-77",
            QctoCentreCode = "CC-7789",
            QctoAccreditationStartDate = DateTime.UtcNow.Date,
            QctoAccreditationEndDate = DateTime.UtcNow.Date.AddYears(5),
            ProviderStatusCode = "Accredited",
            IsActive = true
        };
        db.TrainingProviders.Add(qctoProvider);

        // Add a qualification scope
        var qual = new TrainingProviderQualification
        {
            TrainingProvider = qctoProvider,
            QualificationTitle = "Occupational Certificate: Welder",
            SaqaQualificationId = 94100,
            NqfLevel = 4,
            AccreditationStatusCode = "Accredited",
            ExpiryDate = DateTime.UtcNow.Date.AddYears(5)
        };
        db.TrainingProviderQualifications.Add(qual);
        await db.SaveChangesAsync();

        var pdfBytes = await pdfService.GenerateQctoEndorsementLetterPdfAsync(qctoProvider.Id);

        Assert.NotNull(pdfBytes);
        Assert.NotEmpty(pdfBytes);

        // Verify PDF Magic Bytes "%PDF"
        var magic = System.Text.Encoding.ASCII.GetString(pdfBytes.Take(4).ToArray());
        Assert.Equal("%PDF", magic);
    }
}
