# DolphinScheduler

## Docker

```shell
sudo nerdctl compose --profile schema up -d
sudo nerdctl compose --profile all    up -d
```

## Kubernetes

```shell
git clone https://github.com/apache/dolphinscheduler.git

cd dolphinscheduler/deploy/kubernetes/dolphinscheduler

# 修改 HelmChart

helm install dolphinscheduler . --namespace dolphinscheduler --create-namespace --values ./values-override.yaml
```

### 修改 HelmChart

-   `Charts.yaml`

-   ZooKeeper：`11.4.11` 是最新一个 11 版本，对应 ZooKeeper 版本为 `3.8.2`。
-   PostgreSQL：`12.12.10` 是最新一个 12 版本，对应 PostgreSQL 版本为 `15.4.0`。
-   MySQL：`TODO`。
-   MinIO：`11.10.26` 是最新一个 11 版本，对应 MinIO 版本为 `2022.12.12`。

    ```yaml
    dependencies:
      - name: postgresql
        version: 12.12.10
        repository: https://charts.bitnami.com/bitnami
        condition: postgresql.enabled
      - name: zookeeper
        version: 11.4.11
        repository: https://charts.bitnami.com/bitnami
        condition: zookeeper.enabled
      - name: mysql
        version: 9.4.1
        repository: https://charts.bitnami.com/bitnami
        condition: mysql.enabled
      - name: minio
        version: 11.10.26
        repository: https://charts.bitnami.com/bitnami
        condition: minio.enabled
    ```

-   `templates/_helpers.tpl`

    -   Sub HelmChart 顶级 `params`。
    -   Bitnami HelmChart 普通数据库用户的 Secret Key 为 `database`。

    ```yaml
    - name: SPRING_DATASOURCE_URL
      {{- if .Values.postgresql.enabled }}
      value: jdbc:postgresql://{{ template "dolphinscheduler.postgresql.fullname" . }}:5432/{{ .Values.postgresql.postgresqlDatabase }}?{{ .Values.postgresql.params }}
      {{- else if .Values.mysql.enabled }}
      value: jdbc:mysql://{{ template "dolphinscheduler.mysql.fullname" . }}:3306/{{ .Values.mysql.auth.database }}?{{ .Values.mysql.params }}
      {{- else }}
      value: jdbc:{{ .Values.externalDatabase.type }}://{{ .Values.externalDatabase.host }}:{{ .Values.externalDatabase.port }}/{{ .Values.externalDatabase.database }}?{{ .Values.externalDatabase.params }}
      {{- end }}
    - name: SPRING_DATASOURCE_USERNAME
      {{- if .Values.postgresql.enabled }}
      value: {{ .Values.postgresql.auth.username }}
      {{- else if .Values.mysql.enabled }}
      value: {{ .Values.mysql.auth.username }}
      {{- else }}
      value: {{ .Values.externalDatabase.username | quote }}
      {{- end }}
    - name: SPRING_DATASOURCE_PASSWORD
      valueFrom:
        secretKeyRef:
          {{- if .Values.postgresql.enabled }}
          name: {{ template "dolphinscheduler.postgresql.fullname" . }}
          key: password
          {{- else if .Values.mysql.enabled }}
          name: {{ template "dolphinscheduler.mysql.fullname" . }}
          key: password
          {{- else }}
          name: {{ include "dolphinscheduler.fullname" . }}-externaldb
          key: database-password
          {{- end }}
    ```

-   `values.yaml`

    -   Sub HelmChart 顶级 `params`。
    -   PostgreSQL JDBC 的参数更换为有意义的值。https://jdbc.postgresql.org/documentation/use/#connection-parameters

    ```yaml
    postgresql:
      enabled: true
      driverClassName: 'org.postgresql.Driver'
      params: 'stringtype=unspecified'
      auth:
        postgresPassword: postgres
        username: dolphinscheduler
        password: dolphinscheduler
        database: dolphinscheduler
    mysql:
      enabled: true
      driverClassName: 'com.mysql.cj.jdbc.Driver'
      params: 'characterEncoding=utf8'
      auth:
        rootPassword: root
        username: dolphinscheduler
        password: dolphinscheduler
        database: dolphinscheduler
    ```

## 集成

### Python

```shell
bash ./Miniconda3-py311_23.10.0-1-Linux-x86_64.sh -b -p /opt/soft/python

export PYTHON_LAUNCHER=/opt/soft/python/bin/python
```

### DataX

```shell
wget https://datax-opensource.oss-cn-hangzhou.aliyuncs.com/202308/datax.tar.gz

tar -zxvf ./datax.tar.gz /opt/soft
```
