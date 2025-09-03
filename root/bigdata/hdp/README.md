# HDP

## 收藏

#### [安装 Ambari 2.7.5 + HDP3.1.5（附安装包）](https://blog.csdn.net/qq_36048223/article/details/116113987)

#### [ambari 安装过程中的一些误区（跳坑指南）（ambari 的两个 bug 修复）](https://blog.csdn.net/alwaysbefine/article/details/123651049)

## 安装

```shell
cp /var/www/html/ambari/centos7/2.7.5.0-72/ambari.repo /etc/yum.repos.d/
cp /var/www/html/HDP/centos7/3.1.5.0-152/hdp.repo /etc/yum.repos.d/
cp /var/www/html/HDP-GPL/centos7/3.1.5.0-152/hdp.gpl.repo /etc/yum.repos.d/
cp /var/www/html/HDP-UTILS/centos7/1.1.0.22/hdp-utils.repo /etc/yum.repos.d/
```

```toml
[ambari-2.7.5.0]
name=ambari Version - ambari-2.7.5.0
baseurl=http://hdp-111/ambari/centos7/2.7.5.0-72
gpgcheck=0
enabled=1
priority=1
```

```shell
tee /etc/yum.repos.d/ambari.repo > /dev/null <<- "EOF"
[ambari-2.7.5.0]
name=ambari Version - ambari-2.7.5.0
baseurl=http://hdp-111/ambari/centos7/2.7.5.0-72
gpgcheck=0
enabled=1
priority=1
EOF

tee /etc/yum.repos.d/hdp.repo > /dev/null <<- "EOF"
[HDP-3.1.5.0]
name=HDP Version - HDP-3.1.5.0
baseurl=http://hdp-111/HDP/centos7/3.1.5.0-152
gpgcheck=0
enabled=1
priority=1

[HDP-UTILS-1.1.0.22]
name=HDP-UTILS Version - HDP-UTILS-1.1.0.22
baseurl=http://hdp-111/HDP-UTILS/centos7/1.1.0.22
gpgcheck=0
enabled=1
priority=1

[HDP-GPL-3.1.5.0]
name=HDP-GPL Version - HDP-GPL-3.1.5.0
baseurl=http://hdp-111/HDP-GPL/centos7/3.1.5.0-152
gpgcheck=0
enabled=1
priority=1
EOF
```
