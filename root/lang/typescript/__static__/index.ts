type JSTypeMap = {
  ['string']: string
  ['number']: number
  ['boolean']: boolean
  ['object']: object
  ['function']: Function
  ['symbol']: symbol
  ['undefined']: undefined
  ['bigint']: bigint
}

type JSTypeName = keyof JSTypeMap

type ArgsType<T extends JSTypeName[]> = {
  [I in keyof T]: JSTypeMap[T[I]]
}

declare function addImpl<T extends JSTypeName[]>(...args: [...T, (...args: ArgsType<T>) => any]): void

addImpl('string', 'boolean', 'number', (a, b, c) => {})

type DeepReadonly<T extends Record<string | symbol, any>> = {
  readonly [K in keyof T]: DeepReadonly<T[K]>
}

/**
 * 1. ( ) => R
 * 2. (x) => R
 * 3. (x) => 新的函数
 */
type Curried<A, R> = A extends []
  ? () => R
  : A extends [infer ARG]
    ? (param: ARG) => R
    : A extends [infer ARG, ...infer REST]
      ? (param: ARG) => Curried<REST, R>
      : never

declare function curry<A extends any[], R>(fn: (...args: A) => R): Curried<A, R>

function sum(a: string, b: number, c: object) {
  return 123123
}

const currySum = curry(sum)
currySum('asdf')(23)({})
