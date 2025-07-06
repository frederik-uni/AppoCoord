#!/bin/bash

pkill -f "minikube mount ../certs:/certs"
pkill -f "kubectl port-forward -n appocoord"

kubectl delete -f ingres.yml --namespace=appocoord
kubectl delete -f servers-deployment.yml --namespace=appocoord
kubectl delete -f redis-deployment.yml --namespace=appocoord
kubectl delete -f secrets.yml --namespace=appocoord
kubectl delete -f namespace.yml
minikube delete
echo "Cleanup complete."