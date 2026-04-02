"use client";

import { useMemo, useState } from "react";
import {
  ColumnDef,
  flexRender,
  getCoreRowModel,
  useReactTable,
  getPaginationRowModel,
  SortingState,
} from "@tanstack/react-table";
import Link from "next/link";
import { Plus, ArrowRight, BarChart2 } from "lucide-react";
import { Button } from "@/components/ui/button";

interface WspGridProps {
  wsps: any[];
  providerId: number;
}

export function WspGrid({ wsps, providerId }: WspGridProps) {
  const [sorting, setSorting] = useState<SortingState>([]);

  const columns = useMemo<ColumnDef<any>[]>(
    () => [
      {
        accessorKey: "finYear",
        header: "Fin Year",
        cell: (info) => <span className="font-semibold">{info.getValue() as number}</span>,
      },
      {
        accessorKey: "status",
        header: "Status",
        cell: ({ row }) => {
           let color = "bg-gray-100 text-gray-800";
           const stat = row.getValue("status") as string;
           if (stat === "Approved") color = "bg-green-100 text-green-800";
           if (stat === "Draft") color = "bg-blue-100 text-blue-800";
           if (stat === "Rejected") color = "bg-red-100 text-red-800";
           return <span className={`px-2 py-1 rounded-full text-xs font-semibold ${color}`}>{stat}</span>;
        }
      },
      {
        accessorKey: "totalPayroll",
        header: "Total Payroll",
        cell: (info) => `R ${(info.getValue() as number)?.toLocaleString()}`,
        className: "hidden md:table-cell",
      },
      {
        accessorKey: "totalTrainingCosts",
        header: "Training Cost",
        cell: (info) => `R ${(info.getValue() as number)?.toLocaleString()}`,
        className: "hidden md:table-cell",
      },
      {
        accessorKey: "percentagePayrollSpent",
        header: "% Spent",
        cell: (info) => `${info.getValue() as number}%`,
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
                Inspect <ArrowRight className="w-4 h-4" />
              </Link>
            </div>
          );
        },
      },
    ],
    []
  );

  const table = useReactTable({
    data: wsps,
    columns,
    state: { sorting },
    onSortingChange: setSorting,
    getCoreRowModel: getCoreRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
  });

  return (
    <div className="space-y-4">
      <div className="flex justify-between items-center mb-4">
        <h3 className="text-lg font-semibold text-gray-900 flex items-center gap-2">
            <BarChart2 className="w-5 h-5 text-gray-400" />
            Declared Skills Plans
        </h3>
        <Link href={`/workplace-skills-plans/new?orgId=${providerId}`}>
          <Button className="flex gap-2">
            <Plus className="w-4 h-4" /> Submit WSP
          </Button>
        </Link>
      </div>

      <div className="rounded-md border bg-white shadow-sm overflow-hidden">
        <table className="w-full text-sm text-left">
          <thead className="bg-gray-50 border-b">
            {table.getHeaderGroups().map((headerGroup) => (
              <tr key={headerGroup.id}>
                {headerGroup.headers.map((header) => (
                  <th key={header.id} className={`px-4 py-3 font-semibold text-gray-900 uppercase text-xs tracking-wider ${(header.column.columnDef.meta as Record<string, string>)?.className || ''}`}>
                    {header.isPlaceholder
                      ? null
                      : flexRender(
                          header.column.columnDef.header,
                          header.getContext()
                        )}
                  </th>
                ))}
              </tr>
            ))}
          </thead>
          <tbody>
            {table.getRowModel().rows.length > 0 ? (
              table.getRowModel().rows.map((row) => (
                <tr key={row.id} className="border-b last:border-0 hover:bg-gray-50">
                  {row.getVisibleCells().map((cell) => (
                    <td key={cell.id} className={`px-4 py-3 ${(cell.column.columnDef.meta as Record<string, string>)?.className || ''}`}>
                      {flexRender(cell.column.columnDef.cell, cell.getContext())}
                    </td>
                  ))}
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={columns.length} className="px-4 py-8 text-center text-gray-500">
                  This organisation has no active Workplace Skills Plans.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
      
      {table.getPageCount() > 1 && (
        <div className="flex items-center justify-end space-x-2 py-4">
          <Button
            variant="outline"
            size="sm"
            onClick={() => table.previousPage()}
            disabled={!table.getCanPreviousPage()}
          >
            Previous
          </Button>
          <Button
            variant="outline"
            size="sm"
            onClick={() => table.nextPage()}
            disabled={!table.getCanNextPage()}
          >
            Next
          </Button>
        </div>
      )}
    </div>
  );
}
