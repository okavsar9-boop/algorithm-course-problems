# 1.1 - Implement Dynamic Array
# Run: python3 01_01_implement_dynamic_array.py

class DynamicArray:
  def __init__(self):
    self.capacity = 10
    self._size = 0
    self.fixed_array = [None] * self.capacity

  def get(self, i):
    if i < 0 or i >= self._size:
      raise IndexError('Index out of bounds')
    return self.fixed_array[i]

  def set(self, i, x):
    if i < 0 or i >= self._size:
      raise IndexError('Index out of bounds')
    self.fixed_array[i] = x

  def size(self):
    return self._size

  def append(self, x):
    if self._size == self.capacity:
      self.resize(self.capacity * 2)
    self.fixed_array[self._size] = x
    self._size += 1

  def resize(self, new_capacity):
    new_fixed_size_arr = [None] * (new_capacity)
    for i in range(self._size):
      new_fixed_size_arr[i] = self.fixed_array[i]
    self.fixed_array = new_fixed_size_arr
    self.capacity = new_capacity

  def pop_back(self):
    if self._size == 0:
      raise IndexError('Pop from empty array')
    self._size -= 1
    if self._size / self.capacity < 0.25 and self.capacity > 10:
      self.resize(self.capacity//2)


def run_tests():

  def test_get_set():
    d = DynamicArray()
    # Setup array with [0,1,2,3,4]
    for i in range(5):
      d.append(i)

    # Test get
    assert d.get(0) == 0, "get(0) should return 0"
    assert d.get(4) == 4, "get(4) should return 4"

    # Test set
    d.set(0, 10)
    assert d.get(0) == 10, "After set(0,10), get(0) should return 10"

    # Test error cases
    try:
      d.get(-1)
      assert False, "get(-1) should raise IndexError"
    except IndexError:
      pass

    try:
      d.get(5)
      assert False, "get(5) should raise IndexError"
    except IndexError:
      pass

    try:
      d.set(-1, 0)
      assert False, "set(-1,0) should raise IndexError"
    except IndexError:
      pass

    try:
      d.set(5, 0)
      assert False, "set(5,0) should raise IndexError"
    except IndexError:
      pass

  def test_append():
    d = DynamicArray()
    # Test append to empty array
    d.append(1)
    assert d.size() == 1, "Size should be 1 after append"
    assert d.get(0) == 1, "Element at 0 should be 1"

    # Test multiple appends
    d.append(2)
    d.append(3)
    assert d.size() == 3, "Size should be 3 after appends"
    assert d.get(1) == 2, "Element at 1 should be 2"
    assert d.get(2) == 3, "Element at 2 should be 3"

  def test_pop_back():
    d = DynamicArray()
    # Test pop from empty array
    try:
      d.pop_back()
      assert False, "pop_back() on empty array should raise IndexError"
    except IndexError:
      pass

    # Setup array with [1,2,3]
    d.append(1)
    d.append(2)
    d.append(3)

    # Test pop_back
    d.pop_back()
    assert d.size() == 2, "Size should be 2 after pop_back"
    try:
      d.get(2)
      assert False, "get(2) should raise IndexError after pop_back"
    except IndexError:
      pass

  def test_resize():
    d = DynamicArray()
    # Test initial capacity
    assert d.capacity == 10, "Initial capacity should be 10"

    # Test grow capacity
    for i in range(11):
      d.append(i)
    assert d.capacity == 20, "Capacity should double to 20"

    # Test shrink capacity
    for i in range(8):
      d.pop_back()
    assert d.capacity == 10, "Capacity should shrink back to 10"

  tests = [
    lambda: test_get_set(),
    lambda: test_append(),
    lambda: test_pop_back(),
    lambda: test_resize(),
  ]

  for test in tests:
    test()

run_tests()
