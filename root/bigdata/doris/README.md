# Doris

## 技术概述

Doris 整体架构如下图所示，Doris 架构非常简单，只有两类进程

-   Frontend（FE），主要负责用户请求的接入、查询解析规划、元数据的管理、节点管理相关工作。

-   Backend（BE），主要负责数据存储、查询计划的执行。

这两类进程都是可以横向扩展的，单集群可以支持到数百台机器，数十 PB 的存储容量。并且这两类进程通过一致性协议来保证服务的高可用和数据的高可靠。这种高度集成的架构设计极大的降低了一款分布式系统的运维成本。

在使用接口方面，Doris 采用 MySQL 协议，高度兼容 MySQL 语法，支持标准 SQL，用户可以通过各类客户端工具来访问 Doris，并支持与 BI 工具的无缝对接。

在存储引擎方面，Doris 采用列式存储，按列进行数据的编码压缩和读取，能够实现极高的压缩比，同时减少大量非相关数据的扫描，从而更加有效利用 IO 和 CPU 资源。

![](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/mnz20ae3s23vv3e9ltmi.png)

在查询引擎方面，Doris 采用 MPP 的模型，节点间和节点内都并行执行，也支持多个大表的分布式 Shuffle Join，从而能够更好应对复杂查询。

![](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/vjlmumwyx728uymsgcw0.png)

Doris 查询引擎是向量化的查询引擎，所有的内存结构能够按照列式布局，能够达到大幅减少虚函数调用、提升 Cache 命中率，高效利用 SIMD 指令的效果。在宽表聚合场景下性能是非向量化引擎的 5-10 倍。

![](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/ck2m3kbnodn28t28vphp.png)
