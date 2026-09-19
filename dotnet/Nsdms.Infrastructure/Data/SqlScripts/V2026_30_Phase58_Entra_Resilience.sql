-- Phase 58: Microsoft Entra ID Resilient Authentication & Backup Password Schema
-- Provisions Entra ID federation properties and disaster recovery emergency backup password columns on dbo.AppUser

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'IsEntraUser')
BEGIN
    ALTER TABLE [dbo].[AppUser] ADD [IsEntraUser] BIT NOT NULL CONSTRAINT [DF_AppUser_IsEntraUser] DEFAULT (0);
    PRINT 'Added [IsEntraUser] to [dbo].[AppUser].';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'EntraObjectId')
BEGIN
    ALTER TABLE [dbo].[AppUser] ADD [EntraObjectId] NVARCHAR(100) NULL;
    PRINT 'Added [EntraObjectId] to [dbo].[AppUser].';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'EntraUserPrincipalName')
BEGIN
    ALTER TABLE [dbo].[AppUser] ADD [EntraUserPrincipalName] NVARCHAR(150) NULL;
    PRINT 'Added [EntraUserPrincipalName] to [dbo].[AppUser].';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'EntraAccountEnabled')
BEGIN
    ALTER TABLE [dbo].[AppUser] ADD [EntraAccountEnabled] BIT NULL CONSTRAINT [DF_AppUser_EntraAccountEnabled] DEFAULT (1);
    PRINT 'Added [EntraAccountEnabled] to [dbo].[AppUser].';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'LastEntraSyncUtc')
BEGIN
    ALTER TABLE [dbo].[AppUser] ADD [LastEntraSyncUtc] DATETIME2(7) NULL;
    PRINT 'Added [LastEntraSyncUtc] to [dbo].[AppUser].';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'BackupPasswordHash')
BEGIN
    ALTER TABLE [dbo].[AppUser] ADD [BackupPasswordHash] NVARCHAR(MAX) NULL;
    PRINT 'Added [BackupPasswordHash] to [dbo].[AppUser].';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'BackupPasswordSetAt')
BEGIN
    ALTER TABLE [dbo].[AppUser] ADD [BackupPasswordSetAt] DATETIME2(7) NULL;
    PRINT 'Added [BackupPasswordSetAt] to [dbo].[AppUser].';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'BackupPasswordMustChange')
BEGIN
    ALTER TABLE [dbo].[AppUser] ADD [BackupPasswordMustChange] BIT NOT NULL CONSTRAINT [DF_AppUser_BackupPasswordMustChange] DEFAULT (0);
    PRINT 'Added [BackupPasswordMustChange] to [dbo].[AppUser].';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'LastBackupPasswordLoginUtc')
BEGIN
    ALTER TABLE [dbo].[AppUser] ADD [LastBackupPasswordLoginUtc] DATETIME2(7) NULL;
    PRINT 'Added [LastBackupPasswordLoginUtc] to [dbo].[AppUser].';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'BackupPasswordFailedAttempts')
BEGIN
    ALTER TABLE [dbo].[AppUser] ADD [BackupPasswordFailedAttempts] INT NOT NULL CONSTRAINT [DF_AppUser_BackupPasswordFailedAttempts] DEFAULT (0);
    PRINT 'Added [BackupPasswordFailedAttempts] to [dbo].[AppUser].';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'BackupPasswordLockoutEnd')
BEGIN
    ALTER TABLE [dbo].[AppUser] ADD [BackupPasswordLockoutEnd] DATETIMEOFFSET NULL;
    PRINT 'Added [BackupPasswordLockoutEnd] to [dbo].[AppUser].';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_AppUser_EntraObjectId' AND object_id = OBJECT_ID(N'[dbo].[AppUser]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_AppUser_EntraObjectId] ON [dbo].[AppUser]([EntraObjectId]) WHERE [EntraObjectId] IS NOT NULL;
    PRINT 'Created index [IX_AppUser_EntraObjectId].';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_AppUser_EntraUserPrincipalName' AND object_id = OBJECT_ID(N'[dbo].[AppUser]'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_AppUser_EntraUserPrincipalName] ON [dbo].[AppUser]([EntraUserPrincipalName]) WHERE [EntraUserPrincipalName] IS NOT NULL;
    PRINT 'Created index [IX_AppUser_EntraUserPrincipalName].';
END
GO

-- Backfill default merSETA employees as Entra users
UPDATE [dbo].[AppUser]
SET [IsEntraUser] = 1,
    [EntraAccountEnabled] = 1,
    [EntraUserPrincipalName] = [Email],
    [LastEntraSyncUtc] = SYSUTCDATETIME()
WHERE [Email] LIKE '%@merseta.org.za' AND [IsEntraUser] = 0;
GO
