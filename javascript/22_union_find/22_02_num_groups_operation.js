// 22.2 - Num Groups Operation
// Run: node 22_02_num_groups_operation.js

class UnionFind {
  constructor() {
    this.parent = {};
    this.size = {};
  }

  // Assumes x is not already in the UnionFind.
  add(x) {
    this.parent[x] = x;
    this.size[x] = 1;
  }

  // Assumes x is already in the UnionFind.
  find(x) {
    let root = this.parent[x];
    while (this.parent[root] !== root) {
      root = this.parent[root];
    }
    while (x !== root) {
      const parent = this.parent[x];
      this.parent[x] = root;
      x = parent;
    }
    return root;
  }

  // Assumes x and y are already in the UnionFind.
  union(x, y) {
    const reprX = this.find(x);
    const reprY = this.find(y);
    if (reprX === reprY) return; // They are already in the same set.

    if (this.size[reprX] < this.size[reprY]) {
      this.size[reprY] += this.size[reprX];
      this.parent[reprX] = reprY;
    } else {
      this.size[reprX] += this.size[reprY];
      this.parent[reprY] = reprX;
    }
  }
}

class CustomUnionFind {
  constructor() {
    this.parent = {};
    this.size = {};
  }

  add(x) {
    this.parent[x] = x;
    this.size[x] = 1;
  }

  find(x) {
    let root = this.parent[x];
    while (this.parent[root] !== root) {
      root = this.parent[root];
    }
    while (x !== root) {
      const parent = this.parent[x];
      this.parent[x] = root;
      x = parent;
    }
    return root;
  }

  union(x, y) {
    const reprX = this.find(x);
    const reprY = this.find(y);
    if (reprX === reprY) {
      return;
    }
    if (this.size[reprX] < this.size[reprY]) {
      this.size[reprY] += this.size[reprX];
      this.parent[reprX] = reprY;
      delete this.size[reprX];
    } else {
      this.size[reprX] += this.size[reprY];
      this.parent[reprY] = reprX;
      delete this.size[reprY];
    }
  }

  size() {
    return Object.keys(this.parent).length;
  }

  numGroups() {
    return Object.keys(this.size).length;
  }
}


function runTests() {
  // Test basic operations
  const uf = new CustomUnionFind();
  uf.add(1);
  uf.add(2);
  uf.add(3);
  if (uf.numGroups() !== 3) {
    throw new Error(`\ngot: ${uf.numGroups()}, want: 3\n`);
  }
  uf.union(1, 2);
  if (uf.numGroups() !== 2) {
    throw new Error(`\ngot: ${uf.numGroups()}, want: 2\n`);
  }
  uf.union(2, 3);
  if (uf.numGroups() !== 1) {
    throw new Error(`\ngot: ${uf.numGroups()}, want: 1\n`);
  }
  uf.add(4);
  uf.add(5);
  if (uf.numGroups() !== 3) {
    throw new Error(`\ngot: ${uf.numGroups()}, want: 3\n`);
  }
  uf.union(4, 5);
  if (uf.numGroups() !== 2) {
    throw new Error(`\ngot: ${uf.numGroups()}, want: 2\n`);
  }

  // Test find after unions
  const uf2 = new CustomUnionFind();
  uf2.add(1);
  uf2.add(2);
  uf2.add(3);
  uf2.union(1, 2);
  uf2.union(2, 3);
  if (uf2.find(1) !== uf2.find(3)) {
    throw new Error(`\nuf.find(1) !== uf.find(3)\n`);
  }

  // Test multiple unions of same elements
  const uf3 = new CustomUnionFind();
  uf3.add(1);
  uf3.add(2);
  uf3.union(1, 2);
  uf3.union(1, 2);
  if (uf3.numGroups() !== 1) {
    throw new Error(`\ngot: ${uf3.numGroups()}, want: 1\n`);
  }
}

runTests();
