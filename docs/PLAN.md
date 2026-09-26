# Systemic Remediation & Convention Alignment Plan

## Context & Objectives
Address all violations identified during the global and local conventions audit across .NET 10 Blazor Server, EF Core, PFMA governance, WCAG 2.2 AA accessibility, and database performance indexing.

---

## Workstreams & Task Breakdown

### 1. Database & Schema Architecture (`database-architect`)
- [ ] Add covering `.HasIndex(...)` in `NsdmsDbContext.cs` for all 36 foreign keys currently lacking explicit index configuration in Fluent API (including `PrimaryContactPersonId`, `AssessorPersonId`, `WorkflowHistory` from/to states, etc.).
- [ ] In `AuxiliaryEnterpriseEntities.cs`, refactor entities (`BankingDetails`, `ContractAddenda`, etc.) to inherit `BaseEntity` (or `BaseEntity<int>`), consolidating PK and audit properties.
- [ ] Align nomenclature: Refactor `SdfCompany` to `OrganisationSdf` with backward-compatible table mapping `[Table("SdfCompany")]` / `[Table("OrganisationSdf")]` to adhere to the `Organisation` naming invariant.

### 2. Backend Governance, Transactions & Scoping (`backend-specialist`)
- [ ] **PFMA Maker-Checker in `DiscretionaryGrantClaimService.cs`**:
  - In `ProcessClaimApprovalAsync`, enforce `claim.CreatedBy != currentUsername`.
  - Validate that the officer who performed CLO verification cannot act as CFO approver.
  - Wrap claim status update and `_audit.LogAction` within `strategy.ExecuteAsync` and an atomic transaction.
- [ ] **Maker-Checker in `BankingDetailsService.cs`**:
  - In `FirstSignoffAsync`, enforce `entity.CreatedBy != currentUsername`.
- [ ] **Dashboard Tenant Scoping in `AnalyticsService.cs` & `Home.razor`**:
  - Update `GetExecutiveDashboardSummaryAsync` to accept optional `int? organisationId = null`.
  - In `Home.razor`, extract user's `DefaultOrganisationId` and pass it to ensure non-admin SDFs see only their organisation's statistics and action items.
- [ ] **Zero Hardcoding in `ReportExportService.cs`**:
  - Inject `ISystemConfigurationService` and replace hardcoded `"https://nsdms.merseta.org.za"` with dynamic `System.BaseUrl`.

### 3. Frontend Accessibility, Dialogs & UX Polish (`frontend-specialist`)
- [ ] **WCAG SC 1.3.1 Single `<main>` Landmark**:
  - Replace redundant `<main id="main-content">` with semantic `<section class="nsdms-page-container">` across all 18 child pages (`BroadcastComposer`, `BroadcastDetail`, `BroadcastsList`, `DocumentRejectionReasonManagement`, `EmailOutboxDashboard`, `MgWindowDetail`, `MgWindowList`, `Conflict*`, `Declaration*`, `InstitutionalInsiders`, `OrganisationGovernanceDetail`, `Notification*`).
- [ ] **Destructive Entity Deletion Confirmation**:
  - In `EtqaList.razor`, `InterSetaTransferList.razor`, and `SdfDetail.razor`, wrap deletion/deactivation actions in `DialogService.ShowAsync<ConfirmDialog>`.
- [ ] **Form Spacing & Typography**:
  - Remove `mb-3` / `mb-4` margin classes directly on `<MudTextField>` primitives in `DocumentTemplateDetail.razor`, `DocumentTemplateList.razor`, `NambBatchStagingQueue.razor`, and `InterSetaTransferList.razor`.
- [ ] **Empty States**:
  - Replace plain text in `EtqaList.razor` and other key tables with `<EmptyStateCard>`.
- [ ] **Action Button Icons**:
  - Add standard `StartIcon="@Icons.Material.Filled.Close"` and `@Icons.Material.Filled.Edit` to unadorned dialog buttons.

### 4. Verification & Regression Testing (`test-engineer`)
- [ ] Run `dotnet build Nsdms.slnx /p:BuildProjectReferences=false` to verify clean compilation.
- [ ] Run unit and integration tests via `dotnet test dotnet/Nsdms.Tests/Nsdms.Tests.csproj /p:BuildProjectReferences=false`.
- [ ] Ensure all tests pass with zero regressions.
