package LinkedIn;

import java.util.*;
import java.util.concurrent.locks.*;

class RetainBestCache<K, V extends Comparable<V>> {
    private final int capacity;
    private final Map<K, V> cache;
    private final PriorityQueue<K> minHeap;
    private final Map<K, Integer> rankings;
    private final ReentrantLock lock = new ReentrantLock();

    public RetainBestCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.rankings = new HashMap<>();
        this.minHeap = new PriorityQueue<>(Comparator.comparing(rankings::get));
    }

    /**
     * PUT()
     * Scenario: Two threads (T1 and T2) attempt to insert elements at the same time.
     * Issue: If T1 and T2 both check cache.size() >= capacity at the same time and decide to evict an item, they might both call evictLowestRanked() and remove more than one item.
     * Fix: Locking ensures only one thread modifies cache, rankings, and minHeap at a time.
     *
     */
    public void put(K key, V value, int rank) {
        lock.lock();
        try {
            if (cache.size() >= capacity) {
                evictLowestRanked();
            }
            cache.put(key, value);
            rankings.put(key, rank);
            minHeap.offer(key);
        } finally {
            lock.unlock();
        }
    }

    /*
    Scenario: T1 calls get() while T2 is modifying the cache with put().
Issue: If T2 removes the key while T1 is reading it, T1 might return a null even though it expected the key to exist.
Fix: Locking prevents put() and get() from running at the same time.

     */
    public V get(K key) {
        lock.lock();
        try {
            return cache.get(key);  // Simple read, but needs lock to avoid inconsistencies.
        } finally {
            lock.unlock();
        }
    }

    /**
     * Scenario: T1 is evicting an element while T2 is adding a new element.
     * Issue: T1 may remove an element from minHeap that T2 just inserted, leading to an inconsistent state.
     * Fix: Since evictLowestRanked() is only called inside a locked put(), we avoid this issue.
     */
    private void evictLowestRanked() {
        lock.lock();
        try {
            if (!minHeap.isEmpty()) {
                K lowestRankKey = minHeap.poll();
                cache.remove(lowestRankKey);
                rankings.remove(lowestRankKey);
            }
        } finally {
            lock.unlock();
        }
    }
}



