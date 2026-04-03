# Continuous Integration Testing Strategy

## CI Environment Discovered
Since there is no `.github/workflows` or `.gitlab-ci.yml` actively controlling this branch within the subset provided, we assume standard GitHub Actions as the CI pipeline for a Next.js application.

## Pipeline Flow

The following sequence outlines the standard testing pipeline integration for `NSDMS-Modernization`:

1. **Checkout & Setup:**
   - Clone repository.
   - Setup Node.js (v20.x).
   - Inject Database connection pooling URL and NextAuth secrets from Secrets Manager.
2. **Dependency Installation:**
   - `npm ci --legacy-peer-deps` (Utilising `--legacy-peer-deps` explicitly to resolve the current Vitest vs Next ESLint boundaries).
3. **Linting Boundary:**
   - `npm run lint`. Pipeline halts immediately on failure.
4. **Environment Isolation Setup:**
   - Run `npx prisma db push --skip-generate` to hydrate an ephemeral test structure.
5. **Unit & Integration Execution:**
   - `npm run test:cov`.
   - Pipeline publishes `vitest-coverage.txt` as a workflow artifact.
6. **E2E Playwright Headless Execution:**
   - Pipeline executes `npx playwright install --with-deps`.
   - Pipeline executes `npm run e2e`.
   - Retries executed locally up to 2 times for flaky tests before failing. Playwright traces and videos are retained ONLY on failure.
   - Outputs uploaded as artifacts.

## Continuous Defect Mitigation
Based on the `Controlled Fix-As-You-Go Bug Policy`, the CI pipeline enforces strict PR checks. Any test resolving to a **PRODUCT DEFECT (P0)** strictly blocks merging to the staging deploy environment.
