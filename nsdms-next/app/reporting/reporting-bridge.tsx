"use client";

import { useTransition } from "react";
import { generateExternalExtractAction, simulateSarsIngestionAction } from "./_actions/pipeline";
import { Button } from "@/components/ui/button";

export function ReportingActionBridge() {
  const [isPending, startTransition] = useTransition();

  const handleGenerate = (type: 'SETMIS' | 'NLRD' | 'SAQA') => {
    startTransition(async () => {
      const res = await generateExternalExtractAction(type);
      if (!res.success) alert(res.message);
    });
  };

  const handleSarsInjection = () => {
    startTransition(async () => {
      const res = await simulateSarsIngestionAction();
      if (!res.success) alert(res.message);
    });
  };

  return (
    <div className="flex flex-col gap-4">
      <div className="flex gap-2">
        <Button 
          variant="default" 
          disabled={isPending}
          onClick={() => handleGenerate('SETMIS')}
        >
          Generate SETMIS Extract
        </Button>
        <Button 
          variant="outline" 
          disabled={isPending}
          onClick={() => handleGenerate('NLRD')}
        >
          Generate NLRD Extract
        </Button>
      </div>
      <div className="pt-4 border-t">
        <Button 
          variant="secondary" 
          disabled={isPending}
          onClick={handleSarsInjection}
        >
          Inject Dummy SARS Levy Data
        </Button>
      </div>
    </div>
  );
}
