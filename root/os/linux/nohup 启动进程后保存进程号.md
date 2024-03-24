> `$!` 表示最后执行的进程的 PID

```shell
#!/usr/bin/env bash
case $1 in
start)
  nohup node main.js &> app.log &
  echo $! > app.pid
;;
stop)
  if [[ -f app.pid ]]; then
    kill -9 $(cat app.pid)
    rm -rf app.pid
  fi
;;
*)
  echo "USAGE ./cli.sh <start|stop>"
;;
```
