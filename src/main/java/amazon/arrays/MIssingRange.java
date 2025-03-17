package amazon.arrays;
import java.util.*;

/**
 * Start with prev initialized to lower - 1 to track the end of the previous range.
 * Iterate through the nums array and also handle the upper boundary by treating upper + 1 as an additional element.
 * For each current value (from nums or upper + 1), check if there is a gap greater than 1 between prev and current.
 * If a gap exists, add the range [prev + 1, current - 1] to the result list.
 * Update prev to current after processing each value, and return the result list after the loop
 */
public class MIssingRange {
    public List<List<Integer>> findMissingRanges(int[] nums, int lower, int upper) {
        int prev = lower - 1;
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i <= nums.length; i++) {

            int current = 0;
            if (i < nums.length) {
                current = nums[i];
            } else {
                current = upper + 1;
            }

            if (current - prev > 1) {
                result.add(Arrays.asList(prev + 1, current - 1));
            }
            prev = current;
        }
        return result;
    }
}
