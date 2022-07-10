const http = require('http')
const express = require('express')

const app = express()

app.get('/*', (req, res) => {
  res.json({ url: `${req.protocol}://${req.get('host')}${req.originalUrl}` })
})

const port = 3000

const server = http.createServer(app).listen(port, () => console.log(`Listening on :${port}`))
