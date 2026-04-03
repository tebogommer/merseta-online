# NSDMS Test Strategy

## 1. Scope & Test Pyramid
The strategy adheres to a functional test pyramid prioritizing deterministic feedback:
- **Unit/Integration (Vitest)**: Fast validation of server actions (`_actions/workflow`), Zod validation schemas (`_validators`), and complex capability logic (`lib/abilities.ts`).
- **E2E (Playwright)**: User Journey testing validating the Next.js UI integration with the Prisma DB Layer and CASL Auth flow.

## 2. Quality Risks
- **Security & Authorization**: Bypassing UI elements to execute restricted server actions (e.g. STANDARD user calling `deleteWSPAction()`).
- **Workflow Governance**: Skipping mandated XState transitions and ending up in invalid state.
- **Data Integrity**: Submitting related records (e.g. Visits) without required relational keys (`contactPersonId`).
- **Auditability**: Database mutations succeeding without corresponding `AuditLog` entries.

## 3. Environments & Determinism
Tests will execute exclusively against a local configuration of SQLite (`dev.db`). E2E scripts will not parallelize database actions to avoid Windows SQLite locking errors (`query_engine.dll.node` file lock error).

## 4. Entry / Exit Criteria
- **Entry**: PR opened, code compiled (`npm run build` succeeds).
- **Exit**: Vitest suite 100% pass, Playwright suite 100% pass. No P0 bugs unresolved.

## 5. Severity Model
*(Reference `GEMINI.md` Controlled Fix-As-You-Go Bug Policy for full details)*
- **P0**: Security/CASL bypasses, Workflow transitions violated, Atomicity failures (Audit missing). FIX IMMEDIATELY.
- **P1**: Non-blocking validation, minor miscalculations. FIX POST-PASS.
- **P2**: Cosmetic layout issues. DEFER.
