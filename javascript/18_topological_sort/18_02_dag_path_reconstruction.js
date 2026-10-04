// 18.2 - DAG Path Reconstruction
// Run: node 18_02_dag_path_reconstruction.js

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

function shortestPath(graph, start, goal) {
  const topoOrder = topologicalSort(graph);

  const distances = new Map();
  const predecessors = new Map();
  distances.set(start, 0);

  for (const node of topoOrder) {
    if (!distances.has(node)) continue;
    for (const [nbr, weight] of graph[node]) {
      if (
        !distances.has(nbr) ||
        distances.get(node) + weight < distances.get(nbr)
      ) {
        distances.set(nbr, distances.get(node) + weight);
        predecessors.set(nbr, node);
      }
    }
  }

  if (!distances.has(goal)) {
    return [];
  }

  const path = [goal];
  while (path[path.length - 1] !== start) {
    path.push(predecessors.get(path[path.length - 1]));
  }
  path.reverse();
  return path;
}


function runTests() {
  const tests = [
    // Example from the book
    {
      graph: [
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
      start: 4,
      goal: 1,
      want: [4, 5, 2, 1],
    },
    // Edge case: Single node graph
    {
      graph: [[]],
      start: 0,
      goal: 0,
      want: [0],
    },
    // Edge case: Disconnected graph
    {
      graph: [[[1, 5]], [], [[3, 2]], []],
      start: 0,
      goal: 3,
      want: [],
    },
    // Edge case: Graph with negative weights
    {
      graph: [[[1, -1]], [[2, -2]], []],
      start: 0,
      goal: 2,
      want: [0, 1, 2],
    },
    // Edge case: Start node with no outgoing edges
    {
      graph: [[[1, 2]], [[2, 3]], []],
      start: 2,
      goal: 0,
      want: [],
    },
  ];

  for (const { graph, start, goal, want } of tests) {
    const got = shortestPath(graph, start, goal);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nshortestPath(${JSON.stringify(graph)}, ${start}, ${goal}): ` +
          `got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
