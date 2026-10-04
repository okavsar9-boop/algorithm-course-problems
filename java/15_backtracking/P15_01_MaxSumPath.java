// 15.1 - Max-Sum Path
// Run: javac P15_01_MaxSumPath.java && java P15_01_MaxSumPath

import java.util.*;
import java.util.function.*;

def visit(partial_solution):
  if full_solution(partial_solution):
    # Process leaf/full solution.
  else:
    for choice in choices(partial_solution):
      # Prune children where possible.
      child = apply_choice(partial_solution)
      visit(child)

class MaxSumPathBacktracking {
  private final int[][] grid;
  private int maxSum;
  private final int R, C;

  public MaxSumPathBacktracking(int[][] grid) {
    this.grid = grid;
    this.R = grid.length;
    this.C = grid[0].length;
    this.maxSum = Integer.MIN_VALUE;
  }

  private void visit(int r, int c, int curSum) {
    if (r == R - 1 && c == C - 1) {
      maxSum = Math.max(maxSum, curSum);
      return;
    }

    if (r + 1 < R) {
      visit(r + 1, c, curSum + grid[r + 1][c]); // Go down.
    }
    if (c + 1 < C) {
      visit(r, c + 1, curSum + grid[r][c + 1]); // Go right.
    }
  }

  // Inefficient backtracking solution. DP is better!
  public int solve() {
    visit(0, 0, grid[0][0]);
    return maxSum;
  }
}

max_path(r, c): the maximum path sum starting from grid[r][c]

bottom-right corner (r == R - 1, c == C - 1):
  max_path(r, c) = grid[r][c]
last row (r == R - 1, c < C - 1):
  max_path(r, c) = grid[r][c] + max_path(r, c + 1)  # we can only go right
last column (r < R - 1, c == C - 1):
  max_path(r, c) = grid[r][c] + max_path(r + 1, c)  # we can only go down
general case (r < R - 1, c < C - 1):
  max_path(r, c) = grid[r][c] + max(max_path(r + 1, c),  # go down
                                    max_path(r, c + 1))  # go right

original problem: max_path(0, 0)

memo = empty map

f(subproblem_id):
  if subproblem is base case:
    return result directly
  if subproblem in memo map:
    return cached result

  memo[subproblem_id] = recurrence relation formula
  return memo[subproblem_id]

return f(initial subproblem)

class MaxSumPathMemoization {
  private final int[][] grid;
  private final Map<String, Integer> memo;
  private final int R, C;

  public MaxSumPathMemoization(int[][] grid) {
    this.grid = grid;
    this.R = grid.length;
    this.C = grid[0].length;
    this.memo = new HashMap<>();
  }

  private int dp(int r, int c) {
    String key = r + "," + c;
    if (memo.containsKey(key)) {
      return memo.get(key);
    }

    if (r == R - 1 && c == C - 1) {
      return grid[r][c];
    }

    int maxSum = Integer.MIN_VALUE;
    // Try going down
    if (r + 1 < R) {
      maxSum = Math.max(maxSum, grid[r][c] + dp(r + 1, c));
    }
    // Try going right
    if (c + 1 < C) {
      maxSum = Math.max(maxSum, grid[r][c] + dp(r, c + 1));
    }

    memo.put(key, maxSum);
    return maxSum;
  }

  public int solve() {
    return dp(0, 0);
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1 from the book
        { new int[][] { { 1, 4, 3 }, { 2, 7, 6 }, { 5, 8, 9 } }, 29 },
        // Example 2 from the book
        { new int[][] { { 5 } }, 5 },
        // Additional test cases
        // Edge case - single row
        { new int[][] { { 1, 2, 3, 4 } }, 10 },
        // Edge case - single column
        { new int[][] { { 1 }, { 2 }, { 3 }, { 4 } }, 10 },
        // Larger grid
        { new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } }, 29 },
        // Edge case - all elements are the same
        { new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, 5 },
    };

    for (Object[] test : tests) {
      int[][] grid = (int[][]) test[0];
      int want = (int) test[1];

      int gotBacktracking = new MaxSumPathBacktracking(grid).solve();
      int gotMemoization = new MaxSumPathMemoization(grid).solve();

      if (gotBacktracking != gotMemoization) {
        throw new RuntimeException(String.format(
            "\nMaxSumPathBacktracking(%s) != MaxSumPathMemoization(%s)\n",
            Arrays.deepToString(grid), Arrays.deepToString(grid)));
      }
      if (gotBacktracking != want) {
        throw new RuntimeException(String.format(
            "\nMaxSumPathBacktracking(%s): got: %d, want: %d\n",
            Arrays.deepToString(grid), gotBacktracking, want));
      }
      if (gotMemoization != want) {
        throw new RuntimeException(String.format(
            "\nMaxSumPathMemoization(%s): got: %d, want: %d\n",
            Arrays.deepToString(grid), gotMemoization, want));
      }
    }
  }
}

public class P15_01_MaxSumPath {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
