# DataX

## 快速开始

### Python2 转 Python3

-   Ubuntu

    ```shell
    apt install 2to3

    2to3 -w /opt/module/datax/bin
    ```

-   Windows

    ```shell
    python /d/env/python-3.10.2/Tools/scripts/2to3.py -w /d/env/datax/bin
    ```

### 执行任务

-   `PostgreSQLReader.json`

    ```json
    {
      "job": {
        "setting": {
          "speed": {
            "byte": 1048576
          }
        },
        "content": [
          {
            "reader": {
              "name": "postgresqlreader",
              "parameter": {
                "connection": [
                  {
                    "jdbcUrl": ["jdbc:postgresql://192.192.192.101:17700/demo"],
                    "table": ["t_user"]
                  }
                ],
                "username": "demo",
                "password": "demo",
                "column": ["id", "username", "age"]
              }
            },
            "writer": {
              "name": "streamwriter",
              "parameter": {
                "print": true
              }
            }
          }
        ]
      }
    }
    ```

-   `PostgreSQLWriter.json`

    ```json
    {
      "job": {
        "setting": {
          "speed": {
            "channel": 1
          }
        },
        "content": [
          {
            "reader": {
              "name": "streamreader",
              "parameter": {
                "column": [
                  { "type": "Long", "value": 2 },
                  { "type": "String", "value": "mm" },
                  { "type": "Long", "value": 23 }
                ],
                "sliceRecordCount": 1
              }
            },
            "writer": {
              "name": "postgresqlwriter",
              "parameter": {
                "connection": [
                  {
                    "jdbcUrl": "jdbc:postgresql://192.192.192.101:17700/demo",
                    "table": ["t_user"]
                  }
                ],
                "username": "demo",
                "password": "demo",
                "column": ["id", "username", "age"]
              }
            }
          }
        ]
      }
    }
    ```

```shell
python /opt/module/datax/bin/datax.py ~/PostgreSQLReader.json
```

## 插件开发

### 打包

-   打包

    ```shell
    mvn clean package -DskipTests assembly:assembly
    ```

-   只打包指定插件

    ```shell
    mvn clean package assembly:assembly -U -T 1C -Dmaven.test.skip=true -Dmaven.compile.fork=true -pl postgresqlwriter -am
    ```
