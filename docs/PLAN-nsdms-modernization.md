# Project Plan: NSDMS Modernization (MVP)

Provide a staged roadmap for rebuilding the NSDMS monolith into a modern, rational Next.js 16+ Modular Monolith. The architecture will use Prisma (SQLite for local demo, Postgres ready), Zod for validation, CASL for RBAC, and XState for complex workflows.

> [!IMPORTANT]
> **MVP Strategy:** We will build a foundational "Smart Shell" that allows raw CRUD on any entity *before* binding them to complex XState workflows. We will begin with the **Employer / Stakeholder Management** module as it is the prerequisite for all other flows (Learners, Grants, and ETQA all require a core `Company` entity).

## Proposed Implementation Phases

### Phase 1: Foundation & "Smart Shell" Scaffold (MVP Bootstrapping)
- Scaffold a Next.js 16+ App Router project (using Shadcn UI).
- Define the base `schema.prisma` configured for SQLite but easily swappable to PostgreSQL.
- Setup **CASL** abilities (e.g., `can('manage', 'Company')`).
- Implement the baseline "Smart Shell / Action Bridge" pattern (UI calls `_actions`).

### Phase 2: Employer Management (The Core MVP Module)
- **Entities:** `Company`, `User`, `CompanyUserLink` (SDFs).
- **Features:** 
    - Master-Detail Grid for viewing companies.
    - Raw Create/Read/Update/Delete (CRUD) forms.
    - Zod validation for UI and Server Actions.
    - Double-write Audit Logging on all mutations.
- **Outcome:** A running system where staff can manage organizations without any rigid state-machine blocking them.

### Phase 3: Workplace Approvals & ETQA (Workflow Introduction)
- Once the CRUD foundation is solid, introduce **XState**.
- **Entities:** `WorkplaceApproval`, `TrainingProvider`.
- Build the State Machine for an approval flow (Draft -> Under Review -> Approved/Rejected).
- Integrate CASL into the XState transitions so only authorized roles can advance the state.

### Phase 4: Learner Lifecycle & Grants (High Complexity)
- Model the massive Learner DB entities.
- Implement specialized background queues (if necessary) for heavy grant calculations.

---

## Technical Architecture Decisions

1. **Database:** Prisma ORM. SQLite for instant demo capability.
2. **Auth:** NextAuth (Auth.js v5) or customized JWT-based session handling, tightly integrated with CASL.
3. **UI/UX:** Stacked Master-Detail with Shadcn UI (DataTable, Forms, Tabs). Forms will support complex nested edits.
4. **Validation:** Zod schemas applied at the Form level and instantly re-applied inside the Server Actions.
5. **Auditing:** Every Server Action logs a JSON snapshot to an `audit_logs` table.

---

## User Review Required (Socratic Gate)

> [!WARNING]
> Before we write any code (`/create`), please clarify the following architectural questions:

1. **Authentication:** For the MVP, should we mock a simple login system (e.g., hardcoded Admin vs. External User roles), or do you want to integrate a real provider (like Keycloak/Active Directory) from day one?
2. **XState Persistence:** When we eventually add XState for workflows, do you want to persist the entire JSON state machine blob in the database, or just a simple `status` string column that the State Machine reads to resume its state?
3. **Entity Auditing:** Do you want the double-write audit logs to track *every single field change* (like Envers did), or only track the JSON snapshot of the object before/after the mutation?

## Verification Plan

### Automated Tests
- Run `prisma validate` to ensure schema portability between SQLite and Postgres.
- Run type-checks to verify Zod / Prisma type alignments.

### Manual Verification
- Launch the Next.js dev server.
- Verify we can view the Company grid, click a row, and edit the Company details via Server Actions.
- Ensure the `audit_logs` table captures the exact timestamp and user ID of the edit.
