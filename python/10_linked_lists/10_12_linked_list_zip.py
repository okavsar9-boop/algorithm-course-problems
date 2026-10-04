# 10.12 - Linked-List Zip
# Run: python3 10_12_linked_list_zip.py

class Node:
  def __init__(self, val):
    self.val = val
    self.next = None

def merge(head1, head2):
  dummy = Node(0)
  cur = dummy

  p1, p2 = head1, head2
  while p1 and p2:
    cur.next = p1
    cur = cur.next
    p1 = p1.next

    cur.next = p2
    p2 = p2.next
    cur = cur.next

  if p1:
    cur.next = p1
  else:
    cur.next = p2

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

  tests = [
      # Book examples
      ([1, 3, 5], [2, 4, 6], [1, 2, 3, 4, 5, 6]),
      ([1, 2, 3, 4], [8, 7], [1, 8, 2, 7, 3, 4]),

      # Test empty lists
      ([], [], []),
      # Test one empty list
      ([1, 2], [], [1, 2]),
      ([], [1, 2], [1, 2]),
      # Test equal length lists
      ([1, 3], [2, 4], [1, 2, 3, 4]),
      # Test different length lists
      ([1, 3, 5], [2, 4], [1, 2, 3, 4, 5]),
      ([1, 3], [2, 4, 6], [1, 2, 3, 4, 6]),
      # Test with negative numbers
      ([-1, -3], [-2, -4], [-1, -2, -3, -4]),
      # Test with zeros
      ([0, 0], [0, 0], [0, 0, 0, 0]),
      # Test longer lists
      ([1, 3, 5, 7, 9], [2, 4, 6, 8, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]),
  ]

  for i, (list1, list2, want) in enumerate(tests):
    head1 = array_to_linked_list(list1)
    head2 = array_to_linked_list(list2)
    got = merge(head1, head2)
    got_list = linked_list_to_array(got)
    assert got_list == want, f"\nTest {        i + 1}: merge({list1}, {list2}): got: {got_list}, want: {want}\n"

run_tests()
