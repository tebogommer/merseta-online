using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class InitiateSdpDisciplinaryCaseRequest
{
    public int TrainingProviderId { get; set; }
    public string CaseType { get; set; } = "Suspension"; // Suspension, DeAccreditation, VoluntarySurrender, NonComplianceNotice, ScopeRestriction
    public string ComplaintSource { get; set; } = "AuditFinding";
    public string AllegationSummary { get; set; } = string.Empty;
}

public class RecordSdpDisciplinaryOutcomeRequest
{
    public int CaseId { get; set; }
    public string OutcomeCode { get; set; } = "SUSPENDED"; // SUSPENDED, DEREGISTERED, DISMISSED, FORMAL_WARNING, REINSTATED
    public string SanctionType { get; set; } = "TemporarySuspension"; // TemporarySuspension, FullDeregistration, FormalWarning, ScopeRestriction, None
    public string ReviewCommitteeDecisionNumber { get; set; } = string.Empty;
    public DateTime ReviewCommitteeDate { get; set; } = DateTime.UtcNow;
    public DateTime? SanctionStartDate { get; set; }
    public DateTime? SanctionEndDate { get; set; }
    public string? NoticeDocumentRef { get; set; }
}

public interface ISdpDisciplinaryService
{
    Task<SdpDisciplinaryCase> InitiateCaseAsync(InitiateSdpDisciplinaryCaseRequest request, string currentUsername = "SYSTEM");
    Task<SdpDisciplinaryCase> RecordInvestigationReportAsync(int caseId, string reportSummary, string currentUsername = "SYSTEM");
    Task<SdpDisciplinaryCase> RecordOutcomeAsync(RecordSdpDisciplinaryOutcomeRequest request, string currentUsername = "SYSTEM");
    Task<SdpDisciplinaryCase?> GetCaseByIdAsync(int caseId);
    Task<List<SdpDisciplinaryCase>> GetCasesForProviderAsync(int trainingProviderId);
    Task<List<SdpDisciplinaryCase>> GetAllCasesAsync(string? status = null, string? caseType = null);
    Task<bool> IsProviderEligibleForEnrolmentsAsync(int trainingProviderId);

    // Site Inspection Form ETQ-TP-012
    Task<SdpSiteInspection> RecordSiteInspectionAsync(SdpSiteInspection inspection, string currentUsername = "SYSTEM");
    Task<List<SdpSiteInspection>> GetSiteInspectionsForProviderAsync(int trainingProviderId);

    // Historical Re-Accreditation
    Task<List<SdpReAccreditationApplication>> GetReAccreditationApplicationsAsync(int trainingProviderId);
    Task<SdpReAccreditationApplication> SubmitReAccreditationApplicationAsync(int trainingProviderId, SdpReAccreditationApplication application, string currentUsername = "SYSTEM");
}

public class SdpDisciplinaryService : ISdpDisciplinaryService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public SdpDisciplinaryService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<SdpDisciplinaryCase> InitiateCaseAsync(InitiateSdpDisciplinaryCaseRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var provider = await db.TrainingProviders.FirstOrDefaultAsync(p => p.Id == request.TrainingProviderId);
        if (provider == null)
            throw new KeyNotFoundException($"TrainingProvider with ID {request.TrainingProviderId} not found.");

        var count = await db.SdpDisciplinaryCases.CountAsync();
        string caseNumber = $"SDP-DISC-{DateTime.UtcNow.Year}-{(count + 1):D4}";

        var entity = new SdpDisciplinaryCase
        {
            TrainingProviderId = request.TrainingProviderId,
            CaseNumber = caseNumber,
            CaseType = request.CaseType,
            Status = "UnderInvestigation",
            ComplaintSource = request.ComplaintSource,
            AllegationSummary = request.AllegationSummary,
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.SdpDisciplinaryCases.Add(entity);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "SdpDisciplinaryCase", entity.Id, "InitiateDisciplinaryCase", currentUsername, null, entity);
        await db.SaveChangesAsync();

        return entity;
    }

    public async Task<SdpDisciplinaryCase> RecordInvestigationReportAsync(int caseId, string reportSummary, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var entity = await db.SdpDisciplinaryCases.FirstOrDefaultAsync(c => c.Id == caseId);
        if (entity == null)
            throw new KeyNotFoundException($"SdpDisciplinaryCase with ID {caseId} not found.");

        var beforeState = new { entity.Status, entity.InvestigationFindings };

        entity.InvestigationFindings = reportSummary;
        entity.Status = "CommitteeReview";
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = currentUsername;

        _audit.LogAction(db, "SdpDisciplinaryCase", entity.Id, "RecordInvestigationReport", currentUsername, beforeState, entity);
        await db.SaveChangesAsync();

        return entity;
    }

    public async Task<SdpDisciplinaryCase> RecordOutcomeAsync(RecordSdpDisciplinaryOutcomeRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var entity = await db.SdpDisciplinaryCases
            .Include(c => c.TrainingProvider)
            .FirstOrDefaultAsync(c => c.Id == request.CaseId);

        if (entity == null)
            throw new KeyNotFoundException($"SdpDisciplinaryCase with ID {request.CaseId} not found.");

        var beforeState = new { entity.Status, entity.SanctionType, entity.SanctionStartDate, entity.SanctionEndDate };

        entity.ReviewCommitteeDecisionNumber = request.ReviewCommitteeDecisionNumber;
        entity.ReviewCommitteeDate = request.ReviewCommitteeDate;
        entity.SanctionType = request.SanctionType;
        entity.SanctionStartDate = request.SanctionStartDate ?? DateTime.UtcNow;
        entity.SanctionEndDate = request.SanctionEndDate;
        entity.NoticeDocumentRef = request.NoticeDocumentRef;
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = currentUsername;

        var provider = entity.TrainingProvider ?? await db.TrainingProviders.FindAsync(entity.TrainingProviderId);

        if (request.OutcomeCode == "SUSPENDED" || request.SanctionType == "TemporarySuspension")
        {
            entity.Status = "Suspended";
            if (provider != null)
            {
                // Statutory Freeze Invariant: Provider is suspended and cannot register new learners
                provider.IsActive = false;
                provider.ProviderStatusCode = "Suspended";
                provider.ModifiedAt = DateTime.UtcNow;
                provider.ModifiedBy = currentUsername;
            }
        }
        else if (request.OutcomeCode == "DEREGISTERED" || request.SanctionType == "FullDeregistration")
        {
            entity.Status = "Deregistered";
            if (provider != null)
            {
                provider.IsActive = false;
                provider.ProviderStatusCode = "De-accredited";
                provider.ModifiedAt = DateTime.UtcNow;
                provider.ModifiedBy = currentUsername;
            }
        }
        else if (request.OutcomeCode == "REINSTATED")
        {
            entity.Status = "Reinstated";
            if (provider != null)
            {
                provider.IsActive = true;
                provider.ProviderStatusCode = "Active";
                provider.ModifiedAt = DateTime.UtcNow;
                provider.ModifiedBy = currentUsername;
            }
        }
        else if (request.OutcomeCode == "DISMISSED")
        {
            entity.Status = "Dismissed";
        }
        else
        {
            entity.Status = "Sanctioned";
        }

        if (provider != null)
        {
            db.TrainingProviders.Update(provider);
        }

        _audit.LogAction(db, "SdpDisciplinaryCase", entity.Id, "RecordDisciplinaryOutcome", currentUsername, beforeState, entity);
        await db.SaveChangesAsync();

        return entity;
    }

    public async Task<SdpDisciplinaryCase?> GetCaseByIdAsync(int caseId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.SdpDisciplinaryCases
            .Include(c => c.TrainingProvider)
            .FirstOrDefaultAsync(c => c.Id == caseId);
    }

    public async Task<List<SdpDisciplinaryCase>> GetCasesForProviderAsync(int trainingProviderId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.SdpDisciplinaryCases
            .Where(c => c.TrainingProviderId == trainingProviderId)
            .OrderByDescending(c => c.CreatedAt)
            .ToListAsync();
    }

    public async Task<List<SdpDisciplinaryCase>> GetAllCasesAsync(string? status = null, string? caseType = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.SdpDisciplinaryCases
            .Include(c => c.TrainingProvider)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(status))
            query = query.Where(c => c.Status == status);

        if (!string.IsNullOrWhiteSpace(caseType))
            query = query.Where(c => c.CaseType == caseType);

        return await query.OrderByDescending(c => c.CreatedAt).ToListAsync();
    }

    public async Task<bool> IsProviderEligibleForEnrolmentsAsync(int trainingProviderId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var provider = await db.TrainingProviders.FirstOrDefaultAsync(p => p.Id == trainingProviderId);
        if (provider == null || !provider.IsActive || provider.ProviderStatusCode == "Suspended" || provider.ProviderStatusCode == "De-accredited")
            return false;

        var hasActiveSuspension = await db.SdpDisciplinaryCases.AnyAsync(c =>
            c.TrainingProviderId == trainingProviderId &&
            c.Status == "Suspended" &&
            (c.SanctionEndDate == null || c.SanctionEndDate > DateTime.UtcNow));

        return !hasActiveSuspension;
    }

    // Site Inspection Form ETQ-TP-012
    public async Task<SdpSiteInspection> RecordSiteInspectionAsync(SdpSiteInspection inspection, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        if (inspection.Id == 0)
        {
            inspection.CreatedAt = DateTime.UtcNow;
            inspection.CreatedBy = currentUsername;
            db.SdpSiteInspections.Add(inspection);
        }
        else
        {
            var existing = await db.SdpSiteInspections.FindAsync(inspection.Id);
            if (existing == null) throw new KeyNotFoundException($"Inspection {inspection.Id} not found.");

            existing.InspectionDate = inspection.InspectionDate;
            existing.InspectorPersonId = inspection.InspectorPersonId;
            existing.InspectionType = inspection.InspectionType;
            existing.WorkshopSquareMeters = inspection.WorkshopSquareMeters;
            existing.ClassroomSquareMeters = inspection.ClassroomSquareMeters;
            existing.HealthAndSafetyCompliant = inspection.HealthAndSafetyCompliant;
            existing.MachineGuardingCompliant = inspection.MachineGuardingCompliant;
            existing.FireSafetyCompliant = inspection.FireSafetyCompliant;
            existing.AblutionFacilitiesCompliant = inspection.AblutionFacilitiesCompliant;
            existing.ToolRatioScore = inspection.ToolRatioScore;
            existing.OverallRecommendation = inspection.OverallRecommendation;
            existing.ConditionNotes = inspection.ConditionNotes;
            existing.InspectionReportDocumentRef = inspection.InspectionReportDocumentRef;
            existing.ModifiedAt = DateTime.UtcNow;
            existing.ModifiedBy = currentUsername;
            inspection = existing;
        }

        await db.SaveChangesAsync();
        _audit.LogAction(db, "SdpSiteInspection", inspection.Id, "SaveInspection", currentUsername, null, inspection);
        await db.SaveChangesAsync();

        return inspection;
    }

    public async Task<List<SdpSiteInspection>> GetSiteInspectionsForProviderAsync(int trainingProviderId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.SdpSiteInspections
            .Include(i => i.InspectorPerson)
            .Where(i => i.TrainingProviderId == trainingProviderId)
            .OrderByDescending(i => i.InspectionDate)
            .ToListAsync();
    }

    // Historical Re-Accreditation
    public async Task<List<SdpReAccreditationApplication>> GetReAccreditationApplicationsAsync(int trainingProviderId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.SdpReAccreditationApplications
            .Where(a => a.TrainingProviderId == trainingProviderId)
            .OrderByDescending(a => a.CreatedAt)
            .ToListAsync();
    }

    public async Task<SdpReAccreditationApplication> SubmitReAccreditationApplicationAsync(int trainingProviderId, SdpReAccreditationApplication application, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var provider = await db.TrainingProviders.FindAsync(trainingProviderId);
        if (provider == null) throw new KeyNotFoundException($"TrainingProvider {trainingProviderId} not found.");

        var count = await db.SdpReAccreditationApplications.CountAsync();
        application.TrainingProviderId = trainingProviderId;
        application.ApplicationNumber = $"SDP-REN-{DateTime.UtcNow.Year}-{(count + 1):D4}";
        application.CurrentAccreditationExpiryDate = provider.AccreditationEndDate ?? DateTime.UtcNow;
        application.ProposedAccreditationExpiryDate = (provider.AccreditationEndDate ?? DateTime.UtcNow).AddYears(5);
        application.StatusCode = "UnderReview";
        application.CreatedAt = DateTime.UtcNow;
        application.CreatedBy = currentUsername;

        db.SdpReAccreditationApplications.Add(application);

        // Set operational continuity flag on provider
        provider.ReAccreditationUnderway = true;
        provider.ReAccreditationEffectiveDate = DateTime.UtcNow;
        provider.ModifiedAt = DateTime.UtcNow;
        provider.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();
        _audit.LogAction(db, "SdpReAccreditationApplication", application.Id, "SubmitReAccreditation", currentUsername, null, application);
        await db.SaveChangesAsync();

        return application;
    }
}
