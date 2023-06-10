import * as os from 'node:os'
import express from 'express'
import bodyParser from 'body-parser'
import { FetchRequest, FetchResponse, FunctionService, Instance, Invocation, InvokeRequest, InvokeResponse } from './types'

const PORT = 8080

const app = express()
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }))

const functionService: FunctionService = {
  fetch: async (request: FetchRequest): Promise<FetchResponse> => {
    const instances = [
      new Instance({
        name: 'fetch',
        addr: `${os.hostname}:${PORT}`,
        platform: `${os.platform()}/${os.arch()}`,
        language: 'javascript',
        protocol: 'grpc'
      }),
      new Instance({
        name: 'invoke',
        addr: `${os.hostname}:${PORT}`,
        platform: `${os.platform()}/${os.arch()}`,
        language: 'javascript',
        protocol: 'grpc'
      })
    ]
    const response = new FetchResponse({ instances })
    return response
  },
  invoke: async (request: InvokeRequest): Promise<InvokeResponse> => {
    const results = new Map<string, Invocation>()
    const ps = request.invocations.map(invocation => {
      const { host, port } = invocation

      const fetchRequest = new FetchRequest({})
      return fetch(`http://${host}:${port}/fetch`, { method: 'post', body: JSON.stringify(fetchRequest) })
        .then(response => response.json() as Promise<FetchResponse>)
        .then(fetchResponse => {
          const key = `${host}:${port}`
          const value = new Invocation({ instances: fetchResponse.instances })
          results.set(key, value)
        })
    })

    await Promise.all(ps)

    const response = new InvokeResponse({ results })
    return response
  }
}

app.post('/fetch', async (req, res) => {
  const request = req.body as FetchRequest
  const response = await functionService.fetch(request)
  res.json(response)
})

app.post('/invoke', async (req, res) => {
  const request = req.body as InvokeRequest
  const response = await functionService.invoke(request)
  const json = JSON.stringify(response, (key, value) => (value instanceof Map ? Object.fromEntries(value) : value))
  res.end(json)
})

app.listen(PORT, () => {
  console.log(`Listening on :${PORT}`)
})
