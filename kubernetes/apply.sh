#!/bin/bash

if ! command -v yq &> /dev/null; then
    echo "yq is not installed. Please install it using:"
    echo "  brew install yq             # macOS"
    echo "  sudo apt install yq         # Ubuntu/Debian"
    echo "  sudo dnf install yq         # Fedora"
    echo "  or download from https://github.com/mikefarah/yq"
    exit 1
fi

print_help() {
  echo "Usage: $0 --replicas <number> [--ssl] [--cert <path>] [--password <string>] [--server-name <string>] [-h|--help]"
  echo
  echo "Options:"
  echo "  --replicas <number>    Number of replicas to set (required)"
  echo "  --ssl                  Enable SSL (sets SSL to true in secrets)"
  echo "  --password <string>    Redis password (if not set, omitted from secrets)"
  echo "  --server-name <string> Nginx server name [default: localhost]"
  echo "  -h, --help             Show this help message and exit"
}

REPLICAS=
SSL_ENABLED=false
REDIS_PASSWORD=""
SERVER_NAME=""

while [[ "$#" -gt 0 ]]; do
  case $1 in
    --replicas)
      REPLICAS="$2"
      shift 2
      ;;
    --ssl)
      SSL_ENABLED=true
      shift
      ;;
    --password)
      REDIS_PASSWORD="$2"
      shift 2
      ;;
     --server-name)
      SERVER_NAME="$2"
      shift 2
      ;;
    -h|--help)
      print_help
      exit 0
      ;;
    *)
      echo "Unknown parameter passed: $1"
      exit 1
      ;;
  esac
done

if [[ -z "$REPLICAS" ]]; then
  echo "Error: --replicas flag is required."
  echo "Usage: $0 --replicas <number> [--ssl] [--cert <path>] [--password <string>]"
  exit 1
fi

yq e '
  .stringData.SSL = (env(SSL_ENABLED) == "true" | tostring) |
  .stringData.SERVER_NAME = (env(SERVER_NAME) != "" ? env(SERVER_NAME) : "localhost") |
  (env(REDIS_PASSWORD) != "" ? .stringData.REDIS_PASSWORD = env(REDIS_PASSWORD) : del(.stringData.REDIS_PASSWORD))
' secrets.yml > temp-secrets.yml

# shellcheck disable=SC2046
eval $(minikube docker-env)
docker build -t my-local-server:latest ..
docker build -t my-local-redis:latest ../dockerfiles/redis

minikube mount ../certs:/certs
kubectl apply -f namespace.yml
kubectl apply -f temp-secrets.yml
kubectl apply -f persistent-volumes.yml
kubectl apply -f redis-deployment.yml
yq e ".spec.replicas = $REPLICAS" servers-deployment.yml | kubectl apply -f -
kubectl apply -f servers-deployment.yml
kubectl apply -f nginx-configmap.yml
kubectl apply -f nginx-deployment.yml

rm temp-secrets.yml