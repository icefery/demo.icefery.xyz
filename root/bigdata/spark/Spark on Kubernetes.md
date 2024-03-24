## Spark on Native Kubernetes

### 架构

![](https://spark.apache.org/docs/latest/img/k8s-cluster-mode.png)

### RBAC

```shell
kubectl create namespace spark
kubectl --namespace spark create serviceaccount spark
kubectl create clusterrolebinding spark-role --clusterrole=edit --serviceaccount=spark:spark
```

### 提交作业

#### Client 模式

```shell
SPARK_VERSION="3.3.2"
SPARK_DRIVER_HOST="192.168.8.101"

bin/spark-submit \
  --master k8s://https://127.0.0.1:6443 \
  --deploy-mode client \
  --name spark-pi \
  --class org.apache.spark.examples.SparkPi \
  --conf spark.driver.host=${SPARK_DRIVER_HOST} \
  --conf spark.executor.instances=3 \
  --conf spark.kubernetes.namespace=spark \
  --conf spark.kubernetes.authenticate.driver.serviceAccountName=spark \
  --conf spark.kubernetes.container.image=apache/spark:v${SPARK_VERSION} \
  examples/jars/spark-examples_2.12-${SPARK_VERSION}.jar
```

#### Cluster 模式

```shell
SPARK_VERSION="3.3.2"

bin/spark-submit \
  --master k8s://http://127.0.0.1:6443 \
  --deploy-mode cluster \
  --name spark-pi \
  --class org.apache.spark.examples.SparkPi \
  --conf spark.executor.instances=3 \
  --conf spark.kubernetes.namespace=spark \
  --conf spark.kubernetes.authenticate.driver.serviceAccountName=spark \
  --conf spark.kubernetes.container.image=apache/spark:v${SPARK_VERSION} \
  local:///opt/spark/examples/jars/spark-examples_2.12-${SPARK_VERSION}.jar
```

## 收藏

### [Spark on K8s 在茄子科技的实践](https://zhuanlan.zhihu.com/p/620805734)
