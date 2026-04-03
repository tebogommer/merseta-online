"use client";

import { useState, useTransition } from "react";
import { Download, Loader2 } from "lucide-react";
import { createComplianceCertificatePDFAction } from "../_actions/pdf";

export function PDFDownloadButton({ orgId }: { orgId: string }) {
  const [isPending, startTransition] = useTransition();

  const handleDownload = () => {
    startTransition(async () => {
      const response = await createComplianceCertificatePDFAction(orgId);
      
      if (response.success && response.data) {
        // Convert Base64 to Blob and trigger download
        const byteCharacters = atob(response.data);
        const byteNumbers = new Array(byteCharacters.length);
        for (let i = 0; i < byteCharacters.length; i++) {
          byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);
        const blob = new Blob([byteArray], { type: 'application/pdf' });
        
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `Certificate_${orgId}.pdf`;
        document.body.appendChild(a);
        a.click();
        
        // cleanup
        URL.revokeObjectURL(url);
        document.body.removeChild(a);
      } else {
        alert(response.error || "Failed to download PDF.");
      }
    });
  };

  return (
    <button 
      onClick={handleDownload} 
      disabled={isPending}
      className="inline-flex items-center gap-1 text-muted-foreground hover:text-green-700 font-medium ml-4 transition-colors disabled:opacity-50"
      title="Download Compliance Certificate"
    >
      {isPending ? <Loader2 className="w-4 h-4 animate-spin" /> : <Download className="w-4 h-4" />}
      <span className="hidden md:inline">PDF</span>
    </button>
  );
}
