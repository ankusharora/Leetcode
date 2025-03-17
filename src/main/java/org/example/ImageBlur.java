package org.example;

import java.util.*;

public class ImageBlur {
    public static int[][] solution(int[][] image, int radius) {
        int m = image.length, n = image[0].length;
        int[][] result = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                List<Integer> neighbors = new ArrayList<>();
                // Collect valid neighbors within the radius
                for (int k = Math.max(0, i - radius); k <= Math.min(m - 1, i + radius); k++) {
                    for (int l = Math.max(0, j - radius); l <= Math.min(n - 1, j + radius); l++) {
                        if (k != i || l != j) { // Exclude the center pixel itself
                            neighbors.add(image[k][l]);
                        }
                    }
                }

                // Compute the mean of valid neighbors
                int meanIntensity = 0;
                if (!neighbors.isEmpty()) {
                    int sum = 0;
                    for (int val : neighbors) {
                        sum += val;
                    }
                    meanIntensity = sum / neighbors.size();
                }

                // Apply the blurring formula
                result[i][j] = (image[i][j] + meanIntensity) / 2;
            }
        }
        return result;
    }

    // Utility function to print the matrix
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void main(String[] args) {
        int[][] image = {
                {9, 6}, {3, 0}
        };
        int radius = 1;

        int[][] blurredImage = solution(image, radius);
        printMatrix(blurredImage);
    }
}

