package snap;

import java.util.*;

public class LargestIsland {
    public int largestIslandSize(int[][] grid) {
        int n = grid.length;
        boolean[][] visited = new boolean[n][n];
        int maxIslandSize = 0;

        // Iterate through the grid to find all islands
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1 && !visited[r][c]) {
                    int size = dfs(grid, visited, r, c);
                    maxIslandSize = Math.max(maxIslandSize, size);
                }
            }
        }
        return maxIslandSize;
    }

    private int dfs(int[][] grid, boolean[][] visited, int r, int c) {
        int n = grid.length;
        if (r < 0 || r >= n || c < 0 || c >= n || grid[r][c] == 0 || visited[r][c]) {
            return 0;
        }

        visited[r][c] = true; // Mark cell as visited
        int size = 1; // Count the current cell

        // Explore all 4 directions (up, down, left, right)
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            size += dfs(grid, visited, r + dir[0], c + dir[1]);
        }

        return size;
    }

    public static void main(String[] args) {
        LargestIsland solution = new LargestIsland();
        int[][] grid = {
                {1, 1, 0, 0},
                {1, 0, 1, 1},
                {0, 0, 1, 0},
                {1, 1, 0, 0}
        };
        System.out.println("Largest Island Size: " + solution.largestIslandSize(grid)); // Output: 3
    }
}
