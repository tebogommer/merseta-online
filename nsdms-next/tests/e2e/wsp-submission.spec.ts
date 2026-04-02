import { test, expect } from '../fixtures/auth.fixture';
import { WspPage } from '../pom/wsp.page';

test.describe('WSP Financial Parity & Zod Boundaries', () => {

  test('Server Action severely rejects invalid Training vs Payroll ratio', async ({ adminPage }) => {
    const wsp = new WspPage(adminPage);

    // 1. Navigate to isolated new WSP form 
    await wsp.gotoNewWsp(1);

    // 2. Intentionally violate the strict financial boundary (Costs > Payroll)
    await wsp.fillFinancials('50', '1000000', '2000000'); // Traning costs 2 Million (Impossible)
    
    // 3. Trigger Server Action Bridge
    await wsp.submit();

    // 4. Assert Backend intercepted the mutation cleanly and surfaced the error without crashing
    await expect(wsp.errorMessageTrainingCosts).toBeVisible();
    await expect(wsp.errorMessageGeneral).toBeVisible();

    // 5. Ensure the Double Write did not accidentally commit by guaranteeing we remain on the form
    expect(adminPage.url()).toContain('/workplace-skills-plans/new');
  });

});
