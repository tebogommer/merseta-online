"use client";

import { OrganisationActions } from "./organisation-actions";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { LearnerGrid } from "@/app/learners/_components/learner-grid";
import { WspGrid } from "@/app/workplace-skills-plans/_components/wsp-grid";
import { StatusBadge } from "@/components/status-badge";
import { VisitForm } from "@/components/visit-form";
type OrganisationDetailsProps = {
  isNew: boolean;
  org: any;
  learners?: any[];
  wsps?: any[];
  canCreate: boolean;
  canUpdate: boolean;
  canDelete: boolean;
};

export function OrganisationDetails({
  isNew,
  org,
  learners = [],
  wsps = [],
  canCreate,
  canUpdate,
  canDelete,
}: OrganisationDetailsProps) {
  return (
    <div className="max-w-4xl mx-auto space-y-6">
      
      {!isNew && org && (
        <div className="flex justify-between items-center bg-card text-card-foreground p-4 rounded-lg shadow-sm border border-border">
           <div className="flex items-center gap-4">
              <span className="text-sm font-medium text-muted-foreground uppercase tracking-wider">Current Status:</span>
              {/* @ts-ignore - Prisma lag */}
              <StatusBadge value={org.status} />
           </div>
        </div>
      )}

      {isNew ? (
        <OrganisationActions 
          isNew={isNew} 
          org={org} 
          canCreate={canCreate} 
          canUpdate={canUpdate} 
          canDelete={canDelete} 
        />
      ) : (
        <Tabs defaultValue="general" className="w-full">
          <TabsList className="mb-6 bg-muted border border-border">
            <TabsTrigger value="general" className="px-6 data-[state=active]:bg-primary data-[state=active]:text-primary-foreground font-semibold">General Info</TabsTrigger>
            <TabsTrigger value="learners" className="px-6 data-[state=active]:bg-primary data-[state=active]:text-primary-foreground font-semibold">Enrolled Learners</TabsTrigger>
            <TabsTrigger value="wsp" className="px-6 data-[state=active]:bg-primary data-[state=active]:text-primary-foreground font-semibold">WSP & Grants</TabsTrigger>
            <TabsTrigger value="visits" className="px-6 data-[state=active]:bg-primary data-[state=active]:text-primary-foreground font-semibold">Site Visits</TabsTrigger>
          </TabsList>
          
          <TabsContent value="general" className="outline-none mt-0">
            <OrganisationActions 
              isNew={isNew} 
              org={org} 
              canCreate={canCreate} 
              canUpdate={canUpdate} 
              canDelete={canDelete} 
            />
          </TabsContent>
          
          <TabsContent value="learners" className="outline-none mt-0">
             <LearnerGrid learners={learners} providerId={org?.id} />
          </TabsContent>

          <TabsContent value="wsp" className="outline-none mt-0">
             <WspGrid wsps={wsps} providerId={org?.id} />
          </TabsContent>

          <TabsContent value="visits" className="outline-none mt-0 space-y-6">
            <div className="bg-card text-card-foreground p-6 rounded-lg shadow-sm border border-border">
              <h3 className="text-lg font-semibold mb-4">Schedule a Visit</h3>
              <VisitForm 
                onSubmit={(data) => console.log('Deep save Visit with Contact', data)} 
                contactPersons={[{ id: 1, name: "John Doe (Demo)" }, { id: 2, name: "Jane Smith (Demo)" }]} 
              />
            </div>
            <div className="bg-card text-card-foreground p-6 rounded-lg shadow-sm border border-border">
              <h3 className="text-lg font-semibold mb-4">Past Visits (Child Grid)</h3>
              <p className="text-sm text-muted-foreground">A child grid DataTable would bind here showing past related visits.</p>
            </div>
          </TabsContent>
        </Tabs>
      )}

      {/* Audit Log Stub Preview */}
      {!isNew && org && (
         <div className="text-xs text-muted-foreground text-center mt-8 pb-8">
            Database Lineage: Record #{org.id} | Created at: {new Date(org.createdAt).toISOString()}
         </div>
      )}
    </div>
  );
}
