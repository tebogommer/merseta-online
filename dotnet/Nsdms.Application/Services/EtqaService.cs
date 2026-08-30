using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public record AssessorValidationResult(
    bool IsValid,
    string? ErrorMessage
);

public interface IEtqaService
{
    Task<EtqaAssessor> RegisterAssessorAsync(EtqaAssessor assessor, string currentUsername = "SYSTEM");
    Task<EtqaAssessor> SaveAssessorAsync(EtqaAssessor assessor, string currentUsername = "SYSTEM");
    Task<EtqaAssessor> SaveAsync(EtqaAssessor assessor, string currentUsername = "SYSTEM");
    Task<EtqaAssessor?> GetAssessorByIdAsync(int id);
    Task<EtqaAssessor?> GetByIdAsync(int id);
    Task<List<EtqaAssessor>> GetAllAsync(string? search = null, string? role = null);
    Task<List<EtqaAssessor>> GetAllAssessorsAsync(string? search = null, string? role = null, bool? activeOnly = null);
    Task<List<EtqaAssessor>> GetAllAssessorsAsync(string? role, bool activeOnly);
    Task<EtqaAssessor> UpdateAssessorAsync(EtqaAssessor assessor, string currentUsername = "SYSTEM");
    Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM");
    Task<bool> DeleteAssessorAsync(int id, string currentUsername = "SYSTEM");
    Task<bool> DeactivateAssessorAsync(int id, string currentUsername = "SYSTEM");

    Task<AssessorModeratorScope> AddScopeAsync(AssessorModeratorScope scope, string currentUsername = "SYSTEM");
    Task<AssessorModeratorScope> AssignScopeAsync(int assessorId, AssessorModeratorScope scope, string currentUsername = "SYSTEM");
    Task<List<AssessorModeratorScope>> GetScopesAsync(int assessorId);
    Task<List<AssessorModeratorScope>> GetScopesByAssessorIdAsync(int assessorId);
    Task<bool> RemoveScopeAsync(int scopeId, string currentUsername = "SYSTEM");

    Task<AssessorValidationResult> ValidateAssessorForAssessmentAsync(int assessorId, int saqaQualificationId, DateTime assessmentDate);
    bool IsAssessorActiveAndValid(EtqaAssessor assessor, DateTime checkDate);
    bool IsScopeValid(AssessorModeratorScope scope, DateTime checkDate);

    Task<LearnerAssessment> RecordLearnerAssessmentAsync(LearnerAssessment assessment, string currentUsername = "SYSTEM");
    Task<LearnerAssessment> AddLearnerAssessmentAsync(LearnerAssessment assessment, string currentUsername = "SYSTEM");
    Task<bool> RemoveLearnerAssessmentAsync(int assessmentId, string currentUsername = "SYSTEM");
    Task<LearnerAssessment> RecordModerationAsync(int assessmentId, int moderatorPersonId, DateTime moderationDate, string currentUsername = "SYSTEM");
    Task<LearnerAssessment?> GetLearnerAssessmentByIdAsync(int id);
    Task<List<LearnerAssessment>> GetLearnerAssessmentsAsync(int? personId = null, int? organisationId = null, int? assessorId = null);
    Task<List<LearnerAssessment>> GetAssessmentsForLearnerAsync(int personId);
    Task<List<LearnerAssessment>> GetAssessmentsForAssessorAsync(int assessorId);
}

public class EtqaService : IEtqaService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public EtqaService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<EtqaAssessor> RegisterAssessorAsync(EtqaAssessor assessor, string currentUsername = "SYSTEM")
    {
        if (assessor.PersonId <= 0)
        {
            throw new ArgumentException("A valid PersonId is required to register an ETQA Assessor/Moderator.");
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        var personExists = await db.People.AnyAsync(p => p.Id == assessor.PersonId);
        if (!personExists)
        {
            throw new KeyNotFoundException($"Person with ID {assessor.PersonId} was not found.");
        }

        if (string.IsNullOrWhiteSpace(assessor.RegistrationNumber))
        {
            var prefix = assessor.EtqaRole.StartsWith("Mod", StringComparison.OrdinalIgnoreCase) ? "MOD" : "ASM";
            assessor.RegistrationNumber = $"{prefix}-{DateTime.UtcNow.Year}-{assessor.PersonId}-{Guid.NewGuid().ToString("N")[..4].ToUpper()}";
        }

        if (string.IsNullOrWhiteSpace(assessor.StatusCode))
        {
            assessor.StatusCode = "Registered";
        }

        if (assessor.StartDate == default)
        {
            assessor.StartDate = DateTime.UtcNow;
        }

        if (assessor.EndDate == default)
        {
            assessor.EndDate = assessor.StartDate.AddYears(3);
        }

        assessor.CreatedAt = DateTime.UtcNow;
        assessor.CreatedBy = currentUsername;

        db.EtqaAssessors.Add(assessor);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "EtqaAssessor", assessor.Id, "RegisterAssessor", currentUsername, null, assessor);
        await db.SaveChangesAsync();

        return assessor;
    }

    public async Task<EtqaAssessor> SaveAssessorAsync(EtqaAssessor assessor, string currentUsername = "SYSTEM")
    {
        if (assessor.Id == 0)
        {
            return await RegisterAssessorAsync(assessor, currentUsername);
        }
        return await UpdateAssessorAsync(assessor, currentUsername);
    }

    public async Task<EtqaAssessor> SaveAsync(EtqaAssessor assessor, string currentUsername = "SYSTEM")
    {
        return await SaveAssessorAsync(assessor, currentUsername);
    }

    public async Task<EtqaAssessor?> GetByIdAsync(int id)
    {
        return await GetAssessorByIdAsync(id);
    }

    public async Task<EtqaAssessor?> GetAssessorByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.EtqaAssessors
            .Include(a => a.Person)
            .Include(a => a.Scopes)
            .Include(a => a.Assessments)
                .ThenInclude(asm => asm.Person)
            .Include(a => a.Assessments)
                .ThenInclude(asm => asm.Organisation)
            .FirstOrDefaultAsync(a => a.Id == id);
    }

    public async Task<List<EtqaAssessor>> GetAllAsync(string? search = null, string? role = null)
    {
        return await GetAllAssessorsAsync(search, role);
    }

    public async Task<List<EtqaAssessor>> GetAllAssessorsAsync(string? search = null, string? role = null, bool? activeOnly = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.EtqaAssessors
            .Include(a => a.Person)
            .Include(a => a.Scopes)
            .Include(a => a.Assessments)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(role))
        {
            query = query.Where(a => a.EtqaRole == role);
        }

        if (activeOnly.HasValue && activeOnly.Value)
        {
            query = query.Where(a => a.IsActive && a.EndDate >= DateTime.UtcNow);
        }

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim();
            query = query.Where(a =>
                a.RegistrationNumber.Contains(s) ||
                (a.StatusCode != null && a.StatusCode.Contains(s)) ||
                (a.Person != null && (a.Person.FirstName.Contains(s) || a.Person.LastName.Contains(s) || a.Person.RsaIdNumber.Contains(s))));
        }

        return await query
            .OrderByDescending(a => a.Id)
            .ToListAsync();
    }

    public async Task<List<EtqaAssessor>> GetAllAssessorsAsync(string? role, bool activeOnly)
    {
        return await GetAllAssessorsAsync(search: null, role: role, activeOnly: activeOnly);
    }

    public async Task<EtqaAssessor> UpdateAssessorAsync(EtqaAssessor assessor, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.EtqaAssessors.FindAsync(assessor.Id);
        if (existing == null)
        {
            throw new KeyNotFoundException($"EtqaAssessor with ID {assessor.Id} was not found.");
        }

        var beforeState = new
        {
            existing.RegistrationNumber,
            existing.EtqaRole,
            existing.StatusCode,
            existing.StartDate,
            existing.EndDate,
            existing.IsActive
        };

        existing.RegistrationNumber = assessor.RegistrationNumber;
        existing.EtqaRole = assessor.EtqaRole;
        existing.StatusCode = assessor.StatusCode;
        existing.StartDate = assessor.StartDate;
        existing.EndDate = assessor.EndDate;
        existing.IsActive = assessor.IsActive;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "EtqaAssessor", existing.Id, "UpdateAssessor", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();

        return existing;
    }

    public async Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM")
    {
        return await DeleteAssessorAsync(id, currentUsername);
    }

    public async Task<bool> DeleteAssessorAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var assessor = await db.EtqaAssessors.FindAsync(id);
        if (assessor == null)
        {
            return false;
        }

        var beforeState = new
        {
            assessor.Id,
            assessor.PersonId,
            assessor.RegistrationNumber,
            assessor.EtqaRole
        };

        db.EtqaAssessors.Remove(assessor);
        _audit.LogAction(db, "EtqaAssessor", id, "DeleteAssessor", currentUsername, beforeState, null);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<bool> DeactivateAssessorAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var assessor = await db.EtqaAssessors.FindAsync(id);
        if (assessor == null)
        {
            return false;
        }

        var beforeState = new { assessor.IsActive, assessor.StatusCode };
        assessor.IsActive = false;
        assessor.StatusCode = "Deactivated";
        assessor.ModifiedAt = DateTime.UtcNow;
        assessor.ModifiedBy = currentUsername;

        _audit.LogAction(db, "EtqaAssessor", id, "DeactivateAssessor", currentUsername, beforeState, assessor);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<AssessorModeratorScope> AddScopeAsync(AssessorModeratorScope scope, string currentUsername = "SYSTEM")
    {
        return await AssignScopeAsync(scope.EtqaAssessorId, scope, currentUsername);
    }

    public async Task<AssessorModeratorScope> AssignScopeAsync(int assessorId, AssessorModeratorScope scope, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var assessor = await db.EtqaAssessors.FindAsync(assessorId);
        if (assessor == null)
        {
            throw new KeyNotFoundException($"EtqaAssessor with ID {assessorId} was not found.");
        }

        if (scope.SaqaQualificationId <= 0)
        {
            throw new ArgumentException("A valid SaqaQualificationId must be specified.");
        }

        if (string.IsNullOrWhiteSpace(scope.QualificationTitle))
        {
            throw new ArgumentException("Qualification title is required.");
        }

        scope.EtqaAssessorId = assessorId;
        if (string.IsNullOrWhiteSpace(scope.RegistrationStatusCode))
        {
            scope.RegistrationStatusCode = "Active";
        }

        if (!scope.ExpiryDate.HasValue)
        {
            scope.ExpiryDate = assessor.EndDate;
        }

        scope.CreatedAt = DateTime.UtcNow;
        scope.CreatedBy = currentUsername;

        db.AssessorModeratorScopes.Add(scope);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "AssessorModeratorScope", scope.Id, "AddScope", currentUsername, null, scope);
        await db.SaveChangesAsync();

        return scope;
    }

    public async Task<List<AssessorModeratorScope>> GetScopesAsync(int assessorId)
    {
        return await GetScopesByAssessorIdAsync(assessorId);
    }

    public async Task<List<AssessorModeratorScope>> GetScopesByAssessorIdAsync(int assessorId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.AssessorModeratorScopes
            .Where(s => s.EtqaAssessorId == assessorId)
            .OrderBy(s => s.QualificationTitle)
            .ToListAsync();
    }

    public async Task<bool> RemoveScopeAsync(int scopeId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var scope = await db.AssessorModeratorScopes.FindAsync(scopeId);
        if (scope == null)
        {
            return false;
        }

        var beforeState = new
        {
            scope.Id,
            scope.EtqaAssessorId,
            scope.SaqaQualificationId,
            scope.QualificationTitle
        };

        db.AssessorModeratorScopes.Remove(scope);
        _audit.LogAction(db, "AssessorModeratorScope", scopeId, "RemoveScope", currentUsername, beforeState, null);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<AssessorValidationResult> ValidateAssessorForAssessmentAsync(int assessorId, int saqaQualificationId, DateTime assessmentDate)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var assessor = await db.EtqaAssessors
            .Include(a => a.Scopes)
            .FirstOrDefaultAsync(a => a.Id == assessorId);

        if (assessor == null)
        {
            return new AssessorValidationResult(false, $"Assessor with ID {assessorId} was not found.");
        }

        if (!IsAssessorActiveAndValid(assessor, assessmentDate))
        {
            return new AssessorValidationResult(false, $"Assessor registration is inactive or expired on the assessment date ({assessmentDate:yyyy-MM-dd}).");
        }

        if (saqaQualificationId > 0)
        {
            var matchingScope = assessor.Scopes.FirstOrDefault(s => s.SaqaQualificationId == saqaQualificationId);
            if (matchingScope == null)
            {
                return new AssessorValidationResult(false, $"Assessor does not possess registered scope for SAQA Qualification ID {saqaQualificationId}.");
            }

            if (!IsScopeValid(matchingScope, assessmentDate))
            {
                return new AssessorValidationResult(false, $"Assessor scope for SAQA Qualification ID {saqaQualificationId} is not approved or has expired.");
            }
        }

        return new AssessorValidationResult(true, null);
    }

    public bool IsAssessorActiveAndValid(EtqaAssessor assessor, DateTime checkDate)
    {
        if (assessor == null || !assessor.IsActive)
        {
            return false;
        }

        return assessor.StartDate <= checkDate && checkDate <= assessor.EndDate;
    }

    public bool IsScopeValid(AssessorModeratorScope scope, DateTime checkDate)
    {
        if (scope == null)
        {
            return false;
        }

        var isApproved = string.Equals(scope.RegistrationStatusCode, "Approved", StringComparison.OrdinalIgnoreCase) ||
                         string.Equals(scope.RegistrationStatusCode, "Active", StringComparison.OrdinalIgnoreCase);
        var notExpired = !scope.ExpiryDate.HasValue || scope.ExpiryDate.Value >= checkDate;

        return isApproved && notExpired;
    }

    public async Task<LearnerAssessment> AddLearnerAssessmentAsync(LearnerAssessment assessment, string currentUsername = "SYSTEM")
    {
        return await RecordLearnerAssessmentAsync(assessment, currentUsername);
    }

    public async Task<LearnerAssessment> RecordLearnerAssessmentAsync(LearnerAssessment assessment, string currentUsername = "SYSTEM")
    {
        if (assessment.PersonId <= 0)
        {
            throw new ArgumentException("A valid PersonId (learner) is required.");
        }

        if (assessment.OrganisationId <= 0)
        {
            throw new ArgumentException("A valid OrganisationId (training site/employer) is required.");
        }

        if (assessment.EtqaAssessorId <= 0)
        {
            throw new ArgumentException("A valid EtqaAssessorId is required.");
        }

        if (string.IsNullOrWhiteSpace(assessment.QualificationTitle))
        {
            throw new ArgumentException("Qualification title is required.");
        }

        if (assessment.AssessmentDate == default)
        {
            assessment.AssessmentDate = DateTime.UtcNow;
        }

        if (string.IsNullOrWhiteSpace(assessment.CompetencyStatusCode))
        {
            assessment.CompetencyStatusCode = "Competent";
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        assessment.CreatedAt = DateTime.UtcNow;
        assessment.CreatedBy = currentUsername;

        db.LearnerAssessments.Add(assessment);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "LearnerAssessment", assessment.Id, "RecordAssessment", currentUsername, null, assessment);
        await db.SaveChangesAsync();

        return assessment;
    }

    public async Task<bool> RemoveLearnerAssessmentAsync(int assessmentId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var item = await db.LearnerAssessments.FindAsync(assessmentId);
        if (item == null) return false;

        db.LearnerAssessments.Remove(item);
        _audit.LogAction(db, "LearnerAssessment", assessmentId, "RemoveAssessment", currentUsername, null, null);
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<LearnerAssessment> RecordModerationAsync(int assessmentId, int moderatorPersonId, DateTime moderationDate, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var assessment = await db.LearnerAssessments.FindAsync(assessmentId);
        if (assessment == null)
        {
            throw new KeyNotFoundException($"LearnerAssessment with ID {assessmentId} was not found.");
        }

        if (moderatorPersonId <= 0)
        {
            throw new ArgumentException("A valid ModeratorPersonId must be specified.");
        }

        var beforeState = new { assessment.ModeratorPersonId, assessment.ModerationDate };

        assessment.ModeratorPersonId = moderatorPersonId;
        assessment.ModerationDate = moderationDate == default ? DateTime.UtcNow : moderationDate;
        assessment.ModifiedAt = DateTime.UtcNow;
        assessment.ModifiedBy = currentUsername;

        _audit.LogAction(db, "LearnerAssessment", assessment.Id, "RecordModeration", currentUsername, beforeState, assessment);
        await db.SaveChangesAsync();

        return assessment;
    }

    public async Task<LearnerAssessment?> GetLearnerAssessmentByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.LearnerAssessments
            .Include(a => a.EtqaAssessor)
            .Include(a => a.Person)
            .Include(a => a.Organisation)
            .Include(a => a.ModeratorPerson)
            .FirstOrDefaultAsync(a => a.Id == id);
    }

    public async Task<List<LearnerAssessment>> GetLearnerAssessmentsAsync(int? personId = null, int? organisationId = null, int? assessorId = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.LearnerAssessments
            .Include(a => a.EtqaAssessor)
            .Include(a => a.Person)
            .Include(a => a.Organisation)
            .Include(a => a.ModeratorPerson)
            .AsQueryable();

        if (personId.HasValue)
        {
            query = query.Where(a => a.PersonId == personId.Value);
        }

        if (organisationId.HasValue)
        {
            query = query.Where(a => a.OrganisationId == organisationId.Value);
        }

        if (assessorId.HasValue)
        {
            query = query.Where(a => a.EtqaAssessorId == assessorId.Value);
        }

        return await query
            .OrderByDescending(a => a.AssessmentDate)
            .ToListAsync();
    }

    public async Task<List<LearnerAssessment>> GetAssessmentsForLearnerAsync(int personId)
    {
        return await GetLearnerAssessmentsAsync(personId: personId);
    }

    public async Task<List<LearnerAssessment>> GetAssessmentsForAssessorAsync(int assessorId)
    {
        return await GetLearnerAssessmentsAsync(assessorId: assessorId);
    }
}
