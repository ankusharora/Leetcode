package Doordash;

class StackOrders {
    private int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};

    public int maxOrders(int[][] city) {
        if (city == null || city.length == 0) return 0;

        int rows = city.length, cols = city[0].length;
        int[][] memo = new int[rows][cols]; // Memoization for DP
        int maxOrders = 0;

        // Try starting from every restaurant (cell)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maxOrders = Math.max(maxOrders, dfs(city, i, j, memo));
            }
        }

        return maxOrders;
    }

    private int dfs(int[][] city, int i, int j, int[][] memo) {
        // If already computed, return the stored result
        if (memo[i][j] > 0) return memo[i][j];

        int maxPath = 1; // Start with current cell

        for (int[] dir : directions) {
            int ni = i + dir[0], nj = j + dir[1];

            // Check bounds and increasing order condition
            if (ni >= 0 && ni < city.length && nj >= 0 && nj < city[0].length
                    && city[ni][nj] > city[i][j]) {
                maxPath = Math.max(maxPath, 1 + dfs(city, ni, nj, memo));
            }
        }

        memo[i][j] = maxPath; // Store computed value
        return maxPath;
    }

    // Main method to test the function with sample test cases
    public static void main(String[] args) {
        StackOrders sol = new StackOrders();

        // Test Case 1
        int[][] city1 = {
                {9, 9, 4},
                {6, 6, 8},
                {2, 1, 1}
        };
        System.out.println("Test Case 1: " + sol.maxOrders(city1)); // Expected Output: 4

        // Test Case 2 (Simple increasing path)
        int[][] city2 = {
                {1, 2, 3},
                {6, 5, 4},
                {7, 8, 9}
        };
        System.out.println("Test Case 2: " + sol.maxOrders(city2)); // Expected Output: 9

        // Test Case 3 (All same values)
        int[][] city3 = {
                {5, 5, 5},
                {5, 5, 5},
                {5, 5, 5}
        };
        System.out.println("Test Case 3: " + sol.maxOrders(city3)); // Expected Output: 1

    }
}
