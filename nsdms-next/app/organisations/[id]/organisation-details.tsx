import { OrganisationActions } from "./organisation-actions";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { LearnerGrid } from "@/app/learners/_components/learner-grid";
import { WspGrid } from "@/app/workplace-skills-plans/_components/wsp-grid";
import { StatusBadge } from "@/components/status-badge";

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
        <div className="flex justify-between items-center bg-white p-4 rounded-lg shadow-sm border border-gray-200">
           <div className="flex items-center gap-4">
              <span className="text-sm font-medium text-gray-500 uppercase tracking-wider">Current Status:</span>
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
          <TabsList className="mb-6 bg-white border border-gray-200">
            <TabsTrigger value="general" className="px-6 data-[state=active]:bg-primary data-[state=active]:text-primary-foreground font-semibold">General Info</TabsTrigger>
            <TabsTrigger value="learners" className="px-6 data-[state=active]:bg-primary data-[state=active]:text-primary-foreground font-semibold">Enrolled Learners</TabsTrigger>
            <TabsTrigger value="wsp" className="px-6 data-[state=active]:bg-primary data-[state=active]:text-primary-foreground font-semibold">WSP & Grants</TabsTrigger>
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
        </Tabs>
      )}

      {/* Audit Log Stub Preview */}
      {!isNew && org && (
         <div className="text-xs text-gray-400 text-center mt-8 pb-8">
            Database Lineage: Record #{org.id} | Created at: {new Date(org.createdAt).toISOString()}
         </div>
      )}
    </div>
  );
}
