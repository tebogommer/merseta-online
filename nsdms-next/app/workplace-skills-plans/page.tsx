import Link from "next/link";
import { fetchWSPs } from "./_actions/workflow";
import { Plus, ArrowRight, FileCheck } from "lucide-react";
import { auth } from "@/auth";
import { defineAbilitiesFor } from "@/lib/abilities";
import { columns } from "./_components/columns";
import { DataTable } from "./_components/data-table";

export default async function WSPGridPage() {
  const wsps = await fetchWSPs();
  const session = await auth();
  const ability = defineAbilitiesFor(session?.user);

  const canCreate = ability.can('create', 'WorkplaceSkillsPlan');

  return (
    <main className="min-h-screen bg-muted p-8">
      <div className="max-w-7xl mx-auto space-y-6">
        
        {/* Header Bar */}
        <div className="flex justify-between items-center bg-card text-card-foreground p-6 rounded-lg shadow-sm border border-border">
          <div>
            <h1 className="text-3xl font-bold flex items-center gap-3 text-foreground">
              <FileCheck className="text-primary" /> 
              Workplace Skills Plans
            </h1>
            <p className="text-muted-foreground mt-1">Manage and track WSP submissions and approvals.</p>
          </div>
          {canCreate && (
             <Link 
               href="/workplace-skills-plans/new" 
               className="flex items-center gap-2 bg-merseta-highlight text-merseta-darkest border border-merseta-dark hover:bg-merseta-light px-4 py-2 rounded-md transition-colors shadow-sm font-semibold text-sm"
             >
               <Plus className="w-5 h-5" /> Submit WSP
             </Link>
          )}
        </div>

        {/* Master Data Grid (The List) powered by Shadcn TanStack */}
        <DataTable columns={columns} data={wsps as any} />

      </div>
    </main>
  );
}
