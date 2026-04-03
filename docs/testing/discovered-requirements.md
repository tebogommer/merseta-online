# Discovered Requirements

## Employer / Stakeholder Management
- Master-Detail Grid required for viewing companies.
- "Smart Shell" pattern utilizing React Server Components for details fetching, Client Components for UI editing.
- Strict requirement that "Visit" activity against an Employer must enforce selection of a specific Contact Person (`contactPersonId`).
- Double-write Audit Logging on all mutations.

## ETQA / Provider Accreditation
- Approval flow (Draft -> Under Review -> Approved/Rejected).
- XState persistence for workflow machine states.
- Provider accreditation involves complex grid data and Zod validation, synchronized with Database persistence.

## Learner Management (Apprenticeships, Learnerships)
- Complex workflows for Learner Enrollments.
- Form submissions with Zod schemas for learner capture.

## Lookup Management (Admin)
- Generic and dynamic CRUD interface for all system taxonomies and reference data types (CategoryType, InterventionType, etc.).
- Active field toggling and creation.
- Admin dashboard displaying data subsets depending on the permissions context.

## Roles and Access (CASL)
- **ADMIN**: Unrestricted management.
- **STANDARD**: Read-only core elements, create interactions only permitted for specific ownership flows.
