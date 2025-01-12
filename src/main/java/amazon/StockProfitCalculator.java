package amazon;

import java.util.*;

public class StockProfitCalculator {
    public static int maxProfit(int[] price, int[] futurePrice, int principle) {
        // Step 1: Create a list of stocks with their price and profit
        List<Stock> stocks = new ArrayList<>();
        for (int i = 0; i < price.length; i++) {
            if (price[i] <= principle) { // Only consider stocks within the budget
                int profit = futurePrice[i] - price[i];
                if (profit > 0) { // Only consider profitable stocks
                    stocks.add(new Stock(price[i], profit));
                }
            }
        }

        // Step 2: Sort stocks by price (ascending), and by profit (descending) if prices are equal
        stocks.sort((a, b) -> {
            if (a.price == b.price) {
                return Integer.compare(b.profit, a.profit);
            }
            return Integer.compare(a.price, b.price);
        });

        // Step 3: Calculate maximum profit
        int totalProfit = 0;
        for (Stock stock : stocks) {
            if (principle >= stock.price) {
                totalProfit += stock.profit;
                principle -= stock.price; // Deduct the price from the available principle
            }
        }

        return totalProfit;
    }

    public static void main(String[] args) {
        int[] price = {10, 50, 30, 40, 70};
        int[] futurePrice = {100, 40, 50, 30, 90};
        int principle = 100;

        int maxProfit = maxProfit(price, futurePrice, principle);
        System.out.println("Maximum Profit: " + maxProfit); // Expected: 120
    }

    // Helper class to represent a stock
    static class Stock {
        int price;
        int profit;

        Stock(int price, int profit) {
            this.price = price;
            this.profit = profit;
        }
    }
}
