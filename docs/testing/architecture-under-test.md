# Architecture Under Test

## Module Boundaries
- **UI (View)**: Next.js Client and Server Components (`app/**/page.tsx`, `components/ui/**`). Focuses purely on visual representation using Shadcn UI and TailwindCSS.
- **Server Actions (Bridge & Controller)**: Server-side mutation controllers located in `_actions/workflow.ts` directories. These contain the Zod validations, Prisma logic, and ability checks.
- **Data (Persistence)**: Prisma ORM interacting with a SQLite database (`dev.db`).

## AuthN / AuthZ Approach
- **Authentication**: NextAuth.js (`auth()` hooks). Manages JWT session logic.
- **Authorization**: `@casl/ability` policy based rules defined in `lib/abilities.ts`. Defines `ADMIN` vs `STANDARD` capabilities.

## Workflows and State Machines
- Complex entities like `WorkplaceApproval` use `XState` definitions found in `app/etqa/_machines/` to enforce directed graphs for state transitons (e.g., Draft -> SME Review).

## Data Storage
- SQLite (for initial sprint/demo validation), backed by Prisma schema standardizations allowing immediate pivot to PostgreSQL in staging.
