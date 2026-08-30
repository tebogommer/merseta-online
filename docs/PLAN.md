# NSDMS Modernization: Master Orchestration Plan (Option A - Layered Stride)

## 📌 Executive Overview
This plan coordinates the migration of legacy Java EE NSDMS into a production-ready **.NET 10 Blazor Web App (MudBlazor)** solution connected to **SQL Server Express (`NSDMS-NET`)**.

Following **Option A ("Layered Stride")**:
1. **Core Kernel & Identity**: Complete ASP.NET Core Identity with strict separation between `Person` (demographics) and `ApplicationUser` (authentication).
2. **Master Data CRUD**: Build comprehensive domain CRUD for `Organisation` (Stakeholder/Employer), `Person`, and `OrganisationContact`/`OrganisationSdf` linkages using the Stacked Master-Detail UI pattern.
3. **Workflow Engine**: Establish the blueprints for the **Dynamic State Matrix Workflow Engine**, designed for simple database-driven configuration and ready to "power-up" business modules once CRUD is locked in.

---

## 🏗️ Phase 1: Security, Identity & Master Data Core (Current Target)

### 1.1 Identity & Access Control
- Scaffold ASP.NET Core Identity with SQL Server (`app_user`, `app_role`, `app_user_role`, `app_user_claim`).
- `ApplicationUser` inherits `IdentityUser<int>` and holds a foreign key to `PersonId`.
- Seed default SETA roles: `Admin`, `Primary_SDF`, `Secondary_SDF`, `Assessor`, `Moderator`, `merSETA_Staff`.

### 1.2 Master Data Domain Entities & Constraints
- Expand `Organisation`:
  - SARS SDL Number (`L...`/`N...`), Legal Name, Trading Name, Company Reg No, Tax Pin.
  - Foreign key lookups: `CategoryCode`, `StatusCode`, `ProvinceCode`, `SectorCode`, `ChamberCode`, `SicCode`.
  - Banking details & Verification status.
  - Child collections: `Contacts`, `SdfLinkages`, `Sites`, `Visits`.
- Expand `Person`:
  - First, Middle, Last Names, RSA ID Number (auto-calculation of DOB and Gender), Passport Number.
  - Demographics lookups: `GenderCode`, `EquityCode`, `DisabilityCode`, `NationalityCode`, `HomeLanguageCode`.
  - Contact information and address details.
- Add `OrganisationContact`:
  - Polymorphic/Type-based linkage connecting `Person` to `Organisation` with roles (`Primary_SDF`, `Secondary_SDF`, `Financial_Contact`, `Director`).

### 1.3 Application & Business Rules
- `RsaIdValidator`: Validates 13-digit RSA IDs via Luhn algorithm, auto-extracts Date of Birth (handles 1900/2000 centuries), Gender, and Citizen Status.
- `PersonService` and `OrganisationService`: Handles CRUD, child collection updates, and double-write audit logging (`AuditLog`).

### 1.4 MudBlazor Stacked Master-Detail UI
- `/people`: Data table with search, filter, and drill-down to `/people/{id}`.
- `/people/{id}`: Master-Detail view with RSA ID live calculation, edit/view visual parity, and confirmation toasts.
- `/employers/{id}`: Upgraded Master-Detail view with Tabs for General Info, Contacts & SDF Linkages, Sites, and Visits (enforcing mandatory Contact Person selection).

---

## ⚡ Phase 2: Workflow Engine Blueprints (Universal Power-Up)

- **Architecture**: Dynamic State Matrix with database tables (`workflow_definition`, `workflow_step`, `workflow_transition`, `workflow_task`, `workflow_task_history`).
- **Ergonomics**: Easy runtime configuration; admins can adjust transitions and roles without rebuilding or redeploying code.
- **Maker-Checker**: Built-in guard preventing the task creator from approving their own submission.

---

## 🚦 Approval Checkpoint
Ready to execute Phase 1 implementation across Database, Backend, Frontend, and Security subagents.
