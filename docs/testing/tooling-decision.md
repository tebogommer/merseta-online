# Tooling Decision & Rationale

## Existing Tooling Assessed
- **Playwright (@playwright/test)**: Discovered installed and configured for basic Chromium testing on port 3000. It adheres to Windows SQLite locking rules (`workers: 1`, `fullyParallel: false`). 

## Decisions Made
- **Playwright**: **ETAINED & ENHANCED**. Since it exists and is the industry standard for React SSR apps, we will retain it. I will enhance the config to capture screenshots and videos on failure as dictated by Phase 1.2 rules.
- **Vitest**: **INTRODUCED**. No native unit test runner was discovered. Vitest is the correct choice for Next.js 14/15/16 + Vite-compatible environments because it is drastically faster than Jest, integrates well with native modern ECMAScript features without Babel, and maps directly to the protocol demands. 

## Tooling Execution Plan
1. `npm install -D vitest @vitest/coverage-v8 jsdom @testing-library/react @testing-library/jest-dom`
2. Generate `vitest.config.ts`.
3. Update `package.json` to include `test`, `test:cov`, `test:all`.
