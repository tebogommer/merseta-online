using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Result DTO for Chamber and Dynamics GP Vendor Class derivation.
/// </summary>
public record ChamberDerivationResult(
    bool IsSuccess,
    string? ChamberCode,
    string? ChamberName,
    string? GpVendorClass,
    string ResolutionSource,
    string? WarningMessage
);

/// <summary>
/// Result DTO for organisation-level chamber governance validation and action blocking.
/// </summary>
public record ChamberValidationResult(
    bool IsCompliant,
    string? ChamberCode,
    string? GpVendorClass,
    bool CanSubmitGrantApplication,
    bool CanSubmitWsp,
    bool CanContractMoa,
    bool CanDisbursePayment,
    string? BlockingReason
);

/// <summary>
/// Service contract for deriving merSETA Chambers and Dynamics GP Vendor Classes from SIC codes,
/// enforcing organisation-level compliance flags, and blocking downstream workflows when unmapped.
/// </summary>
public interface IChamberDerivationService
{
    /// <summary>
    /// Derives the appropriate merSETA Chamber Code and Microsoft Dynamics GP Vendor Class
    /// based on the declared 5-digit SIC code or organisation legal constitution type.
    /// </summary>
    Task<ChamberDerivationResult> DeriveChamberAndVendorClassAsync(
        string? sicCode, 
        string? organisationTypeCode = null, 
        string? manualChamberCode = null, 
        bool isManualOverride = false);

    /// <summary>
    /// Inspects an organisation to determine if Chamber / Vendor Class is missing,
    /// returning governance permission flags that block grant submissions, MoAs, and payment runs.
    /// </summary>
    Task<ChamberValidationResult> ValidateChamberGovernanceAsync(int organisationId);

    /// <summary>
    /// Evaluates an organisation's declared SIC code, auto-populates ChamberCode and GpVendorClass,
    /// sets HasMissingChamberMapping, and performs an audited change log double-write.
    /// </summary>
    Task<Organisation> ApplyChamberDerivationAsync(int organisationId, string currentUsername = "SYSTEM");

    /// <summary>
    /// Maps a merSETA Chamber code (e.g. AUTO, METAL, PLASTICS, NEW_TYRE)
    /// to the statutory Microsoft Dynamics GP Vendor Class string (AUTO, METAL, MOTOR, NEW TYRE, PLASTICS, SETA).
    /// </summary>
    string MapChamberToGpVendorClass(string chamberCode);
}
