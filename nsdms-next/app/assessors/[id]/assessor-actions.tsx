"use client";

import { useTransition } from "react";
import { transitionAssessorStatusAction } from "../_actions/workflow";
import { Button } from "@/components/ui/button";

export function AssessorActionsBridge({ id, currentStatus }: { id: number, currentStatus: string }) {
  const [isPending, startTransition] = useTransition();

  const handleStatusChange = (event: 'SUBMIT' | 'COMMENCE_REVIEW' | 'APPROVE' | 'REJECT' | 'APPEAL' | 'REVERT_TO_DRAFT') => {
    startTransition(async () => {
      const res = await transitionAssessorStatusAction(id, event);
      if (!res.success) {
        alert(res.message);
      }
    });
  };

  return (
    <div className="flex gap-2">
      <Button 
        variant="outline" 
        size="sm"
        disabled={isPending || currentStatus !== 'DRAFT'}
        onClick={() => handleStatusChange('SUBMIT')}
      >
        Submit Application
      </Button>

      {/* Example ETQA buttons */}
      <Button 
        variant="default" 
        size="sm"
        disabled={isPending || currentStatus === 'APPROVED'}
        onClick={() => handleStatusChange('APPROVE')}
      >
        Approve
      </Button>
      <Button 
        variant="destructive" 
        size="sm"
        disabled={isPending || currentStatus === 'REJECTED'}
        onClick={() => handleStatusChange('REJECT')}
      >
        Reject
      </Button>
    </div>
  );
}
