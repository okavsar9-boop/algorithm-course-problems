// 13.1 - Implement a Heap
// Run: javac P13_01_ImplementAHeap.java && java P13_01_ImplementAHeap

import java.util.*;
import java.util.function.*;

class Heap<T extends Comparable<T>> {
  /**
  * A binary heap implementation that can act as either min-heap or max-heap.
  */

  private List<T> heap;
  private BiFunction<T, T, Boolean> higherPriority;

  /**
  * By default, it creates a min-heap (the smallest element has highest
  * priority). For max-heap behavior, provide a custom 'higherPriority'
  * function.
  *
  * higherPriority: Function that returns true if x has higher priority than y.
  * heap: Optional list of initial elements to heapify.
  */
  public Heap(BiFunction<T, T, Boolean> higherPriority, List<T> heap) {
    if (higherPriority == null) {
      // Default to min-heap
      this.higherPriority = (x, y) -> x.compareTo(y) < 0;
    } else {
      this.higherPriority = higherPriority;
    }
    if (heap != null && heap.size() > 0) {
      this.heap = new ArrayList<>(heap);
      heapify();
    } else {
      this.heap = new ArrayList<>();
    }
  }

  public Heap() {
    this(null, null);
  }

  public Heap(BiFunction<T, T, Boolean> higherPriority) {
    this(higherPriority, null);
  }

  public Heap(List<T> heap) {
    this(null, heap);
  }

  /** Returns the number of elements in the heap */
  public int size() {
    return heap.size();
  }

  /** Returns the highest priority element without removing it */
  public T top() {
    if (heap.isEmpty()) {
      return null;
    }
    return heap.get(0);
  }

  /** Adds an element to the heap */
  public void push(T elem) {
    heap.add(elem);
    bubbleUp(heap.size() - 1);
  }

  /** Removes and returns the highest priority element */
  public T pop() {
    if (heap.isEmpty()) {
      return null;
    }

    T top = heap.get(0);
    if (heap.size() == 1) {
      heap.clear();
      return top;
    }

    heap.set(0, heap.get(heap.size() - 1));
    heap.remove(heap.size() - 1);
    bubbleDown(0);

    return top;
  }

  /** Converts a list into a valid heap in O(n) time */
  public void heapify() {
    for (int idx = heap.size() / 2; idx >= 0; idx--) {
      bubbleDown(idx);
    }
  }

  /** Get parent index */
  private int parent(int idx) {
    if (idx == 0) {
      return -1; // The root has no parent
    }
    return (idx - 1) / 2;
  }

  /** Get left child index */
  private int leftChild(int idx) {
    return 2 * idx + 1;
  }

  /** Get right child index */
  private int rightChild(int idx) {
    return 2 * idx + 2;
  }

  /** Move element up until heap property is restored */
  private void bubbleUp(int idx) {
    if (idx == 0) {
      return;
    }

    int parentIdx = parent(idx);
    if (parentIdx >= 0
    && higherPriority.apply(heap.get(idx), heap.get(parentIdx))) {
      T temp = heap.get(idx);
      heap.set(idx, heap.get(parentIdx));
      heap.set(parentIdx, temp);
      bubbleUp(parentIdx);
    }
  }

  /** Move element down until heap property is restored */
  private void bubbleDown(int idx) {
    int leftIdx = leftChild(idx);
    boolean isLeaf = leftIdx >= heap.size();
    if (isLeaf) {
      return;
    }

    // Find child with higher priority
    int childIdx = leftIdx;
    int rightIdx = rightChild(idx);
    if (rightIdx < heap.size() &&
    higherPriority.apply(heap.get(rightIdx), heap.get(leftIdx))) {
      childIdx = rightIdx;
    }

    // Swap with child if it has higher priority
    if (higherPriority.apply(heap.get(childIdx), heap.get(idx))) {
      T temp = heap.get(idx);
      heap.set(idx, heap.get(childIdx));
      heap.set(childIdx, temp);
      bubbleDown(childIdx);
    }
  }
}


class RunTests {
  public void runTests() {
    // Test min heap
    Heap<Integer> minHeap = new Heap<>();
    Integer[] values = { 4, 8, 2, 6, 1, 7, 3, 5 };
    for (Integer val : values) {
      minHeap.push(val);
    }

    // Should pop in ascending order
    Integer[] sortedValues = new Integer[values.length];
    int i = 0;
    while (minHeap.size() > 0) {
      sortedValues[i++] = minHeap.pop();
    }
    Integer[] want = { 1, 2, 3, 4, 5, 6, 7, 8 };
    if (!Arrays.equals(sortedValues, want)) {
      throw new RuntimeException(String.format(
          "\nmin heap popped values: got: %s, want: %s\n",
          Arrays.toString(sortedValues), Arrays.toString(want)));
    }

    // Test max heap
    Heap<Integer> maxHeap = new Heap<>((x, y) -> x > y);
    for (Integer val : values) {
      maxHeap.push(val);
    }

    // Should pop in descending order
    sortedValues = new Integer[values.length];
    i = 0;
    while (maxHeap.size() > 0) {
      sortedValues[i++] = maxHeap.pop();
    }
    Integer[] want2 = { 8, 7, 6, 5, 4, 3, 2, 1 };
    if (!Arrays.equals(sortedValues, want2)) {
      throw new RuntimeException(String.format(
          "\nmax heap popped values: got: %s, want: %s\n",
          Arrays.toString(sortedValues), Arrays.toString(want2)));
    }

    // Test heapify
    Heap<Integer> heap = new Heap<>(Arrays.asList(4, 8, 2, 6, 1, 7, 3, 5));
    if (heap.pop() != 1) {
      throw new RuntimeException("heap.pop(): expected 1");
    }
    if (heap.pop() != 2) {
      throw new RuntimeException("heap.pop(): expected 2");
    }
    if (heap.pop() != 3) {
      throw new RuntimeException("heap.pop(): expected 3");
    }
  }
}

public class P13_01_ImplementAHeap {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
