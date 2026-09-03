using Microsoft.EntityFrameworkCore;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class HighVolumePrimaryKeyTests
{
    private static DbContextOptions<NsdmsDbContext> CreateOptions(string dbName)
    {
        return new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: dbName)
            .Options;
    }

    [Fact]
    public async Task HighVolumeEntities_ShouldSupport64BitBigIntPrimaryKeys()
    {
        var dbName = Guid.NewGuid().ToString();
        var options = CreateOptions(dbName);

        const long largeBigIntValue = 5_000_000_000L; // Exceeds 32-bit INT.MaxValue (2,147,483,647)

        using var db = new NsdmsDbContext(options);

        // 1. LevyFileLine
        var levyLine = new LevyFileLine
        {
            Id = largeBigIntValue,
            LevyFileId = 1,
            SdlNumber = "L123456789",
            SchemeYear = "2026",
            MandatoryLevyAmount = 50000m,
            DiscretionaryLevyAmount = 120000m
        };
        db.LevyFileLines.Add(levyLine);

        // 2. WspTrainingPlan
        var plan = new WspTrainingPlan
        {
            Id = largeBigIntValue + 1,
            WspSubmissionId = 1,
            ProgrammeTypeCode = "Learnership",
            NqfLevel = 4,
            EstimatedCost = 750000m,
            BeneficiaryCount = 25
        };
        db.WspTrainingPlans.Add(plan);

        // 3. AuditLog
        var audit = new AuditLog
        {
            Id = largeBigIntValue + 2,
            EntityName = "LevyFileLine",
            RecordId = largeBigIntValue,
            ActionName = "RECONCILE",
            Actor = "finance_officer"
        };
        db.AuditLogs.Add(audit);

        await db.SaveChangesAsync();

        // Query back and verify 64-bit integrity
        var fetchedLine = await db.LevyFileLines.FindAsync(largeBigIntValue);
        Assert.NotNull(fetchedLine);
        Assert.Equal(largeBigIntValue, fetchedLine.Id);

        var fetchedPlan = await db.WspTrainingPlans.FindAsync(largeBigIntValue + 1);
        Assert.NotNull(fetchedPlan);
        Assert.Equal(largeBigIntValue + 1, fetchedPlan.Id);

        var fetchedAudit = await db.AuditLogs.FindAsync(largeBigIntValue + 2);
        Assert.NotNull(fetchedAudit);
        Assert.Equal(largeBigIntValue + 2, fetchedAudit.Id);
        Assert.Equal(largeBigIntValue, fetchedAudit.RecordId);
    }
}
