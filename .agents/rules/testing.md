# Testing & Testability Governance Rule

> Enforces testing patterns, test isolation, and real database verification.

## 1. Real Database vs InMemory Isolation
- While EF Core InMemory is acceptable for fast domain logic unit tests, it MUST NOT be used to validate schema migrators, raw SQL execution, unique constraints, foreign keys, or temporal tables.
- All schema and DDL tests must execute against real SQL Server instances (LocalDB or Testcontainers) guarded by explicit environment flags (`ENABLE_LIVE_SQL_SYNC == "true"`).

## 2. Test Quality & Assertions
- Tests must assert meaningful business rules, calculations, and invariant enforcement (AAA pattern).
- Avoid coupling unit tests to internal private implementation details or mock-only verification.
- Playwright E2E tests must maintain synchronized accessibility selectors (`aria-label`) matching UI components.

## 3. Playwright Visual, Console & Interactivity Standard
- Playwright test harnesses must incorporate `playwright_assertions.py` to ensure only true, visually styled, fully interactive pages pass:
  - **Console Assertions**: Capture console messages and unhandled page errors; assert zero runtime JS exceptions and zero `console.error` logs.
  - **Visual Styling Assertions**: Verify CSS stylesheets are attached and loaded, MudBlazor CSS custom properties are resolved (`--mud-palette-primary`), and structural layout containers (`.mud-layout`, `main#main-content`, `.mud-main-content`, `.mud-container`) are visible with non-zero dimensions (`width >= 200px`, `height >= 100px`).
  - **Interactivity Assertions**: Verify Blazor Server interactive circuit stability (no reconnect/disconnect failures) and ensure interactive elements (buttons, links, inputs) are visible, enabled, clickable (`pointer-events != 'none'`), and responsive.

## 4. End-to-End Automated Dynamic CRUD & Intake Testing Governance Standard
- **Dynamic RSA ID Generation**:
  - Automated end-to-end tests performing person intake (`/people/create`) must NEVER use static or hardcoded 13-digit RSA National ID numbers (e.g. `8001015009087`).
  - Because `PersonService.CreateAsync` strictly validates database uniqueness on `RsaIdNumber`, hardcoded IDs cause subsequent regression runs to fail with duplicate key exceptions.
  - Test suites must dynamically compute mathematically valid RSA IDs using the Luhn algorithm with an ephemeral gender sequence counter (e.g. `(int(time.time()) % 4000) + 5500` for males) to guarantee validity and uniqueness across continuous test runs.
- **DG Funding Window Template Blueprint Pre-Configuration**:
  - In automated intake of Discretionary Grant funding windows (`/dg-funding-windows/create`), statutory governance requires at least one eligible stakeholder classification and at least one allowed intervention.
  - Tests should trigger the 1-Click Template Blueprint Specification Engine (`.cursor-pointer:has-text('PIVOTAL')`) to pre-populate compliant statutory combinations prior to form submission.
- **Multi-Step Modal Dialog Deletion Verification**:
  - Destructive entity deletion tests must not stop at clicking the page-level "Delete" button; tests must explicitly assert the presence of `<ConfirmDialog>` (`.mud-dialog`), capture dialog state, and dispatch the affirmative action (`.mud-dialog button:has-text('Delete ...')`) to verify true database cascading removal and list route redirection.

