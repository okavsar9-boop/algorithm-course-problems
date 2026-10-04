// 15.11 - Escape with All Clues
// Run: node 15_11_escape_with_all_clues.js

function escapeWithAllClues(room) {

  function getClues() {
    const clues = [];
    for (let i = 0; i < room.length; i++) {
      for (let j = 0; j < room[0].length; j++) {
        if (room[i][j] === 2) {
          clues.push([i, j]);
        }
      }
    }
    return clues;
  }

  function validMoves(pos) {
    const moves = [];
    const dirs = [
      [0, 1],
      [1, 0],
      [0, -1],
      [-1, 0],
    ];
    for (const [dirI, dirJ] of dirs) {
      const nbrI = pos[0] + dirI;
      const nbrJ = pos[1] + dirJ;
      if (
        nbrI >= 0 &&
        nbrI < room.length &&
        nbrJ >= 0 &&
        nbrJ < room[0].length &&
        room[nbrI][nbrJ] !== 1
      ) {
        moves.push([nbrI, nbrJ]);
      }
    }
    return moves;
  }

  const allClues = new Set(
    getClues().map((clue) => JSON.stringify(clue))
  );
  let shortestPath = [];

  // State of the current partial solution
  let currentPath = [[0, 0]];
  const currentVisited = new Set();
  currentVisited.add(JSON.stringify([0, 0]));
  const currentCluesLeft = new Set(allClues);
  if (currentCluesLeft.has(JSON.stringify([0, 0]))) {
    currentCluesLeft.delete(JSON.stringify([0, 0]));
  }

  function visit() {
    // Prune if path is already longer than shortest found
    if (shortestPath.length && currentPath.length >= shortestPath.length) {
      return;
    }

    // Found valid solution
    if (currentCluesLeft.size === 0) {
      if (!shortestPath.length || currentPath.length < shortestPath.length) {
        shortestPath = currentPath.map((p) => [...p]);
      }
      return;
    }

    // Try each valid move
    const cur = currentPath[currentPath.length - 1];
    for (const nextPos of validMoves(cur)) {
      const nextPosStr = JSON.stringify(nextPos);
      if (currentVisited.has(nextPosStr)) {
        continue; // Don't revisit cells
      }

      // Modify state
      currentPath.push(nextPos);
      currentVisited.add(nextPosStr);
      if (allClues.has(nextPosStr)) {
        currentCluesLeft.delete(nextPosStr);
      }

      visit();

      // Undo state changes
      currentPath.pop();
      currentVisited.delete(nextPosStr);
      if (allClues.has(nextPosStr)) {
        currentCluesLeft.add(nextPosStr);
      }
    }
  }

  visit();
  return shortestPath;
}


function runTests() {
  const tests = [
    // Example 1 from the book
    [
      [
        [0, 1, 0],
        [0, 2, 0],
        [0, 0, 2],
      ],
      [
        [0, 0],
        [1, 0],
        [1, 1],
        [1, 2],
        [2, 2],
      ],
    ],

    // Example 2 from the book
    [
      [
        [0, 0, 0],
        [2, 1, 2],
      ],
      [],
    ],

    // Example 3 from the book
    [
      [
        [0, 0, 1, 2],
        [0, 1, 0, 0],
      ],
      [],
    ],

    // single clue
    [
      [[0, 2]],
      [
        [0, 0],
        [0, 1],
      ],
    ],

    // no valid path
    [
      [
        [0, 1],
        [1, 2],
      ],
      [],
    ],

    // multiple clues in a line
    [
      [[0, 2, 2]],
      [
        [0, 0],
        [0, 1],
        [0, 2],
      ],
    ],

    // 2x2: cannot reach both clues without revisiting the first square
    [
      [
        [0, 2],
        [2, 1],
      ],
      [],
    ],

    // Shortest path doesn't start by going to the closest clue
    [
      [
        [0, 0, 0, 0, 0],
        [1, 1, 0, 1, 1],
        [2, 0, 0, 0, 0],
        [0, 0, 2, 0, 2],
        [0, 0, 0, 0, 0],
      ],
      [
        [0, 0],
        [0, 1],
        [0, 2],
        [1, 2],
        [2, 2],
        [2, 1],
        [2, 0],
        [3, 0],
        [3, 1],
        [3, 2],
        [3, 3],
        [3, 4],
      ],
    ],
  ];

  for (const [room, want] of tests) {
    const got = escapeWithAllClues(room);
    const isEqual =
      got.length === want.length &&
      got.every(
        (val, idx) =>
          val.length === want[idx].length &&
          val.every((v, i) => v === want[idx][i]),
      );
    if (
      !isEqual &&
      !(got.length > 0 && want.length > 0 && got.length === want.length)
    ) {
      throw new Error(
        `\nescapeWithAllClues(${JSON.stringify(room)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
  return true;
}

runTests();
