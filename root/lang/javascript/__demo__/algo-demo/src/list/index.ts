export interface IList<T> {
  pushFront(data: T): void

  pushBack(data: T): void

  popFront(): T | undefined

  popBack(): T | undefined

  peekFront(): T | undefined

  peekBack(): T | undefined

  get(index: number): T

  set(index: number, data: T): void

  del(index: number): void

  add(index: number, data: T): void

  reverse(): void

  toString(): string
}
