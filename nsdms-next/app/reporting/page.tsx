import { PrismaClient } from "@prisma/client";
import { ReportingActionBridge } from "./reporting-bridge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";

const prisma = new PrismaClient();

export default async function ReportingDashboardPage() {
  // Aggregate Metrics
  const totalLevyAggregation = await prisma.sarsLevyDetail.aggregate({
    _sum: { amount: true },
    _count: true
  });

  const extractHistory = await prisma.reportingExtract.findMany({
    orderBy: { createdAt: 'desc' },
    take: 15,
    include: {
      documents: true
    }
  });

  return (
    <div className="p-6 space-y-6 max-w-7xl mx-auto">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold tracking-tight">Reporting & Integrations Pipeline</h1>
      </div>

      <div className="grid md:grid-cols-3 gap-6">
        <Card className="col-span-1 border-primary/20">
          <CardHeader>
            <CardTitle>SARS Levy Imports</CardTitle>
          </CardHeader>
          <CardContent className="space-y-2">
            <p className="text-4xl font-black text-primary">
              R {(totalLevyAggregation._sum.amount || 0).toLocaleString()}
            </p>
            <p className="text-sm text-muted-foreground">{totalLevyAggregation._count} total payments ingested</p>
          </CardContent>
        </Card>

        <Card className="col-span-2">
          <CardHeader>
            <CardTitle>Pipeline Controls</CardTitle>
          </CardHeader>
          <CardContent>
             <ReportingActionBridge />
          </CardContent>
        </Card>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Recent Extraction Batches</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Extract ID</TableHead>
                <TableHead>System</TableHead>
                <TableHead>Processed</TableHead>
                <TableHead>Timestamp</TableHead>
                <TableHead className="text-right">Export File</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {extractHistory.length === 0 ? (
                <TableRow>
                  <TableCell colSpan={5} className="text-center py-6 text-muted-foreground">
                    No extraction batches triggered.
                  </TableCell>
                </TableRow>
              ) : (
                extractHistory.map((run) => (
                  <TableRow key={run.id}>
                    <TableCell className="font-medium">#{run.id}</TableCell>
                    <TableCell>
                      <Badge variant="outline">{run.extractType}</Badge>
                    </TableCell>
                    <TableCell>{run.recordsProcessed.toLocaleString()} rows</TableCell>
                    <TableCell>
                      {run.createdAt.toLocaleString()}
                    </TableCell>
                    <TableCell className="text-right text-xs text-muted-foreground">
                      {run.documents.length > 0 
                        ? run.documents[0].filename 
                        : "No Document"}
                    </TableCell>
                  </TableRow>
                ))
              )}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
      
    </div>
  );
}
