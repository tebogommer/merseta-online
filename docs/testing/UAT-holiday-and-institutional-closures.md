# User Acceptance Testing (UAT) Test Specification: Holidays & Institutional Closures Management

**Module:** System Administration & Governance — Statutory Holidays & Institutional Closures (Option A)  
**System:** merSETA National Skills Development Management System (NSDMS)  
**Target Audience:** Human QA Testers, Business Analysts, and System Administrators  
**Architecture:** .NET 10 Blazor Server + MudBlazor Master-Detail (Clean Architecture)  
**Version:** 1.0 (Human-Centric UI Navigation)  
**Date:** September 2026  
**Status:** Approved for Manual & User Acceptance Testing  

---

## 1. Document Control & Scope

### 1.1 Purpose
This document provides step-by-step User Acceptance Testing (UAT) instructions written specifically for **human testers**. All steps use clear user interface navigation paths (sidebar menus, pillar accordions, action buttons, table rows, tabs, and modal dialogs) with **zero technical URLs**.

### 1.2 User Personas & Test Credentials
| Persona Role | User Account | Authorized Functions |
| :--- | :--- | :--- |
| **SuperAdmin** | sysadmin@merseta.org.za | Full CRUD, approval of ad-hoc closures, year-end shutdown amendments, audit inspection |
| **Admin** | admin@merseta.org.za | Create, Edit, View holidays and institutional closures, inspect change logs |
| **CLO / QA Officer** | clo@merseta.org.za | View-only access to holidays; observes SLA paused days on inspections |
| **External SDF** | sdf@toyota.co.za | Strictly unauthorized (must be intercepted and blocked) |

---

## 2. Test Scenarios Summary Matrix

| Scenario ID | Test Scenario Description | Test Case Count | Priority |
| :--- | :--- | :--- | :--- |
| **SC-01** | Navigation & Master Registry Discovery | 3 | High |
| **SC-02** | Search Filtering & merSETA Year-End Shutdown Verification | 2 | High |
| **SC-03** | Ad-Hoc Gazetted Public Holiday Creation | 4 | Critical |
| **SC-04** | Master-Detail View-by-Default Inspection | 3 | Critical |
| **SC-05** | Interactive SLA Impact Simulator Verification | 3 | Critical |
| **SC-06** | Audited Change Log Double-Write Inspection | 2 | High |
| **SC-07** | Full-Form Stacked Edit Mode & Save Confirmation | 3 | High |
| **SC-08** | Destructive Affirmation Dialog & Record Deletion | 2 | Medium |

---

## 3. Detailed User Test Cases for Human Testers

### Scenario 1: Navigation & Master Registry Discovery

#### **TC-UAT-HOL-001: Navigation to Holidays & Closures Hub**
- **Description:** Verify that an administrator can locate and open the Holidays & Institutional Closures registry from the main navigation menu.
- **Preconditions:** User is signed in with an administrative account (sysadmin@merseta.org.za).
- **Steps:**
  1. Open the application and sign in.
  2. Locate the left-hand navigation sidebar.
  3. Scroll down and expand the accordion titled **"System administration"**.
  4. Click on the menu item **"Holidays & institutional closures"** (marked with an event calendar icon).
- **Expected Result:**
  - The main page updates to display the header **"Holidays & Institutional Closures"** with subtitle *"Manage statutory public holidays, annual merSETA shutdowns, and special closures governing officer SLA working day calculations."*
  - Breadcrumbs at top display: Home > Administration > Holidays & Closures.
  - Top summary cards display metrics:
    - *Total configured:* Total non-working day entries.
    - *Affecting officer SLAs:* Count of active dates that pause SLA calculation clocks.
    - *Statutory public holidays:* Count of Act 36 of 1994 statutory dates.
    - *merSETA shutdowns:* Count of institutional year-end closure spans.
  - Action button **"Add closure or holiday"** is visible on the right.
  - The master data table loads showing 2026/2027 holidays.
- **Pass/Fail Criteria:** Page loads cleanly without errors; table and summary cards populated.

---

### Scenario 2: Search Filtering & merSETA Year-End Shutdown

#### **TC-UAT-HOL-002: Filter Table by 'Shutdown'**
- **Description:** Verify that typing 'Shutdown' in the search bar quickly isolates the multi-day institutional year-end closure span.
- **Preconditions:** On the Holidays & Institutional Closures list page.
- **Steps:**
  1. Locate the search text field at the top of the data table.
  2. Type Shutdown.
- **Expected Result:**
  - Table instantly filters down to the record **"merSETA Annual Year-End Office Shutdown 2026/2027"**.
  - Type badge shows **"merSETA annual year-end shutdown"** in orange.
  - Dates display: 24 Dec 2026 (Thursday) to 03 Jan 2027 (Sunday).
  - Duration chip shows 11 days.
  - SLA Badge displays Pauses SLAs.
- **Pass/Fail Criteria:** Single matching multi-day record returned; dates and badges render properly.

---

### Scenario 3: Ad-Hoc Gazetted Public Holiday Creation

#### **TC-UAT-HOL-003: Create Ad-Hoc Holiday**
- **Description:** Verify that an administrator can create a new special ad-hoc public holiday (e.g. Election Day).
- **Preconditions:** On the Holidays & Institutional Closures list page.
- **Steps:**
  1. Click the primary button **"Add closure or holiday"** in the top action bar.
  2. Verify that the URL updates to /admin/non-working-days/create and the page displays **"New Holiday or Institutional Closure"**.
  3. In **"Holiday or closure designation"**, enter: 2026 National General Elections Day.
  4. In **"Typology classification"**, select: National statutory public holiday.
  5. In **"Start date"** and **"End date"**, select: 2026-09-08.
  6. In **"Calendar year"**, verify it defaults to 2026.
  7. In **"Operational status"**, ensure Approved (Active in SLA engine) is selected.
  8. In **"Authority, circular or gazette reference"**, enter: Government Gazette No. 50412.
  9. Ensure the switch **"Excludes from workflow SLAs"** is toggled ON.
  10. In **"Administrative description / operational notes"**, enter: Special gazetted public holiday proclaimed by the President for national and provincial elections. Universal workflow SLAs pause during this day.
  11. Click **"Create record"** in the sticky top bar.
- **Expected Result:**
  - Green success toast message: Holiday / closure '2026 National General Elections Day' created successfully.
  - The application automatically navigates to the View Detail page (/admin/non-working-days/{id}).
- **Pass/Fail Criteria:** Form validates, submits, and transitions into read-only View mode.

---

### Scenario 4: Master-Detail View-by-Default Drill-Down

#### **TC-UAT-HOL-004: Strict View Mode Verification**
- **Description:** Verify that records open strictly in read-only View mode by default without editable inputs.
- **Preconditions:** Navigated to the record detail page (/admin/non-working-days/{id}).
- **Steps:**
  1. Inspect the sticky header top bar.
  2. Inspect the fields rendered under the **"General details"** tab.
- **Expected Result:**
  - Sticky header displays:
    - Back button: **"Back to list"**.
    - Record title: 2026 National General Elections Day.
    - Badges: Approved (green), National statutory holiday (blue).
    - Subtitle: Period: 08 Sept 2026 | Spanning: 1 day | Workflow SLA: Excluded (Pauses SLAs).
    - Action buttons: **"Delete"** (outline red) and **"Edit record"** (filled dark/primary).
  - All form fields render as ReadOnlyField components (plain text typography with subtle caption labels), with zero input or textarea tags.
- **Pass/Fail Criteria:** Zero editable fields present; all metadata readable.

---

### Scenario 5: Interactive SLA Impact Simulator

#### **TC-UAT-HOL-005: Simulate SLA Horizon Over Non-Working Days**
- **Description:** Verify that the interactive SLA Impact Simulator accurately projects target completion dates by pausing SLA countdown clocks over institutional closures and holidays.
- **Preconditions:** On the detail page of any holiday or closure.
- **Steps:**
  1. Click on the tab titled **"SLA Impact Simulator"** (marked with a calculator/timer icon).
  2. In **"Simulation submission date"**, select or verify 15 Dec 2026.
  3. In **"Statutory business days horizon"**, enter 20 (standard merSETA Workplace Approval SLA).
  4. Click **"Calculate simulated SLA due date"**.
- **Expected Result:**
  - Green projection card displays:
    - *Calculated due date:* 22 January 2027.
    - *Target business days:* 20 working days.
    - *Calendar days elapsed:* 38 calendar days.
    - *Days paused / excluded:* 18 days.
  - Forensic breakdown table lists every single calendar day between 15 Dec 2026 and 22 Jan 2027:
    - Normal business days display status **"Elapsed"** (green checkmark).
    - Saturdays and Sundays display status **"Excluded"** (Weekend, grey badge).
    - 16 Dec 2026 displays status **"Excluded"** (Day of Reconciliation).
    - 24 Dec 2026 to 03 Jan 2027 display status **"Excluded"** (merSETA Annual Year-End Office Shutdown 2026/2027).
- **Pass/Fail Criteria:** Calculation runs instantly; exact dates match 20 working day exclusion rules.

---

### Scenario 6: Audited Change Log Double-Write

#### **TC-UAT-HOL-006: Inspect Audited Change Log**
- **Description:** Verify that creation and updates are logged in the audited change log with actor, timestamp, and JSON snapshot.
- **Preconditions:** On the detail page of the created holiday.
- **Steps:**
  1. Click on the tab titled **"Audited Change Log"** (marked with a history clock icon).
- **Expected Result:**
  - A timeline appears displaying the event **"CreateNonWorkingDay"** (green chip).
  - Timestamp reflects UTC creation time.
  - User reflects the authenticated user (e.g. sysadmin@merseta.org.za).
  - An expandable Technical Verification Data section reveals the JSON snapshot with record details.
- **Pass/Fail Criteria:** Audit event is visible and reflects authentic change history.

---

### Scenario 7: Full-Form Stacked Edit Mode

#### **TC-UAT-HOL-007: Edit Record and Save Changes**
- **Description:** Verify that clicking 'Edit record' opens all fields in edit mode with Cancel and Save buttons.
- **Preconditions:** On the detail page in View mode.
- **Steps:**
  1. Click the button **"Edit record"** in the top action bar.
  2. Verify the top bar updates:
     - An orange badge **"Editing"** appears.
     - Action buttons switch to **"Cancel"** (outline) and **"Save changes"** (filled).
  3. In **"Administrative description / operational notes"**, append: Updated: Confirmed by National IEC and Department of Home Affairs. All merSETA regional operations closed.
  4. Click **"Save changes"**.
- **Expected Result:**
  - Green success snackbar appears: Holiday / closure '2026 National General Elections Day' saved successfully.
  - The view switches back to read-only View mode.
  - The description displays the updated text in read-only format.
- **Pass/Fail Criteria:** Form fields become editable, update successfully, and return to View mode.

---

### Scenario 8: Destructive Action Affirmation & Deletion

#### **TC-UAT-HOL-008: Delete Record with Confirmation Dialog**
- **Description:** Verify that deleting a non-working day requires explicit confirmation via a dialog and cleanly removes the record.
- **Preconditions:** On the master list or detail view of the ad-hoc record.
- **Steps:**
  1. From the master list, locate the row 2026 National General Elections Day.
  2. Click the three-dots action menu icon on the far right of the row.
  3. Click **"Delete record"**.
  4. A confirmation dialog appears titled **"Confirm Deletion"** with message: "Are you sure you want to delete '2026 National General Elections Day'? This will remove this date from all future SLA calculations."
  5. Click the red destructive button **"Delete closure"**.
- **Expected Result:**
  - Dialog closes.
  - Green success toast: Non-working day record '2026 National General Elections Day' was deleted successfully.
  - The record is immediately removed from the data table.
- **Pass/Fail Criteria:** Dialog requires affirmative confirmation; record deleted from DB and UI.
