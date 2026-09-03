using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class AssessorRenewalStatusDto
{
    public int EtqaAssessorId { get; set; }
    public string RegistrationNumber { get; set; } = string.Empty;
    public string PractitionerName { get; set; } = string.Empty;
    public string Designation { get; set; } = "Assessor";
    public DateTime RegistrationStartDate { get; set; }
    public DateTime RegistrationEndDate { get; set; }
    public bool IsExpired => DateTime.UtcNow > RegistrationEndDate;
    public int DaysRemaining => (int)(RegistrationEndDate - DateTime.UtcNow).TotalDays;
    public bool IsDueForRenewal => DaysRemaining <= 180; // Within 6 months
    public string CurrentStatusCode { get; set; } = "Active";
    public int ActiveScopeCount { get; set; }
    public List<AssessorReRegistrationApplication> PastApplications { get; set; } = new();
}

public class InitiateReRegistrationRequest
{
    public int EtqaAssessorId { get; set; }
    public string ApplicationTypeCode { get; set; } = "ReRegistration";
    public string? CpdPortfolioSummary { get; set; }
    public List<string>? ConfirmedUnitStandardCodes { get; set; }
}

public class LogCpdActivityRequest
{
    public int ApplicationId { get; set; }
    public DateTime ActivityDate { get; set; } = DateTime.UtcNow;
    public string ActivityTitle { get; set; } = string.Empty;
    public string ActivityCategory { get; set; } = "IndustryPractice";
    public int PointsClaimed { get; set; } = 5;
    public string? EvidenceDocumentRef { get; set; }
}

public class AdjudicateReRegistrationRequest
{
    public int ApplicationId { get; set; }
    public bool Approved { get; set; }
    public string CommitteeDecisionNumber { get; set; } = string.Empty;
    public string? AdjudicationNotes { get; set; }
    public string AdjudicatorName { get; set; } = "ETQA Committee";
}

public interface IAssessorReRegistrationService
{
    Task<AssessorRenewalStatusDto> GetAssessorRenewalStatusAsync(int assessorId);
    Task<AssessorReRegistrationApplication> InitiateReRegistrationApplicationAsync(InitiateReRegistrationRequest request, string currentUsername = "SYSTEM");
    Task<AssessorCpdActivity> LogCpdActivityAsync(LogCpdActivityRequest request, string currentUsername = "SYSTEM");
    Task<AssessorReRegistrationApplication> SubmitApplicationForReviewAsync(int applicationId, string currentUsername = "SYSTEM");
    Task<AssessorReRegistrationApplication> AdjudicateApplicationAsync(AdjudicateReRegistrationRequest request, string currentUsername = "SYSTEM");
    Task<List<AssessorReRegistrationApplication>> GetApplicationsForAssessorAsync(int assessorId);
    Task<AssessorReRegistrationApplication?> GetApplicationDetailsAsync(int applicationId);
}

public class AssessorReRegistrationService : IAssessorReRegistrationService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public AssessorReRegistrationService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<AssessorRenewalStatusDto> GetAssessorRenewalStatusAsync(int assessorId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var assessor = await db.EtqaAssessors
            .Include(a => a.Person)
            .Include(a => a.Scopes)
            .Include(a => a.ReRegistrationApplications)
                .ThenInclude(r => r.CpdActivities)
            .FirstOrDefaultAsync(a => a.Id == assessorId);

        if (assessor == null)
            throw new KeyNotFoundException($"EtqaAssessor with ID {assessorId} not found.");

        return new AssessorRenewalStatusDto
        {
            EtqaAssessorId = assessor.Id,
            RegistrationNumber = assessor.RegistrationNumber,
            PractitionerName = assessor.Person?.FullName ?? "Registered Practitioner",
            Designation = assessor.EtqaRole,
            RegistrationStartDate = assessor.StartDate,
            RegistrationEndDate = assessor.EndDate,
            CurrentStatusCode = assessor.RegistrationStatusCode ?? "Active",
            ActiveScopeCount = assessor.Scopes.Count,
            PastApplications = assessor.ReRegistrationApplications.OrderByDescending(r => r.CreatedAt).ToList()
        };
    }

    public async Task<AssessorReRegistrationApplication> InitiateReRegistrationApplicationAsync(InitiateReRegistrationRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var assessor = await db.EtqaAssessors
            .Include(a => a.Scopes)
            .FirstOrDefaultAsync(a => a.Id == request.EtqaAssessorId);

        if (assessor == null)
            throw new KeyNotFoundException($"EtqaAssessor with ID {request.EtqaAssessorId} not found.");

        var existingCount = await db.AssessorReRegistrationApplications
            .CountAsync(a => a.EtqaAssessorId == assessor.Id);

        string refNumber = $"REG-{DateTime.UtcNow.Year}-ASS-{assessor.Id:D4}-{(existingCount + 1):D2}";
        var currentExpiry = assessor.EndDate > DateTime.MinValue ? assessor.EndDate : DateTime.UtcNow;
        var newExpiry = currentExpiry.AddYears(3);

        var scopesJson = request.ConfirmedUnitStandardCodes != null && request.ConfirmedUnitStandardCodes.Count > 0
            ? JsonSerializer.Serialize(request.ConfirmedUnitStandardCodes)
            : JsonSerializer.Serialize(assessor.Scopes.Select(s => s.SaqaQualificationId.ToString()).Where(s => !string.IsNullOrEmpty(s)));

        var app = new AssessorReRegistrationApplication
        {
            EtqaAssessorId = assessor.Id,
            ApplicationReferenceNumber = refNumber,
            ApplicationTypeCode = request.ApplicationTypeCode,
            CurrentExpirationDate = currentExpiry,
            ProposedNewExpirationDate = newExpiry,
            CpdPointsAccumulated = 0,
            CpdPortfolioSummary = request.CpdPortfolioSummary,
            ScopeConfirmationJson = scopesJson,
            ReviewStatusCode = "Draft",
            CreatedBy = currentUsername
        };

        db.AssessorReRegistrationApplications.Add(app);
        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorReRegistrationApplication", app.Id, "InitiateReRegistration", currentUsername, new
        {
            app.ApplicationReferenceNumber,
            app.CurrentExpirationDate,
            app.ProposedNewExpirationDate
        });

        return app;
    }

    public async Task<AssessorCpdActivity> LogCpdActivityAsync(LogCpdActivityRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.AssessorReRegistrationApplications
            .Include(a => a.CpdActivities)
            .FirstOrDefaultAsync(a => a.Id == request.ApplicationId);

        if (app == null)
            throw new KeyNotFoundException($"AssessorReRegistrationApplication with ID {request.ApplicationId} not found.");

        var activity = new AssessorCpdActivity
        {
            AssessorReRegistrationApplicationId = app.Id,
            ActivityDate = request.ActivityDate,
            ActivityTitle = request.ActivityTitle,
            ActivityCategory = request.ActivityCategory,
            PointsClaimed = request.PointsClaimed,
            PointsApproved = request.PointsClaimed, // Auto-accredit in standard submission
            EvidenceDocumentRef = request.EvidenceDocumentRef,
            CreatedBy = currentUsername
        };

        db.AssessorCpdActivities.Add(activity);
        
        // Update accumulated points on parent application
        app.CpdPointsAccumulated += activity.PointsApproved;
        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorCpdActivity", activity.Id, "LogCpdActivity", currentUsername, new
        {
            activity.ActivityTitle,
            activity.PointsApproved,
            TotalPoints = app.CpdPointsAccumulated
        });

        return activity;
    }

    public async Task<AssessorReRegistrationApplication> SubmitApplicationForReviewAsync(int applicationId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.AssessorReRegistrationApplications
            .Include(a => a.CpdActivities)
            .FirstOrDefaultAsync(a => a.Id == applicationId);

        if (app == null)
            throw new KeyNotFoundException($"AssessorReRegistrationApplication with ID {applicationId} not found.");

        app.ReviewStatusCode = "CommitteeReview";
        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorReRegistrationApplication", app.Id, "SubmitForReview", currentUsername, new
        {
            app.ApplicationReferenceNumber,
            app.CpdPointsAccumulated,
            ActivityCount = app.CpdActivities.Count
        });

        return app;
    }

    public async Task<AssessorReRegistrationApplication> AdjudicateApplicationAsync(AdjudicateReRegistrationRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.AssessorReRegistrationApplications
            .Include(a => a.EtqaAssessor)
            .FirstOrDefaultAsync(a => a.Id == request.ApplicationId);

        if (app == null)
            throw new KeyNotFoundException($"AssessorReRegistrationApplication with ID {request.ApplicationId} not found.");

        app.AdjudicationDate = DateTime.UtcNow;
        app.AdjudicatedByUserId = currentUsername;
        app.AdjudicationNotes = request.AdjudicationNotes;
        app.CommitteeDecisionNumber = request.CommitteeDecisionNumber;

        if (request.Approved)
        {
            app.ReviewStatusCode = "Approved";

            // Generate SHA-256 digital security seal
            string rawSeal = $"ETQA-ASSESSOR-RENEWAL:{app.Id}:{app.EtqaAssessorId}:{request.CommitteeDecisionNumber}:{app.ProposedNewExpirationDate:yyyyMMdd}";
            using var sha = SHA256.Create();
            byte[] hashBytes = sha.ComputeHash(Encoding.UTF8.GetBytes(rawSeal));
            app.DigitalSecuritySeal = Convert.ToHexString(hashBytes).ToLowerInvariant();

            // Extend parent assessor registration by 3 years
            if (app.EtqaAssessor != null)
            {
                app.EtqaAssessor.EndDate = app.ProposedNewExpirationDate;
                app.EtqaAssessor.RegistrationStatusCode = "Active";
                app.EtqaAssessor.IsActive = true;
                app.EtqaAssessor.EtqeDecisionNumber = request.CommitteeDecisionNumber;
            }
        }
        else
        {
            app.ReviewStatusCode = "Rejected";
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorReRegistrationApplication", app.Id, "AdjudicateApplication", currentUsername, new
        {
            app.ReviewStatusCode,
            app.CommitteeDecisionNumber,
            app.DigitalSecuritySeal,
            NewExpiry = app.ProposedNewExpirationDate
        });

        return app;
    }

    public async Task<List<AssessorReRegistrationApplication>> GetApplicationsForAssessorAsync(int assessorId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.AssessorReRegistrationApplications
            .Include(a => a.CpdActivities)
            .Where(a => a.EtqaAssessorId == assessorId)
            .OrderByDescending(a => a.CreatedAt)
            .ToListAsync();
    }

    public async Task<AssessorReRegistrationApplication?> GetApplicationDetailsAsync(int applicationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.AssessorReRegistrationApplications
            .Include(a => a.EtqaAssessor)
                .ThenInclude(ass => ass!.Person)
            .Include(a => a.EtqaAssessor)
                .ThenInclude(ass => ass!.Scopes)
            .Include(a => a.CpdActivities)
            .FirstOrDefaultAsync(a => a.Id == applicationId);
    }
}
