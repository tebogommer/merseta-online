# 15. Document Management Lifecycle & Storage

## 1. Domain Purpose
The NSDMS acts as a massive compliance repository, requiring thousands of PDF and image uploads daily (e.g. proof of IDs, company registrations, audit reports, banking details). The legacy system mapped evidence to almost every workflow via the polymorphic `Doc.java` entity, storing actual byte arrays internally inside a split `doc_byte_new` relational table.

## 2. Structural Modernization (Anti-Pattern Elimination)
The legacy architecture suffers from the **Database-as-a-Filesystem** anti-pattern. `DocByte` instances routinely bloated the primary DB cluster containing `LONGBLOB` columns for binary data. The COT Touch UI/C# modernization will explicitly decouple metadata from payload.

* **Metadata Repository**: The relational SQL Server database will strictly track document states, workflow ownership, expiration, and MIME types within the `Document` schema.
* **Payload Repository**: Binary chunks (PDFs, Images) will be handled off-cluster using Azure Blob Storage or S3 compatible object storage. Storage URIs will be referenced natively in the relational model.

## 3. The Polymorphic Document Router
Because *any* record can require an upload (User, Company, Grant, WSP), the legacy system used explicit nullable foreign keys in the `Doc` table (`user_id`, `company_id`, `wsp_id`...). This pattern must be modernized cleanly:

1. **Explicit Foreign Keys**: Kept only for heavy relational domains (e.g. `Document.CompanyId`, `Document.UserId`).
2. **Abstract Route Pointers**: Legacy stored polymorphic hooks via `target_class` and `target_key`. The Entity Framework equivalent must evaluate these strings into distinct mapped DTOs during hydration.

## 4. Required Data Fields Definition
| Field Code | Data Type | Required | Business Rule |
| :--- | :--- | :--- | :--- |
| `Id` | `UNIQUEIDENTIFIER` | Yes | Primary sequence GUID map. |
| `BlobUri` | `VARCHAR(MAX)` | Yes | Decouples from `LONGBLOB`. Real cloud URI payload pointing to object storage. |
| `Extension` | `VARCHAR(20)` | Yes | e.g. `.pdf` |
| `MimeType` | `VARCHAR(200)` | Yes | e.g. `application/pdf` |
| `VersionNumber` | `INT` | Yes | Auto-increments if a file is re-uploaded upon rejection. |
| `ApprovalStatusEnum` | `INT` | Yes | Status (Pending, Approved, Rejected). |
| `TargetEntityId` | `UNIQUEIDENTIFIER` | Conditional | The Task or Row the document technically validates against. |

## 5. Security & Socratic Governance
**Direct access to the Blob Storage URLs MUST be obfuscated or aggressively proxy-signed**. Only users who clear the COT Access Control Rules abilities for the respective `TargetEntityId` may fetch the URI via the backend API. 

## 8. Business Rules Engine (The "Gotchas")
- **The BLOB Storage Lock:** Legacy Java stored multi-megabyte `LONGBLOB` files directly inside the relational database (`doc_byte_new`), destroying SQL Server cache plans and saturating transaction logs.
  - ✨ **Modernization Directive (How-To Mapping):** Store all physical files in an internal S3/Azure Blob bucket. The `DocByte` table must be dropped. The `Doc` entity will only retain a string `BlobUri` reference.
- **Polymorphic Link Breakage:** The legacy system tied a document to a record via `TargetClass` strings ("haj.com.entity.Wsp") and `TargetKey` longs.
  - ✨ **Modernization Directive (How-To Mapping):** Map strict `TargetEntityEnum` values representing the Domain instead of brittle Java class string reflections.

## 9. Standardized Error Messages

To eliminate ambiguity and ensure UI alignment across the application, the following hardcoded error string literals **MUST** be implemented within the presentation layer and `COT Business Rules Validation (Result.ShowMessage)` constraints for this domain:

| Error Code | Hardcoded String Literal | Trigger Condition |
| :--- | :--- | :--- |
| `ERR_DOC_001` | 'Invalid file format. Only PDF, JPEG, and PNG are permitted.' | Primary Validation Failure |
| `ERR_DOC_002` | 'Document version mismatch. A newer iteration of this compliance document was already uploaded.' | State Machine Blocked |
| `ERR_DOC_003` | 'Payload exceeds the 5MB upload limit.' | Domain Invariant Violated |


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
