# 13.1 - Implement a Heap
# Run: python3 13_01_implement_a_heap.py

class Heap:
  # A binary heap implementation that can act as either min-heap or max-heap.
  # By default, it creates a min-heap (the smallest element has highest priority).
  # For max-heap behavior, provide a custom 'higher_priority' function.
  # higher_priority: Function that returns True if x has higher priority than y.
  # heap:            Optional list of initial elements to heapify.
  def __init__(self, higher_priority=lambda x, y: x < y, heap=None):
    self.higher_priority = higher_priority
    self.heap = []
    if heap:
      self.heap = heap.copy()
      self.heapify()

  # Returns the number of elements in the heap
  def size(self):
    return len(self.heap)

  # Returns the highest priority element without removing it
  def top(self):
    if not self.heap:
      return None
    return self.heap[0]

  # Adds an element to the heap
  def push(self, elem):
    self.heap.append(elem)
    self._bubble_up(len(self.heap) - 1)

  # Removes and returns the highest priority element
  def pop(self):
    if not self.heap:
      return None

    top = self.heap[0]
    if len(self.heap) == 1:
      self.heap = []
      return top

    # Move last element to root and bubble down
    self.heap[0] = self.heap[-1]
    self.heap.pop()
    self._bubble_down(0)

    return top

  # Converts a list into a valid heap in O(n) time
  def heapify(self):
    for idx in range(len(self.heap) // 2, -1, -1):
      self._bubble_down(idx)

  # Get parent index
  def _parent(self, idx):
    if idx == 0:
      return -1  # The root has no parent
    return (idx - 1) // 2

  # Get left child index
  def _left_child(self, idx):
    return 2 * idx + 1

  # Get right child index
  def _right_child(self, idx):
    return 2 * idx + 2

  # Move element up until heap property is restored
  def _bubble_up(self, idx):
    if idx == 0:
      return

    parent_idx = self._parent(idx)
    if parent_idx >= 0 and self.higher_priority(self.heap[idx], self.heap[parent_idx]):
      self.heap[idx], self.heap[parent_idx] = self.heap[parent_idx], self.heap[idx]
      self._bubble_up(parent_idx)

  # Move element down until heap property is restored
  def _bubble_down(self, idx):
    left_idx = self._left_child(idx)
    is_leaf = left_idx >= len(self.heap)
    if is_leaf:
      return

    # Find child with higher priority
    child_idx = left_idx
    right_idx = self._right_child(idx)
    if (right_idx < len(self.heap) and
            self.higher_priority(self.heap[right_idx], self.heap[left_idx])):
      child_idx = right_idx

    # Swap with child if it has higher priority
    if self.higher_priority(self.heap[child_idx], self.heap[idx]):
      self.heap[idx], self.heap[child_idx] = self.heap[child_idx], self.heap[idx]
      self._bubble_down(child_idx)


def run_tests():
  """Test heap implementation"""
  # Test min heap
  min_heap = Heap()
  values = [4, 8, 2, 6, 1, 7, 3, 5]
  for val in values:
    min_heap.push(val)

  # Should pop in ascending order
  sorted_values = []
  while min_heap.size() > 0:
    sorted_values.append(min_heap.pop())
  assert sorted_values == [1, 2, 3, 4, 5, 6, 7, 8]

  # Test max heap
  max_heap = Heap(higher_priority=lambda x, y: x > y)
  for val in values:
    max_heap.push(val)
  # Should pop in descending order
  sorted_values = []
  while max_heap.size() > 0:
    sorted_values.append(max_heap.pop())
  got = sorted_values
  want = [8, 7, 6, 5, 4, 3, 2, 1]
  assert got == want, f"\nmax_heap popped values: got: {got}, want: {want}\n"

  # Test heapify
  heap = Heap(heap=[4, 8, 2, 6, 1, 7, 3, 5])
  assert heap.pop() == 1
  assert heap.pop() == 2
  assert heap.pop() == 3

run_tests()
