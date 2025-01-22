package amazon.hash_table;

import java.util.*;

/**
 * Create a map where each number is a key, and its value is a list of indices where it appears.
 * During pick(target), get the list of indices for the target.
 * Randomly select one index from the list using Random.
 * Return the randomly chosen index.
 */
public class RandomPickIndex {

    Map<Integer, List<Integer>> map;
    Random random = new Random();

    public RandomPickIndex(int[] nums) {
        this.map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(nums[i])) {
                map.put(nums[i], new ArrayList<>());
            }
            map.get(nums[i]).add(i);
        }
    }

    public int pick(int target) {
        if (!map.containsKey(target))
            return -1;
        List<Integer> list = map.get(target);
        int index = random.nextInt(list.size());
        return list.get(index);
    }

}
