using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator aligning all NSDMS domain entity tables with the
/// statutory columns and index requirements defined in the DHET SETMIS File Specifications (Files 100 to 506).
/// </summary>
public static class Phase8SetmisSchemaAlignmentMigrator
{
    public static async Task MigrateSetmisSchemaAlignmentAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var db = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        try
        {
            logger?.LogInformation("Starting Phase 8: SETMIS Flat File Schema Alignment Migration...");

            var migrationSql = @"
-- ============================================================================
-- Helper procedure to add a column safely if missing
-- ============================================================================

-- 1. PERSON (SETMIS File 400)
IF OBJECT_ID(N'dbo.Person', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'AlternateIdTypeId')
        ALTER TABLE dbo.Person ADD [AlternateIdTypeId] NVARCHAR(10) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'SeeingRatingId')
        ALTER TABLE dbo.Person ADD [SeeingRatingId] NVARCHAR(10) NULL DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'HearingRatingId')
        ALTER TABLE dbo.Person ADD [HearingRatingId] NVARCHAR(10) NULL DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'WalkingRatingId')
        ALTER TABLE dbo.Person ADD [WalkingRatingId] NVARCHAR(10) NULL DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'RememberingRatingId')
        ALTER TABLE dbo.Person ADD [RememberingRatingId] NVARCHAR(10) NULL DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'CommunicatingRatingId')
        ALTER TABLE dbo.Person ADD [CommunicatingRatingId] NVARCHAR(10) NULL DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'SelfCareRatingId')
        ALTER TABLE dbo.Person ADD [SelfCareRatingId] NVARCHAR(10) NULL DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'LastSchoolEmisNumber')
        ALTER TABLE dbo.Person ADD [LastSchoolEmisNumber] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'LastSchoolYear')
        ALTER TABLE dbo.Person ADD [LastSchoolYear] NVARCHAR(10) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'StatssaAreaCode')
        ALTER TABLE dbo.Person ADD [StatssaAreaCode] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'PopiActStatusId')
        ALTER TABLE dbo.Person ADD [PopiActStatusId] NVARCHAR(10) NULL DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'PopiActConsentDate')
        ALTER TABLE dbo.Person ADD [PopiActConsentDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'PreviousLastName')
        ALTER TABLE dbo.Person ADD [PreviousLastName] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'PreviousAlternateId')
        ALTER TABLE dbo.Person ADD [PreviousAlternateId] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'PreviousAlternateIdTypeId')
        ALTER TABLE dbo.Person ADD [PreviousAlternateIdTypeId] NVARCHAR(10) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'PreviousProviderCode')
        ALTER TABLE dbo.Person ADD [PreviousProviderCode] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'PreviousProviderEtqaId')
        ALTER TABLE dbo.Person ADD [PreviousProviderEtqaId] NVARCHAR(10) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'FaxNumber')
        ALTER TABLE dbo.Person ADD [FaxNumber] NVARCHAR(30) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'PhysicalAddressPostalCode')
        ALTER TABLE dbo.Person ADD [PhysicalAddressPostalCode] NVARCHAR(20) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'PostalAddressPostalCode')
        ALTER TABLE dbo.Person ADD [PostalAddressPostalCode] NVARCHAR(20) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'IX_Person_AlternateIdTypeId')
        CREATE INDEX [IX_Person_AlternateIdTypeId] ON dbo.Person([AlternateIdTypeId]);

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'IX_Person_StatssaAreaCode')
        CREATE INDEX [IX_Person_StatssaAreaCode] ON dbo.Person([StatssaAreaCode]);

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.Person') AND name = N'IX_Person_PopiActStatusId')
        CREATE INDEX [IX_Person_PopiActStatusId] ON dbo.Person([PopiActStatusId]);
END

-- 2. ORGANISATION (SETMIS File 100 & 200)
IF OBJECT_ID(N'dbo.Organisation', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Organisation') AND name = N'MainSdlNumber')
        ALTER TABLE dbo.Organisation ADD [MainSdlNumber] NVARCHAR(20) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Organisation') AND name = N'SetaId')
        ALTER TABLE dbo.Organisation ADD [SetaId] NVARCHAR(10) NOT NULL CONSTRAINT DF_Organisation_SetaId DEFAULT '17';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Organisation') AND name = N'CountryCode')
        ALTER TABLE dbo.Organisation ADD [CountryCode] NVARCHAR(10) NOT NULL CONSTRAINT DF_Organisation_CountryCode DEFAULT 'ZA';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Organisation') AND name = N'PhoneNumber')
        ALTER TABLE dbo.Organisation ADD [PhoneNumber] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Organisation') AND name = N'FaxNumber')
        ALTER TABLE dbo.Organisation ADD [FaxNumber] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Organisation') AND name = N'WebsiteUrl')
        ALTER TABLE dbo.Organisation ADD [WebsiteUrl] NVARCHAR(200) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Organisation') AND name = N'PhysicalAddress')
        ALTER TABLE dbo.Organisation ADD [PhysicalAddress] NVARCHAR(500) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Organisation') AND name = N'PhysicalAddressPostalCode')
        ALTER TABLE dbo.Organisation ADD [PhysicalAddressPostalCode] NVARCHAR(20) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Organisation') AND name = N'PostalAddress')
        ALTER TABLE dbo.Organisation ADD [PostalAddress] NVARCHAR(500) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Organisation') AND name = N'PostalAddressPostalCode')
        ALTER TABLE dbo.Organisation ADD [PostalAddressPostalCode] NVARCHAR(20) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.Organisation') AND name = N'IX_Organisation_MainSdlNumber')
        CREATE INDEX [IX_Organisation_MainSdlNumber] ON dbo.Organisation([MainSdlNumber]);

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.Organisation') AND name = N'IX_Organisation_SetaId')
        CREATE INDEX [IX_Organisation_SetaId] ON dbo.Organisation([SetaId]);
END

-- 3. ORGANISATION SITE (SETMIS File 200)
IF OBJECT_ID(N'dbo.OrganisationSite', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.OrganisationSite') AND name = N'SiteNumber')
        ALTER TABLE dbo.OrganisationSite ADD [SiteNumber] NVARCHAR(20) NOT NULL CONSTRAINT DF_OrganisationSite_SiteNumber DEFAULT '001';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.OrganisationSite') AND name = N'CountryCode')
        ALTER TABLE dbo.OrganisationSite ADD [CountryCode] NVARCHAR(10) NOT NULL CONSTRAINT DF_OrganisationSite_CountryCode DEFAULT 'ZA';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.OrganisationSite') AND name = N'Latitude')
        ALTER TABLE dbo.OrganisationSite ADD [Latitude] DECIMAL(10, 6) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.OrganisationSite') AND name = N'Longitude')
        ALTER TABLE dbo.OrganisationSite ADD [Longitude] DECIMAL(10, 6) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.OrganisationSite') AND name = N'StatssaAreaCode')
        ALTER TABLE dbo.OrganisationSite ADD [StatssaAreaCode] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.OrganisationSite') AND name = N'PhoneNumber')
        ALTER TABLE dbo.OrganisationSite ADD [PhoneNumber] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.OrganisationSite') AND name = N'FaxNumber')
        ALTER TABLE dbo.OrganisationSite ADD [FaxNumber] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.OrganisationSite') AND name = N'Email')
        ALTER TABLE dbo.OrganisationSite ADD [Email] NVARCHAR(150) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.OrganisationSite') AND name = N'IX_OrganisationSite_SiteNumber')
        CREATE INDEX [IX_OrganisationSite_SiteNumber] ON dbo.OrganisationSite([SiteNumber]);

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.OrganisationSite') AND name = N'IX_OrganisationSite_StatssaAreaCode')
        CREATE INDEX [IX_OrganisationSite_StatssaAreaCode] ON dbo.OrganisationSite([StatssaAreaCode]);
END

-- 4. TRAINING PROVIDER (SETMIS File 100)
IF OBJECT_ID(N'dbo.TrainingProvider', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.TrainingProvider') AND name = N'ProviderCode')
        ALTER TABLE dbo.TrainingProvider ADD [ProviderCode] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.TrainingProvider') AND name = N'EtqaId')
        ALTER TABLE dbo.TrainingProvider ADD [EtqaId] NVARCHAR(10) NOT NULL CONSTRAINT DF_TrainingProvider_EtqaId DEFAULT '17';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.TrainingProvider') AND name = N'ProviderClassId')
        ALTER TABLE dbo.TrainingProvider ADD [ProviderClassId] NVARCHAR(10) NOT NULL CONSTRAINT DF_TrainingProvider_ProviderClassId DEFAULT '02';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.TrainingProvider') AND name = N'ProviderTypeId')
        ALTER TABLE dbo.TrainingProvider ADD [ProviderTypeId] NVARCHAR(10) NOT NULL CONSTRAINT DF_TrainingProvider_ProviderTypeId DEFAULT '02';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.TrainingProvider') AND name = N'ProviderStatusId')
        ALTER TABLE dbo.TrainingProvider ADD [ProviderStatusId] NVARCHAR(10) NOT NULL CONSTRAINT DF_TrainingProvider_ProviderStatusId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.TrainingProvider') AND name = N'SarsNumber')
        ALTER TABLE dbo.TrainingProvider ADD [SarsNumber] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.TrainingProvider') AND name = N'FaxNumber')
        ALTER TABLE dbo.TrainingProvider ADD [FaxNumber] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.TrainingProvider') AND name = N'WebsiteUrl')
        ALTER TABLE dbo.TrainingProvider ADD [WebsiteUrl] NVARCHAR(200) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.TrainingProvider') AND name = N'IX_TrainingProvider_ProviderCode')
        CREATE INDEX [IX_TrainingProvider_ProviderCode] ON dbo.TrainingProvider([ProviderCode]);

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.TrainingProvider') AND name = N'IX_TrainingProvider_ProviderClassId')
        CREATE INDEX [IX_TrainingProvider_ProviderClassId] ON dbo.TrainingProvider([ProviderClassId]);

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.TrainingProvider') AND name = N'IX_TrainingProvider_ProviderStatusId')
        CREATE INDEX [IX_TrainingProvider_ProviderStatusId] ON dbo.TrainingProvider([ProviderStatusId]);
END

-- 5. ETQA ASSESSOR (SETMIS File 401)
IF OBJECT_ID(N'dbo.EtqaAssessor', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.EtqaAssessor') AND name = N'DesignationTypeId')
        ALTER TABLE dbo.EtqaAssessor ADD [DesignationTypeId] NVARCHAR(10) NOT NULL CONSTRAINT DF_EtqaAssessor_DesignationTypeId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.EtqaAssessor') AND name = N'DesignationStructureStatusId')
        ALTER TABLE dbo.EtqaAssessor ADD [DesignationStructureStatusId] NVARCHAR(10) NOT NULL CONSTRAINT DF_EtqaAssessor_DesignationStructureStatusId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.EtqaAssessor') AND name = N'EtqaId')
        ALTER TABLE dbo.EtqaAssessor ADD [EtqaId] NVARCHAR(10) NOT NULL CONSTRAINT DF_EtqaAssessor_EtqaId DEFAULT '17';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.EtqaAssessor') AND name = N'EtqeDecisionNumber')
        ALTER TABLE dbo.EtqaAssessor ADD [EtqeDecisionNumber] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.EtqaAssessor') AND name = N'TrainingProviderId')
        ALTER TABLE dbo.EtqaAssessor ADD [TrainingProviderId] INT NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.EtqaAssessor') AND name = N'IX_EtqaAssessor_DesignationTypeId')
        CREATE INDEX [IX_EtqaAssessor_DesignationTypeId] ON dbo.EtqaAssessor([DesignationTypeId]);

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.EtqaAssessor') AND name = N'IX_EtqaAssessor_DesignationStructureStatusId')
        CREATE INDEX [IX_EtqaAssessor_DesignationStructureStatusId] ON dbo.EtqaAssessor([DesignationStructureStatusId]);
END

-- 6. COMPANY LEARNER (SETMIS Files 500, 501, 502, 506)
IF OBJECT_ID(N'dbo.CompanyLearner', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'OrganisationSiteId')
        ALTER TABLE dbo.CompanyLearner ADD [OrganisationSiteId] INT NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'LearnershipId')
        ALTER TABLE dbo.CompanyLearner ADD [LearnershipId] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'NonNqfInterventionCode')
        ALTER TABLE dbo.CompanyLearner ADD [NonNqfInterventionCode] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'PartOfId')
        ALTER TABLE dbo.CompanyLearner ADD [PartOfId] NVARCHAR(10) NOT NULL CONSTRAINT DF_CompanyLearner_PartOfId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'EnrolmentTypeId')
        ALTER TABLE dbo.CompanyLearner ADD [EnrolmentTypeId] NVARCHAR(10) NOT NULL CONSTRAINT DF_CompanyLearner_EnrolmentTypeId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'EnrolmentStatusId')
        ALTER TABLE dbo.CompanyLearner ADD [EnrolmentStatusId] NVARCHAR(10) NOT NULL CONSTRAINT DF_CompanyLearner_EnrolmentStatusId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'EnrolmentStatusDate')
        ALTER TABLE dbo.CompanyLearner ADD [EnrolmentStatusDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'EnrolmentStatusReasonId')
        ALTER TABLE dbo.CompanyLearner ADD [EnrolmentStatusReasonId] NVARCHAR(10) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'AssessorRegistrationNumber')
        ALTER TABLE dbo.CompanyLearner ADD [AssessorRegistrationNumber] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'AssessorEtqaId')
        ALTER TABLE dbo.CompanyLearner ADD [AssessorEtqaId] NVARCHAR(10) NOT NULL CONSTRAINT DF_CompanyLearner_AssessorEtqaId DEFAULT '17';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'PracticalProviderCode')
        ALTER TABLE dbo.CompanyLearner ADD [PracticalProviderCode] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'PracticalProviderEtqaId')
        ALTER TABLE dbo.CompanyLearner ADD [PracticalProviderEtqaId] NVARCHAR(10) NOT NULL CONSTRAINT DF_CompanyLearner_PracticalProviderEtqaId DEFAULT '17';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'OfoCode')
        ALTER TABLE dbo.CompanyLearner ADD [OfoCode] NVARCHAR(20) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'EconomicStatusId')
        ALTER TABLE dbo.CompanyLearner ADD [EconomicStatusId] NVARCHAR(10) NOT NULL CONSTRAINT DF_CompanyLearner_EconomicStatusId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'UrbanRuralId')
        ALTER TABLE dbo.CompanyLearner ADD [UrbanRuralId] NVARCHAR(10) NOT NULL CONSTRAINT DF_CompanyLearner_UrbanRuralId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'CumulativeSpend')
        ALTER TABLE dbo.CompanyLearner ADD [CumulativeSpend] DECIMAL(18, 2) NOT NULL CONSTRAINT DF_CompanyLearner_CumulativeSpend DEFAULT 0.00;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'CertificateNumber')
        ALTER TABLE dbo.CompanyLearner ADD [CertificateNumber] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'PriorQualificationId')
        ALTER TABLE dbo.CompanyLearner ADD [PriorQualificationId] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'PriorQualificationAchievementDate')
        ALTER TABLE dbo.CompanyLearner ADD [PriorQualificationAchievementDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'InternshipStatusId')
        ALTER TABLE dbo.CompanyLearner ADD [InternshipStatusId] NVARCHAR(10) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'FundingId')
        ALTER TABLE dbo.CompanyLearner ADD [FundingId] NVARCHAR(10) NOT NULL CONSTRAINT DF_CompanyLearner_FundingId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'IX_CompanyLearner_OrganisationSiteId')
        CREATE INDEX [IX_CompanyLearner_OrganisationSiteId] ON dbo.CompanyLearner([OrganisationSiteId]);

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'IX_CompanyLearner_LearnershipId')
        CREATE INDEX [IX_CompanyLearner_LearnershipId] ON dbo.CompanyLearner([LearnershipId]);

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'IX_CompanyLearner_OfoCode')
        CREATE INDEX [IX_CompanyLearner_OfoCode] ON dbo.CompanyLearner([OfoCode]);

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'IX_CompanyLearner_EnrolmentStatusId')
        CREATE INDEX [IX_CompanyLearner_EnrolmentStatusId] ON dbo.CompanyLearner([EnrolmentStatusId]);
END

-- 7. LEARNER ASSESSMENT (SETMIS File 503)
IF OBJECT_ID(N'dbo.LearnerAssessment', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'CompanyLearnerId')
        ALTER TABLE dbo.LearnerAssessment ADD [CompanyLearnerId] INT NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'TrainingProviderId')
        ALTER TABLE dbo.LearnerAssessment ADD [TrainingProviderId] INT NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'UnitStandardId')
        ALTER TABLE dbo.LearnerAssessment ADD [UnitStandardId] INT NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'UnitStandardTitle')
        ALTER TABLE dbo.LearnerAssessment ADD [UnitStandardTitle] NVARCHAR(250) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'PartOfId')
        ALTER TABLE dbo.LearnerAssessment ADD [PartOfId] NVARCHAR(10) NOT NULL CONSTRAINT DF_LearnerAssessment_PartOfId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'EnrolmentTypeId')
        ALTER TABLE dbo.LearnerAssessment ADD [EnrolmentTypeId] NVARCHAR(10) NOT NULL CONSTRAINT DF_LearnerAssessment_EnrolmentTypeId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'EnrolmentStatusId')
        ALTER TABLE dbo.LearnerAssessment ADD [EnrolmentStatusId] NVARCHAR(10) NOT NULL CONSTRAINT DF_LearnerAssessment_EnrolmentStatusId DEFAULT '02';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'EnrolmentStatusReasonId')
        ALTER TABLE dbo.LearnerAssessment ADD [EnrolmentStatusReasonId] NVARCHAR(10) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'AssessorRegistrationNumber')
        ALTER TABLE dbo.LearnerAssessment ADD [AssessorRegistrationNumber] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'AssessorEtqaId')
        ALTER TABLE dbo.LearnerAssessment ADD [AssessorEtqaId] NVARCHAR(10) NOT NULL CONSTRAINT DF_LearnerAssessment_AssessorEtqaId DEFAULT '17';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'CertificateNumber')
        ALTER TABLE dbo.LearnerAssessment ADD [CertificateNumber] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'CumulativeSpend')
        ALTER TABLE dbo.LearnerAssessment ADD [CumulativeSpend] DECIMAL(18, 2) NOT NULL CONSTRAINT DF_LearnerAssessment_CumulativeSpend DEFAULT 0.00;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'OfoCode')
        ALTER TABLE dbo.LearnerAssessment ADD [OfoCode] NVARCHAR(20) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'FundingId')
        ALTER TABLE dbo.LearnerAssessment ADD [FundingId] NVARCHAR(10) NOT NULL CONSTRAINT DF_LearnerAssessment_FundingId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'UrbanRuralId')
        ALTER TABLE dbo.LearnerAssessment ADD [UrbanRuralId] NVARCHAR(10) NOT NULL CONSTRAINT DF_LearnerAssessment_UrbanRuralId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'EconomicStatusId')
        ALTER TABLE dbo.LearnerAssessment ADD [EconomicStatusId] NVARCHAR(10) NOT NULL CONSTRAINT DF_LearnerAssessment_EconomicStatusId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'NonNqfInterventionCode')
        ALTER TABLE dbo.LearnerAssessment ADD [NonNqfInterventionCode] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'IX_LearnerAssessment_CompanyLearnerId')
        CREATE INDEX [IX_LearnerAssessment_CompanyLearnerId] ON dbo.LearnerAssessment([CompanyLearnerId]);

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.LearnerAssessment') AND name = N'IX_LearnerAssessment_UnitStandardId')
        CREATE INDEX [IX_LearnerAssessment_UnitStandardId] ON dbo.LearnerAssessment([UnitStandardId]);
END

-- 8. LEARNER TRADE TEST (SETMIS File 505)
IF OBJECT_ID(N'dbo.LearnerTradeTest', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerTradeTest') AND name = N'TradeTestCentreCode')
        ALTER TABLE dbo.LearnerTradeTest ADD [TradeTestCentreCode] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerTradeTest') AND name = N'TradeTestCentreEtqaId')
        ALTER TABLE dbo.LearnerTradeTest ADD [TradeTestCentreEtqaId] NVARCHAR(10) NOT NULL CONSTRAINT DF_LearnerTradeTest_TradeTestCentreEtqaId DEFAULT '17';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerTradeTest') AND name = N'TradeCode')
        ALTER TABLE dbo.LearnerTradeTest ADD [TradeCode] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerTradeTest') AND name = N'QualificationId')
        ALTER TABLE dbo.LearnerTradeTest ADD [QualificationId] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerTradeTest') AND name = N'TradeTestResultId')
        ALTER TABLE dbo.LearnerTradeTest ADD [TradeTestResultId] NVARCHAR(10) NOT NULL CONSTRAINT DF_LearnerTradeTest_TradeTestResultId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerTradeTest') AND name = N'TradeTestResultReasonId')
        ALTER TABLE dbo.LearnerTradeTest ADD [TradeTestResultReasonId] NVARCHAR(10) NOT NULL CONSTRAINT DF_LearnerTradeTest_TradeTestResultReasonId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerTradeTest') AND name = N'AssessorRegistrationNumber')
        ALTER TABLE dbo.LearnerTradeTest ADD [AssessorRegistrationNumber] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerTradeTest') AND name = N'AssessorEtqaId')
        ALTER TABLE dbo.LearnerTradeTest ADD [AssessorEtqaId] NVARCHAR(10) NOT NULL CONSTRAINT DF_LearnerTradeTest_AssessorEtqaId DEFAULT '17';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerTradeTest') AND name = N'ModeratorRegistrationNumber')
        ALTER TABLE dbo.LearnerTradeTest ADD [ModeratorRegistrationNumber] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerTradeTest') AND name = N'ModeratorEtqaId')
        ALTER TABLE dbo.LearnerTradeTest ADD [ModeratorEtqaId] NVARCHAR(10) NOT NULL CONSTRAINT DF_LearnerTradeTest_ModeratorEtqaId DEFAULT '17';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerTradeTest') AND name = N'TrainingProviderId')
        ALTER TABLE dbo.LearnerTradeTest ADD [TrainingProviderId] INT NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerTradeTest') AND name = N'TrainingProviderEtqaId')
        ALTER TABLE dbo.LearnerTradeTest ADD [TrainingProviderEtqaId] NVARCHAR(10) NOT NULL CONSTRAINT DF_LearnerTradeTest_TrainingProviderEtqaId DEFAULT '17';

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.LearnerTradeTest') AND name = N'IX_LearnerTradeTest_TradeTestCentreCode')
        CREATE INDEX [IX_LearnerTradeTest_TradeTestCentreCode] ON dbo.LearnerTradeTest([TradeTestCentreCode]);

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.LearnerTradeTest') AND name = N'IX_LearnerTradeTest_TradeTestResultId')
        CREATE INDEX [IX_LearnerTradeTest_TradeTestResultId] ON dbo.LearnerTradeTest([TradeTestResultId]);
END

-- 9. SKILLS REGISTRATION (SETMIS File 304)
IF OBJECT_ID(N'dbo.SkillsRegistration', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.SkillsRegistration') AND name = N'NonNqfIntervCode')
        ALTER TABLE dbo.SkillsRegistration ADD [NonNqfIntervCode] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.SkillsRegistration') AND name = N'NonNqfIntervName')
        ALTER TABLE dbo.SkillsRegistration ADD [NonNqfIntervName] NVARCHAR(200) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.SkillsRegistration') AND name = N'SubfieldId')
        ALTER TABLE dbo.SkillsRegistration ADD [SubfieldId] NVARCHAR(10) NOT NULL CONSTRAINT DF_SkillsRegistration_SubfieldId DEFAULT '06';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.SkillsRegistration') AND name = N'EtqaId')
        ALTER TABLE dbo.SkillsRegistration ADD [EtqaId] NVARCHAR(10) NOT NULL CONSTRAINT DF_SkillsRegistration_EtqaId DEFAULT '17';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.SkillsRegistration') AND name = N'NonNqfIntervStatusId')
        ALTER TABLE dbo.SkillsRegistration ADD [NonNqfIntervStatusId] NVARCHAR(10) NOT NULL CONSTRAINT DF_SkillsRegistration_NonNqfIntervStatusId DEFAULT '01';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.SkillsRegistration') AND name = N'LearningProgrammeTypeId')
        ALTER TABLE dbo.SkillsRegistration ADD [LearningProgrammeTypeId] NVARCHAR(10) NOT NULL CONSTRAINT DF_SkillsRegistration_LearningProgrammeTypeId DEFAULT '03';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.SkillsRegistration') AND name = N'RegistrationStartDate')
        ALTER TABLE dbo.SkillsRegistration ADD [RegistrationStartDate] DATETIME2 NOT NULL CONSTRAINT DF_SkillsRegistration_RegStartDate DEFAULT GETUTCDATE();

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.SkillsRegistration') AND name = N'RegistrationEndDate')
        ALTER TABLE dbo.SkillsRegistration ADD [RegistrationEndDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'dbo.SkillsRegistration') AND name = N'IX_SkillsRegistration_NonNqfIntervCode')
        CREATE INDEX [IX_SkillsRegistration_NonNqfIntervCode] ON dbo.SkillsRegistration([NonNqfIntervCode]);
END
";

            await db.Database.ExecuteSqlRawAsync(migrationSql);
            logger?.LogInformation("Successfully executed Phase 8: SETMIS Flat File Schema Alignment Migration.");
        }
        catch (Exception ex)
        {
            logger?.LogError(ex, "Error executing Phase 8 SETMIS Schema Alignment Migrator.");
        }
    }
}
