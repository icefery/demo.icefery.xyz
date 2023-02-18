## 一、Spark 运行架构

### 1.1 运行架构

Spark 框架的核心是一个计算引擎，整体来说，它采用了标准的 master-slave 的结构。

如下图所示，它展示了一个 Spark 执行时的基本结构。图形中的 Driver 表示 master，负责整个集群中的作业任务调度，图形中的 Executor 则是 slave，负责实际执行任务。

![](https://spark.apache.org/docs/latest/img/cluster-overview.png)

### 1.2 核心组件

右上图可以看出，对于 Spark 框架有两个核心组件：

#### 1.2.1 Driver

Spark 驱动器节点，用于执行 Spark 任务中的 main 方法，负责实际代码的执行工作。Driver 在 Spark 作业执行时主要负责：

- 将用户程序转化为作业（job）
- 在 Executor 之间调度任务（task）
- 跟踪 Executor 的执行情况
- 通过 UI 展示查询运行情况

实际上，我们无法准确地描述 Driver 的定义，因为在整个的编程过程中没有看到任何有关的 Driver 的字眼。所以简单理解，所谓的 Driver 就是驱动整个应用运行起来的程序，也称之为 Driver 类。

#### 1.2.2 Executor

Spark Executor 是集群中工作节点（Worker）中的一个 JVM 进程，负责在 Spark 作业中运行具体任务（Task），任务彼此之间相互独立。Spark 应用启动时，Executor 节点被同时启动，并且始终伴随着整个 Spark 应用的生命周期而存在。如果有 Executor 节点发生了故障或崩溃，Spark 应用也可以继续执行，会将出错节点上的任务调度到其它 Executor 节点上继续运行。

Executor 有两个核心功能：

- 负责运行组成 Spark 应用的任务，并将结果返回给启动器进程
- 它们通过自身的块管理器（Block Manager）为用户程序中要求缓存的 RDD 提供内存式存储。RDD 是直接缓存在 Executor 进程内的，因此任务可以在运行时充分利用缓存数据加速运算。

#### 1.2.3 Master & Worker

Spark 集群的独立部署环境中，不需要依赖其它的资源调度框架，自身就实现了资源调度的功能，所以环境中还有其它两个核心组件：Master 和 Worker，这里的 Master 是一个进程，主要负责资源的调度和分配，并进行集群的监控等职责，类似于 Yarn 环境中的 RM，而 Worker 呢，也是进程，一个 Worker 运行在集群中的一台服务器上，由 Master 分配资源对数据进行并行的处理和计算，类似于 Yarn 环境中的 NM。

#### 1.2.4 ApplicationMaster

Hadoop 用户向 Yarn 集群提交应用程序时，提交程序中应该包含 ApplicationMaster，用于向资源调度器申请执行任务的资源容器 Container，运行用户自己的程序任务 Job，监控整个任务的执行，跟踪整个任务的状态，处理任务失败等异常情况。

说的简单点就是， ResourceManager（资源）和 Driver（计算）之间的解耦合靠的就是 ApplicationMaster。

### 1.3 核心概念

#### 1.3.1 Executor

Spark Executor 是集群中运行在工作节点（Worker）中的一个 JVM 进程，是整个集群中的专门用于计算的节点。在提交应用中，可以提供参数指定计算节点的个数，以及对应的资源。这里的资源一般指的是工作节点 Executor 的内存和使用的虚拟 CPU 核数量。

应用程序相关启动参数如下：

| 名称                | 说明                                |
| ------------------- | ----------------------------------- |
| `--num-executors`   | 配置 Executor 的数量                |
| `--executor-memory` | 配置每个 Executor 的内存大小        |
| `--executor-cores`  | 配置每个 Executor 的虚拟 CPU 核数量 |

#### 1.3.2 并行度（Parallelism）

在分布式计算框架中一般都是多个任务同时执行，由于任务分布在不同的计算节点进行计算，所以能够真正地实现多任务并行执行，记住，这里是并行，而不是并发。这里我们将整个集群并行执行任务的数量称之为并行度。那么一个作业到底并行度是多少呢？这个取决于框架的默认配置。应用程序也可以在运行过程中动态修改。

#### 1.3.3 有向无环图（DAG）

大数据计算引擎框架我们根据使用方式的不同一般会分为四类，其中有一类就是 Hadoop 所承载的 MapReduce，它将计算分为两个阶段，分为 Map 阶段和 Reduce 阶段。对于上层应用来说，就不得不想方设法去拆分算法，甚至于不得不在上层时下你多个 Job 的串联，以完成一个完整的算法，例如迭代计算。由于这样的弊端，催生了支持 DAG 框架的产生。因此，支持 DAG 的框架被划分为第二代计算引擎，如 Tez 以及更上层的 Oozie。这里我们不去细究各种 DAG 实现之间的区别，不过对于当时的 Tez 和 Oozie 来说，大多还是批处理的任务。接下来就是以 Spark 为代表的第三代的计算引擎。第三点计算引擎的特点主要是 Job 内部的 DAG 支持（不跨越 Job），以及实时计算。

这里所谓的有向无环图，并不是真正意义上的图形，而是由 Spark 程序直接映射成的数据流的高级抽象面模型。简单理解就是将整个程序计算的执行过程用图形表示出来哦，这样更直观，更便于理解，可以用于表示程序的拓扑结构。

DAG（Directed Acyclic Graph）有向无环图是由点和县组成的拓扑图形，该图形具有方向，不会闭环。

### 1.4 提交流程

所谓的提交流程，其实就是我们开发人员根据需求写的应用程序通过 Spark 客户端提交 Spark 运行环境执行计算的流程。在不同的部署环境中，这个提交过程基本相同人，但是又有细微的区别，我们这里不进行详细的比较，但是因为国内工作中，将 Spark 应用部署到 Yarn 环境中会更多一些，所以后续的提交流程都是基于 Yarn 环境的。

Spark 应用程序提交到 Yarn 环境中执行的时候，一般会有两种部署执行的方式：Client 和 Cluster。两种模式主要区别在于：Driver 程序的运行节点位置。

#### 1.4.1 Yarn Client 模式

Client 模式将用于监控和调度的 Driver 模块在客户端执行，而不是在 Yarn 中，所以一般用于测试。

- Driver 在任务提交的本地机器上运行
- Driver 启动后会和 ResourceManager 通讯申请启动 ApplicationMaster
- ResourceManager 分配 Container，在合适的 NodeManager 上启动 ApplicationMaster，负责向 ResourceManager 申请 Executor 内存
- ResourceManager 接到 ApplicationMaster 的资源申请后会分配 Container，然后 ApplicationMaster 在资源分配指定的 NodeManager 上启动 Executor 进程
- Executor 进程启动后会向 Driver 反向注册，Executor 全部注册完成后 Driver 开始执行 main 函数
- 之后执行到 Action 算子时，触发一个 Job，并根据宽依赖开始划分 stage，每个 stage 生成对应的 TaskSet，之后将 Task 分发到各个 Executor 上执行。

#### 1.4.2 Yarn Cluster 模式

Cluster 模式将用于监控和调度的 Driver 模块启动在 Yarn 集群资源中执行。一般应用于实际生产环境。

- 在 Yarn Cluster 模式下，任务提交后会和 ResourceManager 通讯申请启动 ApplicationMaster
- 随后 ResourceManager 分配 Container，在合适的 NodeManager 上启动 ApplicationMaster，此时的 ApplicationMaster 就是 Driver
- Driver 启动后向 ResourceManager 申请 Executor 内存，ResourceManager 接到 ApplicationMaster 的资源申请后会分配 Container，然后在合适的 NodeManager 上启动 Executor 进程
- Executor 进程启动后会向 Driver 反向注册，Executor 全部注册后 Driver 开始执行 main 函数
- 之后执行到 Action 算子时，触发一个 Job，并根据宽依赖开发划分 stage，每个 stage 生成对应的 TaskSet，之后将 Task 分发到各个 Executor 上执行。

## 二、Spark 核心编程

Spark 计算框架为了能够进行高并发和高吞吐的数据处理，封装了三大数据结构，用于处理不同的应用场景。三大数据结构分别是：

- RDD：弹性分布式数据集
- 累加器：分布式共享只写变量
- 广播变量：分布式共享只读变量

### 2.1 RDD

#### 2.1.1 什么是 RDD

RDD（Resilient Distributed Dataset）叫做弹性分布式数据集，是 Spark 中最基本的数据处理模型。代码中是一个抽象类，它代表一个弹性、不可变、可分区、里面的元素可并行计算的计算。

- 弹性
  - 存储的弹性：内存与磁盘的自动切换
  - 容错的弹性：数据丢失可以自动恢复
  - 计算的弹性：计算出错重试机制
  - 分片的弹性：可根据需要重新分片
- 分布式：数据存储在大数据集群不同节点上
- 数据集：RDD 封装了计算逻辑，并不保存数据
- 数据抽象：RDD 是一个抽象类，需要子类具体实现
- 不可变：RDD 封装了计算逻辑，是不可以改变的，想要改变，只能产生新的 RDD，在新的 RDD 里面封装计算逻辑
- 可分区：并行计算

#### 2.1.2 执行原理

从计算的角度来讲，数据处理过程中需要计算资源（内存 & CPU）和计算模型（逻辑）。执行时，需要将计算资源和计算模型进行协调和整合。

Spark 框架在执行时，先申请内存，然后将应用程度的数据处理逻辑分解成一个一个的计算任务。然后将任务发到已经分配资源的计算节点上，按照指定的计算模型进行数据计算，最后得到计算结果。

#### 2.1.3 基础编程

##### 2.1.3.1 RDD 创建

1. 从集合（内存）创建 RDD

   ```scala
   object App {
     def main(args: Array[String]): Unit = {
       val conf = new SparkConf().setAppName("App").setMaster("local[*]")
       val sc = new SparkContext(conf)

       val rdd1 = sc.parallelize(List(1, 2, 3, 4))
       val rdd2 = sc.makeRDD(List(1, 2, 3, 4))
       rdd1.collect().foreach(println)
       rdd2.collect().foreach(println)

       sc.stop()
     }
   }
   ```

2. 从文件（外部存储）创建 RDD

   ```scala
   object App {
     def main(args: Array[String]): Unit = {
       val conf = new SparkConf().setAppName("App").setMaster("local[*]")
       val sc = new SparkContext(conf)

       val rdd = sc.textFile("pom.xml")
       rdd.collect().foreach(println)

       sc.stop()
     }
   }
   ```

3. 从其它 RDD 创建

   主要是通过一个 RDD 运算完后，再产生新的 RDD。

4. 直接创建 RDD

   使用 `new` 的方式直接构造 RDD，一般由 Spark 框架自身使用。

##### 2.1.3.2 RDD 并行度与分区

默认情况下，Spark 可以将一个作业切分多个任务后，发送给 Executor 节点并行计算，而能够并行计算的任务数量我们称之为并行度。这个数量可以在构建 RDD 时指定。记住，这里的并行执行的任务数量，并不是指的切分任务的数量，不要混淆了。

##### 2.1.3.3 RDD 转换算子

RDD 根据数据处理方式的不同将算子整体上分为 Value 类型、双 Value 类型和 Key-Value 类型。

- Value 类型

  1. `map`

     - 函数签名

       ```scala
       def map[U: ClassTag](f: T => U): RDD[U]
       ```

  2. `mapPartitions`

     - 函数签名

       ```scala
       def mapPartitions[U: ClassTag](
         f: Iterator[T] => Iterator[U],
         preservesPartitioning: Boolean = false
       ): RDD[U]
       ```

     - 函数说明

       将待处理的数据以分区为单位发送到计算节点进行处理，这里的处理是指可以进行任意的处理，哪怕是过滤数据。

       ```scala
       val dataRDD1 = dataRDD.mapPartitions(items => items.filter(item => item == 2))
       ```

     - 小功能

       - 获取每个数据分区的最大值

     - `map` 和 `mapPartitions` 的区别

       - 数据处理角度

         `map` 算子时分区内一个数据一个数据的执行，类似于串行操作。而 `mapPartitions` 算子时以分区为单位进行批处理操作。

       - 功能角度

         `map` 算子主要目的将数据源中的数据进行转换和改变。但是不会减少或增多数据。`mapPartitions` 算子需要传递一个迭代器，返回一个迭代器，没有要求元素的个数保持不变，所以可以增加或减少数据。

       - 性能角度

         `map` 算子因为类似于串行操作，所以性能较低，而 `mapPartitions` 算子类似于批处理，所以性能较高。但是 `mapPartitions` 算子会长时间占用内存，那么这样会导致内存可能不够用，出现内存溢出的错误。所以在内存有限的情况下，不推荐使用，应使用 `map` 算子。

  3. `mapPartitionsWithIndex`

     - 函数签名

       ```scala
       def mapPartitionsWithIndex[U: ClassTag](
           f: (Int, Iterator[T]) => Iterator[U],
           preservesPartitioning: Boolean = false
       ): RDD[U]
       ```

     - 函数说明

       将待处理的数据以分区为单位发送到计算节点进行处理，这里的处理是指可以进行任意的处理，哪怕是过滤数据，在处理时同时可以获取当前分区索引。

     - 小功能
       - 获取第二个数据分区的数据

  4. `flatMap`

     - 函数签名

       ```scala
       def flatMap[U: ClassTag](f: T => TraversableOnce[U]): RDD[U]
       ```

  5. `glom`

     - 函数签名

       ```scala
       def glom(): RDD[Array[T]]
       ```

     - 函数说明

       将同一个分区的数据直接转换为相同类型的内存数组进行处理，分区不变。

       ```scala
       val dataRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 1)
       val dataRDD1: RDD[Array[Int]] = dataRDD.glom()
       ```

     - 小功能

       - 计算所有分区最大值求和（分区之内取最大值，分区之间最大值求和）

  6. `groupBy`

     - 函数签名

       ```scala
       def groupBy[K](f: T => K)(implicit kt: ClassTag[K]): RDD[(K, Iterable[T])]
       ```

     - 函数说明

       将数据根据指定的规则进行分组，分区默认不变，但是数据会被打乱重新组合，我们将这样的操作称之为 shuffle。极限情况下，数据可能会被分在同一个分区中。一个组的数据在一个分区中，但是并不是说一个分区中只有一个组。

       ```scala
       val dataRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 1)
       val dataRDD1 = dataRDD.groupBy(it => it % 2)
       ```

     - 小功能

       - 将 `List("hello", "Hive", "HBase", "Hadoop")` 根据首字母进行分组
       - 从服务器日志数据 `apache.log` 中获取每个时间段访问量
       - WordCount

  7. `filter`

     - 函数签名

       ```scala
       def filter(f: T => Boolean): RDD[T]
       ```

     - 函数说明

       将数据根据指定的规则进行筛选过滤，符合规则的数据保留，不符合规则的数据丢弃。当数据进行筛选过滤后，分区不变，但是分区内的数据可能不均衡，生产环境下，可能会出现数据倾斜。

  8. `sample`

     - 函数签名

       ```scala
       def sample(
           withReplacement: Boolean,
           fraction: Double,
           seed: Long = Utils.random.nextLong
       ): RDD[T]
       ```

     - 函数说明

       根据指定的规则从数据集中抽取数据。

  9. `distinct`

     - 函数签名

       ```scala
       def distinct(): RDD[T]
       def distinct(numPartitions: Int)(implicit ord: Ordering[T] = null)
       ```

  10. `coalesce`

      - 函数签名

        ```scala
        def coalesce(
            numPartitions: Int,
            shuffle: Boolean = false,
            partitionCoalescer: Option[PartitionCoalescer] = Option.empty
        )(
            implicit ord: Ordering[T] = null
        ): RDD[T]
        ```

      - 函数说明

        根据数据量缩减分区，用于大数据集过滤后，提高小数据集的执行效率。当 Spark 程序中存在过多的小任务的时候，可以通过 `coalesce` 方法，收缩合并分区，减少分区的个数，减小任务调度成本。

        ```scala
        val dataRDD = sc.makeRDD(List(1, 2, 3, 4, 1, 2), 6)
        val dataRDD1 = dataRDD.coalesce(2)
        ```

  11. `repartition`

      - 函数签名

        ```scala
        def repartition(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T]
        ```

      - 函数说明

        改操作内部其实执行的是 `coalesce` 操作，参数 `shuffle` 的默认值为 `true`。无论是将分区数多的 RDD 转换为分区数少的 RDD，还是将分区数少的 RDD 转换为分区数多的 RDD，`repartition` 操作都可以完成，因为无论如何都会经 Shuffle 过程。

        ```scala
        val dataRDD = sc.makeRDD(List(1, 2, 3, 4, 1, 2), 2)
        val dataRDD1 = dataRDD.repartition(4)
        ```

  12. `sortBy`

      - 函数签名

        ```scala
        sortBy[K](
            f: (T) => K,
            ascending: Boolean = true,
            numPartitions: Int = this.partitions.length
        )(
            implicit ord: Ordering[K], ctag: ClassTag[K]
        ): RDD[T]
        ```

      - 函数说明

        该操作用于排序数据。在排序之前，可以将数据通过 f 函数进行处理，之后按照 f 函数处理的结构进行排序，默认为升序排列。排列后新产生的 RDD 分区数与原 RDD 的分区数一直，中间存在 Shuffle 过程。

        ```scala
        val dataRDD = sc.makeRDD(List(1, 2, 3, 4, 1, 2), 2)
        val dataRDD1 = dataRDD.sortBy(num => num, false, 4)
        ```

- 双 Value 类型

  1. `intersection`

     - 函数签名
       ```scala
       def intersection(other: RDD[T]): RDD[T]
       ```

  2. `union`

     - 函数签名

       ```scala
       def union(other: RDD[T]): RDD[T]
       ```

  3. `subtract`

     - 函数签名

       ```scala
       def subtract(other: RDD[T]): RDD[T]
       ```

  4. `zip`

     - 函数签名

       ```scala
       def zip[U: ClassTag](other: RDD[U]): RDD[(T, U)]
       ```

     - 函数说明

       将两个 RDD 中的元素，以简直对的形式进行合并。其中，键值对中的 Key 为第一个 RDD 中的元素，Value 为第二个 RDD 中的相同位置的元素。

       > 分区和分区数据量需要一致。

- Key-Value 类型

  1. `partitionBy`

     - 函数签名

       ```scala
       def partitionBy(partitioner: Partitioner): RDD[(K, V)]
       ```

     - 函数说明

       将数据按照指定 Partition 重新进行分区。Spark 默认的分区器是 HashPartitioner。

       ```scala
       val rdd1: RDD[(Int, String)] = sc.makeRDD(Array((1, "aaa"), (2, "bbb"), (3, "ccc")), 3)
       val rdd2: RDD[(Int, String)] = rdd1.partitionBy(new HashPartitioner(2))
       ```

  2. `reduceByKey`

     - 函数签名

       ```scala
       def reduceByKey(func: (V, V) => V): RDD[(K, V)]
       def reduceByKey(func: (V, V) => V, numPartitions: Int): RDD[(K, V)]
       ```

     - 函数说明

       可以将数据按照相同的 Key 第 Value 进行聚合。

       ```scala
       val dataRDD1 = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3), ("c", 3)))
       val dataRDD2 = dataRDD1.reduceByKey((a, b) => a + b)
       val dataRDD3 = dataRDD1.reduceByKey((a, b) => a + b, 2)
       ```

     - 小功能

       - WordCount

  3. `groupByKey`

     - 函数签名

       ```scala
       def groupByKey(): RDD[(K, Iterable[V])]
       def groupByKey(numPartitions: Int): RDD[(K, Iterable[V])]
       def groupByKey(partitioner: Partitioner): RDD[(K, Iterable[V])]
       ```

     - `reduceByKey` 和 `groupByKey` 的区别

       - 从 Shuffle 的角度

         `reduceByKey` 和 `groupByKey` 都存在 Shuffle 的操作，但是 `reduceByKey` 可以在 Shuffle 前对分区内相同的数据进行预聚合功能，这样会减少罗盘的数量量，而 `groupByKey` 只是进行分组，不存在数据量减少的问题，`reduceByKey` 性能较高。

       - 从功能的角度

         `reduceByKey` 其实包含分组和聚合的功能，`groupByKey` 只能分组，不能聚合，所以在分组聚合的场景下，推荐使用 `reduceByKey`，如果仅仅是分组而不需要聚合。那么还是只能使用 `groupByKey`。

  4. `aggregateByKey`

  - 函数签名

    ```scala
    def aggregateByKey[U: ClassTag](
        zeroValue: U
    )(
        seqOp: (U, V) => U,
        combOp: (U, U) => U
     ): RDD[(K, U)]
    ```

  - 函数说明

    将数据根据不同的规则进行分区内计算和分区间计算。

    ```scala
    val dataRDD1 = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3), ("c", 3)))
    dataRDD1.aggregateByKey(0)((a, b) => a + b, (a, b) => a + b)
    ```

    > 取出每个分区相同 Key 的最大值然后分区间相加。

    ```scala
    val rdd = sc.makeRDD(
        List(("a", 1), ("a", 2), ("c", 3), ("b", 4), ("c", 5), ("c", 6)),
        2
    )
    val resultRDD = rdd.aggregateByKey(10)(
        (x, y) => math.max(x, y),
        (x, y) => x + y
    )
    resultRDD.collect().foreach(println)
    ```

  5. `foldByKey`

     - 函数签名

       ```scala
       def foldByKey(zeroValue: V)(func: (V, V) => V): RDD[(K, V)]
       ```

     - 函数说明

       当分区内计算规则和分区间计算规则相同时，`aggregateByKey` 就可以简化为 `foldByKey`。

       ```scala
       val dataRDD1 = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3)))
       val dataRDD2 = dataRDD1.foldByKey(0)((a, b) => a + b)
       ```

  6. `combineByKey`

     - 函数签名

       ```scala
       def combineByKey[C](
           createCombiner: V => C,
           mergeValue: (C, V) => C,
           mergeCombiners: (C, C) => C,
           numPartitions: Int
       ): RDD[(K, C)]
       ```

     - 函数说明

       最通用的对 Key-Value 型 RDD 进行聚集操作的聚集函数（Aggregation Function），类似于 `aggregate()`，`combineByKey` 允许用户返回值的类型与输入不一致。

       > 求数据每个 Key 的平均值。

       ```scala
       val input: RDD[(String, Int)] = sc.makeRDD(List(("a", 88), ("b", 95), ("a", 91), ("b", 93), ("a", 95), ("b", 98)), 2)
       val output: RDD[(String, (Int, Int))] = input.combineByKey(
           (_, 1),
           (acc: (Int, Int), v) => (acc._1 + v, acc._2 + 1),
           (acc1: (Int, Int), acc2: (Int, Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2)
       )
       ```

     - `reduceByKey`、`foldByKey`、`aggregateByKey`、`combineByKey` 的区别

       |                  |                                                                                            |
       | ---------------- | ------------------------------------------------------------------------------------------ |
       | `reduceByKey`    | 相同 Key 的第一个数据不进行任何计算，分区内和分区间计算规则相同                            |
       | `foldByKey`      | 相同 Key 的第一个数据和初始值进行分区内计算，分区内和分区间计算规则相同                    |
       | `aggregateByKey` | 相同 Key 的第一个数据和初始值进行分区内计算，分区内和分区间计算规则可以不相同              |
       | `combineByKey`   | 当计算时，发现数据结构不满足要求时，可以让第一个数据转换结构。分区内和分区间计算规则不相同 |

  7. `sortByKey`

     - 函数签名

       ```scala
       def sortByKey(
           ascending: Boolean = true,
           numPartitions: Int = self.partitions.length
       ): RDD[(K, V)]
       ```

  8. `join`

     - 函数签名

       ```scala
       def join[W](other: RDD[(K, W)], partitioner: Partitioner): RDD[(K, (V, W))]
       ```

     - 函数说明

       ```scala
       val rdd1: RDD[(Int, String)] = sc.makeRDD(Array((1, "a"), (2, "b"), (3, "c")))
       val rdd2: RDD[(Int, Int)] = sc.makeRDD(Array((1, 4), (2, 5), (3, 6)))
       rdd1.join(rdd2).collect().foreach(println)
       ```

  9. `leftOuterJoin`

     - 函数签名

       ```scala
       def leftOuterJoin[W](other: RDD[(K, W)], partitioner: Partitioner): RDD[(K, (V, Option[W]))]
       ```

  10. `cogroup`

      - 函数签名

        ```scala
        def cogroup[W](other: RDD[(K, W)], partitioner: Partitioner)
        ```

      - 函数说明

        ```scala
        val dataRDD1 = sc.makeRDD(List(("a", 1), ("a", 2), ("c", 3)))
        val dataRDD2 = sc.makeRDD(List(("a", 1), ("c", 2), ("c", 3)))
        val resultRDD: RDD[(String, (Iterable[Int], Iterable[Int]))] = dataRDD1.cogroup(dataRDD2)
        ```

##### 2.1.3.4 案例实操

- 数据准备
  `agent.log`：时间戳、省份、城市、用户、广告，中间字段使用空格分隔。

- 需求描述

  统计出每一个省份每个广告被点击数量排行的 Top3。

- 功能实现

  ```scala
  object App {
    def main(args: Array[String]): Unit = {
      val conf = new SparkConf().setAppName("App").setMaster("local[*]")
      val sc = new SparkContext(conf)

      // 1. 原始数据
      // "时间戳 省份 城市 用户 广告"
      val rdd1: RDD[String] = sc.textFile("input/agent.log")

      // 2. 原始数据进行结构的转换
      // "时间戳 省份 城市 用户 广告" => ((省份, 广告), 1)
      val rdd2: RDD[((String, String), Int)] = rdd1.map(
        line => {
          val fields = line.split(" ")
          ((fields(1), fields(4)), 1)
        }
      )

      // 3. 将转换结构后的数据进行分组聚合
      // ((省份, 广告), 1) => ((省份, 广告), sum)
      val rdd3: RDD[((String, String), Int)] = rdd2.reduceByKey((a, b) => a + b)

      // 4. 将聚合的结果进行结构的转换
      // ((省份, 广告), sum) => (省份, (广告, sum))
      val rdd4: RDD[(String, (String, Int))] = rdd3.map({
        case ((province, advertisement), sum) => (province, (advertisement, sum))
      })

      // 5. 将转换结构后的数据根据省份进行分组
      // (省份, (广告, sum)) => (省份, Iterable[(广告, sum)])
      val rdd5: RDD[(String, Iterable[(String, Int)])] = rdd4.groupByKey()

      // 6. 将分组后的数据组内排序取前 3 名
      // (省份, Iterable[(广告, sum)]) => (省份, List[(广告, sum)])
      val rdd6: RDD[(String, List[(String, Int)])] = rdd5.mapValues(
        values => values
          .toList
          .sortBy(t => t._2)(Ordering.Int.reverse)
          .take(3)
      )

      // 7. 采集数据打印在控制台上
      rdd6.collect().foreach(println)

      sc.stop()
    }
  }
  ```

##### 2.1.3.5 RDD 行动算子

1. `reduce`

   - 函数签名

     ```scala
     def reduce(f: (T, T) => T): T
     ```

2. `collect`

   - 函数签名

     ```scala
     def collect(): Array[T]
     ```

   - 函数说明

     在驱动程序中，以数组 Array 的形式返回数据集的所有元素。

3. `count`

   - 函数签名

     ```scala
     def count(): Long
     ```

   - 函数说明

     返回 RDD 中元素的个数。

4. `first`

   - 函数签名

     ```scala
     def first(): T
     ```

   - 函数说明

     返回 RDD 中的第一个元素。

5. `take`

   - 函数签名

     ```scala
     def take(num: Int): Array[T]
     ```

   - 函数说明

     返回一个由 RDD 的前 n 个元素组成的数组。

6. `takeOrdered`

   - 函数签名

     ```scala
     def takeOrdered(num: Int)(implicit ord: Ordering[T]): Array[T]
     ```

   - 函数说明

     返回该 RDD 排序后的前 n 个元素组成的数组。

7. `aggregate`

   - 函数签名

     ```scala
     def aggregate[U: ClassTag](zeroValue: U)(seqOp: (U, T) => U, combOp: (U, U) => U): U
     ```

   - 函数说明

     分区的数据通过初始值和分区内的数据进行聚合，然后再和初始值进行分区间的数据聚合。

8. `fold`

   - 函数签名

     ```scala
     def fold(zeroValue: T)(op: (T, T) => T): T
     ```

   - 函数说明

     折叠操作，`aggregate` 的简化版操作。

9. `countByKey`

   - 函数签名

     ```scala
     def countByKey(): Map[K, Long]
     ```

   - 函数说明

     统计每种 Key 的个数。

10. `save` 相关算子

    - 函数签名

      ```scala
      def saveAsTextFile(path: String): Unit
      def saveAsObjectFile(path: String): Unit
      def saveAsSequenceFile(
          path: String,
          codec: Option[Class[_ <: CompressionCodec]] = None
      ): Unit
      ```

    - 函数说明

      ```scala
      val rdd: RDD[String] = sc.makeRDD(Array("hello", "world"))
      // 保存成 Text 文件
      rdd.saveAsTextFile("output")
      // 序列化成对象保存到文件
      rdd.saveAsObjectFile("output1")
      // 保存成 Sequencefile 文件
      rdd.map(it => (it, 1)).saveAsSequenceFile("output2")
      ```

11. `foreach`

    - 函数签名

      ```scala
      def foreach[U](f: A => U): Unit
      ```

    - 函数说明

      分布式遍历 RDD 中的每一个元素，调用指定函数。

##### 2.1.3.6 RDD 序列化

1. 闭包检查

   从计算的角度，算子以外的代码都是在 Driver 端执行，算子里面的代码都是在 Executor 端执行。那么在 Scala 函数式编程中，就会导致算子内经常会用到算子外的数据，这样就形成了闭包的效果，如果使用的算子外的数据无法序列化，就意味着无法传值给 Executor 端执行，就会发生错误，所以需要在执行任务计算前，检测闭包内的对象是否可以进行序列化，这个操作我们称之为闭包检查。

2. 序列化方法和属性

   ```scala
   class Searcher(query: String) extends Serializable {
     def getMatchRDD(rdd: RDD[String]): RDD[String] = rdd.filter(it => it.contains(query))
   }

   object App3 {
     def main(args: Array[String]): Unit = {
       val conf = new SparkConf().setAppName("App").setMaster("local[*]")
       val sc = new SparkContext(conf)

       val rdd = sc.makeRDD(Array("hello world", "hello spark", "hello flink", "kafka"))
       val searcher = new Searcher("hello")
       val result = searcher.getMatchRDD(rdd)
       result.collect().foreach(println)

       sc.stop()
     }
   }
   ```

3. Kryo 序列化框架

   > https://github.com/EsotericSoftware/kryo

   Java 的序列化能够序列化任何的类，但是比较重（字节多），序列化后对象的提交也比较大。Spark 出于性能的考虑，Spark 2.0 开始支持另外一种 Kryo 序列化机制。Kryo 速度是 Serializable 的 10 倍。当 RDD 在 Shuffle 数据的时候，简单数据类型、数组、字符串类型已经在 Spark 内部使用 Kryo 来序列化。

   注意：即使使用 Kryo 序列化，也要继承 Serializable 接口。

   ```scala
   class Searcher(query: String) extends Serializable {
     def getMatchRDD(rdd: RDD[String]): RDD[String] = rdd.filter(it => it.contains(query))
   }

   object App {
     def main(args: Array[String]): Unit = {
       val conf = new SparkConf()
         .setAppName("App")
         .setMaster("local[*]")
         // 替换默认的序列化机制
         .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
         // 注册需要使用 Kryo 序列化的自定义类
         .registerKryoClasses(Array(classOf[Searcher]))
       val sc = new SparkContext(conf)

       val rdd = sc.makeRDD(Array("hello world", "hello spark", "hello flink", "kafka"))
       val searcher = new Searcher("hello")
       val result = searcher.getMatchRDD(rdd)
       result.collect().foreach(println)

       sc.stop()
     }
   }
   ```

##### 2.1.3.7 RDD 依赖关系

1. RDD 血缘关系

   RDD 只支持粗粒度转换，即在大量记录上执行的单个操作。将创建 RDD 的一系列 Lineage（血统）记录下来，以便恢复丢失的分区。RDD 的 Lineage 会记录 RDD 的元数据信息和转换行为，当该 RDD 的部分分区数据丢失时，它可以根据这些信息来重新运算和恢复丢失的数据分区。

   ```scala
   val fileRDD: RDD[String] = sc.textFile("input/1.txt")
   println(fileRDD.toDebugString)
   println("----------------------")

   val wordRDD: RDD[String] = fileRDD.flatMap(_.split(" "))
   println(wordRDD.toDebugString)
   println("----------------------")

   val mapRDD: RDD[(String, Int)] = wordRDD.map((_, 1))
   println(mapRDD.toDebugString)
   println("----------------------")

   val resultRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)
   println(resultRDD.toDebugString)

   resultRDD.collect()
   ```

2. RDD 依赖关系

   这里所谓的依赖关系，其实就是两个相邻 RDD 之间的关系。

   ```scala
   val fileRDD: RDD[String] = sc.textFile("input/1.txt")
   println(fileRDD.dependencies)
   println("----------------------")

   val wordRDD: RDD[String] = fileRDD.flatMap(_.split(" "))
   println(wordRDD.dependencies)
   println("----------------------")

   val mapRDD: RDD[(String, Int)] = wordRDD.map((_, 1))
   println(mapRDD.dependencies)
   println("----------------------")

   val resultRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)
   println(resultRDD.dependencies)

   resultRDD.collect()
   ```

3. RDD 窄依赖

   窄依赖表示每一个父（上游） RDD 的 Partition 最多被子（下游） RDD 的一个 Partition 使用，窄依赖我们形象的比喻为独生子女。

   ```scala
   class OneToOneDependency[T](rdd: RDD[T]) extends NarrowDependency[T](rdd)
   ```

4. RDD 宽依赖

   宽依赖表示同一个父（上游）RDD 的 Partition 被多个子（下游）RDD 的 Partition 依赖，会引起 Shuffle。总结：宽依赖我们形象的比喻为多生。

   ```scala
   class ShuffleDependency[K: ClassTag, V: ClassTag, C: ClassTag](
       @transient private val _rdd: RDD[_ <: Product2[K, V]],
       val partitioner: Partitioner,
       val serializer: Serializer = SparkEnv.get.serializer,
       val keyOrdering: Option[Ordering[K]] = None,
       val aggregator: Option[Aggregator[K, V, C]] = None,
       val mapSideCombine: Boolean = false,
       val shuffleWriterProcessor: ShuffleWriteProcessor = new ShuffleWriteProcessor
   ) extends Dependency[Product2[K, V]]
   ```

5. RDD 任务划分

   - Application：初始化一个 SparkContext 即生成一个 Application；
   - Job：一个 Action 算子就会生成一个 Job；
   - Stage：Stage 等于宽依赖（ShuffleDependency）的个数加 1；
   - Task：一个 Stage 阶段中，最后一个 RDD 的分区个数就是 Task 的个数；

   注意：Application -> Job -> Stage -> Task 每一层都是一对多的关系。

##### 5.1.4.8 RDD 持久化

1. RDD Cache 缓存

   RDD 通过 `cache()`或者 `persist()`方法将前面的计算结果缓存，默认情况下会把数据缓存在 JVM 的堆内存中。但是并不是这两个方法被调用时立即缓存，而是触发后面的 Action 算子时，该 RDD 才会被缓存在计算节点的内存中，并供后面复用。

   ```scala
   rdd.cache()
   rdd.persist(StorageLevel.MEMORY_AND_DISK_2)
   ```

   缓存有可能丢失，或者存储于内存的数据由于内存不足而被删除，RDD 的缓存容错机制保证了即使缓存丢失也能保证计算的正确执行。通过基于 RDD 的系列转换，丢失的数据会被重算，由于 RDD 的各个 Partition 是相对独立的，因此只需要计算丢失的部分即可，并不需要重算全部 Partition。

   Spark 会自动对一些 Shuffle 操作的中间数据做持久化操作（比如 `reduceByKey`），这样做的目的是为了当一个节点 Shuffle 失败了避免重新计算整个输入。但是，在实际使用的时候，如果想重用数据，仍然建议调用 `cache()` 或 `persist()` 。

2. RDD Checkpoint 检查点

   所谓的检查点其实就是通过将 RDD 中间结果写入磁盘，由于血缘依赖过长会造成容错成本过高，这样就不如在中间阶段做检查点容错，如果检查点之后有节点出现问题，可以从检查点重做血缘，减少了开销。对 RDD 进行 Checkpoint 操作并不会马上被执行，必须执行 Action 操作才能触发。

   ```scala
   // 设置检查点路径
   sc.setCheckpointDir("checkpoint")
   val lineRdd: RDD[String] = sc.textFile("input/1.txt")
   // 业务逻辑
   val wordRdd: RDD[String] = lineRdd.flatMap(line => line.split(" "))
   val wordToOneRdd: RDD[(String, Long)] = wordRdd.map(word => (word, System.currentTimeMillis()))
   // 缓存
   wordToOneRdd.cache()
   // 检查点
   wordToOneRdd.checkpoint()
   // 触发执行逻辑
   wordToOneRdd.collect().foreach(println)
   ```

3. 缓存和检查点区别

   - Cache 只是将数据保存起来，不切断血缘依赖；Checkpoint 切断血缘依赖。
   - Cache 缓存的数据通常存储在磁盘、内存等地方，可靠性低；Checkpoint 的数据通常存储在 HDFS 等容错、高可用的文件系统，可靠性高。
   - 建议对 Checkpoint 的 RDD 使用 Cache，这样 Checkpoint 的 Job 只需从 Cache 中读取数据即可，否则需要再从头计算一次 RDD。

##### 2.1.3.9 RDD 分区器

Spark 目前支持 Hash 分区和 Range 分区，和用户自定义分区。Hash 分区为当前的默认分区。分区器直接决定了 RDD 中分区的个数、RDD 中每条数据经过 Shuffle 后进入哪个分区，进而决定了 Reduce 的个数。

只有 Key-Value 类型的 RDD 才有分区器，非 Key-Value 类型的 RDD 分区的值是 None。

- Hash 分区：对于给定的 Key，计算其 Hash Code 并处以分区个数取余。
- Range 分区：将一定范围内的数据映射到一个分区中，尽量保证每个分区数据均匀，而且分区间有序。

##### 2.1.3.10 RDD 文件读取与保存

Spark 的数据读取及保存可以从两个维度来作为区分：文件格式以及文件系统。文件格式分为 Text 文件、CSV 文件、Sequence 文件以及 Object 文件；文件系统分为本地文件系统、HDFS、HBase 以及数据库。

### 2.2 累加器

#### 2.2.1 实现原理

累加器用来把 Executor 端变量信息聚合到 Driver 端。在 Driver 程序中定义的变量，在 Executor 端的每个 Task 都会得到这个变量的一份新的副本，每个 Task 更新这些副本的值后，传回 Driver 端进行 Merge。

#### 2.2.2 基础编程

##### 2.2.2.1 系统累加器

```scala
val rdd = sc.makeRDD(List(1, 2, 3, 4, 5))
val sum = sc.longAccumulator("sum")
rdd.foreach(it => sum.add(it))

println(sum)
```

##### 2.2.2.2 自定义累加器

```scala
class WordCountAccumulator extends AccumulatorV2[String, mutable.Map[String, Long]] {
  private var map: mutable.Map[String, Long] = mutable.Map[String, Long]()

  override def isZero: Boolean = map.isEmpty

  override def copy(): AccumulatorV2[String, mutable.Map[String, Long]] = new WordCountAccumulator

  override def reset(): Unit = map.clear()

  override def add(word: String): Unit = map(word) = map.getOrElse(word, 0L) + 1L

  override def merge(other: AccumulatorV2[String, mutable.Map[String, Long]]): Unit = {
    val map1 = map
    val map2 = other.value
    map = map1.foldLeft(map2)(
      (innerMap, kv) => {
        innerMap(kv._1) = innerMap.getOrElse(kv._1, 0L) + kv._2
        innerMap
      }
    )
  }

  override def value: mutable.Map[String, Long] = map
}
```

### 2.3 广播变量

#### 2.3.1 实现原理

广播变量用来高效分发较大的对象。向所有工作节点发送一个较大的只读值，以供一个或多个 Spark 操作。比如，如果你的应用需要向所有节点发送一个较大的只读查询表，广播变量用起来都很顺手。在多个并行操作中使用同一个变量，但是 Spark 会为每个任务分别发送。

#### 2.3.2 基础编程

```scala
val list1 = List(("a", 1), ("b", 2), ("c", 3), ("d", 4))
val list2 = List(("a", 4), ("b", 5), ("c", 6), ("d", 7))

val rdd = sc.makeRDD(list1, 4)
val broadcast = sc.broadcast(list2)

val resultRDD = rdd.map({
    case (k1, v1) => {
        var v = 0
        broadcast.value.foreach({
            case (k2, v2) => {
                if (k2 == k1) {
                    v = v2
                }
            }
        })
        (k1, (v1, v))
    }
})

resultRDD.collect().foreach(println)
```

## 三、案例实操

- 样例类

  ```scala
  case class UserVisitAction(
    date: String,                 // 用户点击行为的日期
    user_id: String,              // 用户的 ID
    session_id: String,           // 会话的 ID
    page_id: String,              // 页面的 ID
    action_time: String,          // 动作的时间点
    search_keyword: String,       // 用户搜索的关键词
    click_category_id: String,    // 商品品类的 ID
    click_product_id: String,     // 商品的 ID
    order_category_ids: String,   // 一次订单中所有品类的 ID 集合
    order_product_ids: String,    // 一次订单中所有品类的 ID 集合
    pay_category_ids: String,     // 一次支付中所有品类的 ID 集合
    pay_product_ids: String,      // 一次支付中所有商品的 ID 集合
    city_id: String               // 城市的 ID
  )

  case object UserVisitAction {
    def parse(line: String): UserVisitAction = {
      val fields = line.split("_")
      UserVisitAction(
        fields(0),
        fields(1),
        fields(2),
        fields(3),
        fields(4),
        fields(5),
        fields(6),
        fields(7),
        fields(8),
        fields(9),
        fields(10),
        fields(11),
        fields(12)
      )
    }
  }
  ```

- 数据规则

  数据取自电商网站的用户行为数据，主要包含用户的 4 种行为：搜索、点击、下单、支付。数据规则如下：

  - 数据文件中的每行数据采用下划线分隔数据；
  - 每一行数据表示用户的一次行为，这个行为只能是 4 种行为的一种；
  - 如果搜索关键字为 null，表示数据不是搜索数据；
  - 如果点击的品类 ID 和产品 ID 为 -1，表示数据不是点击数据；
  - 针对于下单行为，一次可以下单多个商品，所有品类 ID 和产品 ID 可以是多个，id 之间采用逗号分隔，如果本次不是下单行为，则数据采用 null 表示；
  - 支付行为和下单行为类似；

### 3.1 需求一：Top10 热门品类

```scala
object UserVisitActionRequirement1 {
  private val APP_NAME: String = UserVisitActionRequirement1.getClass.getSimpleName

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(APP_NAME).setMaster("local[*]")
    val sc = new SparkContext(conf)

    val actionRDD = sc.textFile("input/user_visit_action.txt").map(UserVisitAction.parse)

    val top10Category = getTop10Category(actionRDD)

    top10Category.foreach(println)
  }

  private def getTop10Category(actionRDD: RDD[UserVisitAction]): Array[(String, (Int, Int, Int))] = {
    actionRDD
      // 数据结构转换
      // > click: (category_id,(1,0,0))
      // > order: (category_id,(0,1,0))
      // > pay:   (category_id,(0,0,1))
      .flatMap(action => {
        if (action.click_category_id != "-1") {
          // 点击的场合
          Array((action.click_category_id, (1, 0, 0)))
        } else if (action.order_category_ids != "null") {
          // 下单的场合
          action.order_category_ids.split(",").map(id => (id, (0, 1, 0)))
        } else if (action.pay_category_ids != "null") {
          // 支付的场合
          action.pay_category_ids.split(",").map(id => (id, (0, 0, 1)))
        } else {
          Nil
        }
      })
      // 将相同品类 ID 的数据进行分组聚合
      // > (category_id,(click_count,order_count,pay_count))
      .reduceByKey((a, b) => (a._1 + b._1, a._2 + b._2, a._3 + b._3))
      // 将统计结果根据数量进行降序处理取前 10 名
      .sortBy(f = it => it._2, ascending = false)
      .take(10)
  }
}
```

### 3.2 需求二：Top10 热门品类中每个品类的 Top10 活跃会话统计

```scala
object UserVisitActionRequirement2 {
  private val APP_NAME: String = UserVisitActionRequirement2.getClass.getSimpleName

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(APP_NAME).setMaster("local[*]")
    val sc = new SparkContext(conf)

    val actionRDD = sc.textFile("input/user_visit_action.txt").map(UserVisitAction.parse)

    val top10Category = getTop10Category(actionRDD)

    val top10CategoryTop10Session = getTop10CategoryTop10Session(
      actionRDD,
      top10Category.map(it => it._1)
    )

    top10CategoryTop10Session.foreach(println)
  }

  private def getTop10Category(actionRDD: RDD[UserVisitAction]): Array[(String, (Int, Int, Int))] = ???

  private def getTop10CategoryTop10Session(actionRDD: RDD[UserVisitAction], top10Category: Array[String]): Array[(String, List[(String, Int)])] = {
    actionRDD
      // 过滤原始数据保留点击和前 10 品类
      .filter(action => action.click_category_id != "-1" && top10Category.contains(action.click_category_id))
      // 根据品类 ID 和会话 ID 进行点击量的统计
      .map(action => ((action.click_category_id, action.session_id), 1))
      .reduceByKey((a, b) => a + b)
      // 数据结构转换
      // > ((category_id,session_id),sum) => (category_id,(session_id,sum))
      .map({ case ((cid, sid), sum) => (cid, (sid, sum)) })
      // 根据相同的品类进行分组
      .groupByKey()
      // 将分组后的数据进行点击量的排序取前 10 名
      .mapValues(values =>
        values
          .toList
          .sortBy(it => it._2)(Ordering.Int.reverse)
          .take(10)
      )
      .collect()
  }
}
```

### 3.3 需求三：页面单跳转化率统计

#### 3.3.1 需求说明

- 什么是页面单跳转化率

  比如一个用户在一次会话过程中访问的页面路径 3,5,7,9,10,21，那么页面 3 跳到页面 5 叫一次单挑， 7 到 9 也叫单跳，那么单跳转化率就是要统计页面点击的概率。

  比如计算 3-5 的单跳转率，先获取符合条件的会话对于页面 3 的访问次数（PV）为 A，然后获取符合条件的会话中访问了页面 3 又紧接着访问了页面 5 的次数为 B，那么 B/A 就是 3-5 的页面单跳转化率。

- 统计页面单跳转化率的意义
  - 产品经理和运营总监，可以根据这个指标，去尝试分析，整个网站，产品，各个页面的表现怎么样，是不是需要去优化产品的布局，吸引用户最终可以进入最后的支付页面。
  - 数据分析师，可以根据此数据做更深一步的计算和分析。
  - 企业管理员，可以看到整个公司的网站，各个页面之间跳转的表现如何，可以适当调整公司的经营战略。

#### 3.3.2 功能实现

```scala
object UserVisitActionRequirement3 {
  private val APP_NAME: String = UserVisitAction.getClass.getSimpleName

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(APP_NAME).setMaster("local[*]")
    val sc = new SparkContext(conf)

    val actionRDD = sc.textFile("input/user_visit_action.txt").map(UserVisitAction.parse)

    val pageflow = getPageflow(actionRDD)

    pageflow.foreach({
      case ((page1, page2), sum) => println(s"从页面 ${page1} 跳转到页面 ${page2} 的单跳转化率为 ${sum}")
    })
  }

  private def getPageflow(actionRDD: RDD[UserVisitAction]): Array[((String, String), Double)] = {
    // 计算分母
    val down = actionRDD
      .map(action => (action.page_id, 1L))
      .reduceByKey((a, b) => a + b)
      .collect()
      .toMap

    // 计算分子
    val up = actionRDD
      // 根据会话分组
      .groupBy(action => action.session_id)
      // 分组后根据访问时间进行排序
      .mapValues(values => {
        val ids = values
          .toList
          .sortBy(action => action.action_time)
          .map(action => action.page_id)
        ids
          .zip(ids.tail)
          .map(it => (it, 1))
      })
      .flatMap(it => it._2)
      .reduceByKey((a, b) => a + b)

    // 计算单跳转率
    up
      .map({
        case ((page1, page2), sum) => {
          val d = down.getOrElse(page1, 0L)
          ((page1, page2), sum.toDouble / d)
        }
      })
      .collect()
  }
}
```
