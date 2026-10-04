// 7.2 - Nested Circles
// Run: node 07_02_nested_circles.js

function contains(c1, c2) {
  const [[x1, y1], r1] = c1;
  const [[x2, y2], r2] = c2;
  const centerDistance = Math.sqrt((x1 - x2) ** 2 + (y1 - y2) ** 2);
  return centerDistance + r2 < r1;
}

function areCirclesNested(circles) {
  circles.sort((a, b) => b[1] - a[1]); // sort by radius in descending order

  for (let i = 0; i < circles.length - 1; i++) {
    if (!contains(circles[i], circles[i + 1])) {
      return false;
    }
  }
  return true;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [
      [
        [[4, 4], 5],
        [[8, 4], 2],
      ],
      false,
    ],
    // Example 2 from the book
    [
      [
        [[5, 3], 3],
        [[5, 3], 2],
        [[4, 4], 5],
      ],
      true,
    ],
    // Example 3 from the book
    [[[[5, 3], 3]], true],
    // Edge case - two identical circles
    [
      [
        [[1, 1], 2],
        [[1, 1], 2],
      ],
      false,
    ],
    // Edge case - touching circles
    [
      [
        [[0, 0], 4],
        [[0, 0], 2],
      ],
      true,
    ],
    // Edge case - empty list
    [[], true],
    // Edge case - negative coordinates
    [
      [
        [[-5, -3], 4],
        [[-5, -3], 2],
      ],
      true,
    ],
    // Edge case - negative radius
    [[[[0, 0], -2]], true],
    // Edge case - max coordinate values
    [
      [
        [[10000, 10000], 10000],
        [[0, 0], 100],
      ],
      false,
    ],
    // Edge case - min coordinate values
    [
      [
        [[-10000, -10000], 10000],
        [[0, 0], 100],
      ],
      false,
    ],
    // Edge case - multiple circles with same center
    [
      [
        [[1, 1], 5],
        [[1, 1], 4],
        [[1, 1], 3],
        [[1, 1], 2],
      ],
      true,
    ],
    // Edge case - circles not sorted by radius
    [
      [
        [[0, 0], 2],
        [[0, 0], 4],
        [[0, 0], 3],
      ],
      true,
    ],
  ];

  for (const [circles, want] of tests) {
    const got = areCirclesNested(circles);
    if (got !== want) {
      throw new Error(
        `\nareCirclesNested(${JSON.stringify(circles)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
