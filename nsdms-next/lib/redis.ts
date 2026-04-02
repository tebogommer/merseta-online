import { Redis } from "ioredis";

// Centralized Redis connection options
// In production, these would come from process.env.REDIS_URL
export const redisConnection = {
  host: process.env.REDIS_HOST || "localhost",
  port: parseInt(process.env.REDIS_PORT || "6379"),
};

// Singleton Redis Instance for BullMQ
let redisInstance: Redis | null = null;

export function getRedis() {
  if (!redisInstance) {
    redisInstance = new Redis({
      ...redisConnection,
      maxRetriesPerRequest: null, // Critical requirement for BullMQ
    });
  }
  return redisInstance;
}
