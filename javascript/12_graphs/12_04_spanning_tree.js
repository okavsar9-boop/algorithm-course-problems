// 12.4 - Spanning Tree
// Run: node 12_04_spanning_tree.js

function spanningTree(graph) {
  const visited = new Set();
  const predecessors = new Map(); // Maps node -> predecessor in DFS tree

  function dfs(node) {
    visited.add(node);
    for (const nbr of graph[node]) {
      if (!visited.has(nbr)) {
        predecessors.set(nbr, node);
        dfs(nbr);
      }
    }
  }

  // Start DFS from node 0
  dfs(0);

  // Convert predecessors map to list of edges
  const edges = [];
  for (const [node, pred] of predecessors) {
    edges.push([pred, node]);
  }

  return edges;
}


function runTests() {
  const tests = [
    // Example from the book
    [[1], [0, 2, 5], [1, 3, 4], [2], [2, 5], [1, 4]],
    // Single edge
    [[1], [0]],
    // Line graph
    [[1], [0, 2], [1]],
    // Star graph
    [[1, 2, 3], [0], [0], [0]],
    // Complete graph
    [
      [1, 2],
      [0, 2],
      [0, 1],
    ],
    // Single node graph
    [[]],
  ];

  for (const graph of tests) {
    const got = spanningTree(graph);
    // Since there can be multiple valid spanning trees,
    // we check that:
    // 1. We have V-1 edges
    // 2. Each edge connects valid nodes
    // 3. The edges form a tree (no cycles)
    const V = graph.length;
    if (got.length !== V - 1) {
      throw new Error(
        `\nspanningTree(${JSON.stringify(graph)}): got wrong number of edges: ${JSON.stringify(got)}`,
      );
    }

    // Check edges are valid
    for (const [u, v] of got) {
      if (!(0 <= u && u < V && 0 <= v && v < V)) {
        throw new Error(
          `\nspanningTree(${JSON.stringify(graph)}): invalid node in edge ${JSON.stringify([u, v])}`,
        );
      }
      if (!(graph[u].includes(v) && graph[v].includes(u))) {
        throw new Error(
          `\nspanningTree(${JSON.stringify(graph)}): invalid edge ${JSON.stringify([u, v])}`,
        );
      }
    }

    // Check no cycles by counting reachable nodes
    const adj = Array(V)
      .fill()
      .map(() => []);
    for (const [u, v] of got) {
      adj[u].push(v);
      adj[v].push(u);
    }

    const visited = new Set();

    function dfsCheck(node, parent) {
      visited.add(node);
      for (const nbr of adj[node]) {
        if (nbr !== parent) {
          if (visited.has(nbr)) {
            return false;
          }
          if (!dfsCheck(nbr, node)) {
            return false;
          }
        }
      }
      return true;
    }

    if (!(dfsCheck(0, -1) && visited.size === V)) {
      throw new Error(
        `\nspanningTree(${JSON.stringify(graph)}): edges do not form a tree: ${JSON.stringify(got)}`,
      );
    }
  }
}

runTests();
