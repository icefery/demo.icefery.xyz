### `.` 和 `source`的区别

-   `. <SCRIPT>`：启动一个子 Shell 来执行脚本，在子 Shell 中执行的一切操作都不会影响父 Shell
-   `source <SCRIPT>`：在当前 Shell 环境执行脚本

### interactive shell 和 non-interactive shell

> 判断方法：`$PS1`是否有值或者 `$-` 是否包含 `i`

-   interactive shell

    ```shell
    echo $PS1
    # ${debian_chroot:+($debian_chroot)}\u@\h:\w\$

    echo $-
    # himBHs
    ```

    ```shell
    bash -i -c 'echo $PS1'
    # ${debian_chroot:+($debian_chroot)}\u@\h:\w\$

    bash -i -c 'echo $-'
    # himBHs
    ```

-   non-interactive shell

    ```shell
    bash -c 'echo $PS1'
    #

    bash -c 'echo $-'
    # hBc
    ```

    ####

### login shell 和 non-login shell

> 判断方法：
>
> -   `$0`显示 `-bash` 的一定是 login shell（通过 `bash --login` 启动的 login shell 的 `$0` 并非以 `-` 开头）
> -   使用`exit`命令会判断当前的 shell 并分别调用 `logout` 或 `exit`

-   login shell

    ```shell
    bash --login
    exit
    # logout
    ```

-   non-login shell

    ```shell
    bash
    exit
    #
    ```

### HeroDoc

```shell
cat << EOF
	pwd=${PWD}
	now=$(date +%Y-%m-%d)
EOF
```

```shell
# 忽略前导制表符
cat <<- EOF
	pwd=${PWD}
	now=$(date +%Y-%m-%d)
EOF
```

```shell
# 忽略前导制表符 + 禁止变量扩展
cat <<- "EOF"
	pwd=${PWD}
	now=$(date +%Y-%m-%d)
EOF
```
