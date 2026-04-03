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
    adminPage.on('console', msg => console.log('BROWSER LOG:', msg.text()));
    
    // We can also bind to sonner toast DOM to grab the exact text
    await adminPage.exposeFunction('logToast', (text: string) => console.log('TOAST_CONTENT:', text));
    await adminPage.addScriptTag({ content: `
      const observer = new MutationObserver((mutations) => {
        for (const m of mutations) {
          if (m.addedNodes.length > 0) {
            m.addedNodes.forEach(node => {
               if (node.innerText) window.logToast(node.innerText);
            });
          }
        }
      });
      observer.observe(document.body, { childList: true, subtree: true });
    `});

    // 1. Create Organisation
    await orgPage.createOrganisation(testOrg.name, testOrg.sdl, testOrg.address);

    try {
      await expect(adminPage.getByText(/Organisation successfully created/i)).toBeVisible({ timeout: 10000 });
    } catch (e) {
      const stateDump = await adminPage.locator('#__e2e_state_dump').textContent();
      console.log("TEST FAILED. STATE DUMP:", stateDump);
      throw e;
    }
    await expect(adminPage).toHaveURL(/\/organisations\/\d+/);
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
