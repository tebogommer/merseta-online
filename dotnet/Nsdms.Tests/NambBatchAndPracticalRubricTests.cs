using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class NambBatchAndPracticalRubricTests
{
    private (TestDbContextFactory Factory, IAuditService Audit) CreateServices()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var audit = new AuditService(factory);
        return (factory, audit);
    }

    [Fact]
    public async Task PracticalRubric_ScoringEvaluation_AccuratelyCalculatesPassFailAgainst70PercentThreshold()
    {
        var (factory, audit) = CreateServices();
        int appId = 0;

        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var person = new Person { FirstName = "Bongani", LastName = "Nkosi", RsaIdNumber = "9501015555088" };
            db.People.Add(person);
            await db.SaveChangesAsync();

            var app = new LearnerTradeTestApplication
            {
                PersonId = person.Id,
                ApplicationNumber = "TT-2026-001",
                TradeTitle = "Welder",
                CompetencyStatusCode = "Pending",
                StatusCode = "Assessing"
            };
            db.LearnerTradeTestApplications.Add(app);
            await db.SaveChangesAsync();
            appId = app.Id;
        }

        var service = new NambBatchService(factory, audit);

        // 1. Pass all tasks (> 70%)
        var passingScores = new List<TradeTestTaskScoreDto>
        {
            new() { TaskNumber = 1, TaskTitle = "Shielded Metal Arc Welding (SMAW)", TotalMarksAvailable = 100, MarksObtained = 85, PassPercentage = 70 },
            new() { TaskNumber = 2, TaskTitle = "Gas Metal Arc Welding (GMAW)", TotalMarksAvailable = 100, MarksObtained = 75, PassPercentage = 70 },
            new() { TaskNumber = 3, TaskTitle = "Tolerances & Safety Inspection", TotalMarksAvailable = 50, MarksObtained = 40, PassPercentage = 70 }
        };

        var passingResult = await service.RecordPracticalTaskMarksAsync(appId, passingScores, "Assessor John Doe");
        Assert.Equal("Competent", passingResult.CompetencyStatusCode);
        Assert.Equal("Competent", passingResult.StatusCode);

        // 2. Failing case (one task fails threshold)
        var failingScores = new List<TradeTestTaskScoreDto>
        {
            new() { TaskNumber = 1, TaskTitle = "Shielded Metal Arc Welding (SMAW)", TotalMarksAvailable = 100, MarksObtained = 85, PassPercentage = 70 },
            new() { TaskNumber = 2, TaskTitle = "Gas Metal Arc Welding (GMAW)", TotalMarksAvailable = 100, MarksObtained = 50, PassPercentage = 70 } // Failed
        };

        var failingResult = await service.RecordPracticalTaskMarksAsync(appId, failingScores, "Assessor John Doe");
        Assert.Equal("NotYetCompetent", failingResult.CompetencyStatusCode);
        Assert.Equal("NotYetCompetent", failingResult.StatusCode);
    }

    [Fact]
    public async Task NambBatch_CreationAndCandidateStaging_SetsSerialBatchAndSecuritySeal()
    {
        var (factory, audit) = CreateServices();
        var candidateIds = new List<int>();

        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            for (int i = 1; i <= 3; i++)
            {
                var person = new Person { FirstName = $"Candidate{i}", LastName = "Artisan", RsaIdNumber = $"90010{i}5555088" };
                db.People.Add(person);
                await db.SaveChangesAsync();

                var app = new LearnerTradeTestApplication
                {
                    PersonId = person.Id,
                    ApplicationNumber = $"TT-2026-00{i}",
                    TradeTitle = "Fitter and Turner",
                    TradeOfoCode = "653306",
                    AssessmentCenterName = "CSIR Test Centre",
                    AssessmentDate = DateTime.UtcNow.AddDays(-5),
                    CompetencyStatusCode = "Competent",
                    StatusCode = "Competent"
                };
                db.LearnerTradeTestApplications.Add(app);
                await db.SaveChangesAsync();
                candidateIds.Add(app.Id);
            }
        }

        var service = new NambBatchService(factory, audit);

        // Check pending candidates
        var pending = await service.GetPendingCandidatesForBatchAsync();
        Assert.True(pending.Count >= 3);

        // Create Batch
        var batch = await service.CreateNambBatchAsync(candidateIds, "Q3 Fitter & Turner NAMB Intake");
        Assert.NotNull(batch);
        Assert.StartsWith("NAMB-", batch.BatchReferenceNumber);
        Assert.Equal(3, batch.TotalCandidates);
        Assert.Equal("SubmittedToNamb", batch.Status);
        Assert.NotNull(batch.DigitalSecuritySeal);
        Assert.Equal(64, batch.DigitalSecuritySeal.Length);

        // Verify candidates updated
        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var apps = await db.LearnerTradeTestApplications
                .Where(a => candidateIds.Contains(a.Id))
                .ToListAsync();

            foreach (var app in apps)
            {
                Assert.Equal(batch.Id, app.NambSubmissionBatchId);
                Assert.Equal("AwaitingNambApproval", app.StatusCode);
                Assert.Equal("Pending", app.NambDecisionStatusCode);
            }
        }
    }

    [Fact]
    public async Task NambBatch_TsvExportAndAdjudication_ProducesValidTsvAndCertifiesCandidates()
    {
        var (factory, audit) = CreateServices();
        int batchId = 0;
        int app1Id = 0;
        int app2Id = 0;

        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var p1 = new Person { FirstName = "Mandla", LastName = "Mahlangu", RsaIdNumber = "9102025555088" };
            var p2 = new Person { FirstName = "Kagiso", LastName = "Mokoena", RsaIdNumber = "9203035555088" };
            db.People.AddRange(p1, p2);
            await db.SaveChangesAsync();

            var app1 = new LearnerTradeTestApplication
            {
                PersonId = p1.Id,
                ApplicationNumber = "TT-2026-MAN-01",
                TradeTitle = "Electrician",
                TradeOfoCode = "671101",
                AssessmentCenterName = "Apex Training Centre",
                AssessmentDate = new DateTime(2026, 7, 20),
                CompetencyStatusCode = "Competent",
                StatusCode = "Competent"
            };
            var app2 = new LearnerTradeTestApplication
            {
                PersonId = p2.Id,
                ApplicationNumber = "TT-2026-KAG-02",
                TradeTitle = "Electrician",
                TradeOfoCode = "671101",
                AssessmentCenterName = "Apex Training Centre",
                AssessmentDate = new DateTime(2026, 7, 21),
                CompetencyStatusCode = "Competent",
                StatusCode = "Competent"
            };
            db.LearnerTradeTestApplications.AddRange(app1, app2);
            await db.SaveChangesAsync();
            app1Id = app1.Id;
            app2Id = app2.Id;
        }

        var service = new NambBatchService(factory, audit);
        var batch = await service.CreateNambBatchAsync(new List<int> { app1Id, app2Id }, "Electrician Batch");
        batchId = batch.Id;

        // Export TSV
        var tsv = await service.GenerateNambExportTsvAsync(batchId);
        Assert.NotNull(tsv);
        Assert.Contains("BATCH_REF\tAPP_REF\tRSA_ID", tsv);
        Assert.Contains("TT-2026-MAN-01", tsv);
        Assert.Contains("Electrician", tsv);

        // Process Decision (Allocate serials)
        var serials = new Dictionary<int, string>
        {
            { app1Id, "NAMB-2026-ELEC-0042" },
            { app2Id, "NAMB-2026-ELEC-0043" }
        };

        var updatedBatch = await service.ProcessNambDecisionAsync(batchId, "Approved", "All verified and competent.", serials);
        Assert.Equal("Approved", updatedBatch.Status);
        Assert.Equal(2, updatedBatch.ApprovedCandidates);
        Assert.Equal(0, updatedBatch.RejectedCandidates);

        // Verify candidates certified
        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var a1 = await db.LearnerTradeTestApplications.FindAsync(app1Id);
            Assert.NotNull(a1);
            Assert.Equal("NAMB-2026-ELEC-0042", a1.NambSerialNumber);
            Assert.Equal("Certified", a1.StatusCode);
            Assert.StartsWith("CERT-NAMB-", a1.SerialCertificateNumber);
        }
    }
}
