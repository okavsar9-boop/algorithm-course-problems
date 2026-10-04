// 7.4 - Spreadsheet
// Run: javac P07_04_Spreadsheet.java && java P07_04_Spreadsheet

import java.util.*;
import java.util.function.*;

class Spreadsheet {
  private final List<List<Integer>> cells;
  private final int rows;
  private final int cols;

  public Spreadsheet(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.cells = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      List<Integer> row = new ArrayList<>();
      for (int j = 0; j < cols; j++) {
        row.add(0);
      }
      this.cells.add(row);
    }
  }

  public void set(int row, int col, int val) {
    this.cells.get(row).set(col, val);
  }

  public int get(int row, int col) {
    return this.cells.get(row).get(col);
  }

  public void sortRow(int row) {
    Collections.sort(this.cells.get(row));
  }

  public void sortCol(int col) {
    List<Integer> column = new ArrayList<>();
    for (int i = 0; i < this.rows; i++) {
      column.add(this.cells.get(i).get(col));
    }
    Collections.sort(column);
    for (int i = 0; i < this.rows; i++) {
      this.cells.get(i).set(col, column.get(i));
    }
  }

  public void sortRowsByColumn(int col) {
    List<List<Integer>> sortedRows = new ArrayList<>(this.cells);
    sortedRows
        .sort((row1, row2) -> Integer.compare(row1.get(col), row2.get(col)));
    for (int i = 0; i < this.rows; i++) {
      this.cells.set(i, sortedRows.get(i));
    }
  }

  public void sortColumnsByRow(int row) {
    // Create pairs of (column index, value) for the given row
    List<Map.Entry<Integer, Integer>> columnsWithValues = new ArrayList<>();
    for (int col = 0; col < this.cols; col++) {
      columnsWithValues.add(
          new AbstractMap.SimpleEntry<>(col, this.cells.get(row).get(col)));
    }

    // Sort columns based on their values in the given row
    columnsWithValues.sort(Map.Entry.comparingByValue());

    // Create new sorted sheet
    List<List<Integer>> sortedSheet = new ArrayList<>();
    for (int r = 0; r < this.rows; r++) {
      List<Integer> newRow = new ArrayList<>();
      for (Map.Entry<Integer, Integer> entry : columnsWithValues) {
        int col = entry.getKey();
        newRow.add(this.cells.get(r).get(col));
      }
      sortedSheet.add(newRow);
    }

    // Update the sheet
    for (int r = 0; r < this.rows; r++) {
      this.cells.set(r, sortedSheet.get(r));
    }
  }
}


class RunTests {
  public void runTests() {
    // Example from the book
    {
      Spreadsheet sheet = new Spreadsheet(3, 3);
      sheet.set(0, 0, 5);
      sheet.set(0, 1, 3);
      sheet.set(0, 2, 8);
      sheet.set(1, 0, 6);
      sheet.set(2, 1, 1);
      sheet.sortColumnsByRow(0);
      sheet.sortRowsByColumn(1);
      int[][] want = new int[][] {
          { 1, 0, 0 },
          { 3, 5, 8 },
          { 0, 6, 0 },
      };
      for (int r = 0; r < want.length; r++) {
        for (int c = 0; c < want[0].length; c++) {
          int got = sheet.get(r, c);
          int expect = want[r][c];
          if (got != expect) {
            throw new RuntimeException(String.format(
                "\nget(%d, %d): got: %d, want: %d\n", r, c, got, expect));
          }
        }
      }
    }

    // Edge case - 1x1 spreadsheet
    {
      Spreadsheet sheet = new Spreadsheet(1, 1);
      sheet.set(0, 0, 42);
      int[][] want = new int[][] {
          { 42 },
      };
      for (int r = 0; r < want.length; r++) {
        for (int c = 0; c < want[0].length; c++) {
          int got = sheet.get(r, c);
          int expect = want[r][c];
          if (got != expect) {
            throw new RuntimeException(String.format(
                "\nget(%d, %d): got: %d, want: %d\n", r, c, got, expect));
          }
        }
      }
    }

    // Edge case - sort empty rows
    {
      Spreadsheet sheet = new Spreadsheet(3, 2);
      sheet.sortRowsByColumn(0);
      int[][] want = new int[][] {
          { 0, 0 },
          { 0, 0 },
          { 0, 0 },
      };
      for (int r = 0; r < want.length; r++) {
        for (int c = 0; c < want[0].length; c++) {
          int got = sheet.get(r, c);
          int expect = want[r][c];
          if (got != expect) {
            throw new RuntimeException(String.format(
                "\nget(%d, %d): got: %d, want: %d\n", r, c, got, expect));
          }
        }
      }
    }
  }
}

public class P07_04_Spreadsheet {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
