using System.Security.Cryptography;

namespace Nsdms.Application.Services;

/// <summary>
/// Statutory Certificate Number Generator for MerSETA academic qualification certificates.
/// Implements the exact statutory formula defined in Use Case MerSeta\NSDMS\LMS\ASM\12:
/// Formula: '17' (SETA Code) + middle 4 digits of SA ID (index 5..8) + 6 random/sequential numbers.
/// Foreign Nationals: '17' + middle 4 digits of DOB (DDMMYYYY -> MMYY) + 6 random/sequential numbers.
/// </summary>
public static class StatutoryCertificateNumberGenerator
{
    private static int _sequenceCounter = 1;
    private static readonly object _lock = new();

    public static string Generate(string? rsaIdNumber, DateTime? dateOfBirth, int? sequenceOverride = null)
    {
        const string mersetaCode = "17";
        string middleFour;

        if (!string.IsNullOrWhiteSpace(rsaIdNumber) && rsaIdNumber.Trim().Length == 13 && rsaIdNumber.Trim().All(char.IsDigit))
        {
            var cleaned = rsaIdNumber.Trim();
            // South African ID: 13 digits (YYMMDD SSSS C A Z).
            // Middle 4 digits = characters 6, 7, 8, 9 (indices 5 to 8).
            middleFour = cleaned.Substring(5, 4);
        }
        else if (dateOfBirth.HasValue)
        {
            // Foreign National: DOB in MMYY format (Month & 2-digit Year, e.g. 0798 for July 1998).
            middleFour = dateOfBirth.Value.ToString("MMyy");
        }
        else
        {
            // Fallback for missing demographic dates
            middleFour = "9999";
        }

        int seq;
        if (sequenceOverride.HasValue && sequenceOverride.Value > 0)
        {
            seq = sequenceOverride.Value % 1000000;
        }
        else
        {
            lock (_lock)
            {
                seq = _sequenceCounter++;
                if (_sequenceCounter > 999999) _sequenceCounter = 1;
            }
            // Mix with pseudo-random cryptographic digits to avoid sequential predictability
            int randomComponent = RandomNumberGenerator.GetInt32(100, 999);
            seq = ((seq % 1000) * 1000) + randomComponent;
        }

        string sixDigits = (seq % 1000000).ToString("D6");
        return $"{mersetaCode}{middleFour}{sixDigits}";
    }
}
