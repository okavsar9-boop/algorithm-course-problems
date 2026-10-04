// 6.5 - Domain Resolver
// Run: node 06_05_domain_resolver.js

class DomainResolver {
  constructor() {
    this.ipToDomains = new Map();
    this.domainToSubdomains = new Map();
  }

  registerDomain(ip, domain) {
    if (!this.ipToDomains.has(ip)) {
      this.ipToDomains.set(ip, new Set());
    }
    this.ipToDomains.get(ip).add(domain);
    return null;
  }

  registerSubdomain(domain, subdomain) {
    if (!this.domainToSubdomains.has(domain)) {
      this.domainToSubdomains.set(domain, new Set());
    }
    this.domainToSubdomains.get(domain).add(subdomain);
    return null;
  }

  hasSubdomain(ip, domain, subdomain) {
    if (!this.ipToDomains.has(ip)) {
      return false;
    }
    if (!this.ipToDomains.get(ip).has(domain)) {
      return false;
    }
    if (!this.domainToSubdomains.has(domain)) {
      return false;
    }
    return this.domainToSubdomains.get(domain).has(subdomain);
  }
}


function runTests() {
  const tests = [
    // Example
    [
      [
        ["register_domain", "192.168.1.1", "example.com"],
        ["register_domain", "192.168.1.1", "example.org"],
        ["register_domain", "192.168.1.2", "domain.com"],
        ["register_subdomain", "example.com", "a"],
        ["register_subdomain", "example.com", "b"],
        ["has_subdomain", "192.168.1.1", "example.com", "a"],
        ["has_subdomain", "192.168.1.1", "example.com", "c"],
        ["has_subdomain", "127.0.0.1", "example.com", "a"],
        ["has_subdomain", "192.168.1.1", "example.org", "a"],
        ["has_subdomain", "192.168.1.2", "example.com", "a"],
      ],
      [null, null, null, null, null, true, false, false, false, false],
    ],
    // Additional test cases
    [
      [
        ["register_domain", "1.1.1.1", "test.com"],
        ["register_subdomain", "test.com", "www"],
        ["has_subdomain", "1.1.1.1", "test.com", "www"],
      ],
      [null, null, true],
    ],
    [
      [
        ["register_domain", "1.1.1.1", "site1.com"],
        ["register_domain", "2.2.2.2", "site2.com"],
        ["register_subdomain", "site1.com", "www"],
        ["register_subdomain", "site2.com", "www"],
        ["has_subdomain", "1.1.1.1", "site1.com", "www"], // Should be true
        ["has_subdomain", "2.2.2.2", "site2.com", "www"], // Should be true
        ["has_subdomain", "1.1.1.1", "site2.com", "www"], // Should be false (wrong IP)
        ["has_subdomain", "2.2.2.2", "site1.com", "www"], // Should be false (wrong IP)
      ],
      [null, null, null, null, true, true, false, false],
    ],
  ];

  for (const [operations, wants] of tests) {
    const resolver = new DomainResolver();
    for (let i = 0; i < operations.length; i++) {
      const [op, ...args] = operations[i];
      let got;
      if (op === "register_domain") {
        got = resolver.registerDomain(...args);
      } else if (op === "register_subdomain") {
        got = resolver.registerSubdomain(...args);
      } else {
        // has_subdomain
        got = resolver.hasSubdomain(...args);
      }
      const want = wants[i];
      if (
        !(
          got === want ||
          (got === undefined && want === null) ||
          (got === null && want === undefined)
        )
      ) {
        throw new Error(
          `\n${op}(${args.map((x) => JSON.stringify(x)).join(", ")}): got: ${got}, want: ${want}\n`,
        );
      }
    }
  }
}

runTests();
