# 6.11 - Largest Set Intersection
# Run: python3 06_11_largest_set_intersection.py

def largest_set_intersection_frequency_map(sets):
  if len(sets) == 1:
    return 0

  # Create frequency map from integers to number of sets they appear in
  freq = {}
  for s in sets:
    for x in s:
      if x not in freq:
        freq[x] = 0
      freq[x] += 1

  # For each set, count elements that appear k-1 times
  k = len(sets)
  best_index = 0
  min_count = float('inf')
  for i, s in enumerate(sets):
    count = sum(1 for x in s if freq[x] == k - 1)
    if count < min_count:
      min_count = count
      best_index = i

  return best_index

def largest_set_intersection_prefix_sum(sets):
  n = len(sets)
  if n == 1:
    return 0

  hash_sets = [set(s) for s in sets]

  # Compute prefix intersections
  prefix_intersections = [None] * n
  prefix_intersections[0] = hash_sets[0]
  for i in range(1, n):
    prefix_intersections[i] = prefix_intersections[i - 1] & hash_sets[i]

  # Compute suffix intersections
  suffix_intersections = [None] * n
  suffix_intersections[n - 1] = hash_sets[n - 1]
  for i in range(n - 2, -1, -1):
    suffix_intersections[i] = suffix_intersections[i + 1] & hash_sets[i]

  # Find the best index to exclude
  best_index = 0
  max_size = 0

  for i in range(n):
    # Compute intersection excluding sets[i]
    if i == 0:
      intersection = suffix_intersections[1]
    elif i == n - 1:
      intersection = prefix_intersections[n - 2]
    else:
      intersection = prefix_intersections[i - 1] & suffix_intersections[i + 1]

    if len(intersection) > max_size:
      max_size = len(intersection)
      best_index = i

  return best_index


def run_tests():
  tests = [
      # Example 1 
      ([[1, 2, 3], [3, 2, 1], [1, 4, 5], [1, 2]], 2),
      # Example 2 
      ([[1, 2], [3, 4], [5, 6]], 0),
      # Example 3 
      ([[1, 2, 3], [4, 5]], 1),
      # Example 4 
      ([[1, 2, 3]], 0),
      # Additional test cases
      ([[1], [1]], 0),
      ([[1, 2], [2, 3], [1, 3]], 0),
  ]
  for sets, want in tests:
    got_freq = largest_set_intersection_frequency_map(sets)
    got_prefix = largest_set_intersection_prefix_sum(sets)
    assert got_freq == want, f"\nlargest_set_intersection_frequency_map({sets}): got: {got_freq}, want: {want}\n"
    assert got_prefix == want, f"\nlargest_set_intersection_prefix_sum({sets}): got: {got_prefix}, want: {want}\n"

run_tests()
