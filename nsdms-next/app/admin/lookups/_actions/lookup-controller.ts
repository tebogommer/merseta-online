"use server";

import { PrismaClient } from "@prisma/client";
import { auth } from "@/auth";
import { revalidatePath } from "next/cache";

const prisma = new PrismaClient();

import { LOOKUP_MODELS } from "../_config/lookup-registry";

const getModelConfig = (lookupType: string) => {
  // Normalize if it ends with an extra 's' that shouldn't be there because Prisma model is singular
  // the route is e.g. "category-types" mapping to "CategoryType", wait I already have 'route' in my array
  // We can just find by route. Wait, the route generated was 'category-type' but the URL might be plural?
  // Let me check. The user URL parameter is 'lookupType'. They visited /admin/lookups/category-types.
  // My generator made 'route: category-type'. I should handle optional 's' at the end or precisely map.
  let exactMatch = LOOKUP_MODELS.find(m => m.route === lookupType);
  if (!exactMatch && lookupType.endsWith('s')) {
    exactMatch = LOOKUP_MODELS.find(m => m.route === lookupType.slice(0, -1));
  }
  if (!exactMatch) {
    throw new Error(`Invalid lookup type: ${lookupType}`);
  }
  return exactMatch;
};

const getModelName = (lookupType: string): any => {
  const config = getModelConfig(lookupType);
  // Prisma property is camelCase (e.g. categoryType)
  return config.model.charAt(0).toLowerCase() + config.model.slice(1);
};

const getEntityName = (lookupType: string): string => {
  return getModelConfig(lookupType).model;
};

export async function fetchLookups(lookupType: string) {
  const model = getModelName(lookupType);
  const data = await (prisma as any)[model].findMany({
    orderBy: { name: 'asc' }
  });
  return data;
}

export async function fetchLookupById(lookupType: string, id: number) {
  const model = getModelName(lookupType);
  const data = await (prisma as any)[model].findUnique({
    where: { id }
  });
  return data;
}

export async function saveLookup(lookupType: string, isNew: boolean, id: number | null, payload: any) {
  const session = await auth();
  const userId = session?.user && (session.user as any).id ? parseInt((session.user as any).id) : 0;
  const actor = session?.user?.email || "SYSTEM";
  const model = getModelName(lookupType);
  const entityName = getEntityName(lookupType);
  
  let result;
  
  // Clean payload
  const cleanPayload = {
    name: payload.name,
    code: payload.code,
    description: payload.description,
    active: payload.active === 'true' || payload.active === true,
  };

  if (isNew) {
    result = await (prisma as any)[model].create({
      data: {
        ...cleanPayload,
        createdBy: userId,
      }
    });

    await prisma.auditLog.create({
      data: {
        recordId: result.id,
        entityName: entityName,
        actionName: "CREATE",
        actor: actor,
        snapshot: JSON.stringify({ after: result }),
        createdBy: userId
      }
    });
  } else {
    if (!id) throw new Error("ID required for update");
    
    const before = await (prisma as any)[model].findUnique({ where: { id } });
    
    result = await (prisma as any)[model].update({
      where: { id },
      data: {
        ...cleanPayload,
        modifiedBy: userId,
      }
    });

    await prisma.auditLog.create({
      data: {
        recordId: id,
        entityName: entityName,
        actionName: "UPDATE",
        actor: actor,
        snapshot: JSON.stringify({ before, after: result }),
        createdBy: userId
      }
    });
  }

  revalidatePath(`/admin/lookups/${lookupType}`);
  revalidatePath(`/admin/lookups/${lookupType}/${result.id}`);
  
  return { success: true, id: result.id };
}

export async function deleteLookup(lookupType: string, id: number) {
  const session = await auth();
  const userId = session?.user && (session.user as any).id ? parseInt((session.user as any).id) : 0;
  const actor = session?.user?.email || "SYSTEM";
  const model = getModelName(lookupType);
  const entityName = getEntityName(lookupType);

  const before = await (prisma as any)[model].findUnique({ where: { id } });
  if (!before) throw new Error("Record not found");

  await (prisma as any)[model].delete({ where: { id } });

  await prisma.auditLog.create({
    data: {
      recordId: id,
      entityName: entityName,
      actionName: "DELETE",
      actor: actor,
      snapshot: JSON.stringify({ before }),
      createdBy: userId
    }
  });

  revalidatePath(`/admin/lookups/${lookupType}`);
  return { success: true };
}
