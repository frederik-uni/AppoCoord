#!/bin/bash

print_install_guide() {
  local tool="$1"

  case "$tool" in
    yq)
      echo "  brew install yq             # macOS"
      echo "  sudo apt install yq         # Ubuntu/Debian"
      echo "  sudo dnf install yq         # Fedora"
      echo "  or download from https://github.com/mikefarah/yq"
      ;;
    docker)
      echo "  brew install --cask docker  # macOS"
      echo "  sudo apt install docker.io  # Ubuntu/Debian"
      echo "  sudo dnf install docker     # Fedora"
      echo "  or visit https://docs.docker.com/get-docker/"
      ;;
    kubectl)
      echo "  brew install kubectl        # macOS"
      echo "  sudo apt install kubectl    # Ubuntu/Debian"
      echo "  sudo dnf install kubectl    # Fedora"
      echo "  or visit https://kubernetes.io/docs/tasks/tools/"
      ;;
    minikube)
      echo "  brew install minikube       # macOS"
      echo "  curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64"
      echo "  sudo install minikube-linux-amd64 /usr/local/bin/minikube  # Ubuntu/Debian"
      echo "  sudo dnf install minikube   # Fedora (if available)"
      echo "  or visit https://minikube.sigs.k8s.io/docs/start/"
      ;;
  esac
}

check_command() {
  local cmd="$1"
  if ! command -v "$cmd" &> /dev/null; then
    echo "$cmd is not installed. Please install it using one of the following methods:"
    print_install_guide "$cmd"
    exit 1
  fi
}

check_command yq
check_command docker
check_command kubectl
check_command minikube
check_command envsubst

#todo: check if docker is running or minikube start

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
SSL_ENABLED=true
REDIS_PASSWORD=
SERVER_NAME="localhost"

while [[ "$#" -gt 0 ]]; do
  case $1 in
    --replicas)
      REPLICAS="$2"
      shift 2
      ;;
    --password)
      # shellcheck disable=SC2034
      REDIS_PASSWORD="$2"
      shift 2
      ;;
     --server-name)
      # shellcheck disable=SC2034
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
  echo "Usage: $0 --replicas <number> [--cert <path>] [--password <string>]"
  exit 1
fi

if [[ -z "$REDIS_PASSWORD" ]]; then
  echo "Error: --password flag is required."
  echo "Usage: $0 --password <string> [--replicas <number>] [--cert <path>]"
  exit 1
fi

# shellcheck disable=SC2046
eval $(minikube docker-env)
docker build -t my-local-server:latest ..
docker build -t my-local-redis:latest ../dockerfiles/redis

kubectl apply -f namespace.yml
minikube mount ../certs:/certs &
envsubst < secrets.yml | kubectl apply -f -
kubectl apply -f persistent-volumes.yml
kubectl apply -f redis-deployment.yml
yq e "select(documentIndex == 0) | .spec.replicas = $REPLICAS" servers-deployment.yml | kubectl apply -f -
kubectl apply -f servers-deployment.yml

kubectl create secret tls tls-secret \
  --cert=/certs/https/fullchain.pem \
  --key=/certs/https/privkey.pem \
  -n appocoord
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.3.0/deploy/static/provider/cloud/deploy.yaml
helm upgrade --install ingress-nginx ingress-nginx --repo https://kubernetes.github.io/ingress-nginx --namespace ingress-nginx --create-namespace
kubectl apply -f nginx-deployment.yml
kubectl wait --for=condition=Ready pod -l app=ingress-nginx -n appocoord --timeout=120s
kubectl port-forward -n ingress-nginx svc/ingress-nginx-controller 9090:80 &
kubectl port-forward -n ingress-nginx svc/ingress-nginx-controller 9091:443 &
MINIKUBE_IP=127.0.0.1
HTTP_PORT=9090
HTTPS_PORT=9091

echo "Access your web server at:"
echo "  http://$MINIKUBE_IP:$HTTP_PORT"
echo "  https://$MINIKUBE_IP:$HTTPS_PORT"