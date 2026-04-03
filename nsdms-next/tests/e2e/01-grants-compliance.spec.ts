import { test, expect } from '../fixtures/auth.fixture';

test.describe('Grants Compliance Flow', () => {
  test('should load the Grants Master Grid and display table elements', async ({ adminPage }) => {
    // Note: If authentication redirects, this will catch the login adminPage
    // In a full testing environment we would use 'adminPage.context().addCookies()' to bypass auth.
    await adminPage.goto('/grants');
    
    // Check if the adminPage loaded
    await expect(adminPage.locator('h1').filter({ hasText: 'Grants Management' })).toBeVisible();
    await expect(adminPage.locator('table')).toBeVisible();
    
    // Check for the "New Application" button
    await expect(adminPage.getByRole('link', { name: 'New Application' })).toBeVisible();
  });

  test('should navigate to a Grant Detail shell and verify tab structure', async ({ adminPage }) => {
    // Fallback to checking the UI without deep ID routing if it's dynamic
    await adminPage.goto('/grants');
    
    const firstRowLink = adminPage.locator('tbody tr').first().locator('a').first();
    
    if (await firstRowLink.isVisible()) {
      await firstRowLink.click();
      
      // We should be on /grants/[id]
      await expect(adminPage.locator('.block').first()).toBeVisible();
      
      // Verify the Stacked Detail Tabs exist
      await expect(adminPage.getByRole('tab', { name: 'General Details' })).toBeVisible();
      await expect(adminPage.getByRole('tab', { name: /Verifications/ })).toBeVisible();
      await expect(adminPage.getByRole('tab', { name: /Payment Requests/ })).toBeVisible();
    }
  });
});
