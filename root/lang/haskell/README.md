# Haskell

## 基本语法

### 函数

中缀函数调用

```haskell
zsh ❯ ghci
GHCi, version 9.10.1: https://www.haskell.org/ghc/  :? for help
ghci> 1 + 2
3
ghci> 1 - 2
-1
ghci> 1 * 2
2
ghci> 1 / 2
0.5
ghci>
ghci>
ghci>
ghci> True && False
False
ghci> True && True
True
ghci> False || True
True
ghci> not False
True
ghci> not (True && True)
False
ghci>
ghci>
ghci>
ghci> 1 == 1
True
ghci> 1 == 2
False
ghci> 1 /= 2
True
ghci> "hello" == "hello"
True
ghci> :quit
Leaving GHCi.
```

前缀函数调用

```haskell
ghci> -- successor 后继
ghci> succ 1
2
ghci> min 1 2
1
ghci> max 1 2
2
```

混合

> 函数调用拥有最高的优先级

```haskell
ghci> succ 9 + max 5 4 + 1
16
-- 等价于 (succ 9) + (max 5 4) + 1
-- 等价于 (10)     + (5)       + 1
ghci> (succ 9) + (max 5 4) + 1
16
```

### 区间

```haskell
ghci> -- 步长为 1
ghci> [1..10]
[1,2,3,4,5,6,7,8,9,10]
ghci> [1,2..10]
[1,2,3,4,5,6,7,8,9,10]
ghci>
ghci> -- 步长为 2
ghci> [1,3..10]
[1,3,5,7,9]

```

### 列表推导式

```haskell
-- 列表推导式
ghci> [x * 2 | x <- [1..5]]
[2,4,6,8,10]

ghci> [x * 2 | x <- [1..5], x * 2 < 8]
[2,4,6]

ghci> [x * y | x <- [1..3], y <- [2..4]]
[2,3,4,4,6,8,6,9,12]
```

### 函数定义

```haskell
ghci> -- 定义函数
ghci> removeNonUppercase s = [c | c <- s, c `elem` ['A'..'Z']]
ghci> -- 调用函数
ghci> removeNonUppercase "Hello World"
"HW"
ghci> -- 查看类型
ghci> :t removeNonUppercase
removeNonUppercase :: [Char] -> [Char]

ghci> -- 定义函数并申明类型
ghci> addThree :: Int -> Int -> Int -> Int; addThree x y z = x + y + z
ghci> -- 调用函数
ghci> addTree 1 2 3
6
ghci> -- 查看类型
ghci> :t addTree
addTree :: Num a => a -> a -> a -> a
```
