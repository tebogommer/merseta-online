# Agent instruction block

> Paste the block below into `AGENTS.md` or `CLAUDE.md` in each repository that adopts the UI Standard. This is what constrains ongoing generation — the adoption and upgrade prompts are one-off passes, this is permanent.
>
> Everything under the line goes in the file.

---

## UI rules

`UI-STANDARD.md` in this repository governs every page, dialog, and shared UI component. Read it before creating or modifying any UI, and treat it as normative — not as advice.

**Before generating a page:**

1. State which archetype it implements (A1–A5, or T3 reference data). If it fits none, stop and ask — do not invent a sixth.
2. Check the shared component set. Use those components; do not hand-write badges, headers, button groups, grids, steppers, or stat cards.
3. Check the technology mapping note for this application's entity vocabulary, and use those nouns in routes, labels, and messages.

**Rules that are breached most often — check these explicitly:**

- Records open in **View** mode. Edit is a separate route reached by an explicit action.
- **State reports, actions perform.** A badge is never clickable; a button never displays a value.
- Workflow **state**, **status**, and **flags** are three distinct things. One state badge per page.
- Transition actions come from the shared transition service, never from conditions written into a page. Never render an action that will fail.
- Every list meets the full data table baseline, via the shared grid component.
- The unique key column is a real hyperlink to the record's View route.
- Reference data is maintained in the reference-data area, not as entity CRUD. Values are deactivated, never deleted once referenced.

**Before declaring any page done:**

Run the compliance checklist in the standard. A page that cannot tick every box is not finished.

**When declining or deviating:**

Cite the clause identifier. If the standard does not cover what is needed, stop and ask rather than improvising. Report clauses you believe are wrong — do not edit `UI-STANDARD.md`.
