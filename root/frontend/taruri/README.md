# Tauri

## 一、快速开始

### 1.1 安装 CLI 工具

````shell
# cargo install tauri-cli

# alias tauri='cargo tauri'

npm install -g @tauri-apps/cli
```

### 1.2 运行

```shell
tauri dev
````

### 1.3 打包

```shell
tauri build
```

## 二、打包

### 2.1 macOS 通用二进制

```shell
rustup target add aarch64-apple-darwin
rustup target add x86_64-apple-darwin--target

tauri build --target universal-apple-darwin

ls -lhF ./src-tauri/target/universal-apple-darwin/release/bundle/dmg
```
