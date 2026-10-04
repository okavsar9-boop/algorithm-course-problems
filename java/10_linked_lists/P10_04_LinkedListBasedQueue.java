// 10.4 - Linked-List-Based Queue
// Run: javac P10_04_LinkedListBasedQueue.java && java P10_04_LinkedListBasedQueue

import java.util.*;
import java.util.function.*;

class Node {
  int val;
  Node next;

  Node(int x) {
    val = x;
    next = null;
  }
}

class LinkedListQueue {
  private Node head;
  private Node tail;
  private int size;

  public LinkedListQueue() {
    head = null;
    tail = null;
    size = 0;
  }

  public boolean empty() {
    return head == null;
  }

  public int size() {
    return size;
  }

  public void push(int val) {
    Node newNode = new Node(val);
    if (tail != null) {
      tail.next = newNode;
    }
    tail = newNode;
    if (head == null) {
      head = newNode;
    }
    size++;
  }

  public Integer pop() {
    if (empty()) {
      return null;
    }
    Integer val = head.val;
    head = head.next;
    if (head == null) {
      tail = null;
    }
    size--;
    return val;
  }
}


class RunTests {
  public void runTests() {
    LinkedListQueue queue = new LinkedListQueue();

    // Test size on empty queue
    if (queue.size() != 0) {
      throw new RuntimeException(String.format(
          "\nsize(): got: %d, want: 0\n", queue.size()));
    }

    // Test pop on empty queue
    if (queue.pop() != null) {
      throw new RuntimeException("\npop() on empty queue should return null\n");
    }

    // Test push and size
    queue.push(10);
    if (queue.size() != 1) {
      throw new RuntimeException(String.format(
          "\nsize(): got: %d, want: 1\n", queue.size()));
    }

    // Test push and pop
    queue.push(20);
    if (queue.pop() != 10) {
      throw new RuntimeException("\npop() should return 10\n");
    }
    if (queue.size() != 1) {
      throw new RuntimeException(String.format(
          "\nsize(): got: %d, want: 1\n", queue.size()));
    }

    // Test empty
    if (queue.empty()) {
      throw new RuntimeException("\nempty() should return false\n");
    }
    queue.pop();
    if (!queue.empty()) {
      throw new RuntimeException("\nempty() should return true\n");
    }
  }
}

public class P10_04_LinkedListBasedQueue {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
