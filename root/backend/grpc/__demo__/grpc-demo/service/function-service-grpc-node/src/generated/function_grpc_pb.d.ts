// package:
// file: function.proto

/* tslint:disable */
/* eslint-disable */

import * as grpc from '@grpc/grpc-js'
import * as function_pb from './function_pb'

interface IFunctionService extends grpc.ServiceDefinition<grpc.UntypedServiceImplementation> {
  fetch: IFunctionService_Ifetch
  invoke: IFunctionService_Iinvoke
}

interface IFunctionService_Ifetch extends grpc.MethodDefinition<function_pb.FetchRequest, function_pb.FetchResponse> {
  path: '/Function/fetch'
  requestStream: false
  responseStream: false
  requestSerialize: grpc.serialize<function_pb.FetchRequest>
  requestDeserialize: grpc.deserialize<function_pb.FetchRequest>
  responseSerialize: grpc.serialize<function_pb.FetchResponse>
  responseDeserialize: grpc.deserialize<function_pb.FetchResponse>
}
interface IFunctionService_Iinvoke extends grpc.MethodDefinition<function_pb.InvokeRequest, function_pb.InvokeResponse> {
  path: '/Function/invoke'
  requestStream: false
  responseStream: false
  requestSerialize: grpc.serialize<function_pb.InvokeRequest>
  requestDeserialize: grpc.deserialize<function_pb.InvokeRequest>
  responseSerialize: grpc.serialize<function_pb.InvokeResponse>
  responseDeserialize: grpc.deserialize<function_pb.InvokeResponse>
}

export const FunctionService: IFunctionService

export interface IFunctionServer extends grpc.UntypedServiceImplementation {
  fetch: grpc.handleUnaryCall<function_pb.FetchRequest, function_pb.FetchResponse>
  invoke: grpc.handleUnaryCall<function_pb.InvokeRequest, function_pb.InvokeResponse>
}

export interface IFunctionClient {
  fetch(request: function_pb.FetchRequest, callback: (error: grpc.ServiceError | null, response: function_pb.FetchResponse) => void): grpc.ClientUnaryCall
  fetch(
    request: function_pb.FetchRequest,
    metadata: grpc.Metadata,
    callback: (error: grpc.ServiceError | null, response: function_pb.FetchResponse) => void
  ): grpc.ClientUnaryCall
  fetch(
    request: function_pb.FetchRequest,
    metadata: grpc.Metadata,
    options: Partial<grpc.CallOptions>,
    callback: (error: grpc.ServiceError | null, response: function_pb.FetchResponse) => void
  ): grpc.ClientUnaryCall
  invoke(request: function_pb.InvokeRequest, callback: (error: grpc.ServiceError | null, response: function_pb.InvokeResponse) => void): grpc.ClientUnaryCall
  invoke(
    request: function_pb.InvokeRequest,
    metadata: grpc.Metadata,
    callback: (error: grpc.ServiceError | null, response: function_pb.InvokeResponse) => void
  ): grpc.ClientUnaryCall
  invoke(
    request: function_pb.InvokeRequest,
    metadata: grpc.Metadata,
    options: Partial<grpc.CallOptions>,
    callback: (error: grpc.ServiceError | null, response: function_pb.InvokeResponse) => void
  ): grpc.ClientUnaryCall
}

export class FunctionClient extends grpc.Client implements IFunctionClient {
  constructor(address: string, credentials: grpc.ChannelCredentials, options?: Partial<grpc.ClientOptions>)
  public fetch(
    request: function_pb.FetchRequest,
    callback: (error: grpc.ServiceError | null, response: function_pb.FetchResponse) => void
  ): grpc.ClientUnaryCall
  public fetch(
    request: function_pb.FetchRequest,
    metadata: grpc.Metadata,
    callback: (error: grpc.ServiceError | null, response: function_pb.FetchResponse) => void
  ): grpc.ClientUnaryCall
  public fetch(
    request: function_pb.FetchRequest,
    metadata: grpc.Metadata,
    options: Partial<grpc.CallOptions>,
    callback: (error: grpc.ServiceError | null, response: function_pb.FetchResponse) => void
  ): grpc.ClientUnaryCall
  public invoke(
    request: function_pb.InvokeRequest,
    callback: (error: grpc.ServiceError | null, response: function_pb.InvokeResponse) => void
  ): grpc.ClientUnaryCall
  public invoke(
    request: function_pb.InvokeRequest,
    metadata: grpc.Metadata,
    callback: (error: grpc.ServiceError | null, response: function_pb.InvokeResponse) => void
  ): grpc.ClientUnaryCall
  public invoke(
    request: function_pb.InvokeRequest,
    metadata: grpc.Metadata,
    options: Partial<grpc.CallOptions>,
    callback: (error: grpc.ServiceError | null, response: function_pb.InvokeResponse) => void
  ): grpc.ClientUnaryCall
}
