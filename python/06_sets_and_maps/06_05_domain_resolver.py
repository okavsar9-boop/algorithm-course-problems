# 6.5 - Domain Resolver
# Run: python3 06_05_domain_resolver.py

class DomainResolver:
  def __init__(self):
    self.ip_to_domains = dict()
    self.domain_to_subdomains = dict()

  def register_domain(self, ip, domain):
    if ip not in self.ip_to_domains:
      self.ip_to_domains[ip] = set()
    self.ip_to_domains[ip].add(domain)

  def register_subdomain(self, domain, subdomain):
    if domain not in self.domain_to_subdomains:
      self.domain_to_subdomains[domain] = set()
    self.domain_to_subdomains[domain].add(subdomain)

  def has_subdomain(self, ip, domain, subdomain):
    if ip not in self.ip_to_domains:
      return False
    if domain not in self.ip_to_domains[ip]:
      return False
    if domain not in self.domain_to_subdomains:
      return False
    return subdomain in self.domain_to_subdomains[domain]


def run_tests():
  tests = [
      # Example 
      (
          [
              ("register_domain", "192.168.1.1", "example.com"),
              ("register_domain", "192.168.1.1", "example.org"),
              ("register_domain", "192.168.1.2", "domain.com"),
              ("register_subdomain", "example.com", "a"),
              ("register_subdomain", "example.com", "b"),
              ("has_subdomain", "192.168.1.1", "example.com", "a"),
              ("has_subdomain", "192.168.1.1", "example.com", "c"),
              ("has_subdomain", "127.0.0.1", "example.com", "a"),
              ("has_subdomain", "192.168.1.1", "example.org", "a"),
              ("has_subdomain", "192.168.1.2", "example.com", "a"),
          ],
          [None, None, None, None, None, True, False, False, False, False]
      ),
      # Additional test cases
      (
          [
              ("register_domain", "1.1.1.1", "test.com"),
              ("register_subdomain", "test.com", "www"),
              ("has_subdomain", "1.1.1.1", "test.com", "www"),
          ],
          [None, None, True]
      ),
      (
          [
              ("register_domain", "1.1.1.1", "site1.com"),
              ("register_domain", "2.2.2.2", "site2.com"),
              ("register_subdomain", "site1.com", "www"),
              ("register_subdomain", "site2.com", "www"),
              ("has_subdomain", "1.1.1.1", "site1.com", "www"),  # Should be True
              ("has_subdomain", "2.2.2.2", "site2.com", "www"),  # Should be True
              ("has_subdomain", "1.1.1.1", "site2.com", "www"),  # Should be False (wrong IP)
              ("has_subdomain", "2.2.2.2", "site1.com", "www"),  # Should be False (wrong IP)
          ],
          [None, None, None, None, True, True, False, False]
      ),
  ]

  for operations, wants in tests:
    resolver = DomainResolver()
    for i, op in enumerate(operations):
      if op[0] == "register_domain":
        got = resolver.register_domain(op[1], op[2])
      elif op[0] == "register_subdomain":
        got = resolver.register_subdomain(op[1], op[2])
      else:  # has_subdomain
        got = resolver.has_subdomain(op[1], op[2], op[3])
      want = wants[i]
      assert got == want, \
          f"\n{op[0]}({', '.join(repr(x) for x in op[1:])}): got: {              got}, want: {want}\n"

run_tests()
