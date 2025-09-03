import { type IList } from './index'

class ListNode<T> {
  data: T
  prev: ListNode<T> | undefined
  next: ListNode<T> | undefined

  constructor(data: T) {
    this.data = data
  }
}

export class LinkedList<T> implements IList<T> {
  private head: ListNode<T> | undefined
  private tail: ListNode<T> | undefined
  size: number

  constructor() {
    this.head = undefined
    this.tail = undefined
    this.size = 0
  }

  pushFront(data: T): void {
    const newNode = new ListNode<T>(data)
    if (this.head === undefined) {
      this.head = newNode
      this.tail = newNode
    } else {
      newNode.next = this.head
      this.head.prev = newNode
      this.head = newNode
    }
    this.size++
  }

  pushBack(data: T): void {
    const newNode = new ListNode<T>(data)
    if (this.tail === undefined) {
      this.head = newNode
      this.tail = newNode
    } else {
      newNode.prev = this.tail
      this.tail.next = newNode
      this.tail = newNode
    }
    this.size++
  }

  popFront(): T | undefined {
    if (this.head === undefined) {
      return undefined
    } else {
      const front = this.head.data
      if (this.head === this.tail) {
        this.head = undefined
        this.tail = undefined
      } else {
        this.head = this.head.next
        if (this.head !== undefined) {
          this.head.prev = undefined
        }
      }
      this.size--
      return front
    }
  }

  popBack(): T | undefined {
    if (this.head === undefined) {
      return undefined
    } else {
      const back = this.tail?.data
      if (this.head === this.tail) {
        this.head = undefined
        this.tail = undefined
      } else {
        this.tail = this.tail?.prev
        if (this.tail !== undefined) {
          this.tail.next = undefined
        }
      }
      this.size--
      return back
    }
  }

  peekFront(): T | undefined {
    return this.head?.data
  }

  peekBack(): T | undefined {
    return this.tail?.data
  }

  get(index: number): T {
    this.checkIndex(index)
    let curr = this.head
    for (let i = 0; i < index; i++) {
      curr = curr?.next
    }
    curr = curr as ListNode<T>
    return curr.data
  }

  set(index: number, data: T): void {
    this.checkIndex(index)
    let curr = this.head
    for (let i = 0; i < index; i++) {
      curr = curr?.next
    }
    curr = curr as ListNode<T>
    curr.data = data
  }

  del(index: number): void {
    this.checkIndex(index)
    let curr = this.head
    for (let i = 0; i < index; i++) {
      curr = curr?.next
    }
    curr = curr as ListNode<T>
    const prev = curr.prev
    const next = curr.next
    if (prev === undefined) {
      this.head = next
    } else {
      prev.next = next
    }
    if (next === undefined) {
      this.tail = prev
    } else {
      next.prev = prev
    }
    this.size--
  }

  add(index: number, data: T): void {
    this.checkIndex(index)
    const newNode = new ListNode<T>(data)
    let curr = this.head
    let prev = undefined
    for (let i = 0; i < index; i++) {
      prev = curr
      curr = curr?.next
    }
    curr = curr as ListNode<T>
    curr.prev = newNode
    newNode.prev = prev
    newNode.next = curr
    if (prev === undefined) {
      this.head = newNode
    } else {
      prev.next = newNode
    }
    this.size++
  }

  reverse(): void {
    let curr = this.head
    let prev = undefined
    while (curr !== undefined) {
      const next = curr?.next
      curr.next = prev
      curr.prev = next
      prev = curr
      curr = next
    }
    this.tail = this.head
    this.head = prev
  }

  toString(): string {
    const result = []
    let curr = this.head
    while (curr !== undefined) {
      result.push(curr.data)
      curr = curr.next
    }
    return JSON.stringify(result)
  }

  private checkIndex(index: number): void {
    if (index < 0 || index > this.size) {
      throw new RangeError('Index out of bounds')
    }
  }
}
