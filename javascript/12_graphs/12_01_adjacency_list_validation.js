// 12.1 - Adjacency List Validation
// Run: node 12_01_adjacency_list_validation.js

function validate(graph) {
  const V = graph.length;
  for (let node = 0; node < V; node++) {
    const seen = new Set();
    for (const nbr of graph[node]) {
      if (nbr < 0 || nbr >= V) {
        return false; // Invalid node index.
      }
      if (nbr === node) {
        return false; // Self-loop.
      }
      if (seen.has(nbr)) {
        return false; // Parallel edge.
      }
      seen.add(nbr);
    }
  }

  const edges = new Set();
  for (let node1 = 0; node1 < V; node1++) {
    for (const node2 of graph[node1]) {
      const edge = [Math.min(node1, node2), Math.max(node1, node2)].toString();
      if (edges.has(edge)) {
        edges.delete(edge);
      } else {
        edges.add(edge);
      }
    }
  }
  return edges.size === 0;
}


function runTests() {
  const tests = [
    // Valid cases
    [[[1], [0]], true], // Simple valid graph
    [
      [
        [1, 2],
        [0, 2],
        [0, 1],
      ],
      true,
    ], // Triangle graph
    [[], true], // Empty graph
    [[[]], true], // Single isolated node

    // Invalid node index cases
    [[[2], [0]], false], // Node index too large
    [[[-1], []], false], // Negative node index

    // Self-loop cases
    [[[0], []], false], // Self loop
    [[[1], [1]], false], // Self loop in second node

    // Parallel edge cases
    [
      [
        [1, 1],
        [0, 0],
      ],
      false,
    ], // Same edge twice from first node
    [[[1], [0, 2, 0], [1]], false], // Same edge twice from second node

    // Unmatched edge cases
    [[[1], []], false], // Edge only in one direction
    [[[1, 2], [0], []], false], // Some edges missing their pairs
    [[[1], [2], [0]], false], // Cycle with unmatched edges
  ];
  for (const [graph, want] of tests) {
    const got = validate(graph);
    if (got !== want) {
      throw new Error(
        `\nvalidate(${JSON.stringify(graph)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
