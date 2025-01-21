package amazon;

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
