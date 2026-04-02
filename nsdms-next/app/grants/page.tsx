import Link from "next/link";
import { fetchGrants } from "./_actions/workflow";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";

export default async function GrantsPage() {
  const grants = await fetchGrants();

  return (
    <div className="p-6 space-y-4 max-w-7xl mx-auto">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold tracking-tight">Grants Management</h1>
        <Link 
          href="/grants/new" 
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
              <TableHead>Organisation</TableHead>
              <TableHead>Type</TableHead>
              <TableHead className="hidden md:table-cell">Fin Year</TableHead>
              <TableHead className="text-right">Status</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {grants.length === 0 ? (
              <TableRow>
                <TableCell colSpan={5} className="text-center py-6 text-muted-foreground">
                  No grant applications found.
                </TableCell>
              </TableRow>
            ) : (
              grants.map((grant) => (
                <TableRow key={grant.id} className="cursor-pointer group">
                  <TableCell className="font-medium">
                    <Link href={`/grants/${grant.id}`} className="block w-full">
                      #{grant.id}
                    </Link>
                  </TableCell>
                  <TableCell>
                    <Link href={`/grants/${grant.id}`} className="block w-full">
                      {grant.organisation?.organisationName || `Org #${grant.organisationId}`}
                    </Link>
                  </TableCell>
                  <TableCell>
                    <Link href={`/grants/${grant.id}`} className="block w-full">
                      {grant.grantType}
                    </Link>
                  </TableCell>
                  <TableCell className="hidden md:table-cell">
                    <Link href={`/grants/${grant.id}`} className="block w-full">
                      {grant.finYear}
                    </Link>
                  </TableCell>
                  <TableCell className="text-right">
                    <Link href={`/grants/${grant.id}`} className="block w-full">
                      <Badge variant={grant.status === 'APPROVED' ? 'default' : 'secondary'}>
                        {grant.status}
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
