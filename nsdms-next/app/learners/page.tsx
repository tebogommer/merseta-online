import { Suspense } from "react";
import { fetchLearners } from "./_actions/workflow";
import { LearnerGrid } from "./_components/learner-grid";
import { Users, LayoutDashboard } from "lucide-react";
import Link from "next/link";
import { Card, CardContent } from "@/components/ui/card";

export default async function LearnersPage() {
  const learners = await fetchLearners();

  return (
    <main className="min-h-screen bg-gray-50/50 p-6 md:p-8">
      {/* Header section matching Master-Detail and Theming */}
      <div className="max-w-6xl mx-auto space-y-6">
        <div className="flex items-center justify-between bg-white px-6 py-4 rounded-xl border border-gray-100 shadow-sm border-l-4 border-l-primary/60">
          <div className="flex items-center gap-3">
            <div className="p-2 bg-primary/10 rounded-lg">
              <Users className="w-5 h-5 text-primary" />
            </div>
            <div>
              <h1 className="text-xl font-bold tracking-tight text-gray-900">Learner Registry</h1>
              <p className="text-sm text-gray-500 font-medium hidden sm:block">
                Manage global learner lifecycle and enrollments
              </p>
            </div>
          </div>
          
          <div className="flex items-center gap-3">
            <Link 
              href="/"
              className="text-gray-500 hover:text-primary transition-colors text-sm font-semibold uppercase flex items-center gap-2 px-3 py-2"
            >
              <LayoutDashboard className="w-4 h-4" />
              <span>Dashboard</span>
            </Link>
          </div>
        </div>

        {/* Global Key Metrics summary (similar to Metric-over-List) */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <Card className="border-gray-100 shadow-sm">
            <CardContent className="p-6">
              <p className="text-sm font-medium text-gray-500">Total Registered</p>
              <h3 className="text-2xl font-bold text-gray-900 mt-2">{learners.length}</h3>
            </CardContent>
          </Card>
          <Card className="border-gray-100 shadow-sm">
            <CardContent className="p-6">
              <p className="text-sm font-medium text-gray-500">Active Enrollments</p>
              <h3 className="text-2xl font-bold text-primary mt-2">
                {learners.reduce((acc, l) => acc + l.enrollments.filter((e: any) => e.status !== 'Terminated' && e.status !== 'Completed').length, 0)}
              </h3>
            </CardContent>
          </Card>
          <Card className="border-gray-100 shadow-sm">
            <CardContent className="p-6">
              <p className="text-sm font-medium text-gray-500">Completed (EISA Passed)</p>
              <h3 className="text-2xl font-bold text-green-600 mt-2">
                {learners.reduce((acc, l) => acc + l.enrollments.filter((e: any) => e.status === 'Completed').length, 0)}
              </h3>
            </CardContent>
          </Card>
        </div>

        {/* The Grid Component */}
        <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
          <Suspense fallback={<div className="h-40 flex items-center justify-center font-semibold text-primary animate-pulse">Loading Registry Data...</div>}>
            <LearnerGrid learners={learners} providerId={0} />
          </Suspense>
        </div>
      </div>
    </main>
  );
}
