package Doordash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * TODO: THIS NEEDS TO BE FILED IN FOR CHECKSTYLE
 */
public class GetClosestDashMart {

    private static final char EMPTY = ' ';
    private static final char DASHMART = 'D';
    private static final List<int[]> DIRECTIONS = Arrays.asList(
            new int[] { 1,  0},
            new int[] {-1,  0},
            new int[] { 0,  1},
            new int[] { 0, -1}
    );

    public static void main(String[] args) {
        char[][] locationBoard = {
                {'X', ' ', ' ', 'D', ' ', ' ', 'X', ' ', 'X'},
                {'X', ' ', 'X', 'X', ' ', ' ', ' ', ' ', 'X'},
                {' ', ' ', ' ', 'D', 'X', 'X', ' ', 'X', ' '},
                {' ', ' ', ' ', 'D', ' ', 'X', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ', 'X'},
                {' ', ' ', ' ', ' ', 'X', ' ', ' ', 'X', 'X'}};

        distanceToNearestGate(locationBoard);

        List<List<Integer>> locationList = Arrays.asList(Arrays.asList(2,2), Arrays.asList(4,0), Arrays.asList(0,4), Arrays.asList(2,6));
        List<Integer> answer = new ArrayList<>();
        for (List<Integer> location : locationList) {
            answer.add(locationBoard[location.get(0)][location.get(1)] - 'D');
        }
        System.out.println(answer);
    }

    private static char[][] distanceToNearestGate(char[][] rooms) {
        int m = rooms.length;
        if (m == 0) return rooms;
        int n = rooms[0].length;
        Queue<int[]> q = new LinkedList<>();
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (rooms[row][col] == DASHMART) {
                    q.add(new int[] { row, col });
                }
            }
        }
        while (!q.isEmpty()) {
            int[] point = q.poll();
            int row = point[0];
            int col = point[1];
            for (int[] direction : DIRECTIONS) {
                int r = row + direction[0];
                int c = col + direction[1];
                if (r < 0 || c < 0 || r >= m || c >= n || rooms[r][c] != EMPTY) {
                    continue;
                }
                rooms[r][c] = (char)(rooms[row][col] + 1);
                q.add(new int[] { r, c });
            }
        }
        return rooms;
    }
}