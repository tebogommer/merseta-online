import Link from "next/link";
import { Plus, Users } from "lucide-react";
import { getPersons } from "./_actions/person-controller";
import { columns } from "./_components/columns";
import { DataTable } from "./_components/data-table";

export default async function PersonsPage() {
  const persons = await getPersons();

  return (
    <main className="p-8">
      <div className="max-w-7xl mx-auto space-y-6">
        
        {/* Header Section */}
        <div className="flex justify-between items-center bg-card text-card-foreground p-6 rounded-xl shadow-sm border border-border dark:bg-slate-950 dark:border-slate-800">
          <div>
            <h1 className="text-3xl font-bold flex items-center gap-3 text-foreground transition-all">
              <Users className="text-primary h-8 w-8" /> 
              People Directory
            </h1>
            <p className="text-muted-foreground mt-1">Centralized demographic records for stakeholders, learners, and employees.</p>
          </div>
          <Link 
            href="/admin/persons/new" 
            className="flex items-center gap-2 bg-merseta shadow text-white hover:bg-merseta-dark px-5 py-2.5 rounded-lg transition-all font-semibold text-sm active:scale-95"
          >
            <Plus className="w-5 h-5" /> Add Person
          </Link>
        </div>

        {/* Data Table */}
        <div className="animate-in fade-in slide-in-from-bottom-4 duration-500">
           <DataTable columns={columns} data={persons as any} />
        </div>

      </div>
    </main>
  );
}
