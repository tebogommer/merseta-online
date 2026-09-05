# GEMINI.md

<!-- UI-STANDARD-RULES:START -->
## UI Rules (.NET 10 Blazor + MudBlazor)

`BLAZOR-MUDBLAZOR-DESIGN-SYSTEM.md` in this repository governs every page, dialog, and shared UI component. Treat it as normative — not as advice.

**Before generating any page:**
1. State which archetype it implements (A1 List, A2 Work Queue, A3 Entity Detail, A4 Form/Wizard, A5 Dashboard, or T3 Reference Data).
2. Check the shared component set in `Components/Shared/`. Use those components; do not hand-write custom badges, headers, or grid wrappers.
3. Check the technology mapping note for this application's entity vocabulary.

**Strict Rules that must never be breached:**
- **View by default:** Records open in **View** mode (`/{entity}/{id}`). Edit is a separate route (`/{entity}/{id}/edit`) with Save/Cancel buttons.
- **State reports, actions perform:** A badge is never clickable; a button never displays a value.
- **Workflow axes:** Workflow **state**, **status**, and **flags** are three distinct concepts. Exactly one state badge per page.
- **Central transitions:** Transition actions come from the shared transition service, never from conditions hardcoded in a page.
- **Data Table Baseline:** Every list meets the 13-point data table baseline via `<DataGridShell>`.
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

---

### 🛡️ Schema-Domain Synchronization Rule
- Whenever new properties are added to an Entity class in `Nsdms.Domain/Entities/`, immediately:
  1. Add corresponding `ALTER TABLE ... ADD [ColumnName] ...` clauses to the active Schema Migrator in `Nsdms.Infrastructure/Data/`.
  2. Update the master DDL script (`V2026_08_Complete_Nsdms_Enterprise_DDL.sql`).
  3. Verify column presence against `INFORMATION_SCHEMA.COLUMNS` before testing UI routes.

---

### 🛡️ EF Core Fluent API & [NotMapped] Alias Invariant
- When entity classes contain computed convenience getters/setters or statutory aliases marked with `[NotMapped]` (e.g. `SkillsProgrammeCode` aliasing `NonNqfIntervCode` on `SkillsRegistration`), **NEVER** configure those aliases with `entity.Property(...)` or `entity.HasIndex(...)` in `NsdmsDbContext.cs` Fluent API without explicit `entity.Ignore(...)`.
- Fluent API overrides CLR attributes. Calling `entity.Property(...)` on a `[NotMapped]` property forces EF Core to query the alias as a physical column in SQL Server, resulting in invalid column runtime exceptions (`Invalid column name '...'`).
- Always map the actual statutory persistent columns (e.g., `s.NonNqfIntervCode`) and explicitly call `entity.Ignore(...)` for any alias convenience properties.

---

### 🛡️ Artisan Mentor-to-Apprentice Ratio Governance Standard
1. **Cascading Evaluation Precedence**:
   When validating learner enrollments, workplace capacity, or artisan quotas, ALWAYS resolve ratios via `IMentorRatioPolicyEngine.EvaluateWorkplaceApprovalCapacityAsync(id)`. Never hardcode a 1:4 ratio. The engine evaluates policies in the following strict order:
   - **Tier 1 (Mentor Override)**: `WorkplaceApprovalMentor.MaxLearnerCapacity` or `IsRatioExempt = true`.
   - **Tier 2 (Workplace Approval)**: `WorkplaceApproval.CustomTradeRatio` or `IsRatioEnforced = false`.
   - **Tier 3 (Organisation Exemption)**: `Organisation.IsMentorRatioEnforced = false` or `Organisation.CustomMentorRatioCap`.
   - **Tier 4 (Trade Policy)**: `TradeMentorRatioPolicy.StandardRatio` matching the trade/qualification.
   - **Tier 5 (Global Toggle)**: System configuration `WorkplaceApproval.EnforceMentorRatios`.
2. **Audit Double-Write**: All policy overrides and trade policy mutations must record snapshots in `audit_logs`.

---

### 🛡️ DG Funding Window Lifecycle & Gazette Governance Standard
1. **Lifecycle & Deadline Enforcement**:
   - Every Discretionary Grant funding allocation cycle MUST be defined through a `GrantFundingWindow` record with a designated financial scheme year, opening date, hard closing deadline date, and total budget allocation envelope.
   - Dedicated management routes exist at `/dg-funding-windows` (Master Registry) and `/dg-funding-windows/{id}` (Detail Hub), with legacy aliases at `/grants/windows`.
   - On the Discretionary Grant application form (`/dg-grants/{id}`), applicants and grant administrators must link submissions to gazetted funding windows via `FundingWindowId`.
2. **Audit Double-Write**: All window creation, modification, extension, and close-down actions must record audit snapshots in `audit_logs`.

---

### 🛡️ UI Primary Key Masking & Business Reference Invariant
1. **Zero Integer Key Leakage**: Never display auto-increment database integer IDs or primary keys on the UI (dropdowns, table cells, headers, badges, toasts, or breadcrumbs).
2. **MudSelect ToStringFunc Requirement**: In `MudSelect` components binding to integer foreign keys (e.g., `FundingWindowId`, `OrganisationId`, `QualificationId`), ALWAYS provide custom `ToStringFunc` converters so the UI renders descriptive names, financial years, and budget envelopes rather than raw database numbers.
3. **Statutory Reference Display**: Always display statutory business reference numbers (e.g., Application Number `DG-2026-TOYOTA-01`, WSP Reference `WSP-2026-0042`, MoA Number `MOA-2026-001`, SDL Number `L123456789`, RSA National ID, or entity titles).

---

### 🛡️ Discretionary Grant (DG) vs Mandatory Grant (MG) Universal Nomenclature Standard
1. **Zero Generic "Grant" Usage**: Never use the term "Grant" or "Grants" in isolation. Using "Grant" without qualification causes ambiguity between Mandatory Grants (20% statutory levy rebates) and Discretionary Grants (49.5% strategic PIVOTAL allocations).
2. **Mandatory Explicit Suffixes**: Always explicitly specify **"Discretionary Grant (DG)"** or **"Mandatory Grant (MG)"** across all UI headers, action buttons, table columns, breadcrumbs, toasts, URIs, and code comments.
3. **Standard Route Conventions**:
   - Discretionary Grants: `/dg-grants`, `/dg-grants/{id}`, `/dg-grants/create`, `/dg-funding-windows`
   - Mandatory Grants: `/wsp`, `/wsp/{id}`, `/wsp/create`, `/finance/mg-rebates`
   - Discretionary Grant Contracting: `/finance/dg-moa`, `/finance/dg-moa/{id}`

---

### 🛡️ Statutory Navigation Pillar Architecture & Persona Governance Standard
1. **7 Statutory Domain Pillars**: All navigation menu items MUST reside strictly within the 7 official NSDMS domain pillars:
   - `Overview & tasks`
   - `Registries & stakeholders`
   - `Grants, levies & finance`
   - `Learner & artisan development`
   - `Quality assurance & ETQA`
   - `Legal, compliance & BI`
   - `System administration`
2. **Zero IT Asset / Hardware Module Leakage**: Never place IT asset tracking, SIM cards, or telecom hardware modules into core MerSETA skills navigation menus.
3. **Collapsible Accordion & Sentence Case Invariant**: All pillar headers and menu items must use standard sentence case and implement `MudNavGroup` accordion containers with persisted collapse states.
4. **Persona Normalization**: All 10 role personas (`SDF`, `SDP`, `Assessor`, `Finance`, `CLO`, `Legal`, `Compliance`, `Executive`, `Admin`, `All`) must be mapped dynamically in `NavigationMenuService` and `PersonaSwitcher`.
5. **Decoupled Pin Targets**: Quick-access pin toggles must be standalone icon buttons isolated from nav link anchors to prevent overlapping focus/touch hitboxes (WCAG 2.2 AA SC 2.5.8).

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
5. **Governance & PFMA Controls**: Adhere to Delegation of Financial Authority (DOFA), Segregation of Duties (Maker-Checker), and non-repudiation audit logging for all approval gates.

---

### 🛡️ Multi-Tenancy Query Filter Resilience Invariant
1. In `NsdmsDbContext`, always initialize `_tenantProvider` with a guaranteed non-null fallback:
   `_tenantProvider = tenantProvider ?? new DefaultTenantProvider(null, isAdmin: true);`
2. Never perform direct null navigation on `_tenantProvider` inside EF Core query filters. Use `_tenantProvider.IsAdmin || _tenantProvider.CurrentOrganisationId == null || entity.OrganisationId == _tenantProvider.CurrentOrganisationId` with explicit spacing around ` == `.
3. For background services, bulk batch syncs, and system-level reconciliations, explicitly append `.IgnoreQueryFilters()` when cross-tenant aggregation is required.

---

### 🛡️ High-Volume Primary Key & Audit Invariant
1. Entities expected to exceed $2.14 \times 10^9$ rows (`LevyFileLine`, `WspTrainingPlan`, and `AuditLog`) must inherit `BaseLongEntity` (`Id` of type `long` / `BIGINT`).
2. `AuditLog.RecordId` and `IAuditService.LogActionAsync(..., long recordId, ...)` must use `long` to ensure compatibility with both standard `int` and high-volume `long` entities.

---

### 🛡️ Automated Test Isolation & SQL Server Probing Guardrail
1. Unit and integration tests must never attempt live socket/pipe connections to local SQL Server instances without explicit opt-in environment variables (e.g. `ENABLE_LIVE_SQL_SYNC == "true"`).
2. Any test that modifies process-level environment flags (e.g. `ENABLE_EF_TEMPORAL_TABLES`) must implement `IDisposable` and clear the flag in `Dispose()` to avoid corrupting subsequent tests executed by the test host.

---

### 🛡️ Statutory DHET SETMIS & SAQA NLRD Flat-File Extract Invariant
1. **DHET SETMIS Positional Record Lengths**:
   All 11 statutory flat files must strictly match exact character widths without delimiters:
   - File 100 (Provider): 799
   - File 200 (Provider Accreditation): 796
   - File 304 (Non-NQF Registration): 328
   - File 400 (Person Demographics): 845
   - File 401 (Assessor / Moderator): 187
   - File 500 (Learnership Enrolment): 258
   - File 501 (Qualification Enrolment): 411
   - File 502 (Non-NQF Enrolment): 273
   - File 503 (Unit Standard Assessment): 407
   - File 505 (Apprenticeship Enrolment): 206
   - File 506 (Internship Enrolment): 185
2. **SAQA NLRD Edu.Dex Invariant**:
   - Supplier Code is always fixed at `599` (MerSETA).
   - All files must be prepended with a statutory `HEADER599` record padded to the exact file record length:
     - File 21: 847
     - File 24: 135
     - File 25: 791
     - File 26: 157
     - File 27: 119
     - File 28: 143
     - File 29: 161
     - File 30: 171
3. **Double-Write & Digital Security Seal**:
   Every batch generation must compute a SHA-256 digital security seal over the batch archive, record snapshots in `StatutorySubmissionBatch`, and write to `audit_logs`.

---

### 🛡️ Culture-Invariant ERP & Flat-File Formatting Invariant
1. In all financial exports, flat-file extracts, and ERP batch generation routines (Dynamics GP, Sage Pastel, SARS, SETMIS, NLRD), always format decimal and currency values with explicit `CultureInfo.InvariantCulture` (`value.ToString("F2", CultureInfo.InvariantCulture)`).
2. Never rely on default `ToString("F2")` without culture parameters on Windows hosts where the OS culture may be set to South Africa (`en-ZA`) or European locales that output comma `,` decimal separators instead of dot `.`.

---

### 🛡️ WSP Multi-Party Quorum & Dispute Governance Invariant
1. **Quorum Logic by Employer Size**:
   - Small employers (< 50 staff): Bipartite Quorum requiring 2 digital attestations (Primary SDF + CEO / Accounting Authority).
   - Medium/large employers ($\ge$ 50 staff): Tripartite Quorum requiring 3 digital attestations (Primary SDF + Mandated Labour Union Representative + CEO / Accounting Authority).
2. **Dispute Precedence**:
   - When a trade union representative lodges a formal labour dispute (`WspDispute`), the submission must transition to `Disputed` and subsequent sign-offs must be blocked until executive mediation is concluded.
3. **Non-Repudiation Security Seal**:
   - Every individual sign-off attestation must record an immutable SHA-256 digital security seal combining signer metadata, timestamp, and OTP challenge.

---

### 🛡️ DOFA Discretionary Grant Claim & Payment Voucher Serialization Invariant
1. **Budget Envelope Protection**:
   - Every Discretionary Grant tranche claim submission must validate remaining headroom against the parent MoA and PIP total awarded amount (`TotalAwardedAmount - TotalClaimedAmount`). Over-claims must be blocked with explicit domain exceptions.
2. **Delegation of Financial Authority (DOFA) Chains**:
   - Tier 1: Client Liaison Officer (CLO) milestone deliverable inspection (`CloVerified`).
   - Tier 2: Finance Officer banking & tax compliance approval (`Approved` for standard claims).
   - Tier 3: Mandatory Chief Financial Officer (CFO) sign-off for claims $\ge$ R500,000 (`PendingCfoApproval` -> `CfoApproved`).
3. **Serialized Payment Vouchers**:
   - Every approved claim must generate a unique, non-repudiable payment voucher in the format `PV-{yyyy}-DG-{id:D5}` prior to staging into ERP payment batches (`ErpPaymentBatchHeader` / `ErpPaymentBatchEntry`).

---

### 🛡️ ETQA Assessor 3-Year Re-registration & CPD Governance Standard
1. **Strict 3-Year Statutory Validity Cycle**:
   - In accordance with SAQA and QCTO ETQA regulations, Assessor and Moderator registration periods must strictly be 3-year cycles (`StartDate` to `StartDate.AddYears(3)`). Never set 5-year expirations.
2. **Continuous Professional Development (CPD) Threshold**:
   - Re-registration applications (`AssessorReRegistrationApplication`) require practitioners to record CPD activities (`AssessorCpdActivity`) meeting a minimum statutory threshold of $\ge 30$ points across accredited categories (`IndustryPractice`, `SetaWorkshop`, `PeerModeration`, `CourseAttendance`, `Mentorship`).
3. **Committee Ratification & Digital Security Seal**:
   - Re-registration requires an official ETQA Committee Decision Number, non-repudiation audit snapshot in `audit_logs`, and an immutable SHA-256 digital security seal certifying the Certificate of Registration.

---

### 🛡️ QuestPDF Statutory Contract & Dynamic QR Verification Invariant
1. **Dynamic Verification QR Codes**:
   - All statutory PDF documents (Tripartite Learnership Agreements, Assessor Certificates of Registration, Trade Test Certificates, WSP Outcome Letters) must render dynamic high-resolution QR codes linking to `/verify/{type}/{reference}` using `IDocumentVerificationService`.
2. **Visual Government Layout Standards**:
   - Must include Republic of South Africa and official merSETA header branding, clear tabular sections for statutory entities (Learner, Employer, SDP), and immutable cryptographic hash stamps in footers.
### 🛡️ UI Button Hierarchy, Anti-Stacking & Spacing Governance Invariant
1. **Single Primary Action Rule**: Exactly one filled primary action button per page/header. Secondary actions must be outlined/text or grouped inside a `MudMenu` (e.g. *Services*, *More Actions*).
2. **Zero Rainbow Button Stacks**: Never stack full-width chunky buttons using arbitrary semantic status colors (Success, Warning, Info) for navigation. Use clean `MudList` Action Rails with monochrome icons, sentence-case labels, brief context subtext, and chevron indicators.
3. **AppShell Padding Invariant**: Never put `Class="pa-*"`, `Class="pt-*"`, or `Class="py-*"` directly on `<MudMainContent>`. Always wrap content inside `<MudMainContent><div class="pa-4 pa-md-6"><main id="main-content">@Body</main></div></MudMainContent>`.
4. **No Double Container Nesting**: Detail views must not place `<MudContainer MaxWidth="...">` inside `MainLayout`'s `<main>` container; horizontal alignment is governed uniformly by the shell.
5. **List Status Badge Exclusion**: `EntityHeader` on List (`A1`/`T1`) pages must never render a record `StatusBadge`.

---

### 🛡️ Artisan Practical Assessment 70% Threshold & NAMB Batch Governance
1. **Compulsory Practical Threshold**:
   - In accordance with NAMB and QCTO artisan assessment regulations, candidate competency requires achieving $\ge 70\%$ overall average score across evaluated practical tasks and passing all compulsory safety/tolerance items.
2. **Batch Staging & Serial Anchoring**:
   - Competent candidates must be staged into official `NambSubmissionBatch` records anchored by a 64-character SHA-256 digital security seal before official NAMB moderation and serial certificate generation (`CERT-NAMB-{yyyy}-{id:D5}`).

---

### 🛡️ SARS Monthly SDL Ingestion & Statutory Ratio Split Standard
1. **Four-Part Statutory Levy Allocation**:
   - SARS electronic levy schedule imports must enforce statutory allocations: 20% Mandatory Grant pool, 49.5% Discretionary Grant pool, 10.5% merSETA Admin pool, and 0.5% QCTO levy.
2. **Automated Inter-SETA Boundary Detection**:
   - Ingestion must evaluate reported SIC codes against `SicCodeType.SetaCode`. Any non-merSETA (SETA != 17) levy line item must automatically create a `SarsLevyReconAudit` discrepancy and initiate an `InterSetaTransfer`.

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
   - Any compliance violation must immediately throw `SarsComplianceException`, aborting ingestion with zero database modifications and returning a line-by-line forensic diagnostic log.

---

### 🛡️ SQL Script Batch Execution & SSMS "GO" Invariant
1. **Batch Splitting Required**: When executing T-SQL scripts containing SSMS `GO` separators via ADO.NET or EF Core `ExecuteSqlRawAsync`, ALWAYS use `SqlBatchRunner.ExecuteBatchesAsync` to split commands on `^\s*GO\s*$`. Direct execution of scripts containing `GO` causes TDS protocol syntax exceptions (`Incorrect syntax near 'GO'`).
2. **Idempotency**: All DDL migration clauses must guard table and column creation with `IF NOT EXISTS` or `INFORMATION_SCHEMA` checks.

---

### 🛡️ High-Volume Primary Key Type Alignment Invariant
1. **BIGINT Primary Key Integrity**: Entities inheriting `BaseLongEntity` (`LevyFileLine`, `WspTrainingPlan`, `AuditLog`) MUST map to underlying SQL Server columns defined as `BIGINT IDENTITY(1,1)`.
2. **Type Cast Protection**: Mismatches where the physical SQL Server column is `INT` while the C# domain property is `long` cause runtime ADO.NET `InvalidCastException: Unable to cast Int32 to Int64` during query iteration.

---

### 🛡️ High-Volume Lookup Composite Indexing & Covering Standard
1. **Composite Search Indexing**: Reference and taxonomy lookups exceeding 1,000 rows (`SicCodeType`, `OfoCodeType`, `StatssaAreaCodeType`) MUST declare non-clustered composite indexes on `(Code, Name)` in both Fluent API (`entity.HasIndex(x => new { x.Code, x.Name }).HasDatabaseName(...)`) and physical DDL scripts.
2. **Covering INCLUDE Columns**: Physical DDL migrations for lookup search tables MUST include commonly filtered/displayed attributes (`INCLUDE (Active, Description, ...)`) to enable index-only scans during combobox debounced queries.
3. **Wizard Contract Parity**: Multi-step wizards registered in `DESIGN.md` §10 must expose step-level review jump links and pair with bUnit parity tests ensuring shared form field definitions match statutory domain entities without unmapped property mismatches.

---

### 🛡️ Trade Testing & DOFA Gating Standard
1. **Statutory Entry Clearance**: Trade test applications for contracted apprentices (`Section26D`) and ARPL recognition candidates (`Section28`) must enforce theoretical credit prerequisites (N2 / NCV Level 4) and employer logbook attestation ($\ge 80$ weeks / 3,200 hours verified experience).
2. **Accredited TTC Scheduling**: Assessment booking must strictly target accredited Trade Test Centres (`TrainingProviderId`) with confirmed examination dates and candidate safety tooling/PPE readiness verification.
3. **DOFA CFO Gating for DG Claims**: All Discretionary Grant tranche claims reaching or exceeding R 500,000 must automatically flag `RequiresCfoApproval = true` and route through the Tier 3 dual authorization chain prior to payment voucher serialization (`PV-{yyyy}-DG-{id:D5}`).


