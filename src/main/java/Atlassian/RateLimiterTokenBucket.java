package Atlassian;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/** Base interface for rate limiters */
interface RateLimiter {
    boolean allowRequest();
}

/** 1. Basic Token Bucket Algorithm */
class TokenBucketRateLimiter implements RateLimiter {
    private final int maxTokens;
    private final long refillInterval;
    private int tokens;
    private long lastRefillTime;
    private final ReentrantLock lock = new ReentrantLock();

    public TokenBucketRateLimiter(int maxTokens, int intervalInSeconds) {
        this.maxTokens = maxTokens;
        this.refillInterval = intervalInSeconds * 1000L;
        this.tokens = maxTokens;
        this.lastRefillTime = System.currentTimeMillis();
    }

    private void refillTokens() {
        long now = System.currentTimeMillis();
        long elapsedTime = now - lastRefillTime;
        if (elapsedTime >= refillInterval) {
            tokens = maxTokens;
            lastRefillTime = now;
        }
    }

    @Override
    public boolean allowRequest() {
        lock.lock();
        try {
            refillTokens();
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
}

/** 2. Token Bucket Algorithm with Credit System */
class TokenBucketWithCredits implements RateLimiter {
    private final int maxTokens;
    private final int maxCredits;
    private final long refillInterval;
    private int tokens;
    private int credits;
    private long lastRefillTime;
    private final ReentrantLock lock = new ReentrantLock();

    public TokenBucketWithCredits(int maxTokens, int maxCredits, int intervalInSeconds) {
        this.maxTokens = maxTokens;
        this.maxCredits = maxCredits;
        this.refillInterval = intervalInSeconds * 1000L;
        this.tokens = maxTokens;
        this.credits = 0;
        this.lastRefillTime = System.currentTimeMillis();
    }

    private void refillTokens() {
        long now = System.currentTimeMillis();
        long elapsedTime = now - lastRefillTime;

        if (elapsedTime >= refillInterval) {
            int unusedTokens = tokens;
            tokens = maxTokens;
            credits = Math.min(credits + unusedTokens, maxCredits);
            lastRefillTime = now;
        }
    }

    @Override
    public boolean allowRequest() {
        lock.lock();
        try {
            refillTokens();

            if (tokens > 0) {
                tokens--;
                return true;
            } else if (credits > 0) {
                credits--;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
}

/** 3. Multi-threaded Token Bucket with Credit System */
class MultiThreadedTokenBucketWithCredits implements RateLimiter {
    private final int maxTokens;
    private final int maxCredits;
    private final long refillInterval;
    private int tokens;
    private int credits;
    private long lastRefillTime;
    private final Semaphore semaphore;
    private final ReentrantLock lock = new ReentrantLock();

    public MultiThreadedTokenBucketWithCredits(int maxTokens, int maxCredits, int intervalInSeconds) {
        this.maxTokens = maxTokens;
        this.maxCredits = maxCredits;
        this.refillInterval = intervalInSeconds * 1000L;
        this.tokens = maxTokens;
        this.credits = 0;
        this.lastRefillTime = System.currentTimeMillis();
        this.semaphore = new Semaphore(maxTokens);
    }

    private void refillTokens() {
        lock.lock();
        try {
            long now = System.currentTimeMillis();
            long elapsedTime = now - lastRefillTime;

            if (elapsedTime >= refillInterval) {
                int unusedTokens = tokens;
                tokens = maxTokens;
                credits = Math.min(credits + unusedTokens, maxCredits);

                int newTokens = maxTokens - semaphore.availablePermits();
                semaphore.release(newTokens);
                lastRefillTime = now;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean allowRequest() {
        refillTokens();

        if (semaphore.tryAcquire()) {
            return true;
        } else {
            lock.lock();
            try {
                if (credits > 0) {
                    credits--;
                    return true;
                }
            } finally {
                lock.unlock();
            }
            return false;
        }
    }
}

/** Demo class */
public class RateLimiterTokenBucket {
    public static void main(String[] args) throws InterruptedException {
        int maxRequests = 5;
        int maxCredits = 3;
        int timeWindow = 10;

        System.out.println("=== Testing Basic Token Bucket ===");
        TokenBucketRateLimiter basicLimiter = new TokenBucketRateLimiter(maxRequests, timeWindow);
        for (int i = 0; i < 7; i++) {
            System.out.println("Request: " + (basicLimiter.allowRequest() ? "Allowed" : "Rejected"));
            Thread.sleep(1000);
        }

        System.out.println("\n=== Testing Token Bucket with Credits ===");
        TokenBucketWithCredits creditLimiter = new TokenBucketWithCredits(maxRequests, maxCredits, timeWindow);
        for (int i = 0; i < 8; i++) {
            System.out.println("Request: " + (creditLimiter.allowRequest() ? "Allowed" : "Rejected"));
            Thread.sleep(1000);
        }

        System.out.println("\n=== Testing Multi-threaded Token Bucket with Credits ===");
        MultiThreadedTokenBucketWithCredits multiThreadedLimiter = new MultiThreadedTokenBucketWithCredits(maxRequests, maxCredits, timeWindow);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 7; i++) {
            executor.submit(() -> System.out.println("Request: " + (multiThreadedLimiter.allowRequest() ? "Allowed" : "Rejected")));
        }
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}
