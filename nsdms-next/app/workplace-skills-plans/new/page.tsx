"use client";

import { useTransition, useState, use } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";
import { ArrowLeft, Save, Building, Calculator, FileText } from "lucide-react";
import { createWspAction, ActionState } from "../_actions/workflow";

export default function NewWSP({ searchParams }: { searchParams: Promise<{ orgId?: string }> }) {
  const router = useRouter();
  const [isPending, startTransition] = useTransition();
  const [state, setState] = useState<ActionState>({});
  const params = use(searchParams);
  const orgId = params.orgId || "";

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    
    startTransition(async () => {
      const result = await createWspAction({}, formData);
      setState(result);

      if (result.success) {
        alert("Workplace Skills Plan successfully submitted to Draft!");
        if (orgId) {
            router.push(`/organisations/${orgId}`);
        } else {
            router.push(`/workplace-skills-plans`);
        }
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
    <main className="min-h-screen bg-gray-50 p-8">
      <div className="max-w-4xl mx-auto mb-6">
        <div className="flex justify-between items-center bg-white p-4 rounded-lg shadow-sm border border-gray-200">
          <Link href={orgId ? `/organisations/${orgId}` : `/workplace-skills-plans`} className="text-gray-500 hover:text-gray-900 inline-flex items-center gap-2">
            <ArrowLeft className="w-4 h-4" /> Cancel Submission
          </Link>
          <div className="font-bold text-lg text-primary">New Workplace Skills Plan (WSP)</div>
        </div>
      </div>

      <div className="max-w-4xl mx-auto space-y-6">
        {state.message && (
            <div className={`p-4 rounded font-semibold border ${state.success ? 'bg-green-50 text-green-700 border-green-200' : 'bg-red-50 text-red-600 border-red-100'}`}>
            {state.message}
            </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-6 bg-white p-8 rounded-lg shadow-sm border border-gray-200">
            <input type="hidden" name="organisationId" value={orgId} />
            
            <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                
                {/* Core Parameters */}
                <div className="space-y-6">
                    <h3 className="text-lg font-semibold text-gray-900 border-b pb-2 flex items-center gap-2">
                        <Building className="w-5 h-5 text-gray-400" /> Administrative Context
                    </h3>
                    
                    {!orgId && (
                        <div>
                            <label className="block text-sm font-semibold text-gray-700 mb-2">Organisation ID *</label>
                            <input name="organisationId" type="number" className="w-full px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                            <FieldError field="organisationId" />
                        </div>
                    )}

                    <div>
                        <label className="block text-sm font-semibold text-gray-700 mb-2">Financial Year *</label>
                        <input name="finYear" type="number" defaultValue={new Date().getFullYear() + 1} className="w-full px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                        <FieldError field="finYear" />
                        <p className="text-xs text-gray-400 mt-1">Default allocated to upcoming financial period.</p>
                    </div>

                    <div className="flex gap-4">
                        <div className="w-1/2">
                            <label className="block text-sm font-semibold text-gray-700 mb-2">Total Employees *</label>
                            <input name="numberOfEmployees" type="number" placeholder="e.g. 50" className="w-full px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                            <FieldError field="numberOfEmployees" />
                        </div>
                        <div className="w-1/2">
                            <label className="block text-sm font-semibold text-gray-700 mb-2">Target Beneficiaries</label>
                            <input name="numberOfBeneficiaries" type="number" defaultValue={0} className="w-full px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                            <FieldError field="numberOfBeneficiaries" />
                        </div>
                    </div>
                </div>

                {/* Financials Section */}
                <div className="space-y-6">
                    <h3 className="text-lg font-semibold text-gray-900 border-b pb-2 flex items-center gap-2">
                        <Calculator className="w-5 h-5 text-gray-400" /> Financial Projections
                    </h3>

                    <div>
                        <label className="block text-sm font-semibold text-gray-700 mb-2">Total Payroll (R) *</label>
                        <input name="totalPayroll" type="number" step="0.01" placeholder="Current total annual payroll" className="w-full px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                        <FieldError field="totalPayroll" />
                    </div>

                    <div>
                        <label className="block text-sm font-semibold text-gray-700 mb-2">Estimated Training Costs (R) *</label>
                        <input name="totalTrainingCosts" type="number" step="0.01" placeholder="Must not exceed Total Payroll" className="w-full px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                        <FieldError field="totalTrainingCosts" />
                        <p className="text-xs text-gray-400 mt-1">Percentage Spent will be auto-calculated securely on the backend.</p>
                    </div>
                </div>

                {/* Extended Details */}
                <div className="md:col-span-2 space-y-6 pt-4">
                     <h3 className="text-lg font-semibold text-gray-900 border-b pb-2 flex items-center gap-2">
                        <FileText className="w-5 h-5 text-gray-400" /> Strategy Profile
                    </h3>

                    <div>
                        <label className="block text-sm font-semibold text-gray-700 mb-2">Project Description</label>
                        <textarea name="projectDescription" rows={3} placeholder="Brief outline of the learning strategy..." className="w-full px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-primary focus:outline-none"></textarea>
                        <FieldError field="projectDescription" />
                    </div>
                    
                    <div>
                        <label className="block text-sm font-semibold text-gray-700 mb-2">Planned Interventions</label>
                        <textarea name="interventions" rows={2} placeholder="List key strategic gap closures..." className="w-full px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-primary focus:outline-none"></textarea>
                        <FieldError field="interventions" />
                    </div>
                </div>
            </div>

            <div className="pt-6 border-t flex justify-end">
                 <button 
                    type="submit" 
                    disabled={isPending}
                    className={`inline-flex items-center gap-2 px-8 py-3 uppercase text-sm font-bold rounded shadow-sm bg-primary text-primary-foreground hover:bg-primary/90 ${isPending ? 'opacity-50 cursor-not-allowed' : ''}`}
                 >
                    <Save className="w-5 h-5" /> {isPending ? "Validating Topology..." : "Save to Draft"}
                 </button>
            </div>
        </form>
      </div>
    </main>
  );
}
