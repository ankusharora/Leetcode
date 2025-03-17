package Atlassian;

import java.util.Map;
import java.util.TreeMap;

public class CommodityPriceTrackerWithCheckpoints {
    private TreeMap<Long, Integer> checkpointToMaxPrice;

    public CommodityPriceTrackerWithCheckpoints() {
        checkpointToMaxPrice = new TreeMap<>();
    }

    // Add a timestamp and price to the tracker
    public void addPrice(long timestamp, int price) {
        // Get the maximum price up to the previous checkpoint
        Map.Entry<Long, Integer> previousCheckpoint = checkpointToMaxPrice.floorEntry(timestamp);
        int currentMax = (previousCheckpoint != null) ? previousCheckpoint.getValue() : Integer.MIN_VALUE;

        // Update the current maximum price
        currentMax = Math.max(currentMax, price);

        // Add a new checkpoint with the updated maximum price
        checkpointToMaxPrice.put(timestamp, currentMax);
    }

    // Get the maximum price up to a given timestamp
    public int getMaxPriceUpToTimestamp(long timestamp) {
        Map.Entry<Long, Integer> checkpoint = checkpointToMaxPrice.floorEntry(timestamp);
        return (checkpoint != null) ? checkpoint.getValue() : -1; // Return -1 if no checkpoint exists
    }

    public static void main(String[] args) {
        CommodityPriceTrackerWithCheckpoints tracker = new CommodityPriceTrackerWithCheckpoints();

        // Add some prices
        tracker.addPrice(1000, 50);
        tracker.addPrice(1000, 60); // Overwrites the previous price for timestamp 1000
        tracker.addPrice(2000, 70);
        tracker.addPrice(2000, 65); // Does not overwrite because 70 is higher
        tracker.addPrice(3000, 80);

        // Query the maximum price up to specific timestamps
        System.out.println("Max price up to 1000: " + tracker.getMaxPriceUpToTimestamp(1000)); // 60
        System.out.println("Max price up to 1500: " + tracker.getMaxPriceUpToTimestamp(1500)); // 60
        System.out.println("Max price up to 2000: " + tracker.getMaxPriceUpToTimestamp(2000)); // 70
        System.out.println("Max price up to 2500: " + tracker.getMaxPriceUpToTimestamp(2500)); // 70
        System.out.println("Max price up to 3000: " + tracker.getMaxPriceUpToTimestamp(3000)); // 80
        System.out.println("Max price up to 3500: " + tracker.getMaxPriceUpToTimestamp(3500)); // 80
        System.out.println("Max price up to 4000: " + tracker.getMaxPriceUpToTimestamp(4000)); // 80
    }
}

//Base Case:

/**
 * import java.util.*;
 *
 * public class CommodityPriceTracker {
 *     private Map<Long, Integer> timestampToHighestPrice;
 *
 *     public CommodityPriceTracker() {
 *         timestampToHighestPrice = new HashMap<>();
 *     }
 *
 *     // Add a timestamp and price to the tracker
 *     public void addPrice(long timestamp, int price) {
 *         // Update the highest price for the given timestamp
 *         timestampToHighestPrice.put(timestamp, Math.max(
 *             timestampToHighestPrice.getOrDefault(timestamp, Integer.MIN_VALUE),
 *             price
 *         ));
 *     }
 *
 *     // Get the highest price at a given timestamp
 *     public int getHighestPrice(long timestamp) {
 *         return timestampToHighestPrice.getOrDefault(timestamp, -1); // Return -1 if timestamp not found
 *     }
 *
 *     public static void main(String[] args) {
 *         CommodityPriceTracker tracker = new CommodityPriceTracker();
 *
 *         // Add some prices
 *         tracker.addPrice(1000, 50);
 *         tracker.addPrice(1000, 60); // Overwrites the previous price for timestamp 1000
 *         tracker.addPrice(2000, 70);
 *         tracker.addPrice(2000, 65); // Does not overwrite because 70 is higher
 *         tracker.addPrice(3000, 80);
 *
 *         // Query the highest price at specific timestamps
 *         System.out.println("Highest price at 1000: " + tracker.getHighestPrice(1000)); // 60
 *         System.out.println("Highest price at 2000: " + tracker.getHighestPrice(2000)); // 70
 *         System.out.println("Highest price at 3000: " + tracker.getHighestPrice(3000)); // 80
 *         System.out.println("Highest price at 4000: " + tracker.getHighestPrice(4000)); // -1 (not found)
 *     }
 * }
 */