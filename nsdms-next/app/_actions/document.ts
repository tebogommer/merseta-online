"use server";

import { revalidatePath } from "next/cache";
import { PrismaClient } from "@prisma/client";
import { auth } from "@/auth";
import { defineAbilitiesFor } from "@/lib/abilities";
import { chromium } from "playwright";

const prisma = new PrismaClient();

export async function generateGrantCertificatePDF(grantId: number) {
  try {
    const session = await auth();
    if (!session?.user) throw new Error("Unauthorized");
    
    // CASL check
    const ability = defineAbilitiesFor(session.user);
    if (ability.cannot('read', 'GrantApplication') || ability.cannot('create', 'Document')) {
      throw new Error(`Forbidden: You do not have permission to generate this document.`);
    }

    const systemUserId = session.user.id ? Number(session.user.id) : 1;

    // We must pass the dynamic URL to playwright. 
    // Usually this requires a running app. Vercel / serverless can be weird with localhost,
    // but in this containerized/server environment, we assume the host handles it.
    // Ensure APP_URL is correctly set.
    const baseUrl = process.env.NEXT_PUBLIC_APP_URL || "http://localhost:3000";
    const targetUrl = `${baseUrl}/templates/grant-certificate/${grantId}`;

    const browser = await chromium.launch({ headless: true });
    const page = await browser.newPage();
    
    // We navigate and wait for the page to completely hydrate
    await page.goto(targetUrl, { waitUntil: 'networkidle' });

    // Generate the PDF Buffer
    const pdfBuffer = await page.pdf({
      format: 'A4',
      printBackground: true,
      margin: { top: '0', right: '0', bottom: '0', left: '0' }
    });

    await browser.close();

    // Store in our new Document polymorphic table
    const docRecord = await prisma.document.create({
      data: {
        filename: `Grant_Certificate_${grantId}.pdf`,
        mimeType: 'application/pdf',
        sizeBytes: pdfBuffer.length,
        blob: pdfBuffer,
        isGenerated: true,
        grantApplicationId: grantId,
        createdBy: systemUserId
      }
    });

    revalidatePath(`/grants/${grantId}`);
    
    // Return base64 so the client can trigger a download
    return {
      success: true,
      filename: docRecord.filename,
      base64: pdfBuffer.toString('base64')
    };

  } catch (error: any) {
    console.error("PDF Generation Error:", error);
    return { success: false, message: error.message || "Failed to generate PDF." };
  }
}
