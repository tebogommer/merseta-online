# Memory Index

## Project
- [project] Base all porting on NSDMS-Latest (never modify NSDMS-Latest folder) → project-conventions.md
- [project] Always create a new dedicated branch for major code changes → project-conventions.md
- [project] AG Kit only supports Gemini CLI and Google Antigravity (not other AI coding tools) → project-conventions.md
- [project] Use Organisation (not Company) across DB, entities, services, and UI (covers NPOs, NGOs, public entities, corporations) → project-conventions.md
- [project] Use PascalCase on DB, lookup schema, Code (varchar 15) PK for lookups, performance indexing → project-conventions.md
- [project] Employer Visit activity must always enforce and validate ContactPersonId link → project-conventions.md
- [project] CASL ability checks must check both View and Manage (CanViewOrManage) → project-conventions.md
- [project] MudBlazor nested layout invariant: never place pa-* on MudMainContent; top bars dock at 64px → project-conventions.md
- [project] Zero hardcoding invariant: all parameters via ISystemConfigurationService, integrations off by default → project-conventions.md
- [project] UI/UX 5-Pillar Compliance: WCAG 2.2 AA (landmarks, aria-labels), NN/g 10 heuristics, ISO 9241, IxDF (>=36px targets), and Lighthouse Vitals → project-conventions.md
- [project] Form Keybindings & Empty States: Ctrl+S to save, '/' to search, Esc to cancel, <EmptyStateCard> for empty tables → project-conventions.md
- [project] CI Quality Gate: All changes must pass python scripts/ci_ux_quality_gate.py with 0 errors and >=90% clean pass → project-conventions.md
- [project] Systemic Bug Remediation: Fix bugs across entire app and enforce regression tests to prevent recurrence → project-conventions.md
- [project] When pushing to git, generate and commit DDL SQL script with lookup tables and seed values aligned with code → project-conventions.md
- [testing] Playwright visual & console assertions: enforce zero console errors, loaded stylesheets, MudBlazor CSS variables, non-zero bounding box layout, and active interactive controls → project-conventions.md
- [project] Component metadata uses SemVer while toolkit releases use CalVer → tech-decisions.md
- [project] Always add Core Infrastructure & Architecture Invariants block to GEMINI.md when generated → project-conventions.md
- [project] Ensure transactions are managed properly for data integrity (atomic double-writes, CreateExecutionStrategy, RCSI) → project-conventions.md
- [project] UI Accessibility & Landmarks: single <main> on MainLayout, EntityHeader uses HtmlTag="h1", wizards use <section> → project-conventions.md
- [project] Form Field Spacing & Typography: zero child mb-4 margins, sentence case labels, 84px bottom padding on FormShell → project-conventions.md
- [testing] Static cache test isolation: clear static in-memory lookup caches during Arrange in unit/integration test fixtures → project-conventions.md
- [project] ISO 9001:2015 & DPSA Directive Compliant Atomic Audit Logging Standard: Pre-validate specs before flush, execute mutations inside CreateExecutionStrategy().ExecuteAsync() + BeginTransactionAsync(), enforce zero anonymous actors, UTC precision, POPIA PII masking, PFMA maker-checker segregation of duties, zero partial commits → project-conventions.md
- [testing] Atomic audit test isolation: pre-validate specs before SaveChangesAsync and invoke db.ChangeTracker.Clear() on error when tx is null (EF Core InMemory compatibility) → project-conventions.md
- [testing] Windows MSBuild project reference locks: when dev server is running on Windows, use /p:BuildProjectReferences=false on dotnet test / dotnet build to prevent locked dll collision → project-conventions.md
- [project] Always update GEMINI.md with architectural invariants, statutory controls, and prevention guidance → project-conventions.md
- [project] AGSA & ISO 27001 ITGC Coding Invariants: Append-only audit entities, period-filtered reports with DLP & SHA-256 seal, statutory lexicon, and external tool hooks → project-conventions.md
## Preferences
- [preference] Test suite verification: add visual/console assertions to Playwright harness so only true, visually styled, fully interactive pages pass → user-preferences.md
## Feedback
- [feedback] Human test cases must use UI menu names and button labels, never raw URLs → feedback-history.md

