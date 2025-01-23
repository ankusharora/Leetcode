package amazon.dp;

import java.util.Arrays;

public class CoinChange {

    public int coinChange(int[] coins, int amount) {

//        Input: coins = [1,2,5], amount = 11
//        Output: 3
//        Explanation: 11 = 5 + 5 + 1

        int[] dp = new int[amount + 1];

        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int i = 1; i <= amount; i++){
            for (int j = 0; j < coins.length; j++){
                if (coins[j] <= i){
                    dp[i] = Math.min(dp[i], dp[i - coins[j]]+ 1);
                }
            }
        }

        return dp[amount]> amount ? -1  : dp[amount];
    }

}
