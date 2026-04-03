"use client";

import { useTransition, useState } from "react";
import { useRouter } from "next/navigation";
import { createProviderAction, ActionState } from "../_actions/workflow";
import { Building2, Save, ArrowLeft } from "lucide-react";
import Link from "next/link";
import { toast } from "sonner";

export default function NewProviderPage() {
  const router = useRouter();
  const [isPending, startTransition] = useTransition();
  const [state, setState] = useState<ActionState>({});

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    
    startTransition(async () => {
      const result = await createProviderAction({}, formData);
      setState(result);

      if (result.success) {
        toast.success("Provider registered successfully.");
        router.push("/providers");
      }
    });
  };

  const FieldError = ({ field }: { field: string }) => {
    if (!state.errors?.[field]) return null;
    return (
      <div className="text-red-500 text-xs font-semibold mt-1">
        {state.errors[field].join(", ")}
      </div>
    );
  };

  return (
    <main className="min-h-screen bg-muted p-8">
      <div className="max-w-4xl mx-auto mb-6">
        <div className="flex justify-between items-center bg-card text-card-foreground p-4 rounded-lg shadow-sm border border-border">
          <Link href={`/providers`} className="text-muted-foreground hover:text-foreground inline-flex items-center gap-2">
            <ArrowLeft className="w-4 h-4" /> Cancel Registration
          </Link>
          <div className="font-bold text-lg text-primary">Provider Registration</div>
        </div>
      </div>

      <div className="max-w-4xl mx-auto space-y-6">
        {state.message && (
            <div className={`p-4 rounded font-semibold border ${state.success ? 'bg-green-50 text-green-700 border-green-200' : 'bg-red-50 text-red-600 border-red-100'}`}>
            {state.message}
            </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-6 bg-card text-card-foreground p-8 rounded-lg shadow-sm border border-border">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                
                <div className="md:col-span-2 space-y-6">
                    <h3 className="text-lg font-semibold text-foreground border-b pb-2 flex items-center gap-2">
                        <Building2 className="w-5 h-5 text-muted-foreground" /> Core Accreditation Details
                    </h3>
                    
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div>
                            <label className="block text-sm font-semibold text-muted-foreground mb-2">Organisation ID *</label>
                            <input name="organisationId" type="number" className="w-full px-4 py-2 border border-border rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                            <FieldError field="organisationId" />
                        </div>
                        
                        <div>
                            <label className="block text-sm font-semibold text-muted-foreground mb-2">Provider Type ID *</label>
                            <input name="providerTypeId" type="number" defaultValue={1} className="w-full px-4 py-2 border border-border rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                            <FieldError field="providerTypeId" />
                        </div>

                        <div className="md:col-span-2">
                            <label className="block text-sm font-semibold text-muted-foreground mb-2">Accreditation Number *</label>
                            <input name="accreditationNumber" type="text" placeholder="e.g. ACC1000" className="w-full px-4 py-2 border border-border rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                            <FieldError field="accreditationNumber" />
                            <p className="text-xs text-muted-foreground mt-1">Must strictly be unique and valid across provider records.</p>
                        </div>
                    </div>
                </div>
            </div>

            <div className="pt-6 border-t flex justify-end">
                 <button 
                    type="submit" 
                    disabled={isPending}
                    className={`inline-flex items-center gap-2 px-8 py-3 uppercase text-sm font-bold rounded shadow-sm bg-primary text-primary-foreground hover:bg-primary/90 ${isPending ? 'opacity-50 cursor-not-allowed' : ''}`}
                 >
                    <Save className="w-5 h-5" /> {isPending ? "Validating Bounds..." : "Submit Registration"}
                 </button>
            </div>
        </form>
      </div>
    </main>
  );
}
