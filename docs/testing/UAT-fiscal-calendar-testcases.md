# User Acceptance Testing (UAT) Test Specification: Financial Years & Quarters Management

**Module:** System Administration & Governance — Fiscal Calendar (Option A)  
**System:** merSETA National Skills Development Management System (NSDMS)  
**Target Audience:** Human QA Testers, Business Analysts, and System Administrators  
**Architecture:** .NET 10 Blazor Server + MudBlazor Master-Detail (Clean Architecture)  
**Version:** 2.0 (Human-Centric UI Navigation)  
**Date:** September 2026  
**Status:** Approved for Manual & User Acceptance Testing  

---

## 1. Document Control & Scope

### 1.1 Purpose
This document provides step-by-step User Acceptance Testing (UAT) instructions written specifically for **human testers**. All steps use clear user interface navigation paths (sidebar menus, pillar accordions, action buttons, table rows, tabs, and modal dialogs) with **zero technical URLs**.

### 1.2 User Personas & Test Credentials
| Persona Role | User Account | Authorized Functions |
| :--- | :--- | :--- |
| **SuperAdmin** | `sysadmin@merseta.org.za` | Full CRUD, quarter locking/closing, template configuration, audit inspection |
| **Admin** | `admin@merseta.org.za` | Create, Edit, View financial years & quarters, inspect change logs |
| **Finance Officer** | `finance@merseta.org.za` | View-only access to financial scheme years, working day projections, and quarters |
| **External SDF** | `sdf@toyota.co.za` | Strictly unauthorized (must be intercepted and blocked) |

---

## 2. Test Scenarios Summary Matrix

| Scenario ID | Test Scenario Description | Test Case Count | Priority |
| :--- | :--- | :--- | :--- |
| **SC-01** | Financial Years Hub & Master List Discovery | 4 | High |
| **SC-02** | View-by-Default Drill-Down & Dynamic Computations | 5 | Critical |
| **SC-03** | Financial Year Creation & Contiguity Validation | 6 | Critical |
| **SC-04** | Stacked Editing, Date Adjustments & Atomic Saves | 5 | High |
| **SC-05** | Destructive Action Affirmation Dialogs | 2 | Medium |
| **SC-06** | System Settings Global Statutory Template Configuration | 2 | Medium |
| **SC-07** | Security, Authorization & Zero-Trust Verification | 2 | Critical |

---

## 3. Detailed User Test Cases for Human Testers

### Scenario 1: Financial Years Hub & Master List Discovery

#### **TC-UAT-FY-001: Navigation to Financial Years Hub**
- **Description:** Verify that an administrator can locate and open the Financial Years & Quarters registry from the main navigation menu.
- **Preconditions:** User is signed in with an administrative account.
- **Steps:**
  1. Open the application and sign in.
  2. Locate the left-hand navigation sidebar.
  3. Scroll down and expand the accordion titled **"System administration"**.
  4. Click on the menu item **"Financial years & quarters"** (marked with a calendar icon).
- **Expected Result:**
  - The main page updates to display the header **"Financial Years & Quarters"** with subtitle *"Manage statutory financial scheme years, customize quarter boundaries, and view computed month and working day projections."*
  - Breadcrumbs at top display: `Home > Administration > Financial Years & Quarters`.
  - An action bar on the top right displays two buttons: **"System Settings"** (outline) and **"Add Financial Year"** (primary solid).
  - The master data table loads displaying existing financial scheme years.
- **Pass/Fail Criteria:** Page loads cleanly without errors; table and action buttons visible.

---

#### **TC-UAT-FY-002: Real-Time Search Filtering**
- **Description:** Verify search filtering across financial year codes and descriptive text.
- **Preconditions:** On the **Financial Years & Quarters** list page.
- **Steps:**
  1. In the search box placeholder reading *"Search by Financial Year Code, description..."*, type `"2026/2027"`.
  2. Review the displayed table rows.
  3. Clear the search box, then type descriptive text: `"leap year"`.
  4. Click the small **Clear (`x`)** icon inside the search box.
- **Expected Result:**
  - Typing `"2026/2027"` filters the grid to only display the FY 2026/2027 scheme year.
  - Typing `"leap year"` filters the grid to display the FY 2027/2028 record.
  - Clicking Clear immediately restores the complete list of financial years.
- **Pass/Fail Criteria:** Grid filters dynamically as you type without page reloads.

---

#### **TC-UAT-FY-003: Lifecycle Status Dropdown Filtering**
- **Description:** Verify filtering by operational status using the dropdown selector.
- **Preconditions:** On the **Financial Years & Quarters** list page.
- **Steps:**
  1. Click the **"Status Filter"** dropdown next to the search bar.
  2. Select **"Active"** from the options.
  3. Open the dropdown again and select **"Upcoming"**.
  4. Open the dropdown again and select **"Audited"**.
  5. Select **"All Statuses"** to reset.
- **Expected Result:**
  - Selecting **"Active"** shows only currently operational scheme years with a green `Active` chip.
  - Selecting **"Upcoming"** shows future scheme years with a blue `Upcoming` chip.
  - Selecting **"Audited"** shows concluded prior years (e.g. FY 2025/2026).
  - Selecting **"All Statuses"** restores all records.
- **Pass/Fail Criteria:** Table updates to match the selected status filter.

---

#### **TC-UAT-FY-004: Data Table Baseline & Quarter Badges**
- **Description:** Verify table columns, quarter chips, tooltips, and pagination.
- **Preconditions:** On the **Financial Years & Quarters** list page.
- **Steps:**
  1. Confirm the following table headers are visible:
     - `Financial Year Code`
     - `Effective Period`
     - `Calendar Days`
     - `Quarters (Q1 – Q4)`
     - `Status`
     - `Actions`
  2. Move the mouse cursor over the **Q1** chip on the `2026/2027` row and pause.
  3. At the bottom of the table, click the page size dropdown and select `50`.
- **Expected Result:**
  - The Financial Year Code column shows a blue clickable monospace text link.
  - Hovering over **Q1** displays a tooltip: *"Q1: 01 Apr 2026 to 30 Jun 2026 (91 days)"*.
  - Total calendar days displays formatted as monospace text (e.g., `365 days`).
  - Page size changes smoothly without resetting filter criteria.
- **Pass/Fail Criteria:** Tooltip displays date breakdown; table pagination is responsive.

---

### Scenario 2: View-by-Default Drill-Down & Dynamic Computations

#### **TC-UAT-FY-005: Strict View-by-Default Drill-Down**
- **Description:** Verify clicking a record opens in read-only View mode with no editable fields.
- **Preconditions:** On the **Financial Years & Quarters** list page.
- **Steps:**
  1. Click the blue link **"2026/2027"** or click the **Eye (View)** icon button in the Actions column.
- **Expected Result:**
  - The screen navigates into the detail view for `2026/2027`.
  - A sticky top bar appears with:
    - **"Back to list"** button with a left-arrow icon.
    - Calendar icon badge.
    - Large title **"2026/2027"** with a green **"Active"** chip.
    - Period summary: *"Period: 01 Apr 2026 to 31 Mar 2027 | Total days: 365"*.
    - Top right action buttons: **"Delete"** (outline red) and **"Edit financial year"** (solid primary).
  - All displayed fields are strictly read-only text. No editable textboxes, dropdowns, or date pickers appear.
- **Pass/Fail Criteria:** Strict View-by-default rule verified; editing controls are absent.

---

#### **TC-UAT-FY-006: Tab 1 — Master Setup & Quarter Card Display**
- **Description:** Verify the master financial year attributes and child quarter summary cards.
- **Preconditions:** In View mode for financial year `2026/2027`.
- **Steps:**
  1. Click on the first tab titled **"Calendar & Quarters Setup"**.
  2. Review the top Master Record section: Code, Start Year, End Year, Period, Status, Active toggle, Closed toggle.
  3. Scroll down to review the four Quarter Cards: **Quarter 1**, **Quarter 2**, **Quarter 3**, **Quarter 4**.
- **Expected Result:**
  - All master fields present their values clearly in read-only form.
  - Each quarter card clearly lists its Quarter Code, description, start date, end date, total calendar days, and status chips (e.g. `Open`, `Unlocked`).
- **Pass/Fail Criteria:** All 4 quarters render in structured cards underneath the master record.

---

#### **TC-UAT-FY-007: Tab 2 — Dynamic Month Slicing & Leap Year Verification**
- **Description:** Verify system computation of constituent months, calendar days, and leap year February.
- **Preconditions:** In View mode for the leap year `2027/2028`.
- **Steps:**
  1. From the top bar, click **"Back to list"**.
  2. In the list, click on **"2027/2028"**.
  3. In the detail view, click on **"Tab 2: Computed Month & Day Breakdown"**.
  4. Inspect the monthly table rows from April 2027 through March 2028.
  5. Locate the row for **February 2028**.
- **Expected Result:**
  - Exactly 12 monthly rows are displayed sequentially.
  - The row for **February 2028** displays exactly **29 calendar days**.
  - Total calendar days in the summary card at the top displays **366 days**.
- **Pass/Fail Criteria:** February calculates 29 days in a leap year (vs 28 in non-leap years).

---

#### **TC-UAT-FY-008: Tab 2 — Statutory South African Working Days & Public Holidays**
- **Description:** Verify statutory working days calculation excluding weekends and gazetted public holidays via Computus.
- **Preconditions:** In View mode for `2026/2027`, open **"Tab 2: Computed Month & Day Breakdown"**.
- **Steps:**
  1. Review the four top metric summary cards:
     - **Total Calendar Days**
     - **Total Statutory Working Days**
     - **Weekend Days**
     - **Gazetted Public Holidays**
  2. Inspect the monthly breakdown table columns: `Month`, `Quarter`, `Calendar Days`, `Working Days`, `Weekend Days`, `Public Holidays`.
  3. Check the row for **April 2026**.
- **Expected Result:**
  - Total statutory working days = **249 days**.
  - Weekend days = **104 days**.
  - Gazetted public holidays = **12 days** ($249 + 104 + 12 = 365\text{ days}$).
  - April 2026 correctly deducts public holidays including Easter weekend (Good Friday, Family Day) calculated via the South African statutory Computus engine.
- **Pass/Fail Criteria:** Values match statutory calendar figures with exact mathematical reconciliation.

---

#### **TC-UAT-FY-009: Tab 3 — Chronological Audited Change Log**
- **Description:** Verify that system mutations are recorded in the non-repudiation audit trail.
- **Preconditions:** In View mode for any financial year.
- **Steps:**
  1. Click on the third tab titled **"Audited Change Log"**.
  2. Inspect the timeline entries.
- **Expected Result:**
  - A chronological timeline shows audit events (such as `CreateFinancialYear` or `UpdateFinancialYear`).
  - Each entry lists the timestamp, actor username/role, and action taken.
  - Expanding an entry reveals a clean summary of what changed (zero technical database jargon).
- **Pass/Fail Criteria:** Audit entries are present and readable by non-technical officers.

---

### Scenario 3: Financial Year Creation & Contiguity Validation

#### **TC-UAT-FY-010: Form Initiation & "Apply Statutory Defaults"**
- **Description:** Verify opening the creation wizard and pre-filling standard South African statutory dates.
- **Preconditions:** On the **Financial Years & Quarters** list page.
- **Steps:**
  1. In the top action bar, click the button **"Add Financial Year"**.
  2. Verify the page transitions to the creation screen titled **"Add new financial year"**.
  3. In the top action bar, click the button **"Apply statutory defaults"** (marked with a reset/arrow icon).
- **Expected Result:**
  - The form pre-populates:
    - Financial Year Code: next logical scheme year (e.g. `2028/2029`).
    - Start Year: `2028`, End Year: `2029`.
    - Period: `01 Apr 2028` to `31 Mar 2029`.
    - Q1: `01 Apr 2028` to `30 Jun 2028`.
    - Q2: `01 Jul 2028` to `30 Sep 2028`.
    - Q3: `01 Oct 2028` to `31 Dec 2028`.
    - Q4: `01 Jan 2029` to `31 Mar 2029`.
  - A green confirmation toast appears: *"Statutory defaults applied for FY 2028/2029"*.
  - The primary button **"Save financial year"** becomes active/enabled.
- **Pass/Fail Criteria:** Pre-populates clean statutory dates with one click.

---

#### **TC-UAT-FY-011: Strict Contiguity Validation — Inter-Quarter Gap Detection**
- **Description:** Verify that leaving a gap between sequential quarters blocks saving.
- **Preconditions:** On the **"Add new financial year"** page with defaults applied.
- **Steps:**
  1. Scroll down to the **Quarter 1** card.
  2. Click the **End Date** date picker and change the date from `30 Jun 2028` to `25 Jun 2028` (creating a 5-day gap before Q2 starts on `01 Jul 2028`).
  3. Observe the top of the form and the **"Save financial year"** button.
- **Expected Result:**
  - A prominent red warning alert immediately appears at the top: *"Quarter Date Contiguity Issues Detected:"*
  - An error bullet explains: *"Gap detected between Q1 and Q2: Q1 ends on 25 Jun 2028 but Q2 starts on 01 Jul 2028. Quarters must be strictly contiguous."*
  - The button **"Save financial year"** is immediately **disabled** to prevent saving invalid dates.
- **Pass/Fail Criteria:** Error alert displayed with exact dates; Save button is disabled.

---

#### **TC-UAT-FY-012: Strict Contiguity Validation — Overlap Detection**
- **Description:** Verify that overlapping quarter dates are detected and blocked.
- **Preconditions:** On the **"Add new financial year"** page.
- **Steps:**
  1. Set Q1 End Date to `30 Jun 2028`.
  2. In the **Quarter 2** card, set the **Start Date** to `28 Jun 2028` (two days before Q1 ends).
  3. Observe the validation banner.
- **Expected Result:**
  - The red alert banner indicates: *"Overlap detected: Q2 starts on 28 Jun 2028 before or on the same day Q1 ends (30 Jun 2028)."*
  - The **Save financial year** button remains disabled.
- **Pass/Fail Criteria:** Overlap prevented; specific error message displayed.

---

#### **TC-UAT-FY-013: Boundary Synchronization Invariant**
- **Description:** Verify that Q1 Start Date must match the Financial Year Start Date, and Q4 End Date must match the Financial Year End Date.
- **Preconditions:** On the **"Add new financial year"** page.
- **Steps:**
  1. Set the Financial Year Start Date to `01 Apr 2028`.
  2. In Quarter 1, set the Start Date to `05 Apr 2028`.
- **Expected Result:**
  - Error banner displays: *"Quarter 1 start date (05 Apr 2028) does not match Financial Year start date (01 Apr 2028)."*
  - Save button is disabled.
- **Pass/Fail Criteria:** Boundary mismatch detected.

---

#### **TC-UAT-FY-014: Duplicate Financial Year Code Prevention**
- **Description:** Verify that attempting to register an already existing code is rejected.
- **Preconditions:** `2026/2027` already exists in the system.
- **Steps:**
  1. On the **"Add new financial year"** page, type `"2026/2027"` into the Financial Year Code field.
  2. Attempt to save the record.
- **Expected Result:**
  - A notification toast reports: *"Financial year code '2026/2027' already exists."*
  - The record is rejected without creating duplicates.
- **Pass/Fail Criteria:** Duplicate code blocked.

---

#### **TC-UAT-FY-015: Successful Creation with Toast & Automatic Navigation**
- **Description:** Verify saving creates the master year and all 4 quarters atomically.
- **Preconditions:** Enter valid contiguous dates for FY `2030/2031`.
- **Steps:**
  1. Enter:
     - Financial Year Code: `2030/2031`
     - Start Year: `2030`, End Year: `2031`
     - Period: `01 Apr 2030` to `31 Mar 2031`
     - Q1: `01 Apr 2030` to `30 Jun 2030`
     - Q2: `01 Jul 2030` to `30 Sep 2030`
     - Q3: `01 Oct 2030` to `31 Dec 2030`
     - Q4: `01 Jan 2031` to `31 Mar 2031`
  2. Click **"Save financial year"**.
- **Expected Result:**
  - A green toast confirms: *"Financial Year '2030/2031' created successfully with 4 quarters."*
  - The view automatically transitions into read-only View mode for `2030/2031`.
  - **Tab 2** immediately calculates the working days and monthly breakdown.
  - **Tab 3** displays a new `CreateFinancialYear` audit event.
- **Pass/Fail Criteria:** Record persisted and immediately viewable.

---

### Scenario 4: Stacked Editing, Date Adjustments & Atomic Saves

#### **TC-UAT-FY-016: Stacked Navigation to Edit Mode**
- **Description:** Verify transitioning from View mode into Edit mode.
- **Preconditions:** In View mode for any financial year.
- **Steps:**
  1. In the top action bar, click **"Edit financial year"**.
- **Expected Result:**
  - The screen stays in full view (no small modal).
  - A yellow badge reading **"Editing"** appears in the top header.
  - The top action buttons switch to: **"Apply statutory defaults"**, **"Cancel"**, and **"Save financial year"**.
  - Read-only fields convert into editable text inputs and date pickers.
- **Pass/Fail Criteria:** Seamless stacked transition into full-page edit mode.

---

#### **TC-UAT-FY-017: Cancel Action & Dirty Tracking**
- **Description:** Verify that clicking Cancel discards pending changes safely.
- **Preconditions:** Currently in Edit mode.
- **Steps:**
  1. Change the Description text to: `"Unsaved draft text"`.
  2. In the top action bar, click **"Cancel"**.
- **Expected Result:**
  - The form returns to read-only View mode.
  - The description reverts to its original saved value.
- **Pass/Fail Criteria:** Changes discarded without database modification.

---

#### **TC-UAT-FY-018: Custom Non-Standard Quarter Boundaries**
- **Description:** Verify adjusting quarter boundaries updates computed days upon saving.
- **Preconditions:** Currently in Edit mode.
- **Steps:**
  1. In Quarter 1, set the End Date to `15 Jul 2030`.
  2. In Quarter 2, set the Start Date to `16 Jul 2030` (keeping contiguity).
  3. Confirm the validation alert is green/hidden.
  4. Click **"Save financial year"**.
  5. Switch to **Tab 2: Computed Month & Day Breakdown**.
- **Expected Result:**
  - Saves successfully with confirmation toast.
  - Tab 2 reflects July split across Q1 (15 days) and Q2 (16 days) with accurate working day counts.
- **Pass/Fail Criteria:** Custom quarter date boundaries supported and recalculated.

---

#### **TC-UAT-FY-019: Quarter Lock & Close Flags**
- **Description:** Verify setting lock and close toggles on individual quarters.
- **Preconditions:** Currently in Edit mode.
- **Steps:**
  1. In the **Quarter 1** card, toggle the switch **"Lock Quarter"** to active.
  2. Toggle **"Close Quarter"** to active.
  3. Click **"Save financial year"**.
- **Expected Result:**
  - The Quarter 1 card in View mode displays badges for `"Locked"` and `"Closed"`.
- **Pass/Fail Criteria:** Quarter status flags persisted.

---

#### **TC-UAT-FY-020: Double-Write Audit Trail Snapshot**
- **Description:** Verify edits record before/after snapshots in the change log.
- **Preconditions:** Edits completed in TC-UAT-FY-018.
- **Steps:**
  1. Open **Tab 3: Audited Change Log**.
  2. Inspect the latest audit entry.
- **Expected Result:**
  - Displays `UpdateFinancialYear` action.
  - Snapshot captures both previous date values and updated date values.
  - Lists the officer name who performed the edit.
- **Pass/Fail Criteria:** Complete audit snapshot verified.

---

### Scenario 5: Destructive Actions & Confirmation Safeguards

#### **TC-UAT-FY-021: Deletion Cancellation via Confirmation Dialog**
- **Description:** Verify that destructive deletion requires explicit affirmative confirmation.
- **Preconditions:** In View mode for a test financial year.
- **Steps:**
  1. In the top action bar, click the red **"Delete"** button.
  2. A confirmation modal pop-up appears:
     - Header: *"Confirm Deletion"* with red warning icon.
     - Message: *"Are you sure you want to permanently delete Financial Year '...'? This action cannot be undone."*
  3. Click the **"Cancel"** button inside the modal dialog.
- **Expected Result:**
  - The modal dialog closes.
  - The financial year remains active and intact in the database.
- **Pass/Fail Criteria:** Deletion requires confirmation; canceling preserves data.

---

#### **TC-UAT-FY-022: Affirmative Permanent Deletion & Cascade Removal**
- **Description:** Verify confirming deletion permanently removes the master record and child quarters.
- **Preconditions:** In View mode for a test financial year (e.g. `2030/2031`).
- **Steps:**
  1. Click **"Delete"**.
  2. In the confirmation dialog, click the solid red button **"Delete"**.
- **Expected Result:**
  - A green toast appears: *"Financial Year '2030/2031' deleted successfully."*
  - The screen navigates back to the master list.
  - The deleted year is no longer listed in the table.
  - All four child quarters are cascade-deleted from the database.
  - Audit log registers a `DeleteFinancialYear` record with the pre-deletion snapshot.
- **Pass/Fail Criteria:** Record removed cleanly and action audited.

---

### Scenario 6: System Settings Global Defaults

#### **TC-UAT-FY-023: System Settings Statutory Default Quarter Template**
- **Description:** Verify administrators can customize statutory defaults in System Settings.
- **Preconditions:** Authenticated as SuperAdmin.
- **Steps:**
  1. In the left navigation sidebar, expand **"System administration"** and click **"System Settings"**.
  2. In the category filter chips, click **"Fiscal Calendar"**.
  3. Review the 10 statutory configuration keys:
     - `Fiscal:DefaultStartMonthDay` (`04-01`)
     - `Fiscal:DefaultEndMonthDay` (`03-31`)
     - `Fiscal:DefaultQ1Start` (`04-01`) / `Fiscal:DefaultQ1End` (`06-30`)
     - `Fiscal:DefaultQ2Start` (`07-01`) / `Fiscal:DefaultQ2End` (`09-30`)
     - `Fiscal:DefaultQ3Start` (`10-01`) / `Fiscal:DefaultQ3End` (`12-31`)
     - `Fiscal:DefaultQ4Start` (`01-01`) / `Fiscal:DefaultQ4End` (`03-31`)
  4. Modify a default date value and click Save.
- **Expected Result:**
  - Settings are saved to the database.
  - Audit log records `UpdateConfig` / `UpdateFiscalTemplate`.
- **Pass/Fail Criteria:** Statutory defaults editable through the user interface.

---

#### **TC-UAT-FY-024: Inheritance by New Financial Years**
- **Description:** Verify that "Apply statutory defaults" consumes the configured settings.
- **Preconditions:** Default template modified in TC-UAT-FY-023.
- **Steps:**
  1. In the sidebar, click **"Financial years & quarters"**.
  2. Click **"Add Financial Year"**.
  3. Click **"Apply statutory defaults"**.
- **Expected Result:**
  - The quarter cards populate with the newly configured dates from System Settings.
- **Pass/Fail Criteria:** Updated database configuration dynamically applied.

---

### Scenario 7: Security & Role-Based Access Control (RBAC)

#### **TC-UAT-FY-025: Unauthenticated User Redirection**
- **Description:** Verify that unauthenticated visitors cannot access fiscal calendar pages.
- **Preconditions:** User is logged out.
- **Steps:**
  1. Open a new private/incognito browser window.
  2. Attempt to open the application directly to the Financial Years page.
- **Expected Result:**
  - The system immediately displays the **Sign In / Login** page.
  - Zero private financial data or table structures are exposed.
- **Pass/Fail Criteria:** Protected by directory-wide authorization guard.

---

#### **TC-UAT-FY-026: Non-Admin Persona Access Restriction**
- **Description:** Verify that external non-administrative roles (e.g. SDF, SDP, Learner) cannot modify financial years.
- **Preconditions:** Sign in as an SDF Facilitator (`sdf@toyota.co.za`).
- **Steps:**
  1. Look for the **"Financial years & quarters"** menu item in the navigation drawer.
  2. Attempt to navigate to the creation form.
- **Expected Result:**
  - The menu item is hidden from the sidebar.
  - Any direct attempt displays an **Access Denied** message or redirects away.
  - Action buttons (*"Add Financial Year"*, *"Edit"*, *"Delete"*) are not available.
- **Pass/Fail Criteria:** Strict Segregation of Duties and Role-Based Access Control enforced.

---

### Scenario 8: Native Maker-Checker Governance Lifecycle & Segregation of Duties

#### **TC-UAT-FY-027: Financial Scheme Year Draft Initialization**
- **Description:** Verify that newly created financial scheme years initialize strictly in `Draft` status with Revision #1 and display the 4-stage Maker-Checker Governance Lifecycle Stepper.
- **Preconditions:** Authenticated as Maker (`sysadmin@merseta.org.za`).
- **Steps:**
  1. In the left navigation sidebar, expand **"System administration"** and click **"Financial years & quarters"**.
  2. Click the solid primary button **"Add Financial Year"**.
  3. Enter a new scheme year code (e.g. `"2035/2036"`).
  4. Click **"Apply statutory defaults"** to populate continuous quarterly boundaries.
  5. Click **"Save financial year"**.
- **Expected Result:**
  - Record saves and switches to View mode.
  - Operational status displays a grey **`Draft`** badge and **`Rev #1`** revision chip.
  - The **"Maker-Checker Governance Lifecycle"** stepper renders with **Step 1: Draft Prep** highlighted with subtitle *"Revision #1 in progress"*.
  - Action bar displays: **"Delete"**, **"Edit financial year"**, and **"Submit for review"**.
- **Pass/Fail Criteria:** Record initializes in Draft status with governance stepper.

---

#### **TC-UAT-FY-028: Maker Submission for Review**
- **Description:** Verify that the preparer (Maker) can submit a prepared draft for administrative review, and that submission locks dates against direct editing.
- **Preconditions:** Viewing a financial year in `Draft` status.
- **Steps:**
  1. In the top-right action bar, click **"Submit for review"** (marked with a paper plane icon).
  2. In the confirmation dialog, review the warning: *"Once submitted, the dates and quarters will be locked against direct modification until review concludes."*
  3. Enter optional preparer notes: `"Draft FY 2035/2036 prepared in accordance with statutory gazette."`
  4. Click **"Submit for Review"**.
- **Expected Result:**
  - A green confirmation toast appears: *"Financial Year '2035/2036' submitted for review successfully."*
  - Operational status transitions to **`Under Review`** (amber badge).
  - The Governance Stepper advances to **Step 2: Under Review** (*"Checker review & segregation of duties"*).
  - An **Audited Governance Metadata Banner** displays the submitter identity (`sysadmin@merseta.org.za`), timestamp, and submission notes.
  - Direct edit buttons are locked and disabled; an informational alert warns: *"Direct editing is locked while this financial year is Under Review."*
- **Pass/Fail Criteria:** Status transitions to Under Review; direct editing locked; audit metadata captured.

---

#### **TC-UAT-FY-029: Segregation of Duties Enforcement (Anti-Self-Approval Safeguard)**
- **Description:** Verify that the system strictly prevents the submitter (Maker) from approving and activating their own submission.
- **Preconditions:** Viewing the submitted record while still signed in as the Maker (`sysadmin@merseta.org.za`).
- **Steps:**
  1. In the top action bar, locate the green button **"Approve & activate"**.
  2. Click **"Approve & activate"**.
  3. In the confirmation dialog, click **"Approve & Activate"**.
- **Expected Result:**
  - The system rejects the transaction.
  - A prominent red error alert appears: *"Error approving financial year: Segregation of duties violation: The submitter cannot approve their own financial year submission. An independent reviewer is required."*
  - The record remains safely in **`Under Review`** status.
  - No operational state change occurs.
- **Pass/Fail Criteria:** Strict Segregation of Duties enforces maker-checker role separation.

---

#### **TC-UAT-FY-030: Independent Reviewer Adjudication & Activation**
- **Description:** Verify that an independent administrator (Checker) can review the schedule, approve the dates, and activate the operational scheme year.
- **Preconditions:** Submitter has logged out; Independent Reviewer (`admin.checker@merseta.org.za`) logs in.
- **Steps:**
  1. Sign in as Independent Checker (`admin.checker@merseta.org.za` with password `MerSETA@2026!`).
  2. In the left navigation sidebar, expand **"System administration"** and click **"Financial years & quarters"**.
  3. Locate the submitted scheme year (e.g. `"2035/2036"`) and click its hyperlink.
  4. Inspect the constituent quarters and the computed working day matrix.
  5. In the top action bar, click **"Approve & activate"**.
  6. In the confirmation dialog, enter review notes: `"Statutory schedule verified against SA public holiday calendar."`
  7. Click **"Approve & Activate"**.
- **Expected Result:**
  - A green toast appears: *"Financial Year '2035/2036' approved and activated successfully."*
  - Operational status badge updates to solid blue **`Active`**.
  - The Governance Stepper highlights **Step 3: Active Year** (*"Approved statutory cycle, locked"*).
  - The Audited Governance Banner displays both Maker and Checker identities and timestamps.
  - A new primary action button appears: **"Request amendment"** (marked with an edit note icon).
- **Pass/Fail Criteria:** Independent administrator successfully establishes the active scheme year.

---

#### **TC-UAT-FY-031: Accidental Mutation Protection & Amendment Revisions (Rev #2)**
- **Description:** Verify that active operational scheme years cannot be directly modified without initiating a formal amendment with mandatory business justification.
- **Preconditions:** Viewing the newly activated financial year.
- **Steps:**
  1. Verify that standard direct edit buttons are absent or locked.
  2. In the top action bar, click **"Request amendment"**.
  3. In the modal dialog, review the consequence notice: *"Active Financial Year is strictly locked against accidental modifications. Requesting an amendment creates a revision (Amendment Draft) where dates and quarters can be safely updated."*
  4. Leave the justification reason blank and observe that the confirm button remains disabled.
  5. Enter a clear business reason: `"Ministerial Gazette notice shifting Q2 submission window by 5 statutory working days."`
  6. Click **"Create Amendment Draft"**.
- **Expected Result:**
  - A green toast appears: *"Amendment draft created for '2035/2036' (Rev #2). You may now edit quarters."*
  - Status updates to **`Amendment Draft`** and revision increments to **`Rev #2`**.
  - The page safely enters edit mode with full access to adjust quarterly dates and descriptions.
  - The Audited Governance Banner displays the revision number and amendment justification.
- **Pass/Fail Criteria:** Active records protected from direct tampering; formal revision audit trail maintained.

---

#### **TC-UAT-FY-032: End-to-End Audited Change Log Verification**
- **Description:** Verify that every stage of the Maker-Checker workflow is permanently recorded in the immutable audit timeline.
- **Preconditions:** Financial year has completed creation, submission, approval, and amendment.
- **Steps:**
  1. On the Financial Year details page, click on Tab 3 titled **"Audited Change Log"** (marked with a clock/history icon).
  2. Review the chronological vertical timeline.
  3. Inspect each entry for:
     - `CreateFinancialYear` (Maker identity, status Draft, initial quarter count)
     - `SubmitFinancialYearForReview` (Maker identity, status Under Review, submitter notes)
     - `ApproveFinancialYearActivation` (Checker identity, status Active, review notes)
     - `RequestFinancialYearAmendment` (Checker identity, status Amendment Draft, Revision 2, justification)
- **Expected Result:**
  - Complete, non-repudiable audit timeline is displayed with exact UTC timestamps, user accounts, before-and-after states, and formatted change summaries.
- **Pass/Fail Criteria:** All lifecycle actions recorded in compliance with PFMA governance standards.

---

## 4. Test Execution Sign-off Sheet

| Test Run ID | Execution Date | Tested By | Role / Department | Status | Comments |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `UAT-FY-2026-01` | ____________ | ___________________ | ___________________ | [ ] Pass  [ ] Fail | ___________________________________ |
| `UAT-FY-2026-02` | ____________ | ___________________ | ___________________ | [ ] Pass  [ ] Fail | ___________________________________ |
