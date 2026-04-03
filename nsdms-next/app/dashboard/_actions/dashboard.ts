"use server";

import { PrismaClient } from "@prisma/client";
import { auth } from "@/auth";
import { defineAbilitiesFor } from "@/lib/abilities";

const prisma = new PrismaClient();

async function requireAuth() {
  const session = await auth();
  if (!session?.user) throw new Error("Unauthorized");
  return session.user;
}

export type DashboardStats = {
  totalOrganisations: number;
  totalProviders: number;
  totalLearners: number;
  pendingWSPs: number;
  pendingAccreditations: number;
  activeGrants: number;
};

export async function fetchDashboardStats(): Promise<DashboardStats> {
  const user = await requireAuth();
  const ability = defineAbilitiesFor(user);

  // If NOT admin, we should conceptually filter by user linkage.
  // For the MVP, we'll implement role-based global vs contextual counts.
  
  const isAdmin = user.role === 'ADMIN' || ability.can('manage', 'all');

  if (isAdmin) {
    // Admin sees global totals
    const [orgs, providers, learners, wsp, accreditations, grants] = await Promise.all([
      prisma.organisation.count(),
      prisma.trainingProvider.count(),
      prisma.learner.count(),
      prisma.workplaceSkillsPlan.count({ where: { status: { contains: 'Awaiting' } } }),
      prisma.trainingProvider.count({ where: { status: { contains: 'Pending' } } }),
      prisma.grantApplication.count()
    ]);

    return {
      totalOrganisations: orgs,
      totalProviders: providers,
      totalLearners: learners,
      pendingWSPs: wsp,
      pendingAccreditations: accreditations,
      activeGrants: grants,
    };
  } else {
    // Standard user sees their counts (linked via createdBy or specific user links if implemented)
    // For now, we filter by createdBy as a proxy for ownership in MVP
    const userId = user.id ? Number(user.id) : 0;

    const [orgs, providers, learners, wsp, accreditations, grants] = await Promise.all([
      prisma.organisation.count({ where: { createdBy: userId } }),
      prisma.trainingProvider.count({ where: { createdBy: userId } }),
      prisma.learner.count({ where: { createdBy: userId } }),
      prisma.workplaceSkillsPlan.count({ where: { createdBy: userId, status: { contains: 'Awaiting' } } }),
      prisma.trainingProvider.count({ where: { createdBy: userId, status: { contains: 'Pending' } } }),
      prisma.grantApplication.count({ where: { createdBy: userId } })
    ]);

    return {
      totalOrganisations: orgs,
      totalProviders: providers,
      totalLearners: learners,
      pendingWSPs: wsp,
      pendingAccreditations: accreditations,
      activeGrants: grants,
    };
  }
}

export async function fetchRecentAuditLogs() {
  await requireAuth(); // Only logged in users can see activity
  return await prisma.auditLog.findMany({
    take: 10,
    orderBy: { createdAt: 'desc' }
  });
}
