// lib/pdf/playwright-engine.ts
import { chromium, Page } from 'playwright';

export interface PDFGenerationOptions {
  url: string;
  format?: 'A4' | 'Letter';
  landscape?: boolean;
  printBackground?: boolean;
}

/**
 * Invokes a headless Chromium instance to visit a specific local Next.js route
 * and captures its output as a pixel-perfect PDF Buffer.
 */
export async function generatePDF({
  url,
  format = 'A4',
  landscape = false,
  printBackground = true,
}: PDFGenerationOptions): Promise<Buffer> {
  let browser;

  try {
    // Launch headless Chromium. 
    // args optimized for serverless/automated execution
    browser = await chromium.launch({
      headless: true,
      args: [
        '--no-sandbox',
        '--disable-setuid-sandbox',
        '--disable-dev-shm-usage',
        '--disable-gpu'
      ]
    });

    const context = await browser.newContext();
    const page: Page = await context.newPage();

    // Set a specialized system token header or cookie here if needed 
    // to bypass Local Next.js Authentication guards for templates.
    // await context.addCookies([{name: 'pdf-auth', value: 'internal', domain: 'localhost', path: '/'}]);

    // Go to the target Next.js page where the document layout is rendered
    await page.goto(url, { waitUntil: 'networkidle' });

    // Ensure all dynamic layout shifts have concluded, fonts are loaded, etc.
    await page.waitForLoadState('networkidle');

    // Print the document to a Buffer
    const pdfBuffer = await page.pdf({
      format: format,
      landscape: landscape,
      printBackground: printBackground,
      margin: {
        top: '0',
        right: '0',
        bottom: '0',
        left: '0'
      }
    });

    return pdfBuffer;

  } catch (error) {
    console.error("Failed to generate PDF via Playwright:", error);
    throw new Error("PDF Generation Engine Failure");
  } finally {
    if (browser) {
      await browser.close();
    }
  }
}
