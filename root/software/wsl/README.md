# WSL

## 常用命令

> - https://docs.microsoft.com/zh-cn/windows/wsl/basic-commands

- 查看 WSL 状态

  ```bash
  wsl --status
  ```

- 查看可用发行版

  ```bash
  wsl --list --online
  ```

- 安装指定发行版

  ```bash
  wsl --install --distribution <Distribution Name>
  ```

- 卸载发行版

  > 尽管可以通过 Microsoft Store 安装 Linux 发行版，但无法通过 Store 将其卸载。

  ```bash
  wsl --unregister <DistributionName>
  ```

## 使用 systemd

> - https://github.com/nullpo-head/wsl-distrod
> - https://github.com/nullpo-head/wsl-distrod/blob/main/docs/references.md#install-and-run-multiple-distros-at-the-same-time

```bash
distrod_wsl_launcher -d <new_distrod>
```
