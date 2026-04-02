import { test, expect } from '@playwright/test';

test.describe('Security & CASL Enforcement', () => {

  test('Unauthenticated user is redirected to Edge barrier', async ({ page }) => {
    // Attempting to directly hijack an internal entity detail page
    await page.goto('/organisations/1');
    
    // Assert Edge middleware caught and redirected to NextAuth default sigin or 307
    expect(page.url()).toContain('/api/auth/signin');
  });

  test('STANDARD user cannot view Audit Logs (CASL 404 Bounce)', async ({ page }) => {
    // 1. Authenticate as Standard User
    await page.goto('/api/auth/signin');
    await page.fill('input[name="email"]', 'user@merseta.org.za');
    await page.fill('input[name="password"]', 'password123'); // any password due to mock
    await page.click('button[type="submit"]');

    // 2. Wait for Session
    await page.waitForURL(url => !url.href.includes('signin')); 

    // 3. Attempt direct hijack of high-clearance dashboard
    const response = await page.goto('/admin/audit-logs');
    
    // 4. Assert CASL physically denied route rendering, returning 404 void
    expect(response?.status()).toBe(404);
  });

  test('ADMIN user CAN view Audit Logs', async ({ page }) => {
    // 1. Authenticate as Admin User
    await page.goto('/api/auth/signin');
    await page.fill('input[name="email"]', 'admin@merseta.org.za');
    await page.fill('input[name="password"]', 'password123'); 
    await page.click('button[type="submit"]');

    // Wait for Session to prevent POST abort
    await page.waitForURL(url => !url.href.includes('signin')); 

    // 2. Access dashboard
    await page.goto('/admin/audit-logs');
    
    // 3. Assert CASL permitted viewing of System Audit Matrix header
    await expect(page.getByRole('heading', { name: 'System Audit Matrix' })).toBeVisible();
  });

});
