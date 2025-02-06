package Docusign;

public class WaysToSum {

    public static int countWays(int total, int k) {
        int[][] dp = new int[k + 1][total + 1];

        // Base cases
        for (int i = 0; i <= k; i++) {
            dp[i][0] = 1; // There's one way to make sum 0 (using no items)
        }

        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= total; j++) {
                if (i > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - i];
                }
            }
        }

        return dp[k][total];
    }

    public static void main(String[] args) {
        int total = 5;
        int k = 3;

        int ways = countWays(total, k);
        System.out.println("Number of ways: " + ways);
    }
}