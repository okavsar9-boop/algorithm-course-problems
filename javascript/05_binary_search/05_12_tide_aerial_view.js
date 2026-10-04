// 5.12 - Tide Aerial View
// Run: node 05_12_tide_aerial_view.js

class TideAerialView {
  // Time: O(log n)
  // Space: O(1)
  getOnesInRow(row) {
    if (row[0] === 0) {
      return 0;
    }
    if (row[row.length - 1] === 1) {
      return row.length;
    }

    function isBefore(idx) {
      return row[idx] === 1;
    }

    let l = 0,
      r = row.length;
    while (r - l > 1) {
      const mid = Math.floor((l + r) / 2);
      if (isBefore(mid)) {
        l = mid;
      } else {
        r = mid;
      }
    }
    return r;
  }

  // Time: O(n log n)
  // Space: O(1)
  getOnesInPicture(picture) {
    let ones = 0;
    for (const row of picture) {
      ones += this.getOnesInRow(row);
    }
    return ones;
  }

  // Time: O((log k) * n log n)
  // Space: O(1)
  solve(pictures) {
    const isBefore = (picture) => {
      const water = this.getOnesInPicture(picture);
      const total = Math.pow(picture[0].length, 2);
      return water / total < 0.5;
    };

    if (!isBefore(pictures[0])) {
      return 0;
    }
    if (isBefore(pictures[pictures.length - 1])) {
      return pictures.length - 1;
    }

    let l = 0,
      r = pictures.length - 1;
    while (r - l > 1) {
      const mid = Math.floor((l + r) / 2);
      if (isBefore(pictures[mid])) {
        l = mid;
      } else {
        r = mid;
      }
    }

    // Return the closest one to the midpoint, or l in case of a tie
    const lWater = this.getOnesInPicture(pictures[l]);
    const rWater = this.getOnesInPicture(pictures[r]);
    const midPoint = Math.pow(pictures[0][0].length, 2) / 2;
    return Math.abs(lWater - midPoint) <= Math.abs(rWater - midPoint) ? l : r;
  }
}


function runTests() {
  const tests = [
    // Example from the book
    [
      [
        [
          [0, 0, 0],
          [0, 0, 0],
          [0, 0, 0],
        ],
        [
          [1, 0, 0],
          [0, 0, 0],
          [1, 0, 0],
        ],
        [
          [1, 1, 0],
          [0, 0, 0],
          [1, 0, 0],
        ],
        [
          [1, 1, 0],
          [1, 1, 1],
          [1, 0, 0],
        ],
        [
          [1, 1, 1],
          [1, 1, 1],
          [1, 1, 0],
        ],
      ],
      2,
    ],
    // 3 pictures with increasing water
    [
      [
        [
          [1, 0, 0],
          [1, 0, 0],
          [1, 0, 0],
        ],
        [
          [1, 1, 0],
          [1, 1, 0],
          [1, 0, 0],
        ],
        [
          [1, 1, 1],
          [1, 1, 1],
          [1, 0, 0],
        ],
      ],
      1,
    ],
    // 2 pictures
    [
      [
        [
          [1, 0],
          [0, 0],
        ],
        [
          [1, 1],
          [1, 0],
        ],
      ],
      0,
    ],
    // Incremental progression
    [
      [
        [
          [0, 0, 0],
          [0, 0, 0],
          [0, 0, 0],
        ],
        [
          [1, 0, 0],
          [0, 0, 0],
          [0, 0, 0],
        ],
        [
          [1, 0, 0],
          [1, 0, 0],
          [0, 0, 0],
        ],
        [
          [1, 1, 0],
          [1, 0, 0],
          [0, 0, 0],
        ],
        [
          [1, 1, 1],
          [1, 0, 0],
          [0, 0, 0],
        ],
        [
          [1, 1, 1],
          [1, 1, 0],
          [0, 0, 0],
        ],
        [
          [1, 1, 1],
          [1, 1, 1],
          [0, 0, 0],
        ],
        [
          [1, 1, 1],
          [1, 1, 1],
          [1, 0, 0],
        ],
        [
          [1, 1, 1],
          [1, 1, 1],
          [1, 1, 0],
        ],
        [
          [1, 1, 1],
          [1, 1, 1],
          [1, 1, 1],
        ],
      ],
      4,
    ],
    // Edge case - single picture
    [
      [
        [
          [1, 1],
          [0, 0],
        ],
      ],
      0,
    ],
    // Edge case - all water
    [
      [
        [
          [1, 1],
          [1, 1],
        ],
      ],
      0,
    ],
    // Edge case - all land
    [
      [
        [
          [0, 0],
          [0, 0],
        ],
      ],
      0,
    ],
  ];

  for (const [pictures, want] of tests) {
    const got = new TideAerialView().solve(pictures);
    if (got !== want) {
      throw new Error(
        `\ntideAerialView(${JSON.stringify(pictures)}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
