using System.Text;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

public static class Phase7SetmisLookupMigrator
{
    public static readonly Dictionary<string, string> CategoryToTableMap = new()
    {
        { "Alternate_Id_Type_Id", "AlternateIdType" },
        { "Citizen_Resident_Status_Code", "CitizenStatusType" },
        { "Country_Code", "CountryType" },
        { "Nationality_Code", "NationalityType" },
        { "Home_Language_Code", "HomeLanguageType" },
        { "Province_Code", "ProvinceType" },
        { "Equity_Code", "EquityType" },
        { "Gender_Code", "GenderType" },
        { "Economic_Status_Id", "EconomicStatusType" },
        { "POPI_Act_Status_ID", "PopiActStatusType" },
        { "Communicating_Rating_Id", "CommunicatingRatingType" },
        { "Hearing_Rating_Id", "HearingRatingType" },
        { "Remembering_Rating_Id", "RememberingRatingType" },
        { "Seeing_Rating_Id", "SeeingRatingType" },
        { "Self_Care_Rating_Id", "SelfCareRatingType" },
        { "Walking_Rating_Id", "WalkingRatingType" },
        { "Designation_Id", "DesignationType" },
        { "Designation_Structure_Status_Id", "DesignationStructureStatusType" },
        { "Learning_Programme_Type_Id", "LearningProgrammeType" },
        { "Enrolment_Type_Id", "EnrolmentType" },
        { "Enrolment_Status_Id", "EnrolmentStatusType" },
        { "Enrolment_Status_Reason_Id", "EnrolmentStatusReasonType" },
        { "Internship_Status_Id", "InternshipStatusType" },
        { "Non_NQF_Interv_Status_Id", "NonNqfInterventionStatusType" },
        { "Part_Of_Id", "PartOfType" },
        { "Provider_Class_Id", "ProviderClassType" },
        { "Provider_Type_Id", "ProviderType" },
        { "Provider_Status_Id", "ProviderStatusType" },
        { "Subfield_Id", "SubfieldType" },
        { "Trade_Test_Result_Id", "TradeTestResultType" },
        { "Trade_Test_Result_Reason_Id", "TradeTestResultReasonType" },
        { "OFO_Code", "OfoCodeType" },
        { "SIC_Code", "SicCodeType" },
        { "STATSSA_Area_Code", "StatssaAreaCodeType" },
        { "Urban_Rural_ID", "UrbanRuralType" },
        { "SETA_Id", "SetaType" },
        { "Funding_Id", "FundingType" },
        { "Employer_Approval_Status_Id", "EmployerApprovalStatusType" }
    };

    public static async Task MigrateSetmisLookupsAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var db = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        try
        {
            // 1. Ensure Schema
            await db.Database.ExecuteSqlRawAsync(@"
IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'lookup')
BEGIN
    EXEC('CREATE SCHEMA [lookup]');
END");

            // 2. Ensure all lookup tables exist
            foreach (var kvp in CategoryToTableMap)
            {
                var tableName = kvp.Value;
                var createTableSql = $@"
IF OBJECT_ID(N'[lookup].[{tableName}]', N'U') IS NULL
BEGIN
    CREATE TABLE [lookup].[{tableName}] (
        [Code] NVARCHAR(50) NOT NULL PRIMARY KEY,
        [Name] NVARCHAR(250) NOT NULL,
        [Description] NVARCHAR(500) NULL,
        [Active] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SETMIS_LOADER',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
END

IF OBJECT_ID(N'[lookup].[{tableName}]', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[lookup].[{tableName}]') AND name = N'IX_{tableName}_Name')
        CREATE INDEX [IX_{tableName}_Name] ON [lookup].[{tableName}]([Name]);
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[lookup].[{tableName}]') AND name = N'IX_{tableName}_Active')
        CREATE INDEX [IX_{tableName}_Active] ON [lookup].[{tableName}]([Active]);
END";
                await db.Database.ExecuteSqlRawAsync(createTableSql);
            }

            // 3. Batch Seed Data from Embedded Resource
            var allCategories = SetmisLookupBatchSeeder.GetAllCategories();
            int totalInserted = 0;

            foreach (var kvp in CategoryToTableMap)
            {
                var categoryKey = kvp.Key;
                var tableName = kvp.Value;

                if (!allCategories.TryGetValue(categoryKey, out var items) || items.Count == 0)
                    continue;

                // Check existing count in table
                var countSql = $"SELECT COUNT(1) FROM [lookup].[{tableName}]";
                // If table already has same or more items, skip batch insert
                // Otherwise do idempotent batch insert
                var batchSize = 250;
                for (int i = 0; i < items.Count; i += batchSize)
                {
                    var batch = items.Skip(i).Take(batchSize).ToList();
                    var sb = new StringBuilder();
                    sb.AppendLine($"INSERT INTO [lookup].[{tableName}] ([Code], [Name], [Description], [Active], [CreatedAt], [CreatedBy])");
                    sb.AppendLine("SELECT v.Code, v.Name, v.Description, 1, GETUTCDATE(), 'SETMIS_LOADER'");
                    sb.AppendLine("FROM (VALUES");

                    for (int j = 0; j < batch.Count; j++)
                    {
                        var it = batch[j];
                        var codeEsc = it.code.Replace("'", "''");
                        var nameEsc = it.name.Replace("'", "''");
                        var descEsc = (it.description ?? it.name).Replace("'", "''");

                        sb.Append($"('{codeEsc}', N'{nameEsc}', N'{descEsc}')");
                        if (j < batch.Count - 1)
                            sb.AppendLine(",");
                        else
                            sb.AppendLine();
                    }

                    sb.AppendLine($") AS v(Code, Name, Description)");
                    sb.AppendLine($"WHERE NOT EXISTS (SELECT 1 FROM [lookup].[{tableName}] t WHERE t.[Code] = v.Code);");

                    try
                    {
                        await db.Database.ExecuteSqlRawAsync(sb.ToString());
                        totalInserted += batch.Count;
                    }
                    catch (Exception ex)
                    {
                        logger?.LogWarning(ex, "Could not insert batch for SETMIS table {TableName}", tableName);
                    }
                }
            }

            logger?.LogInformation("Successfully aligned and loaded SETMIS lookups across all 38 categories ({TotalInserted} items processed).", totalInserted);
        }
        catch (Exception ex)
        {
            logger?.LogError(ex, "Error executing Phase 7 SETMIS Lookups Migrator.");
        }
    }
}
