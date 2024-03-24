### 全局配置

```shell
git config --global user.name "icefery"

git config --global user.email "icefery@163.com"
```

### 常用命令

-   新建空白分支

    ```shell
    git checkout --orphan <branch>
    ```

-   远程

    ```shell
    # 添加远程仓库
    git remote add origin <url>

    # 修改远程仓库的地址
    git remote set-url origin <url>

    # 修改远程分支的名称
    git remote rename <old_name> <new_name>

    # 移除远程仓库
    git remote remove origin

    # 查看远程分支
    git remote -v

    # 关联远程分支
    git branch --set-upstream-to=origin/<remote-branch> <local_branch>
    ```

-   合并到上一次提交

    ```shell
    git commit --amend
    ```

-   撤销所有暂存和修改

    ```shell
    git fetch --all

    git reset HEAD -a
    ```

### `.gitignore`

```shell
# 忽略所有的 .a 文件
*.a

# 但跟踪所有的 lib.a，即便你在前面忽略了 .a 文件
!lib.a

# 只忽略当前目录下的 TODO 文件，而不忽略 subdir/TODO
/TODO

# 忽略任何目录下名为 build 的文件夹
build/

# 忽略 doc/notes.txt，但不忽略 doc/server/arch.txt
doc/*.txt

# 忽略 doc/ 目录及其所有子目录下的 .pdf 文件
doc/**/*.pdf
```
