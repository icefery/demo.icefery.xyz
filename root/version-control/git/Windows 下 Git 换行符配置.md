## 最优配置

为了最大限度兼容 macOS 以及 Linux，需要：

1. 提交时转换为 LF，检出时不转换
2. 拒绝提交包含混合换行符的文件

```shell
git config --global core.autocrlf input
git config --global core.safecrlf true
```

## 参数说明

### `autocrlf`

```shell
# 提交时转换为 LF 检出时转换为 CRLF
git config --global core.autocrlf true

# 提交时转换为 LF 检出时不转换
git config --global core.autocrlf input

# 提交和检出时均不转换
git config --global core.autocrlf false
```

### `safecrlf`

```shell
# 拒绝提交包含混合换行符的文件
git config --global core.safecrlf true

# 允许提交包含混合换行符的文件
git config --global core.safecrlf false

# 提交包含混合换行符的文件时给出警告
git config --global core.safecrlf warn
```

## 批量将 CRLF 转换成 LF

如果在 Windows 下不慎将部分文本的换行符写为 CRLF，可以使用 `dos2unix` 工具修复回 LF。

```shell
find . -type f | xargs dos2unix
```
