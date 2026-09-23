---
type: project
created: 2026-05-25
updated: 2026-09-19
---

# Project Conventions

## Porting Source Baseline
- The definitive, canonical source codebase for all migration and porting into .NET 10 is located at: `C:\Antigravity\nsdms-2026-04-01\nsdms\MerSETA\NSDMS-Latest`.
- All legacy business rules, workflows, calculations, and data structures must be referenced directly from this directory.
- **Strict Read-Only Invariant**: NEVER modify, delete, format, or write code into the `NSDMS-Latest/` folder. It is an immutable legacy reference source.

## Git Workflow & DDL Synchronization
- Always create a new dedicated branch for major code changes.
- Branch name format should follow: `feature/[task-slug]` or `fix/[bug-slug]`.
- **DDL SQL Script & Lookup Values**: When pushing to Git, always generate the DDL SQL script including all database tables, lookup tables (`lookup.*`), and lookup seed values. These must be committed and pushed to Git aligned with the corresponding application code.

## Supported AI platforms (AG Kit)
- AG Kit **only supports Gemini CLI and Google Antigravity**.
- Do not claim compatibility with Claude Code, Cursor, Copilot, Windsurf, or other assistants unless the user explicitly expands scope.
- Copy on the website, docs, FAQ, README, and marketing should describe AG Kit as a toolkit for Gemini CLI / Antigravity-style agent setups.

## Domain Nomenclature & Entity Semantics
- We work with **Organisations** across all entity types (NPOs, NGOs, public entities, private corporations, levy payers, non-levy payers).
- Always use **`Organisation`** (not `Company`) across database tables, entity models, DTOs, service methods, and UI views, unless referring to a specific corporate subtype.

## Database & EF Core Naming Conventions
- Database entities, tables, and properties must use **PascalCase** (e.g. `Organisation`, `OrganisationContact`, `OrganisationSite`, `GrantMoa`, `SetmisSubmissionBatch`, `MandatoryGrantDisbursement`, `Visit`).
- Every business table must have an auto-generated integer primary key `Id` and audit columns (`CreatedAt`, `CreatedBy`, `ModifiedAt`, `ModifiedBy`).
- Lookup tables must be placed under a dedicated **`lookup` schema** (e.g., `lookup.CategoryType`, `lookup.StatusType`, `lookup.ProvinceType`) with `*Type` suffix and unique `Code` (`varchar(15)`).
- Implement explicit performance indexing on all foreign keys, status columns, and search queries in Fluent API.
- Idempotent T-SQL DDL migrators must accompany every new module under `dotnet/Nsdms.Infrastructure/Data/`.

## Clean Architecture & Living Documentation
- Every service mutation must perform a double-write into `audit_logs` with a structured `MetadataJson` snapshot.
- Application services must use `INsdmsDbContextFactory` for thread-safety in Blazor Server interactive circuits.
- Add unit tests in `Nsdms.Tests` for every business rule and calculation.
- Maintain and update `test_all_pages_playwright.py` with every new route created to guarantee 100% test coverage.

## Employer Visit & Contact Person Invariant
- When building features that schedule or execute ANY type of "Visit" or "Monitoring" activity against an Employer, ALWAYS enforce selection of a specific Contact Person (`ContactPersonId` / `contact_person_id`).
- Backend services must explicitly validate the `ContactPersonId` relational link before saving any visit record.

## CASL Role-Based Authorization & Visibility Standard
- When implementing CASL ability checks in UI components for visibility, ALWAYS check for both `View` and `Manage` actions (e.g., `CaslAbilityService.CanViewOrManage(context, "Model")` or `ability.can('View', 'Model') || ability.can('Manage', 'Model')`) because CASL treats these actions as strictly distinct unless aliases are explicitly configured.
- Admin users have global visibility and full CRUD permissions across all modules.
- Non-admin users are strictly scoped to their `DefaultOrganisationId` tenant boundary.

## MudBlazor Layout & Sticky Action Bar Standard
- Never place utility padding classes (`Class="pa-*"`, `Class="pt-*"`, `Class="py-*"`) directly on `<MudMainContent>`. MudBlazor utility classes apply `!important` which overrides the framework's calculated `padding-top: var(--mud-appbar-height)` (64px) and causes the header to overlap page content by 48px.
- Always nest inner padding inside `<MudMainContent>`: `<MudMainContent><div class="pa-4"><main id="main-content">@Body</main></div></MudMainContent>`.
- All sticky action bars, detail top bars, and table toolbars must use `top: var(--mud-appbar-height, 64px) !important;` (or the `.sticky-top` / `.sticky-top-header` CSS classes) so they dock flush underneath the `MudAppBar` during scroll.
- When editing a record, the edit button must expose all editable fields in full view with Cancel and Save buttons (including icon indicators from `Icons.Material.Filled`), and all mutations must produce `ISnackbar` toast feedback.

## RSA ID Demographics Helper
- Any Razor component capturing a South African National ID number must bind an `OnBlur` / `TextChanged` handler to automatically extract and populate Date of Birth, Gender, and Citizenship via `RsaIdValidator.Parse`.

## Dynamic Configuration & Feature Flags Governance
- **Zero Hardcoding Invariant**: No business parameter, threshold, storage path, or external integration endpoint may be hardcoded. Always use `ISystemConfigurationService` with cascading database overrides.
- **Integrations Off-By-Default**: All external integrations (Dynamics GP, Sage, Live DHET SFTP, Live SARS FTP, SMS OTP, Azure Blob) MUST default to `IsEnabled = false`. Workflows must cleanly execute in mock simulation mode when disabled.

## EF Core Nullability & SETMIS Schema Resilience Standard
- **Optional Relational Codes**: In EF Core entities representing legacy or SETMIS records (`LearnerTradeTest`, `CompanyLearner`, `Person`, `TrainingProvider`), declare all optional foreign key string properties as nullable (`string?`) to prevent `SqlNullValueException` when existing database rows contain NULLs.
- **Explicit Singular Table Names**: When defining new `DbSet<T>` properties in `INsdmsDbContext` and `NsdmsDbContext`, always configure `modelBuilder.Entity<T>().ToTable("SingularName")` in Fluent API to ensure EF Core does not default to plural table names.

## Systemic Bug Remediation & Zero-Regression Standard
- **System-Wide Fix Enforcement**: When a bug or defect is identified and fixed, never fix it as an isolated one-off. Proactively search for and remediate the exact root-cause pattern across all related components, services, and routes throughout the application.
- **Zero-Regression Safeguards**: Reinforce every bug fix with automated unit/integration/E2E regression tests, schema constraints, or architectural invariants to ensure the defect never reoccurs.

## UI Plain-Language & Anti-Jargon Governance Invariant
1. **Zero Database/Architecture Jargon**: Replace "Double-Write / MetadataJson" with **"Audited Change Log"**; replace "Temporal Tables" with **"Historical Version Timeline"**.
2. **Zero Cryptography Jargon**: Replace "SHA-256 Hash / Fingerprint" with **"Digital Security Seal"** or **"Verification Reference"**. Keep 64-character hashes hidden under an expandable "Technical Verification Data" drawer.
3. **Disambiguate "Claims"**: Never use "Claims" for authorization permissions on the UI (use **"Permissions / Authorised Functions"**). Reserve "Claims" exclusively for **Discretionary Grant Tranche Invoices**.
4. **Natural Workflow State Language**: Replace "Terminal State" with **"Completed / Finalised"**; replace "Workflow Blueprint" with **"Approval Process Lifecycle"**.
5. **Task Management Clarity**: Replace "Task Lease" with **"Reserved / In Review by [Officer]"**.
6. **Mask All Database Integer Keys**: Dropdowns, headers, badges, and table cells must only display statutory business references (e.g. `DG-2026-TOYOTA-01`, `WSP-2026-0042`, `SDL: L123456789`).

## MerSETA Statutory Nomenclature & Terminology Governance
1. **Discretionary Grants (DG) vs Mandatory Grants (MG)**: Never use the word "Grant" in isolation. Always qualify as "Discretionary Grant (DG)" (PIVOTAL strategic allocations / MoAs) or "Mandatory Grant (MG)" (20% WSP/ATR levy rebates).
2. **Contracting via MoA**: The legal contracting instrument for Discretionary Grants is the **Memorandum of Agreement (MoA)**, never generic "Contracts".
3. **Skills Development Providers (SDP)**: Refer to accredited training institutions as **Skills Development Providers (SDPs)** per QCTO statutory guidelines.
4. **Artisan Mentorship Ratios**: Enforce NAMB / QCTO artisan mentor-to-apprentice ratios via `IMentorRatioPolicyEngine`, respecting trade-specific caps.
5. **Governance & PFMA Controls**: Adhere to financial approval delegation, Segregation of Duties (Maker-Checker), and non-repudiation audit logging for all approval gates.

## Playwright Test Harness Visual, Console & Interactivity Standard
- **Invariant**: Only true, visually styled, fully interactive pages must pass the automated test suite. A test passing solely on HTTP 200 or non-empty body text is strictly prohibited.
- **Mandatory Console & Exception Assertions**:
  - Test suites must register error listeners via `page.on("console")` and `page.on("pageerror")` using `ConsoleErrorTracker`.
  - Zero tolerance for uncaught JavaScript exceptions, runtime unhandled promise rejections, network script 4xx/5xx failures, or console error messages (`type == "error"`). Any console error triggers immediate test failure.
  - Zero tolerance for Blazor Server circuit failures: `.blazor-error-boundary` must not be visible; body must not contain unhandled exception strings (`"An unhandled exception occurred"`, `"SqlException"`, `"NullReferenceException"`).
- **Mandatory Visual Styling Assertions**:
  - Verify that CSS stylesheets are attached and actively loaded (`document.styleSheets.length > 0` with populated rules).
  - Verify that MudBlazor theme variables and design tokens are defined and resolved on the document (`--mud-palette-primary`, `--mud-palette-background`).
  - Verify that structural layout containers (`.mud-layout`, `main#main-content`, `.mud-main-content`, or `.mud-container`) are visible and possess positive, non-zero bounding box dimensions (`width >= 200px`, `height >= 100px`).
  - Verify computed body styles to confirm the page is not an unstyled white canvas or transparent (`display !== 'none'`, `visibility !== 'hidden'`, `opacity > 0`, DOM element count >= 10).
- **Mandatory Full Interactivity Assertions**:
  - Verify Blazor Server interactive circuit connectivity: `#components-reconnect-modal` must not be in a visible reconnecting or circuit-failed state (`components-reconnect-show`, `components-reconnect-failed`).
  - Verify active interactive controls: The page must contain at least one visible, enabled interactive element (`button`, `a[href]`, `input`, `select`, `textarea`, `.mud-button-root`, `.mud-link`, `.mud-tab`) with positive bounding box and `pointer-events !== 'none'`.
  - Verify that no lingering crash modals, unhandled loading veils, or backdrop overlays prevent user interaction.

## End-to-End Automated Dynamic CRUD & Intake Testing Governance Standard
- **Dynamic RSA ID Generation with Ephemeral Gender Sequence**:
  - Automated end-to-end tests performing person intake (`/people/create`) must NEVER use static or hardcoded 13-digit RSA National ID numbers (e.g. `8001015009087`).
  - Because `PersonService.CreateAsync` strictly validates database uniqueness on `RsaIdNumber`, hardcoded IDs cause subsequent regression runs to fail with duplicate key exceptions.
  - Test suites must dynamically compute mathematically valid RSA IDs using the Luhn algorithm with an ephemeral gender sequence counter (e.g. `(int(time.time()) % 4000) + 5500` for males) to guarantee validity and uniqueness across continuous test runs.
- **DG Funding Window Template Blueprint Pre-Configuration**:
  - In automated intake of Discretionary Grant funding windows (`/dg-funding-windows/create`), statutory governance requires at least one eligible stakeholder classification and at least one allowed intervention.
  - Tests should trigger the 1-Click Template Blueprint Specification Engine (`.cursor-pointer:has-text('PIVOTAL')`) to pre-populate compliant statutory combinations prior to form submission.
- **Multi-Step Modal Dialog Deletion Verification**:
  - Destructive entity deletion tests must not stop at clicking the page-level "Delete" button; tests must explicitly assert the presence of `<ConfirmDialog>` (`.mud-dialog`), capture dialog state, and dispatch the affirmative action (`.mud-dialog button:has-text('Delete ...')`) to verify true database cascading removal and list route redirection.

## Core Infrastructure & Architecture Invariants (Post-Remediation)
- **Guidance Invariant**: Always add the Core Infrastructure & Architecture Invariants guidance block to `GEMINI.md` whenever generating or updating system guidelines.
- **Schema Migration Journaling**: Never invoke raw DDL scripts on startup without wrapping them in `SchemaMigrationJournal.ExecuteIfNotAppliedAsync(...)`. Journal table `dbo.__CustomSchemaJournal` ensures that executed migrations are bypassed in <5ms, protecting cold start times.
- **Interactive Circuit Multi-Tenancy**: Never resolve `ITenantProvider` solely from `IHttpContextAccessor.HttpContext` (null on subsequent WebSocket packets); always verify claims via `AuthenticationStateProvider` fallback to enforce fail-closed tenant scoping on `OrganisationId`.
- **Event Subscription Lifecycle**: Never declare `public static event Action<...>` in singleton notification services; use instance events and enforce `IDisposable` with event unsubscription (`service.NotificationReceived -= Handler`) in Blazor components to prevent garbage collection retention of disconnected circuits.
- **Segregation of Duties (Maker-Checker)**: In all financial activations (Banking Details, Discretionary Grant Claims, Mandatory Grant Disbursements), enforce `CreatedBy != currentUserId` and `FirstSignoffUserId != currentUserId` before committing status changes.
## Transaction Management & Database Atomicity Governance Standard
- **Execution Strategy with Transactions**: Whenever `EnableRetryOnFailure` is configured, user-initiated transactions MUST be enclosed within `db.Database.CreateExecutionStrategy().ExecuteAsync(...)`. Directly invoking `db.Database.BeginTransactionAsync()` outside an execution strategy triggers runtime `InvalidOperationException` on SQL Server.
- **Atomic Double-Write Invariant**: All business mutations and their corresponding `audit_logs` entries must be committed within the same database transaction. Never rely on two uncoordinated `SaveChangesAsync()` calls where the second failure drops the audit record.
- **Non-Zero Identity Evaluation**: When saving a newly created entity and logging its creation, ensure the entity's auto-generated `Id` is evaluated after the insert and before the transaction commits so `AuditLog.RecordId` is never committed as `0`.
- **Financial Headroom & TOCTOU Protection**: In financial claim submissions (e.g. Discretionary Grant claims against Project Implementation Plan budgets), enforce atomic headroom checks within transactions (or serializable isolation) to eliminate multi-user over-allocation race conditions.
- **Client Concurrency Token Hydration**: In disconnected edit operations with optimistic concurrency (`RowVersion`), assign the client-provided token to `db.Entry(existing).Property(e => e.RowVersion).OriginalValue` so EF Core correctly detects Last-Write-Wins collisions.
- **Queue Row-Locking Concurrency**: High-throughput message queues (`ErpOutboxMessage`, `OutboxMessage`) must use atomic row-level reservation (`WITH (UPDLOCK, READPAST)`) to prevent multiple workers from leasing and dispatching duplicate transactions.
- **Database Engine Concurrency Baseline**: Ensure database options `AUTO_CLOSE OFF`, `READ_COMMITTED_SNAPSHOT ON`, and `ALLOW_SNAPSHOT_ISOLATION ON` are permanently configured to eliminate reader/writer deadlock cascades (SQL Error 1205).

## UI Accessibility, Form Spacing & Test Cache Isolation Standards
- **Accessible Landmark Hierarchy (WCAG SC 1.3.1)**: Single `<main id="main-content">` landmark per page provided by `MainLayout.razor`. Inner components, wizards (`WizardShell`), and tabs must use `<section>` or `<article>`, never `<main>`. Detail headers (`EntityHeader`) must use `HtmlTag="h1"` for primary titles.
- **Form Layout & Spacing Budget**: Never place hardcoded bottom margins (e.g. `mb-4`) on field primitives (`ReadOnlyField`, `MudTextField`); spacing must be governed exclusively by parent `<MudGrid Spacing="3">`. Labels must use sentence case (`0.75rem`, weight 600, opacity 0.85); `text-uppercase` is strictly prohibited. Forms with sticky bottom actions must use `.nsdms-form-shell` (`padding-bottom: 84px !important`).
- **Static Cache Test Isolation**: Services maintaining static in-memory lookup caches (`LookupService._lookupCache`) must expose `ClearCache()` / `ResetCache()`. Automated unit/integration tests running against unique in-memory databases must call `service.ClearCache()` during `Arrange` to prevent cross-test cache contamination.

## ISO 9001:2015 & DPSA Directive Compliant Atomic Audit Logging Standard
1. **Zero Partial Commits & Transactional Double-Write**:
   - Every domain entity mutation and its associated audit log entry MUST be executed atomically within a single transactional unit via `IAtomicAuditTransactionManager.ExecuteAtomicAsync` or `ExecuteAtomicBatchAsync`.
   - In SQL Server environments, user transactions must be enclosed within `db.Database.CreateExecutionStrategy().ExecuteAsync(...)` to ensure resilience under transient failures. Any failure in business logic, specification validation, or persistence MUST trigger an immediate, complete rollback of both entity mutations and audit entries (`RollbackAsync()`).
2. **Pre-Validation Ordering Invariant**:
   - Specification validation (checking `EntityName`, `ActionName`, and verifying `Actor` against `"ANONYMOUS"` or `"UNKNOWN"`) MUST occur BEFORE calling `db.SaveChangesAsync()` to ensure zero partial commits in both relational and in-memory test databases.
   - On transaction abort when `tx == null` (such as in unit test environments), invoke `db.ChangeTracker.Clear()` to guarantee untracked entities are purged.
3. **ISO 9001:2015 Clause 7.5 Control of Documented Information**:
   - Every audit entry MUST preserve mandatory identification attributes (`EntityName`, `ActionName`, `Actor`), positive auto-generated integer `RecordId`, and structured differential state capture (`before` and `after` snapshots in `MetadataJson`).
   - Every recorded transaction computes an immutable SHA-256 digital security seal (`ComputeDigitalSecuritySeal`) across the payload, enabling real-time cryptographic tamper detection and non-repudiation.
4. **DPSA Information Security Directive & CGICTPF Public Sector Compliance**:
   - **Zero Anonymous Actors**: 100% actor accountability is strictly enforced. Any attempt to record mutations with empty, `"ANONYMOUS"`, or `"UNKNOWN"` actors must fail fast and abort the transaction.
   - **Chronological UTC Precision**: Audit timestamps must record UTC timestamps with sub-second precision; future-dated timestamps exceeding reasonable drift thresholds are rejected.
   - **POPIA Sensitive PII Redaction**: Sensitive personal identifiers (such as 13-digit RSA National ID numbers and bank account numbers) must be masked in `MetadataJson` payloads before database persistence.
   - **PFMA Maker-Checker Segregation of Duties**: Approvals and statutory signoffs (e.g., Banking Details, Discretionary Grant MoAs, Tranche Claims) must verify that the creator and approver are distinct individuals (`CreatedBy != ApproverUserId`).
5. **Windows MSBuild Assembly Lock Safeguard in Test Pipelines**:
   - When running test suites (`dotnet test`) on Windows environments with an active local dev server (`Nsdms.Web`), pass `/p:BuildProjectReferences=false` to prevent MSBuild locked DLL copy collisions.


