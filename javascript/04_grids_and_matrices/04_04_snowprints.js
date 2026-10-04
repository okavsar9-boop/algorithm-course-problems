// 4.4 - Snowprints
// Run: node 04_04_snowprints.js

function distanceToRiver(field) {
  const R = field.length;
  const C = field[0].length;

  function hasFootprints(r, c) {
    return 0 <= r && r < R && 0 <= c && c < C && field[r][c] === 1;
  }

  // Find starting position in first column
  let r = 0;
  while (r < R && !field[r][0]) {
    r++;
  }

  let closest = r;
  let c = 0;

  // Track fox through remaining columns
  while (c < C - 1) {
    // Stop before last column
    let found = false;
    for (const dirR of [-1, 0, 1]) {
      // Check up, same level, down
      const newR = r + dirR;
      const newC = c + 1;
      if (hasFootprints(newR, newC)) {
        r = newR;
        c = newC;
        closest = Math.min(closest, r);
        found = true;
        break;
      }
    }
    if (!found) {
      // No valid move found
      break;
    }
  }

  return closest;
}


function runTests() {
  const tests = [
    // Example from book
    [
      [
        [0, 0, 0, 0, 0, 0],
        [0, 0, 1, 0, 0, 0],
        [1, 1, 0, 1, 0, 0],
        [0, 0, 0, 0, 1, 1],
      ],
      1,
    ],
    // Edge case - top of grid
    [
      [
        [0, 0, 0, 1, 0, 0],
        [0, 0, 1, 0, 1, 0],
        [1, 1, 0, 0, 0, 1],
        [0, 0, 0, 0, 0, 0],
      ],
      0,
    ],
    // Edge case - bottom of grid
    [
      [
        [0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [1, 1, 1, 1, 1, 1],
      ],
      3,
    ],
    // Edge case - single column
    [[[0], [1]], 1],
    // Edge case - single row
    [[[1, 1, 1]], 0],
    // Edge case - zigzag path
    [
      [
        [0, 0, 0],
        [1, 0, 0],
        [0, 1, 0],
        [0, 0, 1],
      ],
      1,
    ],
  ];

  for (const [field, want] of tests) {
    const got = distanceToRiver(field);
    if (got !== want) {
      throw new Error(
        `\ndistanceToRiver(${JSON.stringify(field)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
