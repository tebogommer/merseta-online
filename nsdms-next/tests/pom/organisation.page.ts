import { Page, Locator, expect } from '@playwright/test';

/**
 * OrganisationPage POM
 * Encapsulates interactions with the Organisation Master-Detail views.
 */
export class OrganisationPage {
  readonly page: Page;
  
  // Locators
  readonly nameInput: Locator;
  readonly sdlInput: Locator;
  readonly addressInput: Locator;
  readonly saveButton: Locator;
  readonly deleteButton: Locator;
  readonly certificateButton: Locator;

  constructor(page: Page) {
    this.page = page;
    this.nameInput = page.locator('input[name="organisationName"]');
    this.sdlInput = page.locator('input[name="sdlNumber"]');
    this.addressInput = page.locator('input[name="address"]');
    this.saveButton = page.getByRole('button', { name: /Save Changes|Create Organisation/i });
    this.deleteButton = page.getByRole('button', { name: /Delete/i });
    this.certificateButton = page.locator('#btn-download-certificate');
  }

  async gotoList() {
    await this.page.goto('/organisations');
  }

  async gotoNew() {
    await this.page.goto('/organisations/new');
  }

  async createOrganisation(name: string, sdl: string, address: string, sicCode: string = "99999") {
    await this.gotoNew();
    await this.nameInput.fill(name);
    await this.sdlInput.fill(sdl);
    await this.addressInput.fill(address);
    await this.page.locator('input[name="sicCode"]').fill(sicCode);
    await this.saveButton.click();
  }

  async downloadCertificate() {
    // Start waiting for download before clicking
    const downloadPromise = this.page.waitForEvent('download');
    await this.certificateButton.click();
    return await downloadPromise;
  }
}
