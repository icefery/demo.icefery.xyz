# gRPC demo

## 生成代码

### Go

```shell
go install google.golang.org/protobuf/cmd/protoc-gen-go@v1.28
go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@v1.2
```

### Node

```shell
npm install -D grpc_tools_node_protoc_ts grpc-tools
```

## 测试

### `FunctionService`

-   `fetch`

    ```json
    {}
    ```

-   `invoke`

    ```json
    {
      "invocations": [
        { "host": "localhost", "port": 8081 },
        { "host": "127.0.0.1", "port": 8081 }
      ]
    }
    ```
