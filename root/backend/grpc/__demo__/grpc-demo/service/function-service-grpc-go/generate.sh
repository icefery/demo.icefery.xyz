PROTO_PATH=../../proto
GENERATED_PATH=./src

go install google.golang.org/protobuf/cmd/protoc-gen-go@v1.28
go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@v1.2

protoc \
    --go_out=$GENERATED_PATH \
    --go-grpc_out=$GENERATED_PATH \
    --proto_path=$PROTO_PATH \
    $PROTO_PATH/function.proto
