## Install
### docker-compose
```yaml
services:  
  web:  
    image: 'gitlab/gitlab-ce:15.0.0-ce.0'  
    container_name: 'gitlab'  
    environment:  
      GITLAB_OMNIBUS_CONFIG: |  
        external_url 'http://192.192.192.6:8929'  
        gitlab_rails['gitlab_shell_ssh_port'] = 2224  
    ports:  
      - '8929:8929'  
      - '2224:2224'  
    volumes:  
      - '/d/mount/gitlab/etc/gitlab:/etc/gitlab'  
      - '/d/mount/gitlab/var/log/gitlab:/var/log/gitlab'  
      - '/d/mount/gitlab/var/opt/gitlab:/var/opt/gitlab'  
    shm_size: '256m'  
networks:  
  default:  
    external: true  
    name: local
```

### Login
#### Password
```bash
sudo docker exec -it gitlab grep 'Password:' /etc/gitlab/initial_root_password
```
