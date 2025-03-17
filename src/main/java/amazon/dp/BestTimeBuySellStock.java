package amazon.dp;

public class BestTimeBuySellStock {
    public int maxProfit(int[] prices) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < min) {
                min = prices[i];
            }

            int currProf = prices[i] - min;
            if(currProf > max) {
                max = currProf;
            }
        }

        return max;
    }
}
