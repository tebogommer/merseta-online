# System Under Test

- **Framework/Runtime**: Next.js 16.2.2 (App Router), React 19
- **Package Manager**: NPM (determined from `package-lock.json`)
- **Existing Test Tooling**: Playwright installed (`@playwright/test` v1.59.1 in `devDependencies`). No unit testing framework detected natively configured (Vitest/Jest).
- **Build/Dev Scripts**: 
  - `dev`: `next dev`
  - `build`: `next build`
  - `start`: `next start`
  - `lint`: `next lint`
  - `seed:orgs`: `npx tsx prisma/seeds/seed-organisations.ts`
- **Deployment Shape**: Server-Side Rendered (SSR) / React Server Components (RSC) Web App with Server Actions for API mutations.
