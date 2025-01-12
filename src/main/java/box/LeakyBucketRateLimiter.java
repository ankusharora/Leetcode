package box;

public class LeakyBucketRateLimiter {
    private final int capacity;       // Maximum number of requests (bucket size)
    private final int leakRate;       // Leak rate (requests per second)
    private int currentWaterLevel = 0; // Current drops in the bucket
    private long lastLeakTimestamp;  // Timestamp of the last leak event

    public LeakyBucketRateLimiter(int capacity, int leakRate) {
        this.capacity = capacity;
        this.leakRate = leakRate;
        this.lastLeakTimestamp = System.nanoTime();
    }

    public synchronized boolean shouldAllowRequest() {
        long now = System.nanoTime();
        long elapsedTime = now - lastLeakTimestamp;

        // Calculate how much water should have leaked
        double leakedWater = (elapsedTime / 1_000_000_000.0) * leakRate;

        // Update the water level in the bucket
        if (leakedWater > 0) {
            currentWaterLevel = Math.max(0, currentWaterLevel - (int) leakedWater);
            lastLeakTimestamp = now;
        }

        // Check if the request can be allowed
        if (currentWaterLevel < capacity) {
            currentWaterLevel++;
            return true;
        } else {
            return false; // Bucket is full
        }
    }
}

