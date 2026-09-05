-- =========================================================================================
-- V2026_23_WorkplaceApproval_Spec_Alignment.sql
-- MerSETA NSDMS 2.0 - Workplace Approval Statutory Use Case Alignment (NMok_19122022)
-- =========================================================================================

IF OBJECT_ID(N'[dbo].[WorkplaceApproval]', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'LearningProgramTypeCode')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [LearningProgramTypeCode] NVARCHAR(100) NULL DEFAULT 'Apprenticeship';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'RequiresWorkplaceApproval')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [RequiresWorkplaceApproval] BIT NOT NULL DEFAULT 1;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'IsSiteVisitRequired')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [IsSiteVisitRequired] BIT NULL DEFAULT 1;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'SiteVisitJustification')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [SiteVisitJustification] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'InspectionDueDate')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [InspectionDueDate] DATETIME2 NULL;

    -- Section 6.2: Workplace Verification Attributes (Role-Neutral)
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'VerificationRecommendationReason')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [VerificationRecommendationReason] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'VerificationRecommendationExplanation')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [VerificationRecommendationExplanation] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'VerificationRejectionReason')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [VerificationRejectionReason] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'VerificationRejectionExplanation')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [VerificationRejectionExplanation] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'VerifiedDate')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [VerifiedDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'VerifiedByPersonId')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [VerifiedByPersonId] INT NULL;

    -- Section 6.3: Workplace Evaluation & Decision Attributes (Role-Neutral)
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'ApprovalReason')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [ApprovalReason] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'ApprovalExplanation')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [ApprovalExplanation] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'RejectionReason')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [RejectionReason] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'RejectionExplanation')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [RejectionExplanation] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'DecisionDate')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [DecisionDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'DecisionByPersonId')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [DecisionByPersonId] INT NULL;

    -- Non-merSETA Support
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'IsNonMerSetaCompany')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [IsNonMerSetaCompany] BIT NOT NULL DEFAULT 0;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'HomeSetaName')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [HomeSetaName] NVARCHAR(150) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'HomeSetaAgreementRef')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [HomeSetaAgreementRef] NVARCHAR(100) NULL;

    -- Helpful indexes
    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = N'IX_WorkplaceApproval_VerifiedByPersonId')
        CREATE NONCLUSTERED INDEX [IX_WorkplaceApproval_VerifiedByPersonId] ON [dbo].[WorkplaceApproval] ([VerifiedByPersonId]) WHERE [VerifiedByPersonId] IS NOT NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = N'IX_WorkplaceApproval_DecisionByPersonId')
        CREATE NONCLUSTERED INDEX [IX_WorkplaceApproval_DecisionByPersonId] ON [dbo].[WorkplaceApproval] ([DecisionByPersonId]) WHERE [DecisionByPersonId] IS NOT NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = N'IX_WorkplaceApproval_InspectionDueDate')
        CREATE NONCLUSTERED INDEX [IX_WorkplaceApproval_InspectionDueDate] ON [dbo].[WorkplaceApproval] ([InspectionDueDate]) WHERE [InspectionDueDate] IS NOT NULL;
END
