// 18.3 - DAG Longest Path
// Run: node 18_03_dag_longest_path.js

function topologicalSort(graph) {
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
    return [];
  }
  return topoOrder;
}

function longestPath(graph, start) {
  const topoOrder = topologicalSort(graph);

  const lengths = new Array(graph.length).fill(-Infinity);
  lengths[start] = 0;
  for (const node of topoOrder) {
    if (lengths[node] === -Infinity) continue;
    for (const [nbr, weight] of graph[node]) {
      if (lengths[node] + weight > lengths[nbr]) {
        lengths[nbr] = lengths[node] + weight;
      }
    }
  }

  return lengths;
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
      [-Infinity, 31, 21, -Infinity, 0, 14],
    ],
    // Edge case: Single node graph
    [[[]], 0, [0]],
    // Edge case: Disconnected graph
    [[[[1, 5]], [], [[3, 2]], []], 0, [0, 5, -Infinity, -Infinity]],
    // Edge case: Graph with negative weights
    [[[[1, -1]], [[2, -2]], []], 0, [0, -1, -3]],
    // Edge case: Start node with no outgoing edges
    [[[[1, 2]], [[2, 3]], []], 2, [-Infinity, -Infinity, 0]],
  ];

  for (const [graph, start, want] of tests) {
    const got = longestPath(graph, start);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nlongestPath(${JSON.stringify(graph)}, ${start}): ` +
          `got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
