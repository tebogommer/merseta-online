# Project Plan: .NET 10 Blazor WebApp & SQL Express Migration

**Slug:** `blazor-net10`  
**Primary Agent:** `backend-specialist` + `frontend-specialist`  
**Lead Planning Agent:** `project-planner`  
**Mode:** Clean Architecture Full-Stack Migration

---

## 1. Overview
Abandon the incomplete Next.js / SQLite prototype in `nsdms-next/` and establish a clean, production-grade .NET 10 Clean Architecture solution (`Nsdms.sln`) with Blazor Web App (MudBlazor UI) connected to SQL Server Express database `NSDMS-NET` on `localhost`.

### Key Constraints & Rules:
- **Zero Document Loss**: Merge any doc additions from `nsdms-next/docs` into `docs/` and preserve all root documentation, SQL dumps, legacy Java reference code, and specs.
- **Database**: SQL Server Express on `localhost`, DB `NSDMS-NET`, User `NSDMS-NET`, Password `NSDMS-NET`.
- **Naming Conventions**: Singular snake_case database tables with integer primary keys, audit columns (`created_at`, `created_by`, `modified_at`, `modified_by`), lookup `*_type` tables with unique codes/names and active flags.
- **UI Architecture**: Stacked Master-Detail pattern (Data table -> Full-page detail view with breadcrumbs, Cancel/Save, Tabs for General + Related Child grids, Toast feedback on mutations).

---

## 2. Solution Structure
```
Nsdms.sln
├── src/
│   ├── Nsdms.Domain/
│   │   ├── Common/              # BaseEntity, IAuditableEntity, AuditLog
│   │   ├── Entities/            # Organisation, Visit, GrantApplication, Person, User
│   │   ├── Lookups/             # SectorType, StatusType, ProvinceType, etc.
│   │   └── Enums/               # WorkflowStatus, RoleEnum
│   ├── Nsdms.Application/
│   │   ├── Common/              # Interfaces, DTOs, Mappings, Behaviors
│   │   ├── Features/            # Handlers / Services organized by feature
│   │   └── Validators/          # FluentValidation schemas
│   ├── Nsdms.Infrastructure/
│   │   ├── Data/                # NsdmsDbContext, Configurations (EF Core)
│   │   ├── Interceptors/        # AuditableEntityInterceptor
│   │   └── Migrations/          # EF Core SQL Server migrations
│   └── Nsdms.Web/
│   │   ├── Components/          # Blazor layouts, common controls, master-detail components
│   │   ├── Pages/               # Employers, Visits, Grants, Admin lookups
│   │   ├── Services/            # UI state, Notification bridges
│   │   ├── appsettings.json     # ConnectionStrings (NSDMS-NET)
│   │   └── Program.cs           # Dependency Injection & Middleware
└── tests/
    └── Nsdms.Tests/             # xUnit & integration tests
```

---

## 3. Task Breakdown

### Task 1: Preserve Documentation & Clean Workspace
- **Agent**: `project-planner`
- **Skills**: `clean-code`
- **Input**: Current workspace containing `nsdms-next/` and documentation folders
- **Output**: Merged `docs/workflows/` and `docs/UI-doc.md`, deleted `nsdms-next/` directory
- **Verify**: `docs/` contains all workflow & UI docs; `nsdms-next/` is removed; all SQL scripts and Java sources remain untouched.

### Task 2: Scaffold .NET 10 Clean Architecture Solution
- **Agent**: `backend-specialist`
- **Skills**: `clean-code`, `api-patterns`
- **Input**: Cleaned workspace with .NET 10 SDK (`10.0.303`)
- **Output**: `Nsdms.sln` with `Nsdms.Domain`, `Nsdms.Application`, `Nsdms.Infrastructure`, `Nsdms.Web`, `Nsdms.Tests`
- **Verify**: `dotnet build Nsdms.sln` compiles with 0 errors.

### Task 3: Infrastructure, EF Core & SQL Server Configuration
- **Agent**: `database-architect` / `backend-specialist`
- **Skills**: `database-design`
- **Input**: Database parameters (Host: `localhost`, DB: `NSDMS-NET`, User: `NSDMS-NET`, Pass: `NSDMS-NET`)
- **Output**: `NsdmsDbContext`, EF Core SQL Server package references, connection strings in `appsettings.json`, and automatic audit interceptor
- **Verify**: DbContext connection test passes against SQL Server Express.

### Task 4: Core Domain Entities & Fluent API Configurations
- **Agent**: `backend-specialist`
- **Skills**: `clean-code`, `database-design`
- **Input**: Schema requirements (Singular snake_case, ID column, audit columns, `*_type` lookups, indexed FKs)
- **Output**: Domain entities (`Organisation`, `Person`, `User`, `Visit`, `LookupType`) with EF Core entity configurations
- **Verify**: Initial EF Core migration builds and models align with business rules.

### Task 5: MudBlazor Integration & Stacked Master-Detail Layout
- **Agent**: `frontend-specialist`
- **Skills**: `frontend-design`, `clean-code`
- **Input**: `Nsdms.Web` project
- **Output**: MudBlazor package integration, custom theme, master layout, reusable Data Grid, Detail Shell with Breadcrumbs, Tabs, and Toast feedback
- **Verify**: Blazor app launches on `localhost`, displays navigation and responsive table grid with drill-down actions.

---

## 4. Phase X: Final Verification Checklist

- [ ] Build succeeds with 0 errors: `dotnet build Nsdms.sln`
- [ ] Database connection string configured for `NSDMS-NET` on SQL Server Express
- [ ] All documentation files preserved in `docs/`, `doc/`, `Notes/`
- [ ] CRUD and Stacked Master-Detail UI patterns active with confirmation toasts
- [ ] Unit & integration test suite executes with `dotnet test`
