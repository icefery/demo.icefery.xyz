## 静态 IP

-   [CentOS Stream 9 设置静态 IP](https://blog.csdn.net/zhongxj183/article/details/122811236)

```shell
ls -l /etc/NetworkManager/system-connections/
```

```toml
[ipv4]
method=manual
address1=192.192.192.9/24,192.192.192.1
dns=192.192.192.1;
```

> 逗号后是网关地址。
