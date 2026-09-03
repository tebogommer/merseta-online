-- =========================================================================================
-- NSDMS Migration: V2026_11_Add_Physical_Foreign_Key_Constraints.sql
-- Description: Applies physical FOREIGN KEY constraints across core enterprise tables
--              to guarantee referential data integrity during bulk batch ETL and ERP sync.
-- =========================================================================================

SET NOCOUNT ON;

PRINT 'Starting migration: V2026_11_Add_Physical_Foreign_Key_Constraints...';

-- Helper macro procedure logic
-- 1. WspSubmission -> Organisation
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'WspSubmission' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Organisation' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_WspSubmission_Organisation' AND parent_object_id = OBJECT_ID(N'dbo.WspSubmission'))
    BEGIN
        ALTER TABLE [dbo].[WspSubmission] WITH NOCHECK
        ADD CONSTRAINT [FK_WspSubmission_Organisation]
        FOREIGN KEY ([OrganisationId]) REFERENCES [dbo].[Organisation] ([Id]);

        ALTER TABLE [dbo].[WspSubmission] CHECK CONSTRAINT [FK_WspSubmission_Organisation];
        PRINT 'Added constraint [FK_WspSubmission_Organisation].';
    END
END

-- 2. WspTrainingPlan -> WspSubmission
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'WspTrainingPlan' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'WspSubmission' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_WspTrainingPlan_WspSubmission' AND parent_object_id = OBJECT_ID(N'dbo.WspTrainingPlan'))
    BEGIN
        ALTER TABLE [dbo].[WspTrainingPlan] WITH NOCHECK
        ADD CONSTRAINT [FK_WspTrainingPlan_WspSubmission]
        FOREIGN KEY ([WspSubmissionId]) REFERENCES [dbo].[WspSubmission] ([Id]);

        ALTER TABLE [dbo].[WspTrainingPlan] CHECK CONSTRAINT [FK_WspTrainingPlan_WspSubmission];
        PRINT 'Added constraint [FK_WspTrainingPlan_WspSubmission].';
    END
END

-- 3. WspEmploymentSummary -> WspSubmission
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'WspEmploymentSummary' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'WspSubmission' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_WspEmploymentSummary_WspSubmission' AND parent_object_id = OBJECT_ID(N'dbo.WspEmploymentSummary'))
    BEGIN
        ALTER TABLE [dbo].[WspEmploymentSummary] WITH NOCHECK
        ADD CONSTRAINT [FK_WspEmploymentSummary_WspSubmission]
        FOREIGN KEY ([WspSubmissionId]) REFERENCES [dbo].[WspSubmission] ([Id]);

        ALTER TABLE [dbo].[WspEmploymentSummary] CHECK CONSTRAINT [FK_WspEmploymentSummary_WspSubmission];
        PRINT 'Added constraint [FK_WspEmploymentSummary_WspSubmission].';
    END
END

-- 4. LevyFileLine -> LevyFile
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'LevyFileLine' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'LevyFile' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_LevyFileLine_LevyFile' AND parent_object_id = OBJECT_ID(N'dbo.LevyFileLine'))
    BEGIN
        ALTER TABLE [dbo].[LevyFileLine] WITH NOCHECK
        ADD CONSTRAINT [FK_LevyFileLine_LevyFile]
        FOREIGN KEY ([LevyFileId]) REFERENCES [dbo].[LevyFile] ([Id]);

        ALTER TABLE [dbo].[LevyFileLine] CHECK CONSTRAINT [FK_LevyFileLine_LevyFile];
        PRINT 'Added constraint [FK_LevyFileLine_LevyFile].';
    END
END

-- 5. GrantApplication -> Organisation
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'GrantApplication' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Organisation' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_GrantApplication_Organisation' AND parent_object_id = OBJECT_ID(N'dbo.GrantApplication'))
    BEGIN
        ALTER TABLE [dbo].[GrantApplication] WITH NOCHECK
        ADD CONSTRAINT [FK_GrantApplication_Organisation]
        FOREIGN KEY ([OrganisationId]) REFERENCES [dbo].[Organisation] ([Id]);

        ALTER TABLE [dbo].[GrantApplication] CHECK CONSTRAINT [FK_GrantApplication_Organisation];
        PRINT 'Added constraint [FK_GrantApplication_Organisation].';
    END
END

-- 6. GrantMoa -> GrantApplication
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'GrantMoa' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'GrantApplication' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_GrantMoa_GrantApplication' AND parent_object_id = OBJECT_ID(N'dbo.GrantMoa'))
    BEGIN
        ALTER TABLE [dbo].[GrantMoa] WITH NOCHECK
        ADD CONSTRAINT [FK_GrantMoa_GrantApplication]
        FOREIGN KEY ([GrantApplicationId]) REFERENCES [dbo].[GrantApplication] ([Id]);

        ALTER TABLE [dbo].[GrantMoa] CHECK CONSTRAINT [FK_GrantMoa_GrantApplication];
        PRINT 'Added constraint [FK_GrantMoa_GrantApplication].';
    END
END

-- 7. GrantMoaMilestone -> GrantMoa
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'GrantMoaMilestone' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'GrantMoa' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_GrantMoaMilestone_GrantMoa' AND parent_object_id = OBJECT_ID(N'dbo.GrantMoaMilestone'))
    BEGIN
        ALTER TABLE [dbo].[GrantMoaMilestone] WITH NOCHECK
        ADD CONSTRAINT [FK_GrantMoaMilestone_GrantMoa]
        FOREIGN KEY ([GrantMoaId]) REFERENCES [dbo].[GrantMoa] ([Id]);

        ALTER TABLE [dbo].[GrantMoaMilestone] CHECK CONSTRAINT [FK_GrantMoaMilestone_GrantMoa];
        PRINT 'Added constraint [FK_GrantMoaMilestone_GrantMoa].';
    END
END

-- 8. GrantTranchePayment -> GrantMoaMilestone
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'GrantTranchePayment' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'GrantMoaMilestone' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_GrantTranchePayment_GrantMoaMilestone' AND parent_object_id = OBJECT_ID(N'dbo.GrantTranchePayment'))
    BEGIN
        ALTER TABLE [dbo].[GrantTranchePayment] WITH NOCHECK
        ADD CONSTRAINT [FK_GrantTranchePayment_GrantMoaMilestone]
        FOREIGN KEY ([GrantMoaMilestoneId]) REFERENCES [dbo].[GrantMoaMilestone] ([Id]);

        ALTER TABLE [dbo].[GrantTranchePayment] CHECK CONSTRAINT [FK_GrantTranchePayment_GrantMoaMilestone];
        PRINT 'Added constraint [FK_GrantTranchePayment_GrantMoaMilestone].';
    END
END

-- 9. MandatoryGrantDisbursement -> Organisation
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'MandatoryGrantDisbursement' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Organisation' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_MandatoryGrantDisbursement_Organisation' AND parent_object_id = OBJECT_ID(N'dbo.MandatoryGrantDisbursement'))
    BEGIN
        ALTER TABLE [dbo].[MandatoryGrantDisbursement] WITH NOCHECK
        ADD CONSTRAINT [FK_MandatoryGrantDisbursement_Organisation]
        FOREIGN KEY ([OrganisationId]) REFERENCES [dbo].[Organisation] ([Id]);

        ALTER TABLE [dbo].[MandatoryGrantDisbursement] CHECK CONSTRAINT [FK_MandatoryGrantDisbursement_Organisation];
        PRINT 'Added constraint [FK_MandatoryGrantDisbursement_Organisation].';
    END
END

-- 10. MandatoryGrantDisbursement -> WspSubmission
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'MandatoryGrantDisbursement' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'WspSubmission' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_MandatoryGrantDisbursement_WspSubmission' AND parent_object_id = OBJECT_ID(N'dbo.MandatoryGrantDisbursement'))
    BEGIN
        ALTER TABLE [dbo].[MandatoryGrantDisbursement] WITH NOCHECK
        ADD CONSTRAINT [FK_MandatoryGrantDisbursement_WspSubmission]
        FOREIGN KEY ([WspSubmissionId]) REFERENCES [dbo].[WspSubmission] ([Id]);

        ALTER TABLE [dbo].[MandatoryGrantDisbursement] CHECK CONSTRAINT [FK_MandatoryGrantDisbursement_WspSubmission];
        PRINT 'Added constraint [FK_MandatoryGrantDisbursement_WspSubmission].';
    END
END

-- 11. CompanyLearner -> Organisation
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'CompanyLearner' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Organisation' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_CompanyLearner_Organisation' AND parent_object_id = OBJECT_ID(N'dbo.CompanyLearner'))
    BEGIN
        ALTER TABLE [dbo].[CompanyLearner] WITH NOCHECK
        ADD CONSTRAINT [FK_CompanyLearner_Organisation]
        FOREIGN KEY ([EmployerId]) REFERENCES [dbo].[Organisation] ([Id]);

        ALTER TABLE [dbo].[CompanyLearner] CHECK CONSTRAINT [FK_CompanyLearner_Organisation];
        PRINT 'Added constraint [FK_CompanyLearner_Organisation].';
    END
END

-- 12. CompanyLearner -> Person
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'CompanyLearner' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Person' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_CompanyLearner_Person' AND parent_object_id = OBJECT_ID(N'dbo.CompanyLearner'))
    BEGIN
        ALTER TABLE [dbo].[CompanyLearner] WITH NOCHECK
        ADD CONSTRAINT [FK_CompanyLearner_Person]
        FOREIGN KEY ([PersonId]) REFERENCES [dbo].[Person] ([Id]);

        ALTER TABLE [dbo].[CompanyLearner] CHECK CONSTRAINT [FK_CompanyLearner_Person];
        PRINT 'Added constraint [FK_CompanyLearner_Person].';
    END
END

-- 13. WorkplaceApproval -> Organisation
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'WorkplaceApproval' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Organisation' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_WorkplaceApproval_Organisation' AND parent_object_id = OBJECT_ID(N'dbo.WorkplaceApproval'))
    BEGIN
        ALTER TABLE [dbo].[WorkplaceApproval] WITH NOCHECK
        ADD CONSTRAINT [FK_WorkplaceApproval_Organisation]
        FOREIGN KEY ([OrganisationId]) REFERENCES [dbo].[Organisation] ([Id]);

        ALTER TABLE [dbo].[WorkplaceApproval] CHECK CONSTRAINT [FK_WorkplaceApproval_Organisation];
        PRINT 'Added constraint [FK_WorkplaceApproval_Organisation].';
    END
END

-- 14. WorkplaceApprovalMentor -> WorkplaceApproval
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'WorkplaceApprovalMentor' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'WorkplaceApproval' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_WorkplaceApprovalMentor_WorkplaceApproval' AND parent_object_id = OBJECT_ID(N'dbo.WorkplaceApprovalMentor'))
    BEGIN
        ALTER TABLE [dbo].[WorkplaceApprovalMentor] WITH NOCHECK
        ADD CONSTRAINT [FK_WorkplaceApprovalMentor_WorkplaceApproval]
        FOREIGN KEY ([WorkplaceApprovalId]) REFERENCES [dbo].[WorkplaceApproval] ([Id]);

        ALTER TABLE [dbo].[WorkplaceApprovalMentor] CHECK CONSTRAINT [FK_WorkplaceApprovalMentor_WorkplaceApproval];
        PRINT 'Added constraint [FK_WorkplaceApprovalMentor_WorkplaceApproval].';
    END
END

-- 15. OrganisationContact -> Organisation
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'OrganisationContact' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Organisation' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_OrganisationContact_Organisation' AND parent_object_id = OBJECT_ID(N'dbo.OrganisationContact'))
    BEGIN
        ALTER TABLE [dbo].[OrganisationContact] WITH NOCHECK
        ADD CONSTRAINT [FK_OrganisationContact_Organisation]
        FOREIGN KEY ([OrganisationId]) REFERENCES [dbo].[Organisation] ([Id]);

        ALTER TABLE [dbo].[OrganisationContact] CHECK CONSTRAINT [FK_OrganisationContact_Organisation];
        PRINT 'Added constraint [FK_OrganisationContact_Organisation].';
    END
END

-- 16. OrganisationSite -> Organisation
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'OrganisationSite' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Organisation' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_OrganisationSite_Organisation' AND parent_object_id = OBJECT_ID(N'dbo.OrganisationSite'))
    BEGIN
        ALTER TABLE [dbo].[OrganisationSite] WITH NOCHECK
        ADD CONSTRAINT [FK_OrganisationSite_Organisation]
        FOREIGN KEY ([OrganisationId]) REFERENCES [dbo].[Organisation] ([Id]);

        ALTER TABLE [dbo].[OrganisationSite] CHECK CONSTRAINT [FK_OrganisationSite_Organisation];
        PRINT 'Added constraint [FK_OrganisationSite_Organisation].';
    END
END

-- 17. Visit -> Organisation
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Visit' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Organisation' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_Visit_Organisation' AND parent_object_id = OBJECT_ID(N'dbo.Visit'))
    BEGIN
        ALTER TABLE [dbo].[Visit] WITH NOCHECK
        ADD CONSTRAINT [FK_Visit_Organisation]
        FOREIGN KEY ([OrganisationId]) REFERENCES [dbo].[Organisation] ([Id]);

        ALTER TABLE [dbo].[Visit] CHECK CONSTRAINT [FK_Visit_Organisation];
        PRINT 'Added constraint [FK_Visit_Organisation].';
    END
END

-- 18. Visit -> Person (ContactPersonId)
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Visit' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Person' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_Visit_ContactPerson' AND parent_object_id = OBJECT_ID(N'dbo.Visit'))
    BEGIN
        ALTER TABLE [dbo].[Visit] WITH NOCHECK
        ADD CONSTRAINT [FK_Visit_ContactPerson]
        FOREIGN KEY ([ContactPersonId]) REFERENCES [dbo].[Person] ([Id]);

        ALTER TABLE [dbo].[Visit] CHECK CONSTRAINT [FK_Visit_ContactPerson];
        PRINT 'Added constraint [FK_Visit_ContactPerson].';
    END
END

PRINT 'Completed migration: V2026_11_Add_Physical_Foreign_Key_Constraints.';
