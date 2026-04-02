import { test, expect } from '../fixtures/auth.fixture';

test.describe('Reporting & Ingestion Pipeline Automation', () => {
  test('should load the Ingestion Center and interact with SETMIS and SARS components', async ({ adminPage }) => {
    console.log("Navigating to ingestion...");
    await adminPage.goto('/admin/ingestion');
    
    console.log("Checking UI Shell...");
    await expect(adminPage.locator('h2').filter({ hasText: 'Data Ingestion Center' })).toBeVisible();
    await expect(adminPage.locator('text=SARS Levy Ingestion')).toBeVisible();
    await expect(adminPage.locator('text=SETMIS Data Extract')).toBeVisible();
    
    console.log("Clicking Generate Extract...");
    const generateExtractBtn = adminPage.getByRole('button', { name: 'Generate Extract' });
    await expect(generateExtractBtn).toBeVisible();
    await generateExtractBtn.click();
    
    console.log("Waiting for Extract Job ID state...");
    await expect(adminPage.getByText(/Extract Job ID:/)).toBeVisible();
    await expect(adminPage.getByText(/In Progress|100%/)).toBeVisible();
    
    console.log("Mocking SARS file drop...");
    const mockCsv = Buffer.from('SDLNum,Amount\nL123456,50000');
    const sarsUploadInput = adminPage.locator('input[type="file"]').first();
    await sarsUploadInput.setInputFiles({
        name: 'sars_levy_monthly.csv',
        mimeType: 'text/csv',
        buffer: mockCsv
    });

    console.log("Verifying SARS file staged...");
    await expect(adminPage.getByRole('button', { name: /sars_levy_monthly\.csv/i })).toBeVisible();

    console.log("Clicking Start Ingestion...");
    const startIngestionBtn = adminPage.getByRole('button', { name: /Start Ingestion/i });
    await expect(startIngestionBtn).toBeVisible();
    await startIngestionBtn.click();
    
    console.log("Verifying SARS Job ID state...");
    await expect(adminPage.getByText(/Job ID:/).first()).toBeVisible();
    console.log("Test execution completed.");
  });
});
