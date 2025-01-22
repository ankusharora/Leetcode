package amazon.Matrix2D;

public class RotateImage {
    public void rotate(int[][] matrix) {

        //transpose and swap i,j with j, i

        int rows = matrix.length;
        int columns = matrix[0].length;

        // transpose diognal
        for (int i = 0; i < rows; i++){
            for (int j = i; j < rows; j++){
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Swap two values like two pointer approach
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < (rows/2); j++){
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][rows - 1 - j];
                matrix[i][rows - 1 - j] = temp;
            }
        }

    }
}
