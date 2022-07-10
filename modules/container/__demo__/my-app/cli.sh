#!/usr/bin/env bash
set -e

APP_NAME="my-app"
APP_VERSION="$(jq -r .version < ./package.json)"

function build() {
    docker -t "icefery/${APP_NAME}:${APP_VERSION}" ./docker
}

function push() {
    docker login --username icefery --password-stdin
    docker tag "icefery/${APP_NAME}:${APP_VERSION}" "icefery/${APP_NAME}:latest"
    docker push "icefery/${APP_NAME}:latest"
}
