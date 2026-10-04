# 10.4 - Linked-List-Based Queue
# Run: python3 10_04_linked_list_based_queue.py

class Node:
  def __init__(self, val):
    self.val = val
    self.next = None

class LinkedListQueue:
  def __init__(self):
    self.head = None
    self.tail = None
    self._size = 0

  def empty(self):
    return not self.head

  def size(self):
    return self._size

  def push(self, val):
    new = Node(val)
    if self.tail:
      self.tail.next = new
    self.tail = new
    if not self.head:
      self.head = new
    self._size += 1

  def pop(self):
    if self.empty():
      return None
    val = self.head.val
    self.head = self.head.next
    if not self.head:
      self.tail = None
    self._size -= 1
    return val


def run_tests():
  queue = LinkedListQueue()

  # Test size on empty queue
  assert queue.size() == 0, f"\nsize(): got: {queue.size()}, want: 0\n"

  # Test pop on empty queue
  assert queue.pop() is None, "\npop() on empty queue should return None\n"

  # Test push and size
  queue.push(10)
  assert queue.size() == 1, f"\nsize(): got: {queue.size()}, want: 1\n"

  # Test push and pop
  queue.push(20)
  assert queue.pop() == 10, "\npop() should return 10\n"
  assert queue.size() == 1, f"\nsize(): got: {queue.size()}, want: 1\n"

  # Test empty
  assert not queue.empty(), "\nempty() should return False\n"
  queue.pop()
  assert queue.empty(), "\nempty() should return True\n"

run_tests()
