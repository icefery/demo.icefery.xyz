#### 脚本文件

```shell
vim temp.sh
```

```shell
echo "A  = ${A}"
echo "\$0 = $0"
echo "P1 = $1"
echo "P2 = $2"
echo "P3 = $3"
```

```shell
nohup python3 -m http.server &> /dev/null &
```

#### 管道

|                     |                                                                                                        |
| :------------------ | :----------------------------------------------------------------------------------------------------- |
| `-f`,`--fail`       | 表示在服务器错误时，阻止一个返回的表示错误原因的 html 页面，而由 curl 命令返回一个错误码 22 来提示错误 |
| `-s`,`--slient`     | `-s` 参数将不输出错误和进度信息                                                                        |
| `-S`,`--show-error` | `-S` 参数指定只输出错误信息，通常与 `-s` 一起使用。                                                    |
| `-L`,`--location`   | `-L` 参数会让 HTTP 请求跟随服务器的重定向。curl 默认不跟随重定向                                       |

```shell
[root@dev:~]# curl -fsSL http://localhost:8000/temp.sh | A=1 bash -s -- -p1 --p2 p3
A  = 1
$0 = bash
P1 = -p1
P2 = --p2
P3 = p3
```

#### 进程替换

```shell
[root@dev:~]# A=1 bash <(curl -fsSL http://localhost:8000/temp.sh) -p1 --p2 p3
A  = 1
$0 = /dev/fd/61
P1 = -p1
P2 = --p2
P3 = p3
```
