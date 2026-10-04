// 8.3 - Viewer Counter Class
// Run: javac P08_03_ViewerCounterClass.java && java P08_03_ViewerCounterClass

import java.util.*;
import java.util.function.*;

class ViewerCounter {
  private final Map<String, LinkedList<Integer>> queues;
  private final int window;

  public ViewerCounter(int window) {
    this.queues = new HashMap<>();
    this.queues.put("guest", new LinkedList<>());
    this.queues.put("follower", new LinkedList<>());
    this.queues.put("subscriber", new LinkedList<>());
    this.window = window;
  }

  public void join(int t, String v) {
    removeOldViewers(t);
    this.queues.get(v).add(t);
  }

  public int getViewers(int t, String v) {
    removeOldViewers(t);
    return this.queues.get(v).size();
  }

  private void removeOldViewers(int t) {
    for (LinkedList<Integer> queue : this.queues.values()) {
      while (!queue.isEmpty() && queue.peek() < t - this.window) {
        queue.poll();
      }
    }
  }
}
The description also mentions that multiple viewers can arrive at the same timestamp. That means that our queues may end up looking like [1, 2, 3, 3, 3, 3, 3, 3, 3, 3], which is wasteful in terms of space. We could save space by using the "piggybacking extra info" reusable idea and storing (timestamp, count) tuples. Then the same queue becomes [(1, 1), (2, 1), (3, 8)].

class ViewerCounterOptimized {
  private final Map<String, LinkedList<int[]>> queues;
  private final int window;

  public ViewerCounterOptimized(int window) {
    this.queues = new HashMap<>();
    this.queues.put("guest", new LinkedList<>());
    this.queues.put("follower", new LinkedList<>());
    this.queues.put("subscriber", new LinkedList<>());
    this.window = window;
  }

  public void join(int t, String v) {
    removeOldViewers(t);
    LinkedList<int[]> queue = this.queues.get(v);
    if (!queue.isEmpty() && queue.getLast()[0] == t) {
      queue.getLast()[1] += 1;
    } else {
      queue.add(new int[] { t, 1 });
    }
  }

  public int getViewers(int t, String v) {
    removeOldViewers(t);
    return this.queues.get(v).stream().mapToInt(pair -> pair[1]).sum();
  }

  private void removeOldViewers(int t) {
    for (LinkedList<int[]> queue : this.queues.values()) {
      while (!queue.isEmpty() && queue.peek()[0] < t - this.window) {
        queue.poll();
      }
    }
  }
}


class RunTests {
  public void runTests() {
    // Test basic version
    ViewerCounter counter = new ViewerCounter(10);
    counter.join(1, "subscriber");
    counter.join(1, "guest");
    counter.join(2, "follower");
    counter.join(2, "follower");
    counter.join(2, "follower");
    counter.join(3, "follower");
    assert counter.getViewers(10, "subscriber") == 1;
    assert counter.getViewers(10, "guest") == 1;
    assert counter.getViewers(10, "follower") == 4;
    assert counter.getViewers(13, "follower") == 1;

    // Test optimized version
    ViewerCounterOptimized counterOpt = new ViewerCounterOptimized(10);
    counterOpt.join(1, "subscriber");
    counterOpt.join(1, "guest");
    counterOpt.join(2, "follower");
    counterOpt.join(2, "follower");
    counterOpt.join(2, "follower");
    counterOpt.join(3, "follower");
    assert counterOpt.getViewers(10, "subscriber") == 1;
    assert counterOpt.getViewers(10, "guest") == 1;
    assert counterOpt.getViewers(10, "follower") == 4;
    assert counterOpt.getViewers(13, "follower") == 1;
  }
}

public class P08_03_ViewerCounterClass {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
