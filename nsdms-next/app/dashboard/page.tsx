import { Suspense } from "react";
import DashboardView from "./dashboard-view";
import { fetchDashboardStats, fetchRecentAuditLogs } from "./_actions/dashboard";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Skeleton } from "@/components/ui/skeleton";
import DashboardActions from "./dashboard-actions";

export default async function DashboardPage() {
  const stats = await fetchDashboardStats();
  const auditLogs = await fetchRecentAuditLogs();

  return (
    <div className="flex-1 space-y-4 p-8 pt-6">
      <div className="flex items-center justify-between space-y-2">
        <h2 className="text-3xl font-bold tracking-tight">Dashboard</h2>
        <div className="flex items-center space-x-2">
          {/* Dashboard Actions Bridge for Workflow/Triggers */}
          <Suspense fallback={<Skeleton className="h-10 w-32" />}>
            <DashboardActions />
          </Suspense>
        </div>
      </div>
      
      {/* Dashboard View Component for Layout & Visualization */}
      <Suspense fallback={<DashboardLoadingSkeleton />}>
        <DashboardView stats={stats} auditLogs={auditLogs} />
      </Suspense>
    </div>
  );
}

function DashboardLoadingSkeleton() {
  return (
    <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
      {Array(4).fill(0).map((_, i) => (
        <Card key={i}>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <Skeleton className="h-4 w-24" />
          </CardHeader>
          <CardContent>
            <Skeleton className="h-8 w-12" />
          </CardContent>
        </Card>
      ))}
    </div>
  );
}
