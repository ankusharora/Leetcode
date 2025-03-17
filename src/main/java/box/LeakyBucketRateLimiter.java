package box;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/*

Feature	                    synchronized	                    Lock
Simplicity	                Easier to use, automatic release	More complex, requires manual handling
Fairness	                No fairness	                        Optional fairness policy available
Timeout for Locking	        Not supported	                    Supported with tryLock()
Interruptible Locking	    Not supported	                    Supported
Deadlock Management	        None	                            Can be managed explicitly
Performance	                Can be slower in high contention	Often faster due to finer-grained control


capacity = 10
leakRate = 2 (2 requests per second)
Initially, currentWaterLevel = 5
A new request comes in after 3 seconds.
elapsedTime = 3 seconds
leakedWater = 3 * 2 = 6 requests leaked.
currentWaterLevel = max(0, 5 - 6) = 0.


 */


public class LeakyBucketRateLimiter {
    private final int capacity;       // Maximum number of requests (bucket size)
    private final int leakRate;       // Leak rate (requests per second)
    private int currentWaterLevel = 0; // Current drops in the bucket
    private long lastLeakTimestamp;  // Timestamp of the last leak event

    private final Lock lock = new ReentrantLock();

    public LeakyBucketRateLimiter(int capacity, int leakRate) {
        this.capacity = capacity;
        this.leakRate = leakRate;
        this.lastLeakTimestamp = System.nanoTime();
    }

    public boolean shouldAllowRequest() {
        lock.lock();
        try {
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
        } finally {
            lock.unlock();
        }
    }
}

