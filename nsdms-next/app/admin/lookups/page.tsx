import { LOOKUP_MODELS } from "./_config/lookup-registry";
import Link from "next/link";
import { Database, FolderTree } from "lucide-react";

export default function LookupDashboardPage() {
  // Group models by category
  const categories = LOOKUP_MODELS.reduce((acc, curr) => {
    if (!acc[curr.category]) acc[curr.category] = [];
    acc[curr.category].push(curr);
    return acc;
  }, {} as Record<string, typeof LOOKUP_MODELS>);

  return (
    <div className="space-y-8 pb-12">
      <div>
        <h1 className="text-3xl font-bold tracking-tight flex items-center gap-3 text-foreground">
          <Database className="text-primary w-8 h-8" />
          System Lookups Master Registry
        </h1>
        <p className="text-muted-foreground mt-2 max-w-3xl">
          Manage system-wide dropdown matrices, taxonomy definitions, and metadata registries.
          Select a category below to access the dynamic CRUD interface for the respective lookup domain.
        </p>
      </div>

      <div className="grid grid-cols-1 xl:grid-cols-2 gap-8">
        {Object.entries(categories).map(([category, models]) => (
          <div key={category} className="bg-card text-card-foreground border border-border shadow-sm rounded-xl overflow-hidden">
             <div className="bg-muted px-6 py-4 border-b border-border flex items-center gap-3">
               <FolderTree className="w-5 h-5 text-primary" />
               <h2 className="font-bold text-lg">{category} Lookups</h2>
               <span className="ml-auto bg-primary/10 text-primary text-xs font-bold px-2 py-1 rounded-full">
                 {models.length}
               </span>
             </div>
             
             <div className="p-6">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-3 max-h-[400px] overflow-y-auto pr-2 custom-scrollbar">
                  {models.sort((a, b) => a.name.localeCompare(b.name)).map(model => (
                    <Link 
                      key={model.route} 
                      href={`/admin/lookups/${model.route}s`}
                      className="group flex items-center justify-between p-3 border border-border rounded hover:bg-muted transition-colors"
                    >
                      <span className="text-sm font-semibold group-hover:text-primary transition-colors line-clamp-1">{model.name}</span>
                      <span className="text-xs text-muted-foreground bg-background px-2 py-0.5 rounded border border-border opacity-0 group-hover:opacity-100 transition-opacity">Edit</span>
                    </Link>
                  ))}
                </div>
             </div>
          </div>
        ))}
      </div>
    </div>
  );
}
