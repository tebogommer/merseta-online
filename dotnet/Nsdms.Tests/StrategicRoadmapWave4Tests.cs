using System;
using System.Threading.Tasks;
using Microsoft.Extensions.Logging.Abstractions;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Common.Models;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class StrategicRoadmapWave4Tests
{
    [Fact]
    public void ResultPattern_Success_ShouldReturnValueAndNoError()
    {
        // Arrange & Act
        var result = Result.Success("TestPayload");

        // Assert
        Assert.True(result.IsSuccess);
        Assert.False(result.IsFailure);
        Assert.Equal("TestPayload", result.Value);
        Assert.Equal(Error.None, result.Error);
    }

    [Fact]
    public void ResultPattern_Failure_ShouldReturnErrorAndThrowOnValueAccess()
    {
        // Arrange
        var customError = new Error("INSUFFICIENT_FUNDS", "Discretionary grant budget envelope exhausted.");

        // Act
        var result = Result.Failure<string>(customError);

        // Assert
        Assert.False(result.IsSuccess);
        Assert.True(result.IsFailure);
        Assert.Equal("INSUFFICIENT_FUNDS", result.Error.Code);
        Assert.Throws<InvalidOperationException>(() => result.Value);
    }

    [Fact]
    public void DigitalSignatureSealService_GenerateAndVerify_ShouldBeValid()
    {
        // Arrange
        var service = new DigitalSignatureSealService(NullLogger<DigitalSignatureSealService>.Instance);
        var payloadSummary = "DG Tranche 1 Claim - R250,000.00 for Toyota SA";

        // Act
        var seal = service.GenerateApprovalSeal(
            entityName: "GrantPaymentClaim",
            recordId: 501,
            approverUserId: "cfo_officer_1",
            approverRole: "ChiefFinancialOfficer",
            approvedAmount: 250000.00m,
            payloadSummary: payloadSummary);

        // Assert
        Assert.NotNull(seal);
        Assert.Equal("GrantPaymentClaim", seal.EntityName);
        Assert.Equal(501, seal.RecordId);
        Assert.Equal(16, seal.DigitalSecuritySealPrefix.Length);
        Assert.Equal(64, seal.DigitalSecuritySealSha256.Length);

        // Verify with exact same payload
        bool isValid = service.VerifyApprovalSeal(seal, payloadSummary);
        Assert.True(isValid);
    }

    [Fact]
    public void DigitalSignatureSealService_TamperedPayload_ShouldFailVerification()
    {
        // Arrange
        var service = new DigitalSignatureSealService(NullLogger<DigitalSignatureSealService>.Instance);
        var originalPayload = "DG Tranche 1 Claim - R250,000.00 for Toyota SA";
        var tamperedPayload = "DG Tranche 1 Claim - R950,000.00 for Toyota SA"; // Tampered amount in summary

        var seal = service.GenerateApprovalSeal(
            entityName: "GrantPaymentClaim",
            recordId: 501,
            approverUserId: "cfo_officer_1",
            approverRole: "ChiefFinancialOfficer",
            approvedAmount: 250000.00m,
            payloadSummary: originalPayload);

        // Act - Verify with tampered payload
        bool isValid = service.VerifyApprovalSeal(seal, tamperedPayload);

        // Assert
        Assert.False(isValid);
    }

    [Fact]
    public void BackgroundJobJournal_EntityMapping_ShouldStoreAuditMetadata()
    {
        // Arrange & Act
        var journal = new BackgroundJobJournal
        {
            Id = 1,
            JobGuid = Guid.NewGuid(),
            JobType = "DocumentGeneration",
            Description = "Generating WSP approval letter",
            Status = "Completed",
            ProgressPercentage = 100,
            RequestedBy = "system_admin",
            CreatedAt = DateTime.UtcNow,
            CompletedAt = DateTime.UtcNow
        };

        // Assert
        Assert.Equal(1, journal.Id);
        Assert.Equal("Completed", journal.Status);
        Assert.Equal(100, journal.ProgressPercentage);
        Assert.Equal("system_admin", journal.RequestedBy);
    }
}
