import { Page, Locator, expect } from '@playwright/test';

/**
 * LearnerPage POM
 * Encapsulates interactions with the Learner enrollment and detail views.
 */
export class LearnerPage {
  readonly page: Page;
  
  // Locators
  readonly rsaIdInput: Locator;
  readonly dobInput: Locator;
  readonly providerIdInput: Locator;
  readonly submitButton: Locator;

  constructor(page: Page) {
    this.page = page;
    this.rsaIdInput = page.locator('#rsaIdNumber');
    this.dobInput = page.locator('#dateOfBirth');
    this.providerIdInput = page.locator('#providerId');
    this.submitButton = page.locator('#btn-enroll-learner');
  }

  async gotoList() {
    await this.page.goto('/learners');
  }

  async gotoNew(providerId?: string) {
    const url = providerId ? `/learners/new?providerId=${providerId}` : '/learners/new';
    await this.page.goto(url);
  }

  async enrollLearner(rsaId: string, dob: string, providerId?: string) {
    await this.gotoNew(providerId);
    await this.rsaIdInput.fill(rsaId);
    await this.dobInput.fill(dob);
    
    // Fill provider if not pre-populated via URL
    if (!providerId && await this.providerIdInput.isVisible()) {
      await this.providerIdInput.fill('1'); // Default test provider
    }
    
    await this.submitButton.click();
  }
}
