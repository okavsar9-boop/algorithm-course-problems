// 6.1 - Account Sharing Detection
// Run: javac P06_01_AccountSharingDetection.java && java P06_01_AccountSharingDetection

import java.util.*;
import java.util.function.*;

class AccountSharing {
  public String solve(List<List<String>> connections) {
    Set<String> seen = new HashSet<>();
    for (List<String> conn : connections) {
      String username = conn.get(0);
      String ip = conn.get(1);
      if (seen.contains(username)) {
        return ip;
      }
      seen.add(username);
    }
    return "";
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1
        {
            new String[][] {
                { "203.0.113.10", "mike" },
                { "298.51.100.25", "bob" },
                { "292.0.2.5", "mike" },
                { "203.0.113.15", "bob2" }
            },
            "203.0.113.10"
        },
        // Example 2
        {
            new String[][] {
                { "111.0.0.0", "mike" },
                { "111.0.0.1", "mike" },
                { "111.0.0.2", "bob" },
                { "111.0.0.3", "bob" }
            },
            "111.0.0.0"
        },
        // Example 3
        {
            new String[][] {
                { "111.0.0.0", "mike" },
                { "111.0.0.1", "mike2" },
                { "111.0.0.2", "mike3" },
                { "111.0.0.3", "mike4" }
            },
            ""
        },
        // Edge case - empty list
        {
            new String[][] {},
            ""
        },
        // Edge case - single connection
        {
            new String[][] { { "1.1.1.1", "alice" } },
            ""
        }
    };

    AccountSharing solution = new AccountSharing();
    for (Object[] test : tests) {
      String[][] connectionsArray = (String[][]) test[0];
      List<List<String>> connections = Arrays.stream(connectionsArray)
          .map(Arrays::asList)
          .collect(Collectors.toList());
      String want = (String) test[1];
      String got = solution.solve(connections);

      // Check if got matches want directly
      if (Objects.equals(got, want)) {
        continue;
      }

      // If want is empty, got must also be empty
      if (want.isEmpty()) {
        if (!got.isEmpty()) {
          throw new RuntimeException(String.format(
              "\nsolve(%s): got: %s, want: %s\n",
              connections, got, want));
        }
        continue;
      }
    }
  }
}

public class P06_01_AccountSharingDetection {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
