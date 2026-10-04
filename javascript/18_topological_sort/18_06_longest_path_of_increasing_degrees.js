// 18.6 - Longest Path of Increasing Degrees
// Run: node 18_06_longest_path_of_increasing_degrees.js

function dagNeighbors(graph, node) {
  return graph[node].filter((nbr) => graph[nbr].length > graph[node].length);
}

function topologicalSort(graph) {
  // Initialization
  const V = graph.length;
  const inDegrees = new Array(V).fill(0);
  for (let node = 0; node < V; node++) {
    for (const nbr of dagNeighbors(graph, node)) {
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
    for (const nbr of dagNeighbors(graph, node)) {
      inDegrees[nbr]--;
      if (inDegrees[nbr] === 0) {
        degreeZero.push(nbr);
      }
    }
  }

  return topoOrder;
}

function longestPathOfIncreasingDegrees(V, edges) {
  const graph = Array.from({ length: V }, () => []);
  for (const [u, v] of edges) {
    graph[u].push(v);
    graph[v].push(u);
  }

  const topoOrder = topologicalSort(graph);

  const lengths = new Array(V).fill(0);
  for (const node of topoOrder) {
    for (const nbr of dagNeighbors(graph, node)) {
      if (lengths[node] + 1 > lengths[nbr]) {
        lengths[nbr] = lengths[node] + 1;
      }
    }
  }

  return Math.max(...lengths);
}


function runTests() {
  const tests = [
    // Example from the book.
    {
      V: 8,
      edges: [
        [0, 1],
        [1, 2],
        [2, 3],
        [0, 2],
        [0, 4],
        [2, 6],
        [3, 7],
        [2, 7],
        [4, 5],
        [5, 6],
        [6, 7],
      ],
      want: 2,
    },
    // Edge case: Single node.
    {
      V: 1,
      edges: [],
      want: 0,
    },
    // Can do 0 -> 1 or 2 -> 1.
    {
      V: 3,
      edges: [
        [0, 1],
        [1, 2],
      ],
      want: 1,
    },
    // Cycle graph.
    {
      V: 4,
      edges: [
        [0, 1],
        [1, 2],
        [2, 3],
        [3, 0],
      ],
      want: 0,
    },
    // Star graph.
    {
      V: 4,
      edges: [
        [0, 1],
        [0, 2],
        [0, 3],
      ],
      want: 1,
    },
    // Can do 3 -> 2 -> 1 -> 0.
    {
      V: 10,
      edges: [
        [0, 1],
        [1, 2],
        [2, 3],
        [2, 4],
        [3, 5],
        [3, 6],
        [3, 7],
      ],
      want: 3,
    },
  ];

  for (const { V, edges, want } of tests) {
    const got = longestPathOfIncreasingDegrees(V, edges);
    if (got !== want) {
      throw new Error(
        `\nlongestPathOfIncreasingDegrees(${V}, ${JSON.stringify(edges)}): ` +
          `got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
