SET NOCOUNT ON;

-- 1. Promote LevyFileLine.Id to BIGINT
IF EXISTS (
    SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_NAME = 'LevyFileLine' AND COLUMN_NAME = 'Id' AND DATA_TYPE = 'int'
)
BEGIN
    PRINT 'Promoting LevyFileLine.Id to BIGINT IDENTITY...';
    BEGIN TRANSACTION;
    SELECT * INTO #TempLevyFileLine FROM [dbo].[LevyFileLine];
    DROP TABLE [dbo].[LevyFileLine];

    CREATE TABLE [dbo].[LevyFileLine] (
        [Id] BIGINT IDENTITY(1,1) NOT NULL,
        [LevyFileId] INT NOT NULL,
        [SdlNumber] NVARCHAR(50) NOT NULL,
        [SchemeYear] NVARCHAR(20) NOT NULL,
        [MandatoryLevyAmount] DECIMAL(18,2) NOT NULL CONSTRAINT [DF_LevyFileLine_Mandatory] DEFAULT 0,
        [DiscretionaryLevyAmount] DECIMAL(18,2) NOT NULL CONSTRAINT [DF_LevyFileLine_Discretionary] DEFAULT 0,
        [AdminLevyAmount] DECIMAL(18,2) NOT NULL CONSTRAINT [DF_LevyFileLine_Admin] DEFAULT 0,
        [QctoLevyAmount] DECIMAL(18,2) NOT NULL CONSTRAINT [DF_LevyFileLine_Qcto] DEFAULT 0,
        [InterestAmount] DECIMAL(18,2) NOT NULL CONSTRAINT [DF_LevyFileLine_Interest] DEFAULT 0,
        [PenaltyAmount] DECIMAL(18,2) NOT NULL CONSTRAINT [DF_LevyFileLine_Penalty] DEFAULT 0,
        [TotalLevyAmount] DECIMAL(18,2) NOT NULL CONSTRAINT [DF_LevyFileLine_Total] DEFAULT 0,
        [SicCode] NVARCHAR(20) NULL,
        [ChamberCode] NVARCHAR(50) NULL,
        [ReconciliationStatusCode] NVARCHAR(50) NULL CONSTRAINT [DF_LevyFileLine_Status] DEFAULT 'Reconciled',
        [ReconciliationNotes] NVARCHAR(MAX) NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_LevyFileLine_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [PK_LevyFileLine] PRIMARY KEY CLUSTERED ([Id] ASC)
    );

    CREATE INDEX [IX_LevyFileLine_LevyFileId] ON [dbo].[LevyFileLine] ([LevyFileId]);
    CREATE INDEX [IX_LevyFileLine_SdlNumber] ON [dbo].[LevyFileLine] ([SdlNumber]);

    IF EXISTS (SELECT 1 FROM #TempLevyFileLine)
    BEGIN
        SET IDENTITY_INSERT [dbo].[LevyFileLine] ON;
        INSERT INTO [dbo].[LevyFileLine] (
            [Id], [LevyFileId], [SdlNumber], [SchemeYear], [MandatoryLevyAmount], [DiscretionaryLevyAmount],
            [AdminLevyAmount], [QctoLevyAmount], [InterestAmount], [PenaltyAmount], [TotalLevyAmount],
            [SicCode], [ChamberCode], [ReconciliationStatusCode], [ReconciliationNotes],
            [CreatedAt], [CreatedBy], [ModifiedAt], [ModifiedBy]
        )
        SELECT
            CAST([Id] AS BIGINT), [LevyFileId], [SdlNumber], [SchemeYear], [MandatoryLevyAmount], [DiscretionaryLevyAmount],
            [AdminLevyAmount], [QctoLevyAmount], [InterestAmount], [PenaltyAmount], [TotalLevyAmount],
            [SicCode], [ChamberCode], [ReconciliationStatusCode], [ReconciliationNotes],
            [CreatedAt], [CreatedBy], [ModifiedAt], [ModifiedBy]
        FROM #TempLevyFileLine;
        SET IDENTITY_INSERT [dbo].[LevyFileLine] OFF;
    END

    DROP TABLE #TempLevyFileLine;
    COMMIT TRANSACTION;
    PRINT 'LevyFileLine.Id successfully promoted to BIGINT.';
END
GO

-- 2. Promote WspTrainingPlan.Id to BIGINT
IF EXISTS (
    SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_NAME = 'WspTrainingPlan' AND COLUMN_NAME = 'Id' AND DATA_TYPE = 'int'
)
BEGIN
    PRINT 'Promoting WspTrainingPlan.Id to BIGINT IDENTITY...';
    BEGIN TRANSACTION;
    SELECT * INTO #TempWspTrainingPlan FROM [dbo].[WspTrainingPlan];
    DROP TABLE [dbo].[WspTrainingPlan];

    CREATE TABLE [dbo].[WspTrainingPlan] (
        [Id] BIGINT IDENTITY(1,1) NOT NULL,
        [WspSubmissionId] INT NOT NULL,
        [ProgrammeTypeCode] NVARCHAR(50) NULL,
        [NqfLevel] INT NOT NULL CONSTRAINT [DF_WspTrainingPlan_NqfLevel] DEFAULT 1,
        [BeneficiaryCount] INT NOT NULL CONSTRAINT [DF_WspTrainingPlan_Beneficiaries] DEFAULT 0,
        [EstimatedCost] DECIMAL(18,2) NOT NULL CONSTRAINT [DF_WspTrainingPlan_Cost] DEFAULT 0,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_WspTrainingPlan_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [PK_WspTrainingPlan] PRIMARY KEY CLUSTERED ([Id] ASC)
    );

    CREATE INDEX [IX_WspTrainingPlan_WspSubmissionId] ON [dbo].[WspTrainingPlan] ([WspSubmissionId]);

    IF EXISTS (SELECT 1 FROM #TempWspTrainingPlan)
    BEGIN
        SET IDENTITY_INSERT [dbo].[WspTrainingPlan] ON;
        INSERT INTO [dbo].[WspTrainingPlan] (
            [Id], [WspSubmissionId], [ProgrammeTypeCode], [NqfLevel], [BeneficiaryCount],
            [EstimatedCost], [CreatedAt], [CreatedBy], [ModifiedAt], [ModifiedBy]
        )
        SELECT
            CAST([Id] AS BIGINT), [WspSubmissionId], [ProgrammeTypeCode], [NqfLevel], [BeneficiaryCount],
            [EstimatedCost], [CreatedAt], [CreatedBy], [ModifiedAt], [ModifiedBy]
        FROM #TempWspTrainingPlan;
        SET IDENTITY_INSERT [dbo].[WspTrainingPlan] OFF;
    END

    DROP TABLE #TempWspTrainingPlan;
    COMMIT TRANSACTION;
    PRINT 'WspTrainingPlan.Id successfully promoted to BIGINT.';
END
GO

-- 3. Promote AuditLog.Id to BIGINT
IF EXISTS (
    SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_NAME = 'AuditLog' AND COLUMN_NAME = 'Id' AND DATA_TYPE = 'int'
)
BEGIN
    PRINT 'Promoting AuditLog.Id to BIGINT IDENTITY...';
    BEGIN TRANSACTION;
    SELECT * INTO #TempAuditLog FROM [dbo].[AuditLog];
    DROP TABLE [dbo].[AuditLog];

    CREATE TABLE [dbo].[AuditLog] (
        [Id] BIGINT IDENTITY(1,1) NOT NULL,
        [EntityName] NVARCHAR(100) NOT NULL,
        [RecordId] BIGINT NOT NULL,
        [ActionName] NVARCHAR(100) NOT NULL,
        [Actor] NVARCHAR(100) NOT NULL,
        [MetadataJson] NVARCHAR(MAX) NULL,
        [Timestamp] DATETIME2 NOT NULL CONSTRAINT [DF_AuditLog_Timestamp] DEFAULT SYSUTCDATETIME(),
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_AuditLog_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [PK_AuditLog] PRIMARY KEY CLUSTERED ([Id] ASC)
    );

    CREATE INDEX [IX_AuditLog_EntityName_RecordId] ON [dbo].[AuditLog] ([EntityName], [RecordId]);
    CREATE INDEX [IX_AuditLog_Timestamp] ON [dbo].[AuditLog] ([Timestamp]);

    IF EXISTS (SELECT 1 FROM #TempAuditLog)
    BEGIN
        SET IDENTITY_INSERT [dbo].[AuditLog] ON;
        INSERT INTO [dbo].[AuditLog] (
            [Id], [EntityName], [RecordId], [ActionName], [Actor], [MetadataJson],
            [Timestamp], [CreatedAt], [CreatedBy], [ModifiedAt], [ModifiedBy]
        )
        SELECT
            CAST([Id] AS BIGINT), [EntityName], [RecordId], [ActionName], [Actor], [MetadataJson],
            [Timestamp], [CreatedAt], [CreatedBy], [ModifiedAt], [ModifiedBy]
        FROM #TempAuditLog;
        SET IDENTITY_INSERT [dbo].[AuditLog] OFF;
    END

    DROP TABLE #TempAuditLog;
    COMMIT TRANSACTION;
    PRINT 'AuditLog.Id successfully promoted to BIGINT.';
END
GO
