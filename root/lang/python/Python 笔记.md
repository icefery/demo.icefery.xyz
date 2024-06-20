# Python 笔记

## 装饰器

#### 定义代理函数

```python
def proxy(func):
    def around(*args, **kwargs):
        print("@Before")
        result = func(*args, **kwargs)
        print("@After")
        return result
    return around
```

#### 手动代理调用

```python
def func():
    print("func")

if __name__ == "__main__":
    proxy(func)()
```

#### 自动代理调用(装饰器)

```python
@proxy
def func():
    print("func")

if __name__ == "__main__":
  func()
```
