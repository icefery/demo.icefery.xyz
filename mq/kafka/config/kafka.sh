#!/usr/bin/env bash

function check {
  if [[ "${KAFKA_HOME}" = "" ]]; then
    echo "KAFKA_HOME is not defined."
    exit 1
  fi
}

function init {
  "${KAFKA_HOME}"/bin/kafka-storage.sh random-uuid > "${KAFKA_HOME}"/uuid
  "${KAFKA_HOME}"/bin/kafka-storage.sh format -t "$(cat "${KAFKA_HOME}"/uuid)" -c "${KAFKA_HOME}"/config/kraft/server.properties
}

function reset {
  rm -r "${KAFKA_HOME}"/data "${KAFKA_HOME}"/logs
}

function start {
  "${KAFKA_HOME}"/bin/kafka-server-start.sh -daemon "${KAFKA_HOME}"/config/kraft/server.properties
}

function stop {
  "${KAFKA_HOME}"/bin/kafka-server-stop.sh
}

function help {
  echo "USAGE: ./$0 <init | reset | start | stop>"
}

check

case $1 in 
  init)  init  ;;
  reset) reset ;;
  start) start ;;
  stop)  stop  ;;
  *)     help  ;;
esac