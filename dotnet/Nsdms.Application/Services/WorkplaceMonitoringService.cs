using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class WorkplaceMonitoringService : IWorkplaceMonitoringService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly AuditService _audit;

    public WorkplaceMonitoringService(INsdmsDbContextFactory contextFactory, AuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<WorkplaceMonitoringSiteVisit> CreateSiteVisitAsync(WorkplaceMonitoringSiteVisit siteVisit, string currentUsername = "SYSTEM")
    {
        // Explicit ContactPersonId validation invariant
        if (siteVisit.ContactPersonId <= 0)
        {
            throw new ArgumentException("A specific Contact Person at the employer site must be selected for the monitoring visit.", nameof(siteVisit.ContactPersonId));
        }

        using var db = await _contextFactory.CreateDbContextAsync();

        var contactExists = await db.People.AnyAsync(p => p.Id == siteVisit.ContactPersonId);
        if (!contactExists)
        {
            throw new KeyNotFoundException($"Contact Person with ID {siteVisit.ContactPersonId} not found in repository.");
        }

        var orgExists = await db.Organisations.AnyAsync(o => o.Id == siteVisit.OrganisationId);
        if (!orgExists)
        {
            throw new KeyNotFoundException($"Organisation with ID {siteVisit.OrganisationId} not found in repository.");
        }

        siteVisit.MonitoringStatusCode = "Draft";
        siteVisit.CreatedAt = DateTime.UtcNow;
        siteVisit.CreatedBy = currentUsername;

        // Auto-populate 10 standard merSETA statutory compliance survey questions if none provided
        if (siteVisit.ComplianceSurveys == null || siteVisit.ComplianceSurveys.Count == 0)
        {
            siteVisit.ComplianceSurveys = GetDefaultComplianceQuestions(currentUsername);
        }

        db.WorkplaceMonitoringSiteVisits.Add(siteVisit);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "WorkplaceMonitoringSiteVisit", siteVisit.Id, "CreateSiteVisit", currentUsername, null, siteVisit);
        await db.SaveChangesAsync();

        return siteVisit;
    }

    public async Task<WorkplaceMonitoringSiteVisit> UpdateSiteVisitAsync(WorkplaceMonitoringSiteVisit siteVisit, string currentUsername = "SYSTEM")
    {
        if (siteVisit.ContactPersonId <= 0)
        {
            throw new ArgumentException("A valid Contact Person must be linked to the monitoring visit.", nameof(siteVisit.ContactPersonId));
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.WorkplaceMonitoringSiteVisits.FindAsync(siteVisit.Id);
        if (existing == null) throw new KeyNotFoundException($"WorkplaceMonitoringSiteVisit with ID {siteVisit.Id} not found.");

        var before = new
        {
            existing.MonitoringDate,
            existing.ContactPersonId,
            existing.MonitoringStatusCode,
            existing.CloUserId,
            existing.CrmUserId
        };

        existing.MonitoringDate = siteVisit.MonitoringDate;
        existing.ContactPersonId = siteVisit.ContactPersonId;
        existing.CloUserId = siteVisit.CloUserId;
        existing.CrmUserId = siteVisit.CrmUserId;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceMonitoringSiteVisit", existing.Id, "UpdateSiteVisit", currentUsername, before, existing);
        await db.SaveChangesAsync();

        return existing;
    }

    public async Task<WorkplaceMonitoringSiteVisit> SubmitForApprovalAsync(int siteVisitId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var visit = await db.WorkplaceMonitoringSiteVisits.FindAsync(siteVisitId);
        if (visit == null) throw new KeyNotFoundException($"WorkplaceMonitoringSiteVisit with ID {siteVisitId} not found.");

        var before = new { visit.MonitoringStatusCode };
        visit.MonitoringStatusCode = "PendingApproval";
        visit.ModifiedAt = DateTime.UtcNow;
        visit.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceMonitoringSiteVisit", visit.Id, "SubmitForApproval", currentUsername, before, visit);
        await db.SaveChangesAsync();

        return visit;
    }

    public async Task<WorkplaceMonitoringSiteVisit> ApproveSiteVisitAsync(int siteVisitId, string comments, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var visit = await db.WorkplaceMonitoringSiteVisits.FindAsync(siteVisitId);
        if (visit == null) throw new KeyNotFoundException($"WorkplaceMonitoringSiteVisit with ID {siteVisitId} not found.");

        var before = new { visit.MonitoringStatusCode, visit.ApprovalDate };
        visit.MonitoringStatusCode = "Approved";
        visit.ApprovalDate = DateTime.UtcNow;
        visit.ApprovedByUserId = currentUsername;
        visit.ApprovalComments = comments;
        visit.SignOffState = true;
        visit.ModifiedAt = DateTime.UtcNow;
        visit.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceMonitoringSiteVisit", visit.Id, "ApproveSiteVisit", currentUsername, before, visit);
        await db.SaveChangesAsync();

        return visit;
    }

    public async Task<WorkplaceMonitoringSiteVisit> FlagNonComplianceAsync(int siteVisitId, string nonComplianceNotes, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var visit = await db.WorkplaceMonitoringSiteVisits.FindAsync(siteVisitId);
        if (visit == null) throw new KeyNotFoundException($"WorkplaceMonitoringSiteVisit with ID {siteVisitId} not found.");

        var before = new { visit.MonitoringStatusCode, visit.NonCompliancesIdentified };
        visit.MonitoringStatusCode = "NonComplianceIdentified";
        visit.NonCompliancesIdentified = true;
        visit.NonComplianceHoldingArea = true;
        visit.NonComplianceSubmittedDate = DateTime.UtcNow;
        visit.NonComplianceNotes = nonComplianceNotes;
        visit.ModifiedAt = DateTime.UtcNow;
        visit.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceMonitoringSiteVisit", visit.Id, "FlagNonCompliance", currentUsername, before, visit);
        await db.SaveChangesAsync();

        return visit;
    }

    public async Task<bool> DeleteSiteVisitAsync(int siteVisitId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var visit = await db.WorkplaceMonitoringSiteVisits.FindAsync(siteVisitId);
        if (visit == null) return false;

        var before = new { visit.Id, visit.MonitoringStatusCode, visit.OrganisationId };
        db.WorkplaceMonitoringSiteVisits.Remove(visit);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "WorkplaceMonitoringSiteVisit", visit.Id, "DeleteSiteVisit", currentUsername, before, null);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<WorkplaceMonitoringSiteVisit?> GetSiteVisitByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.WorkplaceMonitoringSiteVisits
            .Include(v => v.Organisation)
            .Include(v => v.ContactPerson)
            .Include(v => v.ComplianceSurveys.OrderBy(s => s.QuestionNumber))
            .Include(v => v.ActionPlans)
            .Include(v => v.MitigationPlans)
            .Include(v => v.LearnerSurveys)
            .FirstOrDefaultAsync(v => v.Id == id);
    }

    public async Task<List<WorkplaceMonitoringSiteVisit>> GetAllSiteVisitsAsync(int? organisationId = null, string? statusCode = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.WorkplaceMonitoringSiteVisits
            .Include(v => v.Organisation)
            .Include(v => v.ContactPerson)
            .AsQueryable();

        if (organisationId.HasValue && organisationId.Value > 0)
        {
            query = query.Where(v => v.OrganisationId == organisationId.Value);
        }

        if (!string.IsNullOrWhiteSpace(statusCode))
        {
            query = query.Where(v => v.MonitoringStatusCode == statusCode);
        }

        return await query.OrderByDescending(v => v.MonitoringDate).ToListAsync();
    }

    public async Task<List<WorkplaceMonitoringComplianceSurvey>> SaveComplianceSurveysAsync(
        int siteVisitId,
        List<WorkplaceMonitoringComplianceSurvey> surveys,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var visit = await db.WorkplaceMonitoringSiteVisits
            .Include(v => v.ComplianceSurveys)
            .FirstOrDefaultAsync(v => v.Id == siteVisitId);

        if (visit == null) throw new KeyNotFoundException($"WorkplaceMonitoringSiteVisit with ID {siteVisitId} not found.");

        bool anyNonCompliance = false;

        foreach (var survey in surveys)
        {
            survey.NonComplianceRisk = survey.Answer.Equals("No", StringComparison.OrdinalIgnoreCase);
            if (survey.NonComplianceRisk) anyNonCompliance = true;

            var existing = visit.ComplianceSurveys.FirstOrDefault(s => s.Id == survey.Id && survey.Id > 0);
            if (existing != null)
            {
                existing.Answer = survey.Answer;
                existing.NonComplianceRisk = survey.NonComplianceRisk;
                existing.Comments = survey.Comments;
                existing.ModifiedAt = DateTime.UtcNow;
                existing.ModifiedBy = currentUsername;
            }
            else
            {
                survey.WorkplaceMonitoringSiteVisitId = siteVisitId;
                survey.CreatedAt = DateTime.UtcNow;
                survey.CreatedBy = currentUsername;
                db.WorkplaceMonitoringComplianceSurveys.Add(survey);
            }
        }

        if (anyNonCompliance)
        {
            visit.NonCompliancesIdentified = true;
        }

        visit.ModifiedAt = DateTime.UtcNow;
        visit.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceMonitoringComplianceSurvey", siteVisitId, "SaveComplianceSurveys", currentUsername, null, new { Count = surveys.Count, anyNonCompliance });
        await db.SaveChangesAsync();

        return await db.WorkplaceMonitoringComplianceSurveys
            .Where(s => s.WorkplaceMonitoringSiteVisitId == siteVisitId)
            .OrderBy(s => s.QuestionNumber)
            .ToListAsync();
    }

    public async Task<WorkplaceMonitoringActionPlan> AddActionPlanAsync(WorkplaceMonitoringActionPlan actionPlan, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        actionPlan.ActionPlanStatusCode = "Open";
        actionPlan.CreatedAt = DateTime.UtcNow;
        actionPlan.CreatedBy = currentUsername;

        db.WorkplaceMonitoringActionPlans.Add(actionPlan);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "WorkplaceMonitoringActionPlan", actionPlan.Id, "AddActionPlan", currentUsername, null, actionPlan);
        await db.SaveChangesAsync();

        return actionPlan;
    }

    public async Task<WorkplaceMonitoringActionPlan> ResolveActionPlanAsync(int actionPlanId, string resolutionNotes, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var plan = await db.WorkplaceMonitoringActionPlans.FindAsync(actionPlanId);
        if (plan == null) throw new KeyNotFoundException($"WorkplaceMonitoringActionPlan with ID {actionPlanId} not found.");

        var before = new { plan.ActionPlanStatusCode, plan.ResolutionDate };
        plan.ActionPlanStatusCode = "Completed";
        plan.ResolutionDate = DateTime.UtcNow;
        plan.ResolutionNotes = resolutionNotes;
        plan.ModifiedAt = DateTime.UtcNow;
        plan.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceMonitoringActionPlan", plan.Id, "ResolveActionPlan", currentUsername, before, plan);
        await db.SaveChangesAsync();

        return plan;
    }

    public async Task<WorkplaceMonitoringMitigationPlan> AddMitigationPlanAsync(WorkplaceMonitoringMitigationPlan mitigationPlan, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        mitigationPlan.MitigationStatusCode = "Pending";
        mitigationPlan.CreatedAt = DateTime.UtcNow;
        mitigationPlan.CreatedBy = currentUsername;

        db.WorkplaceMonitoringMitigationPlans.Add(mitigationPlan);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "WorkplaceMonitoringMitigationPlan", mitigationPlan.Id, "AddMitigationPlan", currentUsername, null, mitigationPlan);
        await db.SaveChangesAsync();

        return mitigationPlan;
    }

    public async Task<WorkplaceMonitoringLearnerSurvey> AddLearnerSurveyAsync(WorkplaceMonitoringLearnerSurvey survey, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        survey.CreatedAt = DateTime.UtcNow;
        survey.CreatedBy = currentUsername;

        db.WorkplaceMonitoringLearnerSurveys.Add(survey);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "WorkplaceMonitoringLearnerSurvey", survey.Id, "AddLearnerSurvey", currentUsername, null, survey);
        await db.SaveChangesAsync();

        return survey;
    }

    private static List<WorkplaceMonitoringComplianceSurvey> GetDefaultComplianceQuestions(string user)
    {
        var questions = new (int Num, string Cat, string Text)[]
        {
            (1, "Toolbox", "Does the employer provide the learner with required toolboxes & trade instruments?"),
            (2, "Training", "Is the learner exposed to the full registered curriculum and designated trade work processes?"),
            (3, "Mentor", "Are certified workplace mentors assigned to the learner in the prescribed ratio?"),
            (4, "PPE", "Does the employer supply all required Personal Protective Equipment (PPE) without deductions?"),
            (5, "Wages", "Are prescribed bargaining council or statutory minimum learner allowances/wages paid on time?"),
            (6, "Contracts", "Is the tripartite learner contract legally compliant and registered with merSETA?"),
            (7, "Training", "Are structured logbooks reviewed and signed off weekly by the supervisor/mentor?"),
            (8, "HealthAndSafety", "Does the workplace maintain compliant Occupational Health and Safety (OHS) standards?"),
            (9, "Training", "Is there structured access to accredited training provider theory facilities?"),
            (10, "SDF", "Has the Skills Development Facilitator (SDF) conducted statutory quarterly progress reviews?")
        };

        return questions.Select(q => new WorkplaceMonitoringComplianceSurvey
        {
            QuestionNumber = q.Num,
            SurveyCategory = q.Cat,
            QuestionText = q.Text,
            Answer = "Yes",
            NonComplianceRisk = false,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = user
        }).ToList();
    }
}
