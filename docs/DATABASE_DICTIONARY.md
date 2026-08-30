# MerSETA NSDMS — Database Data Dictionary

> **Generated:** 2026-08-30 | **Target Engine:** Microsoft SQL Server Express (`localhost:NSDMS-NET`) | **Architecture:** .NET 10 EF Core Clean Architecture

---

## 📋 Schema Overview Table

| Schema | Table Name | CLR Entity | Columns | Primary Key | Description |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `dbo` | `Organisation` | `Organisation` | 18 | `Id` | Registered employers, host workplaces, and legal enterprise entities under MerSETA jurisdiction. |
| `dbo` | `Person` | `Person` | 16 | `Id` | Individual identity demographic profile (RSA ID, demographics, contact info). |
| `dbo` | `OrganisationContact` | `OrganisationContact` | 12 | `Id` | Relational link between an organisation and authorized contact persons / SDFs. |
| `dbo` | `OrganisationSite` | `OrganisationSite` | 14 | `Id` | Physical training sites, branch offices, and manufacturing facilities for an organisation. |
| `dbo` | `TrainingProvider` | `TrainingProvider` | 16 | `Id` | Accredited Skills Development Providers (SDP) offering registered occupational qualifications. |
| `dbo` | `WspSubmission` | `WspSubmission` | 18 | `Id` | Workplace Skills Plan (WSP) & Annual Training Report (ATR) submissions for mandatory grants. |
| `dbo` | `GrantApplication` | `GrantApplication` | 19 | `Id` | Discretionary Grant project funding applications submitted during open funding windows. |
| `dbo` | `grant_moa` | `GrantMoa` | 20 | `Id` | Memorandum of Agreement contractual legal commitments for approved discretionary grants. |
| `dbo` | `grant_moa_milestone` | `GrantMoaMilestone` | 15 | `Id` | Tranche delivery milestones (Inception, Midterm, Final, Closeout) for MOA contracts. |
| `dbo` | `grant_tranche_payment` | `GrantTranchePayment` | 18 | `Id` | Tranche tax invoice requisitions, finance dual-authorization, and EFT payout batches. |
| `dbo` | `mandatory_grant_disbursement` | `MandatoryGrantDisbursement` | 16 | `Id` | Statutory 20% Skills Development Levy rebate calculation ledger for compliant employers. |
| `dbo` | `InterSetaTransfer` | `InterSetaTransfer` | 18 | `Id` | Chamber and SIC code employer migrations with counterpart SETAs and DHET approvals. |
| `dbo` | `EtqaAssessor` | `EtqaAssessor` | 17 | `Id` | Registered ETQA assessors and moderators with registered unit standard / qualification scopes. |
| `dbo` | `WorkplaceApproval` | `WorkplaceApproval` | 18 | `Id` | Workplace site inspection approval records with mentor-to-learner ratio allocations. |
| `dbo` | `CompanyLearner` | `CompanyLearner` | 20 | `Id` | Registered apprentices, learnerships, and skills programme learners. |
| `dbo` | `LearnerTradeTest` | `LearnerTradeTest` | 18 | `Id` | Trade test serial certifications, ARPL evaluations, and competency awards. |
| `dbo` | `WorkflowInstance` | `WorkflowInstance` | 14 | `Id` | Universal state machine execution instances for long-running approval lifecycles. |
| `dbo` | `WorkflowTask` | `WorkflowTask` | 17 | `Id` | Actionable review, inspection, and verification tasks assigned to SETA roles. |
| `dbo` | `DocumentMetadata` | `DocumentMetadata` | 14 | `Id` | SHA-256 integrity hashed digital evidence files in the Document Vault. |
| `dbo` | `setmis_submission_batch` | `SetmisSubmissionBatch` | 14 | `Id` | Department of Higher Education & Training (DHET) flat-file extract submission batch logs. |
| `dbo` | `AuditLog` | `AuditLog` | 10 | `Id` | Immutable operational audit trail capturing user, action, timestamp, and JSON before/after snapshots. |
| `lookup` | `CategoryType` | `CategoryType` | 6 | `Code` | Lookup: Organisation category classifications (Levy Paying, Non-Levy Paying, Exempt). |
| `lookup` | `ChamberType` | `ChamberType` | 6 | `Code` | Lookup: MerSETA chambers (Auto, Metal, Plastics, New Tyre, Electronics). |
| `lookup` | `SicCodeType` | `SicCodeType` | 7 | `Code` | Lookup: Standard Industrial Classification (SIC) sub-sector codes. |
| `lookup` | `ProvinceType` | `ProvinceType` | 6 | `Code` | Lookup: South African 9 administrative provinces. |
| `lookup` | `GenderType` | `GenderType` | 6 | `Code` | Lookup: Gender demographic classifications. |
| `lookup` | `EquityType` | `EquityType` | 6 | `Code` | Lookup: BBBEE / Employment Equity demographic groups. |
| `lookup` | `DisabilityType` | `DisabilityType` | 6 | `Code` | Lookup: Disability status and accommodation classifications. |
| `lookup` | `GrantWindowType` | `GrantWindowType` | 6 | `Code` | Lookup: Discretionary Grant funding window types (PIVOTAL, Non-PIVOTAL, Bursaries). |
| `lookup` | `StatusType` | `StatusType` | 6 | `Code` | Lookup: Universal entity workflow lifecycle status codes. |

---

## 🏛️ Key Table Specifications

### `dbo.grant_moa`
**Description:** Memorandum of Agreement contractual legal commitments for approved discretionary grants.  
**Primary Key:** `Id`  
**Columns:**
- `Id` (`int`, **NOT NULL**, 🔑 **PK**): Auto-generated integer primary key identifier.
- `MoaNumber` (`nvarchar(50)`, **NOT NULL**): Unique legal MOA contract reference number (e.g. `MOA-2026-DG-0001`).
- `GrantApplicationId` (`int`, **NOT NULL**, 🔗 **FK**): Foreign key link to `GrantApplication`.
- `OrganisationId` (`int`, **NOT NULL**, 🔗 **FK**): Foreign key link to `Organisation`.
- `TotalContractValue` (`decimal(18,2)`, **NOT NULL**): Total committed funding in South African Rands (ZAR).
- `ContractStartDate` (`datetime2`, **NOT NULL**): MOA effective start date.
- `ContractEndDate` (`datetime2`, **NOT NULL**): MOA final deliverable completion date.
- `MoaStatusCode` (`nvarchar(30)`, **NOT NULL**): Lifecycle status (`Drafting`, `PendingSignoff`, `Active`, `Completed`, `Terminated`).
- `SetaSignatoryPersonId` (`int`, NULL, 🔗 **FK**): SETA CEO / COO signatory profile.
- `EmployerSignatoryPersonId` (`int`, NULL, 🔗 **FK**): Employer CEO / Managing Director signatory profile.
- `CreatedAt` (`datetime2`, **NOT NULL**): UTC creation timestamp.
- `CreatedBy` (`nvarchar(100)`, NULL): Creator identifier.
- `ModifiedAt` (`datetime2`, NULL): UTC update timestamp.
- `ModifiedBy` (`nvarchar(100)`, NULL): Last updater.

### `dbo.setmis_submission_batch`
**Description:** Department of Higher Education & Training (DHET) flat-file extract submission batch logs.  
**Primary Key:** `Id`  
**Columns:**
- `Id` (`int`, **NOT NULL**, 🔑 **PK**): Auto-generated integer primary key identifier.
- `BatchNumber` (`nvarchar(50)`, **NOT NULL**): Unique submission batch reference (e.g. `SETMIS-2026-Q1-001`).
- `ReportingPeriod` (`nvarchar(20)`, **NOT NULL**): Reporting quarter / year (e.g. `2026-Q1`).
- `ExtractionDate` (`datetime2`, **NOT NULL**): UTC timestamp when the extract flat files were generated.
- `TotalRecords` (`int`, **NOT NULL**): Total number of entity records extracted.
- `ValidRecords` (`int`, **NOT NULL**): Number of records passing all SETMIS pre-submission validation rules.
- `ErrorRecords` (`int`, **NOT NULL**): Number of records requiring remediation.
- `SubmissionStatusCode` (`nvarchar(30)`, **NOT NULL**): Status (`Extracted`, `Validated`, `Submitted`, `Accepted`, `Rejected`).
- `DhetAcknowledgementRef` (`nvarchar(100)`, NULL): Ministerial upload acknowledgment reference.
- `ExtractedBy` (`nvarchar(100)`, NULL): User identity responsible for extraction.
