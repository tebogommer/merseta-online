using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class ExtensionOfScopeService : IExtensionOfScopeService
{
    private readonly INsdmsDbContextFactory _factory;
    private readonly AuditService _audit;

    public ExtensionOfScopeService(INsdmsDbContextFactory factory, AuditService audit)
    {
        _factory = factory;
        _audit = audit;
    }

    public async Task<List<SdpExtensionOfScope>> GetSdpScopeExtensionsAsync()
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.SdpExtensionOfScopes
            .Include(s => s.TrainingProvider)
            .OrderByDescending(s => s.CreatedAt)
            .ToListAsync();
    }

    public async Task<SdpExtensionOfScope?> GetSdpScopeExtensionByIdAsync(int id)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.SdpExtensionOfScopes
            .Include(s => s.TrainingProvider)
            .FirstOrDefaultAsync(s => s.Id == id);
    }

    public async Task<SdpExtensionOfScope> SubmitSdpScopeExtensionAsync(int trainingProviderId, string qualificationTitle, string? saqaId, int nqfLevel, int credits, string programmeTypeCode, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var count = await db.SdpExtensionOfScopes.CountAsync() + 1;
        var entity = new SdpExtensionOfScope
        {
            TrainingProviderId = trainingProviderId,
            ApplicationNumber = $"SDP-EOS-2026-{count:D4}",
            AdditionalQualificationTitle = qualificationTitle,
            SaqaQualificationId = saqaId,
            NqfLevel = nqfLevel,
            Credits = credits,
            ProgrammeTypeCode = programmeTypeCode,
            StatusCode = "Submitted",
            CreatedBy = currentUsername,
            CreatedAt = DateTime.UtcNow
        };

        db.SdpExtensionOfScopes.Add(entity);
        await db.SaveChangesAsync();
        await _audit.LogAsync("SdpExtensionOfScope", entity.Id, "SubmitSdpScopeExtension", currentUsername, new { entity.ApplicationNumber, qualificationTitle });
        return entity;
    }

    public async Task<SdpExtensionOfScope> RecordSiteInspectionAsync(int id, bool passed, string evaluatorUserId)
    {
        using var db = await _factory.CreateDbContextAsync();
        var entity = await db.SdpExtensionOfScopes.FindAsync(id) ?? throw new InvalidOperationException($"Scope extension #{id} not found.");

        entity.SiteInspectionPassed = passed;
        entity.SiteInspectionDate = DateTime.UtcNow;
        entity.SiteEvaluatorUserId = evaluatorUserId;
        entity.StatusCode = passed ? "CommitteeReview" : "Rejected";
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = evaluatorUserId;

        await db.SaveChangesAsync();
        await _audit.LogAsync("SdpExtensionOfScope", entity.Id, "RecordSiteInspection", evaluatorUserId, new { passed, entity.StatusCode });
        return entity;
    }

    public async Task<SdpExtensionOfScope> ApproveSdpScopeExtensionAsync(int id, string decisionRef, string approvedByUserId)
    {
        using var db = await _factory.CreateDbContextAsync();
        var entity = await db.SdpExtensionOfScopes.FindAsync(id) ?? throw new InvalidOperationException($"Scope extension #{id} not found.");

        entity.StatusCode = "Approved";
        entity.CommitteeDecisionReference = decisionRef;
        entity.ApprovedByUserId = approvedByUserId;
        entity.ApprovalDate = DateTime.UtcNow;
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = approvedByUserId;

        await db.SaveChangesAsync();
        await _audit.LogAsync("SdpExtensionOfScope", entity.Id, "ApproveSdpScopeExtension", approvedByUserId, new { entity.StatusCode, decisionRef });
        return entity;
    }

    public async Task<List<SdpReAccreditationApplication>> GetReAccreditationApplicationsAsync()
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.SdpReAccreditationApplications
            .Include(r => r.TrainingProvider)
            .OrderByDescending(r => r.CreatedAt)
            .ToListAsync();
    }

    public async Task<SdpReAccreditationApplication> SubmitReAccreditationAsync(int trainingProviderId, DateTime currentExpiry, DateTime proposedExpiry, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var count = await db.SdpReAccreditationApplications.CountAsync() + 1;
        var entity = new SdpReAccreditationApplication
        {
            TrainingProviderId = trainingProviderId,
            ApplicationNumber = $"REACC-2026-{count:D4}",
            CurrentAccreditationExpiryDate = currentExpiry,
            ProposedAccreditationExpiryDate = proposedExpiry,
            QmsComplianceAudited = true,
            FacilitatorAssessorRatiosCompliant = true,
            OshSafetyCertificatesValid = true,
            StatusCode = "UnderReview",
            CreatedBy = currentUsername,
            CreatedAt = DateTime.UtcNow
        };

        db.SdpReAccreditationApplications.Add(entity);
        await db.SaveChangesAsync();
        await _audit.LogAsync("SdpReAccreditationApplication", entity.Id, "SubmitReAccreditation", currentUsername, new { entity.ApplicationNumber });
        return entity;
    }

    public async Task<SdpReAccreditationApplication> EndorseReAccreditationAsync(int id, string decisionNumber, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var entity = await db.SdpReAccreditationApplications.FindAsync(id) ?? throw new InvalidOperationException($"Re-accreditation #{id} not found.");

        entity.StatusCode = "ReAccredited";
        entity.CouncilDecisionNumber = decisionNumber;
        entity.ReAccreditationDecisionDate = DateTime.UtcNow;
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();
        await _audit.LogAsync("SdpReAccreditationApplication", entity.Id, "EndorseReAccreditation", currentUsername, new { entity.StatusCode, decisionNumber });
        return entity;
    }

    public async Task<List<AssessorExtensionOfScope>> GetAssessorScopeExtensionsAsync()
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.AssessorExtensionOfScopes
            .Include(a => a.AssessorPerson)
            .OrderByDescending(a => a.CreatedAt)
            .ToListAsync();
    }

    public async Task<AssessorExtensionOfScope> SubmitAssessorScopeExtensionAsync(int assessorPersonId, string practitionerType, string qualTitle, string? saqaId, int nqfLevel, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var count = await db.AssessorExtensionOfScopes.CountAsync() + 1;
        var entity = new AssessorExtensionOfScope
        {
            AssessorPersonId = assessorPersonId,
            ApplicationNumber = $"AM-EOS-2026-{count:D4}",
            PractitionerTypeCode = practitionerType,
            RequestedQualificationTitle = qualTitle,
            SaqaQualificationId = saqaId,
            NqfLevel = nqfLevel,
            RelevantIndustryCvVerified = true,
            SubjectMatterCertificateVerified = true,
            StatusCode = "Submitted",
            CreatedBy = currentUsername,
            CreatedAt = DateTime.UtcNow
        };

        db.AssessorExtensionOfScopes.Add(entity);
        await db.SaveChangesAsync();
        await _audit.LogAsync("AssessorExtensionOfScope", entity.Id, "SubmitAssessorScopeExtension", currentUsername, new { entity.ApplicationNumber, qualTitle });
        return entity;
    }

    public async Task<AssessorExtensionOfScope> EndorseAssessorScopeExtensionAsync(int id, string endorsedByUserId)
    {
        using var db = await _factory.CreateDbContextAsync();
        var entity = await db.AssessorExtensionOfScopes.FindAsync(id) ?? throw new InvalidOperationException($"Assessor scope extension #{id} not found.");

        entity.StatusCode = "ScopeApproved";
        entity.EndorsedByUserId = endorsedByUserId;
        entity.EndorsementDate = DateTime.UtcNow;
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = endorsedByUserId;

        await db.SaveChangesAsync();
        await _audit.LogAsync("AssessorExtensionOfScope", entity.Id, "EndorseAssessorScopeExtension", endorsedByUserId, new { entity.StatusCode });
        return entity;
    }
}
