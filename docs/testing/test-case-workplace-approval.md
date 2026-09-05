# Test Case Specification: TC-WFL-011

## Title
**Workplace Approval & Site Audit (`WPAPP`) Multi-Persona End-to-End Lifecycle Test**

---

## 1. Test Summary & Objective
Validate the complete statutory lifecycle of a **Workplace Approval & Site Audit** application using seeded test data in the NSDMS application:
1. **Employer / SDF:** Creates the workplace application, links the mandatory Employer Contact Person, registers certified workshop mentors against NAMB/QCTO ratios, declares required workshop tools, and initiates the workflow.
2. **Client Liaison Officer (CLO):** Reviews the application in the Universal Task Inbox, takes a task lease, and schedules an on-site physical inspection.
3. **CLO & Evaluating Assessor:** Conducts the on-site physical inspection, verifies tools and mentor capacity, and records audit findings.
4. **Quality Assurance / Administrator:** Enforces Maker-Checker / Segregation of Duties (SoD) and grants the formal 3-year statutory workplace accreditation.

---

## 2. Test Environment & Seed Data References

| Component | Test Value / Seed Reference | Notes |
| :--- | :--- | :--- |
| **Application URL** | `http://localhost:5121` | Interactive Blazor Server web application |
| **Host Employer** | `Toyota SA Motors (Pty) Ltd` (`SDL: L123456789`) | Seeded in `SampleDataSeeder` |
| **Employer Site** | `Prospecton Manufacturing Plant (Durban South)` | Seeded in `SampleDataSeeder` |
| **Mandatory Contact Person** | `Nalini Priya Moodley` (`RSA ID: 8505120058089`) | Enforces Employer Visit Contact Person rule |
| **Trade Scope** | `Automotive Motor Mechanic` (SAQA Qual ID: `65409`) | Seeded qualification & trade policy |
| **Trade Ratio Policy** | `1:4` Standard Ratio (1 Mentor : 4 Apprentices) | Governed by `IMentorRatioPolicyEngine` |
| **Master Artisan Mentor** | `Sipho Themba Khumalo` (`ART-1999-88741`) | Master Automotive Artisan & Workshop Lead (18 yrs exp) |
| **Evaluating Assessor** | `Bongani Sithole` (`RSA ID: 8803155412083`) | Accredited ETQA Assessor / Inspector |
| **Primary Workflow Process** | `WPAPP` | Defined in `WorkflowDefinitionSeeder` |

---

## 3. Pre-Requisites
1. Ensure the web application is running (`http://localhost:5121`).
2. Verify that `SampleDataSeeder` and `WorkflowDefinitionSeeder` have executed successfully.
3. Use the **Persona Switcher** dropdown in the top-right app bar of the UI to switch between role contexts without needing to log out.

---

## 4. Test Execution Steps

### Phase 1: Employer SDF Application & Mentorship Setup
* **Persona Switcher Selection:** `Skills Development Facilitator (SDF)`
* **Starting URL:** `http://localhost:5121/workplace-approvals`

| Step # | Action | Input Data / Clicks | Expected UI & System Result |
| :---: | :--- | :--- | :--- |
| **1.1** | Navigate to List | Go to `/workplace-approvals` | Workplace Approvals data grid loads with search bar and filter controls. |
| **1.2** | Open Creation Form | Click **+ Create Workplace Approval** (or visit `/workplace-approvals/0`) | Opens the full-page create form. Status badge displays `Draft`. Approval reference displays `Auto-generated upon creation`. |
| **1.3** | Select Employer | Dropdown: **Host Employer / Organisation** $\rightarrow$ `Toyota SA Motors (Pty) Ltd (L123456789)` | Employer is bound. |
| **1.4** | Enter Trade Details | **Qualification / Trade Scope:** `Automotive Motor Mechanic`<br>**SAQA Qualification ID:** `65409` | Fields populate correctly. |
| **1.5** | Select Contact Person | Dropdown: **Mandatory Employer Contact Person** $\rightarrow$ `Nalini Priya Moodley (8505120058089)` | Contact person linked per visit governance mandate. |
| **1.6** | Select Assessor | Dropdown: **Evaluating Assessor / Inspector** $\rightarrow$ `Bongani Sithole (8803155412083)` | Assessor linked for audit scheduling. |
| **1.7** | Select Trade Policy | Dropdown: **Designated Trade Ratio Policy** $\rightarrow$ `Automotive Motor Mechanic (65409 - 1:4)` | Statutory ratio standard set to 1:4. |
| **1.8** | Save Initial Record | Click the **Save** button in top sticky bar | Toast message: `Workplace Approval record saved successfully`. Reference number generated (e.g. `WPA-2026-TOYOTA-PROS-01`). Tabs 2–8 unlock. |
| **1.9** | Add Workshop Mentor | Click **Tab 2: Workshop Mentors** $\rightarrow$ Click **+ Add Certified Mentor**<br>- **Artisan / Mentor:** `Sipho Themba Khumalo`<br>- **Designation:** `Master Automotive Artisan & Workshop Lead`<br>- **Trade Number:** `ART-1999-88741`<br>- **Experience:** `18` $\rightarrow$ Click **Save** | Mentor row appears in the table. The **Artisan Mentor-to-Apprentice Ratio & Capacity Dashboard** updates to **Compliant (100% capacity / 4 Available Slots)**. |
| **1.10** | Add Workshop Tools | Click **Tab 3: Tools & Equipment** $\rightarrow$ Click **+ Add Required Tool Item**<br>- **Tool Name:** `Hydraulic Vehicle Lift (4-Post)`, Qty Req: `4`, Qty Avail: `6`<br>- Add second item: `OBD-II Diagnostic Engine Scanners`, Qty Req: `2`, Qty Avail: `4` | Both tool items appear in the grid with category and inspection status indicators. |
| **1.11** | Initiate Workflow | On the top **Workflow Action Bridge**, click **Initiate WPAPP** | State badge transitions from `Draft / Uninitiated` to `DRAFT` (*Draft Workplace Request*). An initial audited change log entry is committed. |

---

### Phase 2: SETA CLO Desk Review & Inspection Scheduling
* **Persona Switcher Selection:** `SETA Client Liaison Officer (CLO)`
* **Starting URL:** `http://localhost:5121/tasks`

| Step # | Action | Input Data / Clicks | Expected UI & System Result |
| :---: | :--- | :--- | :--- |
| **2.1** | Open Task Inbox | Go to `/tasks` | Universal Task Inbox renders tabs: `My Tasks`, `Unassigned Pool`, `Completed`. |
| **2.2** | Find Review Task | Click **Unassigned Pool** tab $\rightarrow$ Locate task: *"Workplace Approval & Site Audit: Toyota SA Motors"* | Task card displays priority `Normal`, due date in 7 days, and role `Client Liaison Officer (CLO)`. |
| **2.3** | Claim Task | Click **Claim Task** button | Task status changes to `Claimed` (reserved under active officer lease). |
| **2.4** | Open Detail View | Click the hyperlinked task title or deep-link to `/workplace-approvals/{id}` | Workplace Approval Detail view opens. Sticky top bar reflects `CLO` context. |
| **2.5** | Schedule Visit | 1. In Tab 1, set **Site Inspection Date** to tomorrow's date.<br>2. On the **Workflow Action Bridge**, click the blue button:<br>$$\mathbf{Schedule\ Physical\ Inspection}$$ | Action confirmation modal appears. Enter optional comments: *"Desktop verification satisfactory. On-site inspection scheduled."* Click **Confirm**. |
| **2.6** | Verify Transition | Inspect top bar and Action Bridge | Status Badge updates to `AUDIT_SCHEDULED` (*Site Audit Scheduled*). Stepper step 2 is active. Automated notification dispatched. |

---

### Phase 3: CLO & Assessor On-Site Inspection & Tool Verification
* **Persona Switcher Selection:** `SETA Client Liaison Officer (CLO)` or `Assessor & Quality Assurance Partner`
* **Starting URL:** `http://localhost:5121/workplace-approvals/{id}`

| Step # | Action | Input Data / Clicks | Expected UI & System Result |
| :---: | :--- | :--- | :--- |
| **3.1** | Review Workshop Site | Navigate to `/workplace-approvals/{id}` | Detail page loads in `AUDIT_SCHEDULED` state. |
| **3.2** | Verify Tools Checklist | Click **Tab 3: Tools & Equipment** $\rightarrow$ Verify all tools have Available $\ge$ Required | Ratios and safety tolerances display compliant. |
| **3.3** | Enter Audit Findings | In Tab 1, enter text in **Audit Findings & Recommendations**:<br>`"Workshop premises, 4-post vehicle lifts, and diagnostic equipment physically inspected. Master artisan Sipho Khumalo verified. Site complies with merSETA trade test readiness guidelines."` $\rightarrow$ Click **Save** | Confirmation toast confirms update to audit findings. |
| **3.4** | Complete Verification | On the **Workflow Action Bridge**, click:<br>$$\mathbf{Complete\ Site\ Verification}$$ | Comments modal prompts for rationale. Enter *"Site inspection passed with zero safety violations."* $\rightarrow$ Click **Confirm**. |
| **3.5** | Verify Transition | Check State Badge | State Badge updates to `AUDITED` (*Audit Completed - Tools & Mentors Verified*). Next action for QA Approval becomes available. |

---

### Phase 4: QA Approver / Administrator Final Accreditation
* **Persona Switcher Selection:** `System Administrator`
* **Starting URL:** `http://localhost:5121/workplace-approvals/{id}`

| Step # | Action | Input Data / Clicks | Expected UI & System Result |
| :---: | :--- | :--- | :--- |
| **4.1** | Open Detail Record | Go to `/workplace-approvals/{id}` | Page renders under `Admin Control` persona. |
| **4.2** | Check Maker-Checker | Verify Segregation of Duties | Governance engine validates that the approver is an independent officer from the initiator. |
| **4.3** | Set Accreditation Dates | 1. **Approval Granted Date:** Today<br>2. **Accreditation Expiry Date:** Today + 3 Years (statutory 3-year validity) $\rightarrow$ Click **Save** | Dates persist to database. |
| **4.4** | Execute Final Approval | On the **Workflow Action Bridge**, click the green button:<br>$$\mathbf{Approve\ Workplace\ Accreditation}$$ | Confirmation modal opens. Enter comments: *"Accreditation approved for 3-year term. Automotive Motor Mechanic trade ready."* $\rightarrow$ Click **Confirm**. |
| **4.5** | Verify Terminal State | Check State Badge and Action Bridge | 1. State Badge shows `APPROVED` (*Workplace Approved*).<br>2. Chip shows **Completed / Finalised** with a checkmark.<br>3. No further edit buttons or pending action gates remain open. |
| **4.6** | Inspect Audit Trail | Click **Tab 8: Workflow History** | Full chronological timeline renders:<br>- `DRAFT` $\rightarrow$ `AUDIT_SCHEDULED` (by CLO)<br>- `AUDIT_SCHEDULED` $\rightarrow$ `AUDITED` (by CLO)<br>- `AUDITED` $\rightarrow$ `APPROVED` (by Admin)<br>With timestamps, actor names, roles, and comments. |
| **4.7** | Verify Apprentice Placement Readiness | Click **Tab 4: Placed Learners** | Host employer is now accredited. Employer can register and assign apprentices up to the available capacity quota (4 slots). |

---

## 5. Post-Condition Verification Checklist

- [ ] **No Database Integer Leakage:** Headers, toasts, and dropdowns only display business reference strings (`WPA-2026-TOYOTA-PROS`, `L123456789`, `Automotive Motor Mechanic`).
- [ ] **Audited Change Log Double-Write:** All state mutations wrote entries into both `WorkflowHistory` and `AuditLog` with before/after snapshots.
- [ ] **Task Auto-Completion:** The original task in `/tasks` is marked `Completed` and no orphaned open tasks remain for this instance.
- [ ] **Ratio Invariant:** At no point was the ratio hardcoded to 1:4 in code; it was evaluated dynamically via `IMentorRatioPolicyEngine`.
- [ ] **Employer Contact Person Link:** Record successfully persisted the foreign key relational link to `Nalini Priya Moodley` (`ContactPersonId`).
