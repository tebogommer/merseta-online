"use server";

import { revalidatePath } from "next/cache";
import { PrismaClient } from "@prisma/client";
import { auth } from "@/auth";
import { defineAbilitiesFor } from "@/lib/abilities";
import { z } from "zod";
import { getNextStatus } from "@/lib/machines/complianceMachine";

const prisma = new PrismaClient();

async function requireAuthAndAbility(action: 'manage' | 'create' | 'read' | 'update' | 'delete', subject: 'GrantApplication' | 'all') {
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

const grantSchema = z.object({
  organisationId: z.coerce.number(),
  grantType: z.enum(["MANDATORY", "DISCRETIONARY"]),
  finYear: z.coerce.number().min(2020).max(2050),
  amountRequested: z.coerce.number().min(0).optional(),
});

export async function fetchGrants() {
  try {
    return await prisma.grantApplication.findMany({
      include: {
        organisation: true,
        wsp: true,
      },
      orderBy: { createdAt: 'desc' }
    });
  } catch (e) {
    console.error(e);
    return [];
  }
}

export async function createGrantAction(prevState: ActionState, formData: FormData): Promise<ActionState> {
  try {
    const sessionUser = await requireAuthAndAbility('create', 'GrantApplication');
    const systemUserId = sessionUser.id ? Number(sessionUser.id) : 1;
    const actorEmail = sessionUser.email || "system";

    const dataObj = Object.fromEntries(formData.entries());
    const parsed = grantSchema.safeParse(dataObj);
    
    if (!parsed.success) {
      return { errors: parsed.error.flatten().fieldErrors };
    }

    const resultId = await prisma.$transaction(async (tx) => {
      const newGrant = await tx.grantApplication.create({
        data: {
          organisationId: parsed.data.organisationId,
          grantType: parsed.data.grantType,
          finYear: parsed.data.finYear,
          amountRequested: parsed.data.amountRequested,
          status: "DRAFT",
          createdBy: systemUserId
        }
      });
      await createAuditLog(newGrant.id, "GrantApplication", "CREATE", actorEmail, newGrant);
      return newGrant.id;
    });

    revalidatePath("/grants");
    return { success: true, id: resultId };

  } catch (error: any) {
    return { message: error.message || "Failed to create grant application." };
  }
}

// XState Decoupled Workflow action
export async function transitionGrantStatusAction(id: number, event: 'SUBMIT' | 'COMMENCE_REVIEW' | 'APPROVE' | 'REJECT' | 'APPEAL' | 'REVERT_TO_DRAFT') {
    try {
        const sessionUser = await requireAuthAndAbility('update', 'GrantApplication');
        const systemUserId = sessionUser.id ? Number(sessionUser.id) : 1;
        const actorEmail = sessionUser.email || "system";
        
        const grantCurrent = await prisma.grantApplication.findUnique({ where: { id }});
        if (!grantCurrent) throw new Error("Entity Not Found");

        // Mathematically calculate next state (Throws natively if illegal)
        const newStatus = getNextStatus(grantCurrent.status, event);
    
        const resultId = await prisma.$transaction(async (tx) => {
          const grant = await tx.grantApplication.update({
            where: { id },
            data: {
              status: newStatus,
              modifiedBy: systemUserId
            }
          });
          await createAuditLog(grant.id, "GrantApplication", "STATUS_CHANGE", actorEmail, grant);
          return grant.id;
        });
    
        revalidatePath(`/grants/${id}`);
        revalidatePath(`/grants`);
        return { success: true };
    
      } catch (error: any) {
        return { success: false, message: error.message || "Failed to update grant status." };
      }
}
