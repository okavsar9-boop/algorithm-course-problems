// 10.2 - Doubly Linked List Design
// Run: javac P10_02_DoublyLinkedListDesign.java && java P10_02_DoublyLinkedListDesign

import java.util.*;
import java.util.function.*;

class Node {
  int val;
  Node next;
  Node prev;

  Node(int x) {
    val = x;
    next = null;
    prev = null;
  }
}

push_front(v):

pop_front():

pop_back(): Removing the last node is efficient because we can access the second-to-last node through the tail's prev pointer.

size(): we return the size field.

class DoublyLinkedList {
  private Node head;
  private Node tail;
  private int size;

  public DoublyLinkedList() {
    head = null;
    tail = null;
    size = 0;
  }

  public int size() {
    return size;
  }

  public void pushFront(int val) {
    Node newNode = new Node(val);
    if (head == null) {
      head = tail = newNode;
    } else {
      newNode.next = head;
      head.prev = newNode;
      head = newNode;
    }
    size++;
  }

  public Integer popFront() {
    if (head == null) {
      return null;
    }
    int val = head.val;
    head = head.next;
    if (head != null) {
      head.prev = null;
    } else {
      tail = null;
    }
    size--;
    return val;
  }

  public void pushBack(int val) {
    Node newNode = new Node(val);
    if (tail == null) {
      head = tail = newNode;
    } else {
      newNode.prev = tail;
      tail.next = newNode;
      tail = newNode;
    }
    size++;
  }

  public Integer popBack() {
    if (tail == null) {
      return null;
    }
    int val = tail.val;
    tail = tail.prev;
    if (tail != null) {
      tail.next = null;
    } else {
      head = null;
    }
    size--;
    return val;
  }

  public Node contains(int val) {
    Node cur = head;
    while (cur != null) {
      if (cur.val == val)
        return cur;
      cur = cur.next;
    }
    return null;
  }
}


class RunTests {
  public void runTests() {
    DoublyLinkedList list = new DoublyLinkedList();

    // Test empty list
    if (list.size() != 0) {
      throw new RuntimeException(String.format(
          "\nsize(): got: %d, want: 0\n", list.size()));
    }

    if (list.popFront() != null) {
      throw new RuntimeException("\npopFront(): got value, want: null\n");
    }

    if (list.popBack() != null) {
      throw new RuntimeException("\npopBack(): got value, want: null\n");
    }

    // Test push_front
    list.pushFront(10);
    list.pushBack(20);
    if (list.size() != 2) {
      throw new RuntimeException(String.format(
          "\nsize(): got: %d, want: 2\n", list.size()));
    }
    if (list.contains(10) == null) {
      throw new RuntimeException("\ncontains(10): got: false, want: true\n");
    }
    if (list.contains(20) == null) {
      throw new RuntimeException("\ncontains(20): got: false, want: true\n");
    }

    // Test pop_front
    Integer val = list.popFront();
    if (val == null || val != 10) {
      throw new RuntimeException(String.format(
          "\npopFront(): got: %s, want: 10\n", val == null ? "null" : val));
    }
    if (list.size() != 1) {
      throw new RuntimeException(String.format(
          "\nsize(): got: %d, want: 1\n", list.size()));
    }
    if (list.contains(10) != null) {
      throw new RuntimeException("\ncontains(10): got: true, want: false\n");
    }

    // Test pop_back
    val = list.popBack();
    if (val == null || val != 20) {
      throw new RuntimeException(String.format(
          "\npopBack(): got: %s, want: 20\n", val == null ? "null" : val));
    }
    if (list.size() != 0) {
      throw new RuntimeException(String.format(
          "\nsize(): got: %d, want: 0\n", list.size()));
    }
    if (list.contains(20) != null) {
      throw new RuntimeException("\ncontains(20): got: true, want: false\n");
    }

    // Test multiple operations
    list.pushBack(30);
    list.pushFront(40);
    if (list.size() != 2) {
      throw new RuntimeException(String.format(
          "\nsize(): got: %d, want: 2\n", list.size()));
    }

    val = list.popFront();
    if (val == null || val != 40) {
      throw new RuntimeException(String.format(
          "\npopFront(): got: %s, want: 40\n", val == null ? "null" : val));
    }

    val = list.popBack();
    if (val == null || val != 30) {
      throw new RuntimeException(String.format(
          "\npopBack(): got: %s, want: 30\n", val == null ? "null" : val));
    }

    if (list.size() != 0) {
      throw new RuntimeException(String.format(
          "\nsize(): got: %d, want: 0\n", list.size()));
    }
  }
}

public class P10_02_DoublyLinkedListDesign {
  public static void main(String[] args) {
    new RunTests().runTests();
  }
}
