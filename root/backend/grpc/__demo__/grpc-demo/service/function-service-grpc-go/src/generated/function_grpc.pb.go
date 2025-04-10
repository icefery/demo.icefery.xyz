// Code generated by protoc-gen-go-grpc. DO NOT EDIT.
// versions:
// - protoc-gen-go-grpc v1.2.0
// - protoc             v4.23.1
// source: function.proto

package generated

import (
	context "context"
	grpc "google.golang.org/grpc"
	codes "google.golang.org/grpc/codes"
	status "google.golang.org/grpc/status"
)

// This is a compile-time assertion to ensure that this generated file
// is compatible with the grpc package it is being compiled against.
// Requires gRPC-Go v1.32.0 or later.
const _ = grpc.SupportPackageIsVersion7

// FunctionClient is the client API for Function service.
//
// For semantics around ctx use and closing/ending streaming RPCs, please refer to https://pkg.go.dev/google.golang.org/grpc/?tab=doc#ClientConn.NewStream.
type FunctionClient interface {
	Fetch(ctx context.Context, in *FetchRequest, opts ...grpc.CallOption) (*FetchResponse, error)
	Invoke(ctx context.Context, in *InvokeRequest, opts ...grpc.CallOption) (*InvokeResponse, error)
}

type functionClient struct {
	cc grpc.ClientConnInterface
}

func NewFunctionClient(cc grpc.ClientConnInterface) FunctionClient {
	return &functionClient{cc}
}

func (c *functionClient) Fetch(ctx context.Context, in *FetchRequest, opts ...grpc.CallOption) (*FetchResponse, error) {
	out := new(FetchResponse)
	err := c.cc.Invoke(ctx, "/Function/fetch", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *functionClient) Invoke(ctx context.Context, in *InvokeRequest, opts ...grpc.CallOption) (*InvokeResponse, error) {
	out := new(InvokeResponse)
	err := c.cc.Invoke(ctx, "/Function/invoke", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

// FunctionServer is the server API for Function service.
// All implementations must embed UnimplementedFunctionServer
// for forward compatibility
type FunctionServer interface {
	Fetch(context.Context, *FetchRequest) (*FetchResponse, error)
	Invoke(context.Context, *InvokeRequest) (*InvokeResponse, error)
	mustEmbedUnimplementedFunctionServer()
}

// UnimplementedFunctionServer must be embedded to have forward compatible implementations.
type UnimplementedFunctionServer struct {
}

func (UnimplementedFunctionServer) Fetch(context.Context, *FetchRequest) (*FetchResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method Fetch not implemented")
}
func (UnimplementedFunctionServer) Invoke(context.Context, *InvokeRequest) (*InvokeResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method Invoke not implemented")
}
func (UnimplementedFunctionServer) mustEmbedUnimplementedFunctionServer() {}

// UnsafeFunctionServer may be embedded to opt out of forward compatibility for this service.
// Use of this interface is not recommended, as added methods to FunctionServer will
// result in compilation errors.
type UnsafeFunctionServer interface {
	mustEmbedUnimplementedFunctionServer()
}

func RegisterFunctionServer(s grpc.ServiceRegistrar, srv FunctionServer) {
	s.RegisterService(&Function_ServiceDesc, srv)
}

func _Function_Fetch_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(FetchRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(FunctionServer).Fetch(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/Function/fetch",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(FunctionServer).Fetch(ctx, req.(*FetchRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _Function_Invoke_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(InvokeRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(FunctionServer).Invoke(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/Function/invoke",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(FunctionServer).Invoke(ctx, req.(*InvokeRequest))
	}
	return interceptor(ctx, in, info, handler)
}

// Function_ServiceDesc is the grpc.ServiceDesc for Function service.
// It's only intended for direct use with grpc.RegisterService,
// and not to be introspected or modified (even as a copy)
var Function_ServiceDesc = grpc.ServiceDesc{
	ServiceName: "Function",
	HandlerType: (*FunctionServer)(nil),
	Methods: []grpc.MethodDesc{
		{
			MethodName: "fetch",
			Handler:    _Function_Fetch_Handler,
		},
		{
			MethodName: "invoke",
			Handler:    _Function_Invoke_Handler,
		},
	},
	Streams:  []grpc.StreamDesc{},
	Metadata: "function.proto",
}
