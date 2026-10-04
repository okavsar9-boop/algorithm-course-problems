// 12.6 - Strongly Connected Graph
// Run: node 12_06_strongly_connected_graph.js

function visit(graph, visited, node) {
  if (visited.has(node)) {
    return;
  }
  visited.add(node);
  for (const nbr of graph[node]) {
    visit(graph, visited, nbr);
  }
}

function stronglyConnected(graph) {
  const V = graph.length;
  const visited = new Set();
  visit(graph, visited, 0);
  if (visited.size < V) {
    return false;
  }

  const reverseGraph = Array(V)
    .fill()
    .map(() => []);
  for (let node = 0; node < V; node++) {
    for (const nbr of graph[node]) {
      reverseGraph[nbr].push(node);
    }
  }

  const reverseVisited = new Set();
  visit(reverseGraph, reverseVisited, 0);
  return reverseVisited.size === V;
}


function runTests() {
  const tests = [
    // Example strongly connected
    [[[1], [2], [0]], true],
    // Example not strongly connected
    [[[1], [2], []], false],
    // Single node
    [[[]], true],
    // Two nodes, strongly connected
    [[[1], [0]], true],
    // Two nodes, not strongly connected
    [[[1], []], false],
    // Cycle of 4 nodes
    [[[1], [2], [3], [0]], true],
    // Almost cycle of 4 nodes, missing one edge
    [[[1], [2], [3], []], false],
    // Complete graph
    [
      [
        [1, 2],
        [0, 2],
        [0, 1],
      ],
      true,
    ],
  ];
  for (const [graph, want] of tests) {
    const got = stronglyConnected(graph);
    if (got !== want) {
      throw new Error(
        `\nstronglyConnected(${JSON.stringify(graph)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
