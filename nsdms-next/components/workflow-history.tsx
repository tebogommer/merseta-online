import { PrismaClient } from "@prisma/client";
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";

const prisma = new PrismaClient();

export async function WorkflowHistory({ entityId, entityName }: { entityId: number, entityName: string }) {
  // Query all audit logs tied to this specific entity record
  const history = await prisma.auditLog.findMany({
    where: {
      recordId: entityId,
      entityName: entityName,
    },
    orderBy: {
      createdAt: 'desc'
    }
  });

  return (
    <Card>
      <CardHeader>
        <CardTitle>Audit & Workflow History</CardTitle>
        <CardDescription>Comprehensive timeline of all state transitions and record mutations.</CardDescription>
      </CardHeader>
      <CardContent>
        {history.length > 0 ? (
          <div className="space-y-4 border-l-2 border-muted pl-4 ml-2">
            {history.map((log) => (
              <div key={log.id} className="relative">
                {/* Timeline node dot */}
                <div className="absolute -left-[21px] top-1 h-3 w-3 rounded-full bg-primary ring-4 ring-background" />
                <div className="flex flex-col mb-1">
                  <div className="flex items-center gap-2">
                    <span className="font-semibold text-sm">{log.actionName}</span>
                    <Badge variant="outline" className="text-xs">{log.actor}</Badge>
                  </div>
                  <span className="text-xs text-muted-foreground mt-0.5">
                    {log.createdAt.toLocaleString()}
                  </span>
                </div>
                {/* Expandable snapshot diff could go here */}
                {log.snapshot && log.snapshot !== "{}" && (
                   <pre className="text-[10px] bg-muted p-2 rounded-md mt-2 overflow-x-auto">
                     {log.snapshot}
                   </pre>
                )}
              </div>
            ))}
          </div>
        ) : (
          <div className="text-sm text-muted-foreground text-center py-4">
            No audit logs have been recorded for this entity yet.
          </div>
        )}
      </CardContent>
    </Card>
  );
}
