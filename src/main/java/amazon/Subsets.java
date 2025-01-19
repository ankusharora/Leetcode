package amazon;

import java.util.*;

public class Subsets {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        dfsSubset(0, nums, new ArrayList<Integer>(), result);

        return result;
    }

    public void dfsSubset(int index, int[] nums, List<Integer> current, List<List<Integer>> result){
        result.add(new ArrayList<>(current));

        for (int i = index; i < nums.length; i++){
            current.add(nums[i]);
            dfsSubset(i + 1, nums, current, result);
            current.remove(current.size() - 1);
        }
    }
}
