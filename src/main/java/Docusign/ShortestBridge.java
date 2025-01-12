package Docusign;

import java.util.LinkedList;
import java.util.Queue;

public class ShortestBridge {

    public int shortestBridge(int[][] grid) {


        int row = grid.length;
        int col = grid[0].length;

        int steps = 0;
        boolean found = false;

        Queue<int[]> queue = new LinkedList<>();

        for (int i = 0; i < row; i++){
            if (found) break;
            for (int j = 0; j < col; j++){
                if (grid[i][j] == 1){
                    found = true;
                    dfs(grid, i, j, queue);
                    break;
                }
            }
        }

        int[][] directions = new int[][] { { 1,0 }, { -1,0 }, {0,1}, {0,-1} };

        while (!queue.isEmpty()){
            int size = queue.size();
            for (int i = 0; i < size; i++){
                int[] curr = queue.poll();


                for(int[] dir: directions){
                    int x = curr[0] + dir[0];
                    int y = curr[1] + dir[1];

                    if (x >= 0 && x < row && y >= 0 && y < col){
                        if (grid[x][y] == 1) return steps;

                        if (grid[x][y] == 0){
                            grid[x][y] = 2;
                            queue.offer(new int[] {x, y});
                        }

                    }
                }
            }
            steps++;
        }

        return -1;
    }


    private void dfs(int[][] grid, int i, int j, Queue<int[]> queue){

        if ( i < 0 || i >= grid.length || j < 0 || j >= grid[0].length|| grid[i][j] != 1  ){
            return;
        }

        grid[i][j] = 2;
        queue.add(new int[] {i, j});
        dfs(grid, i+1, j, queue);
        dfs(grid, i-1, j, queue);
        dfs(grid, i, j+1, queue);
        dfs(grid, i, j-1, queue);
    }

}
