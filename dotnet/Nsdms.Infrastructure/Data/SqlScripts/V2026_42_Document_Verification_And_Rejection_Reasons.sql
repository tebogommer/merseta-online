-- =========================================================================================
-- Phase 42: Universal Document Verification, Quality Marking, and Rejection Reasons Catalog
-- =========================================================================================

IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'lookup')
BEGIN
    EXEC('CREATE SCHEMA [lookup]');
END;

-- 1. Create or extend DocumentAttachment table
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
END
ELSE
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[DocumentAttachment]') AND name = 'VerificationStatusCode')
    BEGIN
        ALTER TABLE [dbo].[DocumentAttachment] ADD [VerificationStatusCode] NVARCHAR(50) NOT NULL CONSTRAINT [DF_DocumentAttachment_VerificationStatusCode] DEFAULT 'Pending';
    END;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[DocumentAttachment]') AND name = 'VerifiedBy')
    BEGIN
        ALTER TABLE [dbo].[DocumentAttachment] ADD [VerifiedBy] NVARCHAR(150) NULL;
    END;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[DocumentAttachment]') AND name = 'VerifiedAt')
    BEGIN
        ALTER TABLE [dbo].[DocumentAttachment] ADD [VerifiedAt] DATETIME2 NULL;
    END;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[DocumentAttachment]') AND name = 'DocumentCertificationDate')
    BEGIN
        ALTER TABLE [dbo].[DocumentAttachment] ADD [DocumentCertificationDate] DATETIME2 NULL;
    END;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[DocumentAttachment]') AND name = 'DocumentExpiryDate')
    BEGIN
        ALTER TABLE [dbo].[DocumentAttachment] ADD [DocumentExpiryDate] DATETIME2 NULL;
    END;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[DocumentAttachment]') AND name = 'VerificationNotes')
    BEGIN
        ALTER TABLE [dbo].[DocumentAttachment] ADD [VerificationNotes] NVARCHAR(2000) NULL;
    END;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[DocumentAttachment]') AND name = 'RejectionReason')
    BEGIN
        ALTER TABLE [dbo].[DocumentAttachment] ADD [RejectionReason] NVARCHAR(2000) NULL;
    END;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[DocumentAttachment]') AND name = 'RejectionReasonCodesJson')
    BEGIN
        ALTER TABLE [dbo].[DocumentAttachment] ADD [RejectionReasonCodesJson] NVARCHAR(MAX) NULL;
    END;

    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_DocumentAttachment_VerificationStatusCode' AND object_id = OBJECT_ID(N'[dbo].[DocumentAttachment]'))
    BEGIN
        CREATE NONCLUSTERED INDEX [IX_DocumentAttachment_VerificationStatusCode] ON [dbo].[DocumentAttachment] ([VerificationStatusCode]);
    END;
END;

-- 2. Create DocumentRejectionReasonType lookup table
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
END;

-- 3. Seed Standard merSETA Rejection Reasons
MERGE INTO [lookup].[DocumentRejectionReasonType] AS target
USING (VALUES
    -- Universal / General Rejection Reasons
    ('DOC_ILLEGIBLE', 'Document illegible or blurry', 'Scan resolution is too low, text is blurry, or key fields are unreadable.', 'ALL', 1, 1),
    ('DOC_CUTOFF', 'Incomplete scan / cut-off margins', 'Essential information, page margins, or official signatures are cropped out.', 'ALL', 2, 1),
    ('DOC_MISSING_PAGES', 'Missing pages', 'Document sequence is incomplete; one or more numbered pages are missing.', 'ALL', 3, 1),
    ('DOC_CORRUPT', 'File damaged or corrupted', 'Uploaded file cannot be opened or contains unreadable artifacts.', 'ALL', 4, 1),
    ('DOC_WRONG_TYPE', 'Incorrect document uploaded', 'Uploaded file does not match the requested evidence category.', 'ALL', 5, 1),

    -- Certified RSA ID / Passport Rejection Reasons
    ('ID_EXPIRED_CERT', 'Certification older than 3 months', 'Statutory requirement mandates certification by Commissioner of Oaths within 90 days.', 'ID_DOCUMENT', 10, 1),
    ('ID_NO_STAMP', 'Missing Commissioner of Oaths stamp', 'Copy is uncertified or missing official commissioner stamp.', 'ID_DOCUMENT', 11, 1),
    ('ID_NO_SIGNATURE', 'Missing Commissioner of Oaths signature', 'Commissioner stamp is affixed but signature or date is missing.', 'ID_DOCUMENT', 12, 1),
    ('ID_MISMATCH_NAME', 'Name mismatch with identity registry', 'Name on ID copy does not match registered applicant demographics.', 'ID_DOCUMENT', 13, 1),
    ('ID_MISSING_BACK', 'Smart ID card back side missing', 'Only the front of the smart identity card was provided.', 'ID_DOCUMENT', 14, 1),

    -- Qualification & Assessment Certificates
    ('QUAL_NOT_CERTIFIED', 'Certificate copy not certified', 'Qualification or statement of results must carry a valid certified stamp.', 'QUALIFICATION_CERT', 20, 1),
    ('QUAL_NO_TRANSCRIPT', 'Missing academic transcript / Statement of Results', 'Qualification certificate provided without complete module breakdown.', 'QUALIFICATION_CERT', 21, 1),
    ('QUAL_UNACCREDITED', 'Issuing body not accredited by SAQA/QCTO', 'Training institution lacks accredited scope for the specified qualification.', 'QUALIFICATION_CERT', 22, 1),
    ('QUAL_INVALID_SCOPE', 'Out of required qualification scope', 'Certificate does not cover the designated trade or learnership curriculum.', 'QUALIFICATION_CERT', 23, 1),

    -- Bank Account Confirmation Letters
    ('BANK_EXPIRED', 'Bank confirmation letter older than 3 months', 'Bank confirmation letter must be dated within 90 days of submission.', 'BANK_CONFIRMATION', 30, 1),
    ('BANK_NO_STAMP', 'Missing official bank stamp', 'Letter lacks verifiable bank stamp, branch code verification, or electronic bank seal.', 'BANK_CONFIRMATION', 31, 1),
    ('BANK_NAME_MISMATCH', 'Account name does not match organisation legal name', 'Bank account holder must strictly match the registered legal entity.', 'BANK_CONFIRMATION', 32, 1),
    ('BANK_NOT_CURRENT', 'Account status invalid for disbursements', 'Account is reported closed, dormant, or restricted from EFT disbursements.', 'BANK_CONFIRMATION', 33, 1),

    -- Workplace Site Inspection Photos
    ('PHOTO_UNVERIFIABLE', 'Site location unidentifiable', 'Photograph lacks recognizable context, plant signage, or machine serial tag.', 'SITE_PHOTO', 40, 1),
    ('PHOTO_SAFETY_VIOLATION', 'Visible occupational health & safety violation', 'Photo reveals clear OHS contraventions, lack of PPE, or machine hazard.', 'SITE_PHOTO', 41, 1),
    ('PHOTO_TOOL_DEFICIENCY', 'Tooling/machinery does not match trade inventory', 'Workshop tooling shown is inadequate for the designated trade curriculum.', 'SITE_PHOTO', 42, 1),

    -- Signed Grant Agreement MOA
    ('MOA_MISSING_INITIALS', 'Initials missing on agreement pages', 'Every page of the Memorandum of Agreement must be initialed by signatories.', 'SIGNED_MOA', 50, 1),
    ('MOA_WITNESS_MISSING', 'Witness signatures missing', 'Legal contracting requires two competent adult witness signatures.', 'SIGNED_MOA', 51, 1),
    ('MOA_UNAUTHORIZED_SIGN', 'Signatory lacks legal delegation of authority', 'Signatory is not registered as an authorized corporate signatory or SDF.', 'SIGNED_MOA', 52, 1),
    ('MOA_WRONG_YEAR', 'Incorrect allocation or financial scheme year', 'MoA terms or budget allocations do not match the approved funding award.', 'SIGNED_MOA', 53, 1)
) AS source ([Code], [Name], [Description], [DocumentCategoryCode], [DisplayOrder], [Active])
ON target.[Code] = source.[Code]
WHEN MATCHED THEN
    UPDATE SET 
        target.[Name] = source.[Name],
        target.[Description] = source.[Description],
        target.[DocumentCategoryCode] = source.[DocumentCategoryCode],
        target.[DisplayOrder] = source.[DisplayOrder],
        target.[Active] = source.[Active],
        target.[ModifiedAt] = SYSUTCDATETIME(),
        target.[ModifiedBy] = 'Phase42Migrator'
WHEN NOT MATCHED THEN
    INSERT ([Code], [Name], [Description], [DocumentCategoryCode], [DisplayOrder], [Active], [CreatedAt], [CreatedBy])
    VALUES (source.[Code], source.[Name], source.[Description], source.[DocumentCategoryCode], source.[DisplayOrder], source.[Active], SYSUTCDATETIME(), 'Phase42Migrator');
