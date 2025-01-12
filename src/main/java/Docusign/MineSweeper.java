package Docusign;

import java.util.LinkedList;
import java.util.Queue;

public class MineSweeper {

    public char[][] updateBoard(char[][] board, int[] click) {
        int row = click[0];
        int col = click[1];

        // If clicked on a mine, change it to 'X' and return the board
        if (board[row][col] == 'M') {
            board[row][col] = 'X';
            return board;
        }

        // Initialize BFS queue
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{row, col});

        // Directions for the 8 neighboring cells
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},         {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int curRow = current[0];
            int curCol = current[1];

            // Skip if the cell is not empty
            if (board[curRow][curCol] != 'E') {
                continue;
            }

            // Count adjacent mines
            int mineCount = 0;
            for (int[] dir : directions) {
                int newRow = curRow + dir[0];
                int newCol = curCol + dir[1];
                if (newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board[0].length && board[newRow][newCol] == 'M') {
                    mineCount++;
                }
            }

            if (mineCount > 0) {
                // Update the cell with the mine count
                board[curRow][curCol] = (char) (mineCount + '0');
            } else {
                // Mark the cell as 'B' and add neighbors to the queue
                board[curRow][curCol] = 'B';
                for (int[] dir : directions) {
                    int newRow = curRow + dir[0];
                    int newCol = curCol + dir[1];
                    if (newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board[0].length && board[newRow][newCol] == 'E') {
                        queue.add(new int[]{newRow, newCol});
                    }
                }
            }
        }

        return board;
    }

}
