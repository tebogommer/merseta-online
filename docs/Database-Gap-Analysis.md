# Revised Database Integration & Gap Analysis Report

**Date:** April 9, 2026
**Target Schema:** `SETMIS LLP SCHEMA 08 APR 2026 16_01.sql` (100 Entities)
**Comparison Target:** `docs/specifications` (MerSETA NSDMS Modernization Directives)

---

## 🛑 Executive Summary
An orchestration sweep was performed against the provided `.sql` database schema. Following clarification regarding the **SETMIS Naming Convention**, the analysis has been vastly adjusted. The schema currently utilizes DHET/SETMIS export file nomenclature natively as the core relational domain model:

*   **`FILE 100`** = Training Providers (`Spec-05`)
*   **`FILE 200`** = Employers / Company (`Spec-02`)
*   **`FILE 400`** = Persons / Demographics (`Spec-01`)
*   **`FILE 401`** = Assessors / Moderators (`Spec-04`)
*   **`FILE 500+`** = Company Learners / Learner Registrations (`Spec-08`)

While this validates significant portions of the domain logic (specifically Learner and ETQA), crucial infrastructure, workflow, and modernization components remain **missing or severely misaligned**.

---

## 🏗️ 1. Identity & Security Gaps (`database-architect`)
*References: Spec-01 (Auth), User Rules*

*   ✅ **Identity Decoupling Achieved:** `FILE 400` correctly serves as the `Person` equivalent, successfully isolating Demographics from Authentication.
*   ❌ **Legacy ASP.NET Membership vs. .NET 8 Core Identity:** The schema still utilizes `aspnet_Users`, `aspnet_Roles`, and `aspnet_Membership`. This strictly correlates to the deprecated 15-year-old ASP.NET framework. 
    *   **Action Required:** Replace with modern EF Core `.NET 8 Identity` schema (`AspNetUsers`, `AspNetRoles`).
*   ❌ **Missing Roles/Permissions Engine:** No `CASL` permission mapping tables or internal staff policy matrices exist natively.
*   ❌ **Missing Audit Logs:** Global user rule requires `audit_logs` capturing `recordId, actionName, actor` for all dual-writes. Table is completely missing.

## ⚙️ 2. Core Modules & Domain Gaps (`frontend-specialist`)
*References: Spec-02 (Company), Spec-06 (WSP), Spec-11 (Workplace)*

*   ✅ **Base ETQA Exists:** `FILE 100` (Providers) and `FILE 401` (Assessors) exist.
*   ❌ **Missing ETQA Agenda Matrix:** `ReviewCommitteeAgenda` (Mandatory for ETQA status jumps across Spec-04 and Spec-05) is missing.
*   ❌ **Missing Linking Entities (Company/SDF):** `FILE 200` acts as the Employer, but `SdfCompany` (to link SDFs to Employers with unique constraints) is missing.
*   ❌ **Missing WSP Subsystem:** `Spec-06` governs the Workplace Skills Plan pipeline. There is no `Wsp` table or `WspSignoff` entity to satisfy the statutory time-gate rules.
*   ❌ **Missing Workplace Approval System:** `Spec-08` Learner Registrations depend on `Spec-11` checks via a `WorkplaceApproval` table containing Trade/Mentor capacity calculations. This is completely absent.

## 🚀 3. Workflow & Infrastructure Gaps (`backend-specialist`)
*References: Spec-10 (Workflow), Spec-15 (Documents)*

*   ❌ **Missing Task Engine:** Null references to `Tasks`, `TaskUsers`, or `StateTransitions`. The system currently has zero capability to route XState workflow commands dynamically as dictated by `Spec-10`.
*   ❌ **Document Management Omission:** `Spec-15` explicitly requires a `Doc` table storing an S3/Blob URI referencing the entity. There are no document tracking tables in the schema.
*   ❌ **Missing asynchronous Queues:** `Spec-16` (National Data Imports) requires background workers (like Hangfire). No job tables exist.

## 💰 4. Financials & Integrations Gaps (`backend-specialist`)
*References: Spec-03 (DG), Spec-07 (MG), Spec-09 (SARS)*

*   ❌ **Missing SARS Ingestion:** The "No Levy, No Grant" rule requires `SarsLevyDetails`. No SARS integration staging or tracking tables exist in the schema.
*   ❌ **Missing Great Plains ERP Synchronization:** No outbox pattern or `SuspendedGrantsQueue` tables (required by `Spec-07`) to manage ERP latency.

---

### 📝 Next Steps for Implementation
1.  **EF Core Migration Swap:** Discard the legacy `aspnet_*` authentication schema in favor of scaffolding a fresh `dotnet ef migrations add InitialMigration` leveraging `Microsoft.AspNetCore.Identity.EntityFrameworkCore`.
2.  **Domain Driven Scaffolding:** Implement the Next.js / .NET 8 structural classes that bridge the SETMIS data with the workflow application (e.g., `Wsp`, `WorkplaceApproval`, `ReviewCommitteeAgenda`, `Tasks`, `Doc`) and push the schema using Entity Framework Code-First.
3.  **Audit Enforcement:** Inject a `SaveChangesInterceptor` into the DbContext that automatically writes to a newly created `audit_logs` table.
4.  **Hangfire Integration:** Install the Hangfire NuGet package and run its installation scripts to automatically overlay the background queuing tables on the SQL database.
