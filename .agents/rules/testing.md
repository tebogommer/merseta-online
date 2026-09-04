# Testing & Testability Governance Rule

> Enforces testing patterns, test isolation, and real database verification.

## 1. Real Database vs InMemory Isolation
- While EF Core InMemory is acceptable for fast domain logic unit tests, it MUST NOT be used to validate schema migrators, raw SQL execution, unique constraints, foreign keys, or temporal tables.
- All schema and DDL tests must execute against real SQL Server instances (LocalDB or Testcontainers) guarded by explicit environment flags (`ENABLE_LIVE_SQL_SYNC == "true"`).

## 2. Test Quality & Assertions
- Tests must assert meaningful business rules, calculations, and invariant enforcement (AAA pattern).
- Avoid coupling unit tests to internal private implementation details or mock-only verification.
- Playwright E2E tests must maintain synchronized accessibility selectors (`aria-label`) matching UI components.
