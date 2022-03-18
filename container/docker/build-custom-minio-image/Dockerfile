FROM ubuntu:20.04

ENV MINIO_ACCESS_KEY=admin \
    MINIO_SECRET_KEY=admin123

# System
RUN echo "\
    deb http://mirrors.ustc.edu.cn/ubuntu/ focal           main restricted universe multiverse\n \
    deb http://mirrors.ustc.edu.cn/ubuntu/ focal-security  main restricted universe multiverse\n \
    deb http://mirrors.ustc.edu.cn/ubuntu/ focal-updates   main restricted universe multiverse\n \
    deb http://mirrors.ustc.edu.cn/ubuntu/ focal-backports main restricted universe multiverse\n \
    "> /etc/apt/sources.list && \
    apt-get update -y && \
    apt-get install -y vim sudo

# Custom
COPY minio_0.0.20210116021944_amd64.deb /root/minio.deb
RUN apt-get install -y /root/minio.deb

RUN groupadd icefery && useradd icefery -g icefery -u 1000 -m -s /bin/bash && mkdir -p /data

# Permission
RUN echo "icefery ALL=(ALL) NOPASSWD:ALL" > /etc/sudoers.d/icefery

# Endpoint
EXPOSE 9000
VOLUME [ "/data" ]

# Runtime
USER gx

# Start
ENTRYPOINT sudo chmod 777 /data && minio server /data