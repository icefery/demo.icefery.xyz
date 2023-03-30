# Monitoring

## Telegraf

#### 将 Telegraf 暴露为 Prometheus 数据格式

- `telegraf.conf`

  ```toml
  [[inputs.cpu]]
    percpu = true
    totalcpu = true
    collect_cpu_time = false
    report_active = false
    core_tags = false

  [[outputs.file]]
    files = ["stdout"]

  [[outputs.prometheus_client]]
    listen = ":9273"
  ```

- `telegraf --config telegraf.conf`
- `curl http://localhost:9273/metrics`
