-- ====================================================================================================
-- MerSETA NSDMS — Complete Idempotent T-SQL Enterprise DDL Migration
-- Database Target: Microsoft SQL Server 2022 / SQL Server Express (NSDMS-NET)
-- Standards: Singular PascalCase, Auto-increment Integer Id, Audit Columns, FK/Search Indexes, Idempotent
-- ====================================================================================================

SET NOCOUNT ON;
PRINT 'Beginning Idempotent Enterprise DDL Deployment...';

-- 1. Ensure Lookup Schema
IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = N'lookup')
BEGIN
    EXEC('CREATE SCHEMA lookup AUTHORIZATION dbo;');
    PRINT 'Created schema [lookup].';
END

-- 2. Audit Trail
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'audit_logs' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.audit_logs (
        id BIGINT IDENTITY(1,1) NOT NULL CONSTRAINT PK_audit_logs PRIMARY KEY CLUSTERED,
        entity_name NVARCHAR(100) NOT NULL,
        record_id BIGINT NOT NULL,
        action_name NVARCHAR(100) NOT NULL,
        actor NVARCHAR(100) NOT NULL,
        timestamp DATETIME2(7) NOT NULL CONSTRAINT DF_audit_logs_timestamp DEFAULT SYSUTCDATETIME(),
        metadata_json NVARCHAR(MAX) NULL
    );
    CREATE NONCLUSTERED INDEX IX_audit_logs_entity ON dbo.audit_logs (entity_name, record_id);
    CREATE NONCLUSTERED INDEX IX_audit_logs_timestamp ON dbo.audit_logs (timestamp DESC);
    PRINT 'Created table [dbo].[audit_logs].';
END

-- 3. System Configuration & Feature Flags
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'SystemConfig' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.SystemConfig (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_SystemConfig PRIMARY KEY CLUSTERED,
        ConfigKey NVARCHAR(150) NOT NULL,
        ConfigValue NVARCHAR(MAX) NOT NULL,
        ConfigCategory NVARCHAR(50) NOT NULL,
        Description NVARCHAR(500) NULL,
        DataType NVARCHAR(50) NOT NULL CONSTRAINT DF_SystemConfig_DataType DEFAULT N'String',
        IsActive BIT NOT NULL CONSTRAINT DF_SystemConfig_IsActive DEFAULT 1,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_SystemConfig_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_SystemConfig_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX UX_SystemConfig_ConfigKey ON dbo.SystemConfig (ConfigKey);
    CREATE NONCLUSTERED INDEX IX_SystemConfig_Category ON dbo.SystemConfig (ConfigCategory);
    PRINT 'Created table [dbo].[SystemConfig].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'SystemFeatureFlag' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.SystemFeatureFlag (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_SystemFeatureFlag PRIMARY KEY CLUSTERED,
        FeatureKey NVARCHAR(150) NOT NULL,
        FeatureName NVARCHAR(150) NOT NULL,
        FeatureCategory NVARCHAR(50) NOT NULL,
        Description NVARCHAR(500) NULL,
        IsEnabled BIT NOT NULL CONSTRAINT DF_SystemFeatureFlag_IsEnabled DEFAULT 0,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_SystemFeatureFlag_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_SystemFeatureFlag_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX UX_SystemFeatureFlag_FeatureKey ON dbo.SystemFeatureFlag (FeatureKey);
    PRINT 'Created table [dbo].[SystemFeatureFlag].';
END

-- 4. Core Registries (Person, Organisation, TrainingProvider)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'Person' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.Person (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_Person PRIMARY KEY CLUSTERED,
        Title NVARCHAR(20) NULL,
        FirstName NVARCHAR(100) NOT NULL,
        MiddleName NVARCHAR(100) NULL,
        LastName NVARCHAR(100) NOT NULL,
        RsaIdNumber NVARCHAR(13) NOT NULL,
        PassportNumber NVARCHAR(50) NULL,
        DateOfBirth DATE NULL,
        GenderCode NVARCHAR(15) NULL,
        CitizenStatusCode NVARCHAR(15) NULL,
        EquityCode NVARCHAR(15) NULL,
        DisabilityCode NVARCHAR(15) NULL,
        NationalityCode NVARCHAR(15) NULL,
        HomeLanguageCode NVARCHAR(15) NULL,
        ProvinceCode NVARCHAR(15) NULL,
        Email NVARCHAR(150) NOT NULL,
        PhoneNumber NVARCHAR(50) NULL,
        CellNumber NVARCHAR(50) NULL,
        PhysicalAddress NVARCHAR(500) NULL,
        PostalAddress NVARCHAR(500) NULL,
        IsActive BIT NOT NULL CONSTRAINT DF_Person_IsActive DEFAULT 1,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_Person_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_Person_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX UX_Person_RsaIdNumber ON dbo.Person (RsaIdNumber) WHERE RsaIdNumber <> N'';
    CREATE NONCLUSTERED INDEX IX_Person_Email ON dbo.Person (Email);
    CREATE NONCLUSTERED INDEX IX_Person_Names ON dbo.Person (LastName, FirstName);
    PRINT 'Created table [dbo].[Person].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'Organisation' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.Organisation (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_Organisation PRIMARY KEY CLUSTERED,
        SdlNumber NVARCHAR(50) NOT NULL,
        CompanyName NVARCHAR(200) NOT NULL,
        TradingName NVARCHAR(200) NULL,
        RegistrationNumber NVARCHAR(50) NULL,
        TaxNumber NVARCHAR(50) NULL,
        LevyCategoryCode NVARCHAR(50) NULL,
        OrganisationStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Organisation_Status DEFAULT N'ACTIVE',
        ProvinceCode NVARCHAR(15) NULL,
        ChamberCode NVARCHAR(15) NULL,
        SicCode NVARCHAR(15) NULL,
        Email NVARCHAR(150) NULL,
        PhoneNumber NVARCHAR(50) NULL,
        PhysicalAddress NVARCHAR(500) NULL,
        PostalAddress NVARCHAR(500) NULL,
        IsActive BIT NOT NULL CONSTRAINT DF_Organisation_IsActive DEFAULT 1,
        IsManualChamberOverride BIT NOT NULL CONSTRAINT DF_Organisation_IsManualChamberOverride DEFAULT 0,
        ChamberOverrideReason NVARCHAR(500) NULL,
        ChamberOverrideDate DATETIME2(7) NULL,
        ChamberOverrideApprovedBy NVARCHAR(100) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_Organisation_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_Organisation_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX UX_Organisation_SdlNumber ON dbo.Organisation (SdlNumber);
    CREATE NONCLUSTERED INDEX IX_Organisation_CompanyName ON dbo.Organisation (CompanyName);
    CREATE NONCLUSTERED INDEX IX_Organisation_Status ON dbo.Organisation (OrganisationStatusCode);
    CREATE NONCLUSTERED INDEX IX_Organisation_IsManualChamberOverride ON dbo.Organisation (IsManualChamberOverride);
    PRINT 'Created table [dbo].[Organisation].';
END

-- 5. Learner Lifecycle & Contracts
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'CompanyLearner' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.CompanyLearner (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_CompanyLearner PRIMARY KEY CLUSTERED,
        PersonId INT NOT NULL CONSTRAINT FK_CompanyLearner_Person FOREIGN KEY REFERENCES dbo.Person(id),
        OrganisationId INT NOT NULL CONSTRAINT FK_CompanyLearner_Organisation FOREIGN KEY REFERENCES dbo.Organisation(id),
        TrainingProviderId INT NULL,
        LearnerContractNumber NVARCHAR(100) NULL,
        QualificationTitle NVARCHAR(250) NULL,
        SaqaQualificationId INT NULL,
        NqfLevel INT NULL,
        LearningProgrammeTypeCode NVARCHAR(50) NULL,
        FundingTypeCode NVARCHAR(50) NULL,
        EnrolmentStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_CompanyLearner_Status DEFAULT N'Registered',
        RegistrationDate DATETIME2(7) NOT NULL CONSTRAINT DF_CompanyLearner_RegDate DEFAULT SYSUTCDATETIME(),
        CommencementDate DATETIME2(7) NULL,
        CompletionDate DATETIME2(7) NULL,
        IsActive BIT NOT NULL CONSTRAINT DF_CompanyLearner_IsActive DEFAULT 1,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_CompanyLearner_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_CompanyLearner_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_CompanyLearner_PersonId ON dbo.CompanyLearner (PersonId);
    CREATE NONCLUSTERED INDEX IX_CompanyLearner_OrganisationId ON dbo.CompanyLearner (OrganisationId);
    CREATE NONCLUSTERED INDEX IX_CompanyLearner_ContractNumber ON dbo.CompanyLearner (LearnerContractNumber);
    CREATE NONCLUSTERED INDEX IX_CompanyLearner_Status ON dbo.CompanyLearner (EnrolmentStatusCode);
    PRINT 'Created table [dbo].[CompanyLearner].';
END

-- 6. Trade Tests & ARPL (Area 13)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'LearnerTradeTestApplication' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.LearnerTradeTestApplication (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_LearnerTradeTestApplication PRIMARY KEY CLUSTERED,
        CompanyLearnerId INT NOT NULL CONSTRAINT FK_TradeTest_Learner FOREIGN KEY REFERENCES dbo.CompanyLearner(id),
        PersonId INT NOT NULL CONSTRAINT FK_TradeTest_Person FOREIGN KEY REFERENCES dbo.Person(id),
        OrganisationId INT NULL CONSTRAINT FK_TradeTest_Org FOREIGN KEY REFERENCES dbo.Organisation(id),
        TrainingProviderId INT NULL,
        ApplicationNumber NVARCHAR(50) NOT NULL,
        TradeTitle NVARCHAR(200) NOT NULL,
        TradeOfoCode NVARCHAR(50) NULL,
        DesignatedTradeLevel NVARCHAR(50) NULL,
        ApplicationTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_TradeTest_TypeCode DEFAULT N'Section26D',
        AttemptNumber INT NOT NULL CONSTRAINT DF_TradeTest_Attempt DEFAULT 1,
        AssessmentCenterName NVARCHAR(200) NULL,
        AssessmentDate DATETIME2(7) NULL,
        ScheduledStartTime TIME NULL,
        NambSerialNumber NVARCHAR(100) NULL,
        NambDecisionStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_TradeTest_NambStatus DEFAULT N'Pending',
        CompetencyStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_TradeTest_Competency DEFAULT N'Pending',
        SerialCertificateNumber NVARCHAR(100) NULL,
        CertificateIssueDate DATETIME2(7) NULL,
        StatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_TradeTest_Status DEFAULT N'Draft',
        AssessorName NVARCHAR(150) NULL,
        AssessorRegistrationNumber NVARCHAR(100) NULL,
        ModeratorName NVARCHAR(150) NULL,
        ModeratorRegistrationNumber NVARCHAR(100) NULL,
        Notes NVARCHAR(MAX) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_TradeTest_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_TradeTest_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX UX_TradeTest_AppNo ON dbo.LearnerTradeTestApplication (ApplicationNumber);
    CREATE NONCLUSTERED INDEX IX_TradeTest_LearnerId ON dbo.LearnerTradeTestApplication (CompanyLearnerId);
    CREATE NONCLUSTERED INDEX IX_TradeTest_Status ON dbo.LearnerTradeTestApplication (StatusCode);
    CREATE NONCLUSTERED INDEX IX_TradeTest_NambSerial ON dbo.LearnerTradeTestApplication (NambSerialNumber);
    CREATE NONCLUSTERED INDEX IX_TradeTest_Certificate ON dbo.LearnerTradeTestApplication (SerialCertificateNumber);
    PRINT 'Created table [dbo].[LearnerTradeTestApplication].';
END

-- 7. Summative Assessment Reports & SOR (Area 14)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'SummativeAssessmentReport' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.SummativeAssessmentReport (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_SummativeAssessmentReport PRIMARY KEY CLUSTERED,
        CompanyLearnerId INT NOT NULL CONSTRAINT FK_Summative_Learner FOREIGN KEY REFERENCES dbo.CompanyLearner(id),
        PersonId INT NOT NULL CONSTRAINT FK_Summative_Person FOREIGN KEY REFERENCES dbo.Person(id),
        OrganisationId INT NULL CONSTRAINT FK_Summative_Org FOREIGN KEY REFERENCES dbo.Organisation(id),
        TrainingProviderId INT NULL,
        ReportNumber NVARCHAR(50) NOT NULL,
        QualificationTitle NVARCHAR(250) NOT NULL,
        SaqaQualificationId NVARCHAR(50) NULL,
        NqfLevel INT NOT NULL CONSTRAINT DF_Summative_Nqf DEFAULT 4,
        InterventionTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Summative_Intervention DEFAULT N'Learnership',
        AssessmentDate DATETIME2(7) NOT NULL CONSTRAINT DF_Summative_AssDate DEFAULT SYSUTCDATETIME(),
        ModerationDate DATETIME2(7) NULL,
        AssessorPersonId INT NULL CONSTRAINT FK_Summative_Assessor FOREIGN KEY REFERENCES dbo.Person(id),
        AssessorRegistrationNumber NVARCHAR(100) NULL,
        InternalModeratorPersonId INT NULL CONSTRAINT FK_Summative_Moderator FOREIGN KEY REFERENCES dbo.Person(id),
        InternalModeratorRegistrationNumber NVARCHAR(100) NULL,
        ExternalModeratorUserId NVARCHAR(100) NULL,
        ExternalModeratorApprovalDate DATETIME2(7) NULL,
        ExternalModeratorComments NVARCHAR(1000) NULL,
        TotalCreditsEarned INT NOT NULL CONSTRAINT DF_Summative_Earned DEFAULT 0,
        TotalCreditsRequired INT NOT NULL CONSTRAINT DF_Summative_Required DEFAULT 120,
        StatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Summative_Status DEFAULT N'Draft',
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_Summative_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_Summative_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX UX_Summative_ReportNumber ON dbo.SummativeAssessmentReport (ReportNumber);
    CREATE NONCLUSTERED INDEX IX_Summative_LearnerId ON dbo.SummativeAssessmentReport (CompanyLearnerId);
    CREATE NONCLUSTERED INDEX IX_Summative_Status ON dbo.SummativeAssessmentReport (StatusCode);
    PRINT 'Created table [dbo].[SummativeAssessmentReport].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'StatementOfResults' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.StatementOfResults (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_StatementOfResults PRIMARY KEY CLUSTERED,
        SummativeAssessmentReportId INT NOT NULL CONSTRAINT FK_SOR_Report FOREIGN KEY REFERENCES dbo.SummativeAssessmentReport(id),
        CompanyLearnerId INT NOT NULL CONSTRAINT FK_SOR_Learner FOREIGN KEY REFERENCES dbo.CompanyLearner(id),
        PersonId INT NOT NULL CONSTRAINT FK_SOR_Person FOREIGN KEY REFERENCES dbo.Person(id),
        SorSerialNumber NVARCHAR(100) NOT NULL,
        DateIssued DATETIME2(7) NOT NULL CONSTRAINT DF_SOR_DateIssued DEFAULT SYSUTCDATETIME(),
        TotalCreditsCertified INT NOT NULL,
        TamperProofHashSha256 NVARCHAR(100) NOT NULL,
        QrVerificationUrl NVARCHAR(500) NULL,
        IssuedByUserId NVARCHAR(100) NOT NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_SOR_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_SOR_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX UX_SOR_SerialNumber ON dbo.StatementOfResults (SorSerialNumber);
    CREATE NONCLUSTERED INDEX IX_SOR_Hash ON dbo.StatementOfResults (TamperProofHashSha256);
    CREATE NONCLUSTERED INDEX IX_SOR_LearnerId ON dbo.StatementOfResults (CompanyLearnerId);
    PRINT 'Created table [dbo].[StatementOfResults].';
END

-- 8. Qualifications Curriculum Development (Area 15)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'QualificationsCurriculumDevelopment' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.QualificationsCurriculumDevelopment (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_QCD PRIMARY KEY CLUSTERED,
        ApplicationNumber NVARCHAR(50) NOT NULL,
        QualificationTitle NVARCHAR(250) NOT NULL,
        SaqaQualificationId NVARCHAR(50) NULL,
        OfoCode NVARCHAR(50) NULL,
        NqfLevel INT NOT NULL CONSTRAINT DF_QCD_Nqf DEFAULT 4,
        TotalCreditsRequired INT NOT NULL CONSTRAINT DF_QCD_Credits DEFAULT 120,
        DevelopmentTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_QCD_DevType DEFAULT N'NewDevelopment',
        NationalDevelopmentPlanChecked BIT NOT NULL CONSTRAINT DF_QCD_NDP DEFAULT 1,
        NationalDevelopmentPlanEvidence NVARCHAR(MAX) NULL,
        NewGrowthPlanChecked BIT NOT NULL CONSTRAINT DF_QCD_NGP DEFAULT 1,
        NewGrowthPlanEvidence NVARCHAR(MAX) NULL,
        IndustrialPolicyActionPlanChecked BIT NOT NULL CONSTRAINT DF_QCD_IPAP DEFAULT 1,
        IndustrialPolicyActionPlanEvidence NVARCHAR(MAX) NULL,
        StrategicInfrastructureChecked BIT NOT NULL CONSTRAINT DF_QCD_SIP DEFAULT 0,
        PurposeOfQualification NVARCHAR(MAX) NULL,
        TargetLearnerAudience NVARCHAR(MAX) NULL,
        IndustryDemandJustification NVARCHAR(MAX) NULL,
        DevelopmentQualityPartner NVARCHAR(100) NOT NULL CONSTRAINT DF_QCD_DQP DEFAULT N'merSETA DQP',
        AssessmentQualityPartner NVARCHAR(100) NOT NULL CONSTRAINT DF_QCD_AQP DEFAULT N'merSETA AQP',
        OrganisationId INT NULL CONSTRAINT FK_QCD_Org FOREIGN KEY REFERENCES dbo.Organisation(id),
        WorkingGroupConvenedDate DATETIME2(7) NULL,
        PublicCommentClosingDate DATETIME2(7) NULL,
        SaqaSubmissionDate DATETIME2(7) NULL,
        SaqaRegistrationNumber NVARCHAR(100) NULL,
        SaqaRegistrationDate DATETIME2(7) NULL,
        StatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_QCD_Status DEFAULT N'Draft',
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_QCD_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_QCD_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX UX_QCD_AppNo ON dbo.QualificationsCurriculumDevelopment (ApplicationNumber);
    CREATE NONCLUSTERED INDEX IX_QCD_Status ON dbo.QualificationsCurriculumDevelopment (StatusCode);
    CREATE NONCLUSTERED INDEX IX_QCD_OfoCode ON dbo.QualificationsCurriculumDevelopment (OfoCode);
    PRINT 'Created table [dbo].[QualificationsCurriculumDevelopment].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'CurriculumWorkingGroupMember' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.CurriculumWorkingGroupMember (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_CWGM PRIMARY KEY CLUSTERED,
        QualificationsCurriculumDevelopmentId INT NOT NULL CONSTRAINT FK_CWGM_QCD REFERENCES dbo.QualificationsCurriculumDevelopment(id) ON DELETE CASCADE,
        PersonId INT NULL CONSTRAINT FK_CWGM_Person REFERENCES dbo.Person(id),
        MemberName NVARCHAR(150) NOT NULL,
        StakeholderRoleTitle NVARCHAR(100) NOT NULL CONSTRAINT DF_CWGM_Role DEFAULT N'IndustryExpert',
        OrganisationRepresented NVARCHAR(200) NOT NULL CONSTRAINT DF_CWGM_Org DEFAULT '',
        EmailAddress NVARCHAR(150) NOT NULL CONSTRAINT DF_CWGM_Email DEFAULT '',
        PhoneNumber NVARCHAR(50) NOT NULL CONSTRAINT DF_CWGM_Phone DEFAULT '',
        IsConfirmedAttendee BIT NOT NULL CONSTRAINT DF_CWGM_Confirmed DEFAULT 1,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_CWGM_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_CWGM_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_CWGM_QcdId ON dbo.CurriculumWorkingGroupMember (QualificationsCurriculumDevelopmentId);
    PRINT 'Created table [dbo].[CurriculumWorkingGroupMember].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'SkillsRegistration' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.SkillsRegistration (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_SkillsRegistration PRIMARY KEY CLUSTERED,
        QualificationsCurriculumDevelopmentId INT NOT NULL CONSTRAINT FK_SkillsReg_QCD REFERENCES dbo.QualificationsCurriculumDevelopment(id) ON DELETE CASCADE,
        NonNqfIntervCode NVARCHAR(50) NOT NULL,
        NonNqfIntervName NVARCHAR(200) NOT NULL,
        SubfieldId NVARCHAR(10) NOT NULL CONSTRAINT DF_SkillsReg_Subfield DEFAULT N'06',
        EtqaId NVARCHAR(10) NOT NULL CONSTRAINT DF_SkillsReg_Etqa DEFAULT N'17',
        NonNqfIntervStatusId NVARCHAR(10) NOT NULL CONSTRAINT DF_SkillsReg_Status DEFAULT N'01',
        LearningProgrammeTypeId NVARCHAR(10) NOT NULL CONSTRAINT DF_SkillsReg_ProgType DEFAULT N'03',
        RegistrationStartDate DATETIME2(7) NOT NULL CONSTRAINT DF_SkillsReg_StartDate DEFAULT SYSUTCDATETIME(),
        RegistrationEndDate DATETIME2(7) NULL,
        Credits INT NOT NULL CONSTRAINT DF_SkillsReg_Credits DEFAULT 30,
        NqfLevel INT NOT NULL CONSTRAINT DF_SkillsReg_Nqf DEFAULT 3,
        UnitStandardsIncludedJson NVARCHAR(MAX) NOT NULL CONSTRAINT DF_SkillsReg_UnitStandards DEFAULT N'[]',
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_SkillsReg_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_SkillsReg_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_SkillsReg_QcdId ON dbo.SkillsRegistration (QualificationsCurriculumDevelopmentId);
    CREATE NONCLUSTERED INDEX IX_SkillsReg_Code ON dbo.SkillsRegistration (NonNqfIntervCode);
    PRINT 'Created table [dbo].[SkillsRegistration].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'NonSetaCompany' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.NonSetaCompany (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_NonSetaCompany PRIMARY KEY CLUSTERED,
        CompanyName NVARCHAR(200) NOT NULL,
        SdlNumber NVARCHAR(50) NULL,
        PrimarySetaCode NVARCHAR(50) NOT NULL CONSTRAINT DF_NonSetaCompany_Seta DEFAULT N'W&RSETA',
        CompanyRegistrationNumber NVARCHAR(50) NULL,
        Email NVARCHAR(150) NOT NULL CONSTRAINT DF_NonSetaCompany_Email DEFAULT '',
        PhoneNumber NVARCHAR(50) NOT NULL CONSTRAINT DF_NonSetaCompany_Phone DEFAULT '',
        PhysicalAddress NVARCHAR(500) NULL,
        IsActive BIT NOT NULL CONSTRAINT DF_NonSetaCompany_Active DEFAULT 1,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_NonSetaCompany_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_NonSetaCompany_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_NonSetaCompany_Seta ON dbo.NonSetaCompany (PrimarySetaCode);
    PRINT 'Created table [dbo].[NonSetaCompany].';
END


-- 9. Non-SETA Qualifications (Area 16)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'NonSetaQualificationsCompletion' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.NonSetaQualificationsCompletion (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_NonSetaQualificationsCompletion PRIMARY KEY CLUSTERED,
        CompanyLearnerId INT NULL CONSTRAINT FK_NonSeta_Learner FOREIGN KEY REFERENCES dbo.CompanyLearner(id),
        PersonId INT NOT NULL CONSTRAINT FK_NonSeta_Person FOREIGN KEY REFERENCES dbo.Person(id),
        NonSetaCompanyId INT NULL,
        OriginatingSetaCode NVARCHAR(50) NOT NULL,
        QualificationTitle NVARCHAR(250) NOT NULL,
        SaqaQualificationId NVARCHAR(50) NULL,
        NqfLevel INT NOT NULL CONSTRAINT DF_NonSeta_Nqf DEFAULT 4,
        TotalCreditsAchieved INT NOT NULL CONSTRAINT DF_NonSeta_Credits DEFAULT 120,
        AchievementDate DATETIME2(7) NOT NULL CONSTRAINT DF_NonSeta_AchDate DEFAULT SYSUTCDATETIME(),
        ExternalCertificateNumber NVARCHAR(100) NOT NULL,
        VerificationStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_NonSeta_Status DEFAULT N'PendingVerification',
        EndorsementDate DATETIME2(7) NULL,
        EndorsedByUserId NVARCHAR(100) NULL,
        EndorsementNotes NVARCHAR(1000) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_NonSeta_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_NonSeta_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_NonSeta_PersonId ON dbo.NonSetaQualificationsCompletion (PersonId);
    CREATE NONCLUSTERED INDEX IX_NonSeta_Status ON dbo.NonSetaQualificationsCompletion (VerificationStatusCode);
    CREATE NONCLUSTERED INDEX IX_NonSeta_CertNo ON dbo.NonSetaQualificationsCompletion (ExternalCertificateNumber);
    PRINT 'Created table [dbo].[NonSetaQualificationsCompletion].';
END

-- 10. Advanced SARS Historical Levy Reconciliation (Area 17)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'SarsLevyReconAudit' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.SarsLevyReconAudit (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_SarsLevyReconAudit PRIMARY KEY CLUSTERED,
        FinancialYear NVARCHAR(10) NOT NULL,
        SchemeYear NVARCHAR(10) NOT NULL,
        SdlNumber NVARCHAR(50) NOT NULL,
        OrganisationId INT NULL CONSTRAINT FK_SarsAudit_Org FOREIGN KEY REFERENCES dbo.Organisation(id),
        TotalSarsLeviesReceived DECIMAL(18,2) NOT NULL CONSTRAINT DF_SarsAudit_Rec DEFAULT 0,
        TotalCalculatedLeviesExpected DECIMAL(18,2) NOT NULL CONSTRAINT DF_SarsAudit_Exp DEFAULT 0,
        VarianceAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_SarsAudit_Var DEFAULT 0,
        DiscrepancyReasonCode NVARCHAR(50) NOT NULL CONSTRAINT DF_SarsAudit_Reason DEFAULT N'ExactMatch',
        ClawbackActionRequired BIT NOT NULL CONSTRAINT DF_SarsAudit_ClawbackReq DEFAULT 0,
        ClawbackAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_SarsAudit_ClawbackAmt DEFAULT 0,
        ClawbackIssuedDate DATETIME2(7) NULL,
        ClawbackSettledDate DATETIME2(7) NULL,
        AuditStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_SarsAudit_Status DEFAULT N'Reconciled',
        AuditNotes NVARCHAR(MAX) NULL,
        AuditorUserId NVARCHAR(100) NOT NULL CONSTRAINT DF_SarsAudit_Auditor DEFAULT N'SYSTEM',
        ReconciliationDate DATETIME2(7) NOT NULL CONSTRAINT DF_SarsAudit_ReconDate DEFAULT SYSUTCDATETIME(),
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_SarsAudit_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_SarsAudit_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_SarsAudit_Year ON dbo.SarsLevyReconAudit (FinancialYear);
    CREATE NONCLUSTERED INDEX IX_SarsAudit_SdlNumber ON dbo.SarsLevyReconAudit (SdlNumber);
    CREATE NONCLUSTERED INDEX IX_SarsAudit_Status ON dbo.SarsLevyReconAudit (AuditStatusCode);
    PRINT 'Created table [dbo].[SarsLevyReconAudit].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'SarsSchemeYearCalculation' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.SarsSchemeYearCalculation (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_SarsSchemeYearCalculation PRIMARY KEY CLUSTERED,
        SchemeYear NVARCHAR(10) NOT NULL,
        MandatoryPercentage DECIMAL(18,2) NOT NULL CONSTRAINT DF_SarsCalc_Mandatory DEFAULT 20.0,
        DiscretionaryPercentage DECIMAL(18,2) NOT NULL CONSTRAINT DF_SarsCalc_Discretionary DEFAULT 49.5,
        AdminPercentage DECIMAL(18,2) NOT NULL CONSTRAINT DF_SarsCalc_Admin DEFAULT 10.5,
        QctoPercentage DECIMAL(18,2) NOT NULL CONSTRAINT DF_SarsCalc_Qcto DEFAULT 0.5,
        TotalPercentage DECIMAL(18,2) NOT NULL CONSTRAINT DF_SarsCalc_Total DEFAULT 80.5,
        AllowReturnsMandatory BIT NOT NULL CONSTRAINT DF_SarsCalc_RetMandatory DEFAULT 1,
        AllowInvoicesMandatory BIT NOT NULL CONSTRAINT DF_SarsCalc_InvMandatory DEFAULT 1,
        AllowReturnsDiscretionary BIT NOT NULL CONSTRAINT DF_SarsCalc_RetDiscretionary DEFAULT 1,
        AllowInvoicesDiscretionary BIT NOT NULL CONSTRAINT DF_SarsCalc_InvDiscretionary DEFAULT 1,
        StatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_SarsCalc_Status DEFAULT N'Active',
        Notes NVARCHAR(MAX) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_SarsCalc_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_SarsCalc_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_SarsCalc_SchemeYear ON dbo.SarsSchemeYearCalculation (SchemeYear);
    CREATE NONCLUSTERED INDEX IX_SarsCalc_Status ON dbo.SarsSchemeYearCalculation (StatusCode);
    PRINT 'Created table [dbo].[SarsSchemeYearCalculation].';
END

-- 11. Workplace Monitoring & Audits (Area 10)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'WorkplaceMonitoringSiteVisit' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.WorkplaceMonitoringSiteVisit (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_WorkplaceMonitoringSiteVisit PRIMARY KEY CLUSTERED,
        OrganisationId INT NOT NULL CONSTRAINT FK_Monitoring_Org FOREIGN KEY REFERENCES dbo.Organisation(id),
        ContactPersonId INT NOT NULL CONSTRAINT FK_Monitoring_ContactPerson FOREIGN KEY REFERENCES dbo.Person(id),
        MonitoringDate DATETIME2(7) NOT NULL,
        VisitTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Monitoring_VisitType DEFAULT N'ScheduledAudit',
        MonitoringOfficerUserId NVARCHAR(100) NOT NULL,
        ComplianceScore DECIMAL(5,2) NOT NULL CONSTRAINT DF_Monitoring_Score DEFAULT 100,
        HasOhsCompliance BIT NOT NULL CONSTRAINT DF_Monitoring_Ohs DEFAULT 1,
        HasQualifiedMentors BIT NOT NULL CONSTRAINT DF_Monitoring_Mentors DEFAULT 1,
        HasStructuredLogbooks BIT NOT NULL CONSTRAINT DF_Monitoring_Logbooks DEFAULT 1,
        HasSafeWorkEnvironment BIT NOT NULL CONSTRAINT DF_Monitoring_SafeWork DEFAULT 1,
        GeneralFindings NVARCHAR(MAX) NULL,
        CorrectiveActionsRequired NVARCHAR(MAX) NULL,
        FollowUpRequired BIT NOT NULL CONSTRAINT DF_Monitoring_FollowUp DEFAULT 0,
        FollowUpDueDate DATETIME2(7) NULL,
        MonitoringStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Monitoring_Status DEFAULT N'Draft',
        SignOffDate DATETIME2(7) NULL,
        SignOffOfficerUserId NVARCHAR(100) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_Monitoring_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_Monitoring_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_Monitoring_Org ON dbo.WorkplaceMonitoringSiteVisit (OrganisationId);
    CREATE NONCLUSTERED INDEX IX_Monitoring_ContactPerson ON dbo.WorkplaceMonitoringSiteVisit (ContactPersonId);
    CREATE NONCLUSTERED INDEX IX_Monitoring_Status ON dbo.WorkplaceMonitoringSiteVisit (MonitoringStatusCode);
    PRINT 'Created table [dbo].[WorkplaceMonitoringSiteVisit].';
END

-- 12. Banking Details & Dual-Signoff (Auxiliary Option A)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'BankingDetails' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.BankingDetails (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_BankingDetails PRIMARY KEY CLUSTERED,
        OrganisationId INT NULL CONSTRAINT FK_Bank_Org FOREIGN KEY REFERENCES dbo.Organisation(id),
        TrainingProviderId INT NULL CONSTRAINT FK_Bank_Provider FOREIGN KEY REFERENCES dbo.TrainingProvider(id),
        BankName NVARCHAR(100) NOT NULL,
        BranchCode NVARCHAR(50) NOT NULL,
        BranchName NVARCHAR(100) NULL,
        AccountNumber NVARCHAR(50) NOT NULL,
        AccountHolderName NVARCHAR(150) NOT NULL,
        AccountTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Bank_AccType DEFAULT N'Cheque',
        ConfirmationDocumentPath NVARCHAR(500) NULL,
        DocumentIssueDate DATETIME2(7) NULL,
        VerificationStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Bank_Status DEFAULT N'SubmittedForVerification',
        FirstSignoffDate DATETIME2(7) NULL,
        FirstSignoffUserId NVARCHAR(100) NULL,
        FirstSignoffApproved BIT NOT NULL CONSTRAINT DF_Bank_FirstSignoff DEFAULT 0,
        FirstSignoffNotes NVARCHAR(500) NULL,
        SecondSignoffDate DATETIME2(7) NULL,
        SecondSignoffUserId NVARCHAR(100) NULL,
        SecondSignoffApproved BIT NOT NULL CONSTRAINT DF_Bank_SecondSignoff DEFAULT 0,
        SecondSignoffNotes NVARCHAR(500) NULL,
        ErpVendorId NVARCHAR(50) NULL,
        ErpSyncDate DATETIME2(7) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_Bank_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_Bank_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_Bank_Org ON dbo.BankingDetails (OrganisationId);
    CREATE NONCLUSTERED INDEX IX_Bank_Provider ON dbo.BankingDetails (TrainingProviderId);
    CREATE NONCLUSTERED INDEX IX_Bank_Status ON dbo.BankingDetails (VerificationStatusCode);
    PRINT 'Created table [dbo].[BankingDetails].';
END

-- 13. SDF Appointments (Auxiliary Option B)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'SdfCompany' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.SdfCompany (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_SdfCompany PRIMARY KEY CLUSTERED,
        OrganisationId INT NOT NULL CONSTRAINT FK_Sdf_Org FOREIGN KEY REFERENCES dbo.Organisation(id),
        PersonId INT NOT NULL CONSTRAINT FK_Sdf_Person FOREIGN KEY REFERENCES dbo.Person(id),
        SdfTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Sdf_Type DEFAULT N'Primary',
        SdfStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Sdf_StatusCode DEFAULT N'PendingApproval',
        AppointmentStartDate DATETIME2(7) NOT NULL CONSTRAINT DF_Sdf_ApptStart DEFAULT SYSUTCDATETIME(),
        AppointmentEndDate DATETIME2(7) NULL,
        AppointmentLetterDocumentPath NVARCHAR(500) NULL,
        CompanyResolutionDocumentPath NVARCHAR(500) NULL,
        SignedAppointmentLetterReceived BIT NOT NULL CONSTRAINT DF_Sdf_SignedAppt DEFAULT 0,
        SignedAcceptanceDeclarationReceived BIT NOT NULL CONSTRAINT DF_Sdf_SignedDecl DEFAULT 0,
        AllowWspSubmission BIT NOT NULL CONSTRAINT DF_Sdf_Wsp DEFAULT 1,
        AllowDgApplication BIT NOT NULL CONSTRAINT DF_Sdf_AllowDg DEFAULT 1,
        AllowTrancheClaims BIT NOT NULL CONSTRAINT DF_Sdf_Tranche DEFAULT 0,
        IsActive BIT NOT NULL CONSTRAINT DF_Sdf_IsActive DEFAULT 1,
        StartDate DATETIME2(7) NULL,
        EndDate DATETIME2(7) NULL,
        AllowDiscretionaryGrantSubmission BIT NULL,
        ApprovalStatusCode NVARCHAR(50) NULL,
        ApprovalDate DATETIME2(7) NULL,
        ApprovedByUserId NVARCHAR(100) NULL,
        ApprovalComments NVARCHAR(500) NULL,
        TerminationReason NVARCHAR(500) NULL,
        TerminationDate DATETIME2(7) NULL,
        TerminatedByUserId NVARCHAR(100) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_Sdf_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_Sdf_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_Sdf_Org ON dbo.SdfCompany (OrganisationId);
    CREATE NONCLUSTERED INDEX IX_Sdf_Person ON dbo.SdfCompany (PersonId);
    CREATE NONCLUSTERED INDEX IX_Sdf_Status ON dbo.SdfCompany (SdfStatusCode);
    PRINT 'Created table [dbo].[SdfCompany].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'SdfAppointmentHistory' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.SdfAppointmentHistory (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_SdfAppointmentHistory PRIMARY KEY CLUSTERED,
        SdfCompanyId INT NOT NULL CONSTRAINT FK_SdfHist_SdfCompany FOREIGN KEY REFERENCES dbo.SdfCompany(id) ON DELETE CASCADE,
        PreviousStatusCode NVARCHAR(50) NOT NULL,
        NewStatusCode NVARCHAR(50) NOT NULL,
        ChangeReason NVARCHAR(MAX) NULL,
        ChangedByUserId NVARCHAR(100) NOT NULL CONSTRAINT DF_SdfHist_ChangedBy DEFAULT N'SYSTEM',
        ChangedAt DATETIME2(7) NOT NULL CONSTRAINT DF_SdfHist_ChangedAt DEFAULT SYSUTCDATETIME()
    );
    CREATE NONCLUSTERED INDEX IX_SdfHist_SdfCompanyId ON dbo.SdfAppointmentHistory (SdfCompanyId);
    PRINT 'Created table [dbo].[SdfAppointmentHistory].';
END

-- 14. Contract Addenda & Variations (Auxiliary Option C)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'ContractAddenda' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.ContractAddenda (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_ContractAddenda PRIMARY KEY CLUSTERED,
        GrantMoaId INT NOT NULL CONSTRAINT FK_Addenda_Moa FOREIGN KEY REFERENCES dbo.GrantMoa(id),
        AddendaNumber NVARCHAR(50) NOT NULL,
        VariationTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Addenda_Type DEFAULT N'TimelineExtension',
        OriginalContractValue DECIMAL(18,2) NOT NULL,
        RevisedContractValue DECIMAL(18,2) NOT NULL,
        OriginalEndDate DATETIME2(7) NOT NULL,
        RevisedEndDate DATETIME2(7) NOT NULL,
        MotivationReason NVARCHAR(1000) NOT NULL,
        StatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Addenda_Status DEFAULT N'Draft',
        LegalReviewerUserId NVARCHAR(100) NULL,
        LegalReviewDate DATETIME2(7) NULL,
        LegalReviewComments NVARCHAR(MAX) NULL,
        ExecutiveApprovedByUserId NVARCHAR(100) NULL,
        ExecutiveApprovalDate DATETIME2(7) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_Addenda_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_Addenda_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX UX_Addenda_Number ON dbo.ContractAddenda (AddendaNumber);
    CREATE NONCLUSTERED INDEX IX_Addenda_Moa ON dbo.ContractAddenda (GrantMoaId);
    CREATE NONCLUSTERED INDEX IX_Addenda_Status ON dbo.ContractAddenda (StatusCode);
    PRINT 'Created table [dbo].[ContractAddenda].';
END

-- 15. Scope Extensions & Flat-File Exports (Auxiliary Option D)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'SdpExtensionOfScope' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.SdpExtensionOfScope (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_SdpExtensionOfScope PRIMARY KEY CLUSTERED,
        TrainingProviderId INT NOT NULL CONSTRAINT FK_Scope_Provider FOREIGN KEY REFERENCES dbo.TrainingProvider(id),
        ApplicationNumber NVARCHAR(50) NOT NULL,
        QualificationTitle NVARCHAR(250) NOT NULL,
        SaqaQualificationId NVARCHAR(50) NULL,
        NqfLevel INT NOT NULL,
        Credits INT NOT NULL,
        ProgrammeTypeCode NVARCHAR(50) NOT NULL,
        SiteInspectionPassed BIT NOT NULL CONSTRAINT DF_Scope_Inspection DEFAULT 0,
        SiteInspectionDate DATETIME2(7) NULL,
        EvaluatorUserId NVARCHAR(100) NULL,
        StatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Scope_Status DEFAULT N'Submitted',
        EtqaCommitteeDecisionNumber NVARCHAR(100) NULL,
        DecisionDate DATETIME2(7) NULL,
        ApprovedByUserId NVARCHAR(100) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_Scope_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_Scope_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX UX_Scope_AppNo ON dbo.SdpExtensionOfScope (ApplicationNumber);
    CREATE NONCLUSTERED INDEX IX_Scope_Provider ON dbo.SdpExtensionOfScope (TrainingProviderId);
    PRINT 'Created table [dbo].[SdpExtensionOfScope].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'DhetFlatFileExportLog' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.DhetFlatFileExportLog (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_DhetFlatFileExportLog PRIMARY KEY CLUSTERED,
        ExportBatchNumber NVARCHAR(50) NOT NULL,
        FileCode NVARCHAR(50) NOT NULL,
        TargetDestination NVARCHAR(50) NOT NULL,
        FileName NVARCHAR(150) NOT NULL,
        FileContent NVARCHAR(MAX) NOT NULL,
        Sha256Checksum NVARCHAR(100) NOT NULL,
        TotalRecordCount INT NOT NULL,
        ExportDate DATETIME2(7) NOT NULL CONSTRAINT DF_DhetExport_Date DEFAULT SYSUTCDATETIME(),
        ExportedByUserId NVARCHAR(100) NOT NULL,
        SftpUploadSuccess BIT NOT NULL CONSTRAINT DF_DhetExport_Sftp DEFAULT 0,
        SftpTransmissionLog NVARCHAR(MAX) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_DhetExport_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_DhetExport_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX UX_DhetExport_Batch ON dbo.DhetFlatFileExportLog (ExportBatchNumber);
    PRINT 'Created table [dbo].[DhetFlatFileExportLog].';
END

-- 16. WSP Qualitative Surveys & Strategic Skills Priorities
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'WspSkillsGap' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.WspSkillsGap (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_WspSkillsGap PRIMARY KEY CLUSTERED,
        WspId INT NOT NULL CONSTRAINT FK_WspSkillsGap_Wsp FOREIGN KEY REFERENCES dbo.WspSubmission(id),
        OccupationTitle NVARCHAR(200) NOT NULL,
        OfoCode NVARCHAR(50) NULL,
        SkillGapDescription NVARCHAR(1000) NOT NULL,
        CauseOfGap NVARCHAR(500) NULL,
        PlannedIntervention NVARCHAR(500) NULL,
        PriorityLevel NVARCHAR(50) NOT NULL CONSTRAINT DF_WspSkillsGap_Priority DEFAULT N'High',
        TargetLearnerCount INT NOT NULL CONSTRAINT DF_WspSkillsGap_Learners DEFAULT 1,
        EstimatedBudget DECIMAL(18,2) NOT NULL CONSTRAINT DF_WspSkillsGap_Budget DEFAULT 0.0,
        IsActive BIT NOT NULL CONSTRAINT DF_WspSkillsGap_Active DEFAULT 1,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_WspSkillsGap_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_WspSkillsGap_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_WspSkillsGap_Wsp ON dbo.WspSkillsGap (WspId);
    PRINT 'Created table [dbo].[WspSkillsGap].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'WspTrainingImpactSurvey' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.WspTrainingImpactSurvey (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_WspTrainingImpactSurvey PRIMARY KEY CLUSTERED,
        WspId INT NOT NULL CONSTRAINT FK_WspImpact_Wsp FOREIGN KEY REFERENCES dbo.WspSubmission(id),
        SurveyCategory NVARCHAR(100) NOT NULL,
        QuestionText NVARCHAR(500) NOT NULL,
        RatingScore INT NOT NULL CONSTRAINT DF_WspImpact_Rating DEFAULT 4,
        QualitativeImpactNotes NVARCHAR(2000) NULL,
        EvidenceDocumentUrl NVARCHAR(500) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_WspImpact_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_WspImpact_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_WspImpact_Wsp ON dbo.WspTrainingImpactSurvey (WspId);
    PRINT 'Created table [dbo].[WspTrainingImpactSurvey].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'WspStrategicPriority' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.WspStrategicPriority (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_WspStrategicPriority PRIMARY KEY CLUSTERED,
        WspId INT NOT NULL CONSTRAINT FK_WspPriority_Wsp FOREIGN KEY REFERENCES dbo.WspSubmission(id),
        PriorityCode NVARCHAR(50) NOT NULL,
        StrategicObjective NVARCHAR(500) NOT NULL,
        AlignmentDescription NVARCHAR(1000) NULL,
        AllocatedBudget DECIMAL(18,2) NOT NULL CONSTRAINT DF_WspPriority_Budget DEFAULT 0.0,
        IsAlignedWithNsdp BIT NOT NULL CONSTRAINT DF_WspPriority_Nsdp DEFAULT 1,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_WspPriority_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_WspPriority_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_WspPriority_Wsp ON dbo.WspStrategicPriority (WspId);
    PRINT 'Created table [dbo].[WspStrategicPriority].';
END

-- 17. AQP (Assessment Quality Partner) & EISA Assessments
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'AqpPartner' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.AqpPartner (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AqpPartner PRIMARY KEY CLUSTERED,
        AqpName NVARCHAR(250) NOT NULL,
        AqpCode NVARCHAR(50) NOT NULL,
        AccreditationNumber NVARCHAR(100) NOT NULL,
        QualityAssuranceBody NVARCHAR(100) NOT NULL CONSTRAINT DF_Aqp_QA DEFAULT N'QCTO',
        ContactPersonId INT NULL CONSTRAINT FK_Aqp_ContactPerson FOREIGN KEY REFERENCES dbo.Person(id),
        Email NVARCHAR(150) NULL,
        PhoneNumber NVARCHAR(50) NULL,
        PhysicalAddress NVARCHAR(300) NULL,
        PostalCode NVARCHAR(20) NULL,
        ProvinceCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Aqp_Province DEFAULT N'GP',
        AccreditationStartDate DATETIME2(7) NOT NULL,
        AccreditationEndDate DATETIME2(7) NOT NULL,
        StatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Aqp_Status DEFAULT N'Active',
        IsActive BIT NOT NULL CONSTRAINT DF_Aqp_Active DEFAULT 1,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_Aqp_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_Aqp_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX UX_Aqp_Code ON dbo.AqpPartner (AqpCode);
    CREATE UNIQUE NONCLUSTERED INDEX UX_Aqp_Accreditation ON dbo.AqpPartner (AccreditationNumber);
    CREATE NONCLUSTERED INDEX IX_Aqp_ContactPerson ON dbo.AqpPartner (ContactPersonId);
    PRINT 'Created table [dbo].[AqpPartner].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'AqpQualificationScope' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.AqpQualificationScope (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AqpQualificationScope PRIMARY KEY CLUSTERED,
        AqpPartnerId INT NOT NULL CONSTRAINT FK_AqpScope_Partner FOREIGN KEY REFERENCES dbo.AqpPartner(id),
        QualificationTitle NVARCHAR(250) NOT NULL,
        SaqaQualificationId NVARCHAR(50) NULL,
        NqfLevel INT NOT NULL CONSTRAINT DF_AqpScope_Nqf DEFAULT 4,
        CurriculumCode NVARCHAR(50) NULL,
        AssessmentModel NVARCHAR(50) NOT NULL CONSTRAINT DF_AqpScope_Model DEFAULT N'EISA',
        IsActive BIT NOT NULL CONSTRAINT DF_AqpScope_Active DEFAULT 1,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AqpScope_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AqpScope_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_AqpScope_Partner ON dbo.AqpQualificationScope (AqpPartnerId);
    PRINT 'Created table [dbo].[AqpQualificationScope].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'AqpLearnerAssessment' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.AqpLearnerAssessment (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AqpLearnerAssessment PRIMARY KEY CLUSTERED,
        AqpPartnerId INT NOT NULL CONSTRAINT FK_AqpAssessment_Partner FOREIGN KEY REFERENCES dbo.AqpPartner(id),
        CompanyLearnerId INT NULL CONSTRAINT FK_AqpAssessment_Learner FOREIGN KEY REFERENCES dbo.CompanyLearner(id),
        PersonId INT NULL CONSTRAINT FK_AqpAssessment_Person FOREIGN KEY REFERENCES dbo.Person(id),
        AssessmentNumber NVARCHAR(50) NOT NULL,
        EisaExamSession NVARCHAR(100) NOT NULL,
        AssessmentDate DATETIME2(7) NOT NULL,
        AssessmentCenter NVARCHAR(200) NOT NULL,
        TheoryScorePercentage DECIMAL(5,2) NULL,
        PracticalScorePercentage DECIMAL(5,2) NULL,
        FinalOverallPercentage DECIMAL(5,2) NOT NULL CONSTRAINT DF_AqpAssessment_Final DEFAULT 0.0,
        ResultStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_AqpAssessment_Result DEFAULT N'Pending',
        ModerationStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_AqpAssessment_Moderation DEFAULT N'Pending',
        CertificateNumber NVARCHAR(100) NULL,
        CertificateIssuedDate DATETIME2(7) NULL,
        ModeratorComments NVARCHAR(1000) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AqpAssessment_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AqpAssessment_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX UX_AqpAssessment_Number ON dbo.AqpLearnerAssessment (AssessmentNumber);
    CREATE NONCLUSTERED INDEX IX_AqpAssessment_Partner ON dbo.AqpLearnerAssessment (AqpPartnerId);
    CREATE NONCLUSTERED INDEX IX_AqpAssessment_Learner ON dbo.AqpLearnerAssessment (CompanyLearnerId);
    PRINT 'Created table [dbo].[AqpLearnerAssessment].';
END

-- 28. Trade Mentor Ratio Policies & Cascading Policy Engine
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'TradeMentorRatioPolicy' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.TradeMentorRatioPolicy (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_TradeMentorRatioPolicy PRIMARY KEY CLUSTERED,
        TradeCode NVARCHAR(50) NOT NULL,
        TradeTitle NVARCHAR(200) NOT NULL,
        TradeOfoCode NVARCHAR(50) NULL,
        SaqaQualificationId INT NULL,
        StandardRatio INT NOT NULL CONSTRAINT DF_TradeMentorRatioPolicy_Ratio DEFAULT 4,
        MaxAllowedRatio INT NOT NULL CONSTRAINT DF_TradeMentorRatioPolicy_Max DEFAULT 6,
        MinExperienceYearsRequired INT NOT NULL CONSTRAINT DF_TradeMentorRatioPolicy_Exp DEFAULT 3,
        EnforceStrictly BIT NOT NULL CONSTRAINT DF_TradeMentorRatioPolicy_Strict DEFAULT 1,
        IsActive BIT NOT NULL CONSTRAINT DF_TradeMentorRatioPolicy_Active DEFAULT 1,
        Notes NVARCHAR(500) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_TradeMentorRatioPolicy_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_TradeMentorRatioPolicy_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX UX_TradeMentorRatioPolicy_TradeCode ON dbo.TradeMentorRatioPolicy (TradeCode);
    CREATE NONCLUSTERED INDEX IX_TradeMentorRatioPolicy_Ofo ON dbo.TradeMentorRatioPolicy (TradeOfoCode);
    CREATE NONCLUSTERED INDEX IX_TradeMentorRatioPolicy_Saqa ON dbo.TradeMentorRatioPolicy (SaqaQualificationId);
    CREATE NONCLUSTERED INDEX IX_TradeMentorRatioPolicy_Active ON dbo.TradeMentorRatioPolicy (IsActive);
    PRINT 'Created table [dbo].[TradeMentorRatioPolicy].';
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'Organisation')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Organisation') AND name = 'IsMentorRatioEnforced')
        ALTER TABLE dbo.Organisation ADD IsMentorRatioEnforced BIT NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Organisation') AND name = 'MentorRatioExemptionReason')
        ALTER TABLE dbo.Organisation ADD MentorRatioExemptionReason NVARCHAR(500) NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Organisation') AND name = 'CustomMentorRatioCap')
        ALTER TABLE dbo.Organisation ADD CustomMentorRatioCap INT NULL;
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkplaceApproval')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('WorkplaceApproval') AND name = 'TradeCode')
        ALTER TABLE dbo.WorkplaceApproval ADD TradeCode NVARCHAR(50) NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('WorkplaceApproval') AND name = 'IsRatioEnforced')
        ALTER TABLE dbo.WorkplaceApproval ADD IsRatioEnforced BIT NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('WorkplaceApproval') AND name = 'CustomTradeRatio')
        ALTER TABLE dbo.WorkplaceApproval ADD CustomTradeRatio INT NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('WorkplaceApproval') AND name = 'MentorRatioExemptionNotes')
        ALTER TABLE dbo.WorkplaceApproval ADD MentorRatioExemptionNotes NVARCHAR(500) NULL;
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkplaceApprovalMentor')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('WorkplaceApprovalMentor') AND name = 'MaxLearnerCapacity')
        ALTER TABLE dbo.WorkplaceApprovalMentor ADD MaxLearnerCapacity INT NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('WorkplaceApprovalMentor') AND name = 'IsRatioExempt')
        ALTER TABLE dbo.WorkplaceApprovalMentor ADD IsRatioExempt BIT NOT NULL CONSTRAINT DF_WPAMentor_Exempt DEFAULT 0;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('WorkplaceApprovalMentor') AND name = 'IsRatioEnforced')
        ALTER TABLE dbo.WorkplaceApprovalMentor ADD IsRatioEnforced BIT NOT NULL CONSTRAINT DF_WPAMentor_Enforced DEFAULT 1;
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantApplication')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'WspSubmissionId')
        ALTER TABLE dbo.GrantApplication ADD WspSubmissionId INT NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'IsWspCompliant')
        ALTER TABLE dbo.GrantApplication ADD IsWspCompliant BIT NOT NULL CONSTRAINT DF_GrantApp_WspCompliant DEFAULT 0;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'IsWspExempt')
        ALTER TABLE dbo.GrantApplication ADD IsWspExempt BIT NOT NULL CONSTRAINT DF_GrantApp_WspExempt DEFAULT 0;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'WspExemptionReason')
        ALTER TABLE dbo.GrantApplication ADD WspExemptionReason NVARCHAR(500) NULL;
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_GrantApplication_WspSubmission' AND object_id = OBJECT_ID('GrantApplication'))
        CREATE NONCLUSTERED INDEX IX_GrantApplication_WspSubmission ON dbo.GrantApplication (WspSubmissionId);
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_GrantApplication_Org_FundingWindow_Unique' AND object_id = OBJECT_ID('GrantApplication'))
        CREATE UNIQUE NONCLUSTERED INDEX IX_GrantApplication_Org_FundingWindow_Unique ON dbo.GrantApplication (OrganisationId, FundingWindowId) WHERE FundingWindowId IS NOT NULL;
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantFundingWindow')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'Description')
        ALTER TABLE dbo.GrantFundingWindow ADD Description NVARCHAR(MAX) NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'ApprovalStatusCode')
        ALTER TABLE dbo.GrantFundingWindow ADD ApprovalStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_GrantFundingWindow_ApprovalStatus DEFAULT 'Active';
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'ProposedByUserId')
        ALTER TABLE dbo.GrantFundingWindow ADD ProposedByUserId NVARCHAR(100) NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'ProposedDate')
        ALTER TABLE dbo.GrantFundingWindow ADD ProposedDate DATETIME2 NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'ApprovedByUserId')
        ALTER TABLE dbo.GrantFundingWindow ADD ApprovedByUserId NVARCHAR(100) NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'ApprovedDate')
        ALTER TABLE dbo.GrantFundingWindow ADD ApprovedDate DATETIME2 NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'ApprovalJustification')
        ALTER TABLE dbo.GrantFundingWindow ADD ApprovalJustification NVARCHAR(1000) NULL;
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_GrantFundingWindow_ApprovalStatus' AND object_id = OBJECT_ID('GrantFundingWindow'))
        CREATE NONCLUSTERED INDEX IX_GrantFundingWindow_ApprovalStatus ON dbo.GrantFundingWindow (ApprovalStatusCode);
END

-- 29. Training Committees, Members & WSP Disputes
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'TrainingCommittee' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.TrainingCommittee (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_TrainingCommittee PRIMARY KEY CLUSTERED,
        OrganisationId INT NOT NULL CONSTRAINT FK_TrainingCommittee_Org FOREIGN KEY REFERENCES dbo.Organisation(id),
        FinancialYear INT NOT NULL CONSTRAINT DF_TrainingCommittee_Year DEFAULT 2026,
        CommitteeStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_TrainingCommittee_Status DEFAULT N'Active',
        ConstitutionalQuorumMet BIT NOT NULL CONSTRAINT DF_TrainingCommittee_Quorum DEFAULT 1,
        LastMeetingDate DATETIME2(7) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_TrainingCommittee_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_TrainingCommittee_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_TrainingCommittee_Org ON dbo.TrainingCommittee (OrganisationId);
    CREATE NONCLUSTERED INDEX IX_TrainingCommittee_Year ON dbo.TrainingCommittee (FinancialYear);
    PRINT 'Created table [dbo].[TrainingCommittee].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'TrainingCommitteeMember' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.TrainingCommitteeMember (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_TrainingCommitteeMember PRIMARY KEY CLUSTERED,
        TrainingCommitteeId INT NOT NULL CONSTRAINT FK_TrainingCommitteeMember_Committee FOREIGN KEY REFERENCES dbo.TrainingCommittee(id) ON DELETE CASCADE,
        PersonId INT NOT NULL CONSTRAINT FK_TrainingCommitteeMember_Person FOREIGN KEY REFERENCES dbo.Person(id),
        MemberRoleCode NVARCHAR(50) NOT NULL CONSTRAINT DF_TrainingCommitteeMember_Role DEFAULT N'UnionRepresentative',
        Constituency NVARCHAR(50) NOT NULL CONSTRAINT DF_TrainingCommitteeMember_Const DEFAULT N'NUMSA',
        IsActive BIT NOT NULL CONSTRAINT DF_TrainingCommitteeMember_Active DEFAULT 1,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_TrainingCommitteeMember_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_TrainingCommitteeMember_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_TrainingCommitteeMember_Committee ON dbo.TrainingCommitteeMember (TrainingCommitteeId);
    CREATE NONCLUSTERED INDEX IX_TrainingCommitteeMember_Person ON dbo.TrainingCommitteeMember (PersonId);
    PRINT 'Created table [dbo].[TrainingCommitteeMember].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'WspDispute' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.WspDispute (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_WspDispute PRIMARY KEY CLUSTERED,
        OrganisationId INT NOT NULL CONSTRAINT FK_WspDispute_Org FOREIGN KEY REFERENCES dbo.Organisation(id),
        WspSubmissionId INT NULL CONSTRAINT FK_WspDispute_Wsp FOREIGN KEY REFERENCES dbo.WspSubmission(id),
        DisputeReferenceNumber NVARCHAR(50) NOT NULL,
        DisputeReasonCode NVARCHAR(50) NOT NULL CONSTRAINT DF_WspDispute_Reason DEFAULT N'UnionRefusalToSign',
        Description NVARCHAR(2000) NOT NULL,
        DisputeStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_WspDispute_Status DEFAULT N'Logged',
        ResolutionDate DATETIME2(7) NULL,
        ResolutionNotes NVARCHAR(2000) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_WspDispute_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_WspDispute_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_WspDispute_Org ON dbo.WspDispute (OrganisationId);
    CREATE NONCLUSTERED INDEX IX_WspDispute_Ref ON dbo.WspDispute (DisputeReferenceNumber);
    CREATE NONCLUSTERED INDEX IX_WspDispute_Status ON dbo.WspDispute (DisputeStatusCode);
    CREATE NONCLUSTERED INDEX IX_WspDispute_Wsp ON dbo.WspDispute (WspSubmissionId);
    PRINT 'Created table [dbo].[WspDispute].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'WspSkillsGap' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.WspSkillsGap (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_WspSkillsGap PRIMARY KEY CLUSTERED,
        OrganisationId INT NOT NULL CONSTRAINT FK_WspSkillsGap_Org FOREIGN KEY REFERENCES dbo.Organisation(id),
        FinancialYear INT NOT NULL CONSTRAINT DF_WspSkillsGap_Year DEFAULT 2026,
        OfoCode NVARCHAR(50) NOT NULL CONSTRAINT DF_WspSkillsGap_Ofo DEFAULT N'651202',
        OccupationTitle NVARCHAR(200) NOT NULL CONSTRAINT DF_WspSkillsGap_Title DEFAULT N'Welder',
        HardToFillVacanciesCount INT NOT NULL CONSTRAINT DF_WspSkillsGap_Vacancies DEFAULT 0,
        SkillsGapReason NVARCHAR(1000) NOT NULL CONSTRAINT DF_WspSkillsGap_Reason DEFAULT N'',
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_WspSkillsGap_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_WspSkillsGap_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_WspSkillsGap_Org ON dbo.WspSkillsGap (OrganisationId);
    CREATE NONCLUSTERED INDEX IX_WspSkillsGap_Year ON dbo.WspSkillsGap (FinancialYear);
    PRINT 'Created table [dbo].[WspSkillsGap].';
END

-- 30. DG Project Implementation Plans (PIP) & Payment Claims
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'ProjectImplementationPlan' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.ProjectImplementationPlan (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_ProjectImplementationPlan PRIMARY KEY CLUSTERED,
        OrganisationId INT NOT NULL CONSTRAINT FK_PIP_Org FOREIGN KEY REFERENCES dbo.Organisation(id),
        FundingWindowId INT NULL CONSTRAINT FK_PIP_Window FOREIGN KEY REFERENCES dbo.GrantFundingWindow(id),
        GrantApplicationId INT NULL CONSTRAINT FK_PIP_App FOREIGN KEY REFERENCES dbo.GrantApplication(id),
        PlanReferenceNumber NVARCHAR(50) NOT NULL,
        InterventionTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_PIP_Intervention DEFAULT N'Learnership',
        TotalAwardedAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_PIP_TotalAwarded DEFAULT 0.00,
        RecoverableAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_PIP_Recoverable DEFAULT 0.00,
        TotalLearnersAwarded INT NOT NULL CONSTRAINT DF_PIP_TotalLearners DEFAULT 0,
        LearnersWithDisabilityCount INT NOT NULL CONSTRAINT DF_PIP_Disabled DEFAULT 0,
        StatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_PIP_Status DEFAULT N'Draft',
        ContractSignOffDate DATETIME2(7) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_PIP_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_PIP_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_PIP_Org ON dbo.ProjectImplementationPlan (OrganisationId);
    CREATE NONCLUSTERED INDEX IX_PIP_Window ON dbo.ProjectImplementationPlan (FundingWindowId);
    CREATE NONCLUSTERED INDEX IX_PIP_App ON dbo.ProjectImplementationPlan (GrantApplicationId);
    CREATE NONCLUSTERED INDEX IX_PIP_Ref ON dbo.ProjectImplementationPlan (PlanReferenceNumber);
    CREATE NONCLUSTERED INDEX IX_PIP_Status ON dbo.ProjectImplementationPlan (StatusCode);
    PRINT 'Created table [dbo].[ProjectImplementationPlan].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'PipLearnerAllocation' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.PipLearnerAllocation (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_PipLearnerAllocation PRIMARY KEY CLUSTERED,
        ProjectImplementationPlanId INT NOT NULL CONSTRAINT FK_PipAlloc_Plan FOREIGN KEY REFERENCES dbo.ProjectImplementationPlan(id) ON DELETE CASCADE,
        SaqaQualificationId INT NULL,
        QualificationTitle NVARCHAR(250) NULL,
        LearnerCount INT NOT NULL CONSTRAINT DF_PipAlloc_Count DEFAULT 0,
        UnitCost DECIMAL(18,2) NOT NULL CONSTRAINT DF_PipAlloc_UnitCost DEFAULT 0.00,
        TotalAllowanceBudget DECIMAL(18,2) NOT NULL CONSTRAINT DF_PipAlloc_Allowance DEFAULT 0.00,
        TotalTuitionBudget DECIMAL(18,2) NOT NULL CONSTRAINT DF_PipAlloc_Tuition DEFAULT 0.00,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_PipAlloc_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_PipAlloc_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_PipAlloc_Plan ON dbo.PipLearnerAllocation (ProjectImplementationPlanId);
    PRINT 'Created table [dbo].[PipLearnerAllocation].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'GrantPaymentClaim' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.GrantPaymentClaim (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_GrantPaymentClaim PRIMARY KEY CLUSTERED,
        ProjectImplementationPlanId INT NOT NULL CONSTRAINT FK_GrantClaim_Plan FOREIGN KEY REFERENCES dbo.ProjectImplementationPlan(id) ON DELETE CASCADE,
        ClaimNumber NVARCHAR(50) NOT NULL,
        TrancheNumber INT NOT NULL CONSTRAINT DF_GrantClaim_Tranche DEFAULT 1,
        ClaimAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_GrantClaim_Amount DEFAULT 0.00,
        DeliverableDescription NVARCHAR(1000) NOT NULL CONSTRAINT DF_GrantClaim_Deliverable DEFAULT N'',
        StatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_GrantClaim_Status DEFAULT N'PendingSubmission',
        ApprovalDate DATETIME2(7) NULL,
        ApprovedByUserId NVARCHAR(100) NULL,
        ErpBatchNumber NVARCHAR(100) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_GrantClaim_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_GrantClaim_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_GrantClaim_Plan ON dbo.GrantPaymentClaim (ProjectImplementationPlanId);
    CREATE NONCLUSTERED INDEX IX_GrantClaim_Number ON dbo.GrantPaymentClaim (ClaimNumber);
    CREATE NONCLUSTERED INDEX IX_GrantClaim_Status ON dbo.GrantPaymentClaim (StatusCode);
    PRINT 'Created table [dbo].[GrantPaymentClaim].';
END

-- 44. Option A: SARS Monthly Skills Development Levy Ingestion, Staging & Ledger
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'LevyFile' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.LevyFile (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_LevyFile PRIMARY KEY CLUSTERED,
        FileName NVARCHAR(255) NOT NULL,
        FileRef NVARCHAR(100) NOT NULL CONSTRAINT DF_LevyFile_FileRef DEFAULT N'',
        ImportDate DATETIME2(7) NOT NULL CONSTRAINT DF_LevyFile_ImportDate DEFAULT SYSUTCDATETIME(),
        TotalRecords INT NOT NULL CONSTRAINT DF_LevyFile_TotalRecords DEFAULT 0,
        TotalAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_LevyFile_TotalAmount DEFAULT 0.00,
        ImportStatusCode NVARCHAR(25) NOT NULL CONSTRAINT DF_LevyFile_StatusCode DEFAULT N'Imported',
        DigitalSecuritySeal NVARCHAR(64) NULL,
        ControlRecordCount INT NULL,
        ControlTotalAmount DECIMAL(18,2) NULL,
        IsControlValidated BIT NOT NULL CONSTRAINT DF_LevyFile_IsControlValidated DEFAULT 0,
        ProcessingDurationMs BIGINT NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_LevyFile_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_LevyFile_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_LevyFile_FileRef ON dbo.LevyFile (FileRef);
    CREATE NONCLUSTERED INDEX IX_LevyFile_ImportDate ON dbo.LevyFile (ImportDate);
    CREATE NONCLUSTERED INDEX IX_LevyFile_ImportStatusCode ON dbo.LevyFile (ImportStatusCode);
    CREATE NONCLUSTERED INDEX IX_LevyFile_DigitalSecuritySeal ON dbo.LevyFile (DigitalSecuritySeal);
    PRINT 'Created table [dbo].[LevyFile].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'LevyFileLine' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.LevyFileLine (
        id BIGINT IDENTITY(1,1) NOT NULL CONSTRAINT PK_LevyFileLine PRIMARY KEY CLUSTERED,
        LevyFileId INT NOT NULL CONSTRAINT FK_LevyFileLine_LevyFile FOREIGN KEY REFERENCES dbo.LevyFile(id) ON DELETE CASCADE,
        SdlNumber NVARCHAR(20) NOT NULL,
        SchemeYear NVARCHAR(10) NOT NULL CONSTRAINT DF_LevyFileLine_SchemeYear DEFAULT N'',
        MandatoryLevyAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_LevyFileLine_Mandatory DEFAULT 0.00,
        DiscretionaryLevyAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_LevyFileLine_Discretionary DEFAULT 0.00,
        AdminLevyAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_LevyFileLine_Admin DEFAULT 0.00,
        QctoLevyAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_LevyFileLine_Qcto DEFAULT 0.00,
        InterestAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_LevyFileLine_Interest DEFAULT 0.00,
        PenaltyAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_LevyFileLine_Penalty DEFAULT 0.00,
        TotalLevyAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_LevyFileLine_Total DEFAULT 0.00,
        IsReconciled BIT NOT NULL CONSTRAINT DF_LevyFileLine_IsReconciled DEFAULT 0,
        SicCode NVARCHAR(20) NULL,
        ChamberCode NVARCHAR(20) NULL,
        SetaCode NVARCHAR(10) NOT NULL CONSTRAINT DF_LevyFileLine_SetaCode DEFAULT N'17',
        IsOutOfScopeSeta BIT NOT NULL CONSTRAINT DF_LevyFileLine_IsOutOfScope DEFAULT 0,
        HasSicCodeMismatch BIT NOT NULL CONSTRAINT DF_LevyFileLine_HasSicMismatch DEFAULT 0,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_LevyFileLine_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_LevyFileLine_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_LevyFileLine_LevyFileId ON dbo.LevyFileLine (LevyFileId);
    CREATE NONCLUSTERED INDEX IX_LevyFileLine_SdlNumber ON dbo.LevyFileLine (SdlNumber);
    CREATE NONCLUSTERED INDEX IX_LevyFileLine_SicCode ON dbo.LevyFileLine (SicCode);
    CREATE NONCLUSTERED INDEX IX_LevyFileLine_ChamberCode ON dbo.LevyFileLine (ChamberCode);
    CREATE NONCLUSTERED INDEX IX_LevyFileLine_SetaCode ON dbo.LevyFileLine (SetaCode);
    CREATE NONCLUSTERED INDEX IX_LevyFileLine_IsOutOfScopeSeta ON dbo.LevyFileLine (IsOutOfScopeSeta);
    CREATE NONCLUSTERED INDEX IX_LevyFileLine_HasSicCodeMismatch ON dbo.LevyFileLine (HasSicCodeMismatch);
    PRINT 'Created table [dbo].[LevyFileLine].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'SarsLevyStaging' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.SarsLevyStaging (
        id BIGINT IDENTITY(1,1) NOT NULL CONSTRAINT PK_SarsLevyStaging PRIMARY KEY CLUSTERED,
        BatchIdentifier NVARCHAR(100) NOT NULL,
        LineNumber INT NOT NULL,
        RawRecord NVARCHAR(1000) NULL,
        SdlNumber NVARCHAR(20) NOT NULL,
        SchemeYear NVARCHAR(10) NULL,
        SicCode NVARCHAR(20) NULL,
        ChamberCode NVARCHAR(20) NULL,
        SetaCode NVARCHAR(10) NOT NULL CONSTRAINT DF_SarsLevyStaging_Seta DEFAULT N'17',
        MandatoryLevyAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_SarsLevyStaging_Mandatory DEFAULT 0.00,
        DiscretionaryLevyAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_SarsLevyStaging_Discretionary DEFAULT 0.00,
        AdminLevyAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_SarsLevyStaging_Admin DEFAULT 0.00,
        QctoLevyAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_SarsLevyStaging_Qcto DEFAULT 0.00,
        InterestAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_SarsLevyStaging_Interest DEFAULT 0.00,
        PenaltyAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_SarsLevyStaging_Penalty DEFAULT 0.00,
        TotalLevyAmount DECIMAL(18,2) NOT NULL CONSTRAINT DF_SarsLevyStaging_Total DEFAULT 0.00,
        IsOutOfScopeSeta BIT NOT NULL CONSTRAINT DF_SarsLevyStaging_OutOfScope DEFAULT 0,
        HasSicCodeMismatch BIT NOT NULL CONSTRAINT DF_SarsLevyStaging_SicMismatch DEFAULT 0,
        StagingStatus NVARCHAR(25) NOT NULL CONSTRAINT DF_SarsLevyStaging_Status DEFAULT N'Pending',
        ValidationMessage NVARCHAR(500) NULL,
        PromotedLevyFileId INT NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_SarsLevyStaging_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_SarsLevyStaging_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX IX_SarsLevyStaging_Batch ON dbo.SarsLevyStaging (BatchIdentifier);
    CREATE NONCLUSTERED INDEX IX_SarsLevyStaging_Sdl ON dbo.SarsLevyStaging (SdlNumber);
    CREATE NONCLUSTERED INDEX IX_SarsLevyStaging_Sic ON dbo.SarsLevyStaging (SicCode);
    CREATE NONCLUSTERED INDEX IX_SarsLevyStaging_Status ON dbo.SarsLevyStaging (StagingStatus);
    CREATE NONCLUSTERED INDEX IX_SarsLevyStaging_OutOfScope ON dbo.SarsLevyStaging (IsOutOfScopeSeta);
    PRINT 'Created table [dbo].[SarsLevyStaging].';
END

-- ErpOutboxMessage: Transactional Outbox Queue for Microsoft Dynamics GP Web Services Integration
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = N'ErpOutboxMessage' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE [dbo].[ErpOutboxMessage] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_ErpOutboxMessage PRIMARY KEY CLUSTERED,
        [MessageCorrelationId] NVARCHAR(64) NOT NULL,
        [MessageType] NVARCHAR(50) NOT NULL,
        [ReferenceKey] NVARCHAR(100) NOT NULL,
        [OrganisationId] INT NULL,
        [PayloadJson] NVARCHAR(MAX) NOT NULL CONSTRAINT DF_ErpOutboxMessage_Payload DEFAULT '{}',
        [QueueStatusCode] NVARCHAR(50) NOT NULL CONSTRAINT DF_ErpOutboxMessage_Status DEFAULT 'Pending',
        [RetryCount] INT NOT NULL CONSTRAINT DF_ErpOutboxMessage_Retry DEFAULT 0,
        [MaxRetries] INT NOT NULL CONSTRAINT DF_ErpOutboxMessage_MaxRetries DEFAULT 5,
        [NextAttemptAtUtc] DATETIME2 NOT NULL CONSTRAINT DF_ErpOutboxMessage_NextAttempt DEFAULT SYSUTCDATETIME(),
        [LastAttemptAtUtc] DATETIME2 NULL,
        [DeliveredAtUtc] DATETIME2 NULL,
        [LastError] NVARCHAR(MAX) NULL,
        [TransactionReference] NVARCHAR(100) NULL,
        [GpBatchNumber] NVARCHAR(100) NULL,
        [LockToken] NVARCHAR(100) NULL,
        [LockExpiresAtUtc] DATETIME2 NULL,
        [ExecutionPriority] INT NOT NULL CONSTRAINT DF_ErpOutboxMessage_Priority DEFAULT 2,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_ErpOutboxMessage_CreatedAt DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE NONCLUSTERED INDEX [IX_ErpOutboxMessage_Status_NextAttempt] ON [dbo].[ErpOutboxMessage] ([QueueStatusCode], [NextAttemptAtUtc], [ExecutionPriority]);
    CREATE UNIQUE NONCLUSTERED INDEX [IX_ErpOutboxMessage_CorrelationId] ON [dbo].[ErpOutboxMessage] ([MessageCorrelationId]);
    CREATE NONCLUSTERED INDEX [IX_ErpOutboxMessage_Type_Ref] ON [dbo].[ErpOutboxMessage] ([MessageType], [ReferenceKey]);
    CREATE NONCLUSTERED INDEX [IX_ErpOutboxMessage_OrganisationId] ON [dbo].[ErpOutboxMessage] ([OrganisationId]);
    PRINT 'Created table [dbo].[ErpOutboxMessage].';
END

-- 78. Enterprise Vertical Partitioning: Person Satellite Tables
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'PersonContact' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.PersonContact (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_PersonContact PRIMARY KEY CLUSTERED,
        PersonId INT NOT NULL CONSTRAINT FK_PersonContact_Person REFERENCES dbo.Person(Id) ON DELETE CASCADE,
        Email NVARCHAR(150) NULL,
        PhoneNumber NVARCHAR(30) NULL,
        CellNumber NVARCHAR(30) NULL,
        FaxNumber NVARCHAR(30) NULL,
        PhysicalAddress NVARCHAR(500) NULL,
        PhysicalAddressPostalCode NVARCHAR(20) NULL,
        PostalAddress NVARCHAR(500) NULL,
        PostalAddressPostalCode NVARCHAR(20) NULL,
        ProvinceCode NVARCHAR(15) NULL,
        StatssaAreaCode NVARCHAR(50) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_PersonContact_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX IX_PersonContact_PersonId ON dbo.PersonContact (PersonId);
    CREATE NONCLUSTERED INDEX IX_PersonContact_Email ON dbo.PersonContact (Email);
    CREATE NONCLUSTERED INDEX IX_PersonContact_ProvinceCode ON dbo.PersonContact (ProvinceCode);
    CREATE NONCLUSTERED INDEX IX_PersonContact_StatssaAreaCode ON dbo.PersonContact (StatssaAreaCode);
    PRINT 'Created table [dbo].[PersonContact].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'PersonDemographics' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.PersonDemographics (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_PersonDemographics PRIMARY KEY CLUSTERED,
        PersonId INT NOT NULL CONSTRAINT FK_PersonDemographics_Person REFERENCES dbo.Person(Id) ON DELETE CASCADE,
        EquityCode NVARCHAR(15) NULL,
        DisabilityCode NVARCHAR(15) NULL,
        NationalityCode NVARCHAR(15) NULL,
        HomeLanguageCode NVARCHAR(15) NULL,
        CitizenStatusCode NVARCHAR(15) NULL,
        PopiActStatusId NVARCHAR(10) NULL CONSTRAINT DF_PersonDemographics_PopiStatus DEFAULT '01',
        PopiActConsentDate DATETIME2(7) NULL,
        LastSchoolEmisNumber NVARCHAR(50) NULL,
        LastSchoolYear NVARCHAR(10) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_PersonDemographics_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX IX_PersonDemographics_PersonId ON dbo.PersonDemographics (PersonId);
    CREATE NONCLUSTERED INDEX IX_PersonDemographics_EquityCode ON dbo.PersonDemographics (EquityCode);
    CREATE NONCLUSTERED INDEX IX_PersonDemographics_NationalityCode ON dbo.PersonDemographics (NationalityCode);
    CREATE NONCLUSTERED INDEX IX_PersonDemographics_PopiActStatusId ON dbo.PersonDemographics (PopiActStatusId);
    PRINT 'Created table [dbo].[PersonDemographics].';
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'PersonDisabilityRating' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE dbo.PersonDisabilityRating (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_PersonDisabilityRating PRIMARY KEY CLUSTERED,
        PersonId INT NOT NULL CONSTRAINT FK_PersonDisabilityRating_Person REFERENCES dbo.Person(Id) ON DELETE CASCADE,
        DisabilityCode NVARCHAR(15) NULL CONSTRAINT DF_PersonDisabilityRating_DisabilityCode DEFAULT '00',
        SeeingRatingId NVARCHAR(10) NULL CONSTRAINT DF_PersonDisabilityRating_Seeing DEFAULT '01',
        HearingRatingId NVARCHAR(10) NULL CONSTRAINT DF_PersonDisabilityRating_Hearing DEFAULT '01',
        WalkingRatingId NVARCHAR(10) NULL CONSTRAINT DF_PersonDisabilityRating_Walking DEFAULT '01',
        RememberingRatingId NVARCHAR(10) NULL CONSTRAINT DF_PersonDisabilityRating_Remembering DEFAULT '01',
        CommunicatingRatingId NVARCHAR(10) NULL CONSTRAINT DF_PersonDisabilityRating_Communicating DEFAULT '01',
        SelfCareRatingId NVARCHAR(10) NULL CONSTRAINT DF_PersonDisabilityRating_SelfCare DEFAULT '01',
        DisabilitySupportNotes NVARCHAR(1000) NULL,
        IsDisabilityAssessed BIT NOT NULL CONSTRAINT DF_PersonDisabilityRating_Assessed DEFAULT 0,
        AssessedDate DATETIME2(7) NULL,
        AssessedBy NVARCHAR(150) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_PersonDisabilityRating_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX IX_PersonDisabilityRating_PersonId ON dbo.PersonDisabilityRating (PersonId);
    CREATE NONCLUSTERED INDEX IX_PersonDisabilityRating_DisabilityCode ON dbo.PersonDisabilityRating (DisabilityCode);
    PRINT 'Created table [dbo].[PersonDisabilityRating].';
END

-- 81. Non-Levy Organisation Number Sequence & Chamber Derivation Governance
IF NOT EXISTS (SELECT 1 FROM sys.sequences WHERE name = 'seq_NonLevyOrganisationNumber')
BEGIN
    CREATE SEQUENCE [dbo].[seq_NonLevyOrganisationNumber]
    AS INT
    START WITH 100001
    INCREMENT BY 1
    MINVALUE 100000
    MAXVALUE 999999999
    NO CYCLE
    CACHE 10;
    PRINT 'Created sequence [dbo].[seq_NonLevyOrganisationNumber].';
END

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[Organisation]') AND name = 'HasMissingChamberMapping')
BEGIN
    ALTER TABLE [dbo].[Organisation] ADD [HasMissingChamberMapping] BIT NOT NULL CONSTRAINT DF_Organisation_HasMissingChamberMapping DEFAULT (0);
    PRINT 'Added [HasMissingChamberMapping] to [dbo].[Organisation].';
END

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[Organisation]') AND name = 'GpVendorClass')
BEGIN
    ALTER TABLE [dbo].[Organisation] ADD [GpVendorClass] NVARCHAR(50) NULL;
    PRINT 'Added [GpVendorClass] to [dbo].[Organisation].';
END

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'UQ_Organisation_SdlNumber')
BEGIN
    CREATE UNIQUE NONCLUSTERED INDEX [UQ_Organisation_SdlNumber]
    ON [dbo].[Organisation] ([SdlNumber])
    WHERE [SdlNumber] IS NOT NULL AND [SdlNumber] <> '';
END

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_Organisation_HasMissingChamberMapping')
BEGIN
    CREATE NONCLUSTERED INDEX [IX_Organisation_HasMissingChamberMapping]
    ON [dbo].[Organisation] ([HasMissingChamberMapping])
    INCLUDE ([ChamberCode], [GpVendorClass], [SicCode]);
END

-- Phase 4: LearnerEnrolment Cutover & Compatibility Views
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'CompanyLearner' AND schema_id = SCHEMA_ID(N'dbo'))
   AND NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = N'LearnerEnrolment' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    EXEC sp_rename 'dbo.CompanyLearner', 'LearnerEnrolment';
    PRINT 'Renamed dbo.CompanyLearner to dbo.LearnerEnrolment in Master DDL.';
END

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = N'CompanyLearner' AND schema_id = SCHEMA_ID(N'dbo'))
   AND NOT EXISTS (SELECT 1 FROM sys.views WHERE name = N'CompanyLearner' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    EXEC('CREATE VIEW dbo.CompanyLearner AS SELECT * FROM dbo.LearnerEnrolment;');
    PRINT 'Created backward-compatible view dbo.CompanyLearner in Master DDL.';
END

IF NOT EXISTS (SELECT 1 FROM sys.views WHERE name = N'vw_SetmisCompanyLearner' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    EXEC('
    CREATE VIEW dbo.vw_SetmisCompanyLearner AS
    SELECT 
        le.Id,
        le.PersonId,
        le.OrganisationId AS CompanyId,
        le.OrganisationSiteId,
        le.TrainingProviderId,
        le.LearnerContractNumber,
        le.QualificationTitle,
        le.SaqaQualificationId,
        le.NqfLevel,
        le.LearningProgrammeTypeCode,
        le.LearnershipId,
        le.NonNqfInterventionCode,
        le.PartOfId,
        le.EnrolmentTypeId,
        le.EnrolmentStatusId,
        le.EnrolmentStatusDate,
        le.EnrolmentStatusReasonId,
        le.AssessorRegistrationNumber,
        le.AssessorEtqaId,
        le.PracticalProviderCode,
        le.PracticalProviderEtqaId,
        le.OfoCode,
        le.EconomicStatusId,
        le.UrbanRuralId,
        le.CumulativeSpend,
        le.CertificateNumber,
        le.PriorQualificationId,
        le.PriorQualificationAchievementDate,
        le.InternshipStatusId,
        le.FundingTypeCode,
        le.FundingId,
        le.EnrolmentStatusCode,
        le.RegistrationDate,
        le.CommencementDate,
        le.ExpectedCompletionDate,
        le.CompletionDate,
        le.SetaRegion,
        le.ChamberCode,
        le.IsActive,
        le.CreatedAt,
        le.CreatedBy,
        le.ModifiedAt,
        le.ModifiedBy
    FROM dbo.LearnerEnrolment le;
    ');
    PRINT 'Created statutory view dbo.vw_SetmisCompanyLearner in Master DDL.';
END

-- Wizard Draft Persistence & Resume Lifecycle Engine
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = N'WizardDraftSession' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE [dbo].[WizardDraftSession] (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [DraftKey] NVARCHAR(100) NOT NULL,
        [CandidateKey] NVARCHAR(50) NOT NULL,
        [WizardTitle] NVARCHAR(150) NOT NULL,
        [Route] NVARCHAR(250) NOT NULL,
        [UserId] NVARCHAR(100) NOT NULL,
        [OrganisationId] INT NULL,
        [CurrentStepIndex] INT NOT NULL CONSTRAINT [DF_WizardDraftSession_Step] DEFAULT 0,
        [CompletedStepCount] INT NOT NULL CONSTRAINT [DF_WizardDraftSession_Completed] DEFAULT 0,
        [TotalStepCount] INT NOT NULL CONSTRAINT [DF_WizardDraftSession_Total] DEFAULT 5,
        [DraftModelJson] NVARCHAR(MAX) NOT NULL,
        [Status] NVARCHAR(30) NOT NULL CONSTRAINT [DF_WizardDraftSession_Status] DEFAULT N'Active',
        [ExpiresAtUtc] DATETIME2 NOT NULL,
        [IsActive] BIT NOT NULL CONSTRAINT [DF_WizardDraftSession_IsActive] DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_WizardDraftSession_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT [DF_WizardDraftSession_CreatedBy] DEFAULT N'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [PK_WizardDraftSession] PRIMARY KEY CLUSTERED ([Id] ASC)
    );
    CREATE UNIQUE NONCLUSTERED INDEX [UQ_WizardDraftSession_DraftKey] ON [dbo].[WizardDraftSession] ([DraftKey]);
    CREATE NONCLUSTERED INDEX [IX_WizardDraftSession_Lookup] ON [dbo].[WizardDraftSession] ([UserId], [CandidateKey], [IsActive]) INCLUDE ([DraftKey], [CurrentStepIndex], [ExpiresAtUtc], [ModifiedAt]);
    CREATE NONCLUSTERED INDEX [IX_WizardDraftSession_Organisation] ON [dbo].[WizardDraftSession] ([OrganisationId]) WHERE [OrganisationId] IS NOT NULL;
    PRINT 'Created table [dbo].[WizardDraftSession].';
END

-- ==============================================================================================
-- Phase 35: Summative Assessment, Moderation, Batching & Certification Statutory Governance Alignment
-- Reference: Signed Specification (18 Nov 2022) - Assessments and Moderation Use Case (MerSeta\NSDMS\LMS\ASM\12)
-- ==============================================================================================

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessmentBatch')
BEGIN
    CREATE TABLE dbo.AssessmentBatch (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        BatchNumber NVARCHAR(50) NOT NULL,
        TrainingProviderId INT NOT NULL,
        QualificationTitle NVARCHAR(250) NOT NULL,
        SaqaQualificationId NVARCHAR(50) NULL,
        AssessmentStageCode NVARCHAR(50) NOT NULL DEFAULT 'Completion',
        SamplePercentage INT NOT NULL DEFAULT 10,
        TotalLearnersCount INT NOT NULL DEFAULT 0,
        SampledLearnersCount INT NOT NULL DEFAULT 0,
        InternalModerationReportDocumentRef NVARCHAR(500) NULL,
        LastInternalModerationDate DATETIME2 NULL,
        StatusCode NVARCHAR(50) NOT NULL DEFAULT 'Batched',
        ScheduledSiteVisitDate DATETIME2 NULL,
        IsSiteVisitRequired BIT NOT NULL DEFAULT 1,
        SiteVisitSchedulingComments NVARCHAR(1000) NULL,
        AssignedQaUserId NVARCHAR(100) NULL,
        ContactPersonId INT NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessmentBatch_TrainingProvider FOREIGN KEY (TrainingProviderId) REFERENCES dbo.TrainingProvider(Id)
    );
    CREATE UNIQUE INDEX IX_AssessmentBatch_BatchNumber ON dbo.AssessmentBatch(BatchNumber);
    CREATE INDEX IX_AssessmentBatch_TrainingProviderId ON dbo.AssessmentBatch(TrainingProviderId);
    CREATE INDEX IX_AssessmentBatch_StatusCode ON dbo.AssessmentBatch(StatusCode);
    PRINT 'Created table dbo.AssessmentBatch.';
END

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessmentBatchLearner')
BEGIN
    CREATE TABLE dbo.AssessmentBatchLearner (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        AssessmentBatchId INT NOT NULL,
        SummativeAssessmentReportId INT NOT NULL,
        IsSelectedInSample BIT NOT NULL DEFAULT 0,
        LearnerOutcomeStatus NVARCHAR(50) NOT NULL DEFAULT 'Pending',
        RejectionReasonCodes NVARCHAR(500) NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessmentBatchLearner_Batch FOREIGN KEY (AssessmentBatchId) REFERENCES dbo.AssessmentBatch(Id) ON DELETE CASCADE,
        CONSTRAINT FK_AssessmentBatchLearner_Report FOREIGN KEY (SummativeAssessmentReportId) REFERENCES dbo.SummativeAssessmentReport(Id)
    );
    CREATE INDEX IX_AssessmentBatchLearner_BatchId ON dbo.AssessmentBatchLearner(AssessmentBatchId);
    CREATE INDEX IX_AssessmentBatchLearner_ReportId ON dbo.AssessmentBatchLearner(SummativeAssessmentReportId);
    PRINT 'Created table dbo.AssessmentBatchLearner.';
END

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'ModerationChecklistEtqTp043')
BEGIN
    CREATE TABLE dbo.ModerationChecklistEtqTp043 (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        AssessmentBatchId INT NOT NULL,
        ValidationBatchNumber NVARCHAR(50) NOT NULL,
        QualityAssurorUserId NVARCHAR(100) NOT NULL,
        DateOfModeration DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        StageOfModerationCode NVARCHAR(50) NOT NULL DEFAULT 'Completion',
        ValidationDecisionCode NVARCHAR(50) NOT NULL DEFAULT 'Upheld',
        PrimaryRejectionReasonCode NVARCHAR(100) NULL,
        VacsPrincipleViolatedCode NVARCHAR(50) NULL,
        RejectionRemarks NVARCHAR(MAX) NULL,
        RemedialActionRequired NVARCHAR(MAX) NULL,
        ReportDocumentReference NVARCHAR(500) NULL,
        TamperProofHashSha256 NVARCHAR(100) NOT NULL DEFAULT '',
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_ModerationChecklist_Batch FOREIGN KEY (AssessmentBatchId) REFERENCES dbo.AssessmentBatch(Id) ON DELETE CASCADE
    );
    CREATE INDEX IX_ModerationChecklist_BatchId ON dbo.ModerationChecklistEtqTp043(AssessmentBatchId);
    PRINT 'Created table dbo.ModerationChecklistEtqTp043.';
END

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'ModerationChecklistItem')
BEGIN
    CREATE TABLE dbo.ModerationChecklistItem (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        ModerationChecklistEtqTp043Id INT NOT NULL,
        SectionNumber INT NOT NULL DEFAULT 1,
        CriteriaTitle NVARCHAR(250) NOT NULL,
        EvidenceRequirements NVARCHAR(500) NOT NULL,
        IsCompliant BIT NOT NULL DEFAULT 1,
        Comments NVARCHAR(1000) NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_ModerationChecklistItem_Checklist FOREIGN KEY (ModerationChecklistEtqTp043Id) REFERENCES dbo.ModerationChecklistEtqTp043(Id) ON DELETE CASCADE
    );
    CREATE INDEX IX_ModerationChecklistItem_ChecklistId ON dbo.ModerationChecklistItem(ModerationChecklistEtqTp043Id);
    PRINT 'Created table dbo.ModerationChecklistItem.';
END

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'CertificatePrintingBatch')
BEGIN
    CREATE TABLE dbo.CertificatePrintingBatch (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        PrintingBatchNumber NVARCHAR(50) NOT NULL,
        BatchGeneratedDate DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        TotalCertificatesCount INT NOT NULL DEFAULT 0,
        ConsolidatedPdfDocumentRef NVARCHAR(500) NULL,
        ConsolidatedDistributionLettersPdfRef NVARCHAR(500) NULL,
        StatusCode NVARCHAR(50) NOT NULL DEFAULT 'QueuedForPrinting',
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE INDEX IX_CertificatePrintingBatch_BatchNumber ON dbo.CertificatePrintingBatch(PrintingBatchNumber);
    PRINT 'Created table dbo.CertificatePrintingBatch.';
END

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'LearnerCertificate')
BEGIN
    CREATE TABLE dbo.LearnerCertificate (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        CompanyLearnerId INT NOT NULL,
        PersonId INT NOT NULL,
        CertificatePrintingBatchId INT NULL,
        SummativeAssessmentReportId INT NOT NULL,
        CertificateNumber NVARCHAR(50) NOT NULL,
        QualificationTitle NVARCHAR(250) NOT NULL,
        SaqaQualificationId NVARCHAR(50) NULL,
        NqfLevel INT NOT NULL DEFAULT 4,
        IssueDate DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        TamperProofHashSha256 NVARCHAR(100) NOT NULL DEFAULT '',
        IsReprintOrReplacement BIT NOT NULL DEFAULT 0,
        ReplacementReason NVARCHAR(500) NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_LearnerCertificate_CompanyLearner FOREIGN KEY (CompanyLearnerId) REFERENCES dbo.CompanyLearner(Id),
        CONSTRAINT FK_LearnerCertificate_Person FOREIGN KEY (PersonId) REFERENCES dbo.Person(Id),
        CONSTRAINT FK_LearnerCertificate_Batch FOREIGN KEY (CertificatePrintingBatchId) REFERENCES dbo.CertificatePrintingBatch(Id) ON DELETE SET NULL,
        CONSTRAINT FK_LearnerCertificate_Report FOREIGN KEY (SummativeAssessmentReportId) REFERENCES dbo.SummativeAssessmentReport(Id)
    );
    CREATE UNIQUE INDEX IX_LearnerCertificate_CertificateNumber ON dbo.LearnerCertificate(CertificateNumber);
    PRINT 'Created table dbo.LearnerCertificate.';
END

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'DistributionLetter')
BEGIN
    CREATE TABLE dbo.DistributionLetter (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        CertificatePrintingBatchId INT NOT NULL,
        TrainingProviderId INT NOT NULL,
        ProviderAccreditationNumber NVARCHAR(50) NOT NULL,
        LetterReferenceNumber NVARCHAR(50) NOT NULL,
        DocumentReferenceUrl NVARCHAR(500) NULL,
        GeneratedDate DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_DistributionLetter_Batch FOREIGN KEY (CertificatePrintingBatchId) REFERENCES dbo.CertificatePrintingBatch(Id) ON DELETE CASCADE,
        CONSTRAINT FK_DistributionLetter_Provider FOREIGN KEY (TrainingProviderId) REFERENCES dbo.TrainingProvider(Id)
    );
    CREATE UNIQUE INDEX IX_DistributionLetter_LetterRefNumber ON dbo.DistributionLetter(LetterReferenceNumber);
    PRINT 'Created table dbo.DistributionLetter.';
END

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'ScannedCertificateAttachment')
BEGIN
    CREATE TABLE dbo.ScannedCertificateAttachment (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        LearnerCertificateId INT NOT NULL,
        PersonId INT NOT NULL,
        DocumentStorageKey NVARCHAR(500) NOT NULL,
        FileName NVARCHAR(255) NOT NULL,
        FileSizeBytes BIGINT NOT NULL DEFAULT 0,
        ScannedByUserId NVARCHAR(100) NOT NULL,
        ScannedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        OcrExtractedIdNumber NVARCHAR(50) NULL,
        OcrExtractedCertificateNumber NVARCHAR(50) NULL,
        IsVerifiedMatch BIT NOT NULL DEFAULT 0,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_ScannedCert_Certificate FOREIGN KEY (LearnerCertificateId) REFERENCES dbo.LearnerCertificate(Id) ON DELETE CASCADE,
        CONSTRAINT FK_ScannedCert_Person FOREIGN KEY (PersonId) REFERENCES dbo.Person(Id)
    );
    PRINT 'Created table dbo.ScannedCertificateAttachment.';
END

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessmentCertificateDistributionEvent')
BEGIN
    CREATE TABLE dbo.AssessmentCertificateDistributionEvent (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        LearnerCertificateId INT NOT NULL,
        DistributionMethodCode NVARCHAR(50) NOT NULL DEFAULT 'RegisteredMail',
        WaybillOrTrackingNumber NVARCHAR(100) NULL,
        DispatchedDate DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        RecipientName NVARCHAR(150) NULL,
        RecipientIdNumber NVARCHAR(50) NULL,
        ReceivedDate DATETIME2 NULL,
        DispatchNotes NVARCHAR(1000) NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessmentCertDist_Certificate FOREIGN KEY (LearnerCertificateId) REFERENCES dbo.LearnerCertificate(Id) ON DELETE CASCADE
    );
    PRINT 'Created table dbo.AssessmentCertificateDistributionEvent.';
END

IF NOT EXISTS (SELECT 1 FROM sys.sequences WHERE name = 'Seq_StatutoryCertificateNumber')
BEGIN
    CREATE SEQUENCE dbo.Seq_StatutoryCertificateNumber
        AS INT
        START WITH 1
        INCREMENT BY 1
        MINVALUE 1
        MAXVALUE 999999
        CYCLE;
    PRINT 'Created sequence dbo.Seq_StatutoryCertificateNumber.';
END

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_SummativeAssessmentReport_Batch_Status' AND object_id = OBJECT_ID('dbo.SummativeAssessmentReport'))
BEGIN
    CREATE INDEX IX_SummativeAssessmentReport_Batch_Status ON dbo.SummativeAssessmentReport(AssessmentBatchId, StatusCode);
END

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_AssessmentBatchLearner_Batch_Report' AND object_id = OBJECT_ID('dbo.AssessmentBatchLearner'))
BEGIN
    CREATE UNIQUE INDEX IX_AssessmentBatchLearner_Batch_Report ON dbo.AssessmentBatchLearner(AssessmentBatchId, SummativeAssessmentReportId);
END

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_LearnerCertificate_SummativeAssessmentReportId' AND object_id = OBJECT_ID('dbo.LearnerCertificate'))
BEGIN
    CREATE INDEX IX_LearnerCertificate_SummativeAssessmentReportId ON dbo.LearnerCertificate(SummativeAssessmentReportId);
END

-- 49. Option B: Unified Dynamic Portfolio & Capability Dispatch Engine
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'TerritoryZone')
BEGIN
    CREATE TABLE dbo.TerritoryZone (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_TerritoryZone PRIMARY KEY CLUSTERED,
        ZoneCode NVARCHAR(50) NOT NULL,
        ZoneName NVARCHAR(150) NOT NULL,
        RegionCode NVARCHAR(50) NOT NULL,
        RegionName NVARCHAR(150) NOT NULL,
        ProvinceCode NVARCHAR(10) NOT NULL,
        DefaultOfficerUserId NVARCHAR(100) NULL,
        DefaultOfficerName NVARCHAR(150) NULL,
        DefaultOfficerEmail NVARCHAR(150) NULL,
        Description NVARCHAR(500) NULL,
        IsActive BIT NOT NULL CONSTRAINT DF_TerritoryZone_IsActive DEFAULT 1,
        CreatedAt DATETIME2 NOT NULL CONSTRAINT DF_TerritoryZone_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL
    );

    CREATE UNIQUE NONCLUSTERED INDEX IX_TerritoryZone_ZoneCode ON dbo.TerritoryZone (ZoneCode);
    CREATE NONCLUSTERED INDEX IX_TerritoryZone_RegionCode ON dbo.TerritoryZone (RegionCode);
    CREATE NONCLUSTERED INDEX IX_TerritoryZone_DefaultOfficerUserId ON dbo.TerritoryZone (DefaultOfficerUserId);
    CREATE NONCLUSTERED INDEX IX_TerritoryZone_IsActive ON dbo.TerritoryZone (IsActive);
    PRINT 'Created table dbo.TerritoryZone.';
END

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'TerritoryDemarcation')
BEGIN
    CREATE TABLE dbo.TerritoryDemarcation (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_TerritoryDemarcation PRIMARY KEY CLUSTERED,
        TownName NVARCHAR(150) NOT NULL,
        RegionCode NVARCHAR(50) NOT NULL,
        RegionName NVARCHAR(150) NOT NULL,
        ProvinceCode NVARCHAR(10) NOT NULL,
        StatssaAreaCode NVARCHAR(50) NULL,
        ZoneId INT NULL CONSTRAINT FK_TerritoryDemarcation_Zone REFERENCES dbo.TerritoryZone(Id) ON DELETE SET NULL,
        ZoneCode NVARCHAR(50) NULL,
        EffectiveFrom DATETIME2 NOT NULL CONSTRAINT DF_TerritoryDemarcation_EffectiveFrom DEFAULT '2020-01-01T00:00:00',
        EffectiveTo DATETIME2 NULL,
        BoundaryGazetteReference NVARCHAR(250) NULL,
        PostalCodePrefix NVARCHAR(20) NULL,
        MunicipalityName NVARCHAR(150) NULL,
        IsActive BIT NOT NULL CONSTRAINT DF_TerritoryDemarcation_IsActive DEFAULT 1,
        CreatedAt DATETIME2 NOT NULL CONSTRAINT DF_TerritoryDemarcation_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL
    );

    CREATE NONCLUSTERED INDEX IX_TerritoryDemarcation_TownName ON dbo.TerritoryDemarcation (TownName);
    CREATE NONCLUSTERED INDEX IX_TerritoryDemarcation_RegionCode ON dbo.TerritoryDemarcation (RegionCode);
    CREATE NONCLUSTERED INDEX IX_TerritoryDemarcation_ProvinceCode ON dbo.TerritoryDemarcation (ProvinceCode);
    CREATE NONCLUSTERED INDEX IX_TerritoryDemarcation_ZoneId ON dbo.TerritoryDemarcation (ZoneId);
    CREATE NONCLUSTERED INDEX IX_TerritoryDemarcation_ZoneCode ON dbo.TerritoryDemarcation (ZoneCode);
    CREATE NONCLUSTERED INDEX IX_TerritoryDemarcation_TownName_IsActive ON dbo.TerritoryDemarcation (TownName, IsActive);
    PRINT 'Created table dbo.TerritoryDemarcation.';
END

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'StaffCapability')
BEGIN
    CREATE TABLE dbo.StaffCapability (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_StaffCapability PRIMARY KEY CLUSTERED,
        UserId NVARCHAR(100) NOT NULL,
        StaffName NVARCHAR(150) NOT NULL,
        Email NVARCHAR(150) NOT NULL,
        CapabilityCode NVARCHAR(50) NOT NULL,
        CapabilityName NVARCHAR(150) NOT NULL,
        StationedRegionCode NVARCHAR(50) NOT NULL,
        EmploymentRole NVARCHAR(100) NOT NULL CONSTRAINT DF_StaffCapability_EmploymentRole DEFAULT 'Officer',
        CertifiedDate DATETIME2 NOT NULL CONSTRAINT DF_StaffCapability_CertifiedDate DEFAULT SYSUTCDATETIME(),
        ExpiryDate DATETIME2 NULL,
        IsActive BIT NOT NULL CONSTRAINT DF_StaffCapability_IsActive DEFAULT 1,
        CreatedAt DATETIME2 NOT NULL CONSTRAINT DF_StaffCapability_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL
    );

    CREATE NONCLUSTERED INDEX IX_StaffCapability_UserId ON dbo.StaffCapability (UserId);
    CREATE NONCLUSTERED INDEX IX_StaffCapability_CapabilityCode ON dbo.StaffCapability (CapabilityCode);
    CREATE NONCLUSTERED INDEX IX_StaffCapability_StationedRegionCode ON dbo.StaffCapability (StationedRegionCode);
    CREATE NONCLUSTERED INDEX IX_StaffCapability_IsActive ON dbo.StaffCapability (IsActive);
    PRINT 'Created table dbo.StaffCapability.';
END

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OrganisationPortfolio')
BEGIN
    CREATE TABLE dbo.OrganisationPortfolio (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_OrganisationPortfolio PRIMARY KEY CLUSTERED,
        OrganisationId INT NOT NULL CONSTRAINT FK_OrganisationPortfolio_Org FOREIGN KEY REFERENCES dbo.Organisation(Id) ON DELETE CASCADE,
        RelationshipOfficerUserId NVARCHAR(100) NOT NULL,
        RelationshipOfficerName NVARCHAR(150) NOT NULL,
        RelationshipOfficerEmail NVARCHAR(150) NOT NULL,
        PortfolioRoleCode NVARCHAR(50) NOT NULL CONSTRAINT DF_OrganisationPortfolio_Role DEFAULT 'PRIMARY_CLO',
        ManagingRegionCode NVARCHAR(50) NOT NULL,
        IsCrossRegionalAssignment BIT NOT NULL CONSTRAINT DF_OrganisationPortfolio_CrossRegional DEFAULT 0,
        AssignmentReason NVARCHAR(500) NULL,
        EffectiveFrom DATETIME2 NOT NULL CONSTRAINT DF_OrganisationPortfolio_EffectiveFrom DEFAULT SYSUTCDATETIME(),
        EffectiveTo DATETIME2 NULL,
        AssignedByUserId NVARCHAR(100) NOT NULL CONSTRAINT DF_OrganisationPortfolio_AssignedBy DEFAULT 'SYSTEM',
        IsActive BIT NOT NULL CONSTRAINT DF_OrganisationPortfolio_IsActive DEFAULT 1,
        CreatedAt DATETIME2 NOT NULL CONSTRAINT DF_OrganisationPortfolio_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL
    );

    CREATE NONCLUSTERED INDEX IX_OrganisationPortfolio_Org ON dbo.OrganisationPortfolio (OrganisationId);
    CREATE NONCLUSTERED INDEX IX_OrganisationPortfolio_Officer ON dbo.OrganisationPortfolio (RelationshipOfficerUserId);
    CREATE NONCLUSTERED INDEX IX_OrganisationPortfolio_Region ON dbo.OrganisationPortfolio (ManagingRegionCode);
    CREATE NONCLUSTERED INDEX IX_OrganisationPortfolio_Active ON dbo.OrganisationPortfolio (OrganisationId, IsActive);
    PRINT 'Created table dbo.OrganisationPortfolio.';
END

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'FieldDispatchAssignment')
BEGIN
    CREATE TABLE dbo.FieldDispatchAssignment (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_FieldDispatchAssignment PRIMARY KEY CLUSTERED,
        OrganisationId INT NOT NULL CONSTRAINT FK_FieldDispatch_Org FOREIGN KEY REFERENCES dbo.Organisation(Id) ON DELETE CASCADE,
        VisitId INT NULL CONSTRAINT FK_FieldDispatch_Visit FOREIGN KEY REFERENCES dbo.Visit(Id),
        ContactPersonId INT NOT NULL CONSTRAINT FK_FieldDispatch_Contact FOREIGN KEY REFERENCES dbo.Person(Id),
        ScheduledDate DATETIME2 NOT NULL,
        ActivityTypeCode NVARCHAR(50) NOT NULL,
        RequiredCapabilityCode NVARCHAR(50) NOT NULL,
        DispatchedOfficerUserId NVARCHAR(100) NOT NULL,
        DispatchedOfficerName NVARCHAR(150) NOT NULL,
        ScheduledByCoordinatorUserId NVARCHAR(100) NOT NULL,
        ScheduledByCoordinatorName NVARCHAR(150) NOT NULL,
        DispatchStatus NVARCHAR(50) NOT NULL CONSTRAINT DF_FieldDispatch_Status DEFAULT 'Scheduled',
        Priority NVARCHAR(20) NOT NULL CONSTRAINT DF_FieldDispatch_Priority DEFAULT 'Normal',
        CoordinatorNotes NVARCHAR(1000) NULL,
        OfficerAcceptanceNotes NVARCHAR(1000) NULL,
        CompletedDate DATETIME2 NULL,
        CreatedAt DATETIME2 NOT NULL CONSTRAINT DF_FieldDispatch_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL
    );

    CREATE NONCLUSTERED INDEX IX_FieldDispatch_Org ON dbo.FieldDispatchAssignment (OrganisationId);
    CREATE NONCLUSTERED INDEX IX_FieldDispatch_Visit ON dbo.FieldDispatchAssignment (VisitId);
    CREATE NONCLUSTERED INDEX IX_FieldDispatch_Contact ON dbo.FieldDispatchAssignment (ContactPersonId);
    CREATE NONCLUSTERED INDEX IX_FieldDispatch_Officer ON dbo.FieldDispatchAssignment (DispatchedOfficerUserId);
    CREATE NONCLUSTERED INDEX IX_FieldDispatch_Status ON dbo.FieldDispatchAssignment (DispatchStatus);
    CREATE NONCLUSTERED INDEX IX_FieldDispatch_Date ON dbo.FieldDispatchAssignment (ScheduledDate);
    PRINT 'Created table dbo.FieldDispatchAssignment.';
END

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'PortfolioHandoffLog')
BEGIN
    CREATE TABLE dbo.PortfolioHandoffLog (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_PortfolioHandoffLog PRIMARY KEY CLUSTERED,
        FromOfficerUserId NVARCHAR(100) NOT NULL,
        FromOfficerName NVARCHAR(150) NOT NULL,
        ToOfficerUserId NVARCHAR(100) NOT NULL,
        ToOfficerName NVARCHAR(150) NOT NULL,
        OrganisationId INT NOT NULL CONSTRAINT FK_PortfolioHandoff_Org FOREIGN KEY REFERENCES dbo.Organisation(Id) ON DELETE CASCADE,
        AuthorizedByUserId NVARCHAR(100) NOT NULL,
        AuthorizedByName NVARCHAR(150) NOT NULL,
        HandoffReason NVARCHAR(500) NOT NULL,
        ReassignedTasksCount INT NOT NULL CONSTRAINT DF_PortfolioHandoff_Tasks DEFAULT 0,
        ReassignedTaskIdsJson NVARCHAR(MAX) NULL,
        SecuritySealHash NVARCHAR(64) NOT NULL,
        CreatedAt DATETIME2 NOT NULL CONSTRAINT DF_PortfolioHandoff_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL
    );

    CREATE NONCLUSTERED INDEX IX_PortfolioHandoff_FromOfficer ON dbo.PortfolioHandoffLog (FromOfficerUserId);
    CREATE NONCLUSTERED INDEX IX_PortfolioHandoff_ToOfficer ON dbo.PortfolioHandoffLog (ToOfficerUserId);
    CREATE NONCLUSTERED INDEX IX_PortfolioHandoff_Org ON dbo.PortfolioHandoffLog (OrganisationId);
    CREATE NONCLUSTERED INDEX IX_PortfolioHandoff_Seal ON dbo.PortfolioHandoffLog (SecuritySealHash);
    PRINT 'Created table dbo.PortfolioHandoffLog.';
END

-- ====================================================================================================
-- ASSESSOR & MODERATOR REGISTRATION & LIFECYCLE TABLES
-- ====================================================================================================

-- 1. AssessorRegistrationApplication
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorRegistrationApplication' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorRegistrationApplication (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorRegistrationApplication PRIMARY KEY CLUSTERED,
        ApplicationNumber NVARCHAR(50) NOT NULL,
        PractitionerType NVARCHAR(50) NOT NULL,
        PersonId INT NOT NULL,
        LastSchoolAttended NVARCHAR(150) NULL,
        LastSchoolYear INT NULL,
        EmploymentStatusCode NVARCHAR(30) NOT NULL CONSTRAINT DF_AssessorApp_EmploymentStatus DEFAULT ('Employed'),
        DisabilityTypeCode NVARCHAR(50) NULL,
        DisabilitySeverityCode NVARCHAR(50) NULL,
        UrbanRuralArea NVARCHAR(20) NOT NULL CONSTRAINT DF_AssessorApp_UrbanRural DEFAULT ('Urban'),
        NextOfKinName NVARCHAR(150) NULL,
        NextOfKinContact NVARCHAR(50) NULL,
        NextOfKinRelationship NVARCHAR(50) NULL,
        HighestQualificationTitle NVARCHAR(250) NULL,
        HighestQualificationObtainedDate DATETIME2(7) NULL,
        ApplicationStatusCode NVARCHAR(40) NOT NULL CONSTRAINT DF_AssessorApp_Status DEFAULT ('Draft'),
        IsDeclarationAcknowledged BIT NOT NULL CONSTRAINT DF_AssessorApp_IsAck DEFAULT (0),
        SignedOffByUserId NVARCHAR(100) NULL,
        SignedOffAt DATETIME2(7) NULL,
        VerificationRecommendation NVARCHAR(50) NULL,
        VerificationReason NVARCHAR(100) NULL,
        VerificationExplanation NVARCHAR(MAX) NULL,
        VerifiedByUserId NVARCHAR(100) NULL,
        VerificationDate DATETIME2(7) NULL,
        EvaluationRecommendation NVARCHAR(50) NULL,
        EvaluationReason NVARCHAR(100) NULL,
        EvaluationExplanation NVARCHAR(MAX) NULL,
        EvaluatedByUserId NVARCHAR(100) NULL,
        EvaluationDate DATETIME2(7) NULL,
        ReviewCommitteeDecision NVARCHAR(50) NULL,
        ReviewCommitteeDecisionNumber NVARCHAR(100) NULL,
        ReviewCommitteeMeetingDate DATETIME2(7) NULL,
        ReviewCommitteeNotes NVARCHAR(MAX) NULL,
        IsFinalRejection BIT NOT NULL CONSTRAINT DF_AssessorApp_FinalRejection DEFAULT (0),
        RejectionReason NVARCHAR(100) NULL,
        RejectionComments NVARCHAR(MAX) NULL,
        ApprovedByUserId NVARCHAR(100) NULL,
        ApprovalDate DATETIME2(7) NULL,
        ApprovalComments NVARCHAR(MAX) NULL,
        DigitalSecuritySeal NVARCHAR(64) NULL,
        RegisteredAssessorId INT NULL,
        WithdrawalReason NVARCHAR(100) NULL,
        WithdrawalComments NVARCHAR(MAX) NULL,
        WithdrawnAt DATETIME2(7) NULL,
        WithdrawnByUserId NVARCHAR(100) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorApp_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorApp_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorRegistrationApplication_Person FOREIGN KEY (PersonId) REFERENCES dbo.Person (id) ON DELETE NO ACTION,
        CONSTRAINT FK_AssessorRegistrationApplication_Assessor FOREIGN KEY (RegisteredAssessorId) REFERENCES dbo.EtqaAssessor (id) ON DELETE SET NULL
    );

    CREATE UNIQUE NONCLUSTERED INDEX UQ_AssessorRegistrationApplication_Number ON dbo.AssessorRegistrationApplication (ApplicationNumber);
    CREATE NONCLUSTERED INDEX IX_AssessorRegistrationApplication_PersonId ON dbo.AssessorRegistrationApplication (PersonId);
    CREATE NONCLUSTERED INDEX IX_AssessorRegistrationApplication_Status ON dbo.AssessorRegistrationApplication (ApplicationStatusCode);
    CREATE NONCLUSTERED INDEX IX_AssessorRegistrationApplication_Type ON dbo.AssessorRegistrationApplication (PractitionerType);
    CREATE NONCLUSTERED INDEX IX_AssessorRegistrationApplication_AssessorId ON dbo.AssessorRegistrationApplication (RegisteredAssessorId);
    PRINT 'Created table dbo.AssessorRegistrationApplication.';
END

-- 2. AssessorApplicationScope
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorApplicationScope' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorApplicationScope (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorApplicationScope PRIMARY KEY CLUSTERED,
        AssessorRegistrationApplicationId INT NOT NULL,
        SaqaQualificationId INT NOT NULL,
        QualificationTitle NVARCHAR(250) NOT NULL,
        QualificationObtainedDate DATETIME2(7) NOT NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorAppScope_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorAppScope_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorApplicationScope_App FOREIGN KEY (AssessorRegistrationApplicationId) REFERENCES dbo.AssessorRegistrationApplication (id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_AssessorApplicationScope_AppId ON dbo.AssessorApplicationScope (AssessorRegistrationApplicationId);
    CREATE NONCLUSTERED INDEX IX_AssessorApplicationScope_SaqaId ON dbo.AssessorApplicationScope (SaqaQualificationId);
    PRINT 'Created table dbo.AssessorApplicationScope.';
END

-- 3. AssessorApplicationUnitStandard
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorApplicationUnitStandard' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorApplicationUnitStandard (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorApplicationUnitStandard PRIMARY KEY CLUSTERED,
        AssessorApplicationScopeId INT NOT NULL,
        UnitStandardCode NVARCHAR(20) NOT NULL,
        UnitStandardTitle NVARCHAR(300) NOT NULL,
        NqfLevel INT NOT NULL CONSTRAINT DF_AssessorAppUS_Nqf DEFAULT (4),
        Credits INT NOT NULL CONSTRAINT DF_AssessorAppUS_Credits DEFAULT (15),
        IsPopulatedFromQualification BIT NOT NULL CONSTRAINT DF_AssessorAppUS_IsPopulated DEFAULT (1),
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorAppUS_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorAppUS_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorApplicationUnitStandard_Scope FOREIGN KEY (AssessorApplicationScopeId) REFERENCES dbo.AssessorApplicationScope (id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_AssessorApplicationUnitStandard_ScopeId ON dbo.AssessorApplicationUnitStandard (AssessorApplicationScopeId);
    CREATE NONCLUSTERED INDEX IX_AssessorApplicationUnitStandard_Code ON dbo.AssessorApplicationUnitStandard (UnitStandardCode);
    PRINT 'Created table dbo.AssessorApplicationUnitStandard.';
END

-- 4. AssessorApplicationProviderLink
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorApplicationProviderLink' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorApplicationProviderLink (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorApplicationProviderLink PRIMARY KEY CLUSTERED,
        AssessorRegistrationApplicationId INT NOT NULL,
        TrainingProviderId INT NOT NULL,
        SlaDocumentRef NVARCHAR(500) NULL,
        IsVerifiedByProvider BIT NOT NULL CONSTRAINT DF_AssessorAppProvider_IsVerified DEFAULT (0),
        VerificationDate DATETIME2(7) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorAppProvider_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorAppProvider_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorApplicationProviderLink_App FOREIGN KEY (AssessorRegistrationApplicationId) REFERENCES dbo.AssessorRegistrationApplication (id) ON DELETE CASCADE,
        CONSTRAINT FK_AssessorApplicationProviderLink_Provider FOREIGN KEY (TrainingProviderId) REFERENCES dbo.TrainingProvider (id) ON DELETE NO ACTION
    );

    CREATE NONCLUSTERED INDEX IX_AssessorApplicationProviderLink_AppId ON dbo.AssessorApplicationProviderLink (AssessorRegistrationApplicationId);
    CREATE NONCLUSTERED INDEX IX_AssessorApplicationProviderLink_ProviderId ON dbo.AssessorApplicationProviderLink (TrainingProviderId);
    PRINT 'Created table dbo.AssessorApplicationProviderLink.';
END

-- 5. AssessorApplicationDocument
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorApplicationDocument' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorApplicationDocument (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorApplicationDocument PRIMARY KEY CLUSTERED,
        AssessorRegistrationApplicationId INT NOT NULL,
        DocumentTypeCode NVARCHAR(50) NOT NULL,
        DocumentTitle NVARCHAR(250) NOT NULL,
        FileStoragePath NVARCHAR(500) NOT NULL,
        VersionNumber INT NOT NULL CONSTRAINT DF_AssessorAppDoc_Version DEFAULT (1),
        IsVerified BIT NOT NULL CONSTRAINT DF_AssessorAppDoc_IsVerified DEFAULT (0),
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorAppDoc_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorAppDoc_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorApplicationDocument_App FOREIGN KEY (AssessorRegistrationApplicationId) REFERENCES dbo.AssessorRegistrationApplication (id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_AssessorApplicationDocument_AppId ON dbo.AssessorApplicationDocument (AssessorRegistrationApplicationId);
    CREATE NONCLUSTERED INDEX IX_AssessorApplicationDocument_Type ON dbo.AssessorApplicationDocument (DocumentTypeCode);
    PRINT 'Created table dbo.AssessorApplicationDocument.';
END

-- 6. AssessorUnitStandardScope
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorUnitStandardScope' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorUnitStandardScope (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorUnitStandardScope PRIMARY KEY CLUSTERED,
        AssessorModeratorScopeId INT NOT NULL,
        UnitStandardCode NVARCHAR(20) NOT NULL,
        UnitStandardTitle NVARCHAR(300) NOT NULL,
        NqfLevel INT NOT NULL CONSTRAINT DF_AssessorUSScope_Nqf DEFAULT (4),
        Credits INT NOT NULL CONSTRAINT DF_AssessorUSScope_Credits DEFAULT (15),
        IsPopulatedFromQualification BIT NOT NULL CONSTRAINT DF_AssessorUSScope_IsPopulated DEFAULT (1),
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorUSScope_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorUSScope_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorUnitStandardScope_Scope FOREIGN KEY (AssessorModeratorScopeId) REFERENCES dbo.AssessorModeratorScope (id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_AssessorUnitStandardScope_ScopeId ON dbo.AssessorUnitStandardScope (AssessorModeratorScopeId);
    CREATE NONCLUSTERED INDEX IX_AssessorUnitStandardScope_Code ON dbo.AssessorUnitStandardScope (UnitStandardCode);
    PRINT 'Created table dbo.AssessorUnitStandardScope.';
END

-- 7. AssessorProviderLink
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorProviderLink' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorProviderLink (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorProviderLink PRIMARY KEY CLUSTERED,
        EtqaAssessorId INT NOT NULL,
        TrainingProviderId INT NOT NULL,
        SlaDocumentRef NVARCHAR(500) NULL,
        IsActive BIT NOT NULL CONSTRAINT DF_AssessorProviderLink_IsActive DEFAULT (1),
        VerifiedDate DATETIME2(7) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorProviderLink_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorProviderLink_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorProviderLink_Assessor FOREIGN KEY (EtqaAssessorId) REFERENCES dbo.EtqaAssessor (id) ON DELETE CASCADE,
        CONSTRAINT FK_AssessorProviderLink_Provider FOREIGN KEY (TrainingProviderId) REFERENCES dbo.TrainingProvider (id) ON DELETE NO ACTION
    );

    CREATE NONCLUSTERED INDEX IX_AssessorProviderLink_AssessorId ON dbo.AssessorProviderLink (EtqaAssessorId);
    CREATE NONCLUSTERED INDEX IX_AssessorProviderLink_ProviderId ON dbo.AssessorProviderLink (TrainingProviderId);
    PRINT 'Created table dbo.AssessorProviderLink.';
END

-- 8. AssessorDisciplinaryCase
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorDisciplinaryCase' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorDisciplinaryCase (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorDisciplinaryCase PRIMARY KEY CLUSTERED,
        EtqaAssessorId INT NOT NULL,
        CaseNumber NVARCHAR(50) NOT NULL,
        CaseType NVARCHAR(50) NOT NULL,
        ComplaintSummary NVARCHAR(2000) NOT NULL,
        ComplaintDocumentRef NVARCHAR(500) NULL,
        InvestigationStartDate DATETIME2(7) NULL,
        InvestigationEndDate DATETIME2(7) NULL,
        InvestigationReportSummary NVARCHAR(MAX) NULL,
        ReviewCommitteeDecisionNumber NVARCHAR(100) NULL,
        ReviewCommitteeDate DATETIME2(7) NULL,
        OutcomeCode NVARCHAR(30) NOT NULL CONSTRAINT DF_AssessorDisciplinary_Outcome DEFAULT ('DEREGISTERED'),
        SuspensionStartDate DATETIME2(7) NULL,
        SuspensionEndDate DATETIME2(7) NULL,
        DevelopmentPlanDetails NVARCHAR(MAX) NULL,
        DecisionLetterDocumentRef NVARCHAR(500) NULL,
        Status NVARCHAR(30) NOT NULL CONSTRAINT DF_AssessorDisciplinary_Status DEFAULT ('Open'),
        ClosedAt DATETIME2(7) NULL,
        ClosedByUserId NVARCHAR(100) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorDisciplinary_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorDisciplinary_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorDisciplinaryCase_Assessor FOREIGN KEY (EtqaAssessorId) REFERENCES dbo.EtqaAssessor (id) ON DELETE CASCADE
    );

    CREATE UNIQUE NONCLUSTERED INDEX UQ_AssessorDisciplinaryCase_Number ON dbo.AssessorDisciplinaryCase (CaseNumber);
    CREATE NONCLUSTERED INDEX IX_AssessorDisciplinaryCase_AssessorId ON dbo.AssessorDisciplinaryCase (EtqaAssessorId);
    CREATE NONCLUSTERED INDEX IX_AssessorDisciplinaryCase_Type ON dbo.AssessorDisciplinaryCase (CaseType);
    CREATE NONCLUSTERED INDEX IX_AssessorDisciplinaryCase_Status ON dbo.AssessorDisciplinaryCase (Status);
    PRINT 'Created table dbo.AssessorDisciplinaryCase.';
END

-- 9. Add DeRegistrationReason to EtqaAssessor if missing
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.EtqaAssessor') AND name = 'DeRegistrationReason')
BEGIN
    ALTER TABLE dbo.EtqaAssessor ADD DeRegistrationReason NVARCHAR(250) NULL;
END

-- 10. Phase 37: QCTO Occupational Accreditation & Trade Test Centre Statutory Governance (SDA §26I)
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'QctoAccreditationNumber')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD QctoAccreditationNumber NVARCHAR(50) NULL;
    PRINT 'Added QctoAccreditationNumber to dbo.TrainingProvider.';
END

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'QctoAccreditationStartDate')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD QctoAccreditationStartDate DATETIME2(7) NULL;
    PRINT 'Added QctoAccreditationStartDate to dbo.TrainingProvider.';
END

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'QctoAccreditationEndDate')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD QctoAccreditationEndDate DATETIME2(7) NULL;
    PRINT 'Added QctoAccreditationEndDate to dbo.TrainingProvider.';
END

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'QctoCentreCode')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD QctoCentreCode NVARCHAR(50) NULL;
    PRINT 'Added QctoCentreCode to dbo.TrainingProvider.';
END

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'QctoLetterAttachmentRef')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD QctoLetterAttachmentRef NVARCHAR(255) NULL;
    PRINT 'Added QctoLetterAttachmentRef to dbo.TrainingProvider.';
END

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'IX_TrainingProvider_QctoAccreditationNumber')
BEGIN
    CREATE NONCLUSTERED INDEX IX_TrainingProvider_QctoAccreditationNumber ON dbo.TrainingProvider (QctoAccreditationNumber) WHERE QctoAccreditationNumber IS NOT NULL;
    PRINT 'Created index IX_TrainingProvider_QctoAccreditationNumber.';
END

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'IX_TrainingProvider_NambRegistrationNumber')
BEGIN
    CREATE NONCLUSTERED INDEX IX_TrainingProvider_NambRegistrationNumber ON dbo.TrainingProvider (NambRegistrationNumber) WHERE NambRegistrationNumber IS NOT NULL;
    PRINT 'Created index IX_TrainingProvider_NambRegistrationNumber.';
END

-- ==============================================================================
-- Hierarchical Relational Fiscal Calendar Tables (FinancialYear & FinancialQuarter)
-- ==============================================================================

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'FinancialYear')
BEGIN
    CREATE TABLE [dbo].[FinancialYear] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_FinancialYear] PRIMARY KEY CLUSTERED,
        [FinYearCode] NVARCHAR(50) NOT NULL,
        [StartYear] INT NOT NULL,
        [EndYear] INT NOT NULL,
        [StartDate] DATETIME2 NOT NULL,
        [EndDate] DATETIME2 NOT NULL,
        [Description] NVARCHAR(500) NULL,
        [StatusCode] NVARCHAR(50) NOT NULL CONSTRAINT [DF_FinancialYear_StatusCode] DEFAULT 'Active',
        [IsActive] BIT NOT NULL CONSTRAINT [DF_FinancialYear_IsActive] DEFAULT 1,
        [IsClosed] BIT NOT NULL CONSTRAINT [DF_FinancialYear_IsClosed] DEFAULT 0,
        [SubmittedBy] NVARCHAR(100) NULL,
        [SubmittedAt] DATETIME2 NULL,
        [SubmissionNotes] NVARCHAR(500) NULL,
        [ReviewedBy] NVARCHAR(100) NULL,
        [ReviewedAt] DATETIME2 NULL,
        [ReviewNotes] NVARCHAR(500) NULL,
        [RevisionNumber] INT NOT NULL CONSTRAINT [DF_FinancialYear_RevisionNumber] DEFAULT 1,
        [AmendmentReason] NVARCHAR(500) NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_FinancialYear_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE UNIQUE INDEX [IX_FinancialYear_FinYearCode] ON [dbo].[FinancialYear]([FinYearCode]);
    CREATE INDEX [IX_FinancialYear_Dates] ON [dbo].[FinancialYear]([StartDate], [EndDate]);
    CREATE INDEX [IX_FinancialYear_Status] ON [dbo].[FinancialYear]([StatusCode], [IsActive]);
    
    PRINT 'Created table [FinancialYear]';
END
ELSE
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[FinancialYear]') AND name = 'SubmittedBy')
    BEGIN
        ALTER TABLE [dbo].[FinancialYear] ADD [SubmittedBy] NVARCHAR(100) NULL;
        ALTER TABLE [dbo].[FinancialYear] ADD [SubmittedAt] DATETIME2 NULL;
        ALTER TABLE [dbo].[FinancialYear] ADD [SubmissionNotes] NVARCHAR(500) NULL;
        ALTER TABLE [dbo].[FinancialYear] ADD [ReviewedBy] NVARCHAR(100) NULL;
        ALTER TABLE [dbo].[FinancialYear] ADD [ReviewedAt] DATETIME2 NULL;
        ALTER TABLE [dbo].[FinancialYear] ADD [ReviewNotes] NVARCHAR(500) NULL;
        ALTER TABLE [dbo].[FinancialYear] ADD [RevisionNumber] INT NOT NULL CONSTRAINT [DF_FinancialYear_RevisionNumber] DEFAULT 1;
        ALTER TABLE [dbo].[FinancialYear] ADD [AmendmentReason] NVARCHAR(500) NULL;
        PRINT 'Added workflow columns to [FinancialYear]';
    END;
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'FinancialQuarter')
BEGIN
    CREATE TABLE [dbo].[FinancialQuarter] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_FinancialQuarter] PRIMARY KEY CLUSTERED,
        [FinancialYearId] INT NOT NULL,
        [QuarterCode] NVARCHAR(10) NOT NULL,
        [QuarterNumber] INT NOT NULL,
        [StartDate] DATETIME2 NOT NULL,
        [EndDate] DATETIME2 NOT NULL,
        [Description] NVARCHAR(250) NULL,
        [IsLocked] BIT NOT NULL CONSTRAINT [DF_FinancialQuarter_IsLocked] DEFAULT 0,
        [IsClosed] BIT NOT NULL CONSTRAINT [DF_FinancialQuarter_IsClosed] DEFAULT 0,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_FinancialQuarter_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [FK_FinancialQuarter_FinancialYear_FinancialYearId] 
            FOREIGN KEY ([FinancialYearId]) REFERENCES [dbo].[FinancialYear]([Id]) ON DELETE CASCADE
    );

    CREATE UNIQUE INDEX [IX_FinancialQuarter_Year_Quarter] ON [dbo].[FinancialQuarter]([FinancialYearId], [QuarterCode]);
    CREATE INDEX [IX_FinancialQuarter_Dates] ON [dbo].[FinancialQuarter]([StartDate], [EndDate]);
    CREATE INDEX [IX_FinancialQuarter_Year_Number] ON [dbo].[FinancialQuarter]([FinancialYearId], [QuarterNumber]);

    PRINT 'Created table [FinancialQuarter]';
END;

IF NOT EXISTS (SELECT 1 FROM [dbo].[FinancialYear] WHERE [FinYearCode] = '2026/2027')
BEGIN
    INSERT INTO [dbo].[FinancialYear] ([FinYearCode], [StartYear], [EndYear], [StartDate], [EndDate], [Description], [StatusCode], [IsActive], [IsClosed], [CreatedBy])
    VALUES ('2026/2027', 2026, 2027, '2026-04-01', '2027-03-31', 'Official statutory merSETA 2026/2027 financial scheme year.', 'Active', 1, 0, 'SYSTEM_SEEDED');

    DECLARE @fy2026Id INT = SCOPE_IDENTITY();

    INSERT INTO [dbo].[FinancialQuarter] ([FinancialYearId], [QuarterCode], [QuarterNumber], [StartDate], [EndDate], [Description], [CreatedBy])
    VALUES 
        (@fy2026Id, 'Q1', 1, '2026-04-01', '2026-06-30', 'Quarter 1: April to June 2026', 'SYSTEM_SEEDED'),
        (@fy2026Id, 'Q2', 2, '2026-07-01', '2026-09-30', 'Quarter 2: July to September 2026', 'SYSTEM_SEEDED'),
        (@fy2026Id, 'Q3', 3, '2026-10-01', '2026-12-31', 'Quarter 3: October to December 2026', 'SYSTEM_SEEDED'),
        (@fy2026Id, 'Q4', 4, '2027-01-01', '2027-03-31', 'Quarter 4: January to March 2027', 'SYSTEM_SEEDED');

    PRINT 'Seeded FY 2026/2027 with 4 standard quarters.';
END;

IF NOT EXISTS (SELECT 1 FROM [dbo].[FinancialYear] WHERE [FinYearCode] = '2025/2026')
BEGIN
    INSERT INTO [dbo].[FinancialYear] ([FinYearCode], [StartYear], [EndYear], [StartDate], [EndDate], [Description], [StatusCode], [IsActive], [IsClosed], [CreatedBy])
    VALUES ('2025/2026', 2025, 2026, '2025-04-01', '2026-03-31', 'Concluded merSETA 2025/2026 financial scheme year.', 'Audited', 0, 1, 'SYSTEM_SEEDED');

    DECLARE @fy2025Id INT = SCOPE_IDENTITY();

    INSERT INTO [dbo].[FinancialQuarter] ([FinancialYearId], [QuarterCode], [QuarterNumber], [StartDate], [EndDate], [Description], [IsLocked], [IsClosed], [CreatedBy])
    VALUES 
        (@fy2025Id, 'Q1', 1, '2025-04-01', '2025-06-30', 'Quarter 1: April to June 2025', 1, 1, 'SYSTEM_SEEDED'),
        (@fy2025Id, 'Q2', 2, '2025-07-01', '2025-09-30', 'Quarter 2: July to September 2025', 1, 1, 'SYSTEM_SEEDED'),
        (@fy2025Id, 'Q3', 3, '2025-10-01', '2025-12-31', 'Quarter 3: October to December 2025', 1, 1, 'SYSTEM_SEEDED'),
        (@fy2025Id, 'Q4', 4, '2026-01-01', '2026-03-31', 'Quarter 4: January to March 2026', 1, 1, 'SYSTEM_SEEDED');

    PRINT 'Seeded FY 2025/2026 with 4 standard quarters.';
END;

IF NOT EXISTS (SELECT 1 FROM [dbo].[FinancialYear] WHERE [FinYearCode] = '2027/2028')
BEGIN
    INSERT INTO [dbo].[FinancialYear] ([FinYearCode], [StartYear], [EndYear], [StartDate], [EndDate], [Description], [StatusCode], [IsActive], [IsClosed], [CreatedBy])
    VALUES ('2027/2028', 2027, 2028, '2027-04-01', '2028-03-31', 'Upcoming merSETA 2027/2028 financial scheme year (Leap year Feb 2028).', 'Upcoming', 1, 0, 'SYSTEM_SEEDED');

    DECLARE @fy2027Id INT = SCOPE_IDENTITY();

    INSERT INTO [dbo].[FinancialQuarter] ([FinancialYearId], [QuarterCode], [QuarterNumber], [StartDate], [EndDate], [Description], [CreatedBy])
    VALUES 
        (@fy2027Id, 'Q1', 1, '2027-04-01', '2027-06-30', 'Quarter 1: April to June 2027', 'SYSTEM_SEEDED'),
        (@fy2027Id, 'Q2', 2, '2027-07-01', '2027-09-30', 'Quarter 2: July to September 2027', 'SYSTEM_SEEDED'),
        (@fy2027Id, 'Q3', 3, '2027-10-01', '2027-12-31', 'Quarter 3: October to December 2027', 'SYSTEM_SEEDED'),
        (@fy2027Id, 'Q4', 4, '2028-01-01', '2028-03-31', 'Quarter 4: January to March 2028 (29 days in Feb 2028)', 'SYSTEM_SEEDED');

    PRINT 'Seeded FY 2027/2028 with 4 standard quarters.';
END;

-- =========================================================================================
-- Phase 14: Enterprise Holidays & merSETA Institutional Closures Table
-- =========================================================================================
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'NonWorkingDay')
BEGIN
    CREATE TABLE [dbo].[NonWorkingDay] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_NonWorkingDay] PRIMARY KEY CLUSTERED,
        [Name] NVARCHAR(150) NOT NULL,
        [TypeCode] NVARCHAR(50) NOT NULL,
        [StartDate] DATETIME2 NOT NULL,
        [EndDate] DATETIME2 NOT NULL,
        [CalendarYear] INT NOT NULL,
        [AffectsSla] BIT NOT NULL CONSTRAINT [DF_NonWorkingDay_AffectsSla] DEFAULT 1,
        [IsRecurringAnnually] BIT NOT NULL CONSTRAINT [DF_NonWorkingDay_IsRecurring] DEFAULT 0,
        [GazetteOrResolutionRef] NVARCHAR(200) NULL,
        [Description] NVARCHAR(500) NULL,
        [StatusCode] NVARCHAR(50) NOT NULL CONSTRAINT [DF_NonWorkingDay_StatusCode] DEFAULT 'Approved',
        [IsActive] BIT NOT NULL CONSTRAINT [DF_NonWorkingDay_IsActive] DEFAULT 1,
        [IsClosed] BIT NOT NULL CONSTRAINT [DF_NonWorkingDay_IsClosed] DEFAULT 0,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_NonWorkingDay_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE INDEX [IX_NonWorkingDay_Name] ON [dbo].[NonWorkingDay]([Name]);
    CREATE INDEX [IX_NonWorkingDay_TypeCode] ON [dbo].[NonWorkingDay]([TypeCode]);
    CREATE INDEX [IX_NonWorkingDay_CalendarYear] ON [dbo].[NonWorkingDay]([CalendarYear]);
    CREATE INDEX [IX_NonWorkingDay_Dates] ON [dbo].[NonWorkingDay]([StartDate], [EndDate]);
    CREATE INDEX [IX_NonWorkingDay_Status] ON [dbo].[NonWorkingDay]([StatusCode], [IsActive]);
    CREATE INDEX [IX_NonWorkingDay_AffectsSla] ON [dbo].[NonWorkingDay]([AffectsSla]);

    PRINT 'Created NonWorkingDay table and indexes.';
END;

-- =========================================================================================
-- Phase 42: Universal Document Verification, Quality Marking, and Rejection Reasons Catalog
-- =========================================================================================
IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'lookup')
BEGIN
    EXEC('CREATE SCHEMA [lookup]');
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'DocumentAttachment' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[DocumentAttachment] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [TargetEntityName] NVARCHAR(100) NOT NULL,
        [TargetEntityId] INT NOT NULL,
        [FileName] NVARCHAR(255) NOT NULL,
        [OriginalFileName] NVARCHAR(255) NOT NULL,
        [ContentType] NVARCHAR(100) NOT NULL,
        [FileSizeBytes] BIGINT NOT NULL,
        [StorageProvider] NVARCHAR(50) NOT NULL DEFAULT 'Local',
        [StoragePath] NVARCHAR(500) NOT NULL,
        [FileHashSha256] NVARCHAR(100) NULL,
        [DocumentCategoryCode] NVARCHAR(50) NULL,
        [IsArchived] BIT NOT NULL DEFAULT 0,
        [VerificationStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Pending',
        [VerifiedBy] NVARCHAR(150) NULL,
        [VerifiedAt] DATETIME2 NULL,
        [DocumentCertificationDate] DATETIME2 NULL,
        [DocumentExpiryDate] DATETIME2 NULL,
        [VerificationNotes] NVARCHAR(2000) NULL,
        [RejectionReason] NVARCHAR(2000) NULL,
        [RejectionReasonCodesJson] NVARCHAR(MAX) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE NONCLUSTERED INDEX [IX_DocumentAttachment_TargetEntity] ON [dbo].[DocumentAttachment] ([TargetEntityName], [TargetEntityId]);
    CREATE NONCLUSTERED INDEX [IX_DocumentAttachment_DocumentCategoryCode] ON [dbo].[DocumentAttachment] ([DocumentCategoryCode]);
    CREATE NONCLUSTERED INDEX [IX_DocumentAttachment_IsArchived] ON [dbo].[DocumentAttachment] ([IsArchived]);
    CREATE NONCLUSTERED INDEX [IX_DocumentAttachment_VerificationStatusCode] ON [dbo].[DocumentAttachment] ([VerificationStatusCode]);
    PRINT 'Created DocumentAttachment table and indexes.';
END
ELSE
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[DocumentAttachment]') AND name = 'VerificationStatusCode')
    BEGIN
        ALTER TABLE [dbo].[DocumentAttachment] ADD [VerificationStatusCode] NVARCHAR(50) NOT NULL CONSTRAINT [DF_DocumentAttachment_VerificationStatusCode] DEFAULT 'Pending';
        ALTER TABLE [dbo].[DocumentAttachment] ADD [VerifiedBy] NVARCHAR(150) NULL;
        ALTER TABLE [dbo].[DocumentAttachment] ADD [VerifiedAt] DATETIME2 NULL;
        ALTER TABLE [dbo].[DocumentAttachment] ADD [DocumentCertificationDate] DATETIME2 NULL;
        ALTER TABLE [dbo].[DocumentAttachment] ADD [DocumentExpiryDate] DATETIME2 NULL;
        ALTER TABLE [dbo].[DocumentAttachment] ADD [VerificationNotes] NVARCHAR(2000) NULL;
        ALTER TABLE [dbo].[DocumentAttachment] ADD [RejectionReason] NVARCHAR(2000) NULL;
        ALTER TABLE [dbo].[DocumentAttachment] ADD [RejectionReasonCodesJson] NVARCHAR(MAX) NULL;
        CREATE NONCLUSTERED INDEX [IX_DocumentAttachment_VerificationStatusCode] ON [dbo].[DocumentAttachment] ([VerificationStatusCode]);
        PRINT 'Added verification columns to DocumentAttachment table.';
    END;
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'DocumentRejectionReasonType' AND schema_id = SCHEMA_ID('lookup'))
BEGIN
    CREATE TABLE [lookup].[DocumentRejectionReasonType] (
        [Code] NVARCHAR(50) NOT NULL PRIMARY KEY,
        [Name] NVARCHAR(200) NOT NULL,
        [Description] NVARCHAR(1000) NULL,
        [DocumentCategoryCode] NVARCHAR(50) NOT NULL DEFAULT 'ALL',
        [DisplayOrder] INT NOT NULL DEFAULT 0,
        [Active] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE NONCLUSTERED INDEX [IX_DocumentRejectionReasonType_DocumentCategoryCode] ON [lookup].[DocumentRejectionReasonType] ([DocumentCategoryCode]);
    CREATE NONCLUSTERED INDEX [IX_DocumentRejectionReasonType_Active] ON [lookup].[DocumentRejectionReasonType] ([Active]);
    CREATE NONCLUSTERED INDEX [IX_DocumentRejectionReasonType_DisplayOrder] ON [lookup].[DocumentRejectionReasonType] ([DisplayOrder]);
    PRINT 'Created lookup.DocumentRejectionReasonType table and indexes.';
END;

-- Phase 47: Discretionary Grant Funding Window Configuration, Stakeholder Eligibility & Template Blueprint Engine
IF NOT EXISTS (SELECT * FROM sys.tables t JOIN sys.schemas s ON t.schema_id = s.schema_id WHERE s.name = 'lookup' AND t.name = 'StakeholderEligibilityType')
BEGIN
    CREATE TABLE [lookup].[StakeholderEligibilityType] (
        [Code] NVARCHAR(50) NOT NULL PRIMARY KEY,
        [Name] NVARCHAR(250) NOT NULL,
        [Description] NVARCHAR(500) NULL,
        [Active] BIT NOT NULL CONSTRAINT DF_StakeholderEligibilityType_Active DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_StakeholderEligibilityType_CreatedAt DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_StakeholderEligibilityType_CreatedBy DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_StakeholderEligibilityType_Name] ON [lookup].[StakeholderEligibilityType] ([Name]);
    CREATE INDEX [IX_StakeholderEligibilityType_Active] ON [lookup].[StakeholderEligibilityType] ([Active]);
END;

IF EXISTS (SELECT * FROM sys.tables t JOIN sys.schemas s ON t.schema_id = s.schema_id WHERE s.name = 'lookup' AND t.name = 'InterventionType')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('lookup.InterventionType') AND name = 'IsPivotal')
        ALTER TABLE [lookup].[InterventionType] ADD [IsPivotal] BIT NOT NULL CONSTRAINT DF_InterventionType_IsPivotal DEFAULT 1;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('lookup.InterventionType') AND name = 'Category')
        ALTER TABLE [lookup].[InterventionType] ADD [Category] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('lookup.InterventionType') AND name = 'DefaultUnitCost')
        ALTER TABLE [lookup].[InterventionType] ADD [DefaultUnitCost] DECIMAL(18,2) NOT NULL CONSTRAINT DF_InterventionType_Cost DEFAULT 0.00;
END;

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantFundingWindow')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'IsPivotal')
        ALTER TABLE [dbo].[GrantFundingWindow] ADD [IsPivotal] BIT NOT NULL CONSTRAINT DF_GrantFundingWindow_IsPivotal DEFAULT 1;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'WindowClassification')
        ALTER TABLE [dbo].[GrantFundingWindow] ADD [WindowClassification] NVARCHAR(50) NOT NULL CONSTRAINT DF_GrantFundingWindow_Classification DEFAULT 'Pivotal';

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'RequireWspCompliance')
        ALTER TABLE [dbo].[GrantFundingWindow] ADD [RequireWspCompliance] BIT NOT NULL CONSTRAINT DF_GrantFundingWindow_RequireWsp DEFAULT 0;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'TemplateId')
        ALTER TABLE [dbo].[GrantFundingWindow] ADD [TemplateId] INT NULL;

    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_GrantFundingWindow_IsPivotal' AND object_id = OBJECT_ID('GrantFundingWindow'))
        CREATE INDEX [IX_GrantFundingWindow_IsPivotal] ON [dbo].[GrantFundingWindow] ([IsPivotal]);

    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_GrantFundingWindow_TemplateId' AND object_id = OBJECT_ID('GrantFundingWindow'))
        CREATE INDEX [IX_GrantFundingWindow_TemplateId] ON [dbo].[GrantFundingWindow] ([TemplateId]);
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantWindowEligibility')
BEGIN
    CREATE TABLE [dbo].[GrantWindowEligibility] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [FundingWindowId] INT NOT NULL CONSTRAINT FK_GrantWindowEligibility_Window FOREIGN KEY REFERENCES [dbo].[GrantFundingWindow]([Id]) ON DELETE CASCADE,
        [StakeholderEligibilityTypeCode] NVARCHAR(50) NOT NULL CONSTRAINT FK_GrantWindowEligibility_Type FOREIGN KEY REFERENCES [lookup].[StakeholderEligibilityType]([Code]),
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_GrantWindowEligibility_CreatedAt DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_GrantWindowEligibility_CreatedBy DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT UQ_GrantWindowEligibility UNIQUE ([FundingWindowId], [StakeholderEligibilityTypeCode])
    );
    CREATE INDEX [IX_GrantWindowEligibility_WindowId] ON [dbo].[GrantWindowEligibility] ([FundingWindowId]);
    CREATE INDEX [IX_GrantWindowEligibility_TypeCode] ON [dbo].[GrantWindowEligibility] ([StakeholderEligibilityTypeCode]);
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantWindowIntervention')
BEGIN
    CREATE TABLE [dbo].[GrantWindowIntervention] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [FundingWindowId] INT NOT NULL CONSTRAINT FK_GrantWindowIntervention_Window FOREIGN KEY REFERENCES [dbo].[GrantFundingWindow]([Id]) ON DELETE CASCADE,
        [InterventionTypeCode] NVARCHAR(50) NOT NULL CONSTRAINT FK_GrantWindowIntervention_Type FOREIGN KEY REFERENCES [lookup].[InterventionType]([Code]),
        [MaxBudgetCap] DECIMAL(18,2) NULL,
        [MaxLearnerCap] INT NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_GrantWindowIntervention_CreatedAt DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_GrantWindowIntervention_CreatedBy DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT UQ_GrantWindowIntervention UNIQUE ([FundingWindowId], [InterventionTypeCode])
    );
    CREATE INDEX [IX_GrantWindowIntervention_WindowId] ON [dbo].[GrantWindowIntervention] ([FundingWindowId]);
    CREATE INDEX [IX_GrantWindowIntervention_TypeCode] ON [dbo].[GrantWindowIntervention] ([InterventionTypeCode]);
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantWindowTemplate')
BEGIN
    CREATE TABLE [dbo].[GrantWindowTemplate] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [TemplateCode] NVARCHAR(50) NOT NULL CONSTRAINT UQ_GrantWindowTemplate_Code UNIQUE,
        [Name] NVARCHAR(200) NOT NULL,
        [Description] NVARCHAR(1000) NULL,
        [IsPivotal] BIT NOT NULL CONSTRAINT DF_GrantWindowTemplate_IsPivotal DEFAULT 1,
        [WindowClassification] NVARCHAR(50) NOT NULL CONSTRAINT DF_GrantWindowTemplate_Classification DEFAULT 'Pivotal',
        [RequireWspComplianceDefault] BIT NOT NULL CONSTRAINT DF_GrantWindowTemplate_RequireWsp DEFAULT 0,
        [EstimatedDurationDays] INT NOT NULL CONSTRAINT DF_GrantWindowTemplate_Duration DEFAULT 45,
        [IsActive] BIT NOT NULL CONSTRAINT DF_GrantWindowTemplate_Active DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_GrantWindowTemplate_CreatedAt DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_GrantWindowTemplate_CreatedBy DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_GrantWindowTemplate_Active] ON [dbo].[GrantWindowTemplate] ([IsActive]);
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantWindowTemplateEligibility')
BEGIN
    CREATE TABLE [dbo].[GrantWindowTemplateEligibility] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [TemplateId] INT NOT NULL CONSTRAINT FK_GrantWindowTemplateEligibility_Template FOREIGN KEY REFERENCES [dbo].[GrantWindowTemplate]([Id]) ON DELETE CASCADE,
        [StakeholderEligibilityTypeCode] NVARCHAR(50) NOT NULL CONSTRAINT FK_GrantWindowTemplateEligibility_Type FOREIGN KEY REFERENCES [lookup].[StakeholderEligibilityType]([Code]),
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_GrantWindowTemplateEligibility_CreatedAt DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_GrantWindowTemplateEligibility_CreatedBy DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT UQ_GrantWindowTemplateEligibility UNIQUE ([TemplateId], [StakeholderEligibilityTypeCode])
    );
    CREATE INDEX [IX_GrantWindowTemplateEligibility_TemplateId] ON [dbo].[GrantWindowTemplateEligibility] ([TemplateId]);
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantWindowTemplateIntervention')
BEGIN
    CREATE TABLE [dbo].[GrantWindowTemplateIntervention] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [TemplateId] INT NOT NULL CONSTRAINT FK_GrantWindowTemplateIntervention_Template FOREIGN KEY REFERENCES [dbo].[GrantWindowTemplate]([Id]) ON DELETE CASCADE,
        [InterventionTypeCode] NVARCHAR(50) NOT NULL CONSTRAINT FK_GrantWindowTemplateIntervention_Type FOREIGN KEY REFERENCES [lookup].[InterventionType]([Code]),
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_GrantWindowTemplateIntervention_CreatedAt DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_GrantWindowTemplateIntervention_CreatedBy DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT UQ_GrantWindowTemplateIntervention UNIQUE ([TemplateId], [InterventionTypeCode])
    );
    CREATE INDEX [IX_GrantWindowTemplateIntervention_TemplateId] ON [dbo].[GrantWindowTemplateIntervention] ([TemplateId]);
END;

-- Phase 49: Option A - Dynamic Multi-Section Composite DG Structure & Interventions
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantApplication')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'ProjectDescription')
        ALTER TABLE [dbo].[GrantApplication] ADD [ProjectDescription] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'Purpose')
        ALTER TABLE [dbo].[GrantApplication] ADD [Purpose] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'Outcomes')
        ALTER TABLE [dbo].[GrantApplication] ADD [Outcomes] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'Benefits')
        ALTER TABLE [dbo].[GrantApplication] ADD [Benefits] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'PotentialRisks')
        ALTER TABLE [dbo].[GrantApplication] ADD [PotentialRisks] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'EstimatedOverallProjectCost')
        ALTER TABLE [dbo].[GrantApplication] ADD [EstimatedOverallProjectCost] DECIMAL(18,2) NOT NULL CONSTRAINT DF_GrantApplication_EstimatedCost DEFAULT 0.00;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'NumberOfBeneficiaries')
        ALTER TABLE [dbo].[GrantApplication] ADD [NumberOfBeneficiaries] INT NOT NULL CONSTRAINT DF_GrantApplication_Beneficiaries DEFAULT 0;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'RequireProjectAdministrationCosts')
        ALTER TABLE [dbo].[GrantApplication] ADD [RequireProjectAdministrationCosts] BIT NOT NULL CONSTRAINT DF_GrantApplication_AdminCosts DEFAULT 0;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'TargetProvinces')
        ALTER TABLE [dbo].[GrantApplication] ADD [TargetProvinces] NVARCHAR(500) NULL;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'HasPivotalInterventions')
        ALTER TABLE [dbo].[GrantApplication] ADD [HasPivotalInterventions] BIT NOT NULL CONSTRAINT DF_GrantApplication_HasPivotal DEFAULT 1;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'HasNonPivotalInterventions')
        ALTER TABLE [dbo].[GrantApplication] ADD [HasNonPivotalInterventions] BIT NOT NULL CONSTRAINT DF_GrantApplication_HasNonPivotal DEFAULT 0;

    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_GrantApplication_HasPivotal' AND object_id = OBJECT_ID('GrantApplication'))
        CREATE INDEX [IX_GrantApplication_HasPivotal] ON [dbo].[GrantApplication] ([HasPivotalInterventions]);

    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_GrantApplication_HasNonPivotal' AND object_id = OBJECT_ID('GrantApplication'))
        CREATE INDEX [IX_GrantApplication_HasNonPivotal] ON [dbo].[GrantApplication] ([HasNonPivotalInterventions]);
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantApplicationIntervention')
BEGIN
    CREATE TABLE [dbo].[GrantApplicationIntervention] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [GrantApplicationId] INT NOT NULL CONSTRAINT FK_GrantApplicationIntervention_App FOREIGN KEY REFERENCES [dbo].[GrantApplication]([Id]) ON DELETE CASCADE,
        [InterventionTypeCode] NVARCHAR(50) NOT NULL CONSTRAINT FK_GrantApplicationIntervention_Type FOREIGN KEY REFERENCES [lookup].[InterventionType]([Code]),
        [IsPivotal] BIT NOT NULL CONSTRAINT DF_GrantApplicationIntervention_IsPivotal DEFAULT 1,
        [SaqaId] NVARCHAR(50) NULL,
        [QualificationTitle] NVARCHAR(300) NULL,
        [NqfLevel] NVARCHAR(50) NULL,
        [OfoCode] NVARCHAR(100) NULL,
        [LearnerCountEmployed] INT NOT NULL CONSTRAINT DF_GrantAppIntervention_18_1 DEFAULT 0,
        [LearnerCountUnemployed] INT NOT NULL CONSTRAINT DF_GrantAppIntervention_18_2 DEFAULT 0,
        [UnitCost] DECIMAL(18,2) NOT NULL CONSTRAINT DF_GrantAppIntervention_UnitCost DEFAULT 0.00,
        [DeliverableName] NVARCHAR(300) NULL,
        [TargetQuantity] INT NULL,
        [EstimatedCost] DECIMAL(18,2) NOT NULL CONSTRAINT DF_GrantAppIntervention_EstCost DEFAULT 0.00,
        [ProjectedStartDate] DATETIME2 NULL,
        [ProjectedEndDate] DATETIME2 NULL,
        [ActualEndDate] DATETIME2 NULL,
        [MilestoneNumber] INT NULL,
        [TotalAmount] DECIMAL(18,2) NOT NULL CONSTRAINT DF_GrantAppIntervention_Total DEFAULT 0.00,
        [Comments] NVARCHAR(MAX) NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_GrantAppIntervention_CreatedAt DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_GrantAppIntervention_CreatedBy DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE INDEX [IX_GrantApplicationIntervention_AppId] ON [dbo].[GrantApplicationIntervention] ([GrantApplicationId]);
    CREATE INDEX [IX_GrantApplicationIntervention_TypeCode] ON [dbo].[GrantApplicationIntervention] ([InterventionTypeCode]);
    CREATE INDEX [IX_GrantApplicationIntervention_IsPivotal] ON [dbo].[GrantApplicationIntervention] ([IsPivotal]);
END;

PRINT 'Complete Idempotent Enterprise DDL Deployment Succeeded!';





