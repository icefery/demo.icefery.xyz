### 在 Java 中定义一个不做事且没有参数的构造方法的作用

> Java 程序在执行子类的构造方法之前，如果没有用 `super` 来调用父类特定的构造方法，则会调用父类中”没有参数的构造方法“。因此，如果父类中定义来有参数的构造方法，而在子类的构造方法中又没有用 `super` 来调用父类中特定的构造方法，则编译时将发生错误，因为 Java 程序找不到没有参数的构造方法可供执行。解决办法是在父类里加一个不做事且没有参数的构造方法。

### `import java` 和 `import javax` 有什么区别

> 刚开始的时候 Java API 所必须包是 `java` 开头的包，`javax` 当时只是扩展 API 来 shying，然而随着时间的推移，`javax`逐渐地扩展成为 Java API 的组成部分。但是，将扩展从 `javax` 包移动到 `java`包确实太麻烦来，最终会破坏一堆现有的代码。因此决定 `javax` 包成为标准 API 的一部分。

### `equals()` 和 `hashCode()`

> 我们重写了 `Person` 类的 `equals()` 方法。但是很奇怪的发现 `HashSet` 中仍然有重复元素。为什么会出现这种情况呢？这是因为 `p1` 和 `p2` 的内容相等，但是他们的 `hashCode()` 不等，所以 `HashSet` 在添加 `p1` 和 `p2` 的时候，认为它们不相等。

### 异常类层次结构

> ![java exception handling class hierarchy diagram](https://sp-ao.shortpixel.ai/client/to_avif,q_glossy,ret_img,w_763,h_458/https://simplesnippets.tech/wp-content/uploads/2018/05/java-exception-handling-class-hierarchy-diagram.jpg)

### `Throwable` 接口常用方法

> |                                       |                                                                                                                                                               |
> | ------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------- |
> | `public String getMessage()`          | 返回异常发生时的简要描述                                                                                                                                      |
> | `public String toString()`            | 返回异常发生时的详细信息                                                                                                                                      |
> | `public String getLocalizedMessage()` | 返回异常对象的本地化信息。使用 `Throwable` 的子类覆盖这个方法，可以生成本地化信息。如果子类没有覆盖该方法，则该方法返回的信息与 `getMessage()` 返回的结果相同 |
> | `public void printStackTrace()`       | 在控制台上打印 `Throwable` 对象封装的异常信息                                                                                                                 |

> 在以下 3 种特殊情况下，`finally` 块不会被执行
>
> 1. 在 `try` 或 `finally` 块中用了 `System.exit(int)` 退出程序。但是 `System.exit(int)` 在异常语句之后，`finally` 还是会被执行。
> 2. 程序所在的线程死亡。
> 3. 关闭 CPU。

> 在 `try` 语句和 `finally` 语句中都有 `return` 语句时，在方法返回之前， `finally` 语句的内容将被执行，并且 `finally` 语句的返回值将会覆盖原始的返回值。
