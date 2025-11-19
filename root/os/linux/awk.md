## AWK

#### 日志列拆分和对齐

<details>
<summary><code>cat a.log</code></summary>

```log
[ 2025-11-19 10:44:09 DEBUG    log_around /op/auto_operation_next/workflow/task/warehouse_region_sku_product_wide.py:433  Task.sr_for_first_entry_time MainProcess MainThread ]: ENTER
[ 2025-11-19 10:45:48 DEBUG    log_around /op/auto_operation_next/workflow/task/warehouse_region_sku_product_wide.py:433  Task.sr_for_first_entry_time MainProcess MainThread ]: EXIT cost=99873
[ 2025-11-19 10:45:49 DEBUG    log_around /op/auto_operation_next/workflow/task/warehouse_region_sku_product_wide.py:494  Task.sr_for_forecast MainProcess MainThread ]: ENTER
[ 2025-11-19 10:45:58 DEBUG    log_around /op/auto_operation_next/workflow/task/warehouse_region_sku_product_wide.py:494  Task.sr_for_forecast MainProcess MainThread ]: EXIT cost=9455
[ 2025-11-19 10:45:58 DEBUG    log_around /op/auto_operation_next/workflow/task/warehouse_region_sku_product_wide.py:537  Task.sr_for_main MainProcess MainThread ]: ENTER
[ 2025-11-19 10:48:34 DEBUG    log_around /op/auto_operation_next/workflow/task/warehouse_region_sku_product_wide.py:537  Task.sr_for_main MainProcess MainThread ]: EXIT cost=156270
```

</details>

<details>
<summary><code>cat a.log | grep EXIT | awk -F '=' '{printf "%-220s %s\n", $1, $2/1000/60}'</code></summary>

```log
[ 2025-11-19 10:45:48 DEBUG    log_around /op/auto_operation_next/workflow/task/warehouse_region_sku_product_wide.py:433  Task.sr_for_first_entry_time MainProcess MainThread ]: EXIT cost               1.66455
[ 2025-11-19 10:45:58 DEBUG    log_around /op/auto_operation_next/workflow/task/warehouse_region_sku_product_wide.py:494  Task.sr_for_forecast MainProcess MainThread ]: EXIT cost                       0.157583
[ 2025-11-19 10:48:34 DEBUG    log_around /op/auto_operation_next/workflow/task/warehouse_region_sku_product_wide.py:537  Task.sr_for_main MainProcess MainThread ]: EXIT cost                           2.6045
```

</details>
