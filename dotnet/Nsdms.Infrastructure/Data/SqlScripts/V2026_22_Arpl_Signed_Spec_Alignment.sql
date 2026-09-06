-- ============================================================================
-- V2026_22_Arpl_Signed_Spec_Alignment.sql
-- MerSETA NSDMS 2.0 - Artisan Recognition of Prior Learning (ARPL) Schema Alignment
-- Based on signed specification: ARPL Registration Application Use Case 27012023.NMok.signed.pdf
-- ============================================================================

-- 1. PersonContact table extensions (Address lines, postal toggle, Next of Kin, secondary email)
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.PersonContact') AND name = 'PhysicalAddressLine2')
BEGIN
    ALTER TABLE dbo.PersonContact ADD PhysicalAddressLine2 NVARCHAR(150) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.PersonContact') AND name = 'PhysicalAddressLine3')
BEGIN
    ALTER TABLE dbo.PersonContact ADD PhysicalAddressLine3 NVARCHAR(150) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.PersonContact') AND name = 'PostalAddressLine2')
BEGIN
    ALTER TABLE dbo.PersonContact ADD PostalAddressLine2 NVARCHAR(150) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.PersonContact') AND name = 'PostalAddressLine3')
BEGIN
    ALTER TABLE dbo.PersonContact ADD PostalAddressLine3 NVARCHAR(150) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.PersonContact') AND name = 'IsPostalSameAsPhysical')
BEGIN
    ALTER TABLE dbo.PersonContact ADD IsPostalSameAsPhysical BIT NOT NULL CONSTRAINT DF_PersonContact_IsPostalSameAsPhysical DEFAULT 0;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.PersonContact') AND name = 'NextOfKinName')
BEGIN
    ALTER TABLE dbo.PersonContact ADD NextOfKinName NVARCHAR(150) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.PersonContact') AND name = 'NextOfKinContactNumber')
BEGIN
    ALTER TABLE dbo.PersonContact ADD NextOfKinContactNumber NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.PersonContact') AND name = 'SecondaryEmail')
BEGIN
    ALTER TABLE dbo.PersonContact ADD SecondaryEmail NVARCHAR(150) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.PersonContact') AND name = 'UrbanRuralId')
BEGIN
    ALTER TABLE dbo.PersonContact ADD UrbanRuralId NVARCHAR(10) NULL;
END
GO

-- 2. Person Maiden Surname
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.Person') AND name = 'MaidenSurname')
BEGIN
    ALTER TABLE dbo.Person ADD MaidenSurname NVARCHAR(100) NULL;
END
GO

-- 3. LearnerTradeTestApplication statutory columns
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'RequiresToolkit')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD RequiresToolkit BIT NOT NULL CONSTRAINT DF_LTTA_RequiresToolkit DEFAULT 0;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'PreferredTradeTestCenterId')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD PreferredTradeTestCenterId INT NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'QualificationId')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD QualificationId INT NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'Specialisation')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD Specialisation NVARCHAR(150) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'HasAttemptedTradeTestPreviously')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD HasAttemptedTradeTestPreviously BIT NOT NULL CONSTRAINT DF_LTTA_HasAttemptedTradeTestPreviously DEFAULT 0;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'PreviousAssessmentCenterName')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD PreviousAssessmentCenterName NVARCHAR(150) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'PreviousAttemptDate')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD PreviousAttemptDate DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'PreviousAttemptsCount')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD PreviousAttemptsCount INT NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'LearnerSubmissionDate')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD LearnerSubmissionDate DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'QualifyingCategory')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD QualifyingCategory INT NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'NoticeDispatchedDate')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD NoticeDispatchedDate DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'ResultsUploadDeadlineDate')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD ResultsUploadDeadlineDate DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'ClaUserId')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD ClaUserId NVARCHAR(100) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'ClaRecommendationDate')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD ClaRecommendationDate DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'ClaRecommendationStatus')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD ClaRecommendationStatus NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'ClaRejectionReason')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD ClaRejectionReason NVARCHAR(500) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'QaUserId')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD QaUserId NVARCHAR(100) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'QaApprovalDate')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD QaApprovalDate DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'QaApprovalStatus')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD QaApprovalStatus NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'IsFinalRejection')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD IsFinalRejection BIT NOT NULL CONSTRAINT DF_LTTA_IsFinalRejection DEFAULT 0;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'QaRejectionReason')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD QaRejectionReason NVARCHAR(500) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'QaSignedApplicationDocumentAttachmentId')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD QaSignedApplicationDocumentAttachmentId INT NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'TradeTestSerialNumber')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD TradeTestSerialNumber NVARCHAR(100) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'NambPackDocumentAttachmentId')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD NambPackDocumentAttachmentId INT NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'NambPackVerifiedAt')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD NambPackVerifiedAt DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'NambPackVerifiedByUserId')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD NambPackVerifiedByUserId NVARCHAR(100) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'ScannedCertificateDocumentAttachmentId')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD ScannedCertificateDocumentAttachmentId INT NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'IsSelectedForQaAuditSample')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD IsSelectedForQaAuditSample BIT NOT NULL CONSTRAINT DF_LTTA_IsSelectedForQaAuditSample DEFAULT 0;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'QaAuditSampleStatus')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD QaAuditSampleStatus NVARCHAR(50) NULL CONSTRAINT DF_LTTA_QaAuditSampleStatus DEFAULT 'None';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'WithdrawalReasonCode')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD WithdrawalReasonCode NVARCHAR(100) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'WithdrawalNotes')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD WithdrawalNotes NVARCHAR(1000) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'WithdrawnAt')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD WithdrawnAt DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'WithdrawnBy')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD WithdrawnBy NVARCHAR(100) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'IsWithdrawn')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD IsWithdrawn BIT NOT NULL CONSTRAINT DF_LTTA_IsWithdrawn DEFAULT 0;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'CertificateDistributedAt')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD CertificateDistributedAt DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'EmploymentStatus')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD EmploymentStatus NVARCHAR(50) NOT NULL CONSTRAINT DF_LTTA_EmploymentStatus DEFAULT 'Employed';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'UnregisteredEmployerName')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD UnregisteredEmployerName NVARCHAR(200) NULL;
END
GO

-- 3b. ArplExperienceDetail multi-employer columns
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.ArplExperienceDetail') AND name = 'CompanyRegistrationNumber')
BEGIN
    ALTER TABLE dbo.ArplExperienceDetail ADD CompanyRegistrationNumber NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.ArplExperienceDetail') AND name = 'ContactPersonName')
BEGIN
    ALTER TABLE dbo.ArplExperienceDetail ADD ContactPersonName NVARCHAR(150) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.ArplExperienceDetail') AND name = 'ContactPhoneNumber')
BEGIN
    ALTER TABLE dbo.ArplExperienceDetail ADD ContactPhoneNumber NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.ArplExperienceDetail') AND name = 'EmployerAddress')
BEGIN
    ALTER TABLE dbo.ArplExperienceDetail ADD EmployerAddress NVARCHAR(300) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.ArplExperienceDetail') AND name = 'YearsOfExperience')
BEGIN
    ALTER TABLE dbo.ArplExperienceDetail ADD YearsOfExperience DECIMAL(18,2) NOT NULL CONSTRAINT DF_AED_YearsExp DEFAULT 0;
END
GO

-- 4. TradeTestTask attempt and credit retention columns
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TradeTestTask') AND name = 'AttemptNumber')
BEGIN
    ALTER TABLE dbo.TradeTestTask ADD AttemptNumber INT NOT NULL CONSTRAINT DF_TradeTestTask_AttemptNumber DEFAULT 1;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TradeTestTask') AND name = 'TaskCode')
BEGIN
    ALTER TABLE dbo.TradeTestTask ADD TaskCode NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TradeTestTask') AND name = 'IsRetainedCredit')
BEGIN
    ALTER TABLE dbo.TradeTestTask ADD IsRetainedCredit BIT NOT NULL CONSTRAINT DF_TradeTestTask_IsRetainedCredit DEFAULT 0;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TradeTestTask') AND name = 'CreditRetentionExpiryDate')
BEGIN
    ALTER TABLE dbo.TradeTestTask ADD CreditRetentionExpiryDate DATETIME2 NULL;
END
GO

-- 5. LearnerTradeTestApplicationId link on LearnerTradeTest
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTest') AND name = 'LearnerTradeTestApplicationId')
BEGIN
    ALTER TABLE dbo.LearnerTradeTest ADD LearnerTradeTestApplicationId INT NULL;
END
GO

-- 6. LearnerTradeTestWithdrawal Table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'LearnerTradeTestWithdrawal' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.LearnerTradeTestWithdrawal (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_LearnerTradeTestWithdrawal PRIMARY KEY CLUSTERED,
        LearnerTradeTestApplicationId INT NOT NULL,
        WithdrawalReasonCode NVARCHAR(100) NOT NULL CONSTRAINT DF_LTTW_Reason DEFAULT 'CandidateRequested',
        WithdrawalJustification NVARCHAR(1000) NOT NULL,
        WithdrawnByUserId NVARCHAR(100) NOT NULL CONSTRAINT DF_LTTW_User DEFAULT 'SYSTEM',
        WithdrawalDate DATETIME2 NOT NULL CONSTRAINT DF_LTTW_Date DEFAULT GETUTCDATE(),
        CreatedAt DATETIME2 NOT NULL CONSTRAINT DF_LTTW_CreatedAt DEFAULT GETUTCDATE(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_LTTW_CreatedBy DEFAULT 'SYSTEM',
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_LearnerTradeTestWithdrawal_Application FOREIGN KEY (LearnerTradeTestApplicationId)
            REFERENCES dbo.LearnerTradeTestApplication(Id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_LearnerTradeTestWithdrawal_ApplicationId 
        ON dbo.LearnerTradeTestWithdrawal(LearnerTradeTestApplicationId);
END
GO

-- 7. ArplDocumentChecklist Table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'ArplDocumentChecklist' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.ArplDocumentChecklist (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_ArplDocumentChecklist PRIMARY KEY CLUSTERED,
        LearnerTradeTestApplicationId INT NOT NULL,
        DocumentTypeCode NVARCHAR(100) NOT NULL,
        DocumentTitle NVARCHAR(200) NOT NULL,
        DocumentAttachmentId INT NULL,
        IsUploaded BIT NOT NULL CONSTRAINT DF_ADC_IsUploaded DEFAULT 0,
        UploadedAt DATETIME2 NULL,
        UploadedByUserId NVARCHAR(100) NULL,
        IsVerified BIT NOT NULL CONSTRAINT DF_ADC_IsVerified DEFAULT 0,
        VerifiedByUserId NVARCHAR(100) NULL,
        VerifiedDate DATETIME2 NULL,
        RejectionReason NVARCHAR(500) NULL,
        CreatedAt DATETIME2 NOT NULL CONSTRAINT DF_ADC_CreatedAt DEFAULT GETUTCDATE(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_ADC_CreatedBy DEFAULT 'SYSTEM',
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_ArplDocumentChecklist_Application FOREIGN KEY (LearnerTradeTestApplicationId)
            REFERENCES dbo.LearnerTradeTestApplication(Id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_ArplDocumentChecklist_ApplicationId_TypeCode 
        ON dbo.ArplDocumentChecklist(LearnerTradeTestApplicationId, DocumentTypeCode);
END
GO

-- 8. CertificateDistributionEvent Table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'CertificateDistributionEvent' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.CertificateDistributionEvent (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_CertificateDistributionEvent PRIMARY KEY CLUSTERED,
        LearnerTradeTestApplicationId INT NOT NULL,
        DistributionMethodCode NVARCHAR(100) NOT NULL CONSTRAINT DF_CDE_Method DEFAULT 'RegisteredMail',
        ConsignmentOrTrackingNumber NVARCHAR(150) NULL,
        DispatchedDate DATETIME2 NOT NULL CONSTRAINT DF_CDE_DispatchedDate DEFAULT GETUTCDATE(),
        RecipientName NVARCHAR(150) NULL,
        RecipientIdNumber NVARCHAR(50) NULL,
        ReceivedDate DATETIME2 NULL,
        DispatchedByUserId NVARCHAR(100) NULL,
        Notes NVARCHAR(1000) NULL,
        CreatedAt DATETIME2 NOT NULL CONSTRAINT DF_CDE_CreatedAt DEFAULT GETUTCDATE(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_CDE_CreatedBy DEFAULT 'SYSTEM',
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_CertificateDistributionEvent_Application FOREIGN KEY (LearnerTradeTestApplicationId)
            REFERENCES dbo.LearnerTradeTestApplication(Id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_CertificateDistributionEvent_ApplicationId 
        ON dbo.CertificateDistributionEvent(LearnerTradeTestApplicationId);
END
GO
