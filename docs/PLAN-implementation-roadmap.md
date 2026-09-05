# Phased Implementation Roadmap: Database Normalization & Convention Alignment

## Executive Summary
This roadmap establishes a phased, zero-downtime engineering strategy to normalize the 16 wide tables (> 20 columns) in the \NSDMS-NET\ database, resolve the \CompanyLearner\ convention breach, segregate sensitive POPIA/banking data, and optimize system-versioned temporal table performance.

---

## 🎯 Strategic Objectives
1. **Convention Alignment:** Migrate \CompanyLearner\ and its satellite tables to canonical DDD naming (\LearnerEnrolment\, \LearnerTransfer\, \LearnerLostTime\, \LearnerTermination\) while preserving 100% backward compatibility for DHET SETMIS statutory exports via database views.
2. **Monolith Decomposition:** Vertically partition the top 3 monolithic entities (\Person\ [49 cols], \CompanyLearner\ [44 cols], \Organisation\ [41 cols]) into coherent 1:1 and 1:N sub-tables.
3. **Data Security & POPIA Isolation:** Vertically segregate Washington Group functional disability scores and raw banking data to enforce granular role-based authorization.
4. **Performance & Storage Optimization:** Eliminate full-row write amplification in temporal tables (\history.*\) and enforce projection DTOs across all Blazor interactive master-detail grids.

---

## 🗺️ 4-Phase Implementation Schedule

### Phase 1: Zero-Breaking Optimization & Query Projection Baseline
> **Goal:** Immediate UI/performance gains without touching physical database table structures.

#### Tasks:
1. **Enforce Projection DTOs (UI Standard §11.2):**
   - Audit all queries in \Nsdms.Application/Services/\ that fetch \Person\, \CompanyLearner\, or \Organisation\.
   - Replace entity fetches with lightweight \*GridDto\ projections (\.Select(p => new PersonGridDto { ... })\) ensuring no grid requests more than 7 visible columns.
2. **Consolidate \Organisation\ Banking References:**
   - Deprecate reads/writes to the 5 denormalized embedded bank fields (\BankName\, \BankAccountNumber\, \BankBranchCode\, \BankAccountType\, \BankingDetailsVerified\).
   - Route all banking modifications exclusively through \BankingDetails\ foreign keys.
3. **Automated Baseline Regression:**
   - Execute all Playwright test suites (\	est_all_pages_playwright.py\, \	est_workflow_playwright.py\) to record baseline test parity.

---

### Phase 2: Domain Aliasing & DDD Semantic Realignment
> **Goal:** Adopt canonical \Organisation\ and \LearnerEnrolment\ naming in C# without breaking existing database tables.

#### Tasks:
1. **Entity-Level Mapping in EF Core:**
   - Create \LearnerEnrolment.cs\ in \Nsdms.Domain/Entities/\.
   - In \NsdmsDbContext.cs\, map \modelBuilder.Entity<LearnerEnrolment>().ToTable("CompanyLearner")\.
   - Maintain backward-compatible type aliases for legacy service interfaces.
2. **Application Service Modernization:**
   - Refactor \LearnerLifecycleService.cs\ to operate on \LearnerEnrolmentTransfer\, \LearnerEnrolmentLostTime\, and \LearnerEnrolmentTermination\.
   - Update \INsdmsDbContext\ to expose \DbSet<LearnerEnrolment> LearnerEnrolments\.
3. **Double-Write Audit Verification:**
   - Ensure all mutation events log to \udit_logs\ using the canonical entity identifier \LearnerEnrolment\.

---

### Phase 3: Vertical Partitioning of Core Monoliths [COMPLETED]
> **Goal:** Decompose 40+ column tables into normalized, secure 1:1 sub-tables with idempotent T-SQL migrations.

#### Tasks:
1. **`Person` Vertical Partitioning:**
   - Partitioned into 1:1 satellite entities:
     - `PersonContact` (1:1): `Email`, `PhoneNumber`, `CellNumber`, `FaxNumber`, `PhysicalAddress`, `PostalAddress`, `ProvinceCode`, `StatssaAreaCode`.
     - `PersonDisabilityRating` (1:1, POPIA secured): 6 Washington Group ratings (`SeeingRatingId`, `HearingRatingId`, `WalkingRatingId`, `RememberingRatingId`, `CommunicatingRatingId`, `SelfCareRatingId`), `DisabilitySupportNotes`, assessment records.
     - `PersonDemographics` (1:1): `EquityCode`, `HomeLanguageCode`, `NationalityCode`, `CitizenStatusCode`, `PopiActStatusId`, `PopiActConsentDate`, school metadata.
2. **Zero-Breaking SQL Views & Compatibility Layer:**
   - Created `vw_PersonComplete` (denormalized 360-degree composite view).
   - Created `vw_PersonSetmis` (projection tailored for DHET SETMIS File 400 Person Demographics).
3. **Migration & Idempotent Backfill:**
   - `V2026_19_Vertical_Partitioning.sql` deployed and executed against `localhost\SQLEXPRESS` (`NSDMS-NET`).
   - `Phase25VerticalPartitioningMigrator.cs` registered in `Program.cs`.
   - Master enterprise DDL `V2026_08_Complete_Nsdms_Enterprise_DDL.sql` synchronized.
   - Comprehensive test suite in `PersonServiceTests.cs` (21/21 passing, 531/531 solution-wide).

---

### Phase 4: Physical Renaming & SETMIS Compatibility Layer [COMPLETED]
> **Goal:** Complete physical database alignment and optimize temporal tables while keeping external DHET file exports 100% compliant.

#### Tasks:
1. **SETMIS Compatibility Views:**
   - Created statutory database view `dbo.vw_SetmisCompanyLearner` projecting canonical `dbo.LearnerEnrolment` columns to statutory SETMIS contracts (`CompanyId`, `LearnerContractNumber`, `LearnershipId`, etc.).
2. **Physical Table Cutover (`sp_rename`):**
   - Executed physical cutover `EXEC sp_rename 'dbo.CompanyLearner', 'LearnerEnrolment'`.
   - Created backward-compatible view `dbo.CompanyLearner AS SELECT * FROM dbo.LearnerEnrolment;` ensuring 100% backward compatibility for all existing queries, foreign keys, and bulk staging pipelines.
   - Preserved `CompanyLearner` entity mapping in EF Core referencing the view/underlying table with 0 breaking changes.
3. **Temporal Table Tuning:**
   - Configured and activated SQL Server System-Versioned Temporal Tables on satellite sub-tables (`PersonContact`, `PersonDemographics`, `PersonDisabilityRating`) into the `[history]` schema (`history.PersonContactHistory`, `history.PersonDemographicsHistory`, `history.PersonDisabilityRatingHistory`).
   - Eliminates full-row history bloat in `history.PersonHistory` for frequent contact and demographics revisions.
4. **Migration & Enterprise Artifacts:**
   - `V2026_20_Physical_Renaming_Setmis_Layer.sql` created and executed against `localhost\SQLEXPRESS` (`NSDMS-NET`).
   - `Phase27PhysicalRenamingMigrator.cs` implemented and added to infrastructure migrators.
   - Master enterprise DDL `V2026_08_Complete_Nsdms_Enterprise_DDL.sql` synchronized.
   - Full test suite verified: 544/544 tests passing.


---

## 👥 Multi-Agent Assignment Matrix

| Agent | Responsibilities | Assigned Phases |
|---|---|:---:|
| \project-planner\ | Plan management, milestone tracking, rollback protocols | All Phases |
| \database-architect\ | T-SQL schema migrations, DDL scripts, index creation, temporal table tuning | Phase 1, 3, 4 |
| \ackend-specialist\ | EF Core Fluent API, DTO projections, service refactoring, Clean Architecture | Phase 1, 2, 3 |
| \security-auditor\ | POPIA access validation on \PersonDisabilityRating\, banking isolation audit | Phase 1, 3 |
| \	est-engineer\ | Unit tests in \Nsdms.Tests\, Playwright regression tests (\	est_all_pages_playwright.py\) | All Phases |

---

## 🛡️ Risk & Mitigation Matrix

| Identified Risk | Severity | Mitigation Strategy |
|---|:---:|---|
| **SETMIS File Export Breakage** | **High** | Introduce \w_SetmisCompanyLearner\ view prior to table rename; verify flat-file generation output matches DHET specs byte-for-byte. |
| **Data Drift During Backfill** | **High** | Wrap all data migration statements in single transaction blocks with checksum validation before and after partitioning. |
| **Windows EF Core File Lock** | **Medium** | Ensure database updates are applied via idempotent SQL scripts or standard migrators without requiring dev server restarts during normal runs. |
| **Temporal History Desync** | **Low** | Disable system-versioning temporarily during table restructuring (\SET (SYSTEM_VERSIONING = OFF)\), apply DDL changes, backfill history tables, and re-enable with consistent period definitions. |

---

## ✅ Quality & Verification Gate Checklist

- [ ] **Baseline:** Full Playwright test suite passes prior to initiating Phase 1.
- [ ] **UI Standard:** All master grids display <= 8 columns and use projection DTOs.
- [ ] **POPIA Compliance:** Access to \PersonDisabilityRating\ is protected by explicit CASL / policy checks.
- [ ] **Nomenclature:** Zero user-facing occurrences of \Company\ or \CompanyLearner\ in new UI components and service methods.
- [ ] **SETMIS Interop:** \w_SetmisCompanyLearner\ passes all statutory export format validations.
- [ ] **History Tables:** System-versioned history queries (\.TemporalAll()\) return correct historical timelines.
