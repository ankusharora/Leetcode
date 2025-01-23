package amazon;

import java.util.*;
import java.util.Queue;

public class RottingOranges {
    public int orangesRotting(int[][] grid) {
        Queue<int[]> queue = new LinkedList<>();
        int rows = grid.length;
        int cols = grid[0].length;
        int oranges = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[] { i, j });
                } else if (grid[i][j] == 1) {
                    oranges++;
                }
            }
        }
        int time = 0;
        int[][] directions = { { 1, 0 }, { 0, 1 }, { 0, -1 }, { -1, 0 } };
        while (!queue.isEmpty()) {
            int level = queue.size();

            for (int i = 0; i < level; i++) {
                int[] currentOrange = queue.poll();
                int currentOrangeX = currentOrange[0];
                int currentOrangeY = currentOrange[1];

                for (int[] direction : directions) {
                    int x = direction[0] + currentOrangeX;
                    int y = direction[1] + currentOrangeY;
                    if (x < rows && y < cols && x >= 0 && y >= 0 && grid[x][y] == 1) {
                        grid[x][y] = 2;
                        queue.offer(new int[] { x, y });
                        oranges--;

                    }
                }
            }


            time++;
        }

        return oranges == 0 ? time : -1;
    }
}
