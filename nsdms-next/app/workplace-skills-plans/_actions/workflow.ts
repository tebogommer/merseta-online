"use server";

import { PrismaClient } from "@prisma/client";
import { auth } from "@/auth";
import { defineAbilitiesFor } from "@/lib/abilities";
import { revalidatePath } from "next/cache";

const prisma = new PrismaClient();

async function requireAuthAndAbility(action: any, subject: any) {
  const session = await auth();
  if (!session?.user) throw new Error("Unauthorized");
  const ability = defineAbilitiesFor(session.user);
  if (ability.cannot(action, subject)) throw new Error("Forbidden");
  return session.user;
}

export async function fetchWSPs(orgId?: number) {
  const user = await requireAuthAndAbility('read', 'WorkplaceSkillsPlan');
  const isAdmin = user.role === 'ADMIN';
  
  const baseWhere = orgId ? { organisationId: orgId } : {};

  if (isAdmin) {
    return await prisma.workplaceSkillsPlan.findMany({
      where: baseWhere,
      include: { organisation: true },
      orderBy: { createdAt: 'desc' }
    });
  } else {
    return await prisma.workplaceSkillsPlan.findMany({
      where: { ...baseWhere, createdBy: user.id ? Number(user.id) : 0 },
      include: { organisation: true },
      orderBy: { createdAt: 'desc' }
    });
  }
}

export async function getWSP(id: number) {
  await requireAuthAndAbility('read', 'WorkplaceSkillsPlan');
  return await prisma.workplaceSkillsPlan.findUnique({
    where: { id },
    include: { organisation: true }
  });
}

export type ActionState = {
  success?: boolean;
  message?: string;
  errors?: Record<string, string[]>;
  id?: number;
};

import { createWspSchema } from "../_validators/wsp-schema";

export async function createWspAction(prevState: ActionState, formData: FormData): Promise<ActionState> {
  try {
    const user = await requireAuthAndAbility('create', 'WorkplaceSkillsPlan');
    
    // Parse form data strings to numbers where appropriate
    const dataObj = Object.fromEntries(formData.entries());
    const parsedData = createWspSchema.safeParse({
      organisationId: dataObj.organisationId ? Number(dataObj.organisationId) : 0,
      finYear: dataObj.finYear ? Number(dataObj.finYear) : 0,
      numberOfEmployees: dataObj.numberOfEmployees ? Number(dataObj.numberOfEmployees) : 0,
      numberOfBeneficiaries: dataObj.numberOfBeneficiaries ? Number(dataObj.numberOfBeneficiaries) : 0,
      totalPayroll: dataObj.totalPayroll ? Number(dataObj.totalPayroll) : 0,
      totalTrainingCosts: dataObj.totalTrainingCosts ? Number(dataObj.totalTrainingCosts) : 0,
      projectDescription: dataObj.projectDescription,
      interventions: dataObj.interventions
    });

    if (!parsedData.success) {
      return { 
        errors: parsedData.error.flatten().fieldErrors,
        message: "Please correct the financial and target metric errors before submission."
      };
    }

    const val = parsedData.data;

    const wsp = await prisma.workplaceSkillsPlan.create({
      data: {
        organisationId: val.organisationId,
        finYear: val.finYear,
        numberOfEmployees: val.numberOfEmployees,
        totalPayroll: val.totalPayroll,
        totalTrainingCosts: val.totalTrainingCosts,
        status: "Draft",
        createdBy: user.id ? Number(user.id) : 0
      }
    });

    // Double-write to audit log
    await prisma.auditLog.create({
      data: {
        recordId: wsp.id,
        entityName: "WorkplaceSkillsPlan",
        actionName: "CREATE",
        actor: user.email || "system",
        snapshot: JSON.stringify(wsp),
        createdBy: user.id ? Number(user.id) : 0
      }
    });

    revalidatePath("/workplace-skills-plans");
    return { success: true, id: wsp.id, message: "Successfully created WSP" };
  } catch (error: any) {
    console.error("Failed to create WSP", error);
    return { success: false, message: error.message || "Failed to create WSP" };
  }
}


export async function deleteWSPAction(id: number) {
  const user = await requireAuthAndAbility('delete', 'WorkplaceSkillsPlan');
  const before = await prisma.workplaceSkillsPlan.findUnique({ where: { id } });
  
  await prisma.workplaceSkillsPlan.delete({ where: { id } });
  
  await prisma.auditLog.create({
    data: {
      recordId: id,
      entityName: "WorkplaceSkillsPlan",
      actionName: "DELETE",
      actor: user.email || "system",
      snapshot: JSON.stringify({ before }),
      createdBy: user.id ? Number(user.id) : 0
    }
  });

  revalidatePath("/workplace-skills-plans");
  return { success: true };
}
