> 在实际使用 Hive 的过程中，常常会涉及到行列转换，细分的话，有下面 4 种类型的行列转换，分别是：
>
> 1. 行转多列
> 2. 多列转行
> 3. 行转单列
> 4. 单列转行

## 行转多列

如果需要将上面的样例表转换为 `姓名 | 语文成绩 | 数学成绩 | 英语成绩` 这样的格式，那么就需要用到行转多列。
涉及到行转成列，肯定是会按照某一列或者某几列的值进行分组来压缩行数，所有会用到 `group by`。分组之后需要用到聚合函数，由于多列中的每列只关心自己对应的数据，所以要使用 `case` 语句来进行选择，至于聚合函数，只要能保证唯一性，`max`、`min`、`sum` 等都可以。

```sql
with score_vertical as (
    select 'A' as name, '语文' as subject, '70' as score union all
    select 'A' as name, '数学' as subject, '90' as score union all
    select 'A' as name, '英语' as subject, '80' as score union all
    select 'B' as name, '语文' as subject, '75' as score union all
    select 'B' as name, '数学' as subject, '95' as score union all
    select 'B' as name, '英语' as subject, '85' as score
)
select
	  name,
	  max(case when subject = '语文' then score end) as chinese,
	  max(case when subject = '数学' then score end) as math,
	  max(case when subject = '英语' then score end) as english
from score_vertical
group by name;
```

## 行转单列

将原始表转换为 `姓名 | 所有科目成绩集合` 则涉及到行转单列。
和行转多列一样，行数会减少，所以需要用到 `group by`，然后转成的是单列，所以需要用到 `collect_list` 或者 `collect_set` 聚合函数，如果字段类型想要是有分隔符隔开的字符串，再套上一层 `concat_ws`；
上面的方案得出的 `array` 或者字符串是乱序的，如果想要进行排序，可以使用 `sort_array` 只能按字段类型的升序排序（数值字段自然序，字符串字段字典序）；
如果想要自己指定排序规则，或者排序的不是单列里的这个字段（比如班级考试成绩表，所有分数字段里需要根据学科进行排序，而不是分数高低），则需要使用 `collect_list` 加上 `over` 子句来实现。

-   成绩不排序
    ```sql
    with score_vertical as (
        select 'A' as name, '语文' as subject, '70' as score union all
        select 'A' as name, '数学' as subject, '90' as score union all
        select 'A' as name, '英语' as subject, '80' as score union all
        select 'B' as name, '语文' as subject, '75' as score union all
        select 'B' as name, '数学' as subject, '95' as score union all
        select 'B' as name, '英语' as subject, '85' as score
    )
    select name, concat_ws(',', collect_list(score)) as scores
    from score_vertical
    group by name;
    ```
-   按分数高低排序
    ```sql
    with score_vertical as (
        select 'A' as name, '语文' as subject, '70' as score union all
        select 'A' as name, '数学' as subject, '90' as score union all
        select 'A' as name, '英语' as subject, '80' as score union all
        select 'B' as name, '语文' as subject, '75' as score union all
        select 'B' as name, '数学' as subject, '95' as score union all
        select 'B' as name, '英语' as subject, '85' as score
    )
    select name, concat_ws(',', sort_array(collect_list(score))) as scores
    from score_vertical
    group by name;
    ```
-   按学科进行排序
    ```sql
    with score_vertical as (
        select 'A' as name, '语文' as subject, '70' as score union all
        select 'A' as name, '数学' as subject, '90' as score union all
        select 'A' as name, '英语' as subject, '80' as score union all
        select 'B' as name, '语文' as subject, '75' as score union all
        select 'B' as name, '数学' as subject, '95' as score union all
        select 'B' as name, '英语' as subject, '85' as score
    )
    select name, concat_ws(',', max(scores)) as scores
    from (
    	  select name, collect_list(score) over(partition by name order by subject rows between unbounded preceding and unbounded following) as scores
    	  from score_vertical
    ) t
    group by name;
    ```

## 多列转行

列转行会涉及到行数的增加，所以会用到 UDTF（自定义表值函数）,而 UDTF 只是针对某一列的，要把这列扩展后生成的多行数据和原表中的各列拼接在一起，需要用到 `lateral view` 语法；
需要将多列里各列的列名（业务含义）在新数据中当成一个标识列，而与 `lateral view` 联合使用的 `explode` 函数是支持 `map` 类型的，所以要先将原表里的多列变换成 `map` 类型的一列，然后再使用 `lateral view` 拆开。

```sql
with score_horizontal as (
    select 'A' as name, '70' as chinese, '90' as math, '80' as english union all
    select 'B' as name, '75' as chinese, '95' as math, '85' as english
)
select t1.name, t2.subject, t2.score
from (
	  select name, map('语文', chinese, '数学', math, '英语', english) as scores
	  from score_horizontal
) t1
lateral view explode(scores) t2 as subject, score;
```

## 单列转行

和多列转行一样，使用 `lateral view` 加 `explode` 来转换。但这种方式转换出来会丢失掉科目信息，则需要按照单列里面的顺序的业务含义，先将单列转成 `map` 类型，将科目加到数据里，然后再使用 `lateral view` 转换。

-   无科目字段

    ```sql
    with score_horizontal as (
        select 'A' as name, '70,90,80' as scores union all
        select 'B' as name, '75,95,85' as scores
    )
    select t1.name, t2.score
    from (
        select name, split(scores, ',') as scores
        from score_horizontal
    ) t1
    lateral view explode(scores) t2 as score;
    ```

-   有科目字段
    ```sql
    with score_horizontal as (
      select 'A' as name, '70,90,80' as scores union all
      select 'B' as name, '75,95,85' as scores
    )
    select t2.name, t3.subject, t3.score
    from (
    	select name, map('语文', scores[0], '数学', scores[1], '英语', scores[2]) as scores
    	from (
    		select name, split(scores, ',') as scores
    		from score_horizontal
    	) t1
    ) t2
    lateral view explode(scores) t3 as subject, score;
    ```
