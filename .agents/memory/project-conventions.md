---
type: project
created: 2026-05-25
updated: 2026-08-30
---

# Project Conventions

## Porting Source Baseline
- The definitive, canonical source codebase for all migration and porting into .NET 10 is located at: `C:\Antigravity\nsdms-2026-04-01\nsdms\MerSETA\NSDMS-Latest`.
- All legacy business rules, workflows, calculations, and data structures must be referenced directly from this directory.
- **Strict Read-Only Invariant**: NEVER modify, delete, format, or write code into the `NSDMS-Latest/` folder. It is an immutable legacy reference source.

## Git Workflow
- Always create a new dedicated branch for major code changes.
- Branch name format should follow: `feature/[task-slug]` or `fix/[bug-slug]`.

## Supported AI platforms (AG Kit)
- AG Kit **only supports Gemini CLI and Google Antigravity**.
- Do not claim compatibility with Claude Code, Cursor, Copilot, Windsurf, or other assistants unless the user explicitly expands scope.
- Copy on the website, docs, FAQ, README, and marketing should describe AG Kit as a toolkit for Gemini CLI / Antigravity-style agent setups.

## Domain Nomenclature & Entity Semantics
- We work with **Organisations** across all entity types (NPOs, NGOs, public entities, private corporations, levy payers, non-levy payers).
- Always use **`Organisation`** (not `Company`) across database tables, entity models, DTOs, service methods, and UI views, unless referring to a specific corporate subtype.

## Database & EF Core Naming Conventions
- Database entities, tables, and properties must use **PascalCase** (e.g. `Organisation`, `OrganisationContact`, `OrganisationSite`, `GrantMoa`, `SetmisSubmissionBatch`, `MandatoryGrantDisbursement`, `Visit`).
- Every business table must have an auto-generated integer primary key `Id` and audit columns (`CreatedAt`, `CreatedBy`, `ModifiedAt`, `ModifiedBy`).
- Lookup tables must be placed under a dedicated **`lookup` schema** (e.g., `lookup.CategoryType`, `lookup.StatusType`, `lookup.ProvinceType`) with `*Type` suffix and unique `Code` (`varchar(15)`).
- Implement explicit performance indexing on all foreign keys, status columns, and search queries in Fluent API.
- Idempotent T-SQL DDL migrators must accompany every new module under `dotnet/Nsdms.Infrastructure/Data/`.

## Clean Architecture & Living Documentation
- Every service mutation must perform a double-write into `audit_logs` with a structured `MetadataJson` snapshot.
- Application services must use `INsdmsDbContextFactory` for thread-safety in Blazor Server interactive circuits.
- Add unit tests in `Nsdms.Tests` for every business rule and calculation.
- Maintain and update `test_all_pages_playwright.py` with every new route created to guarantee 100% test coverage.

## Employer Visit & Contact Person Invariant
- When building features that schedule or execute ANY type of "Visit" or "Monitoring" activity against an Employer, ALWAYS enforce selection of a specific Contact Person (`ContactPersonId` / `contact_person_id`).
- Backend services must explicitly validate the `ContactPersonId` relational link before saving any visit record.

## CASL Role-Based Authorization & Visibility Standard
- When implementing CASL ability checks in UI components for visibility, ALWAYS check for both `View` and `Manage` actions (e.g., `CaslAbilityService.CanViewOrManage(context, "Model")` or `ability.can('View', 'Model') || ability.can('Manage', 'Model')`) because CASL treats these actions as strictly distinct unless aliases are explicitly configured.
- Admin users have global visibility and full CRUD permissions across all modules.
- Non-admin users are strictly scoped to their `DefaultOrganisationId` tenant boundary.

## MudBlazor Layout & Sticky Action Bar Standard
- Never place utility padding classes (`Class="pa-*"`, `Class="pt-*"`, `Class="py-*"`) directly on `<MudMainContent>`. MudBlazor utility classes apply `!important` which overrides the framework's calculated `padding-top: var(--mud-appbar-height)` (64px) and causes the header to overlap page content by 48px.
- Always nest inner padding inside `<MudMainContent>`: `<MudMainContent><div class="pa-4"><main id="main-content">@Body</main></div></MudMainContent>`.
- All sticky action bars, detail top bars, and table toolbars must use `top: var(--mud-appbar-height, 64px) !important;` (or the `.sticky-top` / `.sticky-top-header` CSS classes) so they dock flush underneath the `MudAppBar` during scroll.
- When editing a record, the edit button must expose all editable fields in full view with Cancel and Save buttons (including icon indicators from `Icons.Material.Filled`), and all mutations must produce `ISnackbar` toast feedback.

## RSA ID Demographics Helper
- Any Razor component capturing a South African National ID number must bind an `OnBlur` / `TextChanged` handler to automatically extract and populate Date of Birth, Gender, and Citizenship via `RsaIdValidator.Parse`.

## Dynamic Configuration & Feature Flags Governance
- **Zero Hardcoding Invariant**: No business parameter, threshold, storage path, or external integration endpoint may be hardcoded. Always use `ISystemConfigurationService` with cascading database overrides.
- **Integrations Off-By-Default**: All external integrations (Dynamics GP, Sage, Live DHET SFTP, Live SARS FTP, SMS OTP, Azure Blob) MUST default to `IsEnabled = false`. Workflows must cleanly execute in mock simulation mode when disabled.

## EF Core Nullability & SETMIS Schema Resilience Standard
- **Optional Relational Codes**: In EF Core entities representing legacy or SETMIS records (`LearnerTradeTest`, `CompanyLearner`, `Person`, `TrainingProvider`), declare all optional foreign key string properties as nullable (`string?`) to prevent `SqlNullValueException` when existing database rows contain NULLs.
- **Explicit Singular Table Names**: When defining new `DbSet<T>` properties in `INsdmsDbContext` and `NsdmsDbContext`, always configure `modelBuilder.Entity<T>().ToTable("SingularName")` in Fluent API to ensure EF Core does not default to plural table names.


