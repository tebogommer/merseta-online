# Gap Analysis: MerSETA NSDMS Porting Progress

## 📊 Porting Scorecard

| Module                 | Status      | Detail Depth | Actions | Audit |
|------------------------|-------------|--------------|---------|-------|
| organisations          | ✅ DONE      | Stacked Shell | CRUD    | ✅     |
| providers              | 🟡 PARTIAL  | Stacked Shell | CRUD    | ✅     |
| learners               | 🟡 PARTIAL  | Partial Shell | Basic   | ❌     |
| workplace-skills-plans | 🔴 PENDING  | Folder Only  | None    | ❌     |
| etqa                   | 🔴 PENDING  | Machine Only | None    | ❌     |
| grants                 | 🔴 PENDING  | Placeholder  | None    | ❌     |
| lookups (Admin)        | ✅ DONE      | Generic Shell| CRUD    | ✅     |

---

## 🛑 Critical Missing Items (High Priority)

### 1. The Dashboard (Metric-over-List)
- **Current State:** `app/page.tsx` simply redirects to `/organisations`.
- **Target:** A domain-driven dashboard showing user-specific data (Admin sees all, External users see their Company/Learners). 
- **Required Stats:** Active Learners, Pending WSPs, Accreditation Expiry alerts.

### 2. "Visit" Activity & Contact Person Validation
- **Requirement:** Any Visit activity must enforce `contactPersonId` selection.
- **Current State:** No dedicated "Visit" or "Scheduling" logic found in the current scaffold.

### 3. XState Persistence Bridge
- **Requirement:** Formal state machines for WSP and ETQA.
- **Current State:** `etqa/_machines` exists, but the "Controller" (Action Bridge) does not yet persist complex state transitions to the database for these modules.

### 4. Deep Editing (Nested Grids)
- **Requirement:** Tabbed Master record with a Child Grid for related records (e.g., Company -> Branches, or Provider -> Site Visits).
- **Current State:** Most views are scalar (single-record) form edits. No "Deep Save" across parents and children yet.

---

## 🎨 Design System Compliance Audit

| Requirement               | Status | Evidence |
|---------------------------|--------|----------|
| Stacked Detail View (Tabs)| 🟡      | Tabs used in some views, but missing ChildGrids. |
| Smart Shell Pattern       | ✅      | `organisations` follows Shell/View/Bridge/Controller. |
| Confirmation Toasts       | 🟡      | Present in `lookup-form.tsx` but not globally consistent. |
| Admin CRUD active         | ✅      | Admin lookup management is functional. |
| Visual Parity (View/Edit) | 🟡      | Basic parity, but requires layout unification. |

---

## 🚀 Next Steps (Recommendations)

1. **Implement the Dashboard:** Move from redirect to a proper "Smart Shell" dashboard.
2. **Scaffold WSP Module:** Apply the "Smart Shell" pattern to the Workplace Skills Plan folder.
3. **Visit Validation Rule:** Implement a reusable `VisitForm` in `_components` that enforces the Contact Person relationship.
4. **Child Grid Integration:** Add a "Related Records" tab to the [Organisation] details using the DataTable component.
