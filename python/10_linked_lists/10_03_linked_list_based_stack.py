# 10.3 - Linked-List-Based Stack
# Run: python3 10_03_linked_list_based_stack.py

class Node:
  def __init__(self, val):
    self.val = val
    self.next = None

class LinkedListStack:
  def __init__(self):
    self.head = None
    self._size = 0

  def push(self, val):
    new = Node(val)
    new.next = self.head
    self.head = new
    self._size += 1

  def pop(self):
    if not self.head:
      return None
    val = self.head.val
    self.head = self.head.next
    self._size -= 1
    return val

  def peek(self):
    if not self.head:
      return None
    return self.head.val

  def size(self):
    return self._size

  def empty(self):
    return self._size == 0


def run_tests():
  stack = LinkedListStack()

  # Test size on empty stack
  assert stack.size() == 0, f"\nsize(): got: {stack.size()}, want: 0\n"

  # Test pop on empty stack
  assert stack.pop() is None, "\npop() on empty stack should return None\n"

  # Test peek on empty stack
  assert stack.peek() is None, "\npeek() on empty stack should return None\n"

  # Test push and size
  stack.push(10)
  assert stack.size() == 1, f"\nsize(): got: {stack.size()}, want: 1\n"

  # Test peek
  assert stack.peek() == 10, "\npeek() should return 10\n"

  # Test push and pop
  stack.push(20)
  assert stack.pop() == 20, "\npop() should return 20\n"
  assert stack.size() == 1, f"\nsize(): got: {stack.size()}, want: 1\n"

  # Test empty
  assert not stack.empty(), "\nempty() should return False\n"
  stack.pop()
  assert stack.empty(), "\nempty() should return True\n"

run_tests()
