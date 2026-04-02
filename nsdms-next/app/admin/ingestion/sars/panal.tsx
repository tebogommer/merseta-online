"use client";

import { useState, useEffect } from "react";
import { triggerSarsIngestAction } from "@/app/_actions/ingestion";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Progress } from "@/components/ui/progress";
import { Upload, FileText, CheckCircle2, AlertCircle, Loader2 } from "lucide-react";
import { Badge } from "@/components/ui/badge";

/**
 * SarsIngestionPanel
 * Handles file selection, job triggering, and real-time progress polling.
 */
export function SarsIngestionPanel() {
  const [file, setFile] = useState<File | null>(null);
  const [isUploading, setIsUploading] = useState(false);
  const [jobId, setJobId] = useState<string | null>(null);
  const [status, setStatus] = useState<any>(null);

  // Poll for job status once we have a jobId
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

  const handleUpload = async () => {
    if (!file) return;

    setIsUploading(true);
    setStatus(null);

    // In a real app, we'd upload to S3/Azure first.
    // Here we simulate the trigger with a mock row count.
    const mockFileId = `sars-${Date.now()}`;
    const mockRowCount = 12500; // Simulated large dataset

    const result = await triggerSarsIngestAction(mockFileId, mockRowCount);
    
    if (result.success && result.jobId) {
      setJobId(result.jobId);
    }
    
    setIsUploading(false);
  };

  return (
    <Card className="w-full">
      <CardHeader>
        <CardTitle className="flex items-center gap-2">
          <Upload className="w-5 h-5 text-merseta" />
          SARS Levy Ingestion
        </CardTitle>
        <CardDescription>
          Upload SARS flat files for background processing and employer reconciliation.
        </CardDescription>
      </CardHeader>
      <CardContent className="space-y-6">
        
        {!jobId && (
          <div className="border-2 border-dashed border-muted rounded-lg p-12 text-center space-y-4">
            <div className="mx-auto w-12 h-12 bg-merseta/10 rounded-full flex items-center justify-center">
              <FileText className="w-6 h-6 text-merseta" />
            </div>
            <div className="space-y-1">
              <p className="text-sm font-medium">Click to upload or drag and drop</p>
              <p className="text-xs text-muted-foreground">CSV or TXT (Max 50MB)</p>
            </div>
            <input 
              type="file" 
              className="hidden" 
              id="sars-upload" 
              onChange={(e) => setFile(e.target.files?.[0] || null)}
            />
            <Button 
              variant="outline" 
              onClick={() => document.getElementById("sars-upload")?.click()}
              disabled={isUploading}
            >
              {file ? file.name : "Select File"}
            </Button>
            
            {file && (
              <div className="pt-4">
                <Button 
                   className="w-full bg-merseta hover:bg-merseta-dark"
                   onClick={handleUpload}
                   disabled={isUploading}
                >
                  {isUploading ? <Loader2 className="w-4 h-4 animate-spin mr-2" /> : null}
                  Start Ingestion
                </Button>
              </div>
            )}
          </div>
        )}

        {jobId && status && (
          <div className="space-y-6 animate-in fade-in duration-500">
            <div className="flex items-center justify-between">
              <div className="space-y-1">
                 <p className="text-sm font-semibold">Job ID: {jobId}</p>
                 <Badge variant={status.isCompleted ? "default" : status.isFailed ? "destructive" : "secondary"}>
                   {status.state?.toUpperCase() || "PENDING"}
                 </Badge>
              </div>
              {status.isCompleted && <CheckCircle2 className="w-8 h-8 text-emerald-500" />}
              {status.isFailed && <AlertCircle className="w-8 h-8 text-destructive" />}
            </div>

            <div className="space-y-2">
              <div className="flex justify-between text-xs font-medium">
                <span>Processing Records...</span>
                <span>{status.isCompleted ? "100%" : "In Progress"}</span>
              </div>
              <Progress value={status.isCompleted ? 100 : 45} className="h-2" />
            </div>

            {status.isCompleted && (
              <div className="p-4 bg-emerald-500/10 border border-emerald-500/20 rounded-md">
                 <p className="text-sm text-emerald-700 dark:text-emerald-400 font-medium">
                   Successfully processed {status.result?.processedCount || 0} records.
                 </p>
              </div>
            )}
            
            <Button variant="outline" className="w-full" onClick={() => {setJobId(null); setStatus(null); setFile(null);}}>
              Upload Another File
            </Button>
          </div>
        )}

      </CardContent>
    </Card>
  );
}
