# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: 04-provider-accreditation.spec.ts >> Provider Accreditation Flow >> Should strictly enforce accreditation unique bounds
- Location: tests\e2e\04-provider-accreditation.spec.ts:15:7

# Error details

```
PrismaClientKnownRequestError: 
Invalid `prisma.$executeRawUnsafe()` invocation:


Raw query failed. Code: `1`. Message: `no such table: TrainingProviderTypeType`
```

# Test source

```ts
  1  | import { test, expect } from '../fixtures/auth.fixture';
  2  | import { ProviderPage } from '../pom/provider.page';
  3  | import { PrismaClient } from '@prisma/client';
  4  | 
  5  | const prisma = new PrismaClient();
  6  | 
  7  | test.describe('Provider Accreditation Flow', () => {
  8  | 
  9  |   test.beforeAll(async () => {
  10 |      // Ensure baseline relations exist for the E2E test
  11 |      await prisma.$executeRawUnsafe(`INSERT OR IGNORE INTO Organisation (id, organisation_name, sdl_number) VALUES (1, 'Test Org', 'LTestOrg')`);
> 12 |      await prisma.$executeRawUnsafe(`INSERT OR IGNORE INTO TrainingProviderTypeType (id, name, code) VALUES (1, 'SDP', 'SDP')`);
     |      ^ PrismaClientKnownRequestError: 
  13 |   });
  14 | 
  15 |   test('Should strictly enforce accreditation unique bounds', async ({ adminPage }) => {
  16 |     const providerPage = new ProviderPage(adminPage);
  17 | 
  18 |     // 1. Start application
  19 |     await providerPage.gotoNewProvider();
  20 |     
  21 |     // 2. Validate bounds by submitting a blank accreditation intentionally to trigger the Zod validation
  22 |     await providerPage.submit();
  23 | 
  24 |     // 3. Depending on Zod implementation, expect Zod error or server alert
  25 |     // Standard server action UI feedback should be visible
  26 |     await expect(adminPage.locator('text=Valid Organisation is required.')).toBeVisible();
  27 |     await expect(adminPage.getByText(/Accreditation number must be at least 3 characters/)).toBeVisible();
  28 | 
  29 |     // 4. Validate bounds by testing uniqueness/success constraint
  30 |     const uniqueAccreditation = `ACC-TEST-${Date.now()}`;
  31 |     await providerPage.fillApplication(uniqueAccreditation, "1", "1");
  32 |     await providerPage.submit();
  33 |     
  34 |     // 5. Verify the "Double Write" success via sonner toast or router push
  35 |     await expect(adminPage.getByText(/Provider registered successfully/i)).toBeVisible();
  36 |   });
  37 | 
  38 | });
  39 | 
```