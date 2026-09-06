using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class InitiateDisciplinaryCaseRequest
{
    public int EtqaAssessorId { get; set; }
    public string CaseType { get; set; } = "DeRegistration"; // DeRegistration, Suspension, VoluntaryDeRegistration
    public string ComplaintSummary { get; set; } = string.Empty;
    public string? ComplaintDocumentRef { get; set; }
    public DateTime? InvestigationStartDate { get; set; } = DateTime.UtcNow;
    public DateTime? InvestigationEndDate { get; set; }
}

public class RecordDisciplinaryOutcomeRequest
{
    public int CaseId { get; set; }
    public string OutcomeCode { get; set; } = "DEREGISTERED"; // DEREGISTERED, SUSPENDED, DISMISSED
    public string ReviewCommitteeDecisionNumber { get; set; } = string.Empty;
    public DateTime ReviewCommitteeDate { get; set; } = DateTime.UtcNow;
    public DateTime? SuspensionStartDate { get; set; }
    public DateTime? SuspensionEndDate { get; set; }
    public string? DevelopmentPlanDetails { get; set; }
    public string? DecisionLetterDocumentRef { get; set; }
}

public interface IAssessorDisciplinaryService
{
    Task<AssessorDisciplinaryCase> InitiateCaseAsync(InitiateDisciplinaryCaseRequest request, string currentUsername = "SYSTEM");
    Task<AssessorDisciplinaryCase> RecordInvestigationReportAsync(int caseId, string reportSummary, string currentUsername = "SYSTEM");
    Task<AssessorDisciplinaryCase> RecordOutcomeAsync(RecordDisciplinaryOutcomeRequest request, string currentUsername = "SYSTEM");
    Task<AssessorDisciplinaryCase> RecordDeceasedAsync(int etqaAssessorId, string deathCertificateRef, string currentUsername = "SYSTEM");
    Task<AssessorDisciplinaryCase?> GetCaseByIdAsync(int caseId);
    Task<List<AssessorDisciplinaryCase>> GetCasesForAssessorAsync(int etqaAssessorId);
    Task<List<AssessorDisciplinaryCase>> GetAllCasesAsync(string? status = null, string? caseType = null);
    Task<bool> CanConductAssessmentAsync(int etqaAssessorId);
}

public class AssessorDisciplinaryService : IAssessorDisciplinaryService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public AssessorDisciplinaryService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<AssessorDisciplinaryCase> InitiateCaseAsync(InitiateDisciplinaryCaseRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var assessor = await db.EtqaAssessors.FirstOrDefaultAsync(a => a.Id == request.EtqaAssessorId);
        if (assessor == null)
            throw new KeyNotFoundException($"EtqaAssessor with ID {request.EtqaAssessorId} not found.");

        var count = await db.AssessorDisciplinaryCases.CountAsync();
        string caseNumber = $"DISC-{DateTime.UtcNow.Year}-{(count + 1):D4}";

        var disciplinaryCase = new AssessorDisciplinaryCase
        {
            EtqaAssessorId = assessor.Id,
            CaseNumber = caseNumber,
            CaseType = request.CaseType,
            ComplaintSummary = request.ComplaintSummary,
            ComplaintDocumentRef = request.ComplaintDocumentRef,
            InvestigationStartDate = request.InvestigationStartDate,
            InvestigationEndDate = request.InvestigationEndDate,
            Status = "UnderInvestigation",
            CreatedBy = currentUsername
        };

        db.AssessorDisciplinaryCases.Add(disciplinaryCase);
        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorDisciplinaryCase", disciplinaryCase.Id, "InitiateCase", currentUsername, new
        {
            disciplinaryCase.CaseNumber,
            request.CaseType,
            assessorId = assessor.Id
        });

        return disciplinaryCase;
    }

    public async Task<AssessorDisciplinaryCase> RecordInvestigationReportAsync(int caseId, string reportSummary, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var disciplinaryCase = await db.AssessorDisciplinaryCases.FirstOrDefaultAsync(c => c.Id == caseId);
        if (disciplinaryCase == null)
            throw new KeyNotFoundException($"AssessorDisciplinaryCase with ID {caseId} not found.");

        disciplinaryCase.InvestigationReportSummary = reportSummary;
        disciplinaryCase.Status = "InvestigationCompleted";
        disciplinaryCase.InvestigationEndDate = DateTime.UtcNow;
        disciplinaryCase.ModifiedAt = DateTime.UtcNow;
        disciplinaryCase.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorDisciplinaryCase", disciplinaryCase.Id, "RecordInvestigationReport", currentUsername, new
        {
            disciplinaryCase.CaseNumber,
            disciplinaryCase.Status
        });

        return disciplinaryCase;
    }

    public async Task<AssessorDisciplinaryCase> RecordOutcomeAsync(RecordDisciplinaryOutcomeRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var disciplinaryCase = await db.AssessorDisciplinaryCases
            .Include(c => c.EtqaAssessor)
            .FirstOrDefaultAsync(c => c.Id == request.CaseId);

        if (disciplinaryCase == null)
            throw new KeyNotFoundException($"AssessorDisciplinaryCase with ID {request.CaseId} not found.");

        disciplinaryCase.OutcomeCode = request.OutcomeCode;
        disciplinaryCase.ReviewCommitteeDecisionNumber = request.ReviewCommitteeDecisionNumber;
        disciplinaryCase.ReviewCommitteeDate = request.ReviewCommitteeDate;
        disciplinaryCase.SuspensionStartDate = request.SuspensionStartDate;
        disciplinaryCase.SuspensionEndDate = request.SuspensionEndDate;
        disciplinaryCase.DevelopmentPlanDetails = request.DevelopmentPlanDetails;
        disciplinaryCase.DecisionLetterDocumentRef = request.DecisionLetterDocumentRef;
        disciplinaryCase.Status = "Sanctioned";
        disciplinaryCase.ClosedAt = DateTime.UtcNow;
        disciplinaryCase.ClosedByUserId = currentUsername;
        disciplinaryCase.ModifiedAt = DateTime.UtcNow;
        disciplinaryCase.ModifiedBy = currentUsername;

        // Apply state transitions to parent assessor profile per Spec Section 4.2.7
        var assessor = disciplinaryCase.EtqaAssessor ?? await db.EtqaAssessors.FindAsync(disciplinaryCase.EtqaAssessorId);
        if (assessor != null)
        {
            if (request.OutcomeCode == "DEREGISTERED")
            {
                assessor.IsActive = false;
                assessor.RegistrationStatusCode = "De-Registered";
                assessor.AssessmentAbilitySuspended = true;
                assessor.DesignationStructureStatusId = "02"; // Deregistered
            }
            else if (request.OutcomeCode == "SUSPENDED")
            {
                assessor.IsActive = false;
                assessor.RegistrationStatusCode = "Suspended";
                assessor.AssessmentAbilitySuspended = true;
                assessor.DesignationStructureStatusId = "03"; // Suspended
            }
            else if (request.OutcomeCode == "DISMISSED")
            {
                assessor.IsActive = true;
                assessor.RegistrationStatusCode = "Registered";
                assessor.AssessmentAbilitySuspended = false;
                assessor.DesignationStructureStatusId = "01"; // Registered
            }
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorDisciplinaryCase", disciplinaryCase.Id, "RecordOutcome", currentUsername, new
        {
            disciplinaryCase.CaseNumber,
            request.OutcomeCode,
            request.ReviewCommitteeDecisionNumber,
            AssessorStatus = disciplinaryCase.EtqaAssessor?.RegistrationStatusCode
        });

        return disciplinaryCase;
    }

    public async Task<AssessorDisciplinaryCase> RecordDeceasedAsync(int etqaAssessorId, string deathCertificateRef, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var assessor = await db.EtqaAssessors.FirstOrDefaultAsync(a => a.Id == etqaAssessorId);
        if (assessor == null)
            throw new KeyNotFoundException($"EtqaAssessor with ID {etqaAssessorId} not found.");

        // Immediately deactivate and mark deceased (Spec Section 5: once death certificate issued, deregister as deceased)
        assessor.IsActive = false;
        assessor.RegistrationStatusCode = "De-Registered";
        assessor.DeRegistrationReason = "Deceased";
        assessor.AssessmentAbilitySuspended = true;
        assessor.DesignationStructureStatusId = "02"; // Deregistered

        var count = await db.AssessorDisciplinaryCases.CountAsync();
        var deceasedCase = new AssessorDisciplinaryCase
        {
            EtqaAssessorId = assessor.Id,
            CaseNumber = $"DISC-{DateTime.UtcNow.Year}-DHA-{(count + 1):D4}",
            CaseType = "Deceased",
            ComplaintSummary = "Department of Home Affairs (DHA) statutory death certificate notification recorded.",
            ComplaintDocumentRef = deathCertificateRef,
            OutcomeCode = "DECEASED",
            Status = "Closed",
            ClosedAt = DateTime.UtcNow,
            ClosedByUserId = currentUsername,
            CreatedBy = currentUsername
        };

        db.AssessorDisciplinaryCases.Add(deceasedCase);
        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorDisciplinaryCase", deceasedCase.Id, "RecordDeceased", currentUsername, new
        {
            assessorId = assessor.Id,
            deathCertificateRef,
            deceasedCase.CaseNumber
        });

        return deceasedCase;
    }

    public async Task<AssessorDisciplinaryCase?> GetCaseByIdAsync(int caseId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.AssessorDisciplinaryCases
            .Include(c => c.EtqaAssessor)
                .ThenInclude(a => a!.Person)
            .FirstOrDefaultAsync(c => c.Id == caseId);
    }

    public async Task<List<AssessorDisciplinaryCase>> GetCasesForAssessorAsync(int etqaAssessorId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.AssessorDisciplinaryCases
            .Where(c => c.EtqaAssessorId == etqaAssessorId)
            .OrderByDescending(c => c.CreatedAt)
            .ToListAsync();
    }

    public async Task<List<AssessorDisciplinaryCase>> GetAllCasesAsync(string? status = null, string? caseType = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.AssessorDisciplinaryCases
            .Include(c => c.EtqaAssessor)
                .ThenInclude(a => a!.Person)
            .AsNoTracking()
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(status) && status != "All")
            query = query.Where(c => c.Status == status);

        if (!string.IsNullOrWhiteSpace(caseType) && caseType != "All")
            query = query.Where(c => c.CaseType == caseType);

        return await query.OrderByDescending(c => c.CreatedAt).ToListAsync();
    }

    public async Task<bool> CanConductAssessmentAsync(int etqaAssessorId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var assessor = await db.EtqaAssessors.AsNoTracking().FirstOrDefaultAsync(a => a.Id == etqaAssessorId);
        if (assessor == null) return false;

        // Practitioner must be Active, not Deregistered, not Suspended, not Deceased
        if (!assessor.IsActive) return false;
        if (assessor.RegistrationStatusCode == "Deregistered" ||
            assessor.RegistrationStatusCode == "De-Registered" ||
            assessor.RegistrationStatusCode == "Suspended" ||
            assessor.RegistrationStatusCode == "Deceased" ||
            assessor.RegistrationStatusCode == "Expired" ||
            assessor.AssessmentAbilitySuspended)
        {
            return false;
        }

        // Validity end date check
        if (assessor.EndDate < DateTime.UtcNow.Date)
            return false;

        return true;
    }
}
