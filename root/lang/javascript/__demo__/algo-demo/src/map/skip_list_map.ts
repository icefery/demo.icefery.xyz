import { IMap } from './index'

class Node<K, V> {
  constructor(
    public level: number,
    public key: K,
    public value: V,
    public right?: Node<K, V>,
    public down?: Node<K, V>
  ) {}
}

export class SkipListMap<K extends number | string, V> implements IMap<K, V> {
  /**
   * 最高层次
   */
  private readonly maxLevel: number
  /**
   * 索引概率
   */
  private readonly idxFactor: number
  /**
   * 顶层虚拟头节点
   */
  private topDummyHead: Node<K, V>

  constructor(maxLevel: number = 4, idxFactor: number = 0.5) {
    this.maxLevel = maxLevel
    this.idxFactor = idxFactor
    this.topDummyHead = new Node<K, V>(1, undefined as unknown as K, undefined as unknown as V)
  }

  get(key: K): V | undefined {
    return this.getNode(key)?.value
  }

  contains(key: K): boolean {
    return this.getNode(key) !== undefined
  }

  keys(): K[] {
    const keys = Array<K>()
    let curr = this.getBottomDummyHead()
    while (curr.right !== undefined) {
      keys.push(curr.right.key)
      curr = curr.right
    }
    return keys
  }

  toString(): string {
    const json = {} as Record<K, V>
    let curr = this.getBottomDummyHead()
    while (curr.right !== undefined) {
      json[curr.right.key] = curr.right.value
      curr = curr.right
    }
    return JSON.stringify(json)
  }

  structure(): string {
    const separator = ' -> '
    const separatorWidth = separator.length
    const bottomDummyHead = this.getBottomDummyHead()
    const levels = Array<string[]>()
    // 虚拟头节点垂直指针
    for (let v = this.topDummyHead as Node<K, V> | undefined; v !== undefined; v = v.down) {
      const keys = Array<string>()
      // 当前层水平指针
      let h = v.right
      // 最底层水平指针
      let bh = bottomDummyHead.right
      while (h !== undefined) {
        let keyWidth = 0
        let placeholder = ''
        // 当 `bh` 未追赶上 `h` 时持续追赶并用空白占位
        while (bh !== undefined && bh.key < h.key) {
          keyWidth = `[${bh.key}]`.length
          placeholder += ' '.repeat(keyWidth + separatorWidth)
          bh = bh.right
        }
        // 当 `bh` 已追赶上 `h` 时用 `key` 占位
        placeholder += `[${h.key}]`.padStart(keyWidth, ' ')
        keys.push(placeholder)
        h = h.right
        bh = bh?.right
      }
      levels.push(keys)
    }
    return levels.map(keys => 'dummyHead -> ' + keys.join(separator)).join('\n')
  }

  put(key: K, value: V): V | undefined {
    let oldValue = undefined as V | undefined

    // 1. 查找并保存下降前驱节点
    const stack = new Array<Node<K, V>>()
    let curr = this.topDummyHead as Node<K, V> | undefined
    while (curr !== undefined) {
      // 1.1 当前层向右查找
      while (curr.right !== undefined && curr.right.key < key) {
        curr = curr.right
      }
      // 1.2 当前层已经找到
      if (curr.right !== undefined && curr.right.key === key) {
        // 1.2.1 保存旧值
        oldValue = curr.right.value
        // 1.2.2 设置新值
        curr.right.value = value
        return oldValue
      }
      // 1.3 保存下降前驱节点
      stack.push(curr)
      curr = curr.down
    }

    // 2. key 不存在则新增节点
    if (oldValue === undefined) {
      // 2.1 生成索引层级
      const newLevel = this.getRandomLevel()
      // 2.2 填充索引层级(虚拟头节点)
      for (let l = this.topDummyHead.level + 1; l < newLevel; l++) {
        // 2.2.1 生成新虚拟头节点
        this.topDummyHead = new Node<K, V>(l, undefined as unknown as K, undefined as unknown as V, undefined, this.topDummyHead)
        // 2.2.1 更新下降前驱节点
        stack.unshift(this.topDummyHead)
      }
      // 2.3 填充索引层级(新增节点)
      let level = 1
      let down = undefined
      while (stack.length > 0 && level <= newLevel) {
        // 2.3.1 获取下降前驱节点
        const prev = stack.pop()!
        // 2.3.2 生成新索引节点
        const curr = new Node<K, V>(level, key, value, prev.right, down) as Node<K, V>
        prev.right = curr
        // 2.3.3 继续向上填充
        level++
        down = curr
      }
    }

    return oldValue
  }

  remove(key: K): V | undefined {
    let oldValue = undefined as V | undefined
    // 1. 查找
    let curr = this.topDummyHead as Node<K, V> | undefined
    while (curr !== undefined) {
      // 1.1 当前层向右查找
      while (curr.right !== undefined && curr.right.key < key) {
        curr = curr.right
      }
      // 1.2 当前层已经找到
      if (curr.right !== undefined && curr.right.key === key) {
        // 1.2.1 保存旧值
        oldValue = curr.right.value
        // 1.2.1 删除
        curr.right = curr.right.right
      }
      // 1.3 当前层没有找到
      curr = curr.down
    }
    if (oldValue !== undefined) {
      // 2. 清理空层
      while (this.topDummyHead.down !== undefined && this.topDummyHead.right === undefined) {
        this.topDummyHead = this.topDummyHead.down
      }
    }
    return oldValue
  }

  private getRandomLevel(): number {
    let level = 1
    while (Math.random() < this.idxFactor && level < this.maxLevel) {
      level++
    }
    return level
  }

  private getBottomDummyHead(): Node<K, V> {
    let curr = this.topDummyHead
    while (curr.down !== undefined) {
      curr = curr.down
    }
    return curr
  }

  private getNode(key: K): Node<K, V> | undefined {
    let curr = this.topDummyHead as Node<K, V> | undefined
    while (curr !== undefined) {
      // 1. 当前层向右查找
      while (curr.right !== undefined && curr.right.key < key) {
        curr = curr.right
      }
      // 2. 当前层已经找到
      if (curr.right !== undefined && curr.right.key === key) {
        return curr.right
      }
      // 3. 当前层没有找到
      curr = curr.down
    }
    return undefined
  }

  /**
   * ```plain
   * [0] ->                                                  [8]
   * [0] ->                                                  [8] -> [9]
   * [0] ->                      [4] ->                      [8] -> [9]
   * [0] ->        [2] ->        [4] ->        [6] ->        [8] -> [9]
   * [0] -> [1] -> [2] -> [3] -> [4] -> [5] -> [6] -> [7] -> [8] -> [9]
   * ```
   */
  static mock(): SkipListMap<number, number> {
    // level = 1
    const n9_1 = new Node(1, 9, 9, undefined, undefined)
    const n8_1 = new Node(1, 8, 8, n9_1, undefined)
    const n7_1 = new Node(1, 7, 7, n8_1, undefined)
    const n6_1 = new Node(1, 6, 6, n7_1, undefined)
    const n5_1 = new Node(1, 5, 5, n6_1, undefined)
    const n4_1 = new Node(1, 4, 4, n5_1, undefined)
    const n3_1 = new Node(1, 3, 3, n4_1, undefined)
    const n2_1 = new Node(1, 2, 2, n3_1, undefined)
    const n1_1 = new Node(1, 1, 1, n2_1, undefined)
    const n0_1 = new Node(1, 0, 0, n1_1, undefined)
    const dummyHead_1 = new Node(1, undefined as unknown as number, undefined as unknown as number, n0_1, undefined)
    // level = 2
    const n9_2 = new Node(2, n9_1.key, n9_1.value, undefined, n9_1)
    const n8_2 = new Node(2, n8_1.key, n8_1.value, n9_2, n8_1)
    const n6_2 = new Node(2, n6_1.key, n6_1.value, n8_2, n6_1)
    const n4_2 = new Node(2, n4_1.key, n4_1.value, n6_2, n4_1)
    const n2_2 = new Node(2, n2_1.key, n2_1.value, n4_2, n2_1)
    const n0_2 = new Node(2, n0_1.key, n0_1.value, n2_2, n0_1)
    const dummyHead_2 = new Node(2, undefined as unknown as number, undefined as unknown as number, n0_2, dummyHead_1)
    // level = 3
    const n9_3 = new Node(3, n9_1.key, n9_1.value, undefined, n9_2)
    const n8_3 = new Node(3, n8_1.key, n8_1.value, n9_3, n8_2)
    const n4_3 = new Node(3, n4_1.key, n4_1.value, n8_3, n4_2)
    const n0_3 = new Node(3, n0_1.key, n0_1.value, n4_3, n0_2)
    const dummyHead_3 = new Node(3, undefined as unknown as number, undefined as unknown as number, n0_3, dummyHead_2)
    // level = 4
    const n9_4 = new Node(4, n9_1.key, n9_1.value, undefined, n9_3)
    const n8_4 = new Node(4, n8_1.key, n8_1.value, n9_4, n8_3)
    const n0_4 = new Node(4, n0_1.key, n0_1.value, n8_4, n0_3)
    const dummyHead_4 = new Node(4, undefined as unknown as number, undefined as unknown as number, n0_4, dummyHead_3)
    // level = 5
    const n8_5 = new Node(5, n8_1.key, n8_1.value, undefined, n8_4)
    const dummyHead_5 = new Node(5, undefined as unknown as number, undefined as unknown as number, n8_5, dummyHead_4)
    // map
    const map = new SkipListMap<number, number>()
    map.topDummyHead = dummyHead_5
    return map
  }
}
