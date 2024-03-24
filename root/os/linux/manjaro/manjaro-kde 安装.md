### 设备

|         |            |
| ------- | ---------- |
| Machine | 机械师-T90 |

### 换源

```shell
# 排列源
sudo pacman-mirrors -i -c China -m rank

# 添加 archlinuxcn 源
echo -e "
[archlinuxcn]
SigLevel = Optional TrustedOnly
Server = https://mirrors.tuna.tsinghua.edu.cn/archlinuxcn/$arch
" >> /etc/pacman.conf

# 下载 keyring
sudo pacman -S archlinux-keyring

# 更新系统
sudo pacman -Syyu
```

### 输入法

-   参考

    -   https://wiki.archlinux.org/title/Fcitx5_(%E7%AE%80%E4%BD%93%E4%B8%AD%E6%96%87)
    -   https://github.com/hosxy/Fcitx5-Material-Color

-   ```shell
    sudo pacman -S fcitx5-im fcitx5-rime fcitx5-material-color noto-fonts-emoji
    ```

-   `~/.pam_environment`

    ```shell
    GTK_IM_MODULE DEFAULT=fcitx
    QT_IM_MODULE  DEFAULT=fcitx
    XMODIFIERS    DEFAULT=@im=fcitx
    INPUT_METHOD  DEFAULT=fcitx
    SDL_IM_MODULE DEFAULT=fcitx
    ```

-   `/etc/vimrc`

    ```shell
    autocmd InsertLeave * :silent !fcitx5-remote -c " 退出插入模式时禁用输入法
    autocmd BufCreate   * :silent !fcitx5-remote -c " 创建 Buf 时禁用输入法
    autocmd BufEnter    * :silent !fcitx5-remote -c " 进入 Buf 时禁用输入法
    autocmd BufLeave    * :silent !fcitx5-remote -c " 离开 Buf 时禁用输入法
    ```

-   ```shell
    sudo kill `ps -A | grep fcitx5 | awk '{print $1}'` && fcitx5&
    ```
