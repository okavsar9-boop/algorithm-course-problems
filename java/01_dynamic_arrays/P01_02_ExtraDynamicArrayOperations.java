// 1.2 - Extra Dynamic Array Operations
// Run: javac P01_02_ExtraDynamicArrayOperations.java && java P01_02_ExtraDynamicArrayOperations

import java.util.*;
import java.util.function.*;

class DynamicArrayExtras extends DynamicArray {
  public int pop(int i) {
    if (i < 0 || i >= size()) {
      throw new IndexOutOfBoundsException("Index out of bounds");
    }

    int x = get(i);

    for (int j = i; j < size() - 1; j++) {
      set(j, get(j + 1));
    }

    popBack();
    return x;

  }

  public boolean contains(int x) {
    for (int i = 0; i < size(); i++) {
      if (get(i) == x) {
        return true;
      }
    }
    return false;
  }

  public void insert(int i, int x) {
    if (i < 0 || i > size()) {
      throw new IndexOutOfBoundsException("Index out of bounds");
    }

    append(0); // Make space
    for (int j = size() - 1; j > i; j--) {
      set(j, get(j - 1));
    }
    set(i, x);

  }

  public int remove(int x) {
    for (int i = 0; i < size(); i++) {
      if (get(i) == x) {
        pop(i);
        return i;
      }
    }
    return -1;
  }
}

class DynamicArray {
  private int[] fixedArray;
  private int capacity;
  private int _size;

  public DynamicArray() {
    capacity = 10;
    _size = 0;
    fixedArray = new int[capacity]; // Java arrays are initialized to 0 by
    // default
  }

  public int get(int i) {
    if (i < 0 || i >= _size) {
      throw new IndexOutOfBoundsException("Index out of bounds");
    }
    return fixedArray[i];
  }

  public void set(int i, int x) {
    if (i < 0 || i >= _size) {
      throw new IndexOutOfBoundsException("Index out of bounds");
    }
    fixedArray[i] = x;
  }

  public int size() {
    return _size;
  }

  public void append(int x) {
    if (_size == capacity) {
      resize(capacity * 2);
    }
    fixedArray[_size] = x;
    _size++;
  }

  private void resize(int newCapacity) {
    int[] newFixedSizeArr = new int[newCapacity];
    for (int i = 0; i < _size; i++) {
      newFixedSizeArr[i] = fixedArray[i];
    }
    fixedArray = newFixedSizeArr;
    capacity = newCapacity;
  }

  public void popBack() {
    if (_size == 0) {
      throw new IndexOutOfBoundsException("Pop from empty array");
    }
    _size--;
    if (_size * 4 < capacity && capacity > 10) {
      resize(capacity / 2);
    }
  }

  // Added for testing
  public int getCapacity() {
    return capacity;
  }
}


class RunTests {
  private void testPop() {
    DynamicArrayExtras d = new DynamicArrayExtras();
    // Setup array with [0,1,2,3,4]
    for (int i = 0; i < 5; i++) {
      d.append(i);
    }

    // Test pop from middle
    if (d.pop(2) != 2) {
      throw new RuntimeException("pop(2) should return 2");
    }
    if (d.size() != 4) {
      throw new RuntimeException("Size should be 4 after pop");
    }
    if (d.get(2) != 3) {
      throw new RuntimeException("Element at 2 should now be 3");
    }

    // Test pop from start
    if (d.pop(0) != 0) {
      throw new RuntimeException("pop(0) should return 0");
    }
    if (d.size() != 3) {
      throw new RuntimeException("Size should be 3 after pop");
    }
    if (d.get(0) != 1) {
      throw new RuntimeException("Element at 0 should now be 1");
    }

    // Test pop from end
    if (d.pop(2) != 4) {
      throw new RuntimeException("pop(2) should return 4");
    }
    if (d.size() != 2) {
      throw new RuntimeException("Size should be 2 after pop");
    }

    // Test error cases
    try {
      d.pop(-1);
      throw new RuntimeException(
          "pop(-1) should throw IndexOutOfBoundsException");
    } catch (IndexOutOfBoundsException e) {
      // Expected
    }

    try {
      d.pop(2);
      throw new RuntimeException(
          "pop(2) should throw IndexOutOfBoundsException");
    } catch (IndexOutOfBoundsException e) {
      // Expected
    }
  }

  private void testContains() {
    DynamicArrayExtras d = new DynamicArrayExtras();
    // Test empty array
    if (d.contains(1)) {
      throw new RuntimeException("Empty array should not contain 1");
    }

    // Setup array with [1,2,3]
    d.append(1);
    d.append(2);
    d.append(3);

    // Test positive cases
    if (!d.contains(1)) {
      throw new RuntimeException("Array should contain 1");
    }
    if (!d.contains(2)) {
      throw new RuntimeException("Array should contain 2");
    }
    if (!d.contains(3)) {
      throw new RuntimeException("Array should contain 3");
    }

    // Test negative cases
    if (d.contains(0)) {
      throw new RuntimeException("Array should not contain 0");
    }
    if (d.contains(4)) {
      throw new RuntimeException("Array should not contain 4");
    }
  }

  private void testInsert() {
    DynamicArrayExtras d = new DynamicArrayExtras();
    // Test insert into empty array
    d.insert(0, 1);
    if (d.size() != 1) {
      throw new RuntimeException("Size should be 1 after insert");
    }
    if (d.get(0) != 1) {
      throw new RuntimeException("Element at 0 should be 1");
    }

    // Test insert at start
    d.insert(0, 0);
    if (d.size() != 2) {
      throw new RuntimeException("Size should be 2 after insert");
    }
    if (d.get(0) != 0) {
      throw new RuntimeException("Element at 0 should be 0");
    }
    if (d.get(1) != 1) {
      throw new RuntimeException("Element at 1 should be 1");
    }

    // Test insert at end
    d.insert(2, 2);
    if (d.size() != 3) {
      throw new RuntimeException("Size should be 3 after insert");
    }
    if (d.get(2) != 2) {
      throw new RuntimeException("Element at 2 should be 2");
    }

    // Test insert in middle
    d.insert(1, 3);
    if (d.size() != 4) {
      throw new RuntimeException("Size should be 4 after insert");
    }
    if (d.get(1) != 3) {
      throw new RuntimeException("Element at 1 should be 3");
    }

    // Test error cases
    try {
      d.insert(-1, 0);
      throw new RuntimeException(
          "insert(-1, 0) should throw IndexOutOfBoundsException");
    } catch (IndexOutOfBoundsException e) {
      // Expected
    }

    try {
      d.insert(5, 0);
      throw new RuntimeException(
          "insert(5, 0) should throw IndexOutOfBoundsException");
    } catch (IndexOutOfBoundsException e) {
      // Expected
    }
  }

  private void testRemove() {
    DynamicArrayExtras d = new DynamicArrayExtras();
    // Test remove from empty array
    if (d.remove(1) != -1) {
      throw new RuntimeException("Remove from empty array should return -1");
    }

    // Setup array with [1,2,2,3]
    d.append(1);
    d.append(2);
    d.append(2);
    d.append(3);

    // Test successful removes
    if (d.remove(1) != 0) {
      throw new RuntimeException("Remove should return index 0");
    }
    if (d.size() != 3) {
      throw new RuntimeException("Size should be 3 after remove");
    }
    if (d.get(0) != 2) {
      throw new RuntimeException("Element at 0 should be 2");
    }

    if (d.remove(2) != 0) {
      throw new RuntimeException("Remove should return index 0");
    }
    if (d.size() != 2) {
      throw new RuntimeException("Size should be 2 after remove");
    }
    if (d.get(0) != 2) {
      throw new RuntimeException("Element at 0 should be 2");
    }

    // Test remove non-existent element
    if (d.remove(4) != -1) {
      throw new RuntimeException("Remove non-existent should return -1");
    }
  }

  public void runTests() {
    try {
      testPop();
      testContains();
      testInsert();
      testRemove();
    } catch (RuntimeException e) {
      System.out.println("Test failed: " + e.getMessage());
      throw e;
    }
  }
}

public class P01_02_ExtraDynamicArrayOperations {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
