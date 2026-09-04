# UI Design & MudBlazor Standards Rule

> Enforces ICT-STD-UI-001 (Version 0.6) and MudBlazor design system compliance across all generated or modified screens.

## 1. Page Archetypes (MANDATORY DECLARATION)
Every Razor component must implement exactly one archetype:
- **A1: List Page**: Uses `<DataGridShell>` with standard 7-tier pagination (`5, 10, 20, 50, 100, 250, 500`). Unique key column is a `<MudLink>` navigating to View mode `/{entity}/{id}`.
- **A2: Work Queue**: Uses `<DataGridShell>` with bulk action rail, batch selection, and priority filters.
- **A3: Entity Detail (View Mode)**: Route is `/{entity}/{id}`. Header uses `<EntityHeader>`. Fields are strictly read-only (`<ReadOnlyField>`). Primary action button is "Edit" navigating to `/{entity}/{id}/edit`. Never render editable `<MudTextField>` or Save buttons on `/{entity}/{id}`.
- **A4: Form / Wizard (Edit/Create Mode)**: Route is `/{entity}/{id}/edit` or `/{entity}/create`. Wrapped in `<FormShell>` with sticky bottom actions, dirty tracking (`NavigationLock`), Cancel, and Save buttons.
- **A5: Dashboard / Hub**: Header uses `<EntityHeader>`. Key operational metrics use `<StatCard>` and `<StatCardRow>`. Never synthesize fake mock data when database queries return empty.
- **T3: Reference Data / Lookups**: Grid with inline dialogs or drawers for code maintenance.

## 2. Table Baseline & Pagination Scale
All tables and grids must support the exact 7 page size options:
`PageSizeOptions="new int[] { 5, 10, 20, 50, 100, 250, 500 }"`

## 3. Destructive Action Safeguard
Any destructive action (Delete, Cancel Agreement, Reject with Prejudice) MUST require an affirmative confirmation dialog via `IDialogService.ShowAsync<ConfirmDialog>("Confirm Action", parameters)`. Immediate deletion without confirmation is prohibited.

## 4. Theming, Contrast & Style Isolation
- Hex colors (`#...`) in `.razor` files are forbidden. Use CSS variables (`var(--text-primary)`, `var(--brand-gold)`, `var(--status-success)`).
- Never use `eval` in JS interop for theming. Use dedicated script functions in `wwwroot/js/nsdms-ux.js`.
- Inline `style="..."` attributes are prohibited; use MudBlazor utility classes or scoped `.razor.css` files.

## 5. Multi-Step Wizard Standards (Strict Invariant)
All multi-step workflows must adhere to the 15 Principles in `DESIGN.md` Section 10:
- **Component Mandate**: Multi-step flows MUST use `<WizardShell>`, `<WizardStepper>`, `<WizardStep>`, and `<WizardReviewStep>` from `Components/Shared/Wizard/`. Direct usage of `<MudStepper>` / `<MudStep>` triggers build failure `[NSDMS0001]`.
- **Shared Fields**: Wizard input steps and the standalone entity edit view MUST share the identical field definition component (in `Components/Shared/Forms/`).
- **Review Step**: Every wizard MUST conclude with a comprehensive read-only review step with grouped summary cards and per-step "Edit" jump links.
- **Enforced Footer**:
  - `Cancel` on the left.
  - `Save draft` on the left-centre (available Step 2 onward; hidden on Step 1).
  - `Back` on the right.
  - `Next` / `Finish` primary action button on the far right.
- **No Progress Counts**: Stepper labels must be <= 3 words with status indicators; do not display live progress percentages or counts.
- **Gated Navigation**: The `Next` action must be blocked until the active step passes `ValidateAsync`.
- **Accessibility**: Stepper rendered as semantic `<ol>` with `aria-current="step"`. Focus moves programmatically to step heading on activation.

