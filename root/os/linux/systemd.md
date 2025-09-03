# Systemd

## 配置 Java 进程

### 命令方式

-   `/etc/systemd/system/my-app-maven.service`

    ```toml
    [Unit]
    Description=my-app-maven
    After=network.target
    After=syslog.target

    [Service]
    User=ubuntu
    Group=ubuntu
    ExecStart=/opt/env/jdk-17 -jar /opt/module/my-app/maven/my-app-maven.jar
    StandardOutput=file:/opt/module/my-app-maven/my-app-maven.log
    StandardError=inherit
    Type=forking
    Restart=on-failure

    [Install]
    WantedBy=multi-user.target
    ```

### 脚本方式

-   `/etc/systemd/system/my-app-maven.service`

    ```toml
    [Unit]
    Description=my-app-maven
    After=network.target
    After=syslog.target

    [Service]
    User=ubuntu
    Group=ubuntu
    ExecStart=/opt/code/my-app-maven/my-app-maven.sh start
    Type=forking
    Restart=on-failure

    [Install]
    WantedBy=multi-user.target
    ```

-   `/opt/code/my-app-maven/my-app-maven.sh`

    ```shell
    #!/usr/bin/env bash
    source /etc/profile

    JAVA_HOME="/opt/env/jdk-17"
    JAVA_OPTS=(
        -Xmx1g
        -Xms1g
    )
    APP_HOME="/opt/module/my-app-maven"
    APP_NAME="my-app-maven"

    nohup "${JAVA_HOME}/bin/java" "${JAVA_OPTS[@]}" -jar "${APP_HOME}/${APP_NAME}.jar" &> "${APP_HOME}/${APP_NAME}.log" &
    ```
