"use server";

import { generatePDF } from "@/lib/pdf/playwright-engine";

export async function createComplianceCertificatePDFAction(referenceId: string) {
  try {
    // 1. Audit Check: Confirm User is authorized.
    // 2. Resolve URL for the hidden template. 
    // Note: You must run your Next.js app on absolute URLs when headless browsers visit.
    const baseUrl = process.env.NEXTAUTH_URL || 'http://localhost:3000';
    const targetUrl = `${baseUrl}/templates/certificate/${referenceId}`;

    // 3. Delegate to the Playwright Engine
    const pdfBuffer = await generatePDF({
      url: targetUrl,
      format: 'A4',
      printBackground: true
    });

    // 4. Return as Base64 for client side downloading or saving to DB
    return {
      success: true,
      data: pdfBuffer.toString('base64'),
    };
  } catch (error) {
    console.error("Server Action PDF Error:", error);
    return { success: false, error: "Failed to build PDF document." };
  }
}
