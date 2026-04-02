"use server";

import { PrismaClient } from "@prisma/client";
import { revalidatePath } from "next/cache";

const prisma = new PrismaClient();
import { auth } from "@/auth";
import { createProviderSchema } from "../_validators/provider-schema";

export type ActionState = {
  errors?: Record<string, string[]>;
  message?: string;
  success?: boolean;
};

export async function createProviderAction(
  prevState: ActionState,
  formData: FormData
): Promise<ActionState> {
  const session = await auth();
  const userId = session?.user?.id ? Number(session.user.id) : 0;

  const rawData = {
    organisationId: Number(formData.get("organisationId")),
    accreditationNumber: formData.get("accreditationNumber")?.toString() || "",
    providerTypeId: Number(formData.get("providerTypeId")),
  };

  const validatedFields = createProviderSchema.safeParse(rawData);

  if (!validatedFields.success) {
    return {
      errors: validatedFields.error.flatten().fieldErrors,
      message: "Please correct the accreditation validation errors before submission."
    };
  }

  const data = validatedFields.data;

  try {
    const existing = await prisma.trainingProvider.findUnique({
        where: { accreditationNumber: data.accreditationNumber }
    });

    if (existing) {
        return {
            errors: { accreditationNumber: ["This accreditation number is already registered to another provider."] },
            message: "Accreditation number must be unique."
        };
    }

    // Double Write Audit Policy
    await prisma.$transaction(async (tx) => {
      const newProvider = await tx.trainingProvider.create({
        data: {
          organisationId: data.organisationId,
          accreditationNumber: data.accreditationNumber,
          providerTypeId: data.providerTypeId,
          status: "Pending",
          createdBy: userId,
          modifiedBy: userId,
        }
      });

      await tx.auditLog.create({
        data: {
          recordId: newProvider.id,
          actionName: "CREATE_PROVIDER",
          entityName: "TrainingProvider",
          actor: userId.toString(),
          snapshot: JSON.stringify({ 
             context: "New Provider Registration",
             payload: data 
          })
        }
      });
    });

    revalidatePath("/providers");
    
    return {
      success: true,
      message: "Provider Registration explicitly registered."
    };

  } catch (error) {
    console.error("Provider Registration Error:", error);
    return {
      success: false,
      message: "A database integrity error occurred while capturing the provider record."
    };
  }
}

/**
 * Fetches all training providers with organization details.
 */
export async function fetchProviders() {
  return await prisma.trainingProvider.findMany({
    include: {
      organisation: true,
      providerType: true,
      providerClass: true,
    },
    orderBy: {
      createdAt: "desc",
    },
  });
}

/**
 * Fetches a single provider with all relations for the detail view.
 */
export async function getProviderById(id: number) {
  return await prisma.trainingProvider.findUnique({
    where: { id },
    include: {
      organisation: true,
      providerType: true,
      providerClass: true,
      siteVisits: {
        orderBy: { visitDate: "desc" },
      },
      documents: true,
    },
  });
}

/**
 * Updates provider accreditation details.
 */
export async function updateProviderAccreditation(id: number, data: {
  accreditationNumber: string;
  startDate: Date;
  expiryDate: Date;
  status: string;
}) {
  const result = await prisma.trainingProvider.update({
    where: { id },
    data,
  });

  revalidatePath("/providers");
  revalidatePath(`/providers/${id}`);
  return result;
}

/**
 * Logs a new site visit for a provider.
 */
export async function logSiteVisit(providerId: number, data: {
  visitDate: Date;
  recommendation: string;
  auditorId: number;
}) {
  const result = await prisma.siteVisit.create({
    data: {
      trainingProviderId: providerId,
      ...data,
    },
  });

  revalidatePath(`/providers/${providerId}`);
  return result;
}
