# 10.5 - Linked-List Copy
# Run: python3 10_05_linked_list_copy.py

class Node:
  def __init__(self, val):
    self.val = val
    self.next = None

def copy_list(head):
  if not head:
    return None
  new_head = Node(head.val)
  cur_new = new_head
  cur_old = head.next
  while cur_old:
    cur_new.next = Node(cur_old.val)
    cur_new = cur_new.next
    cur_old = cur_old.next
  return new_head

def copy_list_with_dummy(head):
  dummy = Node(0)  # New list's dummy head
  cur_new = dummy
  cur_old = head
  while cur_old:
    cur_new.next = Node(cur_old.val)
    cur_new = cur_new.next
    cur_old = cur_old.next
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
      # Test empty list
      [],
      # Test single element list
      [1],
      # Test multiple elements list
      [1, 2, 3],
      # Test list with repeated values
      [1, 1, 1],
      # Test list with negative values
      [-1, -2, -3],
      # Test list with zero
      [0],
      # Test longer list
      [1, 2, 3, 4, 5],
      # Test list with mixed values
      [-1, 0, 1],
  ]

  for i, arr in enumerate(tests):
    head = array_to_linked_list(arr)

    # Test first copy_list function
    copied_head_1 = copy_list(head)
    got_1 = linked_list_to_array(copied_head_1)
    assert got_1 == arr, f"\nTest {        i + 1} (copy_list 1): got: {got_1}, want: {arr}\n"

    # Test second copy_list function
    copied_head_2 = copy_list_with_dummy(head)
    got_2 = linked_list_to_array(copied_head_2)
    assert got_2 == arr, f"\nTest {        i + 1} (copy_list 2): got: {got_2}, want: {arr}\n"

run_tests()
