import { test as base } from '@playwright/test';

type AuthFixtures = {
  adminPage: import('@playwright/test').Page;
};

// Extend basic test by providing an "adminPage" fixture.
export const test = base.extend<AuthFixtures>({
  adminPage: async ({ page }, use) => {
    // Scaffold Admin Context for the application
    await page.goto('/api/auth/signin');
    
    // Check if we are on a login form (handle next-auth basic UI)
    const emailField = page.locator('input[name="email"]');
    if (await emailField.isVisible()) {
      await emailField.fill('admin@merseta.org.za');
      await page.locator('input[name="password"]').fill('password123'); 
      await page.locator('button[type="submit"]').click();
      
      // Wait for navigation away from signin
      await page.waitForURL(url => !url.href.includes('signin'));
    }

    await use(page);
  },
});

export { expect } from '@playwright/test';
