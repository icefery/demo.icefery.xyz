#!/usr/bin/env bash
set -e

APP_NAME="my-app"
APP_VERSION="$(jq -r .version < ./package.json)"

function build() {
    docker build -t "icefery/${APP_NAME}:${APP_VERSION}" .
}

function push() {
    docker login
    docker tag "icefery/${APP_NAME}:${APP_VERSION}" "icefery/${APP_NAME}:latest"
    docker push "icefery/${APP_NAME}:latest"
}

$1
