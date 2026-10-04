// 6.1 - Account Sharing Detection
// Run: node 06_01_account_sharing_detection.js

function accountSharing(connections) {
  const seen = new Set();
  for (const [ip, username] of connections) {
    if (seen.has(username)) {
      return ip;
    }
    seen.add(username);
  }
  return "";
}


function runTests() {
  const tests = [
    // Example 1
    [
      [
        ["203.0.113.10", "mike"],
        ["298.51.100.25", "bob"],
        ["292.0.2.5", "mike"],
        ["203.0.113.15", "bob2"],
      ],
      "203.0.113.10",
    ],
    // Example 2
    [
      [
        ["111.0.0.0", "mike"],
        ["111.0.0.1", "mike"],
        ["111.0.0.2", "bob"],
        ["111.0.0.3", "bob"],
      ],
      "111.0.0.0",
    ],
    // Example 3
    [
      [
        ["111.0.0.0", "mike"],
        ["111.0.0.1", "mike2"],
        ["111.0.0.2", "mike3"],
        ["111.0.0.3", "mike4"],
      ],
      "",
    ],
    // Edge case - empty list
    [[], ""],
    // Edge case - single connection
    [[["1.1.1.1", "alice"]], ""],
  ];

  for (const [connections, want] of tests) {
    const got = accountSharing(connections);
    if (
      !(
        got === want ||
        (want !== "" &&
          connections.some(
            ([ip, username]) =>
              ip === got &&
              connections.filter(([_, u]) => u === username).length > 1,
          ))
      )
    ) {
      throw new Error(
        `\naccountSharing(${JSON.stringify(connections)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
