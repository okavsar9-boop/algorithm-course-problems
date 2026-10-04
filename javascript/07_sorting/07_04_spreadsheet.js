// 7.4 - Spreadsheet
// Run: node 07_04_spreadsheet.js

class Spreadsheet {
  constructor(rows, cols) {
    this.rows = rows;
    this.cols = cols;

    this.sheet = [];
    for (let i = 0; i < rows; i++) {
      this.sheet.push(new Array(cols).fill(0));
    }
  }

  new(rows, cols) {
    this.rows = rows;
    this.cols = cols;
    this.sheet = [];
    for (let i = 0; i < rows; i++) {
      this.sheet.push(new Array(cols).fill(0));
    }
  }

  set(row, col, value) {
    this.sheet[row][col] = value;
  }

  get(row, col) {
    return this.sheet[row][col];
  }

  sortRowsByColumn(col) {
    this.sheet.sort((a, b) => a[col] - b[col]);
  }

  sortColumnsByRow(row) {
    const columnsWithValues = [];
    for (let col = 0; col < this.cols; col++) {
      columnsWithValues.push([col, this.sheet[row][col]]);
    }

    columnsWithValues.sort((a, b) => a[1] - b[1]);
    const sortedSheet = [];
    for (let r = 0; r < this.rows; r++) {
      const newRow = [];
      for (const [col, _] of columnsWithValues) {
        newRow.push(this.sheet[r][col]);
      }
      sortedSheet.push(newRow);
    }
    this.sheet = sortedSheet;
  }
}


function runTests() {
  const tests = [
    // Example from the book
    [
      (s) => [
        s.new(3, 3),
        s.set(0, 0, 5),
        s.set(0, 1, 3),
        s.set(0, 2, 8),
        s.set(1, 0, 6),
        s.set(2, 1, 1),
        s.sortColumnsByRow(0),
        s.sortRowsByColumn(1),
      ],
      [
        [1, 0, 0],
        [3, 5, 8],
        [0, 6, 0],
      ],
    ],
    // Edge case - 1x1 spreadsheet
    [(s) => [s.new(1, 1), s.set(0, 0, 42)], [[42]]],
    // Edge case - sort empty rows
    [(s) => [s.new(3, 2), s.sortRowsByColumn(0)], [
      [0, 0],
      [0, 0],
      [0, 0],
    ]],
  ];

  for (const [operations, want] of tests) {
    const s = new Spreadsheet(0, 0);
    operations(s);
    for (let r = 0; r < want.length; r++) {
      for (let c = 0; c < want[0].length; c++) {
        const got = s.get(r, c);
        const expect = want[r][c];
        if (got !== expect) {
          throw new Error(
            `\nget(${r}, ${c}): got: ${got}, want: ${expect}\n`
          );
        }
      }
    }
  }
}

runTests();
