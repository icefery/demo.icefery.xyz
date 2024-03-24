#### 安装 Python2

> ubuntu22.04 默认已经不带 Python2 了。

```shell
apt install python2
```

#### 安装 Python2 版本的 Pip

```shell
curl https://bootstrap.pypa.io/2.7/get-pip.py -o get-pip.py

python2 get-pip.py
```

#### 安装 Python2 版本的 Pandas

> 使用 pip install pandas 默认安装的是最新版的 pandas，因为 pandas 版本 0.24.0 以后的版本不再支持 python2.7，因此指定 pandas0.23.0 及以前的版本即可

```shell
pip install pandas==0.23.0
```
