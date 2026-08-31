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
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_audit_logs PRIMARY KEY CLUSTERED,
        entity_name NVARCHAR(100) NOT NULL,
        record_id INT NOT NULL,
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
        PhoneNumber NVARCHAR(50) NOT NULL,
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
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_Organisation_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_Organisation_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX UX_Organisation_SdlNumber ON dbo.Organisation (SdlNumber);
    CREATE NONCLUSTERED INDEX IX_Organisation_CompanyName ON dbo.Organisation (CompanyName);
    CREATE NONCLUSTERED INDEX IX_Organisation_Status ON dbo.Organisation (OrganisationStatusCode);
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
        AddendaTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Addenda_Type DEFAULT N'ValueAndTimeline',
        OriginalContractValue DECIMAL(18,2) NOT NULL,
        RevisedContractValue DECIMAL(18,2) NOT NULL,
        ContractValueVariance DECIMAL(18,2) NOT NULL,
        OriginalEndDate DATETIME2(7) NOT NULL,
        RevisedEndDate DATETIME2(7) NOT NULL,
        MotivationReason NVARCHAR(MAX) NOT NULL,
        AddendaDocumentPath NVARCHAR(500) NULL,
        StatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Addenda_Status DEFAULT N'SubmittedForReview',
        ExecutiveApprovalDate DATETIME2(7) NULL,
        ExecutiveApprovedByUserId NVARCHAR(100) NULL,
        ExecutiveNotes NVARCHAR(500) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_Addenda_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_Addenda_CreatedBy DEFAULT N'SYSTEM',
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX UX_Addenda_Number ON dbo.ContractAddenda (AddendaNumber);
    CREATE NONCLUSTERED INDEX IX_Addenda_Moa ON dbo.ContractAddenda (GrantMoaId);
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
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('WorkplaceApprovalMentor') AND name = 'Notes')
        ALTER TABLE dbo.WorkplaceApprovalMentor ADD Notes NVARCHAR(500) NULL;
END

PRINT 'Complete Idempotent Enterprise DDL Deployment Succeeded!';

