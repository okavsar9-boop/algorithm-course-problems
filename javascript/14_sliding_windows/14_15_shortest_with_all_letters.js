// 14.15 - Shortest With All Letters
// Run: node 14_15_shortest_with_all_letters.js

function shortestWithAllLetters(s1, s2) {
  let l = 0,
    r = 0;
  const missing = new Map();
  for (const c of s2) {
    missing.set(c, (missing.get(c) || 0) + 1);
  }
  let distinctMissing = missing.size;
  let curMin = Infinity;

  while (true) {
    const mustGrow = distinctMissing > 0;
    if (mustGrow) {
      if (r === s1.length) {
        break;
      }
      if (missing.has(s1[r])) {
        missing.set(s1[r], missing.get(s1[r]) - 1);
        if (missing.get(s1[r]) === 0) {
          distinctMissing--;
        }
      }
      r++;
    } else {
      curMin = Math.min(curMin, r - l);
      if (missing.has(s1[l])) {
        missing.set(s1[l], missing.get(s1[l]) + 1);
        if (missing.get(s1[l]) === 1) {
          distinctMissing++;
        }
      }
      l++;
    }
  }
  return curMin !== Infinity ? curMin : -1;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    ["helloworld", "well", 5],
    // Example 2 from the book
    ["helloworld", "weelll", -1],
    // Edge case - s2 is single character
    ["hello", "l", 1],
    // s2 not in s1
    ["hello", "z", -1],
  ];
  for (const [s1, s2, want] of tests) {
    const got = shortestWithAllLetters(s1, s2);
    if (got !== want) {
      throw new Error(
        `\nshortestWithAllLetters(${s1}, ${s2}): got: ${got}, want ${want}\n`,
      );
    }
  }
}

runTests();
