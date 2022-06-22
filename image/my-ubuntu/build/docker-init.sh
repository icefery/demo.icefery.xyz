#!/usr/bin/env bash

VERSION_CODENAME=$(cat /etc/os-release | grep "VERSION_CODENAME" | awk -F '=' '{print $2}')

cat > /etc/apt/sources.list <<- EOF
deb http://mirrors.aliyun.com/ubuntu/ ${VERSION_CODENAME}           main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ ${VERSION_CODENAME}-security  main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ ${VERSION_CODENAME}-updates   main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ ${VERSION_CODENAME}-backports main restricted universe multiverse
EOF

apt update && apt full-upgrade -y && apt autoremove -y

apt install -y \
  vim \
  wget \
  ca-certificates \
  curl \
  gnupg \
  lsb-release
