---
type: project
created: 2026-05-25
updated: 2026-07-12
---

# Project Conventions

## Git Workflow
- Always create a new dedicated branch for major code changes.
- Branch name format should follow: `feature/[task-slug]` or `fix/[bug-slug]`.

## Supported AI platforms (AG Kit)
- AG Kit **only supports Gemini CLI and Google Antigravity**.
- Do not claim compatibility with Claude Code, Cursor, Copilot, Windsurf, or other assistants unless the user explicitly expands scope.
- Copy on the website, docs, FAQ, README, and marketing should describe AG Kit as a toolkit for Gemini CLI / Antigravity-style agent setups.

## Database & EF Core Naming Conventions
- Database entities, tables, and properties must use **PascalCase** (e.g. `GrantMoa`, `SetmisSubmissionBatch`, `MandatoryGrantDisbursement`, `Organisation`, `Visit`).
- Every business table must have an auto-generated integer primary key `Id` and audit columns (`CreatedAt`, `CreatedBy`, `ModifiedAt`, `ModifiedBy`).
- Lookup tables must be placed under a dedicated **`lookup` schema** (e.g., `lookup.CategoryType`, `lookup.StatusType`, `lookup.ProvinceType`) with `*Type` suffix and unique `Code` (`varchar(15)`).
- Implement explicit performance indexing on all foreign keys, status columns, and search queries in Fluent API.
- Idempotent T-SQL DDL migrators must accompany every new module under `dotnet/Nsdms.Infrastructure/Data/`.

## Clean Architecture & Living Documentation
- Every service mutation must perform a double-write into `audit_logs` with a structured `MetadataJson` snapshot.
- Application services must use `INsdmsDbContextFactory` for thread-safety in Blazor Server interactive circuits.
- Add unit tests in `Nsdms.Tests` for every business rule and calculation.
- Maintain and update `test_all_pages_playwright.py` with every new route created to guarantee 100% test coverage.

