// 6.10 - Action Log Anomalies
// Run: javac P06_10_ActionLogAnomalies.java && java P06_10_ActionLogAnomalies

import java.util.*;
import java.util.function.*;

record Action(String agent, String action, int ticket) {
}

class FindAnomalies {
  public List<Integer> solve(List<Action> log) {
    Map<String, String> opened = new HashMap<>(); // ticket -> agent who opened
    // it
    Map<String, String> workingOn = new HashMap<>(); // agent -> ticket they are
    // working on
    Set<String> seen = new HashSet<>(); // tickets that were opened or closed
    Set<Integer> anomalies = new HashSet<>();

    for (Action entry : log) {
      String agent = entry.agent();
      String action = entry.action();
      int ticket = entry.ticket();

      // Check if agent is working on another ticket
      if (workingOn.containsKey(agent)
      && !workingOn.get(agent).equals(String.valueOf(ticket))) {
        anomalies.add(Integer.parseInt(workingOn.get(agent)));
      }

      if (anomalies.contains(ticket)) {
        continue;
      }

      if (action.equals("open")) {
        if (seen.contains(String.valueOf(ticket))) {
          anomalies.add(ticket);
          continue;
        }

        opened.put(String.valueOf(ticket), agent);
        workingOn.put(agent, String.valueOf(ticket));
        seen.add(String.valueOf(ticket));
      } else { // close
        if (!opened.containsKey(String.valueOf(ticket)) ||
        !opened.get(String.valueOf(ticket)).equals(agent)) {
          anomalies.add(ticket);
          continue;
        }
        workingOn.remove(agent);
        opened.remove(String.valueOf(ticket));
      }
    }

    // Any tickets still open are anomalous
    for (String ticket : opened.keySet()) {
      anomalies.add(Integer.parseInt(ticket));
    }

    List<Integer> result = new ArrayList<>(anomalies);
    Collections.sort(result);
    return result;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example
        {
            new Action[] {
                new Action("Dwight", "close", 2),
                new Action("Dwight", "open", 2),
                new Action("Drew", "open", 32),
                new Action("Drew", "close", 32),
                new Action("Drew", "open", 32),
                new Action("Drew", "close", 32),
                new Action("Susa", "open", 7),
                new Action("Jo", "close", 7),
                new Action("Susa", "open", 33),
                new Action("Jo", "open", 8),
                new Action("Jo", "open", 36),
                new Action("Jo", "close", 8),
                new Action("Susa", "close", 33)
            },
            new int[] { 2, 32, 7, 8, 36 }
        },
        // Additional test cases
        {
            new Action[] {},
            new int[] {} // no tickets
        },
        {
            new Action[] {
                new Action("Alice", "open", 1),
                new Action("Alice", "close", 1)
            },
            new int[] {} // Nothing anomalous
        },
        {
            new Action[] {
                new Action("Alice", "open", 1),
                new Action("Alice", "open", 1)
            },
            new int[] { 1 } // Opened multiple times
        },
        {
            new Action[] {
                new Action("Alice", "open", 1),
                new Action("Alice", "close", 1),
                new Action("Alice", "open", 1)
            },
            new int[] { 1 } // Opened after close
        },
        {
            new Action[] {
                new Action("Alice", "open", 1)
            },
            new int[] { 1 } // Not closed
        },
        {
            new Action[] {
                new Action("Alice", "open", 1),
                new Action("Susa", "open", 1)
            },
            new int[] { 1 } // Different agent
        },
        {
            new Action[] {
                new Action("Alice", "close", 1)
            },
            new int[] { 1 } // Closed before opened
        },
        {
            new Action[] {
                new Action("Drew", "open", 32),
                new Action("Drew", "close", 2),
                new Action("Drew", "close", 32)
            },
            new int[] { 2, 32 }
        },
        {
            new Action[] {
                new Action("Dwight", "close", 2),
                new Action("Dwight", "open", 2),
                new Action("Drew", "open", 32),
                new Action("Drew", "open", 2),
                new Action("Drew", "close", 32)
            },
            new int[] { 2, 32 } // Multiple agents working on same ticket
        }
    };

    FindAnomalies solution = new FindAnomalies();
    for (Object[] test : tests) {
      Action[] logArray = (Action[]) test[0];
      List<Action> log = Arrays.asList(logArray);

      List<Integer> want = Arrays.stream((int[]) test[1])
          .boxed()
          .collect(Collectors.toList());

      List<Integer> got = solution.solve(log);

      // Sort both lists before comparison
      Collections.sort(got);
      Collections.sort(want);

      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            log, got, want));
      }
    }
  }
}

public class P06_10_ActionLogAnomalies {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
