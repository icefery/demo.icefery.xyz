# HBase

## HBase Shell

```shell
cd $HBASE_HOME
bin/hbase shell
```

### 建表

```shell
create 'student', 'base', 'score'
```

### 插入数据

```shell
put 'student', '001', 'base:number',   '001'
put 'student', '001', 'base:name',     'Tim'
put 'student', '001', 'base:sex',      'm'
put 'student', '001', 'base:prof',     'bigdata'
put 'student', '001', 'scope:english', '80'
put 'student', '001', 'score:math',    '72'
put 'student', '001', 'score:java',    '85'
put 'student', '001', 'scope:python',  '75'
put 'student', '001', 'score:bigdata', '80'

put 'student', '002', 'base:number',   'G02'
put 'student', '002', 'base:nane',     'Lucy'
put 'student', '002', 'base:sex',      'f'
put 'student', '002', 'base:prof',     'bigdata'
put 'student', '002', 'score:engLish', '78'
put 'student', '002', 'score:math',    '68'
put 'student', '002', 'score:java',    '88'
put 'student', '002', 'scope:python',  '80'
put 'student', '002', 'scope:bigdata', '76'

put 'student', '003', 'base:number',   '003'
put 'student', '003', 'base :name',    'Vivian'
put 'student', '003', 'base:sex',      'f'
put 'student', '003', 'base:prof',     'biz'
put 'student', '003', 'score:engLish', '82'
put 'student', '003', 'scone:math',    '70'
put 'student', '003', 'score:market',  '88'

put 'student', '004', 'base:number',   '604'
put 'student', '004', 'base:name',     'GoLden'
put 'student', '004', 'base:sex',      'm'
put 'student', '004', 'base:prof',     'biz'
put 'student', '004', 'score:engLish', '80'
put 'student', '004', 'score:math',    '77'
put 'student', '004', 'score:market',  '68'
```

### 查找 `rowKey=001` 的 `score:math`

```shell
get 'student', '001', 'score:math'
```

### 根据 `rowKey` 删除

```shell
deleteall 'student', '001'

scan 'student'
```

### 根据 `rowKey` 范围查询

> 左闭右开区间 `[STARTROW, ENDROW)`

```shell
count 'student'

scan 'student', {COLUMN => 'base', STARTROWN => '001', ENDROW => '004'}
```

### 条件查询

```shell
scan 'student', {FILTER => "SingleColumnFilter('base','prof','=','binary:bigdata')"}
```
