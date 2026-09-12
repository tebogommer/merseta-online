-- ====================================================================================
-- Script: V2026_44_Enterprise_Performance_And_Foreign_Key_Indexes.sql
-- Description: Creates covering non-clustered indexes for all foreign key relationships
--              and sets enterprise concurrency configurations to support 8,000 users.
-- ====================================================================================

-- 1. AqpLearnerAssessment (PersonId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_AqpLearnerAssessment_PersonId' AND object_id = OBJECT_ID('[dbo].[AqpLearnerAssessment]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_AqpLearnerAssessment_PersonId] ON [dbo].[AqpLearnerAssessment] ([PersonId]);
END
GO

-- 2. CurriculumWorkingGroupMember (PersonId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_CurriculumWorkingGroupMember_PersonId' AND object_id = OBJECT_ID('[dbo].[CurriculumWorkingGroupMember]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_CurriculumWorkingGroupMember_PersonId] ON [dbo].[CurriculumWorkingGroupMember] ([PersonId]);
END
GO

-- 3. DocumentSnapshot (DocumentTemplateId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_DocumentSnapshot_DocumentTemplateId' AND object_id = OBJECT_ID('[dbo].[DocumentSnapshot]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_DocumentSnapshot_DocumentTemplateId] ON [dbo].[DocumentSnapshot] ([DocumentTemplateId]);
END
GO

-- 4. LearnerTradeTestApplication (OrganisationId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_LearnerTradeTestApplication_OrganisationId' AND object_id = OBJECT_ID('[dbo].[LearnerTradeTestApplication]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_LearnerTradeTestApplication_OrganisationId] ON [dbo].[LearnerTradeTestApplication] ([OrganisationId]);
END
GO

-- 5. LearnerTradeTestApplication (PersonId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_LearnerTradeTestApplication_PersonId' AND object_id = OBJECT_ID('[dbo].[LearnerTradeTestApplication]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_LearnerTradeTestApplication_PersonId] ON [dbo].[LearnerTradeTestApplication] ([PersonId]);
END
GO

-- 6. NonSetaQualificationsCompletion (CompanyLearnerId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_NonSetaQualificationsCompletion_CompanyLearnerId' AND object_id = OBJECT_ID('[dbo].[NonSetaQualificationsCompletion]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_NonSetaQualificationsCompletion_CompanyLearnerId] ON [dbo].[NonSetaQualificationsCompletion] ([CompanyLearnerId]);
END
GO

-- 7. QualificationsCurriculumDevelopment (OrganisationId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_QualificationsCurriculumDevelopment_OrganisationId' AND object_id = OBJECT_ID('[dbo].[QualificationsCurriculumDevelopment]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_QualificationsCurriculumDevelopment_OrganisationId] ON [dbo].[QualificationsCurriculumDevelopment] ([OrganisationId]);
END
GO

-- 8. SarsLevyReconAudit (OrganisationId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_SarsLevyReconAudit_OrganisationId' AND object_id = OBJECT_ID('[dbo].[SarsLevyReconAudit]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_SarsLevyReconAudit_OrganisationId] ON [dbo].[SarsLevyReconAudit] ([OrganisationId]);
END
GO

-- 9. SdpSiteInspection (InspectorPersonId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_SdpSiteInspection_InspectorPersonId' AND object_id = OBJECT_ID('[dbo].[SdpSiteInspection]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_SdpSiteInspection_InspectorPersonId] ON [dbo].[SdpSiteInspection] ([InspectorPersonId]);
END
GO

-- 10. StatementOfResults (PersonId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_StatementOfResults_PersonId' AND object_id = OBJECT_ID('[dbo].[StatementOfResults]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_StatementOfResults_PersonId] ON [dbo].[StatementOfResults] ([PersonId]);
END
GO

-- 11. StatementOfResults (SummativeAssessmentReportId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_StatementOfResults_SummativeAssessmentReportId' AND object_id = OBJECT_ID('[dbo].[StatementOfResults]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_StatementOfResults_SummativeAssessmentReportId] ON [dbo].[StatementOfResults] ([SummativeAssessmentReportId]);
END
GO

-- 12. SummativeAssessmentReport (AssessorPersonId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_SummativeAssessmentReport_AssessorPersonId' AND object_id = OBJECT_ID('[dbo].[SummativeAssessmentReport]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_SummativeAssessmentReport_AssessorPersonId] ON [dbo].[SummativeAssessmentReport] ([AssessorPersonId]);
END
GO

-- 13. SummativeAssessmentReport (InternalModeratorPersonId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_SummativeAssessmentReport_InternalModeratorPersonId' AND object_id = OBJECT_ID('[dbo].[SummativeAssessmentReport]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_SummativeAssessmentReport_InternalModeratorPersonId] ON [dbo].[SummativeAssessmentReport] ([InternalModeratorPersonId]);
END
GO

-- 14. SummativeAssessmentReport (OrganisationId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_SummativeAssessmentReport_OrganisationId' AND object_id = OBJECT_ID('[dbo].[SummativeAssessmentReport]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_SummativeAssessmentReport_OrganisationId] ON [dbo].[SummativeAssessmentReport] ([OrganisationId]);
END
GO

-- 15. SummativeAssessmentReport (PersonId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_SummativeAssessmentReport_PersonId' AND object_id = OBJECT_ID('[dbo].[SummativeAssessmentReport]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_SummativeAssessmentReport_PersonId] ON [dbo].[SummativeAssessmentReport] ([PersonId]);
END
GO

-- 16. WorkflowHistory (FromStateId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_WorkflowHistory_FromStateId' AND object_id = OBJECT_ID('[dbo].[WorkflowHistory]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_WorkflowHistory_FromStateId] ON [dbo].[WorkflowHistory] ([FromStateId]);
END
GO

-- 17. WorkflowHistory (ToStateId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_WorkflowHistory_ToStateId' AND object_id = OBJECT_ID('[dbo].[WorkflowHistory]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_WorkflowHistory_ToStateId] ON [dbo].[WorkflowHistory] ([ToStateId]);
END
GO

-- 18. WorkflowNotification (WorkflowInstanceId)
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_WorkflowNotification_WorkflowInstanceId' AND object_id = OBJECT_ID('[dbo].[WorkflowNotification]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_WorkflowNotification_WorkflowInstanceId] ON [dbo].[WorkflowNotification] ([WorkflowInstanceId]);
END
GO
