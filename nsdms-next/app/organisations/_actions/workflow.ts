"use server";

import { revalidatePath } from "next/cache";
import { PrismaClient } from "@prisma/client";
import { auth } from "@/auth";
import { defineAbilitiesFor } from "@/lib/abilities";

const prisma = new PrismaClient();

async function requireAuthAndAbility(action: 'manage' | 'create' | 'read' | 'update' | 'delete', subject: 'Organisation' | 'AuditLog' | 'User' | 'all') {
  const session = await auth();
  if (!session?.user) throw new Error("Unauthorized");
  
  const ability = defineAbilitiesFor(session.user);
  if (ability.cannot(action, subject)) {
    throw new Error(`Forbidden: You do not have permission to ${action} ${subject}`);
  }
  return session.user;
}

// The "Double Write" Audit Log policy
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

import { z } from "zod";

const formSchema = z.object({
  organisationName: z.string().min(1, "Organisation Name is required"),
  tradingName: z.string().optional(),
  sdlNumber: z.string().optional(),
  companyRegistrationNumber: z.string().optional(),
  email: z.string().email("Invalid email address").optional().or(z.literal("")),
  telNumber: z.string().optional(),
  address: z.string().min(1, "Address Not Configured/Set. Contact Support!"),
  bankAccountNumber: z.string()
    .regex(/^[0-9]*$/, "Bank account number can not contain the following: characters / letters, underscores, hyphens and spaces. Only numbers are accepted.")
    .optional(),
  bankBranchCode: z.string().optional(),
  sicCode: z.string().min(1, "SIC Code / Chamber not configured correctly"),
});

export type ActionState = {
  success?: boolean;
  errors?: Record<string, string[]>;
  message?: string;
  id?: number;
};

export async function fetchOrganisations() {
  await requireAuthAndAbility('read', 'Organisation');
  try {
    return await prisma.organisation.findMany({
      orderBy: { createdAt: 'desc' }
    });
  } catch (e) {
    return [];
  }
}

export async function getOrganisation(id: number) {
  await requireAuthAndAbility('read', 'Organisation');
  return await prisma.organisation.findUnique({ where: { id } });
}

export async function createOrganisationAction(prevState: ActionState, formData: FormData): Promise<ActionState> {
  try {
    const user = await requireAuthAndAbility('create', 'Organisation');

    const dataObj = Object.fromEntries(formData.entries());
    const parsed = formSchema.safeParse(dataObj);

    if (!parsed.success) {
      return { errors: parsed.error.flatten().fieldErrors };
    }

    const data = parsed.data;

    if (data.sdlNumber) {
      const existingSdl = await prisma.organisation.findUnique({ where: { sdlNumber: data.sdlNumber }});
      if (existingSdl) {
        return { errors: { sdlNumber: ["Levy number already in use, please provide a different levy number or contact support!"] } };
      }
    }

    if (data.companyRegistrationNumber) {
      const existingReg = await prisma.organisation.findUnique({ where: { companyRegistrationNumber: data.companyRegistrationNumber }});
      if (existingReg) {
        return { errors: { companyRegistrationNumber: ["Company Registration Number aready exist in merSETA database"] } };
      }
    }

    const cleanData = {
      ...data,
      companyRegistrationNumber: data.companyRegistrationNumber || null,
      sdlNumber: data.sdlNumber || null,
      email: data.email || null,
      telNumber: data.telNumber || null,
      bankAccountNumber: data.bankAccountNumber || null,
      bankBranchCode: data.bankBranchCode || null,
      tradingName: data.tradingName || null,
      sicCode: data.sicCode || "99999",
    };

    const org = await prisma.organisation.create({ 
      data: { ...cleanData, createdBy: (user.id ? Number(user.id) : null) as any } 
    });
    await createAuditLog(org.id, "Organisation", "CREATE", (org.createdBy)?.toString() || user.email || "system", org);
    
    revalidatePath("/organisations");
    return { success: true, id: org.id };
  } catch (error: any) {
    return { message: error.message || "Failed to create organisation." };
  }
}

export async function updateOrganisationAction(id: number, prevState: ActionState, formData: FormData): Promise<ActionState> {
  try {
    const user = await requireAuthAndAbility('update', 'Organisation');

    const dataObj = Object.fromEntries(formData.entries());
    const parsed = formSchema.safeParse(dataObj);

    if (!parsed.success) {
      return { errors: parsed.error.flatten().fieldErrors };
    }

    const data = parsed.data;

    if (data.sdlNumber) {
      const existingSdl = await prisma.organisation.findUnique({ where: { sdlNumber: data.sdlNumber }});
      if (existingSdl && existingSdl.id !== id) {
        return { errors: { sdlNumber: ["Levy number already in use, please provide a different levy number or contact support!"] } };
      }
    }

    if (data.companyRegistrationNumber) {
      const existingReg = await prisma.organisation.findUnique({ where: { companyRegistrationNumber: data.companyRegistrationNumber }});
      if (existingReg && existingReg.id !== id) {
        return { errors: { companyRegistrationNumber: ["Company Registration Number aready exist in merSETA database"] } };
      }
    }

    const orgBefore = await prisma.organisation.findUnique({ where: { id } });
    
    const cleanData = {
      ...data,
      companyRegistrationNumber: data.companyRegistrationNumber || null,
      sdlNumber: data.sdlNumber || null,
      email: data.email || null,
      telNumber: data.telNumber || null,
      bankAccountNumber: data.bankAccountNumber || null,
      bankBranchCode: data.bankBranchCode || null,
      tradingName: data.tradingName || null,
      sicCode: data.sicCode || "99999",
    };

    const orgAfter = await prisma.organisation.update({ 
      where: { id }, 
      data: { ...cleanData, modifiedBy: (user.id ? Number(user.id) : null) as any } 
    });
    
    await createAuditLog(id, "Organisation", "UPDATE", (orgAfter.modifiedBy)?.toString() || user.email || "system", { before: orgBefore, after: orgAfter });

    revalidatePath("/organisations");
    revalidatePath(`/organisations/${id}`);
    return { success: true };
  } catch (error: any) {
    return { message: error.message || "Failed to update organisation." };
  }
}

import { organisationMachine } from "@/lib/machines/organisation-machine";
import { CompanyStatus } from "@/types/enums";

export async function updateOrganisationStatusAction(id: number, event: string): Promise<ActionState> {
  try {
    const user = await requireAuthAndAbility('update', 'Organisation');
    
    // 1. Fetch current record
    const org = await prisma.organisation.findUnique({ where: { id } });
    // @ts-ignore - Prisma type may lag on status property
    if (!org || !org.status) throw new Error("Organisation or status not found");

    // 2. Resolve next state via XState v5 patterns
    const transition = organisationMachine.transition(
      // @ts-ignore - Manual state string mapping for xstate v5
      { value: org.status }, 
      { type: event } as any,
      undefined as any
    );
    
    // @ts-ignore - In XState v5, value is the state string
    if (transition.value === org.status) {
      return { 
        success: false, 
        message: `Invalid transition: '${event}' is not allowed from status '${org.status}'` 
      };
    }

    // 3. Update DB
    const updated = await prisma.organisation.update({
      where: { id },
      data: { 
        // @ts-ignore
        status: transition.value as string,
        modifiedBy: (user.id ? Number(user.id) : null) as any
      }
    });

    // 4. Audit Log with 5 required arguments
    // @ts-ignore
    await createAuditLog(id, "Organisation", `STATUS_CHANGE_${event}`, user.email || "system", { 
      before: org.status, 
      after: updated.status 
    });

    revalidatePath("/organisations");
    revalidatePath(`/organisations/${id}`);
    
    return { success: true, message: `Status updated to ${updated.status}` };
  } catch (error: any) {
    return { success: false, message: error.message || "Failed to update status." };
  }
}

export async function deleteOrganisationAction(id: number) {
  const user = await requireAuthAndAbility('delete', 'Organisation');

  const orgBefore = await prisma.organisation.findUnique({ where: { id } });
  if (!orgBefore) throw new Error("Not found");

  await prisma.organisation.delete({ where: { id } });
  await createAuditLog(id, "Organisation", "DELETE", user.email || user.id?.toString() || "system", { before: orgBefore, after: null });

  revalidatePath("/organisations");
  return { success: true };
}
