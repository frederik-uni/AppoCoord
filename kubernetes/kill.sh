#!/bin/bash

pkill -f "minikube mount ../certs:/certs"
pkill -f "kubectl port-forward -n appocoord"

kubectl delete -f nginx-deployment.yml --namespace=appocoord
kubectl delete -f nginx-configmap.yml --namespace=appocoord
kubectl delete -f servers-deployment.yml --namespace=appocoord
kubectl delete -f redis-deployment.yml --namespace=appocoord
kubectl delete -f persistent-volumes.yml --namespace=appocoord
kubectl delete -f secrets.yml --namespace=appocoord
kubectl delete -f namespace.yml

echo "Cleanup complete."