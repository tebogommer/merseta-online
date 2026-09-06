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

### 🛡️ Zero-Trust Default Authorization & Anonymous Whitelisting Standard
1. **Directory-Wide Default Authorization (`Components/Pages/_Imports.razor`)**:
   - All interactive pages and child routes under `dotnet/Nsdms.Web/Components/Pages/` are protected by default via `@attribute [Authorize]` declared in `Components/Pages/_Imports.razor`.
   - Never rely on developers manually remembering to add `[Authorize]` to individual page components.
2. **Explicit Anonymous Whitelisting**:
   - Only genuinely public routes may bypass authentication (e.g. `/login`, `/register`, `/verify`, `/verify/document/{DocumentHash}`).
   - Public pages MUST explicitly declare `@attribute [Microsoft.AspNetCore.Authorization.AllowAnonymous]` and use `@layout Layout.AuthLayout` (or a dedicated public layout).
   - Any new anonymous route MUST be added to the whitelist in `RouteAuthorizationSecurityTests.cs`.
3. **Immediate Navigation Interception via `<RedirectToLogin />`**:
   - In `Routes.razor`, unauthenticated access within `<NotAuthorized>` must NEVER render private DOM, cards, or metrics. It MUST render `<RedirectToLogin />` to immediately issue an HTTP 302 or client-side redirect to `/login?returnUrl={escapedUrl}`.
4. **Continuous Automated Route Security Auditing**:
   - The test suite in `Nsdms.Tests` includes `RouteAuthorizationSecurityTests` which scans every `@page` component in `Nsdms.Web` via reflection and fails the build if any route lacks authorization or is undocumented in the public whitelist.

---

### 🛡️ Learner Management Lifecycle Statutory Governance Standard (Signed 2022 Specification)
1. **In-State vs Overall Status Distinction**:
   - The operational workflow state (`InstateStatusCode`) tracks lifecycle amendments (`Active`, `Extension Requested`, `Transfer Application`, `Termination Pending`, `Requirements Not Met`, `Withdrawal`, `Transferred`) while preserving statutory registration and SETMIS reporting integrity (`EnrolmentStatusCode == "Registered"`).
2. **Transfer Preconditions & Workplace Approval Gate (Use Case 4.7)**:
   - When transferring between employers, the target employer MUST have an active, registered `WorkplaceApproval` (`ApprovalStatusCode == "Approved"` or `ApprovalStatusCode == "Registered"`). If missing, the system MUST immediately present the exact statutory message: `"The selected option is not workplace approved please contact MerSETA"`.
   - The target employer must satisfy artisan mentor-to-apprentice ratios via `IMentorRatioPolicyEngine.EvaluatePlacementFeasibilityAsync`.
   - Mutual agreement is NOT required for a transfer to occur: If the releasing employer dissents, the system flags `DisagreementPromptedTermination = true` to prompt formal termination/conciliation.
3. **Unilateral vs Mutual Termination Governance (Use Cases 4.4, 4.5, 4.6)**:
   - **Mutual Termination (4.6)**: Approved by CRM / Regional CLO; generates statutory form `LPM-TP-010`.
   - **One-Sided Termination (4.5)**: Enforces a 14-working-day investigation SLA (`WorkplaceApprovalService.AddBusinessDays(DateTime.UtcNow, 14)`), completion of **Checklist 036** investigation report, ARPL/Transfer recommendation evaluation, and adjudication by the **ETQA Review Committee** (yielding an official Decision Letter or a "Requirements Not Met" rejection notice).
4. **Extension Addendum Gate (Use Case 4.1)**:
   - Extensions require capture of structured reason codes, original and requested expiry dates, Addendum of Agreement document attachment (`AddendumDocumentId`), and mandatory justification comments. Upon approval, updates `CompanyLearner.ExpectedCompletionDate` and generates the official statutory Addendum PDF.
5. **Double-Write & Digital Security Seal**:
   - All lifecycle transitions (extensions, transfers, lost time recalculations, checklist submissions, and committee adjudications) must record atomic snapshots in `audit_logs` and stamp generated statutory PDFs with dynamic verification references.

---

### 🛡️ MudMenu ActivatorContent Event Binding Invariant
- In MudBlazor v9+, when customizing a `<MudMenu>` trigger via `<ActivatorContent Context="menuCtx">`, the inner interactive component (e.g. `<MudButton>` or `<button>`) MUST explicitly bind `OnClick="@menuCtx.ToggleAsync"` or `@onclick="@menuCtx.ToggleAsync"`.
- Unlike basic menus with `Label="..."` or `Icon="..."` where MudBlazor renders the trigger button automatically, `<ActivatorContent>` replaces the default button and passes a `MenuContext` parameter. Omitting the `OnClick` binding leaves the inner button inert, preventing the popover from opening.

---

### 🛡️ Authentication-First User Session Architecture Invariant
- **Zero Top-Bar Persona Simulation**: Do not embed client-side simulated persona switchers in the top application bar (`MudAppBar`). The active user's identity, roles, and authorization must be strictly governed by ASP.NET Core Identity authentication cookies and claims.
- **Login-Form-Only Role Selection**: Quick test account selection chips (e.g. `SysAdmin`, `CLO Officer`, `SDF Facilitator`, `Finance`, `Committee`) reside exclusively on `/login` (`Login.razor`) to populate credentials and authenticate via `/api/auth/login`.
- **Clean Profile Avatar**: The top application bar avatar pill reflects the actual authenticated user's name and statutory role badge (`SUPERADMIN`, `CLO OFFICER`, `SDF FACILITATOR`, `FINANCE`), with session actions (sign out) isolated from simulated identity switching.

---

### 🛡️ Schema-Domain & Database Migration Synchronization Standard
- Whenever new Entity classes or persistent properties are added to `Nsdms.Domain/Entities/` or `NsdmsDbContext`:
  1. **SQL Scripts & Copy to Output**: Ensure the SQL migration script exists under `dotnet/Nsdms.Infrastructure/Data/SqlScripts/` and that `Nsdms.Infrastructure.csproj` copies `.sql` files (`<CopyToOutputDirectory>PreserveNewest</CopyToOutputDirectory>`).
  2. **Automated C# Migrator**: Create an accompanying C# `Phase*Migrator` in `Nsdms.Infrastructure/Data/` with robust multi-directory path resolution (`AppContext.BaseDirectory`, `Directory.GetCurrentDirectory()`, relative fallbacks), and register it in `Program.cs` via `RunMigrator(...)`.
  3. **Master Enterprise DDL Synchronization**: Add corresponding table creation and `ALTER TABLE ... ADD [ColumnName] ...` clauses to `V2026_08_Complete_Nsdms_Enterprise_DDL.sql`.
  4. **Pre-flight Schema Verification**: Verify table and column presence against `INFORMATION_SCHEMA.TABLES` and `INFORMATION_SCHEMA.COLUMNS` before testing UI routes. Never leave entities in `DbContext` without active migrations.

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
5. **Governance & PFMA Controls**: Adhere to financial approval delegation, Segregation of Duties (Maker-Checker), and non-repudiation audit logging for all approval gates.

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

### 🛡️ Demographic & Contact Field Nullability and Fallback Invariant
1. **Zero Null Leakage on Non-Mandatory Contact Columns**:
   - In all self-service registration wizards, employer contact intake forms, and assessor applications, non-mandatory telephone/contact fields (`PhoneNumber`, `FaxNumber`, `MiddleName`) MUST never write raw `null` to database columns that carry legacy or strict SQL `NOT NULL` constraints.
   - When optional secondary telephone (`_altPhone`) is omitted by the user, the application code MUST safely fallback to the primary cellphone (`_cellNumber?.Trim() ?? string.Empty`) or `string.Empty` rather than `null`.
2. **Schema & Migration Alignment**:
   - The primary `dbo.Person` and `history.PersonHistory` tables must define `PhoneNumber NVARCHAR(50) NULL` to allow pure mobile-first registration without requiring fixed landlines.
   - System migrators modifying column nullability on temporal tables must gracefully handle `SYSTEM_VERSIONING = OFF`, alter both the base table and `history.<Entity>History`, and restore system versioning.

---

### 🛡️ Discretionary Grant Claim & Payment Voucher Serialization Invariant
1. **Budget Envelope Protection**:
   - Every Discretionary Grant tranche claim submission must validate remaining headroom against the parent MoA and PIP total awarded amount (`TotalAwardedAmount - TotalClaimedAmount`). Over-claims must be blocked with explicit domain exceptions.
2. **Financial Approval Delegation Chains**:
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

### 🛡️ Trade Testing & Executive Approval Gating Standard
1. **Statutory Entry Clearance**: Trade test applications for contracted apprentices (`Section26D`) and ARPL recognition candidates (`Section28`) must enforce theoretical credit prerequisites (N2 / NCV Level 4) and employer logbook attestation ($\ge 80$ weeks / 3,200 hours verified experience).
2. **Accredited TTC Scheduling**: Assessment booking must strictly target accredited Trade Test Centres (`TrainingProviderId`) with confirmed examination dates and candidate safety tooling/PPE readiness verification.
3. **CFO Executive Gating for DG Claims**: All Discretionary Grant tranche claims reaching or exceeding R 500,000 must automatically flag `RequiresCfoApproval = true` and route through the Tier 3 dual authorization chain prior to payment voucher serialization (`PV-{yyyy}-DG-{id:D5}`).

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

### 🛡️ Learner Registration Dual-Channel (ATM vs Teller) Statutory Invariant
1. **Dual-Channel Coexistence**:
   - The automated self-service bulk ingestion channel ("The ATM", `/learners/bulk-register`) MUST exist alongside the manual single-registration wizard workflows ("The Teller", `LearnerAgreementRegistrationWizard.razor` and `BursaryRegistrationWizard.razor`). Automated options must NEVER replace or deprecate manual teller routes.
2. **Straight-Through Processing (STP) 6-Gate Safeguards**:
   - Automated registration through `ILearnerStpRiskEngine` is strictly limited to 100% compliant submissions matching 6 gates: active levy-paying employer, verified workplace approval with mentor ratios (`IMentorRatioPolicyEngine`), active non-expired SAQA qualification, valid RSA ID Luhn algorithm check, mandatory guardian details for minors under 18, and execution date within 30 working days.
3. **Graceful Exception Routing**:
   - Any row failing any of the 6 STP criteria must NOT terminate the batch; instead, it is safely queued into `VerificationPending` for officer maker-checker review and manual condonation evaluation.
4. **Digital Security Seal & Double-Write Audit Trail**:
   - Ingested bulk batches must calculate an immutable SHA-256 digital security seal over payload contents. All STP auto-approvals must perform atomic double-writes into `audit_logs` with actor `SYSTEM_STP_GATEKEEPER`.

---

### 🛡️ Summative Assessment, External Moderation & Certification Statutory Governance Standard (Signed 2022 Specification MerSeta\NSDMS\LMS\ASM\12)
1. **Segregation of Duties (Maker-Checker)**:
   - An accredited assessor cannot internally moderate the same learner's assessment results (`AssessorPersonId != ModeratorPersonId`). Enforce validation across both single results and batch processing.
2. **50% Earned Credit Gate for Funded Employers**:
   - For merSETA-funded learners, Progress assessments require at least 50% of mandatory core/fundamental credits to have been earned (`totalEarned >= totalCreditsRequired / 2`) before assessment results can be committed to the Holding Room. Non-funded employers are statutorily exempt.
3. **External Moderation Upheld Certificate Stamping**:
   - When an external moderation batch is upheld by merSETA QA, `LearnerCertificate.IssueDate` is statutorily stamped with the exact external moderation approval date (`DateOfModeration`).
4. **Statutory 12-Digit Certificate Numbering Formula**:
   - Formula: Fixed prefix `17` (MerSETA statutory code) + Middle 4 digits (indices 5..8 of 13-digit RSA ID, or `MMYY` derived from `ddMMyyyy` foreign birth date) + 6 sequential/padded digits (`D6`). Total length is always exactly 12 digits.
5. **VACS Rejection Taxonomy & Controlled Documents**:
   - External moderation rejections must categorize evidentiary defects using the VACS framework: Validity, Authenticity, Currency, and Sufficiency, routing rejected batches for structured remediation.
   - All statutory PDF instruments (ETQ-FM-005, ETQ-TP-043, National Qualification Certificate, ETQ-LT-012 Transmittal Letter) must render dynamic high-resolution QR verification seals and perform audited double-writes into `audit_logs`.
6. **Zero Raw Database Access in Assessment UI**:
   - Razor components managing Summative Assessments, Batching, QA External Moderation, and Certificate Printing MUST NEVER inject `IDbContextFactory` or `DbContext` directly. All entity lookups, scheduling, adjudication, batch submissions, and document triggers must pass through `ISummativeAssessmentAndModerationService` and `IPdfDocumentService`.
7. **Consolidated Batch Certificate Printing & Stitching**:
   - When printing batch qualification certificates, the system must generate a single consolidated multi-page A4 landscape PDF document via `IPdfDocumentService.GenerateBatchConsolidatedCertificatesPdfAsync(batchId)` rather than single-record downloads.

---

### 🛡️ Test DbContext Factory & In-Memory Entity Reload Invariant
- In integration and unit tests using `TestDbContextFactory`, application services execute mutations within their own scoped DbContext instances (`using var db = await _contextFactory.CreateDbContextAsync();`).
- When asserting post-mutation database state in tests, NEVER query entities from the test setup's original `db` instance (even with `.AsNoTracking()`), because the EF Core InMemory provider's local change tracker identity map can retain pre-mutation entity snapshots.
- Always retrieve post-mutation assertions using a fresh context: `using var verifyDb = (NsdmsDbContext)factory.CreateDbContext(); await verifyDb.Entity.FindAsync(...)`.

---

### 🛡️ Unmapped Entity Convenience Properties Invariant
- When adding backward-compatibility aliases or computed properties to domain entities in `Nsdms.Domain/Entities/`, decorate them with `[NotMapped]` AND explicitly configure `entity.Ignore(e => e.PropertyName)` in `NsdmsDbContext.cs` inside Fluent API.
- Fluent API overrides CLR attributes. Calling `entity.Property(...)` on an unmapped property causes EF Core to query non-existent columns, leading to SQL Server runtime errors (`Invalid column name '...'`).

---

### 🛡️ Skills Development Provider (SDP) Delivery Sites vs Campuses Nomenclature Standard
1. **Statutory Terminology Alignment**:
   - In accordance with merSETA ETQA statutory governance, physical locations where Skills Development Providers (SDPs) conduct accredited training deliveries and assessments MUST be designated as **"Delivery Sites"** or **"Sites"** (e.g. *Main Site*, *Secondary Delivery Site*, *Site Code*, *Site Inspection*).
   - Never use the term "Campus" or "Campuses" in user-facing labels, table headers, dialogs, button texts, toast messages, or navigation items.
2. **Backward-Compatible Domain & Service Aliasing**:
   - Underlying entity models and database tables may preserve physical storage columns (`TrainingProviderCampus`) while exposing `SiteName`, `SiteCode`, `SiteContactPersonName`, and `SiteContactPhone` via `[NotMapped]` aliases (with explicit `entity.Ignore(...)` in `NsdmsDbContext.cs`).
   - Application service contracts must provide `ISdpSiteService` / `SdpSiteService` as the primary interface, with `ISdpCampusService` retained as an alias for non-breaking backward compatibility.

---

### 🛡️ Dynamic Business Rule Engine & Configurable Thresholds Standard
1. **Separation of Hardcoded Logic vs Configurable Policy**:
   - High-velocity policies, statutory gatekeeper rules, and risk evaluation workflows (e.g. Learner Straight-Through Processing `LearnerStpRiskEngine`, CFO financial approval thresholds, Assessor minimum practice years) must NOT be hardcoded as immutable C# constants.
   - Dynamic numeric thresholds and operational settings must be resolved dynamically through `ISystemConfigurationService` (`lookup.system_config`).
   - Multi-condition decision pipelines and statutory criteria must be modeled through `IBusinessRuleEngineService` using `Microsoft.RulesEngine` and persisted in `BusinessRuleWorkflow` and `BusinessRule` entities.
2. **Compiled Engine Cache & Hot-Path Performance**:
   - `IBusinessRuleEngineService` implementations must cache compiled `RulesEngine.RulesEngine` instances across executions using a thread-safe `ConcurrentDictionary<string, RulesEngine.RulesEngine>` keyed by workflow name and revision timestamp.
   - High-throughput batch operations (e.g. bulk learner agreement ingestion) must never re-parse JSON or recompile expressions per record.
3. **Statutory Fallback Invariant**:
   - If the dynamic rule engine, database, or workflow definition is unavailable, services MUST execute a safe, compiled statutory fallback (e.g. in `LearnerStpRiskEngine`, verifying active employer, mentor ratios, valid SAQA qualification, and guardian consent for minors) to guarantee 100% operational continuity.
4. **Interactive Sandbox & Non-Destructive Testing**:
   - The UI under `/system-admin/rules/{id}` must provide an interactive evaluation sandbox enabling administrators and compliance officers to test expressions and JSON payloads without mutating live transactional records.
5. **C# XML Documentation Entity Escaping**:
   - In C# XML documentation comments (`/// <summary>`), comparison operators `<` and `>` must always be escaped as `&lt;` and `&gt;` to prevent XML parsing syntax errors under `GenerateDocumentationFile = true`.

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

### 🛡️ ASP.NET Core Identity & User Management Statutory Governance Standard
1. **Separation of Authentication (`ApplicationUser`) vs Demographics (`Person`)**:
   - `ApplicationUser` handles credentials, password hashes, security stamps, lockout tracking, and statutory role claims.
   - `Person` manages statutory personal identity, RSA ID numbers, demographic equity profiles, disability ratings, and contact info.
   - Users and Persons are decoupled; an optional foreign key link (`PersonId`) links an account to their demographic record without mandatory 1-to-1 co-creation.
2. **Password Complexity & Account Lockout Invariant**:
   - Minimum 8 characters, at least 1 uppercase letter, 1 lowercase letter, 1 digit, and 1 non-alphanumeric character.
   - Failed logon attempts increment `AccessFailedCount`. 5 consecutive failed attempts trigger an automated 15-minute lockout (`LockoutEnd = UtcNow.AddMinutes(15)`).
   - Successful logins reset `AccessFailedCount = 0` and clear `LockoutEnd`. Administrators can unlock accounts on demand via `UnlockUserAsync`.
3. **Cookie-Based Authentication Endpoints**:
   - Dedicated minimal API endpoints `POST /api/auth/login` and `GET/POST /api/auth/logout` manage the `NSDMS_AUTH_TICKET` cookie with `HttpOnly`, `SameSiteMode.Lax`, and `SecurePolicy = SameAsRequest`.
   - `NsdmsAuthenticationStateProvider` reads cookie claims when present, while preserving seamless fallback for local persona testing.
4. **Administrative User Management (`/admin/users`)**:
   - Admin users have full CRUD capabilities: User creation, activation/deactivation, password resets, statutory role assignments, and demographic linking.
   - User creation and modifications record atomic snapshots in `audit_logs`.
5. **Idempotent Default Account Seeding**:
   - Default statutory accounts (`sysadmin@merseta.org.za`, `clo.officer@merseta.org.za`, `sdf.employer@toyota.co.za`, `finance.officer@merseta.org.za`, `review.committee@merseta.org.za`) must be seeded idempotently on application startup via `IdentityService.SeedDefaultUsersAsync()`.

---

### 🛡️ Public Self-Service Registration, Email Activation & Non-Admin Tenant Isolation Standard
1. **Standard "User" Role Invariant for Self-Registration**:
   - Public self-service registrants (`/register`, `SelfServiceRegistration.razor`) MUST strictly receive the standard `"User"` role.
   - Self-service registrants must NEVER be assigned privileged or organizational roles (`SDF`, `SDP`, `Assessor`, `Moderator`, `Admin`) at registration time.
   - The standard `"User"` role grants access exclusively to the Applicant Portal (`/`), with permissions solely to submit applications for stakeholder appointments (Apply to be an SDF, SDP Contact, Organisation Contact, Assessor, or Moderator).
2. **Email Confirmation Account Activation Gate**:
   - Accounts created via self-service MUST have `EmailConfirmed = false`.
   - `ValidateCredentialsExtendedAsync` and `/api/auth/login` MUST reject unconfirmed accounts with `IsEmailUnconfirmed = true` and prevent login until activated.
   - Account activation occurs exclusively through cryptographic token verification at `/confirm-email?userId={id}&token={token}` (`ConfirmEmailAsync`).
   - Internal administrative accounts and system seeds are created pre-confirmed (`EmailConfirmed = true`).
3. **Strict Multi-Tenant Isolation for Non-Admin Stakeholders**:
   - Non-admin users (including SDF, SDP, and Employer contacts) must NEVER possess global cross-tenant visibility.
   - `DefaultTenantProvider.IsAdmin` must default to `false` (zero-trust architecture). Global admin status is granted exclusively to verified administrator roles (`SuperAdmin`, `Admin`, `SystemAdministrator`).
   - In `NsdmsDbContext` query filters and `OrganisationService`, non-admin users only query organisations matching their explicit context (`CurrentOrganisationId != null && o.Id == CurrentOrganisationId`). If unlinked, queries must safely return zero organisations (`Where(o => false)`).
   - SDF users have visibility strictly limited to their appointed employer organisation.

---

### 🛡️ User Identity, Role Resolution & UI Session State Invariant
1. **Dynamic User Subtitle & Neutral Initial State**:
   - In `MainLayout.razor` and all top-level layout shells, user identity fields (`_currentUsername`, `_currentUserDisplayName`, `_currentUserEmail`, `_currentUserRoleBadge`, `_currentUserSubtitle`, `_currentUserRoles`) MUST be initialized to neutral/empty values (`string.Empty`, `new()`).
   - Never initialize layout session state to hardcoded SuperAdmin strings or roles.
   - `_currentUserSubtitle` MUST be computed dynamically in `UpdateUserState` based on the active user's roles (e.g., `SDF` -> "Skills Development Facilitator • Registered Employer Representative"; `CLO` -> "Client Liaison Officer • merSETA Regional Operations"; `Finance` -> "Finance Officer • Grants & Financial Control").
2. **Username vs Display Name Claim Disambiguation**:
   - `_currentUsername` MUST be resolved from `user.FindFirst(ClaimTypes.Name)?.Value ?? user.Identity?.Name ?? _currentUserEmail` (the login username or email).
   - Never set `_currentUsername = _currentUserDisplayName` (which contains GivenName / Full Name like "man user"). Doing so causes downstream services (`CaslAbilityService.GetUserContextByUsernameAsync`) to fail database user lookup, resulting in empty roles and unintended fallback to default roles.
3. **Role-Aware Avatar Dropdown Navigation**:
   - Navigation links within the user profile avatar popover menu MUST be strictly conditioned on the authenticated user's role.
   - Internal administration links (Audit trail, User accounts, Data dictionary) must only render for verified system administrators (`_isInternalAdmin`).
   - Stakeholders (such as SDFs) must only see relevant operational links (My organisation, Workplace approvals, Learner registrations, DG, WSP), preventing conflicting role navigation.

---

### 🛡️ Shared Wizard Component Resiliency & Endpoint Safety Standard
1. **Unmatched Attribute Resiliency (`CaptureUnmatchedValues = true`)**:
   - Shared wizard components (such as `WizardReviewStep.razor`) used across diverse statutory modules (Discretionary Grants, Trade Tests, SDP Accreditation, Mandatory Grants/WSP) MUST declare `[Parameter(CaptureUnmatchedValues = true)] public Dictionary<string, object>? AdditionalAttributes { get; set; }`.
   - This prevents Blazor's runtime component renderer from throwing unhandled reflection exceptions (`InvalidOperationException: Object of type '...' does not have a property matching the name '...'`) if any parent wizard passes supplementary attributes or template aliases.
   - Dual parameter aliases (such as `Heading` / `Title` and `Subheading` / `Description`) must be declared as public auto-properties and coalesce safely in the markup (`Heading ?? Title`).
2. **Statutory Document Download Endpoint Safety**:
   - Statutory PDF and artifact minimal API endpoints in `Program.cs` (`/api/documents/{entity}/{id}/pdf`) MUST wrap document generation service calls in `try ... catch (KeyNotFoundException)` returning `Results.NotFound(new { message = ... })` rather than allowing unhandled 500 exceptions to bubble up when non-existent entity IDs are requested.

---

### 🛡️ EF Core Include on [NotMapped] Aliases & Minimal API Tenant Context Invariant
1. **Never Call `.Include()` on `[NotMapped]` Alias Properties**:
   - In EF Core LINQ queries, `.Include(x => x.Alias)` MUST ONLY target physically mapped navigation properties.
   - If an entity exposes convenience properties or statutory aliases marked with `[NotMapped]` (e.g. `public ICollection<CertificateDistributionEvent> DistributionEvents => CertificateDistributions;`), calling `.Include(t => t.DistributionEvents)` will throw `InvalidOperationException: The expression '...' is invalid inside an 'Include' operation, since it does not represent a property access`.
   - Always invoke `.Include()` on the underlying mapped collection property (`.Include(t => t.CertificateDistributions)`).
2. **Minimal API Endpoints and ITenantProvider Identity Resolution**:
   - In ASP.NET Core minimal APIs and background document generation services (`/api/documents/...`), the ambient scoped `ITenantProvider` must correctly parse authentication claims from `HttpContext.User`.
   - Ensure `DefaultTenantProvider` marks `IsAdmin = true` when the user has `Role == "SuperAdmin"` or `"Admin"`. In system document download services (`IQuestPdfDocumentService`), append `.IgnoreQueryFilters()` when fetching approved statutory records (`GrantMoa`, `WspSubmission`, `LearnerTradeTestApplication`, `MandatoryGrantDisbursement`) to prevent multi-tenant query filters from suppressing valid documents for cross-tenant download requests.

