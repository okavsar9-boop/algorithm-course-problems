# 6.10 - Action Log Anomalies
# Run: python3 06_10_action_log_anomalies.py

def find_anomalies(log):
  opened = {}  # ticket -> agent who opened it
  working_on = {}  # agent -> ticket they are working on
  seen = set()  # tickets that were opened or closed
  anomalies = set()

  for agent, action, ticket in log:
    # Check if agent is working on another ticket
    if agent in working_on and working_on[agent] != ticket:
      anomalies.add(working_on[agent])

    if ticket in anomalies:
      continue

    if action == "open":
      if ticket in seen:
        anomalies.add(ticket)
        continue

      opened[ticket] = agent
      working_on[agent] = ticket
      seen.add(ticket)
    else:  # close
      if ticket not in opened or opened[ticket] != agent:
        anomalies.add(ticket)
        continue
      del working_on[agent]
      del opened[ticket]

  # Any tickets still open are anomalous
  anomalies.update(opened.keys())
  return list(anomalies)


def run_tests():
  tests = [
      # Example 
      ([
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
      ], [2, 32, 7, 8, 36]),
      # Additional test cases
      ([], []),  # no tickets
      ([["Alice", "open", 1], ["Alice", "close", 1]], []),  # Nothing anomalous
      ([["Alice", "open", 1], ["Alice", "open", 1]], [1]),  # Opened multiple times
      ([["Alice", "open", 1], ["Alice", "close", 1], [
       "Alice", "open", 1]], [1]),  # Opened after close
      ([["Alice", "open", 1]], [1]),  # Not closed
      ([["Alice", "open", 1], ["Susa", "open", 1]], [1]),  # Different agent
      ([["Alice", "close", 1]], [1]),  # Closed before opened
      ([
          ["Drew", "open", 32],
          ["Drew", "close", 2],
          ["Drew", "close", 32],
      ], [2, 32]),
      ([
          ["Dwight", "close", 2],
          ["Dwight", "open", 2],
          ["Drew", "open", 32],
          ["Drew", "open", 2],
          ["Drew", "close", 32],
      ], [2, 32]),  # Multiple agents working on same ticket

  ]
  for log, want in tests:
    got = find_anomalies(log)
    # Sort both lists to compare them regardless of order
    got.sort()
    want.sort()
    assert got == want, f"\nfind_anomalies({log}): got: {got}, want: {want}\n"

run_tests()
