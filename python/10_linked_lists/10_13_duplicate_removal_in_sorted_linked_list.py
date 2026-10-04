# 10.13 - Duplicate Removal in Sorted Linked List
# Run: python3 10_13_duplicate_removal_in_sorted_linked_list.py

class Node:
  def __init__(self, val):
    self.val = val
    self.next = None

def remove_duplicates(head):
  cur = head
  while cur and cur.next:
    if cur.val == cur.next.val:
      cur.next = cur.next.next
    else:
      cur = cur.next
  return head


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
      # Book example
      ([1, 1, 1, 3, 5, 5], [1, 3, 5]),

      # Test empty list
      ([], []),
      # Test single node
      ([1], [1]),
      # Test no duplicates
      ([1, 2, 3], [1, 2, 3]),
      # Test all duplicates
      ([1, 1, 1, 1, 1], [1]),
      # Test some duplicates
      ([1, 1, 2, 3, 3], [1, 2, 3]),
      # Test duplicates at start
      ([1, 1, 2, 3], [1, 2, 3]),
      # Test duplicates at end
      ([1, 2, 3, 3], [1, 2, 3]),
      # Test duplicates in middle
      ([1, 2, 2, 3], [1, 2, 3]),
      # Test with negative numbers
      ([-3, -3, -2, -1, -1], [-3, -2, -1]),
      # Test with zeros
      ([0, 0, 0, 1, 1], [0, 1]),
  ]
  for i, (input_arr, want) in enumerate(tests):
    head = array_to_linked_list(input_arr)
    got = remove_duplicates(head)
    got_list = linked_list_to_array(got)
    assert got_list == want, f"\nTest {        i + 1}: remove_duplicates({input_arr}): got: {got_list}, want: {want}\n"

run_tests()
