// 8.6 - Balanced Partition
// Run: node 08_06_balanced_partition.js

function maxBalancedPartition(s) {
  let height = 0;
  let res = 0;
  for (const c of s) {
    if (c === "(") {
      height += 1;
    } else {
      height -= 1;
      if (height === 0) {
        res += 1;
      }
    }
  }
  return res;
}


function runTests() {
  const tests = [
    ["((()))(()())()(()(()))", 4],
    ["()()()", 3],
    ["(((())))", 1],
    ["", 0],
    ["()", 1],
  ];
  for (const [s, want] of tests) {
    const got = maxBalancedPartition(s);
    if (got !== want) {
      throw new Error(
        `\nmaxBalancedPartition(${s}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
