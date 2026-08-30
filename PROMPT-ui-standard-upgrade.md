# Prompt — bring this codebase up to the current UI Standard

> Paste the block below into Claude Code, Antigravity, or any coding agent, after you have replaced `UI-STANDARD.md` in the repository. Everything under the line is the prompt.

---

You are upgrading this application to comply with `UI-STANDARD.md`, which has just been replaced with a newer version.

## Ground rules

1. **`UI-STANDARD.md` in this repository is the single source of truth.** Read it in full before doing anything else. Where this prompt and the standard disagree, the standard wins. Do not rely on any version of it you may remember.
2. **Audit before you change anything.** Phase 1 produces a report and nothing else. Do not edit a single file until I have approved the plan.
3. **Cite clause numbers.** Every finding and every change references the clause it satisfies, e.g. "opens in Edit mode — violates 5.2.1".
4. **Never invent a pattern.** If the standard does not cover something you need, stop and ask. Do not improvise a layout, a status value, a button style, or a component.
5. **Ask before any data model change.** Schema changes are proposed, never applied unilaterally.
6. **Work in small, reviewable commits**, one concern per commit, each naming the clauses it addresses.

## Phase 1 — Audit

Do not modify anything in this phase.

**1.1 Establish the baseline.**
- Read `UI-STANDARD.md`. Note its version from the header block.
- Find the technology mapping note (clause 1.2.1) — usually in `README.md`, `DESIGN.md`, or `AGENTS.md`. Note which standard version this application was last built against. If there is no mapping note, record that as finding #1.
- Read `UI-STANDARD-MIGRATION.md` if present, to see what changed between the two versions.

**1.2 Inventory the codebase.**

Produce a table of every page/route with:

| Route | Archetype (A1–A5 / T3) | Page mode handling | Components used | Notes |

Then list: shared UI components that exist today, every status/state enumeration and where it is defined, every lookup/reference table and how it is maintained, and the shell/layout files.

**1.3 Audit against the standard.**

Work through the compliance checklist in the standard's final checklist section, applied to each page. For each finding record: file and line, the clause breached, severity, and the fix in one sentence.

Severity:
- **Blocker** — data model or shared infrastructure must change before pages can comply
- **Major** — a rule is breached in a way users will notice
- **Minor** — cosmetic or consistency

Pay particular attention to the defects this standard exists to prevent:
- Records opening in an editable state instead of View
- Status badges that are clickable, or buttons used to display state
- Workflow state, status, and flags conflated into one field or one badge
- Transition buttons rendered from conditions written into pages instead of a shared service
- Lookup tables built as full entity CRUD instead of reference-data maintenance
- Grids missing capabilities from the data table baseline
- Layout that breaks at narrow widths, or a content column that sits underneath the navigation panel

**1.4 Report.**

Output, in this order:

1. **Version delta** — from version X to version Y, and the sections that changed.
2. **Findings table**, sorted Blocker → Major → Minor.
3. **Open questions** — anything you cannot resolve without a decision from me. Ask these explicitly; do not guess.
4. **Proposed plan** — ordered work packages, following Phase 2 below, with an estimate of files touched per package.

**Then stop and wait for my approval.**

## Phase 2 — Remediate, in this order

Each package corrects many screens at once. Do not start per-page work until packages 1–4 are done, or the work will be redone.

**Package 1 — Model and infrastructure (blockers).**
- If the standard now distinguishes workflow state, status, and flags and this application conflates them: propose the split, list which existing values fall into which axis, identify which statuses block progression and which states are terminal-negative, and write the migration. Apply only once I approve.
- Ensure each workflow's states live in exactly one enumeration, and that transition legality lives in exactly one shared service.

**Package 2 — Shared components.**
- Implement or update every component the standard's component-contract section requires, using the names and inputs it specifies. Implementation is per-stack; the contract is not.
- Where the mapping note names an existing library, extend it rather than creating a parallel set.
- These components are where the standard is enforced. Getting them right removes most findings without touching pages.

**Package 3 — Shell, navigation, and responsive layout.**
- Bring the application shell into line: the navigation panel, its collapse behaviour and persistence, and the breakpoints.
- Namespace shell and layout class names so they cannot collide with the CSS framework in use.
- Two traps worth checking explicitly, because both are easy to introduce and hard to trace:
  - Setting `overflow` on `html` or `body` creates a scroll container and silently disables `position: sticky` on every descendant.
  - Reserving the navigation panel's width as a margin on the content column is fragile — any later width rule lets content slide underneath a fixed panel. Reserve it on the shell instead, so the content region is sized from the space that remains.

**Package 4 — Data tables and reference data.**
- Bring every list up to the data table baseline through the shared grid component, so all grids gain the capabilities together.
- Consolidate lookup maintenance into the reference-data pattern and delete the routes it replaces.
- Audit the consuming controls: active values only when creating, existing records still showing deactivated values marked as such, ordering by the sort field, and references by code rather than numeric identifier. This step surfaces genuine data defects, not just cosmetic ones — report anything you find.

**Package 5 — Performance.**
- Apply the standard's query and rendering rules to every list: projection DTOs, database-side paging/sorting/filtering/counting, index coverage for sortable and filterable columns, virtualisation thresholds, debounced and cancel-safe type-ahead.
- Note any index you assume but cannot verify.

**Package 6 — Per-screen cleanup.**
- Now work page by page, worst first, against the checklist.
- For each page, state which archetype it implements before you change it.

**Package 7 — Gate and verify.**
- Update the pull request template to the standard's current checklist.
- Update `AGENTS.md` / `CLAUDE.md` to reference the new version.
- Update the technology mapping note: standard version, component library, virtualisation mechanism, unsaved-changes guard, and the application's entity vocabulary.
- Verify each page at the widths the standard requires.

## Output format per package

For each package, before making changes:

```
PACKAGE n — <name>
Clauses addressed: <list>
Files to change: <list>
Risk: <what could break>
```

After changes: what you changed, what you could not, and anything you deferred with the reason.

## Stop and ask when

- A change requires a schema or data migration
- The standard is silent on something you need
- A fix would break an existing integration, export, or report
- A page's correct archetype is genuinely ambiguous
- You find a data defect (blanked fields, substituted lookup values, lost history)
- A rule appears impractical for this application — say so rather than implementing it badly or skipping it silently

## Do not

- Do not restyle for taste. Only changes traceable to a clause.
- Do not renumber, reword, or "improve" `UI-STANDARD.md`. If you believe a clause is wrong, report it; do not edit it.
- Do not add dependencies without asking.
- Do not mark a page complete until it passes the full checklist.
- Do not batch unrelated fixes into one commit.

Begin with Phase 1.
