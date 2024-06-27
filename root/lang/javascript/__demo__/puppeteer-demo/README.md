## 模拟

### Book

#### 启动 OAuth2 认证服务器

```shell
git clone https://github.com/spring-projects/spring-authorization-server.git --depth=1

cd cd samples/default-authorizationserver/

../../gradlew bootRun
```

```yaml
server:
  port: 9000

spring:
  security:
    user:
      name: 'admin'
      password: 'admin'
    oauth2:
      authorizationserver:
        client:
          book-client:
            registration:
              client-id: 'book'
              client-secret: '{noop}book'
              client-authentication-methods:
                - 'client_secret_basic'
                - 'client_secret_post'
              authorization-grant-types:
                - 'authorization_code'
                - 'client_credentials'
                - 'refresh_token'
              redirect-uris:
                - 'http://127.0.0.1:8080/callback'
                - 'https://baidu.com'
              scopes:
                - 'all'
```

#### 启动 OAuth2 资源服务器

```shell
node src/mock/book/main.js
```

## 示例

### OAuth2

#### 通过授权码模式获取 access_token

```shell
node src/bin/oauth2/get_oauth2_access_token_by_authorization_code.js
```

#### 通过客户端模式获取 access_token

```shell
node src/bin/oauth2/get_oauth2_access_token_by_client_credentials.js
```
