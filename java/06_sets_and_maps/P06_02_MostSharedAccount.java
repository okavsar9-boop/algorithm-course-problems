// 6.2 - Most Shared Account
// Run: javac P06_02_MostSharedAccount.java && java P06_02_MostSharedAccount

import java.util.*;
import java.util.function.*;

class MostSharedAccount {
  public String solve(List<List<String>> connections) {
    Map<String, Integer> userToCount = new HashMap<>();
    for (List<String> conn : connections) {
      String ip = conn.get(0);
      String user = conn.get(1);
      userToCount.put(user, userToCount.getOrDefault(user, 0) + 1);
    }

    String mostSharedUser = null;
    for (Map.Entry<String, Integer> entry : userToCount.entrySet()) {
      String user = entry.getKey();
      int count = entry.getValue();
      if (mostSharedUser == null || count > userToCount.get(mostSharedUser)) {
        mostSharedUser = user;
      }
    }
    return mostSharedUser;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example
        {
            new String[][] {
                { "203.0.113.10", "mike" },
                { "208.51.100.25", "bob" },
                { "202.0.2.5", "mike" },
                { "203.0.113.15", "bob2" }
            },
            "mike"
        },
        // Additional test cases
        {
            new String[][] {},
            null
        },
        {
            new String[][] {
                { "1.1.1.1", "alice" }
            },
            "alice"
        },
        {
            new String[][] {
                { "1.1.1.1", "alice" },
                { "1.1.1.2", "bob" },
                { "1.1.1.3", "alice" },
                { "1.1.1.4", "bob" }
            },
            "alice"
        }
    };

    MostSharedAccount solution = new MostSharedAccount();
    for (Object[] test : tests) {
      String[][] connectionsArray = (String[][]) test[0];
      List<List<String>> connections = Arrays.stream(connectionsArray)
          .map(Arrays::asList)
          .collect(Collectors.toList());
      String want = (String) test[1];
      String got = solution.solve(connections);
      if (!Objects.equals(got, want)) {
        // Check if got is an acceptable alternative (same count as want)
        if (want != null && got != null) {
          int gotCount = 0;
          int wantCount = 0;
          for (List<String> conn : connections) {
            if (conn.get(1).equals(got))
              gotCount++;
            if (conn.get(1).equals(want))
              wantCount++;
          }
          if (gotCount == wantCount)
            continue;
        }
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            connections, got, want));
      }
    }
  }
}

public class P06_02_MostSharedAccount {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
