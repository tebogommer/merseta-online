# NSDMS Test Plan

## 1. What’s Tested in this Repository
- **E2E Core Journey**: Learner Enrollments, Employer registrations, and Provider Accreditation workflows.
- **Unit/Integration**: Server action isolation logic (Zod validation responses vs actual Prisma mutation).

## 2. Schedule
- **Local Development**: Vitest executes actively using `--watch` mode during development. Playwright executes manually prior to PR creation.
- **CI Pipelines**: Full `--cov` (Vitest) and `headless` (Playwright) execution run asynchronously on all branch pushes against a `test.db`.

## 3. Responsibilities
- **AI Agent**: Responsible for scaffolding tests and ensuring automation coverage.
- **User/Reviewer**: Validates business rule interpretations in the Traceability Matrix.

## 4. Regression Approach
Since mutations require audit logs, the core regression test suite actively queries the `AuditLog` database table for parity against UI CRUD operations.

## 5. Reporting
Reports are generated using standard reporters (`@playwright/test` standard HTML output, and `vitest` v8 `.txt` and `.html` outputs). They are stored contextually in the `/docs/testing/results` folder.
