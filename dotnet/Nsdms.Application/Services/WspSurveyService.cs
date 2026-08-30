using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IWspSurveyService
{
    Task<List<WspStrategicSkillsGap>> GetSkillsGapsAsync(int wspId);
    Task<WspStrategicSkillsGap> AddSkillsGapAsync(int wspId, string occupationTitle, string? ofoCode, string description, string? cause, string? intervention, string priority, int targetLearners, decimal estimatedBudget, string actor);
    Task<bool> DeleteSkillsGapAsync(int id, string actor);

    Task<List<WspTrainingImpactSurvey>> GetTrainingImpactSurveysAsync(int wspId);
    Task<WspTrainingImpactSurvey> SaveTrainingImpactSurveyAsync(int wspId, string category, string question, int rating, string? notes, string? evidenceDocUrl, string actor);
    Task<bool> InitializeStandardImpactQuestionsAsync(int wspId, string actor);

    Task<List<WspStrategicPriority>> GetStrategicPrioritiesAsync(int wspId);
    Task<WspStrategicPriority> AddStrategicPriorityAsync(int wspId, string priorityCode, string objective, string alignment, decimal budget, bool alignedNsdp, string actor);
    Task<bool> DeleteStrategicPriorityAsync(int id, string actor);
}

public class WspSurveyService : IWspSurveyService
{
    private readonly INsdmsDbContextFactory _dbFactory;
    private readonly AuditService _auditService;

    public WspSurveyService(INsdmsDbContextFactory dbFactory, AuditService auditService)
    {
        _dbFactory = dbFactory;
        _auditService = auditService;
    }

    public async Task<List<WspStrategicSkillsGap>> GetSkillsGapsAsync(int wspId)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        return await db.WspStrategicSkillsGaps
            .AsNoTracking()
            .Where(s => s.WspId == wspId && s.IsActive)
            .OrderByDescending(s => s.Id)
            .ToListAsync();
    }

    public async Task<WspStrategicSkillsGap> AddSkillsGapAsync(int wspId, string occupationTitle, string? ofoCode, string description, string? cause, string? intervention, string priority, int targetLearners, decimal estimatedBudget, string actor)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var gap = new WspStrategicSkillsGap
        {
            WspId = wspId,
            OccupationTitle = occupationTitle,
            OfoCode = ofoCode,
            SkillGapDescription = description,
            CauseOfGap = cause,
            PlannedIntervention = intervention,
            PriorityLevel = priority,
            TargetLearnerCount = Math.Max(1, targetLearners),
            EstimatedBudget = estimatedBudget,
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = actor,
            ModifiedAt = DateTime.UtcNow,
            ModifiedBy = actor
        };

        db.WspStrategicSkillsGaps.Add(gap);
        await db.SaveChangesAsync();

        _auditService.LogAction(db, "WspStrategicSkillsGap", gap.Id, "CreateSkillsGap", actor, null, gap);
        await db.SaveChangesAsync();

        return gap;
    }

    public async Task<bool> DeleteSkillsGapAsync(int id, string actor)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var gap = await db.WspStrategicSkillsGaps.FindAsync(id);
        if (gap == null) return false;

        gap.IsActive = false;
        gap.ModifiedAt = DateTime.UtcNow;
        gap.ModifiedBy = actor;

        _auditService.LogAction(db, "WspStrategicSkillsGap", gap.Id, "DeleteSkillsGap", actor, new { IsActive = true }, new { IsActive = false });
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<List<WspTrainingImpactSurvey>> GetTrainingImpactSurveysAsync(int wspId)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        return await db.WspTrainingImpactSurveys
            .AsNoTracking()
            .Where(s => s.WspId == wspId)
            .OrderBy(s => s.Id)
            .ToListAsync();
    }

    public async Task<WspTrainingImpactSurvey> SaveTrainingImpactSurveyAsync(int wspId, string category, string question, int rating, string? notes, string? evidenceDocUrl, string actor)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var existing = await db.WspTrainingImpactSurveys
            .FirstOrDefaultAsync(s => s.WspId == wspId && s.SurveyCategory == category && s.QuestionText == question);

        if (existing != null)
        {
            var oldVal = new { existing.RatingScore, existing.QualitativeImpactNotes };
            existing.RatingScore = Math.Clamp(rating, 1, 5);
            existing.QualitativeImpactNotes = notes;
            existing.EvidenceDocumentUrl = evidenceDocUrl ?? existing.EvidenceDocumentUrl;
            existing.ModifiedAt = DateTime.UtcNow;
            existing.ModifiedBy = actor;

            _auditService.LogAction(db, "WspTrainingImpactSurvey", existing.Id, "UpdateImpactRating", actor, oldVal, existing);
            await db.SaveChangesAsync();
            return existing;
        }

        var survey = new WspTrainingImpactSurvey
        {
            WspId = wspId,
            SurveyCategory = category,
            QuestionText = question,
            RatingScore = Math.Clamp(rating, 1, 5),
            QualitativeImpactNotes = notes,
            EvidenceDocumentUrl = evidenceDocUrl,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = actor,
            ModifiedAt = DateTime.UtcNow,
            ModifiedBy = actor
        };

        db.WspTrainingImpactSurveys.Add(survey);
        await db.SaveChangesAsync();

        _auditService.LogAction(db, "WspTrainingImpactSurvey", survey.Id, "CreateImpactSurvey", actor, null, survey);
        await db.SaveChangesAsync();

        return survey;
    }

    public async Task<bool> InitializeStandardImpactQuestionsAsync(int wspId, string actor)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var count = await db.WspTrainingImpactSurveys.CountAsync(s => s.WspId == wspId);
        if (count > 0) return false;

        var standardQuestions = new List<(string Category, string Question)>
        {
            ("Productivity", "To what extent did completed training improve team throughput and product quality?"),
            ("Health & Safety", "How effectively did occupational health and safety interventions reduce workplace incidents?"),
            ("Innovation & 4IR", "To what degree did digital and automation skills programmes assist technology adoption?"),
            ("Transformation", "How significantly did bursaries and apprenticeships contribute to employment equity objectives?"),
            ("Staff Retention", "What impact did structured skills development have on artisan and technician retention rates?")
        };

        foreach (var (cat, q) in standardQuestions)
        {
            db.WspTrainingImpactSurveys.Add(new WspTrainingImpactSurvey
            {
                WspId = wspId,
                SurveyCategory = cat,
                QuestionText = q,
                RatingScore = 4,
                QualitativeImpactNotes = "Positive measurable impact noted across operational departments.",
                CreatedAt = DateTime.UtcNow,
                CreatedBy = actor,
                ModifiedAt = DateTime.UtcNow,
                ModifiedBy = actor
            });
        }

        await db.SaveChangesAsync();
        _auditService.LogAction(db, "WspSubmission", wspId, "InitializeImpactSurvey", actor, null, new { QuestionCount = standardQuestions.Count });
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<List<WspStrategicPriority>> GetStrategicPrioritiesAsync(int wspId)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        return await db.WspStrategicPriorities
            .AsNoTracking()
            .Where(p => p.WspId == wspId)
            .OrderBy(p => p.Id)
            .ToListAsync();
    }

    public async Task<WspStrategicPriority> AddStrategicPriorityAsync(int wspId, string priorityCode, string objective, string alignment, decimal budget, bool alignedNsdp, string actor)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var priority = new WspStrategicPriority
        {
            WspId = wspId,
            PriorityCode = priorityCode,
            StrategicObjective = objective,
            AlignmentDescription = alignment,
            AllocatedBudget = budget,
            IsAlignedWithNsdp = alignedNsdp,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = actor,
            ModifiedAt = DateTime.UtcNow,
            ModifiedBy = actor
        };

        db.WspStrategicPriorities.Add(priority);
        await db.SaveChangesAsync();

        _auditService.LogAction(db, "WspStrategicPriority", priority.Id, "CreateStrategicPriority", actor, null, priority);
        await db.SaveChangesAsync();

        return priority;
    }

    public async Task<bool> DeleteStrategicPriorityAsync(int id, string actor)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var item = await db.WspStrategicPriorities.FindAsync(id);
        if (item == null) return false;

        db.WspStrategicPriorities.Remove(item);
        _auditService.LogAction(db, "WspStrategicPriority", id, "DeleteStrategicPriority", actor, item, null);
        await db.SaveChangesAsync();
        return true;
    }
}
