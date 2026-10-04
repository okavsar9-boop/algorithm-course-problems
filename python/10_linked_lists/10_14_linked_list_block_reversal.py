# 10.14 - Linked List Block Reversal
# Run: python3 10_14_linked_list_block_reversal.py

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

def reverse_k_group(head, k):
  dummy = Node(0)
  dummy.next = head
  group_prev = dummy

  while True:
    # 1. Find the bounds of the current block
    kth = group_prev
    for _ in range(k):
      kth = kth.next
      if not kth:
        return dummy.next
    group_next = kth.next

    # 2. Break the block out from the rest of the list
    kth.next = None
    group_head = group_prev.next

    # 3. Reverse the block
    reversed_head = reverse_list(group_head)

    # 4. Reattach the reversed block
    group_prev.next = reversed_head
    group_head.next = group_next
    group_prev = group_head


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

  tests = [
      # Book examples
      ([1, 2, 3, 4], 2, [2, 1, 4, 3]),
      ([1, 2, 3, 4, 5], 3, [3, 2, 1, 4, 5]),

      ([1, 2, 3, 4, 5, 6], 2, [2, 1, 4, 3, 6, 5]),
      # Test empty list
      ([], 2, []),
      # Test single element list
      ([1], 2, [1]),
      # Test k greater than list length
      ([1, 2, 3], 4, [1, 2, 3]),
      # Test k equal to list length
      ([1, 2, 3], 3, [3, 2, 1]),
      # Test k less than list length
      ([1, 2, 3, 4, 5], 2, [2, 1, 4, 3, 5]),
      # Test k is 1 (no change)
      ([1, 2, 3, 4, 5], 1, [1, 2, 3, 4, 5]),
      # Test list with repeated values
      ([1, 1, 1, 2, 2], 2, [1, 1, 2, 1, 2]),
      # Test list with negative values
      ([-1, -2, -3, -4], 2, [-2, -1, -4, -3]),
      # Test list with zero
      ([0, 1, 2], 2, [1, 0, 2]),
      # Test longer list
      ([1, 2, 3, 4, 5, 6, 7, 8], 3, [3, 2, 1, 6, 5, 4, 7, 8]),
  ]

  for i, (arr, k, want) in enumerate(tests):
    head = array_to_linked_list(arr)
    reversed_head = reverse_k_group(head, k)
    got = linked_list_to_array(reversed_head)
    assert got == want, f"\nTest {i + 1}: got: {got}, want: {want}\n"

run_tests()
