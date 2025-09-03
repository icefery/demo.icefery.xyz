---
title: '清理无效软链接'
---

### 方法一

```shell
for f in $(find /usr/local/bin -type l); do [[ ! -e $f ]] && echo $f && rm -f $f; done
```

#### 方法二

```shell
apt install -y symlinks
```

```shell
symlinks -d /usr/local/bin
```
