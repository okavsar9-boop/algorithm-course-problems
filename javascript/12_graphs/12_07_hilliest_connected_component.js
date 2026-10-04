// 12.7 - Hilliest Connected Component
// Run: node 12_07_hilliest_connected_component.js

function labelNodesWithCcIds(graph) {
  const nodeToCc = new Map();

  function visit(node, ccId) {
    if (nodeToCc.has(node)) {
      return;
    }
    nodeToCc.set(node, ccId);
    for (const nbr of graph[node]) {
      visit(nbr, ccId);
    }
  }

  let ccId = 0;
  for (let node = 0; node < graph.length; node++) {
    if (!nodeToCc.has(node)) {
      visit(node, ccId);
      ccId++;
    }
  }

  return nodeToCc;
}

function maxHilliness(graph, heights) {
  const nodeToCc = labelNodesWithCcIds(graph); // Same as Problem 4.
  const V = graph.length;
  const ccToElevationGainSum = new Map();
  const ccToNumEdges = new Map();

  for (let node = 0; node < V; node++) {
    const cc = nodeToCc.get(node);
    if (!ccToNumEdges.has(cc)) {
      ccToElevationGainSum.set(cc, 0);
      ccToNumEdges.set(cc, 0);
    }
    for (const nbr of graph[node]) {
      if (nbr > node) {
        ccToNumEdges.set(cc, ccToNumEdges.get(cc) + 1);
        ccToElevationGainSum.set(
          cc,
          ccToElevationGainSum.get(cc) + Math.abs(heights[node] - heights[nbr]),
        );
      }
    }
  }

  let res = 0;
  for (const cc of ccToNumEdges.keys()) {
    if (ccToNumEdges.get(cc) > 0) {
      res = Math.max(res, ccToElevationGainSum.get(cc) / ccToNumEdges.get(cc));
    }
  }
  return res;
}


function runTests() {
  const tests = [
    // Example
    [
      [
        [1, 3],
        [0, 2],
        [1, 3],
        [0, 2],
      ],
      [4, 1, 3, 2],
      2,
    ],
    // Single node component
    [[[]], [5], 0],
    // Two disconnected components
    [[[1], [0], [3], [2]], [1.5, 5.5, 0.0, 5.0], 5],
    // All nodes same height
    [
      [
        [1, 2],
        [0, 2],
        [0, 1],
      ],
      [3, 3, 3],
      0,
    ],
    // Line graph
    [[[1], [0, 2], [1]], [1, 5, 2], 3.5],
    // Complete graph
    [
      [
        [1, 2, 3],
        [0, 2, 3],
        [0, 1, 3],
        [0, 1, 2],
      ],
      [1, 4, 7, 10],
      (3 + 6 + 9 + 3 + 6 + 3) / 6,
    ],
  ];
  for (const [graph, heights, want] of tests) {
    const got = maxHilliness(graph, heights);
    if (!(Math.abs(got - want) < 0.0001)) {
      throw new Error(
        `\nmaxHilliness(${JSON.stringify(graph)}, ${JSON.stringify(heights)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
