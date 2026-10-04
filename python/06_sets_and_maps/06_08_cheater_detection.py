# 6.8 - Cheater Detection
# Run: python3 06_08_cheater_detection.py

def suspect_students(answers, m, students):
  
  def same_row(desk1, desk2):
    return (desk1 - 1)//m == (desk2 - 1)//m

  desk_to_index = {}
  for i, [student_id, desk, student_answers] in enumerate(students):
    if student_answers != answers:
      desk_to_index[desk] = i

  sus_pairs = []
  for student_id, desk, answers in students:
    other_desk = desk + 1
    if same_row(desk, other_desk) and other_desk in desk_to_index:
      other_student = students[desk_to_index[other_desk]]
      if answers == other_student[2]:
        sus_pairs.append([student_id, other_student[0]])
  return sus_pairs


def run_tests():
  tests = [
      # Example 
      (
          ['a', 'b', 'c', 'c'],
          5,
          [
              (4, 10, ['a', 'b', 'c', 'd']),
              (1, 6, ['a', 'b', 'c', 'd']),
              (3, 8, ['a', 'b', 'd', 'd']),
              (5, 11, ['a', 'b', 'c', 'd']),
              (9, 7, ['a', 'b', 'c', 'd']),
              (6, 16, ['a', 'b', 'd', 'd']),
          ],
          [[1, 9]]
      ),
      # Additional test cases
      (
          ['a', 'b'],
          2,
          [
              (1, 1, ['a', 'b']),
              (2, 2, ['a', 'b']),
          ],
          []  # Perfect scores are not suspicious
      ),
      (
          ['a', 'b'],
          2,
          [
              (1, 1, ['b', 'b']),
              (2, 2, ['b', 'b']),
          ],
          [[1, 2]]
      ),
      (
          ['a', 'b'],
          2,
          [
              (1, 1, ['b', 'b']),
              (2, 3, ['b', 'b']),
          ],
          []  # Different rows
      ),
  ]

  for answers, m, students, want in tests:
    got = suspect_students(answers, m, students)
    # Sort both lists to compare them regardless of order
    assert got == want, \
        f"\nsuspect_students({answers}, {m}, {students}): got: {got}, want: {want}\n"

run_tests()
