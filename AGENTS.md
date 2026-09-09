# AGENTS.md

<!-- UI-STANDARD-RULES:START -->
## UI Rules (.NET 10 Blazor + MudBlazor)

`BLAZOR-MUDBLAZOR-DESIGN-SYSTEM.md` in this repository governs every page, dialog, and shared UI component. Treat it as normative — not as advice.

**Before generating any page:**
1. State which archetype it implements (A1 List, A2 Work Queue, A3 Entity Detail, A4 Form/Wizard, A5 Dashboard, or T3 Reference Data).
2. Check the shared component set in `Components/Shared/`. Use those components; do not hand-write custom badges, headers, or grid wrappers.
3. Check the technology mapping note for this application's entity vocabulary.

**Strict Rules that must never be breached:**
- **View by default (Strict):** Records MUST open in read-only **View** mode (`/{entity}/{id}`) displaying `<ReadOnlyField>` components. Never render editable inputs (`<MudTextField>`), dropdowns, or Save buttons on `/{entity}/{id}`. Edit is strictly a separate route (`/{entity}/{id}/edit`) wrapped in `<FormShell>`. Never combine View and Edit into a single file without explicit read-only display state.
- **Authentication & Authorization:** Every interactive page must declare `@attribute [Authorize]` or `@attribute [Authorize(Roles = "...")]`. Router must use `<AuthorizeRouteView>`. Minimal APIs must declare `.RequireAuthorization()`.
- **Zero Raw Database Access in UI:** Razor components MUST NEVER inject `DbContext` or `IDbContextFactory`. All data fetching and mutations must pass through application service interfaces.
- **Unbounded Query Prohibition:** Never call `.ToListAsync()` without `.Take()` or server-side pagination. In-memory aggregation of full tables in services is strictly prohibited.
- **Destructive Action Confirmation:** Deleting any record must require an affirmative confirmation dialog via `IDialogService.ShowAsync<ConfirmDialog>()`. Immediate unconfirmed deletion is prohibited.
- **Theme Token Invariant:** Hardcoded hex color codes (`#...`) in components and inline `style="..."` attributes for layout/colors are strictly prohibited. All colors and spacing must use MudBlazor theme variables.
- **State reports, actions perform:** A badge is never clickable; a button never displays a value.
- **Workflow axes:** Workflow **state**, **status**, and **flags** are three distinct concepts. Exactly one state badge per page.
- **Central transitions:** Transition actions come from the shared transition service, never from conditions hardcoded in a page.
- **Data Table Baseline:** Every list meets the 13-point data table baseline via `<DataGridShell>` with standard 7-tier page sizes (`5, 10, 20, 50, 100, 250, 500`).
- **Unique key links:** The unique key/identifier column is a real hyperlink (`<MudLink>`) to the record's View route.
- **Sentence Case:** All labels, headers, buttons, and messages use standard sentence case.
- **Responsive Layout:** Page content bounded by `<main id="main-content" style="max-width: 1600px; ...">`. Multi-column grids enclosed in overflow scroll wrappers.
- **Enterprise Multi-Step Wizards:** Before proposing any multi-step UI, read `DESIGN.md` → Section 10 and run the Wizards checklist. All multi-step flows MUST use `<WizardShell>` with standard footer layout, shared field definitions, review step with jump links, and pass build guard `[NSDMS0001]`.
<!-- UI-STANDARD-RULES:END -->

## 16-Point UI Standard Compliance Checklist

Every page must pass all 16 items before being declared complete:

- [ ] **1. Archetype:** Declares which archetype (A1–A5 or T3) it implements.
- [ ] **2. View by default:** Records open in View mode (`/{entity}/{id}`).
- [ ] **3. State vs Action:** No clickable badges; no buttons used to show read-only data values.
- [ ] **4. One state badge:** Exactly one primary state badge in the page header.
- [ ] **5. Shared header:** Page uses `<EntityHeader>` (no hand-rolled headers).
- [ ] **6. Action zone:** Single right-aligned action zone with maximum 1 primary action button.
- [ ] **7. Breadcrumbs:** Explicit breadcrumb hierarchy present.
- [ ] **8. Data table baseline:** Grids use `<DataGridShell>` with search, sorting, and pagination.
- [ ] **9. Monospace identifiers:** Technical keys, IMEIs, serial numbers use `.font-mono`.
- [ ] **10. Unique key link:** Grid identifier is a hyperlink to the View route.
- [ ] **11. Sentence case:** All labels, headers, and buttons use sentence case.
- [ ] **12. Sticky footer in forms:** Create and edit pages use `<FormShell>` with sticky bottom actions and dirty tracking.
- [ ] **13. Five grid states:** Grid explicitly handles Loading, Populated, Empty-DB, Empty-Filter, and Error states.
- [ ] **14. Stepper capping:** Workflows with >3 states use `<WorkflowStepper>` (max 7 steps).
- [ ] **15. Dual-theme support:** Tested in both Light and Dark mode with zero contrast loss.
- [ ] **16. Responsive containment:** Verified at 360px, 768px, and 1440px viewport widths with no canvas blowouts.

### 🛡️ Enterprise 2D Barcode & Document Verification Governance Standard
1. **Universal 2D Barcode Verification Seal**:
   - All official outgoing certificates, outcome letters, and contracts (e.g. WSP Approval Letters, Artisan Trade Test Certificates, Statements of Results, Discretionary Grant MoAs) MUST render the standardized QuestPDF `DocumentVerificationSealComponent`.
   - The seal must include a high-contrast 2D QR code (Model 2, ECC Q/M), the document tracking reference (e.g., `WSP-2026-0042`, `TT-2026-00101`), the truncated SHA-256 digital security seal prefix, and mobile scanning instructions.
2. **Immutable Document Snapshots & Hash Anchoring**:
   - Generation of official outcome documents must invoke `IDocumentVerificationService.CreateAndFreezeDocumentSnapshotAsync` to persist an immutable point-in-time snapshot (`DocumentSnapshot`) and calculate its cryptographic SHA-256 digital fingerprint.
   - Point-in-time snapshots and scan lookups must record double-write entries in `audit_logs`.
3. **Public Verification Portal & POPIA Privacy Compliance**:
   - Scanned QR codes resolve to `/verify/document/{hash}` accessible anonymously without requiring login.
   - For statutory compliance and B-BBEE audits, the portal must display official validity status, employer name, SDL number, scheme year, and B-BBEE Priority Element certification.
   - For individual citizens/learners, confidential 13-digit RSA National ID numbers MUST be masked (e.g. `9504******082`) in compliance with the Protection of Personal Information Act (POPIA).
4. **Machine-Readable Ingestion Barcodes for Wet-Ink Returns**:
   - Multi-page printable return forms (WSP Tripartite Sign-Off, DG MoAs, Learner Agreements) must stamp an ingestion barcode encoding structured metadata (`Module`, `DocumentType`, `RecordId`, `PageNumber`, `TotalPages`, and tamper-proof checksum) via `IDocumentIngestionBarcodeService`.
   - The DMS upload pipeline must decode these machine barcodes to automate document sorting, validation, and auto-indexing with zero manual staff tagging.

---

### 🛡️ Enterprise Holiday & Institutional Closure Governance Standard
1. **Dynamic Configurability & Multi-Day Date-Span Support**:
   - Both statutory South African public holidays (Act No. 36 of 1994, Butcher's Computus, and Sunday rollovers) and annual merSETA institutional shutdowns (e.g. 24 December to 03 January) must be stored in the database (`NonWorkingDay`) and manageable via `/admin/non-working-days` (Archetype A1 List) and `/admin/non-working-days/{id}` (Archetype A3 Detail).
   - Both single-day holidays and multi-day closure spans are supported with inclusive `StartDate` and `EndDate`, automatic `TotalDays` computation, and `CalendarYear` alignment.
2. **Universal Officer Workflow SLA Pausing**:
   - All human officer SLAs (20-day Workplace Approval, 14-day Dispute/Termination, 5-day SDP Site Visit, etc.) MUST evaluate `IWorkingDayCalculationEngine.AddBusinessDays` or `IsNonWorkingDate`.
   - Any approved closure tagged with `AffectsSla = true` automatically pauses the countdown timer and pushes due dates forward across all statutory modules.
3. **Typology Classification & Gazette Provenance**:
   - Every non-working day must be categorized into one of 4 official typologies: `NAT_STATUTORY` (National statutory public holiday), `INST_SHUTDOWN` (merSETA annual year-end shutdown), `ADHOC_GAZETTED` (Ad-hoc gazetted public holiday), or `SPEC_CLOSURE` (Special administrative closure).
   - Ad-hoc and special closures require capturing statutory authority / resolution provenance (`GazetteOrResolutionRef`).
4. **Master-Detail View-by-Default & SLA Simulator**:
   - Records open in View mode (`/{id}`) with `<ReadOnlyField>` components. Edits are isolated to `/{id}/edit` with Save and Cancel buttons.
   - Master-Detail views must feature an interactive SLA Impact Simulator allowing administrators to input arbitrary start dates and business day horizons to visually verify pause spans and target delivery dates.
5. **Audited Double-Write & Segregation of Duties**:
   - All holiday/closure creations, updates, approvals, and deletions must record immutable double-write snapshots in `audit_logs` with before and after state captures.

---

### 🛡️ UI Plain-Language & Anti-Jargon Governance Invariant
1. **Zero Database/Architecture Jargon**: Replace "Double-Write / MetadataJson" with **"Audited Change Log"**; replace "Temporal Tables" with **"Historical Version Timeline"**.
2. **Zero Cryptography Jargon**: Replace "SHA-256 Hash / Fingerprint" with **"Digital Security Seal"** or **"Verification Reference"**. Keep 64-character hashes hidden under an expandable "Technical Verification Data" drawer.
3. **Disambiguate "Claims"**: Never use "Claims" for authorization permissions on the UI (use **"Permissions / Authorised Functions"**). Reserve "Claims" exclusively for **Discretionary Grant Tranche Invoices**.
4. **Natural Workflow State Language**: Replace "Terminal State" with **"Completed / Finalised"**; replace "Workflow Blueprint" with **"Approval Process Lifecycle"**.
5. **Task Management Clarity**: Replace "Task Lease" with **"Reserved / In Review by [Officer]"**.
6. **Mask All Database Integer Keys**: Dropdowns, headers, badges, and table cells must only display statutory business references (e.g. `DG-2026-TOYOTA-01`, `WSP-2026-0042`, `SDL: L123456789`).

---

### 🛡️ MerSETA Statutory Nomenclature & Terminology Governance
1. **Discretionary Grants (DG) vs Mandatory Grants (MG)**: Never use the word "Grant" in isolation. Always qualify as "Discretionary Grant (DG)" (PIVOTAL strategic allocations / MoAs) or "Mandatory Grant (MG)" (20% WSP/ATR levy rebates).
2. **Contracting via MoA**: The legal contracting instrument for Discretionary Grants is the **Memorandum of Agreement (MoA)**, never generic "Contracts".
3. **Skills Development Providers (SDP)**: Refer to accredited training institutions as **Skills Development Providers (SDPs)** per QCTO statutory guidelines.
4. **Artisan Mentorship Ratios**: Enforce NAMB / QCTO artisan mentor-to-apprentice ratios via `IMentorRatioPolicyEngine`, respecting trade-specific caps.
5. **Governance & PFMA Controls**: Adhere to financial approval delegation, Segregation of Duties (Maker-Checker), and non-repudiation audit logging for all approval gates.

---

---

### 🛡️ SARS Monthly Levy Reactive Streaming & SqlBulkCopy Staging Standard
1. **Constant Memory Streaming & Digital Security Seal**:
   - Monthly SARS levy files (often exceeding 100,000 lines) must never be loaded into memory via monolithic string splitting or buffers. Ingestion must use `ISarsLevyStreamingPipeline` with line-by-line `StreamReader` streaming and on-the-fly cryptographic SHA-256 Digital Security Seal calculation.
2. **Trailer Control & Truncation Safeguard**:
   - Every file with a `TRAILER` or `CONTROL` record must enforce exact equality between parsed data record counts and gross monetary totals against declared trailer controls ($\le$ R0.05 tolerance). Any mismatch must abort the batch to prevent silent file truncation.
3. **High-Speed Staging & Partitioned Querying**:
   - Raw records must be flushed to `SarsLevyStaging` in micro-batches using `SqlBulkCopy` (`ISarsBulkStagingWriter`). Entity lookups (e.g. `Organisations`, `SicCodeTypes`) must be partitioned in chunks $\le 1500$ to strictly adhere to SQL Server's 2,100 parameter limit.
4. **Idempotency & Non-Repudiation**:
   - Re-importing a file with an identical Digital Security Seal must be blocked to prevent duplicate financial allocations. Batch promotion must record an audited change log entry in `audit_logs`.
5. **Streaming In-Line Pre-Flight Gatekeeper & Zero-Tolerance Policy**:
   - Before any staging records (`SarsLevyStaging`) or financial ledger records (`LevyFile`) are written, the raw file stream must pass pre-flight compliance inspection via `ISarsCompliancePreProcessor`.
   - Structural encoding, statutory SDL number regex (`^L\d{9}$`), 5-digit SIC codes, non-negative monetary values, duplicate Digital Security Seals, and trailer control reconciliations must be 100% compliant.
   - Any compliance violation must immediately throw `SarsComplianceException`, aborting ingestion with zero database modifications and returning a line-by-line forensic diagnostic log.---

## Option and lookup controls

The Option and Lookup Controls Standard v1.1 (Rules E.1 and E.2, OPT-001 through OPT-011) governs all option, lookup, and status controls across this organization:
- **OPT-001**: Status, Workflow State, and Lifecycle fields MUST NOT be bound to user-editable option controls; they must render as read-only text or `<StatusBadge>`.
- **OPT-002**: In View mode, every option field MUST render as text or badge (`<ReadOnlyField>`). Disabled inputs are prohibited.
- **OPT-003 / OPT-004**: Lists > 15 items MUST use searchable comboboxes (`<SearchableLookup>`) debounced $\ge 300\text{ms}$ with min 2 chars and capped results ($\le 50$); entity references and lists > 500 MUST use `<EntityLookupDialog>`.
- **OPT-005**: Reference data MUST be served from cached scoped services with declared TTL expiry (`LookupService`).
- **OPT-006**: 2–5 exclusive record values MUST use radio groups enclosed in `<fieldset><legend>`.
- **OPT-010**: Cascading lookups MUST clear and disable children when the parent value is empty.
- **OPT-011**: All lookups MUST be registered in `declarations.yml`.

---

### 🛡️ ARPL & Artisan Trade Test Statutory Governance Standard (Signed 2023 Specification)
1. **17 Designated Toolkit Trades Whitelist**:
   - Only 17 statutory designated trades (Diesel Mechanic, Motor Mechanic, Boilermaker, Welder, Fitter, Fitter & Turner, Electrician, Heavy Equipment Mechanic, Instrument Mechanic, Lift Mechanic, Shipbuilder, Panel Beater, Vehicle Painter, Bricklayer, Plumber, Carpenter, Sheet fed-Lithograph) require toolkits.
   - Category 7 applications (`Category7_Min3Years_ToolkitAssessment`) are strictly restricted to these 17 trades. All other trades require qualification prerequisites under Categories 1–6.
2. **50% Task Credit Retention & Non-Destructive History**:
   - Candidates passing $\ge 50\%$ of evaluated practical tasks retain credit for passed modules for a maximum of 3 attempts or 18 months.
   - Old tasks must NEVER be deleted from `TradeTestTask` upon re-testing; tasks are partitioned by `AttemptNumber` to maintain a non-repudiable audit trail.
3. **2-Tier Regional Approval & Serial Number Milestone**:
   - Applications must pass 2 sequential regional gates: CLA recommendation (`RecommendedApplication`) followed by Regional QA approval (`Registered`).
   - The official Trade Test Serial Number (`TT-SER-{yyyy}-{id:D5}`) is generated strictly upon QA approval and must be stamped on the re-uploaded application document.
   - QA rejection requires setting `IsFinalRejection`: `false` routes for candidate resubmission (`RejectedForResubmission`); `true` marks terminal rejection (`RejectedApplication`).
4. **Dynamic Document Upload Gate**:
   - Toolkit trades require 7 mandatory verified documents; non-toolkit trades require 6 verified documents per Section 4.2.7.

---

### 🛡️ Workplace Approval & Site Inspection Statutory Governance Standard (Signed 2022 Specification)
1. **Role-Neutral Maker-Checker Workflow Architecture**:
   - Job titles such as "CLO" or "QA" are transitory organizational assignments, not permanent workflow roles.
   - All workflow states, transitions, entity columns, and UI actions MUST use role-neutral terminology:
     - **Verification / Inspection**: `Verification Officer` (verification audit, on-site or desktop audit, `VerifiedDate`, `VerifiedByPersonId`, `VerificationRecommendationReason`, `VerificationRejectionReason`).
     - **Evaluation / Adjudication**: `Approval Authority` (maker-checker decision gatekeeper, `DecisionDate`, `DecisionByPersonId`, `ApprovalReason`, `RejectionReason`).
     - **Applicant / Submitter**: `Primary SDF / Applicant` (submission, condonation, withdrawal).
2. **20-Working-Day Statutory Inspection SLA**:
   - Applications submitted to status `APPLICATION` MUST compute a 20-working-day SLA inspection due date (`InspectionDueDate`), excluding weekends and gazetted public holidays via `WorkplaceApprovalService.AddBusinessDays`.
   - UI master detail views must render real-time countdown chips with alert state progression (Compliant, Warning $\le 5$ days, Overdue).
3. **Desktop vs Physical On-Site Inspection Dual-Mode**:
   - Verification may occur via physical on-site audit (`IsSiteVisitRequired = true`) or desktop evaluation (`IsSiteVisitRequired = false`).
   - If a physical site visit is waived in favour of desktop audit, `SiteVisitJustification` is mandatory and must be audited.
4. **Official QuestPDF Statutory Controlled Documents**:
   - **Annexure 10.1 (`ETQ-TP-003`)**: Workplace Approval Outcome Letter complete with official merSETA branding, QR code verification reference (`/verify/workplace-approval/{id}`), 3-year accreditation validity cycle, and controlled document metadata box.
   - **Annexure 10.2 (`ETQ-TP-054`)**: Workplace Approval Report complete with site information, trade curriculum scope, tool/equipment compliance audit table, and certified artisan mentor quota breakdown.
5. **Preservation of Advanced Invariants & Relational Collections**:
   - Live 5-tier cascading NAMB mentor-to-apprentice ratio policy engine (`IMentorRatioPolicyEngine`).
   - Mandatory Employer Contact Person linkage for all site visits and inspections.
   - 360-degree relational tabs (Placed Apprentices, Partnering SDPs, Site Audits, Tool Inventory, Mentors, Evidence Vault, Workflow Timeline).

---

### 🛡️ Bursary Registration Statutory Governance Standard (Signed 2022 Specification MerSeta\NSDMS\LMS\LR\01)
1. **Unemployed Employer Exemption & Relational Nullability**:
   - For all Bursary applications (`LearningProgrammeTypeCode == "05"`), unemployed applicants (`EmploymentStatusCode == "Unemployed"` or `EconomicStatusId == "02"`) are statutorily exempt from attaching an Employer (`OrganisationId` nullable).
   - Employed applicants MUST link a valid levy-paying or registered employer (`OrganisationId`).
2. **New vs Continuation Bursary Lifecycle Rules**:
   - Continuation applications (`BursaryApplicationTypeCode == "Continuation"`) MUST link to a previously registered active bursary record (`PreviousCompanyLearnerId`).
   - Anti-tamper execution date precondition: A continuation agreement execution date cannot be equal to or earlier than the predecessor agreement execution date.
   - Academic progression: `YearOfStudy` must advance by exactly +1 year, and passed prior academic transcripts (`ContinuationAcademicResultsPassed = true`) are mandatory.
3. **Statutory Bursary Funding Typology**:
   - Every bursary must map to one of the 7 official statutory funding types (`lookup.BursaryFundingType`): `01` - merSETA Funded, `02` - Employer Funded, `03` - Institution Funded, `04` - Self-Funded, `05` - NSFAS / Other Public, `06` - Donor / NGO Funded, `07` - Other Funding.
4. **Dynamic Evidentiary Document Gate**:
   - Unemployed bursaries require certified RSA ID / passport, proof of tertiary registration, verified academic results, and proof of unemployment / affidavit.
   - Employed bursaries additionally mandate certified employer confirmation and tripartite bursary agreement.
5. **Audited Double-Write & Contract Generation**:
   - Bursary registration must generate serial contract numbers `BUR-{yyyy}-{id:D5}` and perform double-write logging to `audit_logs`.

---

### 🛡️ Skills Development Provider (SDP) Accreditation Statutory Governance Standard (Signed 2023 Specification)
1. **5 Accreditation Streams & Trade Test Centres (TTC)**:
   - Accreditation intake must explicitly support 5 distinct statutory streams: Primary merSETA, Programme Approval (non-merSETA SETA), QCTO SDP, QCTO Trade Test Centre (TTC), and Non-merSETA Scope.
   - For Trade Test Centres (TTC), the application MUST capture NAMB TTC assessor/moderator registration credentials and validity dates.
2. **Interactive Two-Stage QMS Self-Evaluation Gate**:
   - After initial QA recommendation, the system must issue a QMS Self-Evaluation task back to the Primary SDP Contact to complete compliance checks (Yes/No, document references, and evidence uploads) before physical site inspection scheduling.
3. **5-Day Site Visit SLA & 6-Month Re-Accreditation Invariant**:
   - Submissions must enforce a 5-working-day SLA for QA initial contact and inspection scheduling.
   - Re-accreditation applications are permitted strictly within 6 months prior to expiry. During re-accreditation processing, the provider's operational status MUST NOT revert to "Pending Approval", ensuring unhindered learner registration transactions.
4. **Multi-Contact Quorum & Banking Confirmation**:
   - Provider registration requires a minimum of two (2) verified contact persons. At least one designated contact other than the primary SDF must be tagged with banking details confirmation authority.
5. **Double-Write & Digital Security Seal**:
   - All approval gates (Regional QA, QA Manager, Review Committee, Senior QA Manager) must record atomic snapshots in `audit_logs` and stamp the generated Accreditation Certificate with an immutable SHA-256 digital security seal.

---

### 🛡️ Assessor and Moderator Registration Statutory Governance Standard (Signed 2023 Specification)
1. **3-Year Post-Qualification Industry Practice Gate**:
   - In accordance with merSETA ETQA regulations, applicants for Assessor or Moderator status MUST possess $\ge 3$ years of verifiable post-qualification occupational practice (`(DateTime.UtcNow - QualificationObtainedDate).TotalDays / 365.25 >= 3.0`). Registration of qualification scopes failing this threshold must be rejected immediately at intake.
2. **Constituent Unit Standards Protection & Non-Removability**:
   - Adding an accredited qualification scope automatically cascades all constituent unit standards with `IsPopulatedFromQualification = true`.
   - Constituent unit standards derived from the parent qualification cannot be deleted individually. Only standalone unit standards may be added or removed independently.
3. **4-Stage Maker-Checker Workflow with Two-Tier Rejection**:
   - Initial applications must traverse 4 sequential gates: Stage 1 Document Verification (`VerificationOfficer`), Stage 2 Application Evaluation (`EvaluationOfficer`), Stage 3 ETQA Review Committee Adjudication, and Stage 4 Senior Manager Final Approval (`Approved`).
   - Review Committee rejection enforces Maker-Checker distinction: `IsFinalRejection = false` routes the application for candidate correction (`RejectedForResubmission`); `IsFinalRejection = true` terminates the process (`RejectedApplication`).
4. **Multi-SDP Affiliation & Service Level Agreement (SLA)**:
   - Practitioners can affiliate with multiple accredited Skills Development Providers (SDPs). Each affiliation mandates capture of a verified Service Level Agreement document reference (`SlaDocumentRef`).
5. **Disciplinary Sanctioning, DHA Deceased De-registration & QuestPDF Seal**:
   - Disciplinary investigations (`AssessorDisciplinaryCase`) conclude with Review Committee sanctions: Suspension (`SUSPENDED`), De-registration (`DEREGISTERED`), or Dismissal (`DISMISSED`). Suspensions immediately set `AssessmentAbilitySuspended = true` and revoke operational assessment eligibility across all learner enrolments.
   - Formal DHA death notifications (`RecordDeceasedAsync`) immediately transition practitioner status to `De-Registered`, stamp `DeRegistrationReason = "Deceased"`, and permanently suspend assessment abilities.
   - All outcomes generate statutory QuestPDF certificates and outcome letters stamped with immutable SHA-256 digital security seals.

---

### 🛡️ Learner Registration Dual-Channel (Automated Bulk vs Manual Single) Statutory Invariant
1. **Dual-Channel Coexistence**:
   - The automated self-service bulk ingestion channel (`/learners/bulk-register`) MUST exist alongside the manual single-registration wizard workflows (`LearnerAgreementRegistrationWizard.razor` and `BursaryRegistrationWizard.razor`). Automated options must NEVER replace or deprecate manual registration routes.
2. **Straight-Through Processing (STP) 6-Gate Safeguards**:
   - Automated registration through `ILearnerStpRiskEngine` is strictly limited to 100% compliant submissions matching 6 gates: active levy-paying employer, verified workplace approval with mentor ratios (`IMentorRatioPolicyEngine`), active non-expired SAQA qualification, valid RSA ID Luhn algorithm check, mandatory guardian details for minors under 18, and execution date within 30 working days.
3. **Graceful Exception Routing**:
   - Any row failing any of the 6 STP criteria must NOT terminate the batch; instead, it is safely queued into `VerificationPending` for officer maker-checker review and manual condonation evaluation.
4. **Digital Security Seal & Double-Write Audit Trail**:
   - Ingested bulk batches must calculate an immutable SHA-256 digital security seal over payload contents. All STP auto-approvals must perform atomic double-writes into `audit_logs` with actor `SYSTEM_STP_GATEKEEPER`.

---

### 🛡️ Skills Development Provider (SDP) Delivery Sites vs Campuses Nomenclature Standard
1. **Statutory Terminology Alignment**:
   - In accordance with merSETA ETQA statutory governance, physical locations where Skills Development Providers (SDPs) conduct accredited training deliveries and assessments MUST be designated as **"Delivery Sites"** or **"Sites"** (e.g. *Main Site*, *Secondary Delivery Site*, *Site Code*, *Site Inspection*).
   - Never use the term "Campus" or "Campuses" in user-facing labels, table headers, dialogs, button texts, toast messages, or navigation items.
2. **Backward-Compatible Domain & Service Aliasing**:
   - Underlying entity models and database tables may preserve physical storage columns (`TrainingProviderCampus`) while exposing `SiteName`, `SiteCode`, `SiteContactPersonName`, and `SiteContactPhone` via `[NotMapped]` aliases (with explicit `entity.Ignore(...)` in `NsdmsDbContext.cs`).
   - Application service contracts must provide `ISdpSiteService` / `SdpSiteService` as the primary interface, with `ISdpCampusService` retained as an alias for non-breaking backward compatibility.

---

### 🛡️ Dynamic Configuration & Zero-Hardcoding Invariant
1. **Zero Hardcoded Business Rules**:
   - Never hardcode statutory SLAs, approval validity durations, financial split percentages, mentor ratios, test attempt limits, file upload caps, or storage paths in service classes or UI components.
2. **Standard Resolution Pattern**:
   - Always inject `ISystemConfigurationService` and resolve parameters with `await _configService.GetValueAsync<T>("Category:KeyName", fallbackValue)`.
   - Provide statutory constants strictly as fallback arguments.
3. **Startup Seeding Requirement**:
   - Any newly introduced configuration parameter MUST be added to `SystemConfigurationService.SeedDefaultConfigsAsync()` with its category, data type, description, and default value so it appears automatically in the System Settings administration portal.

---

### 🛡️ View-Backed Entities & SETMIS Schema-Type Synchronization Invariant
1. **Physical Table Alterations for Backward-Compatible Views**:
   - When backward-compatibility views (e.g. `dbo.CompanyLearner` defined over `dbo.LearnerEnrolment`) are mapped to EF Core entities, any schema alteration (`ALTER COLUMN`, `ADD CONSTRAINT`, `CREATE INDEX`) MUST target the underlying physical base table (e.g. `dbo.LearnerEnrolment`).
   - After altering the physical table, always invoke `EXEC sp_refreshview '[ViewName]'` to synchronize metadata in `sys.columns` and prevent `InvalidCastException` during EF Core materialized queries.
2. **SETMIS Alphanumeric Lookup Columns Standard**:
   - All statutory SETMIS lookup and identifier columns (`FundingId`, `PartOfId`, `EnrolmentTypeId`, `EnrolmentStatusId`, `EconomicStatusId`, `UrbanRuralId`, `InternshipStatusId`) MUST be physically stored as `NVARCHAR(10)` or `NVARCHAR(50)`, NEVER as `INT`.
   - In entity classes, declare them as `string?` with Fluent API configuration `entity.Property(e => e.Property).HasMaxLength(10)`.
3. **Detached Navigation Property Nullification**:
   - When persisting child or master entities resolved from external or temporary DbContext instances (e.g. `Person` loaded by RSA ID), always set the navigation property to `null` (`learner.Person = null;`) before calling `db.Add(learner)`. This ensures EF Core attaches strictly via foreign key (`PersonId`) and prevents accidental duplicate inserts or identity tracking conflicts.

---

### 🛡️ Hierarchical Relational Fiscal Calendar & Working Day Governance Standard
1. **Relational Model & Contiguity Invariant**:
   - Every financial year record (`FinancialYear`) must manage 4 sequential relational quarters (`FinancialQuarter`, 1:4).
   - Quarter dates must satisfy strict contiguity: $Q_n.\text{EndDate} + 1\text{ day} == Q_{n+1}.\text{StartDate}$, with $Q_1.\text{StartDate} == \text{FinancialYear.StartDate}$ and $Q_4.\text{EndDate} == \text{FinancialYear.EndDate}$. Overlaps and gaps must be rejected at both domain and UI validation levels.
2. **Statutory South African Working Days Computation**:
   - Calculation of working days across months and quarters must use `SouthAfricanPublicHolidays` (Butcher's Computus algorithm for Easter/Good Friday/Family Day and Section 2(1) Sunday rollover to Monday under Act No 36 of 1994).
   - February days must dynamically evaluate `DateTime.IsLeapYear(year)` (29 days for leap years, 28 for non-leap years).
3. **Master-Detail & View-by-Default Architecture**:
   - Fiscal calendar management must reside at `/admin/financial-years` (Archetype A1 List) and `/admin/financial-years/{id}` (Archetype A3 Detail).
   - Viewing records (`/{id}`) is strictly read-only by default. Edits are confined to `/{id}/edit` with Save and Cancel buttons.
4. **Audited Double-Write & Dynamic Configuration**:
   - All mutations (financial year creation, quarter modifications, deletions, and template updates) must perform atomic double-writes into `audit_logs` with before/after state snapshots.
   - Statutory default dates must resolve dynamically from `SystemConfigurationService` (`Fiscal:Default*`) with fallback constants.
5. **Native Maker-Checker Governance Lifecycle & Segregation of Duties**:
   - Fiscal year lifecycles follow: `Draft` $\rightarrow$ `Under Review` $\rightarrow$ `Active` $\rightarrow$ `Inactive` $\rightarrow$ `Amendment Draft` (Rev #2).
   - Segregation of Duties is strictly enforced: the preparer/submitter cannot approve their own submission (`SubmittedBy != userId`). An independent reviewer is required unless dynamically overridden by `Fiscal:AllowAnyAdminReviewer == true`.
   - Active scheme years are strictly locked against direct modification; updates require clicking "Request amendment" with mandatory justification, automatically generating `Revision #2` in `Amendment Draft` mode.
   - All human UAT test cases must strictly avoid technical URLs (using UI menus, breadcrumbs, action buttons, and modal dialogs). Automated Playwright tests must verify Segregation of Duties rejections and use `evaluate("el => el.click()")` when switching tabs underneath sticky headers.

---

### 🛡️ QCTO Occupational Accreditation & Trade Test Centre Statutory Governance (SDA §26I)
1. **Statutory Scope Separation (SDA §26I)**:
   - Primary accreditation for occupational qualifications and part-qualifications is held exclusively with the Quality Council for Trades and Occupations (QCTO). MerSETA acts strictly to verify, record, and endorse QCTO-accredited Skills Development Providers (SDPs) and Trade Test Centres (TTCs) for discretionary grant funding and provincial artisan training delivery.
2. **Prohibition of Illegal Form ETQ-TP-002 Issuance**:
   - MerSETA MUST NEVER issue a Form ETQ-TP-002 "Certificate of Accreditation" to providers whose intake stream is `QctoSkillsDevelopmentProvider`, `QctoTradeTestCentre`, or `ProgrammeApproval`.
   - Calling `QuestPdfDocumentService.GenerateSdpAccreditationCertificatePdfAsync` on QCTO/secondary stream providers MUST throw `InvalidOperationException` with a clear reference to SDA §26I.
3. **Letter of Endorsement & Scope Confirmation (Form ETQ-QCTO-001)**:
   - For QCTO SDPs and Trade Test Centres, merSETA generates an official **Letter of Endorsement & Scope Confirmation** (`GenerateQctoEndorsementLetterPdfAsync`, Form ETQ-QCTO-001) that verifies the provider's registered delivery sites, scope qualifications, and QCTO accreditation reference.
4. **Mandatory Dedicated QCTO & NAMB Model Properties**:
   - Domain model `TrainingProvider` and SQL Server tables MUST define dedicated columns for QCTO credentials: `QctoAccreditationNumber`, `QctoAccreditationStartDate`, `QctoAccreditationEndDate`, `QctoCentreCode`, `QctoLetterAttachmentRef`, and `NambRegistrationNumber`.
   - When registering or endorsing, validity dates must preserve the QCTO/NAMB cycle dates rather than defaulting to merSETA's 5-year cycle.
5. **Stream Validation & Maker-Checker Workflow**:
   - Domain validator `TrainingProviderDomainValidator.ValidateAccreditationStream` and application service `TrainingProviderService` MUST enforce presence of `QctoAccreditationNumber` for QCTO SDPs, and `NambRegistrationNumber` for QCTO TTCs.
   - The Stage 3 review queue must route QCTO streams to "Verify & Endorse QCTO / Secondary Credentials" rather than standard primary committee adjudication.




