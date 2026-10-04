// 6.2 - Most Shared Account
// Run: node 06_02_most_shared_account.js

function mostSharedAccount(connections) {
  const userToCount = new Map();
  for (const [_, user] of connections) {
    userToCount.set(user, (userToCount.get(user) || 0) + 1);
  }
  let mostSharedUser = null;
  for (const [user, count] of userToCount) {
    if (!mostSharedUser || count > userToCount.get(mostSharedUser)) {
      mostSharedUser = user;
    }
  }
  return mostSharedUser;
}


function runTests() {
  const tests = [
    // Example
    [
      [
        ["203.0.113.10", "mike"],
        ["208.51.100.25", "bob"],
        ["202.0.2.5", "mike"],
        ["203.0.113.15", "bob2"],
      ],
      "mike",
    ],
    // Additional test cases
    [[], null],
    [[["1.1.1.1", "alice"]], "alice"],
    [
      [
        ["1.1.1.1", "alice"],
        ["1.1.1.2", "bob"],
        ["1.1.1.3", "alice"],
        ["1.1.1.4", "bob"],
      ],
      "alice",
    ],
  ];

  for (const [connections, want] of tests) {
    const got = mostSharedAccount(connections);
    if (
      !(
        got === want ||
        (want &&
          got &&
          connections.filter(([_, u]) => u === got).length ===
            connections.filter(([_, u]) => u === want).length)
      )
    ) {
      throw new Error(
        `\nmostSharedAccount(${JSON.stringify(connections)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
