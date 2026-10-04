// 7.2 - Nested Circles
// Run: javac P07_02_NestedCircles.java && java P07_02_NestedCircles

import java.util.*;
import java.util.function.*;

class Circle {
  public final double[] center;
  public final double radius;

  public Circle(double[] center, double radius) {
    this.center = center;
    this.radius = radius;
  }
}

class AreCirclesNested {
  private boolean contains(Circle c1, Circle c2) {
    double x1 = c1.center[0], y1 = c1.center[1], r1 = c1.radius;
    double x2 = c2.center[0], y2 = c2.center[1], r2 = c2.radius;
    double centerDistance = Math
        .sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    return centerDistance + r2 < r1;
  }

  public boolean solve(List<Circle> circles) {
    circles.sort((a, b) -> Double.compare(b.radius, a.radius)); // sort by
                                                                // radius
                                                                // descending

    for (int i = 0; i < circles.size() - 1; i++) {
      if (!contains(circles.get(i), circles.get(i + 1))) {
        return false;
      }
    }
    return true;
  }
}


class RunTests {
  public void runTests() {
    List<TestCase> tests = Arrays.asList(
        // Example 1 from the book
        new TestCase(
            Arrays.asList(
                new Circle(new double[] { 4, 4 }, 5),
                new Circle(new double[] { 8, 4 }, 2)),
            false),
        // Example 2 from the book
        new TestCase(
            Arrays.asList(
                new Circle(new double[] { 5, 3 }, 3),
                new Circle(new double[] { 5, 3 }, 2),
                new Circle(new double[] { 4, 4 }, 5)),
            true),
        // Example 3 from the book
        new TestCase(
            Arrays.asList(new Circle(new double[] { 5, 3 }, 3)),
            true),
        // Edge case - two identical circles
        new TestCase(
            Arrays.asList(
                new Circle(new double[] { 1, 1 }, 2),
                new Circle(new double[] { 1, 1 }, 2)),
            false),
        // Edge case - touching circles
        new TestCase(
            Arrays.asList(
                new Circle(new double[] { 0, 0 }, 4),
                new Circle(new double[] { 0, 0 }, 2)),
            true),
        // Edge case - empty list
        new TestCase(
            Arrays.asList(),
            true),
        // Edge case - negative coordinates
        new TestCase(
            Arrays.asList(
                new Circle(new double[] { -5, -3 }, 4),
                new Circle(new double[] { -5, -3 }, 2)),
            true),
        // Edge case - negative radius
        new TestCase(
            Arrays.asList(new Circle(new double[] { 0, 0 }, -2)),
            true),
        // Edge case - max coordinate values
        new TestCase(
            Arrays.asList(
                new Circle(new double[] { 10000, 10000 }, 10000),
                new Circle(new double[] { 0, 0 }, 100)),
            false),
        // Edge case - min coordinate values
        new TestCase(
            Arrays.asList(
                new Circle(new double[] { -10000, -10000 }, 10000),
                new Circle(new double[] { 0, 0 }, 100)),
            false),
        // Edge case - multiple circles with same center
        new TestCase(
            Arrays.asList(
                new Circle(new double[] { 1, 1 }, 5),
                new Circle(new double[] { 1, 1 }, 4),
                new Circle(new double[] { 1, 1 }, 3),
                new Circle(new double[] { 1, 1 }, 2)),
            true),
        // Edge case - circles not sorted by radius
        new TestCase(
            Arrays.asList(
                new Circle(new double[] { 0, 0 }, 2),
                new Circle(new double[] { 0, 0 }, 4),
                new Circle(new double[] { 0, 0 }, 3)),
            true));

    AreCirclesNested solution = new AreCirclesNested();
    for (TestCase test : tests) {
      boolean got = solution.solve(new ArrayList<>(test.circles())); // Create a
                                                                     // copy
      if (got != test.want()) {
        throw new RuntimeException(String.format(
            "\nsolve(%s): got: %b, want: %b\n", test.circles(), got,
            test.want()));
      }
    }
  }
}

public class P07_02_NestedCircles {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
