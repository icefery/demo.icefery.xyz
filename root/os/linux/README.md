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