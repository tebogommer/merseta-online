import { Suspense } from "react";
import { getOrganisation } from "../_actions/workflow";
import { fetchLearners } from "@/app/learners/_actions/workflow";
import { fetchWsps } from "@/app/workplace-skills-plans/_actions/workflow";
import { auth } from "@/auth";
import { defineAbilitiesFor } from "@/lib/abilities";
import { OrganisationDetails } from "./organisation-details";
import Link from "next/link";
import { ArrowLeft } from "lucide-react";

export default async function OrganisationSmartShell({ params }: { params: Promise<{ id: string }> }) {
  const { id } = await params;
  const isNew = id === "new";
  
  // Data Fetching
  const org = isNew ? null : await getOrganisation(Number(id));
  const learners = isNew ? [] : await fetchLearners(Number(id));
  const wsps = isNew ? [] : await fetchWsps(Number(id));

  // Authorization Mappings
  const session = await auth();
  const ability = defineAbilitiesFor(session?.user);

  const canDelete = ability.can('delete', 'Organisation');
  const canUpdate = ability.can('update', 'Organisation');
  const canCreate = ability.can('create', 'Organisation');

  return (
    <main className="min-h-screen bg-gray-50 p-8">
      
      {/* Universal Action Bar / Navigation Anchor */}
      <div className="max-w-4xl mx-auto mb-6">
        <div className="flex justify-between items-center bg-white p-4 pb-[72px] rounded-lg shadow-sm border border-gray-200">
           <Link href="/organisations" className="text-gray-500 hover:text-gray-900 inline-flex items-center gap-2">
             <ArrowLeft className="w-4 h-4" /> Back to Grid
           </Link>
        </div>
      </div>

      {/* The Suspended UI View */}
      <Suspense fallback={<div className="max-w-4xl mx-auto text-center text-gray-500 p-12">Loading Organisation Profile...</div>}>
         <OrganisationDetails 
            isNew={isNew} 
            org={org} 
            learners={learners}
            wsps={wsps}
            canCreate={canCreate} 
            canUpdate={canUpdate} 
            canDelete={canDelete} 
         />
      </Suspense>
      
    </main>
  );
}
