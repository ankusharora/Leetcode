package amazon.arrays;
import java.util.*;

/**
 * Use a runningSum to track the cumulative sum of elements while iterating through the array.
 * Use modular arithmetic to store and compare the remainder of runningSum % k.
 * Store the first occurrence of each remainder in a map (remainderMap) for efficient lookups.
 * If the same remainder appears again and the subarray length is greater than 1, return true (the subarray's sum is divisible by
 * 𝑘
 * k).
 * Initialize the map with (0, -1) to handle edge cases where the subarray starts from index 0.
 * Return false if no valid subarray is found after the loop.
 */
public class ContinuousSubarraySum {

    public boolean checkSubarraySum(int[] nums, int k) {
        // Map to store the remainder and its first occurrence index
        Map<Integer, Integer> remainderMap = new HashMap<>();
        remainderMap.put(0, -1); // Handle subarrays starting at index 0

        int runningSum = 0;

        for (int i = 0; i < nums.length; i++) {
            runningSum += nums[i];

            // Compute the running sum modulo k
            if (k != 0) {
                runningSum %= k;
            }

            // Check if the remainder has been seen before
            if (remainderMap.containsKey(runningSum)) {
                // If the subarray length is greater than 1, return true
                if (i - remainderMap.get(runningSum) > 1) {
                    return true;
                }
            } else {
                // Store the remainder and its index
                remainderMap.put(runningSum, i);
            }
        }

        return false;
    }
}
