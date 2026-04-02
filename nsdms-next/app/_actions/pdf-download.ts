"use server";

import { generateAccreditationPDF } from "@/lib/pdf/react-pdf-engine";
import { revalidatePath } from "next/cache";

/**
 * Server Action to generate and download the Accreditation Certificate.
 */
export async function downloadAccreditationCertificateAction(payload: any) {
  try {
    const buffer = await generateAccreditationPDF({
      organisationName: payload.organisationName,
      sdlNumber: payload.sdlNumber,
      accreditationNumber: payload.accreditationNumber || "ACCR-PENDING-001",
      decisionDate: new Date().toLocaleDateString(),
      expiryDate: new Date(Date.now() + 365 * 24 * 60 * 60 * 1000 * 5).toLocaleDateString(), // 5 Years
    });

    // Convert to base64 for client side download
    const base64 = buffer.toString("base64");
    
    return {
      success: true,
      data: base64,
      fileName: `Accreditation-Certificate-${payload.sdlNumber}.pdf`,
    };
  } catch (error: any) {
    console.error("PDF Download Action Error:", error);
    return { success: false, message: "Failed to generate certificate." };
  }
}
