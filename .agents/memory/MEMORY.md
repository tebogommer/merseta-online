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
- [project] Component metadata uses SemVer while toolkit releases use CalVer → tech-decisions.md


