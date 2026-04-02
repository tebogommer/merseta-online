import { Queue } from "bullmq";
import { redisConnection } from "@/lib/redis";
import { QUEUE_NAMES } from "@/lib/workers";

export const sarsLevyQueue = new Queue(QUEUE_NAMES.SARS_LEVY_INGEST, {
  connection: redisConnection,
  defaultJobOptions: {
    attempts: 3,
    backoff: { type: "exponential", delay: 5000 },
    removeOnComplete: true,
  },
});

export const setmisExtractQueue = new Queue(QUEUE_NAMES.SETMIS_EXTRACT, {
  connection: redisConnection,
  defaultJobOptions: {
    attempts: 1, // Only try extraction once per click
    removeOnComplete: false, // Keep logs of extractions
  },
});
