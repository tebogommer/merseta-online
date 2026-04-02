import { PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

async function seed() {
  console.log("🌱 Seeding Internal Admin Environment...");
  
  // 1. Hosting Company
  await prisma.hostingCompany.upsert({
    where: { id: 1 },
    update: {},
    create: {
      id: 1,
      companyName: "merSETA",
      companyRegNumber: "2000/000256/08",
      vatNumber: "4000000000",
      incomeTaxNumber: "9000000000",
      title: "Manufacturing, Engineering and Related Services SETA",
      logo: "/images/merseta-logo.png",
    }
  });

  // 2. System Settings
  const settings = [
    { key: "CURRENT_FIN_YEAR", value: "2024", description: "The active reporting financial year used for Grant Submissions." },
    { key: "MAINTENANCE_MODE", value: "false", description: "Blocks all non-admin access to the portal when true." },
    { key: "AUTO_APPROVAL_THRESHOLD", value: "50000", description: "The ZAR threshold for automated grant approval." },
    { key: "AUDIT_RETENTION_DAYS", value: "2555", description: "Number of days (7 years) to retain detailed audit logs." },
  ];

  for (const s of settings) {
    await prisma.systemSetting.upsert({
      where: { key: s.key },
      update: {},
      create: s,
    });
  }

  console.log("✅ Admin seeding complete.");
}

seed().finally(() => prisma.$disconnect());
