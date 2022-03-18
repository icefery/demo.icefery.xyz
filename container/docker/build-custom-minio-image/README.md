---
title: '构建自定义 MinIO 镜像'
---

```shell
wget https://dl.min.io/server/minio/release/linux-amd64/archive/minio_0.0.20210116021944_amd64.deb
```

```shell
docker build -t my-minio:0.0.0 .
```

```shell
docker run -p 9000:9000 -v /data/:/data/ my-minio:0.0.0
```
