---
title:'清理无效软链接'
---

### 方法一

```bash
for f in $(find /usr/local/bin -type l); do [[ ! -e $f ]] && echo $f && rm -f $f; done
```

#### 方法二

```bash
apt install -y symlinks
```

```bash
symlinks -d /usr/local/bin
```
