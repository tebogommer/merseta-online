import { PrismaClient } from "@prisma/client";
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { FileIcon, DownloadIcon } from "lucide-react";
import { Button } from "@/components/ui/button";

const prisma = new PrismaClient();

export async function DocumentPanel({ entityId, entityType }: { entityId: number, entityType: string }) {
  // Query for polymorphic documents based on standard entity mapping
  let whereClause = {};
  if (entityType === "GrantApplication") {
    whereClause = { grantApplicationId: entityId };
  } else if (entityType === "AssessorModeratorApplication") {
    // Note: If Assessor Mod doesn't map directly, you'd add the column. Assuming standard generic filtering or mapping.
    // Assuming for now Assessors don't have polymorphic linkage yet, this will safely return [] for them
    whereClause = { id: -1 }; 
  }

  let documents: any[] = [];
  if (Object.keys(whereClause).length > 0) {
    // @ts-ignore
    documents = await prisma.document.findMany({
      where: whereClause,
      orderBy: { createdAt: 'desc' }
    });
  }

  return (
    <Card>
      <CardHeader>
        <CardTitle>Documents & Files</CardTitle>
        <CardDescription>All generated certificates, reports, and uploaded compliances.</CardDescription>
      </CardHeader>
      <CardContent>
        {documents.length > 0 ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {documents.map((doc) => (
              <div key={doc.id} className="flex flex-col p-4 border rounded-lg bg-card shadow-sm hover:shadow-md transition-shadow">
                <div className="flex items-center gap-3 mb-3">
                  <div className="p-2 bg-primary/10 rounded-md">
                    <FileIcon className="h-6 w-6 text-primary" />
                  </div>
                  <div className="flex-1 overflow-hidden">
                    <p className="text-sm font-medium truncate" title={doc.filename}>{doc.filename}</p>
                    <p className="text-xs text-muted-foreground">{(doc.sizeBytes / 1024).toFixed(1)} KB • {doc.isGenerated ? "Generated" : "Uploaded"}</p>
                  </div>
                </div>
                <Button variant="secondary" size="sm" className="w-full gap-2">
                  <DownloadIcon className="h-4 w-4" /> Download
                </Button>
              </div>
            ))}
          </div>
        ) : (
          <div className="flex flex-col items-center justify-center p-8 border border-dashed rounded-lg">
            <p className="text-sm text-muted-foreground mb-4">No documents linked to this record.</p>
            {/* Real app would have an Uploader button here */}
            <Button variant="outline" size="sm" disabled>Upload File</Button>
          </div>
        )}
      </CardContent>
    </Card>
  );
}
