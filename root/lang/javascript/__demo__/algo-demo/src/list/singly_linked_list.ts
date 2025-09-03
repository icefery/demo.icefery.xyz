import { type IList } from './index'

class ListNode<T> {
  data: T
  next?: ListNode<T>

  constructor(data: T) {
    this.data = data
  }
}

export class LinkedList<T> implements IList<T> {
  private head: ListNode<T> | undefined
  size: number

  constructor() {
    this.size = 0
  }

  pushFront(data: T): void {
    const newNode = new ListNode<T>(data)
    newNode.next = this.head
    this.head = newNode
  }

  pushBack(data: T): void {
    const newNode = new ListNode<T>(data)
    if (this.head === undefined) {
      this.head = newNode
    } else {
      let curr = this.head
      while (curr.next !== undefined) {
        curr = curr.next
      }
      curr.next = newNode
    }
    this.size++
  }

  popFront(): T | undefined {
    if (this.head === undefined) {
      return undefined
    } else {
      const front = this.head.data
      this.head = this.head.next
      this.size--
      return front
    }
  }

  popBack(): T | undefined {
    if (this.head === undefined) {
      return undefined
    } else {
      let curr = this.head
      let prev = undefined
      while (curr.next !== undefined) {
        prev = curr
        curr = curr.next
      }
      if (prev === undefined) {
        this.head = undefined
      } else {
        prev.next = undefined
      }
      this.size--
      return curr.data
    }
  }

  peekFront(): T | undefined {
    return this.head?.data
  }

  peekBack(): T | undefined {
    if (this.head === undefined) {
      return undefined
    } else {
      let curr = this.head
      while (curr.next !== undefined) {
        curr = curr.next
      }
      return curr.data
    }
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
    let prev = undefined
    let next = curr?.next
    for (let i = 0; i < index; i++) {
      prev = curr
      curr = curr?.next
      next = curr?.next
    }
    curr = curr as ListNode<T>
    if (prev === undefined) {
      this.head = next
    } else {
      prev.next = next
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
      const next = curr.next
      curr.next = prev
      prev = curr
      curr = next
    }
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
    if (index < 0 || index >= this.size) {
      throw new RangeError('Index out of bounds')
    }
  }
}
