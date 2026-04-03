"use client";

import { ColumnDef } from "@tanstack/react-table";
import { ArrowRight, ArrowUpDown, UserIcon } from "lucide-react";
import Link from "next/link";
import { Badge } from "@/components/ui/badge";

export type PersonData = {
  id: number;
  firstName: string;
  lastName: string;
  rsaIdNumber: string | null;
  passportNumber: string | null;
  gender?: { name: string } | null;
  equity?: { name: string } | null;
  user?: { email: string | null } | null;
};

export const columns: ColumnDef<PersonData>[] = [
  {
    accessorKey: "id",
    header: "ID",
    cell: ({ row }) => <div className="font-semibold text-muted-foreground">#{row.getValue("id")}</div>,
  },
  {
    accessorKey: "lastName",
    header: ({ column }) => {
      return (
        <button
          className="flex items-center gap-1 font-semibold text-foreground hover:text-primary uppercase text-xs tracking-wider"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Full Name
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </button>
      );
    },
    cell: ({ row }) => {
      const first = row.original.firstName;
      const last = row.original.lastName;
      return (
        <div className="font-semibold">
           <span className="text-primary">{last}</span>, {first}
        </div>
      );
    },
  },
  {
    id: "identity",
    header: () => <div className="hidden md:table-cell uppercase text-xs tracking-wider font-semibold">Identity / Passport</div>,
    cell: ({ row }) => {
      const rsa = row.original.rsaIdNumber;
      const passport = row.original.passportNumber;
      return (
        <div className="hidden md:table-cell text-muted-foreground">
          {rsa ? (
             <div className="flex flex-col">
                <span className="text-xs uppercase font-bold text-[10px]">RSA ID</span>
                <span>{rsa}</span>
             </div>
          ) : passport ? (
             <div className="flex flex-col">
                <span className="text-xs uppercase font-bold text-[10px]">Passport</span>
                <span>{passport}</span>
             </div>
          ) : "-"}
        </div>
      );
    },
  },
  {
    id: "access",
    header: () => <div className="hidden md:table-cell uppercase text-xs tracking-wider font-semibold">System Access</div>,
    cell: ({ row }) => {
      const user = row.original.user;
      return (
        <div className="hidden md:table-cell">
          {user ? (
             <Badge variant="outline" className="gap-1 border-primary/30 text-primary bg-primary/5">
                <UserIcon className="w-3 h-3" /> {user.email}
             </Badge>
          ) : (
             <span className="text-xs text-muted-foreground italic">No Account</span>
          )}
        </div>
      );
    },
  },
  {
    id: "actions",
    header: () => <div className="text-right uppercase text-xs tracking-wider font-semibold">Action</div>,
    cell: ({ row }) => {
      const person = row.original;
      return (
        <div className="flex justify-end items-center gap-4">
          <Link href={`/admin/persons/${person.id}`} className="text-primary hover:text-primary/80 inline-flex items-center gap-1 text-sm font-bold uppercase tracking-wider">
            Details <ArrowRight className="w-4 h-4" />
          </Link>
        </div>
      );
    },
  },
];
