# fstab

## 收藏

#### 格式

```plain
<文件系统> <挂载点> <文件系统类型> <挂载选项> <转储选项> <自检选项>
```

#### 含义

1. **文件系统（<文件系统>）**：

    - 这是你要挂载的设备或文件系统的名称。可以是块设备（如 `/dev/sda1`）、UUID（如 `UUID=1234-5678`）、LABEL（如 `LABEL=mydisk`）或网络文件系统（如 `//server/share`）。
    - 例如：`/dev/sda1`、`UUID=1234-5678`、`LABEL=root`

2. **挂载点（<挂载点>）**：

    - 这是文件系统在系统中的挂载位置。挂载点是一个目录，文件系统将被挂载到这个目录下。
    - 例如：`/`（根文件系统）、`/home`、`/mnt/data`

3. **文件系统类型（<文件系统类型>）**：

    - 指定要挂载的文件系统类型，如 `ext4`、`xfs`、`btrfs`、`nfs`、`vfat` 等。
    - 例如：`ext4`、`xfs`、`nfs`

4. **挂载选项（<挂载选项>）**：

    - 这是一个逗号分隔的选项列表，用于控制文件系统的挂载行为。这些选项可以指定是否读写、是否启用某些功能等。
    - 常见的选项包括：`defaults`（默认选项）、`ro`（只读）、`rw`（读写）、`noatime`（不更新时间戳）、`noexec`（不允许执行文件）等。
    - 例如：`defaults`、`rw,noatime`、`ro`

5. **转储选项（<转储选项>）**：

    - 这个字段是一个数字，用于指定文件系统是否需要进行备份（通常通过 `dump` 工具）。值 `0` 表示不进行备份，值 `1` 表示进行备份。
    - 例如：`0`（不备份）、`1`（备份）

6. **自检选项（<自检选项>）**：
    - 这个字段是一个数字，用于指定文件系统在系统启动时进行自检的顺序。`0` 表示不进行自检，`1` 表示根文件系统，`2` 表示其他文件系统。
    - 例如：`0`（不自检）、`1`（根文件系统自检）、`2`（其他文件系统自检）

#### 示例

```shell
# <file system> <dir>      <type> <options>                   <dump> <pass>
UUID=1234-5678  /          ext4   defaults                    0      1
/dev/sda2       /home      xfs    defaults                    0      2
/dev/sdb1       /mnt/data  vfat   rw,noatime                  0      0
//server/share  /mnt/share cifs   username=user,password=pass 0      0
```

-   第一行挂载了一个 `ext4` 文件系统到根目录 `/`，使用默认选项，且在启动时进行自检。
-   第二行挂载了一个 `xfs` 文件系统到 `/home`，也使用默认选项，并在启动时自检。
-   第三行挂载了一个 `vfat` 文件系统到 `/mnt/data`，使用读写和不更新时间戳的选项，不进行自检。
-   第四行挂载了一个 CIFS 网络文件系统到 `/mnt/share`，并提供了用户名和密码。