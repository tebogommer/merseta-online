"use client";

import { useMemo, useState } from "react";
import {
  ColumnDef,
  flexRender,
  getCoreRowModel,
  useReactTable,
  getPaginationRowModel,
  getSortedRowModel,
  SortingState,
} from "@tanstack/react-table";
import Link from "next/link";
import { Plus, ArrowRight } from "lucide-react";
import { Button } from "@/components/ui/button";

interface LearnerGridProps {
  learners: any[];
  providerId: number;
}

export function LearnerGrid({ learners, providerId }: LearnerGridProps) {
  const [sorting, setSorting] = useState<SortingState>([]);

  const columns = useMemo<ColumnDef<any>[]>(
    () => [
      {
        accessorFn: (row) => row.user.name,
        id: "name",
        header: "Learner Name",
        cell: (info) => <span className="font-semibold text-primary">{info.getValue() as string}</span>,
      },
      {
        accessorFn: (row) => row.rsaIdNumber || row.passportNumber || "N/A",
        id: "identity",
        header: "Identity",
      },
      {
        accessorKey: "dateOfBirth",
        header: "DOB",
        cell: (info) => new Date(info.getValue() as string).toLocaleDateString(),
        className: "hidden md:table-cell",
      },
      {
        accessorKey: "equityStatus",
        header: "Equity",
        className: "hidden md:table-cell",
      },
      {
        id: "actions",
        cell: ({ row }) => {
          const learner = row.original;
          return (
            <div className="flex justify-end pr-4">
              <Link
                href={`/learners/${learner.id}`}
                className="text-primary hover:text-primary/80 inline-flex items-center gap-1 text-sm font-bold uppercase tracking-wider"
              >
                View <ArrowRight className="w-4 h-4" />
              </Link>
            </div>
          );
        },
      },
    ],
    []
  );

  const table = useReactTable({
    data: learners,
    columns,
    state: { sorting },
    onSortingChange: setSorting,
    getCoreRowModel: getCoreRowModel(),
    getSortedRowModel: getSortedRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
  });

  return (
    <div className="space-y-4">
      <div className="flex justify-between items-center mb-4">
        <h3 className="text-lg font-semibold text-gray-900">Enrolled Learners</h3>
        <Link href={`/learners/new?providerId=${providerId}`}>
          <Button className="flex gap-2">
            <Plus className="w-4 h-4" /> Add Learner
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
                  No learners currently enrolled under this Provider.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
      
      {/* Pagination Controls */}
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
