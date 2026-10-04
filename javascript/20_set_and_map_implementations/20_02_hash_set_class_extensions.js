// 20.2 - Hash Set Class Extensions
// Run: node 20_02_hash_set_class_extensions.js

class HashSet {
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

  contains(x) {
    const hash = this.h(x, this.capacity);
    for (const elem of this.buckets[hash]) {
      if (elem === x) {
        return true;
      }
    }
    return false;
  }

  add(x) {
    const hash = this.h(x, this.capacity);
    for (const elem of this.buckets[hash]) {
      if (elem === x) {
        return;
      }
    }
    this.buckets[hash].push(x);
    this._size++;
    const loadFactor = this._size / this.capacity;
    if (loadFactor > 1) {
      this.resize(this.capacity * 2);
    }
  }

  remove(x) {
    const hash = this.h(x, this.capacity);
    const bucket = this.buckets[hash];
    const index = bucket.findIndex((elem) => elem === x);
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
      for (const elem of bucket) {
        const hash = this.h(elem, newCapacity);
        newBuckets[hash].push(elem);
      }
    }
    this.buckets = newBuckets;
    this.capacity = newCapacity;
  }

  elements() {
    const res = [];
    for (const bucket of this.buckets) {
      for (const elem of bucket) {
        res.push(elem);
      }
    }
    return res;
  }

  union(s) {
    const res = new HashSet(this.h);
    for (const elem of this.elements()) {
      res.add(elem);
    }
    for (const elem of s.elements()) {
      res.add(elem);
    }
    return res;
  }

  intersection(s) {
    const res = new HashSet(this.h);
    for (const elem of this.elements()) {
      if (s.contains(elem)) {
        res.add(elem);
      }
    }
    return res;
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
  const s = new HashSet(hInt);
  if (s.size() !== 0) {
    throw new Error(`\nsize(): got: ${s.size()}, want: 0\n`);
  }
  if (!!s.contains(1)) {
    throw new Error(`\ncontains(1): got: true, want: false\n`);
  }

  s.add(1);
  if (s.size() !== 1) {
    throw new Error(`\nsize(): got: ${s.size()}, want: 1\n`);
  }
  if (!s.contains(1)) {
    throw new Error(`\ncontains(1): got: false, want: true\n`);
  }

  s.add(2);
  s.add(3);
  if (s.size() !== 3) {
    throw new Error(`\nsize(): got: ${s.size()}, want: 3\n`);
  }

  s.remove(2);
  if (s.size() !== 2) {
    throw new Error(`\nsize(): got: ${s.size()}, want: 2\n`);
  }
  if (!!s.contains(2)) {
    throw new Error(`\ncontains(2): got: true, want: false\n`);
  }

  // Test elements()
  let got = s.elements().sort((a, b) => a - b);
  let want = [1, 3];
  if (JSON.stringify(got) !== JSON.stringify(want)) {
    throw new Error(`\nelements(): got: ${got}, want: ${want}\n`);
  }

  // Test union()
  const s2 = new HashSet(hInt);
  s2.add(3);
  s2.add(4);
  const union = s.union(s2);
  got = union.elements().sort((a, b) => a - b);
  want = [1, 3, 4];
  if (JSON.stringify(got) !== JSON.stringify(want)) {
    throw new Error(`\nunion(): got: ${got}, want: ${want}\n`);
  }

  // Test intersection()
  const intersection = s.intersection(s2);
  got = intersection.elements().sort((a, b) => a - b);
  want = [3];
  if (JSON.stringify(got) !== JSON.stringify(want)) {
    throw new Error(`\nintersection(): got: ${got}, want: ${want}\n`);
  }

  // Test resize
  const s3 = new HashSet(hInt);
  for (let i = 0; i < 20; i++) {
    s3.add(i);
  }
  if (s3.size() !== 20) {
    throw new Error(`\nsize(): got: ${s3.size()}, want: 20\n`);
  }

  // Remove elements to test downsize
  for (let i = 0; i < 15; i++) {
    s3.remove(i);
  }
  if (s3.size() !== 5) {
    throw new Error(`\nsize(): got: ${s3.size()}, want: 5\n`);
  }
}

runTests();
