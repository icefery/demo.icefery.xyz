```bash
USERNAME=icefery
APP=my-app
VERSION=0.0.1

docker build -t ${USERNAME}/${APP}:${VERSION} .

docker login --username=${USERNAME} --password-stdin

docker push ${USERNAME}/${APP}:${VERSION}
```
