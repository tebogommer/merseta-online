using System;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Data;

public static class Phase6GovernanceSchemaMigrator
{
    public static async Task MigrateGovernanceSchemaAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        var ddl = @"
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkflowDelegation')
BEGIN
    CREATE TABLE [dbo].[WorkflowDelegation] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [DelegatorUserId] INT NOT NULL,
        [DelegatorUserName] NVARCHAR(150) NOT NULL DEFAULT '',
        [DelegateeUserId] INT NOT NULL,
        [DelegateeUserName] NVARCHAR(150) NOT NULL DEFAULT '',
        [StartDate] DATETIME2 NOT NULL,
        [EndDate] DATETIME2 NOT NULL,
        [AllowedModulesJson] NVARCHAR(MAX) NOT NULL DEFAULT '[""*""]',
        [Reason] NVARCHAR(500) NOT NULL DEFAULT '',
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WorkflowDelegation_DelegatorUserId] ON [dbo].[WorkflowDelegation]([DelegatorUserId]);
    CREATE INDEX [IX_WorkflowDelegation_DelegateeUserId] ON [dbo].[WorkflowDelegation]([DelegateeUserId]);
    CREATE INDEX [IX_WorkflowDelegation_IsActive] ON [dbo].[WorkflowDelegation]([IsActive]);
    CREATE INDEX [IX_WorkflowDelegation_Dates] ON [dbo].[WorkflowDelegation]([StartDate], [EndDate]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkflowTaskLease')
BEGIN
    CREATE TABLE [dbo].[WorkflowTaskLease] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [WorkflowTaskId] INT NOT NULL,
        [ClaimedByUserId] INT NOT NULL,
        [ClaimedByUserName] NVARCHAR(150) NOT NULL DEFAULT '',
        [LeaseStartTime] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [LeaseExpiryTime] DATETIME2 NOT NULL,
        [LastHeartbeatTime] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [IsReleased] BIT NOT NULL DEFAULT 0,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WorkflowTaskLease_WorkflowTaskId] ON [dbo].[WorkflowTaskLease]([WorkflowTaskId]);
    CREATE INDEX [IX_WorkflowTaskLease_ClaimedByUserId] ON [dbo].[WorkflowTaskLease]([ClaimedByUserId]);
    CREATE INDEX [IX_WorkflowTaskLease_LeaseExpiryTime] ON [dbo].[WorkflowTaskLease]([LeaseExpiryTime]);
    CREATE INDEX [IX_WorkflowTaskLease_IsReleased] ON [dbo].[WorkflowTaskLease]([IsReleased]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkflowSignoffAttestation')
BEGIN
    CREATE TABLE [dbo].[WorkflowSignoffAttestation] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [WorkflowTaskId] INT NULL,
        [EntityName] NVARCHAR(100) NOT NULL,
        [EntityId] INT NOT NULL,
        [SignerUserId] INT NOT NULL,
        [SignerName] NVARCHAR(150) NOT NULL DEFAULT '',
        [SignerRsaId] NVARCHAR(20) NOT NULL DEFAULT '',
        [SignoffRole] NVARCHAR(100) NOT NULL DEFAULT '',
        [SignoffAction] NVARCHAR(50) NOT NULL DEFAULT 'Approved',
        [DocumentSha256Checksum] NVARCHAR(128) NOT NULL,
        [AttestationNotes] NVARCHAR(1000) NOT NULL DEFAULT '',
        [SignedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WorkflowSignoffAttestation_WorkflowTaskId] ON [dbo].[WorkflowSignoffAttestation]([WorkflowTaskId]);
    CREATE INDEX [IX_WorkflowSignoffAttestation_Entity] ON [dbo].[WorkflowSignoffAttestation]([EntityName], [EntityId]);
    CREATE INDEX [IX_WorkflowSignoffAttestation_SignerUserId] ON [dbo].[WorkflowSignoffAttestation]([SignerUserId]);
    CREATE INDEX [IX_WorkflowSignoffAttestation_Checksum] ON [dbo].[WorkflowSignoffAttestation]([DocumentSha256Checksum]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'FinancialApprovalThreshold')
BEGIN
    CREATE TABLE [dbo].[FinancialApprovalThreshold] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [RoleName] NVARCHAR(100) NOT NULL,
        [ModuleCode] NVARCHAR(50) NOT NULL DEFAULT 'Grants',
        [ApprovalLevelName] NVARCHAR(100) NOT NULL DEFAULT 'Level 1',
        [MaxApprovalAmount] DECIMAL(18,2) NOT NULL DEFAULT 0,
        [RequiresBoardApproval] BIT NOT NULL DEFAULT 0,
        [Description] NVARCHAR(500) NOT NULL DEFAULT '',
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_FinancialApprovalThreshold_Role_Module] ON [dbo].[FinancialApprovalThreshold]([RoleName], [ModuleCode]);
    CREATE INDEX [IX_FinancialApprovalThreshold_IsActive] ON [dbo].[FinancialApprovalThreshold]([IsActive]);
END
";

        await context.Database.ExecuteSqlRawAsync(ddl);
        logger?.LogInformation("Phase 6 Advanced Workflow Governance DDL verified and executed successfully.");

        // Seed Sample Delegations & Financial Thresholds if empty
        if (!await context.FinancialApprovalThresholds.AnyAsync())
        {
            context.FinancialApprovalThresholds.AddRange(
                new FinancialApprovalThreshold
                {
                    RoleName = "CLO",
                    ModuleCode = "Grants",
                    ApprovalLevelName = "Level 1 - Coordinator / Field Officer",
                    MaxApprovalAmount = 250000m,
                    RequiresBoardApproval = false,
                    Description = "Initial field recommendation & tranche verification up to R250,000"
                },
                new FinancialApprovalThreshold
                {
                    RoleName = "FinanceManager",
                    ModuleCode = "Grants",
                    ApprovalLevelName = "Level 2 - Senior Finance Manager",
                    MaxApprovalAmount = 1000000m,
                    RequiresBoardApproval = false,
                    Description = "Discretionary grant MOA disbursements & mandatory levy rebates up to R1,000,000"
                },
                new FinancialApprovalThreshold
                {
                    RoleName = "Admin",
                    ModuleCode = "Grants",
                    ApprovalLevelName = "Level 3 - Executive / COO",
                    MaxApprovalAmount = 5000000m,
                    RequiresBoardApproval = false,
                    Description = "Strategic project allocation & high-value contracts up to R5,000,000"
                },
                new FinancialApprovalThreshold
                {
                    RoleName = "SuperAdmin",
                    ModuleCode = "Grants",
                    ApprovalLevelName = "Level 4 - Accounting Authority / Board",
                    MaxApprovalAmount = 50000000m,
                    RequiresBoardApproval = true,
                    Description = "Discretionary funding windows & enterprise partnerships exceeding R5,000,000 requiring Board ratification"
                }
            );

            await context.SaveChangesAsync();
            logger?.LogInformation("Default Financial Approval Thresholds seeded successfully.");
        }

        if (!await context.WorkflowDelegations.AnyAsync())
        {
            context.WorkflowDelegations.AddRange(
                new WorkflowDelegation
                {
                    DelegatorUserId = 2,
                    DelegatorUserName = "finance.manager@merseta.org.za",
                    DelegateeUserId = 3,
                    DelegateeUserName = "acting.finance@merseta.org.za",
                    StartDate = DateTime.UtcNow.AddDays(-2),
                    EndDate = DateTime.UtcNow.AddDays(14),
                    AllowedModulesJson = "[\"Grants\",\"Finance\"]",
                    Reason = "Acting appointment during annual leave period (Approved by COO)",
                    IsActive = true
                }
            );

            await context.SaveChangesAsync();
            logger?.LogInformation("Default sample Workflow Delegation seeded successfully.");
        }
    }
}
