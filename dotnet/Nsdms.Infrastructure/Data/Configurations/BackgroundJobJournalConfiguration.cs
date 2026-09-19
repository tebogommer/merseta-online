using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Data.Configurations;

/// <summary>
/// Entity configuration for BackgroundJobJournal providing explicit column bounds and covering indexes.
/// </summary>
public class BackgroundJobJournalConfiguration : IEntityTypeConfiguration<BackgroundJobJournal>
{
    public void Configure(EntityTypeBuilder<BackgroundJobJournal> builder)
    {
        builder.ToTable("BackgroundJobJournal");
        builder.HasKey(e => e.Id);
        builder.Property(e => e.JobGuid).IsRequired();
        builder.Property(e => e.JobType).HasMaxLength(100).IsRequired();
        builder.Property(e => e.Description).HasMaxLength(500);
        builder.Property(e => e.Status).HasMaxLength(50).IsRequired();
        builder.Property(e => e.CurrentStep).HasMaxLength(250);
        builder.Property(e => e.RequestedBy).HasMaxLength(100);
        builder.Property(e => e.ResultFileName).HasMaxLength(250);
        builder.Property(e => e.ResultContentType).HasMaxLength(100);

        builder.HasIndex(e => e.JobGuid).HasDatabaseName("IX_BackgroundJobJournal_JobGuid");
        builder.HasIndex(e => new { e.Status, e.CreatedAt }).HasDatabaseName("IX_BackgroundJobJournal_Status_CreatedAt");
    }
}
