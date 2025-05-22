# AppoCoord

Task:
Think about Doodle, the DFN-Terminplaner and so on, but just in a very limited and minimized
way. The App has three pages. One page to create a new appointment coordination, one to reply to
an appointment coordination and one to see the results of an appointment coordination.
To make it even more simple, an appointment coordination allows to offer between one and three
appointment suggestions. So the controls for those three suggestions can be placed statically within
the page to create a new appointment coordination. There is no need to allow to dynamically add
additional suggestions.

## Defaults

### Port

- 9090

### Api docs

- host:port/swagger

## Info

The frontend uses FingerprintJS for browser fingerprinting, so testing it on a single machine requires multiple
browsers.

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


## Start container
```sh
./generate_https_certs.sh

docker compose up
```
