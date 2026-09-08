using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator provisioning the WspExtensionRequest table and related indices.
/// </summary>
public static class Phase15WspExtensionRequestMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            const string ddlSql = @"
                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WspExtensionRequest')
                BEGIN
                    CREATE TABLE [dbo].[WspExtensionRequest] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [OrganisationId] INT NOT NULL,
                        [WspSubmissionId] INT NULL,
                        [SchemeYear] INT NOT NULL,
                        [ApplicationReference] NVARCHAR(50) NOT NULL,
                        [ReasonCode] NVARCHAR(50) NOT NULL DEFAULT 'BusinessRescue',
                        [GroundsDescription] NVARCHAR(MAX) NULL,
                        [StatutoryMotivation] NVARCHAR(MAX) NOT NULL,
                        [RequestedExtensionDate] DATETIME2 NOT NULL,
                        [GrantedExtensionDate] DATETIME2 NULL,
                        [ApprovalStatusCode] NVARCHAR(30) NOT NULL DEFAULT 'PendingReview',
                        [EvidenceDocumentId] NVARCHAR(100) NULL,
                        [EvidenceFileName] NVARCHAR(250) NULL,
                        [SubmittedByUserId] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
                        [SubmittedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [ReviewedByUserId] NVARCHAR(100) NULL,
                        [ReviewedAt] DATETIME2 NULL,
                        [ReviewerComments] NVARCHAR(MAX) NULL,
                        [ApprovedByUserId] NVARCHAR(100) NULL,
                        [ApprovedAt] DATETIME2 NULL,
                        [ApprovalComments] NVARCHAR(MAX) NULL,
                        [DeclarationAccepted] BIT NOT NULL DEFAULT 1,
                        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL,

                        CONSTRAINT [FK_WspExtensionRequest_Organisation] FOREIGN KEY ([OrganisationId]) 
                            REFERENCES [dbo].[Organisation] ([Id]),
                        CONSTRAINT [FK_WspExtensionRequest_WspSubmission] FOREIGN KEY ([WspSubmissionId]) 
                            REFERENCES [dbo].[WspSubmission] ([Id]) ON DELETE SET NULL
                    );

                    CREATE INDEX [IX_WspExtensionRequest_OrganisationId] ON [dbo].[WspExtensionRequest] ([OrganisationId]);
                    CREATE INDEX [IX_WspExtensionRequest_WspSubmissionId] ON [dbo].[WspExtensionRequest] ([WspSubmissionId]);
                    CREATE INDEX [IX_WspExtensionRequest_SchemeYear] ON [dbo].[WspExtensionRequest] ([SchemeYear]);
                    CREATE INDEX [IX_WspExtensionRequest_ApplicationReference] ON [dbo].[WspExtensionRequest] ([ApplicationReference]);
                    CREATE INDEX [IX_WspExtensionRequest_ApprovalStatusCode] ON [dbo].[WspExtensionRequest] ([ApprovalStatusCode]);
                    CREATE INDEX [IX_WspExtensionRequest_ReasonCode] ON [dbo].[WspExtensionRequest] ([ReasonCode]);
                END;
            ";

            await context.Database.ExecuteSqlRawAsync(ddlSql);
            logger?.LogInformation("Phase15WspExtensionRequestMigrator executed successfully.");
        }
    }
}
