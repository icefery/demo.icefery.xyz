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
