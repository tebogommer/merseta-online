# 16. National Data Imports (SAQA/OFO Component)

## 1. Domain Purpose
The NSDMS relies completely on external, federally governed taxonomic structures: SAQA (South African Qualifications Authority) Qualifications/Unit Standards and DHET OFO (Organising Framework for Occupations) Codes. Users do not manually "create" these entities. They are pulled in bulk.

## 2. The Legacy JAXB Ingestion Engine
Historically, `SAQAQualificationsAndUsLookupDataQuarterlyImportUI` allowed admins to upload raw gigabyte-sized XML fragments periodically. `LoadQualification.java` used heavy multi-pass memory models:
1. Parse XML to Object Array.
2. Truncate staging tables (e.g. `saqa_qualification_temp`).
3. Stream data to Temp.
4. Execute heavy DIFF calculation.
5. Apply UPSERT against active models.

## 3. Structural Modernization (Event-Driven Import)
Instead of forcing the front-end to wait for parsing overhead, the COT Touch UI/C# stack will handle this as a **Background external background worker (e.g., Hangfire) integrated via COT Custom Actions/Worker** task:
* Admin uploads `.xml` via COT Touch UI to S3 and fires a "Process Import" event.
* A microservice or .NET Background Task dequeues the XML.
* The system performs streaming UPSERT leveraging SQL Server's `MERGE` statements instead of row-by-row Entity Framework loops to avoid `OutOfMemory` exceptions.

## 4. Required Data Fields Definition (Qualifications)
| Field Code | Data Type | Required | Business Rule |
| :--- | :--- | :--- | :--- |
| `SaqaQualificationId` | `INT` | Yes | Note: NOT a Guid. SAQA explicitly issues integer based primary keys from the state. |
| `QualificationTitle` | `VARCHAR(300)` | Yes | Raw text from XML feed. |
| `NqfLevelId` | `INT` | Yes | Maps to national framework brackets. |
| `TotalCredits` | `INT` | Yes | Numeric volume assigned to completion. |
| `RegistrationStartDate` | `DATETIME2` | Yes | Legal inception of Qualification state. |
| `RegistrationEndDate` | `DATETIME2` | Yes | De-registration phase triggers "Teach-Out" algorithms. |

## 5. Domain Rules
* **Teach-Out Mechanics:** If an individual enrols before the `RegistrationEndDate`, they have a legal grace period (Teach-out period) to finish their training. The engine must respect this when evaluating active status logic downstream. 
* **OFO Locking:** OFO codes cannot be tampered with. If a learner is registered on an OFO code that becomes deprecated by the government, the historical link must remain intact using a `CASE WHEN` "Sticky flag" invariant.

## 8. Business Rules Engine (The "Gotchas")
- **Synchronous Upload Exhaustion:** Legacy Java processed massive SAQA XML files sequentially in the foreground, locking the Tomcat thread and throwing HTTP 504 timeouts.
  - ✨ **Modernization Directive (How-To Mapping):** Uploads must immediately return an HTTP 202 Accepted. The XML parsing and DB Upsert via Bulk Insert/MERGE will occur asynchronously via a distributed backend worker like external background worker (e.g., Hangfire) integrated via COT Custom Actions, polling status to a read-only table on the frontend.
- **Data Mutation Restrictions:** National lookup data (OFO codes, SAQA IDs) are provided by the government.
  - ✨ **Modernization Directive (How-To Mapping):** Do NOT build conventional CRUD UI screens for SAQA/OFO grids. They must remain aggressively Read-Only for administrative users. Only system-level batch jobs can mutate this data schema.

## 9. Standardized Error Messages

To eliminate ambiguity and ensure UI alignment across the application, the following hardcoded error string literals **MUST** be implemented within the presentation layer and `COT Business Rules Validation (Result.ShowMessage)` constraints for this domain:

| Error Code | Hardcoded String Literal | Trigger Condition |
| :--- | :--- | :--- |
| `ERR_XML_001` | 'The structural schema of the uploaded SAQA XML is invalid or corrupted.' | Primary Validation Failure |
| `ERR_XML_002` | 'An import is currently running. Please wait for the background queue to clear.' | State Machine Blocked |
| `ERR_XML_003` | 'Critical framework mismatch: The imported NQF level bounds do not exist locally.' | Domain Invariant Violated |


### 6.1 Code On Time (COT) Native Form Validations
To satisfy the rapid Touch UI generation of COT, the following rules must be directly injected into the Data Controllers mapping to CurrentEntity to handle synchronous database constraints and immediate UI validation.

#### A) SQL Business Rule (Server-Side Validation)
This SQL snippet executes within COT's [Controller].xml lifecycle before insertion to enforce business invariants dynamically based on the exact table structure.

`sql
-- Pattern: Code On Time SQL Business Rule (Before Insert/Update)
-- Target Controller: CurrentEntity
-- Action: Insert, Update

-- Example Constraint Enforcement:
IF EXISTS (SELECT 1 FROM [dbo].[CurrentEntity] WHERE [Id] = @Id AND [StatusId] IS NULL)
BEGIN
    SET @BusinessRules_PreventDefault = 1;
    SET @Result_ShowMessage = 'A valid Workflow Status must be assigned before committing this record in CurrentEntity.';
END
`

#### B) JavaScript validation (Client-Side Touch UI)
This JS snippet enforces immediate checks on the UI input before a network dispatch, maintaining UI responsiveness for the CurrentEntity controller.

`javascript
// Pattern: Code On Time JavaScript Business Rule (Before Insert/Update)
// Target Controller: CurrentEntity
function override_before_insert(args) {
    var stat = ('StatusId');
    
    if (stat == null || stat === '') {
        this.preventDefault();
        this.result.showMessage('Workflow Status cannot be left blank during creation.');
        this.result.focus('StatusId');
        return;
    }
}
`
