// 8.3 - Viewer Counter Class
// Run: node 08_03_viewer_counter_class.js

class ViewerCounter {
  constructor(window) {
    this.queues = {
      guest: new Queue(),
      follower: new Queue(),
      subscriber: new Queue(),
    };
    this.window = window;
  }

  join(t, v) {
    this._removeOldViewers(t);
    this.queues[v].push(t);
  }

  getViewers(t, v) {
    this._removeOldViewers(t);
    return this.queues[v].size();
  }

  _removeOldViewers(t) {
    for (const queue of Object.values(this.queues)) {
      while (!queue.empty() && queue.head.val < t - this.window) {
        queue.pop();
      }
    }
  }
}

class QueueNode {
  constructor(val) {
    this.val = val;
    this.next = null;
  }
}

class Queue {
  constructor() {
    this.head = null;
    this.tail = null;
    this._size = 0;
  }

  empty() {
    return !this.head;
  }

  size() {
    return this._size;
  }

  push(val) {
    const newNode = new QueueNode(val);
    if (this.tail) {
      this.tail.next = newNode;
    }

    this.tail = newNode;
    if (!this.head) {
      this.head = newNode;
    }
    this._size++;
  }

  pop() {
    if (this.empty()) {
      throw new Error("empty queue");
    }
    const val = this.head.val;
    this.head = this.head.next;
    if (!this.head) {
      this.tail = null;
    }
    this._size--;
    return val;
  }
}

class ViewerCounterOptimized {
  constructor(window) {
    this.queues = {
      guest: new Deque(),
      follower: new Deque(),
      subscriber: new Deque(),
    };
    this.window = window;
  }

  join(t, v) {
    this._removeOldViewers(t);
    const queue = this.queues[v];
    if (!queue.empty() && queue.peekBack()[0] === t) {
      queue.peekBack()[1] += 1;
    } else {
      queue.pushBack([t, 1]);
    }
  }

  getViewers(t, v) {
    this._removeOldViewers(t);
    let total = 0;
    let curr = this.queues[v].head;
    while (curr) {
      total += curr.val[1];
      curr = curr.next;
    }
    return total;
  }

  _removeOldViewers(t) {
    for (const queue of Object.values(this.queues)) {
      while (!queue.empty() && queue.peekFront()[0] < t - this.window) {
        queue.popFront();
      }
    }
  }
}

class DequeNode {
  constructor(val) {
    this.val = val;
    this.next = null;
    this.prev = null;
  }
}

class Deque {
  constructor() {
    this.head = null;
    this.tail = null;
    this._size = 0;
  }

  empty() {
    return !this.head;
  }

  size() {
    return this._size;
  }

  peekFront() {
    if (this.empty()) {
      throw new Error("empty deque");
    }
    return this.head.val;
  }

  peekBack() {
    if (this.empty()) {
      throw new Error("empty deque");
    }
    return this.tail.val;
  }

  pushBack(val) {
    const newNode = new DequeNode(val);
    if (this.tail) {
      this.tail.next = newNode;
      newNode.prev = this.tail;
    }
    this.tail = newNode;
    if (!this.head) {
      this.head = newNode;
    }
    this._size++;
  }

  pushFront(val) {
    const newNode = new DequeNode(val);
    if (this.head) {
      this.head.prev = newNode;
      newNode.next = this.head;
    }
    this.head = newNode;
    if (!this.tail) {
      this.tail = newNode;
    }
    this._size++;
  }

  popBack() {
    if (this.empty()) {
      throw new Error("empty deque");
    }
    const val = this.tail.val;
    this.tail = this.tail.prev;
    if (this.tail) {
      this.tail.next = null;
    } else {
      this.head = null;
    }
    this._size--;
    return val;
  }

  popFront() {
    if (this.empty()) {
      throw new Error("empty deque");
    }
    const val = this.head.val;
    this.head = this.head.next;
    if (this.head) {
      this.head.prev = null;
    } else {
      this.tail = null;
    }
    this._size--;
    return val;
  }
}


function runTests() {
  // Test basic version
  const counter = new ViewerCounter(10);
  counter.join(1, "subscriber");
  counter.join(1, "guest");
  counter.join(2, "follower");
  counter.join(2, "follower");
  counter.join(2, "follower");
  counter.join(3, "follower");
  if (counter.getViewers(10, "subscriber") !== 1) {
    throw new Error("counter.getViewers(10, 'subscriber') !== 1");
  }
  if (counter.getViewers(10, "guest") !== 1) {
    throw new Error("counter.getViewers(10, 'guest') !== 1");
  }
  if (counter.getViewers(10, "follower") !== 4) {
    throw new Error("counter.getViewers(10, 'follower') !== 4");
  }
  if (counter.getViewers(13, "follower") !== 1) {
    throw new Error("counter.getViewers(13, 'follower') !== 1");
  }

  // Test optimized version
  const counterOpt = new ViewerCounterOptimized(10);
  counterOpt.join(1, "subscriber");
  counterOpt.join(1, "guest");
  counterOpt.join(2, "follower");
  counterOpt.join(2, "follower");
  counterOpt.join(2, "follower");
  counterOpt.join(3, "follower");
  if (counterOpt.getViewers(10, "subscriber") !== 1) {
    throw new Error("counterOpt.getViewers(10, 'subscriber') !== 1");
  }
  if (counterOpt.getViewers(10, "guest") !== 1) {
    throw new Error("counterOpt.getViewers(10, 'guest') !== 1");
  }
  if (counterOpt.getViewers(10, "follower") !== 4) {
    throw new Error("counterOpt.getViewers(10, 'follower') !== 4");
  }
  if (counterOpt.getViewers(13, "follower") !== 1) {
    throw new Error("counterOpt.getViewers(13, 'follower') !== 1");
  }
}

runTests();
