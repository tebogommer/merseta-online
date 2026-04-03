# Test Seeds Dictionary

## Current Seeds Discovered
- `prisma/seeds/seed-organisations.ts`: Injects default baseline stakeholders into the database. E2E modules rely heavily on the entities exposed here.

## Data Assumptions
Since standard unit tests operate contextually behind mocked server-action boundaries, they do not require heavy seeding constraints.
