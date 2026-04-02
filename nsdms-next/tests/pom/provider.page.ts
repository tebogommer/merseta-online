import { Page, Locator } from '@playwright/test';

export class ProviderPage {
  readonly page: Page;
  readonly newProviderButton: Locator;
  readonly accreditationNumberInput: Locator;
  readonly providerTypeInput: Locator;
  readonly orgIdInput: Locator;
  readonly submitButton: Locator;

  constructor(page: Page) {
    this.page = page;
    this.newProviderButton = page.getByRole('button', { name: /Add Provider/i });
    this.accreditationNumberInput = page.locator('input[name="accreditationNumber"]');
    this.providerTypeInput = page.locator('input[name="providerTypeId"]');
    this.orgIdInput = page.locator('input[name="organisationId"]');
    this.submitButton = page.getByRole('button', { name: /Submit Registration|Submit/i });
  }

  async gotoProvidersList() {
    await this.page.goto('/providers');
  }

  async gotoNewProvider() {
    await this.page.goto('/providers/new');
  }

  async fillApplication(accreditationNumber: string, typeId: string, orgId: string) {
    await this.accreditationNumberInput.fill(accreditationNumber);
    await this.providerTypeInput.fill(typeId);
    await this.orgIdInput.fill(orgId);
  }

  async submit() {
    await this.submitButton.click();
  }
}
