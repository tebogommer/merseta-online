namespace Nsdms.Application.Common.Interfaces;

/// <summary>
/// Bank account verification request payload for BankservAfrica CDV (Check Digit Verification) &amp; AVS API.
/// </summary>
public class AvsVerificationRequest
{
    public string BankName { get; set; } = string.Empty;
    public string BranchCode { get; set; } = string.Empty;
    public string AccountNumber { get; set; } = string.Empty;
    public string AccountHolderName { get; set; } = string.Empty;
    public string? IdOrRegistrationNumber { get; set; }
    public string? SdlNumber { get; set; }
}

/// <summary>
/// Structured real-time verification outcome from BankservAfrica AVS.
/// </summary>
public class AvsVerificationResult
{
    public bool IsValid { get; set; }
    public bool AccountExists { get; set; }
    public bool NameMatched { get; set; }
    public bool IdNumberMatched { get; set; }
    public bool AccountActiveAndOpen { get; set; }
    public bool AcceptsCredits { get; set; }
    public string ResponseCode { get; set; } = "AVS-00-SUCCESS";
    public string ResponseMessage { get; set; } = "Account successfully verified with BankservAfrica CDV.";
    public string VerificationReference { get; set; } = string.Empty;
    public DateTime VerifiedAt { get; set; } = DateTime.UtcNow;
}

/// <summary>
/// Service contract for real-time BankservAfrica Account Verification Service (AVS) integration.
/// </summary>
public interface IBankservAvsService
{
    /// <summary>
    /// Executes algorithmic CDV check digit calculation and calls BankservAfrica AVS gateway.
    /// </summary>
    Task<AvsVerificationResult> VerifyAccountAsync(AvsVerificationRequest request);

    /// <summary>
    /// Performs algorithmic check-digit modulus verification (Mod10 / Mod11) against South African clearing banks.
    /// </summary>
    bool ValidateCheckDigit(string branchCode, string accountNumber);
}
