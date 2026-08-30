using Microsoft.Extensions.DependencyInjection;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class SetmisSchemaAlignmentTests
{
    private readonly INsdmsDbContextFactory _factory;
    private readonly IAuditService _audit;

    public SetmisSchemaAlignmentTests()
    {
        _factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        _audit = new AuditService(_factory);
    }

    [Fact]
    public void Person_Entity_ContainsAllRequiredSetmisFile400Columns()
    {
        var person = new Person
        {
            FirstName = "Sipho",
            LastName = "Dlamini",
            RsaIdNumber = "9001015009087",
            AlternateIdTypeId = "527",
            SeeingRatingId = "01",
            HearingRatingId = "01",
            WalkingRatingId = "01",
            RememberingRatingId = "01",
            CommunicatingRatingId = "01",
            SelfCareRatingId = "01",
            LastSchoolEmisNumber = "700123456",
            LastSchoolYear = "2008",
            StatssaAreaCode = "798015001",
            PopiActStatusId = "01",
            PopiActConsentDate = DateTime.UtcNow,
            PreviousLastName = "Nkosi",
            PreviousAlternateId = "A98765432",
            PreviousAlternateIdTypeId = "527",
            PreviousProviderCode = "PRV001",
            PreviousProviderEtqaId = "17",
            FaxNumber = "0115551234",
            PhysicalAddressPostalCode = "2001",
            PostalAddressPostalCode = "2000"
        };

        Assert.Equal("Sipho", person.FirstName);
        Assert.Equal("527", person.AlternateIdTypeId);
        Assert.Equal("01", person.SeeingRatingId);
        Assert.Equal("01", person.HearingRatingId);
        Assert.Equal("01", person.WalkingRatingId);
        Assert.Equal("01", person.RememberingRatingId);
        Assert.Equal("01", person.CommunicatingRatingId);
        Assert.Equal("01", person.SelfCareRatingId);
        Assert.Equal("700123456", person.LastSchoolEmisNumber);
        Assert.Equal("2008", person.LastSchoolYear);
        Assert.Equal("798015001", person.StatssaAreaCode);
        Assert.Equal("01", person.PopiActStatusId);
        Assert.NotNull(person.PopiActConsentDate);
        Assert.Equal("Nkosi", person.PreviousLastName);
    }

    [Fact]
    public void Organisation_And_OrganisationSite_ContainAllRequiredSetmisFile200Columns()
    {
        var org = new Organisation
        {
            CompanyName = "Apex Auto Body Works (Pty) Ltd",
            SdlNumber = "L123456789",
            MainSdlNumber = "L123456789",
            SetaId = "17",
            CountryCode = "ZA",
            PhoneNumber = "0115550100",
            FaxNumber = "0115550101",
            WebsiteUrl = "https://apexauto.co.za"
        };

        var site = new OrganisationSite
        {
            SiteName = "Germiston Main Plant",
            SiteNumber = "001",
            CountryCode = "ZA",
            Latitude = -26.223400m,
            Longitude = 28.167800m,
            StatssaAreaCode = "798015001",
            PhoneNumber = "0115550102",
            FaxNumber = "0115550103",
            Email = "germiston@apexauto.co.za"
        };

        Assert.Equal("L123456789", org.MainSdlNumber);
        Assert.Equal("17", org.SetaId);
        Assert.Equal("ZA", org.CountryCode);
        Assert.Equal("001", site.SiteNumber);
        Assert.Equal(-26.223400m, site.Latitude);
        Assert.Equal(28.167800m, site.Longitude);
        Assert.Equal("798015001", site.StatssaAreaCode);
    }

    [Fact]
    public void TrainingProvider_ContainsAllRequiredSetmisFile100Columns()
    {
        var provider = new TrainingProvider
        {
            AccreditationNumber = "17-QA/ACC/0987/15",
            ProviderCode = "PRV-MER-0098",
            EtqaId = "17",
            ProviderClassId = "02",
            ProviderTypeId = "02",
            ProviderStatusId = "01",
            EtqaDecisionNumber = "ETQA-2026-MIN-44",
            SarsNumber = "9988776655",
            FaxNumber = "0115559876",
            WebsiteUrl = "https://provideracademy.co.za"
        };

        Assert.Equal("PRV-MER-0098", provider.ProviderCode);
        Assert.Equal("17", provider.EtqaId);
        Assert.Equal("02", provider.ProviderClassId);
        Assert.Equal("02", provider.ProviderTypeId);
        Assert.Equal("01", provider.ProviderStatusId);
        Assert.Equal("ETQA-2026-MIN-44", provider.EtqaDecisionNumber);
    }

    [Fact]
    public void CompanyLearner_ContainsAllRequiredSetmisFile500To506Columns()
    {
        var learner = new CompanyLearner
        {
            PersonId = 1,
            OrganisationId = 1,
            LearnerContractNumber = "LRN-2026-1-ABCD",
            QualificationTitle = "Occupational Certificate: Welder",
            SaqaQualificationId = 94100,
            NqfLevel = 4,
            LearningProgrammeTypeCode = "02",
            LearnershipId = "18Q180026241203",
            NonNqfInterventionCode = "SKP-01",
            PartOfId = "01",
            EnrolmentTypeId = "01",
            EnrolmentStatusId = "01",
            EnrolmentStatusDate = DateTime.UtcNow,
            AssessorRegistrationNumber = "17/ASS/9988",
            AssessorEtqaId = "17",
            PracticalProviderCode = "PRV-PRACT-01",
            PracticalProviderEtqaId = "17",
            OfoCode = "651202",
            EconomicStatusId = "01",
            UrbanRuralId = "01",
            CumulativeSpend = 35000.00m,
            CertificateNumber = "CERT-2026-WELD-001",
            PriorQualificationId = "QUAL-TVET-N6",
            PriorQualificationAchievementDate = new DateTime(2024, 11, 30),
            InternshipStatusId = "01",
            FundingId = "01"
        };

        Assert.Equal("18Q180026241203", learner.LearnershipId);
        Assert.Equal("651202", learner.OfoCode);
        Assert.Equal("01", learner.PartOfId);
        Assert.Equal("01", learner.EnrolmentTypeId);
        Assert.Equal("01", learner.EnrolmentStatusId);
        Assert.Equal(35000.00m, learner.CumulativeSpend);
        Assert.Equal("17/ASS/9988", learner.AssessorRegistrationNumber);
    }

    [Fact]
    public void LearnerAssessment_And_LearnerTradeTest_ContainStatutoryColumns()
    {
        var assessment = new LearnerAssessment
        {
            UnitStandardId = 119472,
            UnitStandardTitle = "Accommodate audience and context needs in oral communication",
            PartOfId = "01",
            EnrolmentTypeId = "01",
            EnrolmentStatusId = "02",
            AssessorRegistrationNumber = "17/ASS/12345",
            AssessorEtqaId = "17",
            CertificateNumber = "SOR-2026-0099",
            CumulativeSpend = 1200.00m,
            OfoCode = "651202",
            FundingId = "01",
            UrbanRuralId = "01",
            EconomicStatusId = "01",
            CompetencyStatusCode = "COMPETENT"
        };

        var tradeTest = new LearnerTradeTest
        {
            TradeTestCentreCode = "TTC-JHB-01",
            TradeTestCentreEtqaId = "17",
            TradeCode = "651202",
            QualificationId = "94100",
            TradeTestNumber = 1,
            TradeTestResultId = "01",
            TradeTestResultReasonId = "01",
            AssessorRegistrationNumber = "17/ASS/7766",
            AssessorEtqaId = "17",
            ModeratorRegistrationNumber = "17/MOD/5544",
            ModeratorEtqaId = "17",
            SerialCertificateNumber = "REDSEAL-2026-999"
        };

        Assert.Equal(119472, assessment.UnitStandardId);
        Assert.Equal("02", assessment.EnrolmentStatusId);
        Assert.Equal("TTC-JHB-01", tradeTest.TradeTestCentreCode);
        Assert.Equal("01", tradeTest.TradeTestResultId);
        Assert.Equal("REDSEAL-2026-999", tradeTest.SerialCertificateNumber);
    }

    [Fact]
    public void EtqaAssessor_And_SkillsRegistration_ContainStatutoryColumns()
    {
        var assessor = new EtqaAssessor
        {
            RegistrationNumber = "17/ASS/2026/001",
            DesignationTypeId = "01",
            DesignationStructureStatusId = "01",
            EtqaId = "17",
            EtqeDecisionNumber = "DEC-2026-88",
            StartDate = DateTime.UtcNow,
            EndDate = DateTime.UtcNow.AddYears(3)
        };

        var skills = new SkillsRegistration
        {
            NonNqfIntervCode = "SKP-AUTO-01",
            NonNqfIntervName = "Basic Automotive Electrical Diagnostics",
            SubfieldId = "06",
            EtqaId = "17",
            NonNqfIntervStatusId = "01",
            LearningProgrammeTypeId = "03",
            RegistrationStartDate = DateTime.UtcNow,
            Credits = 25,
            NqfLevel = 3
        };

        Assert.Equal("01", assessor.DesignationTypeId);
        Assert.Equal("01", assessor.DesignationStructureStatusId);
        Assert.Equal("17", assessor.EtqaId);
        Assert.Equal("SKP-AUTO-01", skills.NonNqfIntervCode);
        Assert.Equal("06", skills.SubfieldId);
        Assert.Equal("03", skills.LearningProgrammeTypeId);
    }

    [Fact]
    public async Task PersonService_PreservesAndAudits_SetmisFile400Fields()
    {
        var service = new PersonService(_factory, _audit);

        var person = new Person
        {
            FirstName = "Kagiso",
            LastName = "Mahlangu",
            RsaIdNumber = "9405055009080",
            AlternateIdTypeId = "527",
            SeeingRatingId = "01",
            HearingRatingId = "01",
            WalkingRatingId = "01",
            RememberingRatingId = "01",
            CommunicatingRatingId = "01",
            SelfCareRatingId = "01",
            LastSchoolEmisNumber = "700112233",
            LastSchoolYear = "2012",
            StatssaAreaCode = "798015002",
            PopiActStatusId = "01",
            PopiActConsentDate = DateTime.UtcNow,
            PreviousLastName = "Mokoena"
        };

        var created = await service.CreateAsync(person, "AdminTest");
        Assert.True(created.Id > 0);
        Assert.Equal("527", created.AlternateIdTypeId);
        Assert.Equal("700112233", created.LastSchoolEmisNumber);
        Assert.Equal("798015002", created.StatssaAreaCode);
        Assert.Equal("01", created.SeeingRatingId);

        created.PreviousLastName = "UpdatedSurname";
        created.SeeingRatingId = "02";
        var updated = await service.UpdateAsync(created, "AdminTest");

        Assert.Equal("UpdatedSurname", updated.PreviousLastName);
        Assert.Equal("02", updated.SeeingRatingId);
    }

    [Fact]
    public async Task LearnerService_PreservesAndAudits_SetmisFile500Fields()
    {
        var personService = new PersonService(_factory, _audit);
        var orgService = new OrganisationService(_factory, _audit);
        var learnerService = new LearnerService(_factory, _audit);

        var person = await personService.CreateAsync(new Person { FirstName = "Bongani", LastName = "Zwane" }, "AdminTest");
        var org = await orgService.CreateAsync(new Organisation { CompanyName = "Zwane Motors", SdlNumber = "L998877665", MainSdlNumber = "L998877665", SetaId = "17" }, "AdminTest");

        var learner = new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            QualificationTitle = "Automotive Motor Mechanic",
            SaqaQualificationId = 64410,
            NqfLevel = 4,
            LearnershipId = "18Q180026241203",
            OfoCode = "651202",
            PartOfId = "01",
            EnrolmentTypeId = "01",
            EnrolmentStatusId = "01",
            EconomicStatusId = "01",
            UrbanRuralId = "01",
            CumulativeSpend = 15000.00m,
            AssessorRegistrationNumber = "17/ASS/1122"
        };

        var registered = await learnerService.RegisterLearnerAsync(learner, "AdminTest");
        Assert.True(registered.Id > 0);
        Assert.Equal("18Q180026241203", registered.LearnershipId);
        Assert.Equal("651202", registered.OfoCode);
        Assert.Equal(15000.00m, registered.CumulativeSpend);

        registered.EnrolmentStatusId = "02";
        registered.CumulativeSpend = 45000.00m;
        registered.CertificateNumber = "CERT-999";
        var updated = await learnerService.UpdateLearnerAsync(registered, "AdminTest");

        Assert.Equal("02", updated.EnrolmentStatusId);
        Assert.Equal(45000.00m, updated.CumulativeSpend);
        Assert.Equal("CERT-999", updated.CertificateNumber);
    }
}
