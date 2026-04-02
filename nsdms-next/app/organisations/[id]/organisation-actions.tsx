"use client";

import { useTransition, useState } from "react";
import { useRouter } from "next/navigation";
import { updateOrganisationAction, createOrganisationAction, deleteOrganisationAction, ActionState, updateOrganisationStatusAction } from "../_actions/workflow";
import { downloadAccreditationCertificateAction } from "@/app/_actions/pdf-download";
import { Save, Trash2, Plus, Building2, ShieldCheck, Briefcase, Mail, Phone, MapPin, Building, Hash, FileDown, Loader2, CheckCircle, XCircle, RotateCcw } from "lucide-react";
import Link from "next/link";
import { toast } from "sonner"; 
import { CompanyStatus } from "@/types/enums";

type OrganisationActionsProps = {
  isNew: boolean;
  org: any;
  canCreate: boolean;
  canUpdate: boolean;
  canDelete: boolean;
  canApprove?: boolean;
};

export function OrganisationActions({
  isNew,
  org,
  canCreate,
  canUpdate,
  canDelete,
  canApprove,
}: OrganisationActionsProps) {
  const router = useRouter();
  const [isPending, startTransition] = useTransition();
  const [state, setState] = useState<ActionState>({});

  const handleStatusChange = (event: string) => {
     startTransition(async () => {
        const result = await updateOrganisationStatusAction(org.id, event);
        if (result.success) {
           toast.success(result.message);
        } else {
           toast.error(result.message);
        }
     });
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    
    startTransition(async () => {
      let result: ActionState;
      if (isNew) {
        result = await createOrganisationAction({}, formData);
      } else {
        result = await updateOrganisationAction(org.id, {}, formData);
      }
      
      setState(result);

      if (result.success) {
        toast.success(`Organisation successfully ${isNew ? 'created' : 'updated'}.`);
        if (isNew && result.id) {
          router.push(`/organisations/${result.id}`);
        }
      } else {
        toast.error(result.message || "Operation failed");
      }
    });
  };

  const handleDelete = () => {
    if (!org?.id || !confirm("Are you sure you want to delete this organisation?")) return;
    
    startTransition(async () => {
      const result = await deleteOrganisationAction(org.id);
      if (result.success) {
        toast.success("Organisation deleted successfully.");
        router.push("/organisations");
      } else {
        toast.error("Failed to delete organisation.");
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
    <>
      <div className="sticky top-0 z-50 flex justify-end items-center bg-transparent pointer-events-none -mt-4 mb-4 mr-0">
         <div className="pointer-events-auto flex gap-3">
             <Link href="/organisations" className="inline-flex items-center gap-2 bg-white border border-gray-300 text-gray-700 px-4 py-2 hover:bg-gray-50 uppercase text-xs font-bold rounded">
                Cancel
             </Link>
             
            {!isNew && (
              <button 
                type="button" 
                onClick={handleDelete}
                disabled={!canDelete || isPending}
                id="btn-delete-organisation"
                className={`inline-flex items-center gap-2 px-4 py-2 uppercase text-xs font-bold rounded ${canDelete ? 'bg-red-50 text-red-600 hover:bg-red-100' : 'bg-gray-100 text-gray-400 cursor-not-allowed'}`}
              >
                <Trash2 className="w-4 h-4" /> Delete
              </button>
            )}

            {!isNew && (
              <button 
                type="button" 
                id="btn-download-certificate"
                onClick={async () => {
                  startTransition(async () => {
                    const res = await downloadAccreditationCertificateAction(org);
                    if (res.success && res.data) {
                      const link = document.createElement('a');
                      link.href = `data:application/pdf;base64,${res.data}`;
                      link.download = res.fileName || 'certificate.pdf';
                      link.click();
                      toast.success("Certificate downloaded.");
                    } else {
                      toast.error(res.message || "Download failed");
                    }
                  });
                }}
                disabled={isPending}
                className="inline-flex items-center gap-2 bg-white border border-merseta text-merseta px-4 py-2 hover:bg-merseta-light uppercase text-xs font-bold rounded shadow-sm disabled:opacity-50 font-test-download"
              >
                {isPending ? <Loader2 className="w-4 h-4 animate-spin" /> : <FileDown className="w-4 h-4" />}
                Certificate
              </button>
            )}
            
            <button 
              form="org-form" 
              type="submit" 
              id="btn-save-organisation"
              disabled={(isNew ? !canCreate : !canUpdate) || isPending}
              className={`inline-flex items-center gap-2 px-4 py-2 uppercase text-xs font-bold rounded shadow-sm ${(isNew ? canCreate : canUpdate) ? 'bg-merseta text-white hover:bg-merseta-dark' : 'bg-gray-100 text-gray-400 cursor-not-allowed'} ${isPending ? 'opacity-50' : ''}`}
            >
              <Save className="w-4 h-4" /> {isPending ? "Saving..." : (isNew ? "Create Organisation" : "Save Changes")}
            </button>

            {!isNew && canCreate && (
              <Link href="/organisations/new" className="inline-flex items-center gap-2 bg-merseta-light text-merseta-darkest border border-merseta-dark px-4 py-2 hover:bg-merseta-highlight uppercase text-xs font-bold rounded shadow-sm">
                <Plus className="w-4 h-4" /> New
              </Link>
            )}
         </div>
      </div>

      <form id="org-form" onSubmit={handleSubmit} className="space-y-6 bg-white p-8 rounded-lg shadow-sm border border-gray-200">
        
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {/* Core Details column */}
          <div className="space-y-6">
            <h3 className="text-lg font-semibold text-gray-900 mb-4 border-b pb-2">Core Profile</h3>
            
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">Organisation Name *</label>
              <div className="relative">
                <Building2 className="absolute left-3 top-2.5 h-4 w-4 text-gray-400" />
                <input 
                  name="organisationName" 
                  id="organisationName"
                  defaultValue={org?.organisationName || ""}
                  className={`w-full pl-10 px-4 py-2 border rounded focus:ring-2 focus:outline-none ${state.errors?.organisationName ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-ring'}`} 
                />
              </div>
              <FieldError field="organisationName" />
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">Trading Name</label>
              <div className="relative">
                <Briefcase className="absolute left-3 top-2.5 h-4 w-4 text-gray-400" />
                <input 
                  name="tradingName" 
                  defaultValue={org?.tradingName || ""}
                  className="w-full pl-10 px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-ring focus:outline-none" 
                />
              </div>
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">Physical Address *</label>
              <div className="relative">
                <MapPin className="absolute left-3 top-2.5 h-4 w-4 text-gray-400" />
                <input 
                  name="address" 
                  id="address"
                  defaultValue={org?.address || ""}
                  className={`w-full pl-10 px-4 py-2 border rounded focus:ring-2 focus:outline-none ${state.errors?.address ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-ring'}`} 
                />
              </div>
              <FieldError field="address" />
            </div>
            
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">SIC Code</label>
              <div className="relative">
                <Hash className="absolute left-3 top-2.5 h-4 w-4 text-gray-400" />
                <input 
                  name="sicCode" 
                  defaultValue={org?.sicCode || ""}
                  className={`w-full pl-10 px-4 py-2 border rounded focus:ring-2 focus:outline-none ${state.errors?.sicCode ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-ring'}`} 
                />
              </div>
              <FieldError field="sicCode" />
            </div>
          </div>

          {/* Registration column */}
          <div className="space-y-6 bg-slate-50 p-6 rounded border border-slate-100">
            <h3 className="text-lg font-semibold text-gray-900 mb-4 border-b pb-2">Registrations & Banking</h3>

            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">SDL / Levy Number</label>
              <div className="relative">
                <ShieldCheck className="absolute left-3 top-2.5 h-4 w-4 text-gray-400" />
                <input 
                  name="sdlNumber" 
                  id="sdlNumber"
                  defaultValue={org?.sdlNumber || ""} 
                  className={`w-full pl-10 px-4 py-2 border rounded focus:ring-2 focus:outline-none ${state.errors?.sdlNumber ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-ring'}`} 
                />
              </div>
              <FieldError field="sdlNumber" />
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">Company Reg Number</label>
              <div className="relative">
                <ShieldCheck className="absolute left-3 top-2.5 h-4 w-4 text-gray-400" />
                <input 
                  name="companyRegistrationNumber" 
                  defaultValue={org?.companyRegistrationNumber || ""} 
                  className={`w-full pl-10 px-4 py-2 border rounded focus:ring-2 focus:outline-none ${state.errors?.companyRegistrationNumber ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-ring'}`} 
                />
              </div>
              <FieldError field="companyRegistrationNumber" />
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">Bank Account Number</label>
              <div className="relative">
                <Building className="absolute left-3 top-2.5 h-4 w-4 text-gray-400" />
                <input 
                  name="bankAccountNumber" 
                  defaultValue={org?.bankAccountNumber || ""} 
                  placeholder="e.g. 1002345678"
                  className={`w-full pl-10 px-4 py-2 border rounded focus:ring-2 focus:outline-none ${state.errors?.bankAccountNumber ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-ring'}`} 
                />
              </div>
              <FieldError field="bankAccountNumber" />
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">Bank Branch Code</label>
              <div className="relative">
                <Building className="absolute left-3 top-2.5 h-4 w-4 text-gray-400" />
                <input 
                  name="bankBranchCode" 
                  defaultValue={org?.bankBranchCode || ""} 
                  className={`w-full pl-10 px-4 py-2 border rounded focus:ring-2 focus:outline-none ${state.errors?.bankBranchCode ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-ring'}`} 
                />
              </div>
              <FieldError field="bankBranchCode" />
            </div>
          </div>

           {/* Contact Block */}
           <div className="space-y-6 col-span-1 md:col-span-2 mt-4 pt-4 border-t border-gray-100">
             <h3 className="text-lg font-semibold text-gray-900 mb-4">Contacts</h3>
             <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
              <div>
                <label className="block text-sm font-semibold text-gray-700 mb-2">Primary Email</label>
                <div className="relative">
                  <Mail className="absolute left-3 top-2.5 h-4 w-4 text-gray-400" />
                  <input 
                    name="email" 
                    type="text"
                    defaultValue={org?.email || ""} 
                    className={`w-full pl-10 px-4 py-2 border rounded focus:ring-2 focus:outline-none ${state.errors?.email ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-ring'}`} 
                  />
                </div>
                <FieldError field="email" />
              </div>

              <div>
                <label className="block text-sm font-semibold text-gray-700 mb-2">Telephone Number</label>
                <div className="relative">
                  <Phone className="absolute left-3 top-2.5 h-4 w-4 text-gray-400" />
                  <input 
                    name="telNumber" 
                    defaultValue={org?.telNumber || ""} 
                    className="w-full pl-10 px-4 py-2 border border-gray-300 rounded focus:ring-2 focus:ring-ring focus:outline-none" 
                  />
                </div>
              </div>
             </div>
           </div>
        </div>
      </form>
    </>
  );
}
