using Nsdms.Application.Services;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class SetmisLookupTests
{
    [Fact]
    public void SetmisLookupBatchSeeder_LoadsAll41Categories()
    {
        // Act
        var categories = SetmisLookupBatchSeeder.GetAllCategories();

        // Assert
        Assert.NotNull(categories);
        Assert.Equal(41, categories.Count);
    }

    [Fact]
    public void SetmisLookupBatchSeeder_LoadsFullRecordCount()
    {
        // Act
        var categories = SetmisLookupBatchSeeder.GetAllCategories();
        var totalRecords = categories.Values.Sum(v => v.Count);

        // Assert: Exactly 24,986 verified SETMIS & NLRD records loaded
        Assert.Equal(24986, totalRecords);
    }

    [Theory]
    [InlineData("Alternate_Id_Type_Id", 13)]
    [InlineData("Citizen_Resident_Status_Code", 5)]
    [InlineData("Country_Code", 249)]
    [InlineData("Designation_Id", 2)]
    [InlineData("Designation_Structure_Status_Id", 6)]
    [InlineData("Economic_Status_Id", 4)]
    [InlineData("Employer_Approval_Status_Id", 3)]
    [InlineData("Enrolment_Status_Id", 8)]
    [InlineData("Enrolment_Status_Reason_Id", 13)]
    [InlineData("Enrolment_Type_Id", 8)]
    [InlineData("Equity_Code", 5)]
    [InlineData("Funding_Id", 5)]
    [InlineData("Gender_Code", 2)]
    [InlineData("Hearing_Rating_Id", 6)]
    [InlineData("Communicating_Rating_Id", 6)]
    [InlineData("Remembering_Rating_Id", 6)]
    [InlineData("Seeing_Rating_Id", 6)]
    [InlineData("Self_Care_Rating_Id", 6)]
    [InlineData("Walking_Rating_Id", 6)]
    [InlineData("Home_Language_Code", 14)]
    [InlineData("Internship_Status_Id", 3)]
    [InlineData("Learning_Programme_Type_Id", 11)]
    [InlineData("Nationality_Code", 23)]
    [InlineData("Non_NQF_Interv_Status_Id", 3)]
    [InlineData("OFO_Code", 1454)]
    [InlineData("POPI_Act_Status_ID", 3)]
    [InlineData("Part_Of_Id", 5)]
    [InlineData("Provider_Class_Id", 7)]
    [InlineData("Provider_Status_Id", 11)]
    [InlineData("Provider_Type_Id", 5)]
    [InlineData("Province_Code", 11)]
    [InlineData("SETA_Id", 21)]
    [InlineData("SIC_Code", 815)]
    [InlineData("STATSSA_Area_Code", 22108)]
    [InlineData("Subfield_Id", 68)]
    [InlineData("Trade_Test_Result_Id", 2)]
    [InlineData("Trade_Test_Result_Reason_Id", 1)]
    [InlineData("Urban_Rural_ID", 3)]
    [InlineData("Abet_Band_Id", 5)]
    [InlineData("Qualification_Type_Id", 27)]
    [InlineData("Honours_Class_Id", 27)]
    public void SetmisLookupBatchSeeder_CategoryContainsExpectedItemCount(string categoryKey, int expectedCount)
    {
        // Act
        var items = SetmisLookupBatchSeeder.GetCategoryItems(categoryKey);

        // Assert
        Assert.NotNull(items);
        Assert.Equal(expectedCount, items.Count);
    }

    [Fact]
    public void SetmisLookupBatchSeeder_SpecificCriticalLookupsExist()
    {
        // Act
        var setas = SetmisLookupBatchSeeder.GetCategoryItems("SETA_Id");
        var alternateIds = SetmisLookupBatchSeeder.GetCategoryItems("Alternate_Id_Type_Id");
        var citizenStatuses = SetmisLookupBatchSeeder.GetCategoryItems("Citizen_Resident_Status_Code");
        var ofoCodes = SetmisLookupBatchSeeder.GetCategoryItems("OFO_Code");
        var abetBands = SetmisLookupBatchSeeder.GetCategoryItems("Abet_Band_Id");
        var qualTypes = SetmisLookupBatchSeeder.GetCategoryItems("Qualification_Type_Id");
        var honoursClasses = SetmisLookupBatchSeeder.GetCategoryItems("Honours_Class_Id");

        // Assert MerSETA (Code 17)
        Assert.Contains(setas, x => x.code == "17" && x.name.Contains("MERSETA"));

        // Assert Passport Number (Code 527)
        Assert.Contains(alternateIds, x => x.code == "527" && x.name.Contains("Passport"));

        // Assert South African Citizen (Code SA)
        Assert.Contains(citizenStatuses, x => x.code == "SA");

        // Assert OFO Code 264202 Editor
        Assert.Contains(ofoCodes, x => x.code == "264202" && x.name == "Editor");

        // Assert NLRD ABET Band Level 4 (Code 5)
        Assert.Contains(abetBands, x => x.code == "5" && x.name.Contains("ABET Level 4"));

        // Assert NLRD Qualification Type Occupational Certificate (Code 721)
        Assert.Contains(qualTypes, x => x.code == "721" && x.name.Contains("Occupational Certificate"));

        // Assert NLRD Honours Class Cum Laude (Code 21)
        Assert.Contains(honoursClasses, x => x.code == "21" && x.name.Contains("Cum Laude"));
    }

    [Fact]
    public async Task LookupService_GetAllLookupMetadata_Returns53Categories()
    {
        // Arrange
        var service = new LookupService(null!, null!);

        // Act
        var metadata = await service.GetAllLookupMetadataAsync();

        // Assert
        Assert.NotNull(metadata);
        Assert.Equal(53, metadata.Count);
        Assert.Contains(metadata, x => x.TableName == "StatssaAreaCodeType");
        Assert.Contains(metadata, x => x.TableName == "OfoCodeType");
        Assert.Contains(metadata, x => x.TableName == "SetaType");
        Assert.Contains(metadata, x => x.TableName == "AlternateIdType");
        Assert.Contains(metadata, x => x.TableName == "CommunicatingRatingType");
        Assert.Contains(metadata, x => x.TableName == "AbetBandType");
        Assert.Contains(metadata, x => x.TableName == "QualificationTypeType");
        Assert.Contains(metadata, x => x.TableName == "HonoursClassType");
    }
}
