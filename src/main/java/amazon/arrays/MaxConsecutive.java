package amazon.arrays;

public class MaxConsecutive {

    public int findMaxConsecutiveOnes(int[] nums) {
//        Input: nums = [1,1,0,1,1,1]
//        Output: 3
//        Explanation: The first two digits or the last three digits are consecutive 1s. The maximum number of consecutive 1s is 3.
        int total = 0;
        int maxConse = 0;

        for (int i = 0; i < nums.length; i++){
            if (nums[i] == 1){
                total++;
                maxConse = Math.max(total, maxConse);
            } else {
                total = 0;
            }

        }
        return maxConse;

    }


    public int longestOnes(int[] nums, int k) {
//        Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
//        Output: 6
        int i = 0;
        int j = 0;

        while (i < nums.length) {
            if (nums[i] == 0) {
                k--;
            }

            if (k < 0) {
                if (nums[j] == 0) {
                    k++;
                }

                j++;
            }

            i++;
        }
        return i - j;
    }
}
