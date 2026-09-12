using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.LoadTester.Data;
using Nsdms.LoadTester.Telemetry;

namespace Nsdms.LoadTester.Scenarios;

public class LearnerLifecycleWorker
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly PerformanceMetricsCollector _metrics;

    public LearnerLifecycleWorker(INsdmsDbContextFactory contextFactory, PerformanceMetricsCollector metrics)
    {
        _contextFactory = contextFactory;
        _metrics = metrics;
    }

    public async Task<int> ExecuteEnrolmentAndTransitionAsync(
        int learnerIndex,
        int organisationId,
        int trainingProviderId,
        string username = "SDF_LEARNER_OFFICER")
    {
        return await _metrics.MeasureAsync("Learners", "Learner_EnrolmentAndTransition", async () =>
        {
            await using var context = _contextFactory.CreateDbContext();

            // 1. Create Demographic Person with valid RSA National ID (SETMIS File 400)
            var birthDate = new DateTime(1995 + (learnerIndex % 10), 1 + (learnerIndex % 12), 1 + (learnerIndex % 28));
            var rsaId = StatutorySyntheticDataGenerator.GenerateRsaIdNumber(birthDate, isMale: learnerIndex % 2 == 0);
            var (fn, ln, email, cell) = StatutorySyntheticDataGenerator.GeneratePerson(50000 + learnerIndex);

            var person = new Person
            {
                FirstName = fn,
                LastName = ln,
                RsaIdNumber = rsaId,
                DateOfBirth = birthDate,
                Email = email,
                CellNumber = cell,
                GenderCode = learnerIndex % 2 == 0 ? "M" : "F",
                EquityCode = "BA",
                CreatedAt = DateTime.UtcNow,
                CreatedBy = username
            };

            // 2. Register CompanyLearner Agreement (SETMIS Files 500, 501) with navigational link to person
            var regNo = $"LRN-2026-SIM-{learnerIndex:D7}";
            var learner = new CompanyLearner
            {
                Person = person,
                OrganisationId = organisationId,
                TrainingProviderId = trainingProviderId,
                LearnerContractNumber = regNo,
                LearningProgrammeTypeCode = (learnerIndex % 3 == 0) ? "01" : ((learnerIndex % 3 == 1) ? "02" : "05"), // Apprenticeship / Learnership / Bursary
                EnrolmentStatusCode = "Registered",
                InstateStatusCode = "Active",
                RegistrationDate = DateTime.UtcNow.AddMonths(-3),
                CommencementDate = DateTime.UtcNow.AddMonths(-3),
                ExpectedCompletionDate = DateTime.UtcNow.AddYears(3),
                CreatedAt = DateTime.UtcNow,
                CreatedBy = username
            };
            context.CompanyLearners.Add(learner);
            await context.SaveChangesAsync();

            // 3. Mutate / Transition In-State Status (simulate real lifecycle amendment)
            var targetStatus = (learnerIndex % 4) switch
            {
                0 => "Active",
                1 => "Extension Requested",
                2 => "Transfer Application",
                _ => "Active"
            };

            learner.InstateStatusCode = targetStatus;
            learner.ModifiedAt = DateTime.UtcNow;
            learner.ModifiedBy = username;

            // 4. Double-write Audit Log
            var audit = new AuditLog
            {
                EntityName = nameof(CompanyLearner),
                RecordId = learner.Id,
                ActionName = "EnrolAndTransitionLearner",
                Actor = username,
                Timestamp = DateTime.UtcNow,
                MetadataJson = $"{{\"ContractNumber\":\"{regNo}\",\"PersonId\":{learner.PersonId},\"InstateStatus\":\"{targetStatus}\"}}"
            };
            context.AuditLogs.Add(audit);

            await context.SaveChangesAsync();
            return learner.Id;
        });
    }
}
