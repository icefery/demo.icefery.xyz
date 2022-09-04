# Shell

## 常见问题

- 写入权限

  ```bash
  # error
  sudo echo 3 >/proc/sys/vm/drop_caches

  # correct
  echo 3 | sudo tee /proc/sys/vm/drop_caches
  ```

- 进程替换

  ```bash
  # 先将 ls 的结构输出到一个临时文件然后 echo 这个临时文件
  echo <(ls)

  # 这个命令是将左边命令的输出重定向到进程替换产生的文件中，然后对文件中的内容执行>(CMD)中CMD命令
  echo hello > >(grep hello)

  # 显示文件夹 foo 和 bar 中文件的区别
  diff <(ls foo) <(ls bar)
  ```
