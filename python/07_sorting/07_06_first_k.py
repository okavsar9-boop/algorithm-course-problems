# 7.6 - First K
# Run: python3 07_06_first_k.py

import heapq
import random

def first_k_sorting(arr, k):
  arr.sort()
  return arr[:k]

def first_k_min_heap(arr, k):
  heapq.heapify(arr)
  return [heapq.heappop(arr) for _ in range(k)]

def first_k_max_heap(arr, k):
  max_heap = []
  for num in arr:
    heapq.heappush(max_heap, -num)  # Negate values to simulate a max-heap
    if len(max_heap) > k:
      heapq.heappop(max_heap)
  return [-x for x in max_heap]

def first_k_quickselect(arr, k):
  if not arr:
    return []
  kth_val = quickselect(arr, k)
  return [x for x in arr if x <= kth_val]

def partition(arr):
  pivot = random.choice(arr)
  smaller, equal, larger = [], [], []
  for x in arr:
    if x < pivot:
      smaller.append(x)
    elif x == pivot:
      equal.append(x)
    else:
      larger.append(x)
  return smaller, equal, larger

def quickselect(arr, k):
  smaller, equal, larger = partition(arr)
  S, E = len(smaller), len(equal)

  if k <= S:
    return quickselect(smaller, k)
  elif k <= S + E:
    return equal[0]
  else:
    return quickselect(larger, k - S - E)


def run_tests():
  tests = [
      # Example from the book
      ([15, 4, 13, 8, 10, 5, 2, 20, 3, 9, 11, 27], 5, [2, 3, 4, 5, 8]),
      # Edge case - k = 1
      ([5, 2, 1, 3, 4], 1, [1]),
      # Edge case - k = length of array
      ([3, 1, 2], 3, [1, 2, 3]),
      # Edge case - array of length 1
      ([42], 1, [42]),
      # Reverse sorted array
      ([5, 4, 3, 2, 1], 4, [1, 2, 3, 4]),
      # Already sorted array
      ([1, 2, 3, 4, 5], 3, [1, 2, 3]),
      # Edge case - empty array
      ([], 0, []),
      # Array with negative numbers
      ([-3, -1, -4, -2], 3, [-4, -3, -2]),
      # Mix of positive and negative
      ([-5, 3, -2, 8, -1], 4, [-5, -2, -1, 3]),
      # Large numbers
      ([10**9, -(10**9), 0], 2, [-(10**9), 0])
  ]

  solutions = [
      ('first_k_sorting', first_k_sorting),
      ('first_k_max_heap', first_k_max_heap),
      ('first_k_min_heap', first_k_min_heap),
      ('first_k_quickselect', first_k_quickselect)
  ]

  for name, solution in solutions:
    for arr, k, want in tests:
      got = solution(arr.copy(), k)
      assert sorted(got) == sorted(
          want), f"\n{name}({arr}, {k}): got: {got}, want: {want} (in any order)\n"

run_tests()
