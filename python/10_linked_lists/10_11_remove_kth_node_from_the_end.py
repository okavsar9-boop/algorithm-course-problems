# 10.11 - Remove Kth Node From the End
# Run: python3 10_11_remove_kth_node_from_the_end.py

class Node:
  def __init__(self, val):
    self.val = val
    self.next = None

def remove_kth_node_two_pass(head, k):
  # First pass: compute the length of the list
  n = 0
  current = head
  while current:
    n += 1
    current = current.next

  # Second pass: walk n-k steps from the head and remove the element
  if k == n:
    return head.next  # Remove the first element

  current = head
  for _ in range(n - k - 1):
    current = current.next

  current.next = current.next.next
  return head

def remove_kth_node(head, k):
  dummy = Node(0)
  dummy.next = head
  fast = dummy
  slow = dummy

  for _ in range(k):
    fast = fast.next

  while fast and fast.next:
    fast = fast.next
    slow = slow.next

  slow.next = slow.next.next
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
    if not arr:
      return None
    head = Node(arr[0])
    current = head
    for val in arr[1:]:
      current.next = Node(val)
      current = current.next
    return head

  tests = [
      # Test single element list
      ([1], 1, []),
      # Test removing first element (k = length)
      ([1, 2, 3], 3, [2, 3]),
      # Test removing last element (k = 1)
      ([1, 2, 3], 1, [1, 2]),
      # Test removing middle element
      ([1, 2, 3], 2, [1, 3]),
      # Test longer list removing first
      ([1, 2, 3, 4, 5], 5, [2, 3, 4, 5]),
      # Test longer list removing last
      ([1, 2, 3, 4, 5], 1, [1, 2, 3, 4]),
      # Test longer list removing middle
      ([1, 2, 3, 4, 5], 3, [1, 2, 4, 5]),
      # Test with repeated values
      ([1, 1, 1], 2, [1, 1]),
      # Test with negative values
      ([-1, -2, -3], 2, [-1, -3]),
  ]

  for i, (arr, k, want) in enumerate(tests):
    # Test the fast/slow pointer solution
    result = remove_kth_node(array_to_linked_list(arr), k)
    got = linked_list_to_array(result)
    assert got == want, f"\nTest {        i + 1} (fast/slow): remove_kth_node({arr}, {k}): got: {got}, want: {want}\n"

    # Test the two pass solution
    result = remove_kth_node_two_pass(array_to_linked_list(arr), k)
    got = linked_list_to_array(result)
    assert got == want, f"\nTest {        i + 1} (two pass): remove_kth_node_two_pass({arr}, {k}): got: {got}, want: {want}\n"

run_tests()
