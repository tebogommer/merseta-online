"use client";

import { useTransition, useState } from "react";
import { saveUser, deleteUser, linkUserToPerson } from "../_actions/user-controller";
import { Save, Trash2, X, ShieldCheck, ShieldAlert, UserPlus, Link2Off } from "lucide-react";
import { useRouter } from "next/navigation";
import { Badge } from "@/components/ui/badge";
import { Tabs, TabsList, TabsTrigger, TabsContent } from "@/components/ui/tabs";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";

export function UserForm({
  initialData,
  availablePersons = []
}: {
  initialData?: any;
  availablePersons?: any[];
}) {
  const [isPending, startTransition] = useTransition();
  const router = useRouter();
  const isNew = !initialData?.id;
  const [selectedPerson, setSelectedPerson] = useState<string>("");

  // Parse Hybrid Permission Overrides
  const initialPermissions = initialData?.permissions ? JSON.parse(initialData.permissions) : [];
  const hasOverride = (action: string, subject: string) => {
    return initialPermissions.some((p: any) => p.action === action && p.subject === subject);
  };

  const [overrides, setOverrides] = useState({
    canApproveWSP: hasOverride('manage', 'WorkplaceSkillsPlan'),
    isEtqaAdmin: hasOverride('review_etqa', 'WorkplaceApproval'),
    canManageGrants: hasOverride('manage', 'GrantApplication')
  });

  const handleAction = async (formData: FormData) => {
    startTransition(async () => {
      try {
        const payload = {
          id: initialData?.id,
          name: formData.get("name") as string,
          email: formData.get("email") as string,
          role: formData.get("role") as string,
          active: formData.get("active") === "on",
          permissions: JSON.stringify([
            ...(overrides.canApproveWSP ? [{ action: 'manage', subject: 'WorkplaceSkillsPlan' }] : []),
            ...(overrides.isEtqaAdmin ? [{ action: 'review_etqa', subject: 'WorkplaceApproval' }] : []),
            ...(overrides.canManageGrants ? [{ action: 'manage', subject: 'GrantApplication' }] : [])
          ])
        };

        await saveUser(payload);
        alert("Access configuration updated.");
        router.push("/admin/users");
      } catch (e: any) {
        alert(e.message || "Failed to save user");
      }
    });
  };

  const handleLinkPerson = async () => {
    if (!selectedPerson || isNew) return;
    startTransition(async () => {
       try {
         await linkUserToPerson(initialData.id, parseInt(selectedPerson));
         alert("Authentication account anchored to identity profile.");
         router.refresh();
       } catch (e: any) {
         alert(e.message || "Failed to link person");
       }
    });
  };

  const handleUnlink = async () => {
     if (!initialData?.id || !initialData.person) return;
     if (!confirm("This will remove the identity link from this account. The account will become an anonymous service account. Continue?")) return;
     
     startTransition(async () => {
        try {
          await linkUserToPerson(initialData.id, null);
          alert("Identity link removed.");
          router.refresh();
        } catch (e: any) {
          alert(e.message || "Failed to unlink");
        }
     });
  };

  return (
    <div className="space-y-6">
      
      {/* Action Header */}
      <div className="z-20 sticky top-0 flex justify-between items-center bg-card/80 backdrop-blur-md text-card-foreground p-4 border border-border rounded-xl shadow-lg dark:bg-slate-950/80 mb-6">
        <div className="flex items-center gap-3">
           <button
             type="button"
             onClick={() => router.push("/admin/users")}
             className="p-2 hover:bg-muted rounded-full transition-all text-muted-foreground hover:text-foreground"
           >
             <X className="w-5 h-5" />
           </button>
           <div>
             <h2 className="text-xl font-bold uppercase tracking-tight">
               {isNew ? "Provision Account" : initialData.email}
             </h2>
             <p className="text-[10px] uppercase font-bold text-muted-foreground tracking-widest -mt-1">System Access Control</p>
           </div>
        </div>
        
        <div className="flex items-center gap-2">
          {!isNew && (
            <button
              type="button"
              onClick={async () => {
                 if(confirm("Confirm deletion of this account? This is irreversible.")) {
                    startTransition(async () => {
                       await deleteUser(initialData.id);
                       router.push("/admin/users");
                    });
                 }
              }}
              className="px-4 py-2 text-red-600 hover:bg-red-50 rounded-lg text-sm font-bold uppercase transition-all"
              disabled={isPending}
            >
              Terminate Access
            </button>
          )}

          <button
            onClick={() => {
              const form = document.getElementById("user-form") as HTMLFormElement;
              if (form) form.requestSubmit();
            }}
            className="flex items-center gap-2 bg-primary text-primary-foreground px-6 py-2.5 rounded-lg text-sm font-bold uppercase shadow-xl hover:bg-primary/90 transition-all active:scale-95 disabled:opacity-50"
            disabled={isPending}
          >
            <Save className="w-4 h-4" /> {isPending ? "Applying..." : "Apply Config"}
          </button>
        </div>
      </div>

      <Tabs defaultValue="credentials" className="w-full">
        <TabsList className="bg-muted p-1 rounded-lg mb-4">
          <TabsTrigger value="credentials" className="flex items-center gap-2 px-6 py-2">
            Login Credentials
          </TabsTrigger>
          {!isNew && (
            <>
              <TabsTrigger value="overrides" className="flex items-center gap-2 px-6 py-2">
                Permission Overrides
              </TabsTrigger>
              <TabsTrigger value="identity" className="flex items-center gap-2 px-6 py-2">
                Identity Anchor
              </TabsTrigger>
            </>
          )}
        </TabsList>

        <TabsContent value="credentials">
           <form id="user-form" action={handleAction} className="grid grid-cols-1 md:grid-cols-3 gap-6">
              <div className="md:col-span-2 space-y-6">
                <div className="bg-card text-card-foreground p-8 rounded-xl border border-border shadow-sm dark:bg-slate-950/50 space-y-8 animate-in fade-in slide-in-from-left-4 duration-500">
                   <h3 className="text-xs font-black uppercase tracking-[0.2em] text-primary flex items-center gap-2">
                     <ShieldCheck className="w-4 h-4" /> Security Identity
                   </h3>
                   
                   <div className="space-y-6">
                     <div className="space-y-2">
                       <label className="text-xs font-black uppercase text-muted-foreground/70 tracking-wider">Display Name</label>
                       <input 
                         name="name"
                         defaultValue={initialData?.name}
                         required
                         placeholder="e.g. Administrator"
                         className="w-full bg-transparent border-b-2 border-border focus:border-primary px-1 py-1 text-base font-semibold focus:outline-none transition-all"
                       />
                     </div>

                     <div className="space-y-2">
                       <label className="text-xs font-black uppercase text-muted-foreground/70 tracking-wider">Email Address</label>
                       <input 
                         type="email"
                         name="email"
                         defaultValue={initialData?.email}
                         required
                         placeholder="user@merseta.org.za"
                         className="w-full bg-transparent border-b-2 border-border focus:border-primary px-1 py-1 text-base font-mono font-bold focus:outline-none transition-all"
                       />
                     </div>
                   </div>
                </div>
              </div>

              <div className="space-y-6">
                 <div className="bg-card text-card-foreground p-8 rounded-xl border border-border shadow-sm dark:bg-slate-950/50 space-y-8 animate-in fade-in slide-in-from-right-4 duration-500">
                    <h3 className="text-xs font-black uppercase tracking-[0.2em] text-primary">
                      Permissions
                    </h3>

                    <div className="space-y-6">
                      <div className="space-y-2">
                        <label className="text-xs font-black uppercase text-muted-foreground/70 tracking-wider">System Role</label>
                        <select 
                          name="role"
                          defaultValue={initialData?.role || "STANDARD"}
                          className="w-full bg-transparent border border-border rounded-lg px-3 py-2 text-sm font-black uppercase tracking-wider focus:ring-1 focus:ring-primary outline-none"
                        >
                          <option value="ADMIN">ADMINISTRATOR</option>
                          <option value="STANDARD">STANDARD USER</option>
                          <option value="READONLY">READ ONLY AUDIT</option>
                        </select>
                      </div>

                      <div className="flex items-center gap-2 pt-4">
                         <input 
                           type="checkbox"
                           name="active"
                           id="active"
                           defaultChecked={isNew ? true : initialData?.active}
                           className="w-4 h-4 rounded border-border"
                         />
                         <label htmlFor="active" className="text-xs font-black uppercase text-muted-foreground/70 tracking-widest cursor-pointer">Account Enabled</label>
                      </div>
                    </div>
                 </div>
              </div>
           </form>
         </TabsContent>

         {!isNew && (
           <TabsContent value="overrides" className="animate-in zoom-in-95 duration-300">
             <div className="bg-card text-card-foreground p-8 rounded-xl border border-border shadow-sm dark:bg-slate-950/50 max-w-4xl mx-auto space-y-8">
                <div className="flex items-center gap-4 border-b border-border pb-6">
                   <div className="bg-primary/10 p-4 rounded-2xl">
                      <ShieldCheck className="w-8 h-8 text-primary" />
                   </div>
                   <div>
                      <h3 className="text-2xl font-bold">Hybrid Overrides</h3>
                      <p className="text-muted-foreground">Grant specific abilities bypassing their general System Role.</p>
                   </div>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                   <div className="p-6 border border-border rounded-xl space-y-2 bg-muted/20">
                     <div className="flex items-center justify-between">
                       <label className="text-sm font-bold uppercase tracking-wider">Can Manage WSP</label>
                       <input 
                         type="checkbox" 
                         checked={overrides.canApproveWSP}
                         onChange={(e) => setOverrides({...overrides, canApproveWSP: e.target.checked})}
                         className="w-5 h-5 accent-primary"
                       />
                     </div>
                     <p className="text-xs text-muted-foreground">Allows overriding readonly limitations to Workplace Skills Plans.</p>
                   </div>

                   <div className="p-6 border border-border rounded-xl space-y-2 bg-muted/20">
                     <div className="flex items-center justify-between">
                       <label className="text-sm font-bold uppercase tracking-wider">ETQA Administrator</label>
                       <input 
                         type="checkbox" 
                         checked={overrides.isEtqaAdmin}
                         onChange={(e) => setOverrides({...overrides, isEtqaAdmin: e.target.checked})}
                         className="w-5 h-5 accent-primary"
                       />
                     </div>
                     <p className="text-xs text-muted-foreground">Allows final approval and review of Workplace Approvals.</p>
                   </div>
                   
                   <div className="p-6 border border-border rounded-xl space-y-2 bg-muted/20">
                     <div className="flex items-center justify-between">
                       <label className="text-sm font-bold uppercase tracking-wider">Grant Manager</label>
                       <input 
                         type="checkbox" 
                         checked={overrides.canManageGrants}
                         onChange={(e) => setOverrides({...overrides, canManageGrants: e.target.checked})}
                         className="w-5 h-5 accent-primary"
                       />
                     </div>
                     <p className="text-xs text-muted-foreground">Allows creating and submitting Grant Applications.</p>
                   </div>
                </div>

                <div className="pt-4 flex justify-end">
                  <button
                    onClick={() => {
                      const form = document.getElementById("user-form") as HTMLFormElement;
                      if (form) form.requestSubmit();
                    }}
                    className="flex items-center gap-2 bg-primary text-primary-foreground px-6 py-2.5 rounded-lg text-sm font-bold uppercase shadow-xl hover:bg-primary/90 transition-all active:scale-95 disabled:opacity-50"
                    disabled={isPending}
                  >
                    <Save className="w-4 h-4" /> {isPending ? "Applying..." : "Apply Overrides"}
                  </button>
                </div>
             </div>
           </TabsContent>
         )}

        <TabsContent value="identity" className="animate-in zoom-in-95 duration-300">
           <div className="bg-card text-card-foreground p-8 rounded-xl border border-border shadow-sm dark:bg-slate-950/50 max-w-4xl mx-auto space-y-8">
              <div className="flex items-center gap-4 border-b border-border pb-6">
                 <div className="bg-primary/10 p-4 rounded-2xl">
                    <ShieldAlert className="w-8 h-8 text-primary" />
                 </div>
                 <div>
                    <h3 className="text-2xl font-bold">Demographic Anchor</h3>
                    <p className="text-muted-foreground">Attach this authentication record to a physical Person profile in the directory.</p>
                 </div>
              </div>

              {initialData?.person ? (
                 <div className="flex items-center justify-between p-6 bg-primary/5 border border-primary/20 rounded-2xl">
                    <div className="flex items-center gap-6">
                       <div className="text-center">
                          <p className="text-[10px] font-black uppercase text-primary tracking-widest">Linked Person</p>
                          <Avatar className="w-16 h-16 mt-2 border-4 border-white shadow-xl">
                             <AvatarFallback className="bg-primary text-white font-bold text-xl">{initialData.person.lastName[0]}</AvatarFallback>
                          </Avatar>
                       </div>
                       <div>
                          <p className="text-xl font-bold">{initialData.person.lastName}, {initialData.person.firstName}</p>
                          <div className="flex items-center gap-2 mt-1">
                             <Badge variant="outline" className="font-mono text-[10px]">{initialData.person.rsaIdNumber || "PASSPORT"}</Badge>
                             <span className="text-xs text-muted-foreground italic font-medium">Internal ID: #{initialData.person.id}</span>
                          </div>
                          <Link href={`/admin/persons/${initialData.person.id}`} className="inline-flex items-center gap-1 text-[10px] uppercase font-bold text-primary mt-4 hover:underline">
                             View Demographic Profile
                          </Link>
                       </div>
                    </div>
                    <button 
                      onClick={handleUnlink}
                      disabled={isPending}
                      className="group flex items-center gap-2 px-6 py-3 rounded-xl border-2 border-red-200 text-red-600 hover:bg-red-600 hover:text-white transition-all font-bold text-sm uppercase"
                    >
                       <Link2Off className="w-4 h-4" /> {isPending ? "Unlinking..." : "Detach Identity"}
                    </button>
                 </div>
              ) : (
                 <div className="space-y-6">
                    <div className="text-center py-12 border-2 border-dashed border-border rounded-3xl bg-muted/20">
                       <UserPlus className="w-12 h-12 text-muted-foreground/30 mx-auto mb-4" />
                       <h4 className="text-lg font-bold">Anonymous Service Account</h4>
                       <p className="text-sm text-muted-foreground max-w-sm mx-auto px-4 mt-2">
                         This account is currently not linked to a physical human profile.
                       </p>
                    </div>
                    
                    <div className="bg-muted p-2 rounded-2xl flex items-center gap-2 shadow-inner">
                       <select 
                         value={selectedPerson}
                         onChange={(e) => setSelectedPerson(e.target.value)}
                         className="flex-1 bg-transparent px-4 py-3 font-semibold text-sm focus:outline-none"
                       >
                          <option value="">Select Person from Directory...</option>
                          {availablePersons.map(p => (
                            <option key={p.id} value={p.id}>{p.lastName}, {p.firstName} ({p.rsaIdNumber || "Passport"})</option>
                          ))}
                       </select>
                       <button 
                         onClick={handleLinkPerson}
                         disabled={!selectedPerson || isPending}
                         className="bg-primary text-primary-foreground px-8 py-3 rounded-xl font-black text-xs uppercase tracking-widest shadow-xl shadow-primary/20 hover:-translate-y-0.5 active:translate-y-0 transition-all disabled:opacity-50"
                       >
                         {isPending ? "Anchoring..." : "Link Identity"}
                       </button>
                    </div>
                 </div>
              )}
           </div>
        </TabsContent>
      </Tabs>
      
    </div>
  );
}

import Link from "next/link";
