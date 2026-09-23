# Plan: UX/UI Spacing, Padding, Label Typography & Design System Alignment

## Executive Summary
This plan implements the high-priority UX/UI recommendations from the comprehensive audit, fixing layout spacing/padding issues, missing CSS tokens, label typography inconsistencies, and updating `DESIGN.md`.

## Work Breakdown

### Phase 1: CSS Design Tokens & Missing Primitives
- Add `.bg-surface`, `.border-default`, `.nsdms-form-section`, `.nsdms-context-rail`, `.nsdms-workspace-surface`, `.nsdms-form-shell`, and `.nsdms-control-aligner` to `dotnet/Nsdms.Web/wwwroot/app.css`.
- Harmonize `--nsdms-header-height: 60px;` and `.sticky-top-header` docking offset.

### Phase 2: Shared Component Refinements
- `ReadOnlyField.razor`: Sentence case `0.75rem` label, remove outer `mb-4`, clean tabular values.
- `EntityHeader.razor`: Semantic `<h1>` rendering with `mud-typography-h5` scale.
- `FormShell.razor`: Clean `.nsdms-form-shell` bottom padding (84px).
- `WizardShell.razor`: Semantic `<section>` body, eliminating duplicate `<main>` landmarks.
- `DataGridShell.razor`: Cohesive `.nsdms-workspace-surface` layout.

### Phase 3: Codification in DESIGN.md
- Add Section 12: Form Layout, Label Architecture & Input Field Governance.
- Add Section 13: Spatial Hierarchy, Padding Budget & Container Nesting Constraints.
- Advance revision history to v1.7.

### Phase 4: Validation & Quality Gate
- `dotnet build dotnet/Nsdms.slnx`
- `dotnet test dotnet/Nsdms.Tests/Nsdms.Tests.csproj`
