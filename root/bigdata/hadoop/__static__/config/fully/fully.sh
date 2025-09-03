#!/usr/bin/env bash

mapfile -t WORKERS < "${HADOOP_HOME}/etc/hadoop/workers"

NAME_NODE_HOST=${WORKERS[0]}
RESOURCE_MANAGER_HOST=${WORKERS[1]}
HISTORY_SERVER_HOST=${WORKERS[1]}

MAIN_HOST=${WORKERS[0]}

function check {
    if [[ ${HADOOP_HOME} == "" ]]; then
        echo "HADOOP_HOME is not defined."
        exit 1
    fi
    if [[ ${#WORKERS[@]} == 0 ]]; then
        echo "WORKERS is empty."
        exit 1
    fi
    if [[ ${NAME_NODE_HOST} == "" ]]; then
        echo "NAME_NODE_HOST is not found."
        exit 1
    fi
    if [[ ${RESOURCE_MANAGER_HOST} == "" ]]; then
        echo "RESOURCE_MANAGER_HOST is not found."
        exit 1
    fi
    if [[ ${HISTORY_SERVER_HOST} == "" ]]; then
        echo "HISTORY_SERVER_HOST is not found."
        exit 1
    fi
    if [[ ${MAIN_HOST} == "" ]]; then
        echo "MAIN_HOST is not found."
        exit 1
    fi
}

function init {
    ssh "${NAME_NODE_HOST}" "${HADOOP_HOME}/bin/hdfs" namenode -format
}

function reset {
    for host in "${WORKERS[@]}"; do
        ssh "${host}" rm -r "${HADOOP_HOME}/data/dfs/name" "${HADOOP_HOME}/logs"
    done
}

function sync {
    for host in "${WORKERS[@]}"; do
        if [[ ${host} != "${MAIN_HOST}" ]]; then
            scp -r "${MAIN_HOST}:${HADOOP_HOME}/etc" "${host}:${HADOOP_HOME}"
        fi
    done
}

function start {
    ssh "${NAME_NODE_HOST}" "${HADOOP_HOME}/sbin/start-dfs.sh"
    ssh "${RESOURCE_MANAGER_HOST}" "${HADOOP_HOME}/sbin/start-yarn.sh"
    ssh "${HISTORY_SERVER_HOST}" "${HADOOP_HOME}/bin/mapred --daemon start historyserver"
}

function stop {
    ssh "${HISTORY_SERVER_HOST}" "${HADOOP_HOME}/bin/mapred --daemon stop historyserver"
    ssh "${RESOURCE_MANAGER_HOST}" "${HADOOP_HOME}/sbin/stop-yarn.sh"
    ssh "${NAME_NODE_HOST}" "${HADOOP_HOME}/sbin/stop-dfs.sh"
}

function jps {
    for host in "${WORKERS[@]}"; do
        echo "========== ${host} =========="
        ssh "${host}" "${JAVA_HOME}/bin/jps" -ml
    done
}

function help {
    echo "USAGE: $0 <init | reset | sync | start | stop | jps>"
}

check

case $1 in
init) init ;;
reset) reset ;;
sync) sync ;;
start) start ;;
stop) stop ;;
jps) jps ;;
*) help ;;
esac
