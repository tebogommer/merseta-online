-- V2026_13_Phase3_Core_Statutory_Workflows.sql
-- Idempotent T-SQL schema update for Phase 3: Core Statutory Workflows

-- 1. Alter WspSubmission to add multi-party quorum signoff fields
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WspSubmission]') AND name = 'IsSignoffQuorumMet')
BEGIN
    ALTER TABLE [dbo].[WspSubmission] ADD [IsSignoffQuorumMet] BIT NOT NULL CONSTRAINT DF_WspSubmission_IsSignoffQuorumMet DEFAULT 0;
END;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WspSubmission]') AND name = 'RequiredSignoffCount')
BEGIN
    ALTER TABLE [dbo].[WspSubmission] ADD [RequiredSignoffCount] INT NOT NULL CONSTRAINT DF_WspSubmission_RequiredSignoffCount DEFAULT 2;
END;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WspSubmission]') AND name = 'CompletedSignoffCount')
BEGIN
    ALTER TABLE [dbo].[WspSubmission] ADD [CompletedSignoffCount] INT NOT NULL CONSTRAINT DF_WspSubmission_CompletedSignoffCount DEFAULT 0;
END;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WspSubmission]') AND name = 'SignoffDigitalSecuritySeal')
BEGIN
    ALTER TABLE [dbo].[WspSubmission] ADD [SignoffDigitalSecuritySeal] NVARCHAR(128) NULL;
END;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WspSubmission]') AND name = 'DisputeLogged')
BEGIN
    ALTER TABLE [dbo].[WspSubmission] ADD [DisputeLogged] BIT NOT NULL CONSTRAINT DF_WspSubmission_DisputeLogged DEFAULT 0;
END;

-- 2. Create WspSignoffAttestation Table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'WspSignoffAttestation')
BEGIN
    CREATE TABLE [dbo].[WspSignoffAttestation] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_WspSignoffAttestation PRIMARY KEY CLUSTERED,
        [WspSubmissionId] INT NOT NULL,
        [SignerRoleCode] NVARCHAR(50) NOT NULL,
        [SignerFullName] NVARCHAR(250) NOT NULL,
        [SignerEmail] NVARCHAR(200) NOT NULL,
        [SignerRsaId] NVARCHAR(50) NULL,
        [UnionName] NVARCHAR(150) NULL,
        [OtpToken] NVARCHAR(50) NULL,
        [OtpVerifiedAt] DATETIME2 NULL,
        [DigitalSecuritySeal] NVARCHAR(128) NOT NULL,
        [SignoffDate] DATETIME2 NOT NULL CONSTRAINT DF_WspSignoff_SignoffDate DEFAULT SYSUTCDATETIME(),
        [AttestationStatement] NVARCHAR(MAX) NOT NULL,
        [SignerNotes] NVARCHAR(MAX) NULL,
        [DisputeLogged] BIT NOT NULL CONSTRAINT DF_WspSignoff_DisputeLogged DEFAULT 0,
        [WspDisputeId] INT NULL,
        [AttestationStatusCode] NVARCHAR(50) NOT NULL CONSTRAINT DF_WspSignoff_Status DEFAULT 'SignedOff',
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_WspSignoff_CreatedAt DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_WspSignoff_CreatedBy DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT FK_WspSignoffAttestation_WspSubmission FOREIGN KEY ([WspSubmissionId]) REFERENCES [dbo].[WspSubmission]([Id]) ON DELETE CASCADE
    );

    CREATE INDEX [IX_WspSignoffAttestation_WspSubmissionId] ON [dbo].[WspSignoffAttestation] ([WspSubmissionId]);
    CREATE INDEX [IX_WspSignoffAttestation_SignerRoleCode] ON [dbo].[WspSignoffAttestation] ([SignerRoleCode]);
    CREATE INDEX [IX_WspSignoffAttestation_AttestationStatusCode] ON [dbo].[WspSignoffAttestation] ([AttestationStatusCode]);
END;

-- 3. Create CompanyLearnerChangeRequest Table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'CompanyLearnerChangeRequest')
BEGIN
    CREATE TABLE [dbo].[CompanyLearnerChangeRequest] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_CompanyLearnerChangeRequest PRIMARY KEY CLUSTERED,
        [CompanyLearnerId] INT NOT NULL,
        [ChangeTypeCode] NVARCHAR(50) NOT NULL,
        [CurrentValuesSnapshotJson] NVARCHAR(MAX) NOT NULL,
        [RequestedValuesJson] NVARCHAR(MAX) NOT NULL,
        [JustificationReason] NVARCHAR(MAX) NOT NULL,
        [ChangeStatusCode] NVARCHAR(50) NOT NULL CONSTRAINT DF_LearnerChangeReq_Status DEFAULT 'Pending',
        [ReviewerComments] NVARCHAR(MAX) NULL,
        [ReviewedByUserId] NVARCHAR(100) NULL,
        [ReviewDate] DATETIME2 NULL,
        [ApprovedByUserId] NVARCHAR(100) NULL,
        [ApprovalDate] DATETIME2 NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_LearnerChangeReq_CreatedAt DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_LearnerChangeReq_CreatedBy DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT FK_CompanyLearnerChangeRequest_CompanyLearner FOREIGN KEY ([CompanyLearnerId]) REFERENCES [dbo].[CompanyLearner]([Id]) ON DELETE CASCADE
    );

    CREATE INDEX [IX_CompanyLearnerChangeRequest_Learner] ON [dbo].[CompanyLearnerChangeRequest] ([CompanyLearnerId]);
    CREATE INDEX [IX_CompanyLearnerChangeRequest_Status] ON [dbo].[CompanyLearnerChangeRequest] ([ChangeStatusCode]);
END;

-- 4. Alter GrantPaymentClaim with Milestone & Multi-Tier DOFA Columns
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[GrantPaymentClaim]') AND name = 'GrantMoaMilestoneId')
BEGIN
    ALTER TABLE [dbo].[GrantPaymentClaim] ADD [GrantMoaMilestoneId] INT NULL;
END;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[GrantPaymentClaim]') AND name = 'PaymentVoucherNumber')
BEGIN
    ALTER TABLE [dbo].[GrantPaymentClaim] ADD [PaymentVoucherNumber] NVARCHAR(100) NULL;
END;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[GrantPaymentClaim]') AND name = 'CloVerifiedBy')
BEGIN
    ALTER TABLE [dbo].[GrantPaymentClaim] ADD [CloVerifiedBy] NVARCHAR(100) NULL;
END;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[GrantPaymentClaim]') AND name = 'CloVerifiedDate')
BEGIN
    ALTER TABLE [dbo].[GrantPaymentClaim] ADD [CloVerifiedDate] DATETIME2 NULL;
END;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[GrantPaymentClaim]') AND name = 'FinanceOfficerApprovedBy')
BEGIN
    ALTER TABLE [dbo].[GrantPaymentClaim] ADD [FinanceOfficerApprovedBy] NVARCHAR(100) NULL;
END;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[GrantPaymentClaim]') AND name = 'FinanceOfficerApprovedDate')
BEGIN
    ALTER TABLE [dbo].[GrantPaymentClaim] ADD [FinanceOfficerApprovedDate] DATETIME2 NULL;
END;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[GrantPaymentClaim]') AND name = 'RequiresCfoApproval')
BEGIN
    ALTER TABLE [dbo].[GrantPaymentClaim] ADD [RequiresCfoApproval] BIT NOT NULL CONSTRAINT DF_GrantPaymentClaim_RequiresCfo DEFAULT 0;
END;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[GrantPaymentClaim]') AND name = 'CfoApprovedBy')
BEGIN
    ALTER TABLE [dbo].[GrantPaymentClaim] ADD [CfoApprovedBy] NVARCHAR(100) NULL;
END;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[GrantPaymentClaim]') AND name = 'CfoApprovedDate')
BEGIN
    ALTER TABLE [dbo].[GrantPaymentClaim] ADD [CfoApprovedDate] DATETIME2 NULL;
END;

-- 5. Create ErpPaymentBatchHeader Table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'ErpPaymentBatchHeader')
BEGIN
    CREATE TABLE [dbo].[ErpPaymentBatchHeader] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_ErpPaymentBatchHeader PRIMARY KEY CLUSTERED,
        [BatchNumber] NVARCHAR(100) NOT NULL CONSTRAINT UQ_ErpPaymentBatchHeader_Number UNIQUE,
        [BatchTypeCode] NVARCHAR(50) NOT NULL CONSTRAINT DF_ErpPaymentBatchHeader_Type DEFAULT 'DG_TRANCHE',
        [TotalAmount] DECIMAL(18,2) NOT NULL CONSTRAINT DF_ErpPaymentBatchHeader_Total DEFAULT 0,
        [ItemCount] INT NOT NULL CONSTRAINT DF_ErpPaymentBatchHeader_Count DEFAULT 0,
        [BatchStatusCode] NVARCHAR(50) NOT NULL CONSTRAINT DF_ErpPaymentBatchHeader_Status DEFAULT 'Draft',
        [ExportFileName] NVARCHAR(250) NULL,
        [ExportedDate] DATETIME2 NULL,
        [ExportedByUserId] NVARCHAR(100) NULL,
        [ApprovalComments] NVARCHAR(MAX) NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_ErpPaymentBatchHeader_CreatedAt DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_ErpPaymentBatchHeader_CreatedBy DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE INDEX [IX_ErpPaymentBatchHeader_Status] ON [dbo].[ErpPaymentBatchHeader] ([BatchStatusCode]);
END;

-- 6. Create ErpPaymentBatchEntry Table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'ErpPaymentBatchEntry')
BEGIN
    CREATE TABLE [dbo].[ErpPaymentBatchEntry] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_ErpPaymentBatchEntry PRIMARY KEY CLUSTERED,
        [ErpPaymentBatchHeaderId] INT NOT NULL,
        [GrantPaymentClaimId] INT NULL,
        [MandatoryGrantDisbursementId] INT NULL,
        [OrganisationId] INT NOT NULL,
        [PaymentVoucherNumber] NVARCHAR(100) NOT NULL,
        [VendorNumber] NVARCHAR(50) NOT NULL,
        [BankAccountNumber] NVARCHAR(50) NOT NULL,
        [BankBranchCode] NVARCHAR(20) NOT NULL,
        [PaymentAmount] DECIMAL(18,2) NOT NULL DEFAULT 0,
        [PaymentDescription] NVARCHAR(250) NOT NULL,
        [EntryStatusCode] NVARCHAR(50) NOT NULL CONSTRAINT DF_ErpPaymentBatchEntry_Status DEFAULT 'Pending',
        [EftReferenceNumber] NVARCHAR(100) NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_ErpPaymentBatchEntry_CreatedAt DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT DF_ErpPaymentBatchEntry_CreatedBy DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT FK_ErpPaymentBatchEntry_Header FOREIGN KEY ([ErpPaymentBatchHeaderId]) REFERENCES [dbo].[ErpPaymentBatchHeader]([Id]) ON DELETE CASCADE,
        CONSTRAINT FK_ErpPaymentBatchEntry_Org FOREIGN KEY ([OrganisationId]) REFERENCES [dbo].[Organisation]([Id])
    );

    CREATE INDEX [IX_ErpPaymentBatchEntry_Header] ON [dbo].[ErpPaymentBatchEntry] ([ErpPaymentBatchHeaderId]);
    CREATE INDEX [IX_ErpPaymentBatchEntry_Claim] ON [dbo].[ErpPaymentBatchEntry] ([GrantPaymentClaimId]);
    CREATE INDEX [IX_ErpPaymentBatchEntry_Org] ON [dbo].[ErpPaymentBatchEntry] ([OrganisationId]);
    CREATE INDEX [IX_ErpPaymentBatchEntry_Voucher] ON [dbo].[ErpPaymentBatchEntry] ([PaymentVoucherNumber]);
END;
