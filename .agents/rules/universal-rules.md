---
name: universal-rules
version: 1.0.0
priority: P0
trigger: always_on
---

# Universal Rules (TIER 0) - AG Kit

> Always-active rules that apply to every request, regardless of domain.

---

## 🌐 Language Handling

When user's prompt is NOT in English:

1. **Internally translate** for better comprehension
2. **Respond in user's language** - match their communication
3. **Code comments/variables** remain in English

---

## 🧹 Clean Code (Global Mandatory)

**ALL code MUST follow `@[skills/clean-code]` rules. No exceptions.**

- **Code**: Concise, direct, no over-engineering. Self-documenting.
- **Testing**: Mandatory. Pyramid (Unit > Int > E2E) + AAA Pattern.
- **Performance**: Measure first. Adhere to current Core Web Vitals standards.
---

## ⚙️ Dynamic Configuration & Feature Flags Governance
1. **Zero Hardcoding Invariant**: No business parameter, threshold, storage path, or external integration endpoint may be hardcoded. Always use `ISystemConfigurationService` with cascading database overrides.
2. **Integrations Off-By-Default**: All external integrations (Dynamics GP, Sage, Live DHET SFTP, Live SARS FTP, SMS OTP, Azure Blob) MUST default to `IsEnabled = false`. Workflows must cleanly execute in mock simulation mode when disabled.
3. **Double-Write Audit Trail**: All system configuration updates, feature toggles, and document operations must perform atomic double-writes into `audit_logs`.

---

## 🎨 MudBlazor Layout & Sticky Top Bar Invariant
1. **No Utility Classes on `MudMainContent`**: Never add `Class="pa-*"` or `Class="pt-*"` directly to `<MudMainContent>`. MudBlazor utility classes apply `!important`, which cancels the computed `padding-top: var(--mud-appbar-height)` (64px) and causes the header to overlap page content. Always wrap `@Body` inside `<MudMainContent><div class="pa-4">@Body</div></MudMainContent>`.
2. **Sticky Sub-Header Offsets**: Sticky top bars on Master-Detail pages must always dock below the 64px `MudAppBar` using `top: var(--mud-appbar-height, 64px) !important;` (or `.sticky-top-header`).

---

## 🛡️ SQL Server Temporal Tables & History Schema Governance
1. **Dedicated History Schema**: All system-versioned temporal history tables must reside in the `history` schema (`history.<Entity>History`).
2. **EF Core Convention**: All persistent domain entities in `NsdmsDbContext` are mapped as temporal tables via `modelBuilder.ApplyTemporalTables()`.
3. **Exclusions**: Append-only tables (`AuditLog`), workflow transient leases (`WorkflowTaskLease`), and static lookup tables (`lookup.*`) are non-temporal.
4. **Point-in-Time Queries**: Use EF Core's native temporal query extensions (`.TemporalAsOf(dateTime)`, `.TemporalBetween(start, end)`, `.TemporalAll()`) when retrieving historical snapshots.

---

## 🛡️ Schema-Domain & Database Migration Synchronization Standard
1. Whenever new Entity classes or persistent properties are added to `Nsdms.Domain/Entities/` or `NsdmsDbContext`:
   - Ensure the SQL migration script exists under `dotnet/Nsdms.Infrastructure/Data/SqlScripts/` and that `Nsdms.Infrastructure.csproj` copies `.sql` files (`<CopyToOutputDirectory>PreserveNewest</CopyToOutputDirectory>`).
   - Create an accompanying C# `Phase*Migrator` in `Nsdms.Infrastructure/Data/` with robust multi-directory path resolution and register it in `Program.cs` via `RunMigrator(...)`.
   - Add corresponding table creation and `ALTER TABLE ... ADD [ColumnName] ...` clauses to the master DDL script (`V2026_08_Complete_Nsdms_Enterprise_DDL.sql`).
   - Verify table and column presence against `INFORMATION_SCHEMA.TABLES` and `INFORMATION_SCHEMA.COLUMNS` before testing UI routes. Never leave entities in `DbContext` without active migrations.
2. **Batch Compilation & DDL-DML Decoupling**:
   - In SQL Server, DDL alterations (`ALTER TABLE ... ADD ...`) and subsequent DML statements referencing those new columns/tables (`MERGE`, `INSERT`, `UPDATE`) must NEVER be concatenated into a single execution batch without batch boundaries. Doing so triggers compile-time parser errors (`Invalid column name`) that abort the entire batch before any `ALTER TABLE` statement executes.
   - All multi-statement migrator scripts MUST use `SqlBatchRunner.ExecuteBatchesAsync` with explicit `GO` delimiters between DDL changes and DML/seed operations.
3. **Foreign Key Type and Length Invariant (Msg 1753)**:
   - Referencing foreign key columns (e.g. `InterventionTypeCode NVARCHAR(50)`) and referenced primary key columns (e.g. `lookup.InterventionType.Code`) MUST have identical data types and maximum lengths. Always verify lookup key lengths before creating foreign keys.

---

## 🛡️ Artisan Mentor-to-Apprentice Ratio Governance Standard
1. **Cascading Evaluation Precedence**:
   When validating learner enrollments, workplace capacity, or artisan quotas, ALWAYS resolve ratios via `IMentorRatioPolicyEngine.EvaluateWorkplaceApprovalCapacityAsync(id)`. Never hardcode a 1:4 ratio. The engine evaluates policies in the following strict order:
   - **Tier 1 (Mentor Override)**: `WorkplaceApprovalMentor.MaxLearnerCapacity` or `IsRatioExempt = true`.
   - **Tier 2 (Workplace Approval)**: `WorkplaceApproval.CustomTradeRatio` or `IsRatioEnforced = false`.
   - **Tier 3 (Organisation Exemption)**: `Organisation.IsMentorRatioEnforced = false` or `Organisation.CustomMentorRatioCap`.
   - **Tier 4 (Trade Policy)**: `TradeMentorRatioPolicy.StandardRatio` matching the trade/qualification.
   - **Tier 5 (Global Toggle)**: System configuration `WorkplaceApproval.EnforceMentorRatios`.
2. **Audit Double-Write**: All policy overrides and trade policy mutations must record snapshots in `audit_logs`.

---

## 🛡️ UI Plain-Language & Anti-Jargon Governance Invariant
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

### 🛡️ Zero-Hardcoding & Dynamic Configuration Invariant
1. **Dynamic Business Rules Resolution**:
   - Never embed literal values for statutory SLAs, attempt limits, validity tenures, monetary approval thresholds, levy split percentages, or mentor ratios directly inside application service classes or UI components.
   - Always inject `ISystemConfigurationService` and resolve parameters using:
     `await _configService.GetValueAsync<T>("Category:KeyName", fallbackConstant)`
   - Constant literals may only be used as fallback defaults passed to `GetValueAsync<T>`.
2. **Dynamic Tenancy & Identity Resolution**:
   - Never write fallback logic containing hardcoded organisation names (e.g., `"toyota"`), SDL numbers (e.g., `"700100200"`), or usernames (e.g., `"Admin"`, `"sysadmin@merseta.org.za"`).
   - Tenancy and user identities must resolve strictly from database relations (`OrganisationContact`, `SdfCompany`) and authenticated claims (`IHttpContextAccessor` / `AuthenticationStateProvider`).
3. **MudBlazor Design Token Invariant**:
   - Never write hardcoded hex color codes (`#cc9c47`) or inline RGB strings in `.razor` markup.
   - All colors and theme accents must reference MudBlazor design variables (e.g., `var(--mud-palette-primary)` or `Color.Primary`).

---

### 🛡️ Anti-Synthetic Data & Zero-Hardcoding Architecture Invariant
1. **Zero Synthetic Dummy Entity Creation**:
   - Application services and UI components MUST NEVER synthesize placeholder records (e.g. dummy `Person` with `"Primary Contact"`, `"contact@employer.co.za"`, or `"011-555-0100"`) when a foreign key is missing.
   - Missing required relations must fail fast with descriptive domain validation exceptions requiring the user to select or link a verified record.
2. **Zero Hardcoded Scheme/Financial Years**:
   - Never write literal `"2026"` or `"2026/2027"` in entity property initializers, document templates, or UI `<MudSelectItem>` elements.
   - Dynamic year selectors must compute ranges relative to `DateTime.UtcNow.Year` and resolve the active scheme year via `ISystemConfigurationService.GetValueAsync("Governance:CurrentSchemeYear", DateTime.UtcNow.Year.ToString())`.
3. **Zero Hardcoded URLs & Endpoints**:
   - Verification URLs, QR code links, and external ERP integration endpoints must never contain static domain names (e.g. `"https://verify.merseta.org.za"` or `"https://erp.merseta.org.za"`).
   - All external/public URLs must be constructed from configurable base URLs resolved from `ISystemConfigurationService` or `IConfiguration`.
4. **Zero Inline Timeouts & File Caps**:
   - File upload limits (`maxAllowedSize`), cache TTLs (`MemoryCacheEntryOptions`), and HTTP client timeouts must reference centralized system configuration keys with statutory constants strictly as fallback defaults.

---

### 🛡️ Enterprise Document Template Studio & Single-Active Version Governance Standard
1. **Single Active Version Invariant**:
   - For any statutory document template family (`TemplateCode`), exactly one version can have `IsActive = true` and `ApprovalStatus = "Approved"`.
   - Activating a new revision (e.g. v1.1.0) must atomically transition the prior active version (v1.0.0) to `Superseded` and `IsActive = false` within an audited transaction.
   - Enforced at the database tier via filtered unique index:
     `CREATE UNIQUE INDEX [IX_DocumentTemplate_ActiveFamily] ON [dbo].[DocumentTemplate] ([TemplateCode]) WHERE [IsActive] = 1 AND [ApprovalStatus] = 'Approved';`
2. **Point-in-Time Issuance Traceability**:
   - Official document issuances (`DocumentSnapshot`) must permanently store `DocumentTemplateId`, `TemplateVersionNumber`, and the immutable rendered content hash (`RenderedContentHash`).
   - Historical documents must remain locked and reproducible against their original template version snapshot.
3. **Structured Placeholder Palette & QuestPDF Translation**:
   - Document templates must resolve placeholders through `IDocumentPlaceholderRegistry` with real-time syntax linting before saving.
   - Rich HTML document bodies (`TemplateBodyHtml`) must be rendered using `HtmlToQuestPdfRenderer` to maintain pixel-accurate typography, tables, and 2D barcode verification seals.

---

### 🛡️ Corporate Governance & Institutional Shareholder Standard
1. **Dual Entity Beneficiary Model**:
   - Fiduciary directorships must always link to a verified natural person (`PersonId != null`).
   - Beneficial shareholders may be either a registered natural person (`MemberType == "NATURAL_PERSON"`) or a corporate institutional entity (`MemberType == "CORPORATE_ENTITY"` with `ShareholderOrganisationId` or manual legal registration).
   - Services performing conflict of interest scans must always guard against nullable `PersonId` before evaluating person-specific conflict flags or syndicate links.
2. **MudChip OnClose Event Handlers**:
   - In MudBlazor 8, avoid binding inline `async () => { await ... }` expressions directly to `MudChip.OnClose`. Always bind to a dedicated parameterless asynchronous method (e.g., `OnClose="@ClearKeywordFilter"`).

---

### 🛡️ Blazor Endpoint Routing Case-Sensitivity Invariant
- In ASP.NET Core and Blazor Server, route templates are case-insensitive.
- Never define duplicate `@page` directives that differ only by parameter casing (e.g. `@page "/admin/document-templates/{Id:int}"` alongside `@page "/admin/document-templates/{id:int}"`).
- Doing so triggers a runtime `Microsoft.AspNetCore.Routing.Matching.AmbiguousMatchException` during endpoint selection.

---

### 🛡️ Statutory "No Levy, No Grant" Mandatory Rebate Governance
- In terms of SETA Grant Regulations (Regulation 4), Mandatory Grant (MG / WSP) levy rebates must strictly evaluate reconciled SARS monthly levy contributions.
- If no SARS levy contributions have been collected or reconciled for the submitting organisation for the scheme year, the calculated rebate amount must evaluate to zero (`0m`).
- Under no circumstances should provisional rebates be disbursed against unapproved WSP submissions or zero levy reconciliations.

---

### 🛡️ Mandatory Grant (WSP / ATR) Data & Intake Governance Standard
1. **Quorum Gating & Submission Status Invariant**:
   - A WSP/ATR submission MUST NOT transition to status `"Submitted"` upon initial wizard completion. It must enter `"PendingSignoff"`.
   - Mandatory Grant eligibility (`ValidateMandatoryGrantEligibility`) and disbursement calculations MUST strictly require `IsSignoffQuorumMet == true` in addition to status `"Submitted"` or `"Approved"`.
2. **Reconciled SARS Levy Pre-Population Invariant**:
   - The WSP intake wizard must auto-populate verified annual levy contributions by querying `LevyFileLine` for the organisation's `SdlNumber`.
   - Both user-declared payroll (`DeclaredPayrollAnnualTotal`) and reconciled SARS levy actuals (`ActualSarsLevyReceived`) must be permanently stored on `WspSubmission`.
3. **Granular Training Plan & OFO Taxonomy Integrity**:
   - `WspTrainingPlan` must capture granular PIVOTAL records (`SaqaQualificationId`, `OfoCode`, `NqfLevel`, `LearnerCountEmployed`, `LearnerCountUnemployed`, `UnitCost`).
   - Every OFO code must have a physical foreign key constraint to `lookup.OfoCodeType` and pass active validation via `IOfoTaxonomyService`.
4. **Mandatory Binary Document Sealing for Proof of Consultation**:
   - Training committee minutes and union consultation records must be captured as real binary document uploads with SHA-256 digital security seals. String placeholder file names are strictly prohibited.
5. **Transactional Atomicity & Concurrency Standard**:
   - All parent and child mutations in `WspService` must be enclosed within an explicit database transaction (`BeginTransactionAsync`).
   - `WspSubmission` must implement an optimistic concurrency token (`RowVersion`) to eliminate Last-Write-Wins hazards during 30 April peak surges.
6. **Statutory Weekend & Holiday Rollover Invariant**:
   - The 30 April statutory deadline must evaluate `IWorkingDayCalculationEngine`. If 30 April falls on a weekend or gazetted holiday, the submission window must roll over to the next business day per Section 4 of the Interpretation Act 33 of 1957.

---

### 🛡️ Mandatory Grant (WSP / ATR) Universal Bulk Ingestion Engine Standard (Option C — The Modern Hybrid)
1. **Universal Delimiter & Encoding Sniffing Invariant**:
   - Files uploaded via bulk ingestion (`.xlsx`, `.csv`, `.tsv`, `.txt`) MUST evaluate `FileFormatSniffer.SniffFilePropertiesAsync`.
   - Delimiters (`,`, `;`, `\t`, `|`) and character encodings (UTF-8 with/without BOM, UTF-16, Windows-1252) must be auto-detected with zero manual user configuration.
2. **Two-Tier Staging & Pre-Flight Validation Invariant**:
   - Bulk uploads MUST stage raw strings into `WspBulkImportStaging` without immediately mutating production training plans (`WspTrainingPlan`).
   - Set-based relational validation evaluates active OFO codes against `lookup.OfoCodeType`, 13-digit RSA National ID checksum algorithms, cost bounds, and duplicate entries.
   - Real-time pre-flight health dashboards display Total, Valid, Exception, and Committed row counts alongside spend and beneficiary rollups.
3. **Inline Quick-Fix Drawer & ClosedXML Delta Workbooks**:
   - Users must be able to inspect and correct staging errors directly in the browser via an inline Quick-Fix Drawer with cell-level re-validation.
   - For offline correction of high-volume batches, the system must provide a 1-click "Download Delta Fix-It (.xlsx)" using ClosedXML containing only exception rows highlighted in soft red, with embedded reference catalogs (`OFO Reference Catalog`).
   - Re-uploading corrected delta workbooks via the secondary dropzone merges fixes back into the staging batch automatically based on line indices.
4. **Emergency Cutoff & Partial Commitment Policy**:
   - For impending statutory submission deadlines (30 April), employers must have the option to commit valid rows immediately while holding or pruning unresolvable exceptions via affirmative confirmation dialog (`IDialogService.ShowAsync<ConfirmDialog>()`).
5. **Audited Double-Write & POPIA Masking**:
   - All batch creations, inline row corrections, delta merges, and commits MUST record double-writes in `audit_logs` with before and after snapshots.
   - RSA National ID numbers in staging diagnostics and audit log JSON metadata must be masked (e.g. `9504******082`) in compliance with the Protection of Personal Information Act (POPIA).

---

### 🛡️ Universal Segregation of Duties (Dual Authorisation Control)
- Dual Authorisation Control is strictly enforced across all statutory workflows:
  - WSP extension requests: `ReviewedByUserId != ApproverUserId` and `CreatedBy != ApproverUserId`.
  - Discretionary Grant tranche claims: `pay.CreatedBy != currentUsername` and `pay.FinanceApproverUserId != currentUsername` for CFO escalation.
  - Mandatory Grant disbursements: `disb.CreatedBy != currentUsername`.
  - Trade Test & ARPL QA approval: `app.ClaUserId != currentUsername`.
  - Contract variations (Addenda, Extensions, Terminations): `entity.CreatedBy != currentUsername`.
  - Learner change requests: `changeRequest.CreatedBy != currentUsername`.
- Any attempt at self-review or self-approval must fail fast by throwing an `InvalidOperationException` citing Dual Authorisation Governance breach.

---

### 🛡️ Universal Working Day SLA Engine Integration
- All statutory countdown timers, officer task due dates, and compliance cooling-off periods (such as the 14-day banking cooling-off and trade test result upload deadlines) must compute deadlines using `IWorkingDayCalculationEngine.AddBusinessDaysAsync`.
- Countdown timers must dynamically pause across South African statutory public holidays (Act No. 36 of 1994) and merSETA annual year-end shutdowns.

---

### 🛡️ POPIA Full-Spectrum 13-Digit RSA ID Masking
- Confidential 13-digit RSA National ID numbers must be masked (e.g. `9504******082`) using `PopiaMaskingUtility.MaskRsaId` across all UI screens, queues, grids, tables, and public verification portals.
- When rendering unstructured text, markdown snapshots, or document previews containing embedded IDs, `PopiaMaskingUtility.MaskRsaIdsInText` must be applied.

---

### 🛡️ Playwright Test Harness Visual, Console & Interactivity Standard
1. **Zero Console & Runtime Errors**:
   - Automated tests must register console and pageerror listeners via `ConsoleErrorTracker` (`playwright_assertions.py`).
   - Any runtime unhandled JavaScript error, failed promise rejection, script loading error, or console error (`type == "error"`) triggers test failure.
   - Zero tolerance for Blazor circuit crashes or `.blazor-error-boundary` visibility.
2. **True Visual Styling Verification**:
   - Verify that CSS stylesheets are attached and actively loaded (`document.styleSheets.length > 0` with populated rules).
   - Verify that MudBlazor theme variables and design tokens are defined and resolved on the document (`--mud-palette-primary`, `--mud-palette-background`).
   - Verify that structural layout containers (`.mud-layout`, `main#main-content`, `.mud-main-content`, or `.mud-container`) are visible and possess positive, non-zero bounding box dimensions (`width >= 200px`, `height >= 100px`).
   - Verify computed body styles confirm the page is not an unstyled white canvas or transparent (`display !== 'none'`, `visibility !== 'hidden'`, `opacity > 0`, DOM element count >= 10).
3. **Full Interactivity Verification**:
   - Verify Blazor Server interactive circuit connectivity: `#components-reconnect-modal` must not be in a visible reconnecting or circuit-failed state (`components-reconnect-show`, `components-reconnect-failed`).
   - Verify active interactive controls: The page must contain at least one visible, enabled interactive element (`button`, `a[href]`, `input`, `select`, `textarea`, `.mud-button-root`, `.mud-link`, `.mud-tab`) with positive bounding box and `pointer-events !== 'none'`.
   - Verify that no lingering crash modals, unhandled loading veils, or backdrop overlays prevent user interaction.

---

### 🛡️ End-to-End Automated UI Testing & Static Web Assets Governance Standard
1. **Static Web Assets in Direct DLL Execution**:
   - When launching compiled ASP.NET Core Blazor Server binaries directly via `dotnet bin/Debug/net10.0/Nsdms.Web.dll` (rather than `dotnet run`), `Program.cs` MUST invoke `builder.WebHost.UseStaticWebAssets()` and the server must launch with `--environment Development` to ensure embedded NuGet package static assets (`_content/MudBlazor/MudBlazor.min.css`, `_framework/blazor.web.js`, `Nsdms.Web.styles.css`) resolve correctly instead of throwing `FileNotFoundException` (which leads to unstyled FOUC HTML and giant unconstrained SVGs).
   - `App.razor` must enforce defensive CSS boundaries (`svg.mud-icon-root { max-width: 48px; max-height: 48px; }`) to prevent any unstyled layout blowouts during initial hydration.
2. **Playwright Blazor Form Navigation Decoupling (`no_wait_after=True`)**:
   - In Blazor Server interactive circuits or cookie authentication endpoints that issue redirects, clicking form submission buttons via Playwright (`page.click("button[type='submit']")`) must specify `no_wait_after=True` to prevent Playwright from stalling on scheduled MPA navigations that do not occur in Single-Page Blazor apps.
3. **Chromium Resource Management for Continuous Video Captures**:
   - When recording long-running end-to-end video sessions across multiple functional suites in Playwright on Windows, pass `args=["--disable-dev-shm-usage", "--no-sandbox"]` to `p.chromium.launch()` to eliminate shared memory saturation and ephemeral socket exhaustion (`net::ERR_INSUFFICIENT_RESOURCES`).
4. **Cold JIT Compilation Headroom for Deep Composite Wizards**:
   - First-visit rendering of multi-step Razor wizards with extensive DI dependencies requires generous selector wait timeouts (>= 45s) to absorb initial assembly JIT compilation on warm-up.

---

### 🛡️ End-to-End Automated Dynamic CRUD & Intake Testing Governance Standard
1. **Dynamic RSA ID Generation with Ephemeral Gender Sequence**:
   - Automated end-to-end tests performing person intake (`/people/create`) must NEVER use static or hardcoded 13-digit RSA National ID numbers (e.g. `8001015009087`).
   - Because `PersonService.CreateAsync` strictly validates database uniqueness on `RsaIdNumber`, hardcoded IDs cause subsequent regression runs to fail with duplicate key exceptions.
   - Test suites must dynamically compute mathematically valid RSA IDs using the Luhn algorithm with an ephemeral gender sequence counter (e.g. `(int(time.time()) % 4000) + 5500` for males) to guarantee validity and uniqueness across continuous test runs.
2. **DG Funding Window Template Blueprint Pre-Configuration**:
   - In automated intake of Discretionary Grant funding windows (`/dg-funding-windows/create`), statutory governance requires at least one eligible stakeholder classification and at least one allowed intervention.
   - Tests should trigger the 1-Click Template Blueprint Specification Engine (`.cursor-pointer:has-text('PIVOTAL')`) to pre-populate compliant statutory combinations prior to form submission.
3. **Multi-Step Modal Dialog Deletion Verification**:
   - Destructive entity deletion tests must not stop at clicking the page-level "Delete" button; tests must explicitly assert the presence of `<ConfirmDialog>` (`.mud-dialog`), capture dialog state, and dispatch the affirmative action (`.mud-dialog button:has-text('Delete ...')`) to verify true database cascading removal and list route redirection.


