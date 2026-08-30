# MerSETA NSDMS Enterprise Design System & Architecture Specification

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
