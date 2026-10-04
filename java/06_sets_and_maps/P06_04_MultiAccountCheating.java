// 6.4 - Multi-Account Cheating
// Run: javac P06_04_MultiAccountCheating.java && java P06_04_MultiAccountCheating

import java.util.*;
import java.util.function.*;

class MultiAccountCheating {
  public boolean solve(List<List<String>> users) {
    Map<String, Set<String>> userToIps = new HashMap<>();
    for (List<String> user : users) {
      String username = user.get(0);
      String ip = user.get(1);
      userToIps.computeIfAbsent(username, k -> new HashSet<>()).add(ip);
    }

    List<Set<String>> ipSets = new ArrayList<>(userToIps.values());
    for (int i = 0; i < ipSets.size(); i++) {
      for (int j = i + 1; j < ipSets.size(); j++) {
        if (ipSets.get(i).equals(ipSets.get(j))) {
          return true;
        }
      }
    }
    return false;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1
        {
            new String[][] {
                { "alice", "192.168.1.1" },
                { "alice", "192.168.1.2" },
                { "bob", "192.168.1.2" },
                { "bob", "192.168.1.1" }
            },
            true
        },
        // Example 2
        {
            new String[][] {
                { "alice", "192.168.1.1" },
                { "bob", "192.168.1.2" }
            },
            false
        },
        // Additional test cases
        {
            new String[][] {
                { "alice", "1.1.1.1" },
                { "alice", "2.2.2.2" },
                { "bob", "2.2.2.2" },
                { "bob", "1.1.1.1" },
                { "charlie", "3.3.3.3" }
            },
            true
        },
        {
            new String[][] {},
            false
        }
    };

    MultiAccountCheating solution = new MultiAccountCheating();
    for (Object[] test : tests) {
      String[][] usersArray = (String[][]) test[0];
      List<List<String>> users = Arrays.stream(usersArray)
          .map(Arrays::asList)
          .collect(Collectors.toList());
      boolean want = (boolean) test[1];
      boolean got = solution.solve(users);
      if (got != want) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %b, want: %b\n",
            users, got, want));
      }
    }
  }
}

public class P06_04_MultiAccountCheating {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
