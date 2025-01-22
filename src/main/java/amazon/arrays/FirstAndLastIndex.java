package amazon.arrays;

/**
 * Use binary search to find the first and last occurrence of the target.
 * For the first index, move end = mid - 1 when nums[mid] >= target.
 * For the last index, move start = mid + 1 when nums[mid] <= target.
 * Update index whenever nums[mid] == target.
 * Return [-1, -1] if the target is not found; otherwise, return [first, last].
 */
public class FirstAndLastIndex {
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[2];
        result[0] = startingIndex(nums, target);
        result[1] = endingIndex(nums, target);

        return result;
    }

    public int startingIndex(int[] nums, int target){
        int index = -1, start = 0, end = nums.length -1;

        while (start <= end){
            int midpoint = start + (end - start) / 2;

            if (nums[midpoint] >= target){
                end = midpoint -1;
            } else {
                start = midpoint + 1;
            }

            if (nums[midpoint] == target){
                index = midpoint;
            }

        }
        return index;
    }


    public int endingIndex(int[] nums, int target){
        int index = -1, start = 0, end = nums.length -1;

        while (start <= end){
            int midpoint = start + (end - start) / 2;

            if (nums[midpoint] <= target){
                start = midpoint  + 1;
            } else {
                end = midpoint - 1;
            }

            if (nums[midpoint] == target){
                index = midpoint;
            }

        }
        return index;
    }
}
