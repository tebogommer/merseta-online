import { Queue, Worker, Job } from "bullmq";
import { redisConnection } from "@/lib/redis";
import { PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

// Define Queue Names
export const QUEUE_NAMES = {
  SARS_LEVY_INGEST: "sars-levy-ingest",
  SETMIS_EXTRACT: "setmis-extract",
  NOTIFICATION: "system-notification",
};

/**
 * Worker Logic for the SARS Levy Queue
 * This handles the heavy lifting of processing thousands of SARS records
 * in the background to avoid blocking the main server thread.
 */
export const sarsLevyWorker = new Worker(
  QUEUE_NAMES.SARS_LEVY_INGEST,
  async (job: Job) => {
    const { data } = job;
    console.log(`[Worker] Started processing SARS Levy Job ${job.id}`);
    
    // Simulate complex ingestion logic:
    // 1. Parsing large BLOB/CSV
    // 2. Relational mapping to Employer SDL numbers
    // 3. Batch writing to Prisma 
    
    // Placeholder for actual processing logic
    await new Promise(resolve => setTimeout(resolve, 5000)); 
    
    console.log(`[Worker] Successfully completed SARS Levy Job ${job.id}`);
    return { success: true, processedCount: data.rowCount || 0 };
  },
  {
    connection: redisConnection,
    concurrency: 5, // Process 5 jobs at a time
  }
);

// Worker error handling
sarsLevyWorker.on("failed", (job, err) => {
  console.error(`[Worker] Job ${job?.id} failed with error: ${err.message}`);
});

export const setmisExtractWorker = new Worker(
  QUEUE_NAMES.SETMIS_EXTRACT,
  async (job: Job) => {
    const { data } = job;
    console.log(`[Worker] Started SETMIS Extract Job ${job.id}`);
    
    // Simulate complex extracting logic (e.g., generating XML format per SAQA specifications)
    await new Promise(resolve => setTimeout(resolve, 8000)); 

    const extractType = data.extractType || "SETMIS";
    const processedCount = Math.floor(Math.random() * 50000) + 500;
    const simulatedCsvBuffer = Buffer.from(`ID,Name,Type,Value\n1,GeneratedXML1,${extractType},Ok\n`);

    // Mock writing the double-write record into ReportingExtract and Document attachment
    const extract = await prisma.$transaction(async (tx) => {
       const ext = await tx.reportingExtract.create({
         data: {
           extractType,
           status: "COMPLETED",
           recordsProcessed: processedCount,
         }
       });

       await tx.document.create({
          data: {
            filename: `${extractType}_Batch_${new Date().getTime()}.csv`,
            mimeType: 'text/csv',
            sizeBytes: simulatedCsvBuffer.length,
            blob: simulatedCsvBuffer,
            isGenerated: true,
            reportingExtractId: ext.id,
          }
       });

       return ext;
    });
    
    console.log(`[Worker] Successfully completed SETMIS Extract Job ${job.id}`);
    return { success: true, processedCount: extract.recordsProcessed, extractId: extract.id };
  },
  {
    connection: redisConnection,
    concurrency: 1, // SETMIS jobs are large, only 1 at a time
  }
);

setmisExtractWorker.on("failed", (job, err) => {
  console.error(`[Worker] SETMIS Job ${job?.id} failed with error: ${err.message}`);
});
