using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Cryptographically sealed attestation for WSP/ATR multi-party sign-off (SDF, Labour Union, CEO).
/// </summary>
public class WspSignoffAttestation : BaseEntity
{
    public int WspSubmissionId { get; set; }
    public WspSubmission? WspSubmission { get; set; }

    public string SignerRoleCode { get; set; } = "PrimarySdf"; // PrimarySdf, LabourUnionRep, CeoManagingDirector
    public string SignerFullName { get; set; } = string.Empty;
    public string SignerEmail { get; set; } = string.Empty;
    public string? SignerRsaId { get; set; }
    public string? UnionName { get; set; }

    public string? OtpToken { get; set; }
    public DateTime? OtpVerifiedAt { get; set; }

    public string DigitalSecuritySeal { get; set; } = string.Empty;
    public DateTime SignoffDate { get; set; } = DateTime.UtcNow;

    public string AttestationStatement { get; set; } = "I hereby confirm that the Workplace Skills Plan and Annual Training Report have been compiled with requisite consultation.";
    public string? SignerNotes { get; set; }

    public bool DisputeLogged { get; set; } = false;
    public int? WspDisputeId { get; set; }
    public WspDispute? WspDispute { get; set; }

    public string AttestationStatusCode { get; set; } = "SignedOff"; // PendingOtp, SignedOff, Disputed, Revoked
}
