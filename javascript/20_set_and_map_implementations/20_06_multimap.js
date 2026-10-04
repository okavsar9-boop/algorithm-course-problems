// 20.6 - Multimap
// Run: node 20_06_multimap.js

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

class Multimap {
  constructor(h) {
    this.map = new HashMap(h);
    this._size = 0;
  }

  size() {
    return this._size;
  }

  contains(k) {
    return this.map.contains(k);
  }

  get(k) {
    const values = this.map.get(k);
    if (values === null) {
      return [];
    }
    return values;
  }

  add(k, v) {
    const values = this.get(k);
    values.push(v);
    this.map.add(k, values);
    this._size++;
  }

  remove(k) {
    if (this.contains(k)) {
      this._size -= this.get(k).length;
      this.map.remove(k);
    }
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
  const m = new Multimap(hInt);
  if (m.size() !== 0) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 0\n`);
  }
  if (!!m.contains(1)) {
    throw new Error(`\ncontains(1): got: true, want: false\n`);
  }
  if (JSON.stringify(m.get(1)) !== JSON.stringify([])) {
    throw new Error(`\nget(1): got: ${m.get(1)}, want: []\n`);
  }

  // Test add
  m.add(1, "one");
  if (m.size() !== 1) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 1\n`);
  }
  if (!m.contains(1)) {
    throw new Error(`\ncontains(1): got: false, want: true\n`);
  }
  if (JSON.stringify(m.get(1)) !== JSON.stringify(["one"])) {
    throw new Error(`\nget(1): got: ${m.get(1)}, want: ['one']\n`);
  }

  // Test multiple values for same key
  m.add(1, "ONE");
  if (m.size() !== 2) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 2\n`);
  }
  if (JSON.stringify(m.get(1)) !== JSON.stringify(["one", "ONE"])) {
    throw new Error(`\nget(1): got: ${m.get(1)}, want: ['one', 'ONE']\n`);
  }

  // Test multiple keys
  m.add(2, "two");
  if (m.size() !== 3) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 3\n`);
  }
  if (JSON.stringify(m.get(2)) !== JSON.stringify(["two"])) {
    throw new Error(`\nget(2): got: ${m.get(2)}, want: ['two']\n`);
  }

  // Test remove
  m.remove(1);
  if (m.size() !== 1) {
    throw new Error(`\nsize(): got: ${m.size()}, want: 1\n`);
  }
  if (!!m.contains(1)) {
    throw new Error(`\ncontains(1): got: true, want: false\n`);
  }
  if (JSON.stringify(m.get(1)) !== JSON.stringify([])) {
    throw new Error(`\nget(1): got: ${m.get(1)}, want: []\n`);
  }
}

runTests();
