package snap;

import java.util.*;

public class ConnectIslands {
    private static final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int minConversionsToConnectLargestIslands(int[][] grid) {
        int n = grid.length;
        Map<Integer, Integer> islandSizes = new HashMap<>();
        int islandId = 2;  // Start marking islands from ID 2
        int max1 = 0, max2 = 0; // Largest and second largest island sizes
        int[][] islandMap = new int[n][n];

        // Step 1: Identify islands and store their sizes
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    int size = dfs(grid, r, c, islandId);
                    islandSizes.put(islandId, size);
                    islandMap[r][c] = islandId;

                    // Update largest and second-largest island sizes
                    if (size > max1) {
                        max2 = max1;
                        max1 = size;
                    } else if (size > max2) {
                        max2 = size;
                    }

                    islandId++;
                }
            }
        }

        if (max1 == 0 || max2 == 0) return -1; // No two islands to connect

        // Step 2: Find the shortest bridge between the two largest islands
        return findShortestBridge(grid, islandMap, islandSizes, max1, max2);
    }

    private int dfs(int[][] grid, int r, int c, int islandId) {
        int n = grid.length;
        if (r < 0 || r >= n || c < 0 || c >= n || grid[r][c] != 1) {
            return 0;
        }

        grid[r][c] = islandId; // Mark the land with a unique island ID
        int size = 1;

        for (int[] dir : DIRECTIONS) {
            size += dfs(grid, r + dir[0], c + dir[1], islandId);
        }

        return size;
    }

    private int findShortestBridge(int[][] grid, int[][] islandMap, Map<Integer, Integer> islandSizes, int max1, int max2) {
        int n = grid.length;
        int minConversion = Integer.MAX_VALUE;

        // Step 3: Iterate through water cells (`0`s) and check connections
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 0) {
                    Set<Integer> connectedIslands = new HashSet<>();
                    int minDistance = Integer.MAX_VALUE;

                    for (int[] dir : DIRECTIONS) {
                        int newRow = r + dir[0];
                        int newCol = c + dir[1];

                        if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n) {
                            int islandId = islandMap[newRow][newCol];
                            if (islandId > 1) {
                                connectedIslands.add(islandId);
                            }
                        }
                    }

                    // If this `0` connects two different islands, compute the conversion count
                    if (connectedIslands.size() > 1) {
                        minConversion = Math.min(minConversion, 1);
                    }
                }
            }
        }

        return minConversion == Integer.MAX_VALUE ? -1 : minConversion;
    }

    public static void main(String[] args) {
        ConnectIslands solution = new ConnectIslands();
        int[][] grid = {
                {1, 1, 0, 0, 1},
                {1, 0, 0, 1, 1},
                {0, 0, 1, 0, 0},
                {1, 1, 0, 1, 1}
        };
        System.out.println("Min conversions: " + solution.minConversionsToConnectLargestIslands(grid)); // Output: 1
    }
}
