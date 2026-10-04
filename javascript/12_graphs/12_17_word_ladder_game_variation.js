// 12.17 - Word Ladder Game Variation
// Run: node 12_17_word_ladder_game_variation.js

function canTransform(word1, word2) {
  if (Math.abs(word1.length - word2.length) !== 1) {
    return false;
  }

  // Make word1 the shorter word
  if (word1.length > word2.length) {
    [word1, word2] = [word2, word1];
  }

  // Try removing each letter from word2
  for (let i = 0; i < word2.length; i++) {
    if (word2.slice(0, i) + word2.slice(i + 1) === word1) {
      return true;
    }
  }
  return false;
}

function buildGraph(words, l1, l2) {
  const graph = {};

  // Initialize nodes
  for (const word of words) {
    if (word.length === l1 || word.length === l2) {
      graph[word] = [];
    }
  }

  // Add edges
  for (const word1 of Object.keys(graph)) {
    for (const word2 of Object.keys(graph)) {
      if (word1 !== word2 && canTransform(word1, word2)) {
        graph[word1].push(word2);
      }
    }
  }

  return graph;
}

function hasPath(graph, start, end, visited = null) {
  if (visited === null) {
    visited = new Set();
  }

  // Don't allow paths to the same word
  if (start === end && visited.size === 0) {
    return false;
  }

  if (start === end && visited.size > 0) {
    return true;
  }

  visited.add(start);
  for (const nbr of graph[start]) {
    if (!visited.has(nbr)) {
      if (hasPath(graph, nbr, end, visited)) {
        return true;
      }
    }
  }
  return false;
}

function wordLadderGame(word1, word2, words) {
  // Try path using words of length l and l+1
  const l = word1.length;
  const graph1 = buildGraph(words, l, l + 1);
  if (word2 in graph1 && hasPath(graph1, word1, word2)) {
    return true;
  }

  // Try path using words of length l and l-1
  const graph2 = buildGraph(words, l, l - 1);
  if (word2 in graph2 && hasPath(graph2, word1, word2)) {
    return true;
  }

  return false;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [
      "leap",
      "hop",
      [
        "fare",
        "hug",
        "car",
        "vibes",
        "once",
        "sop",
        "far",
        "ounce",
        "slap",
        "sap",
        "cart",
        "hung",
        "art",
        "shop",
        "fart",
        "lap",
        "soap",
        "are",
        "hop",
        "care",
        "leap",
        "bounce",
        "beyond",
        "cracking",
      ],
      true,
    ],
    // Example 2 from the book
    [
      "car",
      "cart",
      [
        "fare",
        "hug",
        "car",
        "vibes",
        "once",
        "sop",
        "far",
        "ounce",
        "slap",
        "sap",
        "cart",
        "hung",
        "art",
        "shop",
        "fart",
        "lap",
        "soap",
        "are",
        "hop",
        "care",
        "leap",
        "bounce",
        "beyond",
        "cracking",
      ],
      true,
    ],
    // Invalid - double removal
    ["bounce", "once", ["bounce", "ounce", "once"], false],
    // Invalid - reordered letters
    ["car", "race", ["car", "race"], false],
    // No path exists
    ["cat", "dog", ["cat", "cot", "dot", "dog"], false],
  ];

  for (const [word1, word2, words, want] of tests) {
    const got = wordLadderGame(word1, word2, words);
    if (got !== want) {
      throw new Error(
        `\nwordLadderGame(${word1}, ${word2}, ${JSON.stringify(words)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
