"use client";

import { useTransition, useRef } from "react";
import { saveLookup, deleteLookup } from "../_actions/lookup-controller";
import { Save, Trash2, X } from "lucide-react";
import { useRouter } from "next/navigation";

export function LookupForm({
  lookupType,
  initialData
}: {
  lookupType: string;
  initialData: any;
}) {
  const [isPending, startTransition] = useTransition();
  const router = useRouter();
  const formRef = useRef<HTMLFormElement>(null);

  const isNew = !initialData?.id;

  const handleAction = async (formData: FormData) => {
    startTransition(async () => {
      try {
        const payload = {
          name: formData.get("name"),
          code: formData.get("code")?.toString().toUpperCase(),
          description: formData.get("description"),
          active: formData.get("active") === "on"
        };
        
        await saveLookup(lookupType, isNew, initialData?.id || null, payload);
        // We'd add a Shadcn Toast here ideally: toast.success("Record saved!")
        alert("Record saved successfully!");
        router.push(`/admin/lookups/${lookupType}`);
      } catch (e: any) {
        alert(e.message || "Failed to save record");
      }
    });
  };

  const handleDelete = async () => {
    if (!initialData?.id) return;
    if (!confirm("Are you sure you want to permanently delete this record?")) return;

    startTransition(async () => {
      try {
        await deleteLookup(lookupType, initialData.id);
        alert("Record deleted successfully!");
        router.push(`/admin/lookups/${lookupType}`);
      } catch (e: any) {
        alert(e.message || "Failed to delete record");
      }
    });
  };

  return (
    <form ref={formRef} action={handleAction} className="space-y-6">
      
      {/* Action Header Area */}
      <div className="flex justify-between items-center bg-white dark:bg-slate-950 p-4 border border-gray-200 dark:border-slate-800 rounded-lg shadow-sm">
        <h2 className="text-lg font-semibold dark:text-slate-100 placeholder:">
          {isNew ? "Create New Record" : "Edit Record"}
        </h2>
        
        <div className="flex items-center gap-2">
          <button
            type="button"
            onClick={() => router.push(`/admin/lookups/${lookupType}`)}
            className="flex items-center gap-1.5 px-3 py-1.5 text-sm font-semibold rounded-md border border-gray-300 dark:border-slate-700 bg-white dark:bg-slate-900 text-gray-700 dark:text-slate-300 hover:bg-gray-50 dark:hover:bg-slate-800 transition-colors"
            disabled={isPending}
          >
            <X className="w-4 h-4" /> Cancel
          </button>
          
          {!isNew && (
            <button
              type="button"
              onClick={handleDelete}
              className="flex items-center gap-1.5 px-3 py-1.5 text-sm font-semibold rounded-md bg-red-100 text-red-800 hover:bg-red-200 border border-red-200 transition-colors"
              disabled={isPending}
            >
              <Trash2 className="w-4 h-4" /> Delete
            </button>
          )}

          <button
            type="submit"
            className="flex items-center gap-1.5 px-4 py-1.5 text-sm font-semibold rounded-md bg-merseta text-white hover:bg-merseta-dark shadow-sm transition-colors disabled:opacity-50"
            disabled={isPending}
          >
            <Save className="w-4 h-4" /> {isPending ? "Saving..." : "Save Changes"}
          </button>
        </div>
      </div>

      {/* Field Editor */}
      <div className="bg-white dark:bg-slate-950 rounded-lg border border-gray-200 dark:border-slate-800 p-6 shadow-sm space-y-6">
        
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="space-y-2">
            <label className="text-sm font-semibold text-gray-700 dark:text-slate-300">Code</label>
            <p className="text-xs text-gray-500 mb-2">Unique identifier code (will be uppercase)</p>
            <input 
              name="code"
              defaultValue={initialData?.code}
              required
              placeholder="e.g. CAT-01"
              className="w-full rounded-md border border-gray-300 dark:border-slate-700 bg-transparent px-3 py-2 text-sm focus:outline-none focus:ring-1 focus:ring-merseta dark:text-slate-100 uppercase"
            />
          </div>

          <div className="space-y-2">
            <label className="text-sm font-semibold text-gray-700 dark:text-slate-300">Name</label>
            <p className="text-xs text-gray-500 mb-2">Internal display name</p>
            <input 
              name="name"
              defaultValue={initialData?.name}
              required
              placeholder="e.g. Master Category"
              className="w-full rounded-md border border-gray-300 dark:border-slate-700 bg-transparent px-3 py-2 text-sm focus:outline-none focus:ring-1 focus:ring-merseta dark:text-slate-100"
            />
          </div>
        </div>

        <div className="space-y-2">
          <label className="text-sm font-semibold text-gray-700 dark:text-slate-300">Description</label>
          <textarea 
            name="description"
            defaultValue={initialData?.description}
            rows={4}
            className="w-full rounded-md border border-gray-300 dark:border-slate-700 bg-transparent px-3 py-2 text-sm focus:outline-none focus:ring-1 focus:ring-merseta dark:text-slate-100 resize-none"
          />
        </div>

        <div className="flex items-center gap-2 pt-4 border-t border-gray-100 dark:border-slate-800">
          <input 
            type="checkbox"
            name="active"
            id="active"
            defaultChecked={isNew ? true : initialData?.active}
            className="rounded border-gray-300 w-4 h-4 text-merseta focus:ring-merseta"
          />
          <label htmlFor="active" className="text-sm font-medium text-gray-700 dark:text-slate-300">
            Record is active
          </label>
        </div>

      </div>

    </form>
  )
}
