using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Data.Configurations;

public class ApiClientConfiguration : IEntityTypeConfiguration<ApiClient>
{
    public void Configure(EntityTypeBuilder<ApiClient> builder)
    {
        builder.ToTable("ApiClient");
        builder.HasKey(e => e.Id);
        builder.Property(e => e.ClientIdentifier).HasMaxLength(100).IsRequired();
        builder.Property(e => e.ClientName).HasMaxLength(200).IsRequired();
        builder.Property(e => e.HashedClientSecret).HasMaxLength(256).IsRequired();
        builder.Property(e => e.AllowedScopes).HasMaxLength(1000).IsRequired();
        builder.Property(e => e.DpopPublicKeyJwk).HasMaxLength(4000);
        builder.Property(e => e.ClientCertificateThumbprint).HasMaxLength(100);
        builder.Property(e => e.Tier).HasMaxLength(50).HasDefaultValue("Enterprise");
        builder.Property(e => e.RateLimitPerMinute).HasDefaultValue(120);
        builder.Property(e => e.IsActive).HasDefaultValue(true);

        builder.HasIndex(e => e.ClientIdentifier).IsUnique().HasDatabaseName("IX_ApiClient_ClientIdentifier");
        builder.HasIndex(e => e.OrganisationId).HasDatabaseName("IX_ApiClient_OrganisationId");

        builder.HasOne(e => e.Organisation)
            .WithMany()
            .HasForeignKey(e => e.OrganisationId)
            .OnDelete(DeleteBehavior.Restrict);
    }
}

public class ApiWebhookSubscriptionConfiguration : IEntityTypeConfiguration<ApiWebhookSubscription>
{
    public void Configure(EntityTypeBuilder<ApiWebhookSubscription> builder)
    {
        builder.ToTable("ApiWebhookSubscription");
        builder.HasKey(e => e.Id);
        builder.Property(e => e.EventTopic).HasMaxLength(100).IsRequired();
        builder.Property(e => e.TargetUrl).HasMaxLength(1000).IsRequired();
        builder.Property(e => e.SecretKey).HasMaxLength(256).IsRequired();
        builder.Property(e => e.IsActive).HasDefaultValue(true);
        builder.Property(e => e.FailureCount).HasDefaultValue(0);

        builder.HasIndex(e => e.OrganisationId).HasDatabaseName("IX_ApiWebhookSubscription_OrganisationId");
        builder.HasIndex(e => new { e.EventTopic, e.IsActive }).HasDatabaseName("IX_ApiWebhookSubscription_EventTopic_IsActive");

        builder.HasOne(e => e.Organisation)
            .WithMany()
            .HasForeignKey(e => e.OrganisationId)
            .OnDelete(DeleteBehavior.Restrict);
    }
}

public class ApiWebhookDeliveryLogConfiguration : IEntityTypeConfiguration<ApiWebhookDeliveryLog>
{
    public void Configure(EntityTypeBuilder<ApiWebhookDeliveryLog> builder)
    {
        builder.ToTable("ApiWebhookDeliveryLog");
        builder.HasKey(e => e.Id);
        builder.Property(e => e.EventTopic).HasMaxLength(100).IsRequired();
        builder.Property(e => e.PayloadJson).IsRequired();
        builder.Property(e => e.ErrorMessage).HasMaxLength(4000);

        builder.HasIndex(e => e.SubscriptionId).HasDatabaseName("IX_ApiWebhookDeliveryLog_SubscriptionId");
        builder.HasIndex(e => e.DeliveredAt).HasDatabaseName("IX_ApiWebhookDeliveryLog_DeliveredAt");

        builder.HasOne(e => e.Subscription)
            .WithMany()
            .HasForeignKey(e => e.SubscriptionId)
            .OnDelete(DeleteBehavior.Cascade);
    }
}

public class ApiIdempotencyRecordConfiguration : IEntityTypeConfiguration<ApiIdempotencyRecord>
{
    public void Configure(EntityTypeBuilder<ApiIdempotencyRecord> builder)
    {
        builder.ToTable("ApiIdempotencyRecord");
        builder.HasKey(e => e.Id);
        builder.Property(e => e.IdempotencyKey).HasMaxLength(100).IsRequired();
        builder.Property(e => e.ClientIdentifier).HasMaxLength(100).IsRequired();
        builder.Property(e => e.RequestPath).HasMaxLength(500).IsRequired();
        builder.Property(e => e.ResponseBodyJson).IsRequired();

        builder.HasIndex(e => e.IdempotencyKey).IsUnique().HasDatabaseName("IX_ApiIdempotencyRecord_IdempotencyKey");
        builder.HasIndex(e => e.ExpiresAt).HasDatabaseName("IX_ApiIdempotencyRecord_ExpiresAt");
    }
}
