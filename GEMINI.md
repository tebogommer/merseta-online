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

### 🛡️ MudMenu ActivatorContent Event Binding Invariant
- In MudBlazor v9+, when customizing a `<MudMenu>` trigger via `<ActivatorContent Context="menuCtx">`, the inner interactive component (e.g. `<MudButton>` or `<button>`) MUST explicitly bind `OnClick="@menuCtx.ToggleAsync"` or `@onclick="@menuCtx.ToggleAsync"`.
- Unlike basic menus with `Label="..."` or `Icon="..."` where MudBlazor renders the trigger button automatically, `<ActivatorContent>` replaces the default button and passes a `MenuContext` parameter. Omitting the `OnClick` binding leaves the inner button inert, preventing the popover from opening.

---

### 🛡️ Dedicated Top-Bar Persona Switcher Architecture Invariant
- The statutory workspace persona switcher (`PersonaSwitcher.razor`) resides on the top application bar (`MudAppBar`), positioned immediately preceding the user profile avatar menu.
- It displays the active persona in a clean, themed pill button with role-specific icon, responsive label (`GetShortPersonaLabel` / `GetCompactPersonaLabel`), and dropdown chevron.
- Selecting a persona updates `_activePersona`, fires an `ISnackbar` confirmation toast, and dynamically filters the 7 statutory navigation pillars in `NavMenu` via `INavigationMenuService`.
- No nested persona switchers shall be embedded within the avatar profile popover; user account session actions (dashboard links, audit logs, sign out) remain strictly isolated from workspace persona filters.

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

---

### 🛡️ Dynamics GP Transactional Outbox & Priority Execution Governance
1. **Mandatory Outbox Routing**:
   - All external Microsoft Dynamics GP web service mutations (Vendor Creation/Sync, Bank Details Verification, DG Tranche Payments, MG Rebate Disbursements) MUST route through `IErpOutboxQueueService.EnqueueAsync`. Direct, un-staged synchronous HTTP/SOAP calls to GP web services are strictly prohibited.
2. **Execution Priority Order**:
   - Outbox processing must enforce business priority: Priority 1 (`OrganisationVendorSync`, `BankingDetailsUpdate`) MUST always execute before Priority 2 (`DiscretionaryGrantPayment`, `MandatoryGrantPayment`) to prevent `EntityNotFoundException` on missing vendors or outdated bank details.
3. **Outage Resilience & Circuit Breaker**:
   - Before draining outbox batches, `CheckGpHealthAsync` must verify GP availability. Any connection failure mid-batch must immediately abort remaining messages in the batch, pause the queue, and record exponential backoff on retryable failures.
4. **Audit Double-Write**:
   - Every message state transition (`Pending` -> `InFlight` -> `Delivered` / `FailedRetryable` / `DeadLetter`) must perform an audited change log write to `audit_logs`.

---

### 🛡️ Non-Levy Statutory N-Number Generation Governance
1. **4-Tier Zero-Collision Sequence Allocation**:
   - Non-levy organisations (TVET colleges, public universities, NGOs, CBOs, trade unions, exempt SMEs) must be allocated unique 10-character statutory identifiers (`N` + 9 digits: `^[LN]\d{9}$`).
   - Generation MUST utilize `INonLevyNumberGeneratorService` powered by the dedicated SQL Server sequence `[dbo].[seq_NonLevyOrganisationNumber]` with high-water mark dynamic restart seeding (`MAX(TRY_CAST(SUBSTRING(SdlNumber, 2, 9) AS INT)) + 1`).
   - In-memory concurrency and dirty-data collision safeguards must implement a bounded skip-and-retry loop (up to 5 attempts) against `[dbo].[Organisation]` with unique filtered index `[UQ_Organisation_SdlNumber]`.
2. **Audit Double-Write**:
   - All statutory N-number allocations must record an immutable change snapshot in `audit_logs`.

---

### 🛡️ merSETA Chamber & Dynamics GP Vendor Class Derivation Governance
1. **Automated SIC Code & Organisation Type Derivation**:
   - merSETA Chambers (`AUTO`, `METAL`, `MOTOR`, `NEW_TYRE`, `PLASTICS`, `SETA`) and Dynamics GP Vendor Classes (`AUTO`, `METAL`, `MOTOR`, `NEW TYRE`, `PLASTICS`, `SETA`) must be derived via `IChamberDerivationService`.
   - Derivation precedence:
     - Tier 1: Manual Chamber Override (`IsManualChamberOverride = true` with mandatory Board/Executive justification).
     - Tier 2: Special legal constitution types (TVET colleges, public universities, NGOs, government entities $\rightarrow$ `SETA`).
     - Tier 3: 5-digit statutory SIC code lookup against `[lookup].[sic_code_type]`.
2. **Organisation-Level Missing Chamber Flag & Downstream Blockers**:
   - If an organisation lacks a valid Chamber mapping, `HasMissingChamberMapping` MUST be set to `true` on `[dbo].[Organisation]`.
    - When `HasMissingChamberMapping == true`:
      - Discretionary Grant application creation and submission (`GrantService.CreateApplicationAsync`) must be BLOCKED with `InvalidOperationException`.
      - Dynamics GP vendor synchronization (`ErpIntegrationService.EnqueueVendorSyncAsync`) must be BLOCKED.
      - Mandatory Grant WSP/ATR submissions and MoA contracting must be BLOCKED until the Chamber mapping is resolved.

---

### 🛡️ Dependency Injection Dual-Registration Invariant
1. **Concrete & Interface Dual Resolution**:
   - When application or infrastructure services inject a concrete type directly in their constructors (e.g. `AuditService` rather than `IAuditService`), `Program.cs` must register both the concrete implementation and the interface:
     ```csharp
     builder.Services.AddScoped<AuditService>();
     builder.Services.AddScoped<IAuditService>(sp => sp.GetRequiredService<AuditService>());
     ```
   - This ensures that ASP.NET Core service validation (`ValidateOnBuild` and `ValidateScopes`) succeeds in Development mode.

---

### 🛡️ Shared Component Parameter Compatibility & Alias Invariant
1. **Semantic Parameter Aliasing**:
   - Shared components in `Components/Shared/` (such as `EmptyState.razor`) that accept semantic titles or headers must provide property aliases (e.g., `[Parameter] public string? Title { get => Message; set => Message = value; }`) when both `Title` and `Message` are used interchangeably across consuming pages.
   - Blazor components throw a runtime `InvalidOperationException` when an unknown incoming parameter is passed. Ensure shared components define aliases for common naming variations.

---

### 🛡️ Dynamic Persona Switcher & Identity Synchronization Standard
1. **Named Persona Toggle & Compact Representation**:
   - The top application bar persona toggle pill button (`PersonaSwitcher.razor`) MUST display the individual's full person name and role in sentence case (e.g. `System Administrator`, `Sipho Khumalo (Mentor)`, `Thabo Molefe (CLO)`, `Nalini Moodley (SDF)`), using compact representations on mobile/compact viewports (e.g. `Sipho K. (Mentor)`).
2. **Workplace Approval & Enterprise Domain Categorization**:
   - The persona switcher popover MUST cleanly separate domain-specific actors (such as **Workplace Approval Personas**: Artisan Mentor, SETA CLO Site Inspector, Designated Employer Contact SDF, Trade Assessor, Review Committee Chair) from **Enterprise Governance Personas** (System Administrator, Finance Specialist, SDP Principal, Legal Counsel, Compliance Auditor).
   - Each persona entry must render person name, initials avatar, statutory role badge, host organisation, and statutory reference (e.g. `WPA-2025-TOYOTA-PROS`, `ART-1999-88741`).
3. **Simulated Logout / Login Synchronization**:
   - Selecting a persona must invoke `NsdmsAuthenticationStateProvider.SetUser(email, displayName, roles)` to update Blazor's cascading authentication state without a full-page reload.
   - It must synchronize the top-right user profile avatar pill (`_currentUserInitials`, `_currentUserDisplayName`, `_currentUserRoleBadge`) and avatar dropdown card with active person credentials (`Signed in as [Email]`, organisation, and a direct "Log out (Reset to Admin)" button).
   - It must trigger an `ISnackbar` toast confirmation and dynamically filter the 7 statutory navigation pillars in `NavMenu` via `INavigationMenuService`.

---

### 🛡️ Organisation Context Scoping & Multi-Company Affiliation Standard
1. **Multi-Company Affiliation Resolution**:
   - Individuals can represent one or multiple organisations across diverse roles (e.g. Primary/Secondary SDF, Owner, Director, HR Manager, Training Committee Member).
   - Affiliations MUST be resolved dynamically by querying both `OrganisationContact` and `SdfCompany` for the active `PersonId`.
2. **Persistent Top-Bar Context Switcher Pill**:
   - The top application bar (`MudAppBar`) MUST feature an `OrganisationContextSwitcher` pill docked adjacent to the `PersonaSwitcher`.
   - In Admin / merSETA Internal mode (`CLO`, `Finance`, `Executive`, `SuperAdmin`), it displays `All Organisations` (Global View) with an `Admin Bypass` indicator and live search across the entire registry.
   - For employer/SDF personas, it displays the active company name and SDL reference (e.g. `Toyota South Africa...`), restricting options strictly to their affiliated entities.
3. **Reactive Circuit Synchronization & Scoping**:
   - Switching working organisations updates `ITenantProvider.SetTenant(orgId, name, sdl, isAdmin)` and fires `OnTenantChanged`.
   - All downstream domain queries in EF Core (`NsdmsDbContext`) automatically apply multi-tenancy filtering to `CurrentOrganisationId` when not in Admin mode.
   - If a multi-organisation user has no context selected, prompt them with `SelectOrganisationDialog.razor` or alert badge styling.

---

### 🛡️ Multi-Tenancy Scoped Factory & Entity Query Filter Invariant
1. **Primary Entity Query Filters**: Every entity scoped to an organisation (`Organisation`, `OrganisationSite`, `OrganisationContact`, `WspSubmission`, `GrantApplication`, `Visit`) MUST declare `entity.HasQueryFilter(e => _tenantProvider.IsAdmin || _tenantProvider.CurrentOrganisationId == null || e.OrganisationId == _tenantProvider.CurrentOrganisationId);` in `NsdmsDbContext`. For `Organisation`, the filter evaluates `o.Id == _tenantProvider.CurrentOrganisationId`.
2. **Scoped Factory Registration**: `INsdmsDbContextFactory` MUST be registered as `Scoped` in `Program.cs` and inject scoped `ITenantProvider`. `NsdmsDbContext` must have `[ActivatorUtilitiesConstructor]` on its constructor accepting `ITenantProvider? tenantProvider`.
3. **Application Service Defense**: Application service listing methods (`GetAllAsync`, `GetPagedAsync`, `GetByIdAsync`) must inject `ITenantProvider` and enforce `CurrentOrganisationId` constraints when `!_tenantProvider.IsAdmin`.
4. **UI Tenancy Subscription**: Any Blazor page rendering tenant-scoped records must inject `ITenantProvider`, implement `IDisposable`, subscribe to `TenantProvider.OnTenantChanged`, and re-query data on tenant context mutations.
5. **Cross-Tenant Aggregation**: Services dedicated to discovering a user's multi-company affiliations (`OrganisationContextService`) or background bulk reconciliation engines must explicitly append `.IgnoreQueryFilters()`.

---

### 🛡️ Statutory Learner Registration & Minor Protection Invariant
1. **Minor Co-Signatory Requirement**:
   - For all learner registration workflows (Agreements, ARPL, Trade Tests), any applicant under 18 years of age at the time of agreement execution MUST capture a linked `PersonGuardian` record. Bypassing guardian details for minors violates the Skills Development Act.
2. **30-Working-Day Submission Deadline**:
   - Applications must be submitted within 30 working days of `LearnerSignatureDate`. Working days must exclude Saturdays, Sundays, and gazetted South African public holidays via `CompanyLearnerDomainValidator.CalculateWorkingDays`. Submissions exceeding 30 working days require formal condonation.
3. **Engineering Candidacy Exception**:
   - Engineering Candidacy programmes do not require an active SAQA Qualification ID, but MANDATE a verified Professional Council Registration Number (e.g. ECSA Candidate Engineer Reference).
4. **SETMIS Anti-Placeholder Enforcement**:
   - Never accept placeholder strings (`%UNKNOWN%`, `AS ABOVE`, `N/A`, `SOOS BO`, `TEST`) in person names, street addresses, or postal codes. Mobile numbers must strictly conform to 10 digits starting with `0` (`^0\d{9}$`).


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
