### 安装

```shell
curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py

python get-pip.py
```

### 换源 `~/.pip/pip.conf`

```bash
[global]
index-url = https://mirrors.aliyun.com/pypi/simple/
```

### 批量安装

```bash
pip install -r requirements.txt
```

> 其中 `requirements.txt`文件中预先写好需要安装的库名（每行一个库）

### 查看配置

```bash
pip3 config list -v
```

> ```bash
>   For variant 'global', will try loading '/Library/Application Support/pip/pip.conf'
>   For variant 'user', will try loading '/Users/icefery/.pip/pip.conf'
>   For variant 'user', will try loading '/Users/icefery/.config/pip/pip.conf'
>   For variant 'site', will try loading '/Applications/Xcode.app/Contents/Developer/Library/Frameworks/Python3.framework/Versions/3.8/pip.conf'
> ```
