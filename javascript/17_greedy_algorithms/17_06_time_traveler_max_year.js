// 17.6 - Time Traveler Max Year
// Run: node 17_06_time_traveler_max_year.js

function canReachYear(jumpingPoints, k, maxAging, yearIdx) {
  // Easier Version of the problem:
  // Given a year = jumpingPoints[yearIdx], is it possible to reach it?
  const gaps = [];
  for (let i = 0; i < yearIdx; i++) {
    gaps.push(jumpingPoints[i + 1] - jumpingPoints[i]);
  }

  // Sort gaps by size (descending)
  gaps.sort((a, b) => b - a);

  const totalAging =
    gaps.length > k ? gaps.slice(k).reduce((a, b) => a + b, 0) : 0;
  return totalAging <= maxAging;
}
function latestReachableYearBinarySearch(jumpingPoints, k, maxAging) {
  const n = jumpingPoints.length;

  function gapSize(yearIdx) {
    // Gap from jumpingPoints[yearIdx] to jumpingPoints[yearIdx + 1]
    return jumpingPoints[yearIdx + 1] - jumpingPoints[yearIdx];
  }

  // Sort gap indices (0 to n-2) by size (descending)
  const sortedGaps = Array.from({ length: n - 1 }, (_, i) => i).sort(
    (a, b) => gapSize(b) - gapSize(a),
  );

  function yearReached(gapIndicesToSkip) {
    // Year we reach if we skip the gaps in gapIndicesToSkip
    // (assuming we can reach them).
    return (
      jumpingPoints[0] +
      maxAging +
      gapIndicesToSkip.reduce((sum, i) => sum + gapSize(i), 0)
    );
  }

  // Returns whether we can reach the end of gap idx.
  // Takes O(n) time by leveraging the sortedGaps array.
  function canReachYearLinear(yearIdx) {
    let totalAging = 0;
    let jumpsUsed = 0;
    for (const idx of sortedGaps) {
      if (idx >= yearIdx) {
        continue;
      }
      if (jumpsUsed < k) {
        jumpsUsed++;
      } else {
        totalAging += gapSize(idx);
        if (totalAging > maxAging) {
          return false;
        }
      }
    }
    return true;
  }

  // Binary search over year indices (0 to n-1).
  // Goal: find the transition point from the last year we can reach to the
  // first year we can't (if any).
  // Before region: we can reach the end of year yearIdx.
  // After region: we can't reach the end of year yearIdx.
  function isBefore(yearIdx) {
    return canReachYearLinear(yearIdx);
  }

  let l = 0;
  let r = n - 1;
  if (isBefore(r)) {
    // Edge case: we can reach the last year.
    // We skip the k largest gaps and age naturally through the rest.
    return yearReached(sortedGaps.slice(0, k));
  }

  // Now we can binary search until l and r are next to each other.
  // O(n log n) time.
  while (r - l > 1) {
    const mid = Math.floor((l + r) / 2);
    if (isBefore(mid)) {
      l = mid;
    } else {
      r = mid;
    }
  }

  // Now we know we can reach year l, but not year l+1.
  // Thus, we should skip the largest k gaps up to l.
  const sortedGapsUpToL = Array.from({ length: l }, (_, i) => i).sort(
    (a, b) => gapSize(b) - gapSize(a),
  );
  return yearReached(sortedGapsUpToL.slice(0, k));
}

"can I get here with the available jumps?"

jumping_points = [1, 3, 6, 7, 11, 16, 17, 19], k = 2, max_aging = 4

function latestReachableYearGreedy(jumpingPoints, k, maxAging) {
  const gaps = [];
  for (let i = 1; i < jumpingPoints.length; i++) {
    gaps.push(jumpingPoints[i] - jumpingPoints[i - 1]);
  }

  const minHeap = new Heap();
  let totalGapSum = 0;
  let sumHeap = 0;
  for (let i = 0; i < gaps.length; i++) {
    const aged = totalGapSum - sumHeap;
    minHeap.push(gaps[i]);
    sumHeap += gaps[i];
    totalGapSum += gaps[i];
    if (minHeap.size() > k) {
      const smallestJump = minHeap.pop();
      sumHeap -= smallestJump;
    }
    const newAged = totalGapSum - sumHeap;
    if (newAged > maxAging) {
      // We can't reach the end of gap i.
      // We get to jumpingPoints[i] and age naturally from there.
      const remainingAging = maxAging - aged;
      return jumpingPoints[i] + remainingAging;
    }
  }

  // Reached the last jumping point
  const aged = totalGapSum - sumHeap;
  const remainingAging = maxAging - aged;
  return jumpingPoints[jumpingPoints.length - 1] + remainingAging;
}

class Heap {
  // A binary heap implementation that can act as either min-heap or max-heap.
  // By default, it creates a min-heap (the smallest element has highest priority).
  // For max-heap behavior, provide a custom 'higherPriority' function.
  // higherPriority: Function that returns True if x has higher priority than y.
  // heap:           Optional list of initial elements to heapify.
  constructor(higherPriority = (x, y) => x < y, heap = null) {
    this.higherPriority = higherPriority;
    this.heap = [];
    if (heap) {
      this.heap = [...heap];
      this.heapify();
    }
  }

  // Returns the number of elements in the heap.
  size() {
    return this.heap.length;
  }

  // Returns the highest priority element without removing it.
  top() {
    if (this.heap.length === 0) {
      return null;
    }
    return this.heap[0];
  }

  // Adds an element to the heap.
  push(elem) {
    this.heap.push(elem);
    this._bubbleUp(this.heap.length - 1);
  }

  // Removes and returns the highest priority element.
  pop() {
    if (this.heap.length === 0) {
      return null;
    }

    const top = this.heap[0];
    if (this.heap.length === 1) {
      this.heap = [];
      return top;
    }

    // Move last element to root and bubble down
    this.heap[0] = this.heap[this.heap.length - 1];
    this.heap.pop();
    this._bubbleDown(0);

    return top;
  }

  // Converts an array into a valid heap in O(n) time.
  heapify() {
    for (let idx = Math.floor(this.heap.length / 2); idx >= 0; idx--) {
      this._bubbleDown(idx);
    }
  }

  // Get parent index.
  _parent(idx) {
    if (idx === 0) {
      return -1; // The root has no parent.
    }
    return Math.floor((idx - 1) / 2);
  }

  // Get left child index.
  _leftChild(idx) {
    return 2 * idx + 1;
  }

  // Get right child index.
  _rightChild(idx) {
    return 2 * idx + 2;
  }

  // Move element up until heap property is restored.
  _bubbleUp(idx) {
    if (idx === 0) {
      return;
    }

    const parentIdx = this._parent(idx);
    if (
      parentIdx >= 0 &&
      this.higherPriority(this.heap[idx], this.heap[parentIdx])
    ) {
      [this.heap[idx], this.heap[parentIdx]] = [
        this.heap[parentIdx],
        this.heap[idx],
      ];
      this._bubbleUp(parentIdx);
    }
  }

  // Move element down until heap property is restored.
  _bubbleDown(idx) {
    const leftIdx = this._leftChild(idx);
    const isLeaf = leftIdx >= this.heap.length;
    if (isLeaf) {
      return;
    }

    // Find child with higher priority
    let childIdx = leftIdx;
    const rightIdx = this._rightChild(idx);
    if (
      rightIdx < this.heap.length &&
      this.higherPriority(this.heap[rightIdx], this.heap[leftIdx])
    ) {
      childIdx = rightIdx;
    }

    // Swap with child if it has higher priority
    if (this.higherPriority(this.heap[childIdx], this.heap[idx])) {
      [this.heap[idx], this.heap[childIdx]] = [
        this.heap[childIdx],
        this.heap[idx],
      ];
      this._bubbleDown(childIdx);
    }
  }
}


function runTests() {
  const tests = [
    // Example 1
    [[2020, 2024], 0, 2, 2022],
    // Example 2
    [[2020, 2024], 1, 1, 2025],
    // Example 3
    [[1803, 1861, 1863, 1865, 1920, 1929, 1941, 1964, 2001, 2021], 4, 45, 2021],
    // Example 4
    [[1, 10, 30], 1, 5, 15],
    // Example 5
    [[1, 3, 6, 7, 11, 16, 17, 19], 2, 4, 12],

    [[1, 5, 10], 1, 2, 7],
    [[1, 3, 10, 20], 1, 3, 11],
    [[1, 4, 15], 1, 4, 16],

    // Additional test cases
    // Edge case: No jumps allowed, but within aging limit
    [[2000, 2001, 2002], 0, 2, 2002],
    // Edge case: No jumps allowed, exceeding aging limit
    [[2000, 2005, 2010], 0, 4, 2004],
  ];

  for (const [jumpingPoints, k, maxAging, want] of tests) {
    let got = latestReachableYearBinarySearch(jumpingPoints, k, maxAging);
    if (got !== want) {
      throw new Error(
        `\nlatestReachableYearBinarySearch(${JSON.stringify(jumpingPoints)}, ${k}, ${maxAging}): got: ${got}, want: ${want}\n`,
      );
    }

    got = latestReachableYearGreedy(jumpingPoints, k, maxAging);
    if (got !== want) {
      throw new Error(
        `\nlatestReachableYear(${JSON.stringify(jumpingPoints)}, ${k}, ${maxAging}): got: ${got}, want: ${want}\n`,
      );
    }
  }
}

runTests();
