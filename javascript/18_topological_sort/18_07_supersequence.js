// 18.7 - Supersequence
// Run: node 18_07_supersequence.js

function hasCycle(graph) {
  // Use topological sort to return whether there is a cycle.

  // Initialization
  const inDegree = {};
  for (const node in graph) {
    inDegree[node] = 0;
  }
  for (const node in graph) {
    for (const nbr of graph[node]) {
      inDegree[nbr] = (inDegree[nbr] || 0) + 1;
    }
  }
  const degreeZero = [];
  for (const node in inDegree) {
    if (inDegree[node] === 0) {
      degreeZero.push(node);
    }
  }

  // Main 'peel-off' loop
  const topoOrder = [];
  while (degreeZero.length > 0) {
    const node = degreeZero.pop();
    topoOrder.push(node);
    for (const nbr of graph[node]) {
      inDegree[nbr]--;
      if (inDegree[nbr] === 0) {
        degreeZero.push(nbr);
      }
    }
  }

  return topoOrder.length !== Object.keys(graph).length;
}

function canFormSupersequence(arr) {
  // Build the graph
  const graph = {};
  for (const word of arr) {
    for (const c of word) {
      if (!(c in graph)) {
        graph[c] = new Set();
      }
    }
  }
  for (const word of arr) {
    for (let i = 0; i < word.length - 1; i++) {
      graph[word[i]].add(word[i + 1]);
    }
  }

  // Check for cycles using topological sort
  return !hasCycle(graph);
}


function runTests() {
  const tests = [
    [["abc", "bde", "df", "cfe"], true],
    // Cycle present
    [["ab", "ba"], false],
    // Edge case: Single letter
    [["a"], true],
    // Edge case: Empty array
    [[], true],
    // Multiple words with no dependencies
    [["a", "b", "c"], true],
    // Long chain
    [["ab", "bc", "cd", "de", "ef", "fg"], true],
    // Cycle
    [["abc", "bcd", "cda"], false],
    // Same letter multiple times
    [["aba", "bab"], false],
  ];

  for (const [arr, want] of tests) {
    const got = canFormSupersequence(arr);
    if (got !== want) {
      throw new Error(
        `\ncanFormSupersequence(${JSON.stringify(arr)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
