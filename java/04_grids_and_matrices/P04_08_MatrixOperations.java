// 4.8 - Matrix Operations
// Run: javac P04_08_MatrixOperations.java && java P04_08_MatrixOperations

import java.util.*;
import java.util.function.*;

class Matrix {
  private double[][] matrix;

  public Matrix(double[][] grid) {
    matrix = new double[grid.length][];
    for (int i = 0; i < grid.length; i++) {
      matrix[i] = Arrays.copyOf(grid[i], grid[i].length);
    }
  }

  public void transpose() {
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < r; c++) {
        double temp = matrix[r][c];
        matrix[r][c] = matrix[c][r];
        matrix[c][r] = temp;
      }
    }
  }

  public void reflectHorizontally() {
    for (int i = 0; i < matrix.length / 2; i++) {
      double[] temp = matrix[i];
      matrix[i] = matrix[matrix.length - 1 - i];
      matrix[matrix.length - 1 - i] = temp;
    }
  }

  public void reflectVertically() {
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length / 2; c++) {
        double temp = matrix[r][c];
        matrix[r][c] = matrix[r][matrix[0].length - 1 - c];
        matrix[r][matrix[0].length - 1 - c] = temp;
      }
    }
  }

  public void rotateClockwise() {
    transpose();
    reflectVertically();
  }

  public void rotateCounterclockwise() {
    transpose();
    reflectHorizontally();
  }

  public double[][] getMatrix() {
    return matrix;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Test transpose
        { new double[][] { { 1.0, 2.0 }, { 3.0, 4.0 } }, "transpose",
            new double[][] { { 1.0, 3.0 }, { 2.0, 4.0 } } },
        // Test horizontal reflection
        { new double[][] { { 1.0, 2.0 }, { 3.0, 4.0 } }, "reflectHorizontally",
            new double[][] { { 3.0, 4.0 }, { 1.0, 2.0 } } },
        // Test vertical reflection
        { new double[][] { { 1.0, 2.0 }, { 3.0, 4.0 } }, "reflectVertically",
            new double[][] { { 2.0, 1.0 }, { 4.0, 3.0 } } },
        // Test clockwise rotation
        { new double[][] { { 1.0, 2.0 }, { 3.0, 4.0 } }, "rotateClockwise",
            new double[][] { { 3.0, 1.0 }, { 4.0, 2.0 } } },
        // Test counterclockwise rotation
        { new double[][] { { 1.0, 2.0 }, { 3.0, 4.0 } }, "rotateCounterclockwise",
            new double[][] { { 2.0, 4.0 }, { 1.0, 3.0 } } },
        // Edge case - 1x1 matrix
        { new double[][] { { 5.0 } }, "transpose", new double[][] { { 5.0 } } },
        // Edge case - 3x3 matrix
        { new double[][] {
            { 1.0, 2.0, 3.0 },
            { 4.0, 5.0, 6.0 },
            { 7.0, 8.0, 9.0 }
        }, "rotateClockwise",
            new double[][] {
                { 7.0, 4.0, 1.0 },
                { 8.0, 5.0, 2.0 },
                { 9.0, 6.0, 3.0 }
            } },
    };

    for (Object[] test : tests) {
      double[][] grid = (double[][]) test[0];
      String operation = (String) test[1];
      double[][] want = (double[][]) test[2];
      Matrix matrix = new Matrix(grid);
      try {
        Matrix.class.getMethod(operation).invoke(matrix);
        double[][] got = matrix.getMatrix();
        if (!Arrays.deepEquals(got, want)) {
          throw new RuntimeException(String.format(
              "\nMatrix(%s).%s(): got: %s, want: %s\n",
              Arrays.deepToString(grid), operation,
              Arrays.deepToString(got), Arrays.deepToString(want)));
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}

public class P04_08_MatrixOperations {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
