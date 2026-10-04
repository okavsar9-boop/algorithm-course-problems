// 6.5 - Domain Resolver
// Run: javac P06_05_DomainResolver.java && java P06_05_DomainResolver

import java.util.*;
import java.util.function.*;

class DomainResolver {
  private Map<String, Set<String>> ipToDomains;
  private Map<String, Set<String>> domainToSubdomains;

  public DomainResolver() {
    this.ipToDomains = new HashMap<>();
    this.domainToSubdomains = new HashMap<>();
  }

  public Object registerDomain(String ip, String domain) {
    if (!ipToDomains.containsKey(ip)) {
      ipToDomains.put(ip, new HashSet<>());
    }
    ipToDomains.get(ip).add(domain);
    return null;
  }

  public Object registerSubdomain(String domain, String subdomain) {
    if (!domainToSubdomains.containsKey(domain)) {
      domainToSubdomains.put(domain, new HashSet<>());
    }
    domainToSubdomains.get(domain).add(subdomain);
    return null;
  }

  public boolean hasSubdomain(String ip, String domain, String subdomain) {
    if (!ipToDomains.containsKey(ip)) {
      return false;
    }
    if (!ipToDomains.get(ip).contains(domain)) {
      return false;
    }
    if (!domainToSubdomains.containsKey(domain)) {
      return false;
    }
    return domainToSubdomains.get(domain).contains(subdomain);
  }
}


class RunTests {
  public void runTests() {
    TestCase[] tests = {
        // Example 1 from the book
        new TestCase(
            new String[][] {
                { "register_domain", "192.168.1.1", "example.com" },
                { "register_domain", "192.168.1.1", "example.org" },
                { "register_domain", "192.168.1.2", "domain.com" },
                { "register_subdomain", "example.com", "a" },
                { "register_subdomain", "example.com", "b" },
                { "has_subdomain", "192.168.1.1", "example.com", "a" },
                { "has_subdomain", "192.168.1.1", "example.com", "c" },
                { "has_subdomain", "127.0.0.1", "example.com", "a" },
                { "has_subdomain", "192.168.1.1", "example.org", "a" },
                { "has_subdomain", "192.168.1.2", "example.com", "a" }
            },
            new Object[] { null, null, null, null, null, true, false, false,
                false, false }),
        // Example 2 from the book
        new TestCase(
            new String[][] {
                { "register_domain", "1.1.1.1", "test.com" },
                { "register_subdomain", "test.com", "www" },
                { "has_subdomain", "1.1.1.1", "test.com", "www" }
            },
            new Object[] { null, null, true }),
        // Test case that would catch the IP-domain relationship bug
        new TestCase(
            new String[][] {
                { "register_domain", "1.1.1.1", "site1.com" },
                { "register_domain", "2.2.2.2", "site2.com" },
                { "register_subdomain", "site1.com", "www" },
                { "register_subdomain", "site2.com", "www" },
                { "has_subdomain", "1.1.1.1", "site1.com", "www" }, // Should be
                                                                    // true
                { "has_subdomain", "2.2.2.2", "site2.com", "www" }, // Should be
                                                                    // true
                { "has_subdomain", "1.1.1.1", "site2.com", "www" }, // Should be
                                                                    // false
                                                                    // (wrong
                                                                    // IP)
                { "has_subdomain", "2.2.2.2", "site1.com", "www" } // Should be
                                                                   // false
                                                                   // (wrong IP)
            },
            new Object[] { null, null, null, null, true, true, false, false })
    };

    for (TestCase testCase : tests) {
      DomainResolver resolver = new DomainResolver();
      for (int i = 0; i < testCase.operations().length; i++) {
        String[] op = testCase.operations()[i];
        String operation = op[0];
        Object got;
        if (operation.equals("register_domain")) {
          got = resolver.registerDomain(op[1], op[2]);
        } else if (operation.equals("register_subdomain")) {
          got = resolver.registerSubdomain(op[1], op[2]);
        } else { // has_subdomain
          got = resolver.hasSubdomain(op[1], op[2], op[3]);
        }
        Object want = testCase.wants()[i];
        if (!Objects.equals(got, want)) {
          throw new RuntimeException(String.format(
              "\n%s(%s): got: %s, want: %s\n",
              operation, Arrays.toString(Arrays.copyOfRange(op, 1, op.length)),
              got, want));
        }
      }
    }
  }
}

public class P06_05_DomainResolver {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
