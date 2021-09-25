## 构建镜像

```bash
git clone https://github.com/oracle/docker-images.git --depth 1

OracleDatabase/SingleInstance/dockerfiles/buildContainerImage.sh -v 18.4.0 -x
```