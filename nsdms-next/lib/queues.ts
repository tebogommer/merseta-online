import { Queue } from "bullmq";
import { redisConnection } from "@/lib/redis";
import { QUEUE_NAMES } from "@/lib/workers";

const isMock = process.env.MOCK_BULLMQ === "true" || process.env.NODE_ENV === "test" || !process.env.REDIS_HOST;

export const sarsLevyQueue = isMock ? {
    add: async () => ({ id: `mock-sars-${Date.now()}` }),
    getJob: async () => null,
} as unknown as Queue : new Queue(QUEUE_NAMES.SARS_LEVY_INGEST, {
  connection: redisConnection,
  defaultJobOptions: {
    attempts: 3,
    backoff: { type: "exponential", delay: 5000 },
    removeOnComplete: true,
  },
});

export const setmisExtractQueue = isMock ? {
    add: async () => ({ id: `mock-setmis-${Date.now()}` }),
    getJob: async () => null,
} as unknown as Queue : new Queue(QUEUE_NAMES.SETMIS_EXTRACT, {
  connection: redisConnection,
  defaultJobOptions: {
    attempts: 1, // Only try extraction once per click
    removeOnComplete: false, // Keep logs of extractions
  },
});

