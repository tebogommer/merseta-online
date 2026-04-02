import { fetchLookups } from "../_actions/lookup-controller";
import Link from "next/link";
import { Plus, Edit2 } from "lucide-react";

export default async function LookupGridPage({
  params
}: {
  params: { lookupType: string }
}) {
  const { lookupType } = params;
  
  // Format title (e.g. "category-types" -> "Category Types")
  const title = lookupType
    .split('-')
    .map(w => w.charAt(0).toUpperCase() + w.slice(1))
    .join(' ');

  const data = await fetchLookups(lookupType);

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <div>
          <h1 className="text-2xl font-bold tracking-tight">{title}</h1>
          <p className="text-sm text-gray-500">Manage definition data for {title.toLowerCase()}.</p>
        </div>
        <Link 
          href={`/admin/lookups/${lookupType}/new`} 
          className="flex items-center gap-2 bg-merseta shadow text-white hover:bg-merseta-dark px-4 py-2 rounded-md transition-colors font-semibold text-sm"
        >
          <Plus className="w-4 h-4" /> Add New
        </Link>
      </div>

      <div className="bg-white dark:bg-slate-950 rounded-lg shadow-sm border border-gray-200 dark:border-slate-800 overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200 dark:divide-slate-800">
          <thead className="bg-gray-50 dark:bg-slate-900">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Code</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Name</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Status</th>
              <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase">Manage</th>
            </tr>
          </thead>
          <tbody className="bg-white dark:bg-slate-950 divide-y divide-gray-200 dark:divide-slate-800">
            {data.map((row: any) => (
              <tr key={row.id} className="hover:bg-slate-50 dark:hover:bg-slate-900 transition-colors">
                <td className="px-6 py-4 whitespace-nowrap text-sm font-mono text-gray-900 dark:text-gray-300">
                  {row.code}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm font-semibold text-gray-900 dark:text-gray-100">
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
                    className="inline-flex items-center gap-1 text-slate-600 hover:text-merseta dark:text-slate-400 dark:hover:text-merseta transition-colors"
                  >
                    <Edit2 className="w-4 h-4 cursor-pointer" /> <span className="font-semibold text-xs">Edit</span>
                  </Link>
                </td>
              </tr>
            ))}
            {data.length === 0 && (
              <tr>
                <td colSpan={4} className="px-6 py-12 text-center text-gray-500 text-sm">
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
