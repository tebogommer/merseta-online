"use client";

import { useState, useEffect } from "react";
import { triggerSetmisExtractAction } from "@/app/_actions/ingestion";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Progress } from "@/components/ui/progress";
import { Download, FileDown, CheckCircle2, AlertCircle, Loader2 } from "lucide-react";
import { Badge } from "@/components/ui/badge";

export function SetmisExtractionPanel() {
  const [isExtracting, setIsExtracting] = useState(false);
  const [jobId, setJobId] = useState<string | null>(null);
  const [status, setStatus] = useState<any>(null);

  useEffect(() => {
    if (!jobId || status?.isCompleted || status?.isFailed) return;

    const interval = setInterval(async () => {
      try {
        const res = await fetch(`/api/jobs/${jobId}`);
        const data = await res.json();
        setStatus(data);
        
        if (data.isCompleted || data.isFailed) {
          clearInterval(interval);
        }
      } catch (err) {
        console.error("Polling error:", err);
      }
    }, 2000);

    return () => clearInterval(interval);
  }, [jobId, status]);

  const handleExtract = async () => {
    setIsExtracting(true);
    setStatus(null);

    const result = await triggerSetmisExtractAction("SETMIS_Q1");
    
    if (result.success && result.jobId) {
      setJobId(result.jobId);
    }
    
    setIsExtracting(false);
  };

  return (
    <Card className="w-full">
      <CardHeader>
        <CardTitle className="flex items-center gap-2">
          <Download className="w-5 h-5 text-blue-600" />
          SETMIS Data Extract
        </CardTitle>
        <CardDescription>
          Generate SETMIS specifications (XML) for external reporting and NLRD synchronization.
        </CardDescription>
      </CardHeader>
      <CardContent className="space-y-6">
        
        {!jobId && (
          <div className="border-2 border-dashed border-muted rounded-lg p-12 text-center space-y-4">
            <div className="mx-auto w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center">
              <FileDown className="w-6 h-6 text-blue-600" />
            </div>
            <div className="space-y-1">
              <p className="text-sm font-medium">Click below to start Data Extraction</p>
              <p className="text-xs text-muted-foreground">Will aggregate thousands of records into XML.</p>
            </div>
            
            <div className="pt-4">
              <Button 
                  className="w-full bg-blue-600 hover:bg-blue-700"
                  onClick={handleExtract}
                  disabled={isExtracting}
              >
                {isExtracting ? <Loader2 className="w-4 h-4 animate-spin mr-2" /> : null}
                Generate Extract
              </Button>
            </div>
          </div>
        )}

        {jobId && status && (
          <div className="space-y-6 animate-in fade-in duration-500">
            <div className="flex items-center justify-between">
              <div className="space-y-1">
                 <p className="text-sm font-semibold">Extract Job ID: {jobId}</p>
                 <Badge variant={status.isCompleted ? "default" : status.isFailed ? "destructive" : "secondary"}>
                   {status.state?.toUpperCase() || "PENDING"}
                 </Badge>
              </div>
              {status.isCompleted && <CheckCircle2 className="w-8 h-8 text-emerald-500" />}
              {status.isFailed && <AlertCircle className="w-8 h-8 text-destructive" />}
            </div>

            <div className="space-y-2">
              <div className="flex justify-between text-xs font-medium">
                <span>Aggregating Entities...</span>
                <span>{status.isCompleted ? "100%" : "In Progress"}</span>
              </div>
              <Progress value={status.isCompleted ? 100 : 45} className="h-2" />
            </div>

            {status.isCompleted && (
              <div className="p-4 bg-emerald-500/10 border border-emerald-500/20 rounded-md space-y-3">
                 <p className="text-sm text-emerald-700 dark:text-emerald-400 font-medium">
                   Successfully processed {status.result?.processedCount || 0} records into the payload.
                 </p>
                 <Button variant="default" className="w-full bg-emerald-600 hover:bg-emerald-700">
                    <Download className="w-4 h-4 mr-2" /> Download XML Bundle
                 </Button>
              </div>
            )}
            
            <Button variant="outline" className="w-full" onClick={() => {setJobId(null); setStatus(null);}}>
              Run Another Extract
            </Button>
          </div>
        )}

      </CardContent>
    </Card>
  );
}
