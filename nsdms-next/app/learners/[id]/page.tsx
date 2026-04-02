import { Suspense } from "react";
import { notFound } from "next/navigation";
import Link from "next/link";
import { ArrowLeft, User, Folder, History, FileText, Calendar, Network } from "lucide-react";
import { fetchLearnerById } from "./../_actions/workflow";
import { LearnerActionsBridge } from "./learner-actions";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { WorkflowHistory } from "@/components/workflow-history";

interface PageProps {
  params: Promise<{ id: string }>;
}

export default async function LearnerDetailPage({ params }: PageProps) {
  const { id } = await params;
  const learnerId = parseInt(id, 10);
  
  if (isNaN(learnerId)) {
    notFound();
  }

  const learner = await fetchLearnerById(learnerId);

  if (!learner) {
    notFound();
  }

  const identityDoc = learner.rsaIdNumber || learner.passportNumber || "N/A";

  return (
    <main className="min-h-screen bg-gray-50/50 p-6 md:p-8">
      <div className="max-w-6xl mx-auto space-y-6">
        
        {/* Top Bar - Smart Shell Sticky Header */}
        <div className="sticky top-0 z-10 flex flex-col md:flex-row md:items-center justify-between bg-white px-6 py-4 rounded-xl border border-gray-200 shadow-sm">
          <div className="flex items-center gap-4 mb-4 md:mb-0">
            <Link 
              href="/learners" 
              className="text-gray-500 hover:text-gray-900 transition-colors p-2 hover:bg-gray-100 rounded-lg"
            >
              <ArrowLeft className="w-5 h-5" />
            </Link>
            <div>
              <div className="flex items-center gap-2">
                <h1 className="text-xl font-bold text-gray-900">{learner.user?.name || "Unknown Learner"}</h1>
                <span className="px-2 py-0.5 rounded text-xs font-semibold bg-blue-50 text-blue-700 border border-blue-200">
                  {identityDoc}
                </span>
              </div>
              <div className="text-sm text-gray-500 mt-1 flex items-center gap-4">
                <span className="flex items-center gap-1"><Calendar className="w-4 h-4"/> DOB: {learner.dateOfBirth ? new Date(learner.dateOfBirth).toLocaleDateString() : "N/A"}</span>
                <span className="flex items-center gap-1"><User className="w-4 h-4"/> Equity: {learner.equityStatus || "N/A"}</span>
              </div>
            </div>
          </div>

          <div className="flex items-center gap-3">
            <Suspense fallback={<div className="h-10 w-32 bg-gray-100 animate-pulse rounded" />}>
              <LearnerActionsBridge learnerId={learner.id} />
            </Suspense>
          </div>
        </div>

        {/* Tabbed Content Area */}
        <Tabs defaultValue="general" className="w-full">
          <TabsList className="bg-white border text-gray-600 h-12 p-1 rounded-lg w-full md:w-auto shadow-sm grid grid-cols-3 md:flex md:mb-6 mb-4">
            <TabsTrigger value="general" className="data-[state=active]:bg-primary data-[state=active]:text-white rounded-md px-4 py-2 font-medium">
              <User className="w-4 h-4 mr-2" /> General
            </TabsTrigger>
            <TabsTrigger value="enrollments" className="data-[state=active]:bg-primary data-[state=active]:text-white rounded-md px-4 py-2 font-medium">
              <Folder className="w-4 h-4 mr-2" /> Enrollments
            </TabsTrigger>
            <TabsTrigger value="audit" className="data-[state=active]:bg-primary data-[state=active]:text-white rounded-md px-4 py-2 font-medium">
              <History className="w-4 h-4 mr-2" /> Audit
            </TabsTrigger>
          </TabsList>

          {/* General Metadata Tab */}
          <TabsContent value="general" className="mt-0 outline-none">
            <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
              <h3 className="text-lg font-bold text-gray-900 flex items-center gap-2 border-b pb-3 mb-6">
                <FileText className="w-5 h-5 text-gray-400" /> Learner Identity & Profile
              </h3>
              
              <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                <div className="space-y-6">
                  <div>
                    <label className="text-sm font-semibold text-gray-500 uppercase tracking-wide">First Name</label>
                    <p className="font-medium text-gray-900 mt-1">{learner.user?.name?.split(' ')[0] || "N/A"}</p>
                  </div>
                  <div>
                    <label className="text-sm font-semibold text-gray-500 uppercase tracking-wide">Last Name</label>
                    <p className="font-medium text-gray-900 mt-1">{learner.user?.name?.split(' ').slice(1).join(' ') || "N/A"}</p>
                  </div>
                  <div>
                    <label className="text-sm font-semibold text-gray-500 uppercase tracking-wide">Email</label>
                    <p className="font-medium text-gray-900 mt-1">{learner.user?.email || "N/A"}</p>
                  </div>
                </div>
                <div className="space-y-6">
                  <div>
                    <label className="text-sm font-semibold text-gray-500 uppercase tracking-wide">RSA ID Number</label>
                    <p className="font-medium text-gray-900 mt-1">{learner.rsaIdNumber || "N/A"}</p>
                  </div>
                  <div>
                    <label className="text-sm font-semibold text-gray-500 uppercase tracking-wide">Passport Number</label>
                    <p className="font-medium text-gray-900 mt-1">{learner.passportNumber || "N/A"}</p>
                  </div>
                  <div>
                    <label className="text-sm font-semibold text-gray-500 uppercase tracking-wide">Nationality</label>
                    <p className="font-medium text-gray-900 mt-1">{learner.nationality || "N/A"}</p>
                  </div>
                </div>
              </div>
            </div>
          </TabsContent>

          {/* Enrollments Tab */}
          <TabsContent value="enrollments" className="mt-0 outline-none space-y-6">
            <div className="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
                <div className="p-6 border-b border-gray-100 flex items-center justify-between">
                    <h3 className="text-lg font-bold text-gray-900 flex items-center gap-2">
                        <Network className="w-5 h-5 text-gray-400" /> Active Enrollments
                    </h3>
                </div>
                <div className="overflow-x-auto">
                    <table className="w-full text-left text-sm">
                        <thead className="bg-gray-50 text-xs uppercase font-semibold text-gray-500 tracking-wider">
                            <tr>
                                <th className="px-6 py-4">Provider</th>
                                <th className="px-6 py-4 hidden md:table-cell">Employer</th>
                                <th className="px-6 py-4">Intervention</th>
                                <th className="px-6 py-4">Status</th>
                                <th className="px-6 py-4 text-right">Action</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-gray-100">
                            {learner.enrollments.map((e: any) => (
                                <tr key={e.id} className="hover:bg-gray-50">
                                    <td className="px-6 py-4 font-medium text-gray-900">
                                        {e.provider?.tradingName || e.provider?.companyName || `ID: ${e.providerId}`}
                                    </td>
                                    <td className="px-6 py-4 text-gray-500 hidden md:table-cell">
                                        {e.employer?.companyName || "N/A"}
                                    </td>
                                    <td className="px-6 py-4 text-gray-500">
                                        {e.interventionType?.name || `Type: ${e.interventionTypeId}`}
                                    </td>
                                    <td className="px-6 py-4">
                                        <span className={`px-2 py-1 rounded text-xs font-semibold
                                            ${e.status === 'Completed' ? 'bg-green-50 text-green-700 border border-green-200' :
                                              e.status === 'Terminated' ? 'bg-red-50 text-red-700 border border-red-200' :
                                              'bg-yellow-50 text-yellow-700 border border-yellow-200'
                                            }`}
                                        >
                                            {e.status}
                                        </span>
                                    </td>
                                    <td className="px-6 py-4 text-right">
                                        <Link href={`#enrollment-${e.id}`} className="text-primary hover:text-primary/80 font-semibold uppercase text-xs">
                                            Manage
                                        </Link>
                                    </td>
                                </tr>
                            ))}
                            {learner.enrollments.length === 0 && (
                                <tr>
                                    <td colSpan={5} className="px-6 py-8 text-center text-gray-500">
                                        No enrollments found. Use "New Enrollment" to link this learner to a provider.
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </div>
          </TabsContent>

          {/* Audit Tab */}
          <TabsContent value="audit" className="mt-0 outline-none">
            <Suspense fallback={<div className="h-40 flex items-center justify-center font-semibold text-primary animate-pulse">Loading Audit Logs...</div>}>
              <WorkflowHistory entityId={learner.id} entityName="Learner" />
            </Suspense>
          </TabsContent>
        </Tabs>

      </div>
    </main>
  );
}
