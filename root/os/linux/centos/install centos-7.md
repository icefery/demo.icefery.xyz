### 网络配置

-   查看网卡 UUID

    ```shell
    # 查看网卡 UUID
    nmcli con
    ```

-   `/etc/sysconfig/network-scripts/ifcfg-ens33`

    > VMWare 中默认网卡名称为 `ens-33`。

    ```shell
    TYPE=Ethernet
    PROXY_METHOD=none
    BROWSER_ONLY=no
    BOOTPROTO=none
    DEFROUTE=yes
    IPV4_FAILURE_FATAL=no
    IPV6INIT=yes
    IPV6_AUTOCONF=yes
    IPV6_DEFROUTE=yes
    IPV6_FAILURE_FATAL=no
    IPV6_ADDR_GEN_MODE=stable-privacy
    NAME=ens33
    UUID=21f9e3ea-3aab-4de3-90e5-dfa584e84033
    DEVICE=ens33
    ONBOOT=yes
    IPADDR=192.168.137.7
    PREFIX=24
    GATEWAY=192.168.137.1
    DNS1=192.168.137.1
    IPV6_PRIVACY=no
    ```

-   重启网络

    ```shell
    systemctl restart network
    ```

### 换源

```shell
sed -e 's|^mirrorlist=|#mirrorlist=|g' \
    -e 's|^#baseurl=http://mirror.centos.org/centos|baseurl=https://mirrors.ustc.edu.cn/centos|g' \
    -i.bak \
    /etc/yum.repos.d/CentOS-Base.repo

yum makecache
```

### 禁用 SELINUX

```shell
getenforce

sed -i 's/SELINUX=enforcing/SELINUX=disabled/' /etc/selinux/config

reboot
```

### 禁用防火墙

```shell
systemctl stop firewalld

systemctl disable firewalld

systemctl status firewalld
```

### 卸载 MariaDB

```shell
rpm -e --nodeps $(rpm -qa | grep mariadb)
```
