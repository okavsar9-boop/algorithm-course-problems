// 12.5 - Reachability Queries
// Run: node 12_05_reachability_queries.js

function connectedComponentQueries(graph, queries) {
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

  const res = [];
  for (const [node1, node2] of queries) {
    res.push(nodeToCc.get(node1) === nodeToCc.get(node2));
  }
  return res;
}


function runTests() {
  const tests = [
    // Cycle graph with 6 nodes
    [
      [
        [1, 5],
        [0, 2, 4],
        [1, 3, 5],
        [2, 4],
        [1, 3, 5],
        [0, 2, 4],
      ],
      [
        [0, 4],
        [0, 3],
      ],
      [true, true],
    ],
    // Example
    [
      [[1], [0, 2, 5, 4], [1, 4, 5], [], [5, 2, 1], [1, 2, 4]],
      [
        [0, 4],
        [0, 3],
      ],
      [true, false],
    ],
    // Simple line graph
    [
      [[1], [0, 2], [1]],
      [
        [0, 2],
        [0, 1],
      ],
      [true, true],
    ],
    // Disconnected components
    [
      [[1], [0], [3], [2]],
      [
        [0, 1],
        [0, 2],
        [2, 3],
      ],
      [true, false, true],
    ],
    // Complete graph
    [
      [
        [1, 2],
        [0, 2],
        [0, 1],
      ],
      [
        [0, 1],
        [1, 2],
        [0, 2],
      ],
      [true, true, true],
    ],
    // Single node
    [[[]], [[0, 0]], [true]],
    // Empty queries
    [[[1], [0]], [], []],
  ];
  for (const [graph, queries, want] of tests) {
    const got = connectedComponentQueries(graph, queries);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nconnectedComponentQueries(${JSON.stringify(graph)}, ${JSON.stringify(queries)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
