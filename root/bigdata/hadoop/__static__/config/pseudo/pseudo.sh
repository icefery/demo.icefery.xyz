#!/usr/bin/env bash

function check {
    if [[ ${HADOOP_HOME} == "" ]]; then
        echo "HADOOP_HOME is not defined."
        exit 1
    fi
}

function init {
    "${HADOOP_HOME}/bin/hdfs" namenode -format
}

function reset {
    rm -r "${HADOOP_HOME}/data/dfs/name" "${HADOOP_HOME}/logs"
}

function start {
    "${HADOOP_HOME}/sbin/start-dfs.sh"
    "${HADOOP_HOME}/sbin/start-yarn.sh"
    "${HADOOP_HOME}/bin/mapred" --daemon start historyserver
}

function stop {
    "${HADOOP_HOME}/bin/mapred" --daemon stop historyserver
    "${HADOOP_HOME}/sbin/stop-dfs.sh"
    "${HADOOP_HOME}/sbin/stop-yarn.sh"
}

function jps {
    "${JAVA_HOME}/bin/jps" -ml
}

function help {
    echo "USAGE: $0 <init | reset | start | stop | jps>"
}

check

case "$1" in
init) init ;;
reset) reset ;;
start) start ;;
stop) stop ;;
jps) jps ;;
*) help ;;
esac
