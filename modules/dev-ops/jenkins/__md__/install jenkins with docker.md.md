## Install
### docker-compose
```yaml
services:  
  jenkins:  
    image: bitnami/jenkins:2.332.3-debian-10-r2  
    container_name: jenkins  
    environment:  
      - JENKINS_USERNAME=jenkins  
      - JENKINS_PASSWORD=jenkins  
      - JENKINS_EMAIL=icefery@163.com  
    ports:  
      - '8080:8080'  
    volumes:  
      - '/d/mount/jenkins/bitnami/jenkins:/bitnami/jenkins'  
networks:  
  default:  
    external: true  
    name: local
```

### Mount
```yaml
mkdir -p /d/mount/jenkins/bitnami/jenkins

chmod 777 -R /d/mount/jenkins/bitnami/jenkins
```