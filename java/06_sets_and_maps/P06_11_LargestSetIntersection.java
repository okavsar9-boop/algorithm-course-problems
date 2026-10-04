// 6.11 - Largest Set Intersection
// Run: javac P06_11_LargestSetIntersection.java && java P06_11_LargestSetIntersection

import java.util.*;
import java.util.function.*;

class LargestSetIntersectionFrequencyMap {
  public int solve(List<Set<Integer>> sets) {
    if (sets.size() == 1) {
      return 0;
    }

    // Create frequency map from integers to number of sets they appear in
    Map<Integer, Integer> freq = new HashMap<>();
    for (Set<Integer> s : sets) {
      for (int x : s) {
        freq.put(x, freq.getOrDefault(x, 0) + 1);
      }
    }

    // For each set, count elements that appear k-1 times
    int k = sets.size();
    int bestIndex = 0;
    int minCount = Integer.MAX_VALUE;
    for (int i = 0; i < sets.size(); i++) {
      int count = 0;
      for (int x : sets.get(i)) {
        if (freq.get(x) == k - 1) {
          count++;
        }
      }
      if (count < minCount) {
        minCount = count;
        bestIndex = i;
      }
    }

    return bestIndex;
  }
}

# Initialization
prefix_sum[0] = arr[0]  # There must be at least one element.
  prefix_sum[i] = prefix_sum[i-1] + arr[i]

# Query: sum of subarray [l, r]
if l == 0:
  return prefix_sum[r]
return prefix_sum[r] - prefix_sum[l-1]

    intersection(prefix_intersection[i-1], postfix_intersection[i+1])

class LargestSetIntersectionPrefixSum {
  public int solve(List<Set<Integer>> sets) {
    int n = sets.size();
    if (n == 1) {
      return 0;
    }

    List<Set<Integer>> hashSets = new ArrayList<>();
    for (Set<Integer> s : sets) {
      hashSets.add(new HashSet<>(s));
    }

    // Compute prefix intersections
    List<Set<Integer>> prefixIntersections = new ArrayList<>(
        Collections.nCopies(n, null));
    prefixIntersections.set(0, hashSets.get(0));
    for (int i = 1; i < n; i++) {
      Set<Integer> intersection = new HashSet<>(prefixIntersections.get(i - 1));
      intersection.retainAll(hashSets.get(i));
      prefixIntersections.set(i, intersection);
    }

    // Compute suffix intersections
    List<Set<Integer>> suffixIntersections = new ArrayList<>(
        Collections.nCopies(n, null));
    suffixIntersections.set(n - 1, hashSets.get(n - 1));
    for (int i = n - 2; i >= 0; i--) {
      Set<Integer> intersection = new HashSet<>(suffixIntersections.get(i + 1));
      intersection.retainAll(hashSets.get(i));
      suffixIntersections.set(i, intersection);
    }

    // Find the best index to exclude
    int bestIndex = 0;
    int maxSize = 0;

    for (int i = 0; i < n; i++) {
      // Compute intersection excluding sets[i]
      Set<Integer> intersection;
      if (i == 0) {
        intersection = suffixIntersections.get(1);
      } else if (i == n - 1) {
        intersection = prefixIntersections.get(n - 2);
      } else {
        intersection = new HashSet<>(prefixIntersections.get(i - 1));
        intersection.retainAll(suffixIntersections.get(i + 1));
      }

      if (intersection.size() > maxSize) {
        maxSize = intersection.size();
        bestIndex = i;
      }
    }

    return bestIndex;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example 1
        {
            new int[][] {
                { 1, 2, 3 },
                { 3, 2, 1 },
                { 1, 4, 5 },
                { 1, 2 }
            },
            2
        },
        // Example 2
        {
            new int[][] {
                { 1, 2 },
                { 3, 4 },
                { 5, 6 }
            },
            0
        },
        // Example 3
        {
            new int[][] {
                { 1, 2, 3 },
                { 4, 5 }
            },
            1
        },
        // Example 4
        {
            new int[][] {
                { 1, 2, 3 }
            },
            0
        },
        // Additional test cases
        {
            new int[][] {
                { 1 },
                { 1 }
            },
            0
        },
        {
            new int[][] {
                { 1, 2 },
                { 2, 3 },
                { 1, 3 }
            },
            0
        }
    };

    LargestSetIntersectionFrequencyMap solutionFreq = new LargestSetIntersectionFrequencyMap();
    LargestSetIntersectionPrefixSum solutionPrefix = new LargestSetIntersectionPrefixSum();
    for (Object[] test : tests) {
      int[][] setsArray = (int[][]) test[0];
      List<Set<Integer>> sets = Arrays.stream(setsArray)
          .map(arr -> Arrays.stream(arr).boxed().collect(Collectors.toSet()))
          .collect(Collectors.toList());
      int want = (Integer) test[1];
      int gotFreq = solutionFreq.solve(sets);
      int gotPrefix = solutionPrefix.solve(sets);
      if (gotFreq != want) {
        throw new RuntimeException(String.format(
            "\nLargestSetIntersectionFrequencyMap.solve(%s): got: %d, want: %d\n",
            sets, gotFreq, want));
      }
      if (gotPrefix != want) {
        throw new RuntimeException(String.format(
            "\nLargestSetIntersectionPrefixSum.solve(%s): got: %d, want: %d\n",
            sets, gotPrefix, want));
      }
    }
  }
}

public class P06_11_LargestSetIntersection {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
