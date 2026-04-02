"use client";

import { useTransition } from "react";
import { Button } from "@/components/ui/button";
import { processApprovalTransition } from "../_actions/workflow";

// Local minimal types since we are just bridging to XState
type Status = "DRAFT" | "UNDER_REVIEW" | "APPROVED" | "REJECTED" | string;

export default function EtqaActionBridge({ 
  approvalId, 
  status, 
  canReview 
}: { 
  approvalId: number, 
  status: Status, 
  canReview: boolean 
}) {
  const [isPending, startTransition] = useTransition();

  const handleFire = (eventType: 'SUBMIT' | 'APPROVE' | 'REJECT' | 'RETURN_TO_DRAFT') => {
      startTransition(async () => {
          let event: any = { type: eventType };
          if (eventType === 'REJECT') {
              event = { type: 'REJECT', reason: "System generic rejection based on QA." };
          }
          await processApprovalTransition(approvalId, event);
      });
  };

  return (
    <div className="flex gap-3">
       {/* Allowed during DRAFT state */}
       <Button 
          variant="outline" 
          disabled={status !== 'DRAFT' || isPending}
          onClick={() => handleFire('SUBMIT')}
       >
          {isPending && status === 'DRAFT' ? "Pushing State..." : "Submit for QA Review"}
       </Button>

       {/* Admin Review Commands (Visually locked mathematically by XState & Boolean logic) */}
       {canReview && (
         <div className="flex gap-2 border-l pl-3 ml-2 border-slate-200">
           <Button 
              className="bg-merseta-highlight text-white hover:bg-merseta-dark"
              disabled={status !== 'UNDER_REVIEW' || isPending}
              onClick={() => handleFire('APPROVE')}
           >
             Approve Provider
           </Button>
           
           <Button 
              variant="destructive"
              disabled={status !== 'UNDER_REVIEW' || isPending}
              onClick={() => handleFire('REJECT')}
           >
             Reject Default
           </Button>

           <Button 
              variant="secondary"
              disabled={(status !== 'UNDER_REVIEW' && status !== 'REJECTED') || isPending}
              onClick={() => handleFire('RETURN_TO_DRAFT')}
           >
             Return to Sender
           </Button>
         </div>
       )}
    </div>
  );
}
