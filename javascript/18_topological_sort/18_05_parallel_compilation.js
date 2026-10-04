// 18.5 - Parallel Compilation
// Run: node 18_05_parallel_compilation.js

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

function compileTime(seconds, imports) {
  const V = seconds.length;
  const graph = Array(V)
    .fill()
    .map(() => []);
  for (let pkg = 0; pkg < V; pkg++) {
    for (const importedPkg of imports[pkg]) {
      graph[importedPkg].push(pkg);
    }
  }

  const topoOrder = topologicalSort(graph); // Recipe 1.
  const durations = new Map();
  for (const node of topoOrder) {
    if (!durations.has(node)) {
      durations.set(node, seconds[node]);
    }
    for (const nbr of graph[node]) {
      if (!durations.has(nbr)) {
        durations.set(nbr, 0);
      }
      durations.set(
        nbr,
        Math.max(durations.get(nbr), seconds[nbr] + durations.get(node)),
      );
    }
  }

  return Math.max(...durations.values());
}


function runTests() {
  const tests = [
    // Example from the book
    [[10, 20, 30], [[], [], [0, 1]], 50],
    // Example from the book
    [[10, 20, 30], [[], [], []], 30],
    // Single package
    [[10], [[]], 10],
    // Linear dependency
    [[10, 20, 30], [[1], [2], []], 60],
    // Complex dependencies
    [[5, 10, 15, 20], [[], [0], [1], [0, 2]], 50],
  ];

  for (const [seconds, imports, want] of tests) {
    const got = compileTime(seconds, imports);
    if (got !== want) {
      throw new Error(
        `\ncompileTime(${JSON.stringify(seconds)}, ${JSON.stringify(imports)}): ` +
          `got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
