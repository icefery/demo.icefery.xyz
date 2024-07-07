export interface IMap<K, V> {
  get(key: K): V | undefined

  contains(key: K): boolean

  keys(): K[]

  put(key: K, value: V): V | undefined

  remove(key: K): V | undefined

  toString(): string

  structure(): string
}
