import { fetchLookups } from "../_actions/lookup-controller";
import Link from "next/link";
import { Plus, Edit2 } from "lucide-react";

import { LOOKUP_MODELS } from "../_config/lookup-registry";

export default async function LookupGridPage({
  params
}: {
  params: Promise<{ lookupType: string }>
}) {
  const { lookupType } = await params;
  
  // Find name from registry
  const match = LOOKUP_MODELS.find(m => m.route === lookupType || m.route === lookupType.slice(0, -1));
  const title = match?.name || lookupType;

  const data = await fetchLookups(lookupType);

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <div>
          <h1 className="text-2xl font-bold tracking-tight">{title}</h1>
          <p className="text-sm text-muted-foreground">Manage definition data for {title.toLowerCase()}.</p>
        </div>
        <Link 
          href={`/admin/lookups/${lookupType}/new`} 
          className="flex items-center gap-2 bg-merseta shadow text-white hover:bg-merseta-dark px-4 py-2 rounded-md transition-colors font-semibold text-sm"
        >
          <Plus className="w-4 h-4" /> Add New
        </Link>
      </div>

      <div className="bg-card text-card-foreground dark:bg-slate-950 rounded-lg shadow-sm border border-border dark:border-slate-800 overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200 dark:divide-slate-800">
          <thead className="bg-muted dark:bg-slate-900">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase">Code</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase">Name</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase">Status</th>
              <th className="px-6 py-3 text-right text-xs font-medium text-muted-foreground uppercase">Manage</th>
            </tr>
          </thead>
          <tbody className="bg-card text-card-foreground dark:bg-slate-950 divide-y divide-gray-200 dark:divide-slate-800">
            {data.map((row: any) => (
              <tr key={row.id} className="hover:bg-muted dark:hover:bg-slate-900 transition-colors">
                <td className="px-6 py-4 whitespace-nowrap text-sm font-mono text-foreground dark:text-gray-300">
                  {row.code}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm font-semibold text-foreground dark:text-gray-100">
                  {row.name}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm">
                  <span className={`px-2.5 py-0.5 rounded-full text-xs font-medium ${row.active ? 'bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400' : 'bg-red-100 text-red-800 dark:bg-red-900/30 dark:text-red-400'}`}>
                    {row.active ? 'Active' : 'Inactive'}
                  </span>
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-right">
                  <Link 
                    href={`/admin/lookups/${lookupType}/${row.id}`} 
                    className="inline-flex items-center gap-1 text-muted-foreground hover:text-merseta dark:text-muted-foreground dark:hover:text-merseta transition-colors"
                  >
                    <Edit2 className="w-4 h-4 cursor-pointer" /> <span className="font-semibold text-xs">Edit</span>
                  </Link>
                </td>
              </tr>
            ))}
            {data.length === 0 && (
              <tr>
                <td colSpan={4} className="px-6 py-12 text-center text-muted-foreground text-sm">
                  No records found. Click 'Add New' to begin.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
