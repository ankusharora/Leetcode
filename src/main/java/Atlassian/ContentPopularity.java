package Atlassian;

import java.util.*;

class ContentPopularity {
    // Map to store the popularity count for each contentId
    private Map<Integer, Integer> popularityMap;

    // TreeMap to group contentIds by their popularity count
    // Key: Popularity count, Value: Set of contentIds with that count
    private TreeMap<Integer, Set<Integer>> countMap;

    public ContentPopularity() {
        popularityMap = new HashMap<>();
        countMap = new TreeMap<>(Collections.reverseOrder()); // Sort in descending order
    }

    // Increase the popularity of the specified contentId by one
    public void increasePopularity(int contentId) {
        // Get the current popularity count
        int currentCount = popularityMap.getOrDefault(contentId, 0);

        // Update the popularity count in the popularityMap
        popularityMap.put(contentId, currentCount + 1);

        // Remove the contentId from the current count group in countMap
        if (currentCount > 0) {
            countMap.get(currentCount).remove(contentId);
            if (countMap.get(currentCount).isEmpty()) {
                countMap.remove(currentCount);
            }
        }

        // Add the contentId to the new count group in countMap
        countMap.computeIfAbsent(currentCount + 1, k -> new HashSet<>()).add(contentId);
    }

    // Decrease the popularity of the specified contentId by one
    public void decreasePopularity(int contentId) {
        // Check if the contentId exists in the popularityMap
        if (!popularityMap.containsKey(contentId)) {
            return; // Do nothing if the contentId doesn't exist
        }

        // Get the current popularity count
        int currentCount = popularityMap.get(contentId);

        // Update the popularity count in the popularityMap
        if (currentCount == 1) {
            popularityMap.remove(contentId); // Remove if count drops to 0
        } else {
            popularityMap.put(contentId, currentCount - 1);
        }

        // Remove the contentId from the current count group in countMap
        countMap.get(currentCount).remove(contentId);
        if (countMap.get(currentCount).isEmpty()) {
            countMap.remove(currentCount);
        }

        // Add the contentId to the new count group in countMap (if count > 0)
        if (currentCount > 1) {
            countMap.computeIfAbsent(currentCount - 1, k -> new HashSet<>()).add(contentId);
        }
    }

    // Return the contentId with the highest popularity
    public int mostPopular() {
        if (countMap.isEmpty()) {
            return -1; // No content exists
        }
        // Get the first entry in the TreeMap (highest popularity count)
        Map.Entry<Integer, Set<Integer>> entry = countMap.firstEntry();
        // Return any contentId from the set (since ties can be broken arbitrarily)
        return entry.getValue().iterator().next();
    }

    public static void main(String[] args) {
        ContentPopularity cp = new ContentPopularity();

        cp.increasePopularity(1);
        cp.increasePopularity(1);
        cp.increasePopularity(2);
        cp.increasePopularity(3);
        cp.increasePopularity(3);

        System.out.println("Most Popular: " + cp.mostPopular()); // Output: 1 or 3 (tie)

        cp.decreasePopularity(1);
        cp.decreasePopularity(1);

        System.out.println("Most Popular: " + cp.mostPopular()); // Output: 3

        cp.decreasePopularity(3);
        cp.decreasePopularity(3);

        System.out.println("Most Popular: " + cp.mostPopular()); // Output: 2
    }
}
