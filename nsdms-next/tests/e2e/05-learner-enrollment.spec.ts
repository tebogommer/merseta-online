import { test, expect } from '../fixtures/auth.fixture';
import { LearnerPage } from '../pom/learner.page';

/**
 * Learner Enrollment E2E
 * Verifies the full registration pipeline for new learners.
 */
test.describe('Learner Enrollment Pipeline', () => {

  test('Should successfully enroll a new learner and verify toast confirmation', async ({ adminPage }) => {
    const learnerPage = new LearnerPage(adminPage);

    // 1. Setup test data (Using a valid RSA ID for the Luhn checksum)
    const testLearner = {
      rsaId: '9202204720082', // Valid SA ID checksum
      dob: '1992-02-20'
    };

    // 2. Perform enrollment
    await learnerPage.enrollLearner(testLearner.rsaId, testLearner.dob);
    
    // 3. Verify Success Toast (Sonner)
    // Playwright locator for Sonner toasts is typically [data-sonner-toast]
    const toast = adminPage.locator('[data-sonner-toast]');
    await expect(toast).toBeVisible();
    await expect(toast).toContainText('Learner successfully enrolled.');

    // 4. Verify Redirect
    await expect(adminPage).toHaveURL(/.*\/learners/);
    
    console.log(`Successfully verified E2E enrollment for RSA ID: ${testLearner.rsaId}`);
  });

});
