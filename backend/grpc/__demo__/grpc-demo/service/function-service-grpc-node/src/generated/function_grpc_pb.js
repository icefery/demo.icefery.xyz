// GENERATED CODE -- DO NOT EDIT!

'use strict'
var grpc = require('@grpc/grpc-js')
var function_pb = require('./function_pb.js')

function serialize_FetchRequest(arg) {
  if (!(arg instanceof function_pb.FetchRequest)) {
    throw new Error('Expected argument of type FetchRequest')
  }
  return Buffer.from(arg.serializeBinary())
}

function deserialize_FetchRequest(buffer_arg) {
  return function_pb.FetchRequest.deserializeBinary(new Uint8Array(buffer_arg))
}

function serialize_FetchResponse(arg) {
  if (!(arg instanceof function_pb.FetchResponse)) {
    throw new Error('Expected argument of type FetchResponse')
  }
  return Buffer.from(arg.serializeBinary())
}

function deserialize_FetchResponse(buffer_arg) {
  return function_pb.FetchResponse.deserializeBinary(new Uint8Array(buffer_arg))
}

function serialize_InvokeRequest(arg) {
  if (!(arg instanceof function_pb.InvokeRequest)) {
    throw new Error('Expected argument of type InvokeRequest')
  }
  return Buffer.from(arg.serializeBinary())
}

function deserialize_InvokeRequest(buffer_arg) {
  return function_pb.InvokeRequest.deserializeBinary(new Uint8Array(buffer_arg))
}

function serialize_InvokeResponse(arg) {
  if (!(arg instanceof function_pb.InvokeResponse)) {
    throw new Error('Expected argument of type InvokeResponse')
  }
  return Buffer.from(arg.serializeBinary())
}

function deserialize_InvokeResponse(buffer_arg) {
  return function_pb.InvokeResponse.deserializeBinary(new Uint8Array(buffer_arg))
}

var FunctionService = (exports.FunctionService = {
  fetch: {
    path: '/Function/fetch',
    requestStream: false,
    responseStream: false,
    requestType: function_pb.FetchRequest,
    responseType: function_pb.FetchResponse,
    requestSerialize: serialize_FetchRequest,
    requestDeserialize: deserialize_FetchRequest,
    responseSerialize: serialize_FetchResponse,
    responseDeserialize: deserialize_FetchResponse
  },
  invoke: {
    path: '/Function/invoke',
    requestStream: false,
    responseStream: false,
    requestType: function_pb.InvokeRequest,
    responseType: function_pb.InvokeResponse,
    requestSerialize: serialize_InvokeRequest,
    requestDeserialize: deserialize_InvokeRequest,
    responseSerialize: serialize_InvokeResponse,
    responseDeserialize: deserialize_InvokeResponse
  }
})

exports.FunctionClient = grpc.makeGenericClientConstructor(FunctionService)
