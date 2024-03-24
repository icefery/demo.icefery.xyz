---
title: '在 Mac 上卸载 Python'
---

### 参考文档

-   https://docs.python.org/zh-cn/3/using/mac.html

### 移除应用

### 移除框架

```shell
sudo rm -rf /Library/Frameworks/Python.framework
```

### 移除软链接

```shell
ls -l /usr/local/bin | grep 'Python.framework'
```
