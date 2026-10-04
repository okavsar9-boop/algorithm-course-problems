// 20.4 - Hash Map Class
// Run: javac P20_04_HashMapClass.java && java P20_04_HashMapClass

import java.util.*;
import java.util.function.*;

class HashMap {
  private BiFunction<Integer, Integer, Integer> h;
  private int capacity;
  private int size;
  private List<List<Entry>> buckets;

  private static class Entry {
    int key;
    String value;

    Entry(int key, String value) {
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

  public String get(int k) {
    int hash = h.apply(k, capacity);
    for (Entry entry : buckets.get(hash)) {
      if (entry.key == k) {
        return entry.value;
      }
    }
    return null;
  }

  public void add(int k, String v) {
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


class RunTests {
  public void runTests() {
    // Test basic operations
    HashMap m = new HashMap(HInt::solve);
    if (m.size() != 0) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 0\n", m.size()));
    }
    if (m.contains(1)) {
      throw new RuntimeException("\ncontains(1): got: true, want: false\n");
    }
    if (m.get(1) != null) {
      throw new RuntimeException("\nget(1): got: not null, want: null\n");
    }

    m.add(1, "one");
    if (m.size() != 1) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 1\n", m.size()));
    }
    if (!m.contains(1)) {
      throw new RuntimeException("\ncontains(1): got: false, want: true\n");
    }
    if (!"one".equals(m.get(1))) {
      throw new RuntimeException(
          String.format("\nget(1): got: %s, want: one\n", m.get(1)));
    }

    // Test updating existing key
    m.add(1, "ONE");
    if (m.size() != 1) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 1\n", m.size()));
    }
    if (!"ONE".equals(m.get(1))) {
      throw new RuntimeException(
          String.format("\nget(1): got: %s, want: ONE\n", m.get(1)));
    }

    // Test multiple elements
    m.add(2, "two");
    m.add(3, "three");
    if (m.size() != 3) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 3\n", m.size()));
    }

    // Test remove
    m.remove(2);
    if (m.size() != 2) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 2\n", m.size()));
    }
    if (m.contains(2)) {
      throw new RuntimeException("\ncontains(2): got: true, want: false\n");
    }
    if (m.get(2) != null) {
      throw new RuntimeException("\nget(2): got: not null, want: null\n");
    }

    // Test resize up
    HashMap m2 = new HashMap(HInt::solve);
    for (int i = 0; i < 20; i++) {
      m2.add(i, String.valueOf(i));
    }
    if (m2.size() != 20) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 20\n", m2.size()));
    }

    // Test resize down
    for (int i = 0; i < 15; i++) {
      m2.remove(i);
    }
    if (m2.size() != 5) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 5\n", m2.size()));
    }
  }
}

public class P20_04_HashMapClass {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
