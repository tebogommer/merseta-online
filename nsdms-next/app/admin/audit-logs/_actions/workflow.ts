"use server";

import { PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

export async function fetchSystemAudits() {
  try {
    return await prisma.auditLog.findMany({
      orderBy: { createdAt: 'desc' },
      take: 100, // Safe bounds for initial dashboard fetch
      select: {
          id: true,
          recordId: true,
          entityName: true,
          actionName: true,
          actor: true,
          snapshot: true,
          createdAt: true
      }
    });
  } catch (error) {
    console.error("fetchSystemAudits Error:", error);
    return [];
  }
}
