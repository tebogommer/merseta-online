using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

public static class Phase8NotificationSchemaMigrator
{
    public static async Task MigrateNotificationSchemaAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        var ddl = @"
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'SystemNotification')
BEGIN
    CREATE TABLE [dbo].[SystemNotification] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_SystemNotification] PRIMARY KEY CLUSTERED,
        [RecipientUsername] NVARCHAR(150) NULL,
        [RecipientRole] NVARCHAR(100) NULL,
        [Title] NVARCHAR(200) NOT NULL,
        [Message] NVARCHAR(1000) NOT NULL,
        [ActionUrl] NVARCHAR(300) NULL,
        [NotificationType] NVARCHAR(50) NOT NULL CONSTRAINT [DF_SystemNotification_Type] DEFAULT 'SystemAlert',
        [Severity] NVARCHAR(30) NOT NULL CONSTRAINT [DF_SystemNotification_Severity] DEFAULT 'Info',
        [IsRead] BIT NOT NULL CONSTRAINT [DF_SystemNotification_IsRead] DEFAULT 0,
        [ReadAt] DATETIME2 NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_SystemNotification_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE INDEX [IX_SystemNotification_RecipientUsername] ON [dbo].[SystemNotification]([RecipientUsername]);
    CREATE INDEX [IX_SystemNotification_RecipientRole] ON [dbo].[SystemNotification]([RecipientRole]);
    CREATE INDEX [IX_SystemNotification_IsRead] ON [dbo].[SystemNotification]([IsRead]);
    CREATE INDEX [IX_SystemNotification_CreatedAt] ON [dbo].[SystemNotification]([CreatedAt]);
END;
";

        try
        {
            await context.Database.ExecuteSqlRawAsync(ddl);
            logger?.LogInformation("[SCHEMA MIGRATOR] Phase 8 SystemNotification schema applied successfully.");
        }
        catch (Exception ex)
        {
            logger?.LogWarning(ex, "[SCHEMA MIGRATOR] Phase 8 migration notice: {Message}", ex.Message);
        }
    }
}
