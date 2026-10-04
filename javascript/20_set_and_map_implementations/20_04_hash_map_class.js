// 20.4 - Hash Map Class
// Run: node 20_04_hash_map_class.js

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


function hInt(x, m) {
  const C = 0.6180339887; // A "random-looking" fraction between 0 and 1.
  x *= C; // A product with "random-looking" decimals.
  x -= Math.floor(x); // Keep only the fractional part.
  x *= m; // Scale the number in [0, 1) up to [0, m).
  return Math.floor(x); // Return the integer part.
}

function runTests() {
  // Test basic operations
  const m = new HashMap(hInt);
  if (m.size() !== 0) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 0\n`);
  }
  if (!!m.contains(1)) {
    throw new Error(`\ncontains(1): got: true, want: false\n`);
  }
  if (m.get(1) !== null) {
    throw new Error(`\nget(1): got: not null, want: null\n`);
  }

  m.add(1, "one");
  if (m.size() !== 1) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 1\n`);
  }
  if (!m.contains(1)) {
    throw new Error(`\ncontains(1): got: false, want: true\n`);
  }
  if (m.get(1) !== "one") {
    throw new Error(`\nget(1): got: ${m.get(1)}, want: one\n`);
  }

  // Test updating existing key
  m.add(1, "ONE");
  if (m.size() !== 1) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 1\n`);
  }
  if (m.get(1) !== "ONE") {
    throw new Error(`\nget(1): got: ${m.get(1)}, want: ONE\n`);
  }

  // Test multiple elements
  m.add(2, "two");
  m.add(3, "three");
  if (m.size() !== 3) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 3\n`);
  }

  // Test remove
  m.remove(2);
  if (m.size() !== 2) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 2\n`);
  }
  if (!!m.contains(2)) {
    throw new Error(`\ncontains(2): got: true, want: false\n`);
  }
  if (m.get(2) !== null) {
    throw new Error(`\nget(2): got: not null, want: null\n`);
  }

  // Test resize up
  const m2 = new HashMap(hInt);
  for (let i = 0; i < 20; i++) {
    m2.add(i, String(i));
  }
  if (m2.size() !== 20) {
    throw new Error(`\nsize(): got: ${m2.size()}, want: 20\n`);
  }

  // Test resize down
  for (let i = 0; i < 15; i++) {
    m2.remove(i);
  }
  if (m2.size() !== 5) {
    throw new Error(`\nsize(): got: ${m2.size()}, want: 5\n`);
  }
}

runTests();
