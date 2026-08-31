# Project Plan: 360-Degree Relational Master-Detail (Organisation / Employer)

## 🎯 Objective
Upgrade `/employers/{id}` to a complete **360-Degree Master-Detail Relational Hub** with lazy-loaded child data tables, deep in-tab creation/linkage dialogs, and row-level action shortcuts for all related learners, grants/MoAs, workplace approvals, site visits, training committee members, levies, and evidence.

---

## 📋 Task Breakdown & Architecture

### Phase 1: Application Service Relational Contracts (`Nsdms.Application`)
- **Task 1.1**: Define lightweight relational DTOs in `Nsdms.Application/Common/Models/`:
  - `OrganisationLearnerDto` (Learner ID, Full Name, RSA ID, Programme Type, Status, Registration Date, Progression %).
  - `OrganisationGrantSummaryDto` (MoA ID, Application No, Funding Window, Approved Amount, Disbursed Amount, PIP Status).
  - `OrganisationWpaDto` (WPA ID, Reference No, Site Name, Trades Count, Mentors Count, Status, Expiry Date).
  - `OrganisationCommitteeMemberDto` (Member ID, Person Name, Designation, Union Name, IsActive).
- **Task 1.2**: Implement optimized async query methods in `OrganisationService.cs`:
  - `GetLinkedLearnersAsync(int orgId)`
  - `GetGrantMoasAndApplicationsAsync(int orgId)`
  - `GetWorkplaceApprovalsAsync(int orgId)`
  - `GetTrainingCommitteeMembersAsync(int orgId)`
- **Task 1.3**: Unit test relational DTO queries in `Nsdms.Tests/`.

### Phase 2: MudBlazor UI Master-Detail Refactor (`Nsdms.Web`)
- **Task 2.1**: Refactor `EmployerDetail.razor` into a 9-Tab Stacked Master-Detail layout:
  - **Tab 1:** General Master Info (Core Org Demographics & SARS SDL)
  - **Tab 2:** Contacts, SDFs & Training Committee (with `+ Add Contact / SDF` and `+ Add Committee Member` dialogs)
  - **Tab 3:** Sites & Branches (with `+ Add Branch Site` dialog)
  - **Tab 4:** Learner Ecosystem (Lazy-loaded ChildGrid with search, filters, status chips, and drill-through to `/learners/{id}`)
  - **Tab 5:** WSP / ATR & SARS Levies (WSP submissions, mandatory grant rebate disbursements)
  - **Tab 6:** Grants & MoAs (DG applications, approved MoAs, tranches, PIP progress, link to `/finance/grant-moas/{id}`)
  - **Tab 7:** Workplace Approvals (WPA site approvals, approved trades, artisan mentors, link to `/workplace-approvals/{id}`)
  - **Tab 8:** Site Visits & Monitoring (Visits table with mandatory `ContactPersonId`, `+ Schedule Visit` dialog)
  - **Tab 9:** Evidence Vault & Temporal Audit History
- **Task 2.2**: Implement asynchronous lazy-loading trigger on tab activation (`OnTabChanged`) to ensure instant initial page rendering.
- **Task 2.3**: Implement in-tab quick actions with confirmation snackbars (`ISnackbar`) and atomic double-write audit logging.

### Phase 3: Quality Gates & Verification
- **Task 3.1**: Run unit test suite: `dotnet test dotnet/Nsdms.Tests/`.
- **Task 3.2**: Run UI accessibility & quality gate: `python scripts/ci_ux_quality_gate.py`.
- **Task 3.3**: End-to-end manual verification across all tabs, modals, filters, and navigation links.

---

## 🔍 Verification Checklist
- [ ] Tab 1 loads instantly on `/employers/{id}` without blocking.
- [ ] Tab 4 (Learners) asynchronously loads all learners hosted/funded by the employer with functional search and hyperlinked rows.
- [ ] Tab 6 (Grants & MoAs) displays Discretionary Grants and MoA tranches with budget metrics.
- [ ] Tab 7 (Workplace Approvals) displays trades, mentors, and site inspection statuses.
- [ ] Tab 8 (Visits) enforces mandatory `ContactPersonId` when scheduling a visit.
- [ ] All action buttons have appropriate Lucide / MudBlazor icons and machine-readable `aria-label` tags.
- [ ] All mutations show confirmation snackbar toasts and record `AuditLog` snapshots.
