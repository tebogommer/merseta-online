using System.Text.RegularExpressions;

namespace Nsdms.Application.Common.Utilities;

/// <summary>
/// South African 13-digit Identity Number validator and parser.
/// Format: {YYMMDD}{SSSS}{C}{A}{Z}
/// - YYMMDD: Date of birth (with century rollover)
/// - SSSS: Sequence number (0000-4999 = Female, 5000-9999 = Male)
/// - C: Citizenship (0 = SA Citizen, 1 = Permanent Resident)
/// - A: Classification digit (usually 8 or 9)
/// - Z: Luhn algorithm checksum digit
/// </summary>
public static class RsaIdValidator
{
    private static readonly Regex IdPattern = new(@"^\d{13}$", RegexOptions.Compiled);

    /// <summary>
    /// Validates whether the given string is a valid 13-digit South African ID number.
    /// </summary>
    /// <param name="rsaIdNumber">The 13-digit RSA ID string.</param>
    /// <returns>True if valid; otherwise false.</returns>
    public static bool Validate(string? rsaIdNumber)
    {
        return Parse(rsaIdNumber).IsValid;
    }

    /// <summary>
    /// Parses and validates a South African ID number, extracting Date of Birth, Gender, and Citizenship.
    /// </summary>
    /// <param name="rsaIdNumber">The 13-digit RSA ID string.</param>
    /// <returns>A tuple containing IsValid, DateOfBirth, Gender, IsSouthAfricanCitizen, and ErrorMessage.</returns>
    public static (bool IsValid, DateTime? DateOfBirth, string? Gender, bool? IsSouthAfricanCitizen, string? ErrorMessage) Parse(string? rsaIdNumber)
    {
        if (string.IsNullOrWhiteSpace(rsaIdNumber))
        {
            return (false, null, null, null, "ID number cannot be empty or null.");
        }

        string cleanId = rsaIdNumber.Trim();

        if (cleanId.Length != 13 || !IdPattern.IsMatch(cleanId))
        {
            return (false, null, null, null, "ID number must be exactly 13 digits.");
        }

        // 1. Verify Luhn checksum algorithm
        if (!VerifyLuhnChecksum(cleanId))
        {
            return (false, null, null, null, "Invalid Luhn checksum digit.");
        }

        // 2. Parse Date of Birth with Century Rollover
        int yy = int.Parse(cleanId.Substring(0, 2));
        int mm = int.Parse(cleanId.Substring(2, 2));
        int dd = int.Parse(cleanId.Substring(4, 2));

        if (mm < 1 || mm > 12 || dd < 1)
        {
            return (false, null, null, null, "Invalid date of birth in ID number.");
        }

        int currentYear2Digit = DateTime.Today.Year % 100;
        int fullYear = yy <= currentYear2Digit ? 2000 + yy : 1900 + yy;

        if (dd > DateTime.DaysInMonth(fullYear, mm))
        {
            return (false, null, null, null, "Invalid date of birth in ID number (invalid day for month).");
        }

        DateTime dateOfBirth = new(fullYear, mm, dd);
        if (dateOfBirth > DateTime.Today)
        {
            return (false, null, null, null, "Date of birth cannot be in the future.");
        }

        // 3. Gender determination (Digits 7-10: 0000-4999 = Female, 5000-9999 = Male)
        int genderDigits = int.Parse(cleanId.Substring(6, 4));
        string gender = genderDigits >= 5000 ? "Male" : "Female";

        // 4. Citizenship determination (Digit 11: 0 = SA Citizen, 1 = Permanent Resident)
        char citizenChar = cleanId[10];
        bool isSouthAfricanCitizen;
        if (citizenChar == '0')
        {
            isSouthAfricanCitizen = true;
        }
        else if (citizenChar == '1')
        {
            isSouthAfricanCitizen = false;
        }
        else
        {
            return (false, null, null, null, "Invalid citizenship digit in ID number (must be 0 or 1).");
        }

        return (true, dateOfBirth, gender, isSouthAfricanCitizen, null);
    }

    /// <summary>
    /// Calculates Luhn checksum for the first 12 digits and compares with 13th check digit.
    /// </summary>
    private static bool VerifyLuhnChecksum(string id)
    {
        int sum = 0;
        for (int i = 0; i < 12; i++)
        {
            int digit = id[i] - '0';
            if (i % 2 == 1) // 2nd, 4th, 6th, 8th, 10th, 12th digit
            {
                int doubled = digit * 2;
                sum += (doubled > 9) ? (doubled - 9) : doubled;
            }
            else
            {
                sum += digit;
            }
        }

        int expectedCheckDigit = (10 - (sum % 10)) % 10;
        int actualCheckDigit = id[12] - '0';

        return expectedCheckDigit == actualCheckDigit;
    }
}
