import { fetchLookupById } from "../../_actions/lookup-controller";
import { LookupForm } from "../../_components/lookup-form";
import Link from "next/link";
import { ChevronLeft } from "lucide-react";

export default async function LookupDetailPage({
  params
}: {
  params: { lookupType: string, id: string }
}) {
  const { lookupType, id } = params;
  const isNew = id === 'new';

  let initialData = null;
  if (!isNew) {
    initialData = await fetchLookupById(lookupType, parseInt(id));
  }

  const title = lookupType
    .split('-')
    .map(w => w.charAt(0).toUpperCase() + w.slice(1))
    .join(' ');

  return (
    <div className="space-y-6 max-w-4xl">
      <div className="flex items-center gap-4 text-sm font-semibold">
         <Link href={`/admin/lookups/${lookupType}`} className="flex items-center text-slate-500 hover:text-merseta transition-colors">
           <ChevronLeft className="w-4 h-4" /> Back to {title}
         </Link>
      </div>

      <LookupForm lookupType={lookupType} initialData={initialData} />
    </div>
  );
}
