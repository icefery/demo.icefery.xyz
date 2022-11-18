## PostgreSQL 字符串类型简介

PostgreSQL 提供了三种字符类型：`CHAR(n)`、`VARCHAR(n)` 和 `TEXT`，其中 `n` 是正整数。

| 类型         | 描述             |
| :----------- | :--------------- |
| `CHAR(n)`    | 定长，空白填充   |
| `VARCHAR(n)` | 变长，有长度限制 |
| `TEXT`       | 变长，无长度限制 |

简单来说，`varchar` 的长度可变，而 `char` 的长度不可变，对于 postgresql 数据库来说 `varchar` 和 `char` 的区别仅仅在于前者是变长，而后者是定长，最大长度都是 10485760（1GB）。

`varchar` 不指定长度，可以存储最大长度（1GB）的字符串，而 `char` 不指定长度，默认则为 1，这点需要注意。

在 postgresql 数据库里边，`text` 和 `varchar` 几乎无性能差别，区别仅在于存储结构的不同。

对于 `char` 的使用，应该在确定字符串长度的情况下使用，否则应该选择 `varchar` 或者 `text`。

## 收藏

> [postgresql 数据库 varchar、char、text 的比较](https://blog.csdn.net/u013992330/article/details/76653361)

这三种类型之间没有性能差别，除了当使用填充空白类型时的增加存储空间，和当存储长度约束的列时一些检查存入时长度的额外的 CPU 周期。虽然在某些其它的数据库系统里，`character(n)` 有一定的性能优势，但在 PostgreSQL 里没有。事实上，`character(n)` 通常是这三个中最慢的，因为额外存储成本。在大多数情况下，应该使用 `text` 或 `character varying`。
