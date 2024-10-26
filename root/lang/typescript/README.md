# TypeScript

## 收藏

### 实现 Optional

```typescript
type Optional<T, K extends keyof T> = Omit<T, K> & Partial<Pick<T, K>>

interface Article {
  title: string
  content: string
  author: string
  date: Date
  readCount: number
}

type CreateArticleOptions = Optional<Article, 'author' | 'date' | 'readCount'>

function createArticle(options: CreateArticleOptions) {}
```

### 使用 infer 封装通用类型工具

```typescript
/**
 * 提取 Promise 中的类型
 */
type PromiseType<T> = T extends Promise<infer K> ? PromiseType<K> : T

type T1 = PromiseType<Promise<Promise<string>>> // string

/**
 * 提取函数第一个参数类型
 */
type FirstArgType<T> = T extends (first: infer F, ...args: any[]) => any ? F : T

type T2 = FirstArgType<(name: string, age: number) => void> // string

/**
 * 提取函数返回类型
 */
type MyReturnType<T> = T extends (...args: any[]) => infer R ? R : T

type Sum = (a: number, b: number) => number

type T3 = MyReturnType<Sum> // number
```

### 装饰器

```shell
npm install reflect-metadata
```

```typescript
import 'reflect-metadata'

function MyClassDecorator<T extends { new (...args: any[]): {} }>(constructor: T) {
  console.log('[MyClassDecorator] constructor=', constructor)
}

function MethodDecorator(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
  console.log('[MethodDecorator] target=', target, 'propertyKey=', propertyKey, 'descriptor=', descriptor)
}

function AccessorDecorator(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
  console.log('[AccessorDecorator] target=', target, 'propertyKey=', propertyKey, 'descriptor=', descriptor)
}

function PropertyDecorator(target: any, propertyKey: string) {
  console.log('[PropertyDecorator] target=', target, 'propertyKey=', propertyKey)
  Reflect.metadata('aaa', '123')
}

function ParameterDecorator(target: any, propertyKey: string, parameterIndex: number) {
  console.log('[ParameterDecorator] target=', target, 'propertyKey=', propertyKey, 'parameterIndex=', parameterIndex)
}

@MyClassDecorator
class MyClass {
  @PropertyDecorator
  width: number

  constructor(
    width: number,
    private height: number
  ) {
    this.width = width
  }

  @MethodDecorator
  greet(@ParameterDecorator name: string) {
    console.log('hello, ', name)
  }

  @AccessorDecorator
  get area(): number {
    return this.width * this.height
  }
}

function main() {
  const a = new MyClass(3, 4)
  a.greet('world')
  console.log(a.area)
}

main()
```
