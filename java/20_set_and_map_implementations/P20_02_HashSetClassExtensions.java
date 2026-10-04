// 20.2 - Hash Set Class Extensions
// Run: javac P20_02_HashSetClassExtensions.java && java P20_02_HashSetClassExtensions

import java.util.*;
import java.util.function.*;

class HashSet {
  private BiFunction<Integer, Integer, Integer> h;
  private int capacity;
  private int size;
  private List<List<Integer>> buckets;

  public HashSet(BiFunction<Integer, Integer, Integer> h) {
    this.h = h;
    this.capacity = 10;
    this.size = 0;
    this.buckets = new ArrayList<>(capacity);
    for (int i = 0; i < capacity; i++) {
      this.buckets.add(new ArrayList<>());
    }
  }

  public int size() {
    return size;
  }

  public boolean contains(int x) {
    int hash = h.apply(x, capacity);
    for (int elem : buckets.get(hash)) {
      if (elem == x) {
        return true;
      }
    }
    return false;
  }

  public void add(int x) {
    int hash = h.apply(x, capacity);
    for (int elem : buckets.get(hash)) {
      if (elem == x) {
        return;
      }
    }
    buckets.get(hash).add(x);
    size++;
    double loadFactor = (double) size / capacity;
    if (loadFactor > 1) {
      resize(capacity * 2);
    }
  }

  public void remove(int x) {
    int hash = h.apply(x, capacity);
    List<Integer> bucket = buckets.get(hash);
    for (int i = 0; i < bucket.size(); i++) {
      if (bucket.get(i) == x) {
        bucket.remove(i);
        size--;
        double loadFactor = (double) size / capacity;
        if (loadFactor < 0.25 && capacity > 10) {
          resize(capacity / 2);
        }
        return;
      }
    }
  }

  public List<Integer> elements() {
    List<Integer> res = new ArrayList<>();
    for (List<Integer> bucket : buckets) {
      res.addAll(bucket);
    }
    return res;
  }

  public HashSet union(HashSet s) {
    HashSet res = new HashSet(h);
    for (int elem : elements()) {
      res.add(elem);
    }
    for (int elem : s.elements()) {
      res.add(elem);
    }
    return res;
  }

  public HashSet intersection(HashSet s) {
    HashSet res = new HashSet(h);
    for (int elem : elements()) {
      if (s.contains(elem)) {
        res.add(elem);
      }
    }
    return res;
  }

  private void resize(int newCapacity) {
    List<List<Integer>> newBuckets = new ArrayList<>(newCapacity);
    for (int i = 0; i < newCapacity; i++) {
      newBuckets.add(new ArrayList<>());
    }
    for (List<Integer> bucket : buckets) {
      for (int elem : bucket) {
        int hash = h.apply(elem, newCapacity);
        newBuckets.get(hash).add(elem);
      }
    }
    buckets = newBuckets;
    capacity = newCapacity;
  }
}


class RunTests {
  public void runTests() {
    // Test basic operations
    HashSet s = new HashSet(HInt::solve);
    if (s.size() != 0) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 0\n", s.size()));
    }
    if (s.contains(1)) {
      throw new RuntimeException("\ncontains(1): got: true, want: false\n");
    }

    s.add(1);
    if (s.size() != 1) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 1\n", s.size()));
    }
    if (!s.contains(1)) {
      throw new RuntimeException("\ncontains(1): got: false, want: true\n");
    }

    s.add(2);
    s.add(3);
    if (s.size() != 3) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 3\n", s.size()));
    }

    s.remove(2);
    if (s.size() != 2) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 2\n", s.size()));
    }
    if (s.contains(2)) {
      throw new RuntimeException("\ncontains(2): got: true, want: false\n");
    }

    // Test elements()
    List<Integer> got = s.elements();
    Collections.sort(got);
    List<Integer> want = new ArrayList<>();
    want.add(1);
    want.add(3);
    if (!got.equals(want)) {
      throw new RuntimeException(
          String.format("\nelements(): got: %s, want: %s\n", got, want));
    }

    // Test union()
    HashSet s2 = new HashSet(HInt::solve);
    s2.add(3);
    s2.add(4);
    HashSet unionSet = s.union(s2);
    got = unionSet.elements();
    Collections.sort(got);
    want = new ArrayList<>();
    want.add(1);
    want.add(3);
    want.add(4);
    if (!got.equals(want)) {
      throw new RuntimeException(
          String.format("\nunion(): got: %s, want: %s\n", got, want));
    }

    // Test intersection()
    HashSet intersectionSet = s.intersection(s2);
    got = intersectionSet.elements();
    Collections.sort(got);
    want = new ArrayList<>();
    want.add(3);
    if (!got.equals(want)) {
      throw new RuntimeException(
          String.format("\nintersection(): got: %s, want: %s\n", got, want));
    }

    // Test resize
    HashSet s3 = new HashSet(HInt::solve);
    for (int i = 0; i < 20; i++) {
      s3.add(i);
    }
    if (s3.size() != 20) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 20\n", s3.size()));
    }

    // Remove elements to test downsize
    for (int i = 0; i < 15; i++) {
      s3.remove(i);
    }
    if (s3.size() != 5) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 5\n", s3.size()));
    }
  }
}

public class P20_02_HashSetClassExtensions {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
