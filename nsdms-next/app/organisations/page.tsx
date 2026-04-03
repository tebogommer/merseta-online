import Link from "next/link";
import { fetchOrganisations } from "./_actions/workflow";
import { Plus, ArrowRight } from "lucide-react";
import { Building2 } from "lucide-react";
import { auth } from "@/auth";
import { defineAbilitiesFor } from "@/lib/abilities";
import { PDFDownloadButton } from "./_components/pdf-download-button";
import { columns } from "./_components/columns";
import { DataTable } from "./_components/data-table";
export default async function OrganisationsGridPage() {
  const orgs = await fetchOrganisations();
  const session = await auth();
  const ability = defineAbilitiesFor(session?.user);

  const canCreate = ability.can('create', 'Organisation');

  return (
    <main className="min-h-screen bg-transparent p-8">
      <div className="max-w-7xl mx-auto space-y-6">
        
        {/* Header Bar */}
        <div className="flex justify-between items-center bg-card text-card-foreground p-6 rounded-lg shadow-sm border border-border">
          <div>
            <h1 className="text-3xl font-bold flex items-center gap-3 text-foreground">
              <Building2 className="text-primary" /> 
              Organisations
            </h1>
            <p className="text-muted-foreground mt-1">Manage stakeholder SDL linkages and ETQA data.</p>
          </div>
          {canCreate && (
             <Link 
               href="/organisations/new" 
               className="flex items-center gap-2 bg-merseta-highlight text-merseta-darkest border border-merseta-dark hover:bg-merseta-light px-4 py-2 rounded-md transition-colors shadow-sm font-semibold text-sm"
             >
               <Plus className="w-5 h-5" /> Add Organisation
             </Link>
          )}
        </div>

        {/* Master Data Grid (The List) powered by Shadcn TanStack */}
        <DataTable columns={columns} data={orgs as any} />

      </div>
    </main>
  );
}
