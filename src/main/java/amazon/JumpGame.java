package amazon;

public class JumpGame {
    public boolean canJump(int[] nums) {
        int maxReach = 0; // Tracks the furthest index we can reach
        for (int i = 0; i < nums.length; i++) {
            if (i > maxReach) {
                return false; // If current index is beyond the max reachable index
            }
            maxReach = Math.max(maxReach, i + nums[i]);
        }
        return true; // If we can reach or exceed the last index
    }
}
