#!/bin/bash

if ! command -v yq &> /dev/null; then
    echo "yq is not installed. Please install it using:"
    echo "  brew install yq             # macOS"
    echo "  sudo apt install yq         # Ubuntu/Debian"
    echo "  sudo dnf install yq         # Fedora"
    echo "  or download from https://github.com/mikefarah/yq"
    exit 1
else
    echo "yq is installed."
    yq --version
fi

print_help() {
  echo "Usage: $0 --replicas <number> [--ssl] [--cert <path>] [--password <string>] [-h|--help]"
  echo
  echo "Options:"
  echo "  --replicas <number>    Number of replicas to set (required)"
  echo "  --ssl                  Enable SSL (sets SSL to true in secrets)"
  echo "  --cert <path>          Certificate path (default: /tls)"
  echo "  --password <string>    Redis password (if not set, omitted from secrets)"
  echo "  -h, --help             Show this help message and exit"
}

REPLICAS=
SSL_ENABLED=false
CERT_PATH=""
REDIS_PASSWORD=""

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
    --cert)
      CERT_PATH="$2"
      shift 2
      ;;
    --password)
      REDIS_PASSWORD="$2"
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
  .stringData.CERT_PATH = (env(CERT_PATH) != "" ? env(CERT_PATH) : "/tls") |
  (env(REDIS_PASSWORD) != "" ? .stringData.REDIS_PASSWORD = env(REDIS_PASSWORD) : del(.stringData.REDIS_PASSWORD))
' secrets.yml > temp-secrets.yml

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