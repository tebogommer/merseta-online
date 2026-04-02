"use server";

import { PrismaClient } from "@prisma/client";
import { revalidatePath } from "next/cache";

const prisma = new PrismaClient();

/**
 * Updates the Hosting Company profile.
 * Performs a Double-Write to the audit_log.
 */
export async function updateHostingCompany(data: {
  id: number;
  companyName: string;
  companyRegNumber: string;
  vatNumber?: string;
  incomeTaxNumber?: string;
  theme?: string;
  title?: string;
  logo?: string;
}) {
  const { id, ...updateData } = data;

  return await prisma.$transaction(async (tx) => {
    // 1. Get snapshot before update
    const before = await tx.hostingCompany.findUnique({ where: { id } });

    // 2. Perform Update
    const after = await tx.hostingCompany.update({
      where: { id },
      data: updateData,
    });

    // 3. Audit Log
    await tx.auditLog.create({
      data: {
        recordId: id,
        entityName: "HostingCompany",
        actionName: "UPDATE",
        actor: "Admin", // TODO: Get from session
        snapshot: JSON.stringify({ before, after }),
      },
    });

    revalidatePath("/admin/settings");
    return { success: true, data: after };
  });
}

/**
 * Updates a system setting by key.
 */
export async function updateSystemSetting(key: string, value: string) {
  return await prisma.$transaction(async (tx) => {
    const before = await tx.systemSetting.findUnique({ where: { key } });

    const after = await tx.systemSetting.update({
      where: { key },
      data: { value },
    });

    await tx.auditLog.create({
      data: {
        recordId: after.id,
        entityName: "SystemSetting",
        actionName: "UPDATE",
        actor: "Admin",
        snapshot: JSON.stringify({ before, after }),
      },
    });

    revalidatePath("/admin/settings");
    return { success: true, data: after };
  });
}
