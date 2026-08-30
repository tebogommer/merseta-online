using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

public static class Phase3WorkflowSchemaMigrator
{
    public static async Task MigrateWorkflowSchemaAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetRequiredService<ILoggerFactory>().CreateLogger("Phase3WorkflowSchemaMigrator");

        try
        {
            var sql = @"
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkflowDefinition')
BEGIN
    CREATE TABLE [dbo].[WorkflowDefinition] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [Code] NVARCHAR(50) NOT NULL UNIQUE,
        [Name] NVARCHAR(150) NOT NULL,
        [TargetEntityName] NVARCHAR(100) NOT NULL,
        [KeyFieldName] NVARCHAR(50) NOT NULL DEFAULT 'Id',
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WorkflowDefinition_TargetEntityName] ON [dbo].[WorkflowDefinition]([TargetEntityName]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkflowState')
BEGIN
    CREATE TABLE [dbo].[WorkflowState] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [WorkflowDefinitionId] INT NOT NULL FOREIGN KEY REFERENCES [WorkflowDefinition]([Id]) ON DELETE CASCADE,
        [StateName] NVARCHAR(100) NOT NULL,
        [StateCode] NVARCHAR(50) NOT NULL,
        [StepOrder] INT NOT NULL DEFAULT 1,
        [IsInitial] BIT NOT NULL DEFAULT 0,
        [IsTerminal] BIT NOT NULL DEFAULT 0,
        [AllowedGroupRole] NVARCHAR(100) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WorkflowState_WorkflowDefinitionId] ON [dbo].[WorkflowState]([WorkflowDefinitionId]);
    CREATE INDEX [IX_WorkflowState_StateCode] ON [dbo].[WorkflowState]([StateCode]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkflowTransition')
BEGIN
    CREATE TABLE [dbo].[WorkflowTransition] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [WorkflowDefinitionId] INT NOT NULL FOREIGN KEY REFERENCES [WorkflowDefinition]([Id]) ON DELETE CASCADE,
        [FromStateId] INT NOT NULL FOREIGN KEY REFERENCES [WorkflowState]([Id]),
        [ToStateId] INT NOT NULL FOREIGN KEY REFERENCES [WorkflowState]([Id]),
        [ActionName] NVARCHAR(100) NOT NULL,
        [ButtonColor] NVARCHAR(50) NULL DEFAULT '#1e40af',
        [ButtonIcon] NVARCHAR(50) NULL,
        [RequiredPermission] NVARCHAR(100) NULL,
        [RequiresComments] BIT NOT NULL DEFAULT 0,
        [NewEntityStatusCode] NVARCHAR(50) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WorkflowTransition_WorkflowDefinitionId] ON [dbo].[WorkflowTransition]([WorkflowDefinitionId]);
    CREATE INDEX [IX_WorkflowTransition_FromStateId] ON [dbo].[WorkflowTransition]([FromStateId]);
    CREATE INDEX [IX_WorkflowTransition_ToStateId] ON [dbo].[WorkflowTransition]([ToStateId]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkflowInstance')
BEGIN
    CREATE TABLE [dbo].[WorkflowInstance] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [WorkflowDefinitionId] INT NOT NULL FOREIGN KEY REFERENCES [WorkflowDefinition]([Id]),
        [EntityId] INT NOT NULL,
        [EntityTitle] NVARCHAR(250) NULL,
        [EntityReferenceNumber] NVARCHAR(100) NULL,
        [CurrentWorkflowStateId] INT NOT NULL FOREIGN KEY REFERENCES [WorkflowState]([Id]),
        [InitiatorUserId] NVARCHAR(100) NULL,
        [InitiatorName] NVARCHAR(150) NULL,
        [InitiatedDate] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CompletedDate] DATETIME2 NULL,
        [IsCompleted] BIT NOT NULL DEFAULT 0,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WorkflowInstance_WorkflowDefinitionId] ON [dbo].[WorkflowInstance]([WorkflowDefinitionId]);
    CREATE INDEX [IX_WorkflowInstance_Entity] ON [dbo].[WorkflowInstance]([WorkflowDefinitionId], [EntityId]);
    CREATE INDEX [IX_WorkflowInstance_CurrentWorkflowStateId] ON [dbo].[WorkflowInstance]([CurrentWorkflowStateId]);
    CREATE INDEX [IX_WorkflowInstance_IsCompleted] ON [dbo].[WorkflowInstance]([IsCompleted]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkflowTask')
BEGIN
    CREATE TABLE [dbo].[WorkflowTask] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [WorkflowInstanceId] INT NOT NULL FOREIGN KEY REFERENCES [WorkflowInstance]([Id]) ON DELETE CASCADE,
        [TaskTitle] NVARCHAR(250) NOT NULL,
        [TaskDescription] NVARCHAR(500) NULL,
        [AssignedGroupRole] NVARCHAR(100) NULL,
        [AssignedUserId] NVARCHAR(100) NULL,
        [AssignedUserName] NVARCHAR(150) NULL,
        [TaskStatus] NVARCHAR(50) NOT NULL DEFAULT 'Open',
        [Priority] NVARCHAR(20) NOT NULL DEFAULT 'Normal',
        [DueDate] DATETIME2 NOT NULL,
        [ClaimedDate] DATETIME2 NULL,
        [CompletedDate] DATETIME2 NULL,
        [TargetRoute] NVARCHAR(250) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WorkflowTask_WorkflowInstanceId] ON [dbo].[WorkflowTask]([WorkflowInstanceId]);
    CREATE INDEX [IX_WorkflowTask_AssignedGroupRole] ON [dbo].[WorkflowTask]([AssignedGroupRole]);
    CREATE INDEX [IX_WorkflowTask_AssignedUserId] ON [dbo].[WorkflowTask]([AssignedUserId]);
    CREATE INDEX [IX_WorkflowTask_TaskStatus] ON [dbo].[WorkflowTask]([TaskStatus]);
    CREATE INDEX [IX_WorkflowTask_DueDate] ON [dbo].[WorkflowTask]([DueDate]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkflowHistory')
BEGIN
    CREATE TABLE [dbo].[WorkflowHistory] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [WorkflowInstanceId] INT NOT NULL FOREIGN KEY REFERENCES [WorkflowInstance]([Id]) ON DELETE CASCADE,
        [FromStateId] INT NULL FOREIGN KEY REFERENCES [WorkflowState]([Id]),
        [ToStateId] INT NOT NULL FOREIGN KEY REFERENCES [WorkflowState]([Id]),
        [ActionName] NVARCHAR(100) NOT NULL,
        [ActorUserId] NVARCHAR(100) NULL,
        [ActorName] NVARCHAR(150) NULL,
        [ActorRole] NVARCHAR(100) NULL,
        [ActionDate] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [Comments] NVARCHAR(1000) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WorkflowHistory_WorkflowInstanceId] ON [dbo].[WorkflowHistory]([WorkflowInstanceId]);
    CREATE INDEX [IX_WorkflowHistory_ActionDate] ON [dbo].[WorkflowHistory]([ActionDate]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkflowNotification')
BEGIN
    CREATE TABLE [dbo].[WorkflowNotification] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [WorkflowInstanceId] INT NULL FOREIGN KEY REFERENCES [WorkflowInstance]([Id]) ON DELETE CASCADE,
        [RecipientUserId] NVARCHAR(100) NOT NULL,
        [Title] NVARCHAR(200) NOT NULL,
        [MessageHtml] NVARCHAR(MAX) NOT NULL,
        [TargetRoute] NVARCHAR(250) NULL,
        [IsRead] BIT NOT NULL DEFAULT 0,
        [CreatedDate] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [ReadDate] DATETIME2 NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WorkflowNotification_RecipientUserId] ON [dbo].[WorkflowNotification]([RecipientUserId]);
    CREATE INDEX [IX_WorkflowNotification_IsRead] ON [dbo].[WorkflowNotification]([IsRead]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'DocumentMetadata')
BEGIN
    CREATE TABLE [dbo].[DocumentMetadata] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [TargetEntityName] NVARCHAR(100) NOT NULL,
        [TargetEntityId] INT NOT NULL,
        [DocumentTypeCode] NVARCHAR(50) NOT NULL,
        [DocumentTypeName] NVARCHAR(150) NULL,
        [FileName] NVARCHAR(255) NOT NULL,
        [StorageUri] NVARCHAR(500) NOT NULL,
        [FileSizeBytes] BIGINT NOT NULL DEFAULT 0,
        [ContentType] NVARCHAR(100) NULL DEFAULT 'application/pdf',
        [Sha256Hash] NVARCHAR(100) NULL,
        [UploadedByUserId] NVARCHAR(100) NULL,
        [UploadedByUserName] NVARCHAR(150) NULL,
        [UploadDate] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [IsVerified] BIT NOT NULL DEFAULT 0,
        [VerificationNotes] NVARCHAR(500) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_DocumentMetadata_Entity] ON [dbo].[DocumentMetadata]([TargetEntityName], [TargetEntityId]);
    CREATE INDEX [IX_DocumentMetadata_DocumentTypeCode] ON [dbo].[DocumentMetadata]([DocumentTypeCode]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'DocumentRequirementRule')
BEGIN
    CREATE TABLE [dbo].[DocumentRequirementRule] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [WorkflowProcessCode] NVARCHAR(50) NOT NULL,
        [DocumentTypeCode] NVARCHAR(50) NOT NULL,
        [DocumentTypeName] NVARCHAR(150) NOT NULL,
        [Description] NVARCHAR(300) NULL,
        [IsMandatory] BIT NOT NULL DEFAULT 1,
        [RequiredAtStateId] INT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_DocumentRequirementRule_WorkflowProcessCode] ON [dbo].[DocumentRequirementRule]([WorkflowProcessCode]);
END
";
            await context.Database.ExecuteSqlRawAsync(sql);
            logger.LogInformation("Phase 3 Workflow & Document DDL verified and executed successfully.");

            await WorkflowDefinitionSeeder.SeedWorkflowDefinitionsAsync(context);
            logger.LogInformation("Workflow state machine definitions & sample tasks seeded successfully.");
        }
        catch (Exception ex)
        {
            logger.LogError(ex, "Error during Phase 3 Workflow Schema Migration.");
            throw;
        }
    }
}
