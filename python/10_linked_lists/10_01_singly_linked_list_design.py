# 10.1 - Singly Linked List Design
# Run: python3 10_01_singly_linked_list_design.py

class Node:
  def __init__(self, val):
    self.val = val
    self.next = None

class SinglyLinkedList:
  def __init__(self):
    self.head = None
    self._size = 0

  def size(self):
    return self._size

  def push_front(self, val):
    new = Node(val)
    new.next = self.head
    self.head = new
    self._size += 1

  def pop_front(self):
    if not self.head:
      return None
    val = self.head.val
    self.head = self.head.next
    self._size -= 1
    return val

  def push_back(self, val):
    new = Node(val)
    self._size += 1
    if not self.head:
      self.head = new
      return
    cur = self.head
    while cur.next:
      cur = cur.next
    cur.next = new

  def pop_back(self):
    if not self.head:
      return None
    self._size -= 1
    if not self.head.next:
      val = self.head.val
      self.head = None
      return val
    cur = self.head
    while cur.next and cur.next.next:
      cur = cur.next
    val = cur.next.val
    cur.next = None
    return val

  def contains(self, val):
    cur = self.head
    while cur:
      if cur.val == val:
        return cur
      cur = cur.next
    return None


def run_tests():
  sll = SinglyLinkedList()

  # Test size on empty list
  assert sll.size() == 0, f"\nsize(): got: {sll.size()}, want: 0\n"

  # Test pop_front on empty list
  assert sll.pop_front() is None, "\npop_front() on empty list should return None\n"

  # Test pop_back on empty list
  assert sll.pop_back() is None, "\npop_back() on empty list should return None\n"

  # Test push_front and size
  sll.push_front(10)
  assert sll.size() == 1, f"\nsize(): got: {sll.size()}, want: 1\n"

  # Test push_back and size
  sll.push_back(20)
  assert sll.size() == 2, f"\nsize(): got: {sll.size()}, want: 2\n"

  # Test contains
  assert sll.contains(10) is not None, "\ncontains(10) should find the node\n"
  assert sll.contains(30) is None, "\ncontains(30) should not find the node\n"

  # Test pop_front
  assert sll.pop_front() == 10, "\npop_front() should return 10\n"
  assert sll.size() == 1, f"\nsize(): got: {sll.size()}, want: 1\n"

  # Test pop_back
  assert sll.pop_back() == 20, "\npop_back() should return 20\n"
  assert sll.size() == 0, f"\nsize(): got: {sll.size()}, want: 0\n"

  # Test push_back and pop_back
  sll.push_back(30)
  assert sll.pop_back() == 30, "\npop_back() should return 30\n"

  # Test push_front and pop_front
  sll.push_front(40)
  assert sll.pop_front() == 40, "\npop_front() should return 40\n"

run_tests()
