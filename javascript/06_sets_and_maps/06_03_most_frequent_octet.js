// 6.3 - Most Frequent Octet
// Run: node 06_03_most_frequent_octet.js

function mostFrequentOctet(ips) {
  const octetToCount = new Map();
  for (const ip of ips) {
    const firstOctet = ip.split(".")[0];
    octetToCount.set(firstOctet, (octetToCount.get(firstOctet) || 0) + 1);
  }

  let mostFrequent = null;
  for (const [octet, count] of octetToCount) {
    if (!mostFrequent || count > octetToCount.get(mostFrequent)) {
      mostFrequent = octet;
    }
  }
  return mostFrequent;
}


function runTests() {
  const tests = [
    // Example
    [["203.0.113.10", "208.51.100.5", "202.0.2.5", "203.0.113.5"], "203"],
    // Additional test cases
    [[], null],
    [["192.168.1.1"], "192"],
    [["10.0.0.1", "10.0.0.2", "192.168.1.1"], "10"],
    [["172.16.0.1", "172.16.0.2", "172.17.0.1", "172.16.0.3"], "172"],
  ];

  for (const [ips, want] of tests) {
    const got = mostFrequentOctet(ips);
    if (got !== want) {
      throw new Error(
        `\nmostFrequentOctet(${JSON.stringify(ips)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
