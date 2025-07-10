# AppoCoord

Task:
Think about Doodle, the DFN-Terminplaner and so on, but just in a very limited and minimized
way. The App has three pages. One page to create a new appointment coordination, one to reply to
an appointment coordination and one to see the results of an appointment coordination.
To make it even more simple, an appointment coordination allows to offer between one and three
appointment suggestions. So the controls for those three suggestions can be placed statically within
the page to create a new appointment coordination. There is no need to allow to dynamically add
additional suggestions.

## Requirements
- openssl
- docker/docker-compose
- docker buildx(maybe not needed, but docker compose warns that it wasnt found)
- minikube
- kubectl
- yq(use version >=4.46)
- /bin/bash
- envsubst

## Start
`chmod +x {script_name}` may be required
```sh
# https certs
./generate_https_certs.sh

# docker
docker compose up

# kubernetes
cd k8s
# minikube start+docker build
# make sure you run minikube delete before running build if minikube is still running
./build.sh
# start + minikube tunnel
./apply.sh --replicas 2 --password secret
# stop
./kill
```


## Defaults Docker
- http://127.0.0.1:9090
- https://127.0.0.1:9091

## Defaults kubernetes
### MacOS
- http://127.0.0.1
- https://127.0.0.1

### Linux
The ip is logged by minikube tunnel at Status -> route
Example:
```
Status:
	machine: minikube
	pid: 68016
	route: 10.96.0.0/12 -> 192.168.49.2
	minikube: Running
	services: []
    errors:
```

The ip would be `192.168.49.2`

- http://{ip}
- https://{ip}

### Api docs

- host:port/swagger

## Info

The frontend uses FingerprintJS for browser fingerprinting, so testing it on a single machine requires multiple
browsers. If you are testing the ui please be aware that this project uses the default input component and that each browser might work a bit differently. for example Safari displays default values for date input, while the input is actually empty, so the validation will fail.

After creating a poll you will be redirected to the info/vote page. The page changes depending on creator, and if the time to vote is passed. After you were redirected you can just copy the url and share it.

Generic Containers were published using `./dockerfiles/publish.sh`
## Images

### Create/Reply
<div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 0;">
  <img src="https://github.com/user-attachments/assets/2efddfdb-9592-4852-90ad-17a182f544e1" style="width: 49%;" alt="Create">
  <img src="https://github.com/user-attachments/assets/438655b6-6fcd-404d-8b5e-6432c62c37f5" style="width: 49%;" alt="Reply">
</div>

### Info before/after end
<div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 0;">
  <img src="https://github.com/user-attachments/assets/6df6df3f-18f8-46d0-b031-2962a3b76121" style="width: 49%;" alt="Info before end">
  <img src="https://github.com/user-attachments/assets/23c91f6c-47a7-44f3-86cd-9ae8b86dcb70" style="width: 49%;" alt="Info">
</div>


## Essential Paths and files
- `src/main/java/com/frederik/appocoord` folder => backend code
- `pom.xml` file => backend project file
- `Dockerfile` file => backend docker file
- `docker-compose.yml` file => service docker compose file
- `k8s` folder => kubernetes
- `dockerfiles` folder => every dockerfile except backend
- `frontend` folder => frontend code/workspace
- `nginx/nginx.conf` file => nginx config(only used with docker compose)
- `generate_https_certs.sh` file => generate certs for https

## Tested
### What
- docker compose up
- minikube 

### Where
```
OS: Arch Linux x86_64
Kernel: Linux 6.15.5-arch1-1
Memory: 62.7 GiB
WM:  Hyprland 0.49.0 (Wayland)
CPU: AMD Ryzen 5 3600X (12) @ 4.64 GHz
GPU: NVIDIA GeForce RTX 2060 12GB [Discrete]

Info:
- minikube tunnel logs the ip. I decided against adding `/etc/hosts` as it is simpler for testing purposos to just use the logged ip and its not needed for macos. It wouldnt be hard to add it to the `/etc/hosts`: `192.168.49.2  myproject.local`, but i dont see a reason 2 do so. The host can be defined in ingres.yml, bu isnt so its reachable via the ip
Warning:
- `kubectl create secret tls` behaves differntly on macos/linux. it works on both of my machines right now
```

```
OS: macOS 15.5.0
Memory: 24 GiB
Architecture: aarch64
CPU/GPU: Apple M2
```
