# Express 启用 HTTPS

## 申请证书

-   [Let's Encrypt 网页版本,获取 SSL 网站证书](https://letsencrypt.osfipin.com/)

## 使用证书

```js
const http = require('http')
const https = require('https')
const fs = require('fs')
const path = require('path')
const express = require('express')

const app = express()

app.get('/', (req, res) => {
  res.json({
    hello: 'world',
    protocol: req.protocol,
    host: req.get('host'),
    path: req.path,
    ip: req.ip
  })
})

http.createServer(app).listen(8080, () => console.log(`listening on :8080`))

const options = {
  key: fs.readFileSync(path.resolve(__dirname, '../certificate/private.pem'), 'utf-8'),
  cert: fs.readFileSync(path.resolve(__dirname, '../certificate/certificate.crt'), 'utf-8')
}
https.createServer(options, app).listen(8443, () => console.log(`listening on :8443`))
```
