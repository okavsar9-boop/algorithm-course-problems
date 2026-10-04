// 6.10 - Action Log Anomalies
// Run: node 06_10_action_log_anomalies.js

function findAnomalies(log) {
  const opened = new Map(); // ticket -> agent who opened it
  const workingOn = new Map(); // agent -> ticket they are working on
  const seen = new Set(); // tickets that were opened or closed
  const anomalies = new Set();

  for (const [agent, action, ticket] of log) {
    // Check if agent is working on another ticket
    if (workingOn.has(agent) && workingOn.get(agent) !== ticket) {
      anomalies.add(workingOn.get(agent));
    }

    if (anomalies.has(ticket)) {
      continue;
    }

    if (action === "open") {
      if (seen.has(ticket)) {
        anomalies.add(ticket);
        continue;
      }

      opened.set(ticket, agent);
      workingOn.set(agent, ticket);
      seen.add(ticket);
    } else {
      // close
      if (!opened.has(ticket) || opened.get(ticket) !== agent) {
        anomalies.add(ticket);
        continue;
      }
      workingOn.delete(agent);
      opened.delete(ticket);
    }
  }

  // Any tickets still open are anomalous
  for (const ticket of opened.keys()) {
    anomalies.add(ticket);
  }
  return [...anomalies];
}


function runTests() {
  const tests = [
    // Example
    [
      [
        ["Dwight", "close", 2],
        ["Dwight", "open", 2],
        ["Drew", "open", 32],
        ["Drew", "close", 32],
        ["Drew", "open", 32],
        ["Drew", "close", 32],
        ["Susa", "open", 7],
        ["Jo", "close", 7],
        ["Susa", "open", 33],
        ["Jo", "open", 8],
        ["Jo", "open", 36],
        ["Jo", "close", 8],
        ["Susa", "close", 33],
      ],
      [2, 32, 7, 8, 36],
    ],
    // Additional test cases
    [[], []], // no tickets
    [
      [
        ["Alice", "open", 1],
        ["Alice", "close", 1],
      ],
      [],
    ], // Nothing anomalous
    [
      [
        ["Alice", "open", 1],
        ["Alice", "open", 1],
      ],
      [1],
    ], // Opened multiple times
    [
      [
        ["Alice", "open", 1],
        ["Alice", "close", 1],
        ["Alice", "open", 1],
      ],
      [1],
    ], // Opened after close
    [[["Alice", "open", 1]], [1]], // Not closed
    [
      [
        ["Alice", "open", 1],
        ["Susa", "open", 1],
      ],
      [1],
    ], // Different agent
    [[["Alice", "close", 1]], [1]], // Closed before opened
    [
      [
        ["Drew", "open", 32],
        ["Drew", "close", 2],
        ["Drew", "close", 32],
      ],
      [2, 32],
    ],
    [
      [
        ["Dwight", "close", 2],
        ["Dwight", "open", 2],
        ["Drew", "open", 32],
        ["Drew", "open", 2],
        ["Drew", "close", 32],
      ],
      [2, 32],
    ], // Multiple agents working on same ticket
  ];

  for (const [log, want] of tests) {
    const got = findAnomalies(log);
    // Sort both lists to compare them regardless of order
    got.sort((a, b) => a - b);
    want.sort((a, b) => a - b);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nfindAnomalies(${JSON.stringify(log)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
