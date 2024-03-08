# Python

## minconda

### 卸载

```shell
conda init --reverse --all

rm -rf ~/.condarc ~/.conda ~/.continuum

rm -rf /opt/env/miniconda
```

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

## 收藏

#### [Python 调用 C 动态链接库，包括结构体参数、回调函数等](https://segmentfault.com/a/1190000013339754)

#### [Python：实例讲解 Python 中的魔法函数（高级语法）](https://zhuanlan.zhihu.com/p/344951719)

#### [sqlalchemy2.0 使用记录](https://www.jianshu.com/p/dd06b1ec5d62)

#### [你真的了解 python 中的换行以及转义吗?](https://www.cnblogs.com/traditional/p/12236925.html)

#### [3-Pandas 数据初探索之缺失值处理与丢弃数据（填充 fillna()、删除 drop()、drop_duplicates()、dropna()）](https://www.cnblogs.com/Cheryol/p/13382560.html)

#### [Python 利用多线程实时读取 subprocess.Popen 的程序输出的 stdout 和 stderr，并且向其中实时传入 input 信息。](https://blog.csdn.net/weixin_41102672/article/details/106385663)
