"use client";

import { useTransition, useState } from "react";
import { transitionGrantStatusAction } from "../_actions/workflow";
import { generateGrantCertificatePDF } from "../../_actions/document";
import { Button } from "@/components/ui/button";

export function GrantActionsBridge({ id, currentStatus }: { id: number, currentStatus: string }) {
  const [isPending, startTransition] = useTransition();

  const handleStatusChange = (event: 'SUBMIT' | 'COMMENCE_REVIEW' | 'APPROVE' | 'REJECT' | 'APPEAL' | 'REVERT_TO_DRAFT') => {
    startTransition(async () => {
      const res = await transitionGrantStatusAction(id, event);
      if (!res.success) {
        alert(res.message);
      }
    });
  };

  const handleDownloadPDF = () => {
    startTransition(async () => {
      const res = await generateGrantCertificatePDF(id);
      if (res.success && res.base64) {
        // Trigger generic browser download for PDF buffer
        const linkSource = `data:application/pdf;base64,${res.base64}`;
        const downloadLink = document.createElement("a");
        downloadLink.href = linkSource;
        downloadLink.download = res.filename || `document_${id}.pdf`;
        downloadLink.click();
      } else {
        alert(res.message || "Failed to generate PDF");
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
        Submit Grant
      </Button>

      {/* Example ETQA/Admin buttons. In a real app we would wrap these in a CASL UI check */}
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

      {/* Playwright Document Generator */}
      {currentStatus === 'APPROVED' && (
          <Button 
            variant="secondary" 
            size="sm"
            disabled={isPending}
            onClick={handleDownloadPDF}
            className="ml-4"
          >
            Download PDF Certificate
          </Button>
      )}
    </div>
  );
}
