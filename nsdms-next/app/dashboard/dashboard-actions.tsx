"use client";

import { useTransition } from "react";
import { Button } from "@/components/ui/button";
import { Plus, LayoutTemplate, Activity } from "lucide-react";
import { useRouter } from "next/navigation";
import { toast } from "sonner"; // Assuming sonner is the expected toast provider

export default function DashboardActions() {
  const [isPending, startTransition] = useTransition();
  const router = useRouter();

  const handleAction = (label: string, route: string) => {
    startTransition(() => {
      router.push(route);
      toast.success(`${label} flow initiated`);
    });
  };

  return (
    <div className="flex items-center space-x-2">
      <Button variant="outline" size="sm" className="hidden md:flex gap-2" onClick={() => handleAction("New WSP", "/workplace-skills-plans/new")}>
        <LayoutTemplate className="h-4 w-4" />
        New WSP
      </Button>
      <Button variant="outline" size="sm" className="hidden md:flex gap-2" onClick={() => handleAction("Visit", "/activities/visits")}>
        <Activity className="h-4 w-4" />
        Manage Visits
      </Button>
      <Button size="sm" className="gap-2 shadow-md hover:shadow-lg transition-shadow" onClick={() => handleAction("Create Organisation", "/organisations/new")}>
        <Plus className="h-4 w-4" />
        Organisation
      </Button>
    </div>
  );
}
