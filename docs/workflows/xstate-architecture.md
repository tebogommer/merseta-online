# XState Domain Architecture (Option A)

## Decision Log
**Date:** April 2026
**Decision:** Adopt **Option A**, crystallizing legacy dynamic database-driven workflow rules (Roles + State Transitions) directly into static XState `.ts` definitions.

## Context
The legacy MerSETA architecture relied heavily on a generic `hosting_company_process` table recursively linked with `process_roles` to dynamically generate workflow steps, assigning granular `UserPermissionEnum` permissions directly on the DB sequence. 

While incredibly flexible, this created highly coupled, untyped runtime logic prone to invisible state drifts. 

## The Option A Paradigm
By adopting Option A, we prioritize **Type Safety and Performance**. 

All legacy `ProcessRoles` have been collapsed into standard XState `meta` objects within the statechart nodes. 

### Benefits
1. **Zero Database Lookups:** We eliminate the N+1 lookups on `process_roles` every single time an entity transitions.
2. **Deterministic UI:** The Frontend CASL ability engine now natively statically analyzes the exact state the XState machine is in, knowing exactly which Role possesses the `Approve` or `Edit` capability before the DB is even queried.
3. **Traceable Business Logic:** 100% of the State-Machine transition rules live in Git, enabling PR reviews for process alterations instead of arbitrary DB mutations.

### Implementation Pattern Example

```typescript
import { setup } from 'xstate';

export const exampleMachine = setup({
  types: {
    events: {} as { type: 'APPROVE' } | { type: 'REJECT' },
  },
}).createMachine({
  id: 'example',
  initial: 'Pending_SDF',
  states: {
    Pending_SDF: {
      // Option A: Role enforcement statically defined in machine meta
      meta: {
        roleAllowed: 'SDF',
        rolePermission: 'Upload_And_Submit', // Matches legacy enum intent
        stepSequence: 1
      },
      on: {
        APPROVE: 'Pending_Manager'
      }
    },
    Pending_Manager: {
      meta: {
        roleAllowed: 'Manager',
        rolePermission: 'FinalApproval',
        stepSequence: 2
      },
      type: 'final'
    }
  }
});
```

All auto-generated scaffold files (`/lib/machines/domains/*`) will follow this statically defined `meta` pattern to achieve deterministic RBAC.
