# Monitoring

## Telegraf

#### 将 Telegraf 暴露为 Prometheus 数据格式

-   `telegraf.conf`

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

-   `telegraf --config telegraf.conf`
-   `curl http://localhost:9273/metrics`

#### Prometheus 远端存储到 InfluxDB

-   `telegraf.conf`

    ```toml
    [[inputs.http_listener_v2]]
      service_address = ":1234"
      paths = ["/receive"]
      data_format = "prometheusremotewrite"

    [[outputs.influxdb_v2]]
      urls = ["http://192.192.192.6:8086"]
      token = "cL3fFaCMjyEuzU7Bs0uXDApAovt934Y5oE22keyVFHQS1_ELriSLQ6Jneg-bJTVPpHXPddmGENbS6YwwuV9lrw=="
      organization = "admin"
      bucket = "prometheus"
    ```

-   `prometheus.yml`

    ```yml
    global:
      scrape_timeout: 10s
      scrape_interval: 10s
      evaluation_interval: 10s

    scrape_configs:
      - job_name: 'prometheus'
        static_configs:
          - targets: ['192.192.192.6:9090']
      - job_name: 'pushgateway'
        static_configs:
          - targets: ['192.192.192.6:9091']
      - job_name: 'node-exporter'
        static_configs:
          - targets: ['192.192.192.6:9100']
      - job_name: 'cadvisor'
        static_configs:
          - targets: ['192.192.192.6:8080']

    remote_write:
      - url: 'http://192.192.192.6:1234/receive'
    ```
