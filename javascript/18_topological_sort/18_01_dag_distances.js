// 18.1 - DAG Distances
// Run: node 18_01_dag_distances.js

function topologicalSort(graph) {
  // Initialization
  const V = graph.length;
  const inDegrees = new Array(V).fill(0);
  for (let node = 0; node < V; node++) {
    for (const [nbr, _] of graph[node]) {
      inDegrees[nbr]++;
    }
  }
  const degreeZero = [];
  for (let node = 0; node < V; node++) {
    if (inDegrees[node] === 0) {
      degreeZero.push(node);
    }
  }

  // Main 'peel-off' loop
  const topoOrder = [];
  while (degreeZero.length > 0) {
    const node = degreeZero.pop();
    topoOrder.push(node);
    for (const [nbr, _] of graph[node]) {
      inDegrees[nbr]--;
      if (inDegrees[nbr] === 0) {
        degreeZero.push(nbr);
      }
    }
  }

  if (topoOrder.length < V) {
    return []; // There is a cycle; some nodes couldn't be peeled off
  }
  return topoOrder;
}

function distance(graph, start) {
  const topoOrder = topologicalSort(graph);

  const distances = new Map();
  distances.set(start, 0);
  for (const node of topoOrder) {
    if (!distances.has(node)) continue;
    for (const [nbr, weight] of graph[node]) {
      if (
        !distances.has(nbr) ||
        distances.get(node) + weight < distances.get(nbr)
      ) {
        distances.set(nbr, distances.get(node) + weight);
      }
    }
  }

  const res = [];
  for (let i = 0; i < graph.length; i++) {
    if (distances.has(i)) {
      res.push(distances.get(i));
    } else {
      res.push(Infinity);
    }
  }
  return res;
}


function runTests() {
  const tests = [
    // Example from the book
    [
      [
        [[1, 10]],
        [],
        [[1, 10]],
        [[4, 12]],
        [
          [1, 11],
          [2, 21],
          [5, 14],
        ],
        [[2, -30]],
      ],
      4,
      [Infinity, -6, -16, Infinity, 0, 14],
    ],
    // Edge case: Single node graph
    [[[]], 0, [0]],
    // Edge case: Disconnected graph
    [[[[1, 5]], [], [[3, 2]], []], 0, [0, 5, Infinity, Infinity]],
    // Edge case: Graph with negative weights
    [[[[1, -1]], [[2, -2]], []], 0, [0, -1, -3]],
    // Edge case: Start node with no outgoing edges
    [[[[1, 2]], [[2, 3]], []], 2, [Infinity, Infinity, 0]],
  ];

  for (const [graph, start, want] of tests) {
    const got = distance(graph, start);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\ndistance(${JSON.stringify(graph)}, ${start}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
