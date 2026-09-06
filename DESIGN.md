# MerSETA NSDMS Enterprise Design System & Architecture Specification (v1.2)

## 1. Executive Design Philosophy & Paradigm Shift

The merSETA National Skills Development Management System (NSDMS) has been modernized from a traditional form/register-driven application into a high-density, accessible, task-oriented enterprise operations platform.

The architectural interaction model follows the **Enterprise Action Cycle**:
$$\text{WORK} \longrightarrow \text{CONTEXT} \longrightarrow \text{DECISION} \longrightarrow \text{ACTION} \longrightarrow \text{EVIDENCE} \longrightarrow \text{AUDIT}$$

### Core Tenets:
1. **Calm, Authoritative & Professional**: High information density without visual clutter. Soft neutral surfaces (`#f8fafc` / `#0b0f19`) and subtle structural 1px borders (`#e2e8f0` / `#1f2937`) replace heavy card elevations, loud gradients, and decorative hero containers.
2. **Restricted Brand Palette**: merSETA Gold (`#b8860b` Light, `#e6b054` Dark) is reserved for brand identity accents, active navigation states, and key highlights. It is **never** used as a universal status color.
3. **Strict Semantic Status Taxonomy**: State colors carry absolute meaning across the entire application:
   - 🟢 **Success (`#059669` / `#10b981`)**: Approved, Accredited, Certified, Active, Compliant.
   - 🟠 **Warning / Pending (`#d97706` / `#f59e0b`)**: Under Review, Pending Authorization, Awaiting Documents, SLA Due Soon.
   - 🔴 **Danger / Error (`#dc2626` / `#f87171`)**: Rejected, Declined, Terminated, Non-Compliant, Overdue.
   - 🔵 **Active / Info (`#0284c7` / `#38bdf8`)**: In Training, Inspection Scheduled, Progressing, Informational.
   - ⚪ **Neutral (`#64748b` / `#94a3b8`)**: Draft, Inactive, Uninitiated.
4. **State Reports, Actions Perform**: Status badges are strictly non-interactive pills (`pointer-events: none`). Action buttons perform operations and display descriptive imperative verbs (*Submit for Desktop Review*, *Approve Accreditation*, *Save Person*).

---

## 2. Design Tokens & Technology Mapping (UI-STANDARD.md Clause 1.2.1)

| Token Category | Light Mode Token | Dark Mode Token | Usage & CSS Variable |
| :--- | :--- | :--- | :--- |
| **App Bar Background** | `#0f172a` (Slate 900) | `#070a12` (Charcoal) | `--mud-palette-appbar-background` (56px height) |
| **Base Background** | `#f8fafc` (Slate 50) | `#0b0f19` (Dark Slate) | `--mud-palette-background` |
| **Surface** | `#ffffff` | `#111827` (Gray 900) | `--mud-palette-surface` |
| **Subtle Surface** | `#f1f5f9` | `#1f2937` (Gray 800) | `--mud-palette-background-grey` |
| **Primary (Brand Gold)**| `#b8860b` | `#e6b054` | `--mud-palette-primary` |
| **Text Primary** | `#0f172a` | `#f8fafc` | `--mud-palette-text-primary` |
| **Text Secondary** | `#475569` | `#94a3b8` | `--mud-palette-text-secondary` |
| **Border / Lines** | `#e2e8f0` | `#1f2937` | `--mud-palette-lines-default` |
| **Border Radius** | `6px` (`--nsdms-radius-md`) | `6px` | Standardized control and card radius |

---

## 3. Typography Scale & 8px Spatial Grid

- **Typeface**: `Inter`, -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif.
- **Tabular Numerals**: `font-variant-numeric: tabular-nums` enforced on all monetary amounts, dates, RSA ID numbers, and reference codes.
- **Heading Scale**:
  - `H1` (Page Title): `28px / 36px` bold (single H1 per route via `EntityHeader`).
  - `H2` (Major Section): `20px / 28px` semi-bold.
  - `H3` (Card / Modal Header): `16px / 24px` semi-bold.
  - `H4` (Subsection / Field Group): `14px / 20px` medium.
  - `Body1`: `14px / 20px` regular.
  - `Caption / Subtext`: `12px / 16px` muted text.
- **Spacing Rhythm**: Standard 8px spatial intervals (`4px`, `8px`, `16px`, `24px`, `32px`, `48px`).

---

## 4. Application Shell & Navigation Architecture

```
+-----------------------------------------------------------------------------------------------+
| [mS] merSETA NSDMS  Enterprise Portal     [/ Search tasks...]   [Bell:3]  [Kbd]  [Theme]  [Avatar]| 56px TopBar
+------------------+----------------------------------------------------------------------------+
| ≡ WORKFLOW       |  Breadcrumbs > Section > Entity Name                         [Action Zone] | EntityHeader
|  Executive Dash  +----------------------------------------------------------------------------+
|  Task Inbox (4)  |  [Workflow Stepper: Stage 1 -> Stage 2 -> Stage 3 (Active) -> Stage 4]      | WorkflowTracker
| ≡ REGISTRIES     +----------------------------------------------------+-----------------------+
|  People (SETMIS) |                                                    | RECORD CONTEXT        | Master-Detail
|  Employers       |  +-- FormSection: Identity & Demographics -------+ |  Status: In Review    | ContextRail
|  SDP Providers   |  | [13-Digit RSA ID] -> (Auto-Derive DOB/Gender) | |  SLA: Due in 3 days   | (Audit, SLA,
|  Curriculum      |  | [First Name] [Middle Name] [Last Name]        | |  Owner: QA Officer    | Governance)
| ≡ GRANTS & WSP   |  +-----------------------------------------------+ |  POPIA: Compliant     |
|  WSP Submissions |                                                    |                       |
|  DG Applications |  +-- FormSection: Washington Group Ratings ------+ |                       |
| ≡ FINANCE        |  | Seeing [01 None]  Hearing [01 None] ...       | |                       |
|  Grant MoAs      |  +-----------------------------------------------+ +-----------------------+
|  Banking Details |                                                                            |
|  Levy Rebates    +----------------------------------------------------------------------------+
|                  | [Back to List]                                     [Cancel]  [Save Record] | Sticky Action Bar
+------------------+----------------------------------------------------------------------------+
```

### Top Bar (56px Compact Elevation-0):
- MerSETA monogram brand emblem and product identity.
- Global search hint (`/` keybinding shortcut to tasks).
- Real-time SignalR notifications dropdown with unread badge and quick actions.
- Keyboard shortcut helper (`Ctrl+S`, `/`, `Esc`, `Tab`).
- Dark / Light mode instant switcher with persistent preference.
- User profile badge with role indicator.

### Sidebar Navigation:
- Categorized by functional domains (Workflow Orchestration, Core Registries, Grants & WSP, Finance & Disbursements, Learner Lifecycle, Quality Assurance, Skills Planning & BI, System Governance).
- Left gold accent indicator (`border-left: 3px solid var(--mud-palette-primary)`) on active route.
- Muted uppercase section labels (`.mud-navmenu-section-header`).

---

## 5. Workflow State Governance & Stepper Modernization

### Accreditation Workflow Resolution:
- **Sequential Flow**: `Draft Application` $\rightarrow$ `Desktop Review` $\rightarrow$ `Site Audit Scheduled` $\rightarrow$ `Committee Recommendation` $\rightarrow$ `Accredited / Issued`.
- **Alternative Terminal Outcomes**: `Rejected` or `Declined` is modeled as an **alternative terminal branch**, **never** as a 6th sequential step after Accredited.
- **Workflow Stepper Capabilities**:
  1. Displays linear progress with completed (green check), active (gold pulse), and upcoming (outlined gray) gates.
  2. Displays current owner, assigned role, date entered, and SLA countdown.
  3. Seamlessly integrates with `WorkflowActionBridge` directly in context ("What happens next").
  4. Mandatory justification modal on destructive / rejection actions with audit double-write.

---

## 6. Master-Detail & Data Grid Standards

1. **Clean DataGridShell**:
   - Fixed page size ladder (`5, 10, 20, 50, 100, 250, 500`), defaulting to `20`.
   - Real hyperlink on primary business identifier leading to stacked detail view.
   - Quick search input, structured filter selects, and removable active filter chips.
   - Bulk action bar with indeterminate select-all when rows are checked.
   - Five discrete states: Loading Skeleton, Populated Table, Empty Registry, Filtered-Empty, and Error with Retry.
2. **Stacked Detail View**:
   - Sticky top bar docking at `top: var(--mud-appbar-height, 56px) !important; z-index: 10;`.
   - Clear Back button (`Icons.Material.Filled.ArrowBack`), Cancel, and Save buttons with icon indicators.
   - ContextRail on right side capturing governance metadata, statutory dates, and audit trail.
   - Inline non-intrusive validation (e.g. RSA ID Luhn algorithm with auto-calculated indicators for Date of Birth, Gender, and Citizenship).

---

## 7. Vertical Rhythm & Spacing Tokens

| Spatial Boundary | Target Spacing | Implementation Standard |
| :--- | :--- | :--- |
| **Global Header → Page Content** | `20px–24px` | Controlled centrally by `MainLayout.razor` (`.nsdms-page-container pt-3`) |
| **Breadcrumb → Title** | `8px–12px` | Integrated in `EntityHeader` / `PageHeader` (`.nsdms-page-header`) |
| **Title → Subtitle/Description** | `4px–8px` | Integrated in `EntityHeader` / `PageHeader` |
| **PageHeader → Tabs / Content** | `16px–20px` | `.nsdms-page-header mb-4` |
| **Tabs → Table Workspace** | `16px` | `.nsdms-tab-nav mb-4` |
| **Toolbar → Table Content** | `12px–16px` | `.nsdms-table-toolbar` integrated in workspace surface |
| **Major Section Separation** | `20px–24px` | Standard MudGrid `Spacing="3"` |

---

## 8. Enterprise Navigation & Workspace Patterns

### 8.1 Segmented Navigation & Tabs:
- **Surface**: Neutral surface with transparent/subtle background.
- **Brand Accent**: merSETA gold is used **only** as a 2px active bottom underline (`border-bottom: 2px solid var(--nsdms-brand-gold)`) or subtle background tint on the active item.
- **Typography**: Sentence case (`My tasks`, `Unassigned`, `Completed`), 40–44px control height.
- **Count Badges**: Compact numeric pill (`.nsdms-tab-badge`) displaying quantity without visual noise.
- ❌ **Prohibition**: Large solid brand-color horizontal bars across the screen are strictly prohibited.

### 8.2 Unified Table Workspace Surface:
- **Cohesive Container**: The table toolbar, optional active filter chips, table content, and pagination must be encapsulated within a single `.nsdms-workspace-surface` card (`bg-surface border-default`).
- **No Disconnected Stacked Cards**: Avoid rendering separate filter cards, empty decorative cards, and table cards.
- **No Empty Toolbars / Placeholders**: Never render an empty filter card or toolbar container when no filters or actions are present.

### 8.3 Contextual Empty States:
1. **Empty Dataset**: Informative icon, clear heading, friendly subtext, and primary action button (e.g. *No active tasks — You're all caught up. Tasks assigned to you will appear here.*).
2. **No Filter Results**: Distinct filter-off icon, heading (*No matching records found*), and explicit *[Clear filters]* resolution button.

---

## 9. Compliance & Invariant Architectural Rules

- 🛑 **Centralized AppShell Height Invariant**:
  > **Rule**: "Pages MUST NOT compensate independently for the AppShell header height. Header offsets must be controlled centrally by AppShell/PageLayout."
  > Never add arbitrary `mt-16`, `pt-20`, or negative margins on individual pages to compensate for layout positioning.

- 🛑 **Brand Accent Surface Invariant**:
  > **Rule**: "merSETA gold is an accent colour and MUST NOT be used as a large content-area surface unless a documented design exception exists."
  > Gold is reserved for 2px active indicators, active sidebar borders, and focused CTAs.

- ❌ **No Clickable Badges**: Badges report state; buttons perform transitions.
- ❌ **No Rainbow Cards**: Color strictly reserved for status meaning and subtle brand accent.
- ❌ **No Modals for Core Editing**: Full-page Stacked Master-Detail routes (`/[resource]/[id]`) replace small cluttered dialogs.
- ❌ **No Unconnected Status**: Every workflow mutation performs an atomic double-write into `audit_logs` with before/after JSON snapshots.
- ❌ **No Empty Decorative Containers**: Never leave empty bordered cards or placeholder boxes in production UI.

---

## 10. Multi-Step Wizard Architecture & Interaction Standard

### 10.1 The 15 Principles (House Rules)
1. **One decision per step**: Each step focuses on a single semantic task.
2. **Progressive disclosure**: Dependent options appear only after their controlling choice.
3. **Dependency-ordered sequence**: Canonical order: Controlling choice → Data input → Review & lifecycle decisions.
4. **Smart defaults, deferred decisions**: Never pre-check statutory legal consents or hardcode mock decision IDs.
5. **Visible, meaningful progress**: Stepper shows state (completed, current, upcoming) with concise labels (<= 3 words); no live progress counts.
6. **Freedom to leave and return**: "Save draft" available from Step 2 onward; Back never loses input state.
7. **Single clear primary action**: Exactly one Next / Finish action button; exactly one Cancel button.
8. **Concise task-oriented copy**: Zero explanatory essays or parenthetical system commentary in field labels.
9. **Consistency with the product**: Wizard steps and the entity edit view share identical field definitions.
10. **Chunking**: <= 7 inputs per step, grouped under semantic headings.
11. **Reduced choice load**: Few, highly differentiated options.
12. **Per-step validation**: Inline field errors; Next action blocked until the active step passes validation.
13. **Review before commit**: Comprehensive read-only review step with grouped cards and per-step "Edit" links.
14. **Visual consistency**: Helper-text rows level, single theme color for boolean controls (`Color.Primary`), WCAG 2.2 AA contrast in both Light and Dark themes.
15. **Accessibility**: Semantic `<ol>` with `aria-current="step"`, accessible button labels, programmatic focus moves to step heading on activation.

### 10.2 Component Anatomy & Footer Layout
- **Shell**: `<WizardShell>` (`Components/Shared/Wizard/WizardShell.razor`)
- **Stepper**: `<WizardStepper>` (`Components/Shared/Wizard/WizardStepper.razor`)
- **Step Container**: `<WizardStep>` (`Components/Shared/Wizard/WizardStep.razor`)
- **Review Step**: `<WizardReviewStep>` (`Components/Shared/Wizard/WizardReviewStep.razor`)
- **State Machine**: `WizardState<TModel>` (`Components/Shared/Wizard/WizardState.cs`)
- **Enforced Footer Layout**:
  `[Cancel]` (left) | `[Save draft]` (left-centre, hidden on Step 1) | `[Back]` `[Next / Finish]` (right).

### 10.3 Build-Time Guard ([NSDMS0001])
Direct usage of raw library stepper components (`MudStepper`, `MudStep`) outside `Components/Shared/Wizard` is prohibited and halts build execution with diagnostic `[NSDMS0001]`.

### 10.4 Strategic Wizard Candidate Matrix & Approved Step Structures

The Enterprise Multi-Step Wizard architecture (`<WizardShell>`) applies to high-cognitive-load, multi-party, and dependency-ordered statutory actions. The table below codifies the approved candidate matrix and canonical step specifications:

| # | Flow / Wizard | Route | Canonical Steps | Review Step Content |
| :---: | :--- | :--- | :--- | :--- |
| **1** | **Assessor Re-Registration Wizard** (`AssessorReRegistrationWizard.razor`) | `/etqa/assessors/{id}/re-register` | 1. Verification of Profile<br>2. Unit Standards Selection<br>3. Continuous Professional Development (CPD)<br>4. Declaration & Review | Read-only profile cards, unit standard code chips, CPD points summary against 30-point statutory threshold, POPIA compliance checkbox, legal declaration, and per-step "Edit" jump buttons. |
| **2** | **Discretionary Grant (DG) Application Wizard** (`DgGrantApplicationWizard.razor`) | `/dg-grants/apply` | 1. Funding Window & Eligibility<br>2. Organisation & Appointed SDF<br>3. Strategic PIVOTAL Programmes<br>4. PIP & Budget Allocation<br>5. Compliance Documents<br>6. Statutory Declaration & Review | Read-only summary cards per section, funding envelope verification, calculated requested vs available headroom, B-BBEE and tax clearance statuses, POPIA declaration, and per-step "Edit" jump links. |
| **3** | **Tripartite Learnership / Apprenticeship Agreement Wizard** (`LearnerAgreementRegistrationWizard.razor`) | `/learners/register-agreement` | 1. Learner Demographics & Identification<br>2. Qualification & OFO Selection<br>3. Host Employer & SDP Allocation<br>4. Employment Terms & Stipend<br>5. Tripartite Attestation & Review | Demographic validation summary (RSA ID, DOB, gender, equity), SAQA qualification chips, accredited SDP campus, mentor link, monthly stipend schedule, dynamic QR code verification stub, and per-step "Edit" jump links. |
| **4** | **Workplace Approval (WPA) Application Wizard** (`WorkplaceApprovalWizard.razor`) | `/workplace-approvals/apply` | 1. Workplace Site & Mandatory Contact Person<br>2. Designated Trades Scope<br>3. Nominated Mentors & Quota Assessment<br>4. Tooling & OHS Compliance<br>5. Review & Inspection Dispatch | Site details, validated Contact Person (`contact_person_id` invariant), designated trade scope, calculated apprentice capacity via `IMentorRatioPolicyEngine` (1:4 standard or trade policy), tooling audit checklist, and per-step "Edit" jump links. |
| **5** | **Trade Test Application & ARPL Submission Wizard** (`TradeTestApplicationWizard.razor`) | `/trade-tests/apply` | 1. Candidate Route Selection<br>2. Curricular & Theory Clearance<br>3. Workplace Practical Hours Verification<br>4. Accredited Trade Test Centre (TTC)<br>5. Review & Pre-Trade Clearance | Route type (Contracted vs ARPL Section 28), verified N2/NCV credits, employer logbook attestation ($\ge 80$ weeks), accredited TTC selection, fee subsidy verification, and per-step "Edit" jump links. |
| **6** | **DG Tranche Milestone Claim & Payment Voucher Wizard** (`DgTrancheClaimWizard.razor`) | `/finance/claims/create` | 1. MoA & Milestone Selection<br>2. Learner Milestone Deliverables<br>3. Financial Tranche Breakdown<br>4. Audit Verification Evidence<br>5. Financial Attestation & Voucher Review | MoA and tranche balance, remaining budget headroom, selected learner deliverable headcount, financial approval tier (>R500k CFO gate), serialized payment voucher preview, and per-step "Edit" jump links. |
| **7** | **SDP Initial Institutional Accreditation Wizard** (`SdpAccreditationApplicationWizard.razor`) | `/providers/apply-accreditation`, `/sdp/apply-accreditation`, `/sdp/create` | 1. Provider Profile & Legal Entity<br>2. Proposed Scope of Accreditation<br>3. Staffing (Assessor/Moderator)<br>4. QMS Policies & Self-Audit<br>5. Review & Desktop Audit Dispatch | CIPC registration, physical campus, qualification scope chips, linked ETQA registered practitioners, statutory QMS policy checklist, and per-step "Edit" jump links. |
| **8** | **Mandatory Grant WSP/ATR Annual Submission Wizard** (`WspAtrSubmissionWizard.razor`) | `/wsp/submit`, `/wsp/create` | 1. Organisation & Payroll Standing<br>2. ATR Actual Training Completed<br>3. WSP Planned Training Interventions<br>4. Training Committee Consultation<br>5. Quorum Alignment & SETMIS Pre-Flight<br>6. Review & Statutory Seal | Payroll reconciliation, ATR/WSP summary grids by OFO code and equity, committee minutes upload, bipartite (<50) or tripartite ($\ge$50) quorum sign-off structure, 20% grant rebate calculation, and per-step "Edit" jump links. |
| **9** | **Inter-SETA Transfer Application (Section 32)** (`InterSetaTransferWizard.razor`) | `/inter-seta/transfer-request`, `/inter-seta/create` | 1. Employer Profile & SDL Verification<br>2. Transfer Direction & Justification<br>3. SARS SIC Code Corroboration<br>4. Review & Section 32 Gazette Package | Current SETA vs destination SETA, 5-digit SIC code reclassification, SARS EMP103/201 corroboration, stakeholder endorsements, and per-step "Edit" jump links. |

### Wizard Exceptions
None. All multi-step flows strictly adhere to `WizardShell`.

---

## 11. Revision History & Changelog
- **v1.6 (2026-09-05)**: Full completion of all 9 strategic wizard candidates in the Enterprise Multi-Step Wizard Matrix (Section 10.4). Deployed Candidate #7 (`SdpAccreditationApplicationWizard.razor`) and Candidate #9 (`InterSetaTransferWizard.razor`) with shared forms (`SdpAccreditationFields`, `InterSetaTransferFields`), QCTO institutional accreditation self-audit checklist, Section 32 SIC code corroboration, and bUnit parity test coverage.
- **v1.5 (2026-09-05)**: Deployed Candidate #8 (`WspAtrSubmissionWizard.razor`) with shared form (`WspAtrSubmissionFields.razor`), bipartite/tripartite sign-off quorum constitution based on workforce headcount, 20% Mandatory Grant levy rebate estimation, SETMIS File 500 pre-flight validation, and bUnit parity tests.
- **v1.4 (2026-09-05)**: Deployed Candidates #5 (`TradeTestApplicationWizard.razor`) and #6 (`DgTrancheClaimWizard.razor`) with shared forms (`TradeTestApplicationFields`, `DgTrancheClaimFields`), CFO executive dual authorization gating, serialized payment vouchers (`PV-{yyyy}-DG-{id:D5}`), and bUnit parity tests.
- **v1.3 (2026-09-04)**: Codified and deployed the **Strategic Wizard Candidate Matrix** (Section 10.4). Implemented candidates #2 (`DgGrantApplicationWizard`), #3 (`LearnerAgreementRegistrationWizard`), #4 (`WorkplaceApprovalWizard`), and built the administrative `WizardCandidateMatrixHub` (`/wizards`). Added automated discovery and bUnit parity tests for all newly registered multi-step wizards.
- **v1.2 (2026-09-04)**: Installed Enterprise Multi-Step Wizard Standard (Section 10). Implemented shared components (`WizardShell`, `WizardStepper`, `WizardStep`, `WizardReviewStep`), build-time guard `[NSDMS0001]`, rebuilt `AssessorReRegistrationWizard` with zero raw stepper tags, added bUnit parity tests and automated discovery tests.
- **v1.1**: Added SARS Monthly Levy Reactive Streaming & SqlBulkCopy Staging Standard.
- **v1.0**: Initial baseline enterprise design system.
