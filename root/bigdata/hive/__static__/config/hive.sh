#!/usr/bin/env bash

function check {
    if [[ ${HADOOP_HOME} == "" ]]; then
        echo "HADOOP_HOME is not defined."
        exit 1
    fi
    if [[ ${HIVE_HOME} == "" ]]; then
        echo "HIVE_HOME is not defined."
        exit 1
    fi
}

function init {
    rm $(find "${HIVE_HOME}" -name "guava-*")
    cp $(ls "${HADOOP_HOME}"/share/hadoop/common/lib/guava-*) "${HIVE_HOME}/lib"
    if [[ $(ls "${HIVE_HOME}/lib" | grep 'mysql-connector-java') == "" ]]; then
        wget https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.16/mysql-connector-java-8.0.16.jar -P "${HIVE_HOME}/lib"
    fi
}

function start {
    nohup "${HIVE_HOME}/bin/hive" --service metastore &> /dev/null &
    nohup "${HIVE_HOME}/bin/hive" --service hiveserver2 &> /dev/null &
}

function stop {
    kill -9 $("${JAVA_HOME}/bin/jps" -ml | grep 'HiveMetaStore' | awk '{print $1}')
    kill -9 $("${JAVA_HOME}/bin/jps" -ml | grep 'HiveServer2' | awk '{print $1}')
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
