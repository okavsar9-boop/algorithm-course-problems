// 20.3 - Multiset
// Run: node 20_03_multiset.js

class HashMap {
  constructor(h) {
    this.h = h;
    this.capacity = 10;
    this._size = 0;
    this.buckets = Array(this.capacity)
      .fill()
      .map(() => []);
  }

  size() {
    return this._size;
  }

  contains(k) {
    const hash = this.h(k, this.capacity);
    for (const [key, _] of this.buckets[hash]) {
      if (key === k) {
        return true;
      }
    }
    return false;
  }

  get(k) {
    const hash = this.h(k, this.capacity);
    for (const [key, val] of this.buckets[hash]) {
      if (key === k) {
        return val;
      }
    }
    return null;
  }

  add(k, v) {
    const hash = this.h(k, this.capacity);
    const bucket = this.buckets[hash];
    for (let i = 0; i < bucket.length; i++) {
      if (bucket[i][0] === k) {
        bucket[i] = [k, v];
        return;
      }
    }
    bucket.push([k, v]);
    this._size++;
    const loadFactor = this._size / this.capacity;
    if (loadFactor > 1) {
      this.resize(this.capacity * 2);
    }
  }

  remove(k) {
    const hash = this.h(k, this.capacity);
    const bucket = this.buckets[hash];
    const index = bucket.findIndex(([key, _]) => key === k);
    if (index !== -1) {
      bucket.splice(index, 1);
      this._size--;
      const loadFactor = this._size / this.capacity;
      if (loadFactor < 0.25 && this.capacity > 10) {
        this.resize(Math.floor(this.capacity / 2));
      }
    }
  }

  resize(newCapacity) {
    const newBuckets = Array(newCapacity)
      .fill()
      .map(() => []);
    for (const bucket of this.buckets) {
      for (const [k, v] of bucket) {
        const hash = this.h(k, newCapacity);
        newBuckets[hash].push([k, v]);
      }
    }
    this.buckets = newBuckets;
    this.capacity = newCapacity;
  }
}

class Multiset {
  constructor(h) {
    this.map = new HashMap(h);
    this._size = 0;
  }

  size() {
    return this._size;
  }

  contains(x) {
    return this.map.contains(x);
  }

  add(x) {
    const count = this.map.get(x);
    if (count === null) {
      this.map.add(x, 1);
    } else {
      this.map.add(x, count + 1);
    }
    this._size++;
  }

  remove(x) {
    const count = this.map.get(x);
    if (count === null) {
      return;
    }
    if (count === 1) {
      this.map.remove(x);
    } else {
      this.map.add(x, count - 1);
    }
    this._size--;
  }
}


function hInt(x, m) {
  const C = 0.6180339887; // A "random-looking" fraction between 0 and 1.
  x *= C; // A product with "random-looking" decimals.
  x -= Math.floor(x); // Keep only the fractional part.
  x *= m; // Scale the number in [0, 1) up to [0, m).
  return Math.floor(x); // Return the integer part.
}

function runTests() {
  // Test basic operations
  const m = new Multiset(hInt);
  if (m.size() !== 0) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 0\n`);
  }
  if (!!m.contains(1)) {
    throw new Error(`\ncontains(1): got: true, want: false\n`);
  }

  m.add(1);
  if (m.size() !== 1) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 1\n`);
  }
  if (!m.contains(1)) {
    throw new Error(`\ncontains(1): got: false, want: true\n`);
  }

  // Test multiple copies
  m.add(1);
  if (m.size() !== 2) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 2\n`);
  }
  if (!m.contains(1)) {
    throw new Error(`\ncontains(1): got: false, want: true\n`);
  }

  // Test multiple elements
  m.add(2);
  m.add(3);
  if (m.size() !== 4) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 4\n`);
  }

  // Test remove
  m.remove(1);
  if (m.size() !== 3) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 3\n`);
  }
  if (!m.contains(1)) {
    throw new Error(`\ncontains(1): got: false, want: true\n`);
  }

  m.remove(1);
  if (m.size() !== 2) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 2\n`);
  }
  if (!!m.contains(1)) {
    throw new Error(`\ncontains(1): got: true, want: false\n`);
  }

  // Test remove non-existent
  m.remove(4);
  if (m.size() !== 2) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 2\n`);
  }
}

runTests();
