using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IWorkplaceApprovalService
{
    Task<List<WorkplaceApproval>> GetAllAsync(string? search = null, string? status = null, int? organisationId = null);
    Task<WorkplaceApproval?> GetByIdAsync(int id);
    Task<List<WorkplaceApproval>> GetByOrganisationIdAsync(int organisationId);
    Task<WorkplaceApproval> CreateAsync(WorkplaceApproval approval, string currentUsername = "SYSTEM");
    Task<WorkplaceApproval> UpdateAsync(WorkplaceApproval approval, string currentUsername = "SYSTEM");
    Task<WorkplaceApproval> SaveAsync(WorkplaceApproval approval, string currentUsername = "SYSTEM");
    Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM");

    Task<WorkplaceApprovalMentor> AddMentorAsync(WorkplaceApprovalMentor mentor, string currentUsername = "SYSTEM");
    Task<bool> RemoveMentorAsync(int mentorId, string currentUsername = "SYSTEM");

    Task<WorkplaceApprovalToolList> AddToolItemAsync(WorkplaceApprovalToolList toolItem, string currentUsername = "SYSTEM");
    Task<bool> RemoveToolItemAsync(int toolItemId, string currentUsername = "SYSTEM");

    Task<WorkplaceApproval> ApproveWorkplaceAsync(int id, string recommendations, string currentUsername = "SYSTEM");
    Task<WorkplaceApproval> RejectWorkplaceAsync(int id, string reason, string currentUsername = "SYSTEM");

    // Statutory Spec NMok_19122022 Role-Neutral Methods
    Task<WorkplaceApproval> SubmitApplicationAsync(int id, string currentUsername = "SYSTEM");
    Task<WorkplaceApproval> VerifyWorkplaceAsync(
        int id,
        bool isSiteVisitRequired,
        string? visitJustification,
        DateTime? scheduledVisitDate,
        string? recommendationReason,
        string? recommendationExplanation,
        string? rejectionReason,
        string? rejectionExplanation,
        int? officerPersonId,
        string currentUsername = "SYSTEM");
    Task<WorkplaceApproval> EvaluateWorkplaceAsync(
        int id,
        bool isApproved,
        string? reason,
        string? explanation,
        int? decisionMakerPersonId,
        string currentUsername = "SYSTEM",
        int? validityYears = 3,
        bool allowSelfApprovalOverride = false);
    Task<WorkplaceApproval> WithdrawWorkplaceApprovalAsync(int id, string reason, string currentUsername = "SYSTEM");

    // Mentor Approval Lifecycle (Section 4.2.3 & Section 5)
    Task<WorkplaceApprovalMentor> ApproveMentorAsync(int mentorId, int verifiedByPersonId, string currentUsername = "SYSTEM");
    Task<WorkplaceApprovalMentor> RejectMentorAsync(int mentorId, string reason, int verifiedByPersonId, string currentUsername = "SYSTEM");

    // Re-Approval / Renewal Lifecycle (Section 5)
    Task<WorkplaceApproval> RenewWorkplaceApprovalAsync(int existingApprovalId, string currentUsername = "SYSTEM");

    // 360-Degree Relational Queries
    Task<List<Nsdms.Application.Common.Models.WpaLearnerDto>> GetPlacedLearnersAsync(int workplaceApprovalId);
    Task<List<Nsdms.Application.Common.Models.WpaSdpDto>> GetPartnerSdpsAsync(int workplaceApprovalId);
    Task<List<Nsdms.Application.Common.Models.WpaVisitDto>> GetVerificationVisitsAsync(int workplaceApprovalId);
    Task<MentorRatioEvaluationResult> GetRatioEvaluationAsync(int workplaceApprovalId);
}

public class WorkplaceApprovalService : IWorkplaceApprovalService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly IMentorRatioPolicyEngine _ratioEngine;
    private readonly IStorageService? _storageService;
    private readonly Nsdms.Application.Common.Interfaces.IPdfDocumentService? _pdfService;
    private readonly ISystemConfigurationService? _configService;

    public WorkplaceApprovalService(
        INsdmsDbContextFactory contextFactory,
        IAuditService audit,
        IMentorRatioPolicyEngine ratioEngine,
        IStorageService? storageService = null,
        Nsdms.Application.Common.Interfaces.IPdfDocumentService? pdfService = null,
        ISystemConfigurationService? configService = null)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _ratioEngine = ratioEngine;
        _storageService = storageService;
        _pdfService = pdfService;
        _configService = configService;
    }

    public async Task<MentorRatioEvaluationResult> GetRatioEvaluationAsync(int workplaceApprovalId)
    {
        return await _ratioEngine.EvaluateWorkplaceApprovalCapacityAsync(workplaceApprovalId);
    }

    public async Task<List<WorkplaceApproval>> GetAllAsync(string? search = null, string? status = null, int? organisationId = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.WorkplaceApprovals
            .Include(w => w.Organisation)
            .Include(w => w.OrganisationSite)
            .Include(w => w.ContactPerson)
            .Include(w => w.AssessorPerson)
            .Include(w => w.VerifiedByPerson)
            .Include(w => w.DecisionByPerson)
            .Include(w => w.Mentors).ThenInclude(m => m.Person)
            .Include(w => w.ToolItems)
            .AsQueryable();

        if (organisationId.HasValue && organisationId.Value > 0)
        {
            query = query.Where(w => w.OrganisationId == organisationId.Value);
        }

        if (!string.IsNullOrWhiteSpace(status))
        {
            query = query.Where(w => w.ApprovalStatusCode == status);
        }

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim();
            query = query.Where(w =>
                w.ApprovalNumber.Contains(s) ||
                w.QualificationTitle.Contains(s) ||
                (w.Organisation != null && (w.Organisation.CompanyName.Contains(s) || w.Organisation.SdlNumber.Contains(s))));
        }

        return await query
            .OrderByDescending(w => w.Id)
            .ToListAsync();
    }

    public async Task<WorkplaceApproval?> GetByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.WorkplaceApprovals
            .Include(w => w.Organisation)
            .Include(w => w.OrganisationSite)
            .Include(w => w.ContactPerson)
            .Include(w => w.AssessorPerson)
            .Include(w => w.VerifiedByPerson)
            .Include(w => w.DecisionByPerson)
            .Include(w => w.Mentors).ThenInclude(m => m.Person)
            .Include(w => w.ToolItems)
            .FirstOrDefaultAsync(w => w.Id == id);
    }

    public async Task<List<WorkplaceApproval>> GetByOrganisationIdAsync(int organisationId)
    {
        return await GetAllAsync(organisationId: organisationId);
    }

    public async Task<WorkplaceApproval> CreateAsync(WorkplaceApproval approval, string currentUsername = "SYSTEM")
    {
        if (approval.OrganisationId <= 0)
        {
            throw new ArgumentException("A valid OrganisationId is required.");
        }

        if (string.IsNullOrWhiteSpace(approval.QualificationTitle))
        {
            throw new ArgumentException("Qualification title is required for workplace approval.");
        }

        if (string.IsNullOrWhiteSpace(approval.ApprovalNumber))
        {
            approval.ApprovalNumber = $"WPA-{DateTime.UtcNow.Year}-{approval.OrganisationId}-{Guid.NewGuid().ToString("N")[..4].ToUpper()}";
        }

        if (string.IsNullOrWhiteSpace(approval.ApprovalStatusCode))
        {
            approval.ApprovalStatusCode = "Pending";
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        approval.CreatedAt = DateTime.UtcNow;
        approval.CreatedBy = currentUsername;

        db.WorkplaceApprovals.Add(approval);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "WorkplaceApproval", approval.Id, "Create", currentUsername, null, approval);
        await db.SaveChangesAsync();
        return approval;
    }

    public async Task<WorkplaceApproval> UpdateAsync(WorkplaceApproval approval, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.WorkplaceApprovals.FindAsync(approval.Id);
        if (existing == null) throw new KeyNotFoundException($"WorkplaceApproval with ID {approval.Id} not found.");

        var beforeState = new { existing.ApprovalNumber, existing.ApprovalStatusCode, existing.QualificationTitle, existing.InspectionDate };

        existing.QualificationTitle = approval.QualificationTitle;
        existing.SaqaQualificationId = approval.SaqaQualificationId;
        existing.OrganisationSiteId = approval.OrganisationSiteId;
        existing.ContactPersonId = approval.ContactPersonId;
        existing.AssessorPersonId = approval.AssessorPersonId;
        existing.InspectionDate = approval.InspectionDate;
        existing.ApprovalDate = approval.ApprovalDate;
        existing.ExpiryDate = approval.ExpiryDate;
        existing.ApprovalStatusCode = approval.ApprovalStatusCode;
        existing.Recommendations = approval.Recommendations;
        existing.TradeCode = approval.TradeCode;
        existing.IsRatioEnforced = approval.IsRatioEnforced;
        existing.CustomTradeRatio = approval.CustomTradeRatio;
        existing.MentorRatioExemptionNotes = approval.MentorRatioExemptionNotes;
        existing.LearningProgramTypeCode = approval.LearningProgramTypeCode;
        existing.RequiresWorkplaceApproval = approval.RequiresWorkplaceApproval;
        existing.IsSiteVisitRequired = approval.IsSiteVisitRequired;
        existing.SiteVisitJustification = approval.SiteVisitJustification;
        existing.InspectionDueDate = approval.InspectionDueDate;
        existing.VerificationRecommendationReason = approval.VerificationRecommendationReason;
        existing.VerificationRecommendationExplanation = approval.VerificationRecommendationExplanation;
        existing.VerificationRejectionReason = approval.VerificationRejectionReason;
        existing.VerificationRejectionExplanation = approval.VerificationRejectionExplanation;
        existing.VerifiedDate = approval.VerifiedDate;
        existing.VerifiedByPersonId = approval.VerifiedByPersonId;
        existing.ApprovalReason = approval.ApprovalReason;
        existing.ApprovalExplanation = approval.ApprovalExplanation;
        existing.RejectionReason = approval.RejectionReason;
        existing.RejectionExplanation = approval.RejectionExplanation;
        existing.DecisionDate = approval.DecisionDate;
        existing.DecisionByPersonId = approval.DecisionByPersonId;
        existing.IsNonMerSetaCompany = approval.IsNonMerSetaCompany;
        existing.HomeSetaName = approval.HomeSetaName;
        existing.HomeSetaAgreementRef = approval.HomeSetaAgreementRef;
        existing.IsActive = approval.IsActive;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceApproval", existing.Id, "Update", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();
        return existing;
    }

    public async Task<WorkplaceApproval> SaveAsync(WorkplaceApproval approval, string currentUsername = "SYSTEM")
    {
        if (approval.Id == 0) return await CreateAsync(approval, currentUsername);
        return await UpdateAsync(approval, currentUsername);
    }

    public async Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var item = await db.WorkplaceApprovals.Include(w => w.Mentors).Include(w => w.ToolItems).FirstOrDefaultAsync(w => w.Id == id);
        if (item == null) return false;

        var beforeState = new { item.Id, item.ApprovalNumber, item.QualificationTitle, item.OrganisationId };
        db.WorkplaceApprovals.Remove(item);
        _audit.LogAction(db, "WorkplaceApproval", id, "Delete", currentUsername, beforeState, null);
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<WorkplaceApprovalMentor> AddMentorAsync(WorkplaceApprovalMentor mentor, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        mentor.CreatedAt = DateTime.UtcNow;
        mentor.CreatedBy = currentUsername;

        db.WorkplaceApprovalMentors.Add(mentor);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "WorkplaceApprovalMentor", mentor.Id, "AddMentor", currentUsername, null, mentor);
        await db.SaveChangesAsync();
        return mentor;
    }

    public async Task<bool> RemoveMentorAsync(int mentorId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var mentor = await db.WorkplaceApprovalMentors.FindAsync(mentorId);
        if (mentor == null) return false;

        db.WorkplaceApprovalMentors.Remove(mentor);
        _audit.LogAction(db, "WorkplaceApprovalMentor", mentorId, "RemoveMentor", currentUsername, null, null);
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<WorkplaceApprovalToolList> AddToolItemAsync(WorkplaceApprovalToolList toolItem, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        toolItem.CreatedAt = DateTime.UtcNow;
        toolItem.CreatedBy = currentUsername;

        db.WorkplaceApprovalToolLists.Add(toolItem);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "WorkplaceApprovalToolList", toolItem.Id, "AddToolItem", currentUsername, null, toolItem);
        await db.SaveChangesAsync();
        return toolItem;
    }

    public async Task<bool> RemoveToolItemAsync(int toolItemId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var tool = await db.WorkplaceApprovalToolLists.FindAsync(toolItemId);
        if (tool == null) return false;

        db.WorkplaceApprovalToolLists.Remove(tool);
        _audit.LogAction(db, "WorkplaceApprovalToolList", toolItemId, "RemoveToolItem", currentUsername, null, null);
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<WorkplaceApproval> ApproveWorkplaceAsync(int id, string recommendations, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.WorkplaceApprovals.FindAsync(id);
        if (existing == null) throw new KeyNotFoundException($"WorkplaceApproval with ID {id} not found.");

        var beforeState = new { existing.ApprovalStatusCode, existing.ApprovalDate };
        existing.ApprovalStatusCode = "Approved";
        existing.ApprovalDate = DateTime.UtcNow;
        existing.ExpiryDate = DateTime.UtcNow.AddYears(3);
        existing.Recommendations = recommendations;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceApproval", existing.Id, "ApproveWorkplace", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();
        return existing;
    }

    public async Task<WorkplaceApproval> RejectWorkplaceAsync(int id, string reason, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.WorkplaceApprovals.FindAsync(id);
        if (existing == null) throw new KeyNotFoundException($"WorkplaceApproval with ID {id} not found.");

        var beforeState = new { existing.ApprovalStatusCode };
        existing.ApprovalStatusCode = "Rejected";
        existing.Recommendations = reason;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceApproval", existing.Id, "RejectWorkplace", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();
        return existing;
    }

    public async Task<WorkplaceApproval> SubmitApplicationAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.WorkplaceApprovals.FindAsync(id);
        if (existing == null) throw new KeyNotFoundException($"WorkplaceApproval with ID {id} not found.");

        var beforeState = new { existing.ApprovalStatusCode, existing.InspectionDueDate };
        var slaDays = _configService != null 
            ? await _configService.GetValueAsync<int>("WorkplaceApproval:InspectionSlaBusinessDays", 20) 
            : 20;

        existing.ApprovalStatusCode = "APPLICATION";
        existing.InspectionDueDate = AddBusinessDays(DateTime.UtcNow, slaDays);
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceApproval", existing.Id, "SubmitApplication", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();
        return existing;
    }

    public async Task<WorkplaceApproval> VerifyWorkplaceAsync(
        int id,
        bool isSiteVisitRequired,
        string? visitJustification,
        DateTime? scheduledVisitDate,
        string? recommendationReason,
        string? recommendationExplanation,
        string? rejectionReason,
        string? rejectionExplanation,
        int? officerPersonId,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.WorkplaceApprovals.FindAsync(id);
        if (existing == null) throw new KeyNotFoundException($"WorkplaceApproval with ID {id} not found.");

        var beforeState = new { existing.ApprovalStatusCode, existing.VerifiedDate };
        existing.IsSiteVisitRequired = isSiteVisitRequired;
        existing.SiteVisitJustification = visitJustification;
        if (scheduledVisitDate.HasValue) existing.InspectionDate = scheduledVisitDate.Value;
        existing.VerificationRecommendationReason = recommendationReason;
        existing.VerificationRecommendationExplanation = recommendationExplanation;
        existing.VerificationRejectionReason = rejectionReason;
        existing.VerificationRejectionExplanation = rejectionExplanation;
        existing.VerifiedDate = DateTime.UtcNow;
        existing.VerifiedByPersonId = officerPersonId;
        existing.ApprovalStatusCode = isSiteVisitRequired && scheduledVisitDate.HasValue ? "AUDIT_SCHEDULED" : "VERIFIED";
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceApproval", existing.Id, "VerifyWorkplace", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();

        // Statutory Spec Section 4.2.7 & Section 9: Auto-archive inspection report into Document Vault
        if (_pdfService != null && _storageService != null)
        {
            try
            {
                var pdfBytes = await _pdfService.GenerateWorkplaceApprovalReportPdfAsync(id);
                if (pdfBytes != null && pdfBytes.Length > 0)
                {
                    using var stream = new MemoryStream(pdfBytes);
                    var docMeta = await _storageService.UploadDocumentAsync(
                        "WorkplaceApproval",
                        id,
                        "ETQ-TP-054",
                        "Workplace Inspection Report (ETQ-TP-054)",
                        $"WPA_InspectionReport_{existing.ApprovalNumber ?? id.ToString()}_{DateTime.UtcNow:yyyyMMdd}.pdf",
                        stream,
                        "application/pdf",
                        officerPersonId?.ToString() ?? "OFFICER",
                        currentUsername);

                    if (docMeta != null)
                    {
                        await _storageService.VerifyDocumentAsync(docMeta.Id, officerPersonId?.ToString() ?? "OFFICER", "Auto-archived verified inspection report.");
                    }
                }
            }
            catch
            {
                // Non-blocking in decoupled/test contexts
            }
        }

        return existing;
    }

    public async Task<WorkplaceApproval> EvaluateWorkplaceAsync(
        int id,
        bool isApproved,
        string? reason,
        string? explanation,
        int? decisionMakerPersonId,
        string currentUsername = "SYSTEM",
        int? validityYears = 3,
        bool allowSelfApprovalOverride = false)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.WorkplaceApprovals.FindAsync(id);
        if (existing == null) throw new KeyNotFoundException($"WorkplaceApproval with ID {id} not found.");

        // Maker-Checker Segregation of Duties Enforcement (PFMA / Signed Spec Section 4.2.7)
        if (!allowSelfApprovalOverride && decisionMakerPersonId.HasValue && existing.VerifiedByPersonId.HasValue && decisionMakerPersonId.Value == existing.VerifiedByPersonId.Value)
        {
            throw new InvalidOperationException("Maker-Checker Segregation of Duties violation: The Verification Officer who inspected this workplace cannot act as the Approval Authority. Another authorised committee member or manager must evaluate and approve.");
        }

        var beforeState = new { existing.ApprovalStatusCode, existing.ApprovalDate };
        existing.DecisionDate = DateTime.UtcNow;
        existing.DecisionByPersonId = decisionMakerPersonId;

        if (isApproved)
        {
            var defaultValidity = _configService != null 
                ? await _configService.GetValueAsync<int>("WorkplaceApproval:DefaultValidityYears", 3) 
                : 3;
            var years = validityYears.HasValue && validityYears.Value is >= 1 and <= 5 ? validityYears.Value : defaultValidity;
            existing.ApprovalStatusCode = "APPROVED";
            existing.ApprovalDate = DateTime.UtcNow;
            existing.ExpiryDate = DateTime.UtcNow.AddYears(years);
            existing.ApprovalReason = reason;
            existing.ApprovalExplanation = explanation;
            existing.Recommendations = explanation ?? reason;
        }
        else
        {
            existing.ApprovalStatusCode = "REJECTED";
            existing.RejectionReason = reason;
            existing.RejectionExplanation = explanation;
            existing.Recommendations = explanation ?? reason;
        }

        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceApproval", existing.Id, "EvaluateWorkplace", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();
        return existing;
    }

    public async Task<WorkplaceApproval> WithdrawWorkplaceApprovalAsync(int id, string reason, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.WorkplaceApprovals.FindAsync(id);
        if (existing == null) throw new KeyNotFoundException($"WorkplaceApproval with ID {id} not found.");

        var beforeState = new { existing.ApprovalStatusCode, existing.IsActive };
        existing.ApprovalStatusCode = "WITHDRAWN";
        existing.IsActive = false;
        existing.RejectionReason = "Approval Withdrawn / Revoked";
        existing.RejectionExplanation = reason;
        existing.Recommendations = reason;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceApproval", existing.Id, "WithdrawWorkplaceApproval", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();
        return existing;
    }

    public async Task<WorkplaceApprovalMentor> ApproveMentorAsync(int mentorId, int verifiedByPersonId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var mentor = await db.WorkplaceApprovalMentors.FindAsync(mentorId);
        if (mentor == null) throw new KeyNotFoundException($"WorkplaceApprovalMentor with ID {mentorId} not found.");

        var beforeState = new { mentor.ApprovalStatusCode, mentor.VerifiedDate, mentor.VerifiedByPersonId };
        mentor.ApprovalStatusCode = "Approved";
        mentor.VerifiedDate = DateTime.UtcNow;
        mentor.VerifiedByPersonId = verifiedByPersonId;
        mentor.RejectionReason = null;
        mentor.ModifiedAt = DateTime.UtcNow;
        mentor.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceApprovalMentor", mentor.Id, "ApproveMentor", currentUsername, beforeState, mentor);
        await db.SaveChangesAsync();
        return mentor;
    }

    public async Task<WorkplaceApprovalMentor> RejectMentorAsync(int mentorId, string reason, int verifiedByPersonId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var mentor = await db.WorkplaceApprovalMentors.FindAsync(mentorId);
        if (mentor == null) throw new KeyNotFoundException($"WorkplaceApprovalMentor with ID {mentorId} not found.");

        var beforeState = new { mentor.ApprovalStatusCode, mentor.RejectionReason, mentor.VerifiedByPersonId };
        mentor.ApprovalStatusCode = "Rejected";
        mentor.RejectionReason = reason;
        mentor.VerifiedDate = DateTime.UtcNow;
        mentor.VerifiedByPersonId = verifiedByPersonId;
        mentor.ModifiedAt = DateTime.UtcNow;
        mentor.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceApprovalMentor", mentor.Id, "RejectMentor", currentUsername, beforeState, mentor);
        await db.SaveChangesAsync();
        return mentor;
    }

    public async Task<WorkplaceApproval> RenewWorkplaceApprovalAsync(int existingApprovalId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.WorkplaceApprovals
            .Include(w => w.Mentors)
            .Include(w => w.ToolItems)
            .FirstOrDefaultAsync(w => w.Id == existingApprovalId);

        if (existing == null)
            throw new KeyNotFoundException($"WorkplaceApproval with ID {existingApprovalId} not found.");

        var renewal = new WorkplaceApproval
        {
            OrganisationId = existing.OrganisationId,
            OrganisationSiteId = existing.OrganisationSiteId,
            ContactPersonId = existing.ContactPersonId,
            QualificationTitle = existing.QualificationTitle,
            SaqaQualificationId = existing.SaqaQualificationId,
            TradeCode = existing.TradeCode,
            LearningProgramTypeCode = existing.LearningProgramTypeCode,
            RequiresWorkplaceApproval = existing.RequiresWorkplaceApproval,
            IsRatioEnforced = existing.IsRatioEnforced,
            CustomTradeRatio = existing.CustomTradeRatio,
            MentorRatioExemptionNotes = existing.MentorRatioExemptionNotes,
            IsNonMerSetaCompany = existing.IsNonMerSetaCompany,
            HomeSetaName = existing.HomeSetaName,
            HomeSetaAgreementRef = existing.HomeSetaAgreementRef,
            ApprovalStatusCode = "APPLICATION",
            ApprovalNumber = $"WPA-REN-{DateTime.UtcNow.Year}-{existing.Id:D4}",
            InspectionDueDate = AddBusinessDays(DateTime.UtcNow, 20),
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername,
            IsActive = true
        };

        // Clone active mentors (set to Pending for re-verification upon renewal)
        foreach (var m in existing.Mentors.Where(x => x.IsActive))
        {
            renewal.Mentors.Add(new WorkplaceApprovalMentor
            {
                PersonId = m.PersonId,
                Designation = m.Designation,
                ArtisanTradeNumber = m.ArtisanTradeNumber,
                YearsExperience = m.YearsExperience,
                IsCertifiedArtisan = m.IsCertifiedArtisan,
                IsActive = true,
                MaxLearnerCapacity = m.MaxLearnerCapacity,
                IsRatioExempt = m.IsRatioExempt,
                IsRatioEnforced = m.IsRatioEnforced,
                Notes = m.Notes,
                ApprovalStatusCode = "Pending",
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            });
        }

        // Clone tooling inventory
        foreach (var t in existing.ToolItems)
        {
            renewal.ToolItems.Add(new WorkplaceApprovalToolList
            {
                ToolName = t.ToolName,
                Category = t.Category,
                RequiredQuantity = t.RequiredQuantity,
                AvailableQuantity = t.AvailableQuantity,
                Remarks = t.Remarks,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            });
        }

        db.WorkplaceApprovals.Add(renewal);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "WorkplaceApproval", renewal.Id, "RenewWorkplaceApproval", currentUsername, new { SourceApprovalId = existing.Id, existing.ApprovalNumber }, renewal);
        await db.SaveChangesAsync();

        return renewal;
    }

    public static DateTime AddBusinessDays(DateTime startDate, int businessDays)
    {
        var current = startDate;
        while (businessDays > 0)
        {
            current = current.AddDays(1);
            if (current.DayOfWeek != DayOfWeek.Saturday && 
                current.DayOfWeek != DayOfWeek.Sunday &&
                !SouthAfricanPublicHolidays.IsPublicHoliday(current))
            {
                businessDays--;
            }
        }
        return current;
    }

    #region 360-Degree Relational Queries

    public async Task<List<Nsdms.Application.Common.Models.WpaLearnerDto>> GetPlacedLearnersAsync(int workplaceApprovalId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var wpa = await db.WorkplaceApprovals.FindAsync(workplaceApprovalId);
        if (wpa == null) return new List<Nsdms.Application.Common.Models.WpaLearnerDto>();

        var query = db.CompanyLearners
            .Include(l => l.Person)
            .Where(l => l.OrganisationId == wpa.OrganisationId);

        if (wpa.OrganisationSiteId.HasValue)
        {
            query = query.Where(l => l.OrganisationSiteId == wpa.OrganisationSiteId.Value);
        }

        if (!string.IsNullOrWhiteSpace(wpa.QualificationTitle))
        {
            query = query.Where(l => l.QualificationTitle.Contains(wpa.QualificationTitle) || wpa.QualificationTitle.Contains(l.QualificationTitle));
        }

        var learners = await query.OrderByDescending(l => l.Id).ToListAsync();

        return learners.Select(l => new Nsdms.Application.Common.Models.WpaLearnerDto(
            l.Id,
            l.LearnerContractNumber,
            l.Person != null ? $"{l.Person.FirstName} {l.Person.LastName}".Trim() : "Placed Candidate",
            l.Person?.RsaIdNumber,
            l.QualificationTitle,
            GetProgrammeTypeName(l.LearningProgrammeTypeCode),
            l.EnrolmentStatusCode ?? "Active",
            l.RegistrationDate
        )).ToList();
    }

    public async Task<List<Nsdms.Application.Common.Models.WpaSdpDto>> GetPartnerSdpsAsync(int workplaceApprovalId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var wpa = await db.WorkplaceApprovals.FindAsync(workplaceApprovalId);
        if (wpa == null) return new List<Nsdms.Application.Common.Models.WpaSdpDto>();

        var providerIds = await db.CompanyLearners
            .Where(l => l.OrganisationId == wpa.OrganisationId && l.TrainingProviderId != null)
            .Select(l => l.TrainingProviderId!.Value)
            .Distinct()
            .ToListAsync();

        var providers = await db.TrainingProviders
            .Include(p => p.Organisation)
            .Include(p => p.PrimaryContactPerson)
            .Where(p => providerIds.Contains(p.Id))
            .ToListAsync();

        var list = new List<Nsdms.Application.Common.Models.WpaSdpDto>();
        foreach (var p in providers)
        {
            var count = await db.CompanyLearners.CountAsync(l => l.OrganisationId == wpa.OrganisationId && l.TrainingProviderId == p.Id);
            var contact = p.PrimaryContactPerson;
            list.Add(new Nsdms.Application.Common.Models.WpaSdpDto(
                p.Id,
                p.ProviderName,
                p.AccreditationNumber,
                contact != null ? $"{contact.FirstName} {contact.LastName}".Trim() : null,
                contact?.Email,
                count
            ));
        }
        return list;
    }

    public async Task<List<Nsdms.Application.Common.Models.WpaVisitDto>> GetVerificationVisitsAsync(int workplaceApprovalId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var wpa = await db.WorkplaceApprovals.FindAsync(workplaceApprovalId);
        if (wpa == null) return new List<Nsdms.Application.Common.Models.WpaVisitDto>();

        var visits = await db.Visits
            .Include(v => v.ContactPerson)
            .Where(v => v.OrganisationId == wpa.OrganisationId && (v.VisitTypeCode == "WorkplaceApproval" || v.VisitTypeCode == "SiteInspection" || v.Purpose.Contains("Workplace") || v.Purpose.Contains("WPA")))
            .OrderByDescending(v => v.VisitDate)
            .ToListAsync();

        return visits.Select(v => new Nsdms.Application.Common.Models.WpaVisitDto(
            v.Id,
            v.VisitDate,
            v.VisitTypeCode ?? "Workplace Approval Audit",
            v.VisitStatusCode ?? "Completed",
            v.ContactPerson != null ? $"{v.ContactPerson.FirstName} {v.ContactPerson.LastName}".Trim() : null,
            v.Location,
            v.Purpose,
            v.OutcomeNotes
        )).ToList();
    }

    private static string GetProgrammeTypeName(string? code) => code switch
    {
        "01" => "Apprenticeship",
        "02" => "Learnership",
        "03" => "Skills Programme",
        "04" => "Internship",
        "05" => "Bursary",
        "06" => "Candidacy",
        "07" => "ARPL",
        _ => code ?? "Apprenticeship"
    };

    #endregion
}

/// <summary>
/// South African statutory public holidays per Public Holidays Act 36 of 1994.
/// Accurately excludes gazetted national holidays and Sunday rollover observances from SLA business day calculations.
/// </summary>
public static class SouthAfricanPublicHolidays
{
    public static HashSet<DateTime> GetPublicHolidays(int year)
    {
        var holidays = new HashSet<DateTime>();

        void AddHoliday(DateTime date)
        {
            holidays.Add(date.Date);
            if (date.DayOfWeek == DayOfWeek.Sunday)
            {
                // Section 2(1) Public Holidays Act 36 of 1994: Whenever any public holiday falls upon a Sunday, the following Monday shall be a public holiday.
                holidays.Add(date.AddDays(1).Date);
            }
        }

        // Fixed Statutory Holidays
        AddHoliday(new DateTime(year, 1, 1));   // New Year's Day
        AddHoliday(new DateTime(year, 3, 21));  // Human Rights Day
        AddHoliday(new DateTime(year, 4, 27));  // Freedom Day
        AddHoliday(new DateTime(year, 5, 1));   // Workers' Day
        AddHoliday(new DateTime(year, 6, 16));  // Youth Day
        AddHoliday(new DateTime(year, 8, 9));   // National Women's Day
        AddHoliday(new DateTime(year, 9, 24));  // Heritage Day
        AddHoliday(new DateTime(year, 12, 16)); // Day of Reconciliation
        AddHoliday(new DateTime(year, 12, 25)); // Christmas Day
        AddHoliday(new DateTime(year, 12, 26)); // Day of Goodwill

        // Special Sunday rule for Christmas / Day of Goodwill
        if (new DateTime(year, 12, 25).DayOfWeek == DayOfWeek.Sunday)
        {
            // 26 Dec (Monday) is Day of Goodwill, so Tuesday 27 Dec becomes the observed holiday for Christmas
            holidays.Add(new DateTime(year, 12, 27));
        }

        // Variable Easter Holidays (Computus algorithm)
        var easterSunday = GetEasterSunday(year);
        holidays.Add(easterSunday.AddDays(-2).Date); // Good Friday
        holidays.Add(easterSunday.AddDays(1).Date);  // Family Day

        return holidays;
    }

    public static bool IsPublicHoliday(DateTime date)
    {
        var holidays = GetPublicHolidays(date.Year);
        return holidays.Contains(date.Date);
    }

    private static DateTime GetEasterSunday(int year)
    {
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int month = (h + l - 7 * m + 114) / 31;
        int day = ((h + l - 7 * m + 114) % 31) + 1;
        return new DateTime(year, month, day);
    }
}

