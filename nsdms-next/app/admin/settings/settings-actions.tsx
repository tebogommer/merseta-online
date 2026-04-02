"use client";

import { useTransition } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { updateHostingCompany, updateSystemSetting } from "./_actions/workflow";
import { Loader2, Save } from "lucide-react";

interface SettingsActionsProps {
  hostingCompany: any;
  systemSettings: any[];
}

export function SettingsActionsBridge({ hostingCompany, systemSettings }: SettingsActionsProps) {
  const [isPending, startTransition] = useTransition();

  const handleUpdateHC = (formData: FormData) => {
    startTransition(async () => {
      await updateHostingCompany({
        id: hostingCompany.id,
        companyName: formData.get("companyName") as string,
        companyRegNumber: formData.get("companyRegNumber") as string,
        vatNumber: formData.get("vatNumber") as string,
        incomeTaxNumber: formData.get("incomeTaxNumber") as string,
      });
    });
  };

  const handleUpdateSetting = (key: string, value: string) => {
    startTransition(async () => {
      await updateSystemSetting(key, value);
    });
  };

  return (
    <div className="space-y-8">
      {/* Hosting Company Section */}
      <section className="p-6 border rounded-lg bg-card">
        <h2 className="text-xl font-bold mb-4">Hosting Company Identity</h2>
        <form action={handleUpdateHC} className="grid grid-cols-2 gap-4">
          <div className="space-y-2">
            <Label htmlFor="companyName">Company Name</Label>
            <Input id="companyName" name="companyName" defaultValue={hostingCompany.companyName} required />
          </div>
          <div className="space-y-2">
            <Label htmlFor="companyRegNumber">Registration Number</Label>
            <Input id="companyRegNumber" name="companyRegNumber" defaultValue={hostingCompany.companyRegNumber} required />
          </div>
          <div className="space-y-2">
            <Label htmlFor="vatNumber">VAT Number</Label>
            <Input id="vatNumber" name="vatNumber" defaultValue={hostingCompany.vatNumber || ""} />
          </div>
          <div className="space-y-2">
            <Label htmlFor="incomeTaxNumber">Tax Number</Label>
            <Input id="incomeTaxNumber" name="incomeTaxNumber" defaultValue={hostingCompany.incomeTaxNumber || ""} />
          </div>
          <div className="col-span-2 pt-4 flex justify-end">
            <Button type="submit" disabled={isPending}>
              {isPending ? <Loader2 className="mr-2 h-4 w-4 animate-spin" /> : <Save className="mr-2 h-4 w-4" />}
              Save Identity
            </Button>
          </div>
        </form>
      </section>

      {/* Global Settings Section */}
      <section className="p-6 border rounded-lg bg-card">
        <h2 className="text-xl font-bold mb-4">System Constants</h2>
        <div className="space-y-4">
          {systemSettings.map((setting) => (
            <div key={setting.key} className="flex items-center gap-4 border-b pb-4 last:border-0 last:pb-0">
              <div className="flex-1">
                <Label className="font-mono text-xs">{setting.key}</Label>
                <p className="text-sm text-muted-foreground">{setting.description}</p>
              </div>
              <Input
                className="w-48"
                defaultValue={setting.value}
                onBlur={(e) => handleUpdateSetting(setting.key, e.target.value)}
                disabled={isPending}
              />
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}
