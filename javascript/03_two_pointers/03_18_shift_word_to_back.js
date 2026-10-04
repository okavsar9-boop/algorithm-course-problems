// 3.18 - Shift Word to Back
// Run: node 03_18_shift_word_to_back.js

function moveWord(arr, word) {
  let seeker = 0,
  writer = 0;
  let i = 0;
  while (seeker < arr.length) {
    if (i < word.length && arr[seeker] === word[i]) {
      seeker++;
      i++;
    } else {
      arr[writer] = arr[seeker];
      seeker++;
      writer++;
    }
  }
  for (const c of word) {
    arr[writer] = c;
    writer++;
  }
}


function runTests() {
  const tests = [
  // Example 1 from the book
  [Array.from("seekerandwriter"), "edit", Array.from("sekeranwreredit")],
  // Example 2 from the book
  [Array.from("bacb"), "ab", Array.from("bcab")],
  // Example 3 from the book
  [Array.from("babc"), "b", Array.from("abcb")],
  // Additional test cases
  [[], "", []],
  [Array.from("a"), "a", Array.from("a")],
  [Array.from("abc"), "", Array.from("abc")],
  [Array.from("hello"), "ho", Array.from("ellho")],
  [Array.from("abcabc"), "abc", Array.from("abcabc")],
  ];
  for (const [arr, word, want] of tests) {
    const arrCopy = [...arr]; // Make a copy since moveWord modifies in place
    moveWord(arrCopy, word);
    if (JSON.stringify(arrCopy) !== JSON.stringify(want)) {
      throw new Error(
      `\nmoveWord(${JSON.stringify(arr)}, ${word}): got: ${JSON.stringify(arrCopy)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
