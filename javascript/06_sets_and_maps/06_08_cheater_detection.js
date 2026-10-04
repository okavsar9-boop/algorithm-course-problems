// 6.8 - Cheater Detection
// Run: node 06_08_cheater_detection.js

function suspect_students(answers, m, students) {

  function same_row(desk1, desk2) {
    return Math.floor((desk1 - 1) / m) === Math.floor((desk2 - 1) / m);
  }

  function same_answers(answers1, answers2) {
    return answers1.every((a, i) => a === answers2[i]);
  }

  const desk_to_index = new Map();
  // Map desks to student indices, ignoring perfect scores
  for (let i = 0; i < students.length; i++) {
    const [_, desk, student_answers] = students[i];
    if (!same_answers(student_answers, answers)) {
      desk_to_index.set(desk, i);
    }
  }

  const sus_pairs = [];
  for (const [student_id, desk, answers] of students) {
    const other_desk = desk + 1;
    if (same_row(desk, other_desk) && desk_to_index.has(other_desk)) {
      const other_student = students[desk_to_index.get(other_desk)];
      if (same_answers(answers, other_student[2])) {
        sus_pairs.push([student_id, other_student[0]]);
      }
    }
  }
  return sus_pairs;
}


function runTests() {
  const tests = [
    // Example
    [
      ["a", "b", "c", "c"],
      5,
      [
        [4, 10, ["a", "b", "c", "d"]],
        [1, 6, ["a", "b", "c", "d"]],
        [3, 8, ["a", "b", "d", "d"]],
        [5, 11, ["a", "b", "c", "d"]],
        [9, 7, ["a", "b", "c", "d"]],
        [6, 16, ["a", "b", "d", "d"]],
      ],
      [[1, 9]],
    ],
    // Additional test cases
    [
      ["a", "b"],
      2,
      [
        [1, 1, ["a", "b"]],
        [2, 2, ["a", "b"]],
      ],
      [], // Perfect scores are not suspicious
    ],
    [
      ["a", "b"],
      2,
      [
        [1, 1, ["b", "b"]],
        [2, 2, ["b", "b"]],
      ],
      [[1, 2]],
    ],
    [
      ["a", "b"],
      2,
      [
        [1, 1, ["b", "b"]],
        [2, 3, ["b", "b"]],
      ],
      [], // Different rows
    ],
  ];

  for (const [answers, m, students, want] of tests) {
    const got = suspect_students(answers, m, students);
    if (JSON.stringify(got) !== JSON.stringify(want)) {
      throw new Error(
        `\nsuspect_students(${JSON.stringify(answers)}, ${m}, ${JSON.stringify(students)}): got: ${JSON.stringify(got)}, want: ${JSON.stringify(want)}\n`,
      );
    }
  }
}

runTests();
