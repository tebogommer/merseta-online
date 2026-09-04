# AGENTS.md

<!-- UI-STANDARD-RULES:START -->
## UI Rules (.NET 10 Blazor + MudBlazor)

`BLAZOR-MUDBLAZOR-DESIGN-SYSTEM.md` in this repository governs every page, dialog, and shared UI component. Treat it as normative — not as advice.

**Before generating any page:**
1. State which archetype it implements (A1 List, A2 Work Queue, A3 Entity Detail, A4 Form/Wizard, A5 Dashboard, or T3 Reference Data).
2. Check the shared component set in `Components/Shared/`. Use those components; do not hand-write custom badges, headers, or grid wrappers.
3. Check the technology mapping note for this application's entity vocabulary.

**Strict Rules that must never be breached:**
- **View by default (Strict):** Records MUST open in read-only **View** mode (`/{entity}/{id}`) displaying `<ReadOnlyField>` components. Never render editable inputs (`<MudTextField>`), dropdowns, or Save buttons on `/{entity}/{id}`. Edit is strictly a separate route (`/{entity}/{id}/edit`) wrapped in `<FormShell>`. Never combine View and Edit into a single file without explicit read-only display state.
- **Authentication & Authorization:** Every interactive page must declare `@attribute [Authorize]` or `@attribute [Authorize(Roles = "...")]`. Router must use `<AuthorizeRouteView>`. Minimal APIs must declare `.RequireAuthorization()`.
- **Zero Raw Database Access in UI:** Razor components MUST NEVER inject `DbContext` or `IDbContextFactory`. All data fetching and mutations must pass through application service interfaces.
- **Unbounded Query Prohibition:** Never call `.ToListAsync()` without `.Take()` or server-side pagination. In-memory aggregation of full tables in services is strictly prohibited.
- **Destructive Action Confirmation:** Deleting any record must require an affirmative confirmation dialog via `IDialogService.ShowAsync<ConfirmDialog>()`. Immediate unconfirmed deletion is prohibited.
- **Theme Token Invariant:** Hardcoded hex color codes (`#...`) in components and inline `style="..."` attributes for layout/colors are strictly prohibited. All colors and spacing must use MudBlazor theme variables.
- **State reports, actions perform:** A badge is never clickable; a button never displays a value.
- **Workflow axes:** Workflow **state**, **status**, and **flags** are three distinct concepts. Exactly one state badge per page.
- **Central transitions:** Transition actions come from the shared transition service, never from conditions hardcoded in a page.
- **Data Table Baseline:** Every list meets the 13-point data table baseline via `<DataGridShell>` with standard 7-tier page sizes (`5, 10, 20, 50, 100, 250, 500`).
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
   - Any compliance violation must immediately throw `SarsComplianceException`, aborting ingestion with zero database modifications and returning a line-by-line forensic diagnostic log.---

## Option and lookup controls

The Option and Lookup Controls Standard v1.1 (Rules E.1 and E.2, OPT-001 through OPT-011) governs all option, lookup, and status controls across this organization:
- **OPT-001**: Status, Workflow State, and Lifecycle fields MUST NOT be bound to user-editable option controls; they must render as read-only text or `<StatusBadge>`.
- **OPT-002**: In View mode, every option field MUST render as text or badge (`<ReadOnlyField>`). Disabled inputs are prohibited.
- **OPT-003 / OPT-004**: Lists > 15 items MUST use searchable comboboxes (`<SearchableLookup>`) debounced $\ge 300\text{ms}$ with min 2 chars and capped results ($\le 50$); entity references and lists > 500 MUST use `<EntityLookupDialog>`.
- **OPT-005**: Reference data MUST be served from cached scoped services with declared TTL expiry (`LookupService`).
- **OPT-006**: 2–5 exclusive record values MUST use radio groups enclosed in `<fieldset><legend>`.
- **OPT-010**: Cascading lookups MUST clear and disable children when the parent value is empty.
- **OPT-011**: All lookups MUST be registered in `declarations.yml`.

