# User Interface Standard — Workflow Business Applications

| Field | Value |
| --- | --- |
| Document ID | ICT-STD-UI-001 |
| Version | 0.6 (draft) |
| Status | Draft |
| Supersedes | 0.5.1 |
| Applies to | All web applications, regardless of framework or rendering model |
| Owner | ICT Division |
| Audience | Developers and AI coding agents |

**Modal verbs.** **Must** / **must not** is an absolute requirement. **Should** / **should not** is a recommendation that allows justified deviation, recorded in the pull request. **May** is optional.

**Examples are illustrative.** This standard is domain-neutral. Where an example names an entity, a status, or a route, it is a placeholder — substitute the entity of the application in hand. `{entity}` in a route means the plural resource name (`invoices`, `tickets`, `members`, `orders`); `{id}` means its identifier. No rule in this document depends on the example that illustrates it.

---

## 1. Purpose and scope

### 1.1 Purpose

This standard defines how screens are built in the organisation's data-heavy workflow applications, so that a user who learns one application can operate all of them. It exists to remove three recurring defects: records that open in an editable state, status indicators that are mistaken for controls, and page layouts that differ for no functional reason.

### 1.2 Scope

This standard applies to every page, dialog, and shared component of any web application built or maintained under it — whatever the framework (Blazor, React, Vue, server-rendered PHP or Razor, a WordPress plugin's admin screens) and whatever the rendering model (server-side, client-side, or hybrid). It governs structure, interaction, and terminology. It does not govern brand identity — colour values, typefaces, and spacing tokens are defined in each application's design token file (`DESIGN.md` or equivalent) and are referenced, not restated, here.

Sections apply where their subject exists. An application without workflow statuses is not required to invent them; where it has any list, form, status, lookup, or dashboard, the corresponding sections bind in full. A brochure or content site is in scope only for its interactive screens.

### 1.2.1 Technology mapping

Each application must carry a short mapping note in its repository stating:

1. Which version of this standard it is built against.
2. Which component library or module implements the section 9 contracts.
3. Which mechanism provides virtualisation (12.3.1) and which provides the unsaved-changes guard (5.2.4).
4. **Its entity vocabulary** — the domain nouns this application uses for its records, routes, and workflow steps, so that terminology stays consistent across screens, exports, and notifications (14.3).

The requirements of this standard are universal; only the mechanism and the vocabulary are per-application.

### 1.3 Precedence

Where this standard and a visual specification conflict, this standard governs behaviour and structure; the visual specification governs appearance.

---

## 2. How to use this standard

### 2.1 Agent instruction

Any agent generating or modifying a page must read this document first and must state, in its output, which archetype (section 4) the page implements. An agent must not introduce a new page layout, a new status value, or a new button style. Where an existing shared component (section 9) covers a need, the agent must use it rather than writing markup.

### 2.2 Developer instruction

A pull request that adds or changes a page must satisfy the checklist in section 15. Deviations must be justified in the pull request description; undocumented deviations are rejected.

### 2.3 Changing this standard

A new pattern is added to this standard before it is added to an application, not after.

### 2.4 Versioning

1. Every change to this standard advances the version and adds a row to the change history in section 17. A change delivered without a version increment is not a valid change.
2. Numbering follows MAJOR.MINOR:
   - **MINOR** — a new section, a new rule, or a clarification that does not invalidate existing compliant work.
   - **MAJOR** — a change that makes previously compliant work non-compliant, or that renumbers existing clauses. A major release must carry a migration note stating what existing applications must change.
3. Applications must record which version of this standard they were built against, in their technology mapping note (1.2.1).
4. Clause numbers are referenced in pull requests and review comments, so renumbering is avoided where possible. Where a section must be inserted, it takes a decimal sub-number rather than shifting those that follow.

---

### 2.5 Human-centred design

This standard governs how an interface is built. It does not, by itself, establish that the interface works for the people using it. Two applications can satisfy every clause here and still fail their users.

1. A screen that carries significant user effort — a primary work queue, a long capture form, a wizard — must be validated with **real users of that screen** before it is treated as settled, not only reviewed against the checklist in section 16.
2. Validation is proportionate. Five users performing the real task, observed, is sufficient and is expected; a formal laboratory study is not required.
3. Findings that reveal a defect in **this standard**, rather than in the application, must be reported so the standard can be corrected. A pattern that repeatedly confuses users is a defect in the pattern.
4. New archetype implementations and new shared components must be validated once before the pattern is copied across an application, because a flaw in a shared component is reproduced everywhere it is used.
5. Task success, task time, and error rate should be recorded for the application's two or three most frequent tasks, so that a redesign can be judged against evidence rather than preference.
6. Accessibility validation with assistive technology users is part of this activity, not a separate exercise (15.6.2).

Compliance with this standard is necessary but not sufficient. Where observed user behaviour and a clause conflict, raise it — do not quietly ignore either.

---

## 3. Definitions

| Term | Meaning |
| --- | --- |
| **Entity** | A business record the application manages — an order, an invoice, a ticket, a claim, a member, a case. |
| **State** | A condition of an entity that the user reads. It is not clickable. |
| **Action** | An operation the user invokes. It is always clickable. |
| **Status** | The named position of an entity in its workflow, drawn from a fixed set of values. |
| **Transition** | A change from one status to another, triggered by an action and constrained by role and current status. |
| **Page mode** | Whether a page is presenting an entity for reading, for editing, or for creation. See section 5. |
| **Archetype** | One of the five permitted page structures defined in section 4. |
| **Action zone** | The single region of a page in which actions appear. See section 8.2. |

---

## 4. Page archetypes

### 4.1 The five archetypes

Every page must be one of the following. There is no sixth archetype.

| # | Archetype | Job | Example |
| --- | --- | --- | --- |
| A1 | **List** | Find one record among many | All records of a type |
| A2 | **Work queue** | Act on the records assigned to me | Records awaiting my action |
| A3 | **Entity detail** | Read one record and act on it | One record, identified by reference |
| A4 | **Form / wizard** | Capture or amend structured data | New record, step 3 of 6 |
| A5 | **Dashboard** | Read aggregate information | Throughput by team or period |

A work queue (A2) is a list with a fixed, role-scoped filter and row-level actions. It must not be built as a separate layout.

### 4.2 Shared page anatomy

Every archetype must present its elements in this vertical order:

```
1  Breadcrumb                    Where am I
2  Page header                   Title · reference · status badge(s) · action zone
3  Context strip (optional)      Key read-only facts: 3–6 items maximum
4  Content                       Archetype-specific
5  Footer action bar (A4 only)   Sticky. Save / Cancel.
```

The page header must be rendered by the shared `EntityHeader` or `PageHeader` component. A page must not hand-roll a header.

### 4.3 Content rules per archetype

- **A1 / A2 — List.** Filters above the grid, never in a side drawer that hides the active filter. Active filters must remain visible as removable chips. The grid must show a result count. Row click opens the detail page in View mode (section 5.2); row click must never open an editor.
- **A3 — Entity detail.** Content is grouped in cards by subject, not by database table. A detail page must be read-only by default. Where the entity has many facets, tabs may be used, and the tab set must be identical for every status of that entity — tabs must not appear and disappear as status changes; irrelevant tabs are shown empty with an explanatory line.
- **A4 — Form / wizard.** One column for the primary flow. A wizard must show step position ("Step 3 of 6"), must allow return to completed steps, and must persist a draft on step change.
- **A5 — Dashboard.** Read-only. A dashboard must not contain workflow transition buttons; a tile links to a filtered list, and the action is taken there. Stat cards on a dashboard must follow section 4.4.

### 4.4 Stat cards

A stat card presents one number and its context. It appears on dashboards (A5) and may appear as a summary row above a T1/T2 list.

#### 4.4.1 Anatomy

Every stat card carries these elements, in this order, and nothing else:

```
1  Label          What is being counted        e.g. "Awaiting my action"
2  Value          The number, dominant         38
3  Comparison     Optional: trend or target    "▲ 6 since Monday" / "12 overdue"
4  Link           Optional: "View all"         → the filtered T1/T2 list
```

#### 4.4.2 Rules

1. One card, one number. A card must not present two competing figures; a pair such as approved/rejected is two cards.
2. The value must use tabular numerals, and currency values must be abbreviated consistently (R 2.4m, not R 2 400 000) with the exact figure in a tooltip.
3. A stat card is **state, not action**. The card surface must not be clickable. Where drill-down exists it is an explicit **View all** link in the card footer — this keeps the state/action rule of section 6 intact.
4. Comparison direction must be encoded by arrow *and* colour, and colour must follow meaning, not direction: a rise in *Approved this month* is `Success` green; a rise in *Overdue* is `Danger` red. An arrow whose colour is chosen by direction alone is a defect.
5. Where the comparison is against a target, the card must state the target: "38 of 50 target".
6. A card whose query fails must show an inline error state within the card, not a zero. Zero is data; a dash with a retry affordance is an error.
7. A card whose data is loading shows a skeleton of the same dimensions, so the layout does not shift.
8. Cards in a row must be equal height and share one grid; a summary row above a list must not exceed four cards.
9. Card figures and the list below them must come from the same query definition. A card that says 38 while the filtered list shows 41 destroys trust in both; where the figure is cached, the card must state its refresh time.
10. Icons on stat cards are decorative and optional. Where used, one icon per card, muted colour, never carrying meaning on its own.

---

### 4.5 Application shell and navigation

Every application uses the same shell, so that navigation is learned once.

#### 4.5.1 Structure

```
┌──────────────────────────────────────────────┐
│ Top bar   toggle · app name · search · user   │
├───────────┬──────────────────────────────────┤
│ Left nav  │  Page content                    │
│ (primary) │  (breadcrumb → header → content) │
└───────────┴──────────────────────────────────┘
```

1. Primary navigation must be a **left panel**. It must not be a top horizontal menu bar, and it must not be a hamburger-only menu on desktop.
2. The left panel must be **collapsible** to an icon rail, by a control in the top bar. The collapsed state must persist per user across sessions.
3. Collapsed, every item must still be reachable and must reveal its label on hover and on keyboard focus.
4. Navigation items must be grouped under short headings, and the group containing the current page must be expanded on load.
5. The current location must be marked in the panel, and must agree with the breadcrumb.
6. Depth is limited to two levels. A third level belongs on the page, as tabs or an in-page index — not in the panel.
7. Reference data (11.8) occupies exactly one navigation entry, never one per lookup set.
8. Actions must not appear in the navigation panel. The panel navigates; it does not perform operations.
9. Shell and layout class names must be namespaced and must not reuse names defined by the CSS framework in use. A sidebar named `.nav` collides with Bootstrap's `.nav` component, and the resulting layout failure is hard to trace.

#### 4.5.2 Sizes

| State | Width | Shows |
| --- | --- | --- |
| Expanded | 240–280 px | Icon and label |
| Collapsed | 56–72 px | Icon only, label on hover or focus |
| Small screen | Off-canvas | Full panel over a scrim, opened from the top bar |

### 4.6 Responsive behaviour

Screens are used on phones, tablets, and desktops. Layout must adapt by rule, not by shrinking.

#### 4.6.1 Breakpoints

| Name | Range | Layout |
| --- | --- | --- |
| Compact | up to 640 px | Single column. Navigation off-canvas. Tables become stacked cards. |
| Medium | 641–1024 px | Single content column. Navigation collapsed to the icon rail by default. |
| Expanded | 1025 px and above | Full shell. Navigation expanded by default. Multi-column content permitted. |

#### 4.6.2 Rules

1. **No horizontal page scrolling at any width.** A wide element scrolls within its own container; the page does not. Enforce this at source — cap content to its column and let grids scroll inside their card. A page-level `overflow-x` rule is a backstop, not the mechanism, and setting `overflow` on `html` or `body` creates a scroll container that silently disables `position: sticky` on every descendant.
   - **The content column is sized from the space that remains**, never given a fixed or full-viewport width beside a fixed navigation panel. Reserve the panel's width on the shell — as padding or a layout track — so the content region cannot sit underneath it at any width or collapse state. Both must animate together when the panel collapses.
   - **Table content wraps rather than forcing width.** Headers and cells may wrap onto two lines; a grid must not push its container wider because a header refuses to break.
2. **Tables must not simply shrink.** Below the compact breakpoint a grid row becomes a stacked card carrying the identifying reference, the subject, the state badge, and the row actions. Remaining columns are dropped or moved into the card body; they must not be squeezed into unreadable widths.
3. **Touch targets are at least 44 × 44 px** below the expanded breakpoint. Icon-only actions must not sit closer than 8 px apart on touch.
4. **The action zone stacks below the title** on compact screens, with the primary action first and full width. It must not wrap into a ragged row of half-width buttons.
5. **The stepper becomes vertical** on compact screens; it must not scroll horizontally, because a stepper that must be scrolled to be read defeats its purpose.
6. **Stat cards reflow to one column** on compact and two on medium. Cards must not shrink below legibility to preserve a row.
7. **The sticky footer action bar remains visible and above any on-screen keyboard**, and content must be padded so the last field is never hidden behind it.
8. **Drawers and dialogs become full width** on compact screens, retaining their own header and footer.
9. **Filters collapse into a single Filters control** on compact screens, showing the active count. Active filter chips remain visible above the results.
10. Text must never require pinch-zoom to read; the base font size must not drop below 14 px on any device.
11. Content must respect safe areas on devices with notches or rounded corners.

#### 4.6.3 Verification

Every page must be checked at 360 px, 768 px, and 1440 px before it is considered complete. Checking only the developer's own window is not verification.

---

## 5. Page modes

### 5.1 The three modes

Every entity page operates in exactly one mode: **View**, **Edit**, or **Create**. The mode must be explicit in the route, in the page state, and in what the user sees.

| Mode | Route | Default | Controls shown |
| --- | --- | --- | --- |
| View | `/{entity}/{id}` | Yes | Read-only fields. Actions in the action zone. |
| Edit | `/{entity}/{id}/edit` | No | Editable fields. Save and Cancel in the sticky footer. |
| Create | `/{entity}/new` | No | Editable fields. Save and Cancel in the sticky footer. |

### 5.2 Rules

1. A record must open in View mode. There is no exception for records in a draft status, and no exception for administrators.
2. Edit mode must be entered by an explicit user action — an **Edit** button in the action zone, or a row action in a grid.
3. Edit mode must be visually unmistakable. The page must show an editing indicator in the header and a sticky footer action bar. Inputs in View mode must not be rendered as disabled form controls that look editable; they must be rendered as text.
4. Leaving Edit mode with unsaved changes must trigger a confirmation. This applies to the Cancel button, breadcrumb navigation, and browser navigation.
5. On save, the application must return to View mode for the same record and confirm with a toast. It must not remain in Edit mode, and must not return to the list.
6. Create mode must not be reached by opening an existing record. `/{entity}/new` is a separate route.

### 5.3 Inline editing

Inline cell editing in a grid is permitted only for high-volume operational data, and only where it is documented as an exception in the application's `DESIGN.md`. Where used, an edited cell must commit on blur or Enter, must show a save state, and must revert visibly on failure. Inline editing must not be used for any field that participates in a workflow transition.

---

## 6. State and action must be visually distinct

This section is the core of the standard. Most reported confusion in the current applications comes from state and action sharing a visual language.

### 6.1 The rule

**A user must be able to tell, without hovering, whether an element reports something or does something.**

| | State (badge, chip, flag) | Action (button, link) |
| --- | --- | --- |
| Purpose | Reports a condition | Performs an operation |
| Label | Noun or adjective — *Under Review*, *Overdue* | Verb phrase — *Submit for review*, *Recommend* |
| Shape | Pill, small, low height | Button, standard control height |
| Border | None | Per button variant |
| Shadow | None | Per button variant |
| Cursor | Default | Pointer |
| Hover | No change | Visible change |
| Focusable | No | Yes |
| Clickable | **Never** | Always |

### 6.2 Prohibitions

1. A status badge must not be clickable, and must not be the target of a click handler. Where the user needs the history behind a status, provide a separate **View history** action.
2. A button must not be used to display a value. A read-only value is text.
3. An outline or ghost button must not be used for a status. This is the most common current error.
4. Colour must not carry meaning anywhere except status badges, validation states, and the destructive button variant. Decorative colour on buttons, cards, or headers is prohibited.
5. An icon must not appear alone as an action unless it is a standard operation (edit, delete, download, expand) and carries an accessible label and a tooltip.

### 6.3 Flags versus statuses

A **status** is the entity's single position in its workflow — one value at a time, from the fixed set in section 7. A **flag** is an independent condition that may coexist with any status, such as *Overdue*, *Escalated*, or *Amendment requested*.

Rules:

1. An entity has exactly one status. It may carry zero or more flags.
2. The status badge is rendered first, in the header, and is always present.
3. Flags follow the status badge and use the neutral or warning styles only. A flag must not use the same colour as a status value in the same view.
4. A maximum of three flags is shown; further flags collapse into a "+n" indicator with a tooltip.

---

### 6.4 Efficiency for frequent users

The applications this standard covers are operated all day by the same people. A design tuned only for the first-time user penalises them.

1. Common destructive-free operations must have keyboard access: submit a form, close a dialog or drawer, move between wizard steps, and page through a list.
2. Where a list is a primary work surface, keyboard navigation of rows and opening the focused record must be supported.
3. A search or command entry point should be reachable by a single documented shortcut from anywhere in the application.
4. Any shortcut offered must be discoverable — listed in a help panel and shown in the tooltip of the control it duplicates. An undiscoverable shortcut serves nobody.
5. Shortcuts must not conflict with browser or assistive-technology bindings, and single-character shortcuts must be disableable or remappable *(WCAG 2.1.4)*.
6. Repeated work must be reducible: saved views (11.4), persisted preferences (11.2.7), and bulk operations (11.6) exist for this purpose and should be offered wherever the same filter or the same action is applied repeatedly.
7. Efficiency features must be additive. They must never become the only route to an operation.

---

## 7. Status taxonomy

### 7.1 One enumeration per workflow

Each workflow must have a single status enumeration defined in one place in code. Status must not be represented as a free-text string, and must not be inferred in the UI from a combination of dates or booleans.

### 7.2 Semantic groups and colours

Each status value must be mapped to exactly one semantic group. The group determines the colour. The application's `DESIGN.md` defines the hex values for each group; this standard defines the mapping.

| Semantic group | Meaning | Conventional token | Example statuses |
| --- | --- | --- | --- |
| `Neutral` | Not yet in the workflow | secondary / grey | Draft, Not started |
| `Pending` | Waiting on someone else | warning / amber | Submitted, Awaiting documents |
| `Active` | In progress now | info / blue | Under review, In assessment |
| `Success` | Successful terminal state | success / green | Approved, Completed |
| `Danger` | Unsuccessful terminal state | danger / red | Rejected, Withdrawn |

### 7.3 Rules

1. A status value must map to the same colour on every screen of every application. A status that is amber in a list must not be blue on the detail page.
2. Terminal statuses (`Success`, `Danger`) must suppress all transition actions except those explicitly permitted, such as **Reopen**.
3. A status label displayed to the user must be plain language, not the enum name. `AWAITING_DOCS` is displayed as *Awaiting documents*.
4. Status must never be conveyed by colour alone. The badge always carries its text label.

---

### 7.4 Workflow state, status, and flags

#### 7.4.1 The three concepts

Applications commonly carry more than one kind of condition on a record. Each must be modelled and displayed distinctly.

| Concept | Question it answers | Cardinality | Ordered |
| --- | --- | --- | --- |
| **Workflow state** | Where is this item in the process, and who holds it? | Exactly one | Yes |
| **Status** | What condition is it in, within or across the process? | Zero or one | No |
| **Flag** | What independent circumstance applies to it? | Zero or more | No |

The test: if an arrow can be drawn from one value to another, it is workflow state. If it cannot, it is a status or a flag. An ordered path such as *Submitted → Reviewed → Approved* is workflow state; *On hold*, *Compliant*, *Overdue* are not, because none of them follows from another.

An application that has only one axis must use workflow state and must not introduce a second axis for the sake of it.

#### 7.4.2 Display allocation

| Element | Placement | Rendering |
| --- | --- | --- |
| Workflow state | Page header, first badge; grid status column | Status badge per 7.2. The current state only. |
| Workflow progress | Directly beneath the page header | Stepper per 7.4.3. |
| Status | Page header, after the state badge | Status badge, visually equal but never first. |
| Flags | Page header, after the status | Flag style per 6.3, visually subordinate. |
| Sub-state within a step | Inside the card that owns it | Small badge, scoped to that card. Never in the header. |

There must be exactly one workflow state badge on a page. Two badges of the same style in the header, both claiming to be the state, is a defect.

#### 7.4.3 Stepper

A stepper shows the whole ordered path, marking what is complete, what is current, and what remains. It must appear on the detail page (A3) of any entity that has more than three workflow states.

1. Steps must be capped at seven. Where the workflow has more, group states into phases, show the phases, and expand sub-steps for the active phase only.
2. Emphasis is reserved for the current step. Completed steps are muted with a completion mark; future steps are outlined and low-contrast. Colour must not be used to decorate future steps.
3. Every step must be labelled with its business name — the same wording used in the status badge, the audit trail, and any notification.
4. Where an owner or a due date is known for the current step, it is shown against that step, not only in the header.
5. A stepper must not be interactive. It reports position; it does not navigate and it does not transition. Where a completed step has evidence to inspect, that is a link inside the step, not a click on the step itself.
6. **Terminal-negative states break the path.** *Rejected*, *Withdrawn*, and *Lapsed* are not the final step. The stepper must stop at the step where the item ended, mark that point with a terminal indicator in the `Danger` group, and state the reason. Remaining steps are dropped, not greyed.
7. **Blocking status must appear on the stepper.** Where the item carries a status that halts progression — *On hold*, *Awaiting documents* — the current step must carry that indicator. A stepper showing normal progress while the header says *On hold* misreports the item.
8. Where the workflow can loop backwards (*Referred back*), the stepper must show the return rather than hiding it, and the audit trail carries the detail.

#### 7.4.4 Surface by surface

| Surface | Shows |
| --- | --- |
| Grid row (T1/T2) | Workflow state badge and flags. No stepper — too heavy for a row. |
| Detail header (A3) | State badge, status badge, flags, with the stepper directly beneath. |
| Stat cards | Counts grouped by workflow state, since that is where a backlog is visible. |
| External or applicant-facing view | Stepper first and prominent, with plain-language step names. It is the primary thing an applicant came to see. |
| Notifications and correspondence | The step name, worded identically to the interface. |

---

## 8. Actions and workflow transitions

### 8.1 Action hierarchy

| Level | Style | Count per page | Use |
| --- | --- | --- | --- |
| Primary | Solid, brand colour | **One** | The action the user most likely came to take |
| Secondary | Outline | 0–3 | Supporting actions |
| Tertiary | Link or icon | Any | Low-consequence actions |
| Destructive | Solid or outline, danger colour | 0–1 | Delete, reject, withdraw |
| Overflow | "..." menu | Any | Rare actions |

A page must have at most one primary button. Where two actions compete for primacy, the one that advances the workflow wins.

### 8.2 Placement

1. Entity-level actions must appear in the page header action zone, right-aligned, and nowhere else.
2. Row-level actions must appear in the last grid column, as icons or an overflow menu, and must be consistent across all grids.
3. Save and Cancel must appear only in the sticky footer bar in Edit and Create modes. They must not appear in the header.
4. A destructive action must be separated from other actions by spacing, or placed in the overflow menu. It must not sit adjacent to the primary button.

### 8.3 Transition actions

1. A transition action must be rendered only when the transition is legal for the entity's current status **and** permitted for the current user's role. The application must not render an action that will fail.
2. Legality must be determined by one shared service, not by conditions written into the page. A page asks the service what is available; it does not decide.
3. A transition action must be labelled with the business verb of the domain, not the technical one — whatever that domain calls the step, never *Update status* or *Change state*.
4. **Prefer undo to confirmation.** Where an action can be reversed cheaply, it must be performed immediately and offered as an undo in the confirmation toast, for a stated period, rather than interrupted by a dialog. A confirmation dialog is required only where the action is genuinely irreversible or externally visible. Dialogs on reversible actions train users to dismiss them, which weakens the dialogs that matter.
5. A transition that cannot be reversed must require confirmation, and the confirmation must name the consequence in the application's own terms — who receives it, what becomes fixed, and that it cannot be recalled. "Are you sure?" does not satisfy this rule.
6. A transition that requires a reason — rejection, referral back, withdrawal — must capture that reason in the same dialog, and the reason must be mandatory.
7. After a transition, the page must refresh the status badge, the action zone, and the audit trail without a full page reload.

### 8.4 Disabled versus hidden

An action the user's role may never perform is hidden. An action the user's role may perform but that is unavailable in the current status is shown disabled, with a tooltip stating why. This distinction must be applied consistently; a disabled control with no explanation is a defect.

---

## 9. Shared components

### 9.1 Rule

Where a shared component exists for a need, a page must use it. Raw framework or CSS-library markup that reproduces a shared component's job is a defect, whatever the stack — a hand-written badge `<span>`, header block, button group, or grid wrapper. The component set is a **contract**: the names, inputs, and behaviours below are fixed across all applications; the implementation is per-stack (a Razor component, a React component, a Vue component, or a server-side partial/include in a templated application).

### 9.2 Required component set

Every covered application must provide these components with these contracts. Names must be identical across applications.

| Component | Contract | Renders |
| --- | --- | --- |
| `StatusBadge` | `Status`, `Size` | The status pill. Resolves its own colour from the semantic map. Never clickable. |
| `FlagBadge` | `Flags` (collection) | Ordered flags with overflow at three. |
| `EntityHeader` | `Title`, `Reference`, `Status`, `Flags`, `Actions` (child content) | Breadcrumb, title block, badges, action zone. |
| `PageActions` | `Primary`, `Secondary`, `Destructive`, `Overflow` | The action zone, enforcing one primary and correct ordering. |
| `TransitionActions` | `EntityId`, `EntityType` | Queries the transition service and renders only permitted transitions. |
| `FormShell` | `Mode`, `OnSave`, `OnCancel`, `IsDirty` | Edit indicator, layout, sticky footer, unsaved-changes guard. |
| `FieldGroup` | `Label`, `Required`, `Help`, `Error` | One field with consistent label, hint, and validation position. |
| `ReadOnlyField` | `Label`, `Value` | A View-mode value as text, never a disabled input. |
| `DataGridShell` | `Items`, `Columns`, `RowActions`, `BulkActions`, `GridKey`, `EmptyState`, `ErrorState`, `IsLoading` | The full baseline of 11.9: paging with the size ladder, sorting, column chooser, row selection, five states, URL state, and stacked-card layout on compact screens. `GridKey` is the stable identifier under which view preferences are stored. |
| `FilterBar` | `Filters`, `ActiveChips` | Filters above the grid with visible active chips. |
| `ConfirmDialog` | `Title`, `Consequence`, `ConfirmLabel`, `RequiresReason` | Consistent confirmation, with optional mandatory reason. |
| `AuditTrail` | `EntityId` | Chronological status history with actor and timestamp. |
| `WorkflowStepper` | `States`, `CurrentState`, `BlockingStatus`, `TerminalOutcome`, `StepOwner`, `StepDue` | The ordered path per 7.4.3. Non-interactive. Handles terminal-negative truncation and blocked-step indication itself. |
| `EmptyState` | `Message`, `Action` | Illustration or icon, one line of guidance, one action. |
| `StatCard` | `Label`, `Value`, `Comparison`, `ComparisonMeaning`, `LinkUrl` | One figure per section 4.4. Surface not clickable; drill-down via the footer link. Handles its own loading skeleton and error state. |
| `AppShell` | `NavItems`, `NavCollapsed`, `CurrentPath`, `TopBarContent` | The shell of 4.5.1. Owns the collapse toggle, the off-canvas behaviour, and persistence of the collapsed state. |
| `NavPanel` | `Groups`, `Collapsed`, `CurrentPath` | The left panel. Two levels maximum. Marks current location; reveals labels on hover and focus when collapsed. |
| `StatCardRow` | `Cards` (child content) | Equal-height grid of up to four cards above a list or on a dashboard. |

Input names use each stack's convention (`Title` in Razor, `title` in React/Vue); the set of inputs and their meaning must not vary. "Child content" means the stack's composition mechanism: a `RenderFragment` in Blazor, `children` in React, a slot in Vue, a block/include in a server-templated application.

### 9.3 Illustrative usage

The same contract, in two stacks:

```razor
@* Blazor *@
<EntityHeader Title="@item.DisplayName"
              Reference="@item.Reference"
              Status="@item.Status"
              Flags="@item.Flags">
    <Actions>
        <TransitionActions EntityId="@item.Id" EntityType="@EntityType.Order" />
        <SecondaryAction Label="Edit" Href="@($"/orders/{item.Id}/edit")" />
    </Actions>
</EntityHeader>
```

```jsx
// React
<EntityHeader title={item.displayName}
              reference={item.reference}
              status={item.status}
              flags={item.flags}
              actions={
                <>
                  <TransitionActions entityId={item.id} entityType="Order" />
                  <SecondaryAction label="Edit" href={`/orders/${item.id}/edit`} />
                </>
              } />
```

### 9.4 Enforcement

The status-to-colour map, the transition service, and the button variants must each exist in exactly one file. Duplicating any of them is a defect, regardless of whether the duplicate produces the same result today.

---

## 10. Forms and validation

1. A form must use one column for its primary flow. Two columns may be used only for genuinely paired short fields, such as a start and end date.
2. Labels must sit above their inputs, left-aligned, in sentence case.
3. Required fields must be marked at the field. Optional-field marking must not be used instead.
4. Validation must run on blur for a field, and on submit for the form. Validation must not run on every keystroke before a field has been left.
5. Validation messages must appear directly below the field, must state what is wrong and what is acceptable, and must not restate the field name only. A message that shows the required format is correct; "Invalid" is not.
6. On failed submission, the page must move focus to the first field in error and show a summary at the top of the form when more than three fields fail.
7. Long forms must be split into a wizard (A4) rather than presented as a single scroll exceeding roughly two screens.
8. A wizard must save a draft on every step change, and must state when the draft was last saved.
9. Field order must follow the order in which the user holds the information, not the order of the database columns.
10. Currency, identity numbers, and dates must use one shared input component each, with one display format across all applications.

---

## 11. Lists, grids, and reference data

### 11.1 The three list tiers

Not every table of records is the same kind of screen. Each list must be built as one of three tiers, and the tier must be stated when the page is created.

| Tier | Contents | Volume | Pattern |
| --- | --- | --- | --- |
| **T1 — Transactional list** | Business entities that carry a workflow status | Unbounded | Archetype A1. Full page, server paging, row opens the detail page in View mode. |
| **T2 — Work queue** | A T1 list scoped to the current user's role and outstanding work | Unbounded | Archetype A2. A T1 list with a fixed filter and row actions. |
| **T3 — Reference data maintenance** | Closed lookup sets: regions, document types, categories, decline reasons | Typically under 500 rows | Section 11.8. Maintained in place, not through the entity page flow. |

A T3 set must not be built as a T1 list with its own menu entry, detail page, and route family. This is the most common source of duplicated, inconsistent maintenance screens.

### 11.2 Column contract (T1 and T2)

1. A transactional grid must show, at minimum: the identifying reference, the business subject, the status badge, the date the record last changed, and a row action column.
2. The identifying reference must be the first column and must use tabular numerals.
3. Column count should not exceed eight. Where more data is needed, use an expandable row or send the user to the detail page. Horizontal scrolling must not be required to reach the status column or the row actions.
4. Numeric, currency, and date columns must be right-aligned; text columns left-aligned. Currency must carry its unit in the column header, not in each cell.
5. Row density may be user-selectable between comfortable and compact. The default must be the same in every application.

#### 11.2.7 Column visibility and persistence

1. Every T1 and T2 grid must provide a **column chooser** — a control in the grid header listing every available column with a checkbox, so the user can show and hide columns.
2. Columns that identify the record (the reference) and the workflow state must not be hideable. Everything else may be.
3. The chooser must offer **Reset to default**, restoring the standard column set in one action.
4. Column selection, column order where reordering is offered, sort, page size, and density form the grid's **view preferences**. They must be stored **per user and per grid**, keyed by a stable grid identifier — not by page URL, which changes with filters.
5. View preferences must **persist beyond the session**. A user who sets their columns, closes the browser, and returns next week must find the grid as they left it. Session-only storage does not satisfy this rule.
6. Preferences must be stored server-side against the user account where the application has a user store, so they follow the user across devices. Browser storage is acceptable only as a fallback and must be documented in the technology mapping note (1.2.1).
7. A hidden column must be excluded from the query projection where the stack allows it, so hiding columns makes the grid faster rather than merely tidier (12.2.1).
8. Export (11.7) must follow the visible column set, so what the user sees is what they get.
9. Where a preference refers to a column that no longer exists after a release, it must be ignored silently and the remaining preferences honoured. A stale preference must never break the grid.

### 11.3 Sorting and paging

1. The default sort must be the column the user is most likely to work from. For a work queue this is the oldest waiting item, not the most recently created.
2. The active sort column and direction must be visible without hovering.
3. Server-side paging must be used for any set that can exceed 200 rows. Sorting and filtering must also execute server-side wherever paging does.
   - **Page size must be user-selectable** from a fixed ladder: **5, 10, 20, 50, 100, 250, 500**. The ladder is the same in every application so the control is learned once. An application may extend the ladder upwards where a genuine need exists, but must not remove the lower steps — small page sizes matter on phones and on slow connections.
   - The default page size is **20**, unless the screen's purpose argues otherwise and the deviation is recorded.
   - The selected page size must persist per user and per grid (11.2.7) and must survive a return to the screen.
   - Changing page size must return the user to a position containing the first record of the page they were on, not silently to page 1.
   - A page size above 100 must still meet the section 12.1 budgets. Where a large page cannot, the control must remain available but the grid must virtualise (12.3.1) rather than render every row eagerly. Offering 500 rows and then taking eight seconds to draw them is worse than not offering it.
4. Infinite scroll must not be used for records the user must work through, because it defeats position, counting, and return-to-place.
5. Returning to a list from a detail page must restore the previous page, sort, filter, and scroll position.

### 11.4 Filtering and search

1. Filters must sit above the grid. A filter drawer that hides which filters are active must not be used.
2. Active filters must appear as removable chips, with a **Clear all** control once more than one is applied.
3. The result count must always be visible and must reflect the filtered set.
4. Filter, sort, and page state must be held in the URL query string so that a list view can be bookmarked, refreshed, and shared without loss.
5. A quick-search box must search the identifying reference and the business subject at minimum, and must state what it searches in its placeholder.

### 11.5 Grid states

The grid must present five distinct states, and each must be handled explicitly:

| State | Requirement |
| --- | --- |
| Loading | Skeleton rows, not a blocking overlay or a bare spinner. |
| Populated | Normal render. |
| Empty — no records exist | Explanatory line and, where the user may create one, a single primary action. |
| Empty — filter excludes everything | Different message, plus a **Clear filters** action. Must not be confused with the previous state. |
| Error | Inline message stating what failed and a **Retry** action. Must not render an empty grid. |

### 11.6 Row and bulk actions

1. A row must not carry more than two visible actions; the remainder go to the row overflow menu.
2. Row actions must be identical in position and iconography across every grid in every application.
3. **The unique key column must be a hyperlink to the record.** The column carrying the primary or natural key — the reference, code, or number the business uses to identify the record — must render as an anchor pointing at the record's View route (`/{entity}/{id}`), and must open it in View mode.
   - It must be a real link, not a click handler on a cell, so it can be opened in a new tab, copied, bookmarked, reached by keyboard, and read by assistive technology.
   - It must be visually identifiable as a link and must be the first column after any selection checkbox.
   - Where the entity has no meaningful business key, the link goes on the record's name or subject column instead. Every row must have exactly one such link.
   - The link must not be a workflow action. Opening a record is navigation, never a transition.
   - A row click anywhere outside the selection cell and the action column may also open the record in View mode, as a convenience. It does not replace the link requirement above.
4. **Row selection.** Where any bulk operation exists, the grid must provide a checkbox column as the first column, plus a header checkbox that selects and clears every row on the current page.
5. The header checkbox must show an indeterminate state when some but not all rows on the page are selected.
6. Where selection can extend beyond the current page, the grid must say so explicitly and offer the choice — "All 20 on this page selected. Select all 214 matching records" — and must never silently apply an action to records the user cannot see.
7. The bulk action bar appears only once something is selected. It must state the number selected, offer **Clear selection**, and present only actions legal for every selected record.
8. Selection must survive paging within the same filter set, and must be cleared when the filter or the search term changes, because the meaning of "all matching records" has changed.
9. Clicking a row's checkbox must not open the record; the checkbox cell does not trigger row navigation.
10. A bulk workflow transition must apply the same legality rules as the single-record transition, and must report per-record outcomes when some records fail.
11. Bulk destructive operations must confirm with the count stated in the confirmation, and must never be the default action of the bar.

### 11.7 Export

Where export is offered, it must apply the filters currently in force, must state the row count before generating, and must produce the same column set the user sees on screen.

### 11.8 Reference data maintenance (T3)

#### 11.8.1 Placement

All reference data must be maintained in one **Data table baseline**
- [ ] All thirteen capabilities in 11.9 are present.
- [ ] Page size is selectable from 5 / 10 / 20 / 50 / 100 / 250 / 500, defaulting to 20.
- [ ] Column chooser present, with reset; the reference and state columns are not hideable.
- [ ] View preferences persist per user and per grid beyond the session, keyed by grid identifier not URL.
- [ ] A stale preference for a removed column is ignored silently.
- [ ] The unique key column is a real hyperlink to the record's View route, not a cell click handler.
- [ ] Header checkbox selects the page, shows indeterminate state, and selection beyond the page is explicit.
- [ ] Selection survives paging and clears when the filter changes.
- [ ] Export honours current filters and the visible column set.

**Reference data** area, with the tables listed in a single index or left navigation panel. A lookup table must not receive its own top-level menu entry.

#### 11.8.2 Standard shape

Every lookup table must expose the same fields, in this order, whatever the underlying schema:

| Column | Rule |
| --- | --- |
| Code | Short, stable, human-readable. Immutable once the value has been referenced. |
| Description | The label shown to users elsewhere in the application. |
| Parent | Present only for hierarchical sets. |
| Sort order | Controls display order in every consuming control. |
| Active | State, shown as a badge. Never a button, never a bare checkbox in the grid. |
| Last updated | Date and user. |

#### 11.8.3 Maintenance interaction

1. A lookup row must be created and edited in a side drawer or an inline row editor, not by navigating to a separate detail page. This is a permitted exception to section 5 and applies only to T3 sets.
2. The drawer must still obey the mode rules in spirit: an explicit Edit entry point, Save and Cancel at the foot of the drawer, and an unsaved-changes guard.
3. A search box must be present once a table exceeds 25 rows.
4. Hierarchical sets must be presented as an expandable tree or filtered by parent, and must not be presented as a flat list carrying a parent identifier column.

#### 11.8.4 Deactivation, not deletion

1. A lookup value that has ever been referenced must not be deleted. It must be deactivated.
2. Deletion may be offered only where the usage count is zero, and the interface must display that usage count before offering it.
3. Deactivation must be an explicit action labelled **Deactivate**, with confirmation naming the consequence: "This value will no longer be available for new records. Existing records keep it."
4. Active and Inactive are states and must be rendered as badges using the `Success` and `Neutral` semantic groups respectively.
5. Where a set is effective-dated, the effective-from and effective-to dates must be shown, and the interface must indicate which value is current.

#### 11.8.5 Bulk load

Sets that arrive from an external authority — industry classification codes, postal or administrative area codes, published standard code lists — must support import from a file, must present a preview showing additions, changes, and deactivations before committing, and must never silently overwrite existing values.

#### 11.8.6 Audit

Every change to reference data must be recorded with actor, timestamp, and previous value, and must be viewable from the same drawer.

### 11.9 Data table baseline

Every T1 and T2 grid must provide all thirteen of the following. A grid missing any of them is not complete, regardless of how well it renders.

| # | Capability | Clause |
| --- | --- | --- |
| 1 | Server-side paging with a selectable page size from the fixed ladder | 11.3.3 |
| 2 | Sorting on every column where sorting is meaningful, with the active sort visible | 11.3.1–11.3.2 |
| 3 | Quick search across the reference and the subject, plus structured filters with visible chips | 11.4 |
| 4 | Column chooser with reset, persisted per user beyond the session | 11.2.7 |
| 5 | Row selection with header select-all, indeterminate state, and a bulk action bar | 11.6.4–11.6.11 |
| 6 | Unique key column hyperlinked to the record's View route | 11.6.3 |
| 7 | Row actions: at most two visible, remainder in an overflow menu | 11.6.1–11.6.2 |
| 8 | Five grid states: loading, populated, empty, filtered-empty, error | 11.5 |
| 9 | Filter, sort, and page state held in the URL | 11.4.4 |
| 10 | List position restored on return from a detail page | 11.3.5 |
| 11 | Export honouring current filters and visible columns | 11.7, 11.2.7.8 |
| 12 | Stacked-card layout below the compact breakpoint | 4.6.2.2 |
| 13 | Meets the section 12.1 performance budgets on a representative dataset | 12.1 |

These capabilities are the reason section 9 requires a single shared `DataGridShell`. Thirteen capabilities re-implemented per screen will diverge; implemented once, every grid in the application gains them together.

### 11.10 Consuming lookup data in forms

The maintenance screen and the controls that consume it must agree. These rules govern the consuming side.

1. Control selection is determined by set size, not by preference:

| Set size | Control |
| --- | --- |
| 2 values, mutually exclusive | Radio pair, or a switch where the choice is genuinely on/off |
| 3–7 | Radio group or plain dropdown |
| 8–50 | Dropdown with type-ahead filtering |
| 51–500 | Searchable select, filtered client-side |
| Over 500 | Server-side type-ahead, minimum two characters, showing the code alongside the description |

2. Only active values may be offered when creating or amending a record.
3. An inactive value already held by an existing record must still display on that record, suffixed *(inactive)*. It must not be blanked, must not be silently substituted, and must not block saving unrelated changes.
4. Options must be ordered by the table's sort-order field, or alphabetically by description where no sort order is defined. They must never be ordered by primary key.
5. The description shown must come from the reference table itself, so that the same value reads identically on every screen, in every export, and in every document generated from the record.
6. A lookup must not be paired with a free-text "Other" box. Where "Other" is a legitimate answer it must be a value in the table, and any required elaboration must be a separate, clearly labelled field.
7. Application logic and page markup must reference lookup values by code, never by numeric identifier.
8. A dependent lookup must clear and disable its child when the parent changes, and the child's placeholder must state the dependency by naming the parent — "Select a region first", not "Select an option".
9. Where a user with maintenance rights encounters a missing value, the control may offer a link to the reference data screen. It must not allow ad-hoc creation of a lookup value from inside a transactional form.

---

## 12. Performance

Fast is a requirement, not a quality of implementation. These budgets apply to every covered application and are tested on a representative dataset, not an empty development database.

### 12.1 Budgets

| Interaction | Budget |
| --- | --- |
| List page, first meaningful render (skeleton) | 200 ms |
| List page, populated with data | 1.5 s |
| Page change, sort change, filter apply | 500 ms |
| Type-ahead search results | 300 ms after the debounce |
| Detail page, populated | 1.5 s |
| Save / transition round trip | 2 s, with busy state throughout |
| Stat card populated | 1 s |

A page that misses its budget on the representative dataset is a defect, regardless of how it performs with ten rows.

### 12.2 Query rules

1. A grid must request only the columns it displays, through a projection DTO. It must not bind to full entity objects.
2. Paging, sorting, filtering, and counting must execute in the database. Fetch-all-then-filter in application code is prohibited at any table size, because tables grow.
3. Every column offered for sorting or filtering must be covered by a database index, and the pull request adding the column must say so.
4. The row count query must be as constrained as the data query. Where an exact count is expensive on very large sets, "1 000+" is acceptable and must be stated as such.
5. Grid data and stat card data must be fetched in parallel, not sequentially.
6. Lookup sets consumed by filters and forms must be cached server-side with a defined expiry, and the cache must be invalidated by the reference data maintenance screen on save.

### 12.3 Rendering rules

These rules apply to every rendering model; the mechanism differs per stack and is named in the application's technology mapping (1.2.1).

1. Lists over 100 displayed rows must be virtualised or windowed (`Virtualize` in Blazor, a windowing library in React/Vue) — or, in a server-rendered application without client scripting, must simply never render more than one page of rows.
2. Type-ahead inputs must debounce at 300 ms and must cancel or discard the response of a superseded request, so a slow earlier query can never overwrite a later one.
3. An update to one row must not re-render the whole grid. In component frameworks this means keyed rows and row-level state; in server-rendered applications, a partial update of the affected row where the stack supports it.
4. Images and heavy panels below the fold (audit trails, document previews) must load on demand, not with the page.
5. Skeletons must match the dimensions of the content they stand in for, so that data arriving does not shift the layout.
6. Client bundles must not grow unbounded: a page must load only the scripts and styles it uses, and adding a dependency that ships to every page requires justification in the pull request.

### 12.4 Perceived performance

1. Navigation must be acknowledged instantly: the destination page renders its header and skeleton immediately and fills in, rather than the origin page freezing until data arrives.
2. Data already known to the client — the row the user clicked — may be used to paint the detail header while the full record loads.
3. A second identical request (double-click, repeated filter) must be suppressed, not queued.

---

## 13. Feedback, loading, and errors

### 13.1 Feedback and error handling

1. Every action must produce visible feedback within 200 ms — a spinner on the invoking button, a skeleton in the loading region, or an immediate result.
2. A button that has been invoked must enter a busy state and must not accept a second click.
3. Success is confirmed by a toast that names what happened in the same words as the action: **Submit for review** produces *Submitted for review*.
4. Errors that the user can correct must appear next to the cause. Errors the user cannot correct must appear as a dismissible alert at the top of the content region, with a reference the support desk can trace.
5. An error message must state what happened and what to do next. It must not apologise, must not blame the user, and must not expose exception text or stack traces.
6. Optimistic UI must not be used for workflow transitions. The status badge changes only once the server confirms.
7. Loss of connection must be handled visibly and must not silently discard unsaved input. In a stateful-connection model (Blazor Server, WebSocket-driven apps) this means a clear reconnection state; in a stateless model it means detecting a failed submission and preserving the user's input for retry.

---

### 13.2 Help and guidance

1. Guidance must be placed where the difficulty is: field-level hint text for input rules, a short explanation next to a decision, a link to the governing document where a rule originates. A separate manual is not a substitute.
2. Empty states must teach rather than merely report. They state what belongs here, why it is empty, and offer the one action that resolves it (11.5).
3. First use of a complex screen may offer a brief orientation, which must be dismissible and never repeated once dismissed.
4. Where a term is specific to the domain or the governing policy, its first appearance on a screen must be explainable in place — a definition on hover and focus, meeting 15.4.7.
5. Error messages carry their own remedy (13.5); help content must not be the place users go to decode an error.
6. Help content must be maintained with the screen it describes. Guidance that has drifted from behaviour is worse than none.

---

## 14. Terminology and microcopy

1. One operation carries one name from start to finish. A button labelled **Recommend for approval** produces the toast *Recommended for approval* and appears in the audit trail as *Recommended for approval*.
2. Labels are in sentence case. Buttons use active verbs. **Save changes**, not *Submit*; **Cancel**, not *Close*.
3. The interface uses the vocabulary of the business it serves, not of the system. Whatever the domain calls the thing — an invoice, a ticket, a claim, a member — that word is used, never the generic *record*, *entity*, or *transaction*. Each application records its entity vocabulary in the technology mapping note (1.2.1) so that terminology stays consistent across screens, exports, notifications, and generated documents.
4. Dates are displayed in one format across all applications. Relative dates ("2 days ago") may be used in list views, and must carry the exact date in a tooltip.
5. Terminology must match the source document set — where a policy calls a step *evaluation*, the interface must not call it *screening*.

---

## 15. Accessibility

### 15.1 Conformance requirement

Every covered application **must conform to WCAG 2.2 Level AA**. That external standard governs; the clauses below do not replace it, restate it in full, or limit it. They call out the criteria most often missed in the kind of application this standard covers, and add requirements beyond WCAG where this standard is stricter.

Conformance is claimed per application in the technology mapping note (1.2.1), naming the version conformed to and any documented exception with its justification and remediation date.

Automated tooling detects roughly a third of WCAG failures. A clean automated report is necessary, never sufficient, and must not be presented as conformance.

### 15.2 Document-level requirements

These are page-wide and are the ones most often absent from applications that are otherwise carefully built.

1. Every page must declare its language on the root element (`lang`), and any passage in a different language must declare its own. *(WCAG 3.1.1, 3.1.2)*
2. Every page must have a unique, descriptive document title naming the page and the application. In an entity page, the title must include the record's reference. *(2.4.2)*
3. Every page must provide a **skip link** to the main content as the first focusable element. A persistent navigation panel on every page makes this mandatory, not optional. *(2.4.1)*
4. Page structure must use semantic landmarks — banner, navigation, main, complementary, contentinfo — with exactly one main region per page. *(1.3.1)*
5. Headings must form a correct hierarchy without skipped levels; the page header title is the sole `h1`. *(1.3.1, 2.4.6)*
6. Data tables must use real table semantics with header cells associated to their columns. A grid built from generic containers must expose equivalent semantics. *(1.3.1)*
7. Inputs collecting information about the user must declare their purpose via autocomplete. *(1.3.5)*

### 15.3 Operation

1. All functionality must be operable by keyboard, in a logical order matching the visual order, with no keyboard trap. *(2.1.1, 2.1.2, 2.4.3)*
2. Focus must be visible and must meet the focus appearance expectations of 2.2. A focus indicator removed for aesthetics is a defect. *(2.4.7, 2.4.13)*
3. **Focus must never be obscured** by fixed chrome. The fixed header, the sticky footer action bar, and any collapsed navigation rail must not cover a focused element; content must be scrolled and padded so that the focused element is fully visible. *(2.4.11, 2.4.12)*
4. Dialogs and drawers must move focus into themselves, trap it while open, close on Escape, and return focus to the invoking element.
5. Where a pointer gesture such as drag is offered — column reordering, for example — a single-pointer alternative must exist. *(2.5.7)*
6. Information the user has already entered in a process must not be requested again, unless re-entry is essential. *(3.3.7)*
7. Target size is governed by 4.6.2.3, which exceeds the WCAG minimum. Where the two differ, this standard's figure applies.

### 15.4 Presentation

1. Colour must never be the only carrier of meaning. *(1.4.1; see also 7.3.4)*
2. Text must meet a 4.5:1 contrast ratio, and large text 3:1. Badge and flag text must be verified against its own background in both light and dark themes. *(1.4.3)*
3. **Non-text contrast**: interface component boundaries, control states, focus indicators, and meaningful graphical elements must meet 3:1 against adjacent colours. This applies to flag outlines, stepper connectors and dots, checkbox borders, and input borders — all easy to lose when muted for visual calm. *(1.4.11)*
4. Content must reflow without loss of information or two-dimensional scrolling at **320 CSS pixels** wide. The verification widths in 4.6.3 are a floor for layout review, not a substitute for this criterion. *(1.4.10)*
5. Text must remain legible and functional at 200% zoom. *(1.4.4)*
6. No loss of content or function when the user overrides line height, paragraph spacing, letter spacing, or word spacing. *(1.4.12)*
7. Content revealed on hover or focus — the collapsed navigation label, a truncation tooltip — must be dismissible, hoverable, and persistent. *(1.4.13)*
8. Motion and transitions must respect a reduced-motion preference. *(2.3.3)*

### 15.5 Forms, errors, and status

1. Every input must have a programmatically associated visible label. Placeholder text must not substitute for a label. *(1.3.1, 3.3.2)*
2. Icon-only actions must carry an accessible name. *(4.1.2)*
3. Errors must be identified in text, associated with their field, and must state how to correct them. *(3.3.1, 3.3.3)*
4. Status messages, toasts, validation summaries, and the results of a filter or transition must be announced through a live region without moving focus. *(4.1.3)*
5. Loading and busy states must be exposed to assistive technology, not conveyed by animation alone.
6. A grid's row count, active sort, current page, and selection count must be available as text to assistive technology, not by visual arrangement alone.

### 15.6 Verification

1. Every page must pass automated accessibility checks in CI with zero violations, as a gate rather than a report.
2. Every page must additionally be checked manually: keyboard-only traversal of the full task, one screen reader, 200% zoom, and 320 px width.
3. A new archetype implementation, a new shared component, and any change to the shell must be checked before the pattern is reused.
4. Known non-conformances must be recorded with an owner and a remediation date. An undocumented non-conformance is a defect; a documented one is a debt.

---

## 16. Compliance checklist

A page is not complete until every item passes.

**Structure**
- [ ] The page implements one named archetype from section 4.
- [ ] The header is rendered by the shared header component.

**Mode**
- [ ] The record opens in View mode.
- [ ] Edit is a distinct route with a visible editing indicator and a sticky footer.
- [ ] Unsaved changes are guarded on cancel and on navigation.
- [ ] Save returns to View mode for the same record.

**State and action**
- [ ] No badge is clickable.
- [ ] Every button label is a verb phrase; every badge label is a noun phrase.
- [ ] There is exactly one primary button.
- [ ] Status colours match the semantic map.
- [ ] Flags are visually distinguishable from the status.
- [ ] Exactly one workflow state badge appears on the page.
- [ ] Workflow state, status, and flags are modelled and rendered as separate concepts (7.4.1).
- [ ] The stepper is non-interactive, capped at seven steps, and emphasises only the current step.
- [ ] Terminal-negative outcomes truncate the stepper and state the reason; remaining steps are dropped, not greyed.
- [ ] A blocking status is shown on the active step, not only in the header.

**Workflow**
- [ ] Transition actions come from the shared transition service.
- [ ] No rendered action can fail on permission or status grounds.
- [ ] Irreversible transitions confirm and name the consequence.
- [ ] Rejection and referral capture a mandatory reason.

**Lists**
- [ ] The list declares its tier (T1, T2, or T3).
- [ ] The grid handles all five states in 11.5, with distinct empty and filtered-empty messages.
- [ ] Paging, sorting, and filtering are server-side where the set can exceed 200 rows.
- [ ] Filter, sort, and page state are held in the URL.
- [ ] Returning from a detail page restores the previous list position.

**Data table baseline**
- [ ] All thirteen capabilities in 11.9 are present.
- [ ] Page size is selectable from 5 / 10 / 20 / 50 / 100 / 250 / 500, defaulting to 20.
- [ ] Column chooser present, with reset; the reference and state columns are not hideable.
- [ ] View preferences persist per user and per grid beyond the session, keyed by grid identifier not URL.
- [ ] A stale preference for a removed column is ignored silently.
- [ ] The unique key column is a real hyperlink to the record's View route, not a cell click handler.
- [ ] Header checkbox selects the page, shows indeterminate state, and selection beyond the page is explicit.
- [ ] Selection survives paging and clears when the filter changes.
- [ ] Export honours current filters and the visible column set.

**Reference data**
- [ ] The lookup lives in the Reference data area, not its own menu entry.
- [ ] Code, Description, Sort order, Active, and Last updated are all present.
- [ ] Values are deactivated, not deleted, once referenced; usage count is shown before any delete.
- [ ] Active status renders as a badge, not a control.
- [ ] Consuming controls offer active values only, and existing records still display inactive values marked as such.
- [ ] The control type matches the set size in 11.10.1.
- [ ] No lookup value is referenced by numeric identifier.

**Performance**
- [ ] The page meets its section 12.1 budget on the representative dataset.
- [ ] The grid binds to a projection DTO, not full entities; paging, sorting, filtering, and counting run in the database.
- [ ] Sortable and filterable columns are index-backed, stated in the pull request.
- [ ] Lists over 100 rows are virtualised; type-ahead is debounced and cancels stale requests.

**Stat cards**
- [ ] Each card carries one figure, with tabular numerals and the exact value in a tooltip where abbreviated.
- [ ] Card surfaces are not clickable; drill-down is a View all link to a filtered list.
- [ ] Trend colour follows meaning, not direction.
- [ ] Card figures and the linked list are driven by the same query definition.
- [ ] Cards show skeletons while loading and an in-card error state on failure — never a silent zero.

**Navigation and responsive**
- [ ] Primary navigation is the left panel, collapsible, with the collapsed state persisted.
- [ ] Collapsed items reveal labels on hover and on keyboard focus; the current location is marked.
- [ ] Navigation depth does not exceed two levels and contains no actions.
- [ ] Shell and layout class names are namespaced and do not collide with the CSS framework.
- [ ] The page is verified at 360 px, 768 px, and 1440 px.
- [ ] No horizontal page scrolling at any width.
- [ ] Grids become stacked cards below the compact breakpoint; they are not shrunk.
- [ ] Touch targets are at least 44 px below the expanded breakpoint.
- [ ] The stepper is vertical on compact screens; the action zone stacks with the primary action first.
- [ ] Drawers and dialogs are full width on compact screens.

**Quality**
- [ ] Every action gives feedback within 200 ms.
- [ ] Error messages state cause and remedy, with no exception text.
- [ ] Keyboard navigation and focus indicators work throughout.

**Accessibility (WCAG 2.2 AA — section 15)**
- [ ] Automated checks pass in CI with zero violations, and manual checks are done: keyboard-only, screen reader, 200% zoom, 320 px.
- [ ] Page declares `lang`, has a unique descriptive title, and a skip link as the first focusable element.
- [ ] Semantic landmarks present, one main region, heading hierarchy unbroken.
- [ ] Content reflows at 320 px with no two-dimensional scrolling.
- [ ] Non-text contrast of 3:1 met by borders, control states, focus rings, stepper dots and connectors, and flag outlines — in both themes.
- [ ] Focus is never obscured by the fixed header, sticky footer, or nav rail.
- [ ] Hover/focus content is dismissible, hoverable, persistent; reduced-motion preference respected.
- [ ] Grid row count, sort, page, and selection count are available as text to assistive technology.
- [ ] Any non-conformance is documented with an owner and a remediation date.

**Design validation and efficiency**
- [ ] High-effort screens and new shared components validated with real users before the pattern is reused (2.5).
- [ ] Reversible actions offer undo rather than a confirmation dialog (8.3.4).
- [ ] Keyboard access for common operations; shortcuts discoverable and non-conflicting (6.4).
- [ ] Empty states teach rather than merely report; guidance sits where the difficulty is (13.2).
- [ ] No hand-rolled markup duplicates a shared component.

---

## 17. Related documents and review

- `DESIGN.md` (or equivalent per application) — visual token system: colour values, typography, spacing.
- `AGENTS.md` / agent operating instructions — must reference this standard.
- Technology mapping note per application (1.2.1) — how this stack implements the framework-dependent requirements.
- Workflow specifications per application — status enumerations and permitted transitions.
- `UI-STANDARD-MIGRATION.md` — what an application on an earlier version must change.

This standard is reviewed every 12 months, or when a new application is added to its scope.

### 17.1 Change history

| Version | Date | Author | Change |
| --- | --- | --- | --- |
| 0.1 | | | First draft. Archetypes, page modes, state/action separation, status taxonomy, actions and transitions, shared components, forms, lists, feedback, terminology, accessibility, checklist. |
| 0.6 | | | Section 15 replaced: requires conformance to **WCAG 2.2 Level AA** as an external standard rather than a hand-written subset, and adds the document-level, reflow, non-text-contrast, focus-obscured and verification requirements previously missing. Adds human-centred design validation (2.5, addressing ISO 9241-210), efficiency for frequent users (6.4), help and guidance (13.2), and undo in preference to confirmation (8.3.4). |
| 0.5.1 | | | Editorial review: restores the missing section 8 heading (content was intact but orphaned under 7.4); converts non-standard clause markers (1a, 3a–3e) to sub-bullets so lists render correctly; renumbers the 11.9 baseline to thirteen capabilities including the key hyperlink; adds the class-namespacing rule (4.5.1.9). No normative change beyond 4.5.1.9. |
| 0.5 | | | Requires the unique key column to be a real hyperlink to the record's View route (11.6.3). Adds layout containment guidance to 4.6: the content column is sized from the remaining space, wide grids scroll within their own container, and page-level overflow is a backstop only. |
| 0.4 | | | Adds the data table baseline (11.9, new) and its twelve required capabilities; the page-size ladder 5–500 with persistence (11.3.3a–3e); the column chooser and cross-session view preferences (11.2.7); and full row-selection rules including select-all-beyond-page (11.6.4–11.6.11). `DataGridShell` contract extended. **Breaking:** former 11.9 (consuming lookup data in forms) is now 11.10. |
| 0.3 | | | Adds the application shell and collapsible left navigation (4.5) and responsive behaviour with breakpoints and verification widths (4.6). Adds `AppShell` and `NavPanel` to section 9 and a Responsive block to the checklist. Inserted as sub-sections under 4 so no existing clause is renumbered. |
| 0.2 | | | Adds stat cards (4.4); workflow state / status / flag distinction and stepper (7.4); list tiers and reference data (11, rewritten); performance budgets and query rules (12, new). Generalised from Blazor / Bootstrap to any web framework (1.2, 1.2.1, 9.1–9.3, 12.3, 13.7). Adds versioning rule (2.4). **Breaking:** sections formerly numbered 12–16 are now 13–17. |
