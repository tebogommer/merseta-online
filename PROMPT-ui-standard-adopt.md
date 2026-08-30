# Prompt — adopt the UI Standard in a project that has never had it

> Use this when `UI-STANDARD.md` is being introduced for the first time — either a brand-new project, or an existing application that has never been governed by it. If the project already has an older version of the standard, use `PROMPT-ui-standard-upgrade.md` instead.
>
> Before pasting: copy `UI-STANDARD.md` into the repository root. Copy `ui-standard-mock.html` too if you want the agent to have a visual reference.
>
> Everything under the line is the prompt.

---

You are introducing `UI-STANDARD.md` into this project for the first time.

## Ground rules

1. **`UI-STANDARD.md` in this repository is the single source of truth.** Read it in full before doing anything else. Do not rely on any version you may remember.
2. **Establish which situation this is** before planning:
   - **Greenfield** — little or no UI exists yet. Follow Track A.
   - **Existing application** — screens already exist and were built without the standard. Follow Track B.
   State which track you are on and why, and stop if it is genuinely ambiguous.
3. **Cite clause numbers** for every decision and every change.
4. **Never invent a pattern.** If the standard does not cover something you need, stop and ask. Do not improvise a layout, a status value, a button style, or a component.
5. **Ask before any data model change.**
6. **Do not restyle for taste.** Only changes traceable to a clause.

---

## Step 0 — Foundations (both tracks)

Do this first, whichever track applies. Nothing else works without it.

**0.1 Technology mapping note.** Create it (clause 1.2.1) in `README.md` or `DESIGN.md`, recording:
- Which version of the standard this application is built against
- Which component library or module implements the component contracts
- Which mechanism provides virtualisation and which provides the unsaved-changes guard
- **The entity vocabulary** — the domain nouns this application uses for its records, routes, and workflow steps

The vocabulary matters more than it looks. The standard is domain-neutral by design; this note is what binds it to *this* application, and it is what keeps terminology consistent across screens, exports, and notifications.

**0.2 Design tokens.** Confirm `DESIGN.md` (or equivalent) exists and defines colour values, typography, and spacing. The standard references tokens but does not define them. If it does not exist, propose a minimal one — the semantic groups the standard's status taxonomy requires, plus base type and spacing — and ask before creating it.

**0.3 Agent instructions.** Add the block from `AGENTS-ui-block.md` to `AGENTS.md` / `CLAUDE.md`, so future generation is constrained by the standard rather than by this one-off pass.

**0.4 Review gate.** Put the standard's compliance checklist into the pull request template.

Report what you created or found, then continue.

---

## Track A — Greenfield

Build the enforcement layer before building screens. A standard with no shared components is advice; the agent will fall back to raw markup because there is nothing to reach for.

**A1 — Workflow model.** For each entity that moves through a process, define in one place: the ordered workflow states, the statuses, the flags, which statuses block progression, which states are terminal-negative, and which transitions are legal from which state for which role. Propose this and get approval before writing code — everything downstream depends on it.

**A2 — Shared components.** Implement every component the standard's component-contract section requires, with the names and inputs it specifies. Implementation is per-stack; the contract is not. Use the reference implementation as the visual guide if it is present.

**A3 — Shell and layout.** Build the application shell, navigation panel, and responsive behaviour once, as the layout every page sits inside.

**A4 — One reference page per archetype.** Before building the real screens, build one worked example of each archetype the application needs, and have me review them. These become the template every later page is copied from — getting them right is the highest-leverage step in this whole process.

**A5 — Then build screens.** State the archetype before creating each page. Run the checklist before calling any page done.

---

## Track B — Existing application

Retrofitting page by page wastes work. Each package below corrects many screens at once.

**B1 — Audit first, change nothing.**

Produce a table of every page/route:

| Route | Archetype (A1–A5 / T3) | Page mode handling | Components used | Notes |

Then list: shared UI components that exist today, every status/state enumeration and where it is defined, every lookup/reference table and how it is maintained, and the shell/layout files.

Then work through the standard's compliance checklist against each page. For each finding record file and line, the clause breached, severity, and the fix in one sentence.

- **Blocker** — data model or shared infrastructure must change before pages can comply
- **Major** — a rule is breached in a way users will notice
- **Minor** — cosmetic or consistency

Expect a lot of findings. An application built without the standard will breach it broadly; that is the normal starting position and not a reason to soften the audit.

Pay particular attention to:
- Records opening in an editable state instead of View
- Status badges that are clickable, or buttons used to display state
- Workflow state, status, and flags conflated into one field or one badge
- Transition buttons rendered from conditions written into pages instead of a shared service
- Lookup tables built as full entity CRUD instead of reference-data maintenance
- Grids missing capabilities from the data table baseline
- Layout that breaks at narrow widths, or a content column sitting underneath the navigation panel

Report: findings table sorted by severity, open questions requiring my decision, and a proposed plan following B2–B7. **Then stop and wait for approval.**

**B2 — Model and infrastructure (blockers).** Split conflated axes if needed; one enumeration per workflow; transition legality in one shared service. Propose migrations, apply only once approved.

**B3 — Shared components.** Implement the contracts, then replace hand-written markup with them. This removes most findings without touching page logic.

**B4 — Shell, navigation, responsive layout.** Namespace shell and layout class names so they cannot collide with the CSS framework in use. Two traps worth checking explicitly:
- Setting `overflow` on `html` or `body` creates a scroll container and silently disables `position: sticky` on every descendant.
- Reserving the navigation panel's width as a margin on the content column is fragile — any later width rule lets content slide underneath a fixed panel. Reserve it on the shell, so the content region is sized from the space that remains.

**B5 — Data tables and reference data.** Bring lists to the data table baseline through the shared grid component. Consolidate lookup maintenance and delete the routes it replaces. Audit consuming controls — this surfaces genuine data defects, not just cosmetic ones. Report anything you find.

**B6 — Performance.** Projection DTOs, database-side paging/sorting/filtering/counting, index coverage, virtualisation thresholds, debounced and cancel-safe type-ahead. Note any index you assume but cannot verify.

**B7 — Per-screen cleanup, then verify.** Worst pages first, against the checklist. Verify each page at the widths the standard requires.

---

## Output format per package

Before making changes:

```
PACKAGE <id> — <name>
Clauses addressed: <list>
Files to change: <list>
Risk: <what could break>
```

After: what you changed, what you could not, and anything deferred with the reason.

## Stop and ask when

- A change requires a schema or data migration
- The standard is silent on something you need
- A fix would break an existing integration, export, or report
- A page's correct archetype is genuinely ambiguous
- You find a data defect (blanked fields, substituted lookup values, lost history)
- A rule appears impractical for this application — say so rather than implementing it badly or skipping it silently

## Do not

- Do not renumber, reword, or "improve" `UI-STANDARD.md`. If you believe a clause is wrong, report it; do not edit it.
- Do not add dependencies without asking.
- Do not mark a page complete until it passes the full checklist.
- Do not batch unrelated fixes into one commit.

Begin with Step 0, then state your track.
