import { test, expect } from '../fixtures/auth.fixture';

test.describe('Assessors Navigation Flow', () => {
  test('should load the Master Assessor grid and render columns', async ({ adminPage }) => {
    await adminPage.goto('/assessors');
    
    await expect(adminPage.locator('h1').filter({ hasText: 'Assessors & Moderators' })).toBeVisible();
    
    // Check for standard headers
    await expect(adminPage.getByRole('columnheader', { name: 'User' })).toBeVisible();
    await expect(adminPage.getByRole('columnheader', { name: 'Status' })).toBeVisible();
  });

  test('should navigate to the detail view and check tabs validation', async ({ adminPage }) => {
    await adminPage.goto('/assessors');
    
    const firstRowLink = adminPage.locator('tbody tr').first().locator('a').first();
    
    if (await firstRowLink.isVisible()) {
      await firstRowLink.click();
      
      // Verify Stacked Tabbing
      await expect(adminPage.getByRole('tab', { name: 'Assessor Details' })).toBeVisible();
      await expect(adminPage.getByRole('tab', { name: /Extensions of Scope/ })).toBeVisible();
    }
  });
});
