// 8.4 - Current URL
// Run: node 08_04_current_url.js

function currentUrl(actions) {
  let stack = [];
  for (let i = 0; i < actions.length; i++) {
    let [action, value] = actions[i];
    if (action === "go") {
      stack.push(value);
    } else {
      while (stack.length > 1 && value > 0) {
        stack.pop();
        value -= 1;
      }
    }
  }
  return stack[stack.length - 1];
}


function runTests() {
  const tests = [
    // Test case 1
    [
      [
        ["go", "google.com"],
        ["go", "wikipedia.com"],
        ["go", "amazon.com"],
        ["back", 4],
        ["go", "youtube.com"],
        ["go", "netflix.com"],
        ["back", 1],
      ],
      "youtube.com",
    ],

    // Test case 2
    [
      [
        ["go", "example.com"],
        ["back", 1],
      ],
      "example.com",
    ],

    // Test case 3
    [
      [
        ["go", "site1.com"],
        ["go", "site2.com"],
        ["back", 1],
        ["back", 1],
      ],
      "site1.com",
    ],
  ];

  for (const [actions, want] of tests) {
    const got = currentUrl(actions);
    if (got !== want) {
      throw new Error(
        `\ncurrentUrl(${JSON.stringify(actions)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
