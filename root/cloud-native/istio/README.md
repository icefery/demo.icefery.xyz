# Istio

## 一、快速开始

### 1.1 安装

```shell
# 下载解压
curl -L https://istio.io/downloadIstio | ISTIO_VERSION=1.17.1 TARGET_ARCH=x86_64 sh -
mv istio-1.17.1 /opt/env/istio
cd /opt/env/istio

# 配置环境变量
tee /etc/profile.d/istio.sh <<- "EOF"
export ISTIO_HOME=/opt/env/istio
export PATH=$ISTIO_HOME/bin:$PATH
EOF

# 配置命令自动补全
cp tools/istioctl.bash /etc/bash_completion.d

# 刷新环境变量
source /etc/profile
```

```shell
# 安装 istio
istioctl install --set profile=demo -y

# 配置自动注入
kubectl label namespace default istio-injection=enabled

# 查看网关地址
export INGRESS_HOST=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.status.loadBalancer.ingress[0].ip}')
export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].port}')
export GATEWAY_URL=$INGRESS_HOST:$INGRESS_PORT

# 安装插件
kubectl apply -f samples/addons
kubectl rollout status deployment/kiali -n istio-system
istioctl dashboard kiali --address=$INGRESS_HOST
```

### 1.2 部署 Bookinfo 示例应用

```shell
# 部署 bookinfo
kubectl apply -f samples/bookinfo/platform/kube/bookinfo.yaml

# 创建 istio Gateway
kubectl apply -f samples/bookinfo/networking/bookinfo-gateway.yaml

# 验证
istioctl analyze
```

### 1.3 测试

#### 1.3.1 访问应用程序

```shell
echo "http://$GATEWAY_URL/productpage"
```

#### 1.3.2 批量发送请求

```shell
for i in $(seq 1 100); do curl -s -o /dev/null "http://$GATEWAY_URL/productpage"; done
```

### 1.4 卸载

```shell
# 清理 bookinfo
NAMESPACE=default samples/bookinfo/platform/kube/cleanup.sh

# 卸载 istio
kubectl delete -f samples/addons
istioctl uninstall -y --purge

# 清理命名空间
kubectl delete namespace istio-system
kubectl label namespace default istio-injection-
```

## 二、架构

![](https://istio.io/latest/zh/docs/ops/deployment/architecture/arch.svg)

Istio 服务网格从逻辑上分为数据平面和控制平面。

-   数据平面由一组代理（Envoy）组成，被部署位 Sidecar。这些代理负责协调和控制微服务之间的所有网络通信。它们还收集和报告所有网格流量的遥测数据。
-   控制平面管理并配置代理来进行流量路由。

### 2.1 组件

#### 2.1.1 Envoy

Istio 使用 Envoy 代理的扩展版本。Envoy 是用 C++ 开发的高性能代理，用于协调服务网格中所有服务的入站和出站流量。Envoy 代理是唯一与数据平面流量交互的 Istio 组件。

Envoy 代理被部署位服务的 Sidecar，在逻辑上位服务增加了 Envoy 的许多内置特性，例如：

-   动态服务发现
-   负载均衡
-   TLS 终端
-   HTTP/2 与 gRPC 代理
-   熔断器
-   健康检查
-   基于百分比流量分割的分阶段发布
-   故障注入
-   丰富的指标

这种 Sidecar 部署允许 Istio 可以执行策略决策，并提取丰富的遥测数据，接着将这些数据发送到监控系统以提供有关整个网络行为的信息。

Sidecar 代理模型还允许您向现有的部署添加 Istio 功能，而不需要重新设计架构或重写代码。

由 Envoy 代理启用的一些 Istio 功能和任务包括：

-   流量控制功能：通过丰富的 HTTP、gRPC、WebSocket 和 TCP 流量路由规则来执行细粒度的流量控制。
-   网络弹性特性：重试设置、故障转移、熔断器和故障注入。
-   安全性和身份认证特性：执行安全性策略，并强制实行通过配置 API 定义的访问控制和速率限制。
-   基于 WebAssembly 的可插拔扩展模型，允许通过自定义策略执行和生成网络流量的遥测。

#### 2.1.2 Istiod

Istiod 提供服务发现、配置和证书管理。

Istiod 将控制流量行为的高级路由规则转换为 Envoy 特定的配置，并在运行时将其传播给 Sidecar。Pilot 提取了特定平台的服务发现机制，并将其综合为一种标准格式，任何服务 Envoy API 的 Sidecar 都可以使用。

Istio 可以支持发现多种环境，入 Kubernetes 或 VM。

您可以使用 Istio 流量管理 API 让 Istiod 重新构造 Envoy 的配置，以便服务网格中的流量进行更精细的控制。

Istiod 安全通过内置的身份和凭证管理，实现了强大的服务对服务和终端用户认证。您可以使用 Istio 来升级服务网络中未加密的流量。使用 Istio，运营商可以基于服务身份而不是相对不稳定的第 3 层或第 4 层网络标识符来执行策略。此外，您可以使用 Istio 的授权功能控制谁可以访问您的服务。

Istiod 充当证书授权（CA），生成证书以允许在数据平面中进行安全的 mTLS 通信。
