# Requirements Register — merSETA NSDMS

> **Schema Version:** 3.0
> **Register Revision:** 1.2
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
| FR-025 | Institutional non-working day calendar management and universal officer SLA pause calculation | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:WorkingDayCalculationEngine.AddBusinessDays | - | PFMA | DOC-SPEC-WPA, DOC-STAT-PFMA | NSDMS-FR-025 | Butcher's computus algorithm for variable Christian holidays, Sunday-to-Monday rollovers, and merSETA annual shutdowns. |
| FR-026 | Workplace approval audit inspection, monitoring visit tracking, and trade accreditation recommendation | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:WorkplaceApprovalService.EvaluateWorkplaceAsync | FR-007 | SDA, QCTO | DOC-SPEC-WPA, DOC-STAT-SDA | NSDMS-FR-026 | Enforces contact person relational link, evaluates trade mentor ratios, and issues site visit audit reports. |
| FR-027 | Inter-SETA Transfer (IST) intake, SARS Chamber re-allocation, and financial journal settlement | [x] | HIGH | Class B | Tebogo Moepi | CODE | HIGH | svc:FinanceService.SaveInterSetaTransferAsync | FR-010 | SDA, SDLA, PFMA | DOC-SPEC-SARS, DOC-STAT-SDLA | NSDMS-FR-027 | Manages employer chamber realignment, transfer request approvals, and inter-SETA financial settlements. |

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
| FR-025 | GIVEN a gazetted public holiday or merSETA institutional shutdown WHEN calculating officer action deadlines THEN the engine advances the due date by the exact number of non-working days. | SPEC | [x] |
| FR-026 | GIVEN an employer workplace approval application WHEN an officer schedules a site audit THEN a verified contact person is mandatory and artisan mentor capacity is verified. | SPEC | [x] |
| FR-027 | GIVEN an employer transferring between SETA chambers WHEN the transfer is approved by the Executive Authority THEN SARS SDL migration records and inter-SETA financial journals are balanced. | SPEC | [x] |

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
| FR-025 | dotnet/Nsdms.Application/Services/WorkingDayCalculationEngine.cs | dotnet/Nsdms.Tests/HolidayAndClosureTests.cs | bf993f7 |
| FR-026 | dotnet/Nsdms.Application/Services/WorkplaceApprovalService.cs | dotnet/Nsdms.Tests/WorkplaceApprovalServiceTests.cs | ea12ff8 |
| FR-027 | dotnet/Nsdms.Application/Services/FinanceService.cs | dotnet/Nsdms.Tests/FinanceServiceTests.cs | 862938b |

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
