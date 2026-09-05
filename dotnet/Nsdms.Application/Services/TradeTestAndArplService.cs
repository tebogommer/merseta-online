using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Common.Utilities;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class TradeTestAndArplService : ITradeTestAndArplService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly AuditService _audit;
    private readonly INotificationService? _notifications;

    public TradeTestAndArplService(
        INsdmsDbContextFactory contextFactory,
        AuditService audit,
        INotificationService? notifications = null)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _notifications = notifications;
    }

    public async Task<LearnerTradeTestApplication> CreateTradeTestApplicationAsync(
        int companyLearnerId,
        string tradeTitle,
        string applicationTypeCode = "Section26D",
        int attemptNumber = 1,
        string? tradeOfoCode = null,
        int? preferredTrainingCenterId = null,
        int? qualificationId = null,
        string? specialisation = null,
        bool hasAttemptedPreviously = false,
        string? previousCenterName = null,
        DateTime? previousAttemptDate = null,
        int? previousAttemptsCount = null,
        ArplQualifyingCategory? qualifyingCategory = null,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        if (attemptNumber > 3)
        {
            throw new InvalidOperationException("Statutory maximum of 3 trade test attempts exceeded per Section 5.");
        }

        var learner = await db.CompanyLearners
            .Include(l => l.Person)
            .Include(l => l.Organisation)
            .FirstOrDefaultAsync(l => l.Id == companyLearnerId);

        if (learner == null)
        {
            throw new KeyNotFoundException($"CompanyLearner with ID {companyLearnerId} not found.");
        }

        bool requiresToolkit = ArplTradeValidator.RequiresToolkit(tradeTitle);

        var app = new LearnerTradeTestApplication
        {
            CompanyLearnerId = companyLearnerId,
            PersonId = learner.PersonId,
            OrganisationId = learner.OrganisationId,
            TrainingProviderId = preferredTrainingCenterId,
            PreferredTradeTestCenterId = preferredTrainingCenterId,
            QualificationId = qualificationId,
            Specialisation = specialisation,
            HasAttemptedTradeTestPreviously = hasAttemptedPreviously,
            PreviousAssessmentCenterName = previousCenterName,
            PreviousAttemptDate = previousAttemptDate,
            PreviousAttemptsCount = previousAttemptsCount,
            QualifyingCategory = qualifyingCategory,
            RequiresToolkit = requiresToolkit,
            ApplicationNumber = $"TT-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}",
            TradeTitle = tradeTitle,
            TradeOfoCode = tradeOfoCode,
            ApplicationTypeCode = applicationTypeCode,
            AttemptNumber = attemptNumber,
            LearnerReadinessDate = DateTime.UtcNow,
            LearnerSubmissionDate = DateTime.UtcNow,
            StatusCode = "Submitted",
            CompetencyStatusCode = "Pending",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.LearnerTradeTestApplications.Add(app);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "LearnerTradeTestApplication", app.Id, "CreateTradeTestApplication", currentUsername, null, app);
        await db.SaveChangesAsync();

        // Trigger Notification #1: Application Submitted to CLA
        if (_notifications != null)
        {
            try
            {
                await _notifications.SendNotificationAsync(
                    recipientUsername: "CLA_REGIONAL_OFFICE",
                    recipientRole: "CLA",
                    title: $"New ARPL Application Submitted: {app.ApplicationNumber}",
                    message: $"New {applicationTypeCode} trade test application for {tradeTitle} submitted and queued for CLA review.",
                    actionUrl: $"/trade-tests/{app.Id}",
                    notificationType: "ARPL_SUBMISSION",
                    severity: "Info",
                    actor: currentUsername);
            }
            catch { /* Notification failure should not abort transaction */ }
        }

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

    public async Task<LearnerTradeTestApplication> SubmitClaRecommendationAsync(
        int applicationId,
        bool recommend,
        string? rejectionReason = null,
        string? comments = null,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var app = await db.LearnerTradeTestApplications
            .Include(t => t.Person)
            .FirstOrDefaultAsync(t => t.Id == applicationId);

        if (app == null)
        {
            throw new KeyNotFoundException($"LearnerTradeTestApplication with ID {applicationId} not found.");
        }

        var before = new { app.ClaRecommendationStatus, app.ClaRecommendationDate, app.StatusCode };

        app.ClaRecommendationStatus = recommend ? "Recommended" : "Rejected";
        app.ClaRecommendationDate = DateTime.UtcNow;
        app.ClaUserId = currentUsername;
        app.ClaRejectionReason = rejectionReason;
        app.StatusCode = recommend ? "RecommendedApplication" : "RejectedForResubmission";
        if (!string.IsNullOrWhiteSpace(comments))
        {
            app.Notes = string.IsNullOrWhiteSpace(app.Notes) ? comments : $"{app.Notes}\n[CLA {DateTime.UtcNow:yyyy-MM-dd}]: {comments}";
        }
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        _audit.LogAction(db, "LearnerTradeTestApplication", app.Id, "SubmitClaRecommendation", currentUsername, before, app);
        await db.SaveChangesAsync();

        if (_notifications != null)
        {
            try
            {
                if (recommend)
                {
                    await _notifications.SendNotificationAsync(
                        recipientUsername: "QA_REGIONAL_OFFICE",
                        recipientRole: "QA",
                        title: $"ARPL Application Recommended: {app.ApplicationNumber}",
                        message: $"Application {app.ApplicationNumber} for {app.TradeTitle} has been recommended by CLA and awaits QA approval.",
                        actionUrl: $"/trade-tests/{app.Id}",
                        notificationType: "CLA_RECOMMENDATION",
                        severity: "Info",
                        actor: currentUsername);
                }
                else
                {
                    await _notifications.SendNotificationAsync(
                        recipientUsername: app.Person?.EmailAddress ?? "APPLICANT",
                        recipientRole: "Learner",
                        title: $"ARPL Application Returned for Resubmission: {app.ApplicationNumber}",
                        message: $"Application {app.ApplicationNumber} was returned by CLA: {rejectionReason}",
                        actionUrl: $"/trade-tests/{app.Id}",
                        notificationType: "CLA_REJECTION",
                        severity: "Warning",
                        actor: currentUsername);
                }
            }
            catch { /* non-blocking */ }
        }

        return app;
    }

    public async Task<LearnerTradeTestApplication> SubmitQaApprovalAsync(
        int applicationId,
        bool approve,
        int? stampedDocumentAttachmentId,
        bool isFinalRejection,
        string? rejectionReason,
        string? comments,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var app = await db.LearnerTradeTestApplications
            .Include(t => t.Person)
            .FirstOrDefaultAsync(t => t.Id == applicationId);

        if (app == null)
        {
            throw new KeyNotFoundException($"LearnerTradeTestApplication with ID {applicationId} not found.");
        }

        var before = new { app.QaApprovalStatus, app.QaApprovalDate, app.StatusCode, app.TradeTestSerialNumber };

        app.QaApprovalStatus = approve ? "Approved" : "Rejected";
        app.QaApprovalDate = DateTime.UtcNow;
        app.QaUserId = currentUsername;
        app.QaSignedApplicationDocumentAttachmentId = stampedDocumentAttachmentId;
        app.IsFinalRejection = isFinalRejection;
        app.QaRejectionReason = rejectionReason;

        if (approve)
        {
            app.StatusCode = "Registered";
            // Section 4.2.4: System generates Trade Test serial number upon QA approval
            if (string.IsNullOrWhiteSpace(app.TradeTestSerialNumber))
            {
                app.TradeTestSerialNumber = $"TT-SER-{DateTime.UtcNow.Year}-{app.Id:D5}";
            }
        }
        else
        {
            app.StatusCode = isFinalRejection ? "RejectedApplication" : "RejectedForResubmission";
        }

        if (!string.IsNullOrWhiteSpace(comments))
        {
            app.Notes = string.IsNullOrWhiteSpace(app.Notes) ? comments : $"{app.Notes}\n[QA {DateTime.UtcNow:yyyy-MM-dd}]: {comments}";
        }
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        _audit.LogAction(db, "LearnerTradeTestApplication", app.Id, "SubmitQaApproval", currentUsername, before, app);
        await db.SaveChangesAsync();

        if (_notifications != null)
        {
            try
            {
                if (approve)
                {
                    await _notifications.SendNotificationAsync(
                        recipientUsername: app.Person?.EmailAddress ?? "APPLICANT",
                        recipientRole: "Learner",
                        title: $"ARPL Application Approved & Registered: {app.TradeTestSerialNumber}",
                        message: $"Your trade test application {app.ApplicationNumber} has been approved. Assigned Trade Test Serial Number: {app.TradeTestSerialNumber}.",
                        actionUrl: $"/trade-tests/{app.Id}",
                        notificationType: "QA_APPROVAL",
                        severity: "Success",
                        actor: currentUsername);

                    await _notifications.SendNotificationAsync(
                        recipientUsername: "CLA_REGIONAL_OFFICE",
                        recipientRole: "CLA",
                        title: $"ARPL Application Approved: {app.TradeTestSerialNumber}",
                        message: $"Application {app.ApplicationNumber} has been approved by QA. Serial: {app.TradeTestSerialNumber}.",
                        actionUrl: $"/trade-tests/{app.Id}",
                        notificationType: "QA_APPROVAL",
                        severity: "Info",
                        actor: currentUsername);
                }
                else
                {
                    await _notifications.SendNotificationAsync(
                        recipientUsername: app.Person?.EmailAddress ?? "APPLICANT",
                        recipientRole: "Learner",
                        title: isFinalRejection ? $"ARPL Application Rejected: {app.ApplicationNumber}" : $"ARPL Application Returned: {app.ApplicationNumber}",
                        message: $"Application {app.ApplicationNumber} was rejected by QA: {rejectionReason}. Resubmission allowed: {!isFinalRejection}",
                        actionUrl: $"/trade-tests/{app.Id}",
                        notificationType: "QA_REJECTION",
                        severity: isFinalRejection ? "Error" : "Warning",
                        actor: currentUsername);
                }
            }
            catch { /* non-blocking */ }
        }

        return app;
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
            .Include(t => t.Person)
            .FirstOrDefaultAsync(t => t.Id == applicationId);

        if (app == null)
        {
            throw new KeyNotFoundException($"LearnerTradeTestApplication with ID {applicationId} not found.");
        }

        // Section 5 (Page 19) & Use Case Section 4.2.5:
        // Statutory 50% task credit retention: Candidates passing >= 50% of tasks retain credit for max 3 attempts or 18 months.
        // DO NOT delete old tasks! Partition by AttemptNumber to maintain complete statutory audit trail.
        int currentAttempt = app.AttemptNumber > 0 ? app.AttemptNumber : 1;
        var assessmentDate = app.AssessmentDate ?? DateTime.UtcNow;

        // Evaluate task percentages for this attempt
        foreach (var task in taskResults)
        {
            task.LearnerTradeTestApplicationId = applicationId;
            task.AttemptNumber = currentAttempt;
            task.PercentageAchieved = task.TotalMarksAvailable > 0
                ? Math.Round((task.MarksObtained / task.TotalMarksAvailable) * 100m, 2)
                : 0m;
            task.IsCompetent = task.PercentageAchieved >= task.PassPercentage;
            task.CreatedAt = DateTime.UtcNow;
            task.CreatedBy = currentUsername;
        }

        // Check if candidate achieves >= 50% overall pass rate across tasks in this attempt
        var (qualifiesForRetention, passRate, retentionExpiry) = ArplTradeValidator.EvaluateTaskCreditRetention(taskResults, assessmentDate);

        foreach (var task in taskResults)
        {
            if (qualifiesForRetention && task.IsCompetent)
            {
                task.IsRetainedCredit = true;
                task.CreditRetentionExpiryDate = retentionExpiry;
            }
            else
            {
                task.IsRetainedCredit = false;
                task.CreditRetentionExpiryDate = null;
            }

            db.TradeTestTasks.Add(task);
        }

        // Determine if all required tasks are satisfied (either newly passed, or retained from previous attempts within 18 months)
        var activeRetainedCodes = app.Tasks
            .Where(t => t.IsRetainedCredit && t.CreditRetentionExpiryDate >= DateTime.UtcNow && !string.IsNullOrWhiteSpace(t.TaskCode))
            .Select(t => t.TaskCode!)
            .ToHashSet(StringComparer.OrdinalIgnoreCase);

        bool allPassed = taskResults.All(t => t.IsCompetent || (!string.IsNullOrWhiteSpace(t.TaskCode) && activeRetainedCodes.Contains(t.TaskCode)));

        var before = new { app.CompetencyStatusCode, app.StatusCode, app.ResultsUploadDeadlineDate, app.IsSelectedForQaAuditSample };

        app.CompetencyStatusCode = allPassed ? "Competent" : "NotYetCompetent";
        app.StatusCode = allPassed ? "Competent" : "Assessed";

        // Section 4.2.5: Assessment report SLA is 5 working days from test completion
        app.ResultsUploadDeadlineDate = assessmentDate.AddDays(5);

        // Section 4.0 / DFD: 10% QA achievement audit sampling
        app.IsSelectedForQaAuditSample = (app.Id % 10 == 0);

        if (assessorComments != null)
        {
            app.Notes = string.IsNullOrWhiteSpace(app.Notes) ? assessorComments : $"{app.Notes}\n[Assessor {DateTime.UtcNow:yyyy-MM-dd}]: {assessorComments}";
        }
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        _audit.LogAction(db, "LearnerTradeTestApplication", app.Id, "RecordTaskResults", currentUsername, before, app);
        await db.SaveChangesAsync();

        if (_notifications != null)
        {
            try
            {
                await _notifications.SendNotificationAsync(
                    recipientUsername: app.Person?.EmailAddress ?? "APPLICANT",
                    recipientRole: "Learner",
                    title: $"ARPL Trade Test Results Recorded: {app.CompetencyStatusCode}",
                    message: $"Results for {app.TradeTitle} (Attempt #{currentAttempt}): Outcome is {app.CompetencyStatusCode}. Pass rate: {passRate}%. Credit retention qualified: {qualifiesForRetention}.",
                    actionUrl: $"/trade-tests/{app.Id}",
                    notificationType: "TRADE_TEST_RESULT",
                    severity: allPassed ? "Success" : "Warning",
                    actor: currentUsername);

                if (app.IsSelectedForQaAuditSample)
                {
                    await _notifications.SendNotificationAsync(
                        recipientUsername: "QA_REGIONAL_OFFICE",
                        recipientRole: "QA",
                        title: $"QA 10% Audit Sample Selected: {app.ApplicationNumber}",
                        message: $"Application {app.ApplicationNumber} ({app.TradeTitle}) was selected for 10% post-assessment QA audit sampling.",
                        actionUrl: $"/trade-tests/{app.Id}",
                        notificationType: "QA_AUDIT_SAMPLE",
                        severity: "Info",
                        actor: currentUsername);
                }
            }
            catch { /* non-blocking */ }
        }

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

    public async Task<LearnerTradeTestApplication> WithdrawTradeTestApplicationAsync(
        int applicationId,
        string withdrawalReasonCode,
        string justification,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var app = await db.LearnerTradeTestApplications
            .Include(t => t.Person)
            .FirstOrDefaultAsync(t => t.Id == applicationId);

        if (app == null)
        {
            throw new KeyNotFoundException($"LearnerTradeTestApplication with ID {applicationId} not found.");
        }

        var before = new { app.IsWithdrawn, app.WithdrawalDate, app.WithdrawalReason, app.StatusCode };

        var withdrawal = new LearnerTradeTestWithdrawal
        {
            LearnerTradeTestApplicationId = applicationId,
            WithdrawalReasonCode = withdrawalReasonCode,
            Justification = justification,
            WithdrawnByUserId = currentUsername,
            WithdrawnAt = DateTime.UtcNow,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.LearnerTradeTestWithdrawals.Add(withdrawal);

        app.IsWithdrawn = true;
        app.WithdrawalDate = DateTime.UtcNow;
        app.WithdrawalReason = $"{withdrawalReasonCode}: {justification}";
        app.WithdrawalReasonCode = withdrawalReasonCode;
        app.WithdrawalNotes = justification;
        app.WithdrawnAt = DateTime.UtcNow;
        app.WithdrawnBy = currentUsername;
        app.StatusCode = "Withdrawn";
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        _audit.LogAction(db, "LearnerTradeTestApplication", app.Id, "WithdrawTradeTestApplication", currentUsername, before, app);
        await db.SaveChangesAsync();

        if (_notifications != null)
        {
            try
            {
                await _notifications.SendNotificationAsync(
                    recipientUsername: "CLA_REGIONAL_OFFICE",
                    recipientRole: "CLA",
                    title: $"Trade Test Application Withdrawn: {app.ApplicationNumber}",
                    message: $"Application {app.ApplicationNumber} for {app.TradeTitle} was withdrawn: {withdrawalReasonCode}",
                    actionUrl: $"/trade-tests/{app.Id}",
                    notificationType: "APPLICATION_WITHDRAWN",
                    severity: "Warning",
                    actor: currentUsername);
            }
            catch { /* non-blocking */ }
        }

        return app;
    }

    public async Task<List<ArplDocumentChecklist>> SaveDocumentChecklistAsync(
        int applicationId,
        List<(string DocTypeCode, int? AttachmentId)> documents,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var existingDocs = await db.ArplDocumentChecklists
            .Where(d => d.LearnerTradeTestApplicationId == applicationId)
            .ToListAsync();

        var results = new List<ArplDocumentChecklist>();

        foreach (var (docTypeCode, attachmentId) in documents)
        {
            var doc = existingDocs.FirstOrDefault(d => d.DocumentTypeCode == docTypeCode);
            if (doc == null)
            {
                doc = new ArplDocumentChecklist
                {
                    LearnerTradeTestApplicationId = applicationId,
                    DocumentTypeCode = docTypeCode,
                    DocumentTitle = ArplDocumentChecklist.GetDocumentTitle(docTypeCode),
                    DocumentAttachmentId = attachmentId,
                    IsUploaded = attachmentId.HasValue,
                    UploadedAt = attachmentId.HasValue ? DateTime.UtcNow : null,
                    UploadedByUserId = attachmentId.HasValue ? currentUsername : null,
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = currentUsername
                };
                db.ArplDocumentChecklists.Add(doc);
            }
            else
            {
                doc.DocumentAttachmentId = attachmentId;
                doc.IsUploaded = attachmentId.HasValue;
                doc.UploadedAt = attachmentId.HasValue ? DateTime.UtcNow : doc.UploadedAt;
                doc.UploadedByUserId = attachmentId.HasValue ? currentUsername : doc.UploadedByUserId;
                doc.ModifiedAt = DateTime.UtcNow;
                doc.ModifiedBy = currentUsername;
            }
            results.Add(doc);
        }

        await db.SaveChangesAsync();
        return results;
    }

    public async Task<List<ArplDocumentChecklist>> SaveDocumentChecklistAsync(
        int applicationId,
        List<ArplDocumentChecklist> checklist,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var existingDocs = await db.ArplDocumentChecklists
            .Where(d => d.LearnerTradeTestApplicationId == applicationId)
            .ToListAsync();

        var results = new List<ArplDocumentChecklist>();

        foreach (var item in checklist)
        {
            var doc = existingDocs.FirstOrDefault(d => d.DocumentTypeCode == item.DocumentTypeCode);
            if (doc == null)
            {
                doc = new ArplDocumentChecklist
                {
                    LearnerTradeTestApplicationId = applicationId,
                    DocumentTypeCode = item.DocumentTypeCode,
                    DocumentTitle = item.DocumentTitle ?? ArplDocumentChecklist.GetDocumentTitle(item.DocumentTypeCode),
                    AttachmentId = item.AttachmentId,
                    IsUploaded = item.IsUploaded,
                    UploadedByUserId = item.UploadedByUserId ?? currentUsername,
                    UploadedAt = item.UploadedAt ?? DateTime.UtcNow,
                    IsVerified = item.IsVerified,
                    VerifiedByUserId = item.VerifiedByUserId,
                    VerifiedAt = item.VerifiedAt,
                    VerificationComments = item.VerificationComments,
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = currentUsername
                };
                db.ArplDocumentChecklists.Add(doc);
            }
            else
            {
                doc.AttachmentId = item.AttachmentId ?? doc.AttachmentId;
                doc.IsUploaded = item.IsUploaded;
                doc.UploadedByUserId = item.UploadedByUserId ?? doc.UploadedByUserId;
                doc.IsVerified = item.IsVerified;
                doc.VerifiedByUserId = item.VerifiedByUserId ?? doc.VerifiedByUserId;
                doc.VerificationComments = item.VerificationComments ?? doc.VerificationComments;
                doc.ModifiedAt = DateTime.UtcNow;
                doc.ModifiedBy = currentUsername;
            }
            results.Add(doc);
        }

        await db.SaveChangesAsync();
        _audit.LogAction(db, "ArplDocumentChecklist", applicationId, "SaveDocumentChecklist", currentUsername, null, results);
        await db.SaveChangesAsync();

        return results;
    }

    public async Task<CertificateDistributionEvent> LogCertificateDistributionAsync(
        int applicationId,
        string methodCode,
        string? trackingNumber,
        string recipientName,
        string? recipientId,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var app = await db.LearnerTradeTestApplications.FirstOrDefaultAsync(t => t.Id == applicationId);
        if (app == null)
        {
            throw new KeyNotFoundException($"LearnerTradeTestApplication with ID {applicationId} not found.");
        }

        var distEvent = new CertificateDistributionEvent
        {
            LearnerTradeTestApplicationId = applicationId,
            DistributionMethodCode = methodCode,
            TrackingOrWaybillNumber = trackingNumber,
            RecipientName = recipientName,
            RecipientIdNumber = recipientId,
            DispatchedAt = DateTime.UtcNow,
            DispatchedByUserId = currentUsername,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.CertificateDistributionEvents.Add(distEvent);

        app.CertificateDistributedAt = DateTime.UtcNow;
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        _audit.LogAction(db, "CertificateDistributionEvent", applicationId, "LogCertificateDistribution", currentUsername, null, distEvent);
        await db.SaveChangesAsync();

        return distEvent;
    }

    public async Task<CertificateDistributionEvent> LogCertificateDistributionAsync(
        int applicationId,
        string methodCode,
        string? trackingNumber,
        DateTime dispatchedDate,
        string recipientName,
        string? recipientId,
        DateTime? receivedDate,
        string? notes,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var app = await db.LearnerTradeTestApplications.FirstOrDefaultAsync(t => t.Id == applicationId);
        if (app == null)
        {
            throw new KeyNotFoundException($"LearnerTradeTestApplication with ID {applicationId} not found.");
        }

        var distEvent = new CertificateDistributionEvent
        {
            LearnerTradeTestApplicationId = applicationId,
            DistributionMethodCode = methodCode,
            ConsignmentOrTrackingNumber = trackingNumber,
            DispatchedDate = dispatchedDate,
            RecipientName = recipientName,
            RecipientIdNumber = recipientId,
            ReceivedDate = receivedDate,
            DispatchedByUserId = currentUsername,
            Notes = notes,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.CertificateDistributionEvents.Add(distEvent);

        app.CertificateDistributedAt = DateTime.UtcNow;
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        _audit.LogAction(db, "CertificateDistributionEvent", applicationId, "LogCertificateDistribution", currentUsername, null, distEvent);
        await db.SaveChangesAsync();

        return distEvent;
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

    public async Task<LearnerTradeTestApplication?> GetApplicationWithFullDetailsByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.LearnerTradeTestApplications
            .Include(t => t.Person).ThenInclude(p => p.Contact)
            .Include(t => t.Organisation)
            .Include(t => t.TrainingProvider)
            .Include(t => t.CompanyLearner)
            .Include(t => t.Tasks)
            .Include(t => t.ExperienceDetails)
            .Include(t => t.TrainingDetails)
            .Include(t => t.NambHistories)
            .Include(t => t.Withdrawals)
            .Include(t => t.DocumentChecklists)
            .Include(t => t.DistributionEvents)
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
