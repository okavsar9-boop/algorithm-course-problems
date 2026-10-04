// 10.3 - Linked-List-Based Stack
// Run: javac P10_03_LinkedListBasedStack.java && java P10_03_LinkedListBasedStack

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

class LinkedListStack {
  private Node head;
  private int size;

  public LinkedListStack() {
    head = null;
    size = 0;
  }

  public void push(int val) {
    Node newNode = new Node(val);
    newNode.next = head;
    head = newNode;
    size++;
  }

  public Integer pop() {
    if (head == null) {
      return null;
    }
    int val = head.val;
    head = head.next;
    size--;
    return val;
  }

  public Integer peek() {
    if (head == null) {
      return null;
    }
    return head.val;
  }

  public int size() {
    return size;
  }

  public boolean empty() {
    return size == 0;
  }
}


class RunTests {
  public void runTests() {
    LinkedListStack stack = new LinkedListStack();

    // Test size on empty stack
    if (stack.size() != 0) {
      throw new RuntimeException(String.format(
          "\nsize(): got: %d, want: 0\n", stack.size()));
    }

    // Test pop on empty stack
    if (stack.pop() != null) {
      throw new RuntimeException("\npop() on empty stack should return null\n");
    }

    // Test peek on empty stack
    if (stack.peek() != null) {
      throw new RuntimeException(
          "\npeek() on empty stack should return null\n");
    }

    // Test push and size
    stack.push(10);
    if (stack.size() != 1) {
      throw new RuntimeException(String.format(
          "\nsize(): got: %d, want: 1\n", stack.size()));
    }

    // Test peek
    if (stack.peek() != 10) {
      throw new RuntimeException("\npeek() should return 10\n");
    }

    // Test push and pop
    stack.push(20);
    if (stack.pop() != 20) {
      throw new RuntimeException("\npop() should return 20\n");
    }
    if (stack.size() != 1) {
      throw new RuntimeException(String.format(
          "\nsize(): got: %d, want: 1\n", stack.size()));
    }

    // Test empty
    if (stack.empty()) {
      throw new RuntimeException("\nempty() should return false\n");
    }
    stack.pop();
    if (!stack.empty()) {
      throw new RuntimeException("\nempty() should return true\n");
    }
  }
}

public class P10_03_LinkedListBasedStack {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
