package DoordasOnsite;

class ExternalService {
    private static String cachedJson = null;
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000;

    public static String fetchDeliveriesJson() {
        if (cachedJson != null) {
            return cachedJson; // Return cached data if available
        }

        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            try {
                String json = fetchFromProvider(); // Simulate provider call
                cachedJson = json; // Update cache on success
                return json;
            } catch (Exception e) {
                if (attempt == MAX_RETRIES - 1) {
                    System.err.println("Provider down after retries, using cache or default");
                    return cachedJson != null ? cachedJson : "[]"; // Fallback to empty list
                }
                try {
                    Thread.sleep(RETRY_DELAY_MS * (attempt + 1)); // Exponential backoff
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return "[]"; // Default fallback
    }

    private static String fetchFromProvider() {
        // Simulate actual provider call
        return """
        [
            {"dasher_id": 1, "delivery_id": 1, "timestamp": "2025-03-04T18:01:00", "status": "ACCEPTED"},
            {"dasher_id": 1, "delivery_id": 2, "timestamp": "2025-03-04T18:04:00", "status": "ACCEPTED"},
            {"dasher_id": 1, "delivery_id": 3, "timestamp": "2025-03-04T18:13:00", "status": "FULFILLED"},
            {"dasher_id": 1, "delivery_id": 4, "timestamp": "2025-03-04T18:23:00", "status": "CANCELLED"}
        ]
        """;
    }
}

/**
 *
 * 1. Handling Provider Downtime
 * "If the external provider is down, we’d need to ensure the system remains functional and doesn’t fail outright. Here’s how I’d approach it:
 *
 * Caching: Store the most recent delivery data locally (e.g., in memory or a persistent store like a database). When the provider is down, fall back to this cached data. For example, we could modify ExternalService to check a cache first and only hit the provider if the cache is stale or empty.
 * Retry Mechanism: Implement a retry policy with exponential backoff. If the provider fails, retry the request a few times (e.g., 3 retries with delays of 1s, 2s, 4s) before giving up and using cached data or failing gracefully.
 * Fail-Safe Default: If no cached data is available and the provider is unreachable, return a default response (e.g., an empty delivery list or a predefined payout estimate) and log the failure for later reconciliation when the provider is back online.
 * Asynchronous Processing: Decouple the payout calculation from the provider call by using a queue. When the provider is down, queue the request and process it later when the service recovers, ensuring the user isn’t blocked."
 *
 * 2. Handling High Latency
 * "If the provider has high latency, we’d want to minimize the impact on the user experience and system performance. Here’s how I’d handle it:
 *
 * Timeouts: Set a reasonable timeout (e.g., 2 seconds) on the provider call. If it doesn’t respond in time, fall back to cached data or a default response rather than waiting indefinitely.
 * Asynchronous Calls: Make the fetchDeliveriesJson call asynchronous (e.g., using Java’s CompletableFuture). This way, the main thread isn’t blocked, and we can proceed with other tasks or return a preliminary result while waiting for the provider.
 * Batching Requests: If we’re calling the provider frequently, batch multiple requests into a single call to reduce round-trip overhead. For example, fetch deliveries for multiple dashers at once if the API supports it.
 * Circuit Breaker: Use a circuit breaker pattern (e.g., with a library like Resilience4j). If latency exceeds a threshold or failures pile up, temporarily stop calling the provider, switch to cached data, and periodically check if it’s back to normal."
 *
 * 3. Improving Resilience in the Design
 * "Stepping back, I’d also think about the overall system design to make it more robust:
 *
 * Local Proxy Service: Introduce a lightweight proxy service between our application and the external provider. This proxy could handle caching, retries, and timeouts, abstracting the complexity from the core payout logic.
 * Event-Driven Approach: If the provider supports it, switch to an event-driven model (e.g., webhooks or a pub/sub system). Instead of polling the provider, it pushes delivery updates to us, reducing latency and dependency on its uptime.
 * Monitoring and Alerts: Add monitoring to track provider latency and failure rates. If it’s consistently slow or down, we’d get alerted to adjust our strategy (e.g., rely more on cache or negotiate with the provider)."
 */