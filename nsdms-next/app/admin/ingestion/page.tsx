import { SarsIngestionPanel } from "./sars/panal";
import { SetmisExtractionPanel } from "./setmis/panel";

export default function IngestionPage() {
  return (
    <div className="flex-1 space-y-4 p-8 pt-6">
      <div className="flex items-center justify-between space-y-2">
        <div>
          <h2 className="text-3xl font-bold tracking-tight">Data Ingestion Center</h2>
          <p className="text-muted-foreground">
            Manage high-volume data imports from SARS, SETMIS, and other external systems.
          </p>
        </div>
      </div>
      
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <SarsIngestionPanel />
        <SetmisExtractionPanel />
      </div>
    </div>
  );
}
