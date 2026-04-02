import { Suspense } from "react";
import Link from "next/link";
import { ArrowLeft, Save, Building, ShieldCheck, Download } from "lucide-react";
import { PrismaClient } from "@prisma/client";
import { notFound } from "next/navigation";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { SignoffWizard } from "../_components/signoff-wizard";

const prisma = new PrismaClient();

export default async function WspDetailPage({ params }: { params: Promise<{ id: string }> }) {
  const resolvedParams = await params;
  const idStr = resolvedParams.id;
  const id = parseInt(idStr, 10);

  if (isNaN(id)) {
    notFound();
  }

  const wsp = await prisma.workplaceSkillsPlan.findUnique({
    where: { id },
    include: {
      organisation: true,
    }
  });

  if (!wsp) {
    notFound();
  }

  return (
    <main className="min-h-screen bg-gray-50 p-8">
      {/* Top Banner Context Area */}
      <div className="max-w-6xl mx-auto mb-6">
        <div className="flex justify-between items-center bg-white p-4 rounded-lg shadow-sm border border-gray-200">
           <div className="flex items-center gap-4">
               <Link href={`/organisations/${wsp.organisationId}`} className="text-gray-500 hover:text-gray-900 border-r pr-4">
                 <ArrowLeft className="w-5 h-5" />
               </Link>
               <div>
                   <h1 className="text-xl font-bold text-gray-900 tracking-tight">Workplace Skills Plan</h1>
                   <div className="text-sm font-medium text-muted-foreground flex gap-2 items-center">
                      <Building className="w-4 h-4"/>
                      {wsp.organisation.organisationName} • FinYear: {wsp.finYear}
                   </div>
               </div>
           </div>
           <div className="flex items-center gap-2">
              <span className="px-3 py-1 bg-gray-100 text-gray-800 font-bold uppercase text-xs rounded shadow-sm tracking-wider">
                 {wsp.status}
              </span>
           </div>
        </div>
      </div>

      <div className="max-w-6xl mx-auto">
        <Tabs defaultValue="overview" className="w-full">
          <TabsList className="w-full justify-start border-b rounded-none bg-transparent max-w-none h-12 p-0 mb-6 font-semibold">
             <TabsTrigger value="overview" className="data-[state=active]:border-b-2 data-[state=active]:border-primary data-[state=active]:shadow-none rounded-none w-48 uppercase text-xs tracking-wider">
               Overview & Financials
             </TabsTrigger>
             <TabsTrigger value="signoff" className="data-[state=active]:border-b-2 data-[state=active]:border-primary data-[state=active]:shadow-none rounded-none w-48 uppercase text-xs tracking-wider">
               Compliance & Signoff
             </TabsTrigger>
          </TabsList>

          <TabsContent value="overview" className="animate-in fade-in duration-500">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6 relative">
                 <div className="bg-white rounded-lg shadow-sm border p-6 space-y-4">
                     <h3 className="text-lg font-bold border-b pb-2">Financial Snapshot</h3>
                     <div className="grid grid-cols-2 gap-4">
                         <div>
                            <span className="text-xs text-muted-foreground font-semibold uppercase">Total Payroll</span>
                            <p className="text-xl font-bold">R{wsp.totalPayroll?.toLocaleString()}</p>
                         </div>
                         <div>
                            <span className="text-xs text-muted-foreground font-semibold uppercase">Estimated Training</span>
                            <p className="text-xl font-bold">R{wsp.totalTrainingCosts?.toLocaleString()}</p>
                         </div>
                         <div className="col-span-2 pt-2">
                            <span className="text-xs text-muted-foreground font-semibold uppercase">Payroll Percentage Allocation</span>
                            <p className="text-2xl font-bold text-merseta">{wsp.percentagePayrollSpent}%</p>
                         </div>
                     </div>
                 </div>

                 <div className="bg-white rounded-lg shadow-sm border p-6 space-y-4">
                     <h3 className="text-lg font-bold border-b pb-2">Strategic Interventions</h3>
                     <div>
                        <span className="text-xs text-muted-foreground font-semibold uppercase block mb-1">Project Description</span>
                        <p className="text-sm font-medium">{wsp.projectDescription || "No description provided."}</p>
                     </div>
                     <div className="pt-2">
                        <span className="text-xs text-muted-foreground font-semibold uppercase block mb-1">Focus Areas / Interventions</span>
                        <p className="text-sm font-medium">{wsp.interventions || "No specific interventions listed."}</p>
                     </div>
                 </div>
              </div>
          </TabsContent>

          <TabsContent value="signoff" className="animate-in fade-in duration-500">
             <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                 <div>
                    <SignoffWizard wspId={idStr} />
                 </div>
                 <div className="space-y-4">
                    <div className="bg-blue-50 border border-blue-100 p-6 rounded-lg space-y-3">
                       <h3 className="font-bold text-blue-900 flex items-center gap-2">
                          <ShieldCheck className="w-5 h-5"/> Authorization Requirements
                       </h3>
                       <p className="text-sm text-blue-800 leading-relaxed">
                          The document uploaded must contain wet or verified digital signatures from the primary Skills Development Facilitator (SDF) and an authorized representative of the organization.
                       </p>
                       <button className="inline-flex mt-2 items-center gap-2 text-xs font-bold text-blue-700 bg-blue-100 hover:bg-blue-200 px-3 py-2 rounded transition-colors uppercase">
                          <Download className="w-4 h-4"/> Download Signoff Template
                       </button>
                    </div>
                 </div>
             </div>
          </TabsContent>
        </Tabs>
      </div>
    </main>
  );
}
