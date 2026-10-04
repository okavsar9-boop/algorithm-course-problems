// 12.13 - Count Grid Islands
// Run: node 12_13_count_grid_islands.js

function gridDfs(grid, visited, startR, startC) {
  // Returns if (r, c) is in bounds, not visited, and "walkable."
  function isValid(r, c) {
    return (
      0 <= r &&
      r < grid.length &&
      0 <= c &&
      c < grid[0].length &&
      !visited.has(`${r},${c}`) &&
      grid[r][c] === 1
    );
  }

  const directions = [
    [-1, 0],
    [1, 0],
    [0, 1],
    [0, -1],
  ];

  function visit(r, c) {
    for (const [dirR, dirC] of directions) {
      const nbrR = r + dirR;
      const nbrC = c + dirC;
      if (isValid(nbrR, nbrC)) {
        visited.add(`${nbrR},${nbrC}`);
        visit(nbrR, nbrC);
      }
    }
  }

  visit(startR, startC);
}

function countIslands(grid) {
  const R = grid.length;
  const C = grid[0].length;
  let count = 0;
  const visited = new Set();
  for (let r = 0; r < R; r++) {
    for (let c = 0; c < C; c++) {
      if (grid[r][c] === 1 && !visited.has(`${r},${c}`)) {
        visited.add(`${r},${c}`);
        gridDfs(grid, visited, r, c);
        count++;
      }
    }
  }
  return count;
}


function countIslandsInPlace(grid) {
  const directions = [[-1, 0], [1, 0], [0, 1], [0, -1]];
  const R = grid.length;
  const C = grid[0].length;

  function inBounds(r, c) {
    return 0 <= r && r < R && 0 <= c && c < C;
  }

  // Finds the parent in the stack (neighbor with less negative stack value by 1)
  function findParent(r, c) {
    const currentValue = grid[r][c];
    for (const [dr, dc] of directions) {
      const nbrR = r + dr;
      const nbrC = c + dc;
      if (inBounds(nbrR, nbrC) && grid[nbrR][nbrC] === currentValue + 1) {
        return [nbrR, nbrC];
      }
    }
    return [null, null];
  }

  function findUnvisitedNeighbor(r, c) {
    for (const [dr, dc] of directions) {
      const nbrR = r + dr;
      const nbrC = c + dc;
      if (inBounds(nbrR, nbrC) && grid[nbrR][nbrC] === 1) {
        return [nbrR, nbrC];
      }
    }
    return [null, null];
  }

  function iterativeDfs(startR, startC) {
    let stackLevel = -1;
    grid[startR][startC] = stackLevel;
    let headR = startR;
    let headC = startC;
    while (stackLevel < 0) {
      const [nbrR, nbrC] = findUnvisitedNeighbor(headR, headC);
      if (nbrR !== null) {
        stackLevel--;
        grid[nbrR][nbrC] = stackLevel;
        headR = nbrR;
        headC = nbrC;
      } else {
        // No unvisited neighbors, backtrack
        stackLevel++;
        [headR, headC] = findParent(headR, headC);
      }
    }
  }

  let islandCount = 0;
  for (let startR = 0; startR < R; startR++) {
    for (let startC = 0; startC < C; startC++) {
      if (grid[startR][startC] === 1) {
        iterativeDfs(startR, startC);
        islandCount++;
      }
    }
  }
  return islandCount;
}

function runTests() {
  const tests = [
    // Example 1 from the book
    [
      [
        [0, 0, 1, 0],
        [1, 1, 0, 1],
        [0, 0, 1, 1],
      ],
      3,
    ],
    // Example 2 from the book
    [[[]], 0],
    // Edge case - single cell
    [[[1]], 1],
    // Edge case - all water
    [
      [
        [0, 0],
        [0, 0],
      ],
      0,
    ],
    // Edge case - all land
    [
      [
        [1, 1],
        [1, 1],
      ],
      1,
    ],
    // Multiple islands
    [
      [
        [1, 0, 1],
        [0, 0, 0],
        [1, 0, 1],
      ],
      4,
    ],
  ];

  // Test countIslands function
  for (const [gridTemplate, want] of tests) {
    const grid = JSON.parse(JSON.stringify(gridTemplate)); // Deep copy
    const got = countIslands(grid);
    if (got !== want) {
      throw new Error(
        `\ncountIslands(${JSON.stringify(gridTemplate)}): got: ${got}, want: ${want}\n`,
      );
    }
  }

  // Test countIslandsInPlace function
  for (const [gridTemplate, want] of tests) {
    const grid = JSON.parse(JSON.stringify(gridTemplate)); // Deep copy
    const got = countIslandsInPlace(grid);
    if (got !== want) {
      throw new Error(
        `\ncountIslandsInPlace(${JSON.stringify(gridTemplate)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
