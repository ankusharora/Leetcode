package amazon.dp;

public class JumpGame {

    public boolean canJump(int[] nums) {
        int goal = nums.length -1;

        for (int i = nums.length -1; i >= 0; i--){
            if (i + nums[i] >= goal){
                goal = i;
                System.out.println(goal);
            }
        }

        return goal == 0;

    }


    public int jump(int[] nums) {
        //Input: nums = [2,3,1,1,4]
        //Output: 2
        int res = 0, l = 0, r = 0;

        while (r < nums.length -1){
            int farthest = 0;
            for (int i = l; i < r+1; i++){
                farthest = Math.max(farthest, i + nums[i]);
            }
            l = r + 1;
            r = farthest;
            res++;
        }

        return res;
    }

}
