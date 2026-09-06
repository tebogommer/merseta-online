using System.Security.Cryptography;
using System.Text;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

#region DTOs

public class CreateAssessorApplicationRequest
{
    public string PractitionerType { get; set; } = "Assessor";
    public int PersonId { get; set; }
    public string? LastSchoolAttended { get; set; }
    public int? LastSchoolYear { get; set; }
    public string EmploymentStatusCode { get; set; } = "Employed";
    public string? DisabilityTypeCode { get; set; }
    public string? DisabilitySeverityCode { get; set; }
    public string UrbanRuralArea { get; set; } = "Urban";
    public string? NextOfKinName { get; set; }
    public string? NextOfKinContact { get; set; }
    public string? NextOfKinRelationship { get; set; }
    public string? HighestQualificationTitle { get; set; }
    public DateTime? HighestQualificationObtainedDate { get; set; }
}

public class UpdateAssessorApplicationRequest
{
    public int ApplicationId { get; set; }
    public string PractitionerType { get; set; } = "Assessor";
    public string? LastSchoolAttended { get; set; }
    public int? LastSchoolYear { get; set; }
    public string EmploymentStatusCode { get; set; } = "Employed";
    public string? DisabilityTypeCode { get; set; }
    public string? DisabilitySeverityCode { get; set; }
    public string UrbanRuralArea { get; set; } = "Urban";
    public string? NextOfKinName { get; set; }
    public string? NextOfKinContact { get; set; }
    public string? NextOfKinRelationship { get; set; }
    public string? HighestQualificationTitle { get; set; }
    public DateTime? HighestQualificationObtainedDate { get; set; }
}

public class AddQualificationScopeRequest
{
    public int SaqaQualificationId { get; set; }
    public string QualificationTitle { get; set; } = string.Empty;
    public DateTime QualificationObtainedDate { get; set; }
    public List<ConstituentUnitStandardDto>? UnitStandards { get; set; }
}

public class ConstituentUnitStandardDto
{
    public string UnitStandardCode { get; set; } = string.Empty;
    public string UnitStandardTitle { get; set; } = string.Empty;
    public int NqfLevel { get; set; } = 4;
    public int Credits { get; set; } = 15;
}

public class VerificationStepRequest
{
    public int ApplicationId { get; set; }
    public string Recommendation { get; set; } = "Recommend"; // Recommend, Reject
    public string? Reason { get; set; }
    public string? Explanation { get; set; }
}

public class EvaluationStepRequest
{
    public int ApplicationId { get; set; }
    public string Recommendation { get; set; } = "Recommend"; // Recommend, Reject
    public string? Reason { get; set; }
    public string? Explanation { get; set; }
}

public class ReviewCommitteeDecisionRequest
{
    public int ApplicationId { get; set; }
    public string Decision { get; set; } = "Approve"; // Approve, Reject
    public string CommitteeDecisionNumber { get; set; } = string.Empty;
    public DateTime MeetingDate { get; set; } = DateTime.UtcNow;
    public string? Notes { get; set; }
    public bool IsFinalRejection { get; set; }
    public string? RejectionReason { get; set; }
    public string? RejectionComments { get; set; }
}

public class FinalApprovalRequest
{
    public int ApplicationId { get; set; }
    public string? ApprovalComments { get; set; }
}

#endregion

public interface IAssessorRegistrationService
{
    Task<AssessorRegistrationApplication> CreateDraftApplicationAsync(CreateAssessorApplicationRequest request, string currentUsername = "SYSTEM");
    Task<AssessorRegistrationApplication> UpdateDraftApplicationAsync(UpdateAssessorApplicationRequest request, string currentUsername = "SYSTEM");
    Task<AssessorRegistrationApplication?> GetApplicationByIdAsync(int id);
    Task<List<AssessorRegistrationApplication>> GetApplicationsAsync(string? status = null, string? practitionerType = null, string? search = null);
    Task<AssessorApplicationScope> AddQualificationScopeAsync(int applicationId, AddQualificationScopeRequest request, string currentUsername = "SYSTEM");
    Task<bool> RemoveQualificationScopeAsync(int scopeId, string currentUsername = "SYSTEM");
    Task<AssessorApplicationUnitStandard> AddStandaloneUnitStandardAsync(int scopeId, ConstituentUnitStandardDto unitStandard, string currentUsername = "SYSTEM");
    Task<bool> RemoveUnitStandardAsync(int unitStandardId, string currentUsername = "SYSTEM");
    Task<AssessorApplicationProviderLink> LinkProviderAffiliationAsync(int applicationId, int trainingProviderId, string? slaDocumentRef, string currentUsername = "SYSTEM");
    Task<bool> RemoveProviderAffiliationAsync(int linkId, string currentUsername = "SYSTEM");
    Task<AssessorApplicationDocument> AttachDocumentAsync(int applicationId, string documentType, string title, string filePath, string currentUsername = "SYSTEM");
    Task<AssessorRegistrationApplication> SignOffAndSubmitAsync(int applicationId, string currentUsername = "SYSTEM");
    Task<AssessorRegistrationApplication> WithdrawApplicationAsync(int applicationId, string reason, string? comments, string currentUsername = "SYSTEM");

    // 4-Stage Maker-Checker Workflow
    Task<AssessorRegistrationApplication> VerifyDocumentAsync(VerificationStepRequest request, string currentUsername = "SYSTEM");
    Task<AssessorRegistrationApplication> EvaluateApplicationAsync(EvaluationStepRequest request, string currentUsername = "SYSTEM");
    Task<AssessorRegistrationApplication> RecordCommitteeDecisionAsync(ReviewCommitteeDecisionRequest request, string currentUsername = "SYSTEM");
    Task<AssessorRegistrationApplication> FinalApproveAsync(FinalApprovalRequest request, string currentUsername = "SYSTEM");
}

public class AssessorRegistrationService : IAssessorRegistrationService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public AssessorRegistrationService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<AssessorRegistrationApplication> CreateDraftApplicationAsync(CreateAssessorApplicationRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var existingCount = await db.AssessorRegistrationApplications.CountAsync();
        string prefix = request.PractitionerType == "Moderator" ? "MOD" : "ASS";
        string appNumber = $"APP-{prefix}-{DateTime.UtcNow.Year}-{(existingCount + 1):D5}";

        var app = new AssessorRegistrationApplication
        {
            ApplicationNumber = appNumber,
            PractitionerType = request.PractitionerType,
            PersonId = request.PersonId,
            LastSchoolAttended = request.LastSchoolAttended,
            LastSchoolYear = request.LastSchoolYear,
            EmploymentStatusCode = request.EmploymentStatusCode,
            DisabilityTypeCode = request.DisabilityTypeCode,
            DisabilitySeverityCode = request.DisabilitySeverityCode,
            UrbanRuralArea = request.UrbanRuralArea,
            NextOfKinName = request.NextOfKinName,
            NextOfKinContact = request.NextOfKinContact,
            NextOfKinRelationship = request.NextOfKinRelationship,
            HighestQualificationTitle = request.HighestQualificationTitle,
            HighestQualificationObtainedDate = request.HighestQualificationObtainedDate,
            ApplicationStatusCode = "Draft",
            CreatedBy = currentUsername
        };

        db.AssessorRegistrationApplications.Add(app);
        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorRegistrationApplication", app.Id, "CreateDraft", currentUsername, new
        {
            app.ApplicationNumber,
            app.PractitionerType,
            app.PersonId
        });

        return app;
    }

    public async Task<AssessorRegistrationApplication> UpdateDraftApplicationAsync(UpdateAssessorApplicationRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.AssessorRegistrationApplications.FirstOrDefaultAsync(a => a.Id == request.ApplicationId);

        if (app == null)
            throw new KeyNotFoundException($"AssessorRegistrationApplication with ID {request.ApplicationId} not found.");

        if (app.ApplicationStatusCode != "Draft" && app.ApplicationStatusCode != "Application")
            throw new InvalidOperationException($"Cannot update application when in status '{app.ApplicationStatusCode}'.");

        app.PractitionerType = request.PractitionerType;
        app.LastSchoolAttended = request.LastSchoolAttended;
        app.LastSchoolYear = request.LastSchoolYear;
        app.EmploymentStatusCode = request.EmploymentStatusCode;
        app.DisabilityTypeCode = request.DisabilityTypeCode;
        app.DisabilitySeverityCode = request.DisabilitySeverityCode;
        app.UrbanRuralArea = request.UrbanRuralArea;
        app.NextOfKinName = request.NextOfKinName;
        app.NextOfKinContact = request.NextOfKinContact;
        app.NextOfKinRelationship = request.NextOfKinRelationship;
        app.HighestQualificationTitle = request.HighestQualificationTitle;
        app.HighestQualificationObtainedDate = request.HighestQualificationObtainedDate;
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorRegistrationApplication", app.Id, "UpdateDraft", currentUsername, new
        {
            app.ApplicationNumber,
            app.PractitionerType
        });

        return app;
    }

    public async Task<AssessorRegistrationApplication?> GetApplicationByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.AssessorRegistrationApplications
            .Include(a => a.Person)
            .Include(a => a.RegisteredAssessor)
            .Include(a => a.Scopes)
                .ThenInclude(s => s.UnitStandards)
            .Include(a => a.ProviderAffiliations)
                .ThenInclude(p => p.TrainingProvider)
            .Include(a => a.Documents)
            .FirstOrDefaultAsync(a => a.Id == id);
    }

    public async Task<List<AssessorRegistrationApplication>> GetApplicationsAsync(string? status = null, string? practitionerType = null, string? search = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.AssessorRegistrationApplications
            .Include(a => a.Person)
            .AsNoTracking()
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(status) && status != "All")
            query = query.Where(a => a.ApplicationStatusCode == status);

        if (!string.IsNullOrWhiteSpace(practitionerType) && practitionerType != "All")
            query = query.Where(a => a.PractitionerType == practitionerType);

        if (!string.IsNullOrWhiteSpace(search))
        {
            search = search.Trim().ToLower();
            query = query.Where(a => a.ApplicationNumber.ToLower().Contains(search) ||
                                     (a.Person != null && (a.Person.FirstName.ToLower().Contains(search) ||
                                                           a.Person.LastName.ToLower().Contains(search) ||
                                                           (a.Person.RsaIdNumber != null && a.Person.RsaIdNumber.Contains(search)))));
        }

        return await query.OrderByDescending(a => a.CreatedAt).ToListAsync();
    }

    public async Task<AssessorApplicationScope> AddQualificationScopeAsync(int applicationId, AddQualificationScopeRequest request, string currentUsername = "SYSTEM")
    {
        // Enforce 3-year post-qualification experience invariant (Spec Section 5)
        var yearsSince = (DateTime.UtcNow - request.QualificationObtainedDate).TotalDays / 365.25;
        if (yearsSince < 3.0)
        {
            throw new InvalidOperationException("Statutory Validation Failure: Practitioners must have at least 3 years of post-qualification industry experience before applying for assessor/moderator registration.");
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.AssessorRegistrationApplications.FirstOrDefaultAsync(a => a.Id == applicationId);
        if (app == null)
            throw new KeyNotFoundException($"AssessorRegistrationApplication with ID {applicationId} not found.");

        var scope = new AssessorApplicationScope
        {
            AssessorRegistrationApplicationId = app.Id,
            SaqaQualificationId = request.SaqaQualificationId,
            QualificationTitle = request.QualificationTitle,
            QualificationObtainedDate = request.QualificationObtainedDate,
            CreatedBy = currentUsername
        };

        // Auto-populate constituent unit standards from qualification
        if (request.UnitStandards != null && request.UnitStandards.Count > 0)
        {
            foreach (var us in request.UnitStandards)
            {
                scope.UnitStandards.Add(new AssessorApplicationUnitStandard
                {
                    UnitStandardCode = us.UnitStandardCode,
                    UnitStandardTitle = us.UnitStandardTitle,
                    NqfLevel = us.NqfLevel,
                    Credits = us.Credits,
                    IsPopulatedFromQualification = true,
                    CreatedBy = currentUsername
                });
            }
        }

        db.AssessorApplicationScopes.Add(scope);
        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorApplicationScope", scope.Id, "AddScope", currentUsername, new
        {
            appId = app.Id,
            scope.SaqaQualificationId,
            scope.QualificationTitle,
            UnitStandardCount = scope.UnitStandards.Count
        });

        return scope;
    }

    public async Task<bool> RemoveQualificationScopeAsync(int scopeId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var scope = await db.AssessorApplicationScopes
            .Include(s => s.UnitStandards)
            .FirstOrDefaultAsync(s => s.Id == scopeId);

        if (scope == null) return false;

        db.AssessorApplicationScopes.Remove(scope);
        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorApplicationScope", scopeId, "RemoveScope", currentUsername, new { scope.SaqaQualificationId });
        return true;
    }

    public async Task<AssessorApplicationUnitStandard> AddStandaloneUnitStandardAsync(int scopeId, ConstituentUnitStandardDto unitStandard, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var scope = await db.AssessorApplicationScopes.FirstOrDefaultAsync(s => s.Id == scopeId);
        if (scope == null)
            throw new KeyNotFoundException($"AssessorApplicationScope with ID {scopeId} not found.");

        var us = new AssessorApplicationUnitStandard
        {
            AssessorApplicationScopeId = scope.Id,
            UnitStandardCode = unitStandard.UnitStandardCode,
            UnitStandardTitle = unitStandard.UnitStandardTitle,
            NqfLevel = unitStandard.NqfLevel,
            Credits = unitStandard.Credits,
            IsPopulatedFromQualification = false, // Standalone, removable
            CreatedBy = currentUsername
        };

        db.AssessorApplicationUnitStandards.Add(us);
        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorApplicationUnitStandard", us.Id, "AddStandaloneUnitStandard", currentUsername, new
        {
            scopeId,
            us.UnitStandardCode
        });

        return us;
    }

    public async Task<bool> RemoveUnitStandardAsync(int unitStandardId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var us = await db.AssessorApplicationUnitStandards.FirstOrDefaultAsync(u => u.Id == unitStandardId);
        if (us == null) return false;

        // Enforce business rule: only manually added unit standards can be removed!
        if (us.IsPopulatedFromQualification)
        {
            throw new InvalidOperationException("Statutory Rule: Constituent unit standards populated from a qualification cannot be removed. Only additional standalone unit standards may be removed.");
        }

        db.AssessorApplicationUnitStandards.Remove(us);
        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorApplicationUnitStandard", unitStandardId, "RemoveUnitStandard", currentUsername, new { us.UnitStandardCode });
        return true;
    }

    public async Task<AssessorApplicationProviderLink> LinkProviderAffiliationAsync(int applicationId, int trainingProviderId, string? slaDocumentRef, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.AssessorRegistrationApplications.FirstOrDefaultAsync(a => a.Id == applicationId);
        if (app == null)
            throw new KeyNotFoundException($"AssessorRegistrationApplication with ID {applicationId} not found.");

        var link = new AssessorApplicationProviderLink
        {
            AssessorRegistrationApplicationId = app.Id,
            TrainingProviderId = trainingProviderId,
            SlaDocumentRef = slaDocumentRef,
            IsVerifiedByProvider = false,
            CreatedBy = currentUsername
        };

        db.AssessorApplicationProviderLinks.Add(link);
        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorApplicationProviderLink", link.Id, "LinkProvider", currentUsername, new
        {
            applicationId,
            trainingProviderId,
            slaDocumentRef
        });

        return link;
    }

    public async Task<bool> RemoveProviderAffiliationAsync(int linkId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var link = await db.AssessorApplicationProviderLinks.FirstOrDefaultAsync(l => l.Id == linkId);
        if (link == null) return false;

        db.AssessorApplicationProviderLinks.Remove(link);
        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorApplicationProviderLink", linkId, "RemoveProviderLink", currentUsername, new { link.TrainingProviderId });
        return true;
    }

    public async Task<AssessorApplicationDocument> AttachDocumentAsync(int applicationId, string documentType, string title, string filePath, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.AssessorRegistrationApplications.FirstOrDefaultAsync(a => a.Id == applicationId);
        if (app == null)
            throw new KeyNotFoundException($"AssessorRegistrationApplication with ID {applicationId} not found.");

        var existingDocs = await db.AssessorApplicationDocuments
            .Where(d => d.AssessorRegistrationApplicationId == applicationId && d.DocumentTypeCode == documentType)
            .CountAsync();

        var doc = new AssessorApplicationDocument
        {
            AssessorRegistrationApplicationId = app.Id,
            DocumentTypeCode = documentType,
            DocumentTitle = title,
            FileStoragePath = filePath,
            VersionNumber = existingDocs + 1,
            IsVerified = false,
            CreatedBy = currentUsername
        };

        db.AssessorApplicationDocuments.Add(doc);
        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorApplicationDocument", doc.Id, "AttachDocument", currentUsername, new
        {
            applicationId,
            documentType,
            doc.VersionNumber
        });

        return doc;
    }

    public async Task<AssessorRegistrationApplication> SignOffAndSubmitAsync(int applicationId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.AssessorRegistrationApplications
            .Include(a => a.Scopes)
            .Include(a => a.Documents)
            .FirstOrDefaultAsync(a => a.Id == applicationId);

        if (app == null)
            throw new KeyNotFoundException($"AssessorRegistrationApplication with ID {applicationId} not found.");

        if (app.Scopes.Count == 0)
            throw new InvalidOperationException("Validation Error: Application must include at least one qualification scope before submission.");

        app.IsDeclarationAcknowledged = true;
        app.SignedOffByUserId = currentUsername;
        app.SignedOffAt = DateTime.UtcNow;
        app.ApplicationStatusCode = "VerificationPending";
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorRegistrationApplication", app.Id, "SignOffAndSubmit", currentUsername, new
        {
            app.ApplicationNumber,
            app.SignedOffAt,
            app.ApplicationStatusCode
        });

        return app;
    }

    public async Task<AssessorRegistrationApplication> WithdrawApplicationAsync(int applicationId, string reason, string? comments, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.AssessorRegistrationApplications.FirstOrDefaultAsync(a => a.Id == applicationId);
        if (app == null)
            throw new KeyNotFoundException($"AssessorRegistrationApplication with ID {applicationId} not found.");

        app.ApplicationStatusCode = "Withdrawn";
        app.WithdrawalReason = reason;
        app.WithdrawalComments = comments;
        app.WithdrawnAt = DateTime.UtcNow;
        app.WithdrawnByUserId = currentUsername;
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorRegistrationApplication", app.Id, "WithdrawApplication", currentUsername, new
        {
            app.ApplicationNumber,
            reason,
            app.WithdrawnAt
        });

        return app;
    }

    #region 4-Stage Maker-Checker Workflow

    public async Task<AssessorRegistrationApplication> VerifyDocumentAsync(VerificationStepRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.AssessorRegistrationApplications.FirstOrDefaultAsync(a => a.Id == request.ApplicationId);
        if (app == null)
            throw new KeyNotFoundException($"AssessorRegistrationApplication with ID {request.ApplicationId} not found.");

        app.VerificationRecommendation = request.Recommendation == "Recommend" ? "Recommended" : request.Recommendation;
        app.VerificationReason = request.Reason;
        app.VerificationExplanation = request.Explanation;
        app.VerifiedByUserId = currentUsername;
        app.VerificationDate = DateTime.UtcNow;

        if (request.Recommendation == "Recommend" || request.Recommendation == "Recommended")
        {
            app.ApplicationStatusCode = "EvaluationPending";
        }
        else
        {
            app.ApplicationStatusCode = "Rejected";
        }

        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorRegistrationApplication", app.Id, "DocumentVerification", currentUsername, new
        {
            request.Recommendation,
            request.Reason,
            NewStatus = app.ApplicationStatusCode
        });

        return app;
    }

    public async Task<AssessorRegistrationApplication> EvaluateApplicationAsync(EvaluationStepRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.AssessorRegistrationApplications.FirstOrDefaultAsync(a => a.Id == request.ApplicationId);
        if (app == null)
            throw new KeyNotFoundException($"AssessorRegistrationApplication with ID {request.ApplicationId} not found.");

        app.EvaluationRecommendation = request.Recommendation == "Recommend" ? "Recommended" : request.Recommendation;
        app.EvaluationReason = request.Reason;
        app.EvaluationExplanation = request.Explanation;
        app.EvaluatedByUserId = currentUsername;
        app.EvaluationDate = DateTime.UtcNow;

        if (request.Recommendation == "Recommend" || request.Recommendation == "Recommended")
        {
            app.ApplicationStatusCode = "ReviewCommitteePending";
        }
        else
        {
            app.ApplicationStatusCode = "Rejected";
        }

        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorRegistrationApplication", app.Id, "ApplicationEvaluation", currentUsername, new
        {
            request.Recommendation,
            request.Reason,
            NewStatus = app.ApplicationStatusCode
        });

        return app;
    }

    public async Task<AssessorRegistrationApplication> RecordCommitteeDecisionAsync(ReviewCommitteeDecisionRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.AssessorRegistrationApplications.FirstOrDefaultAsync(a => a.Id == request.ApplicationId);
        if (app == null)
            throw new KeyNotFoundException($"AssessorRegistrationApplication with ID {request.ApplicationId} not found.");

        app.ReviewCommitteeDecision = request.Decision == "Approve" ? "Approved" : request.Decision;
        app.ReviewCommitteeDecisionNumber = request.CommitteeDecisionNumber;
        app.ReviewCommitteeMeetingDate = request.MeetingDate;
        app.ReviewCommitteeNotes = request.Notes;
        app.IsFinalRejection = request.IsFinalRejection;
        app.RejectionReason = request.RejectionReason;
        app.RejectionComments = request.RejectionComments;

        if (request.Decision == "Approve")
        {
            app.ApplicationStatusCode = "ReviewCommitteeApproved";
        }
        else
        {
            app.ApplicationStatusCode = request.IsFinalRejection ? "RejectedApplication" : "RejectedForResubmission";
        }

        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorRegistrationApplication", app.Id, "ReviewCommitteeDecision", currentUsername, new
        {
            request.Decision,
            request.CommitteeDecisionNumber,
            request.IsFinalRejection,
            NewStatus = app.ApplicationStatusCode
        });

        return app;
    }

    public async Task<AssessorRegistrationApplication> FinalApproveAsync(FinalApprovalRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.AssessorRegistrationApplications
            .Include(a => a.Person)
            .Include(a => a.Scopes)
                .ThenInclude(s => s.UnitStandards)
            .Include(a => a.ProviderAffiliations)
            .FirstOrDefaultAsync(a => a.Id == request.ApplicationId);

        if (app == null)
            throw new KeyNotFoundException($"AssessorRegistrationApplication with ID {request.ApplicationId} not found.");

        if (app.ApplicationStatusCode != "ReviewCommitteeApproved" && app.ApplicationStatusCode != "ReviewCommitteePending")
            throw new InvalidOperationException($"Cannot approve application in status '{app.ApplicationStatusCode}'. Committee review must be completed first.");

        app.ApprovedByUserId = currentUsername;
        app.ApprovalDate = DateTime.UtcNow;
        app.ApprovalComments = request.ApprovalComments;
        app.ApplicationStatusCode = "Approved";

        // Generate Digital Security Seal
        string rawSeal = $"ETQA-REG:{app.Id}:{app.PersonId}:{app.PractitionerType}:{app.ApprovalDate:yyyyMMddHHmmss}";
        using var sha = SHA256.Create();
        byte[] hash = sha.ComputeHash(Encoding.UTF8.GetBytes(rawSeal));
        app.DigitalSecuritySeal = Convert.ToHexString(hash).ToLowerInvariant();

        // Generate Registration Number (e.g. ASS-2026-00042)
        string regPrefix = app.PractitionerType == "Moderator" ? "MOD" : "ASS";
        string regNumber = $"{regPrefix}-{DateTime.UtcNow.Year}-{app.Id:D5}";

        // Create or update registered EtqaAssessor record
        var assessor = new EtqaAssessor
        {
            PersonId = app.PersonId,
            RegistrationNumber = regNumber,
            EtqaRole = app.PractitionerType,
            DesignationTypeId = app.PractitionerType == "Moderator" ? "02" : "01",
            DesignationStructureStatusId = "01", // Registered
            EtqaId = "17", // merSETA
            EtqeDecisionNumber = app.ReviewCommitteeDecisionNumber,
            RegistrationStatusCode = "Registered",
            StartDate = DateTime.UtcNow.Date,
            EndDate = DateTime.UtcNow.Date.AddYears(3), // Strict 3-year statutory cycle
            IsActive = true,
            CreatedBy = currentUsername
        };

        // Attach Scopes and Unit Standards
        foreach (var scope in app.Scopes)
        {
            var registeredScope = new AssessorModeratorScope
            {
                SaqaQualificationId = scope.SaqaQualificationId,
                QualificationTitle = scope.QualificationTitle,
                RegistrationStatusCode = "REGISTERED",
                ExpiryDate = assessor.EndDate,
                CreatedBy = currentUsername
            };

            foreach (var us in scope.UnitStandards)
            {
                registeredScope.UnitStandards.Add(new AssessorUnitStandardScope
                {
                    UnitStandardCode = us.UnitStandardCode,
                    UnitStandardTitle = us.UnitStandardTitle,
                    NqfLevel = us.NqfLevel,
                    Credits = us.Credits,
                    IsPopulatedFromQualification = us.IsPopulatedFromQualification,
                    CreatedBy = currentUsername
                });
            }

            assessor.Scopes.Add(registeredScope);
        }

        // Attach Provider Affiliations
        foreach (var p in app.ProviderAffiliations)
        {
            assessor.ProviderAffiliations.Add(new AssessorProviderLink
            {
                TrainingProviderId = p.TrainingProviderId,
                SlaDocumentRef = p.SlaDocumentRef,
                IsActive = true,
                VerifiedDate = DateTime.UtcNow,
                CreatedBy = currentUsername
            });
        }

        db.EtqaAssessors.Add(assessor);
        await db.SaveChangesAsync();

        app.RegisteredAssessorId = assessor.Id;
        await db.SaveChangesAsync();

        await _audit.LogAsync("AssessorRegistrationApplication", app.Id, "FinalApproveAndRegister", currentUsername, new
        {
            app.ApplicationNumber,
            regNumber,
            assessorId = assessor.Id,
            app.DigitalSecuritySeal
        });

        return app;
    }

    #endregion
}
