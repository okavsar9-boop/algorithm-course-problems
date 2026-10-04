// 6.11 - Largest Set Intersection
// Run: node 06_11_largest_set_intersection.js

function largestSetIntersectionFrequencyMap(sets) {
  if (sets.length === 1) {
    return 0;
  }

  // Create frequency map from integers to number of sets they appear in
  const freq = new Map();
  for (const s of sets) {
    for (const x of s) {
      freq.set(x, (freq.get(x) || 0) + 1);
    }
  }

  // For each set, count elements that appear k-1 times
  const k = sets.length;
  let bestIndex = 0;
  let minCount = Infinity;
  for (let i = 0; i < sets.length; i++) {
    const count = sets[i].filter((x) => freq.get(x) === k - 1).length;
    if (count < minCount) {
      minCount = count;
      bestIndex = i;
    }
  }

  return bestIndex;
}

function largestSetIntersectionPrefixSum(sets) {
  const n = sets.length;
  if (n === 1) {
    return 0;
  }

  const hashSets = sets.map((s) => new Set(s));

  // Compute prefix intersections
  const prefixIntersections = new Array(n);
  prefixIntersections[0] = hashSets[0];
  for (let i = 1; i < n; i++) {
    prefixIntersections[i] = new Set(
      [...prefixIntersections[i - 1]].filter((x) => hashSets[i].has(x)),
    );
  }

  // Compute suffix intersections
  const suffixIntersections = new Array(n);
  suffixIntersections[n - 1] = hashSets[n - 1];
  for (let i = n - 2; i >= 0; i--) {
    suffixIntersections[i] = new Set(
      [...suffixIntersections[i + 1]].filter((x) => hashSets[i].has(x)),
    );
  }

  // Find the best index to exclude
  let bestIndex = 0;
  let maxSize = 0;

  for (let i = 0; i < n; i++) {
    // Compute intersection excluding sets[i]
    let intersection;
    if (i === 0) {
      intersection = suffixIntersections[1];
    } else if (i === n - 1) {
      intersection = prefixIntersections[n - 2];
    } else {
      intersection = new Set(
        [...prefixIntersections[i - 1]].filter((x) =>
          suffixIntersections[i + 1].has(x),
        ),
      );
    }

    if (intersection.size > maxSize) {
      maxSize = intersection.size;
      bestIndex = i;
    }
  }

  return bestIndex;
}


function runTests() {
  const tests = [
    // Example 1 
    [[[1, 2, 3], [3, 2, 1], [1, 4, 5], [1, 2]], 2],
    // Example 2 
    [[[1, 2], [3, 4], [5, 6]], 0],
    // Example 3 
    [[[1, 2, 3], [4, 5]], 1],
    // Example 4 
    [[[1, 2, 3]], 0],
    // Additional test cases
    [[[1], [1]], 0],
    [[[1, 2], [2, 3], [1, 3]], 0],
  ];

  for (const [sets, want] of tests) {
    const gotFreq = largestSetIntersectionFrequencyMap(sets);
    const gotPrefix = largestSetIntersectionPrefixSum(sets);
    if (gotFreq !== want) {
      throw new Error(
        `\nlargestSetIntersectionFrequencyMap(${JSON.stringify(sets)}): got: ${gotFreq}, want: ${want}\n`,
      );
    }
    if (gotPrefix !== want) {
      throw new Error(
        `\nlargestSetIntersectionPrefixSum(${JSON.stringify(sets)}): got: ${gotPrefix}, want: ${want}\n`,
      );
    }
  }
}

runTests();
