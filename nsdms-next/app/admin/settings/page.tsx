import { PrismaClient } from "@prisma/client";
import { Suspense } from "react";
import { SettingsActionsBridge } from "./settings-actions";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Loader2 } from "lucide-react";

const prisma = new PrismaClient();

/**
 * The Smart Shell: Server Component responsible for initial data fetching.
 * Uses Suspense boundaries for non-blocking UI.
 */
export default async function SettingsPage() {
  // 1. Fetch Hosting Company record (Singleton assume ID 1 for now)
  const hostingCompany = await prisma.hostingCompany.findFirst() || await prisma.hostingCompany.create({
    data: {
      companyName: "merSETA",
      companyRegNumber: "2000/000000/00",
      vatNumber: "4000000000",
      incomeTaxNumber: "9000000000",
    }
  });

  // 2. Fetch Global System Settings
  const systemSettings = await prisma.systemSetting.findMany();

  // 3. Ensure defaults if empty
  if (systemSettings.length === 0) {
    await prisma.systemSetting.createMany({
      data: [
        { key: "CURRENT_FIN_YEAR", value: "2024", description: "The active reporting financial year." },
        { key: "MAINTENANCE_MODE", value: "false", description: "If true, blocks all non-admin access." },
        { key: "DOC_SERVER_URL", value: "http://localhost:3000/docs", description: "Internal document server path." },
      ]
    });
  }

  return (
    <div className="p-8 space-y-6 max-w-5xl mx-auto">
      <header className="flex items-center justify-between">
        <h1 className="text-3xl font-extrabold tracking-tight">System Configuration</h1>
      </header>

      <Suspense fallback={<Loader2 className="animate-spin" />}>
        <SettingsActionsBridge 
          hostingCompany={hostingCompany} 
          systemSettings={systemSettings.length > 0 ? systemSettings : await prisma.systemSetting.findMany()} 
        />
      </Suspense>
    </div>
  );
}
