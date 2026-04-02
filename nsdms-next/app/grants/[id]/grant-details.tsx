import Link from "next/link";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { GrantActionsBridge } from "./grant-actions";
import { Suspense } from "react";
import { WorkflowHistory } from "@/components/workflow-history";
import { DocumentPanel } from "@/components/document-panel";

export default function GrantDetails({ record }: { record: any }) {
  return (
    <div className="space-y-6">
      {/* Top Bar / Breadcrumb Layout */}
      <div className="flex items-center justify-between sticky top-0 bg-background/95 backdrop-blur z-10 py-4 border-b">
        <div className="flex items-center gap-4">
          <Link href="/grants" className="text-muted-foreground hover:text-foreground text-sm font-medium">
            &larr; Back to Grants
          </Link>
          <div className="h-4 w-px bg-border" />
          <h1 className="text-2xl font-semibold tracking-tight">
            Grant #{record.id}
          </h1>
          <Badge variant={record.status === 'APPROVED' ? 'default' : 'secondary'}>
            {record.status}
          </Badge>
        </div>
        <GrantActionsBridge id={record.id} currentStatus={record.status} />
      </div>

      {/* Main Stacked Detail View */}
      <Tabs defaultValue="general" className="w-full">
        <TabsList className="mb-4">
          <TabsTrigger value="general">General Details</TabsTrigger>
          <TabsTrigger value="verifications">Verifications ({record.verifications?.length || 0})</TabsTrigger>
          <TabsTrigger value="payments">Payment Requests ({record.payments?.length || 0})</TabsTrigger>
          <TabsTrigger value="documents">Documents</TabsTrigger>
          <TabsTrigger value="audit">Audit Log</TabsTrigger>
        </TabsList>

        <TabsContent value="general" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>Overview</CardTitle>
            </CardHeader>
            <CardContent className="grid gap-4 md:grid-cols-2">
              <div className="space-y-1">
                <p className="text-sm font-medium text-muted-foreground">Grant Type</p>
                <p>{record.grantType}</p>
              </div>
              <div className="space-y-1">
                <p className="text-sm font-medium text-muted-foreground">Financial Year</p>
                <p>{record.finYear}</p>
              </div>
              <div className="space-y-1">
                <p className="text-sm font-medium text-muted-foreground">Amount Requested</p>
                <p>{record.amountRequested != null ? `R ${record.amountRequested}` : "Not Specified"}</p>
              </div>
              <div className="space-y-1">
                <p className="text-sm font-medium text-muted-foreground">Amount Approved</p>
                <p>{record.amountApproved != null ? `R ${record.amountApproved}` : "Pending"}</p>
              </div>
            </CardContent>
          </Card>

          <Card>
            <CardHeader>
              <CardTitle>Organisation Anchor</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-1">
                <p className="text-sm font-medium text-muted-foreground">Company</p>
                <p>{record.organisation?.organisationName}</p>
                <p className="text-sm text-muted-foreground">SDL: {record.organisation?.sdlNumber || "N/A"}</p>
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="verifications">
          <Card>
            <CardHeader>
              <CardTitle>Secondary Verification Steps</CardTitle>
            </CardHeader>
            <CardContent>
              {record.verifications && record.verifications.length > 0 ? (
                <div className="space-y-4">
                  {record.verifications.map((v: any) => (
                    <div key={v.id} className="border p-4 rounded-md">
                      <div className="flex gap-2 items-center mb-2">
                        <span className="font-medium">Verification #{v.id}</span>
                        <Badge variant="outline">{v.status}</Badge>
                      </div>
                      <p className="text-sm text-muted-foreground">{v.reviewerNotes || "No notes."}</p>
                    </div>
                  ))}
                </div>
              ) : (
                <p className="text-sm text-muted-foreground">No verifications logged yet.</p>
              )}
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="payments">
            <Card>
            <CardHeader>
              <CardTitle>Tranche Payments</CardTitle>
            </CardHeader>
            <CardContent>
              {record.payments && record.payments.length > 0 ? (
                <div className="space-y-4">
                  {record.payments.map((p: any) => (
                    <div key={p.id} className="border p-4 rounded-md flex justify-between">
                      <div>
                        <div className="flex gap-2 items-center mb-1">
                          <span className="font-medium">Tranche {p.trancheNumber}</span>
                          <Badge variant="outline">{p.status}</Badge>
                        </div>
                      </div>
                      <div className="text-right font-medium">
                        R {p.amount}
                      </div>
                    </div>
                  ))}
                </div>
              ) : (
                <p className="text-sm text-muted-foreground">No payment requests mapped yet.</p>
              )}
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="documents">
           <Suspense fallback={<div className="h-40 border rounded-md animate-pulse p-4">Loading Documents...</div>}>
             <DocumentPanel entityId={record.id} entityType="GrantApplication" />
           </Suspense>
        </TabsContent>

        <TabsContent value="audit">
           <Suspense fallback={<div className="h-64 border rounded-md animate-pulse p-4">Loading Audit Timeline...</div>}>
             <WorkflowHistory entityId={record.id} entityName="GrantApplication" />
           </Suspense>
        </TabsContent>
        
      </Tabs>
    </div>
  );
}
