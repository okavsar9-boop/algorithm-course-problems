// 20.6 - Multimap
// Run: javac P20_06_Multimap.java && java P20_06_Multimap

import java.util.*;
import java.util.function.*;

class HashMap {
  private BiFunction<Integer, Integer, Integer> h;
  private int capacity;
  private int size;
  private List<List<Entry>> buckets;

  private static class Entry {
    int key;
    List<String> values;

    Entry(int key, List<String> values) {
      this.key = key;
      this.values = values;
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

  public List<String> get(int k) {
    int hash = h.apply(k, capacity);
    for (Entry entry : buckets.get(hash)) {
      if (entry.key == k) {
        return entry.values;
      }
    }
    return new ArrayList<>();
  }

  public void add(int k, List<String> v) {
    int hash = h.apply(k, capacity);
    List<Entry> bucket = buckets.get(hash);
    for (int i = 0; i < bucket.size(); i++) {
      if (bucket.get(i).key == k) {
        bucket.get(i).values = v;
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

class Multimap {
  private HashMap map;
  private int size;

  public Multimap(BiFunction<Integer, Integer, Integer> h) {
    this.map = new HashMap(h);
    this.size = 0;
  }

  public int size() {
    return size;
  }

  public boolean contains(int k) {
    return map.contains(k);
  }

  public List<String> get(int k) {
    return map.get(k);
  }

  public void add(int k, String v) {
    List<String> values = get(k);
    values.add(v);
    map.add(k, values);
    size++;
  }

  public void remove(int k) {
    if (contains(k)) {
      size -= get(k).size();
      map.remove(k);
    }
  }
}


class RunTests {
  public void runTests() {
    // Test basic operations
    Multimap m = new Multimap(HInt::solve);
    if (m.size() != 0) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 0\n", m.size()));
    }
    if (m.contains(1)) {
      throw new RuntimeException("\ncontains(1): got: true, want: false\n");
    }
    if (!m.get(1).isEmpty()) {
      throw new RuntimeException("\nget(1): got: not empty, want: empty\n");
    }

    // Test add
    m.add(1, "one");
    if (m.size() != 1) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 1\n", m.size()));
    }
    if (!m.contains(1)) {
      throw new RuntimeException("\ncontains(1): got: false, want: true\n");
    }
    List<String> want = new ArrayList<>();
    want.add("one");
    if (!m.get(1).equals(want)) {
      throw new RuntimeException(
          String.format("\nget(1): got: %s, want: %s\n", m.get(1), want));
    }

    // Test multiple values for same key
    m.add(1, "ONE");
    if (m.size() != 2) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 2\n", m.size()));
    }
    want = new ArrayList<>();
    want.add("one");
    want.add("ONE");
    if (!m.get(1).equals(want)) {
      throw new RuntimeException(
          String.format("\nget(1): got: %s, want: %s\n", m.get(1), want));
    }

    // Test multiple keys
    m.add(2, "two");
    if (m.size() != 3) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 3\n", m.size()));
    }
    want = new ArrayList<>();
    want.add("two");
    if (!m.get(2).equals(want)) {
      throw new RuntimeException(
          String.format("\nget(2): got: %s, want: %s\n", m.get(2), want));
    }

    // Test remove
    m.remove(1);
    if (m.size() != 1) {
      throw new RuntimeException(
          String.format("\nsize(): got: %d, want: 1\n", m.size()));
    }
    if (m.contains(1)) {
      throw new RuntimeException("\ncontains(1): got: true, want: false\n");
    }
    if (!m.get(1).isEmpty()) {
      throw new RuntimeException("\nget(1): got: not empty, want: empty\n");
    }
  }
}

public class P20_06_Multimap {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
