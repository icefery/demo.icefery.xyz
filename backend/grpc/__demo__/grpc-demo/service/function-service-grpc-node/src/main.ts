import * as os from 'node:os'
import * as grpc from '@grpc/grpc-js'
import * as messages from './generated/function_pb'
import * as services from './generated/function_grpc_pb'

const PORT = 8081

const functionService: services.IFunctionServer = {
  fetch: (call: grpc.ServerUnaryCall<messages.FetchRequest, messages.FetchResponse>, callback: grpc.sendUnaryData<messages.FetchResponse>): void => {
    const fetch = new messages.Instance()
    fetch.setName('fetch')
    fetch.setAddr(`${os.hostname()}:${PORT}`)
    fetch.setPlatform(`${os.platform()}/${os.arch()}`)
    fetch.setLanguage('javascript')
    fetch.setProtocol('grpc')
    const invoke = new messages.Instance()
    invoke.setName('invoke')
    invoke.setAddr(`${os.hostname()}:${PORT}`)
    invoke.setPlatform(`${os.platform()}/${os.arch()}`)
    invoke.setLanguage('javascript')
    invoke.setProtocol('grpc')
    const response = new messages.FetchResponse()
    response.addInstances(fetch)
    response.addInstances(invoke)

    callback(null, response)
  },
  invoke: async (call, callback) => {
    const response = new messages.InvokeResponse()
    const ps = call.request.getInvocationsList().map(invocation => {
      const host = invocation.getHost()
      const port = invocation.getPort()

      return new Promise<void>((resolve, reject) => {
        const client = new services.FunctionClient(`${host}:${port}`, grpc.credentials.createInsecure())
        const fetchRequest = new messages.FetchRequest()
        client.fetch(fetchRequest, (error, fetchResponse) => {
          if (error) {
            reject(error)
          } else {
            const key = `${host}:${port}`
            const value = new messages.Invocation()
            value.setInstancesList(fetchResponse.getInstancesList())
            response.getResultsMap().set(key, value)
            resolve()
          }
        })
      })
    })
    await Promise.all(ps)

    callback(null, response)
  }
}

const server = new grpc.Server()
server.addService(services.FunctionService, functionService)
server.bindAsync(`0.0.0.0:${PORT}`, grpc.ServerCredentials.createInsecure(), () => {
  console.log(`Listening on :${PORT}`)
  server.start()
})
