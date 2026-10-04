# 10.9 - Doubly Linked List To Array
# Run: python3 10_09_doubly_linked_list_to_array.py

class Node:
  def __init__(self, val):
    self.val = val
    self.next = None
    self.prev = None

def convert_to_array(node):
  cur = node
  while cur.prev:
    cur = cur.prev
  res = []
  while cur:
    res.append(cur.val)
    cur = cur.next
  return res


def run_tests():

  def create_doubly_linked_list(arr):
    head = Node(arr[0])
    cur = head
    for val in arr[1:]:
      new_node = Node(val)
      cur.next = new_node
      new_node.prev = cur
      cur = new_node
    return head

  def node_at_index(head, index):
    cur = head
    for _ in range(index):
      cur = cur.next
    return cur

  tests = [
      # Examples from the book
      ([1, 2, 3, 4], 2),
      ([1, 2, 3, 4], 0),

      ([1, 2, 3, 4, 5], 0),
      ([1, 2, 3, 4, 5], 1),
      ([1, 2, 3, 4, 5], 2),
      ([1, 2, 3, 4, 5], 3),
      ([1, 2, 3, 4, 5], 4),
      # Test single node
      ([1], 0),
  ]

  for i, (arr, index) in enumerate(tests):
    head = create_doubly_linked_list(arr)
    node = node_at_index(head, index)
    got = convert_to_array(node)
    assert got == arr, f"\nTest {i + 1}: got: {got}, want: {arr}\n"

run_tests()
