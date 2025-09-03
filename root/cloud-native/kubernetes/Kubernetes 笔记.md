## 一、Pod

### 1 Pod 概述

Pod 是 K8S 系统中可以创建和管理的最小单元，是资源对象模型中最小资源对象模型，也是在 K8S 上运行容器化应用的资源对象，其它的资源对象都是用来支撑或者扩展 Pod 对象功能的，比如控制器对象是用来管控 Pod 对象的，Service 或者 Ingress 资源对象是用来暴露 Pod 引用对象的，PersistentVolume 资源对象是用来为 Pod 提供存储等等，K8S 不会直接处理容器，而是 Pod，Pod 是由一个或多个 Container 组成的。

Pod 是 Kubernetes 的最重要概念，每一个 Pod 都有一个特殊的被称为 "根容器" 的 Pause 容器。Pause 容器对应的镜像属于 Kubernetes 平台的一部分，除了 Pause 容器，每个 Pod 还包含一个或多个紧密相关的用户业务容器。

#### 1.1 Pod 和应用的关系

每个 Pod 都是应用的一个实例，有专有的 IP。

#### 1.2 Pod 和容器的关系

一个 Pod 可以有多个容器，彼此间共享网络和存储资源，每个 Pod 中有一个 Pause 容器保存所有的容器状态，通过管理 Pause 容器，达到管理 Pod 中所有容器的效果。

#### 1.3 Pod 和节点的关系

同一个 Pod 中的容器总会被调度到相同节点，不同节点间 Pod 的通信基于虚拟二层网络技术实现。

### 2 Pod 特性

#### 2.1 资源共享

一个 Pod 里的多个容器可以共享存储和网络，可以看做一个逻辑的主机。共享的如 Namespace、Cgroups 或者其它的隔离资源。

多个容器共享同一 Network Namespace，由此在一个 Pod 里的多个容器共享 Pod 的 IP 和端口 Namespace，所以一个 Pod 内的多个容器之间可以通过 localhost 来进行通信，所需要注意的是不同容器要注意不要有端口冲突即可。不同的 Pod 有不同的 IP，不同 Pod 内的多个容器之间通信，不可以使用 IPC（如果没有特殊指定的话）通信，通常情况下使用 Pod 的 IP 进行通信。

一个 Pod 里的多个容器可以共享存储卷，这个存储卷会被定义为 Pod 的一部分，并且可以挂载到该 Pod 里的所有容器的文件系统上。

#### 2.2 生命周期短暂

Pod 属于生命周期比较短暂的组件，比如，当 Pod 所在节点发生故障，那么该节点上的 Pod 会被调度到其它节点上，但需要注意的是，被重新调度的 Pod 是一个全新的 Pod，跟之前的 Pod 没有半毛钱关系。

#### 2.3 平坦的网络

K8S 集群的所有 Pod 都在同一个共享网络地址空间中，也就是说每个 Pod 都可以通过其它 Pod 的 IP 地址来实现访问。

### 3 Pod 的基本使用方法

在 Kubernetes 中对运行容器的要求：容器的主程序需要一直在前台运行，而不是在后台运行。如果我们创建的 Docker 镜像的启动命令是后台执行程序，则在 kubelet 创建包含这个容器的 Pod 之后运行完该命令，即认为 Pod 已经结束，将立即销毁该 Pod。如果为该 Pod 定义了 RC，则创建、销毁会陷入一个无限循环的过程中。Pod 可以由一个或多个容器组合而成。

### 4 Pod 的分类

#### 4.1 普通 Pod

普通 Pod 一旦被创建，就会被放入到 Etcd 中存储，随后会被 Kubernetes Master 调度到某个具体的 Node 上并进行绑定，随后该 Pod 对应的 Node 上的 kubelet 进程实例化成一组相关的 Docker 容器并启动起来。在默认情况下，当 Pod 里某个容器停止时，Kubernetes 会自动检测到这个问题并且重新启动这个 Pod 里的所有容器，如果 Pod 所在的 Node 宕机，则会将这个 Node 上所有 Pod 重新调度到其它节点上。

#### 4.2 静态 Pod

静态 Pod 是由 kubelet 进行管理的仅存在于特定 Node 上的 Pod，它们不能通过 API Server 进行管理，无法与 ReplicationController、Deployment 或 DaemonSet 进行关联，并且 kubelet 也无法对它们进行健康检查。

### 5 Pod 的生命周期和重启策略

#### 5.1 Pod 的状态

| 状态      | 说明                                                                                       |
| :-------- | :----------------------------------------------------------------------------------------- |
| Pending   | API Server 已经创建了该 Pod，但 Pod 中的一个或多个容器的镜像还没有创建，包括镜像下载过程。 |
| Running   | Pod 内所有容器已经创建，且至少一个容器处于运行状态、正在启动状态或正在重启状态。           |
| Completed | Pod 内所有容器均成功执行退出，且不会再重启。                                               |
| Failed    | Pod 内所有容器均已退出，但至少有一个容器退出失败。                                         |
| Unkown    | 由于某种原因无法获取 Pod 状态，例如网络通信不畅。                                          |

#### 5.2 Pod 的重启策略

| 重启状态  | 说明                                                         |
| --------- | ------------------------------------------------------------ |
| Always    | 当容器失效时，由 kubelet 自动重启该容器。默认值。            |
| OnFailure | 当容器终止运行且退出码不为 0 时，由 kubelet 自动重启该容器。 |
| Never     | 无论容器运行状态如何，kubelet 都不会重启该容器。             |

#### 5.3 常见状态转换

| 容器数 | 当前状态 | 发生事件         | 结果状态<br/>（restartPolicy=Always） | 结果状态<br/>（restartPolicy=OnFailure） | 结果状态<br/>（restartPolicy=Never） |
| ------ | -------- | ---------------- | ------------------------------------- | ---------------------------------------- | ------------------------------------ |
| 1      | Running  | 容器成功退出     | Running                               | Succeeded                                | Succeeded                            |
| 1      | Running  | 容器失败退出     | Running                               | Running                                  | Failure                              |
| 2      | Runing   | 一个容器失败退出 | Running                               | Running                                  | Running                              |
| 2      | Running  | 容器被 OOM 杀掉  | Running                               | Running                                  | Failure                              |

#### 5.4 Pod 的资源配置

每个 Pod 都可以对其能使用的服务器上的计算资源设置限额，Kubnernetes 中可以设置限额的计算资源有 CPU 与 Memory 两种，其中 CPU 的资源单位为 CPU 数量（毫核），是一种绝对值而非相对值。Memory 配额也是一个绝对值，它的单位是内存字节数。

## 二、Label

### 1 Label 概述

Label 是 Kubernetes 系统中另一个核心概念。一个 Label 是一个 key=value 的键值对，其中 key 与 value 由用户自己指定。Label 可以附加到各种资源对象上，如 Node、Pod、Service、RC，一个资源对象可以定义任意数量的 Label，同一个 Label 也可以被添加到任意数量的资源对象上，Label 通常在资源对象定义时确定，也可以在对象创建后动态添加或删除。

Label 的最常见的用法是使用 `metadata.labels` 字段来为对象添加 Label，通过 `spec.selector` 来引用对象。

Label 附加到 Kubernetes 集群中各种资源对象上，目的就是对这些资源对象进行分组管理，而分组管理的核心就是 Label Selector。Label 和 Label Selector 都不能单独定义，必须附加在一些资源对象的定义文件上，一般附加在 RC 和 Service 的资源定义文件中。

## 三、Controller 控制器

### 1 Replication Controller

Replication Controller（RC）是 Kubernetes 系统中核心概念之一，当我们定义了一个 RC 并提交到 Kubernetes 集群中以后，Master 节点上的 Controller Manager 组件就得到通知，定期检查系统中存活的 Pod，并确定目标 Pod 实例的数量刚好等于 RC 的预期值，如果有过多或过少的 Pod 运行，系统就会停掉或创建一些 Pod。此外我们也可以通过修改 RC 的副本数量，来实现 Pod 的动态缩放功能。

```shell
kubectl scale rc nginx --replicas=5
```

由于 Replication Controller 与 Kubernetes 代码中的模块 Replication Controller 同名，所以在 Kubernetes v1.2 时，它就升级成了另外一个新的概念 Replica Set，官方解释为下一代的 RC，它与 RC 的区别是：Replica Set 是基于集合的 Label Selector，而 RC 只支持基于等式的 Label Selector。我们很少单独使用 Replica Set，它主要被 Deployment 这个更高层面的资源对象所使用，从而形成一整套 Pod 创建、删除、更新的编排机制。最好不好越过 RC 直接创建 Pod，因为 Replication Controller 会通过 RC 管理 Pod 副本，实现自动创建、补足、替换、删除 Pod 副本，这样就能提高应用的容灾能力，减少由于节点崩溃等意外状况造成的损失。即使应用程序只有一个 Pod 副本，也强烈建议使用 RC 来定义 Pod。

### 2 Replica Set

Replica Set 跟 Replication Controller 没有本质的不同，只是名字不一样，并且 Replica Set 支持集合式的 Selector。Kubernetes 官方强烈建议避免直接使用 Replica Set，而应该通过 Deployment 来创建 RS 和 Pod。由于 Replica Set 是 Replication Controller 的代替物，因此用法基本相同，唯一的区别在于 Replica Set 支持集合式的 Selector。

### 3 Deployment

Deployment 是 Kubernetes v1.2 引入的新概念，引入的目的是为了更好的解决 Pod 的编排问题，Deployment 内部使用了 Replica Set 来实现。Deployment 的定义与 Replica Set 的定义很类似，除了 API 声明与 kind 类型有所区别。

### 4 Horizontal Pod Autoscaler

Horizontal Pod Autoscaler（Pod 横向扩容，简称 HPA）与 RC、Deployment 一样，也属于一种 Kunernetes 资源对象。通过追踪分东西 PC 控制器的所有目标 Pod 的负载变化情况，来确定是否需要针对性地调整目标 Pod 的副本数，这是 HPA 的实现原理。

Kubernetes 对 Pod 扩容与缩容提供了手动和自动两种模式，手动模式通过 `kubectl scale` 命令对一个 Deployment/RC 进行副本数量的设置。自动模式则需要根据某个性能指标或者自定义业务指标，并指定 Pod 副本数量的范围，系统将自动在这个范围内根据性能指标的变化进行调整。

## 四、Volume

### 1 Volume 概述

Volume 是 Pod 中能够被多个容器访问的共享目录。Kubernetes 的 Volume 定义在 Pod 上，它被一个 Pod 中的多个容器挂载到具体的文件目录下。Volume 与 Pod 的生命周期相同，但与容器的声明周期不相关，当容器终止或重启时，Volume 中的数据也不会丢失。

要使用 Volume，Pod 需要指定 Volume 的类型和内容，和映射到容器的位置。

Kubernetes 支持多种类型的 Volume。emptyDir 类型的 Volume 创建于 Pod 被调度到某个宿主机的时候，而同一个 Pod 内的容器都能读写 emptyDir 中的同一个文件。一旦这个 Pod 离开了这个宿主机，EmptyDir 中的数据就会被永久删除。所以目前 emptyDir 类型的 Volume 主要用作临时空间，比如 Web 服务器写日志或者 tmp 文件需要的临时目录。

### 2 hostPath

hostPath 类型的 Volume 使得对应的容器能够访问当前宿主机上的指定目录。例如，需要运行一个访问 Docker 系统目录的容器，那么使用 `/var/lib/mysql` 目录作为一个 hostPath 类型的 Volume。一旦这个 Pod 离开了这个宿主机，hostPath 中的数据虽然不会永久删除，但数据也不会随 Pod 迁移到其它宿主机上。因此，需要注意的是，由于各个宿主机上的文件系统结构和内容并不完全相同，所有相同 Pod 的 hostPath 可能会在不同的宿主机和是哪个表现出不同的行为。

### 3 nfs

nfs 类型的 Volume 允许一块现有的网络硬盘在同一个 Pod 内的容器间共享。

## 五、PVC 和 PV

### 1 基本概念

管理存储是管理计算的一个明显问题。该 PersistentVolume 子系统为用户和管理员提供了一个 API，用于抽象如何根据消费方式提供存储的详细信息。为此，我们引入了两个新的 API 资源：PersistentVolumeClaim 和 PersistentVolume。

PersistentVolume（PV）是集群中由管理员配置的一段网络存储。它是集群中的资源，就像节点是集群资源一样。PV 是容量插件，如 Volumes，但其生命周期独立于使用 PV 的任务单个 Pod。此 API 对象捕获存储实现的详细信息，包括 nfs、iscsi 或特定于云提供程序的存储系统。

PersistentVolumeClaim（PVC）是由用户进行存储的请求。它类似于 Pod，Pod 消耗节点资源，PVC 消耗 PV 资源，Pod 可以请求特定级别的资源（CPU 和内存），Claim 可以请求特定的大小和访问模式（如一次读写或多次只读）。

虽然 PersistentVolumeClaim 允许用户使用抽象存储资源，但是 PersistentVolume 对于不同的问题，用户通常需要具有不同属性（例如性能）。集群管理员需要能够提供各种 PersistentVolume 不同的方式，而不仅仅是大小和访问模式，而不会让用户了解这些卷的实现方式。对于这些需求，有 StorageClass 资源。

StorageClass 为管理员提供了一种描述它们的存储的 "类" 的方法。不同的类可能映射到服务质量级别，或备份策略，或者由集群管理员确定的任意策略。

PVC 和 PV 是一一对应的。

### 2 生命周期

PV 是集群中的资源，PVC 对这些资源的请求，并且还充当对资源的检查。PV 和 PVC 之间的相互作用遵循以下生命周期：

1. Provissioning：通过集群外的存储系统或者云平台来提供持久化支持。
    - 静态提供：集群管理员创建多个 PV，它们携带可供集群用户使用的真实存储的详细信息，它们存在于 Kubnernetes API 中，可用于消费。
    - 动态提供：当集群管理员创建的静态 PV 都不匹配用户的 PVC 时，集群可能会尝试为 PVC 动态配置卷。此配置基于 StorageClass，PVC 必须请求一个类，并且管理员必须已创建并配置该类才能进行动态配置。
2. Binding：用户创建 PVC 并指定需要的资源和访问模式，在找到可用 PV 之前，PVC 会保持未绑定状态。
3. Using：用户可在 Pod 中像使用 Volume 一样使用 PVC。
4. Releasing：用户删除 PVC 来回收存储资源，PV 将变成 "Released" 状态。由于还保留着之前的数据，这些数据需要根据不同的策略来处理，否则这些存储资源无法被其它 PVC 使用。
5. Recycling：PV 可以设置三种回收策略。
    - Retain（保留策略）：允许人工处理保留的数据。
    - Delete（删除策略）：将删除 PV 和外部关联的存储资源，需要插件支持。
    - Recycle（回收策略）：将执行清除操作，之后可以被新的 PVC 使用，需要插件支持。

### 3 PV 卷阶段状态

| 状态     | 说明                                           |
| -------- | ---------------------------------------------- |
| Availble | 资源尚未被 Claim 使用。                        |
| Bound    | 卷已经绑定到 Claim 了。                        |
| Released | Claim 被删除，卷处于释放状态，但未被集群回收。 |
| Failed   | 卷自动回收失败。                               |

## 六、Secret

### 1 Secret 存在意义

Secret 解决了密码、Token、秘钥等敏感数据的配置问题，而不需要吧这些敏感数据暴露到镜像或者 Pod Spec 中。Secret 可以以 Volume 或者环境变量的方式使用。

### 2 Secret 类型

| 类型                           | 说明                                                                                                                          |
| ------------------------------ | ----------------------------------------------------------------------------------------------------------------------------- |
| Service Account                | 用来访问 Kubernetes API，由 Kubernetes 自动创建，并且会自动挂载到 Pod 的 `/run/secrets/kubernetes.io/serviceaccount` 目录中。 |
| Opaque                         | base64 编码格式的 Secret，用来存储密码、秘钥等。                                                                              |
| kubernetes.io/dockerconfigjson | 用来存储私有 docker registry 的认证信息。                                                                                     |

### 3 Service Account

Service Account 用来访问 Kunernetes API，由 Kunernetes 自动创建，并且会自动挂载到 Pod 的 `/run/secrets/kubernetes.io/serviceaccount` 目录中。

### 4 Opaque Secret

#### 4.1 创建 Secret

```shell
echo -n 'admin' | base64

echo -n 'admin123456' | base64

cat <<- EOF > secret.yaml
apiVersion: v1
kind: Secret
metadata:
  name: my-secret
type: Opaque
data:
  username: YWRtaW4=
  password: YWRtaW4xMjM0NTY=
EOF
```

#### 4.2 将 Secret 挂载到 Volume 中

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: secret-test
spec:
  containers:
    - name: secret-test
      image: my-app:0.0.1
      volumeMounts:
        - mountPath: /my-secret
          name: my-secret
  volumes:
    - name: my-secret
      secret:
        secretName: my-secret
```

#### 4.3 将 Secret 设置为环境变量

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: secret-test
spec:
  containers:
    - name: secret-test
      image: my-app:0.0.1
      env:
        - name: USERNAME
          valueFrom:
            secretKeyRef:
              name: my-secret
              key: username
```

## 七、ConfigMap

### 1 ConfigMap 概述

ConfigMap 功能在 Kubernetes 1.2 版本中引入，许多应用程序会从配置文件、命令行参数或环境变量中读取配置信息。ConfigMap 给我们提供了向容器注入配置信息的机制，ConfigMap 可以被用来保存单个属性，也可以用来保存整个配置文件或者 JSON 二进制大对象。

### 2 ConfigMap 创建

#### 2.1 使用目录创建

`--from-file` 指定在目录下的所有文件都会被用在 ConfigMap 里面创建一个键值对，键的名字就是文件名，值就是文件的内容。

```shell
kubectl create configmap game-config --from-file=docs/user-guide/configmap
```

#### 2.2 使用文件创建

`--from-file` 这个参数可以使用多次，效果跟指定整个目录是一样的。

```shell
kubectl create configmap game-config --from-file=docs/user-guide/configmap/game.properteis
```

#### 2.3 使用字面量创建

```shell
kubectl create configmap special-config \
    --from-literal=special.how=very \
    --from-literal=special.type=charm
```

### 3 Pod 中使用 ConfigMap

#### 3.1 使用 ConfigMap 代替环境变量

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: special-config
data:
  special.how: very
  special.type: charm
---
apiVersion: v1
kind: Pod
metadata:
  name: special-pod
spec:
  containers:
    - name: special-container
      image: my-app:0.0.1
      command: ['/bin/bash', '-c', 'env']
      env:
        - name: SPECIAL_CONFIG_HOW
          valueFrom:
            configMapKeyRef:
              name: special-config
              key: special.how
```

#### 3.2 通过数据卷插件使用 ConfigMap

在数据卷里面使用这个 ConfigMap，有不同的选项。最基本的就是将文件填入数据卷，在这个文件中，键就是文件名，值就是文件内容。

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: special-config
data:
  special.how: very
  special.type: charm
---
apiVersion: v1
kind: Pod
metadata:
  name: special-pod
spec:
  containers:
    - name: special-container
      image: my-app:0.0.1
      command: ['/bin/bash', '-c', 'cat /etc/config/special.how']
      volumeMounts:
        - mountPath: /etc/config
          name: config-volume
  volumes:
    - name: config-volume
      configMap:
        name: special-config
```

## 八、Namespace

### 1 Namespace 概述

Namespace 在很多情况下用于实现多用户的资源隔离，通过将集群内部的资源对象分配到不同的 Namespace 中，形成逻辑上的分组，便于不同的分组在共享使用整个集群的资源同时还能被分别管理。Kubernetes 集群在启动后，会创建一个名为 default 的 Namespace，如果不特别指明 Namespace，则用户创建的 Pod、RC、Service 都将被系统创建到这个默认的名为 default 的 Namespace 中。

## 九、Service

### 1 Service 概述

Service 是 Kubernetes 最核心的概念之一，通过创建 Service，可以为一组具有相同功能的容器应用提供一个统一的入口地址，并且将请求负载分发到后端的各个容器应用上。

### 2 外部服务 Service

在某些特殊环境中，应用系统需要将一个外部数据库作为后端服务进行连接，或将另一个集群或 Namespace 中的服务作为服务的后端，这时可以通过创建一个无 Label Selector 的 Service 来实现。

```yaml
apiVersion: v1
kind: Service
metadata:
  name: my-service
spec:
  ports:
    - port: 3306
      protocol: TCP
      targetPort: 3306
---
apiVersion: v1
kind: Endpoints
subsets:
  - addresses:
      - ip: 192.192.192.6
    ports:
      - port: 3306
```

## 十、探针

### 1 livenessProbe（存活探针）

用于判断容器是否存活，即 Pod 是否为 Running 状态，如果 livenessProbe 探针探测到容器不健康，则 kubectl 将 kill 掉容器，并根据容器的重启策略决定是否重启。如果一个容器不包含 livenessProbe 探针，则 kubelet 认为容器的 livenessProbe 探针的返回值永远成功。

有时应用程序可能因为某些原因（后端服务故障等）导致暂时无法对外提供服务，但应用软件没有终止，导致 K8S 无法隔离有故障的 Pod，调用者可能会访问到有故障的 Pod，导致业务不稳定。K8S 提供 livenessProbe 来检测应用程序是否正常运行，并且对相应状况进行相应的补救措施。

### 2 readinessProbe（就绪探针）

用于判断容器是否启动完成，即容器的 Ready 是否为 True，可以接收请求，如果 readinessProbe 探测失败，则容器的 Ready 将为 False，控制器将此 Pod 的 Endpoint 从对应的 Service 的 Endpoint 列表中移除，从此不再将任何请求调度到此 Pod 上，直到下次探测成功。通过使用 readinessProbe 探针，Kubernetes 能够等待应用程序完全启动，然后才允许服务将流量发送到新副本。

比如使用 Tomcat 应用程序来说，并不是简单地说 Tomcat 启动成功就可以对外提供服务的，还需要等待 Spring 容器初始化，数据库连接池连接等等。对于 Spring Boot 应用，默认的 Actuator 带有 `/health` 接口，可以用来进行启动成功的判断。

### 3 探测方法

每类探针都支持三种探测方法。

#### 3.1 exec

通过执行命令来检查服务是否正常，针对复杂检测检测或无 HTTP 接口的服务，命令返回值为 0 则表示容器健康。

#### 3.2 httpGet

通过发送 HTTP 请求检测服务是否正常，返回 200-399 状态码则表示容器健康。

#### 3.3 tcpSocket

通过容器的 IP 和 Port 执行 TCP 检测，如果能够建立 TCP 连接，则表明容器健康。

### 4 示例

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: goproxy
  labels:
    app: goproxy
spec:
  containers:
    - name: goproxy
      image: k8s.gcr.io/goproxy:0.1
      ports:
        - containerPort: 8080
      readinessProbe:
        tcpSocket:
          port: 8080
        initialDelaySeconds: 5
        periodSeconds: 10
      livenessProbe:
        tcpSocket:
          port: 8080
        initialDelaySeconds: 15
        periodSeconds: 20
```

探针有许多可选字段，可以用来更加精确的控制 livenessProbe 和 readinessPorbe 两种探针的行为。这些参数包括：

| 参数                  | 说明                                                                                              |
| --------------------- | ------------------------------------------------------------------------------------------------- |
| `initialDelaySeconds` | 容器启动后第一次执行探测时需要等待多少秒。                                                        |
| `periodSeconds`       | 执行探测的频率。默认 10 秒，最小 1 秒。                                                           |
| `timeoutSeconds`      | 探测超时时间。默认 1 秒，最小 1 秒。                                                              |
| `successThreshold`    | 探测失败后，最少连续探测成功多少次才被认定为成功。默认 1，最小值 1。对于 livenessProbe 必须是 1。 |
| `failureThreshold`    | 探测成功后，最少连续探测失败多少次才被认定为失败。默认 3，最小值 1。                              |

## 十一、调度器

### 1 概述

一个容器平台的主要功能就是为容器分配运行时所需要的计算、存储和网络资源。容器调度系统负责选择在最合适的主机上启动容器，并且将它们关联起来。它必须能够自动的处理容器故障并且能够在更多的主机上自动启动更多的容器来应对更多的应用访问。

目前三大主流的容器平台 Swarm、Mesos 和 Kubernetes 具有不同的容器调度系统。

-   Swarm 的特点是直接调度 Docker 容器，并且提供和标准 Docker API 一致的 API。
-   Mesos 针对不同的运行框架采用相对独立的调度系统，其中 Marathon 框架提供了 Docker 容器的原生支持。
-   Kubernetes 采用了 Pod 和 Label 这样的概念把容器组合成一个个的互相存在依赖关系的逻辑单元。相关容器被组合成 Pod 后被共同部署和调度。形成服务。

相对来说，Kubernetes 采用这样的方式简化了集群范围内相关容器被共同调度的复杂性。换一种角度来看，Kubernetes 采用这种方式能够相对容易的支持更强大、更复杂的容器调度算法。

### 2 K8S 调度工作方式

Kubernetes 调度器作为集群的大脑，在如何提高集群的资源利用率、保证集群中服务的稳定运行中也会变得越来越重要。Kubernetes 的资源分为两种属性。

-   可压缩资源（例如 CPU 循环、Disk I/O 宽带）都是可以被限制和被回收的，对于一个 Pod 来说可以降低这些资源的使用量而不去杀掉 Pod。
-   不可压缩资源（例如内存、硬盘空间）一般来说不杀掉 Pod 就没法回收。未来 Kubernetes 会加入更多资源，例如网络带宽、存储 IOPS 的支持。

### 3 K8S 调度器

#### 3.1 概述

kube-scheduler 是 Kubernetes 系统的核心组件之一，主要负责整个集群资源的调度功能，根据特定的调度算法和策略，将 Pod 调度到最优的工作节点上面去，从而更加合理、更加充分的利用集群的资源，这也是选择使用 Kubernetes 一个非常重要的理由。

#### 3.2 调度流程

默认情况下，kube-scheduler 提供的默认调度器能够满足我们绝大多数的要求，之前接触的示例也基本上用的默认的策略，都可以保证我们的 Pod 可以被分配到资源充足的节点上运行。但是在实际的线上项目中，可能我们自己会比 Kubernetes 更加了解我们自己的应用，比如我们希望一个 Pod 只能运行在特定的几个节点上，或者这几个及诶点只能用来运行特定类型的应用，这就需要我们的调度器能够可控。

kube-scheduler 是 Kubernetes 的调度器，它的主要作用就是根据特定的调度算法和调度策略将 Pod 调度到合适的 Node 节点上去，是一个独立的二进制程序，启动之后会一直监听 API Server，获取到 PodSpec.NodeName，对每个 Pod 都会和藏家一个 Binding。

调度主要分为以下几个部分：

-   首先是预选过程，过滤掉不满足条件的节点，这个过程称为 Predicates。

-   然后是优选过程，对通过的节点按照优先级排序，这个过程称为 Priorities。

-   最后从中选择优先级最高的节点，如果中间任何一步有错误，就直接返回错误。

Predicates 阶段首先遍历全部节点，过滤掉不满足条件的节点，属于强制性规则，这一阶段输出的所有满足要求的 Node 将被记录并作为第二阶段的输入，如果所有的节点都不满足条件，那么 Pod 将会一直处于 Pending 状态，直到节点满足条件，在这期间调度器会不断的重试。

所以我们在部署应用的时候，如果发现有 Pod 一直处于 Pending 状态，那么就是没有满足调度条件的节点，这个时候可以去检查下节点资源是否可用。

Priorities 阶段即再次对节点进行筛选，如果有多个节点都满足条件的话，那么系统会按照节点的优先级大小对节点进行排序，最后选择优先级最高的节点来部署 Pod 应用。

而 Priorities 优先级是由一系列键值对组成的，键是该该优先级的名称，值是它的权重值，同样，我们这里给大家列举几个具有代表性的选型。

| 选项                   | 说明                                                                                                                                                                                                                                    |
| ---------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| LeastRequestedPriority | 通过计算 CPU 和内存的使用率来决定权重，使用率越低权重越高。当然正常也是资源使用率越低权重越高，能给别的 Pod 运行的可能性就越大。                                                                                                        |
| SelectorSpreadPriority | 为了更好的高可用，对同属一个 Deployment 或者 RC 下面的的多个 Pod 副本，尽量调度到多个不同的节点上，当一个 Pod 被调度的时候，会先去查找该 Pod 对应的 Controller，然后查看该 Controller 下面的已存在的 Pod，运行 Pod 越少的节点权重越高。 |
| ImageLocalityPriority  | 就是如果某个节点上已经有要使用的镜像了，镜像总大小值越大，权重将就越高。                                                                                                                                                                |
| NodeAffinityPriority   | 这个就是根据节点的亲和性来计算一个权重值。                                                                                                                                                                                              |

### 4 节点资源调度亲和性

节点亲和性规则：硬亲和性 required、软亲和性 preferred。

硬亲和性规则不满足时，Pod 会置于 Pending 状态，软亲和性规则不满足时，会选择一个不匹配的节点。当节点标签改变而不再符合此节点亲和性规则时，不会将 Pod 从该节点移出，仅对新建的 Pod 对象生效。

#### 4.1 节点硬亲和性

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: my-app
spec:
  containers:
    - { name: 'my-app', image: 'my-app:0.0.1' }
  affinity:
    nodeAffinity:
      requiredDuringSchedulingIgnoredDuringExecution:
        nodeSelectorTerms:
          - matchExpressions:
              - { key: 'zone', operator: 'In', values: ['foo'] }
```

#### 4.2 节点软亲和性

柔性控制逻辑，当条件不满足时，能接受别编排与其它不符合条件的节点之上，权重 weight 定义优先级，1-100 值越大优先级余越高。

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-app
spec:
  selector:
    matchLabels: { app: 'my-app' }
  replicas: 2
  template:
    metadata:
      name: my-app
      labels: { app: 'my-app' }
    spec:
      containers:
        - { name: 'my-app', image: 'my-app:0.0.1' }
      affinity:
        nodeAffinity:
          preferredDuringSchedulingIgnoredDuringExecution:
            - weight: 60
              preference:
                matchExpressions:
                  - { key: 'zone', operator: 'In', values: ['foo'] }
            - weight: 30
              preference:
                matchExpressions:
                  - { key: 'ssd', operator: 'Exists', values: [] }
```

### 5 Pod 资源调度调度亲和性

#### 5.1 Pod 硬亲和调度

Pod 硬亲和性描述一个 Pod 与具有某个特征的现存 Pod 运行位置的依赖关系，即需啊哟事先存在被依赖的 Pod 对象。

```shell
kubectl run tomcat --labels="app=tomcat" --image=tomcat:latest
```

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: my-app
spec:
  containers:
    - name: my-app
      image: my-app:0.0.1
  affinity:
    podAffinity:
      requiredDuringSchedulingIgnoredDuringExecution:
        - labelSelector:
            matchExpressions:
              - { key: 'app', operator: 'In', values: ['tomcat'] } # 当前 Pod 要跟标签为 app 值为 tomcat 的 Pod 在一起
          topologyKey: kubernetes.io/hostname # 根据挑选出来的 Pod 所有节点的 hostname 作为同一位置的判定
```

#### 5.2 Pod 软亲和调度

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-app
spec:
  selector:
    matchLabels: { app: 'my-app' }
  replicas: 3
  template:
    metadata:
      name: my-app
      labels: { app: 'my-app' }
    spec:
      containers:
        - { name: 'my-app', image: 'my-app:0.0.1' }
      affinity:
        podAffinity:
          preferredDuringSchedulingIgnoredDuringExecution:
            - weight: 80
              podAffinityTerm:
                topologyKey: zone
                labelSelector:
                  matchExpressions:
                    - { key: 'app', operator: 'In', values: ['cache'] }
            - weight: 20
              podAffinityTerm:
                topologyKey: zone
                labelSelector:
                  matchExpressions:
                    - { key: 'app', operator: 'In', values: ['db'] }
```

#### 5.3 Pod 反亲和策略

Pod 反亲合调度用于分散同一类应用，调度至不同的区域、机架或节点等，将 `spec.affinity.podAffinity` 替换为 `sepc.affinity.podAntiAffinity`。反亲和策略也分为柔性约束和强制约束。

### 6 污点和容忍度

污点 taints 是定义在节点上的键值型数据，用于让节点拒绝将 Pod 调度运行与其上，除非 Pod 有接纳污点的容忍度。容忍度 tolerations 是定义在 Pod 上的键值型数据，用于配置可容忍的污点，且调度器将 Pod 调度至其能容忍该节点污点的节点上或没有污点的节点上。

Kubernetes 使用预选策略和优选函数完成该机制。

节点亲和性使得 Pod 对象被吸引到一类特定的节点（nodeSelector 和 affinity），污点提供让节点排斥特定 Pod 对象的能力。

#### 6.1 定义污点和容忍度

污点定义于 `nodes.spec.taints`，容忍度定义于 `pods.spec.tolerations`。

#### 6.2 定义排斥等级

-   NoSchedule，不能容忍，但仅影响调度过程，已调度上去的 Pod 不受影响，仅对新增加的 Pod 生效。
-   PreferNoSchedule，柔性约束，节点现存 Pod 不受影响，如果实在是没有符合的节点，也可以调度上来。
-   NoExecute，不能容忍，当污点变动时，Pod 对象会被驱逐。

#### 6.3 在 Pod 上定义容忍度

-   等值比较，容忍度与污点在 key、value、effect（排斥等级） 三者完全匹配。
-   存在性判断，key、effect 完全匹配，value 使用空值。

#### 6.4 管理节点上的污点

同一个键值数据，effect 不同，也属于不同的污点。

-   给节点添加污点

    ```shell
    kubectl taint node node2 node-type=production:NoSchedule
    ```

-   查看节点污点

    ```shell
    kubectl get nodes <node-name> -o go-template={{.spec.taints}}
    ```

-   删除节点污点

    ```shell
    kubectl taint node node1 node-type=production:NoSchedule

    # 删除所有污点
    kubectl patch nodes node1 -p '{"spec": {"taints": []}}'
    ```

#### 6.5 问题节点标识

K8S 核心组件通常都容忍此类污点。

| 污点                                             | 说明                                                                         |
| ------------------------------------------------ | ---------------------------------------------------------------------------- |
| `node.kubernetes.io/not-ready`                   | 节点进入 NotReady 状态时自动添加。                                           |
| `node.alpha.kubernetes.io/unreachable`           | 节点进入 NotReachable 状态时自动添加。                                       |
| `node.kubernetes.io/out-of-disk`                 | 节点进入 OutOfDisk 状态时自动添加。                                          |
| `node.kubernetes.io/memory-pressure`             | 节点内存资源面临压力。                                                       |
| `node.kubernetes.io/disk-pressure`               | 节点磁盘面临压力                                                             |
| `node.kubernetes.io/network-unavailable`         | 节点网络不可用                                                               |
| `node.cloudprovider.kubernetes.io/uninitialized` | kubelet 由外部云环境程序启动时自动添加，待到控制器初始化此节点时再将其删除。 |

### 7 Pod 优先级和抢占式调度

优选级会影响节点上 Pod 的调度顺序和驱逐次序。一个 Pod 对象无法被调度时，调度器会尝试抢占（驱逐）较低优先级的 Pod 对象，以便可以调度当前 Pod。

### 8 Pod 优先级和抢占机制默认处于禁用状态

启用：同时为 kube-apiserver、kube-scheduler、kubelet 程序的 `--feature-gates` 添加 `PodPriority=true`。

使用：事先创建优先级类别，并在创建 Pod 资源时通过 `priorityClassName` 属性指定所属优选级类别。

## 十二、集群安全机制 RBAC

### 1 基本概念

RBAC 在 K8S v1.5 中引入，在 v1.6 版本时升级为 Beta 版本，并成为 kubeadm 安装方式下的默认选型，相对于其它访问控制方式，新的 RBAC 具有如下优势：

-   对集群中的资源和非资源权限均有完整的覆盖。
-   整个 RBAC 完全由几个 API 对象完成，同其它 API 对象一样，可以用 kubectl 或 API 进行操作。
-   可以在运行时进行调整，无需重启 API Server。

要使用 RBAC 授权，需要在 API Server 的启动参数中加上 `--authorization-mode=RBAC`。

### 2 RBAC 的 API 资源对象说明

RBAC 引入了 4 个新的顶级资源对象：Role、ClusterRole、RoleBinding、ClusterRoleBinding。同其它 API 资源对象一样，用户可以使用 kubectl 或者 API 调用等方式操作这些资源对象。

#### 2.1 Role（角色）

一个角色就是一组权限的集合，这里的权限都是许可形式，不存在拒绝的规则。在一个命名空间中，可以用 Role 来定义一个角色，如果是集群级别的，就需要使用 ClusterRole 了。角色只能对命名空间内的资源进行授权，下面的例子中定义的角色具备读取 Pod 的权限。

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  namespace: local
  name: pod-reader
rules:
  - apiGroups: [''] # 空字符串表示核心 API 群
    resources: ['pods'] # 支持的资源对象列表，例如：pods、deployments、jobs 等
    verbs: ['get', 'watch', 'list'] # 对资源对象的操作方法列表，例如：get、watch、list、delete、replace 等
```

#### 2.2 ClusterRole（集群角色）

集群角色除了具有和角色一致的命名空间内资源的管理能力，因其集群级别的范围，还可以用于以下特殊元素的授权。

-   集群范围内的资源，例如 Node。
-   非资源型的路径。
-   包含全部命名空间的资源，例如 Pods。

下面的集群角色可以让用户有权访问任意一个或所有命名空间的 Secrets。

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  # ClusterRole 不受限于命名空间
  name: secret-reader
rules:
  - apiGroups: ['']
    resources: ['secrets']
    verbs: ['get', 'watch', 'list']
```

#### 2.3 RoleBinding（角色绑定）和 ClusterRoleBinding（集群角色绑定）

角色绑定或集群角色绑定用来把一个角色绑定到一个目标上，绑定目标可以是 User Group 或者 Service Account。使用 RoleBinding 为某个命名空间授权，ClusterRoleBinding 为集群范围内授权。

RoleBinding 可以引用 Role 进行授权，下例中的 RoleBinding 将在 default 命名空间把 pod-reader 角色授予用户 jane，可以让 jane 用户读取 default 命名空间的 Pod。

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  namespace: default
  name: read-pods
subjects:
  - kind: User
    name: jane
    apiGroup: rbac.authorization.k8s.io
roleRef:
  apiGroup: Role
  kind: pod-reader
  name: rbac.authorization.k8s.io
```

RoleBinding 也可以引用 ClusterRole，对属于同一命名空间内 ClusterRole 定义的资源主体进行授权。一种常见的做法是集群管理员为集群范围预先定义好一组角（ClusterRole），然后再各个命名空间中重复使用这些 ClusterRole。

使用 RoleBinding 绑定集群角色 secret-reader，使 dave 只能读取 development 命名空间中的 secret：

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  namespace: development
  name: read-pods
subjects:
  - kind: User
    name: dave
    apiGroup: rbac.authorization.k8s.io
roleRef:
  apiGroup: ClusterRole
  kind: secret-reader
  name: rbac.authorization.k8s.io
```

集群角色绑定中的角色只能是集群角色，用于进行集群级别或者对所有命名空间都生效授权。允许 manager 组的用户读取任意 Namespace 中的 secret：

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: read-secrets-global
subjects:
  - kind: Group
    name: manager
    apiGroup: rbac.authorization.k8s.io
roleRef:
  apiGroup: ClusterRole
  kind: secret-reader
  name: rbac.authorization.k8s.io
```

### 3 对资源的引用方式

多数资源可以用其名称的字符串来表达，也就是 Endpoint 中的 URL 相对路径，例如 Pods，然后，某些 kubernetes API 包含下级资源，例如 Pod 的日志，Pod 日志的 Endpoint 是`GET /api/v1/namsepaces/{namespace}/pods/{name}/log`。

Pod 是一个命名空间内的资源，log 就是一个下级资源。要在一个 RBAC 角色中体现，则需要用斜线来分隔资源和下级资源。若想授权让某个主体同时能够读取 Pod 和 Pod log，则可以配置 resources 为一个数组。

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  namespace: default
  name: pod-and-logs-reader
rules:
  - apiGroups: ['']
    resources: ['pods', 'pods/log']
    verbs: ['get', 'list']
```

资源还可以通过名字进行引用。在指定 ResourceName 后，使用 get、delete、update、patch 动词的请求，就会被限制在这个资源实例范围内。例如下面的声明让一个主体只能对一个叫 my-configmap 的 ConfigMap 进行 get 和 update 操作。

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  namespace: default
  name: configmap-updater
rules:
  - apiGroups: ['']
    resources: ['configmap']
    resourceNames: ['my-configmap']
    verbs: ['get', 'update']
```

### 4 常见的角色示例

-   允许读取核心 API 组中 Pod 的资源

    ```yaml
    rules:
      - apiGroups: ['']
        resources: ['pods']
        verbs: ['get', 'list', 'watch']
    ```

-   允许读写 extensions 和 app 两个 API 组中的 deployment 资源

    ```yaml
    rules:
      - apiGroups: ['extensions', 'app']
        resources: ['deployments']
        verbs: ['get', 'list', 'watch', 'create', 'update', 'patch', 'delete']
    ```

-   允许读写 Pods 和 Jobs

    ```yaml
    rules:
      - apiGroups: ['']
        resources: ['pods']
        verbs: ['get', 'list', 'watch']
      - apiGroups: ['batch', 'extensions']
        resources: ['jobs']
        verbs: ['get', 'list', 'watch', 'create', 'update', 'patch', 'delete']
    ```

-   允许读取一个名为 my-config 的 ConfigMap（必须绑定到一个 RoleBinding 来限制到一个 Namespace 下的 ConfigMap）

    ```yaml
    rules:
      - apiGroups: ['']
        resources: ['configmaps']
        verbs: ['get']
    ```

-   读取核心组的 Node 资源（Node 属于集群级别的资源，必须放在 ClusterRole 中，并使用 ClusterRoleBinding 进行绑定）

    ```yaml
    rules:
      - apiGroups: [ '' ]
        resources: [ 'nodes' 'list', 'watch' ]
        verbs: [ 'get', ]
    ```

-   允许对非资源端点 `/healthz` 及其所有子路径进行 GET/POST 操作（必须使用 ClusterRole 和 ClusterRoleBinding）

    ```yaml
    rules:
      - nonResourceURLs: ['/healthz', '/healthz/*']
        verbs: ['get', 'post']
    ```

### 5 常见的角色绑定

-   用户名 `Alice@example.com`

    ```yaml
    subjects:
      - kind: User
        name: 'Alice@example.com'
        apiGroup: rbac.authorization.k8s.io
    ```

-   组名 `frontend-admins`

    ```yaml
    subjects:
      - kind: Group
        name: 'frontend-admins'
        apiGroup: rbac.authorization.k8s.io
    ```

-   kube-admin 命名空间下的默认 ServiceAccount

    ```yaml
    subjects:
      - kind: ServiceAccount
        namespace: kube-system
        name: default
        apiGroup: rbac.authorization.k8s.io
    ```

-   qa 命名空间中的所有 ServiceAccount

    ```yaml
    subjects:
      - kind: ServiceAccount
        name: system:serviceaccounts:qa
        apiGroup: rbac.authorization.k8s.io
    ```

-   所有 ServiceAccount

    ```yaml
    subjects:
      - kind: Group
        name: system:serviceaccounts
        apiGroup: rbac.authorization.k8s.io
    ```

-   所有认证用户

    ```yaml
    subjects:
      - kind: Group
        name: system:authentication
        apiGroup: rbac.authorization.k8s.io
    ```

-   所有未认证用户

    ```yaml
    subjects:
      - kind: Group
        name: system:unauthentication
        apiGroup: rbac.authorization.k8s.io
    ```

-   全部用户

    ```yaml
    subjects:
      - kind: Group
        name: system:authentication
        apiGroup: rbac.authorization.k8s.io
      - kind: Group
        name: system:unauthentication
        apiGroup: rbac.authorization.k8s.io
    ```

### 6 默认的角色和角色绑定

API Server 会创建一套默认的 ClusterRole 和 ClusterRoleBinding 对象，其中很多是以 `system:` 为前缀的，以表明这些资源属于基础架构，对这些对象的改动可能造成集群故障。

所有默认的 ClusterRole 和 RoleBinding 都会用标签 `kubernetes.io/bootstrapping=rbac-defaults` 进行标记。

### 7 授权注意事项

#### 预防提权和授权初始化

RBAC API 拒绝用户利用编辑角色或者角色绑定的方式进行提权。这一限制是在 API 层面作出，因此即使 RBAC 没有启用也仍然有效。

用户只能在拥有一个角色的所有权限，且与该角色的生效范围一致的前提下，才能对角色进行创建和更新。例如用户 user-1 没有列出集群中所有 Secret 的权限，就不能创建具有这一权限的集群角色。

## 十三、性能监控平台

### 1 概述

开源软件 cAdvisor（Container Advisor）用于监控所在节点的容器运行状态，当前已经被默认集成到 kubelet 组件内，默认使用 TCP 4194 端口。在大规模容器集群，一般使用 Heapster + InfluxDB + Grafana 平台实现集群性能数据的采集、存储与展示。

### 2 展示准备

#### 2.1 基础环境

Kubernetes + Heapster + InfluxDB + Grafana。

#### 2.3 原理

##### 2.3.1 Heapster

集群中各 Node 节点的 cAdvisor 的数据采集汇聚系统，通过调用 Node 上 kubelet 的 API，再通过 kubelet 调用 cAdvisor 的 API 来采集所在节点上所有容器的性能和数据。Heapster 对性能数据进行聚合，并将结构保存到后端存储系统，Heapster 支持多种后端存储系统，如 Memory、Influxed 等。

##### 2.3.2 InfluxDB

分布式时序数据库（每条记录都带有时间戳属性），主要用于实时数据采集，时间跟踪记录，存储时间图表，原始数据等。InfluxDB 提供 REST API 用于数据的存储与查询。

##### 2.3.3 Grafana

通过 Dashboard 将 InfluxDB 中的时序数据展现成图表或曲线等形式，便于查看集群运行状态。

## 十四、Helm

### 1 Helm 引入

K8S 上的应用对象，都是由特定的资源描述组成，包括 Deployment、Service 等。都保存各自文件中或者集中写到一个配置文件，然后 `kubectl apply -f` 部署。

如果应用只有一个或几个这样的服务组成，上面部署方式足够了。而对于一个复杂的应用，会有很多类似上面的资源描述文件，例如微服务架构应用，组成应用的服务可能多达十个、几十个。如果有更新或回滚应用的需求，可能要修改和维护所涉及的大量资源文件，而这种组织和管理应用的方式就显得力不从心了。且由于缺少对发布过的应用版本管理和控制，使 Kubernetes 上的应用维护和更新等面临诸多的挑战，主要面临以下问题：

-   如何将这些服务作为一个整体管理

-   这些资源文件如何高效复用

-   不支持应用级别的版本管理

### 2 Helm 介绍

Helm 是一个 Kubernetes 的包管理工具，就像 Linux 下的包管理器，如 yum/apt 等，可以很方便的将之前打包好的 yaml 文件部署到 Kubernetes 上。

Helm 有三个重要概念：

-   helm：一个命令行客户端工具，主要用户 Kubernetes 应用 Chart 的创建、打包、发布和管理。

-   Chart：应用描述，一系列用于描述 K8S 资源相关文件的集合。

-   Release：基于 Chart 的部署实体，一个 Chart 被 Helm 运行后将会生成对应的一个 Release，将在 K8S 中创建出真实运行的资源对象。

### 3 Helm v3 变化

2019 年 11 月 13 日，Helm 团队发布 Helm v3 的第一个版本，该版本的主要变化如下：

1. 最明显的变化就是 Tiller 的删除。

2. Release 名称可以在不同命名空间重用。

3. 支持将 Chart 推送至 Docker 镜像仓库中。

4. 其它。

### 4 Helm 客户端

#### 4.1 配置国内 Chart 仓库

-   `https://mirror.azure.cn/kubernetes/charts/`

-   `https://kubernetes.oss-cn-hangzhou.aliyuncs.com/charts`

-   `https://hub.kubeapps.com/charts/incubator`

##### 4.1.1 添加存储库

```shell
helm repo add stable https://mirror.azure.cn/kubernetes/charts

helm repo add aliyun https://kubernetes.oss-cn-hangzhou.aliyuncs.com/charts

helm repo update
```

##### 4.1.2 查看配置的存储库

```shell
helm repo list

helm search repo stable
```

##### 4.1.3 删除存储库

```shell
helm repo remove aliyun
```

### 5 Helm 基本使用

#### 5.1 使用 Helm 部署一个应用

```shell
helm search repo mysql

helm show chart stable/mysql

helm install mysql stable/mysql

helm list

helm status mysql
```

#### 5.2 安装前自定义 Chart 配置选项

自定义选项是因为并不是所有的 Chart 都能按照默认配置运行成功，可能会需要一些环境依赖，例如 PV。所以我们需要自定义 Chart 配置选项，安装过程中有两种方法可以传递配置数据：

-   `--values`（或 `-f`）：指定带有覆盖的 YAML 文件。可以指定多次，最右边的文件优先。

-   `--set`：在命令行上指定替代。

如果两者都用，`--set` 优先级更高。

###
