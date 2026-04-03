# Test Data & Environment Isolation Strategy

## Overview
Due to SQLite file-locks (`EACCES: permission denied` for `query_engine.dll.node`), the isolation of concurrent database connections during Playwright tests requires architectural boundaries:

1. **Environment Config**: The `vitest.config.ts` runs functionally decoupled from Prisma database writes. Standard Vitest testing operates using `.mock()` layers of Next.js utilities in `vitest.setup.ts`. 
2. **SQLite Constraints**: `test.db` will be implemented on CI layers to replace `dev.db`.
3. **Data Injection**: E2E testing evaluates states natively. Thus, deterministic mock structures rely on a single baseline script.

## Seeding Rules
- **Seeder Pipeline**: `npm run seed:orgs` injects generic organizational data.
- E2E tests target these static entity structures logically without modifying them out of band, allowing the environment to stay stateless avoiding the "Drift Defect". 
