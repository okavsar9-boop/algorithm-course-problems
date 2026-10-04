// 6.3 - Most Frequent Octet
// Run: javac P06_03_MostFrequentOctet.java && java P06_03_MostFrequentOctet

import java.util.*;
import java.util.function.*;

class MostFrequentOctet {
  public String solve(List<String> ips) {
    Map<String, Integer> counts = new HashMap<>();
    for (String ip : ips) {
      String firstOctet = ip.split("\\.")[0];
      counts.put(firstOctet, counts.getOrDefault(firstOctet, 0) + 1);
    }

    String maxOctet = null;
    int maxCount = 0;
    for (Map.Entry<String, Integer> entry : counts.entrySet()) {
      if (entry.getValue() > maxCount) {
        maxCount = entry.getValue();
        maxOctet = entry.getKey();
      }
    }
    return maxOctet;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example
        { new String[] { "203.0.113.10", "208.51.100.5", "202.0.2.5",
            "203.0.113.5" }, "203" },
        // Additional test cases
        { new String[] {}, null },
        { new String[] { "192.168.1.1" }, "192" },
        { new String[] { "10.0.0.1", "10.0.0.2", "192.168.1.1" }, "10" },
        { new String[] { "172.16.0.1", "172.16.0.2", "172.17.0.1",
            "172.16.0.3" }, "172" },
    };

    MostFrequentOctet solution = new MostFrequentOctet();
    for (Object[] test : tests) {
      String[] ipsArray = (String[]) test[0];
      List<String> ips = Arrays.stream(ipsArray).collect(Collectors.toList());
      String want = (String) test[1];
      String got = solution.solve(ips);
      if (!Objects.equals(got, want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %s, want: %s\n",
            ips, got, want));
      }
    }
  }
}

public class P06_03_MostFrequentOctet {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
