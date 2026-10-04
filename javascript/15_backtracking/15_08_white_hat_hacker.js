// 15.8 - White Hat Hacker
// Run: node 15_08_white_hat_hacker.js

function findPassword(checkPassword, maxLength) {

  function visit(password) {
    if (password.length > maxLength) {
      return null;
    }
    if (checkPassword(password)) {
      return password;
    }
    for (const char of "abcdefghijklmnopqrstuvwxyz") {
      if (!password.includes(char)) {
        // Only add if not already present
        const result = visit(password + char);
        if (result) {
          return result;
        }
      }
    }
    return null;
  }

  return visit("");
}


function runTests() {

  function checkPasswordFactory(correctPassword) {
    return (s) => s === correctPassword;
  }

  const tests = [
    "a",
    "bc",
    "def",
    "ghij",
    // Try higher numbers if you'd like to see how fast this stops working
    // but you'll need to edit the max_length variable in the find_password
    // function to make it longer. 8 characters may take a long time to run.
    // "klmno",
    // "pqrstu",
    // "vwxyzab"
  ];

  for (const correctPassword of tests) {
    const checkPassword = checkPasswordFactory(correctPassword);
    const got = findPassword(checkPassword, 4);
    if (got !== correctPassword) {
      throw new Error(
        `\nfindPassword(${correctPassword}): got: ${got}, want: ${correctPassword}\n`,
      );
    }
  }
}

runTests();
