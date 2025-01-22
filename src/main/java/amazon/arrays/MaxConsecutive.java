package amazon.arrays;

public class MaxConsecutive {
    public int longestOnes(int[] nums, int k) {

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
