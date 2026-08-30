# ETL Mapping Specification: Legacy SETMIS / Java DB to .NET 10 NSDMS-NET

**Document Version:** 1.0.0  
**Last Updated:** August 29, 2026  
**Source Database:** Legacy Java SETMIS DB (`SETMIS LLP SCHEMA 08 APR 2026 16_01.sql` / `doc_1.sql` / ASP.NET Schema)  
**Target Database:** SQL Server Express (`NSDMS-NET` on `localhost`)  

---

## 🎯 Executive Summary & Conventions

This document defines the Extract, Transform, Load (ETL) mapping rules to migrate legacy SETMIS files and relational tables into the modernized .NET 10 Clean Architecture database (`NSDMS-NET`).

### Target Architectural Principles:
1. **Naming Standard**: All database tables, columns, and properties use **PascalCase** (e.g. `Organisation`, `Person`, `Visit`, `CategoryType`).
2. **Lookup Schema**: All lookup tables reside in the **`lookup` schema** (e.g., `lookup.CategoryType`, `lookup.StatusType`, `lookup.ProvinceType`).
3. **Lookup Primary Key**: Lookup tables use `Code` (`varchar(15)`) as the Primary Key (e.g., `'EMP'`, `'GP'`, `'ACTIVE'`) instead of auto-increment integers to ensure transparent queries and foreign key readability.
4. **Entity Primary Key**: Domain entity tables use auto-increment integer `Id`.
5. **Auditing**: Every table contains audit columns (`CreatedAt`, `CreatedBy`, `ModifiedAt`, `ModifiedBy`), and mutations trigger dual-writes into `dbo.AuditLog`.
6. **Performance Indexing**: All foreign keys, lookup codes, SDL numbers, RSA ID numbers, and search terms are explicitly indexed.

---

## 📊 Core Entity Mapping Matrix

| Legacy SETMIS Table / File | Target Table (`NSDMS-NET`) | Primary Key Transformation | Business Purpose |
|----------------------------|----------------------------|----------------------------|------------------|
| `FILE 200` (Company/Employer) | `dbo.Organisation` | Legacy `Company_ID` → `Id` (Int) | Employer & Training Provider Registry |
| `FILE 100` (Training Provider) | `dbo.Organisation` | Legacy `Provider_ID` → `Id` (Int) | Mapped with `CategoryCode = 'SDP'` |
| `FILE 400` (Person Demographics) | `dbo.Person` | Legacy `Person_ID` → `Id` (Int) | Demographics & Contact Records |
| `FILE 401` (Assessor/Moderator) | `dbo.EtqaAssessor` | Legacy `Assessor_ID` → `Id` (Int) | ETQA Assessor/Moderator Accreditation |
| `FILE 500+` (Learner Submissions) | `dbo.WspSubmission` / Learners | Legacy `Learner_ID` → `Id` (Int) | Workplace Skills Plans & Learnerships |
| `aspnet_Users` / `aspnet_Membership` | `dbo.User` | Legacy `UserId` (Guid) → `Id` (Int) | Authentication & User Roles |
| Legacy SARS Levy Imports | `dbo.LevyFile` & `dbo.LevyFileLine` | Legacy Import ID → `Id` (Int) | SARS Levy Processing & GP ERP Ingestion |
| Legacy Grant Requests | `dbo.GrantApplication` | Legacy Grant ID → `Id` (Int) | Discretionary Grant Applications (DG) |

---

## 🛠️ Detailed Field Mappings & Transformations

### 1. Employers / Organisations (`FILE 200` → `dbo.Organisation`)

| Legacy Field (`FILE 200`) | Target Field (`dbo.Organisation`) | Data Type | Transformation / Rule |
|---------------------------|-----------------------------------|-----------|-----------------------|
| `SDL_No` / `Levy_Number` | `SdlNumber` | `varchar(20)` | **Unique Index**. Trim whitespace. |
| `Company_Name` | `CompanyName` | `nvarchar(200)` | **Indexed**. Required. |
| `Trading_Name` | `TradingName` | `nvarchar(200)` | Nullable. Default to `CompanyName` if empty. |
| `Registration_No` | `RegistrationNumber` | `varchar(50)` | Nullable. |
| `Tax_No` | `TaxNumber` | `varchar(50)` | Nullable. |
| `Category_ID` | `CategoryCode` | `varchar(15)` | **FK to `lookup.CategoryType`**. Map `1` → `'EMP'`, `2` → `'SDP'`, `3` → `'ASS'`. |
| `Status_ID` | `StatusCode` | `varchar(15)` | **FK to `lookup.StatusType`**. Map `1` → `'ACTIVE'`, `2` → `'INACTIVE'`. |
| `Province_ID` | `ProvinceCode` | `varchar(15)` | **FK to `lookup.ProvinceType`**. Map `1` → `'GP'`, `2` → `'KZN'`, `3` → `'WC'`, etc. |
| `Sector_ID` | `SectorCode` | `varchar(15)` | **FK to `lookup.SectorType`**. Map `1` → `'AUTO'`, `2` → `'METAL'`, etc. |
| `Primary_Contact_ID` | `PrimaryContactPersonId` | `int` | **FK to `dbo.Person`**. |
| N/A | `CreatedAt` | `datetime2` | Set to `GetUtcDate()` if missing. |
| N/A | `CreatedBy` | `nvarchar(100)` | Default `'ETL_MIGRATION'`. |

---

### 2. Person & Demographics (`FILE 400` → `dbo.Person`)

| Legacy Field (`FILE 400`) | Target Field (`dbo.Person`) | Data Type | Transformation / Rule |
|---------------------------|-----------------------------|-----------|-----------------------|
| `First_Name` | `FirstName` | `nvarchar(100)` | Required. |
| `Last_Name` | `LastName` | `nvarchar(100)` | Required. |
| `ID_Number` / `RSA_ID` | `RsaIdNumber` | `varchar(13)` | **Indexed**. Calculate `DateOfBirth` from first 6 digits (YYMMDD). |
| `Birth_Date` | `DateOfBirth` | `datetime2` | Derived from RSA ID if legacy field is null. |
| `Email_Address` | `Email` | `nvarchar(150)` | Trim & lowercase. |
| `Phone_Mobile` | `PhoneNumber` | `varchar(30)` | Sanitize formatting characters. |
| `Is_Active` | `IsActive` | `bit` | Default `1`. |

---

### 3. Employer Visits (`FILE 200_Visits` → `dbo.Visit`)

| Legacy Field | Target Field (`dbo.Visit`) | Data Type | Transformation / Rule |
|--------------|----------------------------|-----------|-----------------------|
| `Company_ID` | `OrganisationId` | `int` | **FK to `dbo.Organisation`**. Required. |
| `Contact_Person_ID` | `ContactPersonId` | `int` | **FK to `dbo.Person`**. **Mandatory Constraint**: If missing, link to `Organisation.PrimaryContactPersonId`. |
| `Visit_Type_ID` | `VisitTypeCode` | `varchar(15)` | **FK to `lookup.VisitTypeType`** (`'MONITOR'`, `'APPROVAL'`, `'AUDIT'`). |
| `Status_ID` | `StatusCode` | `varchar(15)` | **FK to `lookup.StatusType`**. |
| `Visit_Date` | `VisitDate` | `datetime2` | Required. |
| `Title` | `Title` | `nvarchar(200)` | Required. |
| `Notes` / `Outcome` | `OutcomeNotes` | `nvarchar(max)` | Nullable. |

---

### 4. Lookup Table Transformations (`lookup.* Schema`)

All legacy integer-based lookups are transformed to **Code-based Primary Keys (`varchar(15)`)** in the `lookup` schema:

#### A. Status Types (`lookup.StatusType`)
- Legacy `1` → `Code = 'ACTIVE'`, `Name = 'Active'`
- Legacy `2` → `Code = 'INACTIVE'`, `Name = 'Inactive'`
- Legacy `3` → `Code = 'PENDING'`, `Name = 'Pending Approval'`
- Legacy `4` → `Code = 'APPROVED'`, `Name = 'Approved'`
- Legacy `5` → `Code = 'REJECTED'`, `Name = 'Rejected'`

#### B. Province Types (`lookup.ProvinceType`)
- Legacy `1` → `Code = 'GP'`, `Name = 'Gauteng'`
- Legacy `2` → `Code = 'KZN'`, `Name = 'KwaZulu-Natal'`
- Legacy `3` → `Code = 'WC'`, `Name = 'Western Cape'`
- Legacy `4` → `Code = 'EC'`, `Name = 'Eastern Cape'`
- Legacy `5` → `Code = 'FS'`, `Name = 'Free State'`
- Legacy `6` → `Code = 'MP'`, `Name = 'Mpumalanga'`
- Legacy `7` → `Code = 'NW'`, `Name = 'North West'`
- Legacy `8` → `Code = 'NC'`, `Name = 'Northern Cape'`
- Legacy `9` → `Code = 'LP'`, `Name = 'Limpopo'`

#### C. Organisation Categories (`lookup.CategoryType`)
- Legacy `1` → `Code = 'EMP'`, `Name = 'Employer'`
- Legacy `2` → `Code = 'SDP'`, `Name = 'Skills Development Provider'`
- Legacy `3` → `Code = 'ASS'`, `Name = 'Assessment Centre'`

---

## ⚡ Performance & Indexing Strategy

To maintain maximum query throughput during bulk ETL ingestion and high-concurrency Blazor WebApp operations, the following indexes are deployed in `NSDMS-NET`:

1. **Unique Indexes**:
   - `dbo.Organisation(SdlNumber)`
   - `lookup.CategoryType(Code)`
   - `lookup.StatusType(Code)`
   - `lookup.ProvinceType(Code)`
   - `lookup.SectorType(Code)`
2. **Non-Clustered Indexes**:
   - `dbo.Organisation(CompanyName, CategoryCode, StatusCode)`
   - `dbo.Person(RsaIdNumber, Email)`
   - `dbo.Visit(OrganisationId, ContactPersonId, VisitDate)`
   - `dbo.WspSubmission(OrganisationId, FinYear, StatusCode)`
   - `dbo.LevyFileLine(LevyFileId, SdlNumber, IsReconciled)`
   - `dbo.AuditLog(EntityName, RecordId, Timestamp)`

---

## 🔄 Audit & Integrity Rules During ETL

1. **Double Write Policy**: Every insert or update during ETL writes the target entity AND appends an entry into `dbo.AuditLog` capturing:
   - `EntityName`: Target entity (e.g. `'Organisation'`, `'Person'`)
   - `RecordId`: Newly assigned `Id`
   - `ActionName`: `'ETL_MIGRATION_INSERT'`
   - `Actor`: `'ETL_SYSTEM'`
   - `MetadataJson`: JSON string containing snapshot of imported fields.
2. **Orphan Control**: Records with broken foreign key references (e.g. `FILE 200` pointing to non-existent `Contact_ID`) fall back to auto-generated default records or system placeholder entities, logged in `dbo.AuditLog`.
