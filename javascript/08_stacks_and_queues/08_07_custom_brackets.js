// 8.7 - Custom Brackets
// Run: node 08_07_custom_brackets.js

function customBrackets(s, brackets) {
  const openToClose = {};
  const closeSet = new Set();
  for (const pair of brackets) {
    openToClose[pair[0]] = pair[1];
    closeSet.add(pair[1]);
  }

  const stack = [];
  for (const c of s) {
    if (openToClose[c]) {
      stack.push(openToClose[c]);
    } else if (closeSet.has(c)) {
      if (!stack.length || stack[stack.length - 1] !== c) {
        return false;
      }
      stack.pop();
    }
  }
  return stack.length === 0;
}


function runTests() {
  const tests = [
    // Example 1 from book
    ["((a+b)*[c-d]-{e/f})", ["()", "[]", "{}"], true],
    // Example 2 from book
    ["()[}", ["()", "[]", "{}"], false],
    // Example 3 from book
    ["([)]", ["()", "[]", "{}"], false],
    // Example 4 from book
    ["<div> hello :) </div>", ["<>", "()"], false],
    // Example 5 from book
    [")))(()((", [")("], true],
    // Empty string
    ["", ["()"], true],
    // Single character
    ["(", ["()"], false],
    // Multiple bracket types
    ["<<>>()[]{}", ["<>", "()", "[]", "{}"], true],
    // Nested brackets
    ["[{()}]", ["()", "[]", "{}"], true],
    // Unmatched opening bracket
    ["(()", ["()"], false],
    // Unmatched closing bracket
    ["())", ["()"], false],
    // Wrong order of closing
    ["({)}", ["()", "{}"], false],
    // Non-bracket characters mixed in
    ["a(b)c[d]e", ["()", "[]"], true],
    // Multiple identical bracket pairs
    ["<<>>", ["<>"], true],
  ];
  for (const [s, brackets, want] of tests) {
    const got = customBrackets(s, brackets);
    if (got !== want) {
      throw new Error(
        `\ncustomBrackets(${s}, ${JSON.stringify(brackets)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
