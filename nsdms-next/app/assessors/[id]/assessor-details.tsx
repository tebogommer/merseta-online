import Link from "next/link";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { AssessorActionsBridge } from "./assessor-actions";
import { Suspense } from "react";
import { WorkflowHistory } from "@/components/workflow-history";
import { DocumentPanel } from "@/components/document-panel";

export default function AssessorDetails({ record }: { record: any }) {
  return (
    <div className="space-y-6">
      {/* Top Bar / Breadcrumb Layout */}
      <div className="flex items-center justify-between sticky top-0 bg-background/95 backdrop-blur z-10 py-4 border-b">
        <div className="flex items-center gap-4">
          <Link href="/assessors" className="text-muted-foreground hover:text-foreground text-sm font-medium">
            &larr; Back to List
          </Link>
          <div className="h-4 w-px bg-border" />
          <h1 className="text-2xl font-semibold tracking-tight">
            Registration #{record.id}
          </h1>
          <Badge variant={record.status === 'APPROVED' ? 'default' : 'secondary'}>
            {record.status}
          </Badge>
        </div>
        <AssessorActionsBridge id={record.id} currentStatus={record.status} />
      </div>

      {/* Main Stacked Detail View */}
      <Tabs defaultValue="general" className="w-full">
        <TabsList className="mb-4">
          <TabsTrigger value="general">Application Details</TabsTrigger>
          <TabsTrigger value="scope">Extensions of Scope ({record.extensionsOfScope?.length || 0})</TabsTrigger>
          <TabsTrigger value="documents">Documents</TabsTrigger>
          <TabsTrigger value="audit">Audit Log</TabsTrigger>
        </TabsList>

        <TabsContent value="general" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>Role Summary</CardTitle>
            </CardHeader>
            <CardContent className="grid gap-4 md:grid-cols-2">
              <div className="space-y-1">
                <p className="text-sm font-medium text-muted-foreground">Registration Type</p>
                <p className="font-semibold">{record.applicationType}</p>
              </div>
              <div className="space-y-1">
                <p className="text-sm font-medium text-muted-foreground">User Email</p>
                <p>{record.user?.email}</p>
              </div>
              <div className="space-y-1">
                <p className="text-sm font-medium text-muted-foreground">User Name</p>
                <p>{record.user?.name || "N/A"}</p>
              </div>
              <div className="space-y-1">
                <p className="text-sm font-medium text-muted-foreground">Created Date</p>
                <p>{new Date(record.createdAt).toLocaleDateString()}</p>
              </div>
            </CardContent>
          </Card>

          <Card>
            <CardHeader>
              <CardTitle>Associated Training Provider</CardTitle>
            </CardHeader>
            <CardContent>
              {record.trainingProvider ? (
                <div className="space-y-1">
                    <p className="text-sm font-medium text-muted-foreground">Provider Organisation</p>
                    <p>{record.trainingProvider.organisation?.organisationName}</p>
                    <p className="text-sm text-muted-foreground">Accreditation Number: {record.trainingProvider.accreditationNumber}</p>
                </div>
              ) : (
                <p className="text-sm text-muted-foreground">Independent - Not strictly linked to an SDP.</p>
              )}
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="extensions">
          <Card>
            <CardHeader>
              <CardTitle>Qualifying Scope Extensions</CardTitle>
            </CardHeader>
            <CardContent>
              {record.extensionsOfScope && record.extensionsOfScope.length > 0 ? (
                <div className="space-y-4">
                  {record.extensionsOfScope.map((ext: any) => (
                    <div key={ext.id} className="border p-4 rounded-md flex justify-between items-center">
                      <div>
                        <div className="flex gap-2 items-center mb-1">
                          <span className="font-medium">Scope Request #{ext.id}</span>
                          <Badge variant="outline">{ext.status}</Badge>
                        </div>
                        <p className="text-sm text-muted-foreground">Qual Code: {ext.qualificationCode}</p>
                      </div>
                    </div>
                  ))}
                </div>
              ) : (
                <p className="text-sm text-muted-foreground">No extensions requested yet.</p>
              )}
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="documents">
           <Suspense fallback={<div className="h-40 border rounded-md animate-pulse p-4">Loading Documents...</div>}>
             <DocumentPanel entityId={record.id} entityType="AssessorModeratorApplication" />
           </Suspense>
        </TabsContent>

        <TabsContent value="audit">
           <Suspense fallback={<div className="h-64 border rounded-md animate-pulse p-4">Loading Audit Timeline...</div>}>
             <WorkflowHistory entityId={record.id} entityName="AssessorModeratorApplication" />
           </Suspense>
        </TabsContent>
        
      </Tabs>
    </div>
  );
}
