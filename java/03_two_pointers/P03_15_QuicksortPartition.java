// 3.15 - Quicksort Partition
// Run: javac P03_15_QuicksortPartition.java && java P03_15_QuicksortPartition

import java.util.*;
import java.util.function.*;

class Partition {
  public void solve(int[] arr, int pivot) {
    // First pass: partition into <= pivot and > pivot
    int l = 0, r = arr.length - 1;
    while (l < r) {
      if (arr[l] <= pivot) {
        l++;
      } else if (arr[r] > pivot) {
        r--;
      } else {
        swap(arr, l, r);
        l++;
        r--;
      }
    }

    // Find the boundary between <= pivot and > pivot
    int boundary = 0;
    while (boundary < arr.length && arr[boundary] <= pivot) {
      boundary++;
    }

    // Second pass: partition the <= pivot section into < pivot and = pivot
    l = 0;
    r = boundary - 1;
    while (l < r) {
      if (arr[l] < pivot) {
        l++;
      } else if (arr[r] == pivot) {
        r--;
      } else {
        swap(arr, l, r);
        l++;
        r--;
      }
    }

  }

  private void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
}


class RunTests {
  private boolean isValidPartition(int[] arr, int pivot) {
    // Find boundaries between sections
    int first = 0;
    while (first < arr.length && arr[first] < pivot) {
      first++;
    }
    int second = first;
    while (second < arr.length && arr[second] == pivot) {
      second++;
    }

    // Check that all elements are in their correct sections
    // Section 1: < pivot
    for (int i = 0; i < first; i++) {
      if (arr[i] >= pivot) {
        return false;
      }
    }
    // Section 2: = pivot
    for (int i = first; i < second; i++) {
      if (arr[i] != pivot) {
        return false;
      }
    }
    // Section 3: > pivot
    for (int i = second; i < arr.length; i++) {
      if (arr[i] <= pivot) {
        return false;
      }
    }
    return true;

  }

  public void runTests() {
    Object[][] tests = {
      // Example 1 from the book - tests three-way partition
      {new int[] {1, 7, 2, 3, 3, 5, 3}, 3},

      // Example 2 from the book - tests when pivot isn't present
      {new int[] {1, 7, 2, 3, 3, 5, 3}, 4},

      // Test with all elements equal to pivot
      {new int[] {3, 3, 3, 3}, 3},

      // Test with no elements equal to pivot
      {new int[] {1, 2, 4, 5}, 3},

      // Test with elements only less than pivot
      {new int[] {1, 2}, 3},

      // Test with elements only greater than pivot
      {new int[] {4, 5}, 3},

      // Edge cases
      {new int[] {}, 1},
      {new int[] {1}, 1},

      // Test with repeated elements and pivot
      {new int[] {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5}, 3},

      // Test with all elements less than pivot
      {new int[] {1, 2, 3}, 4},

      // Test with all elements greater than pivot
      {new int[] {5, 6, 7}, 4},
    };

    Partition solution = new Partition();

    for (Object[] test : tests) {
      int[] arr = (int[]) test[0];
      int pivot = (int) test[1];
      int[] arrCopy = Arrays.copyOf(arr, arr.length);
      solution.solve(arrCopy, pivot);
      if (!isValidPartition(arrCopy, pivot)) {
        throw new RuntimeException(String.format(
        "\nsolve(%s, %d): got: %s\n",
        Arrays.toString(arr), pivot, Arrays.toString(arrCopy)));
      }
    }

  }
}

public class P03_15_QuicksortPartition {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
