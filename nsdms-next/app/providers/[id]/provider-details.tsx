"use client";

import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";
import { DocumentPanel } from "@/components/document-panel";
import { WorkflowHistory } from "@/components/workflow-history";
import { ProviderActionsBridge } from "./provider-actions";
import { format } from "date-fns";

interface ProviderDetailsViewProps {
  provider: any; // Type fetched from Smart Shell
}

export function ProviderDetailsView({ provider }: ProviderDetailsViewProps) {
  return (
    <Tabs defaultValue="general" className="w-full">
      <div className="flex items-center justify-between mb-4">
        <TabsList className="bg-muted/50 p-1">
          <TabsTrigger value="general">General Information</TabsTrigger>
          <TabsTrigger value="accreditation">Accreditation</TabsTrigger>
          <TabsTrigger value="site-visits">Site Visits</TabsTrigger>
          <TabsTrigger value="documents">Documents</TabsTrigger>
          <TabsTrigger value="audit">Audit Log</TabsTrigger>
        </TabsList>

        <ProviderActionsBridge provider={provider} />
      </div>

      {/* 1. General Info Tab */}
      <TabsContent value="general">
        <Card className="shadow-lg border-2">
          <CardHeader>
            <CardTitle className="text-xl font-bold">SDP Profile</CardTitle>
          </CardHeader>
          <CardContent className="grid grid-cols-2 gap-8">
            <div className="space-y-4">
              <div>
                <dt className="text-xs font-bold uppercase text-muted-foreground tracking-widest">Legal Name</dt>
                <dd className="text-lg font-semibold">{provider.organisation?.organisationName}</dd>
              </div>
              <div>
                <dt className="text-xs font-bold uppercase text-muted-foreground tracking-widest">SDL Number</dt>
                <dd className="font-mono text-primary font-bold">{provider.organisation?.sdlNumber || "N/A"}</dd>
              </div>
            </div>
            <div className="space-y-4 border-l pl-8">
              <div>
                <dt className="text-xs font-bold uppercase text-muted-foreground tracking-widest">Provider Type</dt>
                <dd className="text-lg">{provider.providerType?.name || "Unclassified"}</dd>
              </div>
              <div>
                <dt className="text-xs font-bold uppercase text-muted-foreground tracking-widest">Provider Class</dt>
                <dd className="text-lg">{provider.providerClass?.name || "Unclassified"}</dd>
              </div>
            </div>
          </CardContent>
        </Card>
      </TabsContent>

      {/* 2. Accreditation Tab */}
      <TabsContent value="accreditation">
        <Card className="shadow-lg border-2">
          <CardHeader>
            <CardTitle className="text-xl font-bold">Accreditation Lifecycle</CardTitle>
          </CardHeader>
          <CardContent className="space-y-6">
            <div className="flex items-center gap-4 p-4 bg-muted rounded-lg">
              <div className="flex-1">
                <dt className="text-xs font-bold text-muted-foreground uppercase">Current Status</dt>
                <dd className="text-2xl font-extrabold uppercase tracking-tight">{provider.status}</dd>
              </div>
              <Badge 
                variant={provider.status === "ACTIVE" ? "default" : "secondary"}
                className={provider.status === "ACTIVE" ? "bg-emerald-600 text-white" : ""}
              >
                {provider.status}
              </Badge>
            </div>

            <div className="grid grid-cols-3 gap-6 pt-4 border-t">
              <div>
                <dt className="text-xs font-bold text-muted-foreground uppercase">Start Date</dt>
                <dd className="text-lg">{provider.startDate ? format(new Date(provider.startDate), "dd MMM yyyy") : "N/A"}</dd>
              </div>
              <div>
                <dt className="text-xs font-bold text-muted-foreground uppercase">Expiry Date</dt>
                <dd className="text-lg font-bold">{provider.expiryDate ? format(new Date(provider.expiryDate), "dd MMM yyyy") : "N/A"}</dd>
              </div>
              <div>
                <dt className="text-xs font-bold text-muted-foreground uppercase">Accredition Number</dt>
                <dd className="text-lg font-mono">{provider.accreditationNumber || "SYSTEM_PENDING"}</dd>
              </div>
            </div>
          </CardContent>
        </Card>
      </TabsContent>

      {/* 3. Site Visits Tab */}
      <TabsContent value="site-visits">
        <Card className="shadow-lg border-2">
          <CardHeader>
            <CardTitle className="text-xl font-bold">ETQA Monitoring History</CardTitle>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Visit Date</TableHead>
                  <TableHead>Report Date</TableHead>
                  <TableHead>Auditor</TableHead>
                  <TableHead className="text-right">Recommendation</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {provider.siteVisits?.length === 0 ? (
                  <TableRow>
                    <TableCell colSpan={4} className="text-center py-6 italic text-muted-foreground">
                      No site visits logged for this provider.
                    </TableCell>
                  </TableRow>
                ) : (
                  provider.siteVisits.map((visit: any) => (
                    <TableRow key={visit.id}>
                      <TableCell className="font-semibold">{format(new Date(visit.visitDate), "dd MMM yyyy")}</TableCell>
                      <TableCell>{visit.reportDate ? format(new Date(visit.reportDate), "dd MMM yyyy") : "PENDING"}</TableCell>
                      <TableCell>Auditor ID: {visit.auditorId || "SYSTEM"}</TableCell>
                      <TableCell className="text-right">
                        <Badge variant={visit.recommendation === "APPROVED" ? "default" : "secondary"}>
                          {visit.recommendation || "IN_REVIEW"}
                        </Badge>
                      </TableCell>
                    </TableRow>
                  ))
                )}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </TabsContent>

      {/* 4. Documents Tab */}
      <TabsContent value="documents">
        <DocumentPanel 
          entityId={provider.id}
          entityType="TrainingProvider"
        />
      </TabsContent>

      {/* 5. Audit Log Tab */}
      <TabsContent value="audit">
        <WorkflowHistory entityId={provider.id} entityName="TrainingProvider" />
      </TabsContent>
    </Tabs>
  );
}
