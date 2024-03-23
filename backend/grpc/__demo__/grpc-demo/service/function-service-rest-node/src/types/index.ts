export class Instance {
  name: string
  addr: string
  platform: string
  language: string
  protocol: string
  constructor(options: Instance) {
    Object.assign(this, options)
  }
}

export class Invocation {
  host?: string
  port?: number
  instances?: Instance[]
  constructor(options: Invocation) {
    Object.assign(this, options)
  }
}

export class FetchRequest {
  constructor(options: FetchRequest) {
    Object.assign(this, options)
  }
}

export class FetchResponse {
  instances: Instance[]
  constructor(options: FetchResponse) {
    Object.assign(this, options)
  }
}

export class InvokeRequest {
  invocations: Invocation[]
  constructor(options: InvokeRequest) {
    Object.assign(this, options)
  }
}

export class InvokeResponse {
  results: Map<string, Invocation>
  constructor(options: InvokeResponse) {
    Object.assign(this, options)
  }
}

export abstract class FunctionService {
  abstract fetch(request: FetchRequest): Promise<FetchResponse>
  abstract invoke(request: InvokeRequest): Promise<InvokeResponse>
}
