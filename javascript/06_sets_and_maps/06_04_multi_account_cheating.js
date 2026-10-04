// 6.4 - Multi-Account Cheating
// Run: node 06_04_multi_account_cheating.js

function multiAccountCheating(users) {
  const uniqueLists = new Set();
  for (const [_, ips] of users) {
    const immutableList = JSON.stringify(ips.sort());
    if (uniqueLists.has(immutableList)) {
      return true;
    }
    uniqueLists.add(immutableList);
  }
  return false;
}


function runTests() {
  const tests = [
    // Example
    [
      [
        ["mike", ["203.0.3.10", "208.51.0.5", "52.0.2.5"]],
        ["bob", ["111.0.0.10", "222.0.0.5", "222.0.0.8"]],
        ["bob2", ["222.0.0.5", "222.0.0.8", "111.0.0.10"]],
      ],
      true,
    ],
    // Additional test cases
    [[], false],
    [[["alice", ["1.1.1.1"]]], false],
    [
      [
        ["alice", ["1.1.1.1", "2.2.2.2"]],
        ["bob", ["2.2.2.2", "1.1.1.1"]],
      ],
      true,
    ],
    [
      [
        ["alice", ["1.1.1.1"]],
        ["bob", ["2.2.2.2"]],
      ],
      false,
    ],
  ];

  for (const [users, want] of tests) {
    const got = multiAccountCheating(users);
    if (got !== want) {
      throw new Error(
        `\nmultiAccountCheating(${JSON.stringify(users)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
