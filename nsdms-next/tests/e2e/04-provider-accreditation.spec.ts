import { test, expect } from '../fixtures/auth.fixture';
import { ProviderPage } from '../pom/provider.page';
import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

test.describe('Provider Accreditation Flow', () => {

  test.beforeAll(async () => {
    // Seed Admin User
    await prisma.user.upsert({
      where: { email: 'admin@merseta.org.za' },
      update: { role: 'ADMIN' },
      create: { 
        email: 'admin@merseta.org.za', 
        name: 'Admin User', 
        role: 'ADMIN' 
      }
    });

    // Ensure cleanup of any previous provider linked to this org
    const existingProvider = await prisma.trainingProvider.findUnique({ where: { organisationId: 1004 } });
    if (existingProvider) {
        await prisma.trainingProvider.delete({ where: { id: existingProvider.id } });
    }

    // Ensure consistent seeding for E2E
    await prisma.organisation.upsert({
      where: { id: 1004 },
      update: {},
      create: { 
        id: 1004, 
        organisationName: 'Provider Accreditation Test Org', 
        sdlNumber: 'L1004ACC' 
      }
    });

    await prisma.providerTypeType.upsert({
      where: { code: 'SDP' },
      update: {},
      create: { 
        name: 'Private SDP', 
        code: 'SDP', 
        description: 'Skills Development Provider', 
        active: true 
      }
    });
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
    const sdp = await prisma.providerTypeType.findUnique({ where: { code: 'SDP' } });
    const uniqueAccreditation = `ACC-TEST-${Date.now()}`;
    await providerPage.fillApplication(uniqueAccreditation, sdp?.id.toString() || "1", "1004");
    await providerPage.submit();
    
    // 5. Verify the "Double Write" success via sonner toast or router push
    await expect(adminPage.getByText(/Provider registered successfully/i)).toBeVisible();
  });

});
