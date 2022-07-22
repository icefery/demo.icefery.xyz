#### 安装 Python2

> ubuntu22.04 默认已经不带 Python2 了。

```bash
apt install python2
```

#### 安装 Python2 版本的 Pip

```bash
curl https://bootstrap.pypa.io/2.7/get-pip.py -o get-pip.py

python2 get-pip.py
```

#### 安装 Python2 版本的 Pandas

> 使用pip install pandas默认安装的是最新版的pandas，因为pandas版本0.24.0以后的版本不再支持python2.7，因此指定pandas0.23.0及以前的版本即可

```bash
pip install pandas==0.23.0
```