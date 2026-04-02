import Link from "next/link";
import { fetchAssessors } from "./_actions/workflow";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";

export default async function AssessorsPage() {
  const applications = await fetchAssessors();

  return (
    <div className="p-6 space-y-4 max-w-7xl mx-auto">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold tracking-tight">Assessors & Moderators</h1>
        <Link 
          href="/assessors/new" 
          className="bg-primary text-primary-foreground hover:bg-primary/90 inline-flex items-center justify-center rounded-md text-sm font-medium h-10 px-4 py-2"
        >
          New Application
        </Link>
      </div>
      
      <div className="rounded-md border bg-card">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className="w-[100px]">ID</TableHead>
              <TableHead>User</TableHead>
              <TableHead>Type</TableHead>
              <TableHead className="hidden md:table-cell">SDP Link</TableHead>
              <TableHead className="text-right">Status</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {applications.length === 0 ? (
              <TableRow>
                <TableCell colSpan={5} className="text-center py-6 text-muted-foreground">
                  No applications found.
                </TableCell>
              </TableRow>
            ) : (
              applications.map((app) => (
                <TableRow key={app.id} className="cursor-pointer group">
                  <TableCell className="font-medium">
                    <Link href={`/assessors/${app.id}`} className="block w-full">
                      #{app.id}
                    </Link>
                  </TableCell>
                  <TableCell>
                    <Link href={`/assessors/${app.id}`} className="block w-full">
                      {app.user?.name || app.user?.email || `User #${app.userId}`}
                    </Link>
                  </TableCell>
                  <TableCell>
                    <Link href={`/assessors/${app.id}`} className="block w-full">
                      {app.applicationType}
                    </Link>
                  </TableCell>
                  <TableCell className="hidden md:table-cell">
                    <Link href={`/assessors/${app.id}`} className="block w-full">
                      {app.trainingProvider?.organisation?.organisationName || "Independent"}
                    </Link>
                  </TableCell>
                  <TableCell className="text-right">
                    <Link href={`/assessors/${app.id}`} className="block w-full">
                      <Badge variant={app.status === 'APPROVED' ? 'default' : 'secondary'}>
                        {app.status}
                      </Badge>
                    </Link>
                  </TableCell>
                </TableRow>
              ))
            )}
          </TableBody>
        </Table>
      </div>
    </div>
  );
}
