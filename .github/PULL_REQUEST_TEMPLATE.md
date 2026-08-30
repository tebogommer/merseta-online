# Pull Request — UI Standard Compliance Checklist

## Archetype & Scope
- **Archetype Implemented:** [ ] A1 List | [ ] A2 Work Queue | [ ] A3 Entity Detail | [ ] A4 Form/Wizard | [ ] A5 Dashboard | [ ] T3 Reference Data
- **Route(s):** 
- **Entity Vocabulary Nouns Used:** 

---

## Compliance Checklist (UI-STANDARD.md Section 16)

### Structure
- [ ] The page implements one named archetype from section 4.
- [ ] The header is rendered by the shared header component.

### Mode
- [ ] The record opens in View mode.
- [ ] Edit is a distinct route with a visible editing indicator and a sticky footer.
- [ ] Unsaved changes are guarded on cancel and on navigation.
- [ ] Save returns to View mode for the same record.

### State and Action
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

### Workflow
- [ ] Transition actions come from the shared transition service.
- [ ] No rendered action can fail on permission or status grounds.
- [ ] Irreversible transitions confirm and name the consequence.
- [ ] Rejection and referral capture a mandatory reason.

### Lists
- [ ] The list declares its tier (T1, T2, or T3).
- [ ] The grid handles all five states in 11.5, with distinct empty and filtered-empty messages.
- [ ] Paging, sorting, and filtering are server-side where the set can exceed 200 rows.
- [ ] Filter, sort, and page state are held in the URL.
- [ ] Returning from a detail page restores the previous list position.

### Data Table Baseline
- [ ] All thirteen capabilities in 11.9 are present.
- [ ] Page size is selectable from 5 / 10 / 20 / 50 / 100 / 250 / 500, defaulting to 20.
- [ ] Column chooser present, with reset; the reference and state columns are not hideable.
- [ ] View preferences persist per user and per grid beyond the session, keyed by grid identifier not URL.
- [ ] A stale preference for a removed column is ignored silently.
- [ ] The unique key column is a real hyperlink to the record's View route, not a cell click handler.
- [ ] Header checkbox selects the page, shows indeterminate state, and selection beyond the page is explicit.
- [ ] Selection survives paging and clears when the filter changes.
- [ ] Export honours current filters and the visible column set.

### Reference Data
- [ ] The lookup lives in the Reference data area, not its own menu entry.
- [ ] Code, Description, Sort order, Active, and Last updated are all present.
- [ ] Values are deactivated, not deleted, once referenced; usage count is shown before any delete.
- [ ] Active status renders as a badge, not a control.
- [ ] Consuming controls offer active values only, and existing records still display inactive values marked as such.
- [ ] The control type matches the set size in 11.10.1.
- [ ] No lookup value is referenced by numeric identifier.

### Performance
- [ ] The page meets its section 12.1 budget on the representative dataset.
- [ ] The grid binds to a projection DTO, not full entities; paging, sorting, filtering, and counting run in the database.
- [ ] Sortable and filterable columns are index-backed, stated in the pull request.
- [ ] Lists over 100 rows are virtualised; type-ahead is debounced and cancels stale requests.

### Stat Cards
- [ ] Each card carries one figure, with tabular numerals and the exact value in a tooltip where abbreviated.
- [ ] Card surfaces are not clickable; drill-down is a View all link to a filtered list.
- [ ] Trend colour follows meaning, not direction.
- [ ] Card figures and the linked list are driven by the same query definition.
- [ ] Cards show skeletons while loading and an in-card error state on failure — never a silent zero.

### Navigation and Responsive
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

### Quality
- [ ] Every action gives feedback within 200 ms.
- [ ] Error messages state cause and remedy, with no exception text.
- [ ] Keyboard navigation and focus indicators work throughout.

### Accessibility (WCAG 2.2 AA — section 15)
- [ ] Automated checks pass in CI with zero violations, and manual checks are done: keyboard-only, screen reader, 200% zoom, 320 px.
- [ ] Page declares `lang`, has a unique descriptive title, and a skip link as the first focusable element.
- [ ] Semantic landmarks present, one main region, heading hierarchy unbroken.
- [ ] Content reflows at 320 px with no two-dimensional scrolling.
- [ ] Non-text contrast of 3:1 met by borders, control states, focus rings, stepper dots and connectors, and flag outlines — in both themes.
- [ ] Focus is never obscured by the fixed header, sticky footer, or nav rail.
- [ ] Hover/focus content is dismissible, hoverable, persistent; reduced-motion preference respected.
- [ ] Grid row count, sort, page, and selection count are available as text to assistive technology.
- [ ] Any non-conformance is documented with an owner and a remediation date.

### Design Validation and Efficiency
- [ ] High-effort screens and new shared components validated with real users before the pattern is reused (2.5).
- [ ] Reversible actions offer undo rather than a confirmation dialog (8.3.4).
- [ ] Keyboard access for common operations; shortcuts discoverable and non-conflicting (6.4).
- [ ] Empty states teach rather than merely report; guidance sits where the difficulty is (13.2).
- [ ] No hand-rolled markup duplicates a shared component.

---
## Deviations / Justifications
*(List any clauses deviated from with justification as required by 2.2)*
