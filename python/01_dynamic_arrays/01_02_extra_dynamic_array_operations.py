# 1.2 - Extra Dynamic Array Operations
# Run: python3 01_02_extra_dynamic_array_operations.py

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

class DynamicArrayExtras(DynamicArray):
  def pop(self, i):
    if i < 0 or i >= self._size:
      raise IndexError('Index out of bounds')

    x = self.fixed_array[i]

    for j in range(i, self._size - 1):
      self.fixed_array[j] = self.fixed_array[j + 1]

    self._size -= 1
    if self._size / self.capacity < 0.25 and self.capacity > 10:
      self.resize(self.capacity // 2)
    return x

  def contains(self, x):
    for i in range(self._size):
      if self.fixed_array[i] == x:
        return True
    return False

  def insert(self, i, x):
    if i < 0 or i > self._size:
      raise IndexError('Index out of bounds')

    if self._size == self.capacity:
      self.resize(self.capacity * 2)

    for j in range(self._size - 1, i - 1, -1):
      self.fixed_array[j + 1] = self.fixed_array[j]

    self.fixed_array[i] = x
    self._size += 1

  def remove(self, x):
    for i in range(self._size):
      if self.fixed_array[i] == x:
        self.pop(i)
        return i
    return -1


def run_tests():
  def test_pop():
    d = DynamicArrayExtras()
    # Setup array with [0,1,2,3,4]
    for i in range(5):
      d.append(i)

    # Test pop from middle
    assert d.pop(2) == 2, "pop(2) should return 2"
    assert d.size() == 4, "Size should be 4 after pop"
    assert d.get(2) == 3, "Element at 2 should now be 3"

    # Test pop from start
    assert d.pop(0) == 0, "pop(0) should return 0"
    assert d.size() == 3, "Size should be 3 after pop"
    assert d.get(0) == 1, "Element at 0 should now be 1"

    # Test pop from end
    assert d.pop(2) == 4, "pop(2) should return 4"
    assert d.size() == 2, "Size should be 2 after pop"

    # Test error cases
    try:
      d.pop(-1)
      assert False, "pop(-1) should raise IndexError"
    except IndexError:
      pass

    try:
      d.pop(2)
      assert False, "pop(2) should raise IndexError"
    except IndexError:
      pass

  def test_contains():
    d = DynamicArrayExtras()
    # Test empty array
    assert not d.contains(1), "Empty array should not contain 1"

    # Setup array with [1,2,3]
    d.append(1)
    d.append(2)
    d.append(3)

    # Test positive cases
    assert d.contains(1), "Array should contain 1"
    assert d.contains(2), "Array should contain 2"
    assert d.contains(3), "Array should contain 3"

    # Test negative cases
    assert not d.contains(0), "Array should not contain 0"
    assert not d.contains(4), "Array should not contain 4"

  def test_insert():
    d = DynamicArrayExtras()
    # Test insert into empty array
    d.insert(0, 1)
    assert d.size() == 1, "Size should be 1 after insert"
    assert d.get(0) == 1, "Element at 0 should be 1"

    # Test insert at start
    d.insert(0, 0)
    assert d.size() == 2, "Size should be 2 after insert"
    assert d.get(0) == 0, "Element at 0 should be 0"
    assert d.get(1) == 1, "Element at 1 should be 1"

    # Test insert at end
    d.insert(2, 2)
    assert d.size() == 3, "Size should be 3 after insert"
    assert d.get(2) == 2, "Element at 2 should be 2"

    # Test insert in middle
    d.insert(1, 3)
    assert d.size() == 4, "Size should be 4 after insert"
    assert d.get(1) == 3, "Element at 1 should be 3"

    # Test error cases
    try:
      d.insert(-1, 0)
      assert False, "insert(-1, 0) should raise IndexError"
    except IndexError:
      pass

    try:
      d.insert(5, 0)
      assert False, "insert(5, 0) should raise IndexError"
    except IndexError:
      pass

  def test_remove():
    d = DynamicArrayExtras()
    # Test remove from empty array
    assert d.remove(1) == -1, "Remove from empty array should return -1"

    # Setup array with [1,2,2,3]
    d.append(1)
    d.append(2)
    d.append(2)
    d.append(3)

    # Test successful removes
    assert d.remove(1) == 0, "Remove should return index 0"
    assert d.size() == 3, "Size should be 3 after remove"
    assert d.get(0) == 2, "Element at 0 should be 2"

    assert d.remove(2) == 0, "Remove should return index 0"
    assert d.size() == 2, "Size should be 2 after remove"
    assert d.get(0) == 2, "Element at 0 should be 2"

    # Test remove non-existent element
    assert d.remove(4) == -1, "Remove non-existent should return -1"

  test_pop()
  test_contains()
  test_insert()
  test_remove()

run_tests()
