package amazon;

public class Knapsack01 {

    public static int knapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacity; w++) {
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], values[i - 1] + dp[i - 1][w - weights[i - 1]]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        return dp[n][capacity];
    }

    public static int unboundedKnapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[] dp = new int[capacity + 1];

        for (int w = 0; w <= capacity; w++) {
            for (int i = 0; i < n; i++) {
                if (weights[i] <= w) {
                    dp[w] = Math.max(dp[w], values[i] + dp[w - weights[i]]);
                }
            }
        }

        return dp[capacity];
    }

    public static void main(String[] args) {
        int[] weights = {1, 3, 4, 5}    ;
        int[] values = {10, 40, 50, 70};
        int capacity = 8;

        System.out.println("Maximum value: " + knapsack(weights, values, capacity));
        System.out.println("Unblounded knapsack value: " + unboundedKnapsack(weights, values, capacity));

    }
}
