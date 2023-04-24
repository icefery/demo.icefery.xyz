# Dubbo

## 收藏

#### [Dubbo 如何将 Netty 的异步请求转为同步方法调用](https://blog.csdn.net/o544033135/article/details/128205109)

1. 每次请求生成唯一的 `requestId`，此时是调用线程；

2. 调用线程生成 `CompletableFuture`，向 Netty 的 Channel 写数据前，将 `CompletableFuture` 保存到 `FUTURES`（这是一个 map）中，key 为 `requestId`，同时将 `requestId` 写入请求中。然后从 `CompletableFuture` 同步获取结果；

3. Netty 收到写请求后，解析到 `requestId`，从 `FUTURES` 获取到 `CompletableFuture`，然后调用完成方法；

4. 此时调用线程就可以从 `CompletableFuture` 中获取结果，完成同步调用过程；
