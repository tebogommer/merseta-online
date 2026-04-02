import { notFound } from "next/navigation";
import { Suspense } from "react";
import { getProviderById } from "../_actions/workflow";
import { ProviderDetailsView } from "./provider-details";
import { Loader2 } from "lucide-react";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";


/**
 * The Smart Shell: Server Component for Provider Details.
 * Fetches the provider and all related records (Site Visits, Docs).
 */
export default async function ProviderDetailPage({ 
  params 
}: { 
  params: { id: string } 
}) {
  const providerId = parseInt(params.id);
  const provider = await getProviderById(providerId);

  if (!provider) {
    notFound();
  }

  return (
    <div className="p-8 space-y-6 max-w-7xl mx-auto">
      <header className="flex items-center justify-between border-b pb-6">
        <div className="space-y-1">
          <h1 className="text-4xl font-extrabold tracking-tight">
            {provider.organisation?.organisationName || "SDP Details"}
          </h1>
          <p className="text-muted-foreground font-mono">
            Accreditation Number: {provider.accreditationNumber || "PENDING"}
          </p>
        </div>
      </header>

      <Suspense fallback={<div className="flex justify-center p-12"><Loader2 className="animate-spin h-8 w-8" /></div>}>
        <ProviderDetailsView provider={provider} />
      </Suspense>
    </div>
  );
}
