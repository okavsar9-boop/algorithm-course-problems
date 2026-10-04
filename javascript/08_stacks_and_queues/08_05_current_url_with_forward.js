// 8.5 - Current URL with Forward
// Run: node 08_05_current_url_with_forward.js

function currentUrlWithForward(actions) {
  const stack = [];
  const forwardStack = [];

  for (let [action, value] of actions) {
    if (action === "go") {
      stack.push(value);
      forwardStack.length = 0;
    } else if (action === "back") {
      while (stack.length > 1 && value > 0) {
        forwardStack.push(stack.pop());
        value -= 1;
      }
    } else {
      while (forwardStack.length && value > 0) {
        stack.push(forwardStack.pop());
        value -= 1;
      }
    }
  }
  return stack[stack.length - 1];
}

function currentUrlWithForwardEfficient(actions) {
  const urls = [];
  let current = -1;

  for (let [action, value] of actions) {
    if (action === "go") {
      current += 1;
      urls.length = current; // remove any urls after the current one
      urls.push(value);
    } else if (action === "back") {
      current = Math.max(0, current - value);
    } else {
      current = Math.min(urls.length - 1, current + value);
    }
  }

  return urls[current];
}


function runTests() {
  const tests = [
    [
      [
        ["go", "google.com"],
        ["go", "wikipedia.com"],
        ["back", 1],
        ["forward", 1],
        ["back", 3],
        ["go", "netflix.com"],
        ["forward", 3],
      ],
      "netflix.com",
    ],
    [
      [
        ["go", "example.com"],
        ["forward", 1],
      ],
      "example.com",
    ],
    [
      [
        ["go", "site1.com"],
        ["go", "site2.com"],
        ["back", 1],
        ["forward", 1],
        ["back", 1],
      ],
      "site1.com",
    ],
  ];

  for (const [actions, want] of tests) {
    const got = currentUrlWithForward(actions);
    if (got !== want) {
      throw new Error(
        `\ncurrentUrlWithForward(${JSON.stringify(actions)}): got: ${got}, want: ${want}\n`,
      );
    }
    const got2 = currentUrlWithForwardEfficient(actions);
    if (got2 !== want) {
      throw new Error(
        `\ncurrentUrlWithForwardEfficient(${JSON.stringify(actions)}): got: ${got2}, want: ${want}\n`,
      );
    }
  }
}

runTests();
