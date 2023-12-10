#!/usr/bin/env bash

declare KAFKA_SERVER_CONFIG_FILE
declare KAFKA_UUID_FILE
declare KAFKA_DATA_DIR
declare KAFKA_LOG_DIR

function check {
    if [[ -z ${KAFKA_HOME} ]]; then
        echo "KAFKA_HOME is not defined."
        exit 1
    fi
    KAFKA_SERVER_CONFIG_FILE="${KAFKA_HOME}/config/kraft/server.properties"
    KAFKA_UUID_FILE="${KAFKA_HOME}/uuid"
    KAFKA_DATA_DIR=$(grep "log.dirs" "${KAFKA_SERVER_CONFIG_FILE}" | cut -d "=" -f 2)
    KAFKA_LOG_DIR="${KAFKA_HOME}/logs"
}

function init {
    rm -rf "${KAFKA_DATA_DIR}"
    rm -rf "${KAFKA_LOG_DIR}"
    "${KAFKA_HOME}/bin/kafka-storage.sh" random-uuid > "${KAFKA_UUID_FILE}"
    "${KAFKA_HOME}/bin/kafka-storage.sh" format -t "$(cat "${KAFKA_UUID_FILE}")" -c "${KAFKA_SERVER_CONFIG_FILE}"
}

function start {
    "${KAFKA_HOME}/bin/kafka-server-start.sh" -daemon "${KAFKA_SERVER_CONFIG_FILE}"
}

function stop {
    "${KAFKA_HOME}/bin/kafka-server-stop.sh"
}

function help {
    echo "USAGE: $0 <init | start | stop>"
}

check

case $1 in
init) init ;;
start) start ;;
stop) stop ;;
*) help ;;
esac
