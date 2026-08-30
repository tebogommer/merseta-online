# Migration note — UI Standard 0.1 → 0.5

| Field | Value |
| --- | --- |
| Applies to | Any application built against ICT-STD-UI-001 v0.1 or later |
| Target version | 0.5 |
| Effort | Days for the model change; hours per screen thereafter |

Steps 0–6 cover 0.1 → 0.2. Step 7 covers everything added in 0.3, 0.4, and 0.5. An application already on 0.2 can start at step 7.

Work in this order. Each step corrects many screens at once; doing screens first wastes the work.

---

## Step 0 — Housekeeping (30 minutes)

- Replace `UI-STANDARD.md` in each repository with v0.5.
- Add the **technology mapping note** required by 1.2.1 — a short block in the README or `DESIGN.md` stating, for this stack: what implements the section 9 components, what provides virtualisation, what provides the unsaved-changes guard, and **which version of the standard the application was built against**.
- Update the agent instruction file (`AGENTS.md` / `CLAUDE.md`) so it references v0.5 and the new checklist blocks.

**Clause renumbering.** In 0.2, sections formerly 12–16 became 13–17. In 0.4, the former 11.9 (consuming lookup data in forms) became 11.10. Any pull request template, review comment, or agent instruction citing old clause numbers above 11 must be updated. Sections 1–11 are unchanged in number.

---

## Step 1 — Decide the axis question (blocking; do this first)

Section 7.4 splits what v0.1 treated as one status field into **workflow state**, **status**, and **flags**. Nothing in the UI can display two axes that the database stores as one.

For each workflow, list the current status values and sort them:

| Ask | If yes | Goes to |
| --- | --- | --- |
| Can I draw an arrow from this value to another? | Ordered position | **Workflow state** |
| Is it a condition that can apply at more than one position? | Unordered condition | **Status** |
| Can several apply at once, independently? | Independent circumstance | **Flag** |

A typical outcome, whatever the domain:

- An ordered path such as *Submitted, Validated, Review, Approval, Completed, Declined* → workflow state
- *Awaiting documents, On hold, Compliant* → status
- *Overdue, Escalated, Under appeal, Inspection required* → flags

Then decide the data change:

- **One axis only** — legitimate. Keep the single enum, use it as workflow state, and skip to Step 2. Do not invent a second axis for the sake of it (7.4.1).
- **Two axes conflated** — add a nullable `Status` column alongside the existing state enum, plus a flags table or bitfield. Write a one-off migration that splits existing values, and keep a mapping table in the repository so historical records and reports stay explicable.

Also record, per workflow: which statuses **block progression** (needed for 7.4.3.7) and which states are **terminal-negative** (needed for 7.4.3.6).

---

## Step 2 — Extend the shared component set

Three new contracts in section 9. Build them before touching pages.

| Component | Notes |
| --- | --- |
| `WorkflowStepper` | Ordered path. Non-interactive. Must handle terminal truncation and blocked-step marking itself, so no page has to. |
| `StatCard` | One figure. Surface not clickable. Owns its skeleton and error state. |
| `StatCardRow` | Equal-height grid, maximum four. |

Also amend two existing components:

- `EntityHeader` — accept `Status` and `Flags` as separate inputs from `WorkflowState`, and render them in the fixed order state → status → flags.
- `DataGridShell` — add the **error** state; v0.1 required four grid states, v0.2 requires five (11.5).

The reference implementation (`ui-standard-mock.html`) is the visual reference for all of these, including the blocked, terminal, and returned stepper variants. It is built on a neutral sample domain — substitute your own entity vocabulary.

---

## Step 3 — Reference data consolidation (11.8)

This is the largest structural change if the application has lookup tables with their own menu entries and CRUD pages.

1. Create one **Reference data** area with an index of sets.
2. Move each lookup into it, conforming to the standard table shape: Code, Description, Parent (if hierarchical), Sort order, Active, Last updated.
3. Replace per-lookup detail pages with the drawer editor. Delete the old routes.
4. Add the usage count and switch delete to **deactivate** for any referenced value.
5. Audit the consuming controls against 11.9: active values only when creating; inactive values still displayed on existing records marked *(inactive)*; ordering by sort-order not primary key; reference by code, not numeric identifier.

Item 5 is the one that turns up real data defects — a blanked field on a historical record is a silent data loss the users may not have reported.

---

## Step 4 — Performance pass (section 12, new)

For each list screen, in this order:

1. Confirm the grid binds to a **projection DTO**, not a full entity.
2. Confirm paging, sorting, filtering, and counting execute in the database. Anything doing fetch-all-then-filter is a defect at any current row count.
3. Add indexes for every sortable and filterable column; note them in the pull request.
4. Add virtualisation or windowing where more than 100 rows render.
5. Debounce type-ahead at 300 ms, and ensure a stale response can never overwrite a newer one.
6. Cache lookup sets server-side, invalidated by the reference data screen on save.
7. Measure against the 12.1 budgets **on a representative dataset**, not the development database.

---

## Step 5 — Screen-level changes

Now the per-page work. Worst screens first.

**Every detail page (A3)**
- Header renders state → status → flags in that order, exactly one state badge.
- Add the stepper beneath the header for any workflow with more than three states.
- Verify terminal-negative records truncate the path and show the reason.
- Verify a blocking status marks the active step, not only the header.

**Every list (T1/T2)**
- Declare the tier.
- Add the fifth grid state (error with retry).
- Move filter, sort, and page state into the URL query string (11.4.4).
- Restore list position on return from a detail page (11.3.5).
- Confirm the result count reflects the filtered set and the active sort is visible.

**Dashboards and list summaries**
- Replace ad-hoc tiles with `StatCard`.
- Card surfaces not clickable — drill-down is a *View all* link.
- Trend colour follows meaning, not direction.
- Card figures and the list they link to share one query definition.

---

## Step 6 — Re-baseline the review gate

- Replace the pull request checklist with section 16 of v0.2 (it gains Lists, Reference data, Performance, Stat cards, and workflow blocks).
- Run an audit pass per screen: *"Audit this page against UI-STANDARD.md v0.2 section 16 and list every failure with its clause number."* Agents audit against a checklist far more reliably than they remember rules while generating.

---


---

## Step 7 — Changes introduced in 0.3, 0.4, and 0.5

### 7a. Application shell and navigation (4.5, new in 0.3)

- Primary navigation becomes a **left panel**, collapsible to an icon rail, with the collapsed state persisted per user. A top horizontal menu bar, or a hamburger-only menu on desktop, no longer satisfies the standard.
- Collapsed items must still reveal labels on hover **and keyboard focus**.
- Navigation depth is capped at two levels, and the panel must contain no actions.
- Reference data occupies exactly one navigation entry.
- New shared components: `AppShell`, `NavPanel`.

### 7b. Responsive behaviour (4.6, new in 0.3)

- Three breakpoints: compact (≤640), medium (641–1024), expanded (≥1025).
- Grids become **stacked cards** below the compact breakpoint; they are not shrunk.
- Stepper goes vertical; action zone stacks with the primary action first and full width; drawers go full width; touch targets reach 44 px.
- Every page must be verified at **360 px, 768 px, and 1440 px**.

**Two implementation traps, both encountered in practice:**

1. Setting `overflow` on `html` or `body` creates a scroll container that **silently disables `position: sticky` on every descendant**. If the header or nav panel will not stick, this is almost always why. Contain wide elements at source instead, and treat page-level overflow as a backstop.
2. Reserving the nav panel's width as a **margin on the content column** is fragile — any later width rule on that column will let content slide under a fixed panel. Reserve the width **on the shell**, as padding or a layout track, so the content region is sized from what remains.

Also: do not reuse CSS class names that the framework in use already defines. A sidebar named `.nav` collides with Bootstrap's `.nav` component.

### 7c. Data table baseline (11.9, new in 0.4)

Thirteen required capabilities. The ones most likely to be missing from an existing grid:

- **Page size ladder** — 5 / 10 / 20 / 50 / 100 / 250 / 500, default 20, persisted, and still meeting the 12.1 budgets at the top of the ladder (virtualise rather than render 500 rows eagerly).
- **Column chooser** — with reset; reference and workflow state columns not hideable; hidden columns dropped from the query projection; export follows the visible set.
- **View preferences persisted per user and per grid**, keyed by a stable grid identifier and **not** by page URL, which changes with filters. They must survive the session. A preference naming a since-removed column must be ignored silently.
- **Row selection** — checkbox column first, header select-all with indeterminate state, selection beyond the current page always explicit, selection cleared when the filter changes.

### 7d. Unique key hyperlink (11.6.3, new in 0.5)

The column carrying the primary or natural key must be a **real anchor** to the record's View route — not a cell click handler — so it can be opened in a new tab, copied, bookmarked, and reached by keyboard. Row click may remain as a convenience on top. Where the entity has no business key, the link goes on the name column. Exactly one such link per row.

### Sequencing for 7

| Order | Work |
| --- | --- |
| 1 | Shell and navigation (7a) — touches every page's layout, so do it before per-page work |
| 2 | Responsive rules (7b) — same reason |
| 3 | `DataGridShell` extension for the baseline (7c) and the key hyperlink (7d) — one component change delivers both to every grid |
| 4 | Per-screen verification at the three widths |

---

## Suggested sequencing (0.1 → 0.2 waves)

| Wave | Contents | Unblocks |
| --- | --- | --- |
| 1 | Steps 0–1 | Everything else |
| 2 | Step 2 | Steps 5 |
| 3 | Step 4 | Independent — can run in parallel with 2 |
| 4 | Step 3 | Independent — largest single chunk |
| 5 | Steps 5–6 | Final |

Waves 3 and 4 do not depend on each other and can run concurrently if more than one person is working.
