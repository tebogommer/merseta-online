"use client";

import { useTransition } from "react";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { updateProviderAccreditation, logSiteVisit } from "../_actions/workflow";
import { Loader2, Calendar, CheckCircle, ShieldAlert } from "lucide-react";

interface ProviderActionsProps {
  provider: any;
}

export function ProviderActionsBridge({ provider }: ProviderActionsProps) {
  const [isPending, startTransition] = useTransition();

  const handleApprove = () => {
    startTransition(async () => {
      // Logic for formal accreditation
      await updateProviderAccreditation(provider.id, {
        accreditationNumber: `MER-${Math.floor(Math.random() * 100000)}`,
        startDate: new Date(),
        expiryDate: new Date(new Date().setFullYear(new Date().getFullYear() + 3)),
        status: "ACTIVE",
      });
    });
  };

  const handleSiteVisit = () => {
    startTransition(async () => {
      await logSiteVisit(provider.id, {
        visitDate: new Date(),
        recommendation: "APPROVED",
        auditorId: 1, // System default
      });
    });
  };

  return (
    <div className="flex items-center gap-3">
      {provider.status !== "ACTIVE" && (
        <Button 
          variant="default" 
          onClick={handleApprove}
          disabled={isPending}
          className="bg-emerald-600 hover:bg-emerald-700"
        >
          {isPending ? <Loader2 className="mr-2 h-4 w-4 animate-spin" /> : <CheckCircle className="mr-2 h-4 w-4" />}
          Approve Accreditation
        </Button>
      )}

      <Button 
        variant="outline" 
        onClick={handleSiteVisit}
        disabled={isPending}
      >
        {isPending ? <Loader2 className="mr-2 h-4 w-4 animate-spin" /> : <Calendar className="mr-2 h-4 w-4" />}
        Log Site Visit
      </Button>

      {provider.status === "ACTIVE" && (
        <Badge variant="outline" className="text-emerald-600 border-emerald-600 px-3 py-1">
          <ShieldAlert className="mr-2 h-3 w-3" />
          ETQA Compliant
        </Badge>
      )}
    </div>
  );
}
