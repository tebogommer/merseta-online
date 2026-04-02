"use server";

import { PrismaClient } from "@prisma/client";
import { revalidatePath } from "next/cache";
import { createWspSchema } from "../_validators/wsp-schema";
import { auth } from "@/auth";

const prisma = new PrismaClient();

export type ActionState = {
  errors?: Record<string, string[]>;
  message?: string;
  success?: boolean;
};

export async function fetchWsps(organisationId: number) {
  try {
    return await prisma.workplaceSkillsPlan.findMany({
      where: { organisationId },
      orderBy: { finYear: 'desc' },
      select: {
          id: true,
          finYear: true,
          status: true,
          totalPayroll: true,
          totalTrainingCosts: true,
          percentagePayrollSpent: true,
          createdAt: true
      }
    });
  } catch (error) {
    console.error("fetchWsps Error:", error);
    return [];
  }
}

export async function createWspAction(
  prevState: ActionState,
  formData: FormData
): Promise<ActionState> {
  const session = await auth();
  const userId = session?.user?.id ? Number(session.user.id) : 0;

  // 1. Data Normalization
  const rawData = {
    organisationId: Number(formData.get("organisationId")),
    finYear: Number(formData.get("finYear")),
    numberOfEmployees: Number(formData.get("numberOfEmployees")),
    numberOfBeneficiaries: Number(formData.get("numberOfBeneficiaries") || 0),
    totalPayroll: parseFloat(formData.get("totalPayroll") as string),
    totalTrainingCosts: parseFloat(formData.get("totalTrainingCosts") as string),
    projectDescription: formData.get("projectDescription")?.toString() || "",
    interventions: formData.get("interventions")?.toString() || ""
  };

  // 2. Strict Zod Validation (WSP-2 Extraction)
  const validatedFields = createWspSchema.safeParse(rawData);

  if (!validatedFields.success) {
    return {
      errors: validatedFields.error.flatten().fieldErrors,
      message: "Please correct the financial and target metric errors before submission."
    };
  }

  const data = validatedFields.data;
  const derivedPercentage = Math.round((data.totalTrainingCosts / data.totalPayroll) * 100 * 100) / 100;

  try {
    // 3. Double Write & Audit Enforced Context (WSP-3)
    await prisma.$transaction(async (tx) => {
      
      const newWsp = await tx.workplaceSkillsPlan.create({
        data: {
          organisationId: data.organisationId,
          finYear: data.finYear,
          numberOfEmployees: data.numberOfEmployees,
          numberOfBeneficiaries: data.numberOfBeneficiaries,
          totalPayroll: data.totalPayroll,
          totalTrainingCosts: data.totalTrainingCosts,
          percentagePayrollSpent: derivedPercentage,
          projectDescription: data.projectDescription,
          interventions: data.interventions,
          
          // Temporal / Defaults
          status: "Draft",
          createdBy: userId,
          modifiedBy: userId,
        }
      });

      // Issue Mandatory Audit Lineage Trail Drop
      await tx.auditLog.create({
        data: {
          recordId: newWsp.id,
          actionName: "CREATE_WSP",
          entityName: "WorkplaceSkillsPlan",
          actor: userId.toString(),
          snapshot: JSON.stringify({ 
             context: "New Financial Allocation",
             payload: data 
          })
        }
      });
      
    });

    revalidatePath(`/organisations/${data.organisationId}`);
    
    return {
      success: true,
      message: "Workplace Skills Plan explicitly registered and placed in Draft status."
    };

  } catch (error) {
    console.error("WSP Registration Error:", error);
    return {
      success: false,
      message: "A database integrity error occurred while capturing the WSP record."
    };
  }
}
