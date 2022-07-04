const http = require('http')
const express = require('express')

const app = express()

app.get('/', (req, res) => {
  res.json({
    hello: 'world',
    url: `${req.protocol}://${req.get('host')}${req.originalUrl}`
  })
})

http.createServer(app).listen(8080, () => console.log('Listening on :8080'))
