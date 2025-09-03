# JNA

## JNI 与 JNA

> [JNI 的替代者—使用 JNA 访问 Java 外部功能接口](https://www.cnblogs.com/lanxuezaipiao/p/3635556.html)

### JNI 调用过程

先说 JNI(Java Native Interface)吧，有过不通语言间通信经历的一般都知道，它允许 Java 代码和其他语言(尤其 C/C++)写的代码进行交互，只要遵守调用约定即可。首先看下 JNI 调用 C/C++ 的过程，注意写程序时自下而上，调用时自上而下。

![](https://images0.cnblogs.com/i/390583/201403/311340147974036.png)

可见步骤非常的多，很麻烦，使用 JNI 调用 `.dll`/`.so` 共享库都能体会到这个痛苦的过程。如果一个编译好的 `.dll`/`.so` 文件，如果使用 JNI 技术调用，我们首先需要使用 C 语言另外写一个 `.dll`/`.so` 共享库，使用 Sun 规定的数据结构替代 C 语言的数据结构，调用已有的 `.dll`/`.so`，最后编写 Java `native` 函数作为链接库中函数的代理。经过这些繁琐的步骤才能在 Java 中调用本地代码。因此，很少有 Java 程序员愿意编写调用 `.dll`/`.so` 库中原生函数的 Java 程序。这也使 Java 语言在客户端上乏善可陈，可以说 JNI 是 Java 的一大弱点！

### JNA 调用过程

JNA(Java Native Access)是一个开源的 Java 框架，由 Sun 公司推出的一种调用本地方法的技术，是建立在经典的 JNI 基础之上的一个框架。之所以说它是 JNI 的替代者，是因为 JNA 大大简化了调用本地方法的过程，使用很方便，基本上不需要脱离 Java 环境就可以完成。

如果要和上图做个比较，那么 JNA 调用 C/C++ 的过程大致如下：

![](https://images0.cnblogs.com/i/390583/201403/311340321101993.png)

可以看到步骤减少了很多，最重要的是我们不需要重写我们的动态链接库文件，而是直接调用的 API，大大简化了我们的工作量。

### JNA 技术原理

JNA 使用一个小型的 JNI 库插桩程序来动态调用本地代码。开发者使用 Java 接口描述目标本地库的功能和结构，这使得它很容易利用本机平台的功能，而不会产生多平台配置和生成 JNI 代码的高开销。

这样的性能、准确性和易用性显然受到很大的重视。

此外，JNA 包括一个已与许多本地函数映射的平台库，以及一组简化本地访问的公用接口。

JNA 是建立在 JNI 技术基础之上的一个 Java 类库，它使得你可以方便地使用 Java 直接访问动态链接库中的函数。

原来使用 JNI，你必须手工写一个动态链接库，在 C 语言中映射 Java 的数据类型。

JNA 中，它提供了一个动态的 C 语言编写的转发器，可以自动实现 Java 和 C 数据类型映射，你不再需要编写 C 动态链接库。

也许这意味着，使用 JNA 技术比使用 JNI 技术调用动态链接库会有些微的性能损失。但总体影响不大，因为 JNA 也避免了 JNI 的一些平台配置的开销。

### JNA 技术难点

有过跨语言、跨平台开发的程序员都知道，跨语言、跨平台的难点就是不同语言之间数据类型不一致造成的问题。绝大部分跨平台调用的失败都是这个问题造成的。关于这一点，无论何种语言，何种技术方案，都无法解决这个问题。JNA 也不例外。

上面说到接口中使用的函数必须与链接库中的函数原型保持一致，这是 JNA 甚至所有跨平台调用的难点，因为 C/C++ 的类型与 Java 的类型是不一样的，比如 `printf` 函数在 C 中的原型为：

```c++
void printf(const char* format[, arguments]);
```

你不可能在 Java 中也这么写，Java 中是没有 `char*` 指针类型额，因此 `const char*` 转到 Java 下就是 String 类型了。
这就是类型映射(Type Mappings)，JNA 官方给出的默认类型映射表如下：

> (Default Type Mappings)[https://github.com/java-native-access/jna/blob/master/www/Mappings.md]

| Native Type | Size                | Java Type    | Common Windows Types          |
| :---------- | :------------------ | :----------- | :---------------------------- |
| `char`      | 8-bit integer       | `byte`       | `BYTE`, `TCHAR`               |
| `short`     | 16-bit integer      | `short`      | `WORD`                        |
| `wchar_t`   | 16/32-bit character | `char`       | `TCHAR`                       |
| `int`       | 32-bit integer      | `int`        | `DWORD`                       |
| `int`       | boolean value       | `boolean`    | `BOOL`                        |
| `long`      | 32/64-bit integer   | `NativeLong` | `LONG`                        |
| `long long` | 64-bit integer      | `long`       | `__int64`                     |
| `float `    | 32-bit FP           | `float`      |                               |
| `double`    | 64-bit FP           | `double`     |                               |
| `char*`     | C string            | `String`     | `LPCSTR`                      |
| `void*`     | pointer             | `Pointer`    | `LPVOID`, `HANDLE`, `LP*XXX*` |

### JNA 能完全替代 JNI 吗

这可能是大家比较关心的问题，但是遗憾的是，JNA 是不能完全替代 JNI 的，因为有些需求还是必须求助于 JNI。
使用 JNI 技术，不仅可以实现 Java 访问 C 函数，也可以实现 C 语言调用 Java 代码。
而 JNA 只能实现 Java 访问 C 函数，作为一个 Java 框架，自然不能实现 C 语言调用 Java 代码。此时，你还是需要使用 JNI 技术。

JNI 是 JNA 的基础，是 Java 和 C 互操作的技术基础。有时候，你必须回归到基础上来。
