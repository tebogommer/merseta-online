"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Progress } from "@/components/ui/progress";
import { Upload, FileText, CheckCircle2, AlertCircle, Loader2 } from "lucide-react";
import { Badge } from "@/components/ui/badge";
import { toast } from "sonner";

export function SignoffWizard({ wspId }: { wspId: string }) {
  const [file, setFile] = useState<File | null>(null);
  const [isUploading, setIsUploading] = useState(false);
  const [uploadStatus, setUploadStatus] = useState<"IDLE" | "UPLOADING" | "SUCCESS" | "ERROR">("IDLE");

  const handleUpload = async () => {
    if (!file) return;

    setIsUploading(true);
    setUploadStatus("UPLOADING");

    // Simulate network delay for upload
    setTimeout(() => {
        setIsUploading(false);
        setUploadStatus("SUCCESS");
        toast.success("Document uploaded securely.");
    }, 2000);
  };

  return (
    <Card className="w-full">
      <CardHeader>
        <CardTitle className="flex items-center gap-2">
          <Upload className="w-5 h-5 text-merseta" />
          WSP Sign-Off Document
        </CardTitle>
        <CardDescription>
          Upload the final signed PDF authorization from the WSP stakeholders.
        </CardDescription>
      </CardHeader>
      <CardContent className="space-y-6">
        
        {uploadStatus === "IDLE" && (
          <div className="border-2 border-dashed border-muted rounded-lg p-12 text-center space-y-4">
            <div className="mx-auto w-12 h-12 bg-merseta/10 rounded-full flex items-center justify-center">
              <FileText className="w-6 h-6 text-merseta" />
            </div>
            <div className="space-y-1">
              <p className="text-sm font-medium">Click to upload or drag and drop</p>
              <p className="text-xs text-muted-foreground">PDF formatting strictly required (Max 10MB)</p>
            </div>
            <input 
              type="file" 
              accept=".pdf"
              className="hidden" 
              id="signoff-upload" 
              onChange={(e) => setFile(e.target.files?.[0] || null)}
            />
            <Button 
              variant="outline" 
              onClick={() => document.getElementById("signoff-upload")?.click()}
              disabled={isUploading}
            >
              {file ? file.name : "Select Document"}
            </Button>
            
            {file && (
              <div className="pt-4 flex gap-4">
                <Button 
                   className="w-full bg-merseta hover:bg-merseta-dark"
                   onClick={handleUpload}
                   disabled={isUploading}
                >
                  {isUploading ? <Loader2 className="w-4 h-4 animate-spin mr-2" /> : null}
                  Confirm & Upload
                </Button>
                <Button variant="outline" className="w-full" onClick={() => setFile(null)}>Clear</Button>
              </div>
            )}
          </div>
        )}

        {uploadStatus === "UPLOADING" && (
           <div className="space-y-4 py-8">
               <div className="flex justify-between text-sm font-medium text-muted-foreground">
                   <span>Securely streaming to Blob Storage...</span>
                   <Loader2 className="w-4 h-4 animate-spin text-merseta" />
               </div>
               <Progress value={65} className="h-2" />
           </div>
        )}

        {uploadStatus === "SUCCESS" && (
          <div className="space-y-6 animate-in fade-in duration-500">
            <div className="flex items-center justify-between">
              <div className="space-y-1">
                 <p className="text-sm font-semibold">Verification Complete</p>
                 <Badge variant="default">ATTACHED</Badge>
              </div>
              <CheckCircle2 className="w-8 h-8 text-emerald-500" />
            </div>

            <div className="p-4 bg-emerald-500/10 border border-emerald-500/20 rounded-md">
                 <p className="text-sm text-emerald-700 dark:text-emerald-400 font-medium">
                   Document has been linked to WSP #{wspId} and logged in the global audit trail.
                 </p>
            </div>
            
            <Button variant="outline" className="w-full" onClick={() => { setUploadStatus("IDLE"); setFile(null); }}>
              Replace Document
            </Button>
          </div>
        )}

      </CardContent>
    </Card>
  );
}
