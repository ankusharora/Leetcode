package amazon.Matrix2D;

/**
 * Simulate Diagonal Traversal: Move through the matrix diagonally in two alternating directions:
 * <p>
 * Up-right (direction = 1): Move diagonally upward.
 * Down-left (direction = -1): Move diagonally downward.
 * <p>
 * Handle Matrix Edges:
 * <p>
 * If moving up-right and hitting the top edge, move to the next column.
 * If moving up-right and hitting the right edge, move to the next row.
 * If moving down-left and hitting the bottom edge, move to the next column.
 * If moving down-left and hitting the left edge, move to the next row.
 * <p>
 * Switch Direction: Flip the direction whenever an edge is hit.
 * <p>
 * Fill Result Array: Store each element in the result array while traversing the matrix.
 */
public class DiagonalTraverse {
    public int[] findDiagonalOrder(int[][] mat) {
        if (mat == null || mat.length == 0) return new int[0];

        int m = mat.length, n = mat[0].length;
        int[] result = new int[m * n];
        int row = 0, col = 0, direction = 1; // direction: 1 for up-right, -1 for down-left

        for (int i = 0; i < m * n; i++) {
            result[i] = mat[row][col];
            //0,0 --> 1
            if (direction == 1) {
                if (col == n - 1) { // Hit the right edge
                    row++;
                    direction = -1;
                } else if (row == 0) { // Hit the top edge
                    col++;
                    direction = -1;
                } else {
                    row--;
                    col++;
                }


            } else {

                if (row == m - 1) { // Hit the bottom edge
                    col++;
                    direction = 1;
                } else if (col == 0) { // Hit the left edge
                    row++;
                    direction = 1;
                } else { // Normal case
                    row++;
                    col--;
                }


            }

        }

        return result;

    }
}
