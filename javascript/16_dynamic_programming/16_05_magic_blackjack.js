// 16.5 - Magic Blackjack
// Run: node 16_05_magic_blackjack.js

function numWays() {
  const memo = new Map();

  function numWaysRec(i) {
    if (i > 21) {
      return 1;
    }
    if (i >= 16 && i <= 21) {
      return 0;
    }
    if (memo.has(i)) {
      return memo.get(i);
    }
    let res = 0;
    for (let card = 1; card <= 10; card++) {
      res += numWaysRec(i + card);
    }
    memo.set(i, res);
    return res;
  }

  return numWaysRec(0);
}


function runTests() {
  const TOTAL_POSSIBLE_BUSTS = 100081;
  const got = numWays();
  if (got !== TOTAL_POSSIBLE_BUSTS) {
    throw new Error(
      `\nnumWays(<no input>): got: ${got}, want: ${TOTAL_POSSIBLE_BUSTS}\n`,
    );
  }
}

runTests();
