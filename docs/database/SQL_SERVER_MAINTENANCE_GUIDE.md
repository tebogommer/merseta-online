# SQL Server Maintenance & `MS_Description` Extended Properties Guide

> **Target Database:** Microsoft SQL Server Express (`localhost / NSDMS-NET`)  
> **Database User:** `NSDMS-NET`  
> **Database Password:** `NSDMS-NET`  
> **Last Updated:** August 2026

---

## 1. Overview of `MS_Description` Extended Properties

In Microsoft SQL Server, SQL Server Management Studio (SSMS) Table Designer and Object Explorer display table and column descriptions by reading from the database catalog view `sys.extended_properties` where `@name = N'MS_Description'`.

The MerSETA NSDMS provides a 3-tier maintenance mechanism to ensure these descriptions are always 100% synchronized with C# domain code:

```
[C# XML Doc Comments (Nsdms.Domain)]
                 ↓
[EF Core Dynamic Convention (ApplyXmlDocumentation)]
                 ↓
[Automated T-SQL Extended Properties Synchronizer]
                 ↓
[SQL Server Express sys.extended_properties (MS_Description)]
                 ↓
[SSMS Table Designer & Column Properties Inspector]
```

---

## 2. Viewing Extended Properties in SSMS

### 2.1 Using SSMS Table Designer
1. Connect SSMS to `localhost\SQLEXPRESS` (or `localhost` depending on your instance naming) with database `NSDMS-NET`.
2. Expand **Databases** → **NSDMS-NET** → **Tables**.
3. Right-click on any table (e.g. `dbo.CompanyLearner`) and select **Design**.
4. Open the **Properties** window (press `F4`):
   - Click the table background: The **Description** field displays the table summary.
   - Click any column (e.g. `LearnerContractNumber`, `PersonId`): The **Description** field under **Column Properties** displays the column purpose.

### 2.2 Querying `sys.extended_properties` Directly via T-SQL
To verify all table descriptions in `NSDMS-NET`:
```sql
SELECT 
    s.name AS [Schema],
    t.name AS [Table],
    ep.value AS [Table_Description]
FROM sys.extended_properties ep
JOIN sys.tables t ON ep.major_id = t.object_id
JOIN sys.schemas s ON t.schema_id = s.schema_id
WHERE ep.name = N'MS_Description' AND ep.minor_id = 0
ORDER BY s.name, t.name;
```

To verify all column descriptions for a specific table (e.g. `CompanyLearner`):
```sql
SELECT 
    c.name AS [Column_Name],
    t.name AS [Data_Type],
    c.max_length AS [Max_Length],
    c.is_nullable AS [Is_Nullable],
    ep.value AS [Column_Description]
FROM sys.columns c
JOIN sys.tables tbl ON c.object_id = tbl.object_id
JOIN sys.types t ON c.user_type_id = t.user_type_id
LEFT JOIN sys.extended_properties ep 
    ON ep.major_id = c.object_id 
   AND ep.minor_id = c.column_id 
   AND ep.name = N'MS_Description'
WHERE tbl.name = N'CompanyLearner'
ORDER BY c.column_id;
```

---

## 3. How to Synchronize Extended Properties

### Option 1: Via the Interactive Web UI (Recommended for Developers)
1. Open the NSDMS web application at `https://localhost:7001/developer/schema` (or `/developer/docs`).
2. Click the primary button **"Sync to SQL Server (MS_Description)"** in the top bar.
3. The system executes the dynamic synchronizer and displays a confirmation toast with the exact count of updated properties.

### Option 2: Via Idempotent T-SQL Migration Script (Recommended for DBAs / CI Pipelines)
1. Open the script file located at:
   `dotnet/Nsdms.Infrastructure/Data/SqlScripts/Sync_Database_Extended_Properties.sql`
2. Open in SSMS or execute via `sqlcmd`:
   ```bash
   sqlcmd -S localhost -d NSDMS-NET -U NSDMS-NET -P NSDMS-NET -i dotnet/Nsdms.Infrastructure/Data/SqlScripts/Sync_Database_Extended_Properties.sql
   ```
3. The script is 100% idempotent:
   - If `MS_Description` does not exist for a table/column, it calls `sp_addextendedproperty`.
   - If `MS_Description` already exists, it calls `sp_updateextendedproperty`.

---

## 4. Adding or Updating Descriptions in Source Code

When creating new domain models or adding new database columns:

1. Add standard XML documentation comments in the domain class (`dotnet/Nsdms.Domain`):
   ```csharp
   /// <summary>
   /// Unique SARS Skills Development Levy registration number (e.g. L123456789).
   /// </summary>
   public string SdlNumber { get; set; } = string.Empty;
   ```
2. Build the project:
   ```bash
   dotnet build dotnet/Nsdms.Domain/Nsdms.Domain.csproj
   ```
3. Run the test suite:
   ```bash
   dotnet test dotnet/Nsdms.Tests/Nsdms.Tests.csproj --filter "FullyQualifiedName~DatabaseDocumentationTests"
   ```
   The test runner automatically updates `docs/database/DATA_DICTIONARY.md`, `docs/DATABASE_DICTIONARY.md`, and `Sync_Database_Extended_Properties.sql`.
4. Click **"Sync to SQL Server (MS_Description)"** in the Developer UI or run the SQL script to push changes to your local database instance.
