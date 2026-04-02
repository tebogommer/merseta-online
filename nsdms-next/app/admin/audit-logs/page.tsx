import { Suspense } from "react";
import { notFound } from "next/navigation";
import { auth } from "@/auth";
import { defineAbilitiesFor } from "@/lib/abilities";
import { fetchSystemAudits } from "./_actions/workflow";
import { AuditLogGrid } from "./_components/audit-log-grid";
import { Activity } from "lucide-react";

export default async function AuditLogDashboard() {
  const session = await auth();
  
  // 1. Strict Security CASL Gate
  const ability = defineAbilitiesFor(session?.user);
  if (!ability.can('manage', 'AuditLog')) {
    notFound(); // Bounce unprivileged STANDARD users to 404
  }

  // 2. Fetch Parity Lineage
  const logs = await fetchSystemAudits();

  return (
    <main className="min-h-screen bg-gray-50 p-8">
      <div className="max-w-6xl mx-auto space-y-6">
        
        {/* Header Block */}
        <div className="bg-white p-6 rounded-lg shadow-sm border border-gray-200 flex justify-between items-center">
            <div>
              <h1 className="text-2xl font-bold text-gray-900 flex items-center gap-3">
                  <Activity className="w-6 h-6 text-primary" />
                  System Audit Matrix
              </h1>
              <p className="text-sm text-gray-500 mt-1">
                  Global repository of strict Double-Write parity snapshots. Highest security clearance required.
              </p>
            </div>
            
            <div className="text-right">
                <span className="block text-xs font-semibold text-gray-400 uppercase tracking-wider mb-1">Clearance</span>
                <span className="bg-red-100 text-red-800 px-3 py-1 rounded text-xs font-bold uppercase">ADMIN ONLY</span>
            </div>
        </div>

        {/* Audit Grid Injection */}
        <Suspense fallback={<div className="p-8 text-center text-gray-500">Decrypting Lineage Vectors...</div>}>
            <AuditLogGrid logs={logs} />
        </Suspense>

      </div>
    </main>
  );
}
