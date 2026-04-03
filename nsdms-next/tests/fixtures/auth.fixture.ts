import { test as base } from '@playwright/test';
import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

type AuthFixtures = {
  adminPage: import('@playwright/test').Page;
};

// Extend basic test by providing an "adminPage" fixture.
export const test = base.extend<AuthFixtures>({
  adminPage: async ({ page }, use) => {
    // 0. Bootstrap Admin in Database
    await prisma.user.upsert({
      where: { email: 'admin@merseta.org.za' },
      update: { role: 'ADMIN' },
      create: { 
        email: 'admin@merseta.org.za', 
        name: 'Admin User', 
        role: 'ADMIN' 
      }
    });

    // 1. Scaffold Admin Context for the application
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
