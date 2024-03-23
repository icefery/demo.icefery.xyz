// package:
// file: function.proto

/* tslint:disable */
/* eslint-disable */

import * as jspb from 'google-protobuf'

export class Instance extends jspb.Message {
  getName(): string
  setName(value: string): Instance
  getAddr(): string
  setAddr(value: string): Instance
  getPlatform(): string
  setPlatform(value: string): Instance
  getLanguage(): string
  setLanguage(value: string): Instance
  getProtocol(): string
  setProtocol(value: string): Instance

  serializeBinary(): Uint8Array
  toObject(includeInstance?: boolean): Instance.AsObject
  static toObject(includeInstance: boolean, msg: Instance): Instance.AsObject
  static extensions: { [key: number]: jspb.ExtensionFieldInfo<jspb.Message> }
  static extensionsBinary: {
    [key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>
  }
  static serializeBinaryToWriter(message: Instance, writer: jspb.BinaryWriter): void
  static deserializeBinary(bytes: Uint8Array): Instance
  static deserializeBinaryFromReader(message: Instance, reader: jspb.BinaryReader): Instance
}

export namespace Instance {
  export type AsObject = {
    name: string
    addr: string
    platform: string
    language: string
    protocol: string
  }
}

export class Invocation extends jspb.Message {
  getHost(): string
  setHost(value: string): Invocation
  getPort(): number
  setPort(value: number): Invocation
  clearInstancesList(): void
  getInstancesList(): Array<Instance>
  setInstancesList(value: Array<Instance>): Invocation
  addInstances(value?: Instance, index?: number): Instance

  serializeBinary(): Uint8Array
  toObject(includeInstance?: boolean): Invocation.AsObject
  static toObject(includeInstance: boolean, msg: Invocation): Invocation.AsObject
  static extensions: { [key: number]: jspb.ExtensionFieldInfo<jspb.Message> }
  static extensionsBinary: {
    [key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>
  }
  static serializeBinaryToWriter(message: Invocation, writer: jspb.BinaryWriter): void
  static deserializeBinary(bytes: Uint8Array): Invocation
  static deserializeBinaryFromReader(message: Invocation, reader: jspb.BinaryReader): Invocation
}

export namespace Invocation {
  export type AsObject = {
    host: string
    port: number
    instancesList: Array<Instance.AsObject>
  }
}

export class FetchRequest extends jspb.Message {
  serializeBinary(): Uint8Array
  toObject(includeInstance?: boolean): FetchRequest.AsObject
  static toObject(includeInstance: boolean, msg: FetchRequest): FetchRequest.AsObject
  static extensions: { [key: number]: jspb.ExtensionFieldInfo<jspb.Message> }
  static extensionsBinary: {
    [key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>
  }
  static serializeBinaryToWriter(message: FetchRequest, writer: jspb.BinaryWriter): void
  static deserializeBinary(bytes: Uint8Array): FetchRequest
  static deserializeBinaryFromReader(message: FetchRequest, reader: jspb.BinaryReader): FetchRequest
}

export namespace FetchRequest {
  export type AsObject = {}
}

export class FetchResponse extends jspb.Message {
  clearInstancesList(): void
  getInstancesList(): Array<Instance>
  setInstancesList(value: Array<Instance>): FetchResponse
  addInstances(value?: Instance, index?: number): Instance

  serializeBinary(): Uint8Array
  toObject(includeInstance?: boolean): FetchResponse.AsObject
  static toObject(includeInstance: boolean, msg: FetchResponse): FetchResponse.AsObject
  static extensions: { [key: number]: jspb.ExtensionFieldInfo<jspb.Message> }
  static extensionsBinary: {
    [key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>
  }
  static serializeBinaryToWriter(message: FetchResponse, writer: jspb.BinaryWriter): void
  static deserializeBinary(bytes: Uint8Array): FetchResponse
  static deserializeBinaryFromReader(message: FetchResponse, reader: jspb.BinaryReader): FetchResponse
}

export namespace FetchResponse {
  export type AsObject = {
    instancesList: Array<Instance.AsObject>
  }
}

export class InvokeRequest extends jspb.Message {
  clearInvocationsList(): void
  getInvocationsList(): Array<Invocation>
  setInvocationsList(value: Array<Invocation>): InvokeRequest
  addInvocations(value?: Invocation, index?: number): Invocation

  serializeBinary(): Uint8Array
  toObject(includeInstance?: boolean): InvokeRequest.AsObject
  static toObject(includeInstance: boolean, msg: InvokeRequest): InvokeRequest.AsObject
  static extensions: { [key: number]: jspb.ExtensionFieldInfo<jspb.Message> }
  static extensionsBinary: {
    [key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>
  }
  static serializeBinaryToWriter(message: InvokeRequest, writer: jspb.BinaryWriter): void
  static deserializeBinary(bytes: Uint8Array): InvokeRequest
  static deserializeBinaryFromReader(message: InvokeRequest, reader: jspb.BinaryReader): InvokeRequest
}

export namespace InvokeRequest {
  export type AsObject = {
    invocationsList: Array<Invocation.AsObject>
  }
}

export class InvokeResponse extends jspb.Message {
  getResultsMap(): jspb.Map<string, Invocation>
  clearResultsMap(): void

  serializeBinary(): Uint8Array
  toObject(includeInstance?: boolean): InvokeResponse.AsObject
  static toObject(includeInstance: boolean, msg: InvokeResponse): InvokeResponse.AsObject
  static extensions: { [key: number]: jspb.ExtensionFieldInfo<jspb.Message> }
  static extensionsBinary: {
    [key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>
  }
  static serializeBinaryToWriter(message: InvokeResponse, writer: jspb.BinaryWriter): void
  static deserializeBinary(bytes: Uint8Array): InvokeResponse
  static deserializeBinaryFromReader(message: InvokeResponse, reader: jspb.BinaryReader): InvokeResponse
}

export namespace InvokeResponse {
  export type AsObject = {
    resultsMap: Array<[string, Invocation.AsObject]>
  }
}
