# Python

## pip

### 安装

```shell
curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py

python3.12 get-pip.py
```

### 配置

#### 换源

```shell
# 方式一
pip3.12 config set global.index-url https://pypi.tuna.tsinghua.edu.cn/simple

# 方式二
mkdir -p ~/.config/pip
tee ~/.config/pip/pip.conf > /dev/null <<- "EOF"
[global]
index-url = https://pypi.tuna.tsinghua.edu.cn/simple
EOF

# 查看配置
pip3.12 config debug
pip3.12 config list -v
```

> ```
> For variant 'global', will try loading '/Library/Application Support/pip/pip.conf'
> For variant 'user', will try loading '/Users/icefery/.pip/pip.conf'
> For variant 'user', will try loading '/Users/icefery/.config/pip/pip.conf'
> For variant 'site', will try loading '/opt/homebrew/opt/python@3.12/Frameworks/Python.framework/Versions/3.12/pip.conf'
> global.index-url='https://pypi.tuna.tsinghua.edu.cn/simple'
> ```

### 使用

#### 查看安装的包

```shell
pip3.12 list --format=freeze
```

> ```text
> pip==24.0
> wheel==0.43.0
> ```

### 常见问题

#### pip is being invoked by an old script wrapper. This will fail in a future version of pip

> [https://blog.csdn.net/weixin_44110913/article/details/109533700](https://blog.csdn.net/weixin_44110913/article/details/109533700)

```shell
# 卸载
python3 -m pip uninstall pip

# 重装
python3 -m pip install --upgrade pip

# 刷新环境变量
source /etc/profile
```

## Python2

### 安装

> -   ubuntu22.04 默认已经不带 Python2 了。
> -   使用 pip install pandas 默认安装的是最新版的 pandas，因为 pandas 版本 0.24.0 以后的版本不再支持 python2.7，因此指定 pandas0.23.0 及以前的版本即可。

```shell
# 安装 python2
apt install python2

# 安装 python2 版本的 pip
curl https://bootstrap.pypa.io/pip/2.7/get-pip.py -o get-pip.py

python2.7 get-pip.py

# 安装 python2 版本的 pandas
pip2.7 install pandas==0.23.0
```

## miniconda

### 安装

```shell
# 安装
wget https://repo.anaconda.com/miniconda/Miniconda3-latest-Linux-x86_64.sh -O install_miniconda.sh

mkdir -pv /opt/env/miniconda

bash install_miniconda.sh -b -f -p /opt/env/miniconda

# 启用
eval "$(/opt/env/miniconda/bin/conda shell.bash hook 2> /dev/null)"
```

### 卸载

```shell
conda init --reverse --all

rm -rf ~/.condarc ~/.conda ~/.continuum

rm -rf /opt/env/miniconda
```

### 配置

#### 禁止自动激活 `base` 环境

```shell
conda config --set auto_activate_base false
```

### 使用

```shell
# 创建环境
conda create -n <NAME>

# 查看所有环境
conda info --envs

# 删除环境
conda remove -n <NAME> --all

# 激活环境
conda activate <NAME>

# 退出环境
conda deactivate <NAME>
```

## Poetry

<!-- TODO -->

## 收藏

#### [Python 调用 C 动态链接库，包括结构体参数、回调函数等](https://segmentfault.com/a/1190000013339754)

#### [Python：实例讲解 Python 中的魔法函数（高级语法）](https://zhuanlan.zhihu.com/p/344951719)

#### [sqlalchemy2.0 使用记录](https://www.jianshu.com/p/dd06b1ec5d62)

#### [你真的了解 python 中的换行以及转义吗?](https://www.cnblogs.com/traditional/p/12236925.html)

#### [3-Pandas 数据初探索之缺失值处理与丢弃数据（填充 fillna()、删除 drop()、drop_duplicates()、dropna()）](https://www.cnblogs.com/Cheryol/p/13382560.html)

#### [Python 利用多线程实时读取 subprocess.Popen 的程序输出的 stdout 和 stderr，并且向其中实时传入 input 信息。](https://blog.csdn.net/weixin_41102672/article/details/106385663)
