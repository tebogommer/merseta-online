# Implementation Plan: Configurable Fiscal Calendar, Quarters & Working Days Engine

**Task Slug:** `fiscal-calendar`  
**Target Solution:** .NET 10 / Blazor Interactive Server / EF Core / SQL Server Express  
**Archetype:** A1 List (`/admin/financial-years`) + A3/A4 Detail (`/admin/financial-years/{id}`)  
**Governance Standard:** merSETA Clean Architecture, Non-Repudiation Audit Double-Write, MudBlazor 16-Point UI Checklist

---

## 1. Executive Summary & Context

MerSETA and South African public entities operate under a statutory financial year that traditionally runs from **1 April to 31 March** (e.g., FY 2026/2027), divided into four statutory quarters:
- **Q1:** 1 April – 30 June
- **Q2:** 1 July – 30 September
- **Q3:** 1 October – 31 December
- **Q4:** 1 January – 31 March (calendar rollover)

### Core Requirements
1. **Configurable Financial Years & Quarters:** Ability to set custom start and end dates for any financial year and its four constituent quarters.
2. **Default Template in System Settings:** Pre-configured with standard South African statutory dates (1 Apr – 31 Mar, Q1–Q4), editable via System Settings so new financial years prepopulate automatically.
3. **Dynamic Month & Day Computation Engine:**
   - Slices each quarter into constituent calendar months (even across irregular or non-standard quarter boundaries).
   - Computes total **calendar days** per month and quarter (accurately handling leap years).
   - Computes total **statutory working days** (excluding Saturdays, Sundays, and gazetted South African public holidays with Sunday rollover observances per the Public Holidays Act 36 of 1994).
4. **Strict Contiguity Validation:** Enforces zero gaps and zero overlaps across quarters (e.g., Q2 start must equal Q1 end + 1 day; Q1 start = FY start; Q4 end = FY end).

---

## 2. Technical Architecture & Component Design

```
┌────────────────────────────────────────────────────────────────────────────────────────┐
│                                ARCHITECTURAL LAYERS                                    │
├────────────────────────────────────────────────────────────────────────────────────────┤
│  [UI Layer: Blazor Components]                                                         │
│   ├── /admin/financial-years         -> FinancialYearList.razor (Archetype A1 List)   │
│   ├── /admin/financial-years/{id}    -> FinancialYearDetail.razor (Archetype A3 Detail)│
│   └── /admin/financial-years/create -> FinancialYearDetail.razor (Create Mode)        │
│                                                                                        │
│  [Application Service Layer]                                                           │
│   ├── IFiscalCalendarService / FiscalCalendarService                                   │
│   │    ├── ComputeBreakdownAsync (Date math, calendar days, working days, holidays)    │
│   │    ├── ValidateContiguity (Strict zero-gap, zero-overlap validation)               │
│   │    ├── GetDefaultTemplateAsync / UpdateDefaultTemplateAsync                        │
│   │    └── CRUD with atomic double-write to audit_logs                                 │
│   └── SouthAfricanPublicHolidays (Computus Easter, fixed holidays, Sunday rollover)   │
│                                                                                        │
│  [Domain & Persistence Layer]                                                          │
│   ├── FinancialYear (Master) & FinancialQuarter (Child 1:4)                            │
│   ├── SystemConfig (Fiscal:Default* parameters)                                        │
│   ├── NsdmsDbContext (Temporal history.<Entity>History mapping)                        │
│   └── Phase13FiscalCalendarMigrator (Idempotent T-SQL DDL)                             │
└────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## 3. Detailed Task Breakdown

### Phase 1: Domain Entities & Database Migrations
- **Task 1.1: Domain Entities (`Nsdms.Domain/Entities/FinancialYear.cs`)**
  - Create `FinancialYear` inheriting `BaseEntity`:
    - `FinYearCode` (NVARCHAR(50), e.g., `"2026/2027"`)
    - `StartYear` (INT, e.g., `2026`), `EndYear` (INT, e.g., `2027`)
    - `StartDate` (DATETIME2), `EndDate` (DATETIME2)
    - `Description` (NVARCHAR(500))
    - `StatusCode` (NVARCHAR(50), e.g., `"Upcoming"`, `"Active"`, `"Closed"`)
    - `IsActive` (BIT), `IsClosed` (BIT)
    - `Quarters` (ICollection<FinancialQuarter>)
  - Create `FinancialQuarter` inheriting `BaseEntity`:
    - `FinancialYearId` (INT, FK)
    - `QuarterCode` (NVARCHAR(10), e.g., `"Q1"`, `"Q2"`, `"Q3"`, `"Q4"`)
    - `QuarterNumber` (INT, 1..4)
    - `StartDate` (DATETIME2), `EndDate` (DATETIME2)
    - `Description` (NVARCHAR(250))
    - `IsLocked` (BIT)
- **Task 1.2: DbContext Mapping (`Nsdms.Infrastructure/Data/NsdmsDbContext.cs`)**
  - Register `DbSet<FinancialYear>` and `DbSet<FinancialQuarter>`.
  - Configure Fluent API: singular table names (`FinancialYear`, `FinancialQuarter`), cascade delete, indexes on `FinYearCode`, `StartDate`, `EndDate`, `FinancialYearId`.
  - Enable system versioning via `modelBuilder.ApplyTemporalTables()`.
- **Task 1.3: SQL DDL & Migrator (`Phase13FiscalCalendarMigrator.cs` & `V2026_13_Add_Fiscal_Calendar_Tables.sql`)**
  - Idempotent script creating tables, foreign keys, temporal history tables, and default indexes.
  - Register migrator execution in `Program.cs`.

---

### Phase 2: Application Service & Computation Engine
- **Task 2.1: Data Transfer Objects (`Nsdms.Application/Common/Models/FiscalCalendarDtos.cs`)**
  - `FiscalMonthBreakdownDto`: `MonthNumber`, `MonthName`, `CalendarYear`, `StartDate`, `EndDate`, `CalendarDaysCount`, `WorkingDaysCount`, `PublicHolidaysCount`, `List<string> ObservedHolidays`.
  - `FiscalQuarterBreakdownDto`: `QuarterCode`, `QuarterNumber`, `StartDate`, `EndDate`, `TotalCalendarDays`, `TotalWorkingDays`, `List<FiscalMonthBreakdownDto> Months`.
  - `FiscalYearBreakdownDto`: `FinYearCode`, `StartDate`, `EndDate`, `TotalCalendarDays`, `TotalWorkingDays`, `List<FiscalQuarterBreakdownDto> Quarters`.
  - `FiscalTemplateConfigDto`: Default year start month/day, end month/day, and Q1–Q4 default boundaries.
- **Task 2.2: Service Contract & Implementation (`IFiscalCalendarService` & `FiscalCalendarService`)**
  - `ComputeBreakdownAsync(DateTime start, DateTime end, List<QuarterDateRangeDto> quarters)`:
    - Iterates through each quarter's start and end dates.
    - Slices dates by month boundaries (`new DateTime(year, month, 1)` to `DaysInMonth`).
    - Uses `SouthAfricanPublicHolidays` to count statutory holidays and working days.
  - `ValidateContiguity(...)`:
    - Strict check: Quarter dates must not overlap, must not leave gaps, and must match financial year boundaries.
  - `GetDefaultTemplateAsync()` & `UpdateDefaultTemplateAsync(...)`:
    - Interacts with `ISystemConfigurationService` for keys `Fiscal:DefaultStartMonthDay`, `Fiscal:DefaultQ1Start`, etc.
  - CRUD operations with atomic double-write into `audit_logs` (capturing before/after states).
- **Task 2.3: Configuration Seeding (`SystemConfigurationService.cs`)**
  - Seed default keys:
    - `Fiscal:DefaultStartMonthDay = "04-01"`
    - `Fiscal:DefaultEndMonthDay = "03-31"`
    - `Fiscal:DefaultQ1Start = "04-01"`, `Fiscal:DefaultQ1End = "06-30"`
    - `Fiscal:DefaultQ2Start = "07-01"`, `Fiscal:DefaultQ2End = "09-30"`
    - `Fiscal:DefaultQ3Start = "10-01"`, `Fiscal:DefaultQ3End = "12-31"`
    - `Fiscal:DefaultQ4Start = "01-01"`, `Fiscal:DefaultQ4End = "03-31"`

---

### Phase 3: Blazor User Interface (Master-Detail Pattern)
- **Task 3.1: Financial Years List View (`FinancialYearList.razor`)**
  - Route: `/admin/financial-years`
  - Archetype: A1 List with `<DataGridShell>`
  - Persistent columns: Financial Year Code (hyperlink to `/admin/financial-years/{id}`), Start Date, End Date, Total Days, Working Days, Quarters chips, Status Badge.
  - Action zone: "Create Financial Year", "Filter by Status", "Default Template Settings".
- **Task 3.2: Financial Year Detail & Editor (`FinancialYearDetail.razor`)**
  - Routes: `/admin/financial-years/{id}`, `/admin/financial-years/{id}/edit`, `/admin/financial-years/create`
  - Archetypes: A3 Detail & A4 Form
  - Sticky top bar with Back button, Cancel, Save, and Delete (with `IDialogService.ShowAsync<ConfirmDialog>()`).
  - View mode uses `<ReadOnlyField>` components; Edit mode enables date pickers and inputs.
  - **Tabs**:
    - **Tab 1: Calendar & Quarters Setup:**
      - Start & End Date pickers for Financial Year.
      - 4 Quarter Cards (Q1, Q2, Q3, Q4) with custom start/end pickers.
      - "Reset to Default Statutory Template" helper button.
      - Real-time contiguity validation warning banner.
    - **Tab 2: Computed Month & Day Breakdown Matrix:**
      - Live preview table showing Quarter $\to$ Month Name $\to$ Calendar Days $\to$ Statutory Working Days $\to$ Observed Public Holidays.
    - **Tab 3: Audited Change Log:**
      - Historical audit timeline retrieved from `audit_logs`.
- **Task 3.3: System Settings Integration (`SystemSettings.razor`)**
  - Add "Statutory Fiscal Template" settings card in `/admin/settings` allowing administrators to update the baseline dates globally.
- **Task 3.4: Navigation Menu Integration (`NavigationMenuService.cs`)**
  - Register `"Financial Years & Quarters"` under the `"System administration"` statutory pillar.

---

### Phase 4: Automated Testing & Verification
- **Task 4.1: Unit & Domain Tests (`FiscalCalendarCalculationTests.cs`)**
  - Leap year accuracy: Test that Feb 2028 computes 29 days and Feb 2026 computes 28 days.
  - Public holiday deduction: Verify that South African holidays (Workers' Day, Freedom Day, Good Friday, Christmas/Day of Goodwill Sunday rollover) deduct exactly 1 working day when falling on weekdays.
  - Strict contiguity validation: Test rejection of 1-day gaps or overlapping quarters.
  - Custom shift test: Verify a non-standard 6-month or 15-month transitional period correctly calculates months and working days.
- **Task 4.2: End-to-End Playwright Audit (`test_fiscal_calendar_playwright.py`)**
  - Verify page loading, table sorting, navigation from list to detail, switching between View and Edit modes, and creating a new financial year.

---

## 4. Acceptance Criteria & Guardrails

- [ ] Records open in **View** mode by default (`/admin/financial-years/{id}`); editable inputs only appear in Edit mode.
- [ ] No raw database access in Razor components (all mutations pass through `IFiscalCalendarService`).
- [ ] Strict contiguity validator prevents saving any financial year where quarter dates have gaps or overlaps.
- [ ] Month breakdowns accurately reflect the days in each month and working days minus gazetted South African holidays.
- [ ] All mutations execute atomic double-writes into `audit_logs`.
- [ ] UI strictly complies with the 16-point UI checklist (Sentence case, no hex codes, breadcrumbs, sticky top bar).

---

## 5. Next Steps
Once approved:
1. Run database migration script and migrator.
2. Implement service contracts and holiday-aware computation logic.
3. Build the Master-Detail Razor components and wire navigation.
4. Execute automated unit and Playwright integration tests.
