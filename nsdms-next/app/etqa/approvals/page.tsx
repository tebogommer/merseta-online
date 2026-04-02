import { fetchApprovals, processApprovalTransition } from "../_actions/workflow";
import { auth } from "@/auth";
import { defineAbilitiesFor } from "@/lib/abilities";
import { notFound } from "next/navigation";
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import EtqaActionBridge from "./etqa-action-bridge";

export default async function EtqaApprovalsDashboard() {
  const session = await auth();
  const ability = defineAbilitiesFor(session?.user);

  // 1. Boundary Defense (CASL)
  if (!ability.can("read", "TrainingProvider")) {
    notFound(); 
  }

  // 2. Hydrate from DB
  const approvals = await fetchApprovals();
  const canReview = ability.can("review_etqa", "WorkplaceApproval");

  return (
    <div className="container mx-auto py-8">
      <h1 className="text-3xl font-bold mb-6 text-merseta-darkest">ETQA Provider Approvals</h1>
      <p className="text-muted-foreground mb-8">
        Rigid State-Machine powered review matrix. Approvals flow linearly: DRAFT → UNDER_REVIEW → APPROVED.
      </p>

      <div className="flex flex-col gap-4">
        {approvals.map((approval) => (
          <Card key={approval.id} className="w-full">
            <CardHeader className="pb-2">
              <div className="flex justify-between items-start">
                <div>
                  <CardTitle className="text-xl">
                    Provider: {approval.trainingProvider?.organisation?.organisationName || "Unknown Org"}
                  </CardTitle>
                  <CardDescription>Qual Code: {approval.qualificationCode}</CardDescription>
                </div>
                <Badge variant={
                  approval.status === 'DRAFT' ? 'outline' :
                  approval.status === 'UNDER_REVIEW' ? 'default' :
                  approval.status === 'APPROVED' ? 'default' : 'destructive'
                } className={approval.status === 'APPROVED' ? 'bg-merseta-highlight text-white' : ''}>
                  {approval.status}
                </Badge>
              </div>
            </CardHeader>
            <CardContent>
               <div className="mt-4">
                  <EtqaActionBridge 
                    approvalId={approval.id} 
                    status={approval.status} 
                    canReview={canReview} 
                  />
               </div>
            </CardContent>
          </Card>
        ))}

        {approvals.length === 0 && (
          <p className="text-center text-muted-foreground mt-8">No Provider Approvals exist in the system.</p>
        )}
      </div>
    </div>
  );
}
