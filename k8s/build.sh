#!/bin/bash
check_command() {
  local cmd="$1"
  if ! command -v "$cmd" &> /dev/null; then
    echo "$cmd is not installed. Please install it using one of the following methods:"
    print_install_guide "$cmd"
    exit 1
  fi
}

check_command docker
check_command minikube

minikube delete
minikube start --driver=docker --addons=ingress --mount --mount-string="$(pwd)/../certs:/certs"
# shellcheck disable=SC2046
eval $(minikube docker-env)
docker build -t my-local-server:latest ..
docker build -t my-local-redis:latest ../dockerfiles/redis
