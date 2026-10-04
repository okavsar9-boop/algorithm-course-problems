# 10.7 - Sublist Reversal
# Run: python3 10_07_sublist_reversal.py

class Node:
  def __init__(self, val):
    self.val = val
    self.next = None

def node_at_index(head, index):
  """
  - The index is negative.
  """
  if index < 0:
      # Invalid index
    return None

  cur = head
  i = 0

  while cur:
    if i == index:
      return cur
    cur = cur.next
    i += 1

  # If we traverse the whole list and don't find the index
  return None

def reverse_list(head):
  prev = None
  cur = head
  while cur:
    nxt = cur.next
    cur.next = prev
    prev = cur
    cur = nxt
  return prev

def reverse_section(head, left, right):
  dummy = Node(0)
  dummy.next = head

  # Step 1: find the nodes BEFORE and AFTER the section.
  if left == 0:
    prev = dummy
  else:
    prev = node_at_index(head, left - 1)
  if not prev or not prev.next:
    # Nothing to reverse.
    return head
  nxt = node_at_index(head, right + 1)  # May be None.

  # Step 2: break out the section.
  section_head = prev.next
  prev.next = None
  section_tail = section_head
  while section_tail.next != nxt:
    section_tail = section_tail.next
  section_tail.next = None

  # Step 3: reverse section.
  old_section_head = section_head
  new_section_head = reverse_list(section_head)

  # Step 4: reattach the section.
  prev.next = new_section_head
  old_section_head.next = nxt

  return dummy.next


def run_tests():

  def linked_list_to_array(head):
    result = []
    current = head
    while current:
      result.append(current.val)
      current = current.next
    return result

  def array_to_linked_list(arr):
    dummy = Node(0)
    current = dummy
    for val in arr:
      current.next = Node(val)
      current = current.next
    return dummy.next

  # Test cases
  tests = [
      # From book
      ([1, 2, 3, 4, 5], 1, 3, [1, 4, 3, 2, 5]),
      ([1, 2, 3, 4, 5], 2, 7, [1, 2, 5, 4, 3]),
      ([1, 2], 5, 6, [1, 2]),

      # Test empty list
      ([], 0, 1, []),
      # Test single element list
      ([1], 0, 1, [1]),
      # Test reversing entire list
      ([1, 2, 3], 0, 3, [3, 2, 1]),
      # Test reversing sublist with repeated values
      ([1, 1, 1, 2, 2], 1, 3, [1, 2, 1, 1, 2]),
      # Test reversing sublist with negative values
      ([-1, -2, -3, -4], 1, 3, [-1, -4, -3, -2]),
      # Test reversing sublist with zero
      ([0, 1, 2], 0, 1, [1, 0, 2]),
      # Test reversing sublist at the end
      ([1, 2, 3, 4, 5], 2, 4, [1, 2, 5, 4, 3]),
      # Test left beyond list length - should not modify
      ([1, 2, 3], 4, 5, [1, 2, 3]),
      # Test right beyond list length - reverse to end
      ([1, 2, 3], 1, 5, [1, 3, 2]),
  ]

  for i, (arr, left, right, expected) in enumerate(tests):
    head = array_to_linked_list(arr)
    reversed_head = reverse_section(head, left, right)
    got = linked_list_to_array(reversed_head)
    assert got == expected, f"\nTest {i + 1}: got: {got}, want: {expected}\n"

run_tests()
