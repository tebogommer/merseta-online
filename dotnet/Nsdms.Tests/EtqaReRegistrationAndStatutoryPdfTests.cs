using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class EtqaReRegistrationAndStatutoryPdfTests
{
    private (TestDbContextFactory Factory, IAuditService Audit, QuestPdfDocumentService PdfService) CreateServices()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var conf = new ConfigurationBuilder().AddInMemoryCollection().Build();
        var audit = new AuditService(factory);
        var config = new SystemConfigurationService(factory, conf, audit);
        var flags = new FeatureFlagService(factory, conf, audit);
        var pdfService = new QuestPdfDocumentService(factory, flags, config);

        return (factory, audit, pdfService);
    }

    [Fact]
    public async Task AssessorRenewal_StatusEvaluation_AccuratelyReportsStandingAndDaysRemaining()
    {
        var (factory, audit, _) = CreateServices();

        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var person = new Person { Id = 1, FirstName = "Sipho", LastName = "Dlamini", RsaIdNumber = "8506155555088" };
            var assessor = new EtqaAssessor
            {
                Id = 1,
                PersonId = 1,
                RegistrationNumber = "ASS-2023-084",
                EtqaRole = "Assessor",
                RegistrationStatusCode = "Active",
                StartDate = DateTime.UtcNow.AddYears(-3),
                EndDate = DateTime.UtcNow.AddDays(90), // Due for renewal (< 180 days)
                IsActive = true
            };
            db.People.Add(person);
            db.EtqaAssessors.Add(assessor);
            await db.SaveChangesAsync();
        }

        var service = new AssessorReRegistrationService(factory, audit);
        var status = await service.GetAssessorRenewalStatusAsync(1);

        Assert.Equal(1, status.EtqaAssessorId);
        Assert.Equal("ASS-2023-084", status.RegistrationNumber);
        Assert.Equal("Sipho Dlamini", status.PractitionerName);
        Assert.True(status.IsDueForRenewal);
        Assert.InRange(status.DaysRemaining, 85, 95);
        Assert.False(status.IsExpired);
    }

    [Fact]
    public async Task AssessorReRegistration_InitiationAndCpdLogging_AccumulatesPointsAndSetsThreeYearExpiry()
    {
        var (factory, audit, _) = CreateServices();
        DateTime currentExpiry = new DateTime(2026, 12, 31);

        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var person = new Person { Id = 2, FirstName = "Anelisa", LastName = "Mthembu", RsaIdNumber = "8801015555088" };
            var assessor = new EtqaAssessor
            {
                Id = 2,
                PersonId = 2,
                RegistrationNumber = "MOD-2023-112",
                EtqaRole = "Moderator",
                RegistrationStatusCode = "Active",
                StartDate = currentExpiry.AddYears(-3),
                EndDate = currentExpiry,
                IsActive = true
            };
            db.People.Add(person);
            db.EtqaAssessors.Add(assessor);
            await db.SaveChangesAsync();
        }

        var service = new AssessorReRegistrationService(factory, audit);

        // 1. Initiate Application
        var app = await service.InitiateReRegistrationApplicationAsync(new InitiateReRegistrationRequest
        {
            EtqaAssessorId = 2,
            CpdPortfolioSummary = "Automotive engineering assessment and internal moderation portfolio"
        });

        Assert.NotNull(app);
        Assert.StartsWith("REG-", app.ApplicationReferenceNumber);
        Assert.Equal("Draft", app.ReviewStatusCode);
        Assert.Equal(currentExpiry, app.CurrentExpirationDate);
        Assert.Equal(currentExpiry.AddYears(3), app.ProposedNewExpirationDate);

        // 2. Log CPD Activities
        var act1 = await service.LogCpdActivityAsync(new LogCpdActivityRequest
        {
            ApplicationId = app.Id,
            ActivityTitle = "Annual Automotive Moderation Workshop",
            ActivityCategory = "SetaWorkshop",
            PointsClaimed = 10
        });

        var act2 = await service.LogCpdActivityAsync(new LogCpdActivityRequest
        {
            ApplicationId = app.Id,
            ActivityTitle = "Industry Apprentice Mentorship",
            ActivityCategory = "Mentorship",
            PointsClaimed = 15
        });

        var act3 = await service.LogCpdActivityAsync(new LogCpdActivityRequest
        {
            ApplicationId = app.Id,
            ActivityTitle = "Peer Moderation Review Panel",
            ActivityCategory = "PeerModeration",
            PointsClaimed = 10
        });

        // 3. Submit for Review
        var submittedApp = await service.SubmitApplicationForReviewAsync(app.Id);
        Assert.Equal("CommitteeReview", submittedApp.ReviewStatusCode);
        Assert.Equal(35, submittedApp.CpdPointsAccumulated); // >= 30 points benchmark
    }

    [Fact]
    public async Task AssessorReRegistration_CommitteeAdjudication_UpdatesAssessorValidityAndIssuesSecuritySeal()
    {
        var (factory, audit, _) = CreateServices();
        DateTime currentExpiry = new DateTime(2026, 10, 15);
        int appId = 0;

        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var person = new Person { Id = 3, FirstName = "Johan", LastName = "Botha", RsaIdNumber = "7903125555088" };
            var assessor = new EtqaAssessor
            {
                Id = 3,
                PersonId = 3,
                RegistrationNumber = "ASS-2023-999",
                EtqaRole = "Assessor",
                RegistrationStatusCode = "Active",
                StartDate = currentExpiry.AddYears(-3),
                EndDate = currentExpiry,
                IsActive = true
            };
            db.People.Add(person);
            db.EtqaAssessors.Add(assessor);

            var app = new AssessorReRegistrationApplication
            {
                EtqaAssessorId = 3,
                ApplicationReferenceNumber = "REG-2026-ASS-0003-01",
                CurrentExpirationDate = currentExpiry,
                ProposedNewExpirationDate = currentExpiry.AddYears(3),
                CpdPointsAccumulated = 30,
                ReviewStatusCode = "CommitteeReview"
            };
            db.AssessorReRegistrationApplications.Add(app);
            await db.SaveChangesAsync();
            appId = app.Id;
        }

        var service = new AssessorReRegistrationService(factory, audit);

        // Adjudicate with approval
        var result = await service.AdjudicateApplicationAsync(new AdjudicateReRegistrationRequest
        {
            ApplicationId = appId,
            Approved = true,
            CommitteeDecisionNumber = "ETQA-COM-2026-099",
            AdjudicationNotes = "CPD portfolio approved. 3-year renewal ratified by ETQA Committee."
        }, "EtqaSecretary");

        Assert.Equal("Approved", result.ReviewStatusCode);
        Assert.Equal("ETQA-COM-2026-099", result.CommitteeDecisionNumber);
        Assert.NotNull(result.DigitalSecuritySeal);
        Assert.Equal(64, result.DigitalSecuritySeal.Length); // 64-char SHA-256

        // Check parent assessor in DB
        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var updatedAssessor = await db.EtqaAssessors.FindAsync(3);
            Assert.NotNull(updatedAssessor);
            Assert.Equal(currentExpiry.AddYears(3), updatedAssessor.EndDate);
            Assert.Equal("Active", updatedAssessor.RegistrationStatusCode);
            Assert.Equal("ETQA-COM-2026-099", updatedAssessor.EtqeDecisionNumber);
        }
    }

    [Fact]
    public async Task QuestPdf_GenerateTripartiteAgreementPdf_GeneratesValidPdfBytes()
    {
        var (_, _, pdfService) = CreateServices();

        var learner = new CompanyLearner
        {
            Id = 42,
            LearnerContractNumber = "LRN-2026-0042",
            QualificationTitle = "National Certificate: Mechanical Engineering (Fitting)",
            OfoCode = "653306",
            RegistrationDate = new DateTime(2026, 1, 15),
            ExpectedCompletionDate = new DateTime(2029, 1, 14),
            Person = new Person
            {
                FirstName = "Thabo",
                LastName = "Khumalo",
                RsaIdNumber = "0205125555088",
                Email = "thabo.khumalo@merstest.co.za",
                CellNumber = "0821234567"
            },
            Organisation = new Organisation
            {
                CompanyName = "Bell Equipment Manufacturing",
                SdlNumber = "L123456789"
            },
            TrainingProvider = new TrainingProvider
            {
                ProviderName = "Richards Bay Technical Training Academy",
                AccreditationNumber = "ETQA/17/SDP/089"
            }
        };

        var pdfBytes = await pdfService.GenerateTripartiteAgreementPdfAsync(learner);

        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000);
        // Verify PDF Magic Bytes (%PDF-)
        Assert.Equal((byte)'%', pdfBytes[0]);
        Assert.Equal((byte)'P', pdfBytes[1]);
        Assert.Equal((byte)'D', pdfBytes[2]);
        Assert.Equal((byte)'F', pdfBytes[3]);
        Assert.Equal((byte)'-', pdfBytes[4]);
    }

    [Fact]
    public async Task QuestPdf_GenerateAssessorRegistrationCertificatePdf_GeneratesValidPdfBytes()
    {
        var (_, _, pdfService) = CreateServices();

        var assessor = new EtqaAssessor
        {
            Id = 55,
            RegistrationNumber = "ASS-2026-055",
            EtqaRole = "Assessor",
            EtqaId = "17",
            EtqeDecisionNumber = "ETQA-COM-2026-055",
            StartDate = new DateTime(2026, 3, 1),
            EndDate = new DateTime(2029, 2, 28),
            Person = new Person
            {
                FirstName = "Fatima",
                LastName = "Patel",
                RsaIdNumber = "8209215555088"
            },
            Scopes = new List<AssessorModeratorScope>
            {
                new() { QualificationTitle = "National Certificate: Automotive Repair and Maintenance" },
                new() { QualificationTitle = "Further Education and Training Certificate: Welding Application and Practice" }
            }
        };

        var pdfBytes = await pdfService.GenerateAssessorRegistrationCertificatePdfAsync(assessor);

        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000);
        // Verify PDF Magic Bytes (%PDF-)
        Assert.Equal((byte)'%', pdfBytes[0]);
        Assert.Equal((byte)'P', pdfBytes[1]);
        Assert.Equal((byte)'D', pdfBytes[2]);
        Assert.Equal((byte)'F', pdfBytes[3]);
        Assert.Equal((byte)'-', pdfBytes[4]);
    }
}
