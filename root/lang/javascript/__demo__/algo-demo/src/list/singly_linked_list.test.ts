import { describe, expect, test } from '@jest/globals'
import { range } from '../utils/range'
import { LinkedList } from './singly_linked_list'

describe('LinkedList', () => {
  test('pushFront', () => {
    const list = new LinkedList<number>()
    range(1, 6).forEach(x => list.pushFront(x))
    expect(list.toString()).toEqual('[5,4,3,2,1]')
  })

  test('pushBack', () => {
    const list = new LinkedList<number>()
    range(1, 6).forEach(x => list.pushBack(x))
    expect(list.toString()).toEqual('[1,2,3,4,5]')
  })

  test('popFront', () => {
    const list = new LinkedList<number>()
    range(1, 6).forEach(x => list.pushFront(x))
    const front = list.popFront()
    expect(front).toEqual(5)
    expect(list.toString()).toEqual('[4,3,2,1]')
  })

  test('popBack', () => {
    const list = new LinkedList<number>()
    range(1, 6).forEach(x => list.pushBack(x))
    const back = list.popBack()
    expect(back).toEqual(5)
    expect(list.toString()).toEqual('[1,2,3,4]')
  })

  test('peekFront', () => {
    const list = new LinkedList<number>()
    range(1, 6).forEach(x => list.pushFront(x))
    const front = list.peekFront()
    expect(front).toEqual(5)
    expect(list.toString()).toEqual('[5,4,3,2,1]')
  })

  test('peekBack', () => {
    const list = new LinkedList<number>()
    range(1, 6).forEach(x => list.pushBack(x))
    const back = list.peekBack()
    expect(back).toEqual(5)
    expect(list.toString()).toEqual('[1,2,3,4,5]')
  })

  test('get', () => {
    const list = new LinkedList<number>()
    range(1, 6).forEach(x => list.pushBack(x))
    expect(list.get(0)).toEqual(1)
    expect(list.get(1)).toEqual(2)
    expect(list.get(2)).toEqual(3)
    expect(list.get(3)).toEqual(4)
    expect(list.get(4)).toEqual(5)
  })

  test('set', () => {
    const list = new LinkedList<number>()
    range(1, 6).forEach(x => list.pushBack(x))
    list.set(1, 4)
    expect(list.toString()).toEqual('[1,4,3,4,5]')
  })

  test('del', () => {
    const list = new LinkedList<number>()
    range(1, 6).forEach(x => list.pushBack(x))
    list.del(1)
    expect(list.toString()).toEqual('[1,3,4,5]')
  })

  test('add', () => {
    const list = new LinkedList<number>()
    range(1, 6).forEach(x => list.pushBack(x))
    list.add(1, 1.5)
    expect(list.toString()).toEqual('[1,1.5,2,3,4,5]')
  })

  test('reverse', () => {
    const list = new LinkedList<number>()
    range(1, 6).forEach(x => list.pushBack(x))
    list.reverse()
    expect(list.toString()).toEqual('[5,4,3,2,1]')
  })
})
