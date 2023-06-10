PROTO_PATH=../../proto
GENERATED_PATH=./src/generated

npm install
rm -rf ${GENERATED_PATH}
mkdir -p ${GENERATED_PATH}

./node_modules/grpc-tools/bin/protoc \
    --plugin=protoc-gen-grpc=./node_modules/grpc-tools/bin/grpc_node_plugin \
    --plugin=protoc-gen-ts=./node_modules/grpc_tools_node_protoc_ts/bin/protoc-gen-ts \
    --js_out=import_style=commonjs,binary:$GENERATED_PATH \
    --ts_out=grpc_js:$GENERATED_PATH \
    --grpc_out=grpc_js:$GENERATED_PATH \
    --proto_path=$PROTO_PATH \
    $PROTO_PATH/*.proto
