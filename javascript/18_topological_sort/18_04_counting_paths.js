// 18.4 - Counting Paths
// Run: node 18_04_counting_paths.js

function topologicalSort(graph) {
  // Initialization
  const V = graph.length;
  const inDegrees = new Array(V).fill(0);
  for (let node = 0; node < V; node++) {
    for (const nbr of graph[node]) {
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
    for (const nbr of graph[node]) {
      inDegrees[nbr]--;
      if (inDegrees[nbr] === 0) {
        degreeZero.push(nbr);
      }
    }
  }
  return topoOrder;
}

function pathCount(graph, start) {
  const topoOrder = topologicalSort(graph);

  const counts = new Array(graph.length).fill(0);
  counts[start] = 1;
  for (const node of topoOrder) {
    for (const nbr of graph[node]) {
      counts[nbr] += counts[node];
    }
  }
  return counts;
}


function runTests() {
  const tests = [
    // Example from the book
    [[[1], [], [1], [4], [1, 2, 5], [2]], 4, [0, 3, 2, 0, 1, 1]],
    // Edge case: Single node graph
    [[[]], 0, [1]],
    [[[1], []], 0, [1, 1]],
    [[[1], [2], []], 1, [0, 1, 1]],
    [[[1, 2], [3], [3], []], 0, [1, 1, 1, 2]],
  ];

  for (const [graph, start, want] of tests) {
    const got = pathCount(graph, start);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\npathCount(${JSON.stringify(graph)}, ${start}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
