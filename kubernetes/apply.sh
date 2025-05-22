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

REPLICAS=

while [[ "$#" -gt 0 ]]; do
  case $1 in
    --replicas)
      REPLICAS="$2"
      shift 2
      ;;
    *)
      echo "Unknown parameter passed: $1"
      exit 1
      ;;
  esac
done

if [[ -z "$REPLICAS" ]]; then
  echo "Error: --replicas flag is required."
  echo "Usage: $0 --replicas <number>"
  exit 1
fi

minikube mount ../certs:/certs
kubectl apply -f namespace.yml
kubectl apply -f secrets.yml
kubectl apply -f persistent-volumes.yml
kubectl apply -f redis-deployment.yml
yq e ".spec.replicas = $REPLICAS" servers-deployment.yml | kubectl apply -f -
kubectl apply -f servers-deployment.yml
kubectl apply -f nginx-configmap.yml
kubectl apply -f nginx-deployment.yml