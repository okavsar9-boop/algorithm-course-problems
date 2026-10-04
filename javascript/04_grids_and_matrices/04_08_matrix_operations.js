// 4.8 - Matrix Operations
// Run: node 04_08_matrix_operations.js

class Matrix {
  constructor(grid) {
    this.matrix = grid.map((row) => [...row]);
  }

  transpose() {
    const matrix = this.matrix;
    for (let r = 0; r < matrix.length; r++) {
      for (let c = 0; c < r; c++) {
        [matrix[r][c], matrix[c][r]] = [matrix[c][r], matrix[r][c]];
      }
    }
  }

  reflectHorizontally() {
    this.matrix.reverse();
  }

  reflectVertically() {
    for (const row of this.matrix) {
      row.reverse();
    }
  }

  rotateClockwise() {
    this.transpose();
    this.reflectVertically();
  }

  rotateCounterclockwise() {
    this.transpose();
    this.reflectHorizontally();
  }
}


function runTests() {
  const tests = [
    // Test transpose
    [
      [1, 2],
      [3, 4],
    ],
    "transpose",
    [
      [1, 3],
      [2, 4],
    ],
    // Test horizontal reflection
    [
      [1, 2],
      [3, 4],
    ],
    "reflectHorizontally",
    [
      [3, 4],
      [1, 2],
    ],
    // Test vertical reflection
    [
      [1, 2],
      [3, 4],
    ],
    "reflectVertically",
    [
      [2, 1],
      [4, 3],
    ],
    // Test clockwise rotation
    [
      [1, 2],
      [3, 4],
    ],
    "rotateClockwise",
    [
      [3, 1],
      [4, 2],
    ],
    // Test counterclockwise rotation
    [
      [1, 2],
      [3, 4],
    ],
    "rotateCounterclockwise",
    [
      [2, 4],
      [1, 3],
    ],
    // Edge case - 1x1 matrix
    [[5]],
    "transpose",
    [[5]],
    // Edge case - 3x3 matrix
    [
      [1, 2, 3],
      [4, 5, 6],
      [7, 8, 9],
    ],
    "rotateClockwise",
    [
      [7, 4, 1],
      [8, 5, 2],
      [9, 6, 3],
    ],
  ];

  for (let i = 0; i < tests.length; i += 3) {
    const grid = tests[i];
    const operation = tests[i + 1];
    const want = tests[i + 2];
    const matrix = new Matrix(grid);
    matrix[operation]();
    const got = matrix.matrix;
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nMatrix(${JSON.stringify(grid)}).${operation}(): ` +
          `got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
