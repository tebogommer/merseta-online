---
name: code-review-checklist
description: Comprehensive 11-dimension review guidelines covering code quality, security, performance, design, and UI standards.
when_to_use: "When reviewing code for quality, security, performance, design, and UI standards. When proposing or reviewing any code change."
allowed-tools: Read, Glob, Grep
version: 2.0.0
---

# Code Review Checklist (11 Dimensions)

## 1. Architecture & Layering
- [ ] No direct `DbContext` or `IDbContextFactory` injection in Razor components.
- [ ] Business logic isolated in application services, not in `@code` blocks.
- [ ] Clean Architecture boundaries respected; no data access leaks in domain or presentation layers.
- [ ] No god classes; large views decomposed into focused sub-components.

## 2. Modern Layout & Conventions
- [ ] Follows current .NET 10 idioms (C# 14, minimal APIs, pattern matching).
- [ ] Render mode declared deliberately (`InteractiveServer`); no mixed modes without clear architecture.
- [ ] Sentence case applied to all UI labels, table headers, and buttons.
- [ ] Code-behind / partial classes or clean `@code` separation maintained consistently.

## 3. Security & Access Control
- [ ] Page declares `@attribute [Authorize]` or `@attribute [Authorize(Roles = "...")]`.
- [ ] Minimal API endpoints protected with `.RequireAuthorization()`.
- [ ] No hardcoded connection strings, secrets, or fallback credentials in code.
- [ ] Parameterised queries only; zero interpolated string execution in raw SQL.
- [ ] Input validated and sanitized; protection against SQL injection and XSS.
- [ ] Sensitive citizen and employee data masked / protected per POPIA guidelines.

## 4. Performance & Scalability
- [ ] No unbounded queries; all lists implement paging or limit with `.Take()`.
- [ ] Read-only queries append `.AsNoTracking()`.
- [ ] Aggregations performed on database server (`CountAsync()`, `GroupBy()`), not in-memory.
- [ ] No sequential round-trip waterfalls in `OnInitializedAsync`; lookups bundled and cached.
- [ ] Query patterns supported by appropriate database indexes.

## 5. Technical Debt & Code Quality
- [ ] No TODOs, dead code, commented-out blocks, or temporary workarounds left in place.
- [ ] No magic numbers or hardcoded status strings; uses strongly typed enums or constants.
- [ ] DRY principle followed without over-abstraction.
- [ ] SOLID principles adhered to; single responsibility enforced.

## 6. Hard-Coding & Configuration
- [ ] Zero hardcoded external URLs, paths, or connection strings.
- [ ] Base URLs resolved dynamically from `ISystemConfigurationService` or `IConfiguration`.
- [ ] User-facing strings and options sourced from lookup tables or localization resources.
- [ ] All feature toggles and integration switches default to off (`false`).

## 7. AI Slop & Code Hygiene
- [ ] No unreviewed AI boilerplate, redundant self-evident comments, or placeholder variables.
- [ ] No synthetic mock data fallbacks left in production dashboards or lists.
- [ ] Error messages are helpful, domain-specific, and non-generic.

## 8. Error Handling & Observability
- [ ] No empty `catch` blocks or catch-alls swallowing exceptions.
- [ ] Structured logging with `ILogger` at service and boundary layers with correlation IDs.
- [ ] No raw `ex.Message` or internal database schema exposed in user-facing toasts or UI text.
- [ ] Global `<ErrorBoundary>` configured to protect SignalR circuits.

## 9. Testing & Testability
- [ ] Unit tests present for new business rules and calculations using AAA pattern.
- [ ] Schema migrators, raw SQL, and temporal tables validated against real SQL Server (LocalDB/Testcontainers).
- [ ] Playwright E2E accessibility selectors (`aria-label`) aligned with UI component definitions.

## 10. Maintainability & Readability
- [ ] Self-documenting code with clear, idiomatic naming conventions.
- [ ] Functions kept small and focused; parameter lists kept reasonable.
- [ ] Non-obvious architectural decisions documented in code comments or ADRs.

## 11. Design, UX & UI Standards (ICT-STD-UI-001)
- [ ] Declares which archetype it implements (A1 List, A2 Work Queue, A3 Detail, A4 Form, A5 Dashboard, T3 Lookup).
- [ ] **View by default**: Record opens in read-only **View** mode (`/{entity}/{id}`) with `<ReadOnlyField>`. Edit is a separate route (`/{entity}/{id}/edit`).
- [ ] Detail view uses `<EntityHeader>`; edit/create view uses `<FormShell>` with sticky footer actions and dirty tracking.
- [ ] Destructive actions (Delete) require affirmative confirmation via `<ConfirmDialog>`.
- [ ] Data grids provide 7 page sizes: `5, 10, 20, 50, 100, 250, 500`.
- [ ] Unique key column in tables is a hyperlink navigating to View mode.
- [ ] All colors and spacing come from theme tokens; zero inline hex color codes (`#...`).
- [ ] No arbitrary inline `style="..."` attributes; uses utility classes or `.razor.css`.
- [ ] Status indicators are non-clickable `<StatusBadge>` chips; action buttons are distinct controls.
- [ ] **Multi-Step Wizards**:
  - [ ] Multi-step flows use `<WizardShell>` with standard footer layout (`Cancel` left, `Save draft` left-centre, `Back` and `Next/Finish` right).
  - [ ] Direct `<MudStepper>` / `<MudStep>` prohibited (passes build guard `[NSDMS0001]`).
  - [ ] Wizard and entity edit view share a single field definition component.
  - [ ] Step review includes per-step edit jump links and lifecycle summary.
  - [ ] Parity unit tests pass locally (`dotnet test --filter FullyQualifiedName~WizardParityTests`).

