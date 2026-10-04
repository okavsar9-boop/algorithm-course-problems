// 20.3 - Multiset
// Run: javac P20_03_Multiset.java && java P20_03_Multiset

import java.util.*;
import java.util.function.*;

class HashMap {
  private BiFunction<Integer, Integer, Integer> h;
  private int capacity;
  private int size;
  private List<List<Entry>> buckets;

  private static class Entry {
    int key;
    int value;

    Entry(int key, int value) {
      this.key = key;
      this.value = value;
    }
  }

  public HashMap(BiFunction<Integer, Integer, Integer> h) {
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

  public boolean contains(int k) {
    int hash = h.apply(k, capacity);
    for (Entry entry : buckets.get(hash)) {
      if (entry.key == k) {
        return true;
      }
    }
    return false;
  }

  public Integer get(int k) {
    int hash = h.apply(k, capacity);
    for (Entry entry : buckets.get(hash)) {
      if (entry.key == k) {
        return entry.value;
      }
    }
    return null;
  }

  public void add(int k, int v) {
    int hash = h.apply(k, capacity);
    List<Entry> bucket = buckets.get(hash);
    for (int i = 0; i < bucket.size(); i++) {
      if (bucket.get(i).key == k) {
        bucket.get(i).value = v;
        return;
      }
    }
    bucket.add(new Entry(k, v));
    size++;
    double loadFactor = (double) size / capacity;
    if (loadFactor > 1) {
      resize(capacity * 2);
    }
  }

  public void remove(int k) {
    int hash = h.apply(k, capacity);
    List<Entry> bucket = buckets.get(hash);
    for (int i = 0; i < bucket.size(); i++) {
      if (bucket.get(i).key == k) {
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

  private void resize(int newCapacity) {
    List<List<Entry>> newBuckets = new ArrayList<>(newCapacity);
    for (int i = 0; i < newCapacity; i++) {
      newBuckets.add(new ArrayList<>());
    }
    for (List<Entry> bucket : buckets) {
      for (Entry entry : bucket) {
        int hash = h.apply(entry.key, newCapacity);
        newBuckets.get(hash).add(entry);
      }
    }
    buckets = newBuckets;
    capacity = newCapacity;
  }
}

class Multiset {
  private HashMap map;
  private int size;

  public Multiset(BiFunction<Integer, Integer, Integer> h) {
    this.map = new HashMap(h);
    this.size = 0;
  }

  public int size() {
    return size;
  }

  public boolean contains(int x) {
    return map.contains(x);
  }

  public void add(int x) {
    Integer count = map.get(x);
    if (count == null) {
      map.add(x, 1);
    } else {
      map.add(x, count + 1);
    }
    size++;
  }

  public void remove(int x) {
    Integer count = map.get(x);
    if (count == null) {
      return;
    }
    if (count == 1) {
      map.remove(x);
    } else {
      map.add(x, count - 1);
    }
    size--;
  }
}


class RunTests {
  public void runTests() {
    // Test basic operations
    Multiset m = new Multiset(HInt::solve);
    if (m.size() != 0) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 0\n", m.size()));
    }
    if (m.contains(1)) {
      throw new RuntimeException("\ncontains(1): got: true, want: false\n");
    }

    m.add(1);
    if (m.size() != 1) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 1\n", m.size()));
    }
    if (!m.contains(1)) {
      throw new RuntimeException("\ncontains(1): got: false, want: true\n");
    }

    // Test multiple copies
    m.add(1);
    if (m.size() != 2) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 2\n", m.size()));
    }
    if (!m.contains(1)) {
      throw new RuntimeException("\ncontains(1): got: false, want: true\n");
    }

    // Test multiple elements
    m.add(2);
    m.add(3);
    if (m.size() != 4) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 4\n", m.size()));
    }

    // Test remove
    m.remove(1);
    if (m.size() != 3) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 3\n", m.size()));
    }
    if (!m.contains(1)) {
      throw new RuntimeException("\ncontains(1): got: false, want: true\n");
    }

    m.remove(1);
    if (m.size() != 2) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 2\n", m.size()));
    }
    if (m.contains(1)) {
      throw new RuntimeException("\ncontains(1): got: true, want: false\n");
    }

    // Test remove non-existent
    m.remove(4);
    if (m.size() != 2) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 2\n", m.size()));
    }
  }
}

public class P20_03_Multiset {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
