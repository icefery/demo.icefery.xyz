# Airflow

## 一、安装

### 1.1 安装 Miniconda

> [Miniconda](https://docs.conda.io/en/latest/miniconda.html#linux-installers)

```shell
# 下载 sh 脚本
wget https://repo.anaconda.com/miniconda/Miniconda3-py310_23.1.0-1-Linux-aarch64.sh
# 安装 minconda
bash Miniconda3-py310_23.1.0-1-Linux-aarch64.sh
# 刷新环境变量
source ~/.bashrc
# 禁止自动激活 base 环境
conda config --set auto_activate_base false
```

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

### 1.2 pip 换源

```shell
mkdir -p ~/.pip
tee ~/.pip/pip.conf <<- "EOF"
[global]
index-url = https://pypi.tuna.tsinghua.edu.cn/simple
[install]
trusted-host = https://pypi.tuna.tsinghua.edu.cn
EOF
```

### 1.3 安装 Airflow

```shell
conda create --name airflow python=3.10
conda activate airflow
# 安装 airflow
export AIRFLOW_HOME=/opt/env/airflow
pip install "apache-airflow==2.4.3"
# 查看版本
airflow version
```

### 1.4 配置 Airflow

-   安装依赖

    ```shell
    apt install libmysqlclient-dev -y
    pip install mysqlclient
    ```

-   修改配置文件 `airflow.cfg`

    ```properties
    executor = LocalExecutor
    load_examples = False
    sql_alchemy_conn = mysql+mysqldb://root:root@192.192.192.6:3306/airflow
    ```

-   数据库初始化

    ```shell
    # 初始化
    airflow db init
    # 创建账号
    airflow users create \
      --username admin \
      --password admin \
      --firstname admin \
      --lastname admin \
      --role Admin \
      --email icefery@163.com
    ```

### 1.5 Airflow 启停脚本

```shell
case "$1" in
'start')
    conda activate airflow
    airflow webserver -p 8080 -D
    airflow scheduler -D
    conda deactivate
    ;;
'stop')
    ps -ef | grep -E 'airflow-webserver|airflow scheduler' | grep -v 'grep' | awk '{print $2}' | xargs kill
    ;;
*)
    echo "$0 <start|stop>"
    ;;
esac
```

> http://192.192.192.6:8080

## 二、任务调度

### 2.1 BashOperator

#### 创建任务

-   创建默认 DAG 目录

    ```shell
    mkdir -p "${AIRFLOW_HOME}/{dags}"
    ```

-   新建任务 `dags/demo.py`

    ```python
    from datetime import datetime, timedelta
    from airflow import DAG
    from airflow.operators.bash import BashOperator

    dag = DAG(
      dag_id="demo",
      start_date=datetime(year=2023, month=3, day=1),
      schedule=timedelta(minutes=1),
    )
    t1 = BashOperator(
      dag=dag,
      task_id="t1",
      bash_command="""
      echo 't1' >> ${AIRFLOW_HOME}/demo.txt
      """,
    )
    t2 = BashOperator(
      dag=dag,
      task_id="t2",
      bash_command="""
      echo 't2' >> ${AIRFLOW_HOME}/demo.txt
      """,
    )
    t3 = BashOperator(
      dag=dag,
      task_id="t3",
      bash_command="""
      echo 't3' >> ${AIRFLOW_HOME}/demo.txt
      """,
    )
    t4 = BashOperator(
      dag=dag,
      task_id="t4",
      bash_command="""
      echo 't4' >> ${AIRFLOW_HOME}/demo.txt
      """,
    )
    [t1, t2] >> t3 >> t4
    ```
