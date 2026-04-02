"use client";

import { useTransition, useState, use } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";
import { ArrowLeft, Save, User, Calendar, Folder } from "lucide-react";
import { createLearnerAction, ActionState } from "../_actions/workflow";
import { toast } from "sonner";

export default function NewLearner({ searchParams }: { searchParams: Promise<{ providerId?: string }> }) {
  const router = useRouter();
  const [isPending, startTransition] = useTransition();
  const [state, setState] = useState<ActionState>({});
  const params = use(searchParams);
  const providerId = params.providerId || "";

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    
    startTransition(async () => {
      const result = await createLearnerAction({}, formData);
      setState(result);

      if (result.success) {
        toast.success("Learner successfully enrolled.");
        if (providerId) {
            router.push(`/organisations/${providerId}`);
        } else {
            router.push(`/learners`);
        }
      } else {
        toast.error(result.message || "Enrollment failed");
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
          <Link href={providerId ? `/organisations/${providerId}` : `/learners`} className="text-gray-500 hover:text-gray-900 inline-flex items-center gap-2">
            <ArrowLeft className="w-4 h-4" /> Back
          </Link>
          <div className="font-bold text-lg text-primary">New Learner Enrollment</div>
        </div>
      </div>

      <div className="max-w-4xl mx-auto space-y-6">
        {state.message && (
            <div className="bg-red-50 text-red-600 p-4 rounded font-semibold border border-red-100">
            {state.message}
            </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-6 bg-white p-8 rounded-lg shadow-sm border border-gray-200">
            <input type="hidden" name="providerId" value={providerId} />
            <input type="hidden" name="interventionTypeId" value="1" />
            <input type="hidden" name="qualificationTypeId" value="1" />
            <input type="hidden" name="nationality" value="South African" />
            
            <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                {/* Profile Section */}
                <div className="space-y-6">
                    <h3 className="text-lg font-semibold text-gray-900 border-b pb-2 flex items-center gap-2">
                        <User className="w-5 h-5 text-gray-400" /> Identity Details
                    </h3>
                    
                    <div>
                        <label className="block text-sm font-semibold text-gray-700 mb-2">RSA ID Number</label>
                        <input name="rsaIdNumber" id="rsaIdNumber" placeholder="13-digit ID" className="w-full px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                        <FieldError field="rsaIdNumber" />
                    </div>

                    <div>
                        <label className="block text-sm font-semibold text-gray-700 mb-2">Passport Number (If no RSA ID)</label>
                        <input name="passportNumber" id="passportNumber" className="w-full px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                        <FieldError field="passportNumber" />
                    </div>

                    <div>
                        <label className="block text-sm font-semibold text-gray-700 mb-2">Date of Birth *</label>
                        <div className="relative">
                            <Calendar className="absolute left-3 top-2.5 h-4 w-4 text-gray-400" />
                            <input name="dateOfBirth" id="dateOfBirth" type="date" className="w-full pl-10 px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                        </div>
                        <FieldError field="dateOfBirth" />
                    </div>

                    <div className="flex gap-4">
                        <div className="w-1/2">
                            <label className="block text-sm font-semibold text-gray-700 mb-2">Equity Status *</label>
                            <input name="equityStatus" defaultValue="African" className="w-full px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                            <FieldError field="equityStatus" />
                        </div>
                        <div className="w-1/2">
                            <label className="block text-sm font-semibold text-gray-700 mb-2">Nationality *</label>
                            <input name="nationality" defaultValue="South African" className="w-full px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                            <FieldError field="nationality" />
                        </div>
                    </div>
                </div>

                {/* Enrollment Section */}
                <div className="space-y-6">
                    <h3 className="text-lg font-semibold text-gray-900 border-b pb-2 flex items-center gap-2">
                        <Folder className="w-5 h-5 text-gray-400" /> Enrollment Details
                    </h3>

                    {!providerId && (
                        <div>
                            <label className="block text-sm font-semibold text-gray-700 mb-2">Provider ID *</label>
                            <input name="providerId" id="providerId" type="number" className="w-full px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                            <FieldError field="providerId" />
                        </div>
                    )}

                    <div>
                        <label className="block text-sm font-semibold text-gray-700 mb-2">Intervention Type ID *</label>
                        <input name="interventionTypeId" type="number" defaultValue="1" className="w-full px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                        <FieldError field="interventionTypeId" />
                        <p className="text-xs text-gray-400 mt-1">Hint: Defaults to 1 for generic Type during testing.</p>
                    </div>

                    <div>
                        <label className="block text-sm font-semibold text-gray-700 mb-2">Qualification Type ID *</label>
                        <input name="qualificationTypeId" type="number" defaultValue="1" className="w-full px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-primary focus:outline-none" />
                        <FieldError field="qualificationTypeId" />
                        <p className="text-xs text-gray-400 mt-1">Hint: Defaults to 1 for generic Qualification during testing.</p>
                    </div>
                </div>
            </div>

            <div className="pt-6 border-t flex justify-end">
                 <button 
                    type="submit" 
                    id="btn-enroll-learner"
                    disabled={isPending}
                    className={`inline-flex items-center gap-2 px-6 py-3 uppercase text-sm font-bold rounded shadow-sm bg-primary text-primary-foreground hover:bg-primary/90 ${isPending ? 'opacity-50 cursor-not-allowed' : ''}`}
                 >
                    <Save className="w-5 h-5" /> {isPending ? "Evaluating Payload..." : "Enroll Learner"}
                 </button>
            </div>
        </form>
      </div>
    </main>
  );
}
