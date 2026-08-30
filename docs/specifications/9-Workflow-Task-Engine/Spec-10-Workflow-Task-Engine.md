# Spec 10: Scalable Workflow Engine & Task Matrix

## 1. Domain Overview
To support 50+ different business processes natively in Code On Time (e.g., Learner Recommendation, Leave Request, Purchase Order), a robust, scalable multi-process workflow engine is required. The legacy rigid task architecture has been replaced by a strictly separated state-machine design.

### Core Architecture Concepts
- **WorkflowState**: Refers to the physical step in the routing matrix (e.g., *Draft, Open, In Progress, On Hold, Completed*).
- **EntityStatus**: Refers to the final business outcome defined on the actual entity table (e.g., *Draft, On Application, Rejected, Registered, Approved*).
- **Group-Based Validation**: Users belong to exactly one `Group` (e.g., Region Admin). Permissions dictate which group can trigger which `Transition`.

## 2. SQL Server Schema Strategy (Strictly Singular)

The following tables form the backbone of the Workflow Engine. All must map flawlessly onto default ASP.NET Membership (`aspnet_Users`).

```sql
-- 1. Defines the overarching Business Process
CREATE TABLE [dbo].[WorkflowDefinition] (
    [WorkflowDefinitionId] INT IDENTITY(1,1) PRIMARY KEY,
    [Code] VARCHAR(50) NOT NULL UNIQUE,
    [Name] VARCHAR(100) NOT NULL,
    [ControllerName] VARCHAR(100) NOT NULL, -- COT Controller Name (e.g., LearnerRegistration)
    [KeyFieldName] VARCHAR(100) NOT NULL, -- PK Field (e.g., LearnerId)
    [Active] BIT DEFAULT 1
);

-- 2. Defines the internal steps
CREATE TABLE [dbo].[WorkflowState] (
    [WorkflowStateId] INT IDENTITY(1,1) PRIMARY KEY,
    [WorkflowDefinitionId] INT FOREIGN KEY REFERENCES [WorkflowDefinition](WorkflowDefinitionId),
    [StateName] VARCHAR(100) NOT NULL, -- 'Pending Committee'
    [IsTerminal] BIT DEFAULT 0 -- 1 = Closed/Resolved
);

-- 3. Groups & Permissions (replaces static roles)
CREATE TABLE [dbo].[Group] (
    [GroupId] INT IDENTITY(1,1) PRIMARY KEY,
    [GroupName] VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE [dbo].[UserGroup] (
    [UserGroupId] INT IDENTITY(1,1) PRIMARY KEY,
    [UserId] UNIQUEIDENTIFIER FOREIGN KEY REFERENCES [aspnet_Users](UserId),
    [GroupId] INT FOREIGN KEY REFERENCES [Group](GroupId),
    CONSTRAINT UQ_UserGroup UNIQUE (UserId) -- Strictly ONE group per user
);

CREATE TABLE [dbo].[Permission] (
    [PermissionId] INT IDENTITY(1,1) PRIMARY KEY,
    [Code] VARCHAR(50) NOT NULL UNIQUE -- 'APPROVE_LEARNER'
);

CREATE TABLE [dbo].[GroupPermission] (
    [GroupPermissionId] INT IDENTITY(1,1) PRIMARY KEY,
    [GroupId] INT FOREIGN KEY REFERENCES [Group](GroupId),
    [PermissionId] INT FOREIGN KEY REFERENCES [Permission](PermissionId)
);

-- 4. Governs allowed jumps between States based on Permission
CREATE TABLE [dbo].[Transition] (
    [TransitionId] INT IDENTITY(1,1) PRIMARY KEY,
    [WorkflowDefinitionId] INT FOREIGN KEY REFERENCES [WorkflowDefinition](WorkflowDefinitionId),
    [FromStateId] INT FOREIGN KEY REFERENCES [WorkflowState](WorkflowStateId),
    [ToStateId] INT FOREIGN KEY REFERENCES [WorkflowState](WorkflowStateId),
    [RequiredPermissionId] INT FOREIGN KEY REFERENCES [Permission](PermissionId) NULL,
    [ActionName] VARCHAR(100) NOT NULL -- The COT UI Button Name (e.g., 'Approve')
);

-- 5. The Active Process Record
CREATE TABLE [dbo].[WorkflowInstance] (
    [WorkflowInstanceId] INT IDENTITY(1,1) PRIMARY KEY,
    [WorkflowDefinitionId] INT FOREIGN KEY REFERENCES [WorkflowDefinition](WorkflowDefinitionId),
    [EntityId] VARCHAR(100) NOT NULL, -- Polymorphic Link
    [CurrentWorkflowStateId] INT FOREIGN KEY REFERENCES [WorkflowState](WorkflowStateId),
    [CreateDate] DATETIME DEFAULT GETDATE(),
    [CreateUserId] UNIQUEIDENTIFIER FOREIGN KEY REFERENCES [aspnet_Users](UserId)
);

-- 6. Task Assignment for the Universal Inbox
CREATE TABLE [dbo].[WorkflowTask] (
    [WorkflowTaskId] INT IDENTITY(1,1) PRIMARY KEY,
    [WorkflowInstanceId] INT FOREIGN KEY REFERENCES [WorkflowInstance](WorkflowInstanceId),
    [AssignedGroupId] INT FOREIGN KEY REFERENCES [Group](GroupId) NULL,
    [AssignedUserId] UNIQUEIDENTIFIER FOREIGN KEY REFERENCES [aspnet_Users](UserId) NULL,
    [TaskStatus] VARCHAR(50) DEFAULT 'Open' -- Open, Claimed, Completed
);

-- 7. Audit Logging
CREATE TABLE [dbo].[WorkflowHistory] (
    [WorkflowHistoryId] INT IDENTITY(1,1) PRIMARY KEY,
    [WorkflowInstanceId] INT FOREIGN KEY REFERENCES [WorkflowInstance](WorkflowInstanceId),
    [FromStateId] INT FOREIGN KEY REFERENCES [WorkflowState](WorkflowStateId) NULL,
    [ToStateId] INT FOREIGN KEY REFERENCES [WorkflowState](WorkflowStateId),
    [UserId] UNIQUEIDENTIFIER FOREIGN KEY REFERENCES [aspnet_Users](UserId),
    [ActionDate] DATETIME DEFAULT GETDATE()
);

-- 8. Communication
CREATE TABLE [dbo].[Notification] (
    [NotificationId] INT IDENTITY(1,1) PRIMARY KEY,
    [UserId] UNIQUEIDENTIFIER FOREIGN KEY REFERENCES [aspnet_Users](UserId),
    [WorkflowInstanceId] INT FOREIGN KEY REFERENCES [WorkflowInstance](WorkflowInstanceId) NULL, -- Required for Deep Linking
    [MessageHtml] NVARCHAR(MAX) NOT NULL,
    [IsRead] BIT DEFAULT 0,
    [CreateDate] DATETIME DEFAULT GETDATE()
);
```

## 3. The `usp_AdvanceWorkflow` Stored Procedure

To decouple standard DB triggers from the UI state, ALL workflow steps must execute via one centralized SQL procedure called natively from a Code On Time Custom Action SQL rule.

```sql
CREATE PROCEDURE [dbo].[usp_AdvanceWorkflow] 
    @WorkflowInstanceId INT,
    @ActionName VARCHAR(100),
    @UserId UNIQUEIDENTIFIER,
    @NewEntityStatusId INT = NULL -- Optional direct write to the entity table
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @CurrentStateId INT, @DefId INT, @ToStateId INT, @ReqPermId INT;
    DECLARE @UserGroupId INT;
    
    -- Get current state
    SELECT @CurrentStateId = CurrentWorkflowStateId, @DefId = WorkflowDefinitionId 
    FROM WorkflowInstance WHERE WorkflowInstanceId = @WorkflowInstanceId;
    
    -- Verify Transition exists
    SELECT @ToStateId = ToStateId, @ReqPermId = RequiredPermissionId 
    FROM Transition 
    WHERE FromStateId = @CurrentStateId AND ActionName = @ActionName AND WorkflowDefinitionId = @DefId;
    
    IF @ToStateId IS NULL THROW 50001, 'Invalid Transition requested or state machine deadlock.', 1;

    -- Verify Permissions via Group
    IF @ReqPermId IS NOT NULL
    BEGIN
        SELECT @UserGroupId = GroupId FROM UserGroup WHERE UserId = @UserId;
        IF NOT EXISTS (SELECT 1 FROM GroupPermission WHERE GroupId = @UserGroupId AND PermissionId = @ReqPermId)
        THROW 50002, 'User lacks explicit Permission to execute this Transition.', 1;
    END

    -- Commit Transition
    BEGIN TRAN
        UPDATE WorkflowInstance SET CurrentWorkflowStateId = @ToStateId WHERE WorkflowInstanceId = @WorkflowInstanceId;
        
        -- Insert History
        INSERT INTO WorkflowHistory (WorkflowInstanceId, FromStateId, ToStateId, UserId) 
        VALUES (@WorkflowInstanceId, @CurrentStateId, @ToStateId, @UserId);
        
        -- Close pending Tasks
        UPDATE WorkflowTask SET TaskStatus = 'Completed' WHERE WorkflowInstanceId = @WorkflowInstanceId AND TaskStatus = 'Open';
        
        -- NATIVE: Generate Link & HTML Notification for next group (Pseudo-logic)
        -- INSERT INTO Notification ... using AppSetting BaseAppUrl

        -- OPTION B (DUAL STATUS PUSH): Dynamically push the workflow state back onto the target entity for COT performance
        DECLARE @TargetTable VARCHAR(100), @TargetKeyField VARCHAR(100), @EntityIdStr VARCHAR(100);
        SELECT @TargetTable = ControllerName, @TargetKeyField = KeyFieldName FROM WorkflowDefinition WHERE WorkflowDefinitionId = @DefId;
        SELECT @EntityIdStr = EntityId FROM WorkflowInstance WHERE WorkflowInstanceId = @WorkflowInstanceId;

        DECLARE @DynamicSQL NVARCHAR(MAX);
        SET @DynamicSQL = 'UPDATE ' + QUOTENAME(@TargetTable) + ' SET CurrentWorkflowStateId = ' + CAST(@ToStateId AS VARCHAR(10));
        IF @NewEntityStatusId IS NOT NULL
        BEGIN
            SET @DynamicSQL = @DynamicSQL + ', EntityStatusId = ' + CAST(@NewEntityStatusId AS VARCHAR(10));
        END
        SET @DynamicSQL = @DynamicSQL + ' WHERE ' + QUOTENAME(@TargetKeyField) + ' = ''' + @EntityIdStr + '''';
        
        EXEC sp_executesql @DynamicSQL;
    COMMIT TRAN
END
```

## 4. Code On Time Intercept (Calling the Workflow)

When an Action (e.g., `Approve`) is triggered within a Controller (e.g., `LearnerRegistration.xml`), use the following Code On Time SQL Business Rule pattern:

```sql
-- Pattern: Code On Time SQL Business Rule
-- Target Controller: LearnerRegistration 
-- Action: Custom, Approve

DECLARE @InstanceId INT;
SELECT @InstanceId = WorkflowInstanceId FROM WorkflowInstance WHERE EntityId = CAST(@LearnerId AS VARCHAR(100)) AND WorkflowDefinitionId = 1;

BEGIN TRY
    EXEC [dbo].[usp_AdvanceWorkflow] 
        @WorkflowInstanceId = @InstanceId, 
        @ActionName = 'Approve', 
        @UserId = @BusinessRules_UserId,
        @NewEntityStatusId = 4; -- Pushing "Registered" to EntityStatus

    SET @Result_ShowMessage = 'Learner Recommendation successfully approved and forwarded.';
END TRY
BEGIN CATCH
    SET @BusinessRules_PreventDefault = 1;
    SET @Result_ShowMessage = ERROR_MESSAGE();
END CATCH
```

### 4.1 NATIVE: C# Deep Link Navigation (Zero Data Duplication)

Because we explicitly store `WorkflowInstanceId` within `WorkflowTask` and `Notification`, we do not need to duplicate the TargetController string. Code On Time can dynamically read the definition and instantly generate the deep link when the user clicks a specific task or notification.

```csharp
// Pattern: Code On Time C# Action Handler (Zero Data Duplication Navigation)
[ControllerAction("WorkflowTask", "Custom", "OpenTask")]
public void HandleOpenTask(int workflowTaskId)
{
    // 1. Instantly lookup the exact routing via the normalized Instance relation
    string targetController = "";
    string targetKey = "";
    
    using (SqlText lookup = new SqlText(@"
        SELECT wd.ControllerName, wi.EntityId 
        FROM WorkflowTask wt
        JOIN WorkflowInstance wi ON wt.WorkflowInstanceId = wi.WorkflowInstanceId
        JOIN WorkflowDefinition wd ON wi.WorkflowDefinitionId = wd.WorkflowDefinitionId
        WHERE wt.WorkflowTaskId = @TaskId"))
    {
        lookup.AddParameter("@TaskId", workflowTaskId);
        if (lookup.Read())
        {
            targetController = Convert.ToString(lookup[0]);
            targetKey = Convert.ToString(lookup[1]);
        }
    }

    // 2. Generate and execute the dynamic COT Deep Link Redirect
    if (!string.IsNullOrEmpty(targetController)) {
        string targetUrl = String.Format("~/Pages/{0}.aspx?_controller={0}&_commandName=Select&_commandArgument={1}", 
                           targetController, targetKey);
        Result.NavigateUrl = targetUrl;
    }
}

// Applies identically to Notifications allowing generic HTML messages to ALSO function as quick-links.
[ControllerAction("Notification", "Custom", "OpenNotification")]
public void HandleOpenNotification(int notificationId)
{
    // .. identical logic utilizing NotificationId joining onto WorkflowInstanceId ..
}
```

## 5. Comprehensive NSDMS Seed Data (Code On Time Configuration)

To fully operationalize this engine against the legacy NSDMS architecture, the following definitions, permissions, and groups must be seeded. This replaces the hardcoded boolean flags previously scattered across the legacy `Users` and `Roles` tables.

```sql
-- ==========================================================
-- 1. Setup Definitions (The 50+ Business Processes)
-- ==========================================================
-- The 50+ Comprehensive Business Processes of a Modern SETA
INSERT INTO WorkflowDefinition (Code, Name, ControllerName, KeyFieldName) VALUES 
-- Core ETQA & Provider Submissions
('PROVIDER', 'Training Provider Accreditation', 'TrainingProviders', 'ProviderId'),
('PROV_EXT', 'Training Provider Extension of Scope', 'ProviderExtension', 'ExtensionId'),
('PROV_MON', 'Provider Monitoring & Audit', 'ProviderAudit', 'AuditId'),
('ASSESSOR', 'Assessor Registration', 'Assessor', 'AssessorId'),
('MODERATOR', 'Moderator Registration', 'Moderator', 'ModeratorId'),

-- Finance & Grants 
('WSP', 'Workplace Skills Plan Submission', 'Wsp', 'WspId'),
('WSP_DISP', 'WSP Non-Signoff Dispute', 'WspDispute', 'DisputeId'),
('DG', 'Discretionary Grant Application', 'DgAllocationParent', 'Id'),
('MOA', 'Memorandum of Agreement Signoff', 'Active Contracts', 'ContractId'),
('PIP', 'Project Implementation Plan', 'ProjectImplementationPlan', 'PipId'),
('TRANCHE', 'Tranche Payment Requisition', 'PaymentRequisition', 'Id'),
('VENDOR', 'Financial Vendor Registration', 'Vendor', 'VendorId'),

-- Learner Management Pipeline
('LRN', 'Learner Registration', 'Learner', 'LearnerId'),
('LRN_TERM', 'Learner Termination', 'LearnerTermination', 'TerminationId'),
('LRN_TRANS', 'Learner Employer Transfer', 'LearnerTransfer', 'TransferId'),
('LRN_COMP', 'Learner Statement of Results / Completion', 'LearnerCompletion', 'CompletionId'),
('WPAPP', 'Workplace Approval (Mentors)', 'WorkplaceApproval', 'WorkplaceApprovalId'),
('WPAPP_SIT', 'Workplace Site Visit Report', 'SiteVisit', 'VisitId'),
('TRADETEST', 'Trade Test Application', 'TradeTest', 'TradeTestId'),
('TRADERES', 'Trade Test Results', 'TradeTestResult', 'ResultId'),
('ARTISAN', 'Artisan Certificate Generation', 'ArtisanCertificate', 'CertificateId'),
('QCTO', 'QCTO Recommendation Extract', 'QctoExtract', 'ExtractId'),
('BURSARY', 'Bursary Application Submission', 'Bursary', 'BursaryId'),
('INTERN', 'Internship / TVET Placement', 'Placement', 'PlacementId'),

-- Governance & CRM
('COMPANY', 'Company Profile Update', 'Company', 'CompanyId'),
('SDF_REG', 'SDF Registration / Link', 'SdfCompany', 'SdfId'),
('DOC_VERIF', 'Document Verification & Vaulting', 'Document', 'DocumentId'),
('COMPLAINT', 'Stakeholder CRM Complaint', 'Complaint', 'ComplaintId');
-- (Truncated for brevity in script, but strictly scales to the 50+ array requirements)


-- ==========================================================
-- 2. Setup Permissions (The Explicit Actions extracted from NSDMS legacy mechanics)
-- ==========================================================
INSERT INTO Permission (Code) VALUES 
-- Core Approvals
('APPROVE_WSP'), ('RECOMMEND_WSP'), ('REJECT_WSP'),
('APPROVE_LEARNER'), ('REJECT_LEARNER'),
('APPROVE_COMPANY'), ('VERIFY_SARS_LEVY'),
('APPROVE_DG_BUDGET'), ('RECOMMEND_DG_BUDGET'), ('SIGN_MOA_CONTRACT'),
('ACCREDIT_PROVIDER'), ('REVOKE_PROVIDER_ACCREDITATION'),
('APPROVE_ASSESSOR'), ('REVOKE_ASSESSOR'),
('APPROVE_WORKPLACE'), ('SCHEDULE_SITE_VISIT'),
('APPROVE_TRADE_TEST'), ('GENERATE_ARTISAN_CERTIFICATE');

-- ==========================================================
-- 3. Setup Groups (The Operational Matrices replacing ASP.NET Roles)
-- ==========================================================
INSERT INTO [Group] (GroupName) VALUES 
('Administrator'),
('Region Manager'),
('Region Coordinator'),
('Client Liaison Officer (CLO)'),
('Skills Development Facilitator (SDF)'),
('Primary SDF'),
('Quality Assurance Manager'),
('Review Committee Member'),
('Chief Executive Officer');

-- ==========================================================
-- 4. Setup GroupPermissions (The Maker-Checker Matrix)
-- ==========================================================
-- Example: CLO can recommend/site-visit, but Region Manager approves
INSERT INTO GroupPermission (GroupId, PermissionId) VALUES 
(4, 2), -- CLO -> RECOMMEND_WSP
(4, 16), -- CLO -> SCHEDULE_SITE_VISIT
(2, 1), -- Region Manager -> APPROVE_WSP
(2, 4), -- Region Manager -> APPROVE_LEARNER
(7, 11), -- QA Manager -> ACCREDIT_PROVIDER
(7, 13), -- QA Manager -> APPROVE_ASSESSOR
(8, 9), -- Review Committee -> RECOMMEND_DG_BUDGET
(9, 8); -- CEO -> APPROVE_DG_BUDGET

-- ==========================================================
-- 5. Setup States & Transitions (Example: Learner Pipeline)
-- ==========================================================
-- States for LRN
INSERT INTO WorkflowState (WorkflowDefinitionId, StateName, IsTerminal) VALUES 
(2, 'Draft', 0), 
(2, 'Under Review (CLO)', 0), 
(2, 'Pending Final Approval (Region Manager)', 0), 
(2, 'Registered', 1), 
(2, 'Rejected', 1);

-- Transitions matching the permissions above
-- 1. Submit (No explicit system permission needed by the user creating it, just generic access)
INSERT INTO Transition (WorkflowDefinitionId, FromStateId, ToStateId, RequiredPermissionId, ActionName)
VALUES (2, 1, 2, NULL, 'Submit to CLO');

-- 2. CLO Recommends (Moves to Manager)
INSERT INTO Transition (WorkflowDefinitionId, FromStateId, ToStateId, RequiredPermissionId, ActionName)
VALUES (2, 2, 3, NULL, 'Recommend Registration'); 

-- 3. Region Manager Approves (Moves to Terminal Registered) -> Requires APPROVE_LEARNER (Perm ID 4)
INSERT INTO Transition (WorkflowDefinitionId, FromStateId, ToStateId, RequiredPermissionId, ActionName)
VALUES (2, 3, 4, 4, 'Final Approve');
```
