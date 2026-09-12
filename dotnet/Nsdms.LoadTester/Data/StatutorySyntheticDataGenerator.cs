namespace Nsdms.LoadTester.Data;

/// <summary>
/// Generates statutorily compliant South African test data matching all NSDMS domain validation rules:
/// - 13-digit RSA National ID numbers with valid Luhn checksums
/// - SDL Numbers matching ^L\d{9}$
/// - SIC codes, chambers, and province mappings
/// </summary>
public static class StatutorySyntheticDataGenerator
{
    private static readonly string[] FirstNames = {
        "Sipho", "Thabo", "Kagiso", "Lindiwe", "Nthabiseng", "Mpho", "Bongani", "Tshepo", "Zanele", "Nomvula",
        "Johan", "Pieter", "Francois", "Willem", "Annelize", "Marike", "Lerato", "Khaya", "Sibusiso", "Andile",
        "David", "Michael", "Sarah", "Jessica", "Precious", "Blessing", "Dumisani", "Jabulani", "Nandi", "Ayanda"
    };

    private static readonly string[] LastNames = {
        "Dlamini", "Nkosi", "Ndlovu", "Khumalo", "Mokoena", "Botha", "Van der Merwe", "Smit", "Naidoo", "Pillay",
        "Molefe", " Sithole", "Baloyi", "Chauke", "Mabaso", "Pretorius", "Coetzee", "Govender", "Moodley", "Ntuli",
        "Zulu", "Mthembu", "Khoza", "Mahlangu", "Sibiya", "Muller", "Venter", "Du Plessis", "Patel", "Singh"
    };

    private static readonly string[] OrgPrefixes = {
        "Apex", "Vulcan", "Pioneer", "Dynamic", "Atlas", "Zenith", "Quantum", "Precision", "Titan", "Imperial",
        "Solid", "Vanguard", "Nexus", "Matrix", "Global", "Metro", "Prime", "United", "Southern", "Frontier"
    };

    private static readonly string[] OrgSuffixes = {
        "Engineering", "Manufacturing", "Automotive", "Foundry", "Plastics", "Steelworks", "Tooling", "Aerospace",
        "Fabrication", "Technical Services", "Metals", "Motors", "Logistics", "Precision Works", "Assembly"
    };

    private static readonly string[] Chambers = {
        "AUTO", "METAL", "PLASTIC", "NEWTIRE", "MOTOR"
    };

    private static readonly Random Rnd = new(42); // deterministic seed for reproducibility

    public static string GenerateRsaIdNumber(DateTime birthDate, bool isMale = true, bool isCitizen = true)
    {
        var yy = birthDate.Year % 100;
        var mm = birthDate.Month;
        var dd = birthDate.Day;

        // Gender: 0000-4999 Female, 5000-9999 Male
        var genderVal = isMale ? Rnd.Next(5000, 10000) : Rnd.Next(0, 5000);
        var citizenVal = isCitizen ? 0 : 1;
        var raceLegacy = 8; // typically 8 in modern RSA IDs

        var first12 = $"{yy:D2}{mm:D2}{dd:D2}{genderVal:D4}{citizenVal}{raceLegacy}";
        var checkDigit = CalculateLuhnCheckDigit(first12);

        return $"{first12}{checkDigit}";
    }

    public static string GenerateSdlNumber(int index)
    {
        // Must match ^L\d{9}$
        return $"L{(700000000 + index):D9}";
    }

    public static (string FirstName, string LastName, string Email, string CellNumber) GeneratePerson(int seedIndex)
    {
        var fn = FirstNames[seedIndex % FirstNames.Length];
        var ln = LastNames[(seedIndex / FirstNames.Length) % LastNames.Length];
        var email = $"{fn.ToLower()}.{ln.ToLower()}{seedIndex}@merseta-sim.co.za";
        var cell = $"08{Rnd.Next(1, 9)}{Rnd.Next(1000000, 9999999)}";
        return (fn, ln, email, cell);
    }

    public static (string LegalName, string TradingName, string RegNumber, string Chamber) GenerateOrganisation(int seedIndex)
    {
        var prefix = OrgPrefixes[seedIndex % OrgPrefixes.Length];
        var suffix = OrgSuffixes[(seedIndex / OrgPrefixes.Length) % OrgSuffixes.Length];
        var legal = $"{prefix} {suffix} (Pty) Ltd";
        var trading = $"{prefix} {suffix}";
        var reg = $"{2000 + (seedIndex % 25)}/{100000 + seedIndex}/07";
        var chamber = Chambers[seedIndex % Chambers.Length];
        return (legal, trading, reg, chamber);
    }

    private static int CalculateLuhnCheckDigit(string number)
    {
        int sum = 0;
        bool alternate = true;

        for (int i = number.Length - 1; i >= 0; i--)
        {
            int n = int.Parse(number[i].ToString());
            if (alternate)
            {
                n *= 2;
                if (n > 9)
                    n -= 9;
            }
            sum += n;
            alternate = !alternate;
        }

        return (10 - (sum % 10)) % 10;
    }
}
