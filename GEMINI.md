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

