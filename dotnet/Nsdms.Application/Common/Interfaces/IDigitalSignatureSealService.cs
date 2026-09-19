using System;

namespace Nsdms.Application.Common.Interfaces;

/// <summary>
/// Digital security seal model representing a non-repudiable cryptographic seal
/// over an approved statutory record in compliance with PFMA & National Treasury guidelines.
/// </summary>
public record CryptographicApprovalSeal(
    string EntityName,
    long RecordId,
    string ApproverUserId,
    string ApproverRole,
    decimal ApprovedAmount,
    DateTime SealedAtUtc,
    string DigitalSecuritySealPrefix,
    string DigitalSecuritySealSha256,
    bool IsVerified,
    string KeyVersion = "v1"
);

/// <summary>
/// Service contract providing cryptographic digital signature and verification
/// for executive statutory approvals (DG claims, disbursements, MoAs).
/// </summary>
public interface IDigitalSignatureSealService
{
    CryptographicApprovalSeal GenerateApprovalSeal(
        string entityName,
        long recordId,
        string approverUserId,
        string approverRole,
        decimal approvedAmount,
        string payloadSummary);

    bool VerifyApprovalSeal(
        CryptographicApprovalSeal seal,
        string payloadSummary);
}
