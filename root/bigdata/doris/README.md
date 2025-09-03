# Doris

## 自定义函数

### 示例

-   https://github.com/apache/doris/blob/master/samples/doris-demo/java-udf-demo/src/main/java/org/apache/doris/udf/AddOne.java

#### UDF

```java
package org.example.doris.udf;

public class udf_add_one {
    public Integer evaluate(Integer value) {
        if (value == null) {
            return null;
        }
        return value + 1;
    }
}
```

```sql
drop function if exists demo.udf_add_one(int);

create function demo.udf_add_one(int) returns int properties (
    'file'            = 'http://192.168.31.101:9000/doris/udf/java-udf-demo.jar',
    'symbol'          = 'org.example.doris.udf.udf_add_one',
    'always_nullable' = 'true',
    'type'            = 'JAVA_UDF'
);

select demo.udf_add_one(1);
```

#### UDAF

```java
package org.example.doris.udf;

import java.io.DataInputStream;
import java.io.DataOutputStream;


public class udaf_sum {
    public static class State {
        public int result = 0;
    }

    public State create() {
        return new State();
    }

    public void destroy(State state) {}

    public void reset(State state) {
        state.result = 0;
    }

    public void add(State state, Integer value) throws Exception {
        if (value != null) {
            state.result += value;
        }
    }

    public void merge(State state1, State state2) {
        state1.result += state2.result;
    }

    public Integer getValue(State state) {
        return state.result;
    }

    public void serialize(State state, DataOutputStream out) {
        try {
            out.writeInt(state.result);
        } catch (Exception ignored) {}
    }

    public void deserialize(State state, DataInputStream in) {
        try {
            state.result = in.readInt();
        } catch (Exception ignored) {}
    }
}
```

```sql
drop function if exists demo.udaf_sum(int);

create aggregate function demo.udaf_sum(int) returns int properties (
    'file'            = 'http://192.168.31.101:9000/doris/udf/java-udf-demo.jar',
    'symbol'          = 'org.example.doris.udf.udaf_sum',
    'always_nullable' = 'true',
    'type'            = 'JAVA_UDF'
);

select category, demo.udaf_sum(weight) as weight
from (
  select 'a' as category, 1 as weight union all
  select 'a' as category, 2 as weight union all
  select 'c' as category, 3 as weight
) t
group by category;
```

#### UDTF

```java
package org.example.doris.udf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;

public class udtf_unnest_json_array {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public ArrayList<HashMap<String, String>> evaluate(String value) throws Exception {
        if (value == null) {
            return null;
        }
        return OBJECT_MAPPER.readValue(value, new TypeReference<ArrayList<HashMap<String, String>>>() {});
    }
}
```

```sql
drop function if exists demo.udtf_unnest_json_array(array<int>);

create tables function demo.udtf_unnest_json_array(array<int>) returns array<int> properties (
    'file'            = 'http://192.168.31.101:9000/doris/udf/java-udf-demo.jar',
    'symbol'          = 'org.example.doris.udtf.udtf_unnest_json_array',
    'always_nullable' = 'true',
    'type'            = 'JAVA_UDF'
);

-- FIXME: Doris 3.0.2 版本 UDTF 在调用时不能带库名
select
    cast(json_extract_string(c, '$.id') as bigint) as id,
    json_extract_string(c, '$.name') as name,
    json_extract_string(c, '$.category') as category
from (select 1 temp) as temp
lateral view udtf_unnest_json_array('
    [
        { "id": 1, "name": "大熊猫", "category": "熊" },
        { "id": 2, "name": "大熊猫", "category": "猫" },
        { "id": 3, "name": "小熊猫", "category": "猫" }
    ]
') t as c;
```

## 访问 S3

```sql
drop resource if exists minio_duckdb;

create resource minio_duckdb
properties(
   'type'          = 's3',
   's3.endpoint'   = 'http://192.168.31.101:9000',
   's3.region'     = 'us-east-1',
   's3.bucket'     = 'duckdb',
   's3.access_key' = 'admin',
   's3.secret_key' = 'admin:0000'
);


select *
from s3(
    'uri'            = 's3://duckdb/test/user_info/pt=default/data_0.parquet',
    'resource'       = 'minio_duckdb',
    -- 's3.endpoint'    = 'http://192.168.31.101:9000',
    -- 's3.access_key'  = 'readonly',
    -- 's3.secret_key'  = 'readonly',
    'format'         = 'parquet',
    'use_path_style' = 'true'
)
limit 10;
```
