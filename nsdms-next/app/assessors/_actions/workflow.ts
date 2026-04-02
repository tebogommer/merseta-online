"use server";

import { PrismaClient } from "@prisma/client";
import { revalidatePath } from "next/cache";
import { auth } from "@/auth";
import { defineAbilitiesFor } from "@/lib/abilities";
import { z } from "zod";
import { getNextStatus } from "@/lib/machines/complianceMachine";

const prisma = new PrismaClient();

async function requireAuthAndAbility(action: 'manage' | 'create' | 'read' | 'update' | 'delete', subject: 'AssessorModeratorApplication' | 'AssessorModExtensionOfScope' | 'all') {
  const session = await auth();
  if (!session?.user) throw new Error("Unauthorized");
  
  const ability = defineAbilitiesFor(session.user);
  if (ability.cannot(action, subject)) {
    throw new Error(`Forbidden: You do not have permission to ${action} ${subject}`);
  }
  return session.user;
}

async function createAuditLog(recordId: number, entityName: string, actionName: string, actor: string, snapshot: any) {
  try {
    await prisma.auditLog.create({
      data: {
        recordId,
        entityName,
        actionName,
        actor,
        snapshot: JSON.stringify(snapshot),
      }
    });
  } catch(e) {
    console.warn("Audit Log failed.", e);
  }
}

export type ActionState = {
  success?: boolean;
  errors?: Record<string, string[]>;
  message?: string;
  id?: number;
};

const applicationSchema = z.object({
  applicationType: z.enum(["ASSESSOR", "MODERATOR"]),
  trainingProviderId: z.coerce.number().optional(), // Can apply independently first
});

export async function fetchAssessors() {
  try {
    return await prisma.assessorModeratorApplication.findMany({
      include: {
        user: true,
        trainingProvider: {
            include: {
                organisation: true
            }
        },
      },
      orderBy: { createdAt: 'desc' }
    });
  } catch (e) {
    console.error(e);
    return [];
  }
}

export async function createAssessorApplicationAction(prevState: ActionState, formData: FormData): Promise<ActionState> {
  try {
    const sessionUser = await requireAuthAndAbility('create', 'AssessorModeratorApplication');
    const systemUserId = sessionUser.id ? Number(sessionUser.id) : 1;
    const actorEmail = sessionUser.email || "system";

    const dataObj = Object.fromEntries(formData.entries());
    const parsed = applicationSchema.safeParse(dataObj);
    
    if (!parsed.success) {
      return { errors: parsed.error.flatten().fieldErrors };
    }

    const resultId = await prisma.$transaction(async (tx) => {
      const newApp = await tx.assessorModeratorApplication.create({
        data: {
          userId: systemUserId,
          applicationType: parsed.data.applicationType,
          trainingProviderId: parsed.data.trainingProviderId || null,
          status: "DRAFT",
          createdBy: systemUserId
        }
      });
      await createAuditLog(newApp.id, "AssessorModeratorApplication", "CREATE", actorEmail, newApp);
      return newApp.id;
    });

    revalidatePath("/assessors");
    return { success: true, id: resultId };

  } catch (error: any) {
    return { message: error.message || "Failed to create application." };
  }
}

// XState Decoupled Workflow Action
export async function transitionAssessorStatusAction(id: number, event: 'SUBMIT' | 'COMMENCE_REVIEW' | 'APPROVE' | 'REJECT' | 'APPEAL' | 'REVERT_TO_DRAFT') {
    try {
        const sessionUser = await requireAuthAndAbility('update', 'AssessorModeratorApplication');
        const systemUserId = sessionUser.id ? Number(sessionUser.id) : 1;
        const actorEmail = sessionUser.email || "system";

        const appCurrent = await prisma.assessorModeratorApplication.findUnique({ where: { id }});
        if (!appCurrent) throw new Error("Entity Not Found");

        // Evaluate State Machine
        const newStatus = getNextStatus(appCurrent.status, event);
    
        const resultId = await prisma.$transaction(async (tx) => {
          const app = await tx.assessorModeratorApplication.update({
            where: { id },
            data: {
              status: newStatus,
              modifiedBy: systemUserId
            }
          });
          await createAuditLog(app.id, "AssessorModeratorApplication", "STATUS_CHANGE", actorEmail, app);
          return app.id;
        });
    
        revalidatePath(`/assessors/${id}`);
        revalidatePath(`/assessors`);
        return { success: true };
    
      } catch (error: any) {
        return { success: false, message: error.message || "Failed to update application status." };
      }
}
