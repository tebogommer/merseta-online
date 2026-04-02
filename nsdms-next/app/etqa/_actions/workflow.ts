"use server";

import { PrismaClient } from "@prisma/client";
import { revalidatePath } from "next/cache";
import { createActor } from "xstate";
import { approvalMachine } from "../_machines/approvalMachine";
import { auth } from "@/auth";

const prisma = new PrismaClient();

export async function fetchApprovals() {
  try {
    return await prisma.workplaceApproval.findMany({
      include: {
        trainingProvider: {
            include: { organisation: true }
        }
      },
      orderBy: { createdAt: 'desc' }
    });
  } catch (err) {
    console.error(err);
    return [];
  }
}

export async function processApprovalTransition(
  approvalId: number,
  event: Parameters<typeof approvalMachine.transition>[1]
) {
  const session = await auth();
  const userId = session?.user?.id ? Number(session.user.id) : 0;

  try {
    const result = await prisma.$transaction(async (tx) => {
      // 1. Re-hydrate the state from DB
      const approval = await tx.workplaceApproval.findUniqueOrThrow({
        where: { id: approvalId },
      });

      // 2. Instantiate isolated XState Actor starting from the DB state
      const actor = createActor(approvalMachine, {
        state: approvalMachine.resolveState({ value: approval.status, context: { approvalId, providerId: approval.trainingProviderId } }),
      });
      
      actor.start();

      // 3. Fire requested transition
      actor.send(event);
      const snapshot = actor.getSnapshot();

      // 4. Verify the state actually moved deterministically
      if (snapshot.value === approval.status) {
          throw new Error("XState rejected transition. Invalid trajectory payload.");
      }

      // 5. Hardcode Double-Write Persistence
      const updatedApproval = await tx.workplaceApproval.update({
        where: { id: approvalId },
        data: { 
            status: typeof snapshot.value === 'string' ? snapshot.value : JSON.stringify(snapshot.value),
            modifiedBy: userId
        },
      });

      await tx.auditLog.create({
        data: {
          recordId: approvalId,
          entityName: "WorkplaceApproval",
          actionName: `XSTATE_TRANSITION_${event.type}`,
          actor: userId.toString(),
          snapshot: JSON.stringify({
              fromState: approval.status,
              toState: snapshot.value,
              event: event.type
          })
        }
      });

      return updatedApproval;
    });

    revalidatePath("/etqa/approvals");
    return { success: true, status: result.status };

  } catch (error: any) {
    console.error("XState Persistence Exception:", error);
    return { success: false, message: error.message || "Failed to commit state transition" };
  }
}
