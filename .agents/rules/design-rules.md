---
name: design-rules
version: 1.0.0
priority: P0
trigger: glob
globs: "**/*.{tsx,jsx,vue,svelte,css,scss},**/components/**,**/app/**/page.tsx"
---

# Design Rules (TIER 2) - AG Kit

> Loaded when touching UI files. Design rules live in the specialist agents, NOT here.

## 🛑 GATE: DESIGN.md before any UI code (MANDATORY)

Before writing or editing UI (components, pages, styles — web or mobile), a **`DESIGN.md` must exist at the project root**.

1. **Check** for `DESIGN.md` at the project root.
2. **If missing:** infer the design direction from the brief, then **create `DESIGN.md` first** (tokens + rationale) following the `design-spec` skill. Do not write UI code until it exists.
3. **If present:** READ it and build strictly against its tokens. Descriptive names in prose map to token names.
4. **Keep it in sync** when the visual language changes — it is the single source of truth.

> Exception: none for new UI. A genuinely trivial tweak to existing UI (one button color, a spacing nudge) may proceed if a `DESIGN.md` already governs the project. Net-new UI always requires the gate.

| Need | Read |
| ---- | ---- |
| DESIGN.md format / tokens | `.agents/skills/design-spec/SKILL.md` |

---

| Task         | Read                            |
| ------------ | ------------------------------- |
| Web UI/UX    | `.agents/agent/frontend-specialist.md` |
| Mobile UI/UX | `.agents/agent/mobile-developer.md`    |

**These agents contain:**

- Purple Ban (no purple by default — brand/brief override allowed)
- Template Ban (no standard layouts)
- Anti-cliché rules
- Deep Design Thinking protocol

> 🔴 **For design work:** Open and READ the agent file. Rules are there.

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
