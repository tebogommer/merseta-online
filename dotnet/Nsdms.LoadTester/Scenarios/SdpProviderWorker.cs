using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.LoadTester.Data;
using Nsdms.LoadTester.Telemetry;

namespace Nsdms.LoadTester.Scenarios;

public class SdpProviderWorker
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly PerformanceMetricsCollector _metrics;

    public SdpProviderWorker(INsdmsDbContextFactory contextFactory, PerformanceMetricsCollector metrics)
    {
        _contextFactory = contextFactory;
        _metrics = metrics;
    }

    public async Task<int> ExecuteProviderRegistrationAsync(int sdpIndex, string username = "SDP_CONTACT")
    {
        return await _metrics.MeasureAsync("TrainingProviders", "SDP_RegisterAndAccredit", async () =>
        {
            await using var context = _contextFactory.CreateDbContext();
            var (legal, trading, reg, chamber) = StatutorySyntheticDataGenerator.GenerateOrganisation(20000 + sdpIndex);
            var providerCode = $"SDP-{sdpIndex:D5}";
            var accreditationNo = $"ACC/2026/08/{sdpIndex:D4}";

            // 1. Legal Organisation
            var org = new Organisation
            {
                CompanyName = $"{trading} Training Academy (Pty) Ltd",
                TradingName = $"{trading} Academy",
                RegistrationNumber = reg,
                SdlNumber = StatutorySyntheticDataGenerator.GenerateSdlNumber(10000 + sdpIndex),
                ChamberCode = chamber,
                OrganisationStatusCode = "ACTIVE",
                LevyCategoryCode = "NON_LEVY_PAYING",
                CreatedAt = DateTime.UtcNow,
                CreatedBy = username
            };
            context.Organisations.Add(org);
            await context.SaveChangesAsync();

            // 2. Training Provider Profile (SETMIS File 100)
            var provider = new TrainingProvider
            {
                OrganisationId = org.Id,
                ProviderCode = providerCode,
                EtqaId = "17",
                AccreditationNumber = accreditationNo,
                AccreditationStartDate = DateTime.UtcNow.AddMonths(-6),
                AccreditationEndDate = DateTime.UtcNow.AddYears(3),
                ProviderClassId = "02", // Private Provider
                ProviderTypeId = "02",  // Training
                ProviderStatusId = "01", // Accredited
                CreatedAt = DateTime.UtcNow,
                CreatedBy = username
            };
            context.TrainingProviders.Add(provider);
            await context.SaveChangesAsync();

            // 3. Primary Contact Quorum
            var (fn, ln, email, cell) = StatutorySyntheticDataGenerator.GeneratePerson(30000 + sdpIndex);
            var contact = new TrainingProviderContact
            {
                TrainingProviderId = provider.Id,
                FirstName = fn,
                LastName = ln,
                Email = email,
                CellNumber = cell,
                ContactDesignation = "Director of Academic Quality",
                IsPrimaryContact = true,
                IsBankingConfirmationAuthorized = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = username
            };
            context.TrainingProviderContacts.Add(contact);

            // 4. QMS Self-Evaluation Item
            var selfEval = new TrainingProviderSelfEvaluation
            {
                TrainingProviderId = provider.Id,
                CriteriaCode = "QMS-01",
                CriteriaCategory = "Quality Management Systems",
                CriteriaDescription = "Provider maintains written policies for assessment and RPL",
                IsCompliant = true,
                DocumentReferenceNumber = "POL-QMS-2026-V1.pdf",
                ApplicantComments = "Verified during load test simulation",
                CreatedAt = DateTime.UtcNow,
                CreatedBy = username
            };
            context.TrainingProviderSelfEvaluations.Add(selfEval);

            // 5. Double-write Audit Log
            var audit = new AuditLog
            {
                EntityName = nameof(TrainingProvider),
                RecordId = provider.Id,
                ActionName = "RegisterTrainingProvider",
                Actor = username,
                Timestamp = DateTime.UtcNow,
                MetadataJson = $"{{\"ProviderCode\":\"{providerCode}\",\"AccreditationNumber\":\"{accreditationNo}\"}}"
            };
            context.AuditLogs.Add(audit);

            await context.SaveChangesAsync();
            return provider.Id;
        });
    }
}
