package box;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RateLimiterTest {
    public static void main(String[] args) {
        LeakyBucketRateLimiter limiter = new LeakyBucketRateLimiter(10, 5); // 10 capacity, 5 req/sec
        ExecutorService executor = Executors.newFixedThreadPool(10);

        Runnable task = () -> {
            if (limiter.shouldAllowRequest()) {
                System.out.println("Request allowed: " + Thread.currentThread().getName());
            } else {
                System.out.println("Request denied: " + Thread.currentThread().getName());
            }
        };

        // Submit 50 tasks to simulate 50 requests
        for (int i = 0; i < 50; i++) {
            executor.submit(task);
        }

        executor.shutdown();
    }
}
