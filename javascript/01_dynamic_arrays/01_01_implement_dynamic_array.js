// 1.1 - Implement Dynamic Array
// Run: node 01_01_implement_dynamic_array.js

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


function runTests() {

  function testGetSet() {
    const d = new DynamicArray();
    // Setup array with [0, 1, 2, 3, 4]
    for (let i = 0; i < 5; i++) {
      d.append(i);
    }

    // Test get
    if (d.get(0) !== 0) {
      throw new Error("get(0) should return 0");
    }
    if (d.get(4) !== 4) {
      throw new Error("get(4) should return 4");
    }

    // Test set
    d.set(0, 10);
    if (d.get(0) !== 10) {
      throw new Error("After set(0,10), get(0) should return 10");
    }

    // Test error cases
    try {
      d.get(-1);
      throw new Error("get(-1) should throw Error");
    } catch (error) {
      if (!(error instanceof Error)) {
        throw new Error("Expected Error instance");
      }
    }

    try {
      d.get(5);
      throw new Error("get(5) should throw Error");
    } catch (error) {
      if (!(error instanceof Error)) {
        throw new Error("Expected Error instance");
      }
    }

    try {
      d.set(-1, 0);
      throw new Error("set(-1,0) should throw Error");
    } catch (error) {
      if (!(error instanceof Error)) {
        throw new Error("Expected Error instance");
      }
    }

    try {
      d.set(5, 0);
      throw new Error("set(5,0) should throw Error");
    } catch (error) {
      if (!(error instanceof Error)) {
        throw new Error("Expected Error instance");
      }
    }
  }

  function testAppend() {
    const d = new DynamicArray();
    // Test append to empty array
    d.append(1);
    if (d.size() !== 1) {
      throw new Error("Size should be 1 after append");
    }
    if (d.get(0) !== 1) {
      throw new Error("Element at 0 should be 1");
    }

    // Test multiple appends
    d.append(2);
    d.append(3);
    if (d.size() !== 3) {
      throw new Error("Size should be 3 after appends");
    }
    if (d.get(1) !== 2) {
      throw new Error("Element at 1 should be 2");
    }
    if (d.get(2) !== 3) {
      throw new Error("Element at 2 should be 3");
    }
  }

  function testPopBack() {
    const d = new DynamicArray();
    // Test pop from empty array
    try {
      d.popBack();
      throw new Error("popBack() on empty array should throw Error");
    } catch (error) {
      if (!(error instanceof Error)) {
        throw new Error("Expected Error instance");
      }
    }

    // Setup array with [1,2,3]
    d.append(1);
    d.append(2);
    d.append(3);

    // Test pop_back
    d.popBack();
    if (d.size() !== 2) {
      throw new Error("Size should be 2 after popBack");
    }
    try {
      d.get(2);
      throw new Error("get(2) should throw Error after popBack");
    } catch (error) {
      if (!(error instanceof Error)) {
        throw new Error("Expected Error instance");
      }
    }
  }

  function testResize() {
    const d = new DynamicArray();
    // Test initial capacity
    if (d.capacity !== 10) {
      throw new Error("Initial capacity should be 10");
    }

    // Test grow capacity
    for (let i = 0; i < 11; i++) {
      d.append(i);
    }
    if (d.capacity !== 20) {
      throw new Error("Capacity should double to 20");
    }

    // Test shrink capacity
    for (let i = 0; i < 8; i++) {
      d.popBack();
    }
    if (d.capacity !== 10) {
      throw new Error("Capacity should shrink back to 10");
    }
  }

  const tests = [testGetSet, testAppend, testPopBack, testResize];

  for (const test of tests) {
    test();
  }
  return true;
}

runTests();
