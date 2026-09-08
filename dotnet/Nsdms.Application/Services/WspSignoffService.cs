using System.Security.Cryptography;
using System.Text;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class WspSignoffRequest
{
    public int WspSubmissionId { get; set; }
    public string SignerRoleCode { get; set; } = "PrimarySdf"; // PrimarySdf, LabourUnionRep, CeoManagingDirector
    public string SignerFullName { get; set; } = string.Empty;
    public string SignerEmail { get; set; } = string.Empty;
    public string? SignerRsaId { get; set; }
    public string? UnionName { get; set; }
    public string? OtpToken { get; set; }
    public string AttestationStatement { get; set; } = "I hereby confirm that the Workplace Skills Plan and Annual Training Report have been compiled with requisite consultation.";
    public string? SignerNotes { get; set; }
}

public class WspDisputeLodgementRequest
{
    public int WspSubmissionId { get; set; }
    public string DisputeReasonCode { get; set; } = "UnionRefusalToSign"; // UnionRefusalToSign, ConsultationFailure, SkillsPlanOmission
    public string Description { get; set; } = string.Empty;
    public string UnionRepresentativeName { get; set; } = string.Empty;
    public string? Constituency { get; set; }
}

public class WspQuorumStatusDto
{
    public int WspId { get; set; }
    public string ReferenceNumber { get; set; } = string.Empty;
    public int FinYear { get; set; }
    public int EmployeeCount { get; set; }
    public bool IsSmallEmployer { get; set; }
    public int RequiredSignaturesCount { get; set; }
    public int CompletedSignaturesCount { get; set; }
    public bool IsQuorumMet { get; set; }
    public bool DisputeLogged { get; set; }
    public string? SignoffDigitalSecuritySeal { get; set; }
    public List<WspSignoffAttestation> Attestations { get; set; } = new();
}

public interface IWspSignoffService
{
    Task<WspQuorumStatusDto> GetQuorumStatusAsync(int wspSubmissionId);
    Task<string> RequestSignoffOtpAsync(int wspSubmissionId, string signerRole, string signerEmail, string currentUsername = "SYSTEM");
    Task<WspSignoffAttestation> RecordSignoffAttestationAsync(WspSignoffRequest request, string currentUsername = "SYSTEM");
    Task<WspDispute> LodgeSignoffDisputeAsync(WspDisputeLodgementRequest request, string currentUsername = "SYSTEM");
}

public class WspSignoffService : IWspSignoffService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly ISystemConfigurationService? _configService;

    public WspSignoffService(
        INsdmsDbContextFactory contextFactory, 
        IAuditService audit,
        ISystemConfigurationService? configService = null)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _configService = configService;
    }

    public async Task<WspQuorumStatusDto> GetQuorumStatusAsync(int wspSubmissionId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var wsp = await db.WspSubmissions
            .Include(w => w.Organisation)
            .Include(w => w.SignoffAttestations)
            .FirstOrDefaultAsync(w => w.Id == wspSubmissionId);

        if (wsp == null)
            throw new KeyNotFoundException($"WspSubmission with ID {wspSubmissionId} not found.");

        var smallEmployerThreshold = _configService != null
            ? await _configService.GetValueAsync<int>("Governance:SmallEmployerMaxEmployeeCount", 50)
            : 50;
        bool isSmallEmployer = wsp.EmployeeCount < smallEmployerThreshold;
        int requiredSignatures = isSmallEmployer ? 2 : 3;

        var validAttestations = wsp.SignoffAttestations
            .Where(a => a.AttestationStatusCode == "SignedOff" && !a.DisputeLogged)
            .ToList();

        // Check if all required roles have attested
        var rolesPresent = validAttestations.Select(a => a.SignerRoleCode).ToHashSet();
        bool hasSdf = rolesPresent.Contains("PrimarySdf");
        bool hasCeo = rolesPresent.Contains("CeoManagingDirector");
        bool hasUnion = rolesPresent.Contains("LabourUnionRep");

        bool quorumMet = isSmallEmployer
            ? (hasSdf && hasCeo)
            : (hasSdf && hasCeo && hasUnion);

        return new WspQuorumStatusDto
        {
            WspId = wsp.Id,
            ReferenceNumber = wsp.ReferenceNumber,
            FinYear = wsp.FinYear,
            EmployeeCount = wsp.EmployeeCount,
            IsSmallEmployer = isSmallEmployer,
            RequiredSignaturesCount = requiredSignatures,
            CompletedSignaturesCount = validAttestations.Count,
            IsQuorumMet = quorumMet,
            DisputeLogged = wsp.DisputeLogged,
            SignoffDigitalSecuritySeal = wsp.SignoffDigitalSecuritySeal,
            Attestations = wsp.SignoffAttestations.OrderBy(a => a.SignoffDate).ToList()
        };
    }

    public async Task<string> RequestSignoffOtpAsync(int wspSubmissionId, string signerRole, string signerEmail, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var wsp = await db.WspSubmissions.FindAsync(wspSubmissionId);
        if (wsp == null)
            throw new KeyNotFoundException($"WspSubmission with ID {wspSubmissionId} not found.");

        // Generate cryptographic 6-digit OTP
        using var rng = RandomNumberGenerator.Create();
        var bytes = new byte[4];
        rng.GetBytes(bytes);
        int randomVal = Math.Abs(BitConverter.ToInt32(bytes, 0)) % 900000 + 100000;
        string otp = randomVal.ToString();

        // Audit dispatch simulation
        await _audit.LogAsync("WspSubmission", wspSubmissionId, "RequestSignoffOtp", currentUsername, new
        {
            SignerRole = signerRole,
            SignerEmail = signerEmail,
            OtpGeneratedAt = DateTime.UtcNow
        });

        return otp;
    }

    public async Task<WspSignoffAttestation> RecordSignoffAttestationAsync(WspSignoffRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var wsp = await db.WspSubmissions
            .Include(w => w.SignoffAttestations)
            .FirstOrDefaultAsync(w => w.Id == request.WspSubmissionId);

        if (wsp == null)
            throw new KeyNotFoundException($"WspSubmission with ID {request.WspSubmissionId} not found.");

        if (wsp.DisputeLogged)
            throw new InvalidOperationException("Cannot record sign-off attestation while a formal dispute is lodged against this submission.");

        // Calculate SHA-256 digital security seal
        string rawSignature = $"WSP:{wsp.Id}:{wsp.ReferenceNumber}:{request.SignerRoleCode}:{request.SignerEmail}:{request.SignerRsaId}:{DateTime.UtcNow:O}";
        string sealHash;
        using (var sha = SHA256.Create())
        {
            byte[] hashBytes = sha.ComputeHash(Encoding.UTF8.GetBytes(rawSignature));
            sealHash = Convert.ToHexString(hashBytes).ToLowerInvariant();
        }

        var attestation = new WspSignoffAttestation
        {
            WspSubmissionId = wsp.Id,
            SignerRoleCode = request.SignerRoleCode,
            SignerFullName = request.SignerFullName,
            SignerEmail = request.SignerEmail,
            SignerRsaId = request.SignerRsaId,
            UnionName = request.UnionName,
            OtpToken = request.OtpToken,
            OtpVerifiedAt = DateTime.UtcNow,
            DigitalSecuritySeal = sealHash,
            SignoffDate = DateTime.UtcNow,
            AttestationStatement = request.AttestationStatement,
            SignerNotes = request.SignerNotes,
            AttestationStatusCode = "SignedOff",
            DisputeLogged = false,
            CreatedBy = currentUsername
        };

        db.WspSignoffAttestations.Add(attestation);

        // Check and update submission status
        var smallEmployerThreshold = _configService != null
            ? await _configService.GetValueAsync<int>("Governance:SmallEmployerMaxEmployeeCount", 50)
            : 50;
        bool isSmallEmployer = wsp.EmployeeCount < smallEmployerThreshold;
        wsp.RequiredSignoffCount = isSmallEmployer ? 2 : 3;

        var allRoles = wsp.SignoffAttestations
            .Where(a => a.AttestationStatusCode == "SignedOff" && !a.DisputeLogged)
            .Select(a => a.SignerRoleCode)
            .ToHashSet();

        allRoles.Add(request.SignerRoleCode);

        bool hasSdf = allRoles.Contains("PrimarySdf");
        bool hasCeo = allRoles.Contains("CeoManagingDirector");
        bool hasUnion = allRoles.Contains("LabourUnionRep");

        bool quorumMet = isSmallEmployer ? (hasSdf && hasCeo) : (hasSdf && hasCeo && hasUnion);

        wsp.CompletedSignoffCount = allRoles.Count;
        wsp.IsSignoffQuorumMet = quorumMet;

        if (quorumMet)
        {
            wsp.SignoffDigitalSecuritySeal = sealHash;
            wsp.WspApprovalStatusCode = "SignedOff";
            wsp.SubmissionDate = DateTime.UtcNow;
        }
        else
        {
            wsp.WspApprovalStatusCode = "PendingSignoff";
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("WspSubmission", wsp.Id, "RecordSignoffAttestation", currentUsername, new
        {
            SignerRole = request.SignerRoleCode,
            SignerFullName = request.SignerFullName,
            DigitalSeal = sealHash,
            QuorumMet = quorumMet,
            NewStatus = wsp.WspApprovalStatusCode
        });

        return attestation;
    }

    public async Task<WspDispute> LodgeSignoffDisputeAsync(WspDisputeLodgementRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var wsp = await db.WspSubmissions.FindAsync(request.WspSubmissionId);
        if (wsp == null)
            throw new KeyNotFoundException($"WspSubmission with ID {request.WspSubmissionId} not found.");

        string disputeRef = $"DISP-{wsp.FinYear}-{DateTime.UtcNow:MMdd}-{new Random().Next(100, 999)}";

        var dispute = new WspDispute
        {
            OrganisationId = wsp.OrganisationId,
            WspSubmissionId = wsp.Id,
            DisputeReferenceNumber = disputeRef,
            DisputeReasonCode = request.DisputeReasonCode,
            Description = $"Lodged by {request.UnionRepresentativeName} ({request.Constituency ?? "Labour Union"}): {request.Description}",
            DisputeStatusCode = "Logged",
            CreatedBy = currentUsername
        };

        db.WspDisputes.Add(dispute);

        // Update WSP Submission state
        wsp.DisputeLogged = true;
        wsp.WspApprovalStatusCode = "Disputed";

        // Also record a disputed attestation entry
        var attestation = new WspSignoffAttestation
        {
            WspSubmissionId = wsp.Id,
            SignerRoleCode = "LabourUnionRep",
            SignerFullName = request.UnionRepresentativeName,
            SignerEmail = currentUsername.Contains("@") ? currentUsername : "dispute@labour.org.za",
            UnionName = request.Constituency ?? "Union",
            SignoffDate = DateTime.UtcNow,
            AttestationStatement = "Dispute lodged regarding WSP training plan and consultation failure.",
            SignerNotes = request.Description,
            DisputeLogged = true,
            AttestationStatusCode = "Disputed",
            DigitalSecuritySeal = $"DISPUTE-{disputeRef}",
            CreatedBy = currentUsername
        };

        db.WspSignoffAttestations.Add(attestation);
        await db.SaveChangesAsync();

        await _audit.LogAsync("WspSubmission", wsp.Id, "LodgeSignoffDispute", currentUsername, new
        {
            DisputeReference = disputeRef,
            Reason = request.DisputeReasonCode,
            Representative = request.UnionRepresentativeName
        });

        return dispute;
    }
}
