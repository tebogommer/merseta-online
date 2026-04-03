import { test, expect } from '../fixtures/auth.fixture';
import { LearnerPage } from '../pom/learner.page';
import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

/**
 * Learner Enrollment E2E
 * Verifies the full registration pipeline for new learners.
 */
test.describe('Learner Enrollment Pipeline', () => {

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

    // Seed and Get IDs for baseline relations
    await prisma.organisation.upsert({
      where: { id: 1005 },
      update: {},
      create: { 
        id: 1005, 
        organisationName: 'Learner Enrollment Test Org', 
        sdlNumber: 'L1005LRN' 
      }
    });

    await prisma.providerTypeType.upsert({
      where: { code: 'SDP' },
      update: {},
      create: { name: 'SDP', code: 'SDP', description: 'Skills Provider', active: true }
    });

    await prisma.interventionType.upsert({
      where: { id: 1 },
      update: {},
      create: { id: 1, name: 'Learnership', code: 'LRN', description: 'Learnership Type', active: true }
    });

    await prisma.qualificationType.upsert({
      where: { id: 1 },
      update: {},
      create: { id: 1, name: 'NatCert', code: 'NC', description: 'NC Type', active: true }
    });

    const sdpType = await prisma.providerTypeType.findUnique({ where: { code: 'SDP' } });
    
    // Create the Provider entity for the learner to enroll at
    await prisma.trainingProvider.upsert({
      where: { organisationId: 1005 },
      update: {},
      create: { 
        organisationId: 1005, 
        accreditationNumber: 'ACC-05-LRN', 
        status: 'Active',
        providerTypeId: sdpType?.id || 1 
      }
    });
  });

  test('Should successfully enroll a new learner and verify toast confirmation', async ({ adminPage }) => {
    const learnerPage = new LearnerPage(adminPage);

    // 0. Ensure no conflicting data for this test ID
    const testRsaId = '9202204720083';
    const existing = await prisma.learner.findFirst({ where: { rsaIdNumber: testRsaId } });
    if (existing) {
        await prisma.learnerEnrollment.deleteMany({ where: { learnerId: existing.id } });
        await prisma.learner.delete({ where: { id: existing.id } });
    }

    // 1. Setup test data
    const testLearner = {
      rsaId: testRsaId,
      dob: '1992-02-20'
    };

    // 2. Perform enrollment
    const tp = await prisma.trainingProvider.findUnique({ where: { organisationId: 1005 } });
    await learnerPage.enrollLearner(testLearner.rsaId, testLearner.dob, tp?.id.toString());
    
    // 3. Verify Success Toast (Sonner)
    const toast = adminPage.locator('[data-sonner-toast]');
    await expect(toast).toBeVisible();
    const content = await toast.textContent();
    console.log('TOAST_CONTENT:', content);
    expect(content).toContain('Learner successfully enrolled');
    const toastText = await toast.textContent();
    console.log("Toast emitted:", toastText);
    await expect(toast).toContainText('Learner successfully enrolled.');

    // 4. Verify Redirect
    await expect(adminPage).toHaveURL(/.*\/learners/);
    
    console.log(`Successfully verified E2E enrollment for RSA ID: ${testLearner.rsaId}`);
  });

});
