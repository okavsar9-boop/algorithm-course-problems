// 6.8 - Cheater Detection
// Run: javac P06_08_CheaterDetection.java && java P06_08_CheaterDetection

import java.util.*;
import java.util.function.*;

record Student(int id, int desk, List<String> answers) {
}

class SuspectStudents {
  private boolean isSameRow(int desk1, int desk2, int m) {
    return Math.floorDiv(desk1 - 1, m) == Math.floorDiv(desk2 - 1, m);
  }

  public List<List<Integer>> solve(List<String> answers, int m,
  Student[] students) {
    Map<Integer, Integer> deskToIndex = new HashMap<>();
    // Map desks to student indices, ignoring perfect scores
    for (int i = 0; i < students.length; i++) {
      Student student = students[i];
      if (!student.answers().equals(answers)) {
        deskToIndex.put(student.desk(), i);
      }
    }

    List<List<Integer>> susPairs = new ArrayList<>();
    for (Student student : students) {
      int otherDesk = student.desk() + 1;
      if (isSameRow(student.desk(), otherDesk, m) &&
      deskToIndex.containsKey(otherDesk)) {
        Student otherStudent = students[deskToIndex.get(otherDesk)];
        if (student.answers().equals(otherStudent.answers())) {
          susPairs.add(Arrays.asList(student.id(), otherStudent.id()));
        }
      }
    }
    return susPairs;
  }
}


class RunTests {
  public void runTests() {
    Object[][] tests = {
        // Example
        {
            new String[] { "a", "b", "c", "c" },
            5,
            new Student[] {
                new Student(4, 10, Arrays.asList("a", "b", "c", "d")),
                new Student(1, 6, Arrays.asList("a", "b", "c", "d")),
                new Student(3, 8, Arrays.asList("a", "b", "d", "d")),
                new Student(5, 11, Arrays.asList("a", "b", "c", "d")),
                new Student(9, 7, Arrays.asList("a", "b", "c", "d")),
                new Student(6, 16, Arrays.asList("a", "b", "d", "d"))
            },
            new int[][] { { 1, 9 } }
        },
        // Additional test cases
        {
            new String[] { "a", "b" },
            2,
            new Student[] {
                new Student(1, 1, Arrays.asList("a", "b")),
                new Student(2, 2, Arrays.asList("a", "b"))
            },
            new int[][] {} // Perfect scores are not suspicious
        },
        {
            new String[] { "a", "b" },
            2,
            new Student[] {
                new Student(1, 1, Arrays.asList("b", "b")),
                new Student(2, 2, Arrays.asList("b", "b"))
            },
            new int[][] { { 1, 2 } }
        },
        {
            new String[] { "a", "b" },
            2,
            new Student[] {
                new Student(1, 1, Arrays.asList("b", "b")),
                new Student(2, 3, Arrays.asList("b", "b"))
            },
            new int[][] {} // Different rows
        }
    };

    SuspectStudents solution = new SuspectStudents();
    for (Object[] test : tests) {
      String[] answersArray = (String[]) test[0];
      List<String> answers = Arrays.asList(answersArray);
      int m = (Integer) test[1];
      Student[] students = (Student[]) test[2];
      int[][] wantArray = (int[][]) test[3];
      List<List<Integer>> want = Arrays.stream(wantArray)
          .map(arr -> Arrays.stream(arr).boxed().collect(Collectors.toList()))
          .collect(Collectors.toList());

      List<List<Integer>> got = solution.solve(answers, m, students);
      if (!got.equals(want)) {
        throw new RuntimeException(String.format(
            "\nsolve(%s, %d, %s): got: %s, want: %s\n",
            answers, m, Arrays.toString(students), got, want));
      }
    }
  }
}

public class P06_08_CheaterDetection {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
