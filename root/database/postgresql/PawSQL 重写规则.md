### `ALL` 修饰符的子查询重写优化

```sql
select * from customer where c_regdate > all(select o_orderdate from orders)
```

> 如果子查询的结果中存在 `NULL`，这个 SQL 永远返回为空。正确的写法应该是在子查询里加上非空限制，或使用 `MAX/MIN` 的写法。

```sql
select * from customer where c_regdate > (select max(o_custkey) from orders)
```

### `COUNT` 标量子查询重写优化

```sql
select * from customer where (select count(*) from orders where c_custkey=o_custkey) > 0
```

> 避免了一次聚集运算。

```sql
select * from customer where exists(select 1 from orders where c_custkey=o_custkey)
```

### `HAVING` 条件下推到 `WHERE`

```sql
select c_custkey, count(*) from customer group by c_custkey having c_custkey < 100
```

```sql
select c_custkey, count(*) from customer where c_custkey < 100 group by c_custkey
```

### `IN` 子查询重写优化

-   子查询重写为 `EXISTS`

    ```sql
    -- 获取最近一年有订单的用户信息
    select * from customer where c_custkey in (select o_custkey from orders where o_orderdate >= current_date - interval 1 year)
    ```

    ```sql
    -- 获取最近一年有订单的用户信息
    select * from customer where exists (select * from orders where c_custkey = o_custkey and o_orderdate >= current_date - interval 1 year)
    ```

-   子查询重写为内关联

    ```sql
    select * from orders where o_custkey in (select c_custkey from customer where c_phone like '139%')
    ```

    ```sql
    select orders.* from orders, customer where o_custkey = c_custkey and c_phone like '139%'
    ```

### `MAX`/`MIN` 子查询重写优化

```sql
select * from customer where c_custkey = (select max(o_custkey) from orders)
```

```sql
select * from customer where c_custkey = (select o_custkey from orders order by o_custkey desc null last limit 1)
```

### IN 可空子查询重写

```sql
-- 查询没有订单的用户
select * from customer where c_custkey not in (select o_custkey from orders)
```

> 如果子查询的结果集里有空值，这个 SQL 永远返回为空。正确的写法应该是在子查询里加上非空限制。

```sql
-- 查询没有订单的用户
select * from customer where c_custkey not in (select o_custkey from orders where o_custkey is not null)
```

### 外连接转化为内连接

```sql
select c_custkey from orders left join customer on c_custkey = o_custkey where c_nationkey < 20
```

> `c_nationkey < 20` 是一个 `customer` 表上的 `null`拒绝条件，所以左外连接可以重写为内连接

```sql
select c_custkey from orders join customer on c_custkey = o_custkey where c_nationkey < 20
```

### 投影下推

> 投影下推指的通过删除 DT 子查询中无意义的列（在外查询中没有使用），减少 IO 和网络的代价，同事提升优化器在进行表访问的规划时，采用无需回表的优化选项的几率。

```sql
select count(1) from (select c_custkey, avg(age) from customer group by c_custkey) as derived_t1;
```

```sql
select count(1) from (select 1 from customer group by c_custkey) as derived_t1;
```

### 查询折叠

```sql
select * from (select c_custkey, c_name from customer) as derived_t1;
```

```sql
select c_custkey, c_name from customer
```
