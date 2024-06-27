# Linux

## 镜像源

### 中科大

> [配置生成器](https://mirrors.ustc.edu.cn/repogen/)

## 收藏

-   将相对路径存入变量

    ```shell
    export SAME_IMAGE=$(cd ../../same-image && pwd)
    ```

-   查看端口占用及进程

    ```shell
    netstat -tulnep

    ss -tu
    ```

-   创建交换分区

    ```shell
    sudo fallocate --length 8G /swapfile
    sudo chmod 600 /swapfile
    sudo mkswap /swapfile
    sudo swapon /swapfile
    echo '/swapfile swap swap defaults 0 0' | sudo tee -a /etc/fstab
    ```

### [systemV 和 systemd 的理解](https://blog.csdn.net/chengziwang/article/details/112434240)

### [Linux 文件系统-XFS 收缩与扩展](https://blog.csdn.net/baidu_39459954/article/details/89446794)

### [ext4 和 xfs 文件系统的扩容和收缩](https://www.cnblogs.com/hgzero/p/14193427.html)

### [sed 删除某一行\_linux -- sed 命令](https://blog.csdn.net/weixin_34711401/article/details/112290386)

### [设计 shell 脚本选项：getopt（转）](https://www.cnblogs.com/ajianbeyourself/p/12454161.html)

### [报错 kernel:NMI watchdog: BUG: soft lockup - CPU#0 stuck for 26s](https://blog.csdn.net/weixin_46399792/article/details/114371139)

### [Linux 下 3 种常用的网络测速工具](https://juejin.cn/post/6844904152108105742)

### [linux 网状互信的处理方法](https://www.wangt.cc/2020/12/linux网状互信的处理方法/)

### [Bash 版的 join,map 和 filter](https://blog.lujun9972.win/blog/2019/06/28/bash版的join,map和filter/)

### [Linux 磁盘空间占满，但搜不到大文件](https://blog.csdn.net/weixin_38746118/article/details/131312383)

### [ssh 批量免密登录](https://blog.csdn.net/m0_57808069/article/details/129725893)

### Linux 清理所有的防火墙规则

```shell
sudo iptables -F
sudo iptables -X
sudo iptables -t nat -F
sudo iptables -t nat -X
sudo iptables -t mangle -F
sudo iptables -t mangle -X
sudo iptables -P INPUT ACCEPT
sudo iptables -P FORWARD ACCEPT
sudo iptables -P OUTPUT ACCEPT
```

### 查看指定网段已占用 IP

```shell
nmap -sn 192.168.31.0/24
```

### GLIBC 和 MUSL

#### 查看 GLIBC 版本

```shell
ldd --version
```

```
ldd (GNU libc) 2.39
Copyright (C) 2024 Free Software Foundation, Inc.
This is free software; see the source for copying conditions.  There is NO
warranty; not even for MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
Written by Roland McGrath and Ulrich Drepper.
```

#### 判断 GLIBC 和 MUSL

```shell
cargo build --release --target=x86_64-unknown-linux-gnu --target=x86_64-unknown-linux-musl

ldd target/x86_64-unknown-linux-gnu/release/my-app

ldd target/x86_64-unknown-linux-musl/release/my-app
```

```
        linux-vdso.so.1 (0x00007ffe3afc0000)
        libgcc_s.so.1 => /usr/lib/libgcc_s.so.1 (0x0000701222b6e000)
        libm.so.6 => /usr/lib/libm.so.6 (0x0000701222a83000)
        libc.so.6 => /usr/lib/libc.so.6 (0x0000701222414000)
        /lib64/ld-linux-x86-64.so.2 => /usr/lib64/ld-linux-x86-64.so.2 (0x0000701222ba3000)
```

```shell
        statically linked
```

#### 查看可执行文件动态链接的 GLIBC 版本

```shell
strings /lib64/ld-linux-x86-64.so.2 | grep GLIBC_
```

```
GLIBC_2.2.5
GLIBC_2.3
GLIBC_2.4
GLIBC_2.34
GLIBC_2.35
GLIBC_PRIVATE
GLIBC_TUNABLES
GLIBC_TUNABLES
GLIBC_PRIVATE
GLIBC_TUNABLES
GLIBC_ABI_DT_RELR
GLIBC_2.2.5
WARNING: ld.so: invalid GLIBC_TUNABLES value `%.*s' for option `%s': ignored.
WARNING: ld.so: invalid GLIBC_TUNABLES `%s': ignored.
DT_RELR without GLIBC_ABI_DT_RELR dependency
```
