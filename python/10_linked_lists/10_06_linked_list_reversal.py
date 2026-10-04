# 10.6 - Linked-List Reversal
# Run: python3 10_06_linked_list_reversal.py

class Node:
  def __init__(self, val):
    self.val = val
    self.next = None

def reverse_list(head):
  prev = None
  cur = head
  while cur:
    nxt = cur.next
    cur.next = prev
    prev = cur
    cur = nxt
  return prev


def run_tests():

  def linked_list_to_array(head):
    result = []
    current = head
    while current:
      result.append(current.val)
      current = current.next
    return result

  def array_to_linked_list(arr):
    dummy_head = Node(0)
    current = dummy_head
    for val in arr:
      current.next = Node(val)
      current = current.next
    return dummy_head.next

  # Test cases
  tests = [
      # Test empty list
      ([], []),
      # Test single element list
      ([1], [1]),
      # Test multiple elements list
      ([1, 2, 3], [3, 2, 1]),
      # Test list with repeated values
      ([1, 1, 1], [1, 1, 1]),
      # Test list with negative values
      ([-1, -2, -3], [-3, -2, -1]),
      # Test list with zero
      ([0], [0]),
      # Test longer list
      ([1, 2, 3, 4, 5], [5, 4, 3, 2, 1]),
      # Test list with mixed values
      ([-1, 0, 1], [1, 0, -1]),
  ]

  for i, (arr, expected) in enumerate(tests):
    head = array_to_linked_list(arr)
    reversed_head = reverse_list(head)
    got = linked_list_to_array(reversed_head)
    assert got == expected, f"\nTest {i + 1}: got: {got}, want: {expected}\n"

run_tests()
