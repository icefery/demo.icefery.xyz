# VIM

## 收藏

### 禁用鼠标模式

```shell
sudo find /usr/share/vim -type f -name "defaults.vim" -exec sed -i 's/set mouse=a/set mouse-=a/g' {} \;
```

### 取消自动换行

```shell
:set nowrap
```
