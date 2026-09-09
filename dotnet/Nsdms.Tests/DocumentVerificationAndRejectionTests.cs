using System.Text;
using System.Text.Json;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class DocumentVerificationAndRejectionTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, SystemConfigurationService config, LocalFileStorageService storage) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var inMemory = new Dictionary<string, string?> { { "Storage.UploadDirectory", "test_uploads" } };
        var conf = new ConfigurationBuilder().AddInMemoryCollection(inMemory).Build();
        var configService = new SystemConfigurationService(factory, conf, audit);
        var storage = new LocalFileStorageService(factory, configService, audit);
        return (factory, db, audit, configService, storage);
    }

    [Fact]
    public async Task VerifyAttachment_WhenMarkedCompliant_RecordsOfficerDetailsAndTimestampAndCertDate()
    {
        var (factory, db, audit, config, storage) = CreateContext();

        // 1. Upload a document
        var content = "SAMPLE CERTIFIED RSA ID COPY";
        using var stream = new MemoryStream(Encoding.UTF8.GetBytes(content));
        var attachment = await storage.SaveFileAsync("LearnerTradeTest", 501, "certified_id.pdf", "application/pdf", stream, "ID_DOCUMENT", "ApplicantUser");

        Assert.NotNull(attachment);
        Assert.Equal("Pending", attachment.VerificationStatusCode);
        Assert.True(attachment.IsPendingReview);
        Assert.False(attachment.IsCompliant);

        // 2. Officer verifies document as Compliant (OK)
        var certDate = new DateTime(2026, 8, 15, 0, 0, 0, DateTimeKind.Utc);
        var officerName = "Thabo Molefe (CLO Inspector)";
        var notes = "Certified copy is clear, stamp legible, verified within 90-day window.";

        var verified = await storage.VerifyAttachmentAsync(
            attachment.Id,
            isCompliant: true,
            rejectionReasonCodes: null,
            customNotes: notes,
            certificationDate: certDate,
            expiryDate: null,
            currentUsername: officerName
        );

        Assert.NotNull(verified);
        Assert.Equal("Compliant", verified.VerificationStatusCode);
        Assert.True(verified.IsCompliant);
        Assert.False(verified.IsNonCompliant);
        Assert.False(verified.IsPendingReview);
        Assert.Equal(officerName, verified.VerifiedBy);
        Assert.NotNull(verified.VerifiedAt);
        Assert.Equal(certDate, verified.DocumentCertificationDate);
        Assert.Equal(notes, verified.VerificationNotes);
        Assert.Null(verified.RejectionReason);
        Assert.Null(verified.RejectionReasonCodesJson);

        // 3. Confirm audit log recorded the action
        using var verifyDb = factory.CreateDbContext();
        var logs = await verifyDb.AuditLogs
            .Where(l => l.EntityName == "DocumentAttachment" && l.RecordId == attachment.Id && l.ActionName == "VerifyAttachmentApproved")
            .ToListAsync();
        Assert.Single(logs);
        Assert.Equal(officerName, logs[0].Actor);
    }

    [Fact]
    public async Task VerifyAttachment_WhenMarkedNonCompliant_RecordsMultiSelectReasonsAndJson()
    {
        var (factory, db, audit, config, storage) = CreateContext();

        // 1. Seed rejection reasons
        await storage.SaveRejectionReasonAsync(new DocumentRejectionReasonType
        {
            Code = "ID_EXPIRED_CERT",
            Name = "Certification older than 3 months",
            Description = "Statutory 90-day validity exceeded",
            DocumentCategoryCode = "ID_DOCUMENT",
            Active = true,
            DisplayOrder = 1
        });

        await storage.SaveRejectionReasonAsync(new DocumentRejectionReasonType
        {
            Code = "ID_BLURRY",
            Name = "Document illegible or blurry",
            Description = "Resolution too low to verify details",
            DocumentCategoryCode = "ID_DOCUMENT",
            Active = true,
            DisplayOrder = 2
        });

        // 2. Upload document
        using var stream = new MemoryStream(Encoding.UTF8.GetBytes("BLURRY ID COPY"));
        var attachment = await storage.SaveFileAsync("WorkplaceApproval", 202, "id_scan.png", "image/png", stream, "ID_DOCUMENT", "EmployerSDF");

        // 3. Officer rejects document with multiple reasons
        var selectedCodes = new List<string> { "ID_EXPIRED_CERT", "ID_BLURRY" };
        var remarks = "Please provide high-resolution scan of newly certified copy.";
        var officer = "Nthabiseng Khumalo (QA)";

        var rejected = await storage.VerifyAttachmentAsync(
            attachment.Id,
            isCompliant: false,
            rejectionReasonCodes: selectedCodes,
            customNotes: remarks,
            certificationDate: null,
            expiryDate: null,
            currentUsername: officer
        );

        Assert.NotNull(rejected);
        Assert.Equal("NonCompliant", rejected.VerificationStatusCode);
        Assert.True(rejected.IsNonCompliant);
        Assert.False(rejected.IsCompliant);
        Assert.Equal(officer, rejected.VerifiedBy);
        Assert.NotNull(rejected.VerifiedAt);

        // Verify JSON array of codes
        Assert.NotNull(rejected.RejectionReasonCodesJson);
        var parsedCodes = JsonSerializer.Deserialize<List<string>>(rejected.RejectionReasonCodesJson);
        Assert.NotNull(parsedCodes);
        Assert.Equal(2, parsedCodes.Count);
        Assert.Contains("ID_EXPIRED_CERT", parsedCodes);
        Assert.Contains("ID_BLURRY", parsedCodes);

        // Verify human-readable summary contains reasons and remarks
        Assert.NotNull(rejected.RejectionReason);
        Assert.Contains("Certification older than 3 months", rejected.RejectionReason);
        Assert.Contains("Document illegible or blurry", rejected.RejectionReason);
        Assert.Contains("Officer Remarks: Please provide high-resolution scan", rejected.RejectionReason);

        // Confirm audit log
        using var verifyDb = factory.CreateDbContext();
        var logs = await verifyDb.AuditLogs
            .Where(l => l.EntityName == "DocumentAttachment" && l.RecordId == attachment.Id && l.ActionName == "VerifyAttachmentRejected")
            .ToListAsync();
        Assert.Single(logs);
        Assert.Equal(officer, logs[0].Actor);
    }

    [Fact]
    public async Task GetRejectionReasons_FiltersByCategoryAndIncludesUniversalAll()
    {
        var (factory, db, audit, config, storage) = CreateContext();

        // Seed 3 categories
        await storage.SaveRejectionReasonAsync(new DocumentRejectionReasonType
        {
            Code = "DOC_GENERAL_CORRUPT",
            Name = "File damaged or corrupted",
            DocumentCategoryCode = "ALL",
            Active = true,
            DisplayOrder = 1
        });

        await storage.SaveRejectionReasonAsync(new DocumentRejectionReasonType
        {
            Code = "ID_MISSING_BACK",
            Name = "Smart ID back side missing",
            DocumentCategoryCode = "ID_DOCUMENT",
            Active = true,
            DisplayOrder = 2
        });

        await storage.SaveRejectionReasonAsync(new DocumentRejectionReasonType
        {
            Code = "BANK_EXPIRED",
            Name = "Bank letter older than 3 months",
            DocumentCategoryCode = "BANK_CONFIRMATION",
            Active = true,
            DisplayOrder = 3
        });

        // Query for ID_DOCUMENT: should return ALL + ID_DOCUMENT, but not BANK_CONFIRMATION
        var idReasons = await storage.GetRejectionReasonsAsync("ID_DOCUMENT", activeOnly: true);
        Assert.Equal(2, idReasons.Count);
        Assert.Contains(idReasons, r => r.Code == "DOC_GENERAL_CORRUPT");
        Assert.Contains(idReasons, r => r.Code == "ID_MISSING_BACK");
        Assert.DoesNotContain(idReasons, r => r.Code == "BANK_EXPIRED");

        // Query for BANK_CONFIRMATION: should return ALL + BANK_CONFIRMATION
        var bankReasons = await storage.GetRejectionReasonsAsync("BANK_CONFIRMATION", activeOnly: true);
        Assert.Equal(2, bankReasons.Count);
        Assert.Contains(bankReasons, r => r.Code == "DOC_GENERAL_CORRUPT");
        Assert.Contains(bankReasons, r => r.Code == "BANK_EXPIRED");
        Assert.DoesNotContain(bankReasons, r => r.Code == "ID_MISSING_BACK");
    }

    [Fact]
    public async Task AdminCrud_RejectionReasons_CreateUpdateDeactivate_WorksCorrectly()
    {
        var (factory, db, audit, config, storage) = CreateContext();

        // 1. Create
        var newReason = new DocumentRejectionReasonType
        {
            Code = "TEST_REASON_CODE",
            Name = "Initial Reason Title",
            Description = "Initial statutory description",
            DocumentCategoryCode = "SITE_PHOTO",
            DisplayOrder = 10,
            Active = true
        };

        var created = await storage.SaveRejectionReasonAsync(newReason, "AdminTester");
        Assert.NotNull(created);
        Assert.Equal("TEST_REASON_CODE", created.Code);

        // 2. Read
        var all = await storage.GetAllRejectionReasonsAsync("TEST_REASON", "SITE_PHOTO");
        Assert.Single(all);
        Assert.Equal("Initial Reason Title", all[0].Name);

        // 3. Update
        created.Name = "Updated Reason Title";
        created.DisplayOrder = 15;
        await storage.SaveRejectionReasonAsync(created, "AdminTester");

        var updated = await storage.GetAllRejectionReasonsAsync("TEST_REASON", "SITE_PHOTO");
        Assert.Single(updated);
        Assert.Equal("Updated Reason Title", updated[0].Name);
        Assert.Equal(15, updated[0].DisplayOrder);

        // 4. Deactivate (soft-delete)
        var deactivated = await storage.DeleteRejectionReasonAsync("TEST_REASON_CODE", "AdminTester");
        Assert.True(deactivated);

        var activeOnly = await storage.GetRejectionReasonsAsync("SITE_PHOTO", activeOnly: true);
        Assert.DoesNotContain(activeOnly, r => r.Code == "TEST_REASON_CODE");

        var includingInactive = await storage.GetAllRejectionReasonsAsync("TEST_REASON", "SITE_PHOTO");
        Assert.Single(includingInactive);
        Assert.False(includingInactive[0].Active);
    }
}
