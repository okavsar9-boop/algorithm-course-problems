// 12.8 - Highest Average Elevation Gain
// Run: node 12_08_highest_average_elevation_gain.js

function buildAdjList(V, edges) {
  const graph = Array(V)
    .fill()
    .map(() => []);
  for (const [node1, node2, gain] of edges) {
    graph[node1].push([node2, gain]);
    graph[node2].push([node1, gain]);
  }
  return graph;
}

function labelComponents(graph) {

  function dfs(node, ccId) {
    ccIds.set(node, ccId);
    for (const [nbr, _] of graph[node]) {
      if (!ccIds.has(nbr)) {
        dfs(nbr, ccId);
      }
    }
  }

  const ccIds = new Map();
  let ccId = 0;
  for (let node = 0; node < graph.length; node++) {
    if (!ccIds.has(node)) {
      dfs(node, ccId);
      ccId++;
    }
  }
  return [ccIds, ccId];
}

function highestAverageElevationGain(V, edges) {
  if (!edges.length) {
    return 0;
  }

  // Build adjacency list with weights
  const graph = buildAdjList(V, edges);

  // Label nodes with component IDs
  const [ccIds, numComponents] = labelComponents(graph);

  // Calculate average gain for each component
  const ccGains = Array(numComponents).fill(0);
  const ccEdges = Array(numComponents).fill(0);
  for (let node = 0; node < V; node++) {
    for (const [nbr, gain] of graph[node]) {
      if (node < nbr) {
        // Only count each edge once.
        const ccId = ccIds.get(node);
        ccGains[ccId] += gain;
        ccEdges[ccId]++;
      }
    }
  }

  // Find max average gain
  let maxAvg = 0;
  for (let ccId = 0; ccId < numComponents; ccId++) {
    if (ccEdges[ccId] > 0) {
      const avg = ccGains[ccId] / ccEdges[ccId];
      maxAvg = Math.max(maxAvg, avg);
    }
  }

  return maxAvg;
}


function runTests() {
  const tests = [
    // Example from the book
    [
      4, // V
      [
        [0, 1, 3],
        [1, 2, 2],
        [2, 3, 1],
        [3, 0, 2],
      ], // edges
      2, // want
    ],
    // Single edge
    [2, [[0, 1, 5]], 5],
    // No edges
    [3, [], 0],
    // Multiple components
    [
      6,
      [
        [0, 1, 1],
        [1, 2, 2], // Component 1: avg 1.5
        [3, 4, 3],
        [4, 5, 5],
      ], // Component 2: avg 4.0
      4,
    ],
    // Single node component
    [
      3,
      [[0, 1, 2]], // Node 2 is isolated
      2,
    ],
  ];

  for (const [V, edges, want] of tests) {
    const got = highestAverageElevationGain(V, edges);
    if (!(Math.abs(got - want) < 1e-6)) {
      throw new Error(
        `\nhighestAverageElevationGain(${V}, ${JSON.stringify(edges)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
