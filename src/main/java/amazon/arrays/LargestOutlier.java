package amazon.arrays;

public class LargestOutlier {
    /**
     *
     * Input: nums = [2,3,5,10]
     *
     * Output: 10
     *
     * Explanation:
     *
     * The special numbers could be 2 and 3, thus making their sum 5 and the outlier 10.
     * @param nums
     * @return
     */
    public int getLargestOutlier(int[] nums) {
        if (nums.length == 0 ){
            return 0;
        }

        double  mean = 0;

        for (int num: nums){
            mean += num;
        }

        mean /= nums.length;


        int largestOutlier = nums[0];
        double maxDeviation = 0;

        for (int num: nums){
            double deviation = Math.abs(num - mean);

            if (deviation > maxDeviation){
                maxDeviation = deviation;
                largestOutlier = num;
            }
        }

        return largestOutlier;
    }
}
