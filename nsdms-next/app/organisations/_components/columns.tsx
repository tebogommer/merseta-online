"use client";

import { ColumnDef } from "@tanstack/react-table";
import { ArrowRight, ArrowUpDown } from "lucide-react";
import Link from "next/link";
import { PDFDownloadButton } from "./pdf-download-button";

export type OrganisationData = {
  id: number;
  organisationName: string;
  sdlNumber: string | null;
  companyRegistrationNumber: string | null;
  createdAt: Date;
};

export const columns: ColumnDef<OrganisationData>[] = [
  {
    accessorKey: "id",
    header: "ID",
    cell: ({ row }) => <div className="font-semibold text-muted-foreground">#{row.getValue("id")}</div>,
  },
  {
    accessorKey: "organisationName",
    header: ({ column }) => {
      return (
        <button
          className="flex items-center gap-1 font-semibold text-foreground hover:text-primary uppercase text-xs tracking-wider"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Organisation Name
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </button>
      );
    },
    cell: ({ row }) => <div className="font-semibold text-primary">{row.getValue("organisationName")}</div>,
  },
  {
    id: "sdlInfo",
    header: () => <div className="hidden md:table-cell uppercase text-xs tracking-wider font-semibold">SDL / Reg Num</div>,
    cell: ({ row }) => {
      const sdl = row.original.sdlNumber;
      const reg = row.original.companyRegistrationNumber;
      return (
        <div className="hidden md:table-cell text-muted-foreground">
          <div>{sdl || '-'}</div>
          <div className="text-xs">{reg}</div>
        </div>
      );
    },
  },
  {
    accessorKey: "createdAt",
    header: () => <div className="hidden md:table-cell uppercase text-xs tracking-wider font-semibold">Created</div>,
    cell: ({ row }) => {
      const dateValue = new Date(row.getValue("createdAt"));
      const formattedDate = !isNaN(dateValue.getTime()) 
        ? dateValue.toISOString().split('T')[0] 
        : '-';
      return <div className="hidden md:table-cell text-muted-foreground">{formattedDate}</div>;
    },
  },
  {
    id: "actions",
    header: () => <div className="text-right uppercase text-xs tracking-wider font-semibold">Action</div>,
    cell: ({ row }) => {
      const org = row.original;
      return (
        <div className="flex justify-end items-center gap-4">
          <Link href={`/organisations/${org.id}`} className="text-primary hover:text-primary/80 inline-flex items-center gap-1 text-sm font-bold uppercase tracking-wider">
            View <ArrowRight className="w-4 h-4" />
          </Link>
          <PDFDownloadButton orgId={org.id.toString()} />
        </div>
      );
    },
  },
];
