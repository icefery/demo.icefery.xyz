# Express.js

## 常见问题

### GET 请求 Query 传递数组参数

-   启动服务

    ```javascript
    import express from 'express'

    const app = express()

    app.get('/echo-query', (req, res) => res.json(req.query))

    app.listen(3000, () => console.log('listening on :3000'))
    ```

-   请求接口

    ```shell
    curl -fsSL "http://127.0.0.1:3000/echo-query?arr[]=1&arr[]=2&arr[]=3" | jq -r
    ```

-   输出

    ```json
    {
      "arr": ["1", "2", "3"]
    }
    ```
