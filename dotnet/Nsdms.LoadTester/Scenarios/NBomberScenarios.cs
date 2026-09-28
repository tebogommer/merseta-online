using System.Net.Http;
using Microsoft.EntityFrameworkCore;
using NBomber.Contracts;
using NBomber.CSharp;
using NBomber.Http.CSharp;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;

namespace Nsdms.LoadTester.Scenarios;

public static class NBomberScenarios
{
    public static async Task RunDatabaseBenchmarksAsync(INsdmsDbContextFactory dbContextFactory, int rate = 30, int durationSec = 15)
    {
        Console.WriteLine("\n🚀 Initializing NBomber High-Concurrency Database Scenarios...");
        Console.WriteLine($"   Target Rate: {rate} ops/sec, Duration: {durationSec}s");

        await using var initDb = dbContextFactory.CreateDbContext();
        var activeOrg = await initDb.Organisations.FirstOrDefaultAsync(o => o.OrganisationStatusCode == "ACTIVE")
            ?? await initDb.Organisations.FirstOrDefaultAsync();

        if (activeOrg == null)
        {
            var seedOrg = new Organisation
            {
                CompanyName = "NBomber Benchmark Holdings (Pty) Ltd",
                TradingName = "NBomber Holdings",
                RegistrationNumber = "2026/999999/07",
                SdlNumber = "L999999999",
                ChamberCode = "AUTO",
                OrganisationStatusCode = "ACTIVE",
                LevyCategoryCode = "LEVY_PAYING",
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "NBOMBER_INIT"
            };
            initDb.Organisations.Add(seedOrg);
            await initDb.SaveChangesAsync();
            activeOrg = seedOrg;
        }
        int targetOrgId = activeOrg.Id;
        Console.WriteLine($"   Target Benchmark Organisation ID: {targetOrgId} ({activeOrg.CompanyName})");

        var orgReadScenario = Scenario.Create("db_organisation_read_rcsi", async context =>
        {
            try
            {
                await using var db = dbContextFactory.CreateDbContext();
                // RCSI non-blocking read
                var orgs = await db.Organisations
                    .AsNoTracking()
                    .OrderByDescending(o => o.Id)
                    .Take(20)
                    .ToListAsync();

                return Response.Ok(sizeBytes: orgs.Count * 128);
            }
            catch (Exception)
            {
                return Response.Fail(statusCode: "500");
            }
        })
        .WithWarmUpDuration(TimeSpan.FromSeconds(3))
        .WithLoadSimulations(
            Simulation.Inject(rate: rate, interval: TimeSpan.FromSeconds(1), during: TimeSpan.FromSeconds(durationSec))
        );

        var wspWriteScenario = Scenario.Create("db_wsp_submission_doublewrite", async context =>
        {
            try
            {
                await using var db = dbContextFactory.CreateDbContext();
                var index = (int)context.InvocationNumber;
                var refNo = $"WSP-2026-{index:D6}-{(int)(DateTime.UtcNow.Ticks % 10000):D4}";

                var wsp = new WspSubmission
                {
                    OrganisationId = targetOrgId,
                    FinYear = 2026,
                    ReferenceNumber = refNo,
                    WspApprovalStatusCode = "Submitted",
                    SubmissionDate = DateTime.UtcNow,
                    PlannedTrainingBudget = 250000m,
                    EmployeeCount = 150,
                    IsSignoffQuorumMet = true,
                    RequiredSignoffCount = 2,
                    CompletedSignoffCount = 2,
                    SignoffDigitalSecuritySeal = Guid.NewGuid().ToString("N").ToUpperInvariant(),
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "NBOMBER_LOAD_RUNNER"
                };

                db.WspSubmissions.Add(wsp);
                await db.SaveChangesAsync();

                // Add Training Plan item
                var plan = new WspTrainingPlan
                {
                    WspSubmissionId = wsp.Id,
                    ProgrammeTypeCode = "02",
                    NqfLevel = 4,
                    BeneficiaryCount = 20,
                    EstimatedCost = 50000m,
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "NBOMBER_LOAD_RUNNER"
                };
                db.WspTrainingPlans.Add(plan);

                // Atomic Audit Log entry
                var audit = new AuditLog
                {
                    EntityName = nameof(WspSubmission),
                    RecordId = wsp.Id,
                    ActionName = "NBomber_WspSubmit",
                    Actor = "NBOMBER_LOAD_RUNNER",
                    Timestamp = DateTime.UtcNow,
                    MetadataJson = $"{{\"Ref\":\"{refNo}\",\"Status\":\"Submitted\"}}"
                };
                db.AuditLogs.Add(audit);

                await db.SaveChangesAsync();
                return Response.Ok(statusCode: "201");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"[WSP Benchmark Error]: {ex.Message} -> {ex.InnerException?.Message}");
                return Response.Fail(statusCode: "500");
            }
        })
        .WithWarmUpDuration(TimeSpan.FromSeconds(3))
        .WithLoadSimulations(
            Simulation.Inject(rate: Math.Max(5, rate / 2), interval: TimeSpan.FromSeconds(1), during: TimeSpan.FromSeconds(durationSec))
        );

        Console.WriteLine("📊 Starting NBomber Execution Pipeline...");
        NBomberRunner
            .RegisterScenarios(orgReadScenario, wspWriteScenario)
            .Run();

        Console.WriteLine("\n✅ NBomber Database Benchmark run completed.");
        await Task.CompletedTask;
    }

    public static async Task RunHttpBenchmarksAsync(string baseUrl, int rate = 20, int durationSec = 15)
    {
        Console.WriteLine($"\n🌐 Initializing NBomber HTTP Scenarios against: {baseUrl}...");
        Console.WriteLine($"   Target Rate: {rate} req/sec, Duration: {durationSec}s");

        using var httpClient = new HttpClient { BaseAddress = new Uri(baseUrl) };

        var httpHomeScenario = Scenario.Create("http_get_home_and_pages", async context =>
        {
            var step = await Step.Run("get_landing", context, async () =>
            {
                var request = Http.CreateRequest("GET", "/")
                    .WithHeader("Accept", "text/html,application/xhtml+xml");

                var response = await Http.Send(httpClient, request);
                return response;
            });

            return step;
        })
        .WithWarmUpDuration(TimeSpan.FromSeconds(3))
        .WithLoadSimulations(
            Simulation.Inject(rate: rate, interval: TimeSpan.FromSeconds(1), during: TimeSpan.FromSeconds(durationSec))
        );

        Console.WriteLine("📊 Starting NBomber HTTP Execution Pipeline...");
        NBomberRunner
            .RegisterScenarios(httpHomeScenario)
            .Run();

        Console.WriteLine("\n✅ NBomber HTTP Benchmark run completed.");
        await Task.CompletedTask;
    }
}
