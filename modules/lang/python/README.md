# Python

## 常见问题

- pip is being invoked by an old script wrapper. This will fail in a future version of pip

  > [https://blog.csdn.net/weixin_44110913/article/details/109533700](https://blog.csdn.net/weixin_44110913/article/details/109533700)

  - 重装

    ```bash
    python3 -m pip uninstall pip

    python3 -m pip install --upgrade pip
    ```

  - 刷新环境变量

    > 解决 `which pip` 已正确安装在 `/usr/local/bin` 目录下，但 `pip` 命令依然使用 `/usr/bin` 的问题

    ```bash
    source /etc/profile
    ```
