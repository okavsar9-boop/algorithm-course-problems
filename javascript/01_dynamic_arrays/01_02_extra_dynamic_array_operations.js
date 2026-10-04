// 1.2 - Extra Dynamic Array Operations
// Run: node 01_02_extra_dynamic_array_operations.js

class DynamicArray {
  constructor() {
    this.capacity = 10;
    this._size = 0;
    this.fixedArray = new Array(this.capacity).fill(null);
  }

  get(i) {
    if (i < 0 || i >= this._size) {
      throw new Error("Index out of bounds");
    }
    return this.fixedArray[i];
  }

  set(i, x) {
    if (i < 0 || i >= this._size) {
      throw new Error("Index out of bounds");
    }
    this.fixedArray[i] = x;
  }

  size() {
    return this._size;
  }

  append(x) {
    if (this._size === this.capacity) {
      this.resize(this.capacity * 2);
    }
    this.fixedArray[this._size] = x;
    this._size++;
  }

  resize(newCapacity) {
    const newFixedSizeArr = new Array(newCapacity).fill(null);
    for (let i = 0; i < this._size; i++) {
      newFixedSizeArr[i] = this.fixedArray[i];
    }
    this.fixedArray = newFixedSizeArr;
    this.capacity = newCapacity;
  }

  popBack() {
    if (this._size === 0) {
      throw new Error("Pop from empty array");
    }
    this._size--;
    if (this._size / this.capacity < 0.25 && this.capacity > 10) {
      this.resize(Math.floor(this.capacity / 2));
    }
  }
}

class DynamicArrayExtras extends DynamicArray {
  pop(i) {
    if (i < 0 || i >= this.size()) {
      throw new Error("Index out of bounds");
    }

    const x = this.get(i);

    for (let j = i; j < this.size() - 1; j++) {
      this.set(j, this.get(j + 1));
    }

    this.popBack();
    return x;

  }

  contains(x) {
    for (let i = 0; i < this.size(); i++) {
      if (this.get(i) === x) {
        return true;
      }
    }
    return false;
  }

  insert(i, x) {
    if (i < 0 || i > this.size()) {
      throw new Error("Index out of bounds");
    }

    this.append(0); // Make space
    for (let j = this.size() - 1; j > i; j--) {
      this.set(j, this.get(j - 1));
    }
    this.set(i, x);

  }

  remove(x) {
    for (let i = 0; i < this.size(); i++) {
      if (this.get(i) === x) {
        this.pop(i);
        return i;
      }
    }
    return -1;
  }
}


function runTests() {

  function testPop() {
    const d = new DynamicArrayExtras();
    // Setup array with [0,1,2,3,4]
    for (let i = 0; i < 5; i++) {
      d.append(i);
    }

    // Test pop from middle
    if (d.pop(2) !== 2) {
      throw new Error("pop(2) should return 2");
    }
    if (d.size() !== 4) {
      throw new Error("Size should be 4 after pop");
    }
    if (d.get(2) !== 3) {
      throw new Error("Element at 2 should now be 3");
    }

    // Test pop from start
    if (d.pop(0) !== 0) {
      throw new Error("pop(0) should return 0");
    }
    if (d.size() !== 3) {
      throw new Error("Size should be 3 after pop");
    }
    if (d.get(0) !== 1) {
      throw new Error("Element at 0 should now be 1");
    }

    // Test pop from end
    if (d.pop(2) !== 4) {
      throw new Error("pop(2) should return 4");
    }
    if (d.size() !== 2) {
      throw new Error("Size should be 2 after pop");
    }

    // Test error cases
    try {
      d.pop(-1);
      throw new Error("pop(-1) should throw Error");
    } catch (error) {
      if (!(error instanceof Error)) {
        throw new Error("Expected Error instance");
      }
    }

    try {
      d.pop(2);
      throw new Error("pop(2) should throw Error");
    } catch (error) {
      if (!(error instanceof Error)) {
        throw new Error("Expected Error instance");
      }
    }

  }

  function testContains() {
    const d = new DynamicArrayExtras();
    // Test empty array
    if (d.contains(1)) {
      throw new Error("Empty array should not contain 1");
    }

    // Setup array with [1,2,3]
    d.append(1);
    d.append(2);
    d.append(3);

    // Test positive cases
    if (!d.contains(1)) {
      throw new Error("Array should contain 1");
    }
    if (!d.contains(2)) {
      throw new Error("Array should contain 2");
    }
    if (!d.contains(3)) {
      throw new Error("Array should contain 3");
    }

    // Test negative cases
    if (d.contains(0)) {
      throw new Error("Array should not contain 0");
    }
    if (d.contains(4)) {
      throw new Error("Array should not contain 4");
    }

  }

  function testInsert() {
    const d = new DynamicArrayExtras();
    // Test insert into empty array
    d.insert(0, 1);
    if (d.size() !== 1) {
      throw new Error("Size should be 1 after insert");
    }
    if (d.get(0) !== 1) {
      throw new Error("Element at 0 should be 1");
    }

    // Test insert at start
    d.insert(0, 0);
    if (d.size() !== 2) {
      throw new Error("Size should be 2 after insert");
    }
    if (d.get(0) !== 0) {
      throw new Error("Element at 0 should be 0");
    }
    if (d.get(1) !== 1) {
      throw new Error("Element at 1 should be 1");
    }

    // Test insert at end
    d.insert(2, 2);
    if (d.size() !== 3) {
      throw new Error("Size should be 3 after insert");
    }
    if (d.get(2) !== 2) {
      throw new Error("Element at 2 should be 2");
    }

    // Test insert in middle
    d.insert(1, 3);
    if (d.size() !== 4) {
      throw new Error("Size should be 4 after insert");
    }
    if (d.get(1) !== 3) {
      throw new Error("Element at 1 should be 3");
    }

    // Test error cases
    try {
      d.insert(-1, 0);
      throw new Error("insert(-1, 0) should throw Error");
    } catch (error) {
      if (!(error instanceof Error)) {
        throw new Error("Expected Error instance");
      }
    }

    try {
      d.insert(5, 0);
      throw new Error("insert(5, 0) should throw Error");
    } catch (error) {
      if (!(error instanceof Error)) {
        throw new Error("Expected Error instance");
      }
    }

  }

  function testRemove() {
    const d = new DynamicArrayExtras();
    // Test remove from empty array
    if (d.remove(1) !== -1) {
      throw new Error("Remove from empty array should return -1");
    }

    // Setup array with [1,2,2,3]
    d.append(1);
    d.append(2);
    d.append(2);
    d.append(3);

    // Test successful removes
    if (d.remove(1) !== 0) {
      throw new Error("Remove should return index 0");
    }
    if (d.size() !== 3) {
      throw new Error("Size should be 3 after remove");
    }
    if (d.get(0) !== 2) {
      throw new Error("Element at 0 should be 2");
    }

    if (d.remove(2) !== 0) {
      throw new Error("Remove should return index 0");
    }
    if (d.size() !== 2) {
      throw new Error("Size should be 2 after remove");
    }
    if (d.get(0) !== 2) {
      throw new Error("Element at 0 should be 2");
    }

    // Test remove non-existent element
    if (d.remove(4) !== -1) {
      throw new Error("Remove non-existent should return -1");
    }

  }

  try {
    testPop();
    testContains();
    testInsert();
    testRemove();
  } catch (error) {
    console.log(`Test failed: ${error.message}`);
    throw error;
  }
  return true;
}

runTests();
