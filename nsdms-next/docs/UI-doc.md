# MerSETA NSDMS Modernization: UI Architecture & Patterns

This document maps the legacy PrimeFaces/JSF presentation layer paradigms from the original Java Monolith (`src/main/webapp/...`) to the new Next.js 15 + Shadcn/UI **Stacked Master-Detail** architecture (The "Antigravity" Pattern).

## 1. List Page (The Grid)

### Legacy Pattern
- Utilized PrimeFaces `<p:dataTable>` heavily nested within `<h:form>`.
- Relied on JSF session state for pagination/filtering.
- Often cluttered with inline command buttons and expanding rows `<p:rowExpansion>`.

### Modern Pattern (Next.js)
- **Component:** Shadcn `DataTable` (powered by TanStack Table).
- **Responsive Logic:** Persistent primary columns (ID, Status). Secondary columns hidden on mobile (`hidden md:table-cell`).
- **Interaction:** Row clicks trigger Stacked Navigation (`router.push('/[resource]/[id]')`). Inline modals are strictly prohibited for major entity navigation to preserve URL state and history.

---

## 2. Detail Page (The Drill-Down)

### Legacy Pattern
- Heavy usage of `<p:dialog>` popups or accordion panels (`<p:accordionPanel>`).
- Users lost their context if the page refreshed.

### Modern Pattern (Next.js)
- **Layout:** Replaces the Grid entirely (Stacked Navigation).
- **Header:** Sticky Card Top Bar containing Breadcrumbs for navigation and the **Workflow Action Bar** (see section 4).
- **Structure:** Shadcn `Tabs` isolating core metadata (General) from children operations (e.g., Line Items / Verified Users) and Audit histories.

---

## 3. Edit / Create Page

### Legacy Pattern
- Multi-step wizards `<p:wizard>` and deeply nested isolated field edits.

### Modern Pattern (Next.js)
- **Visual Parity:** The Edit layout is identical to the View layout constraint. Inputs replace static text fields in the exact same DOM position.
- **Interactivity:** Uses Inline Validation via `Zod`. Avoids generic "Save" clicks without immediate feedback.
- **Deep Saves:** Nested child grids inside the Edit Form trigger synchronized deep transactions. Edits are handled entirely rather than isolating fields in popups.

---

## 4. Workflow Action Bar (Action Bridge)

### Legacy Pattern
- Bound directly to `Tasks` and `TaskUsers` using generic JSF Action Listeners (`#{taskBean.approve}`).

### Modern Pattern (Next.js)
- **Component:** Client Component Bridge (`[record]-actions.tsx`).
- **State Management:** XState V5 Mathematical engine.
- **Logic:** Buttons dispatch decoupled semantic events (`APPROVE`, `SUBMIT`) rather than hard-coded status strings. Powered by `useTransition` to handle async Server Action mutations without hydration blocking.

---

## 5. Dashboard Page

### Legacy Pattern
- Tabular text summaries or minimal `<p:chart>` models running extremely expensive aggregate SQL queries on page load.

### Modern Pattern (Next.js)
- **Layout:** Metric-over-List layout (e.g., Admin Reporting Dashboard).
- **Architecture:** KPI cards at the top fetching light aggregation data. Server Components stream `Suspense` boundaries for deeper grids, ensuring the dashboard skeleton paints instantly.

---

## 6. Document / File Panel

### Legacy Pattern
- PrimeFaces `<p:fileUpload>` linked to `Doc` and `DocByte` entities. Output generation relied strictly on heavyweight JasperReports `JasperService.java`.

### Modern Pattern (Next.js)
- **Storage:** Polymorphic `Document` table linked to parent domain IDs.
- **Generation:** Playwright Headless Chromium engine converting standard React Templates (`/app/templates/*`) into pixel-perfect PDFs internally via Server Actions.
- **Display:** Displayed systematically inside a dedicated generic "Documents" interface Tab within the Detail page.

---

## 7. Audit & History Tab

### Legacy Pattern
- Rendered historical tracking tables drawn from `UpdateAuditTrail` queries manually designed for specific entities.

### Modern Pattern (Next.js)
- **Backend Flow:** "Double Write" Policy. Server Actions inherently update the model and simultaneously write to the universal `audit_logs` table parsing `{before, after}` JSON snapshots.
- **UI:** A standard `<Suspense>` wrapped `WorkflowHistory` or `AuditHistory` component dropped into the last Tab of every Detail Page, rendering uniform timelines without bespoke coding per entity.
