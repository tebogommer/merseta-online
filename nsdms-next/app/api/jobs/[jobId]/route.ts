import { NextResponse } from "next/server";
import { sarsLevyQueue, setmisExtractQueue } from "@/lib/queues";

/**
 * API Route to check the status of a background job.
 * Used by the UI to show real-time progress of SARS/SETMIS processing.
 */
export async function GET(
  request: Request,
  { params }: { params: Promise<{ jobId: string }> }
) {
  try {
    const { jobId } = await params;
    
    // E2E Mock check
    if (jobId.startsWith("mock-")) {
        return NextResponse.json({
            id: jobId,
            state: "completed",
            progress: 100,
            result: { processedCount: 15432, extractId: 1 },
            isCompleted: true,
            isFailed: false
        });
    }

    let job = await sarsLevyQueue.getJob(jobId);
    
    if (!job) {
      job = await setmisExtractQueue.getJob(jobId);
    }

    if (!job) {
      return NextResponse.json({ error: "Job not found" }, { status: 404 });
    }

    const state = await job.getState();
    const progress = job.progress;
    const result = job.returnvalue;

    return NextResponse.json({
      id: job.id,
      state,
      progress,
      result,
      isCompleted: await job.isCompleted(),
      isFailed: await job.isFailed(),
    });
  } catch (error: any) {
    return NextResponse.json({ error: error.message }, { status: 500 });
  }
}
