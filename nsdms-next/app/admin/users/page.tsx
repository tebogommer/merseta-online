import Link from "next/link";
import { Plus, Shield } from "lucide-react";
import { getUsers } from "./_actions/user-controller";
import { columns } from "./_components/columns";
import { DataTable } from "./_components/data-table";

export default async function UsersPage() {
  const users = await getUsers();

  return (
    <main className="p-8">
      <div className="max-w-7xl mx-auto space-y-6">
        
        {/* Header Section */}
        <div className="flex justify-between items-center bg-card text-card-foreground p-6 rounded-xl shadow-sm border border-border dark:bg-slate-950/50 dark:border-slate-800 transition-all">
          <div>
            <h1 className="text-3xl font-bold flex items-center gap-3 text-foreground tracking-tight">
              <Shield className="text-primary h-8 w-8" /> 
              Access Management
            </h1>
            <p className="text-muted-foreground mt-1">Manage system authentication accounts and RBAC role assignments.</p>
          </div>
          <Link 
            href="/admin/users/new" 
            className="flex items-center gap-2 bg-merseta shadow text-white hover:bg-merseta-dark px-5 py-2.5 rounded-lg transition-all font-semibold text-sm active:scale-95"
          >
            <Plus className="w-5 h-5" /> Add User Account
          </Link>
        </div>

        {/* Data Table */}
        <div className="animate-in fade-in slide-in-from-bottom-4 duration-500">
           <DataTable columns={columns} data={users as any} />
        </div>

      </div>
    </main>
  );
}
