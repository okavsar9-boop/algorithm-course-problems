// 7.6 - First K
// Run: javac P07_06_FirstK.java && java P07_06_FirstK

import java.util.*;
import java.util.function.*;

class FirstKSorting {
  public List<Integer> solve(List<Integer> arr, int k) {
    Collections.sort(arr);
    return arr.subList(0, k);
  }
}

class FirstKMinHeap {
  public List<Integer> solve(List<Integer> arr, int k) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>(arr);
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < k; i++) {
      result.add(minHeap.poll());
    }
    return result;
  }
}

class FirstKMaxHeap {
  public List<Integer> solve(List<Integer> arr, int k) {
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(
        Collections.reverseOrder());
    for (int num : arr) {
      maxHeap.offer(num);
      if (maxHeap.size() > k) {
        maxHeap.poll();
      }
    }
    List<Integer> result = new ArrayList<>();
    while (!maxHeap.isEmpty()) {
      result.add(maxHeap.poll());
    }
    return result;
  }
}

class FirstKQuickselect {
  private Quickselect quickselect = new Quickselect();

  public List<Integer> solve(List<Integer> arr, int k) {
    if (arr.isEmpty()) {
      return new ArrayList<>();
    }
    int kthVal = quickselect.solve(arr, k);
    List<Integer> result = new ArrayList<>();
    for (int x : arr) {
      if (x <= kthVal) {
        result.add(x);
      }
    }
    return result;
  }
}

class Partition {
  public List<List<Integer>> solve(List<Integer> arr) {
    Random rand = new Random();
    int pivot = arr.get(rand.nextInt(arr.size()));
    List<Integer> smaller = new ArrayList<>();
    List<Integer> equal = new ArrayList<>();
    List<Integer> larger = new ArrayList<>();
    for (int x : arr) {
      if (x < pivot) {
        smaller.add(x);
      } else if (x == pivot) {
        equal.add(x);
      } else {
        larger.add(x);
      }
    }
    List<List<Integer>> result = new ArrayList<>();
    result.add(smaller);
    result.add(equal);
    result.add(larger);
    return result;
  }
}

For example, if n = 100, S = 60, E = 10, and k = 76, we would need to search for the 6th smallest element in larger.

class Quickselect {
  private Partition partition = new Partition();

  public int solve(List<Integer> arr, int k) {
    List<List<Integer>> partitioned = partition.solve(arr);
    List<Integer> smaller = partitioned.get(0);
    List<Integer> equal = partitioned.get(1);
    List<Integer> larger = partitioned.get(2);
    int S = smaller.size();
    int E = equal.size();

    if (k <= S) {
      return solve(smaller, k);
    } else if (k <= S + E) {
      return equal.get(0);
    } else {
      return solve(larger, k - S - E);
    }
  }
}

As you probably know from the analysis of binary search, if the array is reduced by 50% at each step, the number of steps is log_2(n). (The definition of log_2(n) is "the number of times you can divide n by 2 until you get 1".)

T(n) = O(n) * s + T(0.75n)

Where:

As we discussed, there's a >50% chance that s is 1, so the expected value of s is O(1). This leaves:

T(n) = O(n) + T(0.75n) = O(n + 0.75n + (0.75)^2 n + ...)

The infinite geometric series 1 + 0.75 + (0.75)^2 + ... converges to 4, so we can simplify the whole thing to T(n) = O(4n) = O(n).


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example from the book
        { new int[] { 15, 4, 13, 8, 10, 5, 2, 20, 3, 9, 11, 27 },
            5, new int[] { 2, 3, 4, 5, 8 } },
        // Edge case - k = 1
        { new int[] { 5, 2, 1, 3, 4 },
            1, new int[] { 1 } },
        // Edge case - k = length of array
        { new int[] { 3, 1, 2 },
            3, new int[] { 1, 2, 3 } },
        // Edge case - array of length 1
        { new int[] { 42 },
            1, new int[] { 42 } },
        // Reverse sorted array
        { new int[] { 5, 4, 3, 2, 1 },
            4, new int[] { 1, 2, 3, 4 } },
        // Already sorted array
        { new int[] { 1, 2, 3, 4, 5 },
            3, new int[] { 1, 2, 3 } },
        // Edge case - empty array
        { new int[] {},
            0, new int[] {} },
        // Array with negative numbers
        { new int[] { -3, -1, -4, -2 },
            3, new int[] { -4, -3, -2 } },
        // Mix of positive and negative
        { new int[] { -5, 3, -2, 8, -1 },
            4, new int[] { -5, -2, -1, 3 } },
        // Large numbers
        { new int[] { 1000000000, -1000000000, 0 },
            2, new int[] { -1000000000, 0 } }
    };

    Object[][] solutions = {
        { "firstKSorting", new FirstKSorting() },
        { "firstKMaxHeap", new FirstKMaxHeap() },
        { "firstKMinHeap", new FirstKMinHeap() },
        { "firstKQuickselect", new FirstKQuickselect() }
    };

    for (Object[] solutionPair : solutions) {
      String name = (String) solutionPair[0];
      Object solution = solutionPair[1];

      for (Object[] test : tests) {
        int[] arr = (int[]) test[0];
        int k = (int) test[1];
        int[] wantArray = (int[]) test[2];
        List<Integer> want = Arrays.stream(wantArray).boxed()
            .collect(Collectors.toList());

        List<Integer> arrList = Arrays.stream(arr).boxed()
            .collect(Collectors.toList());

        List<Integer> got;
        if (solution instanceof FirstKSorting) {
          got = ((FirstKSorting) solution).solve(new ArrayList<>(arrList), k);
        } else if (solution instanceof FirstKMaxHeap) {
          got = ((FirstKMaxHeap) solution).solve(new ArrayList<>(arrList), k);
        } else if (solution instanceof FirstKMinHeap) {
          got = ((FirstKMinHeap) solution).solve(new ArrayList<>(arrList), k);
        } else {
          got = ((FirstKQuickselect) solution).solve(new ArrayList<>(arrList),
              k);
        }

        List<Integer> gotSorted = new ArrayList<>(got);
        List<Integer> wantSorted = new ArrayList<>(want);
        Collections.sort(gotSorted);
        Collections.sort(wantSorted);

        if (!gotSorted.equals(wantSorted)) {
          throw new RuntimeException(String.format(
              "\n%s(%s, %d): got: %s, want: %s (in any order)\n",
              name, Arrays.toString(arr), k, got, want));
        }
      }
    }
  }
}

public class P07_06_FirstK {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
