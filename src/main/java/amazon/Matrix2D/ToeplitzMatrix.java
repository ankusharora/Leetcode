package amazon.Matrix2D;

/**
 * A Toeplitz matrix has all elements along each diagonal (top-left to bottom-right) the same.
 * Traverse each cell (i, j) in the matrix and check against its top-left neighbor (i-1, j-1).
 * Skip cells in the first row or first column, as they don’t have top-left neighbors.
 * If any mismatch is found, return false; otherwise, return true after traversal.
 */
public class ToeplitzMatrix {
    public boolean isToeplitzMatrix(int[][] matrix) {

        int rows = matrix.length;
        int cols = matrix[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                if (i > 0 && j > 0 && matrix[i - 1][j - 1] != matrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
