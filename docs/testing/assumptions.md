# Testing Assumptions

1. **Authentication Mode**: Given the absence of a visible `seed-users` script inside package.json, we assume that development happens via a deterministic mockup of `auth()` or leveraging an implicit seeded admin.
2. **Database Context**: Since Windows `query_engine.dll.node` lock errors are a known problem on Prisma + Windows + Playwright, we assume SQLite execution must be heavily serial (`fullyParallel: false` in Playwright).
3. **Execution Reporting Path**: It is assumed that Playwright html reports and Vitest coverage output should funnel into `docs/testing/results/`.
