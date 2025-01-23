package amazon;

public class TrappingRainWater {
    class Solution {
        public int trap(int[] height) {
            if (height.length < 2) {
                return 0;
            }
            int left = 0;
            int right = height.length - 1;
            int maxLeft = height[left];
            int maxRight = height[right];
            int res = 0;

            while (left < right){
                if (maxLeft < maxRight){
                    left++;
                    maxLeft = Math.max(maxLeft, height[left]);
                    res += maxLeft - height[left];
                } else {
                    right--;
                    maxRight = Math.max(maxRight, height[right]);
                    res += maxRight - height[right];
                }

            }
            return res;

        }
    }
}
