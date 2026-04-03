import { fetchLookupById } from "../../_actions/lookup-controller";
import { LookupForm } from "../../_components/lookup-form";
import Link from "next/link";
import { ChevronLeft } from "lucide-react";

import { LOOKUP_MODELS } from "../../_config/lookup-registry";

export default async function LookupDetailPage({
  params
}: {
  params: Promise<{ lookupType: string, id: string }>
}) {
  const { lookupType, id } = await params;
  const isNew = id === 'new';

  let initialData = null;
  if (!isNew) {
    initialData = await fetchLookupById(lookupType, parseInt(id));
  }

  // Find name from registry
  const match = LOOKUP_MODELS.find(m => m.route === lookupType || m.route === lookupType.slice(0, -1));
  const title = match?.name || lookupType;

  return (
    <div className="space-y-6 max-w-4xl">
      <div className="flex items-center gap-4 text-sm font-semibold">
         <Link href={`/admin/lookups/${lookupType}`} className="flex items-center text-muted-foreground hover:text-merseta transition-colors">
           <ChevronLeft className="w-4 h-4" /> Back to {title}
         </Link>
      </div>

      <LookupForm lookupType={lookupType} initialData={initialData} />
    </div>
  );
}
