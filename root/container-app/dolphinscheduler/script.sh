DRIVER_MYSQL_VERSION="8.0.16"
DRIVER_ORACLE_VERSION="19.9.0.0"

DOLPHINSCHEDULER_SERVICES=(
    dolphinscheduler-tools
    dolphinscheduler-api
    dolphinscheduler-master
    dolphinscheduler-worker
    dolphinscheduler-alert-server
)
DOLPHINSCHEDULER_VERSION="3.2.0"

function prepare() {
    mkdir -p ./__static__

    wget -O "./__static__/mysql-connector-java-${DRIVER_MYSQL_VERSION}.jar" "https://repo1.maven.org/maven2/mysql/mysql-connector-java/${DRIVER_MYSQL_VERSION}/mysql-connector-java-${DRIVER_MYSQL_VERSION}.jar"
    wget -O "./__static__/ojdbc8-${DRIVER_ORACLE_VERSION}.jar" "https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc8/${DRIVER_ORACLE_VERSION}/ojdbc8-${DRIVER_ORACLE_VERSION}.jar"

    tee ./__static__/dolphinscheduler-tools.dockerfile > /dev/null <<- EOF
FROM --platform=\${TARGETPLATFORM} docker.io/apache/dolphinscheduler-tools:${DOLPHINSCHEDULER_VERSION}
LABEL org.opencontainers.image.source=https://github.com/icefery/apache-dolphinscheduler
COPY mysql-connector-java-${DRIVER_MYSQL_VERSION}.jar ojdbc8-${DRIVER_ORACLE_VERSION}.jar /opt/dolphinscheduler/tools/libs/
EOF

    tee ./__static__/dolphinscheduler-api.dockerfile > /dev/null <<- EOF
FROM --platform=\${TARGETPLATFORM} docker.io/apache/dolphinscheduler-api:${DOLPHINSCHEDULER_VERSION}
LABEL org.opencontainers.image.source=https://github.com/icefery/apache-dolphinscheduler
COPY mysql-connector-java-${DRIVER_MYSQL_VERSION}.jar ojdbc8-${DRIVER_ORACLE_VERSION}.jar /opt/dolphinscheduler/libs/
EOF

    tee ./__static__/dolphinscheduler-master.dockerfile > /dev/null <<- EOF
FROM --platform=\${TARGETPLATFORM} docker.io/apache/dolphinscheduler-master:${DOLPHINSCHEDULER_VERSION}
LABEL org.opencontainers.image.source=https://github.com/icefery/apache-dolphinscheduler
COPY mysql-connector-java-${DRIVER_MYSQL_VERSION}.jar ojdbc8-${DRIVER_ORACLE_VERSION}.jar /opt/dolphinscheduler/libs/
EOF

    tee ./__static__/dolphinscheduler-worker.dockerfile > /dev/null <<- EOF
FROM --platform=\${TARGETPLATFORM} docker.io/apache/dolphinscheduler-worker:${DOLPHINSCHEDULER_VERSION}
LABEL org.opencontainers.image.source=https://github.com/icefery/apache-dolphinscheduler
COPY mysql-connector-java-${DRIVER_MYSQL_VERSION}.jar ojdbc8-${DRIVER_ORACLE_VERSION}.jar /opt/dolphinscheduler/libs/
EOF

    tee ./__static__/dolphinscheduler-alert-server.dockerfile > /dev/null <<- EOF
FROM --platform=\${TARGETPLATFORM} docker.io/apache/dolphinscheduler-alert-server:${DOLPHINSCHEDULER_VERSION}
LABEL org.opencontainers.image.source=https://github.com/icefery/apache-dolphinscheduler
COPY mysql-connector-java-${DRIVER_MYSQL_VERSION}.jar ojdbc8-${DRIVER_ORACLE_VERSION}.jar /opt/dolphinscheduler/libs/
EOF
}

function build-image() {
    for service in "${DOLPHINSCHEDULER_SERVICES[@]}"; do
        docker buildx build \
            --tag "ghcr.io/icefery/${service}:${DOLPHINSCHEDULER_VERSION}" \
            --file "./__static__/${service}.dockerfile" \
            --platform "linux/amd64,linux/arm64" \
            --push \
            ./__static__
    done
}

case $1 in
prepare)
    prepare
    ;;
build-image)
    build-image
    ;;
*)
    echo "Usage: $0 <prepare|build-image>"
    ;;
esac
