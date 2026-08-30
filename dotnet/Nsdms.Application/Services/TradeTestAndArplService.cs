using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class TradeTestAndArplService : ITradeTestAndArplService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly AuditService _audit;

    public TradeTestAndArplService(INsdmsDbContextFactory contextFactory, AuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<LearnerTradeTestApplication> CreateTradeTestApplicationAsync(
        int companyLearnerId,
        string tradeTitle,
        string applicationTypeCode = "Section26D",
        int attemptNumber = 1,
        string? tradeOfoCode = null,
        int? preferredTrainingCenterId = null,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var learner = await db.CompanyLearners
            .Include(l => l.Person)
            .Include(l => l.Organisation)
            .FirstOrDefaultAsync(l => l.Id == companyLearnerId);

        if (learner == null)
        {
            throw new KeyNotFoundException($"CompanyLearner with ID {companyLearnerId} not found.");
        }

        var app = new LearnerTradeTestApplication
        {
            CompanyLearnerId = companyLearnerId,
            PersonId = learner.PersonId,
            OrganisationId = learner.OrganisationId,
            TrainingProviderId = preferredTrainingCenterId,
            ApplicationNumber = $"TT-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}",
            TradeTitle = tradeTitle,
            TradeOfoCode = tradeOfoCode,
            ApplicationTypeCode = applicationTypeCode,
            AttemptNumber = attemptNumber,
            LearnerReadinessDate = DateTime.UtcNow,
            StatusCode = "Submitted",
            CompetencyStatusCode = "Pending",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.LearnerTradeTestApplications.Add(app);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "LearnerTradeTestApplication", app.Id, "CreateTradeTestApplication", currentUsername, null, app);
        await db.SaveChangesAsync();

        return app;
    }

    public async Task<ArplTradeTestInformation> SubmitArplEvidenceAndChecklistAsync(
        int applicationId,
        int yearsOfExperience,
        string currentEmployerName,
        string employerContactPhone,
        List<ArplExperienceDetail> experienceDetails,
        List<ArplTrainingDetail> trainingDetails,
        decimal portfolioScorePercentage,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var app = await db.LearnerTradeTestApplications
            .Include(t => t.ExperienceDetails)
            .Include(t => t.TrainingDetails)
            .FirstOrDefaultAsync(t => t.Id == applicationId);

        if (app == null)
        {
            throw new KeyNotFoundException($"LearnerTradeTestApplication with ID {applicationId} not found.");
        }

        string recommendation = portfolioScorePercentage >= 70m ? "ProceedToTradeTest" : "RequiresBridgingTraining";

        var arplInfo = new ArplTradeTestInformation
        {
            LearnerTradeTestApplicationId = applicationId,
            YearsOfExperienceInTrade = yearsOfExperience,
            CurrentEmployerName = currentEmployerName,
            EmployerContactPhone = employerContactPhone,
            PortfolioOfEvidenceVerified = true,
            PortfolioScorePercentage = portfolioScorePercentage,
            ToolkitChecklistVerified = true,
            PortfolioAssessorUserId = currentUsername,
            PortfolioAssessmentDate = DateTime.UtcNow,
            ArplRecommendation = recommendation,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.ArplTradeTestInformations.Add(arplInfo);

        foreach (var exp in experienceDetails)
        {
            exp.LearnerTradeTestApplicationId = applicationId;
            exp.CreatedAt = DateTime.UtcNow;
            exp.CreatedBy = currentUsername;
            db.ArplExperienceDetails.Add(exp);
        }

        foreach (var trn in trainingDetails)
        {
            trn.LearnerTradeTestApplicationId = applicationId;
            trn.CreatedAt = DateTime.UtcNow;
            trn.CreatedBy = currentUsername;
            db.ArplTrainingDetails.Add(trn);
        }

        var before = new { app.StatusCode, app.Notes };
        app.StatusCode = "AwaitingNambApproval";
        app.Notes = $"ARPL Portfolio Score: {portfolioScorePercentage}%. Recommendation: {recommendation}";
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        _audit.LogAction(db, "LearnerTradeTestApplication", app.Id, "SubmitArplPortfolio", currentUsername, before, app);
        await db.SaveChangesAsync();

        return arplInfo;
    }

    public async Task<LearnerTradeTestApplication> AllocateTradeTestCenterAndScheduleAsync(
        int applicationId,
        int trainingProviderId,
        string assessmentCenterName,
        DateTime assessmentDate,
        TimeSpan scheduledStartTime,
        string? assessorName,
        string? assessorRegNumber,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var app = await db.LearnerTradeTestApplications.FirstOrDefaultAsync(t => t.Id == applicationId);
        if (app == null)
        {
            throw new KeyNotFoundException($"LearnerTradeTestApplication with ID {applicationId} not found.");
        }

        var before = new { app.TrainingProviderId, app.AssessmentCenterName, app.AssessmentDate, app.StatusCode };
        app.TrainingProviderId = trainingProviderId;
        app.AssessmentCenterName = assessmentCenterName;
        app.AssessmentDate = assessmentDate;
        app.ScheduledStartTime = scheduledStartTime;
        app.AssessorName = assessorName;
        app.AssessorRegistrationNumber = assessorRegNumber;
        app.StatusCode = "TradeCenterAllocated";
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        _audit.LogAction(db, "LearnerTradeTestApplication", app.Id, "ScheduleTradeTest", currentUsername, before, app);
        await db.SaveChangesAsync();

        return app;
    }

    public async Task<LearnerTradeTestApplication> SubmitToNambForSerialAsync(
        int applicationId,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var app = await db.LearnerTradeTestApplications.FirstOrDefaultAsync(t => t.Id == applicationId);
        if (app == null)
        {
            throw new KeyNotFoundException($"LearnerTradeTestApplication with ID {applicationId} not found.");
        }

        var before = new { app.NambSubmissionDate, app.StatusCode };
        app.NambSubmissionDate = DateTime.UtcNow;
        app.NambDecisionStatusCode = "Pending";
        app.StatusCode = "AwaitingNambApproval";
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        _audit.LogAction(db, "LearnerTradeTestApplication", app.Id, "SubmitToNamb", currentUsername, before, app);
        await db.SaveChangesAsync();

        return app;
    }

    public async Task<LearnerTradeTestApplication> RecordNambDecisionAsync(
        int applicationId,
        string decisionStatusCode,
        string nambOfficerName,
        string? nambSerialNumber = null,
        string? decisionNotes = null,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var app = await db.LearnerTradeTestApplications.FirstOrDefaultAsync(t => t.Id == applicationId);
        if (app == null)
        {
            throw new KeyNotFoundException($"LearnerTradeTestApplication with ID {applicationId} not found.");
        }

        var before = new { app.NambDecisionStatusCode, app.NambSerialNumber, app.StatusCode };
        app.NambDecisionStatusCode = decisionStatusCode;
        app.NambApprovalDate = DateTime.UtcNow;
        if (!string.IsNullOrEmpty(nambSerialNumber))
        {
            app.NambSerialNumber = nambSerialNumber;
        }

        if (decisionStatusCode == "Approved")
        {
            app.StatusCode = "Assessing";
            if (string.IsNullOrEmpty(app.NambSerialNumber))
            {
                app.NambSerialNumber = $"NAMB-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..8].ToUpper()}";
            }
        }
        else
        {
            app.StatusCode = "Rejected";
        }

        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        db.NambDecisionHistories.Add(new NambDecisionHistory
        {
            LearnerTradeTestApplicationId = applicationId,
            NambOfficerName = nambOfficerName,
            DecisionStatusCode = decisionStatusCode,
            DecisionNotes = decisionNotes,
            DecisionDate = DateTime.UtcNow,
            NambBatchReference = app.NambSerialNumber,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        });

        _audit.LogAction(db, "LearnerTradeTestApplication", app.Id, "RecordNambDecision", currentUsername, before, app);
        await db.SaveChangesAsync();

        return app;
    }

    public async Task<LearnerTradeTestApplication> RecordTaskResultsAsync(
        int applicationId,
        List<TradeTestTask> taskResults,
        string? assessorComments,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var app = await db.LearnerTradeTestApplications
            .Include(t => t.Tasks)
            .FirstOrDefaultAsync(t => t.Id == applicationId);

        if (app == null)
        {
            throw new KeyNotFoundException($"LearnerTradeTestApplication with ID {applicationId} not found.");
        }

        // Remove old tasks if any
        if (app.Tasks.Any())
        {
            db.TradeTestTasks.RemoveRange(app.Tasks);
        }

        bool allPassed = true;
        foreach (var task in taskResults)
        {
            task.LearnerTradeTestApplicationId = applicationId;
            task.PercentageAchieved = task.TotalMarksAvailable > 0
                ? Math.Round((task.MarksObtained / task.TotalMarksAvailable) * 100m, 2)
                : 0m;
            task.IsCompetent = task.PercentageAchieved >= task.PassPercentage;
            if (!task.IsCompetent) allPassed = false;

            task.CreatedAt = DateTime.UtcNow;
            task.CreatedBy = currentUsername;
            db.TradeTestTasks.Add(task);
        }

        var before = new { app.CompetencyStatusCode, app.StatusCode };
        app.CompetencyStatusCode = allPassed ? "Competent" : "NotYetCompetent";
        app.StatusCode = allPassed ? "Competent" : "Assessed";
        if (assessorComments != null) app.Notes = assessorComments;
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        _audit.LogAction(db, "LearnerTradeTestApplication", app.Id, "RecordTaskResults", currentUsername, before, app);
        await db.SaveChangesAsync();

        return app;
    }

    public async Task<LearnerTradeTestApplication> FinalizeTradeTestAndIssueCertificateAsync(
        int applicationId,
        string? moderatorName,
        string? moderatorRegNumber,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var app = await db.LearnerTradeTestApplications
            .Include(t => t.CompanyLearner)
            .FirstOrDefaultAsync(t => t.Id == applicationId);

        if (app == null)
        {
            throw new KeyNotFoundException($"LearnerTradeTestApplication with ID {applicationId} not found.");
        }

        if (app.CompetencyStatusCode != "Competent")
        {
            throw new InvalidOperationException("Cannot issue artisan trade certificate for a candidate who is not yet competent.");
        }

        var before = new { app.SerialCertificateNumber, app.StatusCode };
        app.SerialCertificateNumber = $"CERT-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..8].ToUpper()}";
        app.CertificateIssueDate = DateTime.UtcNow;
        app.ModeratorName = moderatorName;
        app.ModeratorRegistrationNumber = moderatorRegNumber;
        app.StatusCode = "Certified";
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        // Mark learner contract completed
        var learner = await db.CompanyLearners.FirstOrDefaultAsync(l => l.Id == app.CompanyLearnerId);
        if (learner != null)
        {
            learner.EnrolmentStatusCode = "Completed";
            learner.CompletionDate = DateTime.UtcNow;
            learner.ModifiedAt = DateTime.UtcNow;
            learner.ModifiedBy = currentUsername;
        }

        _audit.LogAction(db, "LearnerTradeTestApplication", app.Id, "IssueArtisanTradeCertificate", currentUsername, before, app);
        await db.SaveChangesAsync();

        return app;
    }

    public async Task<LearnerTradeTestApplication?> GetApplicationByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.LearnerTradeTestApplications
            .Include(t => t.Person)
            .Include(t => t.Organisation)
            .Include(t => t.TrainingProvider)
            .Include(t => t.CompanyLearner)
            .Include(t => t.Tasks)
            .Include(t => t.ExperienceDetails)
            .Include(t => t.TrainingDetails)
            .Include(t => t.NambHistories)
            .FirstOrDefaultAsync(t => t.Id == id);
    }

    public async Task<List<LearnerTradeTestApplication>> GetApplicationsAsync(string? statusCode = null, string? tradeTitle = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.LearnerTradeTestApplications
            .Include(t => t.Person)
            .Include(t => t.Organisation)
            .Include(t => t.TrainingProvider)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(statusCode))
        {
            query = query.Where(t => t.StatusCode == statusCode);
        }

        if (!string.IsNullOrWhiteSpace(tradeTitle))
        {
            query = query.Where(t => t.TradeTitle.Contains(tradeTitle));
        }

        return await query.OrderByDescending(t => t.CreatedAt).ToListAsync();
    }
}
