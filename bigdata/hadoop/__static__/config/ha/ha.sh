#!/usr/bin/env bash

mapfile -t WORKERS < "${HADOOP_HOME}/etc/hadoop/workers"

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
    if [[ ${HISTORY_SERVER_HOST} == "" ]]; then
        echo "HISTORY_SERVER_HOST is not found."
        exit 1
    fi
    if [[ ${MAIN_HOST} == "" ]]; then
        echo "MAIN_HOST is not found."
    fi
}

function init {
    for host in "${WORKERS[@]}"; do
        ssh "${host}" "$HADOOP_HOME/bin/hdfs" -daemon start journalnode
    done
    ssh "${MAIN_HOST}" "$HADOOP_HOME/bin/hdfs" namenode -format
    for host in "${WORKERS[@]}"; do
        if [[ ${host} != "${MAIN_HOST}" ]]; then
            ssh "${host}" "$HADOOP_HOME/bin/hdfs" namenode -bootstrapStandby
        fi
    done
    ssh "${MAIN_HOST}" "$HADOOP_HOME/bin/hdfs" zkfs -formatZK
}

function reset {
    for host in "${WORKERS[@]}"; do
        ssh "${host}" rm -r "${HADOOP_HOME}/data/dfs/name" "${HADOOP_HOME}/logs"
    done
}

function sync {
    for host in "${WORKERS[@]}"; do
        if [[ ${host} != "$(hostname)" ]]; then
            scp -r "${HADOOP_HOME}/etc" "${host}:${HADOOP_HOME}"
        fi
    done
}

function start {
    "${HADOOP_HOME}/sbin/start-dfs.sh"
    "${HADOOP_HOME}/sbin/start-yarn.sh"
    "${HADOOP_HOME}/bin/mapred --daemon start historyserver"
}

function stop {
    "${HADOOP_HOME}/bin/mapred --daemon stop historyserver"
    "${HADOOP_HOME}/sbin/stop-yarn.sh"
    "${HADOOP_HOME}/sbin/stop-dfs.sh"
}

function ha {
    "$HADOOP_HOME/bin/hdfs" haadmin -getAllServiceState
    "$HADOOP_HOME/bin/yarn" rmadmin -getAllServiceState
}

function jps {
    for host in "${WORKERS[@]}"; do
        echo "========== ${host} =========="
        ssh "${host}" "${JAVA_HOME}/bin/jps" -ml
    done
}

function help {
    echo "USAGE: $0 <init | reset | sync | start | stop | ha | jps>"
}

check

case $1 in
init) init ;;
reset) reset ;;
sync) sync ;;
start) start ;;
stop) stop ;;
ha) ha ;;
jps) jps ;;
*) help ;;
esac
