### 安装

```shell
apt install -y zsh fonts-powerline

curl -O https://gitee.com/mirrors/oh-my-zsh/raw/master/tools/install.sh

sed -i 's/-ohmyzsh\/ohmyzsh/-mirrors\/oh-my-zsh/g' install.sh

sed -i 's/-https:\/\/github.com\/${REPO}.git/-https:\/\/gitee.com\/${REPO}.git/g' install.sh

sed -i 's/-~\/.oh-my-zsh/-\/etc\/oh-my-zsh/g' install.sh

export ZSH=/etc/oh-my-zsh

./install.sh
```

```shell
cat /etc/shells

chsh -s $(which zsh) root
```

### 卸载

```shell
sh $ZSH/tools/uninstall.sh
```

### 插件

-   `zsh-syntax-highlighting`

    ```shell
    git clone https://github.com/zsh-users/zsh-syntax-highlighting.git $ZSH_CUSTOM/plugins/zsh-syntax-highlighting
    ```

-   `~/.zshrc`

    ```shell
    # ...
    plugins=(git zsh-syntax-highlighting)
    ```

### 环境变量配置

1. `~/.profile`

    ```sh
    export JAVA_HOME=/opt/jdk-11
    ```

2. `~/.zprofile`

    ```sh
    [[ -e ~/.profile ]] && emulate sh -c 'source ~/.profile'
    ```
