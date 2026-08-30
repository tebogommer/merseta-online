using Nsdms.Application.Common.Utilities;
using Xunit;

namespace Nsdms.Tests;

public class RsaIdValidatorTests
{
    #region Valid ID & Luhn Checksum Tests

    [Theory]
    [InlineData("8001015009087", 1980, 1, 1, "Male", true)]       // 1980-01-01, Male (5009), SA Citizen (0)
    [InlineData("9005200123081", 1990, 5, 20, "Female", true)]     // 1990-05-20, Female (0123), SA Citizen (0)
    [InlineData("0512150123184", 2005, 12, 15, "Female", false)]   // 2005-12-15, Female (0123), Permanent Resident (1)
    [InlineData("0211156543186", 2002, 11, 15, "Male", false)]     // 2002-11-15, Male (6543), Permanent Resident (1)
    [InlineData("0002290001086", 2000, 2, 29, "Female", true)]     // 2000-02-29 (Leap year), Female (0001), SA Citizen (0)
    [InlineData("9602295001089", 1996, 2, 29, "Male", true)]       // 1996-02-29 (Leap year), Male (5001), SA Citizen (0)
    public void Validate_Valid13DigitRsaIdNumbers_PassesChecksumAndExtractsFields(
        string idNumber, int expectedYear, int expectedMonth, int expectedDay, string expectedGender, bool expectedIsCitizen)
    {
        // Arrange
        var expectedDob = new DateTime(expectedYear, expectedMonth, expectedDay);

        // Act
        var isValid = RsaIdValidator.Validate(idNumber);
        var (parsedValid, dob, gender, isCitizen, error) = RsaIdValidator.Parse(idNumber);

        // Assert
        Assert.True(isValid);
        Assert.True(parsedValid);
        Assert.Null(error);
        Assert.Equal(expectedDob, dob);
        Assert.Equal(expectedGender, gender);
        Assert.Equal(expectedIsCitizen, isCitizen);
    }

    [Theory]
    [InlineData("8001015009088")] // Last digit altered from 7 to 8
    [InlineData("9005200123082")] // Last digit altered from 1 to 2
    [InlineData("0512150123180")] // Last digit altered from 4 to 0
    [InlineData("0002290001087")] // Last digit altered from 6 to 7
    public void Validate_InvalidLuhnChecksum_FailsValidation(string idNumber)
    {
        // Arrange & Act
        var isValid = RsaIdValidator.Validate(idNumber);
        var (parsedValid, dob, gender, isCitizen, error) = RsaIdValidator.Parse(idNumber);

        // Assert
        Assert.False(isValid);
        Assert.False(parsedValid);
        Assert.Null(dob);
        Assert.Null(gender);
        Assert.Null(isCitizen);
        Assert.NotNull(error);
        Assert.Contains("checksum", error, StringComparison.OrdinalIgnoreCase);
    }

    #endregion

    #region Invalid Format Tests

    [Theory]
    [InlineData(null)]
    [InlineData("")]
    [InlineData("   ")]
    public void Validate_NullOrWhitespace_FailsValidation(string? idNumber)
    {
        // Arrange & Act
        var isValid = RsaIdValidator.Validate(idNumber);
        var (parsedValid, dob, gender, isCitizen, error) = RsaIdValidator.Parse(idNumber);

        // Assert
        Assert.False(isValid);
        Assert.False(parsedValid);
        Assert.Null(dob);
        Assert.Null(gender);
        Assert.Null(isCitizen);
        Assert.NotNull(error);
        Assert.Contains("empty", error, StringComparison.OrdinalIgnoreCase);
    }

    [Theory]
    [InlineData("12345")]                  // 5 digits
    [InlineData("80010150090")]            // 11 digits
    [InlineData("800101500908")]           // 12 digits
    [InlineData("80010150090870")]         // 14 digits
    [InlineData("8001015009087123")]       // 16 digits
    public void Validate_InvalidLength_FailsValidation(string idNumber)
    {
        // Arrange & Act
        var isValid = RsaIdValidator.Validate(idNumber);
        var (parsedValid, dob, gender, isCitizen, error) = RsaIdValidator.Parse(idNumber);

        // Assert
        Assert.False(isValid);
        Assert.False(parsedValid);
        Assert.Null(dob);
        Assert.Null(gender);
        Assert.Null(isCitizen);
        Assert.NotNull(error);
        Assert.Contains("13 digits", error, StringComparison.OrdinalIgnoreCase);
    }

    [Theory]
    [InlineData("800101500908A")]          // Letter at end
    [InlineData("A001015009087")]          // Letter at start
    [InlineData("800101-500908")]          // Hyphen in string
    [InlineData("800101 500908")]          // Space in middle
    [InlineData("abcdefghijklm")]          // All alphabetics
    [InlineData("800101@500908")]          // Special characters
    public void Validate_NonNumericCharacters_FailsValidation(string idNumber)
    {
        // Arrange & Act
        var isValid = RsaIdValidator.Validate(idNumber);
        var (parsedValid, dob, gender, isCitizen, error) = RsaIdValidator.Parse(idNumber);

        // Assert
        Assert.False(isValid);
        Assert.False(parsedValid);
        Assert.Null(dob);
        Assert.Null(gender);
        Assert.Null(isCitizen);
        Assert.NotNull(error);
        Assert.Contains("13 digits", error, StringComparison.OrdinalIgnoreCase);
    }

    #endregion

    #region Date of Birth & Century Rollover Tests

    [Theory]
    [InlineData("9005200123081", 1990, 5, 20)] // 90 -> 1990
    [InlineData("9912315000080", 1999, 12, 31)] // 99 -> 1999
    [InlineData("7506157500082", 1975, 6, 15)]  // 75 -> 1975
    [InlineData("0001010000089", 2000, 1, 1)]   // 00 -> 2000
    [InlineData("0512150123184", 2005, 12, 15)] // 05 -> 2005
    [InlineData("2403155001088", 2024, 3, 15)]  // 24 -> 2024
    public void Parse_DateOfBirthExtraction_HandlesCenturyRolloverCorrectly(
        string idNumber, int expectedYear, int expectedMonth, int expectedDay)
    {
        // Arrange
        var expectedDob = new DateTime(expectedYear, expectedMonth, expectedDay);

        // Act
        var (isValid, dob, _, _, error) = RsaIdValidator.Parse(idNumber);

        // Assert
        Assert.True(isValid);
        Assert.Null(error);
        Assert.Equal(expectedDob, dob);
    }

    [Theory]
    [InlineData("8013015009084")] // Month 13 is invalid
    public void Parse_InvalidMonth_FailsValidation(string idNumber)
    {
        // Act
        var (isValid, dob, _, _, error) = RsaIdValidator.Parse(idNumber);

        // Assert
        Assert.False(isValid);
        Assert.Null(dob);
        Assert.NotNull(error);
    }

    [Fact]
    public void Parse_InvalidDayForMonth_FailsValidation()
    {
        // Arrange - Feb 30th (8002305009084 has check digit 4)
        string idNumber = "8002305009084";

        // Act
        var (isValid, dob, _, _, error) = RsaIdValidator.Parse(idNumber);

        // Assert
        Assert.False(isValid);
        Assert.Null(dob);
        Assert.NotNull(error);
        Assert.Contains("Invalid day", error, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public void Parse_NonLeapYearFebruary29_FailsValidation()
    {
        // Arrange - 1999-02-29 does not exist (990229500008 has Luhn check digit 5)
        string idNumber = "9902295000085";

        // Act
        var (isValid, dob, _, _, error) = RsaIdValidator.Parse(idNumber);

        // Assert
        Assert.False(isValid);
        Assert.Null(dob);
        Assert.NotNull(error);
        Assert.Contains("Invalid day", error, StringComparison.OrdinalIgnoreCase);
    }

    #endregion

    #region Gender Extraction Tests

    [Theory]
    [InlineData("0001010000089", "Female")] // Lower bound: 0000
    [InlineData("9001012500087", "Female")] // Mid female: 2500
    [InlineData("9207044999185", "Female")] // Upper bound: 4999
    public void Parse_FemaleSequenceNumbers_ExtractsFemaleGender(string idNumber, string expectedGender)
    {
        // Act
        var (isValid, _, gender, _, error) = RsaIdValidator.Parse(idNumber);

        // Assert
        Assert.True(isValid);
        Assert.Null(error);
        Assert.Equal(expectedGender, gender);
    }

    [Theory]
    [InlineData("9512315000088", "Male")]   // Lower bound: 5000
    [InlineData("8506157500081", "Male")]   // Mid male: 7500
    [InlineData("0001019999182", "Male")]   // Upper bound: 9999
    public void Parse_MaleSequenceNumbers_ExtractsMaleGender(string idNumber, string expectedGender)
    {
        // Act
        var (isValid, _, gender, _, error) = RsaIdValidator.Parse(idNumber);

        // Assert
        Assert.True(isValid);
        Assert.Null(error);
        Assert.Equal(expectedGender, gender);
    }

    #endregion

    #region Citizenship Extraction Tests

    [Theory]
    [InlineData("8001015009087", true)]  // 11th digit '0' -> SA Citizen
    [InlineData("9005200123081", true)]  // 11th digit '0' -> SA Citizen
    [InlineData("0001010000089", true)]  // 11th digit '0' -> SA Citizen
    public void Parse_CitizenshipZero_IdentifiesAsSouthAfricanCitizen(string idNumber, bool expectedIsCitizen)
    {
        // Act
        var (isValid, _, _, isCitizen, error) = RsaIdValidator.Parse(idNumber);

        // Assert
        Assert.True(isValid);
        Assert.Null(error);
        Assert.Equal(expectedIsCitizen, isCitizen);
    }

    [Theory]
    [InlineData("0512150123184", false)] // 11th digit '1' -> Permanent Resident
    [InlineData("0211156543186", false)] // 11th digit '1' -> Permanent Resident
    [InlineData("9207044999185", false)] // 11th digit '1' -> Permanent Resident
    public void Parse_CitizenshipOne_IdentifiesAsPermanentResident(string idNumber, bool expectedIsCitizen)
    {
        // Act
        var (isValid, _, _, isCitizen, error) = RsaIdValidator.Parse(idNumber);

        // Assert
        Assert.True(isValid);
        Assert.Null(error);
        Assert.Equal(expectedIsCitizen, isCitizen);
    }

    [Fact]
    public void Parse_InvalidCitizenshipDigit_FailsValidation()
    {
        // Arrange - 11th digit is '2' (900520012328 -> sum: 9+0+0+1+2+0+0+2+2+6+2+7 = 31 -> check digit = 9)
        string idNumber = "9005200123289";

        // Act
        var (isValid, _, _, isCitizen, error) = RsaIdValidator.Parse(idNumber);

        // Assert
        Assert.False(isValid);
        Assert.Null(isCitizen);
        Assert.NotNull(error);
        Assert.Contains("citizenship digit", error, StringComparison.OrdinalIgnoreCase);
    }

    #endregion
}
