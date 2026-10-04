// 12.3 - Tree Check
// Run: node 12_03_tree_check.js

function isTree(graph) {
  // Start from node 0 (the starting node doesn't matter).
  const predecessors = new Map([[0, null]]);
  let foundCycle = false;

  function visit(node) {
    if (foundCycle) {
      return;
    }
    for (const nbr of graph[node]) {
      if (!predecessors.has(nbr)) {
        predecessors.set(nbr, node);
        visit(nbr);
      } else if (nbr !== predecessors.get(node)) {
        foundCycle = true;
      }
    }
  }

  visit(0);
  const connected = predecessors.size === graph.length;
  return !foundCycle && connected;
}


function isTree(graph) {
  // Start from node 0 (the starting node doesn't matter).
  const predecessors = new Map([[0, null]]);
  let foundCycle = false;

  function visit(node) {
    if (foundCycle) {
      return;
    }
    for (const nbr of graph[node]) {
      if (!predecessors.has(nbr)) {
        predecessors.set(nbr, node);
        visit(nbr);
      } else if (nbr !== predecessors.get(node)) {
        foundCycle = true;
      }
    }
  }

  visit(0);
  const connected = predecessors.size === graph.length;
  return !foundCycle && connected;
}

function runTests() {
  const tests = [
    // Example 1 from the book
    [[[2], [2, 5], [0, 1, 3, 4], [2], [2], [1]], true],
    // Example 2 from the book
    [[[2], [5], [0, 3], [2], [], [1]], false],
    // Example 3 from the book
    [[[1], [0, 2, 5], [1, 3, 4], [2], [2, 5], [1, 4]], false],
    // Single node
    [[[]], true],
    // Two nodes connected
    [[[1], [0]], true],
    // Two nodes disconnected
    [[[], []], false],
    // Line graph (valid tree)
    [[[1], [0, 2], [1, 3], [2]], true],
    // Cycle
    [[[1, 3], [2, 0], [3, 1], [0, 2]], false],
    // Complete graph K4 (not a tree)
    [[[1, 2, 3], [0, 2, 3], [0, 1, 3], [0, 1, 2]], false],
    // Star graph
    [[[1, 2, 3, 4], [0], [0], [0], [0]], true],
  ];
  for (const [graph, want] of tests) {
    const got = isTree(graph);
    if (got !== want) {
      throw new Error(
        `\nisTree(${JSON.stringify(graph)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
