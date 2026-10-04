// 12.16 - The Floor Is Lava
// Run: node 12_16_the_floor_is_lava.js

function segmentDistance(min1, max1, min2, max2) {
  return Math.max(0, Math.max(min1, min2) - Math.min(max1, max2));
}

function distance(furniture1, furniture2) {
  const [xMin1, yMin1, xMax1, yMax1] = furniture1;
  const [xMin2, yMin2, xMax2, yMax2] = furniture2;
  // Calculate the x and y gaps between rectangles
  const xGap = segmentDistance(xMin1, xMax1, xMin2, xMax2);
  const yGap = segmentDistance(yMin1, yMax1, yMin2, yMax2);
  if (xGap === 0) {
    return yGap;
  } else if (yGap === 0) {
    return xGap;
  } else {
    return Math.sqrt(xGap ** 2 + yGap ** 2);
  }
}

function canReach(furniture, d) {
  const V = furniture.length;
  const graph = Array(V)
    .fill()
    .map(() => []);
  for (let i = 0; i < V; i++) {
    for (let j = i + 1; j < V; j++) {
      if (distance(furniture[i], furniture[j]) <= d) {
        graph[i].push(j);
        graph[j].push(i);
      }
    }
  }

  const visited = new Set([0]);

  function visit(node) {
    for (const nbr of graph[node]) {
      if (!visited.has(nbr)) {
        visited.add(nbr);
        visit(nbr);
      }
    }
  }

  visit(0);
  return visited.has(V - 1);
}


function runTests() {
  const tests = [
    // Example 1 from the book:
    [
      [
        [1, 1, 9, 5],
        [12, 9, 20, 13],
        [16, 2, 22, 7],
        [24, 9, 26, 11],
        [29, 1, 31, 5],
      ],
      5,
      true,
    ],
    // Example 2 from the book:
    [
      [
        [1, 1, 9, 5],
        [12, 9, 20, 13],
        [16, 2, 22, 7],
        [24, 9, 26, 11],
        [29, 1, 31, 5],
      ],
      4,
      false,
    ],
    // Line of furniture pieces
    [
      [
        [0, 0, 1, 1],
        [1, 1, 2, 2],
        [2, 2, 3, 3],
        [3, 3, 4, 4],
        [4, 4, 5, 5],
      ],
      0,
      true,
    ],
    [
      [
        [0, 0, 1, 1],
        [1, 1, 2, 2],
        [2, 2, 3, 3],
        [3, 3, 4, 4],
        [4, 4, 5, 5],
      ],
      1,
      true,
    ],
    [
      [
        [0, 0, 1, 1],
        [1, 1, 2, 2],
        [3, 3, 4, 4],
        [4, 4, 5, 5],
      ],
      1,
      false,
    ],
    [
      [
        [0, 0, 1, 1],
        [1, 1, 2, 2],
        [3, 3, 4, 4],
        [4, 4, 5, 5],
      ],
      2,
      true,
    ],
    // Single piece of furniture
    [[[0, 0, 1, 1]], 5, true],
    // Two pieces far apart
    [
      [
        [0, 0, 1, 1],
        [10, 10, 11, 11],
      ],
      5,
      false,
    ],
    // Two pieces just within reach
    [
      [
        [0, 0, 1, 1],
        [5, 5, 6, 6],
      ],
      5.7,
      true,
    ],
    // Two pieces just out of reach
    [
      [
        [0, 0, 1, 1],
        [5, 5, 6, 6],
      ],
      5.6,
      false,
    ],
    // Pieces in a line
    [
      [
        [0, 0, 1, 1],
        [1, 0, 2, 1],
        [2, 0, 3, 1],
      ],
      1.5,
      true,
    ],
  ];
  for (const [furniture, d, want] of tests) {
    const got = canReach(furniture, d);
    if (got !== want) {
      throw new Error(
        `\ncanReach(${JSON.stringify(furniture)}, ${d}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
