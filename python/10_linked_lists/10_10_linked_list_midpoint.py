# 10.10 - Linked-List Midpoint
# Run: python3 10_10_linked_list_midpoint.py

class Node:
  def __init__(self, val):
    self.val = val
    self.next = None

def get_middle_two_pass(head):
  # First pass: count the nodes
  count = 0
  current = head
  while current:
    count += 1
    current = current.next

  # Second pass: stop at half of the count
  middle_index = count // 2
  current = head
  for _ in range(middle_index):
    current = current.next

  return current.val

def get_middle(head):
  slow, fast = head, head
  while fast and fast.next:
    slow = slow.next
    fast = fast.next.next
  return slow.val


def run_tests():

  def array_to_linked_list(arr):
    head = Node(arr[0])
    current = head
    for val in arr[1:]:
      current.next = Node(val)
      current = current.next
    return head

  tests = [
      # Test single node
      ([10], 10),
      # Test two nodes
      ([10, 20], 20),
      # Test odd number of nodes
      ([10, 20, 30], 20),
      # Test even number of nodes
      ([10, 20, 30, 40], 30),
      # Test longer odd list
      ([10, 20, 30, 40, 50], 30),
      # Test longer even list
      ([10, 20, 30, 40, 50, 60], 40),
      # Test with negative values
      ([-10, -20, -30], -20),
      # Test with zeros
      ([0, 0, 0], 0),
  ]
  for i, (input_arr, want) in enumerate(tests):
    # Test the fast/slow pointer solution
    head = array_to_linked_list(input_arr)
    got = get_middle(head)
    assert got == want, f"\nTest {i + 1} (fast/slow): got: {got}, want: {want}\n"

    # Test the two pass solution
    got = get_middle_two_pass(head)
    assert got == want, f"\nTest {i + 1} (two pass): got: {got}, want: {want}\n"

run_tests()
