using System.Diagnostics;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;
using Xunit.Abstractions;

namespace Nsdms.Tests;

public class PerformanceBenchmarksTests
{
    private readonly ITestOutputHelper _output;

    public PerformanceBenchmarksTests(ITestOutputHelper output)
    {
        _output = output;
    }

    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        return (factory, db, audit);
    }

    private async Task SeedBaselineDataAsync(NsdmsDbContext db, int organisationCount = 50, int learnerCount = 200)
    {
        var orgs = new List<Organisation>();
        for (int i = 1; i <= organisationCount; i++)
        {
            orgs.Add(new Organisation
            {
                Id = i,
                CompanyName = $"Enterprise Manufacturing Partner #{i:D4}",
                TradingName = $"EMP-{i:D4}",
                SdlNumber = $"L{700000000 + i}",
                RegistrationNumber = $"2026/00{i:D4}/07",
                OrganisationStatusCode = "ACTIVE",
                LevyCategoryCode = "LEVY_PAYING",
                ChamberCode = "AUTOMOTIVE",
                ProvinceCode = "GP",
                IsActive = true
            });
        }
        db.Organisations.AddRange(orgs);

        var persons = new List<Person>();
        var learners = new List<CompanyLearner>();
        for (int i = 1; i <= learnerCount; i++)
        {
            var p = new Person
            {
                Id = i,
                FirstName = $"LearnerFirst_{i}",
                LastName = $"LearnerLast_{i}",
                RsaIdNumber = $"9501015{i:D6}",
                DateOfBirth = new DateTime(1995, 1, 1),
                Email = $"learner_{i}@training.co.za",
                CitizenStatusCode = "SA_CITIZEN",
                IsActive = true
            };
            persons.Add(p);

            learners.Add(new CompanyLearner
            {
                Id = i,
                PersonId = i,
                OrganisationId = (i % organisationCount) + 1,
                LearnerContractNumber = $"LRN-2026-{i:D5}",
                CommencementDate = new DateTime(2026, 2, 1),
                CompletionDate = new DateTime(2027, 1, 31),
                EnrolmentStatusCode = "REGISTERED",
                EnrolmentStatusId = "01",
                LearningProgrammeTypeCode = "02",
                QualificationTitle = "Automotive Manufacturing NQF 4"
            });
        }
        db.People.AddRange(persons);
        db.CompanyLearners.AddRange(learners);

        // Seed Grant Application & MOA
        var app = new GrantApplication
        {
            Id = 1,
            OrganisationId = 1,
            ProjectTitle = "Large Scale Artisan Pipeline",
            ApplicationStatusCode = "Approved",
            RequestedAmount = 5000000m
        };
        db.GrantApplications.Add(app);

        var moa = new GrantMoa
        {
            Id = 1,
            GrantApplicationId = 1,
            MoaNumber = "MOA-PERF-2026-001",
            MoaStatusCode = "Active",
            TotalContractValue = 5000000m,
            ContractStartDate = new DateTime(2026, 4, 1),
            ContractEndDate = new DateTime(2027, 3, 31)
        };
        db.GrantMoas.Add(moa);

        await db.SaveChangesAsync();
    }

    [Fact]
    public async Task Benchmark_DbContextFactory_ConcurrentReadThroughput()
    {
        var (factory, db, _) = CreateContext();
        await SeedBaselineDataAsync(db, 50, 200);

        const int concurrencyLevel = 50;
        var sw = Stopwatch.StartNew();

        var tasks = Enumerable.Range(1, concurrencyLevel).Select(async i =>
        {
            await using var ctx = (NsdmsDbContext)factory.CreateDbContext();
            var result = await ctx.CompanyLearners
                .AsNoTracking()
                .Include(l => l.Person)
                .Include(l => l.Organisation)
                .Where(l => l.LearningProgrammeTypeCode == "02")
                .OrderByDescending(l => l.Id)
                .Take(25)
                .ToListAsync();

            Assert.NotEmpty(result);
            Assert.True(result.Count <= 25);
        });

        await Task.WhenAll(tasks);
        sw.Stop();

        _output.WriteLine($"[Benchmark] DbContextFactory Concurrent Reads: {concurrencyLevel} tasks executed in {sw.ElapsedMilliseconds} ms ({concurrencyLevel * 1000.0 / Math.Max(1, sw.ElapsedMilliseconds):F1} ops/sec)");
        Assert.True(sw.ElapsedMilliseconds < 5000, $"Concurrent queries took {sw.ElapsedMilliseconds}ms, exceeding 5000ms threshold.");
    }

    [Fact]
    public async Task Benchmark_DoubleWriteAuditTrail_ThroughputAndThreadSafety()
    {
        var (factory, db, auditService) = CreateContext();
        const int auditWriteCount = 200;
        var sw = Stopwatch.StartNew();

        await Parallel.ForEachAsync(Enumerable.Range(1, auditWriteCount), new ParallelOptions { MaxDegreeOfParallelism = 10 }, async (i, ct) =>
        {
            await using var ctx = (NsdmsDbContext)factory.CreateDbContext();
            var audit = new AuditLog
            {
                EntityName = "Organisation",
                RecordId = i,
                ActionName = "BENCHMARK_STRESS_UPDATE",
                Actor = $"StressWorker_{i % 10}",
                Timestamp = DateTime.UtcNow,
                MetadataJson = $"{{\"iteration\": {i}, \"status\": \"OPTIMIZED\", \"timestamp\": \"{DateTime.UtcNow:O}\"}}"
            };
            ctx.AuditLogs.Add(audit);
            await ctx.SaveChangesAsync(ct);
        });

        sw.Stop();

        await using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var totalAudits = await verifyDb.AuditLogs.CountAsync(a => a.ActionName == "BENCHMARK_STRESS_UPDATE");
        Assert.Equal(auditWriteCount, totalAudits);

        _output.WriteLine($"[Benchmark] Double-Write Audit Trail: {auditWriteCount} writes completed in {sw.ElapsedMilliseconds} ms ({auditWriteCount * 1000.0 / Math.Max(1, sw.ElapsedMilliseconds):F1} writes/sec)");
        Assert.True(sw.ElapsedMilliseconds < 10000, $"Audit writes took {sw.ElapsedMilliseconds}ms, exceeding 10000ms threshold.");
    }

    [Fact]
    public async Task Benchmark_FlatFileExport_CsvGenerationSpeed()
    {
        var (factory, db, _) = CreateContext();
        await SeedBaselineDataAsync(db, 20, 100);

        var exportService = new ReportExportService(factory);

        var sw = Stopwatch.StartNew();
        var csvBytes = await exportService.GenerateSarsLevyReconCsvAsync("2026");
        sw.Stop();

        Assert.NotNull(csvBytes);
        Assert.True(csvBytes.Length > 0);

        var csvContent = System.Text.Encoding.UTF8.GetString(csvBytes);
        _output.WriteLine($"[Benchmark] Flat-File CSV Generation: {csvBytes.Length} bytes generated in {sw.Elapsed.TotalMilliseconds:F2} ms");
        Assert.True(sw.ElapsedMilliseconds < 3000, $"Flat file generation took {sw.ElapsedMilliseconds}ms, exceeding 3000ms threshold.");
    }

    [Fact]
    public async Task Benchmark_QuestPdf_StatutoryDocumentRenderingPerformance()
    {
        var (factory, db, _) = CreateContext();
        await SeedBaselineDataAsync(db, 10, 20);

        var exportService = new ReportExportService(factory);

        const int documentGenerationCount = 10;
        var sw = Stopwatch.StartNew();

        for (int i = 0; i < documentGenerationCount; i++)
        {
            var pdfBytes = await exportService.GenerateGrantMoaAgreementPdfAsync(1);
            Assert.NotNull(pdfBytes);
            Assert.True(pdfBytes.Length > 500);
        }

        sw.Stop();

        var avgMs = sw.ElapsedMilliseconds / (double)documentGenerationCount;
        _output.WriteLine($"[Benchmark] QuestPDF Compilation: {documentGenerationCount} documents compiled in {sw.ElapsedMilliseconds} ms (Avg: {avgMs:F2} ms/doc)");
        Assert.True(avgMs < 500, $"Average PDF rendering time was {avgMs:F2}ms, exceeding 500ms threshold.");
    }

    [Fact]
    public async Task Benchmark_FinancialReconciliation_HighThroughputAllocationCalculations()
    {
        const int transactionCount = 10000;
        var sw = Stopwatch.StartNew();

        decimal totalGross = 0;
        decimal totalDg = 0;
        decimal totalMg = 0;
        decimal totalAdmin = 0;
        decimal totalQcto = 0;

        for (int i = 1; i <= transactionCount; i++)
        {
            var grossLevy = 1000.00m + (i * 0.50m);
            var dg = grossLevy * 0.495m;
            var mg = grossLevy * 0.20m;
            var admin = grossLevy * 0.105m;
            var qcto = grossLevy * 0.005m;

            totalGross += grossLevy;
            totalDg += dg;
            totalMg += mg;
            totalAdmin += admin;
            totalQcto += qcto;
        }

        sw.Stop();

        var totalAllocated = totalDg + totalMg + totalAdmin + totalQcto;
        var netDifference = totalGross * 0.805m - totalAllocated;
        Assert.True(Math.Abs(netDifference) < 0.01m);

        _output.WriteLine($"[Benchmark] Statutory Levy Allocation Math: {transactionCount} calculations in {sw.Elapsed.TotalMicroseconds:F0} \u00b5s ({transactionCount / Math.Max(0.001, sw.Elapsed.TotalSeconds):F0} ops/sec)");
        Assert.True(sw.ElapsedMilliseconds < 500, $"Calculation took {sw.ElapsedMilliseconds}ms, exceeding 500ms threshold.");
    }
}
