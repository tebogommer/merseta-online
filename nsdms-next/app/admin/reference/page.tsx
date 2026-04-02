import { Suspense } from "react";
import { getSystemDictionary } from "@/lib/services/dictionary";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Badge } from "@/components/ui/badge";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { DictionaryEntry } from "@/lib/services/dictionary";
import { StatusBadge } from "@/components/status-badge";

async function DictionaryView() {
  const dictionary = await getSystemDictionary();

  const DictionaryTable = ({ title, data }: { title: string; data: DictionaryEntry[] }) => (
    <Card className="border-none shadow-none">
      <CardHeader className="px-0">
        <CardTitle className="text-lg font-semibold">{title}</CardTitle>
      </CardHeader>
      <CardContent className="px-0">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Code</TableHead>
              <TableHead>Name/Label</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {data.map((entry) => (
              <TableRow key={entry.id}>
                <TableCell className="font-mono text-xs text-muted-foreground">{entry.code}</TableCell>
                <TableCell>
                  {(title.includes("Statuses") || title.includes("Types")) ? (
                    <StatusBadge value={entry.name} />
                  ) : (
                    entry.name
                  )}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </CardContent>
    </Card>
  );

  return (
    <div className="space-y-6">
      <Tabs defaultValue="static" className="w-full">
        <TabsList className="grid w-full max-w-md grid-cols-2 mb-6">
          <TabsTrigger value="static">Code-First Enums</TabsTrigger>
          <TabsTrigger value="dynamic">Database Lookups</TabsTrigger>
        </TabsList>

        <TabsContent value="static" className="animate-in fade-in slide-in-from-left-2 duration-300">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            <DictionaryTable title="Approval Statuses" data={dictionary.approvalStatuses} />
            <DictionaryTable title="Company Statuses" data={dictionary.companyStatuses} />
            <DictionaryTable title="Accreditation Application Types" data={dictionary.accreditationApplicationTypes} />
            <DictionaryTable title="Allocation Change Types" data={dictionary.allocationChangeTypes} />
          </div>
        </TabsContent>

        <TabsContent value="dynamic" className="animate-in fade-in slide-in-from-right-2 duration-300">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <DictionaryTable title="Genders" data={dictionary.genders} />
            <DictionaryTable title="Provinces" data={dictionary.provinces} />
            <DictionaryTable title="SETA Types" data={dictionary.setas} />
            <DictionaryTable title="Equities" data={dictionary.equities} />
            <DictionaryTable title="Nationalities" data={dictionary.nationalities} />
            <DictionaryTable title="Titles" data={dictionary.titles} />
          </div>
        </TabsContent>
      </Tabs>
    </div>
  );
}

export default function SystemDictionaryPage() {
  return (
    <div className="flex-1 space-y-4 p-8 pt-6">
      <div className="flex items-center justify-between space-y-2">
        <div>
          <h2 className="text-3xl font-bold tracking-tight">System Reference</h2>
          <p className="text-muted-foreground">
            View all system-wide enum values used across the application.
          </p>
        </div>
      </div>
      
      <Suspense fallback={<div className="h-96 w-full flex items-center justify-center">Loading System Values...</div>}>
         <DictionaryView />
      </Suspense>
    </div>
  );
}
