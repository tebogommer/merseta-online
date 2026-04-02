import { test, expect } from '../fixtures/auth.fixture';

test.describe('WSP Document Signoff Workflow', () => {

  test('Signoff wizard successfully validates and simulates document upload', async ({ adminPage }) => {
    // 1. Navigate to an existing WSP detail page (mocked ID 1)
    await adminPage.goto('/workplace-skills-plans/1');

    // 2. Click the Compliance & Signoff Tab
    await adminPage.getByRole('tab', { name: /Compliance & Signoff/i }).click();

    // 3. Verify the Signoff Wizard rendered in IDLE state
    await expect(adminPage.locator('text=WSP Sign-Off Document')).toBeVisible();
    await expect(adminPage.getByRole('button', { name: /Select Document/i })).toBeVisible();

    // 4. Mock file upload (set file input)
    // Create a mock PDF buffer
    const mockPDF = Buffer.from('%PDF-1.4 mock content');
    
    // Playwright File Chooser interaction
    const fileChooserPromise = adminPage.waitForEvent('filechooser');
    await adminPage.getByRole('button', { name: /Select Document/i }).click();
    const fileChooser = await fileChooserPromise;
    await fileChooser.setFiles({
        name: 'signed_wsp_authorization.pdf',
        mimeType: 'application/pdf',
        buffer: mockPDF
    });

    // 5. Click the Confirm & Upload button that appears
    const confirmBtn = adminPage.getByRole('button', { name: /Confirm & Upload/i });
    await expect(confirmBtn).toBeVisible();
    await confirmBtn.click();

    // 6. Verify loading state streams then transitions to SUCCESS
    await expect(adminPage.locator('text=Securely streaming to Blob Storage...')).toBeVisible();
    
    // Wait for the mock 2-second timeout completion
    await expect(adminPage.locator('text=Verification Complete')).toBeVisible({ timeout: 5000 });
    
    // 7. Verify the presence of the sonner toast for Double Write audit logs validation
    await expect(adminPage.locator('[data-sonner-toast]')).toContainText('Document uploaded securely.');
    
    // 8. Verify the attached State Badge
    await expect(adminPage.locator('text=ATTACHED')).toBeVisible();
  });

});
