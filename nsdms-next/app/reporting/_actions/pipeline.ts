"use server";

import { revalidatePath } from "next/cache";
import { PrismaClient } from "@prisma/client";
import { auth } from "@/auth";
import { defineAbilitiesFor } from "@/lib/abilities";

const prisma = new PrismaClient();

async function requireAdminAuth() {
  const session = await auth();
  if (!session?.user) throw new Error("Unauthorized");
  
  const ability = defineAbilitiesFor(session.user);
  if (ability.cannot('manage', 'ReportingExtract')) {
    throw new Error(`Forbidden: You do not have permission to trigger Reporting Extracts.`);
  }
  return session.user;
}

export type ActionState = {
  success?: boolean;
  message?: string;
};

/**
 * Simulates generating a SETMIS/NLRD batch flat-file extraction from the DB.
 */
export async function generateExternalExtractAction(extractType: 'SETMIS' | 'NLRD' | 'SAQA'): Promise<ActionState> {
  try {
    const sessionUser = await requireAdminAuth();
    const systemUserId = sessionUser.id ? Number(sessionUser.id) : 1;

    // Simulate Heavy Query Generation
    const mockCount = Math.floor(Math.random() * 5000) + 1000;
    const simulatedCsvBuffer = Buffer.from(`ID,Name,Type,Value\n1,GeneratedRow1,${extractType},Ok`);

    // Complete pipeline transaction
    await prisma.$transaction(async (tx) => {
      // 1. Create the Audit Tracker
      const extract = await tx.reportingExtract.create({
        data: {
          extractType,
          status: "COMPLETED",
          recordsProcessed: mockCount,
          createdBy: systemUserId
        }
      });

      // 2. Wrap the CSV Output in the Polymorphic Document Model
      await tx.document.create({
        data: {
          filename: `${extractType}_Batch_${new Date().getTime()}.csv`,
          mimeType: 'text/csv',
          sizeBytes: simulatedCsvBuffer.length,
          blob: simulatedCsvBuffer,
          isGenerated: true,
          reportingExtractId: extract.id,
          createdBy: systemUserId
        }
      });
    });

    revalidatePath("/reporting");
    return { success: true };
  } catch (error: any) {
    console.error(error);
    return { success: false, message: error.message };
  }
}

/**
 * Simulates a massive file hydration pipeline like the `SarsLevyReconService`.
 * Injects 10 dummy SARS payment rows into random organisations.
 */
export async function simulateSarsIngestionAction(): Promise<ActionState> {
  try {
    await requireAdminAuth();
    
    const orgs = await prisma.organisation.findMany({ take: 10 });
    if (orgs.length === 0) return { success: false, message: "No organisations found to inject SARS data into." };

    for (const org of orgs) {
        await prisma.sarsLevyDetail.create({
            data: {
                organisationId: org.id,
                arrivalDate: new Date(),
                levyYear: new Date().getFullYear(),
                amount: Math.random() * 50000 + 10000 // R10,000 to R60,000
            }
        });
    }

    revalidatePath("/reporting");
    return { success: true };
  } catch (error: any) {
    return { success: false, message: error.message };
  }
}
