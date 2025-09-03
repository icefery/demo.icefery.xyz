# Knative

## 一、快速开始

### 1.1 Function

```shell
# 创建函数
func create f1 --language=typescript --template=http

cd f1

# 构建函数
func build --builder=s2i --image=ghcr.io/icefery/f1:0.0.1 --platform=linux/arm64

# 测试函数
func run

# 部署函数
func deploy

# 调用函数
func invoke

# 查看函数
func list
```

### 1.2 Serving

### 1.3 Eventing
