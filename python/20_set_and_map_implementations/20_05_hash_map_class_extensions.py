# 20.5 - Hash Map Class Extensions
# Run: python3 20_05_hash_map_class_extensions.py

class HashMap:
  def __init__(self, h):
    self.h = h
    self.capacity = 10
    self._size = 0
    self.buckets = [[] for _ in range(self.capacity)]

  def size(self):
    return self._size

  def contains(self, k):
    hash = self.h(k, self.capacity)
    for key, _ in self.buckets[hash]:
      if key == k:
        return True
    return False

  def get(self, k):
    hash = self.h(k, self.capacity)
    for key, val in self.buckets[hash]:
      if key == k:
        return val
    return None

  def add(self, k, v):
    hash = self.h(k, self.capacity)
    for i, (key, _) in enumerate(self.buckets[hash]):
      if key == k:
        self.buckets[hash][i] = (k, v)
        return
    self.buckets[hash].append((k, v))
    self._size += 1
    load_factor = self._size / self.capacity
    if load_factor > 1:
      self.resize(self.capacity * 2)

  def remove(self, k):
    hash = self.h(k, self.capacity)
    for i, (key, _) in enumerate(self.buckets[hash]):
      if key == k:
        self.buckets[hash].pop(i)
        self._size -= 1
        load_factor = self._size / self.capacity
        if load_factor < 0.25 and self.capacity > 10:
          self.resize(self.capacity // 2)

  def resize(self, new_capacity):
    new_buckets = [[] for _ in range(new_capacity)]
    for bucket in self.buckets:
      for k, v in bucket:
        hash = self.h(k, new_capacity)
        new_buckets[hash].append((k, v))
    self.buckets = new_buckets
    self.capacity = new_capacity

  def keys(self):
    result = []
    for bucket in self.buckets:
      for k, _ in bucket:
        result.append(k)
    return result

  def values(self):
    result = []
    for bucket in self.buckets:
      for _, v in bucket:
        result.append(v)
    return result


def h_int(x, m):
  C = 0.6180339887  # A "random-looking" fraction between 0 and 1.
  x *= C            # A product with "random-looking" decimals.
  x -= int(x)       # Keep only the fractional part.
  x *= m            # Scale the number in [0, 1) up to [0, m).
  return int(x)     # Return the integer part.

def run_tests():
  # Test basic operations
  m = HashMap(h_int)
  assert m.size() == 0, f"\nsize(): got: {m.size()}, want: 0\n"
  assert not m.contains(1), f"\ncontains(1): got: true, want: false\n"
  assert m.get(1) == None, f"\nget(1): got: not None, want: None\n"

  m.add(1, "one")
  assert m.size() == 1, f"\nsize(): got: {m.size()}, want: 1\n"
  assert m.contains(1), f"\ncontains(1): got: false, want: true\n"
  assert m.get(1) == "one", f"\nget(1): got: {m.get(1)}, want: one\n"

  # Test updating existing key
  m.add(1, "ONE")
  assert m.size() == 1, f"\nsize(): got: {m.size()}, want: 1\n"
  assert m.get(1) == "ONE", f"\nget(1): got: {m.get(1)}, want: ONE\n"

  # Test multiple elements
  m.add(2, "two")
  m.add(3, "three")
  assert m.size() == 3, f"\nsize(): got: {m.size()}, want: 3\n"

  # Test remove
  m.remove(2)
  assert m.size() == 2, f"\nsize(): got: {m.size()}, want: 2\n"
  assert not m.contains(2), f"\ncontains(2): got: true, want: false\n"
  assert m.get(2) == None, f"\nget(2): got: not None, want: None\n"

  # Test resize up
  m2 = HashMap(h_int)
  for i in range(20):
    m2.add(i, str(i))
  assert m2.size() == 20, f"\nsize(): got: {m2.size()}, want: 20\n"

  # Test resize down
  for i in range(15):
    m2.remove(i)
  assert m2.size() == 5, f"\nsize(): got: {m2.size()}, want: 5\n"

  # Test keys() and values()
  m3 = HashMap(h_int)
  m3.add(1, "one")
  m3.add(2, "two")
  m3.add(3, "three")

  keys = m3.keys()
  keys.sort()
  assert keys == [1, 2, 3], f"\nkeys(): got: {keys}, want: [1, 2, 3]\n"

  values = m3.values()
  values.sort()
  assert values == ["one", "three", "two"], \
      f"\nvalues(): got: {values}, want: ['one', 'three', 'two']\n"

run_tests()
