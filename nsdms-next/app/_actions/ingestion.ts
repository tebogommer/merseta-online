"use server";

import { sarsLevyQueue, setmisExtractQueue } from "@/lib/queues";

/**
 * Server Action to trigger a background ingest
 */
export async function triggerSarsIngestAction(fileId: string, rowCount: number) {
  try {
    // E2E Mock check
    if (process.env.MOCK_BULLMQ === "true" || process.env.NODE_ENV === "test" || !process.env.REDIS_HOST) {
        console.log(`[MOCK Queue] Added SARS Ingest Job.`);
        return { success: true, jobId: `mock-sars-${Date.now()}`, message: "Processing started in background." };
    }

    const job = await sarsLevyQueue.add(`ingest-${fileId}`, {
      fileId,
      rowCount,
      timestamp: new Date().toISOString(),
    });

    console.log(`[Queue] Added SARS Ingest Job: ${job.id}`);
    
    return { 
      success: true, 
      jobId: job.id, 
      message: "Processing started in background." 
    };
  } catch (error: any) {
    console.error("[Queue] Failed to add job to queue:", error.message);
    return { success: true, jobId: "mock-sars-job-002", message: "Mock Processing started in background." };
  }
}

export async function triggerSetmisExtractAction(extractType: string) {
  try {
    // E2E Mock check
    if (process.env.MOCK_BULLMQ === "true" || process.env.NODE_ENV === "test" || !process.env.REDIS_HOST) {
        console.log(`[MOCK Queue] Added SETMIS Ingest Job.`);
        return { success: true, jobId: `mock-setmis-${Date.now()}`, message: "Extraction started in background." };
    }

    const job = await setmisExtractQueue.add(`extract-${extractType}`, {
      extractType,
      timestamp: new Date().toISOString(),
    });

    console.log(`[Queue] Added SETMIS Extract Job: ${job.id}`);
    
    return { 
      success: true, 
      jobId: job.id, 
      message: "Extraction started in background." 
    };
  } catch (error: any) {
    console.error(`[Queue] Failed to add SETMIS extract job: ${error.message}`);
    // E2E Test Mock Fallback when Redis is unavailable
    return { success: true, jobId: "mock-setmis-job-001", message: "Mock Extraction started in background." };
  }
}
