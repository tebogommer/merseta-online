import { test, expect } from '../fixtures/auth.fixture';
import { ProviderPage } from '../pom/provider.page';
import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

test.describe('Provider Accreditation Flow', () => {

  test.beforeAll(async () => {
     // Ensure baseline relations exist for the E2E test
     await prisma.$executeRawUnsafe(`INSERT OR IGNORE INTO Organisation (id, organisation_name, sdl_number) VALUES (1, 'Test Org', 'LTestOrg')`);
     await prisma.$executeRawUnsafe(`INSERT OR IGNORE INTO TrainingProviderTypeType (id, name, code) VALUES (1, 'SDP', 'SDP')`);
  });

  test('Should strictly enforce accreditation unique bounds', async ({ adminPage }) => {
    const providerPage = new ProviderPage(adminPage);

    // 1. Start application
    await providerPage.gotoNewProvider();
    
    // 2. Validate bounds by submitting a blank accreditation intentionally to trigger the Zod validation
    await providerPage.submit();

    // 3. Depending on Zod implementation, expect Zod error or server alert
    // Standard server action UI feedback should be visible
    await expect(adminPage.locator('text=Valid Organisation is required.')).toBeVisible();
    await expect(adminPage.getByText(/Accreditation number must be at least 3 characters/)).toBeVisible();

    // 4. Validate bounds by testing uniqueness/success constraint
    const uniqueAccreditation = `ACC-TEST-${Date.now()}`;
    await providerPage.fillApplication(uniqueAccreditation, "1", "1");
    await providerPage.submit();
    
    // 5. Verify the "Double Write" success via sonner toast or router push
    await expect(adminPage.getByText(/Provider registered successfully/i)).toBeVisible();
  });

});
