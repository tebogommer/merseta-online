# Discovery Notes — merSETA NSDMS

> **Requirements Steward System — Phase 1 Deliverable (§2.8)**  
> **Target Solution:** `.NET 10` Blazor Server + MudBlazor + SQL Server Express  
> **Document Status:** BINDING CONVENTIONS  
> **Last Updated:** 2026-09-13  

---

## 1. Solution Structure & Boundaries

- **Root Solution:** `dotnet/Nsdms.slnx`
- **Application Architecture:** Clean Architecture with Domain-Driven Core and strict Separation of Concerns.
- **Sub-Projects & Responsibilities:**
  - `Nsdms.Domain`: POCO Domain entities, enums, value objects, domain events, security permissions (`AppPermissions.cs`), and business rules. Zero external dependencies.
  - `Nsdms.Application`: Application services, business logic, DTOs, workflow engines, validators, and CQRS handlers.
  - `Nsdms.Infrastructure`: Data persistence (`NsdmsDbContext`), SQL Server temporal tables, DDL phase migrators (`dotnet/Nsdms.Infrastructure/Data/`), QuestPDF document generation, attestation engines, background job hosted services.
  - `Nsdms.Web`: Blazor Server (.NET 10) frontend with MudBlazor component library, interactive razor components, authentication state, and session circuits.
  - `Nsdms.Tests`: Comprehensive xUnit test suite (unit tests, domain calculation tests, SLA tests, security checks) and Playwright E2E suites.

---

## 2. Data Layer

- **Database Engine:** Microsoft SQL Server Express (`localhost`, Database: `NSDMS-NET`).
- **Isolation Level:** Mandatory Read Committed Snapshot Isolation (RCSI: `READ_COMMITTED_SNAPSHOT ON` and `ALLOW_SNAPSHOT_ISOLATION ON`) with `AUTO_CLOSE OFF`.
- **Primary Keys:**
  - Standard entities: Auto-generated integer `Id` (`int`, `IDENTITY(1,1)` via `BaseEntity`).
  - High-volume entities: `long Id` (`BIGINT` via `BaseLongEntity`) for `AuditLog`, `LevyFileLine`, and `WspTrainingPlan`.
- **System Audit Columns:** All persistent tables implement standard audit columns: `CreatedAt`, `CreatedBy`, `ModifiedAt`, `ModifiedBy`.
- **Temporal Versioning:** All persistent domain entities are mapped as system-versioned temporal tables via `modelBuilder.ApplyTemporalTables()` into the `history` schema (`history.<Entity>History`).
- **Lookup Architecture:** Normalized lookups reside in the `lookup.*` schema (e.g. `lookup.InterventionType`, `lookup.StakeholderEligibilityType`, `lookup.TitleType`) featuring `Code` (PK), `Name`, `Description`, and `Active`.
- **Auditing Double-Write Policy:** All state mutations perform an atomic double-write into the `audit_logs` table capturing `RecordId`, `ActionName`, `Actor`, `Timestamp`, and a structured `MetadataJson` delta snapshot.
- **Multi-Tenancy:** Handled via EF Core global query filters referencing `_tenantProvider.CurrentOrganisationId` with `_tenantProvider.IsAdmin` bypass. System-wide background tasks use `.IgnoreQueryFilters()`.

---

## 3. Roles & Permissions (BINDING)

Authentication and authorization use ASP.NET Core Identity with hybrid Role-Based Access Control (RBAC) and Claims-Based Module Permissions (`AppPermissions.cs`).

### 3.1 Role Hierarchy & Classification Proposal (§2.5)

| Tier / Category | Role Name | System Purpose |
|---|---|---|
| **Tier 1 (System Admin)** | `SuperAdmin` | Enterprise Super Administrator with unrestricted access across all modules and functions. |
| **Tier 2 (Application Admin)** | `Admin` | System Administrator with operational configuration, user administration, and system maintenance permissions. |
| **Tier 2 (Executive Authority)** | `Executive` | Executive Directorate, Board Members, and Accounting Authority with final adjudication and governance oversight. |
| **Tier 3 (Governance & Audit)** | `Compliance` | Statutory Compliance & Ethics Auditor enforcing PFMA disclosures, conflict of interest reviews, and statutory audits. |
| **Tier 3 (Governance & Audit)** | `ReviewCommittee` | Review & Adjudication Committee member approving grant allocations, WSP submissions, and project scopes. |
| **Tier 3 (Operational Staff)** | `FinanceManager` | Senior Finance Specialist managing levy reconciliations, DG payment tranches, and MOA disbursals. |
| **Tier 3 (Operational Staff)** | `CLO` | Client Liaison Officer conducting workplace monitoring, site audits, and desktop verifications. |
| **External Stakeholder** | `SDF` | Skills Development Facilitator submitting WSP/ATR, Discretionary Grant applications, and workplace approvals. |
| **External Stakeholder** | `TrainingProvider` / `SDP` | Accredited Skills Development Provider submitting learner registrations and summative assessment results. |
| **External Stakeholder** | `User` | Standard self-registered applicant/citizen profile with rights to apply for statutory stakeholder representation. |

### 3.2 Granular Permission Claims
Permission claims are structured as `{Module}:{Action}` across 11 core modules (`Organisations`, `People`, `Wsp`, `Grants`, `Finance`, `Learners`, `Etqa`, `Workplace`, `Compliance`, `System`, `Governance`).

---

## 4. UI Components (BINDING — Reuse, Never Reinvent)

UI follows the `.NET 10 Blazor + MudBlazor Design System` governed by `BLAZOR-MUDBLAZOR-DESIGN-SYSTEM.md` and `AGENTS.md`.

### 4.1 Strict Archetypes
- **A1 Master List:** Standard tabular view using `<DataGridShell>` with server-side pagination, search debouncing, and 7-tier page sizes (`5, 10, 20, 50, 100, 250, 500`).
- **A2 Work Queue:** Specialized task queue for officer action items, SLA prioritization, and dual authorisation approvals.
- **A3 Entity Detail:** Strictly **View by default** at `/{entity}/{id}` displaying `<ReadOnlyField>` components. Never render editable inputs or save buttons directly on this route.
- **A4 Form / Wizard:** Dedicated edit routes at `/{entity}/{id}/edit` and create routes at `/{entity}/create` wrapped in `<FormShell>` with sticky bottom save/cancel actions and dirty tracking, or multi-step flows in `<WizardShell>`.
- **A5 Dashboard:** Executive and role-tailored dashboards scoped strictly to user's organisation/portfolio.
- **T3 Reference Data:** Lookup and parameter management grids.

### 4.2 Core Shared Components
- `<DataGridShell>`: 13-point baseline data grid with search, sort, and server-side paging.
- `<EntityHeader>`: Standardized page header with breadcrumb hierarchy, status badge, title, subtitle, and single action zone.
- `<FormShell>`: Form wrapper with sticky bottom action footer (`Save`, `Cancel`) and dirty tracking.
- `<WizardShell>`: Multi-step intake flow with standard footer and review step.
- `<WorkflowStepper>`: Visual state progression indicator for workflows with >3 states.
- `<ReadOnlyField>`: Standardized read-only label/value display element.
- `<ConfirmDialog>`: Affirmative dialog invoked via `IDialogService.ShowAsync<ConfirmDialog>()` prior to any destructive action.

---

## 5. Theme Tokens & Styling

- **Design System:** MudBlazor Default Theme with CSS design variables.
- **Theme Variables:** All colors must resolve via MudBlazor theme tokens (e.g. `var(--mud-palette-primary)`, `Color.Primary`, `Color.Secondary`).
- **Zero Hex Hardcoding:** Hardcoded hex colors (`#cc9c47`, `#...`) and arbitrary inline `style="..."` attributes are strictly prohibited.
- **Typography:** Sentence case across all UI labels, headers, and buttons.
- **Monospace Identifiers:** Technical keys (SDL numbers, SAQA IDs, hashes, serial numbers, trade test references) must use `.font-mono`.
- **Sticky Header Invariant:** Sticky top bars dock underneath the 64px `MudAppBar` using `top: var(--mud-appbar-height, 64px) !important;`. Utility padding classes (`pa-*`, `pt-*`) must never be placed directly on `<MudMainContent>`.

---

## 6. Routing Conventions

- **View Route:** `/{resource}/{id}` (strictly read-only view).
- **Edit Route:** `/{resource}/{id}/edit` (form shell with save and cancel).
- **Create Route:** `/{resource}/create` (creation form or wizard).
- **Security:** Every interactive page must declare `@attribute [Authorize]` or `@attribute [Authorize(Roles = "...")]`. Router enforces `<AuthorizeRouteView>`.

---

## 7. Services & Dependency Injection

- Application services injected via interfaces (`IWspService`, `IGrantService`, `ILearnerService`, etc.).
- **Circuit Concurrency:** Services interacting with EF Core must utilize `INsdmsDbContextFactory` to create isolated `DbContext` instances within Blazor interactive server circuits.
- **Zero Raw DB in UI:** Razor components MUST NEVER inject `DbContext` or `IDbContextFactory` directly; all queries and mutations flow through domain application services.

---

## 8. Testing Strategy

- **Test Suite:** `dotnet/Nsdms.Tests/` using xUnit and FluentAssertions.
- **Unit & Domain Tests:** Pure business logic, SLA calculators, mentor ratios, and statutory financial rebate algorithms.
- **Integration Tests:** Repository and service integration against in-memory or test databases using `TestDbContextFactory`.
- **E2E & UI Verification:** Automated Playwright scripts verifying page navigation, form flows, and standard component contracts.

---

## 9. Background Jobs & Circuit Resilience

- **Channel-Decoupled Pipeline:** Heavy tasks (QuestPDF compilation, statutory batch generation, SARS levy bulk reconciliation) execute asynchronously via `IBackgroundJobQueue` through `System.Threading.Channels`.
- **Background Worker:** Handled by hosted service `BackgroundJobProcessingWorker`.
- **Client Draft Persistence:** Statutory multi-step forms leverage `IFormDraftService` and `DraftRecoveryBanner.razor` to protect against circuit disconnection.

---

## 10. Compliance Context

- **SDA (Skills Development Act 97 of 1998):** Workplace skills planning, learnership agreements, and artisan trade test regulations.
- **SDLA (Skills Development Levies Act 9 of 1999):** Mandatory 20% grant rebates, 49.5% discretionary grants, and SARS levy distribution algorithms.
- **SETA Grant Regulations (2012):** Mandatory Grant annual submission deadline of 30 April; statutory extension requests capped strictly at 31 May under Regulation 4(2).
- **POPIA (Protection of Personal Information Act 4 of 2013):** Mandatory masking of 13-digit RSA National ID numbers (e.g. `9504******082`), consent tracking, and audit logging of personal data views.
- **PFMA (Public Finance Management Act 1 of 1999):** Financial approval delegations, dual authorisation governance (Segregation of Duties), non-repudiable audit trails, and annual declarations of interest.
- **QCTO & NAMB:** Quality Council for Trades and Occupations & National Artisan Moderation Body accreditation standards, 17 designated trades, 1:4 mentor ratios, and 50% practical task credit retention.
- **DHET SETMIS & SAQA NLRD:** Mandatory flat-file extract specifications with exact fixed positional character widths.

---

## 11. Tracker Relationship (§1.5)

- **Selected Model:** **(a) Register is authoritative.**
- **Details:** The living Requirements Register (`REQUIREMENTS.md`) serves as the single source of truth for all requirements, acceptance criteria, traceability, and compliance tags. Incumbent issue trackers (GitHub Issues / commit references) mirror requirement identifiers and statuses.

---

## 12. Secrets Inventory (§2.1 — Key Names & Types Only)

*In accordance with §2.1, no credentials, passwords, tokens, connection strings, hostnames, or values are recorded below.*

| Configuration Key Name | Key Type | Purpose / Description |
|---|---|---|
| `ConnectionStrings:DefaultConnection` | Connection String | Primary SQL Server database connection string. |
| `NsdmsSettings:Storage:LocalRootPath` | Filesystem Path | Root filesystem path for local DMS document storage. |
| `NsdmsSettings:Integrations:DynamicsGp:EndpointUrl` | URI / URL | REST endpoint for Microsoft Dynamics GP financial ledger integration. |
| `NsdmsSettings:Integrations:SageErp:EndpointUrl` | URI / URL | REST endpoint for Sage ERP disbursement batch sync. |
| `NsdmsSettings:Integrations:SarsFtp:Host` | Hostname | SFTP server address for monthly SARS levy file transfer. |
| `NsdmsSettings:Integrations:SmsPortal:EndpointUrl` | URI / URL | Gateway endpoint for SMS OTP verification services. |
