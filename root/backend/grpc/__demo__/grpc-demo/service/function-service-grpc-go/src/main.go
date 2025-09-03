package main

import (
	"context"
	"fmt"
	pb "function-service-grpc-go/src/generated"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
	"net"
	"os"
	"runtime"
)

const (
	PORT = 8081
)

type Server struct {
	pb.UnimplementedFunctionServer
}

func (server Server) Fetch(context context.Context, request *pb.FetchRequest) (*pb.FetchResponse, error) {
	hostname, _ := os.Hostname()
	response := &pb.FetchResponse{
		Instances: []*pb.Instance{
			&pb.Instance{
				Name:     "fetch",
				Addr:     fmt.Sprintf("%s:%d", hostname, PORT),
				Platform: fmt.Sprintf("%s/%s", runtime.GOOS, runtime.GOARCH),
				Language: "go",
				Protocol: "grpc",
			},
			&pb.Instance{
				Name:     "invoke",
				Addr:     fmt.Sprintf("%s:%d", hostname, PORT),
				Platform: fmt.Sprintf("%s/%s", runtime.GOOS, runtime.GOARCH),
				Language: "go",
				Protocol: "grpc",
			},
		},
	}
	return response, nil
}

func (server Server) Invoke(context context.Context, request *pb.InvokeRequest) (*pb.InvokeResponse, error) {
	results := map[string]*pb.Invocation{}
	for _, invocation := range request.Invocations {
		host := invocation.Host
		port := invocation.Port
		connection, _ := grpc.Dial(fmt.Sprintf("%s:%d", host, port), grpc.WithTransportCredentials(insecure.NewCredentials()))
		client := pb.NewFunctionClient(connection)

		fetchRequest := &pb.FetchRequest{}
		fetchResponse, _ := client.Fetch(context, fetchRequest)
		key := fmt.Sprintf("%s:%d", host, port)
		value := &pb.Invocation{
			Instances: fetchResponse.Instances,
		}
		results[key] = value
	}
	response := &pb.InvokeResponse{
		Results: results,
	}
	return response, nil
}

func main() {
	listen, _ := net.Listen("tcp", fmt.Sprintf(":%d", PORT))
	server := grpc.NewServer()
	pb.RegisterFunctionServer(server, &Server{})
	_ = server.Serve(listen)
}
