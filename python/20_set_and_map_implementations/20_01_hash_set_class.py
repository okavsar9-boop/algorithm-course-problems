# 20.1 - Hash Set Class
# Run: python3 20_01_hash_set_class.py

class HashSet:
  def __init__(self, h):
    self.h = h
    self.capacity = 10
    self._size = 0
    self.buckets = [[] for _ in range(self.capacity)]

  def size(self):
    return self._size

  def contains(self, x):
    hash = self.h(x, self.capacity)
    for elem in self.buckets[hash]:
      if elem == x:
        return True
    return False

  def add(self, x):
    hash = self.h(x, self.capacity)
    for elem in self.buckets[hash]:
      if elem == x:
        return
    self.buckets[hash].append(x)
    self._size += 1
    load_factor = self._size / self.capacity
    if load_factor > 1:
      self.resize(self.capacity * 2)

  def resize(self, new_capacity):
    new_buckets = [[] for _ in range(new_capacity)]
    for bucket in self.buckets:
      for elem in bucket:
        hash = self.h(elem, new_capacity)
        new_buckets[hash].append(elem)
    self.buckets = new_buckets
    self.capacity = new_capacity

  def remove(self, x):
    hash = self.h(x, self.capacity)
    for i, elem in enumerate(self.buckets[hash]):
      if elem == x:
        self.buckets[hash].pop(i)
        self._size -= 1
        load_factor = self._size / self.capacity
        if load_factor < 0.25 and self.capacity > 10:
          self.resize(self.capacity // 2)


def h_int(x, m):
  C = 0.6180339887  # A "random-looking" fraction between 0 and 1.
  x *= C            # A product with "random-looking" decimals.
  x -= int(x)       # Keep only the fractional part.
  x *= m            # Scale the number in [0, 1) up to [0, m).
  return int(x)     # Return the integer part.

def run_tests():
  # Test basic operations
  s = HashSet(h_int)
  assert s.size() == 0, f"\nsize(): got: {s.size()}, want: 0\n"
  assert not s.contains(1), f"\ncontains(1): got: true, want: false\n"

  s.add(1)
  assert s.size() == 1, f"\nsize(): got: {s.size()}, want: 1\n"
  assert s.contains(1), f"\ncontains(1): got: false, want: true\n"

  s.add(2)
  s.add(3)
  assert s.size() == 3, f"\nsize(): got: {s.size()}, want: 3\n"

  s.remove(2)
  assert s.size() == 2, f"\nsize(): got: {s.size()}, want: 2\n"
  assert not s.contains(2), f"\ncontains(2): got: true, want: false\n"

  # Test resize
  s3 = HashSet(h_int)
  for i in range(20):
    s3.add(i)
  assert s3.size() == 20, f"\nsize(): got: {s3.size()}, want: 20\n"

  # Remove elements to test downsize
  for i in range(15):
    s3.remove(i)
  assert s3.size() == 5, f"\nsize(): got: {s3.size()}, want: 5\n"

run_tests()
