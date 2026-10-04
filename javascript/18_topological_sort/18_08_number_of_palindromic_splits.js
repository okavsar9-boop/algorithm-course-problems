// 18.8 - Number of Palindromic Splits
// Run: node 18_08_number_of_palindromic_splits.js

function countPalindromicSplitsDp(s) {
  const n = s.length;
  if (n === 0) {
    return 0;
  }

  // Preprocessing: compute all palindromic substrings
  const palindromes = findPalindromes(s);
  const palindromeSet = new Set(palindromes.map(([l, r]) => `${l},${r}`));

  const memo = {};

  function countSplits(i) {
    // Base case: reached the end of string
    if (i === n) {
      return 1;
    }

    if (i in memo) {
      return memo[i];
    }

    // Try all possible palindromic substrings starting at i
    let total = 0;
    for (let j = i; j < n; j++) {
      if (palindromeSet.has(`${i},${j}`)) {
        total += countSplits(j + 1);
      }
    }

    memo[i] = total;
    return total;
  }

  return countSplits(0);
}

function findPalindromes(s) {
  const n = s.length;
  const palindromes = [];
  for (let i = 0; i < n; i++) {
    let l = i,
      r = i;
    while (l >= 0 && r < n && s[l] === s[r]) {
      palindromes.push([l, r]);
      l--;
      r++;
    }
    l = i;
    r = i + 1;
    while (l >= 0 && r < n && s[l] === s[r]) {
      palindromes.push([l, r]);
      l--;
      r++;
    }
  }
  return palindromes;
}

function topologicalSort(graph) {
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

function countPalindromicSplits(s) {
  const palindromes = findPalindromes(s);
  const n = s.length;
  if (n === 0) {
    return 0;
  }

  const graph = Array(n + 1)
    .fill()
    .map(() => []);
  for (const [l, r] of palindromes) {
    graph[l].push(r + 1);
  }

  const topoOrder = topologicalSort(graph);

  const counts = new Array(n + 1).fill(0);
  counts[0] = 1;
  for (const node of topoOrder) {
    for (const nbr of graph[node]) {
      counts[nbr] += counts[node];
    }
  }
  return counts[n];
}


function runTests() {
  const tests = [
    ["abbaab", 6],
    ["aabaa", 6],
    ["a", 1],
    ["aa", 2],
    ["aaa", 4],
    ["aaaa", 8],
    ["aaaaa", 16],
    ["abc", 1],
    ["aaba", 3],
    ["abcdedcba", 5],
    ["", 0],
  ];

  for (const [s, want] of tests) {
    const got = countPalindromicSplits(s);
    if (got !== want) {
      throw new Error(
        `\ncountPalindromicSplits("${s}"): got: ${got}, want: ${want}\n`,
      );
    }
    const gotDp = countPalindromicSplitsDp(s);
    if (gotDp !== want) {
      throw new Error(
        `\ncountPalindromicSplitsDp("${s}"): got: ${gotDp}, want: ${want}\n`,
      );
    }
  }
}

runTests();
