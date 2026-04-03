# PLAN: Comprehensive Lookup Management System

## 1. Goal Description
The objective is to expose all 271 `*Type` lookup tables in the Prisma schema to the Next.js admin frontend via a unified, dynamic CRUD interface. This transition will replace hardcoded switch/case mapping with an automated naming resolver, introduce a categorized Lookups Dashboard to maintain navigability, and securely enforce Role-Based Access Control (RBAC) and Audit Logging.

## 2. User Review Required
> [!IMPORTANT]
> **Dynamic DB Access Security:** Exposing 271 tables via a generic `[lookupType]` parameter creates an attack surface. The plan proposes extracting a static enum/list of allowed models from the Prisma DMMF at build time (or generating a constants file) to prevent arbitrary model access requests.

> [!WARNING]
> **Categorization:** With 271 lookups, a flat list is unusable. We will logically group them into domains (e.g., Demographics, ETQA, WSP, Financial). We will need to map these categories or use a searchable metadata registry interface.

## 3. Proposed Changes

---

### Phase 1: Database & Controller Abstraction
#### [MODIFY] `app/admin/lookups/_actions/lookup-controller.ts`
- Remove the hardcoded `switch(lookupType)` logic.
- Implement a string resolver: `my-lookup-type` → `myLookupType` (camelCase for Prisma client) and `MyLookupType` (PascalCase for Entity name).
- Implement an explicit Allowed-List validation check against `Prisma.dmmf` or a generated constant list to ensure users can only ever query `*Type` tables.

### Phase 2: Master Lookups Dashboard
#### [NEW] `app/admin/lookups/page.tsx`
- Create a master categorical dashboard displaying cards for each domain of lookups. 
- Implement a client-side search bar specifically for administrators to quickly find and jump to the management grid of a specific lookup (e.g., searching "Chamber" routes to `/admin/lookups/chamber-types`).

#### [NEW] `app/admin/lookups/_config/lookup-categories.ts`
- A manual dictionary / registry that categorizes the 271 tables into actionable domain groups (e.g., `Demographics: ['GenderType', 'NationalityType', 'EquityType']`).

### Phase 3: Dynamic Grid & Schema Standardization
#### [MODIFY] `app/admin/lookups/[lookupType]/page.tsx`
- Refine the dynamic data table to handle lookups with large record counts. 
- Ensure all rows accurately display Audit standard arrays if they exist on the model (e.g., `active`).

#### [MODIFY] `app/admin/lookups/[lookupType]/[id]/page.tsx`
- Re-bind the smart shell form to gracefully handle lookups that might only have `name` and `code` without a `description` field natively without crashing.

### Phase 4: Navigation Architecture
#### [MODIFY] `components/sidebar.tsx` (or equivalent layout)
- Add a distinguished "System Lookups" primary navigation node for `ADMIN` users linking to the new Master Lookups Dashboard.

## 4. Open Questions
1. **Approval**: Do you approve the switch from manual case routing to an allowed-list dynamic Prisma resolver?
2. **Standardization**: Are we guaranteed that all 271 `*Type` models possess the same core columns `(id, code, name, description, active, createdAt)` or do some vary significantly? We may need a fallback UI pattern if some lookups have extra relational data.

## 5. Verification Plan
- **Security Audit:** Confirm manually attempting to path to `/admin/lookups/user` or `/admin/lookups/organisation` fails the allowed-list validation and returns a 404/403.
- **E2E Playwright:** Update tests to mock navigation into the newly categorized Lookup Dashboard and modify an arbitrary lookup like `ChamberType`.
- **UI Parity:** Verify dark mode remains 100% compliant during semantic generation.
