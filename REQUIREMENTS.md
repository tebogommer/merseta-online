# Requirements Register — merSETA NSDMS

> **Schema Version:** 3.0
> **Register Revision:** 1.3
> **Owner:** Tebogo Moepi
> **Last Updated:** 2026-09-13
> **Validated:** requirements_lint.py v1.1 — exit 0 — 2026-09-13

---

## Status Legend

| Symbol | Meaning |
|---|---|
| `[ ]` | Not started |
| `[~]` | In progress |
| `[x]` | Done & verified |
| `[!]` | Blocked or conflict |
| `[?!]` | High risk inference (blocks builds) |
| `[?]` | Functional inference needing validation |
| `[?~]` | Cosmetic or minor inference |
| `[d]` | Dropped or deprecated |

---

## Source Document Registry

| Doc ID | Document Title | Category | Scope / Module | Location / Link |
|---|---|---|---|---|
| DOC-SPEC-WSP | Spec 06: Workplace Skills Plan (WSP) Submission Flow | Technical Specification | Mandatory Grants / WSP | [Spec-06-WSP-Submission-Flow.md](docs/specifications/4-Workplace-Skills-Plan-WSP/Spec-06-WSP-Submission-Flow.md#1-domain-overview) |
| DOC-SPEC-MG | Spec 07: Mandatory Grants (Levy Rebates) | Technical Specification | Finance / Mandatory Grants | [Spec-07-Mandatory-Grants.md](docs/specifications/7-Financials-Mandatory-Grants/Spec-07-Mandatory-Grants.md#1-domain-overview) |
| DOC-SPEC-DG | Spec 03: Discretionary Grants Allocation & MoA | Technical Specification | Grants / Discretionary Grants | [Spec-03-Discretionary-Grants.md](docs/specifications/6-Financials-Discretionary-Grants/Spec-03-Discretionary-Grants.md#1-domain-overview) |
| DOC-SPEC-TT | Spec 12: Trade Testing & ARPL Governance | Technical Specification | Artisan Development / Trade Tests | [Spec-12-Trade-Testing-And-Artisan.md](docs/specifications/11-Trade-Testing/Spec-12-Trade-Testing-And-Artisan.md#1-domain-overview) |
| DOC-SPEC-WPA | Spec 11: Workplace Approvals & Monitoring | Technical Specification | Quality Assurance / Workplace | [Spec-11-Workplace-Approvals-And-Monitoring.md](docs/specifications/10-Workplace-Approvals/Spec-11-Workplace-Approvals-And-Monitoring.md#1-domain-overview) |
| DOC-SPEC-LRN | Spec 08: Learner Management & Registrations | Technical Specification | Learner Administration / STP | [Spec-08-Learner-Registrations.md](docs/specifications/5-Learner-Management/Spec-08-Learner-Registrations.md#1-domain-overview) |
| DOC-SPEC-DMS | Spec 15: Document Management & 2D Verification | Technical Specification | Core Platform / Documents | [Spec-15-Document-Management.md](docs/specifications/14-Document-Management/Spec-15-Document-Management.md#1-domain-overview) |
| DOC-SPEC-SARS | Spec 09: SARS Electronic Levy File Ingestion | Technical Specification | Integrations / SARS Levies | [Spec-09-SARS-Levy-Integration.md](docs/specifications/8-Integrations-SARS/Spec-09-SARS-Levy-Integration.md#1-domain-overview) |
| DOC-SPEC-SETMIS | Spec 13: DHET SETMIS Statutory Reporting | Technical Specification | Statutory Reporting / DHET | [Spec-13-Statutory-Reporting.md](docs/specifications/12-Statutory-Reporting/Spec-13-Statutory-Reporting.md#1-domain-overview) |
| DOC-SPEC-OKF | Spec 17: Open Knowledge Catalog & Attestation | Technical Specification | Governance / OKF Attestation | [SPECIFICATION.md](docs/specifications/17-Open-Knowledge-Catalog/SPECIFICATION.md#1-domain-overview) |
| DOC-SPEC-AM | Spec 04: Assessor & Moderator Registration | Technical Specification | Quality Assurance / ETQA | [Spec-04-Assessor-Moderator.md](docs/specifications/3-Quality-Assurance-ETQA/Spec-04-Assessor-Moderator.md#1-domain-overview) |
| DOC-SPEC-WF | Spec 10: Workflow & Task Engine | Technical Specification | Platform / Workflow Automation | [Spec-10-Workflow-Task-Engine.md](docs/specifications/9-Workflow-Task-Engine/Spec-10-Workflow-Task-Engine.md#1-domain-overview) |
| DOC-SPEC-AUTH | Spec 01: Core Identity, Users & Permissions | Technical Specification | Platform / Identity & Security | [Spec-01-User-Management-Auth.md](docs/specifications/1-Core-Identity/Spec-01-User-Management-Auth.md#1-domain-overview) |
| DOC-SPEC-ORG | Spec 02: Company & Organisation Registration | Technical Specification | Core Business / Organisations | [Spec-02-Company-Registration.md](docs/specifications/2-Organisation-Management/Spec-02-Company-Registration.md#1-domain-overview) |
| DOC-SPEC-SDP | Spec 05: Training Provider (SDP) Accreditation | Technical Specification | Quality Assurance / ETQA | [Spec-05-Training-Providers.md](docs/specifications/3-Quality-Assurance-ETQA/Spec-05-Training-Providers.md#1-domain-overview) |
| DOC-SPEC-ERP | Spec 14: Dynamics GP ERP Integration | Technical Specification | Integrations / Finance ERP | [Spec-14-Dynamics-GP-Integration.md](docs/specifications/13-Great-Plains-ERP/Spec-14-Dynamics-GP-Integration.md#1-domain-overview) |
| DOC-PLAN-FISCAL | Plan: Hierarchical Fiscal Calendar Governance | Technical Specification | Finance / Governance | [PLAN-fiscal-calendar.md](docs/PLAN-fiscal-calendar.md#1-executive-summary--context) |
| DOC-STAT-SDA | Skills Development Act 97 of 1998 | Statutory Legislation | National Skills Framework | [Act No. 97 of 1998](https://www.gov.za/documents/skills-development-act) |
| DOC-STAT-SDLA | Skills Development Levies Act 9 of 1999 | Statutory Legislation | Levy Collection & Distribution | [Act No. 9 of 1999](https://www.gov.za/documents/skills-development-levies-act) |
| DOC-STAT-SETAREG | SETA Grant Regulations (Government Gazette No. 35940) | Statutory Gazette | Mandatory & Discretionary Grants | [Gazette No. 35940](https://www.gov.za/documents/skills-development-act-regulations-monies-received-seta-and-related-matters) |
| DOC-STAT-PFMA | Public Finance Management Act 1 of 1999 | Statutory Legislation | Financial Control & Dual Authorisation | [Act No. 1 of 1999](https://www.gov.za/documents/public-finance-management-act) |
| DOC-STAT-POPIA | Protection of Personal Information Act 4 of 2013 | Statutory Legislation | Data Privacy & ID Masking | [Act No. 4 of 2013](https://www.gov.za/documents/protection-personal-information-act) |
| DOC-STAT-NAMB | NAMB National Artisan Trade Testing Criteria & ARPL Guidelines (2023) | Regulatory Specification | Trade Testing & Mentor Ratios | [NAMB Criteria 2023](https://www.dhet.gov.za/) |
| DOC-STAT-NLRD | SAQA NLRD Edu.Dex Load Specifications v2.4 | Regulatory Specification | SAQA Extracts (Supplier 599) | [SAQA NLRD Spec](https://www.saqa.org.za/) |

---

## Functional Requirements (FR)

| ID | Description | Status | Priority | Class | Owner | Source | Conf | Fingerprint | Depends On | Compliance | Doc Ref | Ticket | Notes |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| FR-001 | Mandatory Grant (WSP/ATR) statutory 30 April submission cutoff and automatic rejection | [x] | HIGH | Class A | Tebogo Moepi | CODE | HIGH | svc:WspService.CreateAsync | - | SDA, SETAREG | DOC-SPEC-WSP, DOC-STAT-SETAREG | NSDMS-FR-001 | Enforces statutory deadline; rejects submissions post-deadline without approved extension. |
| FR-002 | Mandatory Grant two-tier maker-checker extension request workflow capped at 31 May | [x] | HIGH | Class A | Tebogo Moepi | CODE | HIGH | svc:WspService.AdjudicateExtensionAsync | FR-001 | SDA, SETAREG, PFMA | DOC-SPEC-WSP, DOC-STAT-SETAREG | NSDMS-FR-002 | Review by CLO and adjudication by Executive Authority; enforces segregation of duties. |
| FR-003 | Discretionary Grant funding window configuration engine and 1-click blueprint templates | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:GrantService.CreateFundingWindowAsync | - | SDLA, PFMA | DOC-SPEC-DG, DOC-STAT-SDLA | NSDMS-FR-003 | Decoupled window timeframes, blueprint cloning, and dynamic eligibility tags. |
| FR-004 | Discretionary Grant hybrid application intake and consolidated single-MoA budget rollup | [x] | HIGH | Class A | Tebogo Moepi | CODE | HIGH | svc:GrantService.CreateApplicationAsync | FR-003 | SDLA, PFMA | DOC-SPEC-DG, DOC-STAT-SDLA | NSDMS-FR-004 | Consolidates PIVOTAL training plans and strategic project deliverables into one MoA. |
| FR-005 | Artisan trade test 2-tier regional recommendation, QA approval, and serial generation | [x] | HIGH | Class A | Tebogo Moepi | CODE | HIGH | svc:TradeTestAndArplService.ApproveRegionalQaAsync | - | SDA, QCTO, NAMB | DOC-SPEC-TT, DOC-STAT-NAMB | NSDMS-FR-005 | Regional CLA recommendation to QA approval; issues unique trade test serial number. |
| FR-006 | 17 designated trade toolkit whitelist enforcement and 50% practical task credit retention | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:TradeTestAndArplService.EvaluateTradeEligibility | FR-005 | SDA, NAMB | DOC-SPEC-TT, DOC-STAT-NAMB | NSDMS-FR-006 | Whitelist restricted to 17 designated trades; retains passed task credits for 18 months. |
| FR-007 | Artisan mentor-to-apprentice ratio policy engine with 5-tier cascading precedence | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:MentorRatioPolicyEngine.EvaluateWorkplaceApprovalCapacityAsync | - | SDA, QCTO, NAMB | DOC-SPEC-WPA, DOC-STAT-NAMB | NSDMS-FR-007 | Evaluates mentor overrides, workplace caps, org exemptions, trade policy, and system config. |
| FR-008 | Learner enrolment tripartite agreement intake, dual-channel STP, and RSA ID validation | [x] | HIGH | Class A | Tebogo Moepi | CODE | HIGH | svc:LearnerService.RegisterLearnerAsync | - | SDA, POPIA, SETMIS | DOC-SPEC-LRN, DOC-STAT-SDA | NSDMS-FR-008 | Captures employer, learner, and SDP tripartite commitments; validates 13-digit RSA ID. |
| FR-009 | Universal QuestPDF 2D barcode verification seal and anonymous public verification portal | [x] | HIGH | Class A | Tebogo Moepi | CODE | HIGH | svc:DocumentVerificationService.CreateAndFreezeDocumentSnapshotAsync | - | POPIA, PFMA | DOC-SPEC-DMS, DOC-STAT-POPIA | NSDMS-FR-009 | Renders tamper-evident QR verification seal; masks personal ID numbers per POPIA. |
| FR-010 | Monthly SARS levy file reactive streaming pipeline, digital seal check, and bulk staging | [x] | HIGH | Class A | Tebogo Moepi | CODE | HIGH | svc:SarsLevyStreamingPipeline.StreamProcessLevyFileAsync | - | SDLA, PFMA | DOC-SPEC-SARS, DOC-STAT-SDLA | NSDMS-FR-010 | Constant-memory streaming; verifies security seal and stages records via SqlBulkCopy. |
| FR-011 | DHET SETMIS statutory flat-file batch extract engine across all 11 standard files | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:SetmisExtractService.GenerateBatchAsync | FR-008 | SDA, SETMIS | DOC-SPEC-SETMIS, DOC-STAT-SDA | NSDMS-FR-011 | Produces positional flat files strictly adhering to exact character width specifications. |
| FR-012 | SAQA NLRD Edu.Dex statutory flat-file export pipeline for Supplier Code 599 | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:NlrdExtractService.GenerateBatchAsync | FR-008 | SDA, NLRD | DOC-SPEC-SETMIS, DOC-STAT-NLRD | NSDMS-FR-012 | Formats SAQA Edu.Dex extracts prepended with statutory HEADER599 records. |
| FR-013 | Institutional non-working day calendar management and universal officer SLA pause engine | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:WorkingDayCalculationEngine.AddBusinessDays | - | PFMA | DOC-SPEC-WPA, DOC-STAT-PFMA | NSDMS-FR-013 | Computes public holidays and merSETA shutdowns; pauses workflow SLA timers dynamically. |
| FR-014 | PFMA interest declaration, institutional shareholder scan, and conflict review queue | [x] | HIGH | Class A | Tebogo Moepi | CODE | HIGH | svc:ConflictManagementService.ScanMeetingConflictsAsync | - | PFMA, POPIA | DOC-SPEC-DG, DOC-STAT-PFMA | NSDMS-FR-014 | Captures annual declarations of interest, links shareholdings, and automates meeting scans. |
| FR-015 | Open Knowledge Format (OKF) statutory concept catalog, parser, and T-SQL attestation | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:KnowledgeCatalogService.VerifyConceptAsync | - | PFMA | DOC-SPEC-OKF, DOC-STAT-PFMA | NSDMS-FR-015 | Maintains living statutory concept definitions with mathematical and T-SQL attestation. |
| FR-016 | Discretionary Grant MoA contracting, 4-tier milestone verification, and tranche disbursement | [x] | HIGH | Class A | Tebogo Moepi | CODE | HIGH | svc:FinanceService.ApproveTranchePaymentAsync | FR-004 | SDLA, PFMA | DOC-SPEC-DG, DOC-STAT-PFMA | NSDMS-FR-016 | Dual authorisation governance on tranche approvals; residual milestone disbursement and invoice tracking. |
| FR-017 | Discretionary Grant contract variation management (addenda, extensions, clawback terminations) | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:ContractVariationService.ApproveAddendaAsync | FR-016 | SDLA, PFMA | DOC-SPEC-DG, DOC-STAT-PFMA | NSDMS-FR-017 | Enforces segregation of duties between requesting CLO and executive approver; clawback financial calculation. |
| FR-018 | Employer banking details verification, Bankserv AVS validation, and 14-day cooling-off | [x] | HIGH | Class A | Tebogo Moepi | CODE | HIGH | svc:BankingDetailsService.VerifyBankingDetailsAsync | - | PFMA | DOC-SPEC-MG, DOC-STAT-PFMA | NSDMS-FR-018 | Cross-organisation duplicate account detection and statutory working-day cooling-off period before grant disbursement. |
| FR-019 | Artisan RPL (ARPL Section 28) assessment workflow and 50% task credit retention | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:TradeTestAndArplService.SubmitArplApplicationAsync | FR-005, FR-006 | SDA, NAMB | DOC-SPEC-TT, DOC-STAT-NAMB | NSDMS-FR-019 | Restricts Category 7 candidates to designated trades; retains practical credits across re-attempts. |
| FR-020 | Summative assessment EISA capture, external moderation, and Statement of Results issuance | [x] | HIGH | Class A | Tebogo Moepi | CODE | HIGH | svc:SummativeAssessmentAndModerationService.RecordAssessmentReportAsync | FR-008 | SDA, QCTO | DOC-SPEC-LRN, DOC-STAT-SDA | NSDMS-FR-020 | External moderation sample selection, QCTO mark submission, and POPIA-compliant Statement of Results generation. |
| FR-021 | Non-SETA qualification verification, cross-SETA endorsement, and NLRD routing | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:NonSetaVerificationService.EndorseNonSetaQualificationAsync | FR-008 | SDA, NLRD | DOC-SPEC-LRN, DOC-STAT-NLRD | NSDMS-FR-021 | Cross-checks foreign/sister-SETA credits before granting entry to MerSETA occupational qualifications. |
| FR-022 | Assessor and moderator registration, scope accreditation, and 3-year re-registration lifecycle | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:AssessorRegistrationService.SubmitApplicationAsync | - | SDA, QCTO | DOC-SPEC-AM, DOC-STAT-SDA | NSDMS-FR-022 | Validates ETDP SETA statement of results, statement of qualifications scope, and automatic tenure expiry warnings. |
| FR-023 | Institutional financial approval delegation tiers and executive CFO dual-signoff escalation | [x] | HIGH | Class A | Tebogo Moepi | CODE | HIGH | svc:WorkflowGovernanceService.ValidateFinancialApprovalLimitAsync | FR-016 | PFMA | DOC-SPEC-DG, DOC-STAT-PFMA | NSDMS-FR-023 | Threshold-based dual authorization (disbursements exceeding statutory thresholds require CFO and CEO approvals). |
| FR-024 | Universal BPM workflow state machine engine with decoupled status synchronization and audit logging | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:WorkflowEngineService.TransitionStateAsync | - | PFMA | DOC-SPEC-WF, DOC-STAT-PFMA | NSDMS-FR-024 | Decoupled workflow state and operational status axes; evaluates business days for officer task due dates. |
| FR-025 | Hierarchical fiscal year management, statutory quarters contiguity, and amendment lifecycle | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:FiscalCalendarService.CreateFinancialYearAsync | - | PFMA | DOC-PLAN-FISCAL, DOC-STAT-PFMA | NSDMS-FR-025 | Validates quarter contiguity (Qn.End + 1 == Qn+1.Start), leap year days, and rev #2 amendment locks. |
| FR-026 | Workplace approval audit inspection, monitoring visit tracking, and trade accreditation recommendation | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:WorkplaceApprovalService.EvaluateWorkplaceAsync | FR-007 | SDA, QCTO | DOC-SPEC-WPA, DOC-STAT-SDA | NSDMS-FR-026 | Enforces contact person relational link, evaluates trade mentor ratios, and issues site visit audit reports. |
| FR-027 | Inter-SETA Transfer (IST) intake, SARS Chamber re-allocation, and financial journal settlement | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:FinanceService.SaveInterSetaTransferAsync | FR-010 | SDA, SDLA, PFMA | DOC-SPEC-SARS, DOC-STAT-SDLA | NSDMS-FR-027 | Manages employer chamber realignment, transfer request approvals, and inter-SETA financial settlements. |
| FR-028 | Claims-based role access control (RBAC), CASL ability engine, and administrative delegation | [x] | HIGH | Class A | Tebogo Moepi | CODE | HIGH | svc:RolePermissionService.AssignPermissionsToRoleAsync | - | PFMA | DOC-SPEC-AUTH, DOC-STAT-PFMA | NSDMS-FR-028 | Enforces granular permissions, CASL ability matrix rules, and temporal role delegations. |
| FR-029 | Organisation 360-degree relational hub, legal form registration, and parent-subsidiary linking | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:OrganisationService.CreateAsync | - | SDLA, PFMA | DOC-SPEC-ORG, DOC-STAT-SDLA | NSDMS-FR-029 | Centralizes employer legal details, SDL numbers, Chamber affiliations, SIC codes, and contacts. |
| FR-030 | Skills Development Provider (SDP) accreditation lifecycle, multi-campus governance, and sanctions | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:TrainingProviderService.SubmitAccreditationApplicationAsync | - | SDA, QCTO | DOC-SPEC-SDP, DOC-STAT-SDA | NSDMS-FR-030 | Manages primary provider accreditation, secondary satellite campuses, site audits, and sanctions. |
| FR-031 | Dynamics GP ERP integration, financial voucher export, and transactional outbox queue | [x] | HIGH | Class A | Tebogo Moepi | CODE | HIGH | svc:ErpOutboxQueueService.QueueTransactionAsync | FR-016, FR-023 | PFMA | DOC-SPEC-ERP, DOC-STAT-PFMA | NSDMS-FR-031 | Guaranteed at-least-once ledger integration with exponential retry backoff and dead-letter staging. |
| FR-032 | Enterprise document template studio, single-active version invariant, and QuestPDF rendering | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:EnterpriseDocumentTemplateService.ActivateTemplateRevisionAsync | FR-009 | PFMA | DOC-SPEC-DMS, DOC-STAT-PFMA | NSDMS-FR-032 | Enforces single-active version invariant, placeholder registry linting, and QuestPDF byte generation. |
| FR-033 | Enterprise broadcast messaging, circular attachments, and Microsoft 365 daily rate limiting | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:EmailOutboxService.EnqueueBroadcastAsync | - | PFMA | DOC-SPEC-DMS, DOC-STAT-PFMA | NSDMS-FR-033 | Manages circular attachments, token-bucket throttling (30/min), and 10,000 email daily ceiling. |
| FR-034 | Workplace Skills Plan (WSP) 60% training deviation detection, small employer exemption, and sign-off | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:WspService.EvaluateTrainingDeviationAsync | FR-001 | SDA, SETAREG | DOC-SPEC-WSP, DOC-STAT-SETAREG | NSDMS-FR-034 | Detects <60% planned vs actual training deviation requiring motivation; handles <50 staff exemptions. |
| FR-035 | Workplace monitoring site visits, equipment inspection, and compliance deficiency action plans | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:WorkplaceMonitoringService.ConductMonitoringVisitAsync | FR-026 | SDA, QCTO | DOC-SPEC-WPA, DOC-STAT-SDA | NSDMS-FR-035 | Tracks officer monitoring visits, captures workplace health/safety findings, and logs corrective plans. |
| FR-036 | Regional operations zoning, geographic district mapping, and officer caseload balancing | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:ZoneAndCaseloadService.RebalanceOfficerCaseloadAsync | - | PFMA | DOC-SPEC-WPA, DOC-STAT-PFMA | NSDMS-FR-036 | Maps employers to geographic municipal zones and algorithmically balances CLO caseloads. |
| FR-037 | Dynamic business rules engine, system configuration overrides, and feature flag management | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:BusinessRuleEngineService.EvaluateRulesAsync | - | PFMA | DOC-SPEC-WF, DOC-STAT-PFMA | NSDMS-FR-037 | Resolves parameter overrides dynamically with fallback constants, avoiding hardcoded business rules. |
| FR-038 | Standard Industrial Classification (SIC) code chamber intelligence, sector derivation, and levy splits | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:LevyService.CalculateStatutoryLevySplit | FR-010 | SDLA, PFMA | DOC-SPEC-SARS, DOC-STAT-SDLA | NSDMS-FR-038 | Precision Hare-Niemeyer largest-remainder statutory levy split (20% MG, 49.5% DG, 10.5% Admin, 20% NSF). |

---

## Non-Functional Requirements (NFR)

| ID | Description | Threshold | Measurement Method | Status | Category | Owner | Source | Conf |
|---|---|---|---|---|---|---|---|---|
| NFR-001 | Read Committed Snapshot Isolation (RCSI) non-blocking query concurrency | 0 deadlocks across 100 concurrent simulation workers | Measured via SQL Server deadlock monitor and PerformanceBenchmarksTests.cs | [x] | Performance | Tebogo Moepi | CODE | HIGH |
| NFR-002 | MudBlazor DataGrid server-side pagination index seek execution latency | p95 query execution time under 50 ms on 500k row tables | Measured via SQL Server sys.dm_exec_query_stats and PerformancePagingTests.cs | [x] | Performance | Tebogo Moepi | CODE | HIGH |
| NFR-003 | Asynchronous QuestPDF document generation interactive circuit decoupling | 0 ms blocking on Blazor Server interactive circuit thread | Measured via BackgroundJobProcessingWorker latency logs and PdfDocumentGenerationTests.cs | [x] | Performance | Tebogo Moepi | CODE | HIGH |
| NFR-004 | POPIA confidential 13-digit RSA National ID masking on public portals | 100% of public responses mask digits 7 to 10 (e.g. 9504******082) | Verified via PopiaMaskingAndAuditRedactionTests.cs test suite | [x] | Security | Tebogo Moepi | CODE | HIGH |

---

## Business Rules (BR)

| ID | Rule | Source | Notes |
|---|---|---|---|
| BR-001 | Annual Mandatory Grant submission window closes strictly at 23:59 on 30 April | SETA Grant Regulations (2012) Reg 4(1) | Submissions past deadline are actively rejected unless an approved extension exists. |
| BR-002 | Approved Mandatory Grant extensions cannot exceed 31 May of the scheme year | SETA Grant Regulations (2012) Reg 4(2) | Hard statutory boundary; system rejects any extension date beyond 31 May. |
| BR-003 | Mandatory Grant rebate is strictly 20% of employer statutory SDL levy contributions | Skills Development Levies Act 9 of 1999 | Unclaimed Mandatory Grant funds sweep into the Discretionary Grant funding pool. |
| BR-004 | Toolkit assessment is whitelisted strictly to 17 designated national trades | NAMB ARPL & Trade Test Specification 2023 | Practical task credits (>=50%) are retained for 18 months or up to 3 attempts. |
| BR-005 | Maximum artisan mentor-to-apprentice ratio defaults to 1:4 and is capped at 1:6 | NAMB Criteria & Guidelines for Workplace Approval | Policy resolution evaluates mentor, workplace, organisation, and trade overrides. |
| BR-006 | Dual authorisation control prohibits self-review or self-approval on all workflows | PFMA Act 1 of 1999 §38(1)(a)(i) | Enforced by validating that ProposerUserId does not equal ApproverUserId. |

---

## Architectural Decisions (DEC)

| ID | Decision | Rationale | Implications | Date |
|---|---|---|---|---|
| DEC-001 | System-versioned temporal tables for all persistent domain entities | Provides tamper-evident point-in-time state reconstruction for statutory audits | High-velocity append-only tables use BaseLongEntity to avoid int overflow | 2026-08-30 |
| DEC-002 | Stacked Master-Detail View-by-Default layout pattern across all entity pages | Prevents accidental data modifications and enforces uniform enterprise UX | Detail routes /{id} are strictly read-only; editing is isolated to /{id}/edit | 2026-08-30 |
| DEC-003 | Decoupled background job pipeline using System.Threading.Channels | Isolates heavy CPU tasks from Blazor interactive UI circuits | Outcomes delivered via streaming download endpoints and SignalR notifications | 2026-08-31 |
| DEC-004 | Mandatory audited double-write on all entity mutations | Enforces PFMA and AGSA non-repudiation audit trails across the system | Service operations must wrap entity persistence and audit logging atomically | 2026-08-30 |
| DEC-005 | Dynamic system configuration with cascading database overrides | Enables runtime parameter adjustments without requiring code recompilation | Services inject ISystemConfigurationService and pass fallback constants | 2026-08-30 |

---

## Assumptions & Risks (ASM)

| ID | Assumption | Basis | Expires / Revisit | If False, Affects |
|---|---|---|---|---|
| ASM-001 | Annual WSP submission volumes will remain under 60,000 participating employers | 2025/2026 SETA statutory intake statistics | 2027 Scheme Year Intake | FR-001, NFR-002 |
| ASM-002 | Monthly SARS electronic levy files will contain fewer than 250,000 transaction lines | Historical monthly SARS levy staging volumes | 2027 Q2 SARS Review | FR-010, NFR-001 |

---

## Acceptance Criteria (AC)

| Req ID | Criterion (GIVEN / WHEN / THEN) | Source | Verified |
|---|---|---|---|
| FR-001 | GIVEN an employer attempts to submit a WSP AFTER the statutory deadline of 30 April WHEN no approved extension exists THEN the system actively rejects the submission with a statutory non-compliance message. | SPEC | [x] |
| FR-002 | GIVEN an employer with an active WSP extension request WHEN the reviewing CLO recommends approval THEN an independent executive officer approves the request capping the granted deadline at 31 May. | SPEC | [x] |
| FR-003 | GIVEN a funding window administrator WHEN selecting a pre-configured window blueprint THEN the system provisions a new Discretionary Grant window pre-populated with gazetted eligibility criteria. | SPEC | [x] |
| FR-004 | GIVEN a hybrid Discretionary Grant application WHEN the applicant submits both PIVOTAL and non-PIVOTAL interventions THEN the requested amounts roll up into a single consolidated grant budget. | SPEC | [x] |
| FR-005 | GIVEN an artisan trade test application WHEN regional QA approval is granted THEN the system issues an official Trade Test Serial Number formatted as TT-SER-{yyyy}-{id:D5}. | SPEC | [x] |
| FR-006 | GIVEN a candidate evaluated on designated trade practical tasks WHEN the candidate passes 50% or more tasks THEN passed task credits are retained for 18 months or 3 attempts. | SPEC | [x] |
| FR-007 | GIVEN an artisan workplace approval evaluation WHEN apprentice allocations are calculated THEN the policy engine evaluates mentor overrides before falling back to trade policy caps. | SPEC | [x] |
| FR-008 | GIVEN a learner registration request WHEN tripartite details and RSA ID checksum are validated THEN the learner is enrolled with straight-through processing status. | SPEC | [x] |
| FR-009 | GIVEN an official outcome document generated via QuestPDF WHEN scanned via the public portal THEN the system displays validity details while masking the 13-digit RSA National ID. | SPEC | [x] |
| FR-010 | GIVEN a 100k-line SARS levy electronic file WHEN ingested by the reactive pipeline THEN transaction lines stream into staging without exceeding steady-state memory limits. | SPEC | [x] |
| FR-011 | GIVEN an approved SETMIS submission batch WHEN generating flat files THEN all 11 files strictly conform to statutory positional character widths without delimiters. | SPEC | [x] |
| FR-012 | GIVEN an NLRD statutory export batch WHEN compiling extracts THEN each file begins with the statutory HEADER599 record matching exact SAQA specification widths. | SPEC | [x] |
| FR-013 | GIVEN an approved non-working day or shutdown marked AffectsSla WHEN calculating officer task deadlines THEN the SLA calculation engine pauses the countdown timer. | SPEC | [x] |
| FR-014 | GIVEN an adjudication committee meeting agenda WHEN conflict scanning is executed THEN the system detects shared institutional directorships and flags potential conflicts. | SPEC | [x] |
| FR-015 | GIVEN an Open Knowledge Format statutory concept document WHEN verified by an authorized officer THEN the attestation engine confirms T-SQL attestation formulas. | SPEC | [x] |
| FR-016 | GIVEN an active Discretionary Grant MoA WHEN the employer submits a verified milestone tranche claim THEN the system enforces dual authorisation before releasing payment disbursement. | SPEC | [x] |
| FR-017 | GIVEN an approved MoA requiring scope variation WHEN a CLO files an addendum or termination request THEN the system enforces independent executive approval and computes clawback balances. | SPEC | [x] |
| FR-018 | GIVEN an employer submitting banking details for levy disbursement WHEN Bankserv AVS verification succeeds THEN a 14-day statutory working-day cooling-off timer is initiated before activation. | SPEC | [x] |
| FR-019 | GIVEN an ARPL Section 28 application WHEN evaluating candidate qualifications THEN the system restricts toolkit assessments strictly to 17 designated trades and preserves passed task credits. | SPEC | [x] |
| FR-020 | GIVEN a cohort completing summative assessments WHEN external moderation is executed THEN the system samples learner scripts and generates POPIA-masked Statement of Results. | SPEC | [x] |
| FR-021 | GIVEN a learner presenting qualifications from another SETA WHEN verification is requested THEN the system routes the request for sister-SETA endorsement and NLRD validation. | SPEC | [x] |
| FR-022 | GIVEN an applicant applying for assessor or moderator status WHEN ETQA review approves scope accreditation THEN a credential certificate is issued with a 3-year re-registration lifecycle. | SPEC | [x] |
| FR-023 | GIVEN a financial disbursement exceeding R500,000 WHEN the primary finance officer approves the batch THEN the workflow escalates to the Chief Financial Officer for secondary dual signoff. | SPEC | [x] |
| FR-024 | GIVEN any statutory workflow transition WHEN state change occurs THEN the engine synchronizes entity status, calculates working-day task SLAs, and double-writes audit logs. | SPEC | [x] |
| FR-025 | GIVEN a financial year configuration WHEN the 4 relational quarters are initialized THEN the system validates strict contiguity with zero overlaps or gaps. | SPEC | [x] |
| FR-026 | GIVEN an employer workplace approval application WHEN an officer schedules a site audit THEN a verified contact person is mandatory and artisan mentor capacity is verified. | SPEC | [x] |
| FR-027 | GIVEN an employer transferring between SETA chambers WHEN the transfer is approved by the Executive Authority THEN SARS SDL migration records and inter-SETA financial journals are balanced. | SPEC | [x] |
| FR-028 | GIVEN an authenticated user WHEN permissions are evaluated across modules THEN the CASL engine checks both specific view and manage claims. | SPEC | [x] |
| FR-029 | GIVEN a new employer registering in the NSDMS WHEN capturing legal details THEN the system assigns chamber classification and verifies trading name uniqueness. | SPEC | [x] |
| FR-030 | GIVEN an accredited Skills Development Provider expanding offerings WHEN a secondary campus is added THEN site audit approval is required before enrolling learners. | SPEC | [x] |
| FR-031 | GIVEN an approved financial payment batch WHEN export to Dynamics GP is initiated THEN the transactional outbox guarantees at-least-once delivery. | SPEC | [x] |
| FR-032 | GIVEN a statutory document template update WHEN a new revision is approved and activated THEN prior revisions are atomically marked superseded. | SPEC | [x] |
| FR-033 | GIVEN an urgent institutional communique WHEN broadcasting to all levy-paying employers THEN emails throttle at 30/min without exceeding the 10,000 daily ceiling. | SPEC | [x] |
| FR-034 | GIVEN an employer submitting an ATR WHEN actual training delivered is below 60% of planned targets THEN the system mandates a formal deviation motivation letter. | SPEC | [x] |
| FR-035 | GIVEN an active workplace approval WHEN an officer conducts a periodic monitoring visit THEN physical machinery compliance and mentor logs are evaluated. | SPEC | [x] |
| FR-036 | GIVEN a regional shift in employer registrations WHEN zone rebalancing is triggered THEN officer caseload allocations normalize across district boundaries. | SPEC | [x] |
| FR-037 | GIVEN a runtime change in statutory threshold parameters WHEN resolved by application services THEN dynamic database overrides take precedence over code defaults. | SPEC | [x] |
| FR-038 | GIVEN a monthly SARS levy reconciliation batch WHEN allocating funds THEN the engine computes exact 20% MG, 49.5% DG, and 10.5% Admin shares via Hare-Niemeyer rounding. | SPEC | [x] |

---

## Traceability Matrix (TRC)

| Req ID | Code Files | Test Files | Commits |
|---|---|---|---|
| FR-001 | dotnet/Nsdms.Application/Services/WspService.cs | dotnet/Nsdms.Tests/WspServiceTests.cs | 4ccfd45 |
| FR-002 | dotnet/Nsdms.Application/Services/WspService.cs | dotnet/Nsdms.Tests/MgWindowMakerCheckerTests.cs | 0e5ca2c |
| FR-003 | dotnet/Nsdms.Application/Services/GrantService.cs | dotnet/Nsdms.Tests/GrantServiceTests.cs | 862938b |
| FR-004 | dotnet/Nsdms.Application/Services/GrantService.cs | dotnet/Nsdms.Tests/GrantServiceTests.cs | 862938b |
| FR-005 | dotnet/Nsdms.Application/Services/TradeTestAndArplService.cs | dotnet/Nsdms.Tests/NambBatchAndPracticalRubricTests.cs | 8320619 |
| FR-006 | dotnet/Nsdms.Application/Services/TradeTestAndArplService.cs | dotnet/Nsdms.Tests/NambBatchAndPracticalRubricTests.cs | 8320619 |
| FR-007 | dotnet/Nsdms.Application/Services/MentorRatioPolicyEngine.cs | dotnet/Nsdms.Tests/MentorRatioPolicyEngineTests.cs | 483e712 |
| FR-008 | dotnet/Nsdms.Application/Services/LearnerService.cs | dotnet/Nsdms.Tests/LearnerServiceTests.cs | 6eb2d42 |
| FR-009 | dotnet/Nsdms.Application/Services/DocumentVerificationService.cs | dotnet/Nsdms.Tests/UniversalDocumentVerificationTests.cs | 4ccfd45 |
| FR-010 | dotnet/Nsdms.Application/Services/SarsLevyStreamingPipeline.cs | dotnet/Nsdms.Tests/SarsLevyStreamingPipelineTests.cs | 862938b |
| FR-011 | dotnet/Nsdms.Application/Services/SetmisExtractService.cs | dotnet/Nsdms.Tests/StatutoryExtractEngineTests.cs | 6eb2d42 |
| FR-012 | dotnet/Nsdms.Application/Services/NlrdExtractService.cs | dotnet/Nsdms.Tests/StatutoryExtractEngineTests.cs | 6eb2d42 |
| FR-013 | dotnet/Nsdms.Application/Services/WorkingDayCalculationEngine.cs | dotnet/Nsdms.Tests/HolidayAndClosureTests.cs | bf993f7 |
| FR-014 | dotnet/Nsdms.Application/Services/ConflictManagementService.cs | dotnet/Nsdms.Tests/ConflictManagementServiceTests.cs | 780c035 |
| FR-015 | dotnet/Nsdms.Application/Services/KnowledgeCatalogService.cs | dotnet/Nsdms.Tests/KnowledgeCatalogTests.cs | 780c035 |
| FR-016 | dotnet/Nsdms.Application/Services/FinanceService.cs | dotnet/Nsdms.Tests/FinanceServiceTests.cs | 862938b |
| FR-017 | dotnet/Nsdms.Application/Services/ContractVariationService.cs | dotnet/Nsdms.Tests/AuxiliaryEnterpriseServicesTests.cs | 862938b |
| FR-018 | dotnet/Nsdms.Application/Services/BankingDetailsService.cs | dotnet/Nsdms.Tests/BankservAvsAndFraudShieldTests.cs | 862938b |
| FR-019 | dotnet/Nsdms.Application/Services/TradeTestAndArplService.cs | dotnet/Nsdms.Tests/NambBatchAndPracticalRubricTests.cs | 8320619 |
| FR-020 | dotnet/Nsdms.Application/Services/SummativeAssessmentAndModerationService.cs | dotnet/Nsdms.Tests/SummativeAssessmentAndModerationTests.cs | 7d8edc6 |
| FR-021 | dotnet/Nsdms.Application/Services/NonSetaVerificationService.cs | dotnet/Nsdms.Tests/QcdAndNonSetaAndLevyReconTests.cs | 7d8edc6 |
| FR-022 | dotnet/Nsdms.Application/Services/AssessorRegistrationService.cs | dotnet/Nsdms.Tests/AssessorLifecycleTests.cs | 7d8edc6 |
| FR-023 | dotnet/Nsdms.Application/Services/WorkflowGovernanceService.cs | dotnet/Nsdms.Tests/WorkflowGovernanceAndSecurityTests.cs | bf993f7 |
| FR-024 | dotnet/Nsdms.Application/Services/WorkflowEngineService.cs | dotnet/Nsdms.Tests/WorkflowEngineTests.cs | bf993f7 |
| FR-025 | dotnet/Nsdms.Application/Services/FiscalCalendarService.cs | dotnet/Nsdms.Tests/FiscalCalendarCalculationTests.cs | bf993f7 |
| FR-026 | dotnet/Nsdms.Application/Services/WorkplaceApprovalService.cs | dotnet/Nsdms.Tests/WorkplaceApprovalServiceTests.cs | ea12ff8 |
| FR-027 | dotnet/Nsdms.Application/Services/FinanceService.cs | dotnet/Nsdms.Tests/FinanceServiceTests.cs | 862938b |
| FR-028 | dotnet/Nsdms.Application/Services/RolePermissionService.cs | dotnet/Nsdms.Tests/RolePermissionAndCaslTests.cs | 7d8edc6 |
| FR-029 | dotnet/Nsdms.Application/Services/OrganisationService.cs | dotnet/Nsdms.Tests/OrganisationServiceTests.cs | ea12ff8 |
| FR-030 | dotnet/Nsdms.Application/Services/TrainingProviderService.cs | dotnet/Nsdms.Tests/TrainingProviderServiceTests.cs | 7d8edc6 |
| FR-031 | dotnet/Nsdms.Infrastructure/Services/ErpOutboxQueueService.cs | dotnet/Nsdms.Tests/ErpOutboxQueueTests.cs | 862938b |
| FR-032 | dotnet/Nsdms.Application/Services/EnterpriseDocumentTemplateService.cs | dotnet/Nsdms.Tests/DocumentTemplateVersioningAndStudioTests.cs | 4ccfd45 |
| FR-033 | dotnet/Nsdms.Infrastructure/Services/EmailOutboxService.cs | dotnet/Nsdms.Tests/BroadcastAndEmailOutboxTests.cs | 35c4c77 |
| FR-034 | dotnet/Nsdms.Application/Services/WspService.cs | dotnet/Nsdms.Tests/WspServiceTests.cs | 0e5ca2c |
| FR-035 | dotnet/Nsdms.Application/Services/WorkplaceMonitoringService.cs | dotnet/Nsdms.Tests/WorkplaceMonitoringTests.cs | ea12ff8 |
| FR-036 | dotnet/Nsdms.Application/Services/ZoneAndCaseloadService.cs | dotnet/Nsdms.Tests/ZoningAndCaseloadTests.cs | 483e712 |
| FR-037 | dotnet/Nsdms.Application/Services/BusinessRuleEngineService.cs | dotnet/Nsdms.Tests/BusinessRuleEngineTests.cs | 862938b |
| FR-038 | dotnet/Nsdms.Application/Services/LevyService.cs | dotnet/Nsdms.Tests/HareNiemeyerLevyPrecisionTests.cs | 862938b |

---

## Compliance Matrix (CMP)

| Tag | Statutory Source / Standard | Meaning |
|---|---|---|
| SDA | Skills Development Act 97 of 1998 | Governs workplace skills planning, learnership agreements, and artisan trade testing. |
| SDLA | Skills Development Levies Act 9 of 1999 | Governs collection, calculation, and distribution of statutory skills levies. |
| SETAREG | SETA Grant Regulations (2012) | Governs Mandatory and Discretionary Grant allocations, windows, and deadlines. |
| PFMA | Public Finance Management Act 1 of 1999 | Governs financial controls, delegations, dual authorisation, and ethics disclosures. |
| POPIA | Protection of Personal Information Act 4 of 2013 | Governs confidentiality, consent, and masking of national identity numbers. |
| QCTO | Quality Council for Trades and Occupations | Governs occupational qualification accreditation and SDP quality assurance. |
| NAMB | National Artisan Moderation Body | Governs national artisan trade testing standards, toolkits, and mentor ratios. |
| SETMIS | Higher Education SETMIS Standard | Governs Department of Higher Education statutory flat-file reporting specifications. |
| NLRD | SAQA National Learners' Records Database | Governs SAQA Edu.Dex data submission specifications for Supplier 599. |

---

## Change Log

| Version | Date | Author | Trigger | Changes |
|---|---|---|---|---|
| 1.0 | 2026-09-13 | Tebogo Moepi | Schema 3.0 Bootstrap | Initialized living requirements register with 15 verified functional requirements, 4 measurable NFRs, 6 business rules, 5 architecture decisions, and full traceability. |
| 1.1 | 2026-09-13 | Tebogo Moepi | Document Provenance & Linking Enhancement | Added Source Document Registry table with internal markdown links, section anchors, external statutory gazettes, and bound Doc Ref column in FR table. |
| 1.2 | 2026-09-13 | Tebogo Moepi | Contradiction Remediation & Comprehensive FR Expansion | Added FR-016 through FR-027 covering Discretionary Grant MoAs, contract variations, Bankserv AVS, ARPL Section 28, summative assessments, non-SETA verification, assessor registration, financial approval delegations, universal BPM workflow engine, institutional calendar SLA engine, workplace approval monitoring, and inter-SETA transfers with full AC, TRC, and document provenance. |
| 1.3 | 2026-09-13 | Tebogo Moepi | Full Architectural Parity Expansion | Replaced duplicate calendar rule with Fiscal Calendar Quarters Contiguity (FR-025), and added FR-028 through FR-038 covering RBAC CASL abilities, Organisation 360, SDP multi-campus, Dynamics GP ERP outbox, Document Template Studio, Broadcast Outbox rate limiting, WSP 60% training deviation, workplace monitoring, zone caseload balancing, dynamic business rules engine, and SIC code chamber levy splits. |
