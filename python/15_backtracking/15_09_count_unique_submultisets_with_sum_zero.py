# 15.9 - Count Unique Submultisets with Sum Zero
# Run: python3 15_09_count_unique_submultisets_with_sum_zero.py

from collections import Counter

def count_unique_submultisets_with_sum_zero(S):

  def visit(index, current_sum):
    if index == len(unique_elements):
      return 1 if current_sum == 0 else 0

    element = unique_elements[index]
    count = frequency[element]
    total_count = 0

    # Try all possible counts of the current element
    for i in range(count + 1):
      total_count += visit(index + 1, current_sum + i * element)

    return total_count

  frequency = Counter(S)
  unique_elements = list(frequency.keys())
  return visit(0, 0)


def run_tests():
  tests = [
      # Example 1 from the book
      ([1, 1, -1, -1], 3),
      # Example 2 from the book
      ([], 1),
      # Example 3 from the book
      ([-1, 2, 1, 0, 3], 4),
      # Edge case - no zero-sum submultisets
      ([1, 2, 3], 1),
      # Edge case - all zeros
      ([0, 0, 0], 4),
  ]
  for S, want in tests:
    got = count_unique_submultisets_with_sum_zero(S)
    assert got == want, f"\ncount_unique_submultisets_with_sum_zero({S}): got: {        got}, want: {want}\n"

run_tests()
