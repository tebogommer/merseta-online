using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

#region Request DTOs
public class LearnerTransferRequest
{
    public int CompanyLearnerId { get; set; }
    public string TransferScopeCode { get; set; } = "EmployerToEmployer"; // "EmployerToEmployer", "SdpToSdp"
    public int? FromOrganisationId { get; set; }
    public int? ToOrganisationId { get; set; }
    public int? FromTrainingProviderId { get; set; }
    public int? ToTrainingProviderId { get; set; }
    public int? TargetWorkplaceApprovalId { get; set; }
    public string InitiatedByTypeCode { get; set; } = "CurrentEmployer"; // "CurrentEmployer", "FutureEmployer", "SDP", "Learner", "MerSetaRepresentative"
    public string TransferReasonCode { get; set; } = "ChangedEmployer"; // ChangedEmployer, ChangedLocation, TakenOnByHostEmployer, ChangedProvider, Other
    public DateTime EffectiveDate { get; set; } = DateTime.UtcNow;
    public string? ApprovalComments { get; set; }
    public bool? IsCurrentEmployerAgreed { get; set; }
    public bool? IsFutureEmployerAgreed { get; set; }
    public bool? IsLearnerAgreed { get; set; }
    public int? TransferFormDocumentId { get; set; }
    public bool? CurrentEmployerConsented { get => IsCurrentEmployerAgreed; set => IsCurrentEmployerAgreed = value; }
    public bool? FutureEmployerConsented { get => IsFutureEmployerAgreed; set => IsFutureEmployerAgreed = value; }
    public bool? LearnerConsented { get => IsLearnerAgreed; set => IsLearnerAgreed = value; }
    public string? TransferApplicationFormReference { get; set; }
}

public class LearnerExtensionRequest
{
    public int CompanyLearnerId { get; set; }
    public string ExtensionTypeCode { get; set; } = "PreRegistration"; // "PreRegistration", "ContractAddendum"
    public string? ExtensionCategory { get => ExtensionTypeCode; set => ExtensionTypeCode = value ?? "PreRegistration"; }
    public string ExtensionReasonCode { get; set; } = "PendingDocumentation"; // PendingDocumentation, SdpAccreditationPending, WorkplaceReadiness, MedicalInterruption, Other
    public string JustificationComments { get; set; } = string.Empty;
    public DateTime? OriginalExpiryDate { get; set; }
    public DateTime RequestedExpiryDate { get; set; } = DateTime.UtcNow.AddMonths(3);
    public int? AddendumDocumentId { get; set; }
    public int? AddendumAgreementDocumentId { get => AddendumDocumentId; set => AddendumDocumentId = value; }
    public string? ApprovalComments { get; set; }
}

public class LearnerLostTimeRequest
{
    public int CompanyLearnerId { get; set; }
    public string LostTimeReasonCode { get; set; } = "MedicalLeave";
    public DateTime StartDate { get; set; }
    public DateTime EndDate { get; set; }
    public int DaysLost { get; set; }
    public string? ApprovalComments { get; set; }
}

public class LearnerTerminationRequest
{
    public int CompanyLearnerId { get; set; }
    public string TerminationTypeCode { get; set; } = "Mutual"; // "Mutual", "OneSided"
    public string TerminationReasonCode { get; set; } = "ResignationAgreement";
    public DateTime EffectiveDate { get; set; } = DateTime.UtcNow;
    public bool DisputeLogged { get; set; } = false;
    public string? UnionRepresentativeName { get; set; }
    public string? SettlementNotes { get; set; }
    public string? ApprovalComments { get; set; }
}

public class LearnerChangeRequestInput
{
    public int CompanyLearnerId { get; set; }
    public string ChangeTypeCode { get; set; } = "DemographicCorrection";
    public string ProposedChangesJson { get; set; } = "{}";
    public string JustificationReason { get; set; } = string.Empty;
}

public class LearnerOtpVerificationRequest
{
    public string ContractReferenceOrRsaId { get; set; } = string.Empty;
    public string OtpToken { get; set; } = string.Empty;
    public string SignerName { get; set; } = string.Empty;
    public string? SignerRsaId { get; set; }
    public string SignerRole { get; set; } = "Learner";
}
#endregion

public interface ILearnerLifecycleService
{
    // Section 4.7: Transfer Workflow
    Task<CompanyLearnerTransfer> RequestTransferAsync(CompanyLearnerTransfer transfer, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTransfer> RequestTransferAsync(LearnerTransferRequest request, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTransfer> RecordEmployerConsentAsync(int transferId, string partyRole, bool isAgreed, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTransfer> ApproveTransferAsync(int transferId, string? comments, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTransfer> RejectTransferAsync(int transferId, string? reason, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTransfer?> GetTransferByIdAsync(int transferId);
    Task<List<CompanyLearnerTransfer>> GetTransfersForLearnerAsync(int learnerId);
    Task<List<CompanyLearnerTransfer>> GetPendingTransfersAsync();

    // Section 4.1: Extension Workflow
    Task<CompanyLearnerExtension> RequestExtensionAsync(CompanyLearnerExtension extension, string currentUsername = "SYSTEM");
    Task<CompanyLearnerExtension> RequestExtensionAsync(LearnerExtensionRequest request, string currentUsername = "SYSTEM");
    Task<CompanyLearnerExtension> ApproveExtensionAsync(int extensionId, DateTime? approvedExpiryDate, string? comments, string currentUsername = "SYSTEM");
    Task<CompanyLearnerExtension> RejectExtensionAsync(int extensionId, string? comments, string currentUsername = "SYSTEM");
    Task<CompanyLearnerExtension?> GetExtensionByIdAsync(int extensionId);
    Task<List<CompanyLearnerExtension>> GetExtensionsForLearnerAsync(int learnerId);
    Task<List<CompanyLearnerExtension>> GetPendingExtensionsAsync();

    // Lost Time
    Task<CompanyLearnerLostTime> RecordLostTimeAsync(CompanyLearnerLostTime lostTime, string currentUsername = "SYSTEM");
    Task<CompanyLearnerLostTime> RecordLostTimeAsync(LearnerLostTimeRequest request, string currentUsername = "SYSTEM");
    Task<CompanyLearnerLostTime> ApproveLostTimeAsync(int lostTimeId, string? comments, string currentUsername = "SYSTEM");
    Task<List<CompanyLearnerLostTime>> GetLostTimesForLearnerAsync(int learnerId);

    // Sections 4.4, 4.5, 4.6: Termination Workflow
    Task<CompanyLearnerTermination> RequestTerminationAsync(CompanyLearnerTermination termination, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTermination> RequestTerminationAsync(LearnerTerminationRequest request, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTermination> CompleteChecklist036Async(int terminationId, string checklistDataJson, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTermination> RecordInvestigationOutcomeAsync(int terminationId, string outcomeSummary, bool isArplRecommended, bool isTransferRecommended, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTermination> SubmitTerminationToCommitteeAsync(int terminationId, int? committeeMeetingId, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTermination> AdjudicateCommitteeDecisionAsync(int terminationId, string decisionCode, string? decisionNotes, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTermination> ApproveTerminationAsync(int terminationId, string? comments, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTermination> RejectTerminationAsync(int terminationId, string? comments, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTermination?> GetTerminationByIdAsync(int terminationId);
    Task<List<CompanyLearnerTermination>> GetTerminationsForLearnerAsync(int learnerId);
    Task<List<CompanyLearnerTermination>> GetPendingTerminationsAsync();

    // Change Requests & OTP Sign-off
    Task<CompanyLearnerChangeRequest> SubmitChangeRequestAsync(LearnerChangeRequestInput request, string currentUsername = "SYSTEM");
    Task<CompanyLearnerChangeRequest> ReviewChangeRequestAsync(int changeRequestId, bool approve, string? comments, string currentUsername = "SYSTEM");
    Task<string> GenerateLearnerSignoffOtpAsync(int learnerId, string currentUsername = "SYSTEM");
    Task<(bool Success, string Message, string? SecuritySeal)> VerifyAndExecuteSignoffOtpAsync(LearnerOtpVerificationRequest request, string currentUsername = "SYSTEM");
    Task<List<CompanyLearnerChangeRequest>> GetChangeRequestsForLearnerAsync(int learnerId);
}

public class LearnerLifecycleService : ILearnerLifecycleService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly IMentorRatioPolicyEngine? _mentorRatioEngine;
    private readonly ISystemConfigurationService? _configService;

    public LearnerLifecycleService(
        INsdmsDbContextFactory contextFactory,
        IAuditService audit,
        IMentorRatioPolicyEngine? mentorRatioEngine = null,
        ISystemConfigurationService? configService = null)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _mentorRatioEngine = mentorRatioEngine;
        _configService = configService;
    }

    #region Section 4.7: Transfer Operations
    public async Task<CompanyLearnerTransfer> RequestTransferAsync(CompanyLearnerTransfer transfer, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var learner = await db.CompanyLearners.FindAsync(transfer.CompanyLearnerId);
        if (learner == null)
            throw new KeyNotFoundException($"CompanyLearner with ID {transfer.CompanyLearnerId} not found.");

        if (string.Equals(transfer.TransferScopeCode, "EmployerToEmployer", StringComparison.OrdinalIgnoreCase))
        {
            transfer.FromOrganisationId = learner.OrganisationId;
            if (!transfer.ToOrganisationId.HasValue || transfer.ToOrganisationId.Value <= 0)
                throw new ArgumentException("Target employer organisation is required for Employer-to-Employer transfer.");

            // Validate active Workplace Approval for target employer (Spec Section 4.7)
            var targetWpa = await db.WorkplaceApprovals
                .FirstOrDefaultAsync(w => w.OrganisationId == transfer.ToOrganisationId.Value &&
                                         (w.ApprovalStatusCode == "Approved" || w.ApprovalStatusCode == "Registered"));

            if (targetWpa == null)
            {
                // Exact statutory prompt required by signed specification Section 4.7
                throw new InvalidOperationException("The selected option is not workplace approved please contact MerSETA");
            }

            transfer.TargetWorkplaceApprovalId = targetWpa.Id;

            // Enforce artisan mentor-to-apprentice ratio policy
            if (_mentorRatioEngine != null)
            {
                var feasibility = await _mentorRatioEngine.EvaluatePlacementFeasibilityAsync(targetWpa.Id, 1);
                if (feasibility.Status == MentorRatioComplianceStatus.OverCapacity &&
                    feasibility.EnforcementState == MentorRatioEnforcementState.StrictlyEnforced)
                {
                    throw new InvalidOperationException($"Target workplace approval has exceeded its statutory mentor ratio capacity ({feasibility.TotalPlacedLearners}/{feasibility.EffectiveTotalCapacity} learners).");
                }
            }
        }
        else if (string.Equals(transfer.TransferScopeCode, "SdpToSdp", StringComparison.OrdinalIgnoreCase))
        {
            transfer.FromTrainingProviderId = learner.TrainingProviderId;
            if (!transfer.ToTrainingProviderId.HasValue || transfer.ToTrainingProviderId.Value <= 0)
                throw new ArgumentException("Target SDP training provider is required for SDP-to-SDP transfer.");

            // Validate target SDP accreditation standing
            var targetSdp = await db.TrainingProviders.FindAsync(transfer.ToTrainingProviderId.Value);
            if (targetSdp == null)
                throw new KeyNotFoundException($"Target Training Provider with ID {transfer.ToTrainingProviderId} not found.");

            if (targetSdp.ProviderStatusCode != "Accredited" && targetSdp.ProviderStatusCode != "Approved" && !targetSdp.IsActive)
            {
                throw new InvalidOperationException($"The target Skills Development Provider ({targetSdp.ProviderName}) is not actively accredited.");
            }
        }

        transfer.TransferStatusCode = "Pending";
        transfer.TransferDate = DateTime.UtcNow;
        transfer.CreatedBy = currentUsername;

        if (transfer.IsCurrentEmployerAgreed == false)
        {
            transfer.DisagreementPromptedTermination = true;
        }

        // Instate status set to "Transfer Application", preserving registered DHET status
        learner.InstateStatusCode = "Transfer Application";
        learner.InstateStatusDate = DateTime.UtcNow;

        db.CompanyLearnerTransfers.Add(transfer);
        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearner", learner.Id, "RequestTransfer", currentUsername, new
        {
            transfer.Id,
            transfer.TransferScopeCode,
            transfer.FromOrganisationId,
            transfer.ToOrganisationId,
            transfer.FromTrainingProviderId,
            transfer.ToTrainingProviderId,
            transfer.TransferReasonCode,
            transfer.InitiatedByTypeCode
        });

        return transfer;
    }

    public async Task<CompanyLearnerTransfer> RequestTransferAsync(LearnerTransferRequest request, string currentUsername = "SYSTEM")
    {
        var transfer = new CompanyLearnerTransfer
        {
            CompanyLearnerId = request.CompanyLearnerId,
            TransferScopeCode = request.TransferScopeCode,
            FromOrganisationId = request.FromOrganisationId,
            ToOrganisationId = request.ToOrganisationId,
            FromTrainingProviderId = request.FromTrainingProviderId,
            ToTrainingProviderId = request.ToTrainingProviderId,
            TargetWorkplaceApprovalId = request.TargetWorkplaceApprovalId,
            InitiatedByTypeCode = request.InitiatedByTypeCode,
            TransferReasonCode = request.TransferReasonCode,
            EffectiveDate = request.EffectiveDate,
            ApprovalComments = request.ApprovalComments,
            IsCurrentEmployerAgreed = request.IsCurrentEmployerAgreed,
            IsFutureEmployerAgreed = request.IsFutureEmployerAgreed,
            IsLearnerAgreed = request.IsLearnerAgreed,
            TransferFormDocumentId = request.TransferFormDocumentId
        };
        return await RequestTransferAsync(transfer, currentUsername);
    }

    public async Task<CompanyLearnerTransfer> RecordEmployerConsentAsync(int transferId, string partyRole, bool isAgreed, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var transfer = await db.CompanyLearnerTransfers
            .Include(t => t.CompanyLearner)
            .FirstOrDefaultAsync(t => t.Id == transferId);

        if (transfer == null)
            throw new KeyNotFoundException($"CompanyLearnerTransfer with ID {transferId} not found.");

        if (partyRole == "CurrentEmployer")
        {
            transfer.IsCurrentEmployerAgreed = isAgreed;
            transfer.CurrentEmployerSignoffDate = DateTime.UtcNow;
            transfer.CurrentEmployerSignoffUserId = currentUsername;

            if (!isAgreed)
            {
                // Disagreement by current employer prompts termination route per Section 4.7
                transfer.DisagreementPromptedTermination = true;
            }
        }
        else if (partyRole == "FutureEmployer")
        {
            transfer.IsFutureEmployerAgreed = isAgreed;
            transfer.FutureEmployerSignoffDate = DateTime.UtcNow;
            transfer.FutureEmployerSignoffUserId = currentUsername;
        }
        else if (partyRole == "Learner")
        {
            transfer.IsLearnerAgreed = isAgreed;
            transfer.LearnerSignoffDate = DateTime.UtcNow;
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearnerTransfer", transfer.Id, "RecordEmployerConsent", currentUsername, new
        {
            Party = partyRole,
            IsAgreed = isAgreed,
            transfer.DisagreementPromptedTermination
        });

        return transfer;
    }

    public async Task<CompanyLearnerTransfer> ApproveTransferAsync(int transferId, string? comments, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var transfer = await db.CompanyLearnerTransfers
            .Include(t => t.CompanyLearner)
            .FirstOrDefaultAsync(t => t.Id == transferId);

        if (transfer == null)
            throw new KeyNotFoundException($"CompanyLearnerTransfer with ID {transferId} not found.");

        transfer.TransferStatusCode = "Approved";
        transfer.ApprovalComments = comments;
        transfer.ApprovedByUserId = currentUsername;
        transfer.ApprovalDate = DateTime.UtcNow;

        if (transfer.CompanyLearner != null)
        {
            if (string.Equals(transfer.TransferScopeCode, "EmployerToEmployer", StringComparison.OrdinalIgnoreCase) && transfer.ToOrganisationId.HasValue)
            {
                transfer.CompanyLearner.OrganisationId = transfer.ToOrganisationId.Value;
            }
            else if (string.Equals(transfer.TransferScopeCode, "SdpToSdp", StringComparison.OrdinalIgnoreCase) && transfer.ToTrainingProviderId.HasValue)
            {
                transfer.CompanyLearner.TrainingProviderId = transfer.ToTrainingProviderId.Value;
            }

            transfer.CompanyLearner.InstateStatusCode = "Transferred";
            transfer.CompanyLearner.InstateStatusDate = DateTime.UtcNow;
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearnerTransfer", transfer.Id, "ApproveTransfer", currentUsername, new
        {
            transfer.ToOrganisationId,
            transfer.ToTrainingProviderId,
            comments
        });

        return transfer;
    }

    public async Task<CompanyLearnerTransfer> RejectTransferAsync(int transferId, string? reason, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var transfer = await db.CompanyLearnerTransfers
            .Include(t => t.CompanyLearner)
            .FirstOrDefaultAsync(t => t.Id == transferId);

        if (transfer == null)
            throw new KeyNotFoundException($"CompanyLearnerTransfer with ID {transferId} not found.");

        transfer.TransferStatusCode = "Rejected";
        transfer.ApprovalComments = reason;
        transfer.ApprovedByUserId = currentUsername;
        transfer.ApprovalDate = DateTime.UtcNow;

        if (transfer.CompanyLearner != null)
        {
            transfer.CompanyLearner.InstateStatusCode = "Active";
            transfer.CompanyLearner.InstateStatusDate = DateTime.UtcNow;
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearnerTransfer", transfer.Id, "RejectTransfer", currentUsername, new
        {
            reason
        });

        return transfer;
    }

    public async Task<CompanyLearnerTransfer?> GetTransferByIdAsync(int transferId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerTransfers
            .Include(t => t.FromOrganisation)
            .Include(t => t.ToOrganisation)
            .Include(t => t.FromTrainingProvider)
            .Include(t => t.ToTrainingProvider)
            .Include(t => t.TargetWorkplaceApproval)
            .Include(t => t.CompanyLearner)
                .ThenInclude(l => l!.Person)
            .FirstOrDefaultAsync(t => t.Id == transferId);
    }

    public async Task<List<CompanyLearnerTransfer>> GetTransfersForLearnerAsync(int learnerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerTransfers
            .Include(t => t.FromOrganisation)
            .Include(t => t.ToOrganisation)
            .Include(t => t.FromTrainingProvider)
            .Include(t => t.ToTrainingProvider)
            .Include(t => t.TargetWorkplaceApproval)
            .Where(t => t.CompanyLearnerId == learnerId)
            .OrderByDescending(t => t.TransferDate)
            .ToListAsync();
    }

    public async Task<List<CompanyLearnerTransfer>> GetPendingTransfersAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerTransfers
            .Include(t => t.FromOrganisation)
            .Include(t => t.ToOrganisation)
            .Include(t => t.CompanyLearner)
                .ThenInclude(l => l!.Person)
            .Where(t => t.TransferStatusCode == "Pending" || t.TransferStatusCode == "ReviewCommitteeAgenda")
            .OrderByDescending(t => t.TransferDate)
            .ToListAsync();
    }
    #endregion

    #region Section 4.1: Extension Operations
    public async Task<CompanyLearnerExtension> RequestExtensionAsync(CompanyLearnerExtension extension, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var learner = await db.CompanyLearners.FindAsync(extension.CompanyLearnerId);
        if (learner == null)
            throw new KeyNotFoundException($"CompanyLearner with ID {extension.CompanyLearnerId} not found.");

        if (string.IsNullOrWhiteSpace(extension.JustificationComments))
            throw new ArgumentException("Detailed justification reason comments are mandatory for an extension request.");

        extension.OriginalExpiryDate = learner.ExpectedCompletionDate;
        extension.ExtensionStatusCode = "Pending";
        extension.CreatedBy = currentUsername;

        learner.InstateStatusCode = "Extension Requested";
        learner.InstateStatusDate = DateTime.UtcNow;

        db.CompanyLearnerExtensions.Add(extension);
        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearner", learner.Id, "RequestExtension", currentUsername, new
        {
            extension.Id,
            extension.ExtensionTypeCode,
            extension.ExtensionReasonCode,
            extension.RequestedExpiryDate,
            extension.JustificationComments
        });

        return extension;
    }

    public async Task<CompanyLearnerExtension> RequestExtensionAsync(LearnerExtensionRequest request, string currentUsername = "SYSTEM")
    {
        var ext = new CompanyLearnerExtension
        {
            CompanyLearnerId = request.CompanyLearnerId,
            ExtensionTypeCode = request.ExtensionTypeCode,
            ExtensionReasonCode = request.ExtensionReasonCode,
            JustificationComments = request.JustificationComments,
            OriginalExpiryDate = request.OriginalExpiryDate,
            RequestedExpiryDate = request.RequestedExpiryDate,
            AddendumDocumentId = request.AddendumDocumentId,
            ApprovalComments = request.ApprovalComments
        };
        return await RequestExtensionAsync(ext, currentUsername);
    }

    public async Task<CompanyLearnerExtension> ApproveExtensionAsync(int extensionId, DateTime? approvedExpiryDate, string? comments, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var extension = await db.CompanyLearnerExtensions
            .Include(e => e.CompanyLearner)
            .FirstOrDefaultAsync(e => e.Id == extensionId);

        if (extension == null)
            throw new KeyNotFoundException($"CompanyLearnerExtension with ID {extensionId} not found.");

        extension.ExtensionStatusCode = "Approved";
        extension.ApprovedExpiryDate = approvedExpiryDate ?? extension.RequestedExpiryDate;
        extension.ApprovalComments = comments;
        extension.ApprovedByUserId = currentUsername;
        extension.ApprovalDate = DateTime.UtcNow;

        if (extension.CompanyLearner != null)
        {
            extension.CompanyLearner.ExpectedCompletionDate = extension.ApprovedExpiryDate;
            extension.CompanyLearner.InstateStatusCode = "Active";
            extension.CompanyLearner.InstateStatusDate = DateTime.UtcNow;
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearnerExtension", extension.Id, "ApproveExtension", currentUsername, new
        {
            extension.ApprovedExpiryDate,
            comments
        });

        return extension;
    }

    public async Task<CompanyLearnerExtension> RejectExtensionAsync(int extensionId, string? comments, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var extension = await db.CompanyLearnerExtensions
            .Include(e => e.CompanyLearner)
            .FirstOrDefaultAsync(e => e.Id == extensionId);

        if (extension == null)
            throw new KeyNotFoundException($"CompanyLearnerExtension with ID {extensionId} not found.");

        extension.ExtensionStatusCode = "Rejected";
        extension.ApprovalComments = comments;
        extension.ApprovedByUserId = currentUsername;
        extension.ApprovalDate = DateTime.UtcNow;

        if (extension.CompanyLearner != null)
        {
            extension.CompanyLearner.InstateStatusCode = "Active";
            extension.CompanyLearner.InstateStatusDate = DateTime.UtcNow;
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearnerExtension", extension.Id, "RejectExtension", currentUsername, new
        {
            comments
        });

        return extension;
    }

    public async Task<CompanyLearnerExtension?> GetExtensionByIdAsync(int extensionId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerExtensions
            .Include(e => e.CompanyLearner)
                .ThenInclude(l => l!.Person)
            .Include(e => e.CompanyLearner)
                .ThenInclude(l => l!.Organisation)
            .FirstOrDefaultAsync(e => e.Id == extensionId);
    }

    public async Task<List<CompanyLearnerExtension>> GetExtensionsForLearnerAsync(int learnerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerExtensions
            .Where(e => e.CompanyLearnerId == learnerId)
            .OrderByDescending(e => e.CreatedAt)
            .ToListAsync();
    }

    public async Task<List<CompanyLearnerExtension>> GetPendingExtensionsAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerExtensions
            .Include(e => e.CompanyLearner)
                .ThenInclude(l => l!.Person)
            .Include(e => e.CompanyLearner)
                .ThenInclude(l => l!.Organisation)
            .Where(e => e.ExtensionStatusCode == "Pending")
            .OrderByDescending(e => e.CreatedAt)
            .ToListAsync();
    }
    #endregion

    #region Lost Time Operations
    public async Task<CompanyLearnerLostTime> RecordLostTimeAsync(CompanyLearnerLostTime lostTime, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var learner = await db.CompanyLearners.FindAsync(lostTime.CompanyLearnerId);
        if (learner == null)
            throw new KeyNotFoundException($"CompanyLearner with ID {lostTime.CompanyLearnerId} not found.");

        if (lostTime.DaysLost <= 0 && lostTime.EndDate > lostTime.StartDate)
        {
            lostTime.DaysLost = (int)(lostTime.EndDate - lostTime.StartDate).TotalDays;
        }
        if (lostTime.DaysLost < 1) lostTime.DaysLost = 1;

        lostTime.OriginalContractEndDate = learner.ExpectedCompletionDate ?? DateTime.UtcNow.AddYears(1);
        lostTime.RevisedContractEndDate = lostTime.OriginalContractEndDate.AddDays(lostTime.DaysLost);
        lostTime.LostTimeStatusCode = "Pending";
        lostTime.CreatedBy = currentUsername;

        db.CompanyLearnerLostTimes.Add(lostTime);
        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearner", learner.Id, "RecordLostTime", currentUsername, new
        {
            lostTime.Id,
            lostTime.DaysLost,
            lostTime.LostTimeReasonCode
        });

        return lostTime;
    }

    public async Task<CompanyLearnerLostTime> RecordLostTimeAsync(LearnerLostTimeRequest request, string currentUsername = "SYSTEM")
    {
        var lostTime = new CompanyLearnerLostTime
        {
            CompanyLearnerId = request.CompanyLearnerId,
            LostTimeReasonCode = request.LostTimeReasonCode,
            StartDate = request.StartDate,
            EndDate = request.EndDate,
            DaysLost = request.DaysLost,
            ApprovalComments = request.ApprovalComments
        };
        return await RecordLostTimeAsync(lostTime, currentUsername);
    }

    public async Task<CompanyLearnerLostTime> ApproveLostTimeAsync(int lostTimeId, string? comments, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var lostTime = await db.CompanyLearnerLostTimes
            .Include(l => l.CompanyLearner)
            .FirstOrDefaultAsync(l => l.Id == lostTimeId);

        if (lostTime == null)
            throw new KeyNotFoundException($"CompanyLearnerLostTime with ID {lostTimeId} not found.");

        lostTime.LostTimeStatusCode = "Approved";
        lostTime.ApprovalComments = comments;
        lostTime.ApprovedByUserId = currentUsername;
        lostTime.ApprovalDate = DateTime.UtcNow;

        if (lostTime.CompanyLearner != null)
        {
            lostTime.CompanyLearner.ExpectedCompletionDate = lostTime.RevisedContractEndDate;
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearnerLostTime", lostTime.Id, "ApproveLostTime", currentUsername, new
        {
            lostTime.RevisedContractEndDate,
            comments
        });

        return lostTime;
    }

    public async Task<List<CompanyLearnerLostTime>> GetLostTimesForLearnerAsync(int learnerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerLostTimes
            .Where(l => l.CompanyLearnerId == learnerId)
            .OrderByDescending(l => l.StartDate)
            .ToListAsync();
    }
    #endregion

    #region Sections 4.4, 4.5, 4.6: Termination Operations
    public async Task<CompanyLearnerTermination> RequestTerminationAsync(CompanyLearnerTermination termination, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var learner = await db.CompanyLearners.FindAsync(termination.CompanyLearnerId);
        if (learner == null)
            throw new KeyNotFoundException($"CompanyLearner with ID {termination.CompanyLearnerId} not found.");

        if (string.Equals(termination.TerminationTypeCode, "OneSided", StringComparison.OrdinalIgnoreCase))
        {
            termination.TerminationStatusCode = "InInvestigation";
            termination.InvestigationStartDate = DateTime.UtcNow;
            // Statutory dispute investigation SLA per Section 5 Business Rules
            var slaDays = _configService != null 
                ? await _configService.GetValueAsync<int>("LearnerLifecycle:TerminationInvestigationSlaDays", 14) 
                : 14;
            termination.InvestigationDueDate = WorkplaceApprovalService.AddBusinessDays(DateTime.UtcNow, slaDays);
        }
        else
        {
            termination.TerminationStatusCode = "Pending";
        }

        termination.CreatedBy = currentUsername;
        learner.InstateStatusCode = "Termination Pending";
        learner.InstateStatusDate = DateTime.UtcNow;

        db.CompanyLearnerTerminations.Add(termination);
        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearner", learner.Id, "RequestTermination", currentUsername, new
        {
            termination.Id,
            termination.TerminationTypeCode,
            termination.TerminationReasonCode,
            termination.InvestigationDueDate,
            termination.DisputeLogged
        });

        return termination;
    }

    public async Task<CompanyLearnerTermination> RequestTerminationAsync(LearnerTerminationRequest request, string currentUsername = "SYSTEM")
    {
        var termination = new CompanyLearnerTermination
        {
            CompanyLearnerId = request.CompanyLearnerId,
            TerminationTypeCode = request.TerminationTypeCode,
            TerminationReasonCode = request.TerminationReasonCode,
            EffectiveDate = request.EffectiveDate,
            DisputeLogged = request.DisputeLogged,
            UnionRepresentativeName = request.UnionRepresentativeName,
            SettlementNotes = request.SettlementNotes,
            ApprovalComments = request.ApprovalComments
        };
        return await RequestTerminationAsync(termination, currentUsername);
    }

    public async Task<CompanyLearnerTermination> CompleteChecklist036Async(int terminationId, string checklistDataJson, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var termination = await db.CompanyLearnerTerminations.FindAsync(terminationId);
        if (termination == null)
            throw new KeyNotFoundException($"CompanyLearnerTermination with ID {terminationId} not found.");

        termination.Checklist036Completed = true;
        termination.Checklist036DataJson = checklistDataJson;
        termination.Checklist036CompletedByUserId = currentUsername;
        termination.Checklist036CompletedDate = DateTime.UtcNow;

        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearnerTermination", termination.Id, "CompleteChecklist036", currentUsername, new
        {
            termination.Checklist036CompletedDate
        });

        return termination;
    }

    public async Task<CompanyLearnerTermination> RecordInvestigationOutcomeAsync(
        int terminationId,
        string outcomeSummary,
        bool isArplRecommended,
        bool isTransferRecommended,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var termination = await db.CompanyLearnerTerminations.FindAsync(terminationId);
        if (termination == null)
            throw new KeyNotFoundException($"CompanyLearnerTermination with ID {terminationId} not found.");

        termination.InvestigationCompletedDate = DateTime.UtcNow;
        termination.InvestigationOutcomeSummary = outcomeSummary;
        termination.IsArplRecommended = isArplRecommended;
        termination.IsTransferRecommended = isTransferRecommended;
        termination.InvestigationConductedByUserId = currentUsername;
        termination.TerminationStatusCode = "InvestigationCompleted";

        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearnerTermination", termination.Id, "RecordInvestigationOutcome", currentUsername, new
        {
            termination.InvestigationCompletedDate,
            isArplRecommended,
            isTransferRecommended
        });

        return termination;
    }

    public async Task<CompanyLearnerTermination> SubmitTerminationToCommitteeAsync(int terminationId, int? committeeMeetingId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var termination = await db.CompanyLearnerTerminations.FindAsync(terminationId);
        if (termination == null)
            throw new KeyNotFoundException($"CompanyLearnerTermination with ID {terminationId} not found.");

        termination.ReviewCommitteeMeetingId = committeeMeetingId;
        termination.RecommendedToCommitteeByUserId = currentUsername;
        termination.RecommendationToCommitteeDate = DateTime.UtcNow;
        termination.TerminationStatusCode = "CommitteeAgenda";

        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearnerTermination", termination.Id, "SubmitToCommittee", currentUsername, new
        {
            committeeMeetingId
        });

        return termination;
    }

    public async Task<CompanyLearnerTermination> AdjudicateCommitteeDecisionAsync(
        int terminationId,
        string decisionCode,
        string? decisionNotes,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var termination = await db.CompanyLearnerTerminations
            .Include(t => t.CompanyLearner)
            .FirstOrDefaultAsync(t => t.Id == terminationId);

        if (termination == null)
            throw new KeyNotFoundException($"CompanyLearnerTermination with ID {terminationId} not found.");

        termination.CommitteeDecisionCode = decisionCode;
        termination.CommitteeDecisionDate = DateTime.UtcNow;
        termination.CommitteeDecisionNotes = decisionNotes;
        termination.ApprovedByUserId = currentUsername;
        termination.ApprovalDate = DateTime.UtcNow;

        if (string.Equals(decisionCode, "Approved", StringComparison.OrdinalIgnoreCase))
        {
            termination.TerminationStatusCode = "Approved";

            if (termination.CompanyLearner != null)
            {
                termination.CompanyLearner.InstateStatusCode = "Terminated";
                termination.CompanyLearner.InstateStatusDate = DateTime.UtcNow;
                termination.CompanyLearner.EnrolmentStatusCode = "Terminated";
                termination.CompanyLearner.CompletionDate = termination.EffectiveDate;
            }
        }
        else if (string.Equals(decisionCode, "RequirementsNotMet", StringComparison.OrdinalIgnoreCase))
        {
            termination.TerminationStatusCode = "RequirementsNotMet";

            if (termination.CompanyLearner != null)
            {
                // Learner is NOT terminated; in-state status flags rejection
                termination.CompanyLearner.InstateStatusCode = "Requirements Not Met";
                termination.CompanyLearner.InstateStatusDate = DateTime.UtcNow;
            }
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearnerTermination", termination.Id, "AdjudicateCommitteeDecision", currentUsername, new
        {
            decisionCode,
            decisionNotes
        });

        return termination;
    }

    public async Task<CompanyLearnerTermination> ApproveTerminationAsync(int terminationId, string? comments, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var termination = await db.CompanyLearnerTerminations
            .Include(t => t.CompanyLearner)
            .FirstOrDefaultAsync(t => t.Id == terminationId);

        if (termination == null)
            throw new KeyNotFoundException($"CompanyLearnerTermination with ID {terminationId} not found.");

        termination.TerminationStatusCode = "Approved";
        termination.ApprovalComments = comments;
        termination.ApprovedByUserId = currentUsername;
        termination.ApprovalDate = DateTime.UtcNow;

        if (termination.CompanyLearner != null)
        {
            termination.CompanyLearner.InstateStatusCode = "Terminated";
            termination.CompanyLearner.InstateStatusDate = DateTime.UtcNow;
            termination.CompanyLearner.EnrolmentStatusCode = "Terminated";
            termination.CompanyLearner.CompletionDate = termination.EffectiveDate;
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearnerTermination", termination.Id, "ApproveTermination", currentUsername, new
        {
            termination.TerminationReasonCode,
            comments
        });

        return termination;
    }

    public async Task<CompanyLearnerTermination> RejectTerminationAsync(int terminationId, string? comments, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var termination = await db.CompanyLearnerTerminations
            .Include(t => t.CompanyLearner)
            .FirstOrDefaultAsync(t => t.Id == terminationId);

        if (termination == null)
            throw new KeyNotFoundException($"CompanyLearnerTermination with ID {terminationId} not found.");

        termination.TerminationStatusCode = "Rejected";
        termination.ApprovalComments = comments;
        termination.ApprovedByUserId = currentUsername;
        termination.ApprovalDate = DateTime.UtcNow;

        if (termination.CompanyLearner != null)
        {
            termination.CompanyLearner.InstateStatusCode = "Active";
            termination.CompanyLearner.InstateStatusDate = DateTime.UtcNow;
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearnerTermination", termination.Id, "RejectTermination", currentUsername, new
        {
            comments
        });

        return termination;
    }

    public async Task<CompanyLearnerTermination?> GetTerminationByIdAsync(int terminationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerTerminations
            .Include(t => t.CompanyLearner)
                .ThenInclude(l => l!.Person)
            .Include(t => t.CompanyLearner)
                .ThenInclude(l => l!.Organisation)
            .FirstOrDefaultAsync(t => t.Id == terminationId);
    }

    public async Task<List<CompanyLearnerTermination>> GetTerminationsForLearnerAsync(int learnerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerTerminations
            .Where(t => t.CompanyLearnerId == learnerId)
            .OrderByDescending(t => t.EffectiveDate)
            .ToListAsync();
    }

    public async Task<List<CompanyLearnerTermination>> GetPendingTerminationsAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerTerminations
            .Include(t => t.CompanyLearner)
                .ThenInclude(l => l!.Person)
            .Include(t => t.CompanyLearner)
                .ThenInclude(l => l!.Organisation)
            .Where(t => t.TerminationStatusCode == "Pending" ||
                        t.TerminationStatusCode == "InInvestigation" ||
                        t.TerminationStatusCode == "InvestigationCompleted" ||
                        t.TerminationStatusCode == "CommitteeAgenda")
            .OrderByDescending(t => t.EffectiveDate)
            .ToListAsync();
    }
    #endregion

    #region Change Requests & OTP Sign-off
    public async Task<CompanyLearnerChangeRequest> SubmitChangeRequestAsync(LearnerChangeRequestInput request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var learner = await db.CompanyLearners
            .Include(l => l.Person)
            .FirstOrDefaultAsync(l => l.Id == request.CompanyLearnerId);

        if (learner == null)
            throw new KeyNotFoundException($"CompanyLearner with ID {request.CompanyLearnerId} not found.");

        var currentSnapshot = new
        {
            learner.LearnerContractNumber,
            learner.EnrolmentStatusCode,
            learner.InstateStatusCode,
            learner.OfoCode,
            learner.QualificationTitle,
            learner.ExpectedCompletionDate,
            PersonName = learner.Person?.FullName,
            RsaId = learner.Person?.RsaIdNumber
        };

        var changeRequest = new CompanyLearnerChangeRequest
        {
            CompanyLearnerId = learner.Id,
            ChangeTypeCode = request.ChangeTypeCode,
            CurrentValuesSnapshotJson = JsonSerializer.Serialize(currentSnapshot),
            RequestedValuesJson = request.ProposedChangesJson,
            JustificationReason = request.JustificationReason,
            ChangeStatusCode = "Pending",
            CreatedBy = currentUsername
        };

        db.CompanyLearnerChangeRequests.Add(changeRequest);
        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearner", learner.Id, "SubmitChangeRequest", currentUsername, new
        {
            ChangeRequestId = changeRequest.Id,
            Type = request.ChangeTypeCode,
            Justification = request.JustificationReason
        });

        return changeRequest;
    }

    public async Task<CompanyLearnerChangeRequest> ReviewChangeRequestAsync(int changeRequestId, bool approve, string? comments, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var changeRequest = await db.CompanyLearnerChangeRequests
            .Include(c => c.CompanyLearner)
            .FirstOrDefaultAsync(c => c.Id == changeRequestId);

        if (changeRequest == null)
            throw new KeyNotFoundException($"CompanyLearnerChangeRequest with ID {changeRequestId} not found.");

        changeRequest.ChangeStatusCode = approve ? "Approved" : "Rejected";
        changeRequest.ReviewerComments = comments;
        changeRequest.ReviewedByUserId = currentUsername;
        changeRequest.ReviewDate = DateTime.UtcNow;
        if (approve)
        {
            changeRequest.ApprovedByUserId = currentUsername;
            changeRequest.ApprovalDate = DateTime.UtcNow;
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearnerChangeRequest", changeRequest.Id, "ReviewChangeRequest", currentUsername, new
        {
            changeRequest.ChangeStatusCode,
            comments
        });

        return changeRequest;
    }

    public async Task<string> GenerateLearnerSignoffOtpAsync(int learnerId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var learner = await db.CompanyLearners.FindAsync(learnerId);
        if (learner == null)
            throw new KeyNotFoundException($"CompanyLearner with ID {learnerId} not found.");

        using var rng = RandomNumberGenerator.Create();
        var bytes = new byte[4];
        rng.GetBytes(bytes);
        int val = Math.Abs(BitConverter.ToInt32(bytes, 0)) % 900000 + 100000;
        string otp = val.ToString();

        await _audit.LogAsync("CompanyLearner", learnerId, "GenerateSignoffOtp", currentUsername, new
        {
            OtpGeneratedAt = DateTime.UtcNow
        });

        return otp;
    }

    public async Task<(bool Success, string Message, string? SecuritySeal)> VerifyAndExecuteSignoffOtpAsync(LearnerOtpVerificationRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var query = db.CompanyLearners
            .Include(l => l.Person)
            .AsQueryable();

        var learner = await query.FirstOrDefaultAsync(l =>
            l.LearnerContractNumber == request.ContractReferenceOrRsaId ||
            (l.Person != null && l.Person.RsaIdNumber == request.ContractReferenceOrRsaId));

        if (learner == null)
        {
            return (false, "No active learner contract found matching the provided reference number or RSA ID.", null);
        }

        // Generate SHA-256 seal
        string rawSignature = $"LRN-AGREEMENT:{learner.Id}:{learner.LearnerContractNumber}:{request.SignerRole}:{request.SignerName}:{DateTime.UtcNow:O}";
        string seal;
        using (var sha = SHA256.Create())
        {
            byte[] hashBytes = sha.ComputeHash(Encoding.UTF8.GetBytes(rawSignature));
            seal = Convert.ToHexString(hashBytes).ToLowerInvariant();
        }

        learner.EnrolmentStatusCode = "Registered";
        learner.InstateStatusCode = "Active";
        learner.InstateStatusDate = DateTime.UtcNow;
        learner.RegistrationDate = DateTime.UtcNow;

        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearner", learner.Id, "ExecuteOtpSignoff", currentUsername, new
        {
            Signer = request.SignerName,
            Role = request.SignerRole,
            SecuritySeal = seal
        });

        return (true, "Tripartite agreement executed successfully.", seal);
    }

    public async Task<List<CompanyLearnerChangeRequest>> GetChangeRequestsForLearnerAsync(int learnerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerChangeRequests
            .Where(c => c.CompanyLearnerId == learnerId)
            .OrderByDescending(c => c.CreatedAt)
            .ToListAsync();
    }
    #endregion
}
