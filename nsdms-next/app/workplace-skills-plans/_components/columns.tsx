"use client";

import { ColumnDef } from "@tanstack/react-table";
import { Badge } from "@/components/ui/badge";
import Link from "next/link";
import { ArrowRight, Building2 } from "lucide-react";
import { format } from "date-fns";

export type WSPGridRow = {
  id: number;
  organisationId: number;
  finYear: number;
  status: string;
  createdAt: string;
  organisation: {
    name: string;
    sdlNumber: string;
  };
};

export const columns: ColumnDef<WSPGridRow>[] = [
  {
    accessorKey: "organisation.sdlNumber",
    header: "SDL Number",
    cell: ({ row }) => <span className="font-semibold">{row.original.organisation.sdlNumber}</span>,
  },
  {
    accessorKey: "organisation.name",
    header: "Organisation Name",
    cell: ({ row }) => row.original.organisation.name,
  },
  {
    accessorKey: "finYear",
    header: "Financial Year",
    cell: ({ row }) => row.original.finYear,
  },
  {
    accessorKey: "status",
    header: "Status",
    cell: ({ row }) => {
      const status = row.getValue("status") as string;
      if (status.includes("Awaiting")) {
         return <Badge variant="outline" className="bg-amber-50 text-amber-700 border-amber-200">{status}</Badge>;
      }
      if (status === "Approved") {
         return <Badge variant="outline" className="bg-green-50 text-green-700 border-green-200">{status}</Badge>;
      }
      if (status === "Rejected") {
         return <Badge variant="outline" className="bg-red-50 text-red-700 border-red-200">{status}</Badge>;
      }
      return <Badge variant="secondary">{status}</Badge>;
    },
  },
  {
    accessorKey: "createdAt",
    header: "Date Submitted",
    cell: ({ row }) => format(new Date(row.original.createdAt), "dd MMM yyyy"),
    meta: { className: "hidden md:table-cell" },
  },
  {
    id: "actions",
    cell: ({ row }) => {
      const wsp = row.original;
      return (
        <div className="flex justify-end pr-4">
          <Link
            href={`/workplace-skills-plans/${wsp.id}`}
            className="text-primary hover:text-primary/80 inline-flex items-center gap-1 text-sm font-bold uppercase tracking-wider"
          >
            Review <ArrowRight className="w-4 h-4" />
          </Link>
        </div>
      );
    },
  },
];
