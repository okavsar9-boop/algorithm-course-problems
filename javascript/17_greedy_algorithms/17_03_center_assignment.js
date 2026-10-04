// 17.3 - Center Assignment
// Run: node 17_03_center_assignment.js

function minimizeDistance(points, center1, center2) {
  // Function to calculate Euclidean distance between two points
  function dist(point1, point2) {
    return Math.sqrt(
      Math.pow(point1[0] - point2[0], 2) + Math.pow(point1[1] - point2[1], 2),
    );
  }

  const n = points.length;
  const assignment = new Array(n).fill(0);
  let baseline = 0;
  for (let i = 0; i < n; i++) {
    if (dist(points[i], center1) <= dist(points[i], center2)) {
      assignment[i] = 1;
      baseline += dist(points[i], center1);
    } else {
      assignment[i] = 2;
      baseline += dist(points[i], center2);
    }
  }

  const c1Count = assignment.filter((x) => x === 1).length;
  if (c1Count === Math.floor(n / 2)) {
    return baseline;
  }

  const switchCosts = [];
  for (let i = 0; i < n; i++) {
    if (assignment[i] === 1 && c1Count > Math.floor(n / 2)) {
      switchCosts.push(dist(points[i], center2) - dist(points[i], center1));
    }
    if (assignment[i] === 2 && c1Count < Math.floor(n / 2)) {
      switchCosts.push(dist(points[i], center1) - dist(points[i], center2));
    }
  }

  let res = baseline;
  switchCosts.sort((a, b) => a - b);
  for (let i = 0; i < Math.abs(c1Count - Math.floor(n / 2)); i++) {
    res += switchCosts[i];
  }
  return res;
}


function runTests() {
  const tests = [
    // Example 1
    [
      [
        [0, 1],
        [1, 0],
        [-1, 0],
        [0, -1],
      ],
      [0, 0],
      [1, 1],
      4,
    ],
    // Example 2
    [
      [
        [0, 0],
        [0, 0],
      ],
      [0, 0],
      [1, 1],
      1.414,
    ],
    // Example 3
    [
      [
        [0, 0.5],
        [1, 0.5],
      ],
      [0, 0],
      [1, 1],
      1,
    ],
    // Example 4
    [[], [0.3, -3.3], [-1.6, 4.6], 0],
    // Example 5
    [
      [
        [0, 0],
        [3, 0],
      ],
      [2, 0],
      [5, 0],
      4,
    ],
    // Additional test cases
    // Edge case: All points are the same
    [
      [
        [0, 0],
        [0, 0],
        [0, 0],
        [0, 0],
      ],
      [0, 0],
      [0, 0],
      0,
    ],
  ];

  for (const [points, center1, center2, want] of tests) {
    const got = minimizeDistance(points, center1, center2);
    if (!(Math.abs(got - want) < 1e-3)) {
      throw new Error(
        `\nminimizeDistance(${JSON.stringify(points)}, ${JSON.stringify(center1)}, ${JSON.stringify(center2)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
