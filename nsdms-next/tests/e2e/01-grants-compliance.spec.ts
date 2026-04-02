import { test, expect } from '@playwright/test';

test.describe('Grants Compliance Flow', () => {
  test('should load the Grants Master Grid and display table elements', async ({ page }) => {
    // Note: If authentication redirects, this will catch the login page
    // In a full testing environment we would use 'page.context().addCookies()' to bypass auth.
    await page.goto('/grants');
    
    // Check if the page loaded
    await expect(page.locator('h1').filter({ hasText: 'Grants Management' })).toBeVisible();
    await expect(page.locator('table')).toBeVisible();
    
    // Check for the "New Application" button
    await expect(page.getByRole('link', { name: 'New Application' })).toBeVisible();
  });

  test('should navigate to a Grant Detail shell and verify tab structure', async ({ page }) => {
    // Fallback to checking the UI without deep ID routing if it's dynamic
    await page.goto('/grants');
    
    const firstRowLink = page.locator('tbody tr').first().locator('a').first();
    
    if (await firstRowLink.isVisible()) {
      await firstRowLink.click();
      
      // We should be on /grants/[id]
      await expect(page.locator('.block').first()).toBeVisible();
      
      // Verify the Stacked Detail Tabs exist
      await expect(page.getByRole('tab', { name: 'General Details' })).toBeVisible();
      await expect(page.getByRole('tab', { name: /Verifications/ })).toBeVisible();
      await expect(page.getByRole('tab', { name: /Payment Requests/ })).toBeVisible();
    }
  });
});
