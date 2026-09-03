using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class LearnerTransferRequest
{
    public int CompanyLearnerId { get; set; }
    public int ToOrganisationId { get; set; }
    public string TransferReasonCode { get; set; } = "MutualAgreement";
    public DateTime EffectiveDate { get; set; } = DateTime.UtcNow;
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
    public string TerminationReasonCode { get; set; } = "MutualCancellation";
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

public interface ILearnerLifecycleService
{
    Task<CompanyLearnerTransfer> RequestTransferAsync(CompanyLearnerTransfer transfer, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTransfer> RequestTransferAsync(LearnerTransferRequest request, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTransfer> ApproveTransferAsync(int transferId, string? comments, string currentUsername = "SYSTEM");

    Task<CompanyLearnerLostTime> RecordLostTimeAsync(CompanyLearnerLostTime lostTime, string currentUsername = "SYSTEM");
    Task<CompanyLearnerLostTime> RecordLostTimeAsync(LearnerLostTimeRequest request, string currentUsername = "SYSTEM");
    Task<CompanyLearnerLostTime> ApproveLostTimeAsync(int lostTimeId, string? comments, string currentUsername = "SYSTEM");

    Task<CompanyLearnerTermination> RequestTerminationAsync(CompanyLearnerTermination termination, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTermination> RequestTerminationAsync(LearnerTerminationRequest request, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTermination> ApproveTerminationAsync(int terminationId, string? comments, string currentUsername = "SYSTEM");

    Task<CompanyLearnerChangeRequest> SubmitChangeRequestAsync(LearnerChangeRequestInput request, string currentUsername = "SYSTEM");
    Task<CompanyLearnerChangeRequest> ReviewChangeRequestAsync(int changeRequestId, bool approve, string? comments, string currentUsername = "SYSTEM");

    Task<string> GenerateLearnerSignoffOtpAsync(int learnerId, string currentUsername = "SYSTEM");
    Task<(bool Success, string Message, string? SecuritySeal)> VerifyAndExecuteSignoffOtpAsync(LearnerOtpVerificationRequest request, string currentUsername = "SYSTEM");

    Task<List<CompanyLearnerTransfer>> GetTransfersForLearnerAsync(int learnerId);
    Task<List<CompanyLearnerLostTime>> GetLostTimesForLearnerAsync(int learnerId);
    Task<List<CompanyLearnerTermination>> GetTerminationsForLearnerAsync(int learnerId);
    Task<List<CompanyLearnerChangeRequest>> GetChangeRequestsForLearnerAsync(int learnerId);
}

public class LearnerLifecycleService : ILearnerLifecycleService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public LearnerLifecycleService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<CompanyLearnerTransfer> RequestTransferAsync(CompanyLearnerTransfer transfer, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var learner = await db.CompanyLearners.FindAsync(transfer.CompanyLearnerId);
        if (learner == null)
            throw new KeyNotFoundException($"CompanyLearner with ID {transfer.CompanyLearnerId} not found.");

        transfer.FromOrganisationId = learner.OrganisationId;
        transfer.TransferStatusCode = "Pending";
        transfer.CreatedBy = currentUsername;

        db.CompanyLearnerTransfers.Add(transfer);
        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearner", learner.Id, "RequestTransfer", currentUsername, new
        {
            transfer.Id,
            transfer.FromOrganisationId,
            transfer.ToOrganisationId,
            transfer.TransferReasonCode
        });

        return transfer;
    }

    public async Task<CompanyLearnerTransfer> RequestTransferAsync(LearnerTransferRequest request, string currentUsername = "SYSTEM")
    {
        var transfer = new CompanyLearnerTransfer
        {
            CompanyLearnerId = request.CompanyLearnerId,
            ToOrganisationId = request.ToOrganisationId,
            TransferReasonCode = request.TransferReasonCode,
            EffectiveDate = request.EffectiveDate,
            ApprovalComments = request.ApprovalComments
        };
        return await RequestTransferAsync(transfer, currentUsername);
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
            transfer.CompanyLearner.OrganisationId = transfer.ToOrganisationId;
            transfer.CompanyLearner.EnrolmentStatusCode = "Transferred";
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearnerTransfer", transfer.Id, "ApproveTransfer", currentUsername, new
        {
            transfer.ToOrganisationId,
            comments
        });

        return transfer;
    }

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

    public async Task<CompanyLearnerTermination> RequestTerminationAsync(CompanyLearnerTermination termination, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var learner = await db.CompanyLearners.FindAsync(termination.CompanyLearnerId);
        if (learner == null)
            throw new KeyNotFoundException($"CompanyLearner with ID {termination.CompanyLearnerId} not found.");

        termination.TerminationStatusCode = "Pending";
        termination.CreatedBy = currentUsername;

        db.CompanyLearnerTerminations.Add(termination);
        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearner", learner.Id, "RequestTermination", currentUsername, new
        {
            termination.Id,
            termination.TerminationReasonCode,
            termination.DisputeLogged
        });

        return termination;
    }

    public async Task<CompanyLearnerTermination> RequestTerminationAsync(LearnerTerminationRequest request, string currentUsername = "SYSTEM")
    {
        var termination = new CompanyLearnerTermination
        {
            CompanyLearnerId = request.CompanyLearnerId,
            TerminationReasonCode = request.TerminationReasonCode,
            EffectiveDate = request.EffectiveDate,
            DisputeLogged = request.DisputeLogged,
            UnionRepresentativeName = request.UnionRepresentativeName,
            SettlementNotes = request.SettlementNotes,
            ApprovalComments = request.ApprovalComments
        };
        return await RequestTerminationAsync(termination, currentUsername);
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
            termination.CompanyLearner.EnrolmentStatusCode = "Terminated";
            termination.CompanyLearner.StatusCode = "Terminated";
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("CompanyLearnerTermination", termination.Id, "ApproveTermination", currentUsername, new
        {
            termination.TerminationReasonCode,
            comments
        });

        return termination;
    }

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

    public async Task<List<CompanyLearnerTransfer>> GetTransfersForLearnerAsync(int learnerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerTransfers
            .Include(t => t.FromOrganisation)
            .Include(t => t.ToOrganisation)
            .Where(t => t.CompanyLearnerId == learnerId)
            .OrderByDescending(t => t.TransferDate)
            .ToListAsync();
    }

    public async Task<List<CompanyLearnerLostTime>> GetLostTimesForLearnerAsync(int learnerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerLostTimes
            .Where(l => l.CompanyLearnerId == learnerId)
            .OrderByDescending(l => l.StartDate)
            .ToListAsync();
    }

    public async Task<List<CompanyLearnerTermination>> GetTerminationsForLearnerAsync(int learnerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerTerminations
            .Where(t => t.CompanyLearnerId == learnerId)
            .OrderByDescending(t => t.EffectiveDate)
            .ToListAsync();
    }

    public async Task<List<CompanyLearnerChangeRequest>> GetChangeRequestsForLearnerAsync(int learnerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearnerChangeRequests
            .Where(c => c.CompanyLearnerId == learnerId)
            .OrderByDescending(c => c.CreatedAt)
            .ToListAsync();
    }
}
