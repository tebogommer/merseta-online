---
type: user
created: 2026-07-18
updated: 2026-10-01
---

# User Preferences

## Git & Database Synchronization
- **Script Database on Git Commit & Push**: When committing or pushing to Git, always script the database schema (tables, constraints, indexes, views, stored procedures) and all lookup tables (`lookup.*`) including their reference data/lookup values (using `scripts/script_database.ps1`). Commit and push the generated SQL scripts alongside application code.

## Testing & Quality Gate
- **Playwright Visual & Console Quality Gate**: When testing with Playwright, always include visual, console, and interactivity assertions in the test harness (`playwright_assertions.py`) to guarantee that only true, visually styled, fully interactive pages pass the test suite. Never accept tests that merely verify HTTP 200 without DOM layout styling and interactive readiness.

