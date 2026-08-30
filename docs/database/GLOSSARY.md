# MerSETA NSDMS — Canonical Domain Terms & Schema Glossary

> **Document Version:** 2.0.0  
> **Target Platform:** .NET 10 Blazor Server & Microsoft SQL Server 2022 / Express  
> **Governance Standard:** Zero Hardcoding, Singular PascalCase (C#) / Singular PascalCase or snake_case (SQL), Domain-Qualified Attributes, Audit Traceability.

---

## 1. Executive Summary & Naming Governance

This document establishes the **Master Canonical Glossary** for the National Skills Development Management System (NSDMS). It serves as the single source of truth for entity names, database columns, property identifiers, and lookup codes.

### Strict Governance Invariants:
1. **Zero-Tolerance for Typos**: Historical misspellings (such as `dunding_id` for `funding_id` or `active_aontracts_id` for `active_contracts_id`) are strictly prohibited in all current and migrated schemas.
2. **Domain Qualification Rule**: Generic column names (e.g. `StatusCode`, `Category`) must always be qualified by their bounded context (e.g. `EnrolmentStatusCode`, `ApplicationStatusCode`, `DocumentCategoryCode`, `ConfigCategory`).
3. **C# .NET 10 Naming**: All domain entities and properties must use **Singular PascalCase** (e.g. `CompanyLearner`, `LearnerContractNumber`, `TotalContractValue`).
4. **SQL Server Table Naming**: All table names must use **Singular PascalCase** (e.g. `Organisation`, `CompanyLearner`, `GrantMoa`).
5. **Lookup Types**: All reference tables must use the `*Type` suffix (e.g. `GenderType`, `ProvinceType`, `EquityType`) with indexed `Code`, `Name`, `Description`, and `Active` columns.
6. **Mandatory Audit Columns**: Every mutable operational table must include `CreatedAt` (`datetime2`), `CreatedBy` (`nvarchar(100)`), `ModifiedAt` (`datetime2 null`), and `ModifiedBy` (`nvarchar(100) null`).

---

## 2. Typographical Anti-Patterns & Banned Misspellings Matrix

The following legacy misspellings and naming anomalies discovered in historical NSDMS codebases are formally cataloged and **BANNED** in all modern schemas and migrations:

| Banned Legacy Typo / Misalignment | Correct Canonical Name (C#) | Correct Canonical Column (SQL) | Target Bounded Context | Error Rationale & Resolution |
| :--- | :--- | :--- | :--- | :--- |
| `dunding_id` / `dundingId` | `FundingWindowId` / `FundingTypeCode` | `funding_id` / `funding_window_id` | Learner Lifecycle / Grants | Single-key typographic slip (`d` instead of `f` for `funding`). |
| `active_aontracts_id` | `ActiveContractId` / `GrantMoaId` | `active_contracts_id` / `grant_moa_id` | Discretionary Grants | Single-key typographic slip (`a` instead of `c` for `contracts`). |
| `stats_saarea_code_id` | `StatsSaAreaCodeId` | `stats_sa_area_code_id` | Demographics & Addresses | Concatenation typo (`saarea` instead of `sa_area`). |
| `wa_emplyer_trade_status` | `WaEmployerTradeStatus` | `wa_employer_trade_status` | Workplace Approvals | Typo (`emplyer` missing `o`). |
| `reason_learner_not_avalaible`| `ReasonLearnerNotAvailable` | `reason_learner_not_available` | Workplace Approvals / SME | Typo (`avalaible` inverted vowels). |
| `seta__status` | `SetaStatus` | `seta_status` | Levy Organisations | Accidental double underscore typo in legacy migration. |
| `rejection_user` | `RejectionUserId` | `rejection_user_id` | Discretionary Grants | Missing `_id` relational key suffix. |
| `socio_ecoStatus_desc` | `SocioEconomicStatusDescription` | `socio_economic_status_desc` | Legacy NLRD Integration | Inconsistent camelCase inside snake_case string. |

---

## 3. Master Canonical Domain Dictionary

### 3.1 Identity, People & Demographics Context

| Canonical Term (C#) | SQL Column / Table Name | Data Type | Bounded Context | Business Description & Validation Rules |
| :--- | :--- | :--- | :--- | :--- |
| `Person` | `dbo.Person` | Table | Identity | Individual human record containing statutory demographics. |
| `RsaIdNumber` | `rsa_id_number` / `RsaIdNumber` | `nvarchar(13)` | Identity | 13-digit South African National ID with Luhn checksum validation. |
| `PassportNumber` | `passport_number` | `nvarchar(30)` | Identity | International passport number for foreign nationals. |
| `FirstName` | `first_name` | `nvarchar(100)` | Identity | Legal first name as registered with Department of Home Affairs (DHA). |
| `LastName` | `last_name` | `nvarchar(100)` | Identity | Legal surname as registered with DHA. |
| `DateOfBirth` | `date_of_birth` | `date` | Identity | Calculated automatically from the first 6 digits of the RSA ID. |
| `GenderCode` | `gender_code` | `varchar(15)` | Identity | Foreign key referencing `lookup.GenderType` (`M`, `F`, `U`). |
| `EquityCode` | `equity_code` | `varchar(15)` | Identity | Foreign key referencing `lookup.EquityType` (`African`, `Coloured`, `Indian`, `White`, `Other`). |
| `CitizenStatusCode` | `citizen_status_code` | `varchar(15)` | Identity | Foreign key referencing `lookup.CitizenStatusType` (`SA_CITIZEN`, `PERMANENT_RESIDENT`, `FOREIGN_NATIONAL`). |
| `DisabilityCode` | `disability_code` | `varchar(15)` | Identity | Foreign key referencing `lookup.DisabilityType` (`NONE`, `VISUAL`, `HEARING`, `PHYSICAL`, `MULTIPLE`). |
| `HomeLanguageCode` | `home_language_code` | `varchar(15)` | Identity | Foreign key referencing `lookup.HomeLanguageType` (11 official SA languages). |
| `ProvinceCode` | `province_code` | `varchar(15)` | Identity / Org | Foreign key referencing `lookup.ProvinceType` (9 South African provinces). |

---

### 3.2 Organisations & Employers Context

| Canonical Term (C#) | SQL Column / Table Name | Data Type | Bounded Context | Business Description & Validation Rules |
| :--- | :--- | :--- | :--- | :--- |
| `Organisation` | `dbo.Organisation` | Table | Employers | Employer or legal organisation (NPO, NGO, Corporate, Levy Payer). |
| `CompanyName` | `company_name` | `nvarchar(250)` | Employers | Registered CIPC legal company or trading organisation name. |
| `TradingName` | `trading_name` | `nvarchar(250)` | Employers | Commercial trading as (T/A) name if different from legal name. |
| `SdlNumber` | `sdl_number` | `nvarchar(20)` | Employers | SARS Skills Development Levy number (starts with `L` followed by 9 digits). |
| `OrganisationStatusCode` | `organisation_status_code` | `nvarchar(50)` | Employers | Status of organisation registration (`Registered`, `Active`, `Deregistered`, `PendingApproval`). |
| `LevyCategoryCode` | `levy_category_code` | `nvarchar(50)` | Employers | SARS levy classification (`LEVY_PAYING`, `NON_LEVY_PAYING`, `EXEMPT`). |
| `SicCode` | `sic_code` | `nvarchar(20)` | Employers | Standard Industrial Classification (SIC) code for chamber allocation. |
| `ChamberCode` | `chamber_code` | `varchar(50)` | Employers | merSETA Chamber (`AUTO`, `METAL`, `PLASTICS`, `NEW_TYRE`, `AUTO_COMPONENTS`). |
| `CompanySizeCode` | `company_size_code` | `varchar(20)` | Employers | Employee count bracket (`SMALL_0_49`, `MEDIUM_50_149`, `LARGE_150_PLUS`). |
| `BankingDetailsVerified` | `banking_details_verified` | `bit` | Employers | Confirmation that bank branch and account number were verified against SARS / Bank verification service. |

---

### 3.3 Learner Lifecycle & Agreements Context

| Canonical Term (C#) | SQL Column / Table Name | Data Type | Bounded Context | Business Description & Validation Rules |
| :--- | :--- | :--- | :--- | :--- |
| `CompanyLearner` | `dbo.CompanyLearner` | Table | Learnership | Statutory learnership or apprenticeship agreement contract record. |
| `LearnerContractNumber` | `learner_contract_number` | `nvarchar(50)` | Learnership | Unique contract business identifier (e.g. `APP-2026-1001-ABCD`). |
| `EnrolmentStatusCode` | `enrolment_status_code` | `nvarchar(50)` | Learnership | Operational status (`Registered`, `Active`, `Suspended`, `Completed`, `Terminated`, `Transferred`). |
| `FundingTypeCode` | `funding_type_code` | `nvarchar(50)` | Learnership | Source of funding (`MANDATORY_GRANT`, `DISCRETIONARY_GRANT`, `SELF_FUNDED`, `SPECIAL_PROJECT`). |
| `CommencementDate` | `commencement_date` | `date` | Learnership | Contract start date for structured workplace learning. |
| `ExpectedCompletionDate`| `expected_completion_date` | `date` | Learnership | Expected contract graduation date based on curriculum length. |
| `CompletionDate` | `completion_date` | `date null` | Learnership | Actual completion date recorded upon trade test competency. |
| `LearnerTradeTest` | `dbo.LearnerTradeTest` | Table | Trade Testing | Final trade testing assessment session record (INDLELA / ARPL). |
| `ResultStatusCode` | `result_status_code` | `nvarchar(50)` | Trade Testing | Trade test outcome (`Scheduled`, `Competent`, `NotYetCompetent`, `Absent`). |
| `SerialCertificateNumber`| `serial_certificate_number` | `nvarchar(50)` | Trade Testing | National Artisan Certificate serial identifier issued by merSETA / QCTO. |
| `CompanyLearnerTransfer` | `dbo.CompanyLearnerTransfer` | Table | Learner Lifecycle | Legal transfer of learner contract between employers. |
| `TransferStatusCode` | `transfer_status_code` | `nvarchar(50)` | Learner Lifecycle | Status of transfer request (`Pending`, `Approved`, `Rejected`). |
| `CompanyLearnerLostTime` | `dbo.CompanyLearnerLostTime` | Table | Learner Lifecycle | Statutory apprenticeship contract extension due to illness/maternity. |
| `LostTimeStatusCode` | `lost_time_status_code` | `nvarchar(50)` | Learner Lifecycle | Status of lost time request (`Pending`, `Approved`, `Rejected`). |
| `CompanyLearnerTermination` | `dbo.CompanyLearnerTermination` | Table | Learner Lifecycle | Mutual or disputed termination of tripartite contract. |
| `TerminationStatusCode` | `termination_status_code` | `nvarchar(50)` | Learner Lifecycle | Status of termination request (`Pending`, `Approved`, `Rejected`). |

---

### 3.4 ETQA, Accreditations & Quality Assurance Context

| Canonical Term (C#) | SQL Column / Table Name | Data Type | Bounded Context | Business Description & Validation Rules |
| :--- | :--- | :--- | :--- | :--- |
| `TrainingProvider` | `dbo.TrainingProvider` | Table | ETQA Providers | Accredited Skills Development Provider (SDP) institution. |
| `AccreditationNumber` | `accreditation_number` | `nvarchar(50)` | ETQA Providers | Official merSETA SDP accreditation reference number. |
| `ProviderStatusCode` | `provider_status_code` | `nvarchar(50)` | ETQA Providers | Accreditation state (`Accredited`, `UnderReview`, `Suspended`, `Expired`). |
| `WorkplaceApproval` | `dbo.WorkplaceApproval` | Table | Workplace Approval | Approval of an employer workplace facility to train apprentices. |
| `ApprovalStatusCode` | `approval_status_code` | `nvarchar(50)` | Workplace Approval | Workplace approval state (`Approved`, `PendingInspection`, `Rejected`). |
| `Visit` | `dbo.Visit` | Table | Physical Visits | On-site monitoring, evaluation, or approval inspection visit. |
| `VisitStatusCode` | `visit_status_code` | `nvarchar(50)` | Physical Visits | Visit schedule state (`Scheduled`, `Completed`, `Cancelled`, `FollowUpRequired`). |
| `ContactPersonId` | `contact_person_id` | `int` | Physical Visits | Mandatory relational link to the designated contact person for visit execution. |
| `EtqaAssessor` | `dbo.EtqaAssessor` | Table | Practitioners | Registered constituent Assessor or Moderator practitioner. |
| `RegistrationStatusCode`| `registration_status_code` | `nvarchar(50)` | Practitioners | Assessor registration state (`Registered`, `Active`, `Expired`, `Deregistered`). |

---

### 3.5 Grants, WSP, Levies & Finances Context

| Canonical Term (C#) | SQL Column / Table Name | Data Type | Bounded Context | Business Description & Validation Rules |
| :--- | :--- | :--- | :--- | :--- |
| `WspSubmission` | `dbo.WspSubmission` | Table | Mandatory Grants | Annual Workplace Skills Plan (WSP) & Annual Training Report (ATR). |
| `WspApprovalStatusCode` | `wsp_approval_status_code` | `nvarchar(50)` | Mandatory Grants | WSP submission state (`Draft`, `Submitted`, `Approved`, `Rejected`, `Disputed`). |
| `FinYear` | `fin_year` | `int` | Mandatory Grants | Financial calendar year of submission (e.g. 2026). |
| `GrantApplication` | `dbo.GrantApplication` | Table | Discretionary Grants| Application for Discretionary Grant funding allocation. |
| `ApplicationStatusCode` | `application_status_code` | `nvarchar(50)` | Discretionary Grants| DG application state (`Draft`, `Submitted`, `UnderReview`, `Approved`, `Rejected`). |
| `GrantMoa` | `dbo.GrantMoa` | Table | Discretionary Grants| Memorandum of Agreement contract signed with approved DG recipients. |
| `MoaStatusCode` | `moa_status_code` | `nvarchar(50)` | Discretionary Grants| MOA contractual state (`Draft`, `PendingEmployerSignature`, `Signed`, `Terminated`). |
| `ProjectImplementationPlan`| `dbo.ProjectImplementationPlan` | Table | DG Projects | Detailed project milestone and learner allocation schedule. |
| `PipStatusCode` | `pip_status_code` | `nvarchar(50)` | DG Projects | PIP operational state (`Draft`, `Submitted`, `Approved`, `Closed`). |
| `GrantPaymentClaim` | `dbo.GrantPaymentClaim` | Table | DG Projects | Tranche invoice claim for payment upon milestone delivery. |
| `ClaimStatusCode` | `claim_status_code` | `nvarchar(50)` | DG Projects | Payment claim state (`Submitted`, `Verified`, `Approved`, `BatchExported`, `Paid`). |
| `LevyFile` | `dbo.LevyFile` | Table | SARS Levies | Monthly SARS SDL raw data ingestion batch file. |
| `ImportStatusCode` | `import_status_code` | `nvarchar(50)` | SARS Levies | File ingestion state (`Imported`, `Processing`, `Processed`, `Error`). |

---

### 3.6 Workplace Monitoring & Governance Context

| Canonical Term (C#) | SQL Column / Table Name | Data Type | Bounded Context | Business Description & Validation Rules |
| :--- | :--- | :--- | :--- | :--- |
| `WorkplaceMonitoringSiteVisit` | `dbo.WorkplaceMonitoringSiteVisit` | Table | Monitoring | Comprehensive on-site learner and workplace compliance audit. |
| `MonitoringStatusCode` | `monitoring_status_code` | `nvarchar(50)` | Monitoring | Audit review state (`Draft`, `PendingApproval`, `Approved`, `NonComplianceIdentified`). |
| `WorkplaceMonitoringComplianceSurvey` | `dbo.WorkplaceMonitoringComplianceSurvey` | Table | Monitoring | 10-point statutory survey (PPE, Toolboxes, Mentorship, Stipends, OHS). |
| `SurveyCategory` | `survey_category` | `nvarchar(50)` | Monitoring | Classification area (`Toolbox`, `PPE`, `Wages`, `Contracts`, `Training`, `OHS`, `SDF`). |
| `WorkplaceMonitoringActionPlan` | `dbo.WorkplaceMonitoringActionPlan` | Table | Monitoring | Corrective action plan assigned to employer for non-compliance. |
| `ActionPlanStatusCode` | `action_plan_status_code` | `nvarchar(50)` | Monitoring | Corrective resolution state (`Open`, `InReview`, `Completed`, `Escalated`). |
| `WorkplaceMonitoringMitigationPlan` | `dbo.WorkplaceMonitoringMitigationPlan` | Table | Monitoring | High-risk mitigation plan logged during site audit. |
| `MitigationStatusCode` | `mitigation_status_code` | `nvarchar(50)` | Monitoring | Risk mitigation state (`Pending`, `Implemented`, `Verified`). |
| `TrainingCommittee` | `dbo.TrainingCommittee` | Table | Training Committees| Statutory joint workplace training committee composition. |
| `CommitteeStatusCode` | `committee_status_code` | `nvarchar(50)` | Training Committees| Committee compliance state (`Active`, `NonCompliant`, `Disbanded`). |
| `WspDispute` | `dbo.WspDispute` | Table | WSP Disputes | Dispute logged by union/labour against employer WSP submission. |
| `DisputeStatusCode` | `dispute_status_code` | `nvarchar(50)` | WSP Disputes | Dispute resolution state (`Logged`, `UnderInvestigation`, `Mediation`, `Resolved`). |
| `QmrQuarterlyReport` | `dbo.QmrQuarterlyReport` | Table | DHET Reporting | Quarterly Monitoring Report for statutory submission to DHET. |
| `ReportStatusCode` | `report_status_code` | `nvarchar(50)` | DHET Reporting | Report review state (`Draft`, `SubmittedToDhet`, `AcceptedByDhet`). |

---

### 3.7 System Configuration, Workflows & Storage Context

| Canonical Term (C#) | SQL Column / Table Name | Data Type | Bounded Context | Business Description & Validation Rules |
| :--- | :--- | :--- | :--- | :--- |
| `SystemConfig` | `dbo.SystemConfig` | Table | Dynamic Config | Dynamic system configuration key-value storage. |
| `ConfigCategory` | `config_category` | `nvarchar(50)` | Dynamic Config | Functional partitioning (`General`, `Grants`, `Learners`, `ETQA`, `Security`). |
| `SystemFeatureFlag` | `dbo.SystemFeatureFlag` | Table | Feature Toggles | Runtime toggles for external integrations and experimental modules. |
| `FeatureCategory` | `feature_category` | `nvarchar(50)` | Feature Toggles | Toggle grouping (`Integrations`, `CoreWorkflows`, `UIFeatures`). |
| `DocumentAttachment` | `dbo.DocumentAttachment` | Table | File Storage | Relational metadata index for physical/cloud uploaded documents. |
| `DocumentCategoryCode` | `document_category_code` | `nvarchar(50)` | File Storage | Document classification (`ID_COPY`, `QUALIFICATION_CERT`, `EVIDENCE_POPI`, `INVOICE`). |
| `AuditLog` | `dbo.AuditLog` | Table | Security / Audit | Immutable append-only audit trail capturing double-writes on mutations. |
| `MetadataJson` | `metadata_json` | `nvarchar(max)` | Security / Audit | JSON payload containing "before" and "after" state snapshots. |

---

## 4. Maintenance & Automated Validation

1. **Unit Test Enforcement**: All entities and properties are validated automatically by `DatabaseDocumentationTests` in `Nsdms.Tests`.
2. **Schema Synchronization**: Whenever changes occur, run `DatabaseDocumentationService.GenerateSqlExtendedPropertiesScriptAsync()` or the test suite to push `MS_Description` extended properties directly to SQL Server Express.
