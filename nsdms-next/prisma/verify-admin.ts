import { PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

async function verify() {
  console.log("🔍 Verifying Internal Admin Seeding...");
  const hc = await prisma.hostingCompany.findFirst();
  const settings = await prisma.systemSetting.findMany();
  
  if (hc) {
    console.log(`✅ Hosting Company: ${hc.companyName}`);
  } else {
    console.log("❌ No Hosting Company found.");
  }
  
  if (settings.length > 0) {
    console.log(`✅ System Settings: Found ${settings.length} records.`);
    settings.forEach(s => console.log(`   - ${s.key}: ${s.value}`));
  } else {
    console.log("❌ No System Settings found.");
  }
}

verify().finally(() => prisma.$disconnect());
