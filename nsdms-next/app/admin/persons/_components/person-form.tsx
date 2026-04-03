"use client";

import { useTransition, useState, useEffect } from "react";
import { savePerson, deletePerson } from "../_actions/person-controller";
import { Save, Trash2, X, AlertCircle } from "lucide-react";
import { useRouter } from "next/navigation";
import { Badge } from "@/components/ui/badge";

import { Tabs, TabsList, TabsTrigger, TabsContent } from "@/components/ui/tabs";
import { fetchUsersWithoutPersonLink, linkPersonToUser } from "../_actions/person-controller";

export function PersonForm({
  initialData,
  lookups,
  availableUsers = []
}: {
  initialData?: any;
  lookups: {
    genders: any[];
    equities: any[];
    nationalities: any[];
  };
  availableUsers?: any[];
}) {
  const [isPending, startTransition] = useTransition();
  const router = useRouter();
  const isNew = !initialData?.id;

  const [rsaId, setRsaId] = useState(initialData?.rsaIdNumber || "");
  const [dob, setDob] = useState(initialData?.dateOfBirth ? new Date(initialData.dateOfBirth).toISOString().split('T')[0] : "");
  const [selectedUser, setSelectedUser] = useState<string>("");

  // Auto-derive DOB from RSA ID
  useEffect(() => {
    if (rsaId && rsaId.length === 13) {
      const yearStr = rsaId.substring(0, 2);
      const monthStr = rsaId.substring(2, 4);
      const dayStr = rsaId.substring(4, 6);
      
      const yearNum = parseInt(yearStr, 10);
      const monthNum = parseInt(monthStr, 10);
      const dayNum = parseInt(dayStr, 10);

      if (!isNaN(yearNum) && !isNaN(monthNum) && !isNaN(dayNum)) {
        const fullYear = yearNum < 30 ? 2000 + yearNum : 1900 + yearNum;
        const derivedDate = `${fullYear}-${monthStr.padStart(2, '0')}-${dayStr.padStart(2, '0')}`;
        setDob(derivedDate);
      }
    }
  }, [rsaId]);

  const handleAction = async (formData: FormData) => {
    startTransition(async () => {
      try {
        const payload = {
          id: initialData?.id,
          firstName: formData.get("firstName") as string,
          lastName: formData.get("lastName") as string,
          rsaIdNumber: formData.get("rsaIdNumber") as string || null,
          passportNumber: formData.get("passportNumber") as string || null,
          dateOfBirth: formData.get("dateOfBirth") ? new Date(formData.get("dateOfBirth") as string) : null,
          genderId: formData.get("genderId") ? parseInt(formData.get("genderId") as string) : null,
          equityId: formData.get("equityId") ? parseInt(formData.get("equityId") as string) : null,
          nationalityId: formData.get("nationalityId") ? parseInt(formData.get("nationalityId") as string) : null,
        };

        await savePerson(payload);
        alert("Demographics synced with directory successfully.");
        router.push("/admin/persons");
      } catch (e: any) {
        alert(e.message || "Failed to save person");
      }
    });
  };

  const handleLinkUser = async () => {
    if (!selectedUser || isNew) return;
    startTransition(async () => {
       try {
         await linkPersonToUser(initialData.id, parseInt(selectedUser));
         alert("Identity and access linked successfully.");
         router.refresh();
       } catch (e: any) {
         alert(e.message || "Failed to link user");
       }
    });
  };

  const handleUnlink = async () => {
     if (!initialData?.id || !initialData.user) return;
     if (!confirm("This will detach the authentication account from this physical profile. Continue?")) return;
     
     startTransition(async () => {
        try {
          await linkPersonToUser(initialData.id, null);
          alert("Account unlinked.");
          router.refresh();
        } catch (e: any) {
          alert(e.message || "Failed to unlink");
        }
     });
  };

  return (
    <div className="space-y-6">
      
      {/* Stick Action Header */}
      <div className="z-20 sticky top-0 flex justify-between items-center bg-card/80 backdrop-blur-md text-card-foreground p-4 border border-border rounded-xl shadow-lg dark:bg-slate-950/80 mb-6">
        <div className="flex items-center gap-3">
           <button
             type="button"
             onClick={() => router.push("/admin/persons")}
             className="p-2 hover:bg-muted rounded-full transition-all text-muted-foreground hover:text-foreground"
           >
             <X className="w-5 h-5" />
           </button>
           <div>
             <h2 className="text-xl font-bold uppercase tracking-tight">
               {isNew ? "Create Directory Entry" : `${initialData.lastName}, ${initialData.firstName}`}
             </h2>
             <p className="text-[10px] uppercase font-bold text-muted-foreground tracking-widest -mt-1">Demographics & Identity Directory</p>
           </div>
        </div>
        
        <div className="flex items-center gap-2">
          {!isNew && (
            <button
              type="button"
              onClick={async () => {
                if(confirm("Confirm deletion of this directory record?")) {
                  startTransition(async () => {
                    await deletePerson(initialData.id);
                    router.push("/admin/persons");
                  });
                }
              }}
              className="px-4 py-2 text-red-600 hover:bg-red-50 rounded-lg text-sm font-bold uppercase transition-all"
              disabled={isPending}
            >
              Remove Record
            </button>
          )}

          <button
            onClick={() => {
              const form = document.getElementById("person-form") as HTMLFormElement;
              if (form) form.requestSubmit();
            }}
            className="flex items-center gap-2 bg-merseta text-white px-6 py-2.5 rounded-lg text-sm font-bold uppercase shadow-merseta hover:bg-merseta-dark transition-all active:scale-95 disabled:opacity-50"
            disabled={isPending}
          >
            <Save className="w-4 h-4" /> {isPending ? "Applying..." : "Save Identity"}
          </button>
        </div>
      </div>

      <Tabs defaultValue="general" className="w-full">
        <TabsList className="bg-muted p-1 rounded-lg mb-4">
          <TabsTrigger value="general" className="flex items-center gap-2 px-6 py-2">
            General Demographics
          </TabsTrigger>
          {!isNew && (
            <TabsTrigger value="access" className="flex items-center gap-2 px-6 py-2">
              System Access Link
            </TabsTrigger>
          )}
        </TabsList>

        <TabsContent value="general">
          <form id="person-form" action={handleAction} className="grid grid-cols-1 lg:grid-cols-3 gap-6">
            <div className="lg:col-span-2 space-y-6">
              <div className="bg-card text-card-foreground p-8 rounded-xl border border-border shadow-sm dark:bg-slate-950/50 space-y-8 animate-in fade-in slide-in-from-left-4 duration-500">
                <div className="space-y-6">
                  <h3 className="text-xs font-black uppercase tracking-[0.2em] text-primary flex items-center gap-2">
                     Legal Identification
                  </h3>
                  
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-x-6 gap-y-6">
                    <div className="space-y-2">
                      <label className="text-xs font-black uppercase text-muted-foreground/70 tracking-wider">Given Names</label>
                      <input 
                        name="firstName"
                        defaultValue={initialData?.firstName}
                        required
                        placeholder="e.g. John"
                        className="w-full bg-transparent border-b-2 border-border focus:border-primary px-1 py-1 text-base font-semibold focus:outline-none transition-all placeholder:text-muted-foreground/30"
                      />
                    </div>

                    <div className="space-y-2">
                      <label className="text-xs font-black uppercase text-muted-foreground/70 tracking-wider">Surname / Family Name</label>
                      <input 
                        name="lastName"
                        defaultValue={initialData?.lastName}
                        required
                        placeholder="e.g. Smith"
                        className="w-full bg-transparent border-b-2 border-border focus:border-primary px-1 py-1 text-base font-semibold focus:outline-none transition-all placeholder:text-muted-foreground/30"
                      />
                    </div>

                    <div className="space-y-2">
                      <label className="text-xs font-black uppercase text-muted-foreground/70 tracking-wider">RSA Identity Number</label>
                      <input 
                        name="rsaIdNumber"
                        value={rsaId}
                        onChange={(e) => setRsaId(e.target.value)}
                        placeholder="YYMMDDSSSSCAZ"
                        maxLength={13}
                        className="w-full bg-transparent border-b-2 border-border focus:border-primary px-1 py-1 text-base font-mono font-bold focus:outline-none transition-all placeholder:text-muted-foreground/30"
                      />
                    </div>

                    <div className="space-y-2">
                      <label className="text-xs font-black uppercase text-muted-foreground/70 tracking-wider">Passport Number</label>
                      <input 
                        name="passportNumber"
                        defaultValue={initialData?.passportNumber}
                        placeholder="Non-resident Travel ID"
                        className="w-full bg-transparent border-b-2 border-border focus:border-primary px-1 py-1 text-base font-mono font-bold focus:outline-none transition-all placeholder:text-muted-foreground/30"
                      />
                    </div>

                    <div className="space-y-2 md:col-span-2">
                      <label className="text-xs font-black uppercase text-muted-foreground/70 tracking-wider flex items-center gap-1">
                         Date of Birth <span className="text-[10px] text-primary/50 normal-case">(Auto-calculates from ID)</span>
                      </label>
                      <input 
                        type="date"
                        name="dateOfBirth"
                        value={dob}
                        onChange={(e) => setDob(e.target.value)}
                        className="w-full bg-transparent border-b-2 border-border focus:border-primary px-1 py-1 text-base font-semibold focus:outline-none transition-all"
                      />
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div className="space-y-6">
               <div className="bg-card text-card-foreground p-8 rounded-xl border border-border shadow-sm dark:bg-slate-950/50 space-y-8 animate-in fade-in slide-in-from-right-4 duration-500">
                  <h3 className="text-xs font-black uppercase tracking-[0.2em] text-primary">
                    Profile Classification
                  </h3>

                  <div className="space-y-6">
                    <div className="space-y-2">
                      <label className="text-xs font-black uppercase text-muted-foreground/70 tracking-wider">Gender</label>
                      <select 
                        name="genderId"
                        defaultValue={initialData?.genderId || ""}
                        className="w-full bg-transparent border border-border rounded-lg px-3 py-2 text-sm font-semibold focus:ring-1 focus:ring-primary outline-none"
                      >
                        <option value="">Indicate Gender</option>
                        {lookups.genders.map(g => <option key={g.id} value={g.id}>{g.name}</option>)}
                      </select>
                    </div>

                    <div className="space-y-2">
                      <label className="text-xs font-black uppercase text-muted-foreground/70 tracking-wider">Equity Group</label>
                      <select 
                        name="equityId"
                        defaultValue={initialData?.equityId || ""}
                        className="w-full bg-transparent border border-border rounded-lg px-3 py-2 text-sm font-semibold focus:ring-1 focus:ring-primary outline-none"
                      >
                        <option value="">Indicate Race</option>
                        {lookups.equities.map(e => <option key={e.id} value={e.id}>{e.name}</option>)}
                      </select>
                    </div>

                    <div className="space-y-2">
                      <label className="text-xs font-black uppercase text-muted-foreground/70 tracking-wider">Nationality</label>
                      <select 
                        name="nationalityId"
                        defaultValue={initialData?.nationalityId || ""}
                        className="w-full bg-transparent border border-border rounded-lg px-3 py-2 text-sm font-semibold focus:ring-1 focus:ring-primary outline-none"
                      >
                        {lookups.nationalities.map(n => <option key={n.id} value={n.id}>{n.name}</option>)}
                      </select>
                    </div>
                  </div>
               </div>
            </div>
          </form>
        </TabsContent>

        <TabsContent value="access" className="animate-in zoom-in-95 duration-300">
           <div className="bg-card text-card-foreground p-8 rounded-xl border border-border shadow-sm dark:bg-slate-950/50 max-w-4xl mx-auto space-y-8">
              <div className="flex items-center gap-4 border-b border-border pb-6">
                 <div className="bg-primary/10 p-4 rounded-2xl">
                    <UserIcon className="w-8 h-8 text-primary" />
                 </div>
                 <div>
                    <h3 className="text-2xl font-bold">Authentication Link</h3>
                    <p className="text-muted-foreground">Manage the anchor between this demographic profile and a system login account.</p>
                 </div>
              </div>

              {initialData?.user ? (
                 <div className="flex items-center justify-between p-6 bg-emerald-500/5 border border-emerald-500/20 rounded-2xl">
                    <div className="flex items-center gap-6">
                       <div className="text-center">
                          <p className="text-[10px] font-black uppercase text-emerald-600 tracking-widest">Linked Identity</p>
                          <Avatar className="w-16 h-16 mt-2 border-4 border-white shadow-xl">
                             <AvatarFallback className="bg-emerald-500 text-white font-bold text-xl">{initialData.user.email?.[0].toUpperCase()}</AvatarFallback>
                          </Avatar>
                       </div>
                       <div>
                          <p className="text-xl font-bold">{initialData.user.email}</p>
                          <div className="flex items-center gap-2 mt-1">
                             <Badge className="bg-emerald-500 text-white font-black text-[10px] tracking-widest">ACTIVE SESSION</Badge>
                             <span className="text-xs text-muted-foreground italic font-medium">Mapped to ID: #{initialData.user.id}</span>
                          </div>
                       </div>
                    </div>
                    <button 
                      onClick={handleUnlink}
                      disabled={isPending}
                      className="group flex items-center gap-2 px-6 py-3 rounded-xl border-2 border-red-200 text-red-600 hover:bg-red-600 hover:text-white transition-all font-bold text-sm uppercase"
                    >
                       <X className="w-4 h-4" /> {isPending ? "Detaching..." : "Unlink Profile"}
                    </button>
                 </div>
              ) : (
                 <div className="space-y-6">
                    <div className="text-center py-12 border-2 border-dashed border-border rounded-3xl bg-muted/20">
                       <AlertCircle className="w-12 h-12 text-muted-foreground/30 mx-auto mb-4" />
                       <h4 className="text-lg font-bold">Standalone Demographic Identity</h4>
                       <p className="text-sm text-muted-foreground max-w-sm mx-auto px-4 mt-2">
                         This record exists in the directory but does not currently have permissions to log into the NSD-MS system.
                       </p>
                    </div>
                    
                    <div className="bg-muted p-2 rounded-2xl flex items-center gap-2 shadow-inner">
                       <select 
                         value={selectedUser}
                         onChange={(e) => setSelectedUser(e.target.value)}
                         className="flex-1 bg-transparent px-4 py-3 font-semibold text-sm focus:outline-none"
                       >
                          <option value="">Search Available Accounts...</option>
                          {availableUsers.map(u => (
                            <option key={u.id} value={u.id}>{u.email} ({u.name})</option>
                          ))}
                       </select>
                       <button 
                         onClick={handleLinkUser}
                         disabled={!selectedUser || isPending}
                         className="bg-primary text-primary-foreground px-8 py-3 rounded-xl font-black text-xs uppercase tracking-widest shadow-xl shadow-primary/20 hover:-translate-y-0.5 active:translate-y-0 transition-all disabled:opacity-50"
                       >
                         {isPending ? "Linking..." : "Grant Access"}
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

import { UserIcon, AlertCircle } from "lucide-react";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";

