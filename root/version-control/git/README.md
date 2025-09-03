# Git

## 收藏

-   [error: RPC failed； curl 56 OpenSSL SSL_read: Connection was reset, errno 10054](https://blog.csdn.net/dsqcsdn/article/details/104821042/)

-   [Git 提交时的 emoji 表情使用指南](https://ibyte.blog.csdn.net/article/details/113336076)

-   [.gitignore 忽略规则的匹配语法](https://blog.csdn.net/Mr_JavaScript/article/details/91788035)

## 常见问题

#### detected dubious ownership in repository at xxx To add an exception for this directory

```shell
git config --global --add safe.directory xxx
```

或者

```shell
export GIT_TEST_DEBUG_UNSAFE_DIRECTORIES=true
```

#### Windows 上 Git 保留可执行文件权限

> https://www.cnblogs.com/LoveBB/p/16076897.html

```shell
git update-index --chmod=+x a.sh
```

```shell
git ls-tree HEAD
```

#### 配置默认编辑器

```shell
git config --global core.editor vim
# 测试
git commit --amend
```

#### 从历史版本中彻底删除某个文件或目录

```shell
brew install git-filter-repo

git filter-repo --invert-paths --path <文件或目录>
```

#### 从历史版本彻底修改用户名

```shell
git filter-branch --env-filter '
OLD_EMAIL="old@org.example.com"
CORRECT_NAME="new"
CORRECT_EMAIL="new@org.example.com"
if [ "$GIT_COMMITTER_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_COMMITTER_NAME="$CORRECT_NAME"
    export GIT_COMMITTER_EMAIL="$CORRECT_EMAIL"
fi
if [ "$GIT_AUTHOR_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_AUTHOR_NAME="$CORRECT_NAME"
    export GIT_AUTHOR_EMAIL="$CORRECT_EMAIL"
fi
' --tag-name-filter cat -- --branches --tags

git push --force --all

git push --force --tags
```
