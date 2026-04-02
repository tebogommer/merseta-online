import { test, expect } from '@playwright/test';

test.describe('Assessors Navigation Flow', () => {
  test('should load the Master Assessor grid and render columns', async ({ page }) => {
    await page.goto('/assessors');
    
    await expect(page.locator('h1').filter({ hasText: 'Assessors & Moderators' })).toBeVisible();
    
    // Check for standard headers
    await expect(page.getByRole('columnheader', { name: 'User' })).toBeVisible();
    await expect(page.getByRole('columnheader', { name: 'Status' })).toBeVisible();
  });

  test('should navigate to the detail view and check tabs validation', async ({ page }) => {
    await page.goto('/assessors');
    
    const firstRowLink = page.locator('tbody tr').first().locator('a').first();
    
    if (await firstRowLink.isVisible()) {
      await firstRowLink.click();
      
      // Verify Stacked Tabbing
      await expect(page.getByRole('tab', { name: 'Assessor Details' })).toBeVisible();
      await expect(page.getByRole('tab', { name: /Extensions of Scope/ })).toBeVisible();
    }
  });
});
