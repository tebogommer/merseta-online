import { test, expect } from '../fixtures/auth.fixture';
import { OrganisationPage } from '../pom/organisation.page';

test.describe('Organisation Management E2E', () => {

  test('Should create an organisation and download its certificate', async ({ adminPage }) => {
    const orgPage = new OrganisationPage(adminPage);
    
    const testOrg = {
      name: `Test MerSETA Org ${Date.now()}`,
      sdl: `L${Math.floor(Math.random() * 900000000) + 100000000}`,
      address: '123 Test Street, Melville, Johannesburg'
    };

    // 1. Create Organisation
    await orgPage.createOrganisation(testOrg.name, testOrg.sdl, testOrg.address);
    
    // 2. Verify we are on the detail page (Check for name in header/input)
    await expect(adminPage.locator('#organisationName')).toHaveValue(testOrg.name);
    
    // 3. Download Certificate
    const download = await orgPage.downloadCertificate();
    
    // 4. Verify download
    expect(download.suggestedFilename()).toContain(testOrg.sdl);
    const path = await download.path();
    expect(path).toBeTruthy();
    
    console.log(`Successfully verified E2E flow for ${testOrg.name}`);
  });

});
