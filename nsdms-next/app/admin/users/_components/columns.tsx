"use client";

import { ColumnDef } from "@tanstack/react-table";
import { ArrowRight, ArrowUpDown, UserCheck, UserX, LinkIcon } from "lucide-react";
import Link from "next/link";
import { Badge } from "@/components/ui/badge";

export type UserData = {
  id: number;
  email: string | null;
  name: string | null;
  role: string;
  active: boolean;
  person?: { firstName: string, lastName: string, id: number } | null;
};

export const columns: ColumnDef<UserData>[] = [
  {
    accessorKey: "email",
    header: ({ column }) => {
      return (
        <button
          className="flex items-center gap-1 font-semibold text-foreground hover:text-primary uppercase text-xs tracking-wider"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Email Identity
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </button>
      );
    },
    cell: ({ row }) => (
      <div className="flex items-center gap-2">
         <div className="bg-primary/10 p-1.5 rounded-full">
            <UserCheck className="w-3.5 h-3.5 text-primary" />
         </div>
         <span className="font-semibold text-primary">{row.getValue("email")}</span>
      </div>
    ),
  },
  {
    accessorKey: "role",
    header: () => <div className="hidden md:table-cell uppercase text-xs tracking-wider font-semibold">System Role</div>,
    cell: ({ row }) => <Badge variant="secondary" className="px-2 py-0.5 font-bold text-[10px] tracking-widest">{row.getValue("role")}</Badge>,
  },
  {
    id: "personLink",
    header: () => <div className="hidden md:table-cell uppercase text-xs tracking-wider font-semibold">Linked Identity</div>,
    cell: ({ row }) => {
      const person = row.original.person;
      return (
        <div className="hidden md:table-cell text-muted-foreground flex items-center gap-1">
          {person ? (
             <>
               <LinkIcon className="w-3 h-3" />
               <Link href={`/admin/persons/${person.id}`} className="hover:underline text-xs">
                 {person.lastName}, {person.firstName}
               </Link>
             </>
          ) : (
             <span className="text-xs italic italic">No Profile Linked</span>
          )}
        </div>
      );
    },
  },
  {
    accessorKey: "active",
    header: () => <div className="hidden md:table-cell uppercase text-xs tracking-wider font-semibold">Status</div>,
    cell: ({ row }) => {
      const active = row.getValue("active");
      return (
        <div className="flex items-center gap-2">
           {active ? (
              <span className="flex items-center gap-1 text-[10px] uppercase font-bold text-emerald-600">
                <span className="w-1.5 h-1.5 bg-emerald-500 rounded-full animate-pulse" /> Enabled
              </span>
           ) : (
              <span className="flex items-center gap-1 text-[10px] uppercase font-bold text-red-600">
                 <UserX className="w-3 h-3" /> Suspended
              </span>
           )}
        </div>
      );
    },
  },
  {
    id: "actions",
    header: () => <div className="text-right uppercase text-xs tracking-wider font-semibold">Access Control</div>,
    cell: ({ row }) => {
      const user = row.original;
      return (
        <div className="flex justify-end items-center gap-4">
          <Link href={`/admin/users/${user.id}`} className="text-primary hover:text-primary/80 inline-flex items-center gap-1 text-sm font-bold uppercase tracking-wider transition-all">
             Manage <ArrowRight className="w-4 h-4" />
          </Link>
        </div>
      );
    },
  },
];
