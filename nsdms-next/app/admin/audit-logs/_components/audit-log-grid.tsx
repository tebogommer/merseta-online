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
import { Eye, ShieldAlert, X } from "lucide-react";
import { Button } from "@/components/ui/button";

interface AuditLogGridProps {
  logs: any[];
}

export function AuditLogGrid({ logs }: AuditLogGridProps) {
  const [sorting, setSorting] = useState<SortingState>([]);
  const [selectedLog, setSelectedLog] = useState<any | null>(null);

  const columns = useMemo<ColumnDef<any>[]>(
    () => [
      {
        accessorKey: "createdAt",
        header: "Timestamp",
        cell: (info) => <span className="font-mono text-xs">{new Date(info.getValue() as string).toLocaleString()}</span>,
      },
      {
        accessorKey: "entityName",
        header: "Domain Entity",
        cell: (info) => <span className="font-semibold px-2 py-1 bg-gray-100 rounded-md text-gray-700 text-xs uppercase tracking-wide">{info.getValue() as string}</span>,
      },
      {
        accessorKey: "actionName",
        header: "Action",
        cell: ({ row }) => {
            const action = row.getValue("actionName") as string;
            return <div className="text-primary font-bold text-xs tracking-wider">{action}</div>
        }
      },
      {
        accessorKey: "recordId",
        header: "Target ID",
        cell: (info) => <span className="font-mono text-gray-500">#{info.getValue() as number}</span>,
      },
      {
        accessorKey: "actor",
        header: "Actor",
        cell: (info) => <span className="text-gray-900 border-b border-dotted border-gray-400">{info.getValue() as string}</span>,
      },
      {
        id: "actions",
        cell: ({ row }) => {
          const log = row.original;
          return (
            <div className="flex justify-end pr-4">
              <Button onClick={() => setSelectedLog(log)} variant="outline" size="sm" className="flex gap-2">
                 <Eye className="w-4 h-4" /> Inspect Payload
              </Button>
            </div>
          );
        },
      },
    ],
    []
  );

  const table = useReactTable({
    data: logs,
    columns,
    state: { sorting },
    onSortingChange: setSorting,
    getCoreRowModel: getCoreRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
  });

  return (
    <div className="relative">
      <div className="space-y-4">
        
        <div className="rounded-md border bg-white shadow-sm overflow-hidden">
          <table className="w-full text-sm text-left">
            <thead className="bg-[#f8f9fa] border-b">
              {table.getHeaderGroups().map((headerGroup) => (
                <tr key={headerGroup.id}>
                  {headerGroup.headers.map((header) => (
                    <th key={header.id} className="px-4 py-3 font-semibold text-gray-900 uppercase text-xs tracking-wider">
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
                  <tr key={row.id} className="border-b last:border-0 hover:bg-[#fcfdfd]">
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
                    No Double-Write audit logs found in the system boundary.
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

      {/* Inline Slide-Over (Sheet Alternative) */}
      {selectedLog && (
          <>
            <div className="fixed inset-0 bg-black/20 z-40" onClick={() => setSelectedLog(null)} />
            <div className="fixed top-0 right-0 h-full w-[500px] max-w-full bg-white shadow-2xl z-50 transform transition-transform border-l border-gray-200 overflow-y-auto">
                <div className="p-6">
                    <div className="flex justify-between items-center border-b pb-4 mb-6">
                        <h2 className="text-xl font-bold flex gap-2 items-center text-primary">
                            <ShieldAlert className="w-5 h-5"/> Lineage Inspection
                        </h2>
                        <button onClick={() => setSelectedLog(null)} className="p-2 hover:bg-gray-100 rounded-full">
                            <X className="w-5 h-5 text-gray-500" />
                        </button>
                    </div>

                    <div className="space-y-6">
                        <div className="grid grid-cols-2 gap-4 text-sm">
                            <div>
                                <span className="block text-gray-400 font-semibold uppercase text-xs">Entity</span>
                                <span className="font-bold">{selectedLog.entityName} #{selectedLog.recordId}</span>
                            </div>
                            <div>
                                <span className="block text-gray-400 font-semibold uppercase text-xs">Action</span>
                                <span className="text-primary font-bold">{selectedLog.actionName}</span>
                            </div>
                            <div>
                                <span className="block text-gray-400 font-semibold uppercase text-xs">Actor ID</span>
                                <span className="font-mono">{selectedLog.actor}</span>
                            </div>
                            <div>
                                <span className="block text-gray-400 font-semibold uppercase text-xs">Timestamp</span>
                                <span>{new Date(selectedLog.createdAt).toLocaleString()}</span>
                            </div>
                        </div>

                        <div>
                            <span className="block text-gray-400 font-semibold uppercase text-xs mb-2">Raw Topology (JSON)</span>
                            <div className="bg-[#1e1e1e] rounded-md p-4 overflow-x-auto shadow-inner">
                                <pre className="text-[#d4d4d4] font-mono text-sm leading-relaxed">
                                    {JSON.stringify(JSON.parse(selectedLog.snapshot || "{}"), null, 2)}
                                </pre>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
          </>
      )}
    </div>
  );
}
