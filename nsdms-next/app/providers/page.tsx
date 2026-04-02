import Link from "next/link";
import { fetchProviders } from "./_actions/workflow";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";
import { cn } from "@/lib/utils";
import { format } from "date-fns";

/**
 * Service Provider List Page.
 * Implements RAG status badges for accreditation monitoring.
 */
export default async function ProvidersPage() {
  const providers = await fetchProviders();

  const getStatusRAG = (expiryDate: Date | null) => {
    if (!expiryDate) return "secondary";
    const now = new Date();
    const threeMonthsOut = new Date();
    threeMonthsOut.setDate(now.getDate() + 90);

    if (expiryDate < now) return "destructive"; // Red
    if (expiryDate < threeMonthsOut) return "warning"; // Amber (Custom variant if needed)
    return "default"; // Green
  };

  return (
    <div className="p-8 space-y-6 max-w-7xl mx-auto">
      <div className="flex justify-between items-center">
        <div>
          <h1 className="text-3xl font-extrabold tracking-tight">Service Provider Registry</h1>
          <p className="text-muted-foreground">Manage SDP accreditations and ETQA monitoring.</p>
        </div>
        <Link 
          href="/providers/new" 
          className="bg-primary text-primary-foreground hover:bg-primary/90 inline-flex items-center justify-center rounded-md text-sm font-medium h-10 px-4 py-2"
        >
          Register New SDP
        </Link>
      </div>
      
      <div className="rounded-xl border bg-card/50 backdrop-blur-sm shadow-sm overflow-hidden">
        <Table>
          <TableHeader className="bg-muted/50">
            <TableRow>
              <TableHead className="w-[150px]">Accreditation #</TableHead>
              <TableHead>Provider Name</TableHead>
              <TableHead>Type</TableHead>
              <TableHead className="hidden md:table-cell">Expiry Date</TableHead>
              <TableHead className="text-right whitespace-nowrap">Status</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {providers.length === 0 ? (
              <TableRow>
                <TableCell colSpan={5} className="text-center py-12 text-muted-foreground italic">
                  No service providers found. Start by registering a new provider.
                </TableCell>
              </TableRow>
            ) : (
              providers.map((provider) => {
                const ragColor = getStatusRAG(provider.expiryDate);
                return (
                  <TableRow key={provider.id} className="cursor-pointer group hover:bg-muted/50 transition-colors">
                    <TableCell className="font-mono font-bold py-4">
                      <Link href={`/providers/${provider.id}`} className="block w-full">
                        {provider.accreditationNumber || "PENDING"}
                      </Link>
                    </TableCell>
                    <TableCell>
                      <Link href={`/providers/${provider.id}`} className="block w-full">
                        <div className="font-semibold">{provider.organisation?.organisationName}</div>
                        <div className="text-xs text-muted-foreground">{provider.organisation?.sdlNumber}</div>
                      </Link>
                    </TableCell>
                    <TableCell>
                      <Link href={`/providers/${provider.id}`} className="block w-full">
                        {typeof provider.providerType === 'object' ? provider.providerType?.name : provider.providerType || "-"}
                      </Link>
                    </TableCell>
                    <TableCell className="hidden md:table-cell">
                      <Link href={`/providers/${provider.id}`} className="block w-full">
                        {provider.expiryDate ? format(new Date(provider.expiryDate), "dd MMM yyyy") : "N/A"}
                      </Link>
                    </TableCell>
                    <TableCell className="text-right">
                      <Link href={`/providers/${provider.id}`} className="block w-full">
                        <Badge 
                          variant={ragColor === "warning" ? "secondary" : ragColor}
                          className={cn(
                            ragColor === "warning" && "bg-amber-500 hover:bg-amber-600 text-white",
                            ragColor === "default" && "bg-emerald-600 hover:bg-emerald-700 text-white"
                          )}
                        >
                          {provider.status}
                        </Badge>
                      </Link>
                    </TableCell>
                  </TableRow>
                );
              })
            )}
          </TableBody>
        </Table>
      </div>
    </div>
  );
}
