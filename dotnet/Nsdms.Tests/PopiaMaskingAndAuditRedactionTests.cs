using Nsdms.Application.Common.Utilities;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Xunit;

namespace Nsdms.Tests;

public class PopiaMaskingAndAuditRedactionTests
{
    private readonly TestDbContextFactory _factory;
    private readonly AuditService _audit;

    public PopiaMaskingAndAuditRedactionTests()
    {
        _factory = new TestDbContextFactory($"PopiaTestDb_{Guid.NewGuid()}");
        _audit = new AuditService(_factory);
    }

    [Theory]
    [InlineData("9201015009087", "920101*****87")]
    [InlineData("0101015000080", "010101*****80")]
    [InlineData("8505055009081", "850505*****81")]
    public void MaskRsaId_ShouldPreserveDoBAndChecksumWhileMaskingMiddleDigits(string rawId, string expectedMasked)
    {
        var masked = PopiaMaskingUtility.MaskRsaId(rawId);
        Assert.Equal(expectedMasked, masked);
    }

    [Theory]
    [InlineData("1234567890", "******7890")]
    [InlineData("4000998877", "******8877")]
    [InlineData("62000012345", "*******2345")]
    public void MaskBankAccount_ShouldPreserveOnlyLastFourDigits(string rawAccount, string expectedMasked)
    {
        var masked = PopiaMaskingUtility.MaskBankAccount(rawAccount);
        Assert.Equal(expectedMasked, masked);
    }

    [Theory]
    [InlineData("+27821234567", "+278 *** 4567")]
    [InlineData("0821234567", "0821 *** 4567")]
    public void MaskPhone_ShouldMaskMiddleDigits(string rawPhone, string expectedMasked)
    {
        var masked = PopiaMaskingUtility.MaskPhone(rawPhone);
        Assert.Equal(expectedMasked, masked);
    }

    [Theory]
    [InlineData("john.doe@merseta.org.za", "j***e@merseta.org.za")]
    [InlineData("tmoepi@merseta.org.za", "t***i@merseta.org.za")]
    public void MaskEmail_ShouldMaskUsernamePreserveDomain(string rawEmail, string expectedMasked)
    {
        var masked = PopiaMaskingUtility.MaskEmail(rawEmail);
        Assert.Equal(expectedMasked, masked);
    }

    [Fact]
    public async Task AuditService_ShouldAutomaticallySanitizePiiInMetadataJson()
    {
        // Arrange
        var sensitiveState = new
        {
            RsaIdNumber = "9201015009087",
            BankAccountNumber = "1234567890",
            PhoneNumber = "+27821234567",
            PasswordHash = "SuperSecretHash$123",
            CompanyName = "Apex Engineering Works"
        };

        // Act
        await _audit.LogAsync("Person", 101, "UPDATE_PERSON_PROFILE", "AdminUser", sensitiveState);

        // Assert
        using var db = await _factory.CreateDbContextAsync();
        var log = db.AuditLogs.FirstOrDefault(l => l.EntityName == "Person" && l.RecordId == 101);

        Assert.NotNull(log);
        Assert.NotNull(log.MetadataJson);

        // Raw PII must NOT exist in the serialized JSON
        Assert.DoesNotContain("9201015009087", log.MetadataJson);
        Assert.DoesNotContain("1234567890", log.MetadataJson);
        Assert.DoesNotContain("SuperSecretHash$123", log.MetadataJson);

        // Masked values and safe non-PII must exist
        Assert.Contains("920101*****87", log.MetadataJson);
        Assert.Contains("******7890", log.MetadataJson);
        Assert.Contains("[REDACTED_SECRET]", log.MetadataJson);
        Assert.Contains("Apex Engineering Works", log.MetadataJson);
    }
}
