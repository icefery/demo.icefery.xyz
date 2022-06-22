const express = require('express')
const app = express()

app.get('/', (req, res) => {
  const url = req.url
  const host = req.get('host')
  const result = { url, host }
  res.json(result)
})

app.listen(3000)
